package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import app.bean.NRSCWorksBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;
import app.watershedyatra.service.VillageWatershedYatraReportService;

@Controller("WorkStatusDateWiseReportController")
public class WorkStatusDateWiseReportController {
	
	HttpSession session; 
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	WatershedYatraReportService ser;
	
	@Autowired(required = true)
	VillageWatershedYatraReportService service;
	
	private Map<Integer, String> stateList;
	//private Map<String, String> districtList;
	//private Map<String, String> blockList;
	//private Map<String, String> gpList;
	
	
	@RequestMapping(value="/getWorkStatusReport", method = RequestMethod.GET)
	public ModelAndView getWorkStatusReport(HttpServletRequest request, HttpServletResponse response)
	{
		
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		
			mav = new ModelAndView("reports/WorkStatusDateWiseReport");
			mav.addObject("menu", menuController.getMenuUserId(request));
		
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
		return mav; 
	}
	
	@RequestMapping(value="/getWorkStatusReportpost", method = RequestMethod.POST)
	public ModelAndView getWorkStatusReportpost(HttpServletRequest request, HttpServletResponse response)
	{
			
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String userdate= request.getParameter("userdate");
			String userdateto= request.getParameter("userdateto");
			String stName= request.getParameter("stName");
			List<String[]> dataListact = new ArrayList<String[]>();
			String str[] = null;
			Integer headSeq=0;
			List<Integer> headCode = new ArrayList<Integer>();
			Integer activitySeq =1;
			List<String> activityCode = new ArrayList<String>();
			
			List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
			String fromDateStr=null;
			String toDateStr =null;
			
			if(!userdate.equals("")) {
	        LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
	        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			if(!userdateto.equals("")) {
	        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
	        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
				
				mav = new ModelAndView("reports/WorkStatusDateWiseReport");
				mav.addObject("menu", menuController.getMenuUserId(request));
				
				list=service.getWorkStatusReport(Integer.parseInt(userState), userdate, userdateto);
				
				int createdwork=0;
				int startedwork=0;
				int ongoing=0;
				int completed=0;
				int forclosed=0;
				
				
				for(NRSCWorksBean bean: list) 
				{
					if(Integer.parseInt(userState)>0) 
					{
						str = new String[13];
						if(!headCode.contains(bean.getHead_code()) && activityCode.contains(bean.getHead_code()+"."+bean.getActivity_code())) 
						{
							headCode.add(bean.getHead_code());
							headSeq++;
						}
						if(!headCode.contains(bean.getHead_code()) && !activityCode.contains(bean.getHead_code()+"."+bean.getActivity_code())) 
						{
							headCode.add(bean.getHead_code());
							activityCode.add(bean.getHead_code()+"."+bean.getActivity_code());
							headSeq++;
							activitySeq=1;
						}
						if(headCode.contains(bean.getHead_code()) && !activityCode.contains(bean.getHead_code()+"."+bean.getActivity_code())) 
						{
							activityCode.add(bean.getHead_code()+"."+bean.getActivity_code());
							activitySeq++;
						}
					
						str[0] = bean.getHead_code().toString();//"head_code"
						str[1] = headSeq+". "+bean.getHead_desc();//"head_desc"
						str[2] = bean.getActivity_code().toString();//"activity_code"
						str[3] = headSeq+"."+activitySeq+" "+bean.getActivity_desc();//"activity_desc"
						str[4] = bean.getUnitname();//"uom_code"
						
						str[5] = bean.getCreatedwork().toString();
						str[6] = bean.getStartedwork().toString();
						str[7] = bean.getOngoing().toString();
						str[8] = bean.getCompleted().toString();
						str[9] = bean.getForclosed().toString();
						
						dataListact.add(str);
					}
					createdwork = createdwork + bean.getCreatedwork();
					startedwork = startedwork + bean.getStartedwork();
					ongoing = ongoing + bean.getOngoing();
					completed = completed + bean.getCompleted();
					forclosed = forclosed + bean.getForclosed();
					
				}
				mav.addObject("createdwork1", createdwork);
				mav.addObject("startedwork1", startedwork);
				mav.addObject("ongoing1", ongoing);
				mav.addObject("completed1", completed);
				mav.addObject("forclosed1", forclosed);
				
				if(Integer.parseInt(userState)>0) {
					mav.addObject("dataListact",dataListact);
					mav.addObject("dataListSize",dataListact.size());
				}
				else {
					mav.addObject("dataList", list);
					mav.addObject("dataListSize", list.size());
				}
				mav.addObject("userdate", userdate);
				mav.addObject("dateto", userdateto);
				mav.addObject("fromDateStr", fromDateStr);
				mav.addObject("toDateStr", toDateStr);
				
				stateList=stateMasterService.getAllState();
				mav.addObject("stateList", stateList);
				mav.addObject("state", userState);
				
				mav.addObject("stName", stName);
				
			
			return mav; 
	}

	@RequestMapping(value = "/downloadWorkStatusReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadWorkStatusReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String userState= request.getParameter("state");
		String userdate = request.getParameter("userdate1");
		String userdateto = request.getParameter("userdate2");

		String stName= request.getParameter("stName");
		String fromDateStr ="";
		String toDateStr ="";
		if(!userdate.equals("")) {
			LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
	        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
		if(!userdateto.equals("")) {
	        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
	        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}

        
		List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
		list=service.getWorkStatusReport(Integer.parseInt(userState), userdate, userdateto);
		
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ME8-StateReport");
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
			
				paragraph3 = new Paragraph("Report ME8- State and Date Wise Work Status List", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(7);
				table.setWidths(new int[] { 2, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(100);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);							
				table.setHeaderRows(3);
				
				CommonFunctions.insertCellHeader(table, "State : "+stName +" From Date: "+ fromDateStr+" To Date: "+toDateStr  ,Element.ALIGN_LEFT, 7, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.",Element.ALIGN_RIGHT, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_RIGHT, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Created Works", Element.ALIGN_RIGHT, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Started Works", Element.ALIGN_RIGHT, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ongoing Works	", Element.ALIGN_RIGHT, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Completed Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Foreclosed Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int i=0;
				int k=1;
				Integer createdwork=0;
				Integer ongoingwork=0;
				Integer completeddwork=0;
				Integer startedwork=0;
				Integer forcloseddwork=0;
				if(list.size()!=0)
					for( i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCreatedwork()),Element.ALIGN_RIGHT,1,1,bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getStartedwork()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOngoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCompleted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getForclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						
						createdwork=createdwork +(list.get(i).getCreatedwork());
						ongoingwork=ongoingwork +(list.get(i).getOngoing());
						completeddwork=completeddwork +(list.get(i).getCompleted());
						startedwork=startedwork +(list.get(i).getStartedwork());
						forcloseddwork=forcloseddwork +(list.get(i).getForclosed());
						
					}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(createdwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(startedwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(ongoingwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(completeddwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(forcloseddwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(list.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
				
					
		document.add(table);
		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15f);
		table.setSpacingAfter(0f);
		CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
		CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
		document.add(table);
		document.close();
		response.setContentType("application/pdf");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-Disposition", "attachment;filename=ME8-StateReport.pdf");
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
	
	@RequestMapping(value = "/downloadExcelWorkStatusReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelWorkStatusReport(HttpServletRequest request, HttpServletResponse response) 
	{
		String userState= request.getParameter("state");
		String userdate = request.getParameter("userdate1");
		String userdateto = request.getParameter("userdate2");

		String stName= request.getParameter("stName");
		String fromDateStr ="";
		String toDateStr ="";
		if(!userdate.equals("")) {
			LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
	        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
		if(!userdateto.equals("")) {
	        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
	        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}

        
		List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
		list=service.getWorkStatusReport(Integer.parseInt(userState), userdate, userdateto);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report ME8- State and Date Wise Work Status List");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
			
			
			String rptName = "Report ME8- State and Date Wise Work Status List";
			String areaAmtValDetail = "";
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);
        
	    	mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1);
			sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,5,0,6);
			sheet.addMergedRegion(mergedRegion);
	        
	        Row rowDetail = sheet.createRow(5);
			
			Cell cell = rowDetail.createCell(0);
			cell.setCellValue("State Name : "+ stName+ " From Date: "+ fromDateStr+" To Date: " +toDateStr);  
			cell.setCellStyle(style);
			
			for(int i=1;i<7;i++)
			{
				cell = rowDetail.createCell(i);
				cell.setCellStyle(style);
			}
			
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Created Works");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(3);
			cell.setCellValue("Started Works");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Ongoing Works");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Completed Works");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Foreclosed Works");  
			cell.setCellStyle(style);
			
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<7;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
			int sno = 1;
			int rowno = 8;
			int CreatedWorks = 0;
			int StartedWorks = 0;
			int OngoingWorks = 0;
			int CompletedWorks = 0;
			int forCloseWorks = 0;
			for(NRSCWorksBean bean: list) {
				Row row = sheet.createRow(rowno);
				row.createCell(0).setCellValue(sno); 
				row.createCell(1).setCellValue(bean.getSt_name());
				row.createCell(2).setCellValue(bean.getCreatedwork());  
				row.createCell(3).setCellValue(bean.getStartedwork());  
				row.createCell(4).setCellValue(bean.getOngoing()); 
				row.createCell(5).setCellValue(bean.getCompleted());  
				row.createCell(6).setCellValue(bean.getForclosed()); 

				CreatedWorks = CreatedWorks + bean.getCreatedwork();
				StartedWorks = StartedWorks + bean.getStartedwork();
				OngoingWorks = OngoingWorks + bean.getOngoing();
				CompletedWorks = CompletedWorks + bean.getCompleted();
				forCloseWorks = forCloseWorks + bean.getForclosed();
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
	        cell.setCellValue(CreatedWorks);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(StartedWorks);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(OngoingWorks);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(5);
	        cell.setCellValue(CompletedWorks);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(6);
	        cell.setCellValue(forCloseWorks);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 6);
	        String fileName = "attachment; filename=Report ME8.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "/reports/WorkStatusDateWiseReport";
		
	}
	
}
