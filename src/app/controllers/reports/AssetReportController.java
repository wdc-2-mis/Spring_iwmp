package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import app.bean.AssetIdBean;
import app.bean.Login;
import app.common.CommonFunctions;
import app.model.PfmsCgireceiptDetaildata;
import app.service.PhysicalActionPlanService;
import app.service.StateMasterService;
import app.service.reports.AssetReportService;

@Controller("AssetReportController")
public class AssetReportController {
	
	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	AssetReportService assetReportService;
	
	@Autowired
	PhysicalActionPlanService physicalActionPlanService;
	
	@RequestMapping(value="/assetReport", method = RequestMethod.GET)
	public ModelAndView getAssetReportPage(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/assetReport");
		LinkedHashMap<Integer,String> stateList=stateMasterService.getAllState();
		LinkedHashMap<Integer, String> headList = physicalActionPlanService.getHead();
		
		mav.addObject("stateList",stateList);
		mav.addObject("headList",headList);
		
		return mav;
		
	}
	
	@RequestMapping(value="/getSubActivity", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getSubActivity(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="activityId") Integer activityId)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		map=assetReportService.getSubActivity(activityId);
		return map; 
	}
	
	@RequestMapping(value="/getAssetReport", method = RequestMethod.POST)
	@ResponseBody
	public List<AssetIdBean> getAssetReport(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="stCode") Integer stCode,
			@RequestParam(value ="distCode") Integer distCode, @RequestParam(value ="projId") Integer projId,
			@RequestParam(value ="fyCode") Integer fyCode, @RequestParam(value ="headCode") Integer headCode,
			@RequestParam(value ="activityCode") Integer activityCode, @RequestParam(value ="subActivityCode") Integer subActivityCode,
			@RequestParam(value ="monthid") Integer monthid, @RequestParam(value ="statuss") String status)
	{
		session = request.getSession(true);
		List<AssetIdBean> list = new ArrayList<AssetIdBean>();
	//	System.out.println("stCode "+stCode+" distCode "+distCode+" projId "+projId+" fyCode "+fyCode+" headCode "+headCode+" actCode "+activityCode+" subActCode "+subActivityCode);
		list=assetReportService.getAssetReport(stCode,distCode,projId,fyCode,headCode,activityCode,subActivityCode, monthid, status);
		return list; 
	}
	
	
	@RequestMapping(value = "/downloadAssetReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadAssetReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		
		int stCode = Integer.parseInt(request.getParameter("stCode"));
		int distCode = Integer.parseInt(request.getParameter("distCode"));
		int projId = Integer.parseInt(request.getParameter("projId"));
		int fyCode = Integer.parseInt(request.getParameter("fyCode"));
		int headCode = Integer.parseInt(request.getParameter("headCode"));
		int activityCode = Integer.parseInt(request.getParameter("activityCode"));
		int subActivityCode = Integer.parseInt(request.getParameter("subActivityCode"));
		int monthid = Integer.parseInt(request.getParameter("monthid"));
		String status = request.getParameter("statusk");
		List<AssetIdBean> list = new ArrayList<AssetIdBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("AssetReport");
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
			
				paragraph3 = new Paragraph("Report A2- State-wise, District-wise, Project-wise and Year-wise Details of Monthly Achievements According to Activity Type/Work as per List of Activities", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(14);
				table.setWidths(new int[] { 8, 5, 5, 5, 8, 5, 8, 5, 2, 5, 5, 5, 6, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(2);
				
				list=assetReportService.getAssetReport(stCode,distCode,projId,fyCode,headCode,activityCode,subActivityCode, monthid, status);
			
				CommonFunctions.insertCellHeader(table, "State", Element.ALIGN_RIGHT, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Block", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "FY ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Head", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Activity", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Activity Type", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Work ID", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area Cover", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Unit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Status", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Completion Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int i=0;
				BigDecimal assetachv = BigDecimal.ZERO;
				if(list.size()!=0)
					for( i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getBname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getVname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProjdesc(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFinyrdesc(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getHeaddesc(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getActivitydesc(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSubactivitydesc(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getAssetid().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getAssetach() == null ? "" : list.get(i).getAssetach().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getUnit(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getStatuskd(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getStatusdate(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						assetachv=assetachv.add(list.get(i).getAssetach());
					}
				CommonFunctions.insertCell3(table, " Total", Element.ALIGN_RIGHT, 9, 1, bf10Bold);
				CommonFunctions.insertCell3(table, "Works= "+String.valueOf(i), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(assetachv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, "", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, "", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, "", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 13, 1, bf8);
				
					
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
		response.setHeader("Content-Disposition", "attachment;filename=A2-Report.pdf");
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
	
	@RequestMapping(value = "/downloadExcelassetReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelassetReport(HttpServletRequest request, HttpServletResponse response) 
	{
		int stCode = Integer.parseInt(request.getParameter("stCode"));
		int distCode = Integer.parseInt(request.getParameter("distCode"));
		int projId = Integer.parseInt(request.getParameter("projId"));
		int fyCode = Integer.parseInt(request.getParameter("fyCode"));
		int headCode = Integer.parseInt(request.getParameter("headCode"));
		int activityCode = Integer.parseInt(request.getParameter("activityCode"));
		int subActivityCode = Integer.parseInt(request.getParameter("subActivityCode"));
		int monthid = Integer.parseInt(request.getParameter("monthid"));
		String status = request.getParameter("statusk");
		
		  List<AssetIdBean> list = new ArrayList<AssetIdBean>();
		
		  list=assetReportService.getAssetReport(stCode,distCode,projId,fyCode,headCode,activityCode,subActivityCode,monthid, status);
		  
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report A2- State-wise, District-wise, Project-wise and Year-wise Details of Monthly Achievements");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report A2- State-wise, District-wise, Project-wise and Year-wise Details of Monthly Achievements According to Activity Type/Work as per List of Activities";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,8); 
	        sheet.addMergedRegion(mergedRegion);
			
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("State");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Block");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Village");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Project");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("FY");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Head");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Activity");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Activity Type");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Work ID");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Area Cover");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Unit");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Status");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(13);
			cell.setCellValue("Completion Date");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i=0;i<14;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 7;
	        BigDecimal assetachv = BigDecimal.ZERO;
	        
	        for(AssetIdBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(bean.getStname()); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getBname());
	        	row.createCell(3).setCellValue(bean.getVname());
	        	row.createCell(4).setCellValue(bean.getProjdesc());
	        	row.createCell(5).setCellValue(bean.getFinyrdesc());
	        	row.createCell(6).setCellValue(bean.getHeaddesc());
	        	row.createCell(7).setCellValue(bean.getActivitydesc());
	        	row.createCell(8).setCellValue(bean.getSubactivitydesc());
	        	row.createCell(9).setCellValue(bean.getAssetid()==null?0.0:bean.getAssetid().intValue());
	        	row.createCell(10).setCellValue(bean.getAssetach()==null?0.0:bean.getAssetach().doubleValue());
	        	row.createCell(11).setCellValue(bean.getUnit());
	        	row.createCell(12).setCellValue(bean.getStatuskd());
	        	row.createCell(13).setCellValue(bean.getStatusdate());
	        	
	        	assetachv=assetachv.add(bean.getAssetach());
	        	
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
	        cell.setCellValue("Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(sno-1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(assetachv.toString());
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
	        String fileName = "attachment; filename=Report A2.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/assetReport";
		
	}

}
