package app.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
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

import app.bean.NRSCWorksBean;
import app.common.CommonFunctions;
import app.service.NRSCWorksService;

@Controller("NRSCWorksController")
public class NRSCWorksController {
	
@Autowired
NRSCWorksService nrscWorksService;

@RequestMapping(value = "/getNRSCWorks", method = RequestMethod.GET)
public ModelAndView getNRSCWorksRecord(HttpServletRequest request) {
	
	List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
	ModelAndView mav= new ModelAndView();
	 mav = new ModelAndView("reports/NRSCWorks");
		list = nrscWorksService.getNRSCWorks();
		
			mav.addObject("nrscWorksList",list);
			mav.addObject("nrscWorksListSize",list.size());
	return mav;
	}

@RequestMapping(value = "/getNRSCDistWorks", method = RequestMethod.GET)
public ModelAndView getNRSCDistWorksRecord(HttpServletRequest request) {
	
	String stcd = request.getParameter("stcd");
	String stname = request.getParameter("stname");
	List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
	ModelAndView mav = new ModelAndView("reports/NRSCDistWorks");
	list = nrscWorksService.getNRSCDistWorks(Integer.parseInt(stcd));
	
	mav.addObject("stcd",stcd);
	mav.addObject("stname",stname);
	mav.addObject("nrscDistWorksList",list);
	mav.addObject("nrscDistWorksListSize",list.size());
	
	return mav;
}

@RequestMapping(value = "/getNRSCProjWorks", method = RequestMethod.GET)
public ModelAndView getNRSCProjWorksRecord(HttpServletRequest request) {
	
	String dcode = request.getParameter("dcode");
	String dname = request.getParameter("dname");
	String stname = request.getParameter("stname");
	
	List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
	ModelAndView mav = new ModelAndView("reports/NRSCProjWorks");
	list = nrscWorksService.getNRSCProjWorks(Integer.parseInt(dcode));
	
	mav.addObject("dcode",dcode);
	mav.addObject("dname",dname);
	mav.addObject("stname",stname);
	mav.addObject("nrscProjWorksList",list);
	mav.addObject("nrscProjWorksListSize",list.size());
	
	return mav;
}


@RequestMapping(value = "/downloadExcelNRSCWorks", method = RequestMethod.POST)
@ResponseBody
public String downloadExcelNRSCWorks(HttpServletRequest request, HttpServletResponse response) 
{
	
	List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
	
	list = nrscWorksService.getNRSCWorks();
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("Report GT1- Total No. of Work Code Shared with NRSC for Geotagging");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "Report GT1- Total No. of Work Code Shared with NRSC for Geotagging";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 27, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	sheet.addMergedRegion(mergedRegion);
	
//	mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,4); 
//	sheet.addMergedRegion(mergedRegion);
//	
//	mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,5,7); 
//	sheet.addMergedRegion(mergedRegion);
	
	mergedRegion = new CellRangeAddress(5,5,3,7);
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,5,8,12);
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,5,13,17);
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,5,18,22);
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,5,23,27);
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,0,0); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,1,1); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,2,2); 
    sheet.addMergedRegion(mergedRegion);
    
	
	Row rowhead = sheet.createRow(5);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("State Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total Projects");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Total NRM Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=4;i<8;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(8);
	cell.setCellValue("Total EPA Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=9;i<13;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(13);
	cell.setCellValue("Total Livelihood Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=14;i<18;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(18);
	cell.setCellValue("Total Production Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=19;i<23;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(23);
	cell.setCellValue("Grand Total (NRM+EPA+LIV+PROD)");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=24;i<28;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	
	
	Row rowhead1 = sheet.createRow(6);
	
	for(int i=0;i<3;i++)
	{
		cell = rowhead1.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead1.createCell(3);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(4);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(5);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(6);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(7);
	cell.setCellValue("Foreclosed");  
	cell.setCellStyle(style);
	
	
	cell = rowhead1.createCell(8);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(9);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(10);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(11);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(12);
	cell.setCellValue("Foreclosed");  
	cell.setCellStyle(style);
	
	
	cell = rowhead1.createCell(13);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(14);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(15);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(16);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(17);
	cell.setCellValue("Foreclosed");  
	cell.setCellStyle(style);
	
	
	cell = rowhead1.createCell(18);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(19);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(20);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(21);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(22);
	cell.setCellValue("Foreclosed");  
	cell.setCellStyle(style);
	
	
	cell = rowhead1.createCell(23);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(24);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(25);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(26);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(27);
	cell.setCellValue("Foreclosed");  
	cell.setCellStyle(style);
	

	
	Row rowhead2 = sheet.createRow(7);
	
	for(int i=0;i<28;i++)
	{
		cell =rowhead2.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno  = 8;
	int totProj = 0;
	int totCreated_N = 0;
	int totUnStarted_N = 0;
	int totOngoing_N = 0;
	int totCompleted_N = 0;
	int totForClosed_N = 0;
	int totCreated_E = 0;
	int totUnStarted_E = 0;
	int totOngoing_E = 0;
	int totCompleted_E = 0;
	int totForClosed_E = 0;
	int totCreated_L = 0;
	int totUnStarted_L = 0;
	int totOngoing_L = 0;
	int totCompleted_L = 0;
	int totForClosed_L = 0;
	int totCreated_P = 0;
	int totUnStarted_P = 0;
	int totOngoing_P = 0;
	int totCompleted_P = 0;
	int totForClosed_P = 0;
	int totCreated_T = 0;
	int totUnStarted_T = 0;
	int totOngoing_T = 0;
	int totCompleted_T = 0;
	int totForClosed_T = 0;
	int netTotal = 0;
	
    for(NRSCWorksBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getSt_name());
    	row.createCell(2).setCellValue(bean.getProjects());
    	
    	row.createCell(3).setCellValue(bean.getN_created());
    	row.createCell(4).setCellValue(bean.getN_unstarted());
    	row.createCell(5).setCellValue(bean.getN_ongoing());
    	row.createCell(6).setCellValue(bean.getN_completed());
    	row.createCell(7).setCellValue(bean.getN_forclosed());
    	
    	row.createCell(8).setCellValue(bean.getE_created());
    	row.createCell(9).setCellValue(bean.getE_unstarted());
    	row.createCell(10).setCellValue(bean.getE_ongoing());
    	row.createCell(11).setCellValue(bean.getE_completed());
    	row.createCell(12).setCellValue(bean.getE_forclosed());
    	
    	row.createCell(13).setCellValue(bean.getL_created());
    	row.createCell(14).setCellValue(bean.getL_unstarted());
    	row.createCell(15).setCellValue(bean.getL_ongoing());
    	row.createCell(16).setCellValue(bean.getL_completed());
    	row.createCell(17).setCellValue(bean.getL_forclosed());
    	
    	row.createCell(18).setCellValue(bean.getP_created());
    	row.createCell(19).setCellValue(bean.getP_unstarted());
    	row.createCell(20).setCellValue(bean.getP_ongoing());
    	row.createCell(21).setCellValue(bean.getP_completed());
    	row.createCell(22).setCellValue(bean.getP_forclosed());
    	
    	row.createCell(23).setCellValue(bean.getT_created());
    	row.createCell(24).setCellValue(bean.getT_unstarted());
    	row.createCell(25).setCellValue(bean.getT_ongoing());
    	row.createCell(26).setCellValue(bean.getT_completed());
    	row.createCell(27).setCellValue(bean.getT_forclosed());
    	
    	
    	totProj = totProj + bean.getProjects();
    	
    	totCreated_N = totCreated_N + bean.getN_created();
    	totUnStarted_N = totUnStarted_N + bean.getN_unstarted();
    	totOngoing_N = totOngoing_N + bean.getN_ongoing();
    	totCompleted_N = totCompleted_N + bean.getN_completed();
    	totForClosed_N = totForClosed_N + bean.getN_forclosed();
    	
    	totCreated_E = totCreated_E + bean.getE_created();
    	totUnStarted_E = totUnStarted_E + bean.getE_unstarted();
    	totOngoing_E = totOngoing_E + bean.getE_ongoing();
    	totCompleted_E = totCompleted_E + bean.getE_completed();
    	totForClosed_E = totForClosed_E + bean.getE_forclosed();
    	
    	totCreated_L = totCreated_L + bean.getL_created();
    	totUnStarted_L = totUnStarted_L + bean.getL_unstarted();
    	totOngoing_L = totOngoing_L + bean.getL_ongoing();
    	totCompleted_L = totCompleted_L + bean.getL_completed();
    	totForClosed_L = totForClosed_L + bean.getL_forclosed();
    	
    	totCreated_P = totCreated_P + bean.getP_created();
    	totUnStarted_P = totUnStarted_P + bean.getP_unstarted();
    	totOngoing_P = totOngoing_P + bean.getP_ongoing();
    	totCompleted_P = totCompleted_P + bean.getP_completed();
    	totForClosed_P = totForClosed_P + bean.getP_forclosed();
    	
    	totCreated_T = totCreated_T + bean.getT_created();
    	totUnStarted_T = totUnStarted_T + bean.getT_unstarted();
    	totOngoing_T = totOngoing_T + bean.getT_ongoing();
    	totCompleted_T = totCompleted_T + bean.getT_completed();
    	totForClosed_T = totForClosed_T + bean.getT_forclosed();
    	
    	sno++;
    	rowno++;
    }
    
//    netTotal = netTotal+totCreated_N+totCreated_E+totCreated_L+totCreated_P;
    
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
	cell.setCellValue("Total");
	cell.setCellStyle(style1);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	cell = row.createCell(1);
	cell.setCellStyle(style1);
	cell = row.createCell(2);
	cell.setCellValue(totProj);
	cell.setCellStyle(style1);
	cell = row.createCell(3);
	cell.setCellValue(totCreated_N);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totUnStarted_N);
	cell.setCellStyle(style1);
	cell = row.createCell(5);
	cell.setCellValue(totOngoing_N);
	cell.setCellStyle(style1);
	cell = row.createCell(6);
	cell.setCellValue(totCompleted_N);
	cell.setCellStyle(style1);
	cell = row.createCell(7);
	cell.setCellValue(totForClosed_N);
	cell.setCellStyle(style1);
	cell = row.createCell(8);
	cell.setCellValue(totCreated_E);
	cell.setCellStyle(style1);
	cell = row.createCell(9);
	cell.setCellValue(totUnStarted_E);
	cell.setCellStyle(style1);
	cell = row.createCell(10);
	cell.setCellValue(totOngoing_E);
	cell.setCellStyle(style1);
	cell = row.createCell(11);
	cell.setCellValue(totCompleted_E);
	cell.setCellStyle(style1);
	cell = row.createCell(12);
	cell.setCellValue(totForClosed_E);
	cell.setCellStyle(style1);
	cell = row.createCell(13);
	cell.setCellValue(totCreated_L);
	cell.setCellStyle(style1);
	cell = row.createCell(14);
	cell.setCellValue(totUnStarted_L);
	cell.setCellStyle(style1);
	cell = row.createCell(15);
	cell.setCellValue(totOngoing_L);
	cell.setCellStyle(style1);
	cell = row.createCell(16);
	cell.setCellValue(totCompleted_L);
	cell.setCellStyle(style1);
	cell = row.createCell(17);
	cell.setCellValue(totForClosed_L);
	cell.setCellStyle(style1);
	cell = row.createCell(18);
	cell.setCellValue(totCreated_P);
	cell.setCellStyle(style1);
	cell = row.createCell(19);
	cell.setCellValue(totUnStarted_P);
	cell.setCellStyle(style1);
	cell = row.createCell(20);
	cell.setCellValue(totOngoing_P);
	cell.setCellStyle(style1);
	cell = row.createCell(21);
	cell.setCellValue(totCompleted_P);
	cell.setCellStyle(style1);
	cell = row.createCell(22);
	cell.setCellValue(totForClosed_P);
	cell.setCellStyle(style1);
	cell = row.createCell(23);
	cell.setCellValue(totCreated_T);
	cell.setCellStyle(style1);
	cell = row.createCell(24);
	cell.setCellValue(totUnStarted_T);
	cell.setCellStyle(style1);
	cell = row.createCell(25);
	cell.setCellValue(totOngoing_T);
	cell.setCellStyle(style1);
	cell = row.createCell(26);
	cell.setCellValue(totCompleted_T);
	cell.setCellStyle(style1);
	cell = row.createCell(27);
	cell.setCellValue(totForClosed_T);
	cell.setCellStyle(style1);
	
//	Row row1 = sheet.createRow(list.size()+9);
//	cell = row1.createCell(0);
//	cell.setCellValue("Total Works");
//	cell.setCellStyle(style1);
//	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
//	for(int i=1;i<5;i++) 
//	{
//		cell = row1.createCell(i);
//		cell.setCellStyle(style1);
//	}
//	cell = row1.createCell(5);
//	cell.setCellValue(netTotal);
//	cell.setCellStyle(style1);
//	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
//	for(int i=6;i<8;i++) 
//	{
//		cell = row1.createCell(i);
//		cell.setCellStyle(style1);
//	}

    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 27);
    String fileName = "attachment; filename=Report GT1- State.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/NRSCWorks";
}

@RequestMapping(value = "/downloadPDFNRSCWorks", method = RequestMethod.POST)
public ModelAndView downloadPDFNRSCWorks(HttpServletRequest request, HttpServletResponse response) 
{
	
	List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
	
	list = nrscWorksService.getNRSCWorks();
	
	try {
		
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("NRSCWorksReport");
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
		
			paragraph3 = new Paragraph("Report GT1- Total No. of Work Code Shared with NRSC for Geotagging", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(28);
	        table.setWidths(new int[]{3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);
			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Projects", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Total NRM Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total EPA Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Livelihood Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Production Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Grand Total (NRM+EPA+LIV+PROD)", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			
			
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
	        
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

			
			int k = 1;
			int totProj = 0;
			int totCreated_N = 0;
			int totUnStarted_N = 0;
			int totOngoing_N = 0;
			int totCompleted_N = 0;
			int totForClosed_N = 0;
			int totCreated_E = 0;
			int totUnStarted_E = 0;
			int totOngoing_E = 0;
			int totCompleted_E = 0;
			int totForClosed_E = 0;
			int totCreated_L = 0;
			int totUnStarted_L = 0;
			int totOngoing_L = 0;
			int totCompleted_L = 0;
			int totForClosed_L = 0;
			int totCreated_P = 0;
			int totUnStarted_P = 0;
			int totOngoing_P = 0;
			int totCompleted_P = 0;
			int totForClosed_P = 0;
			int totCreated_T = 0;
			int totUnStarted_T = 0;
			int totOngoing_T = 0;
			int totCompleted_T = 0;
			int totForClosed_T = 0;
		    int netTotal = 0;
			
			
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProjects()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					
					totProj = totProj + list.get(i).getProjects();
					
					totCreated_N = totCreated_N + list.get(i).getN_created();
					totUnStarted_N = totUnStarted_N + list.get(i).getN_unstarted();
					totOngoing_N = totOngoing_N + list.get(i).getN_ongoing();
					totCompleted_N = totCompleted_N + list.get(i).getN_completed();
					totForClosed_N = totForClosed_N + list.get(i).getN_forclosed();
					
					totCreated_E = totCreated_E + list.get(i).getE_created();
					totUnStarted_E = totUnStarted_E + list.get(i).getE_unstarted();
					totOngoing_E = totOngoing_E + list.get(i).getE_ongoing();
					totCompleted_E = totCompleted_E + list.get(i).getE_completed();
					totForClosed_E = totForClosed_E + list.get(i).getE_forclosed();
					
					totCreated_L = totCreated_L + list.get(i).getL_created();
					totUnStarted_L = totUnStarted_L + list.get(i).getL_unstarted();
					totOngoing_L = totOngoing_L + list.get(i).getL_ongoing();
					totCompleted_L = totCompleted_L + list.get(i).getL_completed();
					totForClosed_L = totForClosed_L + list.get(i).getL_forclosed();
					
					totCreated_P = totCreated_P + list.get(i).getP_created();
					totUnStarted_P = totUnStarted_P + list.get(i).getP_unstarted();
					totOngoing_P = totOngoing_P + list.get(i).getP_ongoing();
					totCompleted_P = totCompleted_P + list.get(i).getP_completed();
					totForClosed_P = totForClosed_P + list.get(i).getP_forclosed();
					
					totCreated_T = totCreated_T + list.get(i).getT_created();
					totUnStarted_T = totUnStarted_T + list.get(i).getT_unstarted();
					totOngoing_T = totOngoing_T + list.get(i).getT_ongoing();
					totCompleted_T = totCompleted_T + list.get(i).getT_completed();
					totForClosed_T = totForClosed_T + list.get(i).getT_forclosed();
					
					k++;
				}
				
//			netTotal = netTotal+totCreated_N+totCreated_E+totCreated_L+totCreated_P;
			
			CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totProj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			
			CommonFunctions.insertCell3(table, String.valueOf(totCreated_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totForClosed_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            
            CommonFunctions.insertCell3(table, String.valueOf(totCreated_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totForClosed_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            
            CommonFunctions.insertCell3(table, String.valueOf(totCreated_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totForClosed_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            
            CommonFunctions.insertCell3(table, String.valueOf(totCreated_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totForClosed_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            
            CommonFunctions.insertCell3(table, String.valueOf(totCreated_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            CommonFunctions.insertCell3(table, String.valueOf(totForClosed_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
            
//            CommonFunctions.insertCell3(table, "Total Works", Element.ALIGN_RIGHT, 5, 1, bf10Bold);
//            CommonFunctions.insertCell3(table, String.valueOf(netTotal), Element.ALIGN_LEFT, 3, 1, bf10Bold);
			
			
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 28, 1, bf8);
			
			
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
	response.setHeader("Content-Disposition", "attachment;filename=Report GT1- State.pdf");
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

@RequestMapping(value = "/downloadExcelNRSCDistWorks", method = RequestMethod.POST)
@ResponseBody
public String downloadExcelNRSCDistWorks(HttpServletRequest request, HttpServletResponse response)
{
	String stcd = request.getParameter("stcd");
	String stname = request.getParameter("stname");
	
	List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
	
	list = nrscWorksService.getNRSCDistWorks(Integer.parseInt(stcd));
	
	Workbook workbook = new XSSFWorkbook();
	//invoking createSheet() method and passing the name of the sheet to be created
	Sheet sheet = workbook.createSheet("Report GT1- District-wise Total No. of Work Code Shared with NRSC for Geotagging");
	
	CellStyle style = CommonFunctions.getStyle(workbook);
	
	String rptName = "Report GT1- District-wise Total No. of Work Code Shared with NRSC for Geotagging for State '"+stname+"'";
	String areaAmtValDetail = "";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 27, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1);
	sheet.addMergedRegion(mergedRegion);
	
//	mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,4);
//	sheet.addMergedRegion(mergedRegion);
//	
//	mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,5,7);
//	sheet.addMergedRegion(mergedRegion);
	
	mergedRegion = new CellRangeAddress(5,5,3,7);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,8,12);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,13,17);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,18,22);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,23,27);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,6,0,0);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,6,1,1);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,6,2,2);
	sheet.addMergedRegion(mergedRegion);
	
	
	
	Row rowhead = sheet.createRow(5);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("District Name");
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total Projects");
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Total NRM Works");
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=4;i<8;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(8);
	cell.setCellValue("Total EPA Works");
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=9;i<13;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(13);
	cell.setCellValue("Total Livelihood Works");
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=14;i<18;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(18);
	cell.setCellValue("Total Production Works");
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=19;i<23;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(23);
	cell.setCellValue("Grand Total (NRM+EPA+LIV+PROD)");
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=24;i<28;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	
	
	Row rowhead1 = sheet.createRow(6);
	
	for(int i=0;i<3;i++)
	{
		cell = rowhead1.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead1.createCell(3);
	cell.setCellValue("Total Works");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(4);
	cell.setCellValue("Not Started (Status Not Submitted)");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(5);
	cell.setCellValue("Ongoing");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(6);
	cell.setCellValue("Completed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(7);
	cell.setCellValue("Foreclosed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(8);
	cell.setCellValue("Total Works");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(9);
	cell.setCellValue("Not Started (Status Not Submitted)");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(10);
	cell.setCellValue("Ongoing");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(11);
	cell.setCellValue("Completed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(12);
	cell.setCellValue("Foreclosed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(13);
	cell.setCellValue("Total Works");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(14);
	cell.setCellValue("Not Started (Status Not Submitted)");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(15);
	cell.setCellValue("Ongoing");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(16);
	cell.setCellValue("Completed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(17);
	cell.setCellValue("Foreclosed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(18);
	cell.setCellValue("Total Works");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(19);
	cell.setCellValue("Not Started (Status Not Submitted)");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(20);
	cell.setCellValue("Ongoing");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(21);
	cell.setCellValue("Completed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(22);
	cell.setCellValue("Foreclosed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(23);
	cell.setCellValue("Total Works");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(24);
	cell.setCellValue("Not Started (Status Not Submitted)");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(25);
	cell.setCellValue("Ongoing");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(26);
	cell.setCellValue("Completed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(27);
	cell.setCellValue("Foreclosed");
	cell.setCellStyle(style);
	
	
	
	Row rowhead2 = sheet.createRow(7);
	
	for(int i=0;i<28;i++)
	{
		cell = rowhead2.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno = 8;
	int totProj = 0;
	int totCreated_N = 0;
	int totUnStarted_N = 0;
	int totOngoing_N = 0;
	int totCompleted_N = 0;
	int totForClosed_N = 0;
	int totCreated_E = 0;
	int totUnStarted_E = 0;
	int totOngoing_E = 0;
	int totCompleted_E = 0;
	int totForClosed_E = 0;
	int totCreated_L = 0;
	int totUnStarted_L = 0;
	int totOngoing_L = 0;
	int totCompleted_L = 0;
	int totForClosed_L = 0;
	int totCreated_P = 0;
	int totUnStarted_P = 0;
	int totOngoing_P = 0;
	int totCompleted_P = 0;
	int totForClosed_P = 0;
	int totCreated_T = 0;
	int totUnStarted_T = 0;
	int totOngoing_T = 0;
	int totCompleted_T = 0;
	int totForClosed_T = 0;
	int netTotal = 0;
	
	for(NRSCWorksBean bean : list) {
		Row row = sheet.createRow(rowno);
		row.createCell(0).setCellValue(sno);
		row.createCell(1).setCellValue(bean.getDist_name());
		row.createCell(2).setCellValue(bean.getProjects());
		row.createCell(3).setCellValue(bean.getN_created());
		row.createCell(4).setCellValue(bean.getN_unstarted());
		row.createCell(5).setCellValue(bean.getN_ongoing());
		row.createCell(6).setCellValue(bean.getN_completed());
		row.createCell(7).setCellValue(bean.getN_forclosed());
		row.createCell(8).setCellValue(bean.getE_created());
		row.createCell(9).setCellValue(bean.getE_unstarted());
		row.createCell(10).setCellValue(bean.getE_ongoing());
		row.createCell(11).setCellValue(bean.getE_completed());
		row.createCell(12).setCellValue(bean.getE_forclosed());
		row.createCell(13).setCellValue(bean.getL_created());
		row.createCell(14).setCellValue(bean.getL_unstarted());
		row.createCell(15).setCellValue(bean.getL_ongoing());
		row.createCell(16).setCellValue(bean.getL_completed());
		row.createCell(17).setCellValue(bean.getL_forclosed());
		row.createCell(18).setCellValue(bean.getP_created());
		row.createCell(19).setCellValue(bean.getP_unstarted());
		row.createCell(20).setCellValue(bean.getP_ongoing());
		row.createCell(21).setCellValue(bean.getP_completed());
		row.createCell(22).setCellValue(bean.getP_forclosed());
		row.createCell(23).setCellValue(bean.getT_created());
		row.createCell(24).setCellValue(bean.getT_unstarted());
		row.createCell(25).setCellValue(bean.getT_ongoing());
		row.createCell(26).setCellValue(bean.getT_completed());
		row.createCell(27).setCellValue(bean.getT_forclosed());
		
		
		
		totProj = totProj + bean.getProjects();
    	
    	totCreated_N = totCreated_N + bean.getN_created();
    	totUnStarted_N = totUnStarted_N + bean.getN_unstarted();
    	totOngoing_N = totOngoing_N + bean.getN_ongoing();
    	totCompleted_N = totCompleted_N + bean.getN_completed();
    	totForClosed_N = totForClosed_N + bean.getN_forclosed();
    	
    	totCreated_E = totCreated_E + bean.getE_created();
    	totUnStarted_E = totUnStarted_E + bean.getE_unstarted();
    	totOngoing_E = totOngoing_E + bean.getE_ongoing();
    	totCompleted_E = totCompleted_E + bean.getE_completed();
    	totForClosed_E = totForClosed_E + bean.getE_forclosed();
    	
    	totCreated_L = totCreated_L + bean.getL_created();
    	totUnStarted_L = totUnStarted_L + bean.getL_unstarted();
    	totOngoing_L = totOngoing_L + bean.getL_ongoing();
    	totCompleted_L = totCompleted_L + bean.getL_completed();
    	totForClosed_L = totForClosed_L + bean.getL_forclosed();
    	
    	totCreated_P = totCreated_P + bean.getP_created();
    	totUnStarted_P = totUnStarted_P + bean.getP_unstarted();
    	totOngoing_P = totOngoing_P + bean.getP_ongoing();
    	totCompleted_P = totCompleted_P + bean.getP_completed();
    	totForClosed_P = totForClosed_P + bean.getP_forclosed();
    	
    	totCreated_T = totCreated_T + bean.getT_created();
    	totUnStarted_T = totUnStarted_T + bean.getT_unstarted();
    	totOngoing_T = totOngoing_T + bean.getT_ongoing();
    	totCompleted_T = totCompleted_T + bean.getT_completed();
    	totForClosed_T = totForClosed_T + bean.getT_forclosed();
		
		sno++;
		rowno++;
	}
	
//	netTotal = netTotal+totCreated_N+totCreated_E+totCreated_L+totCreated_P;
	
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
//	font1.setColor(IndexedColors.WHITE.getIndex());
	style1.setFont(font1);
	
	Row row = sheet.createRow(list.size()+8);
	cell = row.createCell(0);
	cell.setCellValue("Total");
	cell.setCellStyle(style1);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	cell = row.createCell(1);
	cell.setCellStyle(style1);
	cell = row.createCell(2);
	cell.setCellValue(totProj);
	cell.setCellStyle(style1);
	cell = row.createCell(3);
	cell.setCellValue(totCreated_N);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totUnStarted_N);
	cell.setCellStyle(style1);
	cell = row.createCell(5);
	cell.setCellValue(totOngoing_N);
	cell.setCellStyle(style1);
	cell = row.createCell(6);
	cell.setCellValue(totCompleted_N);
	cell.setCellStyle(style1);
	cell = row.createCell(7);
	cell.setCellValue(totForClosed_N);
	cell.setCellStyle(style1);
	cell = row.createCell(8);
	cell.setCellValue(totCreated_E);
	cell.setCellStyle(style1);
	cell = row.createCell(9);
	cell.setCellValue(totUnStarted_E);
	cell.setCellStyle(style1);
	cell = row.createCell(10);
	cell.setCellValue(totOngoing_E);
	cell.setCellStyle(style1);
	cell = row.createCell(11);
	cell.setCellValue(totCompleted_E);
	cell.setCellStyle(style1);
	cell = row.createCell(12);
	cell.setCellValue(totForClosed_E);
	cell.setCellStyle(style1);
	cell = row.createCell(13);
	cell.setCellValue(totCreated_L);
	cell.setCellStyle(style1);
	cell = row.createCell(14);
	cell.setCellValue(totUnStarted_L);
	cell.setCellStyle(style1);
	cell = row.createCell(15);
	cell.setCellValue(totOngoing_L);
	cell.setCellStyle(style1);
	cell = row.createCell(16);
	cell.setCellValue(totCompleted_L);
	cell.setCellStyle(style1);
	cell = row.createCell(17);
	cell.setCellValue(totForClosed_L);
	cell.setCellStyle(style1);
	cell = row.createCell(18);
	cell.setCellValue(totCreated_P);
	cell.setCellStyle(style1);
	cell = row.createCell(19);
	cell.setCellValue(totUnStarted_P);
	cell.setCellStyle(style1);
	cell = row.createCell(20);
	cell.setCellValue(totOngoing_P);
	cell.setCellStyle(style1);
	cell = row.createCell(21);
	cell.setCellValue(totCompleted_P);
	cell.setCellStyle(style1);
	cell = row.createCell(22);
	cell.setCellValue(totForClosed_P);
	cell.setCellStyle(style1);
	cell = row.createCell(23);
	cell.setCellValue(totCreated_T);
	cell.setCellStyle(style1);
	cell = row.createCell(24);
	cell.setCellValue(totUnStarted_T);
	cell.setCellStyle(style1);
	cell = row.createCell(25);
	cell.setCellValue(totOngoing_T);
	cell.setCellStyle(style1);
	cell = row.createCell(26);
	cell.setCellValue(totCompleted_T);
	cell.setCellStyle(style1);
	cell = row.createCell(27);
	cell.setCellValue(totForClosed_T);
	cell.setCellStyle(style1);
	
	
//	Row row1 = sheet.createRow(list.size()+9);
//	cell = row1.createCell(0);
//	cell.setCellValue("Total Works");
//	cell.setCellStyle(style1);
//	CellUtil.setCellStyleProperty(cell,  CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
//	for(int i=1;i<5;i++) {
//		cell = row1.createCell(i);
//		cell.setCellStyle(style1);
//	}
//	cell = row1.createCell(5);
//	cell.setCellValue(netTotal);
//	cell.setCellStyle(style1);
//	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
//	for(int i=6;i<8;i++) {
//		cell = row1.createCell(i);
//		cell.setCellStyle(style1);
//	}
	
	CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 27);
	String fileName = "attachment; filename=Report GT1- District.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/NRSCDistWorks";
}

@RequestMapping(value = "/downloadPDFNRSCDistWorks", method = RequestMethod.POST)
public ModelAndView downloadPDFNRSCDistWorks(HttpServletRequest request, HttpServletResponse response)
{
	String stcd = request.getParameter("stcd");
	String stname = request.getParameter("stname");
	
	List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
	
	list = nrscWorksService.getNRSCDistWorks(Integer.parseInt(stcd));
	
	try {
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
        layout.setBackgroundColor(new BaseColor(255, 255, 255));
        Document document = new Document(layout, 25, 10, 10, 0);
        document.addTitle("NRSCDistrictWorksReport");
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
		
			paragraph3 = new Paragraph("Report GT1- District-wise Total No. of Work Code Shared with NRSC for Geotagging for State '"+stname+"'", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(28);
	        table.setWidths(new int[]{3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);
			
	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Projects", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Total NRM Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total EPA Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Livelihood Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Production Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Grand Total (NRM+EPA+LIV+PROD)", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
	        
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

			
		int k = 1;
		int totProj = 0;
		int totCreated_N = 0;
		int totUnStarted_N = 0;
		int totOngoing_N = 0;
		int totCompleted_N = 0;
		int totForClosed_N = 0;
		int totCreated_E = 0;
		int totUnStarted_E = 0;
		int totOngoing_E = 0;
		int totCompleted_E = 0;
		int totForClosed_E = 0;
		int totCreated_L = 0;
		int totUnStarted_L = 0;
		int totOngoing_L = 0;
		int totCompleted_L = 0;
		int totForClosed_L = 0;
		int totCreated_P = 0;
		int totUnStarted_P = 0;
		int totOngoing_P = 0;
		int totCompleted_P = 0;
		int totForClosed_P = 0;
		int totCreated_T = 0;
		int totUnStarted_T = 0;
		int totOngoing_T = 0;
		int totCompleted_T = 0;
		int totForClosed_T = 0;
		int netTotal = 0;
		
		if(list.size()!=0)
			for(int i=0;i<list.size();i++)
			{
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProjects()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				
				totProj = totProj + list.get(i).getProjects();
				
				totCreated_N = totCreated_N + list.get(i).getN_created();
				totUnStarted_N = totUnStarted_N + list.get(i).getN_unstarted();
				totOngoing_N = totOngoing_N + list.get(i).getN_ongoing();
				totCompleted_N = totCompleted_N + list.get(i).getN_completed();
				totForClosed_N = totForClosed_N + list.get(i).getN_forclosed();

				totCreated_E = totCreated_E + list.get(i).getE_created();
				totUnStarted_E = totUnStarted_E + list.get(i).getE_unstarted();
				totOngoing_E = totOngoing_E + list.get(i).getE_ongoing();
				totCompleted_E = totCompleted_E + list.get(i).getE_completed();
				totForClosed_E = totForClosed_E + list.get(i).getE_forclosed();
				
				totCreated_L = totCreated_L + list.get(i).getL_created();
				totUnStarted_L = totUnStarted_L + list.get(i).getL_unstarted();
				totOngoing_L = totOngoing_L + list.get(i).getL_ongoing();
				totCompleted_L = totCompleted_L + list.get(i).getL_completed();
				totForClosed_L = totForClosed_L + list.get(i).getL_forclosed();
				
				totCreated_P = totCreated_P + list.get(i).getP_created();
				totUnStarted_P = totUnStarted_P + list.get(i).getP_unstarted();
				totOngoing_P = totOngoing_P + list.get(i).getP_ongoing();
				totCompleted_P = totCompleted_P + list.get(i).getP_completed();
				totForClosed_P = totForClosed_P + list.get(i).getP_forclosed();
				
				totCreated_T = totCreated_T + list.get(i).getT_created();
				totUnStarted_T = totUnStarted_T + list.get(i).getT_unstarted();
				totOngoing_T = totOngoing_T + list.get(i).getT_ongoing();
				totCompleted_T = totCompleted_T + list.get(i).getT_completed();
				totForClosed_T = totForClosed_T + list.get(i).getT_forclosed();
				
				k++;
			}
		
//		netTotal = netTotal+totCreated_N+totCreated_E+totCreated_L+totCreated_P;
		
		CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totProj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
        CommonFunctions.insertCell3(table, String.valueOf(totCreated_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totForClosed_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
        CommonFunctions.insertCell3(table, String.valueOf(totCreated_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totForClosed_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
        CommonFunctions.insertCell3(table, String.valueOf(totCreated_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totForClosed_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
        CommonFunctions.insertCell3(table, String.valueOf(totCreated_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totForClosed_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
        CommonFunctions.insertCell3(table, String.valueOf(totCreated_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totForClosed_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
//        CommonFunctions.insertCell3(table, "Total Works", Element.ALIGN_RIGHT, 5, 1, bf10Bold);
//        CommonFunctions.insertCell3(table, String.valueOf(netTotal), Element.ALIGN_LEFT, 3, 1, bf10Bold);
		
		
			if(list.size()==0) 
				CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 28, 1, bf8);
		
		
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
		response.setHeader("Content-Disposition", "attachment;filename=Report GT1- District.pdf");
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

@RequestMapping(value = "/downloadExcelNRSCProjWorks", method = RequestMethod.POST)
@ResponseBody
public String downloadExcelNRSCProjWorks(HttpServletRequest request, HttpServletResponse response)
{
	String dcode = request.getParameter("dcode");
	String dname = request.getParameter("dname");
	String stname = request.getParameter("stname");
	
	List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
	
	list = nrscWorksService.getNRSCProjWorks(Integer.parseInt(dcode));
	
	Workbook workbook = new XSSFWorkbook();
	//invoking createSheet() method and passing the name of the sheet to be created
	Sheet sheet = workbook.createSheet("Report GT1- Project-wise Total No. of Work Code Shared with NRSC for Geotagging");
	
	CellStyle style = CommonFunctions.getStyle(workbook);
	
	String rptName = "Report GT1- Project-wise Total No. of Work Code Shared with NRSC for Geotagging for District '"+dname+"' of State '"+stname+"'";
	String areaAmtValDetail = "";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 26, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1);
	sheet.addMergedRegion(mergedRegion);
	
//	mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,4);
//	sheet.addMergedRegion(mergedRegion);
//	
//	mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,5,7);
//	sheet.addMergedRegion(mergedRegion);
	
	mergedRegion = new CellRangeAddress(5,5,2,6);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,7,11);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,12,16);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,17,21);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,22,26);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,6,0,0);
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,6,1,1);
	sheet.addMergedRegion(mergedRegion);
	

	
	Row rowhead = sheet.createRow(5);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("Project Name");
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total NRM Works");
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=3;i<7;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(7);
	cell.setCellValue("Total EPA Works");
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=8;i<12;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(12);
	cell.setCellValue("Total Livelihood Works");
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=13;i<17;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(17);
	cell.setCellValue("Total Production Works");
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=18;i<22;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(22);
	cell.setCellValue("Grand Total (NRM+EPA+LIV+PROD)");
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=23;i<27;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	
	
	Row rowhead1 = sheet.createRow(6);
	
	for(int i=0;i<2;i++)
	{
		cell = rowhead1.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead1.createCell(2);
	cell.setCellValue("Total Works");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(3);
	cell.setCellValue("Not Started (Status Not Submitted)");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(4);
	cell.setCellValue("Ongoing");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(5);
	cell.setCellValue("Completed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(6);
	cell.setCellValue("Foreclosed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(7);
	cell.setCellValue("Total Works");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(8);
	cell.setCellValue("Not Started (Status Not Submitted)");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(9);
	cell.setCellValue("Ongoing");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(10);
	cell.setCellValue("Completed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(11);
	cell.setCellValue("Foreclosed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(12);
	cell.setCellValue("Total Works");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(13);
	cell.setCellValue("Not Started (Status Not Submitted)");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(14);
	cell.setCellValue("Ongoing");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(15);
	cell.setCellValue("Completed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(16);
	cell.setCellValue("Foreclosed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(17);
	cell.setCellValue("Total Works");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(18);
	cell.setCellValue("Not Started (Status Not Submitted)");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(19);
	cell.setCellValue("Ongoing");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(20);
	cell.setCellValue("Completed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(21);
	cell.setCellValue("Foreclosed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(22);
	cell.setCellValue("Total Works");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(23);
	cell.setCellValue("Not Started (Status Not Submitted)");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(24);
	cell.setCellValue("Ongoing");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(25);
	cell.setCellValue("Completed");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(26);
	cell.setCellValue("Foreclosed");
	cell.setCellStyle(style);
	
	
	
	Row rowhead2 = sheet.createRow(7);
	
	for(int i=0;i<27;i++)
	{
		cell = rowhead2.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno = 8;
	int totCreated_N = 0;
	int totUnStarted_N = 0;
	int totOngoing_N = 0;
	int totCompleted_N = 0;
	int totForClosed_N = 0;
	int totCreated_E = 0;
	int totUnStarted_E = 0;
	int totOngoing_E = 0;
	int totCompleted_E = 0;
	int totForClosed_E = 0;
	int totCreated_L = 0;
	int totUnStarted_L = 0;
	int totOngoing_L = 0;
	int totCompleted_L = 0;
	int totForClosed_L = 0;
	int totCreated_P = 0;
	int totUnStarted_P = 0;
	int totOngoing_P = 0;
	int totCompleted_P = 0;
	int totForClosed_P = 0;
	int totCreated_T = 0;
	int totUnStarted_T = 0;
	int totOngoing_T = 0;
	int totCompleted_T = 0;
	int totForClosed_T = 0;
	int netTotal = 0;
	
	for(NRSCWorksBean bean : list) {
		Row row = sheet.createRow(rowno);
		row.createCell(0).setCellValue(sno);
		row.createCell(1).setCellValue(bean.getProj_name());
		row.createCell(2).setCellValue(bean.getN_created());
		row.createCell(3).setCellValue(bean.getN_unstarted());
		row.createCell(4).setCellValue(bean.getN_ongoing());
		row.createCell(5).setCellValue(bean.getN_completed());
		row.createCell(6).setCellValue(bean.getN_forclosed());
		row.createCell(7).setCellValue(bean.getE_created());
		row.createCell(8).setCellValue(bean.getE_unstarted());
		row.createCell(9).setCellValue(bean.getE_ongoing());
		row.createCell(10).setCellValue(bean.getE_completed());
		row.createCell(11).setCellValue(bean.getE_forclosed());
		row.createCell(12).setCellValue(bean.getL_created());
		row.createCell(13).setCellValue(bean.getL_unstarted());
		row.createCell(14).setCellValue(bean.getL_ongoing());
		row.createCell(15).setCellValue(bean.getL_completed());
		row.createCell(16).setCellValue(bean.getL_forclosed());
		row.createCell(17).setCellValue(bean.getP_created());
		row.createCell(18).setCellValue(bean.getP_unstarted());
		row.createCell(19).setCellValue(bean.getP_ongoing());
		row.createCell(20).setCellValue(bean.getP_completed());
		row.createCell(21).setCellValue(bean.getP_forclosed());
		row.createCell(22).setCellValue(bean.getT_created());
		row.createCell(23).setCellValue(bean.getT_unstarted());
		row.createCell(24).setCellValue(bean.getT_ongoing());
		row.createCell(25).setCellValue(bean.getT_completed());
		row.createCell(26).setCellValue(bean.getT_forclosed());
		
		
		totCreated_N = totCreated_N + bean.getN_created();
		totUnStarted_N = totUnStarted_N + bean.getN_unstarted();
		totOngoing_N = totOngoing_N + bean.getN_ongoing();
		totCompleted_N = totCompleted_N + bean.getN_completed();
		totForClosed_N = totForClosed_N + bean.getN_forclosed();
		
		totCreated_E = totCreated_E + bean.getE_created();
		totUnStarted_E = totUnStarted_E + bean.getE_unstarted();
		totOngoing_E = totOngoing_E + bean.getE_ongoing();
		totCompleted_E = totCompleted_E + bean.getE_completed();
		totForClosed_E = totForClosed_E + bean.getE_forclosed();
		
		totCreated_L = totCreated_L + bean.getL_created();
		totUnStarted_L = totUnStarted_L + bean.getL_unstarted();
		totOngoing_L = totOngoing_L + bean.getL_ongoing();
		totCompleted_L = totCompleted_L + bean.getL_completed();
		totForClosed_L = totForClosed_L + bean.getL_forclosed();
		
		totCreated_P = totCreated_P + bean.getP_created();
		totUnStarted_P = totUnStarted_P + bean.getP_unstarted();
		totOngoing_P = totOngoing_P + bean.getP_ongoing();
		totCompleted_P = totCompleted_P + bean.getP_completed();
		totForClosed_P = totForClosed_P + bean.getP_forclosed();
		
		totCreated_T = totCreated_T + bean.getT_created();
		totUnStarted_T = totUnStarted_T + bean.getT_unstarted();
		totOngoing_T = totOngoing_T + bean.getT_ongoing();
		totCompleted_T = totCompleted_T + bean.getT_completed();
		totForClosed_T = totForClosed_T + bean.getT_forclosed();
		
		
		sno++;
		rowno++;
	}
	
//	netTotal = netTotal+totCreated_N+totCreated_E+totCreated_L+totCreated_P;
	
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
//	font1.setColor(IndexedColors.WHITE.getIndex());
	style1.setFont(font1);
	
	Row row = sheet.createRow(list.size()+8);
	cell = row.createCell(0);
	cell.setCellValue("Total");
	cell.setCellStyle(style1);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	cell = row.createCell(1);
	cell.setCellStyle(style1);
	cell = row.createCell(2);
	cell.setCellValue(totCreated_N);
	cell.setCellStyle(style1);
	cell = row.createCell(3);
	cell.setCellValue(totUnStarted_N);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totOngoing_N);
	cell.setCellStyle(style1);
	cell = row.createCell(5);
	cell.setCellValue(totCompleted_N);
	cell.setCellStyle(style1);
	cell = row.createCell(6);
	cell.setCellValue(totForClosed_N);
	cell.setCellStyle(style1);
	cell = row.createCell(7);
	cell.setCellValue(totCreated_E);
	cell.setCellStyle(style1);
	cell = row.createCell(8);
	cell.setCellValue(totUnStarted_E);
	cell.setCellStyle(style1);
	cell = row.createCell(9);
	cell.setCellValue(totOngoing_E);
	cell.setCellStyle(style1);
	cell = row.createCell(10);
	cell.setCellValue(totCompleted_E);
	cell.setCellStyle(style1);
	cell = row.createCell(11);
	cell.setCellValue(totForClosed_E);
	cell.setCellStyle(style1);
	cell = row.createCell(12);
	cell.setCellValue(totCreated_L);
	cell.setCellStyle(style1);
	cell = row.createCell(13);
	cell.setCellValue(totUnStarted_L);
	cell.setCellStyle(style1);
	cell = row.createCell(14);
	cell.setCellValue(totOngoing_L);
	cell.setCellStyle(style1);
	cell = row.createCell(15);
	cell.setCellValue(totCompleted_L);
	cell.setCellStyle(style1);
	cell = row.createCell(16);
	cell.setCellValue(totForClosed_L);
	cell.setCellStyle(style1);
	cell = row.createCell(17);
	cell.setCellValue(totCreated_P);
	cell.setCellStyle(style1);
	cell = row.createCell(18);
	cell.setCellValue(totUnStarted_P);
	cell.setCellStyle(style1);
	cell = row.createCell(19);
	cell.setCellValue(totOngoing_P);
	cell.setCellStyle(style1);
	cell = row.createCell(20);
	cell.setCellValue(totCompleted_P);
	cell.setCellStyle(style1);
	cell = row.createCell(21);
	cell.setCellValue(totForClosed_P);
	cell.setCellStyle(style1);
	cell = row.createCell(22);
	cell.setCellValue(totCreated_T);
	cell.setCellStyle(style1);
	cell = row.createCell(23);
	cell.setCellValue(totUnStarted_T);
	cell.setCellStyle(style1);
	cell = row.createCell(24);
	cell.setCellValue(totOngoing_T);
	cell.setCellStyle(style1);
	cell = row.createCell(25);
	cell.setCellValue(totCompleted_T);
	cell.setCellStyle(style1);
	cell = row.createCell(26);
	cell.setCellValue(totForClosed_T);
	cell.setCellStyle(style1);
	
	
//	Row row1 = sheet.createRow(list.size()+9);
//	cell = row1.createCell(0);
//	cell.setCellValue("Total Works");
//	cell.setCellStyle(style1);
//	CellUtil.setCellStyleProperty(cell,  CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
//	for(int i=1;i<5;i++) {
//		cell = row1.createCell(i);
//		cell.setCellStyle(style1);
//	}
//	cell = row1.createCell(5);
//	cell.setCellValue(netTotal);
//	cell.setCellStyle(style1);
//	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
//	for(int i=6;i<8;i++) {
//		cell = row1.createCell(i);
//		cell.setCellStyle(style1);
//	}
	
	CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 26);
	String fileName = "attachment; filename=Report GT1- Project.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/NRSCProjWorks";
}

@RequestMapping(value = "/downloadPDFNRSCProjWorks", method = RequestMethod.POST)
public ModelAndView downloadPDFNRSCProjWorks(HttpServletRequest request, HttpServletResponse response)
{
	String dcode = request.getParameter("dcode");
	String dname = request.getParameter("dname");
	String stname = request.getParameter("stname");
	
	List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
	
	list = nrscWorksService.getNRSCProjWorks(Integer.parseInt(dcode));
	
	try {
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
        layout.setBackgroundColor(new BaseColor(255, 255, 255));
        Document document = new Document(layout, 25, 10, 10, 0);
        document.addTitle("NRSCProjectWorksReport");
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
		
			paragraph3 = new Paragraph("Report GT1- Project-wise Total No. of Work Code Shared with NRSC for Geotagging for District '"+dname+"' of State '"+stname+"'", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(27);
	        table.setWidths(new int[]{3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);
			
	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total NRM Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total EPA Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Livelihood Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Production Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Grand Total (NRM+EPA+LIV+PROD)", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
	        
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

			
		int k = 1;
		int totCreated_N = 0;
		int totUnStarted_N = 0;
		int totOngoing_N = 0;
		int totCompleted_N = 0;
		int totForClosed_N = 0;
		int totCreated_E = 0;
		int totUnStarted_E = 0;
		int totOngoing_E = 0;
		int totCompleted_E = 0;
		int totForClosed_E = 0;
		int totCreated_L = 0;
		int totUnStarted_L = 0;
		int totOngoing_L = 0;
		int totCompleted_L = 0;
		int totForClosed_L = 0;
		int totCreated_P = 0;
		int totUnStarted_P = 0;
		int totOngoing_P = 0;
		int totCompleted_P = 0;
		int totForClosed_P = 0;
		int totCreated_T = 0;
		int totUnStarted_T = 0;
		int totOngoing_T = 0;
		int totCompleted_T = 0;
		int totForClosed_T = 0;
		int netTotal = 0;
		
		if(list.size()!=0)
			for(int i=0;i<list.size();i++)
			{
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
				
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getT_forclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
				
				
				totCreated_N = totCreated_N + list.get(i).getN_created();
				totUnStarted_N = totUnStarted_N + list.get(i).getN_unstarted();
				totOngoing_N = totOngoing_N + list.get(i).getN_ongoing();
				totCompleted_N = totCompleted_N + list.get(i).getN_completed();
				totForClosed_N = totForClosed_N + list.get(i).getN_forclosed();
				
				totCreated_E = totCreated_E + list.get(i).getE_created();
				totUnStarted_E = totUnStarted_E + list.get(i).getE_unstarted();
				totOngoing_E = totOngoing_E + list.get(i).getE_ongoing();
				totCompleted_E = totCompleted_E + list.get(i).getE_completed();
				totForClosed_E = totForClosed_E + list.get(i).getE_forclosed();
				
				totCreated_L = totCreated_L + list.get(i).getL_created();
				totUnStarted_L = totUnStarted_L + list.get(i).getL_unstarted();
				totOngoing_L = totOngoing_L + list.get(i).getL_ongoing();
				totCompleted_L = totCompleted_L + list.get(i).getL_completed();
				totForClosed_L = totForClosed_L + list.get(i).getL_forclosed();
				
				totCreated_P = totCreated_P + list.get(i).getP_created();
				totUnStarted_P = totUnStarted_P + list.get(i).getP_unstarted();
				totOngoing_P = totOngoing_P + list.get(i).getP_ongoing();
				totCompleted_P = totCompleted_P + list.get(i).getP_completed();
				totForClosed_P = totForClosed_P + list.get(i).getP_forclosed();
				
				totCreated_T = totCreated_T + list.get(i).getT_created();
				totUnStarted_T = totUnStarted_T + list.get(i).getT_unstarted();
				totOngoing_T = totOngoing_T + list.get(i).getT_ongoing();
				totCompleted_T = totCompleted_T + list.get(i).getT_completed();
				totForClosed_T = totForClosed_T + list.get(i).getT_forclosed();
				
				k++;
			}
		
//		netTotal = netTotal+totCreated_N+totCreated_E+totCreated_L+totCreated_P;
		
		CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
		
        CommonFunctions.insertCell3(table, String.valueOf(totCreated_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totForClosed_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
        CommonFunctions.insertCell3(table, String.valueOf(totCreated_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totForClosed_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
        CommonFunctions.insertCell3(table, String.valueOf(totCreated_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totForClosed_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
        CommonFunctions.insertCell3(table, String.valueOf(totCreated_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totForClosed_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
        CommonFunctions.insertCell3(table, String.valueOf(totCreated_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        CommonFunctions.insertCell3(table, String.valueOf(totForClosed_T), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        
        
//        CommonFunctions.insertCell3(table, "Total Works", Element.ALIGN_RIGHT, 5, 1, bf10Bold);
//        CommonFunctions.insertCell3(table, String.valueOf(netTotal), Element.ALIGN_LEFT, 3, 1, bf10Bold);
		
		
			if(list.size()==0) 
				CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 27, 1, bf8);
		
		
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
		response.setHeader("Content-Disposition", "attachment;filename=Report GT1- Project.pdf");
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
