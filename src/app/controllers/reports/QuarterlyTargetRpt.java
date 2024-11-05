package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
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
import app.bean.PhysicalActionPlanBean;
import app.bean.ProjectLocationBean;
import app.bean.reports.QuarterlyTargetBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.service.StateMasterService;
import app.service.reports.TargetAchievementQuarterService;

@Controller("quarterlyTargetRpt")
public class QuarterlyTargetRpt {

	@Autowired
	StateMasterService stateMasterService;
		
	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@RequestMapping(value="/quarterlyTargetrpt", method = RequestMethod.GET)
	public ModelAndView quarterlyTargetrpt(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/quarTargetRpt");
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("financialYear", ser.getFinancialYearonward21());
		return mav;
	}

		@RequestMapping(value="/getQuarTargetReport", method = RequestMethod.POST)
	public ModelAndView getQuarTargetReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String year= request.getParameter("year");
		String stName= request.getParameter("stName");
		String finName= request.getParameter("finName");
		List<String[]> dataList = new ArrayList<String[]>();
		String str[] = null;
		
		try {
			mav = new ModelAndView("reports/quarTargetRpt");
			List<QuarterlyTargetBean> list=new ArrayList<QuarterlyTargetBean>();
			list=ser.fetchquartargetrpt(Integer.parseInt(userState),Integer.parseInt(year));
			for(QuarterlyTargetBean bean : list) 
			{
				str = new String[8];
				str[0] = bean.getIndicators_group();
				str[1] = bean.getQuad1().toString();
				str[2] = bean.getQuad2().toString();
				str[3] = bean.getQuad3().toString();
				str[4] = bean.getQuad4().toString();
				str[5] = bean.getTotal_quad().toString();
			    dataList.add(str);
			}
			mav.addObject("stateList",stateMasterService.getAllState());
			mav.addObject("financialYear", ser.getFinancialYearonward21());
			mav.addObject("dataList", dataList);
			mav.addObject("dataListSize",dataList.size());
			mav.addObject("stCode", userState);
			mav.addObject("year", year);
			mav.addObject("stName", stName);
			mav.addObject("finName", finName);
		}
			catch (Exception e) {
				e.printStackTrace();
			}
			return mav; 
		}
		

		@RequestMapping(value = "/downloadQuarTargetReportPDF", method = RequestMethod.POST)
		public ModelAndView downloadQuarTargetReportPDF(HttpServletRequest request, HttpServletResponse response) {
			
			String userState= request.getParameter("stCode");
			String year= request.getParameter("year");
			
			  String stName= request.getParameter("stName"); 
			  String finName= request.getParameter("finName");
			 
			List<String[]> dataList = new ArrayList<String[]>();
			String str[] = null;
			List<QuarterlyTargetBean> list=new ArrayList<QuarterlyTargetBean>();
			list=ser.fetchquartargetrpt(Integer.parseInt(userState),Integer.parseInt(year));
		  
		    try {
		        Rectangle layout = new Rectangle(PageSize.A4.rotate());
		        layout.setBackgroundColor(new BaseColor(255, 255, 255));
		        Document document = new Document(layout, 25, 10, 10, 0);
		        document.addTitle("T3-Report");
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

		        paragraph3 = new Paragraph("Report T3- State-wise, Year-wise Targets on Output Outcome Monitoring Framework(OOMF) Indicators ", f3);

		        paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(7);
		        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(60);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(2);
		        
		        CommonFunctions.insertCellHeader(table, "State:  "+stName+ "  Financial Year:  "+finName, Element.ALIGN_LEFT, 7, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Name of the Activity", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		       
		        CommonFunctions.insertCellHeader(table, "(April-June)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "(July-September)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "(October-December)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "(January-March)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);

		        CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		       
		        int k = 1;
		        if (list.size() != 0) {
		            for (int i = 0; i < list.size(); i++) {
		                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, list.get(i).getIndicators_group(), Element.ALIGN_LEFT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getQuad1()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getQuad2()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getQuad3()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getQuad4()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_quad()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                
		                k = k + 1;
		               
		            }

		            if(list.size()==0)
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
			
		            document.add(table);
		            table = new PdfPTable(1);
					table.setWidthPercentage(70);
					table.setSpacingBefore(15f);
					table.setSpacingAfter(0f);
					CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
					document.add(table);
					response.setContentType("application/pdf");
					response.setHeader("Expires", "0");
					response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		        }

		        document.close();

		        response.setContentType("application/pdf");
		        response.setHeader("Content-Disposition", "attachment; filename=O12-Report.pdf");
		        OutputStream outputStream = response.getOutputStream();
		        baos.writeTo(outputStream);
		        outputStream.flush();
		        outputStream.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return null;
		}
		@RequestMapping(value = "/downloadExcelQuarTargetReport", method = RequestMethod.POST)
		@ResponseBody
		public String downloadExcelQuarTargetReport(HttpServletRequest request, HttpServletResponse response) 
		{
			
			String userState= request.getParameter("stCode");
			String year= request.getParameter("year");
			
			  String stName= request.getParameter("stName"); 
			  String finName= request.getParameter("finName");
			 
			List<String[]> dataList = new ArrayList<String[]>();
			String str[] = null;
			List<QuarterlyTargetBean> list=new ArrayList<QuarterlyTargetBean>();
			list=ser.fetchquartargetrpt(Integer.parseInt(userState),Integer.parseInt(year));
		  
				
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report T3- State-wise, Year-wise Targets on Output Outcome Monitoring Framework(OOMF) Indicators");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
		    
			String rptName = "Report T3- State-wise, Year-wise Targets on Output Outcome Monitoring Framework(OOMF) Indicators";
			String areaAmtValDetail ="";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,6); 
			sheet.addMergedRegion(mergedRegion);
			
			Row rowDetail = sheet.createRow(5);
			
			Cell cell = rowDetail.createCell(0);
			cell.setCellValue("State : "+ stName+"     Financial Year : "+finName);  
			cell.setCellStyle(style);
			
			
			Row rowhead = sheet.createRow(6);
			
			 cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Name of the Activity");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("(April-June)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("(July-September)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("(October-December)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("(January-March)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			
			for(int i=0;i<7;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
			int sno = 1;
			int rowno  = 8;
			
			
		    for(QuarterlyTargetBean bean: list) 
		    {
		    	Row row = sheet.createRow(rowno);
		    	row.createCell(0).setCellValue(sno); 
		    	row.createCell(1).setCellValue(bean.getIndicators_group());
		    	row.createCell(2).setCellValue(bean.getQuad1().doubleValue());
		    	row.createCell(3).setCellValue(bean.getQuad2().doubleValue());
		    	row.createCell(4).setCellValue(bean.getQuad3().doubleValue());
		    	row.createCell(5).setCellValue(bean.getQuad4().doubleValue());
		    	row.createCell(6).setCellValue(bean.getTotal_quad().doubleValue());
		    	
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

		    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 6);
		    String fileName = "attachment; filename=Report T3- State.xlsx";
		    
		    CommonFunctions.downloadExcel(response, workbook, fileName);

		    return "reports/quarTargetRpt";
		}

}
