package app.punarutthan.controller;

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

import app.common.CommonFunctions;
import app.punarutthan.service.WatershedPunarutthanService;

@Controller("PunarutthanReportController")
public class PunarutthanReportController {

	@Autowired
	WatershedPunarutthanService ser;
	
	@RequestMapping(value = "/getStatePunarutthanReport", method = RequestMethod.GET)
	public ModelAndView punarutthanStateReport(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("punarutthan/StatePunarutthanReport");
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		
		list = ser.punarutthanRptStData();
		
		mav.addObject("punarutthanRptStDataList",list);
		mav.addObject("punarutthanRptStDataListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/getDistPunarutthanReport", method = RequestMethod.GET)
	public ModelAndView punarutthanDistReport(HttpServletRequest request, HttpServletResponse response) {
		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		ModelAndView mav = new ModelAndView("punarutthan/DistPunarutthanReport");
		
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		
		list = ser.punarutthanRptDistData(Integer.parseInt(stcd));
		
		mav.addObject("stcd",stcd);
		mav.addObject("stName",stName);
		mav.addObject("punarutthanRptDistDataList",list);
		mav.addObject("punarutthanRptDistDataListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/getProjPunarutthanReport", method = RequestMethod.GET)
	public ModelAndView punarutthanProjReport(HttpServletRequest request, HttpServletResponse response) {
		
		String stName = request.getParameter("stName");
		String distName = request.getParameter("dName");
		String dcode = request.getParameter("distcd");
		
		ModelAndView mav = new ModelAndView("punarutthan/ProjPunarutthanReport");
		
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		
		list = ser.punarutthanRptProjData(Integer.parseInt(dcode));
		
		mav.addObject("dcode",dcode);
		mav.addObject("dName",distName);
		mav.addObject("stName",stName);
		mav.addObject("punarutthanRptProjDataList",list);
		mav.addObject("punarutthanRptProjDataListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/downloadExcelStPunarutthanRpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStPunarutthanRpt(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		
		list = ser.punarutthanRptStData();
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report WP1 - State-wise Watershed Punarutthan Details");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report WP1 - State-wise Watershed Punarutthan Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,3,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,7,7);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,8,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,9,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,9,12);
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
		cell.setCellValue("Total number of structures identified for maintenance");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Planning");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<7;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total number of structures where maintenance work Implemented");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Total number of structures where maintenance work Not Implemented (3-8)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Implementation");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<13;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Estimated cost (Rs in Lakhs) for maintenance works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Expenditure (Rs in Lakhs) for maintenance works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<13;i++)
		{
			cell =rowhead1.createCell(i);
		}
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(3);
		cell.setCellValue("Total Estimated cost");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(4);
		cell.setCellValue("Cost from WDF");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(5);
		cell.setCellValue("Cost from VB G RAM G/MGNREGA");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(6);
		cell.setCellValue("Cost from Other source (if any)");
		cell.setCellStyle(style);
		
		
		for(int i=7;i<9;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Total Expenditure (11+12+13)");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(10);
		cell.setCellValue("Expenditure from WDF");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(11);
		cell.setCellValue("Expenditure from VB G RAM G/MGNREGA");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(12);
		cell.setCellValue("Expenditure from Other source (if any)");
		cell.setCellStyle(style);
		
		
		
		Row rowhead3 = sheet.createRow(8);
		
		for(int i=0;i<13;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 9;
		int totPlan = 0;
		BigDecimal totCost = BigDecimal.ZERO;
		BigDecimal totWdfCost = BigDecimal.ZERO;
		BigDecimal totMgnregaCost = BigDecimal.ZERO;
		BigDecimal totOtherCost = BigDecimal.ZERO;
		int totImpl = 0;
		int totNotImpl = 0;
		BigDecimal totExp = BigDecimal.ZERO;
		BigDecimal totWdfExp = BigDecimal.ZERO;
		BigDecimal totMgnregaExp = BigDecimal.ZERO;
		BigDecimal totOtherExp = BigDecimal.ZERO;
		
		
	    for(WatershedPunarutthanBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getStname());
	    	row.createCell(2).setCellValue(bean.getPlan_work());
	    	row.createCell(3).setCellValue(bean.getTotalcost().doubleValue());
	    	row.createCell(4).setCellValue(bean.getWdf_cost().doubleValue());
	    	row.createCell(5).setCellValue(bean.getMgnrega_cost().doubleValue());
	    	row.createCell(6).setCellValue(bean.getOther_cost().doubleValue());
	    	row.createCell(7).setCellValue(bean.getImpl_work());
	    	row.createCell(8).setCellValue(bean.getNot_impl());
	    	row.createCell(9).setCellValue(bean.getTotalexp().doubleValue());
	    	row.createCell(10).setCellValue(bean.getWdf_exp().doubleValue());
	    	row.createCell(11).setCellValue(bean.getMgnrega_exp().doubleValue());
	    	row.createCell(12).setCellValue(bean.getOther_exp().doubleValue());
	    	
	    	totPlan = totPlan + bean.getPlan_work();
	    	totCost = totCost.add(bean.getTotalcost());
	    	totWdfCost = totWdfCost.add(bean.getWdf_cost());
	    	totMgnregaCost = totMgnregaCost.add(bean.getMgnrega_cost());
	    	totOtherCost = totOtherCost.add(bean.getOther_cost());
	    	totImpl = totImpl + bean.getImpl_work();
	    	totNotImpl = totNotImpl + bean.getNot_impl();
	    	totExp = totExp.add(bean.getTotalexp());
	    	totWdfExp = totWdfExp.add(bean.getWdf_exp());
	    	totMgnregaExp = totMgnregaExp.add(bean.getMgnrega_exp());
	    	totOtherExp = totOtherExp.add(bean.getOther_exp());
	    	
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
		cell.setCellValue(totPlan);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totWdfCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totMgnregaCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totOtherCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totImpl);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totNotImpl);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totWdfExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totMgnregaExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totOtherExp.doubleValue());
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
	    String fileName = "attachment; filename=Report WP1 - State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "punarutthan/StatePunarutthanReport";
	}
	
	@RequestMapping(value = "/downloadPDFStPunarutthanRpt", method = RequestMethod.POST)
	public ModelAndView downloadPDFStPunarutthanRpt(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		
		list = ser.punarutthanRptStData();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("StatePunarutthanReport");
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
			
			paragraph3 = new Paragraph("Report WP1 - State-wise Watershed Punarutthan Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(13);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total number of structures identified for maintenance", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Planning", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total number of structures where maintenance work Implemented", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total number of structures where maintenance work Not Implemented (3-8)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Implementation", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Estimated cost (Rs in Lakhs) for maintenance works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure (Rs in Lakhs) for maintenance works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Total Estimated cost", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost from WDF", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost from VB G RAM G/MGNREGA", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost from Other source (if any)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Expenditure (11+12+13)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure from WDF", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure from VB G RAM G/MGNREGA", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure from Other source (if any)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
		    
			for (int i = 1; i <= 13; i++) {
			    CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			int k = 1;
			int totPlan = 0;
			BigDecimal totCost = BigDecimal.ZERO;
			BigDecimal totWdfCost = BigDecimal.ZERO;
			BigDecimal totMgnregaCost = BigDecimal.ZERO;
			BigDecimal totOtherCost = BigDecimal.ZERO;
			int totImpl = 0;
			int totNotImpl = 0;
			BigDecimal totExp = BigDecimal.ZERO;
			BigDecimal totWdfExp = BigDecimal.ZERO;
			BigDecimal totMgnregaExp = BigDecimal.ZERO;
			BigDecimal totOtherExp = BigDecimal.ZERO;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPlan_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalcost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWdf_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMgnrega_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOther_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getImpl_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNot_impl()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalexp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWdf_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMgnrega_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOther_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					
					totPlan = totPlan + list.get(i).getPlan_work();
			    	totCost = totCost.add(list.get(i).getTotalcost());
			    	totWdfCost = totWdfCost.add(list.get(i).getWdf_cost());
			    	totMgnregaCost = totMgnregaCost.add(list.get(i).getMgnrega_cost());
			    	totOtherCost = totOtherCost.add(list.get(i).getOther_cost());
			    	totImpl = totImpl + list.get(i).getImpl_work();
			    	totNotImpl = totNotImpl + list.get(i).getNot_impl();
			    	totExp = totExp.add(list.get(i).getTotalexp());
			    	totWdfExp = totWdfExp.add(list.get(i).getWdf_exp());
			    	totMgnregaExp = totMgnregaExp.add(list.get(i).getMgnrega_exp());
			    	totOtherExp = totOtherExp.add(list.get(i).getOther_exp());
					
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPlan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWdfCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMgnregaCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totOtherCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totImpl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totNotImpl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWdfExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMgnregaExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totOtherExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 13, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WP1 - State.pdf");
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
	
	@RequestMapping(value = "/downloadExcelDistPunarutthanRpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistPunarutthanRpt(HttpServletRequest request, HttpServletResponse response)
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		
		list = ser.punarutthanRptDistData(Integer.parseInt(stcd));
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report WP1 - District-wise Watershed Punarutthan Details");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report WP1 - District-wise Watershed Punarutthan Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+10,list.size()+10,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,7,7);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,8,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,9,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,3,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,9,12);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State Name : "+ stName);  
		cell.setCellStyle(style);
		
		for(int i=1;i<13;i++)
		{
			cell = rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total number of structures identified for maintenance");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Planning");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<7;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total number of structures where maintenance work Implemented");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Total number of structures where maintenance work Not Implemented (3-8)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Implementation");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<13;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Estimated cost (Rs in Lakhs) for maintenance works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Expenditure (Rs in Lakhs) for maintenance works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<13;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(3);
		cell.setCellValue("Total Estimated cost");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(4);
		cell.setCellValue("Cost from WDF");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(5);
		cell.setCellValue("Cost from VB G RAM G/MGNREGA");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(6);
		cell.setCellValue("Cost from Other source (if any)");
		cell.setCellStyle(style);
		
		
		for(int i=7;i<9;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Total Expenditure (11+12+13)");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(10);
		cell.setCellValue("Expenditure from WDF");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(11);
		cell.setCellValue("Expenditure from VB G RAM G/MGNREGA");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(12);
		cell.setCellValue("Expenditure from Other source (if any)");
		cell.setCellStyle(style);
		
		
		
		Row rowhead3 = sheet.createRow(9);
		
		for(int i=0;i<13;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 10;
		int totPlan = 0;
		BigDecimal totCost = BigDecimal.ZERO;
		BigDecimal totWdfCost = BigDecimal.ZERO;
		BigDecimal totMgnregaCost = BigDecimal.ZERO;
		BigDecimal totOtherCost = BigDecimal.ZERO;
		int totImpl = 0;
		int totNotImpl = 0;
		BigDecimal totExp = BigDecimal.ZERO;
		BigDecimal totWdfExp = BigDecimal.ZERO;
		BigDecimal totMgnregaExp = BigDecimal.ZERO;
		BigDecimal totOtherExp = BigDecimal.ZERO;
		
		
	    for(WatershedPunarutthanBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getDistname());
	    	row.createCell(2).setCellValue(bean.getPlan_work());
	    	row.createCell(3).setCellValue(bean.getTotalcost().doubleValue());
	    	row.createCell(4).setCellValue(bean.getWdf_cost().doubleValue());
	    	row.createCell(5).setCellValue(bean.getMgnrega_cost().doubleValue());
	    	row.createCell(6).setCellValue(bean.getOther_cost().doubleValue());
	    	row.createCell(7).setCellValue(bean.getImpl_work());
	    	row.createCell(8).setCellValue(bean.getNot_impl());
	    	row.createCell(9).setCellValue(bean.getTotalexp().doubleValue());
	    	row.createCell(10).setCellValue(bean.getWdf_exp().doubleValue());
	    	row.createCell(11).setCellValue(bean.getMgnrega_exp().doubleValue());
	    	row.createCell(12).setCellValue(bean.getOther_exp().doubleValue());
	    	
	    	
	    	totPlan = totPlan + bean.getPlan_work();
	    	totCost = totCost.add(bean.getTotalcost());
	    	totWdfCost = totWdfCost.add(bean.getWdf_cost());
	    	totMgnregaCost = totMgnregaCost.add(bean.getMgnrega_cost());
	    	totOtherCost = totOtherCost.add(bean.getOther_cost());
	    	totImpl = totImpl + bean.getImpl_work();
	    	totNotImpl = totNotImpl + bean.getNot_impl();
	    	totExp = totExp.add(bean.getTotalexp());
	    	totWdfExp = totWdfExp.add(bean.getWdf_exp());
	    	totMgnregaExp = totMgnregaExp.add(bean.getMgnrega_exp());
	    	totOtherExp = totOtherExp.add(bean.getOther_exp());
	    	
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
		
		Row row = sheet.createRow(list.size()+10);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellValue(totPlan);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totWdfCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totMgnregaCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totOtherCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totImpl);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totNotImpl);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totWdfExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totMgnregaExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totOtherExp.doubleValue());
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
	    String fileName = "attachment; filename=Report WP1 - District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "punarutthan/DistPunarutthanReport";
	}
	
	@RequestMapping(value = "/downloadPDFDistPunarutthanRpt", method = RequestMethod.POST)
	public ModelAndView downloadPDFDistPunarutthanRpt(HttpServletRequest request, HttpServletResponse response)
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		
		list = ser.punarutthanRptDistData(Integer.parseInt(stcd));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("DistrictPunarutthanReport");
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
			
			paragraph3 = new Paragraph("Report WP1 - District-wise Watershed Punarutthan Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(13);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(5);
		    
		    CommonFunctions.insertCellHeader(table, "State Name : "+stName, Element.ALIGN_LEFT, 13, 1, bf8Bold);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total number of structures identified for maintenance", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Planning", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total number of structures where maintenance work Implemented", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total number of structures where maintenance work Not Implemented (3-8)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Implementation", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Estimated cost (Rs in Lakhs) for maintenance works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure (Rs in Lakhs) for maintenance works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Total Estimated cost", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost from WDF", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost from VB G RAM G/MGNREGA", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost from Other source (if any)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Expenditure (11+12+13)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure from WDF", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure from VB G RAM G/MGNREGA", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure from Other source (if any)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
		    
			for (int i = 1; i <= 13; i++) {
			    CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			int k = 1;
			int totPlan = 0;
			BigDecimal totCost = BigDecimal.ZERO;
			BigDecimal totWdfCost = BigDecimal.ZERO;
			BigDecimal totMgnregaCost = BigDecimal.ZERO;
			BigDecimal totOtherCost = BigDecimal.ZERO;
			int totImpl = 0;
			int totNotImpl = 0;
			BigDecimal totExp = BigDecimal.ZERO;
			BigDecimal totWdfExp = BigDecimal.ZERO;
			BigDecimal totMgnregaExp = BigDecimal.ZERO;
			BigDecimal totOtherExp = BigDecimal.ZERO;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPlan_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalcost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWdf_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMgnrega_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOther_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getImpl_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNot_impl()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalexp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWdf_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMgnrega_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOther_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					
					totPlan = totPlan + list.get(i).getPlan_work();
			    	totCost = totCost.add(list.get(i).getTotalcost());
			    	totWdfCost = totWdfCost.add(list.get(i).getWdf_cost());
			    	totMgnregaCost = totMgnregaCost.add(list.get(i).getMgnrega_cost());
			    	totOtherCost = totOtherCost.add(list.get(i).getOther_cost());
			    	totImpl = totImpl + list.get(i).getImpl_work();
			    	totNotImpl = totNotImpl + list.get(i).getNot_impl();
			    	totExp = totExp.add(list.get(i).getTotalexp());
			    	totWdfExp = totWdfExp.add(list.get(i).getWdf_exp());
			    	totMgnregaExp = totMgnregaExp.add(list.get(i).getMgnrega_exp());
			    	totOtherExp = totOtherExp.add(list.get(i).getOther_exp());
					
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPlan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWdfCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMgnregaCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totOtherCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totImpl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totNotImpl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWdfExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMgnregaExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totOtherExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 13, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WP1 - District.pdf");
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
	
	@RequestMapping(value = "/downloadExcelProjPunarutthanRpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjPunarutthanRpt(HttpServletRequest request, HttpServletResponse response)
	{
		String stName = request.getParameter("stName");
		String dcode = request.getParameter("dcode");
		String dName = request.getParameter("dName");
		
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		
		list = ser.punarutthanRptProjData(Integer.parseInt(dcode));
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report WP1 - Project-wise Watershed Punarutthan Details");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report WP1 - Project-wise Watershed Punarutthan Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+10,list.size()+10,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,7,7);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,8,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,9,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,3,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,9,12);
		sheet.addMergedRegion(mergedRegion);
		
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State Name : "+ stName+",   District Name : "+dName);  
		cell.setCellStyle(style);
		
		for(int i=1;i<13;i++)
		{
			cell = rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Project Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total number of structures identified for maintenance");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Planning");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<7;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total number of structures where maintenance work Implemented");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Total number of structures where maintenance work Not Implemented (3-8)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Implementation");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<13;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Estimated cost (Rs in Lakhs) for maintenance works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Expenditure (Rs in Lakhs) for maintenance works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<13;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(3);
		cell.setCellValue("Total Estimated cost");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(4);
		cell.setCellValue("Cost from WDF");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(5);
		cell.setCellValue("Cost from VB G RAM G/MGNREGA");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(6);
		cell.setCellValue("Cost from Other source (if any)");
		cell.setCellStyle(style);
		
		
		for(int i=7;i<9;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Total Expenditure (11+12+13)");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(10);
		cell.setCellValue("Expenditure from WDF");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(11);
		cell.setCellValue("Expenditure from VB G RAM G/MGNREGA");
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(12);
		cell.setCellValue("Expenditure from Other source (if any)");
		cell.setCellStyle(style);
		
		
		Row rowhead3 = sheet.createRow(9);
		
		for(int i=0;i<13;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 10;
		int totPlan = 0;
		BigDecimal totCost = BigDecimal.ZERO;
		BigDecimal totWdfCost = BigDecimal.ZERO;
		BigDecimal totMgnregaCost = BigDecimal.ZERO;
		BigDecimal totOtherCost = BigDecimal.ZERO;
		int totImpl = 0;
		int totNotImpl = 0;
		BigDecimal totExp = BigDecimal.ZERO;
		BigDecimal totWdfExp = BigDecimal.ZERO;
		BigDecimal totMgnregaExp = BigDecimal.ZERO;
		BigDecimal totOtherExp = BigDecimal.ZERO;
		
		
	    for(WatershedPunarutthanBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getProj_name());
	    	row.createCell(2).setCellValue(bean.getPlan_work());
	    	row.createCell(3).setCellValue(bean.getTotalcost().doubleValue());
	    	row.createCell(4).setCellValue(bean.getWdf_cost().doubleValue());
	    	row.createCell(5).setCellValue(bean.getMgnrega_cost().doubleValue());
	    	row.createCell(6).setCellValue(bean.getOther_cost().doubleValue());
	    	row.createCell(7).setCellValue(bean.getImpl_work());
	    	row.createCell(8).setCellValue(bean.getNot_impl());
	    	row.createCell(9).setCellValue(bean.getTotalexp().doubleValue());
	    	row.createCell(10).setCellValue(bean.getWdf_exp().doubleValue());
	    	row.createCell(11).setCellValue(bean.getMgnrega_exp().doubleValue());
	    	row.createCell(12).setCellValue(bean.getOther_exp().doubleValue());
	    	
	    	totPlan = totPlan + bean.getPlan_work();
	    	totCost = totCost.add(bean.getTotalcost());
	    	totWdfCost = totWdfCost.add(bean.getWdf_cost());
	    	totMgnregaCost = totMgnregaCost.add(bean.getMgnrega_cost());
	    	totOtherCost = totOtherCost.add(bean.getOther_cost());
	    	totImpl = totImpl + bean.getImpl_work();
	    	totNotImpl = totNotImpl + bean.getNot_impl();
	    	totExp = totExp.add(bean.getTotalexp());
	    	totWdfExp = totWdfExp.add(bean.getWdf_exp());
	    	totMgnregaExp = totMgnregaExp.add(bean.getMgnrega_exp());
	    	totOtherExp = totOtherExp.add(bean.getOther_exp());
	    	
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
		
		Row row = sheet.createRow(list.size()+10);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellValue(totPlan);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totWdfCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totMgnregaCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totOtherCost.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totImpl);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totNotImpl);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totWdfExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totMgnregaExp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totOtherExp.doubleValue());
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
	    String fileName = "attachment; filename=Report WP1 - Project.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "punarutthan/ProjPunarutthanReport";
	}
	
	@RequestMapping(value = "/downloadPDFProjPunarutthanRpt", method = RequestMethod.POST)
	public ModelAndView downloadPDFProjPunarutthanRpt(HttpServletRequest request, HttpServletResponse response)
	{
		String stName = request.getParameter("stName");
		String dcode = request.getParameter("dcode");
		String dName = request.getParameter("dName");
		
		List<WatershedPunarutthanBean> list = new ArrayList<WatershedPunarutthanBean>();
		
		list = ser.punarutthanRptProjData(Integer.parseInt(dcode));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ProjectPunarutthanReport");
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
			
			paragraph3 = new Paragraph("Report WP1 - Project-wise Watershed Punarutthan Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(13);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(5);
		    
      	    
		    CommonFunctions.insertCellHeader(table, "State Name : "+stName + ", District Name : "+dName, Element.ALIGN_LEFT, 13, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total number of structures identified for maintenance", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Planning", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total number of structures where maintenance work Implemented", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total number of structures where maintenance work Not Implemented (3-8)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Implementation", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Estimated cost (Rs in Lakhs) for maintenance works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure (Rs in Lakhs) for maintenance works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Total Estimated cost", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost from WDF", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost from VB G RAM G/MGNREGA", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cost from Other source (if any)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Expenditure (11+12+13)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure from WDF", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure from VB G RAM G/MGNREGA", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure from Other source (if any)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
			for (int i = 1; i <= 13; i++) {
			    CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			int k = 1;
			int totPlan = 0;
			BigDecimal totCost = BigDecimal.ZERO;
			BigDecimal totWdfCost = BigDecimal.ZERO;
			BigDecimal totMgnregaCost = BigDecimal.ZERO;
			BigDecimal totOtherCost = BigDecimal.ZERO;
			int totImpl = 0;
			int totNotImpl = 0;
			BigDecimal totExp = BigDecimal.ZERO;
			BigDecimal totWdfExp = BigDecimal.ZERO;
			BigDecimal totMgnregaExp = BigDecimal.ZERO;
			BigDecimal totOtherExp = BigDecimal.ZERO;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProj_name()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPlan_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalcost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWdf_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMgnrega_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOther_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getImpl_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNot_impl()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalexp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWdf_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMgnrega_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOther_exp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					totPlan = totPlan + list.get(i).getPlan_work();
			    	totCost = totCost.add(list.get(i).getTotalcost());
			    	totWdfCost = totWdfCost.add(list.get(i).getWdf_cost());
			    	totMgnregaCost = totMgnregaCost.add(list.get(i).getMgnrega_cost());
			    	totOtherCost = totOtherCost.add(list.get(i).getOther_cost());
			    	totImpl = totImpl + list.get(i).getImpl_work();
			    	totNotImpl = totNotImpl + list.get(i).getNot_impl();
			    	totExp = totExp.add(list.get(i).getTotalexp());
			    	totWdfExp = totWdfExp.add(list.get(i).getWdf_exp());
			    	totMgnregaExp = totMgnregaExp.add(list.get(i).getMgnrega_exp());
			    	totOtherExp = totOtherExp.add(list.get(i).getOther_exp());
			    	
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPlan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWdfCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMgnregaCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totOtherCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totImpl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totNotImpl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totWdfExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMgnregaExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totOtherExp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 13, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WP1 - Project.pdf");
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
