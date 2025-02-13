package app.watershedyatra.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
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

import app.bean.AssetIdBean;
import app.bean.Login;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.service.VillageWatershedYatraReportService;

@Controller("VillageWatershedYatraReport")
public class VillageWatershedYatraReport {


	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	WatershedYatraReportService ser;
	
	@Autowired(required = true)
	VillageWatershedYatraReportService service;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> blockList;
	private Map<String, String> gpList;
	
	@RequestMapping(value="/getWatershedYatraVillageReport", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraVillageReport(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		String grampan= request.getParameter("grampan");
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("WatershedYatra/wyVillageReport");
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
	@RequestMapping(value="/showWatershedYatraVillageReport", method = RequestMethod.POST)
	public ModelAndView showWatershedYatraVillageReport(HttpServletRequest request, HttpServletResponse response)
	{
			session = request.getSession(true);
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String block= request.getParameter("block");
			String grampan= request.getParameter("grampan");
			String userdate= request.getParameter("userdate");
			
			List<WatershedYatraBean> list = new ArrayList<WatershedYatraBean>();
			
			
			if(session!=null && session.getAttribute("loginID")!=null) {
				
				mav = new ModelAndView("WatershedYatra/wyVillageReport");
				mav.addObject("menu", menuController.getMenuUserId(request));
				
				list=service.showWatershedYatraVillageReport(Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(block), Integer.parseInt(grampan), userdate);
				mav.addObject("dataList", list);
				mav.addObject("dataListSize", list.size());
				mav.addObject("userdate", userdate);
				
				
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

	@RequestMapping(value = "/downloadExcelVillageYatraReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelVillageYatraReport(HttpServletRequest request, HttpServletResponse response) 
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		String grampan= request.getParameter("grampan");
		String userdate= request.getParameter("userdate1");
		
		List<WatershedYatraBean> list = new ArrayList<WatershedYatraBean>();
		
		list=service.showWatershedYatraVillageReport(Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(block), Integer.parseInt(grampan),userdate);
		  
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("List of Watershed Yatra at Village Level");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "List of Watershed Yatra at Village Level";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 33, areaAmtValDetail, workbook);
			
			
			mergedRegion = new CellRangeAddress(5,7,0,0); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,4,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,5,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,6,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,7,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,8,8); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,9,17); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,18,33); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,9,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,11,12); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,13,13); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,16,16); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,17,17); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,18,18); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,19,19); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,20,20); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,21,21); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,22,22); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,32,32); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,33,33); 
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,33); 
	        sheet.addMergedRegion(mergedRegion);
	        
			
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Date");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Time");  
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
			cell.setCellValue("GP Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Village Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Location");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Number of Participation");
			cell.setCellStyle(style);
			for(int i = 10; i<18;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(18);
			cell.setCellValue("Activities");  
			cell.setCellStyle(style);
			for(int i = 19; i<34;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			rowhead = sheet.createRow(6);
			for(int i = 0; i<9;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Participants/Villagers");  
			cell.setCellStyle(style);
			rowhead.createCell(10).setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Ministers");  
			cell.setCellStyle(style);
			rowhead.createCell(12).setCellStyle(style);
			
			cell = rowhead.createCell(13);
			cell.setCellValue("Member of Parliament");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(14);
			cell.setCellValue("Legislative Members");  
			cell.setCellStyle(style);
			rowhead.createCell(15).setCellStyle(style);
			
			cell = rowhead.createCell(16);
			cell.setCellValue("Other Public Representatives");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(17);
			cell.setCellValue("Government Officials");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(18);
			cell.setCellValue("AR Experience");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(19);
			cell.setCellValue("Shapath Shramdan");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(20);
			cell.setCellValue("Film on Yatra");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(21);
			cell.setCellValue("People Participated in Quiz");  	
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(22);
			cell.setCellValue("Cultural Activity");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(23);
			cell.setCellValue("Bhoomi Poojan");  
			cell.setCellStyle(style);
			rowhead.createCell(24).setCellStyle(style);
			
			cell = rowhead.createCell(25);
			cell.setCellValue("Lokarpan");  
			cell.setCellStyle(style);
			rowhead.createCell(26).setCellStyle(style);
			
			cell = rowhead.createCell(27);
			cell.setCellValue("Shramdaan");  
			cell.setCellStyle(style);
			rowhead.createCell(28).setCellStyle(style);
			rowhead.createCell(29).setCellStyle(style);
			
			cell = rowhead.createCell(30);
			cell.setCellValue("Plantation");  
			cell.setCellStyle(style);
			rowhead.createCell(31).setCellStyle(style);
			
			cell = rowhead.createCell(32);
			cell.setCellValue("Award Distribution (Felicitation)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(33);
			cell.setCellValue("No of Uploaded Photographs");  
			cell.setCellStyle(style);
			
			rowhead = sheet.createRow(7);
			for(int i = 0; i<9;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			cell = rowhead.createCell(9);
			cell.setCellValue("Male");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Female");  		
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Central Level");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("State Level");  
			cell.setCellStyle(style);
			rowhead.createCell(13).setCellStyle(style);
			
			cell = rowhead.createCell(14);		
			cell.setCellValue("Assembly");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(15);
			cell.setCellValue("Council");  
			cell.setCellStyle(style);
			rowhead.createCell(16).setCellStyle(style);
			rowhead.createCell(17).setCellStyle(style);
			rowhead.createCell(18).setCellStyle(style);
			rowhead.createCell(19).setCellStyle(style);
			rowhead.createCell(20).setCellStyle(style);
			rowhead.createCell(21).setCellStyle(style);
			rowhead.createCell(22).setCellStyle(style);
			
			cell = rowhead.createCell(23);
			cell.setCellValue("Number of Works");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(24);
			cell.setCellValue("Cost of Total works (in Lakh)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(25);
			cell.setCellValue("Number of Works");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(26);
			cell.setCellValue("Cost of Total works (in Lakh)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(27);
			cell.setCellValue("Number of Locations");		 
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(28);
			cell.setCellValue("No. of people participated");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(29);
			cell.setCellValue("No. of Man Hours");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(30);
			cell.setCellValue("Area (in ha.) ");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(31);
			cell.setCellValue("No. of Agro forestry / Horticultural Plants (No. of Sapling)");  
			cell.setCellStyle(style);
			rowhead.createCell(32).setCellStyle(style);
			rowhead.createCell(33).setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(8);
			for(int i=0;i<34;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 8;
	        
	        for(WatershedYatraBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno);
	        	row.createCell(1).setCellValue(bean.getDate());
	        	row.createCell(2).setCellValue(bean.getTimed());
	        	row.createCell(3).setCellValue(bean.getStname()); 
	        	row.createCell(4).setCellValue(bean.getDistname());
	        	row.createCell(5).setCellValue(bean.getBlockname());
	        	row.createCell(6).setCellValue(bean.getGpname());
	        	row.createCell(7).setCellValue(bean.getVillagename());
	        	row.createCell(8).setCellValue(bean.getLocation());
	        	row.createCell(9).setCellValue(bean.getMale_participants());
	        	row.createCell(10).setCellValue(bean.getFemale_participants());
	        	row.createCell(11).setCellValue(bean.getCentral_ministers());
	        	row.createCell(12).setCellValue(bean.getState_ministers());
	        	row.createCell(13).setCellValue(bean.getParliament());
	        	row.createCell(14).setCellValue(bean.getAssembly_members());
	        	row.createCell(15).setCellValue(bean.getCouncil_members());
	        	row.createCell(16).setCellValue(bean.getOthers());
	        	row.createCell(17).setCellValue(bean.getGov_officials());
	        	row.createCell(18).setCellValue(bean.getNo_of_ar_experience_people());
	        	
	        	if(bean.getBhumi_jal_sanrakshan().equals("true")) {
	        	row.createCell(19).setCellValue("Yes");
	        	}
	        	else {
	        		row.createCell(19).setCellValue("No");
	        	}
	        	if(bean.getWatershed_yatra_film().equals("true")) {
	        	row.createCell(20).setCellValue("Yes");
	        	}
	        	else {
	        		row.createCell(20).setCellValue("No");
	        	}
	        	row.createCell(21).setCellValue(bean.getQuiz_participants());
	        	row.createCell(22).setCellValue(bean.getCultural_name());
	        	row.createCell(23).setCellValue(bean.getNo_works_bhoomipoojan());
	        	row.createCell(24).setCellValue(bean.getTot_works_bhoomipoojan());
	        	row.createCell(25).setCellValue(bean.getNo_works_lokarpan());
	        	row.createCell(26).setCellValue(bean.getTot_works_lokarpan());
	        	row.createCell(27).setCellValue(bean.getNo_location_shramdaan());
	        	row.createCell(28).setCellValue(bean.getNo_people_shramdaan());
	        	row.createCell(29).setCellValue(bean.getManhour());
	        	row.createCell(30).setCellValue(bean.getArea_plantation().doubleValue());
	        	row.createCell(31).setCellValue(bean.getNo_plantation());
	        	row.createCell(32).setCellValue(bean.getNo_awards());
	        	row.createCell(33).setCellValue(bean.getImage_count());
	        	
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
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
			
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 33);
	        String fileName = "attachment; filename=Report VillageYatra.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "WatershedYatra/wyVillageReport";
		
	}
	@RequestMapping(value = "/downloadVillageYatraReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadVillageYatraReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		String grampan= request.getParameter("grampan");
		String userdate= request.getParameter("userdate1");
		List<WatershedYatraBean> list = new ArrayList<WatershedYatraBean>();
		list=service.showWatershedYatraVillageReport(Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(block), Integer.parseInt(grampan),userdate);
		
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("villageYatraReport");
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
			
				paragraph3 = new Paragraph("List of Watershed Yatra at Village Level", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(34);
				table.setWidths(new int[] { 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(100);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);							
				table.setHeaderRows(4);
				
				CommonFunctions.insertCellHeader(table, "S.No.",Element.ALIGN_RIGHT, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Date", Element.ALIGN_RIGHT, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Time", Element.ALIGN_RIGHT, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_RIGHT, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_RIGHT, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Block Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "GP Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Location", Element.ALIGN_CENTER, 1, 3, bf8Bold);

				CommonFunctions.insertCellHeader(table, "Number of Participation", Element.ALIGN_CENTER, 9, 1, bf8Bold);

				CommonFunctions.insertCellHeader(table, "Activities", Element.ALIGN_CENTER, 16, 1, bf8Bold);

				CommonFunctions.insertCellHeader(table, "Participants/Villagers", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ministers", Element.ALIGN_CENTER, 2, 1, bf8Bold);

				CommonFunctions.insertCellHeader(table, "Member of Parliament", Element.ALIGN_CENTER, 1, 2, bf8Bold);

				CommonFunctions.insertCellHeader(table, "Legislative Members", Element.ALIGN_CENTER, 2, 1, bf8Bold);

				CommonFunctions.insertCellHeader(table, "Other Public Representatives", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Government Officials", Element.ALIGN_CENTER, 1, 2, bf8Bold);

				CommonFunctions.insertCellHeader(table, "AR Experience", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Shapath Shramdan", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Film on Yatra", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "People Participated in Quiz", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Cultural Activity", Element.ALIGN_CENTER, 1, 2, bf8Bold);

				CommonFunctions.insertCellHeader(table, "Bhoomi Poojan", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Lokarpan", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Shramdaan", Element.ALIGN_CENTER, 3, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plantation", Element.ALIGN_CENTER, 2, 1, bf8Bold);

				CommonFunctions.insertCellHeader(table, "Award Distribution (Felicitation)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
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
				
				int i=0;
				int k=1;
				if(list.size()!=0)
					for( i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDate(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getTimed(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getBlockname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getGpname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getVillagename(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getLocation(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMale_participants().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFemale_participants().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCentral_ministers().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getState_ministers().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getParliament().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getAssembly_members().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCouncil_members().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOthers().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getGov_officials().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_of_ar_experience_people().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						if(list.get(i).getBhumi_jal_sanrakshan().equals("true")){
						CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
						}
						else {
							CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
						}
						if(list.get(i).getWatershed_yatra_film().equals("true")){
						CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
						}
						else {
							CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
						}
						CommonFunctions.insertCell(table, list.get(i).getQuiz_participants().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCultural_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_works_bhoomipoojan().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getTot_works_bhoomipoojan().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_works_lokarpan().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getTot_works_lokarpan().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_location_shramdaan().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_people_shramdaan().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getManhour().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_plantation().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_plantation().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_awards().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getImage_count().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						k=k+1;
					}
				
				if(list.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 13, 1, bf8);
				
					
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
		response.setHeader("Content-Disposition", "attachment;filename=VillageYatraReport.pdf");
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
