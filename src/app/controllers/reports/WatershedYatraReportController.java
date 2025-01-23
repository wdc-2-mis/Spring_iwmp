package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import app.bean.AssetIdBean;
import app.bean.Login;
import app.bean.UnfreezeBaselineSurveyDataBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;
import app.watershedyatra.bean.NodalOfficerBean;

@Controller("WatershedYatraReportController")
public class WatershedYatraReportController {
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	WatershedYatraReportService ser;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> blockList;
	private Map<String, String> gpList;
	
	@RequestMapping(value="/getWatershedYatraReport", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraReport(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		String grampan= request.getParameter("grampan");
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("WatershedYatra/watershedYatraReportRoutePlan");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = ser.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				blockList = ser.getblockList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("blockList", blockList);}
				mav.addObject("blkd", block);
				
			if( block!=null && !block.equalsIgnoreCase("") && !block.equals("0")) {
				gpList = ser.getGramPanchyatList(Integer.parseInt(block));
				mav.addObject("gpList", gpList);}
				mav.addObject("grampn", grampan);	
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getRoutePlanReportData", method = RequestMethod.POST)
	public ModelAndView getRoutePlanReportData(HttpServletRequest request, HttpServletResponse response)
	{
			session = request.getSession(true);
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String block= request.getParameter("block");
			String grampan= request.getParameter("grampan");
			
			List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
			
			
			if(session!=null && session.getAttribute("loginID")!=null) {
				
				mav = new ModelAndView("WatershedYatra/watershedYatraReportRoutePlan");
				mav.addObject("menu", menuController.getMenuUserId(request));
				
				list=ser.getRoutePlanReportData(Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(block), Integer.parseInt(grampan));
				mav.addObject("routePlanList", list);
				mav.addObject("routePlanListSize", list.size());
				
				
				stateList=stateMasterService.getAllState();
				mav.addObject("stateList", stateList);
				mav.addObject("state", userState);
				
				if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = ser.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);}
				mav.addObject("district", district);
				
				if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
					blockList = ser.getblockList(Integer.parseInt(userState), Integer.parseInt(district));
					mav.addObject("blockList", blockList);}
					mav.addObject("blkd", block);
					
