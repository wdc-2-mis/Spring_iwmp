package app.controllers.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
//import org.apache.catalina.connector.ClientAbortException;
//import org.apache.catalina.connector.ClientAbortException;
import java.io.IOException;

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
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.List;


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

import app.bean.ProjectLocationBean;
import app.bean.ProjectSanctionedBean;
import app.bean.ProjectState;
import app.bean.RoleMenuList;
import app.bean.pfms.PfmsStwiseExpndtreBean;
import app.bean.reports.SelfHelpGroupReportBean;
import app.common.CommonFunctions;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.model.project.WdcpmksyProjectPhysicalAchievementDetails;
import app.service.AddProjectService;
import app.service.CommonService;
import app.service.DistrictMasterService;
import app.service.StateMasterService;

@Controller
public class RptJ5Sanctionedproject {
	HttpSession session;

	@Autowired(required = true)
	private AddProjectService projectService;
	@Autowired
	DistrictMasterService districtMasterService;

	@Autowired(required = true)
	private CommonService commonService;

	@Autowired(required = true)
	StateMasterService stateMasterService;
	
	private static final Logger logger = Logger.getLogger(RptJ5Sanctionedproject.class);

	public RptJ5Sanctionedproject() {
	
	}
	
	@RequestMapping(value = "/rptProjectStatus", method = RequestMethod.GET)
	public String sanctionedProject(Locale locale, Model model) throws IOException {
		IwmpMProject proj=new IwmpMProject();
		model.addAttribute("statelist", stateMasterService.getAllState());
		model.addAttribute("iwmpProj", proj);
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode(0));
		return("/reports/RptJ5Sanctionedproject");
		}
	

	
