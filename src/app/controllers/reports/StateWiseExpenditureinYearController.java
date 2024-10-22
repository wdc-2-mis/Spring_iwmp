package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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

import app.PfmsTreasureBean;
import app.TargetAchievementQuarterBean;
import app.bean.pfms.PfmsStwiseExpndtreBean;
import app.common.CommonFunctions;
import app.service.CommonService;
import app.service.reports.TargetAchievementQuarterService;

@Controller("StateWiseExpenditureinYear")
public class StateWiseExpenditureinYearController {
	
	
	@Autowired(required = true)
	private CommonService commonService;
	
	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@RequestMapping(value="/getStateExpenditureReport", method = RequestMethod.GET)
	public ModelAndView getStateExpenditureReportOne(HttpServletRequest request, HttpServletResponse response)
	{
		
		ModelAndView mav = new ModelAndView();
		String year= request.getParameter("year");
			mav = new ModelAndView("reports/stateWiseExpenditureinYear");
			mav.addObject("financialYear", commonService.getAllFinancialYear());
			mav.addObject("year", year);
		return mav; 
	}
	
	@RequestMapping(value="/getStateExpenditureReport", method = RequestMethod.POST)
	public ModelAndView getStateExpenditureReport(HttpServletRequest request, HttpServletResponse response)
	{
	
			ModelAndView mav = new ModelAndView();
			
			String year= request.getParameter("year");
			String finName= request.getParameter("finName");
			
			String data[] = null;
			int i=1;
			List<String[]> dataList = new ArrayList<String[]>();
			ArrayList dataListNetTotal = new ArrayList();
			List<PfmsTreasureBean> list=new  ArrayList<PfmsTreasureBean>();
			mav = new ModelAndView("reports/stateWiseExpenditureinYear");
			String[] dataArrNetTotalStr = null;
			Integer[] dataArrNetTotal = null;
			BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
			list=ser.getStateExpenditureReport( Integer.parseInt(year));
			dataArrNetTotalStr = new String[] { "", "", "", ""};
			if(list != null) 
			{
				for(PfmsTreasureBean bean : list) 
				{
					dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(bean.getProject_cost());
					dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(bean.getCentralshare());
					dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(bean.getStateshare());
					dataArrNetTotalBD[3] = dataArrNetTotalBD[3].add(bean.getStateexpen());
				}
				dataListNetTotal = new ArrayList();
				dataArrNetTotalStr[0] = dataArrNetTotalBD[0].toString();
				dataArrNetTotalStr[1] = dataArrNetTotalBD[1].toString();
				dataArrNetTotalStr[2] = dataArrNetTotalBD[2].toString();
				dataArrNetTotalStr[3] = dataArrNetTotalBD[3].toString();
				
				dataListNetTotal.add(dataArrNetTotalStr);
				mav.addObject("dataListNetTotal", dataListNetTotal);
			}
			mav.addObject("dataList", list);
			mav.addObject("dataListsize", list.size());
			mav.addObject("financialYear", commonService.getAllFinancialYear());
			mav.addObject("year", year);
			mav.addObject("finName", finName);
		return mav; 
	}
	@RequestMapping(value = "/downloadStExpndtrPDF", method = RequestMethod.POST)
	public ModelAndView downloadStExpndtrPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String year= request.getParameter("year");
		String finName= request.getParameter("finName");
		
		List<PfmsTreasureBean> stBean = new ArrayList<PfmsTreasureBean>();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("StateExpenditureReport");
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
			
