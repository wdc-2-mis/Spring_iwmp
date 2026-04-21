package app.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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

import app.bean.FlexiFundMActivityBean;
import app.common.CommonFunctions;
import app.service.FlexFundService;

@Controller("FlexiFundReportController")
public class FlexiFundReportController {

	@Autowired(required = true)
	FlexFundService ffService;
	
	@RequestMapping(value = "/getStateWiseFlexiFundReport", method = RequestMethod.GET)
	public ModelAndView stateWiseFlexiFundReport(HttpServletRequest request, HttpServletResponse response) {
	    
		ModelAndView mav = new ModelAndView("reports/stateFlexiFundReport");
	    List<FlexiFundMActivityBean> list = new ArrayList<FlexiFundMActivityBean>();
	    
	    list = ffService.getStateWiseFlexiFundReport();
	    
	    mav.addObject("flexiFundReportList", list);
	    mav.addObject("flexiFundReportListSize", list.size());
	    
	    return mav;
	}
	
	
	@RequestMapping(value = "/downloadExcelStateWiseFlexiFundRpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStateWiseFlexiFundRpt(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<FlexiFundMActivityBean> list = new ArrayList<FlexiFundMActivityBean>();
		
		list = ffService.getStateWiseFlexiFundReport();
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report FF1 - State-wise Watershed Flexi Fund Details Report");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report FF1 - State-wise Watershed Flexi Fund Details Report";
		String areaAmtValDetail ="All Amount is Rs in Lakhs";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 19, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,3,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,6,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,9,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,12,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,15,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,18,18);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,19,19);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowhead = sheet.createRow(5);
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("No. of Projects");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Cactus");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<6;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Spring shed PPR Preparation");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=7;i<9;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Model Watershed");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<12;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		cell = rowhead.createCell(12);
		cell.setCellValue("Restoration of Waterbodies");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=13;i<15;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		cell = rowhead.createCell(15);
		cell.setCellValue("Watershed Janbhagidari Cup (Prize Money)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=16;i<18;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		cell = rowhead.createCell(18);
		cell.setCellValue("Total Estimated Cost (5+8+11+14+17)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(19);
		cell.setCellValue("Total Expenditure Cost (6+9+12+15+18)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		int startCol = 3;

		for (int i = 0; i < 5; i++) {
		    int colIndex = startCol + (i * 3);

		    cell = rowhead1.createCell(colIndex);
		    cell.setCellValue("Work");
		    cell.setCellStyle(style);
		    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

		    cell = rowhead1.createCell(colIndex + 1);
		    cell.setCellValue("Estimated Cost");
		    cell.setCellStyle(style);
		    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

		    cell = rowhead1.createCell(colIndex + 2);
		    cell.setCellValue("Expenditure Cost");
		    cell.setCellStyle(style);
		    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		}
		
		for(int i=0;i<2;i++)
		{
			int colIndex = startCol + (5 * 3) + i; // continue from where the loop ended

			cell =rowhead1.createCell(colIndex);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<20;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 8;
		int totProjects = 0;
		Integer totCactus = 0;
		BigDecimal totCactusEst = BigDecimal.ZERO;
		BigDecimal totCactusExp = BigDecimal.ZERO;
		Integer totSpringshed = 0;
		BigDecimal totSpringshedEst = BigDecimal.ZERO;
		BigDecimal totSpringshedExp = BigDecimal.ZERO;
		Integer totWatershed = 0;
		BigDecimal totWatershedEst = BigDecimal.ZERO;
		BigDecimal totWatershedExp = BigDecimal.ZERO;
		Integer totWaterbodies = 0;
		BigDecimal totWaterbodiesEst = BigDecimal.ZERO;
		BigDecimal totWaterbodiesExp = BigDecimal.ZERO;
		Integer totJanbhagidari = 0;
		BigDecimal totJanbhagidariEst = BigDecimal.ZERO;
		BigDecimal totJanbhagidariExp = BigDecimal.ZERO;
		BigDecimal totEstimation = BigDecimal.ZERO;
		BigDecimal totExpenditure = BigDecimal.ZERO;
		
		
	    for(FlexiFundMActivityBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getSt_name());
	    	row.createCell(2).setCellValue(bean.getProject());
	    	row.createCell(3).setCellValue(bean.getCactus());
	    	row.createCell(4).setCellValue(bean.getCactus_est().doubleValue());
	    	row.createCell(5).setCellValue(bean.getCactus_exp().doubleValue());
	    	row.createCell(6).setCellValue(bean.getSpringshed());
	    	row.createCell(7).setCellValue(bean.getSpringshed_est().doubleValue());
	    	row.createCell(8).setCellValue(bean.getSpringshed_exp().doubleValue());
	    	row.createCell(9).setCellValue(bean.getWatershed());
	    	row.createCell(10).setCellValue(bean.getWatershed_est().doubleValue());
	    	row.createCell(11).setCellValue(bean.getWatershed_exp().doubleValue());
	    	row.createCell(12).setCellValue(bean.getWaterbodies());
	    	row.createCell(13).setCellValue(bean.getWaterbodies_est().doubleValue());
	    	row.createCell(14).setCellValue(bean.getWaterbodies_exp().doubleValue());
	    	row.createCell(15).setCellValue(bean.getJanbhagidari());
	    	row.createCell(16).setCellValue(bean.getJanbhagidari_est().doubleValue());
	    	row.createCell(17).setCellValue(bean.getJanbhagidari_exp().doubleValue());
	    	row.createCell(18).setCellValue(bean.getTotal_estimation().doubleValue());
	    	row.createCell(19).setCellValue(bean.getTotal_expenditure().doubleValue());
	    	
	    	totProjects = totProjects + bean.getProject();
	    	totCactus = totCactus + bean.getCactus();
	    	totCactusEst = totCactusEst.add(bean.getCactus_est());
	    	totCactusExp = totCactusExp.add(bean.getCactus_exp());
	    	totSpringshed = totSpringshed + bean.getSpringshed();
	    	totSpringshedEst = totSpringshedEst.add(bean.getSpringshed_est());
	    	totSpringshedExp = totSpringshedExp.add(bean.getSpringshed_exp());
	    	totWatershed = totWatershed + bean.getWatershed();
	    	totWatershedEst = totWatershedEst.add(bean.getWatershed_est());
	    	totWatershedExp = totWatershedExp.add(bean.getWatershed_exp());
	    	totWaterbodies = totWaterbodies + bean.getWaterbodies();
	    	totWaterbodiesEst = totWaterbodiesEst.add(bean.getWaterbodies_est());
	    	totWaterbodiesExp = totWaterbodiesExp.add(bean.getWaterbodies_exp());
	    	totJanbhagidari = totJanbhagidari + bean.getJanbhagidari();
	    	totJanbhagidariEst = totJanbhagidariEst.add(bean.getJanbhagidari_est());
	    	totJanbhagidariExp = totJanbhagidariExp.add(bean.getJanbhagidari_exp());
	    	totEstimation = totEstimation.add(bean.getTotal_estimation());
	    	totExpenditure = totExpenditure.add(bean.getTotal_expenditure());
	    	
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
		cell.setCellValue(totProjects);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totCactus);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totCactusEst.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totCactusExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totSpringshed);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totSpringshedEst.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totSpringshedExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totWatershed);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totWatershedEst.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totWatershedExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totWaterbodies);
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(totWaterbodiesEst.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(totWaterbodiesExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(totJanbhagidari);
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(totJanbhagidariEst.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(17);
		cell.setCellValue(totJanbhagidariExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(18);
		cell.setCellValue(totEstimation.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(19);
		cell.setCellValue(totExpenditure.doubleValue());
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 19);
	    String fileName = "attachment; filename=Report FF1 - State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "reports/stateFlexiFundReport";
	}
	
	@RequestMapping(value = "/downloadPDFStateWiseFlexiFundRpt", method = RequestMethod.POST)
	public ModelAndView downloadPDFStateWiseFlexiFundRpt(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<FlexiFundMActivityBean> list = new ArrayList<FlexiFundMActivityBean>();
		
		list = ffService.getStateWiseFlexiFundReport();
			
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("StateFlexiFundReport");
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
			
			paragraph3 = new Paragraph("Report FF1 - State-wise Watershed Flexi Fund Details Report", f3);
			
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
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table, "All Amount is Rs in Lakhs", Element.ALIGN_RIGHT, 20, 1, bf8Bold);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of Projects", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cactus", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Spring shed PPR Preparation", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Model Watershed", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Restoration of Waterbodies", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Watershed Janbhagidari Cup (Prize Money)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Estimated Cost (5+8+11+14+17)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Expenditure Cost (6+9+12+15+18)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			
			for(int i=1; i <= 5; i++)
			{
				CommonFunctions.insertCellHeader(table, "Work", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Estimated Cost", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Expenditure Cost", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			
			for (int i = 1; i <= 20; i++) {
			    CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			int k = 1;
			Integer totProjects = 0;
			Integer totCactus = 0;
			BigDecimal totCactusEst = BigDecimal.ZERO;
			BigDecimal totCactusExp = BigDecimal.ZERO;
			Integer totSpringshed = 0;
			BigDecimal totSpringshedEst = BigDecimal.ZERO;
			BigDecimal totSpringshedExp = BigDecimal.ZERO;
			Integer totWatershed = 0;
			BigDecimal totWatershedEst = BigDecimal.ZERO;
			BigDecimal totWatershedExp = BigDecimal.ZERO;
			Integer totWaterbodies = 0;
			BigDecimal totWaterbodiesEst = BigDecimal.ZERO;
			BigDecimal totWaterbodiesExp = BigDecimal.ZERO;
			Integer totJanbhagidari = 0;
			BigDecimal totJanbhagidariEst = BigDecimal.ZERO;
			BigDecimal totJanbhagidariExp = BigDecimal.ZERO;
			BigDecimal totEstimation = BigDecimal.ZERO;
			BigDecimal totExpenditure = BigDecimal.ZERO;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProject()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCactus()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCactus_est()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCactus_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSpringshed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSpringshed_est()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSpringshed_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWatershed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWatershed_est()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWatershed_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWaterbodies()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWaterbodies_est()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWaterbodies_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getJanbhagidari()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getJanbhagidari_est()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getJanbhagidari_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_estimation()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_expenditure()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					totProjects = totProjects + list.get(i).getProject();
					totCactus = totCactus + list.get(i).getCactus();
					totCactusEst = totCactusEst.add(list.get(i).getCactus_est());
					totCactusExp = totCactusExp.add(list.get(i).getCactus_exp());
					totSpringshed = totSpringshed + list.get(i).getSpringshed();
					totSpringshedEst = totSpringshedEst.add(list.get(i).getSpringshed_est());
					totSpringshedExp = totSpringshedExp.add(list.get(i).getSpringshed_exp());
					totWatershed = totWatershed + list.get(i).getWatershed();
					totWatershedEst = totWatershedEst.add(list.get(i).getWatershed_est());
					totWatershedExp = totWatershedExp.add(list.get(i).getWatershed_exp());
					totWaterbodies = totWaterbodies + list.get(i).getWatershed();
					totWaterbodiesEst = totWaterbodiesEst.add(list.get(i).getWatershed_est());
					totWaterbodiesExp = totWaterbodiesExp.add(list.get(i).getWatershed_est());
					totJanbhagidari = totJanbhagidari + list.get(i).getJanbhagidari();
			    	totJanbhagidariEst = totJanbhagidariEst.add(list.get(i).getJanbhagidari_est());
			    	totJanbhagidariExp = totJanbhagidariExp.add(list.get(i).getJanbhagidari_exp());
			    	totEstimation = totEstimation.add(list.get(i).getTotal_estimation());
			    	totExpenditure = totExpenditure.add(list.get(i).getTotal_expenditure());
					
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totProjects), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCactus), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCactusEst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCactusExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totSpringshed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totSpringshedEst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totSpringshedExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWatershed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWatershedEst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWatershedExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWaterbodies), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWaterbodiesEst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWaterbodiesExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totJanbhagidari), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totJanbhagidariEst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totJanbhagidariExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totEstimation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totExpenditure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data Not Found", Element.ALIGN_CENTER, 20, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report FF1 - State.pdf");
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