				if( block!=null && !block.equalsIgnoreCase("") && !block.equals("0")) {
					gpList = ser.getGramPanchyatList(Integer.parseInt(block));
					mav.addObject("gpList", gpList);}
					mav.addObject("grampn", grampan);	
				
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
			return mav; 
	}
	
	@RequestMapping(value = "/downloadRoutePlanReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadRoutePlanReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String blkName= request.getParameter("blkName");
		String gpkName= request.getParameter("gpkName");
		
		int stCode = Integer.parseInt(request.getParameter("state"));
		int distCode = Integer.parseInt(request.getParameter("district"));
		int blkCode = Integer.parseInt(request.getParameter("block"));
		int gpCode = Integer.parseInt(request.getParameter("grampan"));
		
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Route Plan");
			document.addCreationDate();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer=PdfWriter.getInstance(document, baos);
			
			document.open(); 
	       
			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
			Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD );
			Font bf8 = new Font(FontFamily.HELVETICA, 8);
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

			PdfPTable table = null;
			document.newPage();
			Paragraph paragraph3 = null; 
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
			
				paragraph3 = new Paragraph("Route Plan for Van Traveling/Watershed Mahotsawa", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(8);
				table.setWidths(new int[] { 2, 5, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(2);
				
				list=ser.getRoutePlanReportData(stCode, distCode, blkCode, gpCode);
				
				CommonFunctions.insertCellHeader(table,"State : "+ stName+"     District : "+distName+" Block : "+blkName+"  Gram Panchyat Name : "+gpkName, Element.ALIGN_LEFT, 8, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Sl. No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Date and Time", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Block Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Gram Panchayat Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Location (Nearby/Milestone)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int stcd=0;
				int i=0;
				int k=1;
				int totNum = 0;
				if(list.size()!=0)
					for( i=0;i<list.size();i++) 
					{
						if(list.get(i).getFlagwise()!=totNum){
							CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
							k=k+1;
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						}
						if(list.get(i).getDate1()!=null){
							CommonFunctions.insertCell(table, list.get(i).getDate1(), Element.ALIGN_LEFT, 1, 1, bf8);	
						}	
						if(list.get(i).getDate1()==null){
							CommonFunctions.insertCell(table, list.get(i).getDate2(), Element.ALIGN_LEFT, 1, 1, bf8);	
						}
						if(Integer.parseInt(list.get(i).getSt_code())!=stcd) {
							CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						}
						CommonFunctions.insertCell(table, list.get(i).getDistrict(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getBlockname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getGramname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getVillagename(), Element.ALIGN_LEFT, 1, 1, bf8);
						if(list.get(i).getLocation1()!=null){
							CommonFunctions.insertCell(table, list.get(i).getLocation1(), Element.ALIGN_LEFT, 1, 1, bf8);
						}
						if(list.get(i).getLocation1()==null){
							CommonFunctions.insertCell(table, list.get(i).getLocation2(), Element.ALIGN_LEFT, 1, 1, bf8);
						}
						totNum=list.get(i).getFlagwise();
						stcd=Integer.parseInt(list.get(i).getSt_code());
					}
				
				
				if(list.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
				
					
		document.add(table);
		table = new PdfPTable(1);
		table.setWidthPercentage(70);
		table.setSpacingBefore(15f);
		table.setSpacingAfter(0f);
		CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
		CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
		document.add(table);
		document.close();
		response.setContentType("application/pdf");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-Disposition", "attachment;RoutePlan.pdf");
		response.setHeader("Pragma", "public");
		response.setContentLength(baos.size());
		OutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
	
	} 
	catch (Exception ex) 
	{
		ex.printStackTrace();
	} 
	
	return null;
}
	
	@RequestMapping(value="/getWatershedYatraNodalReport", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraNodalReport(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String lvl= request.getParameter("level");
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("WatershedYatra/watershedYatraReportNodalOfficer");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("state", "State");
			map.put("district", "District");
			map.put("block", "Block/Project");
			map.put("village", "Village/Van Standing Point");
			mav.addObject("level",map);
			mav.addObject("lvl",lvl);
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = ser.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);}
				mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				blockList = ser.getblockList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("blockList", blockList);}
				mav.addObject("blkd", block);
				
		
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}

	@RequestMapping(value="/getNodalOfficerReportData", method = RequestMethod.POST)
	public ModelAndView getNodalOfficerReportData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String lvl= request.getParameter("level");
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			if(district==null)
				district="0";
			if(block==null)
				block="0";
			
			mav = new ModelAndView("WatershedYatra/watershedYatraReportNodalOfficer");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			list=ser.getNodalOfficerReportData(lvl, Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(block));
			mav.addObject("routePlanList", list);
			mav.addObject("routePlanListSize", list.size());
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("state", "State");
			map.put("district", "District");
			map.put("block", "Block/Project");
			map.put("village", "Village/Van Standing Point");
			mav.addObject("level",map);
			mav.addObject("lvl",lvl);
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = ser.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);}
				mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				blockList = ser.getblockList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("blockList", blockList);}
				mav.addObject("blkd", block);
				
		
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value = "/downloadNodalOfficerReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadNodalOfficerReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String blkName= request.getParameter("blkName");
		String lvlName= request.getParameter("lvlName");

		String lvl= request.getParameter("level");
		int stCode = Integer.parseInt(request.getParameter("state"));
		int distCode = Integer.parseInt(request.getParameter("district"));
		int blkCode = Integer.parseInt(request.getParameter("block"));
		
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Nodal Officer");
			document.addCreationDate();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer=PdfWriter.getInstance(document, baos);
			
			document.open(); 
	       
			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
			Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD );
			Font bf8 = new Font(FontFamily.HELVETICA, 8);
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

			PdfPTable table = null;
			document.newPage();
			Paragraph paragraph3 = null; 
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
			
				paragraph3 = new Paragraph("View Details of Nodal Officers", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
			if(lvl.equals("a") || lvl.equals("block") || lvl.equals("village")){
				table = new PdfPTable(9);
				table.setWidths(new int[] { 2, 5, 5, 5, 5, 5, 5, 5, 5 });
			}
			if(lvl.equals("district") ){
				table = new PdfPTable(8);
				table.setWidths(new int[] { 2, 5, 5, 5, 5, 5, 5, 5 });
			}
			if(lvl.equals("state") ){
				table = new PdfPTable(7);
				table.setWidths(new int[] { 2, 5, 5, 5, 5, 5, 5});
			}	
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(2);
				
				list=ser.getNodalOfficerReportData(lvl, stCode, distCode, blkCode);
				
				if(lvl.equals("a") || lvl.equals("block") || lvl.equals("village"))
					CommonFunctions.insertCellHeader(table,"Level : "+ lvlName+" State : "+ stName+" District : "+distName+" Block : "+blkName, Element.ALIGN_LEFT, 9, 1, bf8Bold);
				if(lvl.equals("district") )
					CommonFunctions.insertCellHeader(table,"Level : "+ lvlName+" State : "+ stName+" District : "+distName, Element.ALIGN_LEFT, 8, 1, bf8Bold);
				if(lvl.equals("state") )
					CommonFunctions.insertCellHeader(table,"Level : "+ lvlName+" State : "+ stName, Element.ALIGN_LEFT, 7, 1, bf8Bold);
				
			//	CommonFunctions.insertCellHeader(table,"Level : "+ lvlName+"State : "+ stName+"     District : "+distName+"     Block : "+blkName, Element.ALIGN_LEFT, 8, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Sl. No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Level", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				if(lvl.equals("a") || lvl.equals("district") || lvl.equals("block") || lvl.equals("village") )
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				if(lvl.equals("a") || lvl.equals("block") || lvl.equals("village") )
				CommonFunctions.insertCellHeader(table, "Block Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Designation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Mobile", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Email Id", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int stcd=0;
				int distcd=0;
				int i=0;
				int k=1;
				int totNum = 0;
				if(list.size()!=0)
					for( i=0;i<list.size();i++) 
					{
						
							CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
							if(list.get(i).getLevel().equals("state") )
								CommonFunctions.insertCell(table, "State", Element.ALIGN_LEFT, 1, 1, bf8);
							if(list.get(i).getLevel().equals("district") )
								CommonFunctions.insertCell(table, "District", Element.ALIGN_LEFT, 1, 1, bf8);
							if(list.get(i).getLevel().equals("block") )
								CommonFunctions.insertCell(table, "Block/Project", Element.ALIGN_LEFT, 1, 1, bf8);
							if(list.get(i).getLevel().equals("village") )
								CommonFunctions.insertCell(table, "Village/Van Standing Point", Element.ALIGN_LEFT, 1, 1, bf8);
						
							if(Integer.parseInt(list.get(i).getSt_code())!=stcd) {
								CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
							else {
								CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							}
							if(lvl.equals("a") || lvl.equals("district") || lvl.equals("block") || lvl.equals("village") ) {
								
								if(Integer.parseInt(list.get(i).getDcode())!=distcd) {
									CommonFunctions.insertCell(table, list.get(i).getDistrict(), Element.ALIGN_LEFT, 1, 1, bf8);
								}
								else {
									CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
								}
							}	
							if(lvl.equals("a") || lvl.equals("block") || lvl.equals("village") )
								CommonFunctions.insertCell(table, list.get(i).getBlockname(), Element.ALIGN_LEFT, 1, 1, bf8);
							
							CommonFunctions.insertCell(table, list.get(i).getNodal_name(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list.get(i).getDesignation(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list.get(i).getMobile(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list.get(i).getEmail(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						stcd=Integer.parseInt(list.get(i).getSt_code());
					}
				
				
				if(list.size()==0) {
					if(lvl.equals("a") || lvl.equals("block") || lvl.equals("village"))
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 9, 1, bf8);
					if(lvl.equals("district") )
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
					if(lvl.equals("state") )
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
				}	
		document.add(table);
		table = new PdfPTable(1);
		table.setWidthPercentage(70);
		table.setSpacingBefore(15f);
		table.setSpacingAfter(0f);
		CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
		CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
		document.add(table);
		document.close();
		response.setContentType("application/pdf");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-Disposition", "attachment;NodalOfficer.pdf");
		response.setHeader("Pragma", "public");
		response.setContentLength(baos.size());
		OutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
	
	} 
	catch (Exception ex) 
	{
		ex.printStackTrace();
	} 
	
	return null;
}
}
