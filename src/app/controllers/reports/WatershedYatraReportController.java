package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

import app.bean.Login;
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
			
		//	String fullPath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/CircularMessageAlert/WDC-PMKSY_file_0060.png";
	    //    String removePath = "/usr/local/apache-tomcat90-nic/webapps/filepath/PRD/CircularMessageAlert/";
	        
	    //    String result = fullPath.replace(removePath, "");
	    //    System.out.println("image "+result);
			
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
			
				table = new PdfPTable(9);
				table.setWidths(new int[] { 2, 5, 5, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(2);
				
				list=ser.getRoutePlanReportData(stCode, distCode, blkCode, gpCode);
				
				CommonFunctions.insertCellHeader(table,"State : "+ stName+"     District : "+distName+" Block : "+blkName+"  Gram Panchayat Name : "+gpkName, Element.ALIGN_LEFT, 9, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Sl. No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Route Plan Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Route Plan Time", Element.ALIGN_CENTER, 1, 1, bf8Bold);
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
							CommonFunctions.insertCell(table, list.get(i).getTime1(), Element.ALIGN_LEFT, 1, 1, bf8);
						}	
						if(list.get(i).getDate1()==null){
							CommonFunctions.insertCell(table, list.get(i).getDate2(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list.get(i).getTime2(), Element.ALIGN_LEFT, 1, 1, bf8);
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
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 9, 1, bf8);
				
					
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
	
	@RequestMapping(value = "/downloadRoutePlanReportExcel", method = RequestMethod.POST)
	@ResponseBody
	public String downloadRoutePlanReportExcel(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String blkName= request.getParameter("blkName");
		String gpkName= request.getParameter("gpkName");
		
		int stCode = Integer.parseInt(request.getParameter("state"));
		int distCode = Integer.parseInt(request.getParameter("district"));
		int blkCode = Integer.parseInt(request.getParameter("block"));
		int gpCode = Integer.parseInt(request.getParameter("grampan"));
		
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		
		list=ser.getRoutePlanReportData(stCode, distCode, blkCode, gpCode);
		
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Route Plan for Van Traveling Watershed Mahotsawa");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Route Plan for Van Traveling/Watershed Mahotsawa";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(5,5,0,8);
		sheet.addMergedRegion(mergedRegion);
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State : "+ stName+"     District : "+distName+"     Block : "+blkName+"     Gram Panchayat : "+gpkName);
		cell.setCellStyle(style);
		
		for(int i=1;i<9;i++)
		{
			cell =rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("Sl. No.");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Route Plan Date");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Route Plan Time");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("State Name");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("District Name");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Block Name");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Gram Panchayat Name");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Village Name");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Location (Nearby/Milestone)");
		cell.setCellStyle(style);
		
			
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 8;
		int totNum = 0;
		String StName = "";
		
	    for(NodalOfficerBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	if(bean.getFlagwise() != totNum)
	    	{
	        	row.createCell(0).setCellValue(sno);
	        	sno++;
	        }
	    	if(bean.getDate1() != null)
	    	{
	    		row.createCell(1).setCellValue(bean.getDate1());
	    		row.createCell(2).setCellValue(bean.getTime1());
	    	}
	    	if(bean.getDate1() == null)
	    	{
		    	row.createCell(1).setCellValue(bean.getDate2());
	    		row.createCell(2).setCellValue(bean.getTime2());
	    	}
	    	if(!StName.equals(bean.getStname()))
	    	{
	        	row.createCell(3).setCellValue(bean.getStname());
	        }
	    	row.createCell(4).setCellValue(bean.getDistrict());
	    	row.createCell(5).setCellValue(bean.getBlockname());
	    	row.createCell(6).setCellValue(bean.getGramname());
	    	row.createCell(7).setCellValue(bean.getVillagename());
	    	if(bean.getLocation1() != null)
	    		row.createCell(8).setCellValue(bean.getLocation1());
	    	if(bean.getLocation1() == null)
		    	row.createCell(8).setCellValue(bean.getLocation2());
	    	
	    	totNum = bean.getFlagwise();
	    	StName = bean.getStname();
	    	rowno++;
	    }
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 8);
	    String fileName = "attachment; filename=Report Route Plan.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "WatershedYatra/watershedYatraReportRoutePlan";
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
		
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
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
			
		}
		else {
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
		//String district = request.getParameter("district") != null ? request.getParameter("district") : "0";
		int distCode = Integer.parseInt(request.getParameter("district") != null ? request.getParameter("district") : "0");
		int blkCode = Integer.parseInt(request.getParameter("block") != null ? request.getParameter("block") : "0");
		
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
				table.setWidths(new int[] { 3, 5, 10, 5, 5, 10, 5, 5, 10 });
				table.setWidthPercentage(90);
			}
			if(lvl.equals("district") ){
				table = new PdfPTable(8);
				table.setWidths(new int[] { 3, 5, 10, 5, 10, 5, 5, 10 });
				table.setWidthPercentage(80);
			}
			if(lvl.equals("state") ){
				table = new PdfPTable(7);
				table.setWidths(new int[] { 3, 5, 10, 10, 5, 5, 10});
				table.setWidthPercentage(70);
			}	
			
				//table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(2);
				
				list=ser.getNodalOfficerReportData(lvl, stCode, distCode, blkCode);
				
				if(lvl.equals("a") ) {
					CommonFunctions.insertCellHeader(table,"Level : "+ lvlName+", State Name: "+ stName, Element.ALIGN_LEFT, 9, 1, bf8Bold);
				}	
				if(lvl.equals("district") ) {
					CommonFunctions.insertCellHeader(table,"Level : "+ lvlName+", State : "+ stName+", District : "+distName, Element.ALIGN_LEFT, 8, 1, bf8Bold);
				}	
				if(lvl.equals("state") ) {
					CommonFunctions.insertCellHeader(table,"Level : "+ lvlName+", State : "+ stName, Element.ALIGN_LEFT, 7, 1, bf8Bold);
				}
				if(lvl.equals("block") || lvl.equals("village") ) {
					CommonFunctions.insertCellHeader(table,"Level : "+ lvlName+", State : "+ stName+", District : "+distName+", block : "+blkName , Element.ALIGN_LEFT, 9, 1, bf8Bold);
				}
				CommonFunctions.insertCellHeader(table, "Sl. No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Level", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				if(lvl.equals("a") || lvl.equals("district") || lvl.equals("block") || lvl.equals("village") ) {
					CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				}
				if(lvl.equals("a") || lvl.equals("block") || lvl.equals("village") ) {
					CommonFunctions.insertCellHeader(table, "Block Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				}
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
							if(lvl.equals("a") || lvl.equals("district") || lvl.equals("block") || lvl.equals("village") ) 
							{
								if(list.get(i).getDistrict()!=null)
								{
									CommonFunctions.insertCell(table, list.get(i).getDistrict(), Element.ALIGN_LEFT, 1, 1, bf8);
								}
								else {
									CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
								}
							}	
							if(lvl.equals("a") || lvl.equals("block") || lvl.equals("village") ) {
								
								if(list.get(i).getBlockname()!=null)
								{
									CommonFunctions.insertCell(table, list.get(i).getBlockname(), Element.ALIGN_LEFT, 1, 1, bf8);
								}
								else {
									CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
								}
							}
							
							CommonFunctions.insertCell(table, list.get(i).getNodal_name(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list.get(i).getDesignation(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list.get(i).getMobile(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list.get(i).getEmail(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						stcd=Integer.parseInt(list.get(i).getSt_code());
						k=k+1;
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
	
	@RequestMapping(value = "/downloadNodalOfficerReportExcel", method = RequestMethod.POST)
	@ResponseBody
	public String downloadNodalOfficerReportExcel(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		String Level= request.getParameter("lvlName");
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String blkName= request.getParameter("blkName");
		
		String lvl = request.getParameter("level");
		int stCode = Integer.parseInt(request.getParameter("state"));
		
		int distCode = request.getParameter("district") != null ? Integer.parseInt(request.getParameter("district")) : 0;
		int blkCode = request.getParameter("block") != null ? Integer.parseInt(request.getParameter("block")) : 0;
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("state", "State");
		map.put("district", "District");
		map.put("block", "Block/Project");
		map.put("village", "Village/Van Standing Point");
		
		
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		
		list=ser.getNodalOfficerReportData(lvl, stCode, distCode, blkCode);
		
		Workbook workbook = new XSSFWorkbook();
		
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Details of Nodal Officers");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Details of Nodal Officers";
		String areaAmtValDetail ="";
		
		Map<String, Integer> headerCount = new HashMap<>();
		headerCount.put("a", 8);
		headerCount.put("block", 8);
		headerCount.put("village", 8);
		headerCount.put("district", 7);
		headerCount.put("state", 6);
		
		int n = headerCount.get(lvl);
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, n, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(5,5,0,n);
		sheet.addMergedRegion(mergedRegion);
		
		Row rowDetail = sheet.createRow(5);
		
		String levelInfo = "Level : " + Level + "     State : " + stName;

		if (Level.equals("District")) {
		    levelInfo += "     District : " + distName;
		} else if (Level.equals("Block/Project") || Level.equals("Village/Van Standing Point")) {
		    levelInfo += "     District : " + distName + "     Block : " + blkName;
		}
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue(levelInfo);
		cell.setCellStyle(style);
		
		for(int i=1;i<=n;i++)
		{
			cell =rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		// Define the header columns based on the level
		List<String> headerColumns = new ArrayList<>();
		headerColumns.add("S.No.");
		headerColumns.add("Level");
		headerColumns.add("State Name");
		
		if (lvl.equals("a") || lvl.equals("district") || lvl.equals("block") || lvl.equals("village")) {
		    headerColumns.add("District Name");
		}
		
		if (lvl.equals("a") || lvl.equals("block") || lvl.equals("village")) {
		    headerColumns.add("Block Name");
		}
		
		headerColumns.add("Name");
		headerColumns.add("Designation");
		headerColumns.add("Mobile");
		headerColumns.add("Email Id");
		
		
		Row rowhead = sheet.createRow(6);
		
		for (int i = 0; i < headerColumns.size(); i++) {
		    cell = rowhead.createCell(i);
		    cell.setCellValue(headerColumns.get(i));
		    cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<=n;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 8;
		String state = "";
		
	    for(NodalOfficerBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(map.get(bean.getLevel()));
	    	if(!state.equals(bean.getStname()))
	    		row.createCell(2).setCellValue(bean.getStname());
	    	int c = 3;
	    	if(lvl.equals("a") || lvl.equals("district") || lvl.equals("block") || lvl.equals("village"))
	    	{
	    		row.createCell(c).setCellValue(bean.getDistrict());
	    		c++;
	    	}
	    	if(lvl.equals("a") || lvl.equals("block") || lvl.equals("village"))
	    	{
	    		row.createCell(c).setCellValue(bean.getBlockname());
	    		c++;
	    	}
	    	row.createCell(c).setCellValue(bean.getNodal_name());
	    	c++;
	    	row.createCell(c).setCellValue(bean.getDesignation());
	    	c++;
	    	row.createCell(c).setCellValue(bean.getMobile());
	    	c++;
	    	row.createCell(c).setCellValue(bean.getEmail());
	    	c++;
	    	
	    	state = bean.getStname();
	    	sno++;
	    	rowno++;
	    }
	    
	    
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), n);
	    String fileName = "attachment; filename=Report Nodal Officers.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "WatershedYatra/watershedYatraReportNodalOfficer";
	}
	
}
