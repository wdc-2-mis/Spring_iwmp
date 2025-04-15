package app.projectevaluation.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
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
import org.apache.poi.ss.usermodel.VerticalAlignment;
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

import app.bean.Login;
import app.common.CommonFunctions;
import app.projectevaluation.bean.ProjectEvaluationBean;
import app.projectevaluation.model.WdcpmksyProjectProfileEvaluation;
import app.projectevaluation.service.ProjectEvaluationService;
import app.service.DistrictMasterService;
import app.service.ProjectMasterService;

@Controller("PEReportController")
public class PEReportController {

	HttpSession session;

	@Autowired
	DistrictMasterService districtMasterService;

	@Autowired(required = true)
	ProjectMasterService projectMasterService;

	@Autowired
	ProjectEvaluationService PEService;

	@RequestMapping(value = "/projEvoluationRpt", method = RequestMethod.GET)
	public ModelAndView projEvoluationRpt(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav;
		HttpSession session = request.getSession(false); // Don't create a new session if one doesn't exist

		if (session != null && session.getAttribute("loginID") != null) {
			Integer stateCode = (session.getAttribute("stateCode") != null)
					? Integer.parseInt(session.getAttribute("stateCode").toString())
					: null;
			if (stateCode != null) {
				mav = new ModelAndView("projectEvaluation/projectEvolReport");
				mav.addObject("districtList", districtMasterService.getDistrictByStateCodeWithDcode(stateCode));
			} else {
				mav = new ModelAndView("errorPage");
			}
		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}

		return mav;
	}

	@RequestMapping(value = "/getprojectforProjEva", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getprojectforProjEva(HttpServletRequest request,
			@RequestParam(value = "dCode") Integer dCode) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		map = PEService.getProjByDCode(dCode);
		return map;
	}

	@RequestMapping(value = "/getprojEvoRptdata", method = RequestMethod.POST)
	@ResponseBody
	public List<ProjectEvaluationBean> getprojEvoRptDetails(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pCode") Integer pCode) {
		ModelAndView mav = new ModelAndView();
		ProjectEvaluationBean bean = new ProjectEvaluationBean();
		List<WdcpmksyProjectProfileEvaluation> list = new ArrayList<WdcpmksyProjectProfileEvaluation>();
		List<ProjectEvaluationBean> finallist = new ArrayList<ProjectEvaluationBean>();
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("loginID") != null) {
			list = PEService.getprojectevorptdata(pCode);

			if ((list != null) && (list.size() > 0)) {
				for (WdcpmksyProjectProfileEvaluation data : list) {
					bean = new ProjectEvaluationBean();
					bean.setPro_profileid(data.getProjectProfileId());
					bean.setFin_yr(data.getIwmpMFinYear().getFinYrDesc());
					bean.setProjname(data.getIwmpMProject().getProjName());
					bean.setDistrict(data.getIwmpMProject().getIwmpDistrict().getDcode());
					bean.setDistname(data.getIwmpMProject().getIwmpDistrict().getDistName());
					bean.setMonthname(data.getIwmpMMonth().getMonthName());
					finallist.add(bean);
				}
			} else {
				list = null;

			}
		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}

		return finallist;
	}

	@RequestMapping(value = "/stateMidProjEvoluationRpt", method = RequestMethod.GET)
	public ModelAndView stateMidProjEvoluationRpt(HttpServletRequest request, HttpServletResponse response) {
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		ModelAndView mav = new ModelAndView("projectEvaluation/StateMidProjEvoluationRpt");
		
		list = PEService.getStateMidProjEvoluation();
		
		mav.addObject("stateMidPrjEvlList",list);
		mav.addObject("stateMidPrjEvlListSize",list.size());
		
		return mav;
	}
	
	
	@RequestMapping(value = "/downloadExcelStMidProjEvoluation", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStMidProjEvoluation(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getStateMidProjEvoluation();
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report PE1 - State-wise Mid Term Project Evaluation Entry Status");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE1 - State-wise Mid Term Project Evaluation Entry Status";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,3,3);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,4,4);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,5,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,6,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,7,9);
		sheet.addMergedRegion(mergedRegion);
		
		Row rowhead = sheet.createRow(5);
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Project");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("District");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Block");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Gram Panchayat");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Village");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Project Evaluation Status");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=8;i<10;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<7;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Completed");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Under Process");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Not Entered");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<10;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 8;
		int totDist = 0;
		int totProj = 0;
		int totBlk = 0;
		int totGP = 0;
		int totVlg = 0;
		int totComplete = 0;
		int totProcess = 0;
		int totNotEntr = 0;
		
		
	    for(ProjectEvaluationBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getSt_name());
	    	row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getDistrict());
	    	row.createCell(4).setCellValue(bean.getBlock());
	    	row.createCell(5).setCellValue(bean.getGp());
	    	row.createCell(6).setCellValue(bean.getVillage());
	    	row.createCell(7).setCellValue(bean.getCompleted());
	    	row.createCell(8).setCellValue(bean.getProcess());
	    	row.createCell(9).setCellValue(bean.getNot_entered());
	    	
	    	totProj = totProj + bean.getTotal_project();
	    	totDist = totDist + bean.getDistrict();
	    	totBlk = totBlk + bean.getBlock();
	    	totGP = totGP + bean.getGp();
	    	totVlg = totVlg + bean.getVillage();
	    	totComplete = totComplete + bean.getCompleted();
	    	totProcess = totProcess + bean.getProcess();
	    	totNotEntr = totNotEntr + bean.getNot_entered();
	    	
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
		cell.setCellValue(totProj);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totDist);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totBlk);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totGP);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totVlg);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totComplete);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totProcess);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totNotEntr);
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);
	    String fileName = "attachment; filename=Report PE1- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "projectEvaluation/StateMidProjEvoluationRpt";
	}
	
	@RequestMapping(value = "/downloadPDFStMidProjEvoluation", method = RequestMethod.POST)
	public ModelAndView downloadPDFStMidProjEvoluation(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getStateMidProjEvoluation();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("MidTermProjEvlReport");
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
			
			paragraph3 = new Paragraph("Report PE1 - State-wise Mid Term Project Evaluation Entry Status", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(10);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(60);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(3);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Block", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Gram Panchayat", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Village", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Evaluation Status", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Under Process", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Entered", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
		    
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
			
			
			int k = 1;
			int totDist = 0;
			int totProj = 0;
			int totBlk = 0;
			int totGP = 0;
			int totVlg = 0;
			int totComplete = 0;
			int totProcess = 0;
			int totNotEntr = 0;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getDistrict()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getBlock()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVillage()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCompleted()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProcess()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNot_entered()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					totProj = totProj + list.get(i).getTotal_project();
				    totDist = totDist + list.get(i).getDistrict();
			    	totBlk = totBlk + list.get(i).getBlock();
			    	totGP = totGP + list.get(i).getGp();
			    	totVlg = totVlg + list.get(i).getVillage();
			    	totComplete = totComplete + list.get(i).getCompleted();
			    	totProcess = totProcess + list.get(i).getProcess();
			    	totNotEntr = totNotEntr + list.get(i).getNot_entered();
					
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totProj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totDist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totBlk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGP), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totVlg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totComplete), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totProcess), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totNotEntr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 10, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE1- State.pdf");
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
