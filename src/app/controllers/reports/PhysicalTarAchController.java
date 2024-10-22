package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

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

import app.bean.reports.PhysicalActionAchievementBean;
import app.common.CommonFunctions;
import app.service.StateMasterService;
import app.service.reports.PhysicalActionAchievementService;
import app.service.reports.PhysicalTarAchService;

@Controller("PhysicalTarAchController")
public class PhysicalTarAchController {

	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@Autowired
	PhysicalTarAchService psa;
	
	@RequestMapping(value="/getPhysicalTarAchReport", method = RequestMethod.GET)
	public ModelAndView getPhysicalTarAchReport(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/physicalTarAchReport");
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		return mav;
	}

	
	@RequestMapping(value="/getphysicalTarAchReport", method = RequestMethod.POST)
	public ModelAndView getphysicalTarAchReport(HttpServletRequest request)
	{
		session = request.getSession(true);
		
		int stCode= Integer.parseInt(request.getParameter("state"));
		int distCode= Integer.parseInt(request.getParameter("district"));
		int projId= Integer.parseInt(request.getParameter("project"));
		int fromYear= Integer.parseInt(request.getParameter("fromYear"));
		
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		String stName= request.getParameter("stName");
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/physicalTarAchReport");
		List<PhysicalActionAchievementBean> beanList = new ArrayList<PhysicalActionAchievementBean>();
				beanList=psa.getphysicalTarAchReport(stCode, distCode, projId,fromYear);
				
			 mav.addObject("beanList",psa.getphysicalTarAchReport(stCode, distCode, projId,fromYear));
			 mav.addObject("beanListSize",beanList.size());
			 mav.addObject("stateList",stateMasterService.getAllState());
			 mav.addObject("stCode",stCode);
			 mav.addObject("distCode",distCode);
			 mav.addObject("projectId",projId);
			 mav.addObject("fromYear",fromYear);
			 mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
			 
			 mav.addObject("stName",stName);
			 mav.addObject("distName",distName);
			 mav.addObject("projName",projName);
			 mav.addObject("yearName",yearName);
				
		return mav;
	}
	
