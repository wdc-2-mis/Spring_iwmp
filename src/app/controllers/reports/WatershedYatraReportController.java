package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.bean.PreYatraPreparationBean;

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
		//session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		String grampan= request.getParameter("grampan");
		/*if(session!=null && session.getAttribute("loginID")!=null) {*/
			
			mav = new ModelAndView("WatershedYatra/watershedYatraReportRoutePlan");
			/* mav.addObject("menu", menuController.getMenuUserId(request)); */
			
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
			
				/*
				 * }else { mav = new ModelAndView("login"); mav.addObject("login", new Login());
				 * }
				 */
		return mav; 
	}
	
	@RequestMapping(value="/getRoutePlanReportData", method = RequestMethod.POST)
	public ModelAndView getRoutePlanReportData(HttpServletRequest request, HttpServletResponse response)
	{
			//session = request.getSession(true);
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String block= request.getParameter("block");
			String grampan= request.getParameter("grampan");
			
			List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
			
			
			/*if(session!=null && session.getAttribute("loginID")!=null) {*/
				
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
				
					/*
					 * }else { mav = new ModelAndView("login"); mav.addObject("login", new Login());
					 * }
					 */
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
		/* session = request.getSession(true); */
		
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
		/* session = request.getSession(true); */
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String lvl= request.getParameter("level");
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		
		/*
		 * if(session!=null && session.getAttribute("loginID")!=null) {
		 */
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
			
				/*
				 * } else { mav = new ModelAndView("login"); mav.addObject("login", new
				 * Login()); }
				 */
		return mav; 
	}

	@RequestMapping(value="/getNodalOfficerReportData", method = RequestMethod.POST)
	public ModelAndView getNodalOfficerReportData(HttpServletRequest request, HttpServletResponse response)
	{
		/* session = request.getSession(true); */
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String lvl= request.getParameter("level");
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		/*if(session!=null && session.getAttribute("loginID")!=null) {*/
			
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
				
		
			
				/*
				 * }else { mav = new ModelAndView("login"); mav.addObject("login", new Login());
				 * }
				 */
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
		/* session = request.getSession(true); */
		
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
	
	
	@RequestMapping(value="/getInaugurationReport", method = RequestMethod.GET)
	public ModelAndView getInaugurationReport(HttpServletRequest request, HttpServletResponse response)
	{
		/* session = request.getSession(true); */
		
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		
		/*if(session!=null && session.getAttribute("loginID")!=null) {*/
			
			mav = new ModelAndView("WatershedYatra/watershedYatraReportInauguration");
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
				
				/*
				 * }else { mav = new ModelAndView("login"); mav.addObject("login", new Login());
				 * }
				 */
		return mav; 
	}
	
	@RequestMapping(value="/getInaugurationReportData", method = RequestMethod.POST)
	public ModelAndView getInaugurationReportData(HttpServletRequest request, HttpServletResponse response)
	{
		/* session = request.getSession(true); */
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String block= request.getParameter("blockk");
			String userdate= request.getParameter("userdate");
			String userdateto= request.getParameter("userdateto");
			
			List<InaugurationBean> list = new ArrayList<InaugurationBean>();
			
			String fromDateStr=null;
			String toDateStr =null;
			
			if(!userdate.equals("")) {
	        LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
	        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			if(!userdateto.equals("")) {
	        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
	        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			
			/*if(session!=null && session.getAttribute("loginID")!=null) {*/
				
				mav = new ModelAndView("WatershedYatra/watershedYatraReportInauguration");
				mav.addObject("menu", menuController.getMenuUserId(request));
				
				list=ser.getInaugurationReportData(Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(block), userdate, userdateto );
				mav.addObject("inaugurationList", list);
				mav.addObject("inaugurationListSize", list.size());
				
				
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
					mav.addObject("udate", userdate);
					mav.addObject("dateto", userdateto);
					mav.addObject("fromDateStr", fromDateStr);
					mav.addObject("toDateStr", toDateStr);
					
					
					/*
					 * }else { mav = new ModelAndView("login"); mav.addObject("login", new Login());
					 * }
					 */
			return mav; 
	}
	
	
	@RequestMapping(value = "/downloadExcelInaugurationReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelInaugurationReport(HttpServletRequest request, HttpServletResponse response)
	{
		/*
		 * session = request.getSession(true);
		 */
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String blkName= request.getParameter("blkName");
		
		int stCode = Integer.parseInt(request.getParameter("state"));
		int distCode = Integer.parseInt(request.getParameter("district"));
		int blkCode = Integer.parseInt(request.getParameter("blockk"));
		String userdate= request.getParameter("udate");
		String userdateto= request.getParameter("userdate2");
		
		String fromDateStr ="";
        String toDateStr = "";
        
        	if(!userdate.equals("")) {
		        LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
		        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			if(!userdateto.equals("")) {
		        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
		        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
        
		
		List<InaugurationBean> list = new ArrayList<InaugurationBean>();
		
		list=ser.getInaugurationReportData(stCode, distCode, blkCode, userdate,userdateto);
		
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Watershed Yatra - Inauguration Program Details");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "List of Watershed Yatra - Inauguration Program Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 33, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(5,5,0,33);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,3,3);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,4,4);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,5,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,6,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,7,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,18,33);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,7,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,9,10);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,11,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,12,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,14,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,15,15);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,16,16);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,17,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,18,18);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,19,19);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,20,21);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,22,23);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,24,26);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,27,28);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,29,29);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,30,30);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,31,31);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,32,32);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,33,33);
		sheet.addMergedRegion(mergedRegion);
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State : " + stName + "     District : " + distName + "     Block : " + blkName + "     From Date: "+ fromDateStr+"     To Date : "+toDateStr);
		cell.setCellStyle(style);
		
		for(int i=1;i<34;i++)
		{
			cell =rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Date");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("State Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("District Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Block Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Location");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Remarks");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Number of Participation");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=8; i<18; i++) 
		{
			cell = rowhead.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(18);
		cell.setCellValue("Activities");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=19; i<34; i++) 
		{
			cell = rowhead.createCell(i); 
			cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0; i<7; i++)
		{
			cell = rowhead1.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Participants/Villagers");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(8);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Ministers");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(10);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(11);
		cell.setCellValue("Member of Parliament");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(12);
		cell.setCellValue("Legislative Members");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(13);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(14);
		cell.setCellValue("Other Public Representatives");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(15);
		cell.setCellValue("Government Officials");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(16);
		cell.setCellValue("Gram Sabha completed before the arrival of the van");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(17);
		cell.setCellValue("Prabhat Pheri completed before the arrival of the van");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(18);
		cell.setCellValue("Flag off of Van");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(19);
		cell.setCellValue("Launch of Theme Song");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(20);
		cell.setCellValue("Bhoomi Poojan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(21);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(22);
		cell.setCellValue("Lokarpan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(23);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(24);
		cell.setCellValue("Shramdaan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(25);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(26);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(27);
		cell.setCellValue("Plantation");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(28);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(29);
		cell.setCellValue("Award Distribution (Felicitation)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(30);
		cell.setCellValue("Number of stalls of Departments");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(31);
		cell.setCellValue("Number of stalls of SHGs/FPOs");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(32);
		cell.setCellValue("Number of LakhPati Didi Participated");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead1.createCell(33);
		cell.setCellValue("No of Uploaded Photographs");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0; i<7; i++)
		{
			cell = rowhead2.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(7);
		cell.setCellValue("Male");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(8);
		cell.setCellValue("Female");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Central Level");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(10);
		cell.setCellValue("State Level");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(11);
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(12);
		cell.setCellValue("Assembly");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(13);
		cell.setCellValue("Council");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=14; i<20; i++)
		{
			cell = rowhead2.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(20);
		cell.setCellValue("Number of Works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(21);
		cell.setCellValue("Cost of Total works (in Lakh)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(22);
		cell.setCellValue("Number of Works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(23);
		cell.setCellValue("Cost of Total works (in Lakh)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(24);
		cell.setCellValue("Number of Locations");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(25);
		cell.setCellValue("No. of people participated");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(26);
		cell.setCellValue("No. of Man Hours");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(27);
		cell.setCellValue("Area (in ha.)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(28);
		cell.setCellValue("No. of Agro forestry / Horticultural Plants (No. of Sapling)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=29; i<34; i++)
		{
			cell = rowhead2.createCell(i); 
			cell.setCellStyle(style);
		}
		
		
		Row rowhead3 = sheet.createRow(9);
		
		for(int i=0;i<34;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 10;
		String StName = "";
		
	    for(InaugurationBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	        
	    	row.createCell(0).setCellValue(sno);
		    row.createCell(1).setCellValue(bean.getDate());
		    row.createCell(2).setCellValue(bean.getStname().equals(StName) ? "" : bean.getStname());
	    	row.createCell(3).setCellValue(bean.getDistname());
	    	row.createCell(4).setCellValue(bean.getBlockname());
	    	row.createCell(5).setCellValue(bean.getLocation());
	    	row.createCell(6).setCellValue(bean.getRemarks());
	    	row.createCell(7).setCellValue(bean.getMale_participants());
	    	row.createCell(8).setCellValue(bean.getFemale_participants());
	    	row.createCell(9).setCellValue(bean.getCentral_ministers());
	    	row.createCell(10).setCellValue(bean.getState_ministers());
	    	row.createCell(11).setCellValue(bean.getParliament());
	    	row.createCell(12).setCellValue(bean.getAssembly_members());
	    	row.createCell(13).setCellValue(bean.getCouncil_members());
	    	row.createCell(14).setCellValue(bean.getOthers());
	    	row.createCell(15).setCellValue(bean.getGov_officials());
	    	row.createCell(16).setCellValue(bean.getGram_sabha().equals("true") ? "Yes" : "No");
	    	row.createCell(17).setCellValue(bean.getPrabhat_pheri().equals("true") ? "Yes" : "No");
	    	row.createCell(18).setCellValue(bean.getFlagoff().equals("true") ? "Yes" : "No");
	    	row.createCell(19).setCellValue(bean.getThemesong().equals("true") ? "Yes" : "No");
	    	row.createCell(20).setCellValue(bean.getNo_works_bhoomipoojan());
	    	row.createCell(21).setCellValue(bean.getTot_works_bhoomipoojan()==null?0.00:bean.getTot_works_bhoomipoojan().doubleValue());
	    	row.createCell(22).setCellValue(bean.getNo_works_lokarpan());
	    	row.createCell(23).setCellValue(bean.getTot_works_lokarpan()==null?0.00:bean.getTot_works_lokarpan().doubleValue());
	    	row.createCell(24).setCellValue(bean.getNo_location_shramdaan());
	    	row.createCell(25).setCellValue(bean.getNo_people_shramdaan());
	    	row.createCell(26).setCellValue(bean.getMan());
	    	row.createCell(27).setCellValue(bean.getArea_plantation()==null?0.00:bean.getArea_plantation().doubleValue());
	    	row.createCell(28).setCellValue(bean.getNo_plantation());
	    	row.createCell(29).setCellValue(bean.getNo_awards());
	    	row.createCell(30).setCellValue(bean.getDept_stalls());
	    	row.createCell(31).setCellValue(bean.getShg_fpo_stalls());
	    	row.createCell(32).setCellValue(bean.getNo_lakhpati_didi());
	    	row.createCell(33).setCellValue(bean.getImage_count());

	    	StName = bean.getStname();
	    	sno++;
	    	rowno++;
	    }
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 33);
	    String fileName = "attachment; filename=Report Inauguration.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "WatershedYatra/watershedYatraReportInauguration";
	}

	
	@RequestMapping(value = "/downloadPDFInaugurationReport", method = RequestMethod.POST)
	public ModelAndView downloadPDFInaugurationReport(HttpServletRequest request, HttpServletResponse response) {

		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
		String blkName = request.getParameter("blkName");
		String userdate= request.getParameter("udate");
		
		int stCode = Integer.parseInt(request.getParameter("state"));
		int distCode = Integer.parseInt(request.getParameter("district"));
		int blkCode = Integer.parseInt(request.getParameter("blockk"));
		String userdateto= request.getParameter("userdate2");
		
		
        String fromDateStr ="";
        String toDateStr = "";
        
        	if(!userdate.equals("")) {
		        LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
		        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			if(!userdateto.equals("")) {
		        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
		        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
        
        
		List<InaugurationBean> list = new ArrayList<InaugurationBean>();

		list = ser.getInaugurationReportData(stCode, distCode, blkCode, userdate, userdateto);

		try {

			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Watershed Yatra - Inauguration Program Details");
			document.addCreationDate();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			document.open();

			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC);
			Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD);
			Font bf8 = new Font(FontFamily.HELVETICA, 8);
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

			PdfPTable table = null;
			document.newPage();
			Paragraph paragraph3 = null;
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);

			paragraph3 = new Paragraph("List of Watershed Yatra - Inauguration Program Details", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);

			table = new PdfPTable(34);
			table.setWidths(new int[] { 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 });
			table.setWidthPercentage(70);

			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(4);
			CommonFunctions.insertCellHeader(table,"State : " + stName + "     District : " + distName + "     Block : " + blkName + "     From Date: "+ fromDateStr+"     To Date : "+toDateStr, Element.ALIGN_LEFT, 34, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Date", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Block Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Location", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Participation", Element.ALIGN_CENTER, 11, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Activities", Element.ALIGN_CENTER, 16, 1, bf8Bold);

			CommonFunctions.insertCellHeader(table, "Participants/Villagers", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Ministers", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Member of Parliament", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Legislative Members", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Other Public Representatives", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Government Officials", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Gram Sabha completed before the arrival of the van", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Prabhat Pheri completed before the arrival of the van", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Flag off of Van", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Launch of Theme Song", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Bhoomi Poojan", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Lokarpan", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Shramdaan", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Plantation", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Award Distribution (Felicitation)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of stalls of Departments", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of stalls of SHGs/FPOs", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of LakhPati Didi Participated", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No of Uploaded Photographs", Element.ALIGN_CENTER, 1, 2, bf8Bold);

			CommonFunctions.insertCellHeader(table, "Male", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Female", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Central Level", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Level", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Assembly", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Council", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost of Total works (in Lakh)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost of Total works (in Lakh)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Locations", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of people participated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of Man Hours", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area (in ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of Agro forestry / Horticultural Plants (No. of Sapling)", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "13", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "14", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "15", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "16", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "17", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "18", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "19", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "20", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "22", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "23", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "24", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "25", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "26", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "27", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "28", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "29", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "30", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "31", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "32", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "33", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "34", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			int i = 0;
			int k = 1;
			String StName = "";

			if (list.size() != 0)
				for (i = 0; i < list.size(); i++) {
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDate(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname().equals(StName) ? "" : list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getBlockname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getLocation(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getRemarks(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMale_participants().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getFemale_participants().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getCentral_ministers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getState_ministers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getParliament().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getAssembly_members().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getCouncil_members().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getOthers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getGov_officials().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getGram_sabha().equals("true") ? "Yes" : "No", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrabhat_pheri().equals("true") ? "Yes" : "No", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getFlagoff().equals("true") ? "Yes" : "No", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getThemesong().equals("true") ? "Yes" : "No", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getNo_works_bhoomipoojan().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTot_works_bhoomipoojan().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getNo_works_lokarpan().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTot_works_lokarpan().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getNo_location_shramdaan().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getNo_people_shramdaan().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMan().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getArea_plantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getNo_plantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getNo_awards().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDept_stalls().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getShg_fpo_stalls().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getNo_lakhpati_didi().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getImage_count().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);

					StName = list.get(i).getStname();
					k = k + 1;
				}

			if (list.size() == 0)
				CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 34, 1, bf8);

			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Inauguration.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	
	@RequestMapping(value="/getPreYatraPreparationReport", method = RequestMethod.GET)
	public ModelAndView getPreYatraReport(HttpServletRequest request, HttpServletResponse response)
	{
		/* session = request.getSession(true); */
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		String grampan= request.getParameter("grampan");
		/*if(session!=null && session.getAttribute("loginID")!=null) {
			*/
			mav = new ModelAndView("WatershedYatra/preYatraPreparationReport");
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
			
				/*
				 * }else { mav = new ModelAndView("login"); mav.addObject("login", new Login());
				 * }
				 */
		return mav; 
	}
	
	@RequestMapping(value="/getPreYatraPreparationReportData", method = RequestMethod.POST)
	public ModelAndView getPreYatraPreparationReportData(HttpServletRequest request, HttpServletResponse response)
	{
		/* session = request.getSession(true); */
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String block= request.getParameter("block");
			String grampan= request.getParameter("grampan");
			
			List<PreYatraPreparationBean> list = new ArrayList<PreYatraPreparationBean>();
			
			
			/*if(session!=null && session.getAttribute("loginID")!=null) {
				
			*/	mav = new ModelAndView("WatershedYatra/preYatraPreparationReport");
				mav.addObject("menu", menuController.getMenuUserId(request));
				
				list=ser.getPreYatraPreparationReportData(Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(block), Integer.parseInt(grampan));
				mav.addObject("preYatraList", list);
				mav.addObject("preYatraListSize", list.size());
				
				
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
				
					/*
					 * }else { mav = new ModelAndView("login"); mav.addObject("login", new Login());
					 * }
					 */
			return mav; 
	}
	
	@RequestMapping(value = "/downloadPDFPreYatraReport", method = RequestMethod.POST)
	public ModelAndView downloadPDFPreYatraReport(HttpServletRequest request, HttpServletResponse response) {
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String blkName= request.getParameter("blkName");
		String gpkName= request.getParameter("gpkName");
		
		int stCode = Integer.parseInt(request.getParameter("state"));
		int distCode = Integer.parseInt(request.getParameter("district"));
		int blkCode = Integer.parseInt(request.getParameter("block"));
		int gpCode = Integer.parseInt(request.getParameter("grampan"));
        
		List<PreYatraPreparationBean> list = new ArrayList<PreYatraPreparationBean>();

		list = ser.getPreYatraPreparationReportData(stCode, distCode, blkCode, gpCode);

		try {

			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("List of Pre - Yatra Preparation Details");
			document.addCreationDate();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			document.open();

			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC);
			Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD);
			Font bf8 = new Font(FontFamily.HELVETICA, 8);
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

			PdfPTable table = null;
			document.newPage();
			Paragraph paragraph3 = null;
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);

			paragraph3 = new Paragraph("List of Pre - Yatra Preparation Details", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);

			table = new PdfPTable(12);
			table.setWidths(new int[] { 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 });
			table.setWidthPercentage(70);

			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(2);			
			CommonFunctions.insertCellHeader(table,"State : " + stName + "     District : " + distName + "     Block : " + blkName + "     Gram Panchayat : " + gpkName , Element.ALIGN_LEFT, 12, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1,1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Block Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Gram Panchayat", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Village", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Yatra Type", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Entry Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Photo1 Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Photo2 Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);


			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			int i = 0;
			int k = 1;
			String StName = "";

			if (list.size() != 0)
				for (i = 0; i < list.size(); i++) {
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname().equals(StName) ? "" : list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistrictname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getBlockname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getGramname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getVillagename(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getYatratype().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getEntrydate().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					String participants = list.get(i).getParticipants() != null ? list.get(i).getParticipants().toString() : "";
					CommonFunctions.insertCell(table, participants, Element.ALIGN_RIGHT, 1, 1, bf8);

					//CommonFunctions.insertCell(table, list.get(i).getParticipants().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					String photo1Time = list.get(i).getPhoto1time() != null ? list.get(i).getPhoto1time().toString() : "";
					CommonFunctions.insertCell(table, photo1Time, Element.ALIGN_RIGHT, 1, 1, bf8);
					
					String photo2Time = list.get(i).getPhoto2time() != null ? list.get(i).getPhoto2time().toString() : "";
					CommonFunctions.insertCell(table, photo2Time, Element.ALIGN_RIGHT, 1, 1, bf8);

//					CommonFunctions.insertCell(table, list.get(i).getPhoto1time().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, list.get(i).getPhoto2time().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getRemarks(), Element.ALIGN_RIGHT, 1, 1, bf8);
					StName = list.get(i).getStname();
					k = k + 1;
				}

			if (list.size() == 0)
				CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 10, 1, bf8);

			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=PreYatraReport.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping(value = "/downloadExcelPreYatraPrepReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelPreYatraPrepReport(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String blkName= request.getParameter("blkName");
		String gpkName= request.getParameter("gpkName");
		
		int stCode = Integer.parseInt(request.getParameter("state"));
		int distCode = Integer.parseInt(request.getParameter("district"));
		int blkCode = Integer.parseInt(request.getParameter("block"));
		int gpCode = Integer.parseInt(request.getParameter("grampan"));
        

		
		List<PreYatraPreparationBean> list = new ArrayList<PreYatraPreparationBean>();
		list = ser.getPreYatraPreparationReportData(stCode, distCode, blkCode, gpCode);
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("List of Pre - Yatra Preparation Details");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "List of Pre - Yatra Preparation Details";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);

		Row rowhead = sheet.createRow(5); 

		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(2);
		cell.setCellValue("District Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("Block Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("Gram Panchayat");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("Village Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Yatra Type");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Entry Date");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Total No. of Participants");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Photo1 Date");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(10);
		cell.setCellValue("Photo2 Date");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("Remarks");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i=0;i<12;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		

		int sno = 1;
		int rowno  = 7;
		
		
		
		for(PreYatraPreparationBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getStname());
			row.createCell(2).setCellValue(bean.getDistrictname());  
			row.createCell(3).setCellValue(bean.getBlockname());  
			row.createCell(4).setCellValue(bean.getGramname()); 
			row.createCell(5).setCellValue(bean.getVillagename()); 
			row.createCell(6).setCellValue(bean.getYatratype()); 
			row.createCell(7).setCellValue(bean.getEntrydate());
			  String participants = (bean.getParticipants() != null) ? bean.getParticipants().toString() : "";
			row.createCell(8).setCellValue(participants);
//			row.createCell(8).setCellValue(bean.getParticipants()); 
			row.createCell(9).setCellValue(bean.getPhoto1time());  
			row.createCell(10).setCellValue(bean.getPhoto2time());
			row.createCell(11).setCellValue(bean.getRemarks());
			
			sno++;
			rowno++;
		}

		CellStyle style1 = workbook.createCellStyle();
		style1.setBorderTop(BorderStyle.THIN); 
		style1.setBorderBottom(BorderStyle.THIN);
		style1.setBorderLeft(BorderStyle.THIN);
		style1.setBorderRight(BorderStyle.THIN);
		style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
		style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
		org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 12);
		font1.setBold(true);
		style1.setFont(font1);


		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
		String fileName = "attachment; filename=Report PreyatraReport- State.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/preYatraPreparationReport";

	}
}