				paragraph3 = new Paragraph("Report PF4 - State wise Expenditure in a Financial Year " + " ' "+finName+" ' ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(6);
				table.setWidths(new int[] { 2, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				BigDecimal project_cost= BigDecimal.valueOf(0);
				BigDecimal centralshare= BigDecimal.valueOf(0);
				BigDecimal stateshare= BigDecimal.valueOf(0);
				BigDecimal stateexpen= BigDecimal.valueOf(0);
				
				stBean=ser.getStateExpenditureReport( Integer.parseInt(year));
				
				CommonFunctions.insertCellHeader(table,  "All amount (Rs. in lakh)", Element.ALIGN_RIGHT, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Sanctioned Amount ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Central Share Released From Treasury to SLNA", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Share Released From Treasury to SLNA", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Expenditure", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(stBean.size()!=0)
					for(int i=0;i<stBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, stBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, stBean.get(i).getProject_cost()==null?"": String.format(Locale.US, "%.2f",stBean.get(i).getProject_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, stBean.get(i).getCentralshare()==null?"": String.format(Locale.US, "%.2f",stBean.get(i).getCentralshare()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, stBean.get(i).getStateshare()==null?"": String.format(Locale.US, "%.2f",stBean.get(i).getStateshare()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, stBean.get(i).getStateexpen()==null?"": String.format(Locale.US, "%.2f", stBean.get(i).getStateexpen()), Element.ALIGN_RIGHT, 1, 1, bf8);
						k=k+1;
						
						project_cost=project_cost.add(stBean.get(i).getProject_cost()==null?BigDecimal.ZERO:stBean.get(i).getProject_cost());
						centralshare=centralshare.add(stBean.get(i).getCentralshare()==null?BigDecimal.ZERO:stBean.get(i).getCentralshare());
						stateshare=stateshare.add(stBean.get(i).getStateshare()==null?BigDecimal.ZERO:stBean.get(i).getStateshare());
						stateexpen=stateexpen.add(stBean.get(i).getStateexpen()==null?BigDecimal.ZERO:stBean.get(i).getStateexpen());
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",project_cost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",centralshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",stateshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",stateexpen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(stBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 6, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=PF4-Report.pdf");
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
	
	@RequestMapping(value = "/downloadExcelStateExpenditureReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStateExpenditureReport(HttpServletRequest request, HttpServletResponse response) 
	{
		String year= request.getParameter("year");
		String finName= request.getParameter("finName");
		
		List<PfmsTreasureBean> list = new ArrayList<PfmsTreasureBean>();
		
		list=ser.getStateExpenditureReport( Integer.parseInt(year));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF4- State wise Expenditure in a Financial Year");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF4- State wise Expenditure in a Financial Year "+"'"+finName+"'";
			String areaAmtValDetail = "All amount (Rs. in lakh)";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
			
        
	        mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Sanctioned Amount");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Central Share Released From Treasury to SLNA");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("State Share Released From Treasury to SLNA");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Total Expenditure");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i=0;i<6;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 7;
	        double totTotalSanAmt = 0;
	        double totCenShareRel = 0;
	        double totStateShareRel = 0;
	        double totTotalExpen = 0;
	        
	        for(PfmsTreasureBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getProject_cost()==null?0.0:bean.getProject_cost().doubleValue());
	        	row.createCell(3).setCellValue(bean.getCentralshare()==null?0.0:bean.getCentralshare().doubleValue());
	        	row.createCell(4).setCellValue(bean.getStateshare()==null?0.0:bean.getStateshare().doubleValue());
	        	row.createCell(5).setCellValue(bean.getStateexpen()==null?0.0:bean.getStateexpen().doubleValue());
	        	
	        	
	        	totTotalSanAmt = totTotalSanAmt + (bean.getProject_cost()==null?0.0:bean.getProject_cost().doubleValue());
	        	totCenShareRel = totCenShareRel + (bean.getCentralshare()==null?0.0:bean.getCentralshare().doubleValue());
	        	totStateShareRel = totStateShareRel + (bean.getStateshare()==null?0.0:bean.getStateshare().doubleValue());
	        	totTotalExpen = totTotalExpen + (bean.getStateexpen()==null?0.0:bean.getStateexpen().doubleValue());
				
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
	        cell.setCellValue(Math.round(totTotalSanAmt*100.0)/100.0);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(Math.round(totCenShareRel*100.0)/100.0);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(Math.round(totStateShareRel*100.0)/100.0);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(Math.round(totTotalExpen*100.0)/100.0);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 5);
	        String fileName = "attachment; filename=Report PF4- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/stateWiseExpenditureinYear";
		
	}

}
