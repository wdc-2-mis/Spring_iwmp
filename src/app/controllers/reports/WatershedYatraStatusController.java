package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import app.bean.ConvergenceWorksBean;
import app.common.CommonFunctions;
import app.service.reports.WatershedYatraReportService;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.bean.WatershedYatraStatusBean;



@Controller("watershedYatraStatusController")
public class WatershedYatraStatusController {
	
	@Autowired(required = true)
	WatershedYatraReportService ser;
	
	@RequestMapping(value = "/getWatershedYatraStatus", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraStatus(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("WatershedYatra/watershedYatraStatus");
			mav.addObject("getRecords",ser.getStateWiseWatershedYatraStatus());
			
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/getDistWatershedYatraStatus", method = RequestMethod.GET)
	public ModelAndView getDistWatershedYatraStatus(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		try {
			
			String stcd = request.getParameter("stcd");
			String stName = request.getParameter("stName");
			List<WatershedYatraStatusBean> records = ser.getDistWiseWatershedYatraStatus(Integer.parseInt(stcd));
			String inaugurationDate = records.get(0).getInauguration_date();
			
			mav = new ModelAndView("WatershedYatra/distWatershedYatraStatus");
			mav.addObject("getRecords",records);
			mav.addObject("stcd",stcd);
			mav.addObject("stName",stName);
			mav.addObject("inaugurationDate", inaugurationDate);
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		
		return mav;
	}
	
	
	@RequestMapping(value = "/downloadExcelWatershedYatraStatusReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelWatershedYatraStatusReport(HttpServletRequest request, HttpServletResponse response)
	{
		/*
		 * session = request.getSession(true);
		 */

		
		List<WatershedYatraStatusBean> list = new ArrayList<WatershedYatraStatusBean>();
		
		list=ser.getStateWiseWatershedYatraStatus();
		
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report - State wise Watershed Yatra Activity Status");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report - State wise Watershed Yatra Activity Status";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 16, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
		sheet.addMergedRegion(mergedRegion);
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
		mergedRegion = new CellRangeAddress(5,5,5,6);
		sheet.addMergedRegion(mergedRegion);
		
		mergedRegion = new CellRangeAddress(6,7,5,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,6,6);
		sheet.addMergedRegion(mergedRegion);
		
		mergedRegion = new CellRangeAddress(5,7,7,7);
		sheet.addMergedRegion(mergedRegion);
		
		mergedRegion = new CellRangeAddress(6,6,8,9);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,10,11);
		sheet.addMergedRegion(mergedRegion);
		
		
		mergedRegion = new CellRangeAddress(5,5,8,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,12,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,13,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,14,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,15,15);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,16,16);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowhead = sheet.createRow(5);
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total Projects");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Location Planned");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Location Completed");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(6);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("State Inauguration Date");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("No. of Locations Where Pre Yatra Activities Completed");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=9; i<13; i++)
		{
			cell = rowhead.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(12);
		cell.setCellValue("AR Experience (No. of Peoples)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(13);
		cell.setCellValue("Bhoomi Poojan (No. of Works)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(14);
		cell.setCellValue("Lokarpan (No. of Works)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(15);
		cell.setCellValue("Shramdaan (No. of Locations)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(16);
		cell.setCellValue("Plantation (No. of Agro Forestry)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0; i<5; i++)
		{
			cell = rowhead1.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("Completed Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Not Completed Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(7);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Gram Sabha");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(9);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(10);
		cell.setCellValue("Prabhat Phere");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=11; i<17; i++)
		{
			cell = rowhead1.createCell(i); 
			cell.setCellStyle(style);
		}
		
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0; i<8; i++)
		{
			cell = rowhead2.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(8);
		cell.setCellValue("Total No. of Locations");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Total No. of Participants");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(10);
		cell.setCellValue("Total No. of Locations");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(11);
		cell.setCellValue("Total No. of Participants");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=12; i<17; i++)
		{
			cell = rowhead2.createCell(i); 
			cell.setCellStyle(style);
		}
		
		
		Row rowhead3 = sheet.createRow(8);
		
		for(int i=0;i<17;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 9;
		int totproj = 0;
		int locPlan = 0;
		int locComp = 0;
		int compAct = 0;
		int notCompAct = 0;
		int gramSabha = 0;
		int prabhatPheri = 0;
		int arExp = 0;
		int bhoomiPoojan = 0;
		int lokarpan = 0;
		int shramdaan = 0;
		int plantation = 0;
		int gramSabhaprtcpnts = 0;
		int prabhatPheriprtcpnts = 0;
		
		
	    for(WatershedYatraStatusBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	        
	    	row.createCell(0).setCellValue(sno);
		    row.createCell(1).setCellValue(bean.getSt_name());
		    row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getTotal_vanplan());
	    	row.createCell(4).setCellValue(bean.getTotal_locv());
	    	row.createCell(5).setCellValue(bean.getActivity_entered()!=null ? bean.getActivity_entered() : 0);
	    	row.createCell(6).setCellValue(bean.getAct_not_entered()!=null ? bean.getAct_not_entered() : 0);
	    	row.createCell(7).setCellValue(bean.getInauguration_date());
	    	row.createCell(8).setCellValue(bean.getGramsabha()!=null ? bean.getGramsabha() : 0);
	    	row.createCell(9).setCellValue(bean.getGramsabha_participants()!=null ? bean.getGramsabha_participants() : 0);
	    	row.createCell(10).setCellValue(bean.getPrabhatpheri()!=null ? bean.getPrabhatpheri() : 0);
	    	row.createCell(11).setCellValue(bean.getPrabhatpheri_participants()!=null ? bean.getPrabhatpheri_participants() : 0);
	    	row.createCell(12).setCellValue(bean.getTotal_arexp()!=null ? bean.getTotal_arexp() : 0);
	    	row.createCell(13).setCellValue(bean.getTotal_bhoomi_poojan()!=null ? bean.getTotal_bhoomi_poojan() : 0);
	    	row.createCell(14).setCellValue(bean.getTotal_lokarpan()!=null ? bean.getTotal_lokarpan() : 0);
	    	row.createCell(15).setCellValue(bean.getTotal_shramdaan()!=null ? bean.getTotal_shramdaan() : 0);
	    	row.createCell(16).setCellValue(bean.getTotal_plantation()!=null ? bean.getTotal_plantation() : 0);

	    	totproj = totproj + bean.getTotal_project();
	    	locPlan = locPlan + bean.getTotal_vanplan();
	    	locComp = locComp + bean.getTotal_locv();
	    	compAct = compAct + (bean.getActivity_entered()!=null ? bean.getActivity_entered() : 0);
	    	notCompAct = notCompAct + (bean.getAct_not_entered()!=null ? bean.getAct_not_entered() : 0);
			gramSabha = gramSabha + (bean.getGramsabha()!=null ? bean.getGramsabha() : 0);
			gramSabhaprtcpnts = gramSabhaprtcpnts + (bean.getGramsabha_participants()!=null ? bean.getGramsabha_participants() : 0);
			prabhatPheri = prabhatPheri + (bean.getPrabhatpheri()!=null ? bean.getPrabhatpheri() : 0);
			prabhatPheriprtcpnts = prabhatPheriprtcpnts + (bean.getPrabhatpheri_participants()!=null ? bean.getPrabhatpheri_participants() : 0);
			arExp = arExp + (bean.getTotal_arexp()!=null ? bean.getTotal_arexp() : 0);
			bhoomiPoojan = bhoomiPoojan + (bean.getTotal_bhoomi_poojan()!=null ? bean.getTotal_bhoomi_poojan() : 0);
			lokarpan = lokarpan + (bean.getTotal_lokarpan()!=null ? bean.getTotal_lokarpan() : 0);
			shramdaan = shramdaan + (bean.getTotal_shramdaan()!=null ? bean.getTotal_shramdaan() : 0);
			plantation =  plantation + (bean.getTotal_plantation()!=null ? bean.getTotal_plantation() : 0);
	    	
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

		Row row = sheet.createRow(list.size()+9);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellValue(totproj);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(locPlan);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(locComp);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(compAct);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(notCompAct);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(gramSabha);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(gramSabhaprtcpnts);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(prabhatPheri);
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(prabhatPheriprtcpnts);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(arExp);
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(bhoomiPoojan);
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(lokarpan);
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(shramdaan);
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(plantation);
		cell.setCellStyle(style1);
	    
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 16);
	    String fileName = "attachment; filename=Report Watershed Yatra Status - State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "WatershedYatra/watershedYatraStatus";
	}
	
	@RequestMapping(value = "/downloadPDFWatershedYatraStatusReport", method = RequestMethod.POST)
	public ModelAndView downloadPDFWatershedYatraStatusReport(HttpServletRequest request, HttpServletResponse response) 
	{
		
		
		List<WatershedYatraStatusBean> list = new ArrayList<WatershedYatraStatusBean>();
		
		list=ser.getStateWiseWatershedYatraStatus();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WatershedYatraStatusReport");
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
			
				paragraph3 = new Paragraph("Report - State wise Watershed Yatra Activity Status", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(17);
		        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(3);
							
		        
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Projects", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Location Planned", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Location Completed", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Activity", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Inauguration Date", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Locations Where Pre Yatra Activities Completed", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "AR Experience (No. of Peoples)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Bhoomi Poojan (No. of Works)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Lokarpan (No. of Works)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Shramdaan (No. of Locations)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plantation (No. of Agro Forestry)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Completed Activity", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Completed Activity", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Gram Sabha", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Prabhat Phere", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Total No. of Locations", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Locations", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
				
		        
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

				
				int k = 1;
				int totproj = 0;
				int locPlan = 0;
				int locComp = 0;
				int compAct = 0;
				int notCompAct = 0;
				int gramSabha = 0;
				int gramSabhaPrtcpnts = 0;
				int prabhatPheri = 0;
				int prabhatPheriPrtcpnts = 0;
				int arExp = 0;
				int bhoomiPoojan = 0;
				int lokarpan = 0;
				int shramdaan = 0;
				int plantation = 0;

				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_vanplan()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_locv()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getActivity_entered()!=null ? list.get(i).getActivity_entered() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAct_not_entered()!=null ? list.get(i).getAct_not_entered() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getInauguration_date()!=null ? list.get(i).getInauguration_date() : ""), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGramsabha()!=null ? list.get(i).getGramsabha() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGramsabha_participants()!=null ? list.get(i).getGramsabha_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPrabhatpheri()!=null ? list.get(i).getPrabhatpheri() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPrabhatpheri_participants()!=null ? list.get(i).getPrabhatpheri_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_arexp()!=null ? list.get(i).getTotal_arexp() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_bhoomi_poojan()!=null ? list.get(i).getTotal_bhoomi_poojan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_lokarpan()!=null ? list.get(i).getTotal_lokarpan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_shramdaan()!=null ? list.get(i).getTotal_shramdaan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_plantation()!=null ? list.get(i).getTotal_plantation() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						totproj = totproj + list.get(i).getTotal_project();
				    	locPlan = locPlan + list.get(i).getTotal_vanplan();
				    	locComp = locComp + list.get(i).getTotal_locv();
				    	compAct = compAct + (list.get(i).getActivity_entered()!=null ? list.get(i).getActivity_entered() : 0);
				    	notCompAct = notCompAct + (list.get(i).getAct_not_entered()!=null ? list.get(i).getAct_not_entered() : 0);
						gramSabha = gramSabha + (list.get(i).getGramsabha()!=null ? list.get(i).getGramsabha() : 0);
						gramSabhaPrtcpnts = gramSabhaPrtcpnts + (list.get(i).getGramsabha_participants()!=null ? list.get(i).getGramsabha_participants() : 0);
						prabhatPheri = prabhatPheri + (list.get(i).getPrabhatpheri()!=null ? list.get(i).getPrabhatpheri() : 0);
						prabhatPheriPrtcpnts = prabhatPheriPrtcpnts + (list.get(i).getPrabhatpheri_participants()!=null ? list.get(i).getPrabhatpheri_participants() : 0);
						arExp = arExp + (list.get(i).getTotal_arexp()!=null ? list.get(i).getTotal_arexp() : 0);
						bhoomiPoojan = bhoomiPoojan + (list.get(i).getTotal_bhoomi_poojan()!=null ? list.get(i).getTotal_bhoomi_poojan() : 0);
						lokarpan = lokarpan + (list.get(i).getTotal_lokarpan()!=null ? list.get(i).getTotal_lokarpan() : 0);
						shramdaan = shramdaan + (list.get(i).getTotal_shramdaan()!=null ? list.get(i).getTotal_shramdaan() : 0);
						plantation =  plantation + (list.get(i).getTotal_plantation()!=null ? list.get(i).getTotal_plantation() : 0);
						
						k++;
					}
					
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(locPlan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(locComp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(compAct), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(notCompAct), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(""), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(gramSabha), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(gramSabhaPrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(prabhatPheri), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(prabhatPheriPrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(arExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(bhoomiPoojan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(lokarpan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(shramdaan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 17, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report Watershed Yatra Status - State.pdf");
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
	
	@RequestMapping(value = "/downloadExcelDistWatershedYatraStatusReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistWatershedYatraStatusReport(HttpServletRequest request, HttpServletResponse response)
	{
		/*
		 * session = request.getSession(true);
		 */

		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		List<WatershedYatraStatusBean> list = ser.getDistWiseWatershedYatraStatus(Integer.parseInt(stcd));
		String inaugurationDate = list.get(0).getInauguration_date() != null ? list.get(0).getInauguration_date() : "";
		
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report - District wise Watershed Yatra Activity Status");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report - District wise Watershed Yatra Activity Status";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 15, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,13); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,3,3);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,4,4);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,5,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,7,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,9,9);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,10,10);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,11,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,12,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,13,13);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State : "+ stName+"     Inauguration Date : "+inaugurationDate);  
		cell.setCellStyle(style);
		
		for(int i=1;i<14;i++)
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
		cell.setCellValue("District");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total Projects");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Location Planned");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Location Completed");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(6);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("No. of Locations Where Pre Yatra Activities Completed");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(9);
		cell.setCellValue("AR Experience (No. of Peoples)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Bhoomi Poojan (No. of Works)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("Lokarpan (No. of Works)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(12);
		cell.setCellValue("Sharmdaan (No. of Locations)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(13);
		cell.setCellValue("Plantation (No. of Agro Forestry)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0; i<5; i++)
		{
			cell = rowhead1.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("Completed Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Not Completed Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Gram Sabha");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Prabhat Phere");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=9; i<14; i++)
		{
			cell = rowhead1.createCell(i); 
			cell.setCellStyle(style);
		}
		
		
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0;i<14;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 9;
		int totproj = 0;
		int locPlan = 0;
		int locComp = 0;
		int compAct = 0;
		int notCompAct = 0;
		int gramSabha = 0;
		int prabhatPheri = 0;
		int arExp = 0;
		int bhoomiPoojan = 0;
		int lokarpan = 0;
		int shramdaan = 0;
		int plantation = 0;
		
		
	    for(WatershedYatraStatusBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	        
	    	row.createCell(0).setCellValue(sno);
		    row.createCell(1).setCellValue(bean.getDist_name());
		    row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getTotal_vanplan());
	    	row.createCell(4).setCellValue(bean.getTotal_locv());
	    	row.createCell(5).setCellValue(bean.getActivity_entered());
	    	row.createCell(6).setCellValue(bean.getAct_not_entered());
	    	row.createCell(7).setCellValue(bean.getGramsabha());
	    	row.createCell(8).setCellValue(bean.getPrabhatpheri());
	    	row.createCell(9).setCellValue(bean.getTotal_arexp());
	    	row.createCell(10).setCellValue(bean.getTotal_bhoomi_poojan());
	    	row.createCell(11).setCellValue(bean.getTotal_lokarpan());
	    	row.createCell(12).setCellValue(bean.getTotal_shramdaan());
	    	row.createCell(13).setCellValue(bean.getTotal_plantation());

	    	totproj = totproj + bean.getTotal_project();
	    	locPlan = locPlan + bean.getTotal_vanplan();
	    	locComp = locComp + bean.getTotal_locv();
	    	compAct = compAct + (bean.getActivity_entered());
	    	notCompAct = notCompAct + (bean.getAct_not_entered());
			gramSabha = gramSabha + (bean.getGramsabha());
			prabhatPheri = prabhatPheri + (bean.getPrabhatpheri());
			arExp = arExp + (bean.getTotal_arexp());
			bhoomiPoojan = bhoomiPoojan + (bean.getTotal_bhoomi_poojan());
			lokarpan = lokarpan + (bean.getTotal_lokarpan());
			shramdaan = shramdaan + (bean.getTotal_shramdaan());
			plantation =  plantation + (bean.getTotal_plantation());
	    	
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

		Row row = sheet.createRow(list.size()+9);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellValue(totproj);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(locPlan);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(locComp);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(compAct);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(notCompAct);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(gramSabha);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(prabhatPheri);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(arExp);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(bhoomiPoojan);
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(lokarpan);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(shramdaan);
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(plantation);
		cell.setCellStyle(style1);
	    
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 13);
	    String fileName = "attachment; filename=Report Watershed Yatra Status - District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "WatershedYatra/distWatershedYatraStatus";
	}
	
	@RequestMapping(value = "/downloadPDFDistWatershedYatraStatusReport", method = RequestMethod.POST)
	public ModelAndView downloadPDFDistWatershedYatraStatusReport(HttpServletRequest request, HttpServletResponse response) 
	{
		
		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		List<WatershedYatraStatusBean> list = ser.getDistWiseWatershedYatraStatus(Integer.parseInt(stcd));
		String inaugurationDate = list.get(0).getInauguration_date()!=null ? list.get(0).getInauguration_date() : "";
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("DistrictWatershedYatraStatusReport");
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
			
				paragraph3 = new Paragraph("Report - District wise Watershed Yatra Activity Status", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(16);
		        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(5);
				
		        CommonFunctions.insertCellHeader(table, "State : "+stName+"     Inauguration Date : "+inaugurationDate, Element.ALIGN_LEFT, 16, 1, bf8Bold);
		        
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Projects", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Location Planned", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Location Completed", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Activity", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Locations Where Pre Yatra Activities Completed", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "AR Experience (No. of Peoples)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Bhoomi Poojan (No. of Works)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Lokarpan (No. of Works)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Shramdaan (No. of Locations)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plantation (No. of Agro Forestry)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Completed Activity", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Completed Activity", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Gram Sabha", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Prabhat Phere", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Total No. of Locations", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Locations", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
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

				
				int k = 1;
				int totproj = 0;
				int locPlan = 0;
				int locComp = 0;
				int compAct = 0;
				int notCompAct = 0;
				int gramSabha = 0;
				int gramSabhapart = 0;
				int prabhatPheri = 0;
				int prabhatPheripart = 0;
				int arExp = 0;
				int bhoomiPoojan = 0;
				int lokarpan = 0;
				int shramdaan = 0;
				int plantation = 0;

				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_vanplan()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_locv()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getActivity_entered()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAct_not_entered()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGramsabha()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGramsabha_participants()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPrabhatpheri()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPrabhatpheri_participants()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_arexp()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_bhoomi_poojan()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_lokarpan()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_shramdaan()), Element.ALIGN_RIGHT, 1, 1, bf8);
						//CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_plantation()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_vanplan()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_locv()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getActivity_entered()!=null ? list.get(i).getActivity_entered() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAct_not_entered()!=null ? list.get(i).getAct_not_entered() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGramsabha()!=null ? list.get(i).getGramsabha() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGramsabha_participants()!=null ? list.get(i).getGramsabha_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPrabhatpheri()!=null ? list.get(i).getPrabhatpheri() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPrabhatpheri_participants()!=null ? list.get(i).getPrabhatpheri_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_arexp()!=null ? list.get(i).getTotal_arexp() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_bhoomi_poojan()!=null ? list.get(i).getTotal_bhoomi_poojan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_lokarpan()!=null ? list.get(i).getTotal_lokarpan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_shramdaan()!=null ? list.get(i).getTotal_shramdaan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_plantation()!=null ? list.get(i).getTotal_plantation() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						totproj = totproj + list.get(i).getTotal_project();
				    	locPlan = locPlan + list.get(i).getTotal_vanplan();
				    	locComp = locComp + list.get(i).getTotal_locv();
						
						compAct = compAct + (list.get(i).getActivity_entered()!=null ? list.get(i).getActivity_entered() : 0);
				    	notCompAct = notCompAct + (list.get(i).getAct_not_entered()!=null ? list.get(i).getAct_not_entered() : 0);
						gramSabha = gramSabha + (list.get(i).getGramsabha()!=null ? list.get(i).getGramsabha() : 0);
						gramSabhapart= gramSabhapart + (list.get(i).getGramsabha_participants()!=null ? list.get(i).getGramsabha_participants() : 0);
						prabhatPheri = prabhatPheri + (list.get(i).getPrabhatpheri()!=null ? list.get(i).getPrabhatpheri() : 0);
						prabhatPheripart = prabhatPheripart + (list.get(i).getPrabhatpheri_participants()!=null ? list.get(i).getPrabhatpheri_participants() : 0);
						arExp = arExp + (list.get(i).getTotal_arexp()!=null ? list.get(i).getTotal_arexp() : 0);
						bhoomiPoojan = bhoomiPoojan + (list.get(i).getTotal_bhoomi_poojan()!=null ? list.get(i).getTotal_bhoomi_poojan() : 0);
						lokarpan = lokarpan + (list.get(i).getTotal_lokarpan()!=null ? list.get(i).getTotal_lokarpan() : 0);
						shramdaan = shramdaan + (list.get(i).getTotal_shramdaan()!=null ? list.get(i).getTotal_shramdaan() : 0);
						plantation =  plantation + (list.get(i).getTotal_plantation()!=null ? list.get(i).getTotal_plantation() : 0);
						
						
						k++;
					}
					
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(locPlan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(locComp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(compAct), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(notCompAct), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(gramSabha), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(gramSabhapart), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(prabhatPheri), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(prabhatPheripart), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(arExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(bhoomiPoojan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(lokarpan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(shramdaan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 16, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report Watershed Yatra Status - District.pdf");
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