//	@RequestMapping(value = "/displayProject", params = "onchange", method = RequestMethod.POST)
//	public String onChangeDistrict(@ModelAttribute("iwmpProj") IwmpMProject projectDetail, BindingResult result,
//			Model model) {
//		model.addAttribute("statelist", stateMasterService.getAllState());
//		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode((int)projectDetail.getIwmpDistrict().getIwmpState().getStCode()));
//		model.addAttribute("financialYear", commonService.getAllFinancialYear());
//		List<IwmpMProject> projectList=null;
//		model.addAttribute("projectList", projectList);
//		return "/reports/RptJ5Sanctionedproject";
//	}
	@RequestMapping(value = "/displayProject", params = "go", method = RequestMethod.POST)
	public String onGo(@ModelAttribute("iwmpProj") IwmpMProject projectDetail, BindingResult result,
			Model model, HttpServletRequest request) {
		
		String projectId = request.getParameter("projectDetail");
		List<IwmpMProject> projectList=null;
		List<ProjectSanctionedBean> list=new  ArrayList<ProjectSanctionedBean>();
		System.out.println((int)projectDetail.getIwmpMFinYear().getFinYrCd()+" kdy");
		if((int)projectDetail.getIwmpDistrict().getIwmpState().getStCode()==0)
		list=projectService.getProjectSanctioned((int)projectDetail.getIwmpMFinYear().getFinYrCd());
		else
		projectList=projectService.getListSanctionedProjectOpen((int)projectDetail.getIwmpDistrict().getIwmpState().getStCode(),(int)projectDetail.getIwmpDistrict().getDcode(),(int)projectDetail.getIwmpMFinYear().getFinYrCd());	
		
		model.addAttribute("statelist", stateMasterService.getAllState());
		model.addAttribute("districtList", districtMasterService.getDistrictByStateCode((int)projectDetail.getIwmpDistrict().getIwmpState().getStCode()));
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("projectList", projectList);
		model.addAttribute("stateprojectList", list);
		model.addAttribute("stcode",projectDetail.getIwmpDistrict().getIwmpState().getStCode());
		model.addAttribute("distcode", projectDetail.getIwmpDistrict().getDcode());
		model.addAttribute("finyear", projectDetail.getIwmpMFinYear().getFinYrCd());
		model.addAttribute("projectDetail", projectId);
		return "/reports/RptJ5Sanctionedproject";
	}
	
	@RequestMapping(value = "/downloaddisplayProjectPDF", method = RequestMethod.POST)
	public ModelAndView downloaddisplayProjectPDF( HttpServletRequest request, HttpServletResponse response)
	{
		//WDC-PMKSY-0001113
		String stcd=request.getParameter("stcode"); 
		String distcode=request.getParameter("distcode");  
		String finyear=request.getParameter("finyear"); 
		 		List<IwmpMProject> projectList = new ArrayList<IwmpMProject>();
		 		projectList=projectService.getListSanctionedProjectOpen(Integer.parseInt(stcd), Integer.parseInt(distcode), Integer.parseInt(finyear));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("displayProjectReport");
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
			
	        int m=Integer.parseInt(finyear);    
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
			if(Integer.parseInt(finyear)==0) {
				paragraph3 = new Paragraph("Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central/State Share) for All Financial Year", f3);
			}
			else {
				paragraph3 = new Paragraph("Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central/State Share) For Financial Year  "+"' " +"20"+finyear+ "-"  +(m+1)+" '", f3);
			}
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(10);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				BigDecimal areaProposed= BigDecimal.valueOf(0);
				BigDecimal projectCost= BigDecimal.valueOf(0);
				BigDecimal centralShareAmt= BigDecimal.valueOf(0);
				BigDecimal stateShareAmt= BigDecimal.valueOf(0);
				
				CommonFunctions.insertCellHeader(table, "All amounts(in Rs lakhs), All Area in Ha.", Element.ALIGN_RIGHT, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Financial Year ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Sanction Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Cost", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Central Share", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Share", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int k=1;
				 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
				if(projectList.size()!=0)
					for(int i=0;i<projectList.size();i++) 
					{
						
						

						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projectList.get(i).getIwmpDistrict().getIwmpState().getStName() , Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projectList.get(i).getIwmpDistrict().getDistName() , Element.ALIGN_LEFT, 1, 1, bf8);
						
						String finYrDesc = "";
						IwmpMProject project = projectList.get(i);
						IwmpMFinYear finYear = project.getProjectSanctionYr();
						if (finYear != null) {
						    finYrDesc = finYear.getFinYrDesc();
						}
						CommonFunctions.insertCell(table, finYrDesc, Element.ALIGN_CENTER, 1, 1, bf8);
						CommonFunctions.insertCell(table, projectList.get(i).getProjName(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						String projectSanctionDt = "";
						IwmpMProject proj = projectList.get(i);
						if (proj.getProjectSanctionDt() != null) {
						    projectSanctionDt = formatter.format(proj.getProjectSanctionDt());
						}
						CommonFunctions.insertCell(table, projectSanctionDt, Element.ALIGN_LEFT, 1, 1, bf8);

						
						CommonFunctions.insertCell(table, projectList.get(i).getAreaProposed() == null ? "" : String.format(Locale.US, "%.4f", projectList.get(i).getAreaProposed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projectList.get(i).getProjectCost() == null ? "" : String.format(Locale.US, "%.2f", projectList.get(i).getProjectCost()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projectList.get(i).getCentralShareAmt() == null ? "" : String.format(Locale.US, "%.2f", projectList.get(i).getCentralShareAmt()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projectList.get(i).getStateShareAmt() == null ? "" : String.format(Locale.US, "%.2f", projectList.get(i).getStateShareAmt()), Element.ALIGN_RIGHT, 1, 1, bf8);
						

						k=k+1;
						areaProposed=areaProposed.add(projectList.get(i).getAreaProposed()==null?BigDecimal.ZERO:projectList.get(i).getAreaProposed());
						projectCost=projectCost.add(projectList.get(i).getProjectCost()==null?BigDecimal.ZERO:projectList.get(i).getProjectCost());
						centralShareAmt=centralShareAmt.add(projectList.get(i).getCentralShareAmt()==null?BigDecimal.ZERO:projectList.get(i).getCentralShareAmt());
						stateShareAmt=stateShareAmt.add(projectList.get(i).getStateShareAmt()==null?BigDecimal.ZERO:projectList.get(i).getStateShareAmt());
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 6, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.4f",areaProposed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",projectCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",centralShareAmt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",stateShareAmt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(projectList.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 10, 1, bf8);
					
						
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
		response.setHeader("Content-Disposition", "attachment;filename=P1-Report.pdf");
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
	
	@RequestMapping(value = "/downloadExcelDisplayProject", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDisplayProject(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcd=request.getParameter("stcode"); 
		String distcode=request.getParameter("distcode");  
		String finyear=request.getParameter("finyear"); 
		 		
		List<IwmpMProject> list = new ArrayList<IwmpMProject>();
		 		
		list=projectService.getListSanctionedProjectOpen(Integer.parseInt(stcd), Integer.parseInt(distcode), Integer.parseInt(finyear));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central or State Share)");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
			
			int y=Integer.parseInt(finyear);
			String rptName1 = "Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central/State Share) for All Financial Year";
			String rptName2 = "Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central/State Share) for Financial Year "+"' "+"20"+finyear+"-"+(y+1)+" '";
			
			String rptName = (Integer.parseInt(finyear)==0)?rptName1:rptName2;
			
			String areaAmtValDetail = "All amounts(in Rs lakhs) And All Area in Ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);
			
        
	        mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,5); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(3);
			cell.setCellValue("Financial Year");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Project Sanction Date");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Area Sanctioned");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Total Cost");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Central Share");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("State Share");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i=0;i<10;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 7;
	        double totAreaSan = 0.0;
	        double totCost = 0.0;
	        double totCenShare = 0.0;
	        double totStateShare = 0.0;
	        
	        String finYrDesc = "";
	        String ProjSanDt = "";
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        
	        for(IwmpMProject bean: list) {
	        	IwmpMFinYear finyear1 = bean.getProjectSanctionYr();
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getIwmpDistrict().getIwmpState().getStName());
	        	row.createCell(2).setCellValue(bean.getIwmpDistrict().getDistName());

	        	if (finyear1 != null) {
				    row.createCell(3).setCellValue(bean.getProjectSanctionYr().getFinYrDesc());
				}
	        	else {
	        	row.createCell(3).setCellValue(finYrDesc);
	        	}
	        	row.createCell(4).setCellValue(bean.getProjName());
	        	
	        	if (bean.getProjectSanctionDt() != null) {
	        		row.createCell(5).setCellValue(formatter.format(bean.getProjectSanctionDt()));
				}
	        	else {
	        	row.createCell(5).setCellValue(ProjSanDt);
	        	}
	        	row.createCell(6).setCellValue(bean.getAreaProposed()==null?0.0:bean.getAreaProposed().doubleValue());
	        	row.createCell(7).setCellValue(bean.getProjectCost()==null?0.0:bean.getProjectCost().doubleValue());
	        	row.createCell(8).setCellValue(bean.getCentralShareAmt()==null?0.0:bean.getCentralShareAmt().doubleValue());
	        	row.createCell(9).setCellValue(bean.getStateShareAmt()==null?0.0:bean.getStateShareAmt().doubleValue());
	        	
	        	
	        	totAreaSan = totAreaSan + (bean.getAreaProposed()==null?0.0:bean.getAreaProposed().doubleValue());
	        	totCost = totCost + (bean.getProjectCost()==null?0.0:bean.getProjectCost().doubleValue());
	        	totCenShare = totCenShare + (bean.getCentralShareAmt()==null?0.0:bean.getCentralShareAmt().doubleValue());
	        	totStateShare = totStateShare + (bean.getStateShareAmt()==null?0.0:bean.getStateShareAmt().doubleValue());

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
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totAreaSan);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totCost);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totCenShare);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totStateShare);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);
	        String fileName = "attachment; filename=Report P1.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "/reports/RptJ5Sanctionedproject";
		
	}
	
	@RequestMapping(value = "/downloadExcelAllStates", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelAllStates(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String finyear=request.getParameter("finyear"); 
		 		
		List<ProjectSanctionedBean> list=new  ArrayList<ProjectSanctionedBean>();
		
		list=projectService.getProjectSanctioned(Integer.parseInt(finyear));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central or State Share)");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
			
			int y=Integer.parseInt(finyear);
			String rptName1 = "Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central/State Share) for All Financial Year";
			String rptName2 = "Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central/State Share) for Financial Year "+"' "+"20"+finyear+"-"+(y+1)+" '";
			
			String rptName = (Integer.parseInt(finyear)==0)?rptName1:rptName2;
			
			String areaAmtValDetail = "All amounts(in Rs lakhs) And All Area in Ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
			
        
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
			cell.setCellValue("District");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(3);
			cell.setCellValue("Project");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Area Sanctioned");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Total Cost");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Central Share");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("State Share");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i=0;i<8;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno = 7;
	        int totDist = 0;
	        int totProj = 0;
	        double totArea = 0.0;
	        double totCost = 0.0;
	        double totCenShare = 0.0;
	        double totStateShare = 0.0;
	        
	        
	        for(ProjectSanctionedBean bean: list) {
	        	
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getSt_name());
	        	row.createCell(2).setCellValue(bean.getDistrict());
	        	row.createCell(3).setCellValue(bean.getProject());
	        	row.createCell(4).setCellValue(bean.getArea()==null?0.0:bean.getArea().doubleValue());
	        	row.createCell(5).setCellValue(bean.getCost()==null?0.0:bean.getCost().doubleValue());
	        	row.createCell(6).setCellValue(bean.getCentral()==null?0.0:bean.getCentral().doubleValue());
	        	row.createCell(7).setCellValue(bean.getState()==null?0.0:bean.getState().doubleValue());
	        	
	        	totDist = totDist + bean.getDistrict();
	        	totProj = totProj + bean.getProject();
	        	totArea = totArea + (bean.getArea()==null?0.0:bean.getArea().doubleValue());
	        	totCost = totCost + (bean.getCost()==null?0.0:bean.getCost().doubleValue());
	        	totCenShare = totCenShare + (bean.getCentral()==null?0.0:bean.getCentral().doubleValue());
	        	totStateShare = totStateShare + (bean.getState()==null?0.0:bean.getState().doubleValue());

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
	        cell.setCellValue(totProj);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCost);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totCenShare);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totStateShare);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 7);
	        String fileName = "attachment; filename=Report P1.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "/reports/RptJ5Sanctionedproject";
		
	}
	
