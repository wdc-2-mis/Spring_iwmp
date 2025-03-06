package app.controllers.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

import app.TargetAchievementQuarterBean;
import app.bean.reports.PhysicalActionAchievementBean;
import app.common.CommonFunctions;
import app.service.reports.WatershedYatraReportService;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.bean.PreYatraPreparationBean;
import app.watershedyatra.service.WatershedYatraParticipantServices;

@Controller("WatershedYatraParticipantController")
public class WatershedYatraParticipantController {
	
	@Autowired(required = true)
	WatershedYatraParticipantServices ser;
	
	
	@RequestMapping(value="/getWatershedYatraParticipantReport", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraParticipantReport(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userdate= request.getParameter("userdate");
		String userdateto= request.getParameter("userdateto");
		List<String> monthdate= new ArrayList<String>();
		List<String> monthdate1= new ArrayList<String>();
		List<String> stateList= new ArrayList<String>();
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		List<NodalOfficerBean> listgrand = new ArrayList<NodalOfficerBean>();
		Integer monthListSize =0;
		String fromDateStr=null;
		String toDateStr =null;
		try {
		/*if(session!=null && session.getAttribute("loginID")!=null) {*/
			if(userdate !=null && !userdate.equals("")  ) {
		        LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
		        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			if(userdateto!=null && !userdateto.equals("") ) {
		        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
		        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			mav = new ModelAndView("WatershedYatra/watershedYatraParticipantReport");
			list=ser.getWatershedYatraParticipant(userdate, userdateto);
			listgrand=ser.getWatershedYatraParticipantgrand(userdate, userdateto);
			
			for(NodalOfficerBean bean : list) 
			{
				if(monthdate!=null && !monthdate.contains(bean.getYatradate()))
					monthdate.add(bean.getYatradate());
			
				if(stateList!=null && !stateList.contains(bean.getSt_name()))
					stateList.add(bean.getSt_name());
			}
			 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		     List<Date> dates = new ArrayList<>();
		        for (String dateString : monthdate) 
		        {
		            try {
		                dates.add(dateFormat.parse(dateString));
		            } catch (ParseException e) {
		                e.printStackTrace();
		            }
		        }
		        Collections.sort(dates);
		        for (Date date : dates) 
		        {
		            monthdate1.add(dateFormat.format(date));
		        }
		        if(dates.size()>0)
					 monthListSize = monthdate1.size();
		        
		        Collections.sort(stateList);

		    mav.addObject("stateList", stateList);    
			mav.addObject("ParticipantList", list);
			mav.addObject("ParticipantListSize", list.size());
			mav.addObject("monthList",monthdate1);
			mav.addObject("monthListSize",monthListSize);
			mav.addObject("fromDateStr", fromDateStr);
			mav.addObject("toDateStr", toDateStr);
			mav.addObject("userdate", userdate);
			mav.addObject("dateto", userdateto);
			mav.addObject("listgrand", listgrand);
			mav.addObject("listgrandSize", listgrand.size());
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
				/*
				 * }else { mav = new ModelAndView("login"); mav.addObject("login", new Login());
				 * }
				 */
		return mav; 
	}
	
	@RequestMapping(value = "/downloadWatershedYatraParticipantReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadWatershedYatraParticipantReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String userdate= request.getParameter("userdate1");
		String userdateto= request.getParameter("userdate2");
		
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		List<NodalOfficerBean> listgrand = new ArrayList<NodalOfficerBean>();
		List<String> monthdate= new ArrayList<String>();
		List<String> monthdate1= new ArrayList<String>();
		List<String> stateList= new ArrayList<String>();
		
		Integer monthListSize =0;
		String fromDateStr ="";
		String toDateStr ="";
		try {
			if(!userdate.equals("")) {
				LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
		        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			if(!userdateto.equals("")) {
		        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
		        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			
			list=ser.getWatershedYatraParticipant(userdate, userdateto);
			listgrand=ser.getWatershedYatraParticipantgrand(userdate, userdateto);
			
			for(NodalOfficerBean bean : list) 
			{
				if(monthdate!=null && !monthdate.contains(bean.getYatradate()))
					monthdate.add(bean.getYatradate());
			
				if(stateList!=null && !stateList.contains(bean.getSt_name()))
					stateList.add(bean.getSt_name());
			}
			 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		     List<Date> dates = new ArrayList<>();
		        for (String dateString : monthdate) 
		        {
		            try {
		                dates.add(dateFormat.parse(dateString));
		            } catch (ParseException e) {
		                e.printStackTrace();
		            }
		        }
		        Collections.sort(dates);
		        for (Date date : dates) 
		        {
		            monthdate1.add(dateFormat.format(date));
		        }
		        if(dates.size()>0)
					 monthListSize = monthdate1.size();
		        
		        Collections.sort(stateList);
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("daily Public Participants");
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
			
				paragraph3 = new Paragraph("State wise daily Public Participants", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(monthListSize+3);
				int[] widths = new int[monthListSize + 3];
				// Set the first element to 2
				widths[0] = 2;
				// Set the rest of the elements to 5
				for (int i = 1; i < widths.length; i++) {
				    widths[i] = 5;
				}
				table.setWidths(widths);
				
			//	table.setWidths(new int[] { 2, 5, 5, 5, 5, 5, 5, 5, 5 });
			//	table.setWidthPercentage(70);
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
			//	table.setHeaderRows(1);
				
				
				
			//	list=ser.getRoutePlanReportData(stCode, distCode, blkCode, gpCode);
				
				CommonFunctions.insertCellHeader(table, "Sl. No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				for (String monthdt : monthdate1) {
				    CommonFunctions.insertCellHeader(table, monthdt, Element.ALIGN_CENTER, 1, 1, bf8Bold);
				}
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				int gtot=0;
				int k=1;
				boolean check=true;
				boolean checkdate=true;
				for(String stlist : stateList) 
				{
					int total=0;
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, stlist, Element.ALIGN_LEFT, 1, 1, bf8);
					for (String monthdt : monthdate1) 
					{
						for(int i=0;i<list.size();i++) 
						{
							if(stlist.equals(list.get(i).getSt_name())  &&  monthdt.equals(list.get(i).getYatradate()) && check ) 
							{
								CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_participants()), Element.ALIGN_RIGHT, 1, 1, bf8);
								total=total+list.get(i).getTotal_participants();
								check=false;
								checkdate=false;
								
							}
						}
						if(checkdate) {
							CommonFunctions.insertCell(table, String.valueOf(0), Element.ALIGN_RIGHT, 1, 1, bf8);
							
						}
						check=true;
						checkdate=true;
						
					}
					CommonFunctions.insertCell(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf8);
					gtot=gtot+total;
					k=k+1;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
				
				for(int i=0; i<listgrand.size(); i++) 
				{
					CommonFunctions.insertCell3(table, String.valueOf(listgrand.get(i).getTotal_participants()) , Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				}
				CommonFunctions.insertCell3(table, String.valueOf(gtot) , Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		
				if(list.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, monthListSize+3, 1, bf8);
				
					
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
		response.setHeader("Content-Disposition", "attachment;Public Participants.pdf");
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
	
	@RequestMapping(value = "/downloadExcelWatershedYatraParticipantReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelWatershedYatraParticipantReport(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String userdate= request.getParameter("userdate1");
		String userdateto= request.getParameter("userdate2");
		
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		List<NodalOfficerBean> listgrand = new ArrayList<NodalOfficerBean>();
		List<String> monthdate= new ArrayList<String>();
		List<String> monthdate1= new ArrayList<String>();
		List<String> stateList= new ArrayList<String>();
		
		Integer monthListSize =0;
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
		
		list=ser.getWatershedYatraParticipant(userdate, userdateto);
		listgrand=ser.getWatershedYatraParticipantgrand(userdate, userdateto);
		
		for(NodalOfficerBean bean : list) 
		{
			if(monthdate!=null && !monthdate.contains(bean.getYatradate()))
				monthdate.add(bean.getYatradate());
		
			if(stateList!=null && !stateList.contains(bean.getSt_name()))
				stateList.add(bean.getSt_name());
		
		}
		
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	     List<Date> dates = new ArrayList<>();
	        for (String dateString : monthdate) 
	        {
	            try {
	                dates.add(dateFormat.parse(dateString));
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }
	        }
	        Collections.sort(dates);
	        for (Date date : dates) 
	        {
	            monthdate1.add(dateFormat.format(date));
	        }
	        if(dates.size()>0)
				 monthListSize = monthdate1.size();
	        
	        Collections.sort(stateList);

		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("State wise daily Public Participants ");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "State wise daily Public Participants ";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, monthListSize+3, areaAmtValDetail, workbook);

		Row rowhead = sheet.createRow(5); 

		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		for (int i=2; i<monthdate1.size()+2; i++)
		{
			cell = rowhead.createCell(i);
			cell.setCellValue(monthdate1.get(i - 2));  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		}

		cell = rowhead.createCell(monthdate1.size()+2);
		cell.setCellValue("Total");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i=0;i<monthdate1.size()+3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int rowno  = 7;
		int col=0;
		int k=1;
		int p=2;
		boolean check=true;
		boolean checkdate=true;
		int gtot=0;
		for(String stlist : stateList) {
			int total=0;
			col = 0;
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(k);
			row.createCell(1).setCellValue(stlist);
			for (String monthdt : monthdate1) 
			{
				for(int i=0;i<list.size();i++) 
				{
					if(stlist.equals(list.get(i).getSt_name())  &&  monthdt.equals(list.get(i).getYatradate()) && check ) 
					{
						row.createCell(p).setCellValue(list.get(i).getTotal_participants());
						
						total=total+list.get(i).getTotal_participants();
						check=false;
						checkdate=false;
						col = p;
						p = p+1;
					}
				}
				if(checkdate) 
				{
					if(p==2) {
						row.createCell(col+2).setCellValue(0);
						col = col+2;
					 	p = col +1;
					}else {
						row.createCell(col+1).setCellValue(0);
						col = col+1;
					 	p = col +1;
					}
				}
				check=true;
				checkdate=true;
			}
			row.createCell(monthdate1.size()+2).setCellValue(total);
			gtot=gtot+total;
			k=k+1;
			rowno++;
			p=2;
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
		style1.setFont(font1);
		
		int h=2;
		Row row = sheet.createRow(rowno);
        cell = row.createCell(0);
        cell.setCellStyle(style1);
        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
        cell = row.createCell(1);
        cell.setCellValue("Grand Total");
        cell.setCellStyle(style1);
        
        for(int i=0;i<listgrand.size();i++) 
		{
	        cell = row.createCell(h);
	        cell.setCellValue(listgrand.get(i).getTotal_participants());
	        cell.setCellStyle(style1);
	        h = h+1;
		}
        cell = row.createCell(h);
        cell.setCellValue(gtot);
        cell.setCellStyle(style1);
		
		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
		String fileName = "attachment; filename=Participants.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return null;

	}
	
	
	
	
	
	
	

}
