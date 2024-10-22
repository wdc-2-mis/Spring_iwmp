package app.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import app.PfmsTreasureBean;
import app.bean.CapicityBuildingTrainingBean;
import app.bean.NetTretatbleAreaBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.common.CommonFunctions;
import app.service.StateProfileWDCService;

@Controller("netTreatableAreaController")
public class NetTreatableAreaController {
	@Autowired(required = true)
	StateProfileWDCService stateProfileWDCService;
	
	@RequestMapping(value = "/netTretatble", method = RequestMethod.GET)
	public ModelAndView netTretatble(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		List<NetTretatbleAreaBean> list = new ArrayList<NetTretatbleAreaBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		ArrayList dataListNetTotal = new ArrayList();
		try {
		mav = new ModelAndView("reports/netTretatble");
		mav.addObject("netTreatledata",stateProfileWDCService.getnetTreatledata());
		dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "","" };
		dataArrNetTotal = new Integer[] { 0, 0, 0, 0 };
		for (List<NetTretatbleAreaBean> l : stateProfileWDCService.getnetTreatledata().values())
  
		for(NetTretatbleAreaBean bean : l){
			
			dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getNo_of_dist();
			dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getNo_of_block();
			dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getTot_no_micro_ws();
			dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(bean.getTot_geo_area_hec());
			dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(bean.getTot_untreat_area_hec());
			dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(bean.getArea_by_preiwmp_proj());
			dataArrNetTotalBD[3] = dataArrNetTotalBD[3].add(bean.getArea_by_other_ws());
			dataArrNetTotalBD[4] = dataArrNetTotalBD[4].add(bean.getIwmp_wdcpmksy_area());
			dataArrNetTotalBD[5] = dataArrNetTotalBD[5].add(bean.getArea_proposed());
			dataArrNetTotalBD[6] = dataArrNetTotalBD[6].add(bean.getTot_area_asur_irrig());
			dataArrNetTotalBD[7] = dataArrNetTotalBD[7].add(bean.getTotal());
			
		}
		dataListNetTotal = new ArrayList();
		dataArrNetTotalStr[0] = dataArrNetTotal[0].toString();
		dataArrNetTotalStr[1] = dataArrNetTotal[1].toString();
		dataArrNetTotalStr[2] = dataArrNetTotal[2].toString();
		dataArrNetTotalStr[3] = dataArrNetTotalBD[0].toString();
		dataArrNetTotalStr[4] = dataArrNetTotalBD[1].toString();
		dataArrNetTotalStr[5] = dataArrNetTotalBD[2].toString();
		dataArrNetTotalStr[6] = dataArrNetTotalBD[3].toString();
		dataArrNetTotalStr[7] = dataArrNetTotalBD[4].toString();
		dataArrNetTotalStr[8] = dataArrNetTotalBD[5].toString();
		dataArrNetTotalStr[9] = dataArrNetTotalBD[6].toString();
		dataArrNetTotalStr[10] = dataArrNetTotalBD[7].toString();
		
		dataListNetTotal.add(dataArrNetTotalStr);
		mav.addObject("dataListNetTotal", dataListNetTotal);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/downloadExcelNetTretatble", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelNetTretatble(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		List<NetTretatbleAreaBean> list = new ArrayList<NetTretatbleAreaBean>();
		for (List<NetTretatbleAreaBean> l : stateProfileWDCService.getnetTreatledata().values()) {
			for(NetTretatbleAreaBean bean : l){
				list.add(bean);
			}
		}
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report SB2- State-wise Number of Watersheds, Total Geographical Area and Net Treatable Area");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report SB2- State-wise Number of Watersheds, Total Geographical Area and Net Treatable Area";
		String areaAmtValDetail = "All amounts(in Rs Lakhs) And All Area in Lakh Ha.";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);

		
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
		sheet.addMergedRegion(mergedRegion);


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
		cell.setCellValue("Total no. of Districts");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("Total no. of Blocks");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("Total no. of Micro-watersheds");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("Total geographical area");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(6);
		cell.setCellValue("Total untreatable area*");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total Area covered under pre-IWMP schemes of DoLR");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Total Area covered under schemes of other Ministries");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(9);
		cell.setCellValue("Total Area covered under IWMP/WDC-PMKSY of DoLR (WDC 1.0)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Project Area proposed under WDC-PMKSY 2.0");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("Total area with assured irrigation");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(12);
		cell.setCellValue("Net treatable area(6-(7+8+9+10+11+12))");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i=0;i<13;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		

		int sno = 1;
		int rowno  = 7;
		int totDist = 0;
		int totBck = 0;
		int totMicroWatershed = 0;
		double totGeoArea = 0.0;
		double totUntreatArea = 0.0;
		double totAreaCovDoLR= 0.0;
		double totAreaCovOtherMinistry = 0.0;
		double totAreaCovDoLR1 = 0.0;
		double totAreaCovDoLR2 = 0.0;
		double totAreaAssIrr= 0.0;
		double totNetTreatArea= 0.0;
		
		
		for(NetTretatbleAreaBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getSt_name());
			row.createCell(2).setCellValue(bean.getNo_of_dist());  
			row.createCell(3).setCellValue(bean.getNo_of_block()); 
			row.createCell(4).setCellValue(bean.getTot_no_micro_ws()); 
			row.createCell(5).setCellValue(bean.getTot_geo_area_hec()==null?0.0:bean.getTot_geo_area_hec().doubleValue());
			row.createCell(6).setCellValue(bean.getTot_untreat_area_hec()==null?0.0:bean.getTot_untreat_area_hec().doubleValue());
			row.createCell(7).setCellValue(bean.getArea_by_preiwmp_proj()==null?0.0:bean.getArea_by_preiwmp_proj().doubleValue());
			row.createCell(8).setCellValue(bean.getArea_by_other_ws()==null?0.0:bean.getArea_by_other_ws().doubleValue());
			row.createCell(9).setCellValue(bean.getIwmp_wdcpmksy_area()==null?0.0:bean.getIwmp_wdcpmksy_area().doubleValue());
			row.createCell(10).setCellValue(bean.getArea_proposed()==null?0.0:bean.getArea_proposed().doubleValue());
			row.createCell(11).setCellValue(bean.getTot_area_asur_irrig()==null?0.0:bean.getTot_area_asur_irrig().doubleValue());
			row.createCell(12).setCellValue(bean.getTotal()==null?0.0:bean.getTotal().doubleValue());
			

			totDist = totDist + bean.getNo_of_dist();
			totBck = totBck + bean.getNo_of_block();
			totMicroWatershed = totMicroWatershed + bean.getTot_no_micro_ws();
			totGeoArea = totGeoArea + (bean.getTot_geo_area_hec()==null?0.0:bean.getTot_geo_area_hec().doubleValue());
			totUntreatArea = totUntreatArea + (bean.getTot_untreat_area_hec()==null?0.0:bean.getTot_untreat_area_hec().doubleValue());
			totAreaCovDoLR = totAreaCovDoLR + (bean.getArea_by_preiwmp_proj()==null?0.0:bean.getArea_by_preiwmp_proj().doubleValue());
			totAreaCovOtherMinistry = totAreaCovOtherMinistry + (bean.getArea_by_other_ws()==null?0.0:bean.getArea_by_other_ws().doubleValue());
			totAreaCovDoLR1 = totAreaCovDoLR1 + (bean.getIwmp_wdcpmksy_area()==null?0.0:bean.getIwmp_wdcpmksy_area().doubleValue());
			totAreaCovDoLR2 = totAreaCovDoLR2 + (bean.getArea_proposed()==null?0.0:bean.getArea_proposed().doubleValue());
			totAreaAssIrr = totAreaAssIrr + (bean.getTot_area_asur_irrig()==null?0.0:bean.getTot_area_asur_irrig().doubleValue());
			totNetTreatArea = totNetTreatArea + (bean.getTotal()==null?0.0:bean.getTotal().doubleValue());
			

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

		Row row = sheet.createRow(list.size()+7);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellValue(totDist);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totBck);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totMicroWatershed);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totGeoArea);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totUntreatArea);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totAreaCovDoLR);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totAreaCovOtherMinistry);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totAreaCovDoLR1);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totAreaCovDoLR2);
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totAreaAssIrr);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totNetTreatArea);
		cell.setCellStyle(style1);

		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
		String fileName = "attachment; filename=Report SB2- State.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/netTretatble";

	}

	
	@RequestMapping(value = "/downloadnetTretatbleReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadnetTretatbleReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		List<NetTretatbleAreaBean> list = new ArrayList<NetTretatbleAreaBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("netTretatbleReport");
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
			
				paragraph3 = new Paragraph("Report SB2- State-wise Number of Watersheds, Total Geographical Area and Net Treatable Area", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(13);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				int no_of_dist=0,no_of_block=0,tot_no_micro_ws=0;
				
				BigDecimal tot_geo_area_hec= BigDecimal.valueOf(0);
				BigDecimal tot_untreat_area_hec= BigDecimal.valueOf(0);
				BigDecimal area_by_preiwmp_proj= BigDecimal.valueOf(0);
				BigDecimal area_by_other_ws= BigDecimal.valueOf(0);
				BigDecimal iwmp_wdcpmksy_area= BigDecimal.valueOf(0);
				BigDecimal area_proposed= BigDecimal.valueOf(0);
				BigDecimal tot_area_asur_irrig= BigDecimal.valueOf(0);
				BigDecimal total= BigDecimal.valueOf(0);
			
				
				CommonFunctions.insertCellHeader(table,  "All area in Lakh ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 13, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of Districts ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of Blocks ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of Micro-watersheds ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total geographical area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total untreatable area*", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Area covered under pre-IWMP schemes of DoLR", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Area covered under schemes of other Ministries", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Area covered under IWMP/WDC-PMKSY of DoLR (WDC 1.0)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Projects Area proposed under WDC-PMKSY 2.0", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total area with assured irrigation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Net treatable area(6-(7+8+9+10+11+12))", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int k=1;
				
				for (List<NetTretatbleAreaBean> list1 : stateProfileWDCService.getnetTreatledata().values())
				    
					for(NetTretatbleAreaBean list2 : list1){
				
				if(list1.size()!=0)
					for(int i=0;i<list1.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list1.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list1.get(i).getNo_of_dist()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list1.get(i).getNo_of_block()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list1.get(i).getTot_no_micro_ws()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list1.get(i).getTot_geo_area_hec() == null ? "" : String.format(Locale.US,"%.4f",list1.get(i).getTot_geo_area_hec()),Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list1.get(i).getTot_untreat_area_hec() == null ? "" : String.format(Locale.US,"%.4f",list1.get(i).getTot_untreat_area_hec()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list1.get(i).getArea_by_preiwmp_proj() == null ? "" : String.format(Locale.US,"%.4f",list1.get(i).getArea_by_preiwmp_proj()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list1.get(i).getArea_by_other_ws() == null ? "" :  String.format(Locale.US,"%.4f",list1.get(i).getArea_by_other_ws()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list1.get(i).getIwmp_wdcpmksy_area() == null ? "" : String.format(Locale.US,"%.4f",list1.get(i).getIwmp_wdcpmksy_area()),Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list1.get(i).getArea_proposed() == null ? "" : String.format(Locale.US,"%.4f",list1.get(i).getArea_proposed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list1.get(i).getTot_area_asur_irrig() == null ? "" :String.format(Locale.US,"%.4f", list1.get(i).getTot_area_asur_irrig()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list1.get(i).getTotal() == null ? "" :  String.format(Locale.US,"%.4f",list1.get(i).getTotal()), Element.ALIGN_RIGHT, 1, 1, bf8);
						k=k+1;
						
						no_of_dist=no_of_dist+list1.get(i).getNo_of_dist();
						no_of_block=no_of_block+list1.get(i).getNo_of_block();
						tot_no_micro_ws=tot_no_micro_ws+list1.get(i).getTot_no_micro_ws();
						tot_geo_area_hec=tot_geo_area_hec.add(list1.get(i).getTot_geo_area_hec()==null?BigDecimal.ZERO:list1.get(i).getTot_geo_area_hec());
						tot_untreat_area_hec=tot_untreat_area_hec.add(list1.get(i).getTot_untreat_area_hec()==null?BigDecimal.ZERO:list1.get(i).getTot_untreat_area_hec());
						area_by_preiwmp_proj=area_by_preiwmp_proj.add(list1.get(i).getArea_by_preiwmp_proj()==null?BigDecimal.ZERO:list1.get(i).getArea_by_preiwmp_proj());
						area_by_other_ws=area_by_other_ws.add(list1.get(i).getArea_by_other_ws()==null?BigDecimal.ZERO:list1.get(i).getArea_by_other_ws());
						iwmp_wdcpmksy_area=iwmp_wdcpmksy_area.add(list1.get(i).getIwmp_wdcpmksy_area()==null?BigDecimal.ZERO:list1.get(i).getIwmp_wdcpmksy_area());
						area_proposed=area_proposed.add(list1.get(i).getArea_proposed()==null?BigDecimal.ZERO:list1.get(i).getArea_proposed());
						tot_area_asur_irrig=tot_area_asur_irrig.add(list1.get(i).getTot_area_asur_irrig()==null?BigDecimal.ZERO:list1.get(i).getTot_area_asur_irrig());
						total=total.add(list1.get(i).getTotal()==null?BigDecimal.ZERO:list1.get(i).getTotal());
					}
				}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_of_dist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_of_block), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(tot_no_micro_ws), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f",tot_geo_area_hec), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f",tot_untreat_area_hec), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f",area_by_preiwmp_proj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f",area_by_other_ws), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f",iwmp_wdcpmksy_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f",area_proposed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f",tot_area_asur_irrig), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f",total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(list.size()==0) {
//					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 13, 1, bf8);
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
		response.setHeader("Content-Disposition", "attachment;filename=SB2-Report.pdf");
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
