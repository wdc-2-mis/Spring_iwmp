package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
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
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,2); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,3,4);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,5,9);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,10,10);
		sheet.addMergedRegion(mergedRegion);
		
		mergedRegion = new CellRangeAddress(5,6,11,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,12,12);
		sheet.addMergedRegion(mergedRegion);
		
		mergedRegion = new CellRangeAddress(5,6,13,13);
		sheet.addMergedRegion(mergedRegion);
		
		mergedRegion = new CellRangeAddress(5,6,14,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,15,16);
		sheet.addMergedRegion(mergedRegion);
		
		
		mergedRegion = new CellRangeAddress(5,6,17,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,18,18);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,19,20);
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
		cell.setCellValue("Inauguration Date");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Pre Yatra Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(4);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Total No. of Participants");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=6; i<10; i++)
		{
			cell = rowhead.createCell(i); 
			cell.setCellStyle(style);
		}
		cell = rowhead.createCell(10);
		cell.setCellValue("No. of Locations Covered(Till Date)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("Total No. of Locations to be Covered for Van Activities");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		
		cell = rowhead.createCell(12);
		cell.setCellValue("No. of People Availed AR Experience");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(13);
		cell.setCellValue("No. of Works for Bhoomi Poojan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(14);
		cell.setCellValue("No. of Works for Lokarpan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(15);
		cell.setCellValue("Shramdaan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(16);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(17);
		cell.setCellValue("No. of Sapling Planted");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(18);
		cell.setCellValue("No. of Watershed Margdarshaks Honored");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(19);
		cell.setCellValue("Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(20);
		cell.setCellStyle(style);
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0; i<3; i++)
		{
			cell = rowhead1.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Gram Sabha");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(4);
		cell.setCellValue("Prabhat Phere");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("Gram Sabha Participants");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Prabhat Phere Participants");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Inauguration Participants");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Village Participants	");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Total");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10; i<15; i++)
		{
			cell = rowhead1.createCell(i); 
			cell.setCellStyle(style);
		}
		cell = rowhead1.createCell(15);
		cell.setCellValue("No. of Locations");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(16);
		cell.setCellValue("No. of People Participated");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(17);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(18);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(19);
		cell.setCellValue("Completed Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(20);
		cell.setCellValue("Not Completed Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		
		
		Row rowhead3 = sheet.createRow(7);
		
		for(int i=0;i<21;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 8;
		BigInteger gramSabha = BigInteger.ZERO;
		BigInteger prabhatPheri = BigInteger.ZERO;
		BigInteger gramSabhaPrtcpnts = BigInteger.ZERO;
		BigInteger prabhatPheriPrtcpnts = BigInteger.ZERO;
		BigInteger inaugPrtcpnts = BigInteger.ZERO;
		BigInteger villagePrtcpnts = BigInteger.ZERO;
		BigInteger grandtotal = BigInteger.ZERO;
		BigInteger locvrd = BigInteger.ZERO;
		BigInteger vanloc = BigInteger.ZERO;
		BigInteger arExp = BigInteger.ZERO;
		BigInteger bhoomiPoojan = BigInteger.ZERO;
		BigInteger lokarpan = BigInteger.ZERO;
		BigInteger shramloc = BigInteger.ZERO;
		BigInteger shramPrtcpnts = BigInteger.ZERO;
		BigInteger plantationsapl = BigInteger.ZERO;
		BigInteger award = BigInteger.ZERO;
		int compAct = 0;
		int notCompAct = 0;
		
		
		
	    for(WatershedYatraStatusBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
		    row.createCell(1).setCellValue(bean.getSt_name());
		    row.createCell(2).setCellValue(bean.getInauguration_date());
		    row.createCell(3).setCellValue(bean.getGramsabha().doubleValue());
		    row.createCell(4).setCellValue(bean.getPrabhatpheri().doubleValue());
		    row.createCell(5).setCellValue(bean.getGramsabha_participants().doubleValue());
		    row.createCell(6).setCellValue(bean.getPrabhatpheri_participants().doubleValue());
		    row.createCell(7).setCellValue(bean.getTotal_inauguration_participants().doubleValue());
		    row.createCell(8).setCellValue(bean.getTotal_village_participants().doubleValue());
		   
		    BigInteger gramsabha1 = bean.getGramsabha_participants() != null ? bean.getGramsabha_participants() : BigInteger.ZERO;
			BigInteger prabhatpheri1 = bean.getPrabhatpheri_participants() != null ? bean.getPrabhatpheri_participants() : BigInteger.ZERO;
			BigInteger inauguration1 = bean.getTotal_inauguration_participants() != null ? bean.getTotal_inauguration_participants() : BigInteger.ZERO;
			BigInteger village1 = bean.getTotal_village_participants() != null ? bean.getTotal_village_participants() : BigInteger.ZERO;
			BigInteger total1 = gramsabha1.add(prabhatpheri1).add(inauguration1).add(village1);
		    row.createCell(9).setCellValue(total1.doubleValue());
	    	row.createCell(10).setCellValue(bean.getTotal_locv().doubleValue());
	    	row.createCell(11).setCellValue(bean.getTotal_vanplan().doubleValue());
	    	
	    	row.createCell(12).setCellValue(bean.getTotal_arexp().doubleValue());
	    	row.createCell(13).setCellValue(bean.getTotal_bhoomi_poojan().doubleValue());
	    	row.createCell(14).setCellValue(bean.getTotal_lokarpan().doubleValue());
	    	row.createCell(15).setCellValue(bean.getTotal_loc_shramdaan().doubleValue());
	    	row.createCell(16).setCellValue(bean.getTotal_partcp_shramdaan().doubleValue());
	    	row.createCell(17).setCellValue(bean.getTotal_plantation_area().doubleValue());
	    	row.createCell(18).setCellValue(bean.getTotal_award_distribution().doubleValue());
	    	row.createCell(19).setCellValue(bean.getActivity_entered()!=null ? bean.getActivity_entered() : 0);
	    	row.createCell(20).setCellValue(bean.getAct_not_entered()!=null ? bean.getAct_not_entered() : 0);
	    	

		    gramSabha = gramSabha.add(bean.getGramsabha());
			prabhatPheri = prabhatPheri.add(bean.getPrabhatpheri());
			gramSabhaPrtcpnts = gramSabhaPrtcpnts.add(bean.getGramsabha_participants());
			prabhatPheriPrtcpnts = prabhatPheriPrtcpnts.add(bean.getPrabhatpheri_participants());
			inaugPrtcpnts = inaugPrtcpnts.add(bean.getTotal_inauguration_participants());
			villagePrtcpnts = villagePrtcpnts.add(bean.getTotal_village_participants());
			grandtotal = grandtotal.add(total1);
			locvrd = locvrd.add(bean.getTotal_locv());
			vanloc = vanloc.add(bean.getTotal_vanplan());
			arExp = arExp.add(bean.getTotal_arexp());
			bhoomiPoojan = bhoomiPoojan.add(bean.getTotal_bhoomi_poojan());
			lokarpan = lokarpan.add(bean.getTotal_lokarpan());
			shramloc = shramloc.add(bean.getTotal_loc_shramdaan());
			shramPrtcpnts = shramPrtcpnts.add(bean.getTotal_partcp_shramdaan());
			plantationsapl = plantationsapl.add(bean.getTotal_plantation_area());
			award = award.add(bean.getTotal_award_distribution());
			compAct = compAct+(bean.getActivity_entered());
			notCompAct = notCompAct+(bean.getAct_not_entered());
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

		Row row = sheet.createRow(list.size()+8);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellStyle(style1);
		
		cell = row.createCell(3);
		cell.setCellValue(gramSabha.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(prabhatPheri.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(gramSabhaPrtcpnts.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(prabhatPheriPrtcpnts.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(inaugPrtcpnts.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(villagePrtcpnts.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(grandtotal.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(locvrd.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(vanloc.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(arExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(bhoomiPoojan.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(lokarpan.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(shramloc.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(shramPrtcpnts.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(17);
		cell.setCellValue(plantationsapl.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(18);
		cell.setCellValue(award.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(19);
		cell.setCellValue(compAct);
		cell.setCellStyle(style1);
		cell = row.createCell(20);
		cell.setCellValue(notCompAct);
		cell.setCellStyle(style1);
	    
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
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
		        table = new PdfPTable(21);
		        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(3);
							
		        
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Inauguration Date", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pre Yatra Activity", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Participants", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Locations Covered(Till Date)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Locations to be Covered for Van Activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of People Availed AR Experience", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Works for Bhoomi Poojan", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Works for Lokarpan", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Shramdaan", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Sapling Planted", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Watershed Margdarshaks Honored", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, " Activity", Element.ALIGN_CENTER, 2, 1, bf8Bold);
								
				CommonFunctions.insertCellHeader(table, "Gram Sabha", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Prabhat Phere", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Gram Sabha Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Prabhat Phere Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Inauguration Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No. of Locations", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of People Participated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Completed Activity", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Completed Activity", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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

				int k = 1;
				
				BigInteger gramSabha = BigInteger.ZERO;
				BigInteger prabhatPheri = BigInteger.ZERO;
				BigInteger gramSabhaPrtcpnts = BigInteger.ZERO;
				BigInteger prabhatPheriPrtcpnts = BigInteger.ZERO;
				BigInteger inaugPrtcpnts = BigInteger.ZERO;
				BigInteger villagePrtcpnts = BigInteger.ZERO;
				BigInteger grandtotal = BigInteger.ZERO;
				BigInteger locvrd = BigInteger.ZERO;
				BigInteger vanloc = BigInteger.ZERO;
				BigInteger arExp = BigInteger.ZERO;
				BigInteger bhoomiPoojan = BigInteger.ZERO;
				BigInteger lokarpan = BigInteger.ZERO;
				BigInteger shramloc = BigInteger.ZERO;
				BigInteger shramPrtcpnts = BigInteger.ZERO;
				BigInteger plantationsapl = BigInteger.ZERO;
				BigInteger award = BigInteger.ZERO;
				int compAct = 0;
				int notCompAct = 0;
				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getInauguration_date()!=null ? list.get(i).getInauguration_date() : ""), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGramsabha()!=null ? list.get(i).getGramsabha() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPrabhatpheri()!=null ? list.get(i).getPrabhatpheri() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGramsabha_participants()!=null ? list.get(i).getGramsabha_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPrabhatpheri_participants()!=null ? list.get(i).getPrabhatpheri_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_inauguration_participants()!=null ? list.get(i).getTotal_inauguration_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_village_participants()!=null ? list.get(i).getTotal_village_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						BigInteger gramsabha1 = list.get(i).getGramsabha_participants() != null ? list.get(i).getGramsabha_participants() : BigInteger.ZERO;
						BigInteger prabhatpheri1 = list.get(i).getPrabhatpheri_participants() != null ? list.get(i).getPrabhatpheri_participants() : BigInteger.ZERO;
						BigInteger inauguration1 = list.get(i).getTotal_inauguration_participants() != null ? list.get(i).getTotal_inauguration_participants() : BigInteger.ZERO;
						BigInteger village1 = list.get(i).getTotal_village_participants() != null ? list.get(i).getTotal_village_participants() : BigInteger.ZERO;
						BigInteger total1 = gramsabha1.add(prabhatpheri1).add(inauguration1).add(village1);
						CommonFunctions.insertCell(table, String.valueOf(total1), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_locv()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_vanplan()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_arexp()!=null ? list.get(i).getTotal_arexp() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_bhoomi_poojan()!=null ? list.get(i).getTotal_bhoomi_poojan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_lokarpan()!=null ? list.get(i).getTotal_lokarpan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_loc_shramdaan()!=null ? list.get(i).getTotal_loc_shramdaan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_partcp_shramdaan()!=null ? list.get(i).getTotal_partcp_shramdaan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_plantation_area()!=null ? list.get(i).getTotal_plantation_area() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_award_distribution()!=null ? list.get(i).getTotal_award_distribution() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getActivity_entered()!=null ? list.get(i).getActivity_entered() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAct_not_entered()!=null ? list.get(i).getAct_not_entered() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						 
						gramSabha = gramSabha.add(list.get(i).getGramsabha());
						prabhatPheri = prabhatPheri.add(list.get(i).getPrabhatpheri());
						gramSabhaPrtcpnts = gramSabhaPrtcpnts.add(list.get(i).getGramsabha_participants());
						prabhatPheriPrtcpnts = prabhatPheriPrtcpnts.add(list.get(i).getPrabhatpheri_participants());
						inaugPrtcpnts = inaugPrtcpnts.add(list.get(i).getTotal_inauguration_participants());
						villagePrtcpnts = villagePrtcpnts.add(list.get(i).getTotal_village_participants());
						grandtotal = grandtotal.add(total1);
						locvrd = locvrd.add(list.get(i).getTotal_locv());
						vanloc = vanloc.add(list.get(i).getTotal_vanplan());
						arExp = arExp.add(list.get(i).getTotal_arexp());
						bhoomiPoojan = bhoomiPoojan.add(list.get(i).getTotal_bhoomi_poojan());
						lokarpan = lokarpan.add(list.get(i).getTotal_lokarpan());
						shramloc = shramloc.add(list.get(i).getTotal_loc_shramdaan());
						shramPrtcpnts = shramPrtcpnts.add(list.get(i).getTotal_partcp_shramdaan());
						plantationsapl = plantationsapl.add(list.get(i).getTotal_plantation_area());
						award = award.add(list.get(i).getTotal_award_distribution());
						compAct = compAct+(list.get(i).getActivity_entered());
						notCompAct = notCompAct+(list.get(i).getAct_not_entered());
						
						k++;
					}
					
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 3, 1, bf10Bold);
				
				CommonFunctions.insertCell3(table, String.valueOf(gramSabha), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(prabhatPheri), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(gramSabhaPrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(prabhatPheriPrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(inaugPrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(villagePrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(grandtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(locvrd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(vanloc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(arExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(bhoomiPoojan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(lokarpan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(shramloc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(shramPrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(plantationsapl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(award), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(compAct), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(notCompAct), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 19, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,19);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,2,3);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,4,8);
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
		mergedRegion = new CellRangeAddress(6,6,14,15);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,16,16);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,17,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,18,19);
		sheet.addMergedRegion(mergedRegion);
		
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State : "+ stName+"     Inauguration Date : "+inaugurationDate);  
		cell.setCellStyle(style);
		
		for(int i=1;i<20;i++)
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
		cell.setCellValue("Pre Yatra Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Total No. of Participants");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=5; i<9; i++)
		{
			cell = rowhead.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(9);
		cell.setCellValue("No. of Locations Covered(Till Date)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Total No. of Locations to be Covered for Van Activities");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("No. of People Availed AR Experience");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(12);
		cell.setCellValue("No. of Works for Bhoomi Poojan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(13);
		cell.setCellValue("No. of Works for Lokarpan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(14);
		cell.setCellValue("Shramdaan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(15);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(16);
		cell.setCellValue("No. of Sapling Planted");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(17);
		cell.setCellValue("No. of Watershed Margdarshaks Honored");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(18);
		cell.setCellValue("Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(19);
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0; i<3; i++)
		{
			cell = rowhead1.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(2);
		cell.setCellValue("Gram Sabha");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Prabhat Phere");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(4);
		cell.setCellValue("Gram Sabha Participants");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("Prabhat Phere Participants");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Inauguration Participants");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Village Participants	");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Total");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=9; i<14; i++)
		{
			cell = rowhead1.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(14);
		cell.setCellValue("No. of Locations");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(15);
		cell.setCellValue("No. of People Participated");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(16);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(17);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(18);
		cell.setCellValue("Completed Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(19);
		cell.setCellValue("Not Completed Activity");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		
		
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0;i<20;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		BigInteger gramSabha = BigInteger.ZERO;
		BigInteger prabhatPheri = BigInteger.ZERO;
		BigInteger gramSabhaPrtcpnts = BigInteger.ZERO;
		BigInteger prabhatPheriPrtcpnts = BigInteger.ZERO;
		BigInteger inaugPrtcpnts = BigInteger.ZERO;
		BigInteger villagePrtcpnts = BigInteger.ZERO;
		BigInteger grandtotal = BigInteger.ZERO;
		BigInteger locvrd = BigInteger.ZERO;
		BigInteger vanloc = BigInteger.ZERO;
		BigInteger arExp = BigInteger.ZERO;
		BigInteger bhoomiPoojan = BigInteger.ZERO;
		BigInteger lokarpan = BigInteger.ZERO;
		BigInteger shramloc = BigInteger.ZERO;
		BigInteger shramPrtcpnts = BigInteger.ZERO;
		BigInteger plantationsapl = BigInteger.ZERO;
		BigInteger award = BigInteger.ZERO;
		int compAct = 0;
		int notCompAct = 0;
		
		for(WatershedYatraStatusBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
		    row.createCell(1).setCellValue(bean.getDist_name());
		    row.createCell(2).setCellValue(bean.getGramsabha().doubleValue());
		    row.createCell(3).setCellValue(bean.getPrabhatpheri().doubleValue());
		    row.createCell(4).setCellValue(bean.getGramsabha_participants().doubleValue());
		    row.createCell(5).setCellValue(bean.getPrabhatpheri_participants().doubleValue());
		    row.createCell(6).setCellValue(bean.getTotal_inauguration_participants().doubleValue());
		    row.createCell(7).setCellValue(bean.getTotal_village_participants().doubleValue());
		   
		    BigInteger gramsabha1 = bean.getGramsabha_participants() != null ? bean.getGramsabha_participants() : BigInteger.ZERO;
			BigInteger prabhatpheri1 = bean.getPrabhatpheri_participants() != null ? bean.getPrabhatpheri_participants() : BigInteger.ZERO;
			BigInteger inauguration1 = bean.getTotal_inauguration_participants() != null ? bean.getTotal_inauguration_participants() : BigInteger.ZERO;
			BigInteger village1 = bean.getTotal_village_participants() != null ? bean.getTotal_village_participants() : BigInteger.ZERO;
			BigInteger total1 = gramsabha1.add(prabhatpheri1).add(inauguration1).add(village1);
		    
			row.createCell(8).setCellValue(total1.doubleValue());
	    	row.createCell(9).setCellValue(bean.getTotal_locv().doubleValue());
	    	row.createCell(10).setCellValue(bean.getTotal_vanplan().doubleValue());
	    	
	    	row.createCell(11).setCellValue(bean.getTotal_arexp().doubleValue());
	    	row.createCell(12).setCellValue(bean.getTotal_bhoomi_poojan().doubleValue());
	    	row.createCell(13).setCellValue(bean.getTotal_lokarpan().doubleValue());
	    	row.createCell(14).setCellValue(bean.getTotal_loc_shramdaan().doubleValue());
	    	row.createCell(15).setCellValue(bean.getTotal_partcp_shramdaan().doubleValue());
	    	row.createCell(16).setCellValue(bean.getTotal_plantation_area().doubleValue());
	    	row.createCell(17).setCellValue(bean.getTotal_award_distribution().doubleValue());
	    	row.createCell(18).setCellValue(bean.getActivity_entered()!=null ? bean.getActivity_entered() : 0);
	    	row.createCell(19).setCellValue(bean.getAct_not_entered()!=null ? bean.getAct_not_entered() : 0);
	    	

		    gramSabha = gramSabha.add(bean.getGramsabha());
			prabhatPheri = prabhatPheri.add(bean.getPrabhatpheri());
			gramSabhaPrtcpnts = gramSabhaPrtcpnts.add(bean.getGramsabha_participants());
			prabhatPheriPrtcpnts = prabhatPheriPrtcpnts.add(bean.getPrabhatpheri_participants());
			inaugPrtcpnts = inaugPrtcpnts.add(bean.getTotal_inauguration_participants());
			villagePrtcpnts = villagePrtcpnts.add(bean.getTotal_village_participants());
			grandtotal = grandtotal.add(total1);
			locvrd = locvrd.add(bean.getTotal_locv());
			vanloc = vanloc.add(bean.getTotal_vanplan());
			arExp = arExp.add(bean.getTotal_arexp());
			bhoomiPoojan = bhoomiPoojan.add(bean.getTotal_bhoomi_poojan());
			lokarpan = lokarpan.add(bean.getTotal_lokarpan());
			shramloc = shramloc.add(bean.getTotal_loc_shramdaan());
			shramPrtcpnts = shramPrtcpnts.add(bean.getTotal_partcp_shramdaan());
			plantationsapl = plantationsapl.add(bean.getTotal_plantation_area());
			award = award.add(bean.getTotal_award_distribution());
			compAct = compAct+(bean.getActivity_entered());
			notCompAct = notCompAct+(bean.getAct_not_entered());
			
			
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
		cell.setCellValue(gramSabha.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(prabhatPheri.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(gramSabhaPrtcpnts.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(prabhatPheriPrtcpnts.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(inaugPrtcpnts.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(villagePrtcpnts.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(grandtotal.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(locvrd.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(vanloc.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(arExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(bhoomiPoojan.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(lokarpan.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(shramloc.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(shramPrtcpnts.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(plantationsapl.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(17);
		cell.setCellValue(award.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(18);
		cell.setCellValue(compAct);
		cell.setCellStyle(style1);
		cell = row.createCell(19);
		cell.setCellValue(notCompAct);
		cell.setCellStyle(style1);
	    

	    
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 19);
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
		        table = new PdfPTable(20);
		        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(3);
				
		        CommonFunctions.insertCellHeader(table, "State : "+stName+"     Inauguration Date : "+inaugurationDate, Element.ALIGN_LEFT, 20, 1, bf8Bold);
		        
		        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pre Yatra Activity", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Participants", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Locations Covered(Till Date)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Locations to be Covered for Van Activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of People Availed AR Experience", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Works for Bhoomi Poojan", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Works for Lokarpan", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Shramdaan", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Sapling Planted", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Watershed Margdarshaks Honored", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, " Activity", Element.ALIGN_CENTER, 2, 1, bf8Bold);
								
				CommonFunctions.insertCellHeader(table, "Gram Sabha", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Prabhat Phere", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Gram Sabha Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Prabhat Phere Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Inauguration Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village Participants", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "No. of Locations", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of People Participated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Completed Activity", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Completed Activity", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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

				int k = 1;
				
				BigInteger gramSabha = BigInteger.ZERO;
				BigInteger prabhatPheri = BigInteger.ZERO;
				BigInteger gramSabhaPrtcpnts = BigInteger.ZERO;
				BigInteger prabhatPheriPrtcpnts = BigInteger.ZERO;
				BigInteger inaugPrtcpnts = BigInteger.ZERO;
				BigInteger villagePrtcpnts = BigInteger.ZERO;
				BigInteger grandtotal = BigInteger.ZERO;
				BigInteger locvrd = BigInteger.ZERO;
				BigInteger vanloc = BigInteger.ZERO;
				BigInteger arExp = BigInteger.ZERO;
				BigInteger bhoomiPoojan = BigInteger.ZERO;
				BigInteger lokarpan = BigInteger.ZERO;
				BigInteger shramloc = BigInteger.ZERO;
				BigInteger shramPrtcpnts = BigInteger.ZERO;
				BigInteger plantationsapl = BigInteger.ZERO;
				BigInteger award = BigInteger.ZERO;
				int compAct = 0;
				int notCompAct = 0;
				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGramsabha()!=null ? list.get(i).getGramsabha() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPrabhatpheri()!=null ? list.get(i).getPrabhatpheri() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGramsabha_participants()!=null ? list.get(i).getGramsabha_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPrabhatpheri_participants()!=null ? list.get(i).getPrabhatpheri_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_inauguration_participants()!=null ? list.get(i).getTotal_inauguration_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_village_participants()!=null ? list.get(i).getTotal_village_participants() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						BigInteger gramsabha1 = list.get(i).getGramsabha_participants() != null ? list.get(i).getGramsabha_participants() : BigInteger.ZERO;
						BigInteger prabhatpheri1 = list.get(i).getPrabhatpheri_participants() != null ? list.get(i).getPrabhatpheri_participants() : BigInteger.ZERO;
						BigInteger inauguration1 = list.get(i).getTotal_inauguration_participants() != null ? list.get(i).getTotal_inauguration_participants() : BigInteger.ZERO;
						BigInteger village1 = list.get(i).getTotal_village_participants() != null ? list.get(i).getTotal_village_participants() : BigInteger.ZERO;
						BigInteger total1 = gramsabha1.add(prabhatpheri1).add(inauguration1).add(village1);
						CommonFunctions.insertCell(table, String.valueOf(total1), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_locv()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_vanplan()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_arexp()!=null ? list.get(i).getTotal_arexp() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_bhoomi_poojan()!=null ? list.get(i).getTotal_bhoomi_poojan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_lokarpan()!=null ? list.get(i).getTotal_lokarpan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_loc_shramdaan()!=null ? list.get(i).getTotal_loc_shramdaan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_partcp_shramdaan()!=null ? list.get(i).getTotal_partcp_shramdaan() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_plantation_area()!=null ? list.get(i).getTotal_plantation_area() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_award_distribution()!=null ? list.get(i).getTotal_award_distribution() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getActivity_entered()!=null ? list.get(i).getActivity_entered() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAct_not_entered()!=null ? list.get(i).getAct_not_entered() : 0), Element.ALIGN_RIGHT, 1, 1, bf8);
						 
						gramSabha = gramSabha.add(list.get(i).getGramsabha());
						prabhatPheri = prabhatPheri.add(list.get(i).getPrabhatpheri());
						gramSabhaPrtcpnts = gramSabhaPrtcpnts.add(list.get(i).getGramsabha_participants());
						prabhatPheriPrtcpnts = prabhatPheriPrtcpnts.add(list.get(i).getPrabhatpheri_participants());
						inaugPrtcpnts = inaugPrtcpnts.add(list.get(i).getTotal_inauguration_participants());
						villagePrtcpnts = villagePrtcpnts.add(list.get(i).getTotal_village_participants());
						grandtotal = grandtotal.add(total1);
						locvrd = locvrd.add(list.get(i).getTotal_locv());
						vanloc = vanloc.add(list.get(i).getTotal_vanplan());
						arExp = arExp.add(list.get(i).getTotal_arexp());
						bhoomiPoojan = bhoomiPoojan.add(list.get(i).getTotal_bhoomi_poojan());
						lokarpan = lokarpan.add(list.get(i).getTotal_lokarpan());
						shramloc = shramloc.add(list.get(i).getTotal_loc_shramdaan());
						shramPrtcpnts = shramPrtcpnts.add(list.get(i).getTotal_partcp_shramdaan());
						plantationsapl = plantationsapl.add(list.get(i).getTotal_plantation_area());
						award = award.add(list.get(i).getTotal_award_distribution());
						compAct = compAct+(list.get(i).getActivity_entered());
						notCompAct = notCompAct+(list.get(i).getAct_not_entered());
						
						k++;
					}
					
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				
				CommonFunctions.insertCell3(table, String.valueOf(gramSabha), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(prabhatPheri), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(gramSabhaPrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(prabhatPheriPrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(inaugPrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(villagePrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(grandtotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(locvrd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(vanloc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(arExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(bhoomiPoojan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(lokarpan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(shramloc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(shramPrtcpnts), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(plantationsapl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(award), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(compAct), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(notCompAct), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 20, 1, bf8);
				
				
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
