package app.projectevaluation.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import app.service.StateMasterService;

@Controller("PEReportController")
public class PEReportController {

	HttpSession session;

	@Autowired
	DistrictMasterService districtMasterService;

	@Autowired(required = true)
	ProjectMasterService projectMasterService;

	@Autowired
	ProjectEvaluationService PEService;
	
	@Autowired
	StateMasterService stateMasterService;

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
		
		request.getSession().removeAttribute("stcd");
	    request.getSession().removeAttribute("stName");

		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		ModelAndView mav = new ModelAndView("projectEvaluation/StateMidProjEvoluationRpt");
		
		list = PEService.getStateMidProjEvoluation();
		
		mav.addObject("stateMidPrjEvlList",list);
		mav.addObject("stateMidPrjEvlListSize",list.size());
		
		response.setHeader("Cache-Control", "public, max-age=600");

		return mav;
	}
	
	@RequestMapping(value = "/distMidProjEvoluationRpt", method = RequestMethod.GET)
	public ModelAndView distMidProjEvoluationRpt(HttpServletRequest request, HttpServletResponse response) {
		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		ModelAndView mav = new ModelAndView("projectEvaluation/DistMidProjEvoluationRpt");
		
		list = PEService.getDistMidProjEvoluation(Integer.parseInt(stcd));
		
		mav.addObject("stcd",stcd);
		mav.addObject("stName",stName);
		mav.addObject("distMidPrjEvlList",list);
		mav.addObject("distMidPrjEvlListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/projMidProjEvoluationRpt", method = RequestMethod.GET)
	public ModelAndView projMidProjEvoluationRpt(HttpServletRequest request, HttpServletResponse response) {
		
		String stName = request.getParameter("stName");
		String distName = request.getParameter("dName");
		String dCode = request.getParameter("distcd");
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		ModelAndView mav = new ModelAndView("projectEvaluation/ProjMidProjEvoluationRpt");
		
		list = PEService.getprojMidProjEvoluation(Integer.parseInt(dCode));
		
		mav.addObject("dcode",dCode);
		mav.addObject("dName",distName);
		mav.addObject("stName",stName);
		mav.addObject("projMidPrjEvlList",list);
		mav.addObject("projMidPrjEvlListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/downloadPDFProjMidProjEvolWork", method = RequestMethod.POST)
	public ModelAndView downloadPDFProjMidProjEvolWork(HttpServletRequest request, HttpServletResponse response)
	{
		String stName = request.getParameter("stName");
		String dCode = request.getParameter("dcode");
		String dName = request.getParameter("dName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getprojMidProjEvolWorkDtl(Integer.parseInt(dCode));
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
			
			paragraph3 = new Paragraph("Report PE11 - Project-wise Mid Term Project Evaluation of Geotagged Work Details", f3);
			
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
		    table.setHeaderRows(3);
		    
      	    
		    CommonFunctions.insertCellHeader(table, "State Name : "+stName + ", District Name : "+dName, Element.ALIGN_LEFT, 7, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Area of Shape Files", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Geotagged Works", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			for (int i = 1; i <= 7; i++) {
			    CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			int k = 1;
			int totCreated = 0;
			int totOngoing = 0;
			int totCompleted = 0;
			BigDecimal totShapeFie = BigDecimal.ZERO;
			int totGeoTagg = 0;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProj_name()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCreated_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOngoing_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCompleted_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getShape_file_area()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGeo_tagg_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					totCreated = totCreated + list.get(i).getCreated_work();
					totOngoing = totOngoing + list.get(i).getOngoing_work();
					totCompleted = totCompleted + list.get(i).getCompleted_work();
					totShapeFie = totShapeFie.add(list.get(i).getShape_file_area());
                    totGeoTagg = totGeoTagg + list.get(i).getGeo_tagg_work();
			    	
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCreated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totOngoing), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCompleted), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totShapeFie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGeoTagg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE11- Project.pdf");
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
	
	
	@RequestMapping(value = "/stateMidProjEvlCropDetailsRpt", method = RequestMethod.GET)
	public ModelAndView stateMidProjEvlCropDetailsRpt(HttpServletRequest request, HttpServletResponse response) {
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		ModelAndView mav = new ModelAndView("projectEvaluation/StateMidProjEvlCropDetailsRpt");
		
		list = PEService.getStateMidProjEvlCropDetails();
		
		mav.addObject("stateMidPrjEvlCrpDetailsList",list);
		mav.addObject("stateMidPrjEvlCrpDetailsListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/distMidProjEvlCropDetailsRpt", method = RequestMethod.GET)
	public ModelAndView distMidProjEvlCropDetailsRpt(HttpServletRequest request, HttpServletResponse response) {
		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		ModelAndView mav = new ModelAndView("projectEvaluation/DistMidProjEvlCropDetailsRpt");
		
		list = PEService.getDistMidProjEvlCropDetails(Integer.parseInt(stcd));
		
		mav.addObject("stcd",stcd);
		mav.addObject("stName",stName);
		mav.addObject("distMidPrjEvlCrpDetailsList",list);
		mav.addObject("distMidPrjEvlCrpDetailsListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/stateMidProjEvlWorkDetailsRpt", method = RequestMethod.GET)
	public ModelAndView stateMidProjEvlWorkDetailsRpt(HttpServletRequest request, HttpServletResponse response) {
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		ModelAndView mav = new ModelAndView("projectEvaluation/StateMidProjEvlWorkDetailsRpt");
		
		list = PEService.getStateMidProjEvlWorkDetails();
		
		mav.addObject("stateMidPrjEvlWrkDetailsList",list);
		mav.addObject("stateMidPrjEvlWrkDetailsListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/distMidProjEvlWorkDetailsRpt", method = RequestMethod.GET)
	public ModelAndView distMidProjEvlWorkDetailsRpt(HttpServletRequest request, HttpServletResponse response) {
		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		ModelAndView mav = new ModelAndView("projectEvaluation/DistMidProjEvlWorkDetailsRpt");
		
		list = PEService.getDistMidProjEvlWorkDetails(Integer.parseInt(stcd));
		
		mav.addObject("stcd",stcd);
		mav.addObject("stName",stName);
		mav.addObject("distMidPrjEvlWrkDetailsList",list);
		mav.addObject("distMidPrjEvlWrkDetailsListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/projMidProjEvolWorkDtlRpt", method = RequestMethod.GET)
	public ModelAndView projMidProjEvolWorkDtlRpt(HttpServletRequest request, HttpServletResponse response) {
		
		String stName = request.getParameter("stName");
		String distName = request.getParameter("dName");
		String dCode = request.getParameter("distcd");
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		ModelAndView mav = new ModelAndView("projectEvaluation/ProjMidProjEvolWorkRpt");
		
		list = PEService.getprojMidProjEvolWorkDtl(Integer.parseInt(dCode));
		
		mav.addObject("dcode",dCode);
		mav.addObject("dName",distName);
		mav.addObject("stName",stName);
		mav.addObject("projMidPrjEvlWorkDtlList",list);
		mav.addObject("projMidPrjEvlWorkDtlListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/projMidProjEvlCropDetailsRpt", method = RequestMethod.GET)
	public ModelAndView projMidProjEvlCropDetailsRpt(HttpServletRequest request, HttpServletResponse response) {
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		
		ModelAndView mav = new ModelAndView();
		try {
				mav = new ModelAndView("projectEvaluation/ProjMidProjEvlCropDetailsRpt");
				List<ProjectEvaluationBean> listP = new ArrayList<ProjectEvaluationBean>();
				 listP = PEService.getprojMidProjEvlCropDetailsRpt(dcode);
					
				 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(dcode).entrySet().iterator().next();
				 mav.addObject("stName",stateMasterService.getStateByDistCode(dcode).get(entry.getKey()));	
				 mav.addObject("distName",districtMasterService.getDistrictByDistCode(dcode).get(dcode));	
				 mav.addObject("cropPList",listP);
				mav.addObject("cropListPSize",listP.size());
				mav.addObject("dcode", dcode);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 13, areaAmtValDetail, workbook);
		
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
		mergedRegion = new CellRangeAddress(5,5,10,13);
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
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Project Grade");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=11;i<14;i++)
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
		
		cell = rowhead1.createCell(10);
		cell.setCellValue("Excellent");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(11);
		cell.setCellValue("Very Good");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(12);
		cell.setCellValue("Satisfactory");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(13);
		cell.setCellValue("Average");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<14;i++)
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
		int totGradeE = 0;
		int totGradeG = 0;
		int totGradeS = 0;
		int totGradeA = 0;
		
		
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
	    	row.createCell(10).setCellValue(bean.getGrade_e());
	    	row.createCell(11).setCellValue(bean.getGrade_g());
	    	row.createCell(12).setCellValue(bean.getGrade_s());
	    	row.createCell(13).setCellValue(bean.getGrade_a());
	    	
	    	totProj = totProj + bean.getTotal_project();
	    	totDist = totDist + bean.getDistrict();
	    	totBlk = totBlk + bean.getBlock();
	    	totGP = totGP + bean.getGp();
	    	totVlg = totVlg + bean.getVillage();
	    	totComplete = totComplete + bean.getCompleted();
	    	totProcess = totProcess + bean.getProcess();
	    	totNotEntr = totNotEntr + bean.getNot_entered();
	    	totGradeE = totGradeE + bean.getGrade_e();
	    	totGradeG = totGradeG + bean.getGrade_g();
	    	totGradeS = totGradeS + bean.getGrade_s();
	    	totGradeA = totGradeA + bean.getGrade_a();
	    	
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
		cell = row.createCell(10);
		cell.setCellValue(totGradeE);
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totGradeG);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totGradeS);
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(totGradeA);
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 13);
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
			Document document = new Document(layout, 25, 14, 14, 0);
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
		    paragraph2.setSpacingAfter(14);
		    paragraph3.setSpacingAfter(14);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(14);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
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
			CommonFunctions.insertCellHeader(table, "Project Grade", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Under Process", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Entered", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Excellent", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Very Good", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Satisfactory", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Average", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
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
			
			
			int k = 1;
			int totDist = 0;
			int totProj = 0;
			int totBlk = 0;
			int totGP = 0;
			int totVlg = 0;
			int totComplete = 0;
			int totProcess = 0;
			int totNotEntr = 0;
			int totGradeE = 0;
			int totGradeG = 0;
			int totGradeS = 0;
			int totGradeA = 0;
				
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
					
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGrade_e()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGrade_g()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGrade_s()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGrade_a()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					totProj = totProj + list.get(i).getTotal_project();
				    totDist = totDist + list.get(i).getDistrict();
			    	totBlk = totBlk + list.get(i).getBlock();
			    	totGP = totGP + list.get(i).getGp();
			    	totVlg = totVlg + list.get(i).getVillage();
			    	totComplete = totComplete + list.get(i).getCompleted();
			    	totProcess = totProcess + list.get(i).getProcess();
			    	totNotEntr = totNotEntr + list.get(i).getNot_entered();
					totGradeE = totGradeE + list.get(i).getGrade_e();
			    	totGradeG = totGradeG + list.get(i).getGrade_g();
			    	totGradeS = totGradeS + list.get(i).getGrade_s();
			    	totGradeA = totGradeA + list.get(i).getGrade_a();
			    	
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
				CommonFunctions.insertCell3(table, String.valueOf(totGradeE), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGradeG), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGradeS), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGradeA), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 14, 1, bf8);
				
				
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
	
	@RequestMapping(value = "/downloadExcelDistMidProjEvoluation", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistMidProjEvoluation(HttpServletRequest request, HttpServletResponse response)
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getDistMidProjEvoluation(Integer.parseInt(stcd));
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report PE1 - District-wise Mid Term Project Evaluation Entry Status");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE1 - District-wise Mid Term Project Evaluation Entry Status";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,3,3);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,4,4);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,5,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,6,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,9,12);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State Name : "+ stName);  
		cell.setCellStyle(style);
		
		for(int i=1;i<13;i++)
		{
			cell = rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Project");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Block");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Gram Panchayat");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Village");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Project Evaluation Status");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=7;i<9;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Project Grade");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<13;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<6;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Completed");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Under Process");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Not Entered");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Excellent");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(10);
		cell.setCellValue("Very Good");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(11);
		cell.setCellValue("Satisfactory");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(12);
		cell.setCellValue("Average");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0;i<13;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		int totProj = 0;
		int totBlk = 0;
		int totGP = 0;
		int totVlg = 0;
		int totComplete = 0;
		int totProcess = 0;
		int totNotEntr = 0;
		int totGradeE = 0;
		int totGradeG = 0;
		int totGradeS = 0;
		int totGradeA = 0;
		
		
	    for(ProjectEvaluationBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getDistname());
	    	row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getBlock());
	    	row.createCell(4).setCellValue(bean.getGp());
	    	row.createCell(5).setCellValue(bean.getVillage());
	    	row.createCell(6).setCellValue(bean.getCompleted());
	    	row.createCell(7).setCellValue(bean.getProcess());
	    	row.createCell(8).setCellValue(bean.getNot_entered());
	    	row.createCell(9).setCellValue(bean.getGrade_e());
	    	row.createCell(10).setCellValue(bean.getGrade_g());
	    	row.createCell(11).setCellValue(bean.getGrade_s());
	    	row.createCell(12).setCellValue(bean.getGrade_a());
	    	
	    	totProj = totProj + bean.getTotal_project();
	    	totBlk = totBlk + bean.getBlock();
	    	totGP = totGP + bean.getGp();
	    	totVlg = totVlg + bean.getVillage();
	    	totComplete = totComplete + bean.getCompleted();
	    	totProcess = totProcess + bean.getProcess();
	    	totNotEntr = totNotEntr + bean.getNot_entered();
	    	totGradeE = totGradeE + bean.getGrade_e();
	    	totGradeG = totGradeG + bean.getGrade_g();
	    	totGradeS = totGradeS + bean.getGrade_s();
	    	totGradeA = totGradeA + bean.getGrade_a();
	    	
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
		
		Row row = sheet.createRow(list.size()+9);
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
		cell.setCellValue(totBlk);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totGP);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totVlg);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totComplete);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totProcess);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totNotEntr);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totGradeE);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totGradeG);
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totGradeS);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totGradeA);
		cell.setCellStyle(style1);
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
	    String fileName = "attachment; filename=Report PE1- District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "projectEvaluation/DistMidProjEvoluationRpt";
	}
	
	@RequestMapping(value = "/downloadPDFDistMidProjEvoluation", method = RequestMethod.POST)
	public ModelAndView downloadPDFDistMidProjEvoluation(HttpServletRequest request, HttpServletResponse response)
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getDistMidProjEvoluation(Integer.parseInt(stcd));
		
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
			
			paragraph3 = new Paragraph("Report PE1 - District-wise Mid Term Project Evaluation Entry Status", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(13);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(60);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table, "State Name : "+stName, Element.ALIGN_LEFT, 13, 1, bf8Bold);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Block", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Gram Panchayat", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Village", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Evaluation Status", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Grade", Element.ALIGN_CENTER, 4, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Under Process", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Not Entered", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
            CommonFunctions.insertCellHeader(table, "Excellent", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Very Good", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Satisfactory", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Average", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
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
			
			
			int k = 1;
			int totProj = 0;
			int totBlk = 0;
			int totGP = 0;
			int totVlg = 0;
			int totComplete = 0;
			int totProcess = 0;
			int totNotEntr = 0;
			int totGradeE = 0;
			int totGradeG = 0;
			int totGradeS = 0;
			int totGradeA = 0;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getBlock()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVillage()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCompleted()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProcess()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNot_entered()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGrade_e()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGrade_g()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGrade_s()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGrade_a()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					totProj = totProj + list.get(i).getTotal_project();
			    	totBlk = totBlk + list.get(i).getBlock();
			    	totGP = totGP + list.get(i).getGp();
			    	totVlg = totVlg + list.get(i).getVillage();
			    	totComplete = totComplete + list.get(i).getCompleted();
			    	totProcess = totProcess + list.get(i).getProcess();
			    	totNotEntr = totNotEntr + list.get(i).getNot_entered();
					totGradeE = totGradeE + list.get(i).getGrade_e();
					totGradeG = totGradeG + list.get(i).getGrade_g();
					totGradeS = totGradeS + list.get(i).getGrade_s();
					totGradeA = totGradeA + list.get(i).getGrade_a();
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totProj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totBlk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGP), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totVlg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totComplete), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totProcess), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totNotEntr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGradeE), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGradeG), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGradeS), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGradeA), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 13, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE1- District.pdf");
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
	
	@RequestMapping(value = "/downloadExcelProjMidProjEvoluation", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjMidProjEvoluation(HttpServletRequest request, HttpServletResponse response)
	{
		String stName = request.getParameter("stName");
		String dCode = request.getParameter("dcode");
		String dName = request.getParameter("dName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getprojMidProjEvoluation(Integer.parseInt(dCode));
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report PE1 - Project-wise Mid Term Project Evaluation Entry Status");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE1 - Project-wise Mid Term Project Evaluation Entry Status";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 4, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,4);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State Name : "+ stName + ",   District Name : "+ dName);  
		cell.setCellStyle(style);
		
		for(int i=1;i<5;i++)
		{
			cell = rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Project Name");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Block");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Gram Panchayat");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Village");
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<5;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 8;
		int totBlk = 0;
		int totGP = 0;
		int totVlg = 0;
		
		
	    for(ProjectEvaluationBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getProjname());
	    	row.createCell(2).setCellValue(bean.getBlock());
	    	row.createCell(3).setCellValue(bean.getGp());
	    	row.createCell(4).setCellValue(bean.getVillage());
	    	
	    	
	    	totBlk = totBlk + bean.getBlock();
	    	totGP = totGP + bean.getGp();
	    	totVlg = totVlg + bean.getVillage();
	    	
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
		cell.setCellValue(totBlk);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totGP);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totVlg);
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 4);
	    String fileName = "attachment; filename=Report PE1- Project.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "projectEvaluation/ProjMidProjEvoluationRpt";
	}
	
	@RequestMapping(value = "/downloadPDFProjMidProjEvoluation", method = RequestMethod.POST)
	public ModelAndView downloadPDFProjMidProjEvoluation(HttpServletRequest request, HttpServletResponse response)
	{
		String stName = request.getParameter("stName");
		String dCode = request.getParameter("dcode");
		String dName = request.getParameter("dName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getprojMidProjEvoluation(Integer.parseInt(dCode));
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
			
			paragraph3 = new Paragraph("Report PE1 - Project-wise Mid Term Project Evaluation Entry Status", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(5);
		    table.setWidths(new int[]{2, 8, 5, 5, 5});
		    table.setWidthPercentage(60);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(3);
		    
		    CommonFunctions.insertCellHeader(table, "State Name : "+stName + ", District Name : "+dName, Element.ALIGN_LEFT, 5, 1, bf8Bold);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Block", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Gram Panchayat", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Village", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
			
			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
			
			int k = 1;
			int totBlk = 0;
			int totGP = 0;
			int totVlg = 0;
			
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProjname()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getBlock()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVillage()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					
					totBlk = totBlk + list.get(i).getBlock();
			    	totGP = totGP + list.get(i).getGp();
			    	totVlg = totVlg + list.get(i).getVillage();
			    	
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totBlk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGP), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totVlg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 5, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE1- Project.pdf");
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
	
	@RequestMapping(value = "/downloadExcelStMidProjEvlCropDetails", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStMidProjEvlCropDetails(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getStateMidProjEvlCropDetails();
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report PE3 - State-wise Mid Term Project Evaluation of Cropped Area Details");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE3 - State-wise Mid Term Project Evaluation of Cropped Area Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,3,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,12,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,15,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,18,20);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,6,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,9,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,12,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,13,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,14,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,15,15);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,16,16);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,17,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,18,18);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,19,19);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,20,20);
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
		cell.setCellValue("Gross Cropped Area (ha.)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<12;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(12);
		cell.setCellValue("Total Gross Cropped Area (ha.)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=13;i<15;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(15);
		cell.setCellValue("Area under Plantation Cover (ha.)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=16;i<18;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(18);
		cell.setCellValue("Area of Culturable Wasteland (ha.)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=19;i<21;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Kharif Crop");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<6;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Rabi Crop");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=7;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Third Crop");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<12;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		String[] headers = {
		        "Pre Project Status (Aggregate)",
		        "Mid Project Status (Aggregate)",
		        "Controlled Area"
		    };
		
		
		for (int i = 0; i < 3; i++) {											// Repetition times(group)
	        for (int j = 0; j < headers.length; j++) {							// values
	            cell = rowhead1.createCell(12 + (i * headers.length) + j);		// = start index + repetition * header length + repeat values
	            cell.setCellValue(headers[j]);
	            cell.setCellStyle(style);
	            CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	            CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	        }
	    }


		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		for (int i = 0; i < 3; i++) {											// Repetition times(group)
	        for (int j = 0; j < headers.length; j++) {							// values
	            cell = rowhead2.createCell(3 + (i * headers.length) + j);		// = start index + repetition * header length + repeat values
	            cell.setCellValue(headers[j]);
	            cell.setCellStyle(style);
	            CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	        }
	    }

		
		for(int i=12;i<21;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		
		Row rowhead3 = sheet.createRow(8);
		
		for(int i=0;i<21;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		int totProj = 0;
		BigDecimal totPreKharif = BigDecimal.ZERO;
		BigDecimal totMidKharif = BigDecimal.ZERO;
		BigDecimal totCtlKharif = BigDecimal.ZERO;
		BigDecimal totPreRabi = BigDecimal.ZERO;
		BigDecimal totMidRabi = BigDecimal.ZERO;
		BigDecimal totCtlRabi = BigDecimal.ZERO;
		BigDecimal totPreThrdCrp = BigDecimal.ZERO;
		BigDecimal totMidThrdCrp = BigDecimal.ZERO;
		BigDecimal totCtlThrdCrp = BigDecimal.ZERO;
		BigDecimal totPreTotal = BigDecimal.ZERO;
		BigDecimal totMidTotal = BigDecimal.ZERO;
		BigDecimal totCtlTotal = BigDecimal.ZERO;
		BigDecimal totPrePlt = BigDecimal.ZERO;
		BigDecimal totMidPlt = BigDecimal.ZERO;
		BigDecimal totCtlPlt = BigDecimal.ZERO;
		BigDecimal totPreClt = BigDecimal.ZERO;
		BigDecimal totMidClt = BigDecimal.ZERO;
		BigDecimal totCtlClt = BigDecimal.ZERO;
		
		
	    for(ProjectEvaluationBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getSt_name());
	    	row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getPre_kharif().doubleValue());
	    	row.createCell(4).setCellValue(bean.getMid_kharif().doubleValue());
	    	row.createCell(5).setCellValue(bean.getCtl_kharif().doubleValue());
	    	row.createCell(6).setCellValue(bean.getPre_rabi().doubleValue());
	    	row.createCell(7).setCellValue(bean.getMid_rabi().doubleValue());
	    	row.createCell(8).setCellValue(bean.getCtl_rabi().doubleValue());
	    	row.createCell(9).setCellValue(bean.getPre_thrdcrp().doubleValue());
	    	row.createCell(10).setCellValue(bean.getMid_thrdcrp().doubleValue());
	    	row.createCell(11).setCellValue(bean.getCtl_thrdcrp().doubleValue());
	    	row.createCell(12).setCellValue(bean.getPre_total().doubleValue());
	    	row.createCell(13).setCellValue(bean.getMid_total().doubleValue());
	    	row.createCell(14).setCellValue(bean.getCtl_total().doubleValue());
	    	row.createCell(15).setCellValue(bean.getPre_plt().doubleValue());
	    	row.createCell(16).setCellValue(bean.getMid_plt().doubleValue());
	    	row.createCell(17).setCellValue(bean.getCtl_plt().doubleValue());
	    	row.createCell(18).setCellValue(bean.getPre_clt().doubleValue());
	    	row.createCell(19).setCellValue(bean.getMid_clt().doubleValue());
	    	row.createCell(20).setCellValue(bean.getCtl_clt().doubleValue());
	    	
	    	totProj = totProj + bean.getTotal_project();
	    	totPreKharif = totPreKharif.add(bean.getPre_kharif());
	    	totMidKharif = totMidKharif.add(bean.getMid_kharif());
			totCtlKharif = totCtlKharif.add(bean.getCtl_kharif());
			totPreRabi = totPreRabi.add(bean.getPre_rabi());
			totMidRabi = totMidRabi.add(bean.getMid_rabi());
			totCtlRabi = totCtlRabi.add(bean.getCtl_rabi());
			totPreThrdCrp = totPreThrdCrp.add(bean.getPre_thrdcrp());
			totMidThrdCrp = totMidThrdCrp.add(bean.getMid_thrdcrp());
			totCtlThrdCrp = totCtlThrdCrp.add(bean.getCtl_thrdcrp());
			totPreTotal = totPreTotal.add(bean.getPre_total());
			totMidTotal = totMidTotal.add(bean.getMid_total());
			totCtlTotal = totCtlTotal.add(bean.getCtl_total());
			totPrePlt = totPrePlt.add(bean.getPre_plt());
			totMidPlt = totMidPlt.add(bean.getMid_plt());
			totCtlPlt = totCtlPlt.add(bean.getCtl_plt());
			totPreClt = totPreClt.add(bean.getPre_clt());
			totMidClt = totMidClt.add(bean.getMid_clt());
			totCtlClt = totCtlClt.add(bean.getCtl_clt());
	    	
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
		
		Row row = sheet.createRow(list.size()+9);
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
		cell.setCellValue(totPreKharif.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totMidKharif.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totCtlKharif.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totPreRabi.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totMidRabi.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totCtlRabi.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totPreThrdCrp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totMidThrdCrp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totCtlThrdCrp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totPreTotal.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(totMidTotal.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(totCtlTotal.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(totPrePlt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(totMidPlt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(17);
		cell.setCellValue(totCtlPlt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(18);
		cell.setCellValue(totPreClt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(19);
		cell.setCellValue(totMidClt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(20);
		cell.setCellValue(totCtlClt.doubleValue());
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
	    String fileName = "attachment; filename=Report PE3- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "projectEvaluation/StateMidProjEvlCropDetailsRpt";
	}
	
	@RequestMapping(value = "/downloadPDFStMidProjEvlCropDetails", method = RequestMethod.POST)
	public ModelAndView downloadPDFStMidProjEvlCropDetails(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getStateMidProjEvlCropDetails();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("MidTermProjEvlCrpReport");
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
			
			paragraph3 = new Paragraph("Report PE3 - State-wise Mid Term Project Evaluation of Cropped Area Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(21);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Gross Cropped Area (ha.)", Element.ALIGN_CENTER, 9, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Gross Cropped Area (ha.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area under Plantation Cover (ha.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area of Culturable Wasteland (ha.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Kharif Crop", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Rabi Crop", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Third Crop", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			
			for (int i = 1; i <= 3; i++) {
				CommonFunctions.insertCellHeader(table, "Pre Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Mid Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			}
			
			for (int i = 1; i <= 3; i++) {
				CommonFunctions.insertCellHeader(table, "Pre Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Mid Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			for (int i = 1; i <= 21; i++) {
			    CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
		    
			
			int k = 1;
			int totProj = 0;
			BigDecimal totPreKharif = BigDecimal.ZERO;
			BigDecimal totMidKharif = BigDecimal.ZERO;
			BigDecimal totCtlKharif = BigDecimal.ZERO;
			BigDecimal totPreRabi = BigDecimal.ZERO;
			BigDecimal totMidRabi = BigDecimal.ZERO;
			BigDecimal totCtlRabi = BigDecimal.ZERO;
			BigDecimal totPreThrdCrp = BigDecimal.ZERO;
			BigDecimal totMidThrdCrp = BigDecimal.ZERO;
			BigDecimal totCtlThrdCrp = BigDecimal.ZERO;
			BigDecimal totPreTotal = BigDecimal.ZERO;
			BigDecimal totMidTotal = BigDecimal.ZERO;
			BigDecimal totCtlTotal = BigDecimal.ZERO;
			BigDecimal totPrePlt = BigDecimal.ZERO;
			BigDecimal totMidPlt = BigDecimal.ZERO;
			BigDecimal totCtlPlt = BigDecimal.ZERO;
			BigDecimal totPreClt = BigDecimal.ZERO;
			BigDecimal totMidClt = BigDecimal.ZERO;
			BigDecimal totCtlClt = BigDecimal.ZERO;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_kharif()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_kharif()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_kharif()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_rabi()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_rabi()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_rabi()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_thrdcrp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_thrdcrp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_thrdcrp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_total()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_total()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_total()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_plt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_plt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_plt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_clt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_clt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_clt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					totProj = totProj + list.get(i).getTotal_project();
					totPreKharif = totPreKharif.add(list.get(i).getPre_kharif());
					totMidKharif = totMidKharif.add(list.get(i).getMid_kharif());
					totCtlKharif = totCtlKharif.add(list.get(i).getCtl_kharif());
					totPreRabi = totPreRabi.add(list.get(i).getPre_rabi());
					totMidRabi = totMidRabi.add(list.get(i).getMid_rabi());
					totCtlRabi = totCtlRabi.add(list.get(i).getCtl_rabi());
					totPreThrdCrp = totPreThrdCrp.add(list.get(i).getPre_thrdcrp());
					totMidThrdCrp = totMidThrdCrp.add(list.get(i).getMid_thrdcrp());
					totCtlThrdCrp = totCtlThrdCrp.add(list.get(i).getCtl_thrdcrp());
					totPreTotal = totPreTotal.add(list.get(i).getPre_total());
					totMidTotal = totMidTotal.add(list.get(i).getMid_total());
					totCtlTotal = totCtlTotal.add(list.get(i).getCtl_total());
					totPrePlt = totPrePlt.add(list.get(i).getPre_plt());
					totMidPlt = totMidPlt.add(list.get(i).getMid_plt());
					totCtlPlt = totCtlPlt.add(list.get(i).getCtl_plt());
					totPreClt = totPreClt.add(list.get(i).getPre_clt());
					totMidClt = totMidClt.add(list.get(i).getMid_clt());
					totCtlClt = totCtlClt.add(list.get(i).getCtl_clt());
					
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totProj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreKharif), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidKharif), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlKharif), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreRabi), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidRabi), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlRabi), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreThrdCrp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidThrdCrp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlThrdCrp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPrePlt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidPlt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlPlt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreClt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidClt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlClt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE3- State.pdf");
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
	

//	@RequestMapping(value = "/downloadExcelProjMidProjEvlCropDetails", method = RequestMethod.POST)
//	@ResponseBody
//	public String downloadExcelProjMidProjEvlCropDetails(HttpServletRequest request, HttpServletResponse response)
//	{
//		String stName = request.getParameter("stName");
//		String dcode=request.getParameter("dcode");
//		String distName=request.getParameter("distName");
//		
//		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
//		
//		list = PEService.getprojMidProjEvlCropDetailsRpt(Integer.parseInt(dcode));
//			
//		Workbook workbook = new XSSFWorkbook();
//		//invoking creatSheet() method and passing the name of the sheet to be created
//		Sheet sheet = workbook.createSheet("Report PE3 - Project-wise Mid Term Project Evaluation of Cropped Area Details");
//		
//		CellStyle style = CommonFunctions.getStyle(workbook);
//	    
//		String rptName = "Report PE3 - Project-wise Mid Term Project Evaluation of Cropped Area Details";
//		String areaAmtValDetail ="";
//		
//		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
//		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
//		
//		mergedRegion = new CellRangeAddress(list.size()+10,list.size()+10,0,1);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(5,5,0,20);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(6,8,0,0);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(6,8,1,1);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(6,8,2,2);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(6,6,3,11);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(6,6,12,14);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(6,6,15,17);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(6,6,18,20);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,7,3,5);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,7,6,8);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,7,9,11);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,8,12,12);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,8,13,13);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,8,14,14);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,8,15,15);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,8,16,16);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,8,17,17);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,8,18,18);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,8,19,19);
//		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(7,8,20,20);
//		sheet.addMergedRegion(mergedRegion);
//		
//		
//		Row rowDetail = sheet.createRow(5);
//		
//		Cell cell = rowDetail.createCell(0);
//		cell.setCellValue("State Name : "+ stName+ ", District Name : "+ distName);  
//		cell.setCellStyle(style);
//		
//		for(int i=1;i<21;i++)
//		{
//			cell = rowDetail.createCell(i);
//			cell.setCellStyle(style);
//		}
//		
//		
//		Row rowhead = sheet.createRow(6);
//		
//		cell = rowhead.createCell(0);
//		cell.setCellValue("S.No.");
//		cell.setCellStyle(style);
//		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//		
//		cell = rowhead.createCell(1);
//		cell.setCellValue("Project Name");
//		cell.setCellStyle(style);
//		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//		
////		cell = rowhead.createCell(2);
////		cell.setCellValue("Project");
////		cell.setCellStyle(style);
////		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
////		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//		
//		cell = rowhead.createCell(3);
//		cell.setCellValue("Gross Cropped Area (ha.)");
//		cell.setCellStyle(style);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//		
//		for(int i=4;i<12;i++)
//		{
//			cell =rowhead.createCell(i);
//			cell.setCellStyle(style);
//		}
//		
//		cell = rowhead.createCell(12);
//		cell.setCellValue("Total Gross Cropped Area (ha.)");
//		cell.setCellStyle(style);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//		
//		for(int i=13;i<15;i++)
//		{
//			cell =rowhead.createCell(i);
//			cell.setCellStyle(style);
//		}
//		
//		cell = rowhead.createCell(15);
//		cell.setCellValue("Area under Plantation Cover (ha.)");
//		cell.setCellStyle(style);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//		
//		for(int i=16;i<18;i++)
//		{
//			cell =rowhead.createCell(i);
//			cell.setCellStyle(style);
//		}
//		
//		cell = rowhead.createCell(18);
//		cell.setCellValue("Area of Culturable Wasteland (ha.)");
//		cell.setCellStyle(style);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//		
//		for(int i=19;i<21;i++)
//		{
//			cell =rowhead.createCell(i);
//			cell.setCellStyle(style);
//		}
//		
//		
//		Row rowhead1 = sheet.createRow(7);
//		
//		for(int i=0;i<3;i++)
//		{
//			cell =rowhead1.createCell(i);
//			cell.setCellStyle(style);
//		}
//		
//		cell = rowhead1.createCell(3);
//		cell.setCellValue("Kharif Crop");
//		cell.setCellStyle(style);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//		
//		for(int i=4;i<6;i++)
//		{
//			cell =rowhead1.createCell(i);
//			cell.setCellStyle(style);
//		}
//		
//		cell = rowhead1.createCell(6);
//		cell.setCellValue("Rabi Crop");
//		cell.setCellStyle(style);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//		
//		for(int i=7;i<9;i++)
//		{
//			cell =rowhead1.createCell(i);
//			cell.setCellStyle(style);
//		}
//		
//		cell = rowhead1.createCell(9);
//		cell.setCellValue("Third Crop");
//		cell.setCellStyle(style);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//		
//		for(int i=10;i<12;i++)
//		{
//			cell =rowhead1.createCell(i);
//			cell.setCellStyle(style);
//		}
//		
//		String[] headers = {
//		        "Pre Project Status (Aggregate)",
//		        "Mid Project Status (Aggregate)",
//		        "Controlled Area"
//		    };
//		
//		for (int i = 0; i < 3; i++) {											// Repetition times(group)
//	        for (int j = 0; j < headers.length; j++) {							// values
//	            cell = rowhead1.createCell(12 + (i * headers.length) + j);		// = start index + repetition * header length + repeat values
//	            cell.setCellValue(headers[j]);
//	            cell.setCellStyle(style);
//	            CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
//	            CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//	        }
//	    }
//		
//		
//		
//		Row rowhead2 = sheet.createRow(8);
//		
//		for(int i=0;i<3;i++)
//		{
//			cell =rowhead2.createCell(i);
//			cell.setCellStyle(style);	
//		}
//		
//		
//		for (int i = 0; i < 3; i++) {											// Repetition times(group)
//	        for (int j = 0; j < headers.length; j++) {							// values
//	            cell = rowhead2.createCell(3 + (i * headers.length) + j);		// = start index + repetition * header length + repeat values
//	            cell.setCellValue(headers[j]);
//	            cell.setCellStyle(style);
//	            CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
//	        }
//	    }
//		
//		
//		
//		for(int i=12;i<21;i++)
//		{
//			cell =rowhead2.createCell(i);
//			cell.setCellStyle(style);
//		}
//		
//		
//		Row rowhead3 = sheet.createRow(9);
//		
//		for(int i=0;i<21;i++)
//		{
//			cell =rowhead3.createCell(i);
//			cell.setCellValue(i+1);
//			cell.setCellStyle(style);
//		}
//		
//		int sno = 1;
//		int rowno  = 10;
////		int totProj = 0;
//		BigDecimal totPreKharif = BigDecimal.ZERO;
//		BigDecimal totMidKharif = BigDecimal.ZERO;
//		BigDecimal totCtlKharif = BigDecimal.ZERO;
//		BigDecimal totPreRabi = BigDecimal.ZERO;
//		BigDecimal totMidRabi = BigDecimal.ZERO;
//		BigDecimal totCtlRabi = BigDecimal.ZERO;
//		BigDecimal totPreThrdCrp = BigDecimal.ZERO;
//		BigDecimal totMidThrdCrp = BigDecimal.ZERO;
//		BigDecimal totCtlThrdCrp = BigDecimal.ZERO;
//		BigDecimal totPreTotal = BigDecimal.ZERO;
//		BigDecimal totMidTotal = BigDecimal.ZERO;
//		BigDecimal totCtlTotal = BigDecimal.ZERO;
//		BigDecimal totPrePlt = BigDecimal.ZERO;
//		BigDecimal totMidPlt = BigDecimal.ZERO;
//		BigDecimal totCtlPlt = BigDecimal.ZERO;
//		BigDecimal totPreClt = BigDecimal.ZERO;
//		BigDecimal totMidClt = BigDecimal.ZERO;
//		BigDecimal totCtlClt = BigDecimal.ZERO;
//		
//		
//	    for(ProjectEvaluationBean bean: list)
//	    {
//	    	Row row = sheet.createRow(rowno);
//	    	row.createCell(0).setCellValue(sno);
//	    	row.createCell(1).setCellValue(bean.getProj_name());
////	    	row.createCell(2).setCellValue(bean.getTotal_project());
//	    	row.createCell(3).setCellValue(bean.getPre_kharif().doubleValue());
//	    	row.createCell(4).setCellValue(bean.getMid_kharif().doubleValue());
//	    	row.createCell(5).setCellValue(bean.getCtl_kharif().doubleValue());
//	    	row.createCell(6).setCellValue(bean.getPre_rabi().doubleValue());
//	    	row.createCell(7).setCellValue(bean.getMid_rabi().doubleValue());
//	    	row.createCell(8).setCellValue(bean.getCtl_rabi().doubleValue());
//	    	row.createCell(9).setCellValue(bean.getPre_thrdcrp().doubleValue());
//	    	row.createCell(10).setCellValue(bean.getMid_thrdcrp().doubleValue());
//	    	row.createCell(11).setCellValue(bean.getCtl_thrdcrp().doubleValue());
//	    	row.createCell(12).setCellValue(bean.getPre_total().doubleValue());
//	    	row.createCell(13).setCellValue(bean.getMid_total().doubleValue());
//	    	row.createCell(14).setCellValue(bean.getCtl_total().doubleValue());
//	    	row.createCell(15).setCellValue(bean.getPre_plt().doubleValue());
//	    	row.createCell(16).setCellValue(bean.getMid_plt().doubleValue());
//	    	row.createCell(17).setCellValue(bean.getCtl_plt().doubleValue());
//	    	row.createCell(18).setCellValue(bean.getPre_clt().doubleValue()); 
//	    	row.createCell(19).setCellValue(bean.getMid_clt().doubleValue());
//	    	row.createCell(20).setCellValue(bean.getCtl_clt().doubleValue());
//	    	
////	    	totProj = totProj + bean.getTotal_project();
//	    	totPreKharif = totPreKharif.add(bean.getPre_kharif());
//	    	totMidKharif = totMidKharif.add(bean.getMid_kharif());
//			totCtlKharif = totCtlKharif.add(bean.getCtl_kharif());
//			totPreRabi = totPreRabi.add(bean.getPre_rabi());
//			totMidRabi = totMidRabi.add(bean.getMid_rabi());
//			totCtlRabi = totCtlRabi.add(bean.getCtl_rabi());
//			totPreThrdCrp = totPreThrdCrp.add(bean.getPre_thrdcrp());
//			totMidThrdCrp = totMidThrdCrp.add(bean.getMid_thrdcrp());
//			totCtlThrdCrp = totCtlThrdCrp.add(bean.getCtl_thrdcrp());
//			totPreTotal = totPreTotal.add(bean.getPre_total());
//			totMidTotal = totMidTotal.add(bean.getMid_total());
//			totCtlTotal = totCtlTotal.add(bean.getCtl_total());
//			totPrePlt = totPrePlt.add(bean.getPre_plt());
//			totMidPlt = totMidPlt.add(bean.getMid_plt());
//			totCtlPlt = totCtlPlt.add(bean.getCtl_plt());
//			totPreClt = totPreClt.add(bean.getPre_clt());
//			totMidClt = totMidClt.add(bean.getMid_clt());
//			totCtlClt = totCtlClt.add(bean.getCtl_clt());
//	    	
//	    	sno++;
//	    	rowno++;
//	    }
//	    
//	    
//	    CellStyle style1 = workbook.createCellStyle();
//		style1.setBorderTop(BorderStyle.THIN); 
//		style1.setBorderBottom(BorderStyle.THIN);
//		style1.setBorderLeft(BorderStyle.THIN);
//		style1.setBorderRight(BorderStyle.THIN);
//		style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
//		style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//		org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
//		font1.setFontHeightInPoints((short) 12);
//		font1.setBold(true);
//		//			font1.setColor(IndexedColors.WHITE.getIndex());
//		style1.setFont(font1);
//		
//		Row row = sheet.createRow(list.size()+10);
//		cell = row.createCell(0);
//		cell.setCellValue("Grand Total");
//		cell.setCellStyle(style1);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
//		cell = row.createCell(1);
//		cell.setCellStyle(style1);
//		cell = row.createCell(2);
////		cell.setCellValue(totProj);
//		cell.setCellStyle(style1);
//		cell = row.createCell(3);
//		cell.setCellValue(totPreKharif.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(4);
//		cell.setCellValue(totMidKharif.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(5);
//		cell.setCellValue(totCtlKharif.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(6);
//		cell.setCellValue(totPreRabi.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(7);
//		cell.setCellValue(totMidRabi.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(8);
//		cell.setCellValue(totCtlRabi.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(9);
//		cell.setCellValue(totPreThrdCrp.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(10);
//		cell.setCellValue(totMidThrdCrp.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(11);
//		cell.setCellValue(totCtlThrdCrp.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(12);
//		cell.setCellValue(totPreTotal.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(13);
//		cell.setCellValue(totMidTotal.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(14);
//		cell.setCellValue(totCtlTotal.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(15);
//		cell.setCellValue(totPrePlt.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(16);
//		cell.setCellValue(totMidPlt.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(17);
//		cell.setCellValue(totCtlPlt.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(18);
//		cell.setCellValue(totPreClt.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(19);
//		cell.setCellValue(totMidClt.doubleValue());
//		cell.setCellStyle(style1);
//		cell = row.createCell(20);
//		cell.setCellValue(totCtlClt.doubleValue());
//		cell.setCellStyle(style1);
//		
//		
//	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
//	    String fileName = "attachment; filename=Report PE3- Project.xlsx";
//	    
//	    CommonFunctions.downloadExcel(response, workbook, fileName);
//	    
//	    return "projectEvaluation/ProjMidProjEvlCropDetailsRpt";
//	}
//	
	@RequestMapping(value = "/downloadExcelProjMidProjEvlCropDetails", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjMidProjEvlCropDetails(HttpServletRequest request, HttpServletResponse response) {
	    String stName = request.getParameter("stName");
	    String dcode = request.getParameter("dcode");
	    String distName = request.getParameter("distName");

	    List<ProjectEvaluationBean> list = PEService.getprojMidProjEvlCropDetailsRpt(Integer.parseInt(dcode));

	    Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Report PE3 - Project-wise Mid Term Project Evaluation of Cropped Area Details");
	    CellStyle style = CommonFunctions.getStyle(workbook);

	    String rptName = "Report PE3 - Project-wise Mid Term Project Evaluation of Cropped Area Details";
	    String areaAmtValDetail = "";
	    CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 0);
	    CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 19, areaAmtValDetail, workbook);

	    mergedRegion = new CellRangeAddress(list.size() + 10, list.size() + 10, 0, 1);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(5, 5, 0, 19);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(6, 8, 0, 0);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(6, 8, 1, 1);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(6, 6, 2, 10);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(6, 6, 11, 13);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(6, 6, 14, 16);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(6, 6, 17, 19);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 7, 2, 4);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 7, 5, 7);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 7, 8, 10);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 8, 11, 11);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 8, 12, 12);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 8, 13, 13);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 8, 14, 14);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 8, 15, 15);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 8, 16, 16);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 8, 17, 17);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 8, 18, 18);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7, 8, 19, 19);
	    sheet.addMergedRegion(mergedRegion);

	    Row rowDetail = sheet.createRow(5);
	    Cell cell = rowDetail.createCell(0);
	    cell.setCellValue("State Name : " + stName + ", District Name : " + distName);
	    cell.setCellStyle(style);

	    for (int i = 1; i < 20; i++) {
	        cell = rowDetail.createCell(i);
	        cell.setCellStyle(style);
	    }

	    Row rowhead = sheet.createRow(6);

	    cell = rowhead.createCell(0);
	    cell.setCellValue("S.No.");
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

	    cell = rowhead.createCell(1);
	    cell.setCellValue("Project Name");
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

	    cell = rowhead.createCell(2);
	    cell.setCellValue("Gross Cropped Area (ha.)");
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

	    for (int i = 3; i < 11; i++) {
	        cell = rowhead.createCell(i);
	        cell.setCellStyle(style);
	    }

	    cell = rowhead.createCell(11);
	    cell.setCellValue("Total Gross Cropped Area (ha.)");
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

	    for (int i = 12; i < 14; i++) {
	        cell = rowhead.createCell(i);
	        cell.setCellStyle(style);
	    }

	    cell = rowhead.createCell(14);
	    cell.setCellValue("Area under Plantation Cover (ha.)");
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

	    for (int i = 15; i < 17; i++) {
	        cell = rowhead.createCell(i);
	        cell.setCellStyle(style);
	    }

	    cell = rowhead.createCell(17);
	    cell.setCellValue("Area of Culturable Wasteland (ha.)");
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

	    for (int i = 18; i < 20; i++) {
	        cell = rowhead.createCell(i);
	        cell.setCellStyle(style);
	    }

	    Row rowhead1 = sheet.createRow(7);

	    for (int i = 0; i < 2; i++) {
	        cell = rowhead1.createCell(i);
	        cell.setCellStyle(style);
	    }

	    cell = rowhead1.createCell(2);
	    cell.setCellValue("Kharif Crop");
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

	    for (int i = 3; i < 5; i++) {
	        cell = rowhead1.createCell(i);
	        cell.setCellStyle(style);
	    }

	    cell = rowhead1.createCell(5);
	    cell.setCellValue("Rabi Crop");
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

	    for (int i = 6; i < 8; i++) {
	        cell = rowhead1.createCell(i);
	        cell.setCellStyle(style);
	    }

	    cell = rowhead1.createCell(8);
	    cell.setCellValue("Third Crop");
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

	    for (int i = 9; i < 11; i++) {
	        cell = rowhead1.createCell(i);
	        cell.setCellStyle(style);
	    }

	    String[] headers = {"Pre Project Status (Aggregate)", "Mid Project Status (Aggregate)", "Controlled Area"};

	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < headers.length; j++) {
	            cell = rowhead1.createCell(11 + (i * headers.length) + j);
	            cell.setCellValue(headers[j]);
	            cell.setCellStyle(style);
	            CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	            CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	        }
	    }

	    Row rowhead2 = sheet.createRow(8);
	    for (int i = 0; i < 2; i++) {
	        cell = rowhead2.createCell(i);
	        cell.setCellStyle(style);
	    }

	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < headers.length; j++) {
	            cell = rowhead2.createCell(2 + (i * headers.length) + j);
	            cell.setCellValue(headers[j]);
	            cell.setCellStyle(style);
	            CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	        }
	    }

	    for (int i = 11; i < 20; i++) {
	        cell = rowhead2.createCell(i);
	        cell.setCellStyle(style);
	    }

	    Row rowhead3 = sheet.createRow(9);
	    for (int i = 0; i < 20; i++) {
	        cell = rowhead3.createCell(i);
	        cell.setCellValue(i + 1);
	        cell.setCellStyle(style);
	    }

	    int sno = 1;
	    int rowno = 10;

	    BigDecimal totPreKharif = BigDecimal.ZERO;
	    BigDecimal totMidKharif = BigDecimal.ZERO;
	    BigDecimal totCtlKharif = BigDecimal.ZERO;
	    BigDecimal totPreRabi = BigDecimal.ZERO;
	    BigDecimal totMidRabi = BigDecimal.ZERO;
	    BigDecimal totCtlRabi = BigDecimal.ZERO;
	    BigDecimal totPreThrdCrp = BigDecimal.ZERO;
	    BigDecimal totMidThrdCrp = BigDecimal.ZERO;
	    BigDecimal totCtlThrdCrp = BigDecimal.ZERO;
	    BigDecimal totPreTotal = BigDecimal.ZERO;
	    BigDecimal totMidTotal = BigDecimal.ZERO;
	    BigDecimal totCtlTotal = BigDecimal.ZERO;
	    BigDecimal totPrePlt = BigDecimal.ZERO;
	    BigDecimal totMidPlt = BigDecimal.ZERO;
	    BigDecimal totCtlPlt = BigDecimal.ZERO;
	    BigDecimal totPreClt = BigDecimal.ZERO;
	    BigDecimal totMidClt = BigDecimal.ZERO;
	    BigDecimal totCtlClt = BigDecimal.ZERO;

	    for (ProjectEvaluationBean bean : list) {
	        Row row = sheet.createRow(rowno);
	        row.createCell(0).setCellValue(sno);
	        row.createCell(1).setCellValue(bean.getProj_name());
	        row.createCell(2).setCellValue(bean.getPre_kharif().doubleValue());
	        row.createCell(3).setCellValue(bean.getMid_kharif().doubleValue());
	        row.createCell(4).setCellValue(bean.getCtl_kharif().doubleValue());
	        row.createCell(5).setCellValue(bean.getPre_rabi().doubleValue());
	        row.createCell(6).setCellValue(bean.getMid_rabi().doubleValue());
	        row.createCell(7).setCellValue(bean.getCtl_rabi().doubleValue());
	        row.createCell(8).setCellValue(bean.getPre_thrdcrp().doubleValue());
	        row.createCell(9).setCellValue(bean.getMid_thrdcrp().doubleValue());
	        row.createCell(10).setCellValue(bean.getCtl_thrdcrp().doubleValue());
	        row.createCell(11).setCellValue(bean.getPre_total().doubleValue());
	        row.createCell(12).setCellValue(bean.getMid_total().doubleValue());
	        row.createCell(13).setCellValue(bean.getCtl_total().doubleValue());
	        row.createCell(14).setCellValue(bean.getPre_plt().doubleValue());
	        row.createCell(15).setCellValue(bean.getMid_plt().doubleValue());
	        row.createCell(16).setCellValue(bean.getCtl_plt().doubleValue());
	        row.createCell(17).setCellValue(bean.getPre_clt().doubleValue());
	        row.createCell(18).setCellValue(bean.getMid_clt().doubleValue());
	        row.createCell(19).setCellValue(bean.getCtl_clt().doubleValue());

	        totPreKharif = totPreKharif.add(bean.getPre_kharif());
	        totMidKharif = totMidKharif.add(bean.getMid_kharif());
	        totCtlKharif = totCtlKharif.add(bean.getCtl_kharif());
	        totPreRabi = totPreRabi.add(bean.getPre_rabi());
	        totMidRabi = totMidRabi.add(bean.getMid_rabi());
	        totCtlRabi = totCtlRabi.add(bean.getCtl_rabi());
	        totPreThrdCrp = totPreThrdCrp.add(bean.getPre_thrdcrp());
	        totMidThrdCrp = totMidThrdCrp.add(bean.getMid_thrdcrp());
	        totCtlThrdCrp = totCtlThrdCrp.add(bean.getCtl_thrdcrp());
	        totPreTotal = totPreTotal.add(bean.getPre_total());
	        totMidTotal = totMidTotal.add(bean.getMid_total());
	        totCtlTotal = totCtlTotal.add(bean.getCtl_total());
	        totPrePlt = totPrePlt.add(bean.getPre_plt());
	        totMidPlt = totMidPlt.add(bean.getMid_plt());
	        totCtlPlt = totCtlPlt.add(bean.getCtl_plt());
	        totPreClt = totPreClt.add(bean.getPre_clt());
	        totMidClt = totMidClt.add(bean.getMid_clt());
	        totCtlClt = totCtlClt.add(bean.getCtl_clt());

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

	    Row row = sheet.createRow(list.size() + 10);
	    cell = row.createCell(0);
	    cell.setCellValue("Grand Total");
	    cell.setCellStyle(style1);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);

	    cell = row.createCell(1);
	    cell.setCellStyle(style1);
	    cell = row.createCell(2);
	    cell.setCellValue(totPreKharif.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(3);
	    cell.setCellValue(totMidKharif.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(4);
	    cell.setCellValue(totCtlKharif.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(5);
	    cell.setCellValue(totPreRabi.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(6);
	    cell.setCellValue(totMidRabi.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(7);
	    cell.setCellValue(totCtlRabi.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(8);
	    cell.setCellValue(totPreThrdCrp.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(9);
	    cell.setCellValue(totMidThrdCrp.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(10);
	    cell.setCellValue(totCtlThrdCrp.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(11);
	    cell.setCellValue(totPreTotal.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(12);
	    cell.setCellValue(totMidTotal.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(13);
	    cell.setCellValue(totCtlTotal.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(14);
	    cell.setCellValue(totPrePlt.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(15);
	    cell.setCellValue(totMidPlt.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(16);
	    cell.setCellValue(totCtlPlt.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(17);
	    cell.setCellValue(totPreClt.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(18);
	    cell.setCellValue(totMidClt.doubleValue());
	    cell.setCellStyle(style1);
	    cell = row.createCell(19);
	    cell.setCellValue(totCtlClt.doubleValue());
	    cell.setCellStyle(style1);

	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 19);
	    String fileName = "attachment; filename=Report_PE3_Project.xlsx";
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "projectEvaluation/ProjMidProjEvlCropDetailsRpt";
	}

	@RequestMapping(value = "/downloadExcelDistMidProjEvlCropDetails", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistMidProjEvlCropDetails(HttpServletRequest request, HttpServletResponse response)
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getDistMidProjEvlCropDetails(Integer.parseInt(stcd));
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report PE3 - District-wise Mid Term Project Evaluation of Cropped Area Details");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE3 - District-wise Mid Term Project Evaluation of Cropped Area Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+10,list.size()+10,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,20);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,8,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,12,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,15,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,18,20);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,3,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,6,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,7,9,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,12,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,13,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,14,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,15,15);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,16,16);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,17,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,18,18);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,19,19);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(7,8,20,20);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State Name : "+ stName);  
		cell.setCellStyle(style);
		
		for(int i=1;i<21;i++)
		{
			cell = rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Project");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Gross Cropped Area (ha.)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<12;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(12);
		cell.setCellValue("Total Gross Cropped Area (ha.)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=13;i<15;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(15);
		cell.setCellValue("Area under Plantation Cover (ha.)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=16;i<18;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(18);
		cell.setCellValue("Area of Culturable Wasteland (ha.)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=19;i<21;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Kharif Crop");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<6;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Rabi Crop");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=7;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Third Crop");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<12;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		String[] headers = {
		        "Pre Project Status (Aggregate)",
		        "Mid Project Status (Aggregate)",
		        "Controlled Area"
		    };
		
		for (int i = 0; i < 3; i++) {											// Repetition times(group)
	        for (int j = 0; j < headers.length; j++) {							// values
	            cell = rowhead1.createCell(12 + (i * headers.length) + j);		// = start index + repetition * header length + repeat values
	            cell.setCellValue(headers[j]);
	            cell.setCellStyle(style);
	            CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	            CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	        }
	    }
		
		
		
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);	
		}
		
		
		for (int i = 0; i < 3; i++) {											// Repetition times(group)
	        for (int j = 0; j < headers.length; j++) {							// values
	            cell = rowhead2.createCell(3 + (i * headers.length) + j);		// = start index + repetition * header length + repeat values
	            cell.setCellValue(headers[j]);
	            cell.setCellStyle(style);
	            CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	        }
	    }
		
		
		
		for(int i=12;i<21;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead3 = sheet.createRow(9);
		
		for(int i=0;i<21;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 10;
		int totProj = 0;
		BigDecimal totPreKharif = BigDecimal.ZERO;
		BigDecimal totMidKharif = BigDecimal.ZERO;
		BigDecimal totCtlKharif = BigDecimal.ZERO;
		BigDecimal totPreRabi = BigDecimal.ZERO;
		BigDecimal totMidRabi = BigDecimal.ZERO;
		BigDecimal totCtlRabi = BigDecimal.ZERO;
		BigDecimal totPreThrdCrp = BigDecimal.ZERO;
		BigDecimal totMidThrdCrp = BigDecimal.ZERO;
		BigDecimal totCtlThrdCrp = BigDecimal.ZERO;
		BigDecimal totPreTotal = BigDecimal.ZERO;
		BigDecimal totMidTotal = BigDecimal.ZERO;
		BigDecimal totCtlTotal = BigDecimal.ZERO;
		BigDecimal totPrePlt = BigDecimal.ZERO;
		BigDecimal totMidPlt = BigDecimal.ZERO;
		BigDecimal totCtlPlt = BigDecimal.ZERO;
		BigDecimal totPreClt = BigDecimal.ZERO;
		BigDecimal totMidClt = BigDecimal.ZERO;
		BigDecimal totCtlClt = BigDecimal.ZERO;
		
		
	    for(ProjectEvaluationBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getDistname());
	    	row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getPre_kharif().doubleValue());
	    	row.createCell(4).setCellValue(bean.getMid_kharif().doubleValue());
	    	row.createCell(5).setCellValue(bean.getCtl_kharif().doubleValue());
	    	row.createCell(6).setCellValue(bean.getPre_rabi().doubleValue());
	    	row.createCell(7).setCellValue(bean.getMid_rabi().doubleValue());
	    	row.createCell(8).setCellValue(bean.getCtl_rabi().doubleValue());
	    	row.createCell(9).setCellValue(bean.getPre_thrdcrp().doubleValue());
	    	row.createCell(10).setCellValue(bean.getMid_thrdcrp().doubleValue());
	    	row.createCell(11).setCellValue(bean.getCtl_thrdcrp().doubleValue());
	    	row.createCell(12).setCellValue(bean.getPre_total().doubleValue());
	    	row.createCell(13).setCellValue(bean.getMid_total().doubleValue());
	    	row.createCell(14).setCellValue(bean.getCtl_total().doubleValue());
	    	row.createCell(15).setCellValue(bean.getPre_plt().doubleValue());
	    	row.createCell(16).setCellValue(bean.getMid_plt().doubleValue());
	    	row.createCell(17).setCellValue(bean.getCtl_plt().doubleValue());
	    	row.createCell(18).setCellValue(bean.getPre_clt().doubleValue()); 
	    	row.createCell(19).setCellValue(bean.getMid_clt().doubleValue());
	    	row.createCell(20).setCellValue(bean.getCtl_clt().doubleValue());
	    	
	    	totProj = totProj + bean.getTotal_project();
	    	totPreKharif = totPreKharif.add(bean.getPre_kharif());
	    	totMidKharif = totMidKharif.add(bean.getMid_kharif());
			totCtlKharif = totCtlKharif.add(bean.getCtl_kharif());
			totPreRabi = totPreRabi.add(bean.getPre_rabi());
			totMidRabi = totMidRabi.add(bean.getMid_rabi());
			totCtlRabi = totCtlRabi.add(bean.getCtl_rabi());
			totPreThrdCrp = totPreThrdCrp.add(bean.getPre_thrdcrp());
			totMidThrdCrp = totMidThrdCrp.add(bean.getMid_thrdcrp());
			totCtlThrdCrp = totCtlThrdCrp.add(bean.getCtl_thrdcrp());
			totPreTotal = totPreTotal.add(bean.getPre_total());
			totMidTotal = totMidTotal.add(bean.getMid_total());
			totCtlTotal = totCtlTotal.add(bean.getCtl_total());
			totPrePlt = totPrePlt.add(bean.getPre_plt());
			totMidPlt = totMidPlt.add(bean.getMid_plt());
			totCtlPlt = totCtlPlt.add(bean.getCtl_plt());
			totPreClt = totPreClt.add(bean.getPre_clt());
			totMidClt = totMidClt.add(bean.getMid_clt());
			totCtlClt = totCtlClt.add(bean.getCtl_clt());
	    	
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
		
		Row row = sheet.createRow(list.size()+10);
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
		cell.setCellValue(totPreKharif.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totMidKharif.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totCtlKharif.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totPreRabi.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totMidRabi.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totCtlRabi.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totPreThrdCrp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totMidThrdCrp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totCtlThrdCrp.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totPreTotal.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(totMidTotal.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(totCtlTotal.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(totPrePlt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(totMidPlt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(17);
		cell.setCellValue(totCtlPlt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(18);
		cell.setCellValue(totPreClt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(19);
		cell.setCellValue(totMidClt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(20);
		cell.setCellValue(totCtlClt.doubleValue());
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
	    String fileName = "attachment; filename=Report PE3- District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "projectEvaluation/DistMidProjEvlCropDetailsRpt";
	}
	
	@RequestMapping(value = "/downloadPDFProjMidProjEvlCropDetails", method = RequestMethod.POST)
	public ModelAndView downloadPDFProjMidProjEvlCropDetails(HttpServletRequest request, HttpServletResponse response)
	{
		String stName = request.getParameter("stName");
		String dcode=request.getParameter("dcode");
		String distName=request.getParameter("distName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getprojMidProjEvlCropDetailsRpt(Integer.parseInt(dcode));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("MidTermProjEvlCrpReport");
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
			
			paragraph3 = new Paragraph("Report PE3 - Project-wise Mid Term Project Evaluation of Cropped Area Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(20);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(5);
		    
		    CommonFunctions.insertCellHeader(table, "State Name : "+stName+ ",  District Name : "+distName , Element.ALIGN_LEFT, 20, 1, bf8Bold);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Gross Cropped Area (ha.)", Element.ALIGN_CENTER, 9, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Gross Cropped Area (ha.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area under Plantation Cover (ha.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area of Culturable Wasteland (ha.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Kharif Crop", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Rabi Crop", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Third Crop", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			
			for (int i = 1; i <= 3; i++) {
				CommonFunctions.insertCellHeader(table, "Pre Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Mid Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			}
			
			for (int i = 1; i <= 3; i++) {
				CommonFunctions.insertCellHeader(table, "Pre Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Mid Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			for (int i = 1; i <= 20; i++) {
			    CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
		    
			
			int k = 1;
//			int totProj = 0;
			BigDecimal totPreKharif = BigDecimal.ZERO;
			BigDecimal totMidKharif = BigDecimal.ZERO;
			BigDecimal totCtlKharif = BigDecimal.ZERO;
			BigDecimal totPreRabi = BigDecimal.ZERO;
			BigDecimal totMidRabi = BigDecimal.ZERO;
			BigDecimal totCtlRabi = BigDecimal.ZERO;
			BigDecimal totPreThrdCrp = BigDecimal.ZERO;
			BigDecimal totMidThrdCrp = BigDecimal.ZERO;
			BigDecimal totCtlThrdCrp = BigDecimal.ZERO;
			BigDecimal totPreTotal = BigDecimal.ZERO;
			BigDecimal totMidTotal = BigDecimal.ZERO;
			BigDecimal totCtlTotal = BigDecimal.ZERO;
			BigDecimal totPrePlt = BigDecimal.ZERO;
			BigDecimal totMidPlt = BigDecimal.ZERO;
			BigDecimal totCtlPlt = BigDecimal.ZERO;
			BigDecimal totPreClt = BigDecimal.ZERO;
			BigDecimal totMidClt = BigDecimal.ZERO;
			BigDecimal totCtlClt = BigDecimal.ZERO;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_kharif()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_kharif()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_kharif()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_rabi()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_rabi()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_rabi()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_thrdcrp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_thrdcrp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_thrdcrp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_total()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_total()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_total()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_plt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_plt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_plt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_clt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_clt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_clt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					totPreKharif = totPreKharif.add(list.get(i).getPre_kharif());
					totMidKharif = totMidKharif.add(list.get(i).getMid_kharif());
					totCtlKharif = totCtlKharif.add(list.get(i).getCtl_kharif());
					totPreRabi = totPreRabi.add(list.get(i).getPre_rabi());
					totMidRabi = totMidRabi.add(list.get(i).getMid_rabi());
					totCtlRabi = totCtlRabi.add(list.get(i).getCtl_rabi());
					totPreThrdCrp = totPreThrdCrp.add(list.get(i).getPre_thrdcrp());
					totMidThrdCrp = totMidThrdCrp.add(list.get(i).getMid_thrdcrp());
					totCtlThrdCrp = totCtlThrdCrp.add(list.get(i).getCtl_thrdcrp());
					totPreTotal = totPreTotal.add(list.get(i).getPre_total());
					totMidTotal = totMidTotal.add(list.get(i).getMid_total());
					totCtlTotal = totCtlTotal.add(list.get(i).getCtl_total());
					totPrePlt = totPrePlt.add(list.get(i).getPre_plt());
					totMidPlt = totMidPlt.add(list.get(i).getMid_plt());
					totCtlPlt = totCtlPlt.add(list.get(i).getCtl_plt());
					totPreClt = totPreClt.add(list.get(i).getPre_clt());
					totMidClt = totMidClt.add(list.get(i).getMid_clt());
					totCtlClt = totCtlClt.add(list.get(i).getCtl_clt());
					
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreKharif), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidKharif), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlKharif), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreRabi), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidRabi), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlRabi), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreThrdCrp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidThrdCrp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlThrdCrp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPrePlt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidPlt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlPlt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreClt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidClt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlClt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE3- Project.pdf");
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
	
	@RequestMapping(value = "/downloadPDFDistMidProjEvlCropDetails", method = RequestMethod.POST)
	public ModelAndView downloadPDFDistMidProjEvlCropDetails(HttpServletRequest request, HttpServletResponse response)
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getDistMidProjEvlCropDetails(Integer.parseInt(stcd));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("MidTermProjEvlCrpReport");
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
			
			paragraph3 = new Paragraph("Report PE3 - District-wise Mid Term Project Evaluation of Cropped Area Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(21);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(5);
		    
		    CommonFunctions.insertCellHeader(table, "State Name : "+stName, Element.ALIGN_LEFT, 21, 1, bf8Bold);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Gross Cropped Area (ha.)", Element.ALIGN_CENTER, 9, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Gross Cropped Area (ha.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area under Plantation Cover (ha.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area of Culturable Wasteland (ha.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Kharif Crop", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Rabi Crop", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Third Crop", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			
			for (int i = 1; i <= 3; i++) {
				CommonFunctions.insertCellHeader(table, "Pre Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Mid Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			}
			
			for (int i = 1; i <= 3; i++) {
				CommonFunctions.insertCellHeader(table, "Pre Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Mid Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			for (int i = 1; i <= 21; i++) {
			    CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
		    
			
			int k = 1;
			int totProj = 0;
			BigDecimal totPreKharif = BigDecimal.ZERO;
			BigDecimal totMidKharif = BigDecimal.ZERO;
			BigDecimal totCtlKharif = BigDecimal.ZERO;
			BigDecimal totPreRabi = BigDecimal.ZERO;
			BigDecimal totMidRabi = BigDecimal.ZERO;
			BigDecimal totCtlRabi = BigDecimal.ZERO;
			BigDecimal totPreThrdCrp = BigDecimal.ZERO;
			BigDecimal totMidThrdCrp = BigDecimal.ZERO;
			BigDecimal totCtlThrdCrp = BigDecimal.ZERO;
			BigDecimal totPreTotal = BigDecimal.ZERO;
			BigDecimal totMidTotal = BigDecimal.ZERO;
			BigDecimal totCtlTotal = BigDecimal.ZERO;
			BigDecimal totPrePlt = BigDecimal.ZERO;
			BigDecimal totMidPlt = BigDecimal.ZERO;
			BigDecimal totCtlPlt = BigDecimal.ZERO;
			BigDecimal totPreClt = BigDecimal.ZERO;
			BigDecimal totMidClt = BigDecimal.ZERO;
			BigDecimal totCtlClt = BigDecimal.ZERO;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_kharif()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_kharif()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_kharif()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_rabi()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_rabi()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_rabi()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_thrdcrp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_thrdcrp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_thrdcrp()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_total()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_total()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_total()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_plt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_plt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_plt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPre_clt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMid_clt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCtl_clt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					totProj = totProj + list.get(i).getTotal_project();
					totPreKharif = totPreKharif.add(list.get(i).getPre_kharif());
					totMidKharif = totMidKharif.add(list.get(i).getMid_kharif());
					totCtlKharif = totCtlKharif.add(list.get(i).getCtl_kharif());
					totPreRabi = totPreRabi.add(list.get(i).getPre_rabi());
					totMidRabi = totMidRabi.add(list.get(i).getMid_rabi());
					totCtlRabi = totCtlRabi.add(list.get(i).getCtl_rabi());
					totPreThrdCrp = totPreThrdCrp.add(list.get(i).getPre_thrdcrp());
					totMidThrdCrp = totMidThrdCrp.add(list.get(i).getMid_thrdcrp());
					totCtlThrdCrp = totCtlThrdCrp.add(list.get(i).getCtl_thrdcrp());
					totPreTotal = totPreTotal.add(list.get(i).getPre_total());
					totMidTotal = totMidTotal.add(list.get(i).getMid_total());
					totCtlTotal = totCtlTotal.add(list.get(i).getCtl_total());
					totPrePlt = totPrePlt.add(list.get(i).getPre_plt());
					totMidPlt = totMidPlt.add(list.get(i).getMid_plt());
					totCtlPlt = totCtlPlt.add(list.get(i).getCtl_plt());
					totPreClt = totPreClt.add(list.get(i).getPre_clt());
					totMidClt = totMidClt.add(list.get(i).getMid_clt());
					totCtlClt = totCtlClt.add(list.get(i).getCtl_clt());
					
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totProj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreKharif), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidKharif), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlKharif), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreRabi), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidRabi), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlRabi), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreThrdCrp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidThrdCrp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlThrdCrp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPrePlt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidPlt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlPlt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPreClt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMidClt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCtlClt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE3- District.pdf");
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
	
	@RequestMapping(value = "/downloadExcelStMidProjEvlWorkDetails", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStMidProjEvlWorkDetails(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getStateMidProjEvlWorkDetails();
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report PE11 - State-wise Mid Term Project Evaluation of Geotagged Work Details");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE11 - State-wise Mid Term Project Evaluation of Geotagged Work Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,3,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,6,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,7,7);
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
		cell.setCellValue("Total Projects");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total Works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<6;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Total Area of Shape Files");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total No. of Geotagged Works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Created");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(4);
		cell.setCellValue("Ongoing");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("Completed");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=6;i<8;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<8;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 8;
		int totProj = 0;
		int totCreated = 0;
		int totOngoing = 0;
		int totCompleted = 0;
		BigDecimal totShape = BigDecimal.ZERO;
		int totGeotag = 0;
		
		
	    for(ProjectEvaluationBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getSt_name());
	    	row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getCreated_work());
	    	row.createCell(4).setCellValue(bean.getOngoing_work());
	    	row.createCell(5).setCellValue(bean.getCompleted_work());
	    	row.createCell(6).setCellValue(bean.getShape_file_area().doubleValue());
	    	row.createCell(7).setCellValue(bean.getGeo_tagg_work());
	    	
	    	totProj = totProj + bean.getTotal_project();
	    	totCreated = totCreated + bean.getCreated_work();
	    	totOngoing = totOngoing + bean.getOngoing_work();
	    	totCompleted = totCompleted + bean.getCompleted_work();
	    	totShape = totShape.add(bean.getShape_file_area());
	    	totGeotag = totGeotag + bean.getGeo_tagg_work();
	    	
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
		cell.setCellValue(totCreated);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totOngoing);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totCompleted);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totShape.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totGeotag);
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 7);
	    String fileName = "attachment; filename=Report PE11- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "projectEvaluation/StateMidProjEvlWorkDetailsRpt";
	}
	
	@RequestMapping(value = "/downloadPDFStMidProjEvlWorkDetails", method = RequestMethod.POST)
	public ModelAndView downloadPDFStMidProjEvlWorkDetails(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getStateMidProjEvlWorkDetails();
		
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
			
			paragraph3 = new Paragraph("Report PE11 - State-wise Mid Term Project Evaluation of Geotagged Work Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(8);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(60);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(3);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Projects", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Area of Shape Files", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Geotagged Works", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			
			CommonFunctions.insertCellHeader(table, "Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
		    
			for (int i = 1; i <= 8; i++) {
			    CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			int k = 1;
			int totProj = 0;
			int totCreated = 0;
			int totOngoing = 0;
			int totCompleted = 0;
			BigDecimal totShape = BigDecimal.ZERO;
			int totGeotag = 0;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCreated_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOngoing_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCompleted_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getShape_file_area()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGeo_tagg_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					
					totProj = totProj + list.get(i).getTotal_project();
					totCreated = totCreated + list.get(i).getCreated_work();
			    	totOngoing = totOngoing + list.get(i).getOngoing_work();
			    	totCompleted = totCompleted + list.get(i).getCompleted_work();
			    	totShape = totShape.add(list.get(i).getShape_file_area());
			    	totGeotag = totGeotag + list.get(i).getGeo_tagg_work();
					
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totProj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCreated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totOngoing), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCompleted), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totShape), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGeotag), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE11- State.pdf");
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
	
	@RequestMapping(value = "/downloadExcelDistMidProjEvlWorkDetails", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistMidProjEvlWorkDetails(HttpServletRequest request, HttpServletResponse response)
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getDistMidProjEvlWorkDetails(Integer.parseInt(stcd));
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report PE11 - District-wise Mid Term Project Evaluation of Geotagged Work Details");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE11 - District-wise Mid Term Project Evaluation of Geotagged Work Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,7);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,6,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,7,7);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State Name : "+ stName);  
		cell.setCellStyle(style);
		
		for(int i=1;i<8;i++)
		{
			cell = rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total Projects");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total Works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<6;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Total Area of Shape Files");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total No. of Geotagged Works");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Created");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(4);
		cell.setCellValue("Ongoing");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("Completed");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=6;i<8;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0;i<8;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		int totProj = 0;
		int totCreated = 0;
		int totOngoing = 0;
		int totCompleted = 0;
		BigDecimal totShape = BigDecimal.ZERO;
		int totGeotag = 0;
		
		
	    for(ProjectEvaluationBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getDistname());
	    	row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getCreated_work());
	    	row.createCell(4).setCellValue(bean.getOngoing_work());
	    	row.createCell(5).setCellValue(bean.getCompleted_work());
	    	row.createCell(6).setCellValue(bean.getShape_file_area().doubleValue());
	    	row.createCell(7).setCellValue(bean.getGeo_tagg_work());
	    	
	    	totProj = totProj + bean.getTotal_project();
	    	totCreated = totCreated + bean.getCreated_work();
	    	totOngoing = totOngoing + bean.getOngoing_work();
	    	totCompleted = totCompleted + bean.getCompleted_work();
	    	totShape = totShape.add(bean.getShape_file_area());
	    	totGeotag = totGeotag + bean.getGeo_tagg_work();
	    	
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
		
		Row row = sheet.createRow(list.size()+9);
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
		cell.setCellValue(totCreated);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totOngoing);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totCompleted);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totShape.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totGeotag);
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 7);
	    String fileName = "attachment; filename=Report PE11- District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "projectEvaluation/DistMidProjEvlWorkDetailsRpt";
	}
	
	@RequestMapping(value = "/downloadPDFDistMidProjEvlWorkDetails", method = RequestMethod.POST)
	public ModelAndView downloadPDFDistMidProjEvlWorkDetails(HttpServletRequest request, HttpServletResponse response)
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		list = PEService.getDistMidProjEvlWorkDetails(Integer.parseInt(stcd));
		
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
			
			paragraph3 = new Paragraph("Report PE11 - District-wise Mid Term Project Evaluation of Geotagged Work Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(8);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(60);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table, "State Name : "+stName, Element.ALIGN_LEFT, 8, 1, bf8Bold);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Projects", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Area of Shape Files", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Geotagged Works", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			
			CommonFunctions.insertCellHeader(table, "Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
		    
			for (int i = 1; i <= 8; i++) {
			    CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			
			int k = 1;
			int totProj = 0;
			int totCreated = 0;
			int totOngoing = 0;
			int totCompleted = 0;
			BigDecimal totShape = BigDecimal.ZERO;
			int totGeotag = 0;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCreated_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOngoing_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCompleted_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getShape_file_area()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGeo_tagg_work()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					
					totProj = totProj + list.get(i).getTotal_project();
					totCreated = totCreated + list.get(i).getCreated_work();
			    	totOngoing = totOngoing + list.get(i).getOngoing_work();
			    	totCompleted = totCompleted + list.get(i).getCompleted_work();
			    	totShape = totShape.add(list.get(i).getShape_file_area());
			    	totGeotag = totGeotag + list.get(i).getGeo_tagg_work();
					
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totProj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCreated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totOngoing), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCompleted), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totShape), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGeotag), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE11- District.pdf");
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