@RequestMapping(value = "/downloadAllStatesPDF", method = RequestMethod.POST)
public ModelAndView downloadAllStatesPDF( HttpServletRequest request, HttpServletResponse response)
{
		  
		String finyear=request.getParameter("finyear"); 
		
		List<ProjectSanctionedBean> list=new  ArrayList<ProjectSanctionedBean>();
		
		list=projectService.getProjectSanctioned(Integer.parseInt(finyear));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("displayProjectReport");
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
			
	        int m=Integer.parseInt(finyear);    
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
			if(Integer.parseInt(finyear)==0) {
				paragraph3 = new Paragraph("Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central/State Share) for All Financial Year", f3);
			}
			else {
				paragraph3 = new Paragraph("Report P1- State-wise,District-wise and Year-wise Details of Projects Sanctioned including Area Sanctioned, Total Cost(Central/State Share) For Financial Year  "+"' " +"20"+finyear+ "-"  +(m+1)+" '", f3);
			}
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(8);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				
				
				CommonFunctions.insertCellHeader(table, "All amounts(in Rs lakhs), All Area in Ha.", Element.ALIGN_RIGHT, 8, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Cost", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Central Share", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Share", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				int totDist = 0;
		        int totProj = 0;
				BigDecimal totArea= BigDecimal.valueOf(0);
				BigDecimal totCost= BigDecimal.valueOf(0);
				BigDecimal totCenShare= BigDecimal.valueOf(0);
				BigDecimal totStateShare= BigDecimal.valueOf(0);

				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{

						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSt_name() , Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getDistrict()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProject()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea() == null ? "" : String.format(Locale.US, "%.4f", list.get(i).getArea()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCost() == null ? "" : String.format(Locale.US, "%.2f", list.get(i).getCost()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCentral() == null ? "" : String.format(Locale.US, "%.2f", list.get(i).getCentral()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getState() == null ? "" : String.format(Locale.US, "%.2f", list.get(i).getState()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						totDist = totDist + list.get(i).getDistrict();
						totProj = totProj + list.get(i).getProject();
						totArea=totArea.add(list.get(i).getArea()==null?BigDecimal.ZERO:list.get(i).getArea());
						totCost=totCost.add(list.get(i).getCost()==null?BigDecimal.ZERO:list.get(i).getCost());
						totCenShare=totCenShare.add(list.get(i).getCentral()==null?BigDecimal.ZERO:list.get(i).getCentral());
						totStateShare=totStateShare.add(list.get(i).getState()==null?BigDecimal.ZERO:list.get(i).getState());
						
						k=k+1;
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totDist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totProj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.4f",totArea), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",totCost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",totCenShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",totStateShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
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
		document.close();
		response.setContentType("application/pdf");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-Disposition", "attachment;filename=P1-Report.pdf");
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
