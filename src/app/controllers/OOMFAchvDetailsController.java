package app.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

import app.bean.Login;
import app.bean.OOMFAchvDetailsBean;
import app.common.CommonFunctions;
import app.service.CommonService;
import app.service.OOMFAchvDetailsService;

@Controller("OOMFAchvDetailsController")
public class OOMFAchvDetailsController 
{
	HttpSession session;

	@Autowired
	OOMFAchvDetailsService oomfAchvService;
	
	@Autowired(required = true)
	private CommonService commonService;
	
	@RequestMapping(value="/getOOMFAchvDetails", method = RequestMethod.GET)
	public ModelAndView getOOMFAchvRecord(HttpServletRequest request, HttpServletResponse response) 
	{
		ModelAndView mav = new ModelAndView("reports/OOMFAchvDetails");
		session = request.getSession(true);
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				
				mav.addObject("financialYear", commonService.getAllFinancialYear());
			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/getOOMFAchvDetails", method = RequestMethod.POST)
	public ModelAndView getOOMFAchvDetailsRecord(HttpServletRequest request, HttpServletResponse response) 
	{
		String finyr = request.getParameter("fincode"); 
		String finName = request.getParameter("finName");
		session = request.getSession(true);
		List<OOMFAchvDetailsBean> list = new ArrayList<OOMFAchvDetailsBean>();
		ModelAndView mav = new ModelAndView("reports/OOMFAchvDetails");
		
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				
				list = oomfAchvService.getOOMFAchvDetails(Integer.parseInt(finyr));
			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mav.addObject("finyr",finyr);
		mav.addObject("finName",finName);
		mav.addObject("oomfAchvList",list);
		mav.addObject("oomfAchvListSize",list.size());
		mav.addObject("financialYear", commonService.getAllFinancialYear());
		
		return mav;
	}
	
	
	@RequestMapping(value = "/downloadExcelOOMFAchvDetails", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelOOMFAchvDetails(HttpServletRequest request, HttpServletResponse response) 
	{
		String finyr = request.getParameter("fincode");
		String finName = request.getParameter("finName");
		session = request.getSession(true);
		List<OOMFAchvDetailsBean> list = new ArrayList<OOMFAchvDetailsBean>();

		list = oomfAchvService.getOOMFAchvDetails(Integer.parseInt(finyr));
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("ES1- State-wise OOMF Additional Achievement Entry Status");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "ES1- State-wise OOMF Additional Achievement Entry Status";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,5); 
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("Financial Year : "+ finName);  
		cell.setCellStyle(style);
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total No. of District");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total No. of Project");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Total No. of Project Submitted Half-Yearly Parameters Achievement");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Total No. of Project Submitted Year-wise Parameters Achievement");  
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<6;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 8;
		int totdist = 0;
		int totproj = 0;
		int totHalfYr = 0;
		int totYrWise = 0;
		
		
	    for(OOMFAchvDetailsBean bean: list) 
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getSt_name());
	    	row.createCell(2).setCellValue(bean.getTotaldist());
	    	row.createCell(3).setCellValue(bean.getTotalproject());
	    	row.createCell(4).setCellValue(bean.getHalfyearfill());
	    	row.createCell(5).setCellValue(bean.getYearwisefill());
	    	
	    	totdist = totdist + bean.getTotaldist();
	    	totproj = totproj + bean.getTotalproject();
	    	totHalfYr = totHalfYr + bean.getHalfyearfill();
	    	totYrWise = totYrWise + bean.getYearwisefill();

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
		cell.setCellValue(totdist);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totproj);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totHalfYr);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totYrWise);
		cell.setCellStyle(style1);

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 5);
	    String fileName = "attachment; filename=ES1- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/OOMFAchvDetails";
	}


	@RequestMapping(value = "/downloadPDFOOMFAchvDetails", method = RequestMethod.POST)
	public ModelAndView downloadPDFOOMFAchvDetails(HttpServletRequest request, HttpServletResponse response) 
	{
		session = request.getSession(true);
		String finyr = request.getParameter("fincode");
		String finName = request.getParameter("finName");
		
		List<OOMFAchvDetailsBean> list = new ArrayList<OOMFAchvDetailsBean>();

		list = oomfAchvService.getOOMFAchvDetails(Integer.parseInt(finyr));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("OOMFAchvDetailsReport");
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
			
			paragraph3 = new Paragraph("ES1- State-wise OOMF Additional Achievement Entry Status", f3);
				
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(6);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5});
		    table.setWidthPercentage(60);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(3);
							
		    CommonFunctions.insertCellHeader(table, "Financial Year : "+ finName, Element.ALIGN_LEFT, 6, 1, bf8Bold);
		        
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of District", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project Submitted Half-Yearly Parameters Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project Submitted Year-wise Parameters Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
		        
			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
			int k = 1;
			int totdist = 0;
			int totproj = 0;
			int totHalfYr = 0;
			int totYrWise = 0;

				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotaldist()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalproject()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getHalfyearfill()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getYearwisefill()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
				    	
				    totdist = totdist + list.get(i).getTotaldist();
				    totproj = totproj + list.get(i).getTotalproject();
				    totHalfYr = totHalfYr + list.get(i).getHalfyearfill();
				    totYrWise = totYrWise + list.get(i).getYearwisefill();
						
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totdist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totHalfYr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totYrWise), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 6, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=ES1- State.pdf");
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