	@RequestMapping(value = "/downloadPhysicalTarAchPDF", method = RequestMethod.POST)
	public ModelAndView downloadPhysicalTarAchPDF(HttpServletRequest request, HttpServletResponse response) 	{
		//WDC-PMKSY-0001113	
		int stCode= Integer.parseInt(request.getParameter("stCode"));
		int distCode= Integer.parseInt(request.getParameter("distCode"));
		int projectId= Integer.parseInt(request.getParameter("projId"));
		int fromYear= Integer.parseInt(request.getParameter("fYear"));
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		List<PhysicalActionAchievementBean> beanList = new ArrayList<PhysicalActionAchievementBean>();
		beanList=psa.getphysicalTarAchReport(stCode, distCode, projectId,fromYear);
		
		try {	
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("PhyAchPlanReport");	
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
			paragraph3 = new Paragraph("Report A3- State, District, Project and Year-wise Details of Achievements Against Annual Physical Action Plan Targets as per List of Activities", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);	
			document.add(paragraph3);	
			table = new PdfPTable(5);
			table.setWidths(new int[] { 8, 3, 5, 5, 5});
			
			table.setWidthPercentage(70);	
			table.setWidthPercentage(100);	
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);
			
				CommonFunctions.insertCellHeader(table, "State: "+stName+"  District: "+distName + "  Project: "+ projName + "  Financial Year: "+yearName, Element.ALIGN_LEFT, 5, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Name of the Activity", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Unit ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Physical Target ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Physical Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);	
				CommonFunctions.insertCellHeader(table, "Achievement % (4*100/3)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				 	 	 	 	 	 	 	 	 	 	
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);	
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);	
				
				int headCode=0;
				int oldActCd = 0;
				int actSeq = 0;
				int j  =0;
				BigDecimal phyTarTot = BigDecimal.ZERO;
				BigDecimal phyAchTot = BigDecimal.ZERO;
				BigDecimal totTotal  = BigDecimal.ZERO;
				if(beanList.size()!=0)
					for(int i=0;i<beanList.size();i++) 
					{
						if(headCode != beanList.get(i).getHead_code()) {
							if(actSeq!=0 && beanList.get(i).getHead_code()<9) {
								CommonFunctions.insertCell3(table, "Sub-Total", Element.ALIGN_LEFT, 2, 1, bf10Bold);
								 CommonFunctions.insertCell3(table, String.valueOf(phyTarTot), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								 CommonFunctions.insertCell3(table, String.valueOf(phyAchTot), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								 CommonFunctions.insertCell3(table, String.valueOf(totTotal+"%"), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								
								phyAchTot = BigDecimal.ZERO;
								phyTarTot = BigDecimal.ZERO;
								totTotal = BigDecimal.ZERO;
								 
							}
							CommonFunctions.insertCell(table,(beanList.get(i).getHead_code()+"."+ beanList.get(i).getHead_desc()), Element.ALIGN_LEFT, 17, 1, bf8);
							headCode=beanList.get(i).getHead_code();		
							actSeq =1;
							j=0;
							
						}
						
						
						oldActCd = beanList.get(i).getActivity_code();
						if(beanList.get(i).getActivity_code()==oldActCd) {
								CommonFunctions.insertCell(table, beanList.get(i).getHead_code()+"."+actSeq+" "+beanList.get(i).getActivity_desc(), Element.ALIGN_LEFT, 1, 1, bf8);
								CommonFunctions.insertCell(table, beanList.get(i).getUnitname(), Element.ALIGN_LEFT, 1, 1, bf8);
								CommonFunctions.insertCell(table, beanList.get(i).getPlan() == null ? "" : String.format(Locale.US, "%.4f", beanList.get(i).getPlan()), Element.ALIGN_RIGHT, 1, 1, bf8);
				        			CommonFunctions.insertCell(table, beanList.get(i).getAchievement() == null ? "" : String.format(Locale.US, "%.4f", beanList.get(i).getAchievement()), Element.ALIGN_RIGHT, 1, 1, bf8);
								
				        			BigDecimal Ach = beanList.get(i).getAchievement();
				        			BigDecimal tar = beanList.get(i).getPlan();

								if (tar.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal per = Ach.multiply(new BigDecimal("100")).divide(tar, 2, RoundingMode.HALF_UP);
									if (per.compareTo(BigDecimal.valueOf(100)) > 0) {
								        per = BigDecimal.valueOf(100);
								    }
								    CommonFunctions.insertCell(table, String.valueOf(per+"%"), Element.ALIGN_RIGHT, 1, 1, bf8);
								} else {
									CommonFunctions.insertCell(table, String.valueOf("0.00%"), Element.ALIGN_RIGHT, 1, 1, bf8);
								}
								j++;
							}
							
						phyTarTot = phyTarTot.add(beanList.get(i).getPlan() == null ? BigDecimal.ZERO : beanList.get(i).getPlan());
						 phyAchTot = phyAchTot.add(beanList.get(i).getAchievement() == null ? BigDecimal.ZERO : beanList.get(i).getAchievement());
						 if (phyTarTot.compareTo(BigDecimal.ZERO) != 0) {
							    totTotal = phyAchTot.multiply(new BigDecimal("100")).divide(phyTarTot, 2, RoundingMode.HALF_UP);
							} else {
								totTotal= BigDecimal.ZERO;
							}

								j=0;
								actSeq++;
							}
							
				if(beanList.size()==0) {				
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 17, 1, bf8);		
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
			response.setHeader("Content-Disposition", "attachment;filename=A3-Report.pdf");		
			response.setHeader("Pragma", "public");		
			response.setContentLength(baos.size());		
			OutputStream os = response.getOutputStream();		
			baos.writeTo(os);		
			os.flush();		
			os.close();		
			} 	catch (Exception ex) 	
		{
				ex.printStackTrace();	
				
		} 		
		return null;	
				
	}	
	

	@RequestMapping(value = "/getPhyTarAchievExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getPhyTarAchievExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		int stCode= Integer.parseInt(request.getParameter("stCode"));
		int distCode= Integer.parseInt(request.getParameter("distCode"));
		int projectId= Integer.parseInt(request.getParameter("projId"));
		int fromYear= Integer.parseInt(request.getParameter("fYear"));
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		List<PhysicalActionAchievementBean> beanList = new ArrayList<PhysicalActionAchievementBean>();
		beanList=psa.getphysicalTarAchReport(stCode, distCode, projectId,fromYear);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report A3- Achievements Against Annual Physical Action Plan Targets");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report A3- State, District, Project and Year-wise Details of Achievements Against Annual Physical Action Plan Targets as per List of Activities";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 4, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,4);
	        sheet.addMergedRegion(mergedRegion);
			
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("State: "+stName+"  District: "+distName + "  Project: "+ projName + "  Financial Year: "+yearName);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			Row rowhead1 = sheet.createRow(6);
			cell = rowhead1.createCell(0);
			cell.setCellValue("Name of the Activity");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(1);
			cell.setCellValue("Unit");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(2);
			cell.setCellValue("Physical Target");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Physical Achievement");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Achievement % (4*100/3)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell.setCellStyle(style);
			
			Row rowhead3 = sheet.createRow(7);
			
			for(int i =0;i<5;i++) {
				cell = rowhead3.createCell(i);
				cell.setCellValue(i+1);  
				cell.setCellStyle(style);
			}
	        int rowno  =8;
	        int oldHeadCode  =0;
	        int headSeq = 1;
	        int oldActCd = 0;
	        int actSeq = 1;
	        int rowCnt = 0;
	        double phyTar = 0.0;
	        double phyAch = 0.0;
	        Row row = sheet.createRow(rowno);
	        for(PhysicalActionAchievementBean bean: beanList) {
	        	if(bean.getHead_code()!=oldHeadCode) {
	        		if(headSeq!=1 && headSeq < 9) {
	        			mergedRegion = new CellRangeAddress(rowno,rowno,0,1);
	        		    sheet.addMergedRegion(mergedRegion);
	        			cell = row.createCell(0);
	        			cell.setCellValue("Sub-Total");
	        			cell.setCellStyle(style);
	        			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
	        			row.createCell(1).setCellStyle(style);
	        			cell = row.createCell(2);
	        			cell.setCellValue(phyTar);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(3);
	        			cell.setCellValue(phyAch);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(4);
	        			cell.setCellValue(phyTar);
	        			try (Formatter formatter = new Formatter()) {
							formatter.format("%.2f%%", (phyAch*100)/phyTar);
							cell.setCellValue(formatter.toString());
							cell.setCellStyle(style);
							CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
						}
	        			rowno++;
	        			rowCnt++;
	        			row = sheet.createRow(rowno);
	        		}
	        		phyTar = 0.0;
	        		phyAch=0.0;
	        		row.createCell(0).setCellValue(headSeq+"."+" "+bean.getHead_desc());
	        		
	        		headSeq++;
	        		rowno++;
	        		rowCnt++;
	        		actSeq = 1;
	        		oldHeadCode = bean.getHead_code();
	        		row = sheet.createRow(rowno);
	        	}
	        	oldActCd = bean.getActivity_code();
	        	if(bean.getActivity_code()==oldActCd) {
	        			row.createCell(0).setCellValue(headSeq-1+"."+actSeq+" "+bean.getActivity_desc());
		        		row.createCell(1).setCellValue(bean.getUnitname());
		        		row.createCell(2).setCellValue(bean.getPlan().doubleValue());
		        		row.createCell(3).setCellValue(bean.getAchievement().doubleValue());
		        		
		        		double Ach = bean.getAchievement().doubleValue();
		        		double tar = bean.getPlan().doubleValue();

		        		if (tar != 0) {
		        		    double per = Math.min((Ach / tar) * 100, 100.0);
		        		    String formattedPer = String.format("%.2f%%", per);
		        		    row.createCell(4).setCellValue(formattedPer);
		        		} else {
		        		    row.createCell(4).setCellValue("0.00%");
		        	
		        		}

	        					phyAch = phyAch + bean.getAchievement().doubleValue();
	    		        		phyTar = phyTar + bean.getPlan().doubleValue();
	        					actSeq++;
	        					rowno++;
	        					rowCnt++;
	        					row = sheet.createRow(rowno);
	        				}
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
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowCnt, 4);
	        String fileName = "attachment; filename=Report A3.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/physicalTarAchReport";
		
	}


}
