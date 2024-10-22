package app.controllers.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

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

import app.bean.BaselineStateWiseAreaDetailBean;
import app.common.CommonFunctions;

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


import app.bean.BaselineStatewiseCropDetailBean;
import app.bean.Login;
import app.bean.ShgDetailBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.service.DistrictMasterService;
import app.service.StateMasterService;
import app.service.reports.BaselineAreawiseDtlService;

@Controller
public class BaselineAreaWiseDetailController {

	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired
	BaselineAreawiseDtlService blsAreawiseDtlsrvc;
	
	@RequestMapping(value = "/blsareasrvydtlrpt", method = RequestMethod.GET)
	public ModelAndView getBaseLineStwiseAreaSurvey(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/blsAreawiseSurvey");
		mav.addObject("stwiseAreaDtlList",blsAreawiseDtlsrvc.getStwiseAreaDetail());
		return mav;
	}
	
	@RequestMapping(value = "/getDistWiseblsareasrvydtlrpt", method = RequestMethod.GET)
	public ModelAndView getDistWiseblsareasrvydtlrpt(HttpServletRequest request, @RequestParam("stcode") int stcode,
			@RequestParam("stname") String stname) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/distWiseblsAreawiseSurvey");
		 mav.addObject("distwiseAreaDtlList", blsAreawiseDtlsrvc.getDistWiseAreaDetail(stcode));
		 mav.addObject("stateName", stname);
		 mav.addObject("stcode", stcode);
		 
		
		return mav;
	}
	
	@RequestMapping(value = "/getProjWiseblsareasrvydtlrpt", method = RequestMethod.GET)
	public ModelAndView getProjWiseblsareasrvydtlrpt(HttpServletRequest request,  @RequestParam("stname") String stname, 
			@RequestParam("distname") String distname, @RequestParam("distcode") int distcode) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/projWiseblsAreawiseSurvey");
		 mav.addObject("projwiseAreaDtlList", blsAreawiseDtlsrvc.getProjWiseAreaDetail(distcode));
		 mav.addObject("stateName", stname);	
		 mav.addObject("distname", distname);
		 mav.addObject("distcode", distcode);
		return mav;
	}
	
	@RequestMapping(value = "/blsareaoutcomedtlrpt", method = RequestMethod.GET)
	public ModelAndView getBaseLineStwiseAreaOutcome(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/blsAreawiseOutcome");
		mav.addObject("stwiseAreaAchDtlList",blsAreawiseDtlsrvc.getStwiseAreaAchievDetail());
		return mav;
	}
	
	@RequestMapping(value = "/getDistwiseAreaAchieveDetail", method = RequestMethod.GET)
	public ModelAndView getDistwiseAreaAchieveDetail(HttpServletRequest request, @RequestParam("stcode") int stcode,@RequestParam("stname") String stname) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/DistwiseAreaAchieveDetail");
		 mav.addObject("distAchList", blsAreawiseDtlsrvc.getDistwiseAreaAchieveDetail(stcode));
		 mav.addObject("stateName", stname);
		 mav.addObject("stcode", stcode);
		return mav;
	}
	
	@RequestMapping(value = "/projwiseAreaAchieveDetail", method = RequestMethod.GET)
	public ModelAndView projwiseAreaAchieveDetail(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		
		ModelAndView mav = new ModelAndView();
	
				mav = new ModelAndView("reports/baselineoutcome/ProjwiseAreaAchieveDetail");
				 List<BaselineStateWiseAreaDetailBean> list= new ArrayList<BaselineStateWiseAreaDetailBean>();
				 list = blsAreawiseDtlsrvc.getProjwiseAreaAchieveDetail(dcode);
					
				 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(dcode).entrySet().iterator().next();
				 mav.addObject("statename",stateMasterService.getStateByDistCode(dcode).get(entry.getKey()));	
				 mav.addObject("distName",districtMasterService.getDistrictByDistCode(dcode).get(dcode));	
				mav.addObject("projAchList", list);
				mav.addObject("projAchListSize", list.size());
				mav.addObject("dcode", dcode);
		return mav;
	}

	@RequestMapping(value = "/blscrpwiseareasrvydtlrpt", method = RequestMethod.GET)
	public ModelAndView getBaseLineStwiseCrpSurvey(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/blsCropwiseAreaSrvy");
		mav.addObject("stwiseAreaSrvyDtlList",blsAreawiseDtlsrvc.getStwiseCropTypeSurveyDetail());
		return mav;
	}
	
	@RequestMapping(value = "/getDistblsCrpareaSrvyDetail", method = RequestMethod.GET)
	public ModelAndView getDistblsCrpareaSrvyDetail(HttpServletRequest request,@RequestParam("id") int id) {
		 session = request.getSession(true);
		 ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/blsDistCropwiseAreaSrvy");
		 mav.addObject("distList",blsAreawiseDtlsrvc.getDistblsCrpareaSrvyDetail(id));
		 mav.addObject("stateName",stateMasterService.getStateByStateCode(id).get(id));	
		 mav.addObject("distCode",id);
			
		return mav;
	}

	@RequestMapping(value = "/getProjWiseblsCrpareaSrvyDetail", method = RequestMethod.GET)
	public ModelAndView getProjWiseblsCrpareaSrvyDetail(HttpServletRequest request,@RequestParam("id") int id) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/blsProjCropWiseAreaDtl");
		 mav.addObject("projectList",blsAreawiseDtlsrvc.getProjWiseblsCrpareaSrvyDetail(id));
		 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(id).entrySet().iterator().next();
		 mav.addObject("stateName",stateMasterService.getStateByDistCode(id).get(entry.getKey()));	
		 mav.addObject("distName",districtMasterService.getDistrictByDistCode(id).get(id));	
		 mav.addObject("projId",id);
		 mav.addObject("id", id);
			
		return mav;
	}
	
	@RequestMapping(value = "/blscrpwiseareaoutdtlrpt", method = RequestMethod.GET)
	public ModelAndView getBaseLineStwiseCrpOutcome(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/blsCropwiseAreaOutcome");
		mav.addObject("stwiseAreaOutcomeDtlList",blsAreawiseDtlsrvc.getStwiseCropTypeOutcomeDetail());
		return mav;
	}

	@RequestMapping(value = "/distblscrpwiseareaoutdtlrpt", method = RequestMethod.GET)
	public ModelAndView distblscrpwiseareaoutdtlrpt(HttpServletRequest request, HttpServletResponse response) {
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		
		ModelAndView mav = new ModelAndView();
		try {
				mav = new ModelAndView("reports/baselineoutcome/DistblscrpwiseareaReport");
				 List<BaselineStatewiseCropDetailBean> list= new ArrayList<BaselineStatewiseCropDetailBean>();
				 list = blsAreawiseDtlsrvc.getDistwiseAreaCrpDetail(stcode);
					
				 Map.Entry<Integer,String> entry = stateMasterService.getStateByStateCode(stcode).entrySet().iterator().next();
				mav.addObject("statename",stateMasterService.getStateByStateCode(stcode).get(entry.getKey()));	
				mav.addObject("crpListt", list);
				mav.addObject("crpListtSize", list.size());
				mav.addObject("stcode", stcode);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/downloadExcelStateWiseBaselineArea", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStateWiseBaselineArea(HttpServletRequest request, HttpServletResponse response) 
	{
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<>();
			list = blsAreawiseDtlsrvc.getStwiseAreaDetail();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report SB3- State wise Distribution of Area According Baseline Survey");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report SB3- State wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and Number of Crops as per Baseline Survey";
			String areaAmtValDetail = "All area in ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,7,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,11,16); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,17,20); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,4,4);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,5,5);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,6,6);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,7,7);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,9,9);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,11,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,12,12);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,13,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
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
	        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Irrigation Status(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellStyle(style);
			
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Ownership(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(8);
			cell.setCellStyle(style);
			cell = rowhead.createCell(9);
			cell.setCellStyle(style);
			cell = rowhead.createCell(10);
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Classification of Land(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =12; i<17;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(17);
			cell.setCellValue("No. of Crops(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(18);
			cell.setCellStyle(style);
			cell = rowhead.createCell(19);
			cell.setCellStyle(style);
			cell = rowhead.createCell(20);
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Protective Irrigation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Assured Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("No Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Private");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Government");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Community");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(11);
			cell.setCellValue("Culturable Wasteland");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(13);
			cell.setCellValue("Rainfed Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Forest Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(15);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(17);
			cell.setCellValue("No Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Single Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(19);
			cell.setCellValue("Double Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(20);
			cell.setCellValue("Multiple Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<14;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(14);
			cell.setCellValue("Plantation");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("No Plantation");
			cell.setCellStyle(style);
			
			for(int i =16; i<21;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			
			Row rowhead3 = sheet.createRow(8);
			for(int i=0;i<21;i++)
			{
				cell =rowhead3.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        int sno = 1;
	        int rowno  =9;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totProIrr = 0;
	        double totAssured = 0;
	        double totNoIrri = 0;
	        double totprivt = 0;
	        double totGovt = 0;
	        double totCmnty = 0;
	        double totOtherOwnr = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totPlanArea = 0;
	        double totNoPlanArea = 0;
	        double totOtherLnd = 0;
	        double totNoCrop = 0;
	        double totSingle = 0;
	        double totDouble = 0;
	        double totMulti = 0;
	        for(BaselineStateWiseAreaDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getProtective_irrigation()==null?0.0:bean.getProtective_irrigation().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getAssured_irrigation()==null?0.0:bean.getAssured_irrigation().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getNo_irrigation()==null?0.0:bean.getNo_irrigation().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPrivate_owner()==null?0.0:bean.getPrivate_owner().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getGovt_owned()==null?0.0:bean.getGovt_owned().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getCommunity_owned()==null?0.0:bean.getCommunity_owned().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getOthers_owned()==null?0.0:bean.getOthers_owned().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	
	        	row.createCell(12).setCellValue(bean.getDegraded()==null?0.0:bean.getDegraded().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getPlantation()==null?0.0:bean.getPlantation().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getNo_plantation()==null?0.0:bean.getNo_plantation().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getOthers_classification()==null?0.0:bean.getOthers_classification().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getNo_crop()==null?0.0:bean.getNo_crop().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getSingle_crop()==null?0.0:bean.getSingle_crop().doubleValue()); 
	        	row.createCell(19).setCellValue(bean.getDouble_crop()==null?0.0:bean.getDouble_crop().doubleValue());  
	        	row.createCell(20).setCellValue(bean.getMultiple_crop()==null?0.0:bean.getMultiple_crop().doubleValue()); 
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());
	        	totProIrr = totProIrr + (bean.getProtective_irrigation()==null?0.0:bean.getProtective_irrigation().doubleValue());
	        	totAssured = totAssured + (bean.getAssured_irrigation()==null?0.0:bean.getAssured_irrigation().doubleValue());
	        	totNoIrri = totNoIrri + (bean.getNo_irrigation()==null?0.0:bean.getNo_irrigation().doubleValue()); 
	        	totprivt = totprivt + (bean.getPrivate_owner()==null?0.0:bean.getPrivate_owner().doubleValue()); 
	        	totGovt = totGovt + (bean.getGovt_owned()==null?0.0:bean.getGovt_owned().doubleValue());
	        	totCmnty = totCmnty + (bean.getCommunity_owned()==null?0.0:bean.getCommunity_owned().doubleValue());
	        	totOtherOwnr = totOtherOwnr + (bean.getOthers_owned()==null?0.0:bean.getOthers_owned().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded()==null?0.0:bean.getDegraded().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totPlanArea = totPlanArea + (bean.getPlantation()==null?0.0:bean.getPlantation().doubleValue());
	        	totNoPlanArea = totNoPlanArea + (bean.getNo_plantation()==null?0.0:bean.getNo_plantation().doubleValue());
	        	totOtherLnd = totOtherLnd + (bean.getOthers_classification()==null?0.0:bean.getOthers_classification().doubleValue());
	        	totNoCrop = totNoCrop + (bean.getNo_crop()==null?0.0:bean.getNo_crop().doubleValue());
	        	totSingle = totSingle + (bean.getSingle_crop()==null?0.0:bean.getSingle_crop().doubleValue());
	        	totDouble = totDouble + (bean.getDouble_crop()==null?0.0:bean.getDouble_crop().doubleValue());
	        	totMulti = totMulti + (bean.getMultiple_crop()==null?0.0:bean.getMultiple_crop().doubleValue());
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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totProIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totAssured);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNoIrri);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totprivt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totGovt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totCmnty);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totOtherOwnr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totPlanArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totNoPlanArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOtherLnd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totNoCrop);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totSingle);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totDouble);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totMulti);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
	        String fileName = "attachment; filename=Report SB3- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/blsAreawiseSurvey";
		
	}

	@RequestMapping(value = "/downloadblOutcomePDF", method = RequestMethod.POST)
	public ModelAndView downloadblOutcomePDF(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<BaselineStateWiseAreaDetailBean> listoutcomeareablsBean = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineOutcomeAreaReport");
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
			
				paragraph3 = new Paragraph("Report O6- State wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and No of Crops as On Date ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(21);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area_ach= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation_ach= BigDecimal.valueOf(0);
				BigDecimal assured_irrigation_ach= BigDecimal.valueOf(0);
				BigDecimal no_irrigation_ach= BigDecimal.valueOf(0);
				BigDecimal private_owner_ach= BigDecimal.valueOf(0);
				BigDecimal govt_owned_ach= BigDecimal.valueOf(0);
				BigDecimal community_owned_ach= BigDecimal.valueOf(0);
				BigDecimal others_owned_ach= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland_ach=BigDecimal.valueOf(0);
				BigDecimal degraded_ach=BigDecimal.valueOf(0);
				BigDecimal rainfed_ach=BigDecimal.valueOf(0);
				BigDecimal plantation_ach=BigDecimal.valueOf(0);
				BigDecimal no_plantation_ach=BigDecimal.valueOf(0);
				BigDecimal others_classification_ach=BigDecimal.valueOf(0);
				BigDecimal no_crop_ach=BigDecimal.valueOf(0);
				BigDecimal single_crop_ach=BigDecimal.valueOf(0);
				BigDecimal double_crop_ach=BigDecimal.valueOf(0);
				BigDecimal multiple_crop_ach=BigDecimal.valueOf(0);
				
				listoutcomeareablsBean=blsAreawiseDtlsrvc.getStwiseAreaAchievDetail();
				
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 21, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Irrigation Status(Area)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ownership(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Area)", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No of Crops(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "Protective Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Assured Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Irrigation", Element.ALIGN_CENTER, 1, 2,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Private", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Government", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Community", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Culturable WasteLand", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Forest Land", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Single Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Double Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Multiple Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int k=1;
				if(listoutcomeareablsBean.size()!=0)
					for(int i=0;i<listoutcomeareablsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getPlot_area_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getProtective_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getAssured_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getNo_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getPrivate_owner_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getGovt_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getCommunity_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getOthers_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getCultivable_wasteland_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getDegraded_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getRainfed_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getPlantation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getNo_plantation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getOthers_classification_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getNo_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getSingle_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getDouble_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listoutcomeareablsBean.get(i).getMultiple_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						project_area=project_area.add(listoutcomeareablsBean.get(i).getProject_area());
						plot_area_ach=plot_area_ach.add(listoutcomeareablsBean.get(i).getPlot_area_ach());
						protective_irrigation_ach=protective_irrigation_ach.add(listoutcomeareablsBean.get(i).getProtective_irrigation_ach());
						assured_irrigation_ach=assured_irrigation_ach.add(listoutcomeareablsBean.get(i).getAssured_irrigation_ach());
						no_irrigation_ach=no_irrigation_ach.add(listoutcomeareablsBean.get(i).getNo_irrigation_ach());
						private_owner_ach=private_owner_ach.add(listoutcomeareablsBean.get(i).getPrivate_owner_ach());
						govt_owned_ach=govt_owned_ach.add(listoutcomeareablsBean.get(i).getGovt_owned_ach());
						community_owned_ach=community_owned_ach.add(listoutcomeareablsBean.get(i).getCommunity_owned_ach());
						others_owned_ach=others_owned_ach.add(listoutcomeareablsBean.get(i).getOthers_owned_ach());
						cultivable_wasteland_ach=cultivable_wasteland_ach.add(listoutcomeareablsBean.get(i).getCultivable_wasteland_ach());
						degraded_ach=degraded_ach.add(listoutcomeareablsBean.get(i).getDegraded_ach());
						rainfed_ach=rainfed_ach.add(listoutcomeareablsBean.get(i).getRainfed_ach());
						plantation_ach=plantation_ach.add(listoutcomeareablsBean.get(i).getPlantation_ach());
						no_plantation_ach=no_plantation_ach.add(listoutcomeareablsBean.get(i).getNo_plantation_ach());
						others_classification_ach=others_classification_ach.add(listoutcomeareablsBean.get(i).getOthers_classification_ach());
						no_crop_ach=no_crop_ach.add(listoutcomeareablsBean.get(i).getNo_crop_ach());
						single_crop_ach=single_crop_ach.add(listoutcomeareablsBean.get(i).getSingle_crop_ach());
						double_crop_ach=double_crop_ach.add(listoutcomeareablsBean.get(i).getDouble_crop_ach());
						multiple_crop_ach=multiple_crop_ach.add(listoutcomeareablsBean.get(i).getMultiple_crop_ach());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(assured_irrigation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_irrigation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(private_owner_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(govt_owned_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(community_owned_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_owned_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_plantation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_classification_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(single_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(double_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(multiple_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listoutcomeareablsBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-O6.pdf");
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
	
	@RequestMapping(value = "/downloadblcropPDF", method = RequestMethod.POST)
	public ModelAndView downloadblcropPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<BaselineStatewiseCropDetailBean> listblscropBean = new ArrayList<BaselineStatewiseCropDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineOutcomeAreaReport");
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
			
				paragraph3 = new Paragraph("Report O7- State wise Distribution of Area According to Crop Type as On Date ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(20);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area_ach= BigDecimal.valueOf(0);
				BigDecimal cereals_ach= BigDecimal.valueOf(0);
				BigDecimal cereals_prod_ach= BigDecimal.valueOf(0);
				BigDecimal pulses_ach= BigDecimal.valueOf(0);
				BigDecimal pulses_prod_ach= BigDecimal.valueOf(0);
				BigDecimal oilseeds_ach= BigDecimal.valueOf(0);
				BigDecimal oilseeds_prod_ach= BigDecimal.valueOf(0);
				BigDecimal fiber_crops_ach= BigDecimal.valueOf(0);
				BigDecimal fiber_crops_prod_ach=BigDecimal.valueOf(0);
				BigDecimal pasture_ach=BigDecimal.valueOf(0);
				BigDecimal pasture_prod_ach=BigDecimal.valueOf(0);
				BigDecimal horticulture_ach=BigDecimal.valueOf(0);
				BigDecimal horticulture_prod_ach=BigDecimal.valueOf(0);
				BigDecimal plantation_crops_ach=BigDecimal.valueOf(0);
				BigDecimal plantation_crops_prod_ach=BigDecimal.valueOf(0);
				BigDecimal other_crops_ach=BigDecimal.valueOf(0);
				BigDecimal other_crops_prod_ach=BigDecimal.valueOf(0);
				
				listblscropBean=blsAreawiseDtlsrvc.getStwiseCropTypeOutcomeDetail();
				
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 20, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Crop Type", Element.ALIGN_CENTER, 16, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cereals", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pulses", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Oil Seeds", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Fiber Crops", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pastures", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Horticultures", Element.ALIGN_CENTER, 2, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plantations", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
				int k=1;
				if(listblscropBean.size()!=0)
					for(int i=0;i<listblscropBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getPlot_area_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getCereals_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getCereals_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getPulses_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getPulses_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getOilseeds_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getOilseeds_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getFiber_crops_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getFiber_crops_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getPasture_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getPasture_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getHorticulture_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getHorticulture_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getPlantation_crops_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getPlantation_crops_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getOther_crops_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblscropBean.get(i).getOther_crops_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						project_area=project_area.add(listblscropBean.get(i).getProject_area());
						plot_area_ach=plot_area_ach.add(listblscropBean.get(i).getPlot_area_ach());
						cereals_ach=cereals_ach.add(listblscropBean.get(i).getCereals_ach());
						cereals_prod_ach=cereals_prod_ach.add(listblscropBean.get(i).getCereals_prod_ach());
						pulses_ach=pulses_ach.add(listblscropBean.get(i).getPulses_ach());
						pulses_prod_ach=pulses_prod_ach.add(listblscropBean.get(i).getPulses_prod_ach());
						oilseeds_ach=oilseeds_ach.add(listblscropBean.get(i).getOilseeds_ach());
						oilseeds_prod_ach=oilseeds_prod_ach.add(listblscropBean.get(i).getOilseeds_prod_ach());
						fiber_crops_ach=fiber_crops_ach.add(listblscropBean.get(i).getFiber_crops_ach());
						fiber_crops_prod_ach=fiber_crops_prod_ach.add(listblscropBean.get(i).getFiber_crops_prod_ach());
						pasture_ach=pasture_ach.add(listblscropBean.get(i).getPasture_ach());
						pasture_prod_ach=pasture_prod_ach.add(listblscropBean.get(i).getPasture_prod_ach());
						horticulture_ach=horticulture_ach.add(listblscropBean.get(i).getHorticulture_ach());
						horticulture_prod_ach=horticulture_prod_ach.add(listblscropBean.get(i).getHorticulture_prod_ach());
						plantation_crops_ach=plantation_crops_ach.add(listblscropBean.get(i).getPlantation_crops_ach());
						plantation_crops_prod_ach=plantation_crops_prod_ach.add(listblscropBean.get(i).getPlantation_crops_prod_ach());
						other_crops_ach=other_crops_ach.add(listblscropBean.get(i).getOther_crops_ach());
						other_crops_prod_ach=other_crops_prod_ach.add(listblscropBean.get(i).getOther_crops_prod_ach());
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cereals_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cereals_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pulses_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pulses_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oilseeds_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oilseeds_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fiber_crops_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fiber_crops_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pasture_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pasture_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(horticulture_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(horticulture_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_crops_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_crops_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other_crops_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other_crops_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(listblscropBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 20, 1, bf8);
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-O7.pdf");
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
	
	
	@RequestMapping(value = "/downloadDistWiseblcropPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistWiseblcropPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcode=request.getParameter("stcode");
		String statename=request.getParameter("statename");
		List<BaselineStatewiseCropDetailBean> list = new ArrayList<BaselineStatewiseCropDetailBean>();
		list = blsAreawiseDtlsrvc.getDistwiseAreaCrpDetail(Integer.parseInt(statename));
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineOutcomeAreaReport");
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
			
				paragraph3 = new Paragraph("Report O7- District wise Distribution of Area According to Crop Type as On Date for State  '"+stcode+"'  ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(20);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area_ach= BigDecimal.valueOf(0);
				BigDecimal cereals_ach= BigDecimal.valueOf(0);
				BigDecimal cereals_prod_ach= BigDecimal.valueOf(0);
				BigDecimal pulses_ach= BigDecimal.valueOf(0);
				BigDecimal pulses_prod_ach= BigDecimal.valueOf(0);
				BigDecimal oilseeds_ach= BigDecimal.valueOf(0);
				BigDecimal oilseeds_prod_ach= BigDecimal.valueOf(0);
				BigDecimal fiber_crops_ach= BigDecimal.valueOf(0);
				BigDecimal fiber_crops_prod_ach=BigDecimal.valueOf(0);
				BigDecimal pasture_ach=BigDecimal.valueOf(0);
				BigDecimal pasture_prod_ach=BigDecimal.valueOf(0);
				BigDecimal horticulture_ach=BigDecimal.valueOf(0);
				BigDecimal horticulture_prod_ach=BigDecimal.valueOf(0);
				BigDecimal plantation_crops_ach=BigDecimal.valueOf(0);
				BigDecimal plantation_crops_prod_ach=BigDecimal.valueOf(0);
				BigDecimal other_crops_ach=BigDecimal.valueOf(0);
				BigDecimal other_crops_prod_ach=BigDecimal.valueOf(0);
								
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 20, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Crop Type", Element.ALIGN_CENTER, 16, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cereals", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pulses", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Oil Seeds", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Fiber Crops", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pastures", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Horticultures", Element.ALIGN_CENTER, 2, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plantations", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
				int k=1;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlot_area_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCereals_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCereals_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPulses_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPulses_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOilseeds_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOilseeds_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFiber_crops_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFiber_crops_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPasture_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPasture_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getHorticulture_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getHorticulture_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlantation_crops_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlantation_crops_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOther_crops_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOther_crops_prod_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						project_area=project_area.add(list.get(i).getProject_area());
						plot_area_ach=plot_area_ach.add(list.get(i).getPlot_area_ach());
						cereals_ach=cereals_ach.add(list.get(i).getCereals_ach());
						cereals_prod_ach=cereals_prod_ach.add(list.get(i).getCereals_prod_ach());
						pulses_ach=pulses_ach.add(list.get(i).getPulses_ach());
						pulses_prod_ach=pulses_prod_ach.add(list.get(i).getPulses_prod_ach());
						oilseeds_ach=oilseeds_ach.add(list.get(i).getOilseeds_ach());
						oilseeds_prod_ach=oilseeds_prod_ach.add(list.get(i).getOilseeds_prod_ach());
						fiber_crops_ach=fiber_crops_ach.add(list.get(i).getFiber_crops_ach());
						fiber_crops_prod_ach=fiber_crops_prod_ach.add(list.get(i).getFiber_crops_prod_ach());
						pasture_ach=pasture_ach.add(list.get(i).getPasture_ach());
						pasture_prod_ach=pasture_prod_ach.add(list.get(i).getPasture_prod_ach());
						horticulture_ach=horticulture_ach.add(list.get(i).getHorticulture_ach());
						horticulture_prod_ach=horticulture_prod_ach.add(list.get(i).getHorticulture_prod_ach());
						plantation_crops_ach=plantation_crops_ach.add(list.get(i).getPlantation_crops_ach());
						plantation_crops_prod_ach=plantation_crops_prod_ach.add(list.get(i).getPlantation_crops_prod_ach());
						other_crops_ach=other_crops_ach.add(list.get(i).getOther_crops_ach());
						other_crops_prod_ach=other_crops_prod_ach.add(list.get(i).getOther_crops_prod_ach());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cereals_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cereals_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pulses_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pulses_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oilseeds_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oilseeds_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fiber_crops_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fiber_crops_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pasture_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pasture_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(horticulture_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(horticulture_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_crops_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_crops_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other_crops_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other_crops_prod_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(list.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 20, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-O7(DistrictReport).pdf");
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
	@RequestMapping(value = "/downloadblsurveyPDF", method = RequestMethod.POST)
	public ModelAndView downloadblsurveyPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<BaselineStateWiseAreaDetailBean> listareablsBean = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineSurveyAreaReport");
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
			
				paragraph3 = new Paragraph("Report SB3- State wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and Number of Crops as per Baseline Survey ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(21);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation= BigDecimal.valueOf(0);
				BigDecimal assured_irrigation= BigDecimal.valueOf(0);
				BigDecimal no_irrigation= BigDecimal.valueOf(0);
				BigDecimal private_owner= BigDecimal.valueOf(0);
				BigDecimal govt_owned= BigDecimal.valueOf(0);
				BigDecimal community_owned= BigDecimal.valueOf(0);
				BigDecimal others_owned= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland=BigDecimal.valueOf(0);
				BigDecimal degraded=BigDecimal.valueOf(0);
				BigDecimal rainfed=BigDecimal.valueOf(0);
				BigDecimal plantation=BigDecimal.valueOf(0);
				BigDecimal no_plantation=BigDecimal.valueOf(0);
				BigDecimal others_classification=BigDecimal.valueOf(0);
				BigDecimal no_crop=BigDecimal.valueOf(0);
				BigDecimal single_crop=BigDecimal.valueOf(0);
				BigDecimal double_crop=BigDecimal.valueOf(0);
				BigDecimal multiple_crop=BigDecimal.valueOf(0);
				
				listareablsBean=blsAreawiseDtlsrvc.getStwiseAreaDetail();
				
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 21, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Irrigation Status(Area)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ownership(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Area)", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No of Crops(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "Protective Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Assured Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Irrigation", Element.ALIGN_CENTER, 1, 2,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Private", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Government", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Community", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Culturable WasteLand", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Forest Land", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Single Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Double Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Multiple Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int k=1;
				if(listareablsBean.size()!=0)
					for(int i=0;i<listareablsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProtective_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getAssured_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPrivate_owner().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getGovt_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getCommunity_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getOthers_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getDegraded().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPlantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_plantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getOthers_classification().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getSingle_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getDouble_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getMultiple_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						project_area=project_area.add(listareablsBean.get(i).getProject_area());
						plot_area=plot_area.add(listareablsBean.get(i).getPlot_area());
						protective_irrigation=protective_irrigation.add(listareablsBean.get(i).getProtective_irrigation());
						assured_irrigation=assured_irrigation.add(listareablsBean.get(i).getAssured_irrigation());
						no_irrigation=no_irrigation.add(listareablsBean.get(i).getNo_irrigation());
						private_owner=private_owner.add(listareablsBean.get(i).getPrivate_owner());
						govt_owned=govt_owned.add(listareablsBean.get(i).getGovt_owned());
						community_owned=community_owned.add(listareablsBean.get(i).getCommunity_owned());
						others_owned=others_owned.add(listareablsBean.get(i).getOthers_owned());
						cultivable_wasteland=cultivable_wasteland.add(listareablsBean.get(i).getCultivable_wasteland());
						degraded=degraded.add(listareablsBean.get(i).getDegraded());
						rainfed=rainfed.add(listareablsBean.get(i).getRainfed());
						plantation=plantation.add(listareablsBean.get(i).getPlantation());
						no_plantation=no_plantation.add(listareablsBean.get(i).getNo_plantation());
						others_classification=others_classification.add(listareablsBean.get(i).getOthers_classification());
						no_crop=no_crop.add(listareablsBean.get(i).getNo_crop());
						single_crop=single_crop.add(listareablsBean.get(i).getSingle_crop());
						double_crop=double_crop.add(listareablsBean.get(i).getDouble_crop());
						multiple_crop=multiple_crop.add(listareablsBean.get(i).getMultiple_crop());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(assured_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(private_owner), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(govt_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(community_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_classification), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(single_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(double_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(multiple_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listareablsBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-SB3.pdf");
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
	
	@RequestMapping(value = "/downloadblsurveyPDFDistrictWise", method = RequestMethod.POST)
	public ModelAndView downloadblsurveyPDFDistrictWise(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String stcode=request.getParameter("stcode");
		List<BaselineStateWiseAreaDetailBean> listareablsBean = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineSurveyAreaReport");
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
			
				paragraph3 = new Paragraph("Report SB3-District wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and Number of Crops as per Baseline Survey", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(21);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation= BigDecimal.valueOf(0);
				BigDecimal assured_irrigation= BigDecimal.valueOf(0);
				BigDecimal no_irrigation= BigDecimal.valueOf(0);
				BigDecimal private_owner= BigDecimal.valueOf(0);
				BigDecimal govt_owned= BigDecimal.valueOf(0);
				BigDecimal community_owned= BigDecimal.valueOf(0);
				BigDecimal others_owned= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland=BigDecimal.valueOf(0);
				BigDecimal degraded=BigDecimal.valueOf(0);
				BigDecimal rainfed=BigDecimal.valueOf(0);
				BigDecimal plantation=BigDecimal.valueOf(0);
				BigDecimal no_plantation=BigDecimal.valueOf(0);
				BigDecimal others_classification=BigDecimal.valueOf(0);
				BigDecimal no_crop=BigDecimal.valueOf(0);
				BigDecimal single_crop=BigDecimal.valueOf(0);
				BigDecimal double_crop=BigDecimal.valueOf(0);
				BigDecimal multiple_crop=BigDecimal.valueOf(0);
				
				listareablsBean=blsAreawiseDtlsrvc.getDistWiseAreaDetail(Integer.parseInt(stcode));
				CommonFunctions.insertCellHeader(table, "State : "+stateName, Element.ALIGN_LEFT, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 11, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Irrigation Status(Area)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ownership(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Area)", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No of Crops(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "Protective Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Assured Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Irrigation", Element.ALIGN_CENTER, 1, 2,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Private", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Government", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Community", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Culturable WasteLand", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Forest Land", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Single Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Double Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Multiple Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int k=1;
				if(listareablsBean.size()!=0)
					for(int i=0;i<listareablsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProtective_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getAssured_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPrivate_owner().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getGovt_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getCommunity_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getOthers_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getDegraded().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPlantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_plantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getOthers_classification().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getSingle_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getDouble_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getMultiple_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						project_area=project_area.add(listareablsBean.get(i).getProject_area());
						plot_area=plot_area.add(listareablsBean.get(i).getPlot_area());
						protective_irrigation=protective_irrigation.add(listareablsBean.get(i).getProtective_irrigation());
						assured_irrigation=assured_irrigation.add(listareablsBean.get(i).getAssured_irrigation());
						no_irrigation=no_irrigation.add(listareablsBean.get(i).getNo_irrigation());
						private_owner=private_owner.add(listareablsBean.get(i).getPrivate_owner());
						govt_owned=govt_owned.add(listareablsBean.get(i).getGovt_owned());
						community_owned=community_owned.add(listareablsBean.get(i).getCommunity_owned());
						others_owned=others_owned.add(listareablsBean.get(i).getOthers_owned());
						cultivable_wasteland=cultivable_wasteland.add(listareablsBean.get(i).getCultivable_wasteland());
						degraded=degraded.add(listareablsBean.get(i).getDegraded());
						rainfed=rainfed.add(listareablsBean.get(i).getRainfed());
						plantation=plantation.add(listareablsBean.get(i).getPlantation());
						no_plantation=no_plantation.add(listareablsBean.get(i).getNo_plantation());
						others_classification=others_classification.add(listareablsBean.get(i).getOthers_classification());
						no_crop=no_crop.add(listareablsBean.get(i).getNo_crop());
						single_crop=single_crop.add(listareablsBean.get(i).getSingle_crop());
						double_crop=double_crop.add(listareablsBean.get(i).getDouble_crop());
						multiple_crop=multiple_crop.add(listareablsBean.get(i).getMultiple_crop());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(assured_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(private_owner), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(govt_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(community_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_classification), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(single_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(double_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(multiple_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listareablsBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-SB3-districtwise.pdf");
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
	
	
	@RequestMapping(value = "/downloadblsurveyPDFProjectWise", method = RequestMethod.POST)
	public ModelAndView downloadblsurveyPDFProjectWise(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String distname=request.getParameter("distname");
		String distcode=request.getParameter("distcode");
		List<BaselineStateWiseAreaDetailBean> listareablsBean = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineSurveyAreaReport");
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
			
				paragraph3 = new Paragraph("Report SB3-Project wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and Number of Crops as per Baseline Survey", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(21);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation= BigDecimal.valueOf(0);
				BigDecimal assured_irrigation= BigDecimal.valueOf(0);
				BigDecimal no_irrigation= BigDecimal.valueOf(0);
				BigDecimal private_owner= BigDecimal.valueOf(0);
				BigDecimal govt_owned= BigDecimal.valueOf(0);
				BigDecimal community_owned= BigDecimal.valueOf(0);
				BigDecimal others_owned= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland=BigDecimal.valueOf(0);
				BigDecimal degraded=BigDecimal.valueOf(0);
				BigDecimal rainfed=BigDecimal.valueOf(0);
				BigDecimal plantation=BigDecimal.valueOf(0);
				BigDecimal no_plantation=BigDecimal.valueOf(0);
				BigDecimal others_classification=BigDecimal.valueOf(0);
				BigDecimal no_crop=BigDecimal.valueOf(0);
				BigDecimal single_crop=BigDecimal.valueOf(0);
				BigDecimal double_crop=BigDecimal.valueOf(0);
				BigDecimal multiple_crop=BigDecimal.valueOf(0);
			
				listareablsBean=blsAreawiseDtlsrvc.getProjWiseAreaDetail(Integer.parseInt(distcode));
				CommonFunctions.insertCellHeader(table, "State : "+stateName+"  District : "+distname, Element.ALIGN_LEFT, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 11, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Irrigation Status(Area)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ownership(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Area)", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No of Crops(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "Protective Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Assured Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Irrigation", Element.ALIGN_CENTER, 1, 2,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Private", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Government", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Community", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Culturable WasteLand", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Forest Land", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Single Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Double Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Multiple Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int k=1;
				if(listareablsBean.size()!=0)
					for(int i=0;i<listareablsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProtective_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getAssured_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPrivate_owner().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getGovt_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getCommunity_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getOthers_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getDegraded().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPlantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_plantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getOthers_classification().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getSingle_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getDouble_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getMultiple_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						project_area=project_area.add(listareablsBean.get(i).getProject_area());
						plot_area=plot_area.add(listareablsBean.get(i).getPlot_area());
						protective_irrigation=protective_irrigation.add(listareablsBean.get(i).getProtective_irrigation());
						assured_irrigation=assured_irrigation.add(listareablsBean.get(i).getAssured_irrigation());
						no_irrigation=no_irrigation.add(listareablsBean.get(i).getNo_irrigation());
						private_owner=private_owner.add(listareablsBean.get(i).getPrivate_owner());
						govt_owned=govt_owned.add(listareablsBean.get(i).getGovt_owned());
						community_owned=community_owned.add(listareablsBean.get(i).getCommunity_owned());
						others_owned=others_owned.add(listareablsBean.get(i).getOthers_owned());
						cultivable_wasteland=cultivable_wasteland.add(listareablsBean.get(i).getCultivable_wasteland());
						degraded=degraded.add(listareablsBean.get(i).getDegraded());
						rainfed=rainfed.add(listareablsBean.get(i).getRainfed());
						plantation=plantation.add(listareablsBean.get(i).getPlantation());
						no_plantation=no_plantation.add(listareablsBean.get(i).getNo_plantation());
						others_classification=others_classification.add(listareablsBean.get(i).getOthers_classification());
						no_crop=no_crop.add(listareablsBean.get(i).getNo_crop());
						single_crop=single_crop.add(listareablsBean.get(i).getSingle_crop());
						double_crop=double_crop.add(listareablsBean.get(i).getDouble_crop());
						multiple_crop=multiple_crop.add(listareablsBean.get(i).getMultiple_crop());
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(assured_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(private_owner), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(govt_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(community_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_classification), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(single_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(double_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(multiple_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
				
					if(listareablsBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-SB3-projectwise.pdf");
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
	

	@RequestMapping(value = "/downloadblcropWiseAreaSurveyPDF", method = RequestMethod.POST)
	public ModelAndView downloadblcropWiseAreaSurveyPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<BaselineStatewiseCropDetailBean> blscropBean = new ArrayList<BaselineStatewiseCropDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("blsropWiseAreaSurveyReport");
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
			
				paragraph3 = new Paragraph("Report SB4- State wise Distribution of Area According to Crop Type as per Baseline Survey ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(20);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal cereals= BigDecimal.valueOf(0);
				BigDecimal cereals_prod= BigDecimal.valueOf(0);
				BigDecimal pulses= BigDecimal.valueOf(0);
				BigDecimal pulses_prod= BigDecimal.valueOf(0);
				BigDecimal oilseeds= BigDecimal.valueOf(0);
				BigDecimal oilseeds_prod= BigDecimal.valueOf(0);
				BigDecimal fiber_crops= BigDecimal.valueOf(0);
				BigDecimal fiber_crops_prod=BigDecimal.valueOf(0);
				BigDecimal pasture=BigDecimal.valueOf(0);
				BigDecimal pasture_prod=BigDecimal.valueOf(0);
				BigDecimal horticulture=BigDecimal.valueOf(0);
				BigDecimal horticulture_prod=BigDecimal.valueOf(0);
				BigDecimal plantation_crops=BigDecimal.valueOf(0);
				BigDecimal plantation_crops_prod=BigDecimal.valueOf(0);
				BigDecimal other_crops=BigDecimal.valueOf(0);
				BigDecimal other_crops_prod=BigDecimal.valueOf(0);
				
				blscropBean=blsAreawiseDtlsrvc.getStwiseCropTypeSurveyDetail();
				
				CommonFunctions.insertCellHeader(table, "All area in ha. And All Production in unit Quintal", Element.ALIGN_RIGHT, 20, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Crop Type", Element.ALIGN_CENTER, 16, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Cereals", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pulses", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Oil Seeds", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Fiber Crops", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pastures", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Horticultures", Element.ALIGN_CENTER, 2, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plantations", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
				int k=1;
				if(blscropBean.size()!=0)
					for(int i=0;i<blscropBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getProject_area()==null?"":blscropBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getPlot_area()==null?"":blscropBean.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getCereals()==null?"":blscropBean.get(i).getCereals().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getCereals_prod()==null?"":blscropBean.get(i).getCereals_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getPulses()==null?"":blscropBean.get(i).getPulses().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getPulses_prod()==null?"":blscropBean.get(i).getPulses_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getOilseeds()==null?"":blscropBean.get(i).getOilseeds().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getOilseeds_prod()==null?"":blscropBean.get(i).getOilseeds_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getFiber_crops()==null?"":blscropBean.get(i).getFiber_crops().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getFiber_crops_prod()==null?"":blscropBean.get(i).getFiber_crops_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getPasture()==null?"":blscropBean.get(i).getPasture().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getPasture_prod()==null?"":blscropBean.get(i).getPasture_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getHorticulture()==null?"":blscropBean.get(i).getHorticulture().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getHorticulture_prod()==null?"":blscropBean.get(i).getHorticulture_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getPlantation_crops()==null?"":blscropBean.get(i).getPlantation_crops().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getPlantation_crops_prod()==null?"":blscropBean.get(i).getPlantation_crops_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getOther_crops()==null?"":blscropBean.get(i).getOther_crops().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blscropBean.get(i).getOther_crops_prod()==null?"":blscropBean.get(i).getOther_crops_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						k=k+1;
						project_area=project_area.add(blscropBean.get(i).getProject_area()==null?BigDecimal.ZERO:blscropBean.get(i).getProject_area());
						plot_area=plot_area.add(blscropBean.get(i).getPlot_area()==null?BigDecimal.ZERO:blscropBean.get(i).getPlot_area());
						cereals=cereals.add(blscropBean.get(i).getCereals()==null?BigDecimal.ZERO:blscropBean.get(i).getCereals());
						cereals_prod=cereals_prod.add(blscropBean.get(i).getCereals_prod()==null?BigDecimal.ZERO:blscropBean.get(i).getCereals_prod());
						pulses=pulses.add(blscropBean.get(i).getPulses()==null?BigDecimal.ZERO:blscropBean.get(i).getPulses());
						pulses_prod=pulses_prod.add(blscropBean.get(i).getPulses_prod()==null?BigDecimal.ZERO:blscropBean.get(i).getPulses_prod());
						oilseeds=oilseeds.add(blscropBean.get(i).getOilseeds()==null?BigDecimal.ZERO:blscropBean.get(i).getOilseeds());
						oilseeds_prod=oilseeds_prod.add(blscropBean.get(i).getOilseeds_prod()==null?BigDecimal.ZERO:blscropBean.get(i).getOilseeds_prod());
						fiber_crops=fiber_crops.add(blscropBean.get(i).getFiber_crops()==null?BigDecimal.ZERO:blscropBean.get(i).getFiber_crops());
						fiber_crops_prod=fiber_crops_prod.add(blscropBean.get(i).getFiber_crops_prod()==null?BigDecimal.ZERO:blscropBean.get(i).getFiber_crops_prod());
						pasture=pasture.add(blscropBean.get(i).getPasture()==null?BigDecimal.ZERO:blscropBean.get(i).getPasture());
						pasture_prod=pasture_prod.add(blscropBean.get(i).getPasture_prod()==null?BigDecimal.ZERO:blscropBean.get(i).getPasture_prod());
						horticulture=horticulture.add(blscropBean.get(i).getHorticulture()==null?BigDecimal.ZERO:blscropBean.get(i).getHorticulture());
						horticulture_prod=horticulture_prod.add(blscropBean.get(i).getHorticulture_prod()==null?BigDecimal.ZERO:blscropBean.get(i).getHorticulture_prod());
						plantation_crops=plantation_crops.add(blscropBean.get(i).getPlantation_crops()==null?BigDecimal.ZERO:blscropBean.get(i).getPlantation_crops());
						plantation_crops_prod=plantation_crops_prod.add(blscropBean.get(i).getPlantation_crops_prod()==null?BigDecimal.ZERO:blscropBean.get(i).getPlantation_crops_prod());
						other_crops=other_crops.add(blscropBean.get(i).getOther_crops()==null?BigDecimal.ZERO:blscropBean.get(i).getOther_crops());
						other_crops_prod=other_crops_prod.add(blscropBean.get(i).getOther_crops_prod()==null?BigDecimal.ZERO:blscropBean.get(i).getOther_crops_prod());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cereals), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cereals_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pulses), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pulses_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oilseeds), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oilseeds_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fiber_crops), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fiber_crops_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pasture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pasture_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(horticulture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(horticulture_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_crops), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_crops_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other_crops), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other_crops_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(blscropBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 20, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-SB4.pdf");
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
	@RequestMapping(value = "/downloadblsDistWisecropAreaSurveyPDF", method = RequestMethod.POST)
	public ModelAndView downloadblsDistWisecropAreaSurveyPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String distCode=request.getParameter("distCode");
		List<BaselineStatewiseCropDetailBean> list = new ArrayList<BaselineStatewiseCropDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("blsDistWiseCropAreaDtlReport");
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
			
				paragraph3 = new Paragraph("Report SB4- District wise Distribution of Area According to Crop Type as per Baseline Survey for State \r\n"+ "'"+stateName+"'", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(20);
				table.setWidths(new int[] { 2, 8, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal cereals= BigDecimal.valueOf(0);
				BigDecimal cereals_prod= BigDecimal.valueOf(0);
				BigDecimal pulses= BigDecimal.valueOf(0);
				BigDecimal pulses_prod= BigDecimal.valueOf(0);
				BigDecimal oilseeds= BigDecimal.valueOf(0);
				BigDecimal oilseeds_prod= BigDecimal.valueOf(0);
				BigDecimal fiber_crops= BigDecimal.valueOf(0);
				BigDecimal fiber_crops_prod=BigDecimal.valueOf(0);
				BigDecimal pasture=BigDecimal.valueOf(0);
				BigDecimal pasture_prod=BigDecimal.valueOf(0);
				BigDecimal horticulture=BigDecimal.valueOf(0);
				BigDecimal horticulture_prod=BigDecimal.valueOf(0);
				BigDecimal plantation_crops=BigDecimal.valueOf(0);
				BigDecimal plantation_crops_prod=BigDecimal.valueOf(0);
				BigDecimal other_crops=BigDecimal.valueOf(0);
				BigDecimal other_crops_prod=BigDecimal.valueOf(0);
				
				list=blsAreawiseDtlsrvc.getDistblsCrpareaSrvyDetail(Integer.parseInt( distCode));
				CommonFunctions.insertCellHeader(table, "State:- "+stateName, Element.ALIGN_LEFT, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All Production in unit Quintal", Element.ALIGN_RIGHT, 16, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Crop Type", Element.ALIGN_CENTER, 16, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Cereals", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pulses", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Oil Seeds", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Fiber Crops", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pastures", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Horticultures", Element.ALIGN_CENTER, 2, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plantations", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
				int k=1;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProject_area()==null?"":list.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlot_area()==null?"":list.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCereals()==null?"":list.get(i).getCereals().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCereals_prod()==null?"":list.get(i).getCereals_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPulses()==null?"":list.get(i).getPulses().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPulses_prod()==null?"":list.get(i).getPulses_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOilseeds()==null?"":list.get(i).getOilseeds().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOilseeds_prod()==null?"":list.get(i).getOilseeds_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFiber_crops()==null?"":list.get(i).getFiber_crops().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFiber_crops_prod()==null?"":list.get(i).getFiber_crops_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPasture()==null?"":list.get(i).getPasture().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPasture_prod()==null?"":list.get(i).getPasture_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getHorticulture()==null?"":list.get(i).getHorticulture().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getHorticulture_prod()==null?"":list.get(i).getHorticulture_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlantation_crops()==null?"":list.get(i).getPlantation_crops().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlantation_crops_prod()==null?"":list.get(i).getPlantation_crops_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOther_crops()==null?"":list.get(i).getOther_crops().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOther_crops_prod()==null?"":list.get(i).getOther_crops_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						k=k+1;
						project_area=project_area.add(list.get(i).getProject_area()==null?BigDecimal.ZERO:list.get(i).getProject_area());
						plot_area=plot_area.add(list.get(i).getPlot_area()==null?BigDecimal.ZERO:list.get(i).getPlot_area());
						cereals=cereals.add(list.get(i).getCereals()==null?BigDecimal.ZERO:list.get(i).getCereals());
						cereals_prod=cereals_prod.add(list.get(i).getCereals_prod()==null?BigDecimal.ZERO:list.get(i).getCereals_prod());
						pulses=pulses.add(list.get(i).getPulses()==null?BigDecimal.ZERO:list.get(i).getPulses());
						pulses_prod=pulses_prod.add(list.get(i).getPulses_prod()==null?BigDecimal.ZERO:list.get(i).getPulses_prod());
						oilseeds=oilseeds.add(list.get(i).getOilseeds()==null?BigDecimal.ZERO:list.get(i).getOilseeds());
						oilseeds_prod=oilseeds_prod.add(list.get(i).getOilseeds_prod()==null?BigDecimal.ZERO:list.get(i).getOilseeds_prod());
						fiber_crops=fiber_crops.add(list.get(i).getFiber_crops()==null?BigDecimal.ZERO:list.get(i).getFiber_crops());
						fiber_crops_prod=fiber_crops_prod.add(list.get(i).getFiber_crops_prod()==null?BigDecimal.ZERO:list.get(i).getFiber_crops_prod());
						pasture=pasture.add(list.get(i).getPasture()==null?BigDecimal.ZERO:list.get(i).getPasture());
						pasture_prod=pasture_prod.add(list.get(i).getPasture_prod()==null?BigDecimal.ZERO:list.get(i).getPasture_prod());
						horticulture=horticulture.add(list.get(i).getHorticulture()==null?BigDecimal.ZERO:list.get(i).getHorticulture());
						horticulture_prod=horticulture_prod.add(list.get(i).getHorticulture_prod()==null?BigDecimal.ZERO:list.get(i).getHorticulture_prod());
						plantation_crops=plantation_crops.add(list.get(i).getPlantation_crops()==null?BigDecimal.ZERO:list.get(i).getPlantation_crops());
						plantation_crops_prod=plantation_crops_prod.add(list.get(i).getPlantation_crops_prod()==null?BigDecimal.ZERO:list.get(i).getPlantation_crops_prod());
						other_crops=other_crops.add(list.get(i).getOther_crops()==null?BigDecimal.ZERO:list.get(i).getOther_crops());
						other_crops_prod=other_crops_prod.add(list.get(i).getOther_crops_prod()==null?BigDecimal.ZERO:list.get(i).getOther_crops_prod());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cereals), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cereals_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pulses), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pulses_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oilseeds), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oilseeds_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fiber_crops), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fiber_crops_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pasture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pasture_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(horticulture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(horticulture_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_crops), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_crops_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other_crops), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other_crops_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(list.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 20, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-SB4DistReport.pdf");
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
	
	@RequestMapping(value = "/downloadblsProjectWisecropAreaSurveyPDF", method = RequestMethod.POST)
	public ModelAndView downloadblsProjectWisecropAreaSurveyPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projId=request.getParameter("projId");
		List<BaselineStatewiseCropDetailBean> list = new ArrayList<BaselineStatewiseCropDetailBean>();
		list=blsAreawiseDtlsrvc.getProjWiseblsCrpareaSrvyDetail(Integer.parseInt(projId));
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("blsProjWiseCropAreaDtlReport");
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
			
				paragraph3 = new Paragraph("Report SB4- Project wise Distribution of Area According to Crop Type as per Baseline Survey for District \r\n"+ "'"+distName+"'", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(20);
				table.setWidths(new int[] { 2, 8, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal cereals= BigDecimal.valueOf(0);
				BigDecimal cereals_prod= BigDecimal.valueOf(0);
				BigDecimal pulses= BigDecimal.valueOf(0);
				BigDecimal pulses_prod= BigDecimal.valueOf(0);
				BigDecimal oilseeds= BigDecimal.valueOf(0);
				BigDecimal oilseeds_prod= BigDecimal.valueOf(0);
				BigDecimal fiber_crops= BigDecimal.valueOf(0);
				BigDecimal fiber_crops_prod=BigDecimal.valueOf(0);
				BigDecimal pasture=BigDecimal.valueOf(0);
				BigDecimal pasture_prod=BigDecimal.valueOf(0);
				BigDecimal horticulture=BigDecimal.valueOf(0);
				BigDecimal horticulture_prod=BigDecimal.valueOf(0);
				BigDecimal plantation_crops=BigDecimal.valueOf(0);
				BigDecimal plantation_crops_prod=BigDecimal.valueOf(0);
				BigDecimal other_crops=BigDecimal.valueOf(0);
				BigDecimal other_crops_prod=BigDecimal.valueOf(0);
				
				CommonFunctions.insertCellHeader(table, "State:- "+stateName, Element.ALIGN_LEFT, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All Production in unit Quintal", Element.ALIGN_RIGHT, 16, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Crop Type", Element.ALIGN_CENTER, 16, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Cereals", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pulses", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Oil Seeds", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Fiber Crops", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Pastures", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Horticultures", Element.ALIGN_CENTER, 2, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plantations", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
				int k=1;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProject_area()==null?"":list.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlot_area()==null?"":list.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCereals()==null?"":list.get(i).getCereals().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCereals_prod()==null?"":list.get(i).getCereals_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPulses()==null?"":list.get(i).getPulses().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPulses_prod()==null?"":list.get(i).getPulses_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOilseeds()==null?"":list.get(i).getOilseeds().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOilseeds_prod()==null?"":list.get(i).getOilseeds_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFiber_crops()==null?"":list.get(i).getFiber_crops().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFiber_crops_prod()==null?"":list.get(i).getFiber_crops_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPasture()==null?"":list.get(i).getPasture().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPasture_prod()==null?"":list.get(i).getPasture_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getHorticulture()==null?"":list.get(i).getHorticulture().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getHorticulture_prod()==null?"":list.get(i).getHorticulture_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlantation_crops()==null?"":list.get(i).getPlantation_crops().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlantation_crops_prod()==null?"":list.get(i).getPlantation_crops_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOther_crops()==null?"":list.get(i).getOther_crops().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOther_crops_prod()==null?"":list.get(i).getOther_crops_prod().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						k=k+1;
						project_area=project_area.add(list.get(i).getProject_area()==null?BigDecimal.ZERO:list.get(i).getProject_area());
						plot_area=plot_area.add(list.get(i).getPlot_area()==null?BigDecimal.ZERO:list.get(i).getPlot_area());
						cereals=cereals.add(list.get(i).getCereals()==null?BigDecimal.ZERO:list.get(i).getCereals());
						cereals_prod=cereals_prod.add(list.get(i).getCereals_prod()==null?BigDecimal.ZERO:list.get(i).getCereals_prod());
						pulses=pulses.add(list.get(i).getPulses()==null?BigDecimal.ZERO:list.get(i).getPulses());
						pulses_prod=pulses_prod.add(list.get(i).getPulses_prod()==null?BigDecimal.ZERO:list.get(i).getPulses_prod());
						oilseeds=oilseeds.add(list.get(i).getOilseeds()==null?BigDecimal.ZERO:list.get(i).getOilseeds());
						oilseeds_prod=oilseeds_prod.add(list.get(i).getOilseeds_prod()==null?BigDecimal.ZERO:list.get(i).getOilseeds_prod());
						fiber_crops=fiber_crops.add(list.get(i).getFiber_crops()==null?BigDecimal.ZERO:list.get(i).getFiber_crops());
						fiber_crops_prod=fiber_crops_prod.add(list.get(i).getFiber_crops_prod()==null?BigDecimal.ZERO:list.get(i).getFiber_crops_prod());
						pasture=pasture.add(list.get(i).getPasture()==null?BigDecimal.ZERO:list.get(i).getPasture());
						pasture_prod=pasture_prod.add(list.get(i).getPasture_prod()==null?BigDecimal.ZERO:list.get(i).getPasture_prod());
						horticulture=horticulture.add(list.get(i).getHorticulture()==null?BigDecimal.ZERO:list.get(i).getHorticulture());
						horticulture_prod=horticulture_prod.add(list.get(i).getHorticulture_prod()==null?BigDecimal.ZERO:list.get(i).getHorticulture_prod());
						plantation_crops=plantation_crops.add(list.get(i).getPlantation_crops()==null?BigDecimal.ZERO:list.get(i).getPlantation_crops());
						plantation_crops_prod=plantation_crops_prod.add(list.get(i).getPlantation_crops_prod()==null?BigDecimal.ZERO:list.get(i).getPlantation_crops_prod());
						other_crops=other_crops.add(list.get(i).getOther_crops()==null?BigDecimal.ZERO:list.get(i).getOther_crops());
						other_crops_prod=other_crops_prod.add(list.get(i).getOther_crops_prod()==null?BigDecimal.ZERO:list.get(i).getOther_crops_prod());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cereals), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cereals_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pulses), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pulses_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oilseeds), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oilseeds_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fiber_crops), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fiber_crops_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pasture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pasture_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(horticulture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(horticulture_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_crops), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_crops_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other_crops), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(other_crops_prod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(list.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 20, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-SB4ProjReport.pdf");
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
	@RequestMapping(value = "/downloadDistWiseAreaAchvDetailPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistWiseAreaAchvDetail(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String stcode=request.getParameter("stcode");
		List<BaselineStateWiseAreaDetailBean> achList = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineSurveyAreaReport");
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
			
				paragraph3 = new Paragraph("Report O6- District wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and No of Crops as On Date", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(21);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation= BigDecimal.valueOf(0);
				BigDecimal assured_irrigation= BigDecimal.valueOf(0);
				BigDecimal no_irrigation= BigDecimal.valueOf(0);
				BigDecimal private_owner= BigDecimal.valueOf(0);
				BigDecimal govt_owned= BigDecimal.valueOf(0);
				BigDecimal community_owned= BigDecimal.valueOf(0);
				BigDecimal others_owned= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland=BigDecimal.valueOf(0);
				BigDecimal degraded=BigDecimal.valueOf(0);
				BigDecimal rainfed=BigDecimal.valueOf(0);
				BigDecimal plantation=BigDecimal.valueOf(0);
				BigDecimal no_plantation=BigDecimal.valueOf(0);
				BigDecimal others_classification=BigDecimal.valueOf(0);
				BigDecimal no_crop=BigDecimal.valueOf(0);
				BigDecimal single_crop=BigDecimal.valueOf(0);
				BigDecimal double_crop=BigDecimal.valueOf(0);
				BigDecimal multiple_crop=BigDecimal.valueOf(0);
				
				achList=blsAreawiseDtlsrvc.getDistwiseAreaAchieveDetail(Integer.parseInt(stcode));
				CommonFunctions.insertCellHeader(table, "State : "+stateName, Element.ALIGN_LEFT, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 11, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Irrigation Status(Area)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ownership(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Area)", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No of Crops(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "Protective Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Assured Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Irrigation", Element.ALIGN_CENTER, 1, 2,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Private", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Government", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Community", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Culturable WasteLand", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Forest Land", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Single Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Double Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Multiple Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
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
				
				int k=1;
				if(achList.size()!=0)
					for(int i=0;i<achList.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getPlot_area_ach()==null?"":achList.get(i).getPlot_area_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getProtective_irrigation_ach()==null?"":achList.get(i).getProtective_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getAssured_irrigation_ach()==null?"":achList.get(i).getAssured_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getNo_irrigation_ach()==null?"":achList.get(i).getNo_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getPrivate_owner_ach()==null?"":achList.get(i).getPrivate_owner_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getGovt_owned_ach()==null?"":achList.get(i).getGovt_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getCommunity_owned_ach()==null?"":achList.get(i).getCommunity_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getOthers_owned_ach()==null?"":achList.get(i).getOthers_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getCultivable_wasteland_ach()==null?"":achList.get(i).getCultivable_wasteland_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getDegraded_ach()==null?"":achList.get(i).getDegraded_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getRainfed_ach()==null?"":achList.get(i).getRainfed_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getPlantation_ach()==null?"":achList.get(i).getPlantation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getNo_plantation_ach()==null?"":achList.get(i).getNo_plantation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getOthers_classification_ach()==null?"":achList.get(i).getOthers_classification_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getNo_crop_ach()==null?"":achList.get(i).getNo_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getSingle_crop_ach()==null?"":achList.get(i).getSingle_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getDouble_crop_ach()==null?"":achList.get(i).getDouble_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getMultiple_crop_ach()==null?"":achList.get(i).getMultiple_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						project_area=project_area.add(achList.get(i).getProject_area());
						plot_area=plot_area.add(achList.get(i).getPlot_area_ach()==null?BigDecimal.ZERO:achList.get(i).getPlot_area_ach());
						protective_irrigation=protective_irrigation.add(achList.get(i).getProtective_irrigation_ach()==null?BigDecimal.ZERO:achList.get(i).getProtective_irrigation_ach());
						assured_irrigation=assured_irrigation.add(achList.get(i).getAssured_irrigation_ach()==null?BigDecimal.ZERO:achList.get(i).getAssured_irrigation_ach());
						no_irrigation=no_irrigation.add(achList.get(i).getNo_irrigation_ach()==null?BigDecimal.ZERO:achList.get(i).getNo_irrigation_ach());
						private_owner=private_owner.add(achList.get(i).getPrivate_owner_ach()==null?BigDecimal.ZERO:achList.get(i).getPrivate_owner_ach());
						govt_owned=govt_owned.add(achList.get(i).getGovt_owned_ach()==null?BigDecimal.ZERO:achList.get(i).getGovt_owned_ach());
						community_owned=community_owned.add(achList.get(i).getCommunity_owned_ach()==null?BigDecimal.ZERO:achList.get(i).getCommunity_owned_ach());
						others_owned=others_owned.add(achList.get(i).getOthers_owned_ach()==null?BigDecimal.ZERO:achList.get(i).getOthers_owned_ach());
						cultivable_wasteland=cultivable_wasteland.add(achList.get(i).getCultivable_wasteland_ach()==null?BigDecimal.ZERO:achList.get(i).getCultivable_wasteland_ach());
						degraded=degraded.add(achList.get(i).getDegraded_ach()==null?BigDecimal.ZERO:achList.get(i).getDegraded_ach());
						rainfed=rainfed.add(achList.get(i).getRainfed_ach()==null?BigDecimal.ZERO:achList.get(i).getRainfed_ach());
						plantation=plantation.add(achList.get(i).getPlantation_ach()==null?BigDecimal.ZERO:achList.get(i).getPlantation_ach());
						no_plantation=no_plantation.add(achList.get(i).getNo_plantation_ach()==null?BigDecimal.ZERO:achList.get(i).getNo_plantation_ach());
						others_classification=others_classification.add(achList.get(i).getOthers_classification_ach()==null?BigDecimal.ZERO:achList.get(i).getOthers_classification_ach());
						no_crop=no_crop.add(achList.get(i).getNo_crop_ach()==null?BigDecimal.ZERO:achList.get(i).getNo_crop_ach());
						single_crop=single_crop.add(achList.get(i).getSingle_crop_ach()==null?BigDecimal.ZERO:achList.get(i).getSingle_crop_ach());
						double_crop=double_crop.add(achList.get(i).getDouble_crop_ach()==null?BigDecimal.ZERO:achList.get(i).getDouble_crop_ach());
						multiple_crop=multiple_crop.add(achList.get(i).getMultiple_crop_ach()==null?BigDecimal.ZERO:achList.get(i).getMultiple_crop_ach());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(assured_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(private_owner), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(govt_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(community_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_classification), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(single_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(double_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(multiple_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(achList.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-O6-districtwise.pdf");
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
	
	@RequestMapping(value = "/downloadProjWiseAreaAchvDetailPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjWiseAreaAchvDetailPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
		String dcode=request.getParameter("dcode");
		List<BaselineStateWiseAreaDetailBean> achList = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineSurveyAreaReport");
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
			
				paragraph3 = new Paragraph("Report O6- Project wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and No of Crops as On Date", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(21);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation= BigDecimal.valueOf(0);
				BigDecimal assured_irrigation= BigDecimal.valueOf(0);
				BigDecimal no_irrigation= BigDecimal.valueOf(0);
				BigDecimal private_owner= BigDecimal.valueOf(0);
				BigDecimal govt_owned= BigDecimal.valueOf(0);
				BigDecimal community_owned= BigDecimal.valueOf(0);
				BigDecimal others_owned= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland=BigDecimal.valueOf(0);
				BigDecimal degraded=BigDecimal.valueOf(0);
				BigDecimal rainfed=BigDecimal.valueOf(0);
				BigDecimal plantation=BigDecimal.valueOf(0);
				BigDecimal no_plantation=BigDecimal.valueOf(0);
				BigDecimal others_classification=BigDecimal.valueOf(0);
				BigDecimal no_crop=BigDecimal.valueOf(0);
				BigDecimal single_crop=BigDecimal.valueOf(0);
				BigDecimal double_crop=BigDecimal.valueOf(0);
				BigDecimal multiple_crop=BigDecimal.valueOf(0);
				
				achList=blsAreawiseDtlsrvc.getProjwiseAreaAchieveDetail(Integer.parseInt(dcode));
				CommonFunctions.insertCellHeader(table, "State : "+statename+"    District : "+distName, Element.ALIGN_LEFT, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 11, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Irrigation Status(Area)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ownership(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Area)", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No of Crops(Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "Protective Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Assured Irrigation", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Irrigation", Element.ALIGN_CENTER, 1, 2,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Private", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Government", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Community", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Culturable WasteLand", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed Land", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Forest Land", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Single Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Double Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Multiple Crops", Element.ALIGN_CENTER, 1, 2, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Plantation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
				CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1,  bf8Bold);
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
				
				int k=1;
				if(achList.size()!=0)
					for(int i=0;i<achList.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getPlot_area_ach()==null?"":achList.get(i).getPlot_area_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getProtective_irrigation_ach()==null?"":achList.get(i).getProtective_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getAssured_irrigation_ach()==null?"":achList.get(i).getAssured_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getNo_irrigation_ach()==null?"":achList.get(i).getNo_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getPrivate_owner_ach()==null?"":achList.get(i).getPrivate_owner_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getGovt_owned_ach()==null?"":achList.get(i).getGovt_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getCommunity_owned_ach()==null?"":achList.get(i).getCommunity_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getOthers_owned_ach()==null?"":achList.get(i).getOthers_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getCultivable_wasteland_ach()==null?"":achList.get(i).getCultivable_wasteland_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getDegraded_ach()==null?"":achList.get(i).getDegraded_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getRainfed_ach()==null?"":achList.get(i).getRainfed_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getPlantation_ach()==null?"":achList.get(i).getPlantation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getNo_plantation_ach()==null?"":achList.get(i).getNo_plantation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getOthers_classification_ach()==null?"":achList.get(i).getOthers_classification_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getNo_crop_ach()==null?"":achList.get(i).getNo_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getSingle_crop_ach()==null?"":achList.get(i).getSingle_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getDouble_crop_ach()==null?"":achList.get(i).getDouble_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, achList.get(i).getMultiple_crop_ach()==null?"":achList.get(i).getMultiple_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						project_area=project_area.add(achList.get(i).getProject_area());
						plot_area=plot_area.add(achList.get(i).getPlot_area_ach()==null?BigDecimal.ZERO:achList.get(i).getPlot_area_ach());
						protective_irrigation=protective_irrigation.add(achList.get(i).getProtective_irrigation_ach()==null?BigDecimal.ZERO:achList.get(i).getProtective_irrigation_ach());
						assured_irrigation=assured_irrigation.add(achList.get(i).getAssured_irrigation_ach()==null?BigDecimal.ZERO:achList.get(i).getAssured_irrigation_ach());
						no_irrigation=no_irrigation.add(achList.get(i).getNo_irrigation_ach()==null?BigDecimal.ZERO:achList.get(i).getNo_irrigation_ach());
						private_owner=private_owner.add(achList.get(i).getPrivate_owner_ach()==null?BigDecimal.ZERO:achList.get(i).getPrivate_owner_ach());
						govt_owned=govt_owned.add(achList.get(i).getGovt_owned_ach()==null?BigDecimal.ZERO:achList.get(i).getGovt_owned_ach());
						community_owned=community_owned.add(achList.get(i).getCommunity_owned_ach()==null?BigDecimal.ZERO:achList.get(i).getCommunity_owned_ach());
						others_owned=others_owned.add(achList.get(i).getOthers_owned_ach()==null?BigDecimal.ZERO:achList.get(i).getOthers_owned_ach());
						cultivable_wasteland=cultivable_wasteland.add(achList.get(i).getCultivable_wasteland_ach()==null?BigDecimal.ZERO:achList.get(i).getCultivable_wasteland_ach());
						degraded=degraded.add(achList.get(i).getDegraded_ach()==null?BigDecimal.ZERO:achList.get(i).getDegraded_ach());
						rainfed=rainfed.add(achList.get(i).getRainfed_ach()==null?BigDecimal.ZERO:achList.get(i).getRainfed_ach());
						plantation=plantation.add(achList.get(i).getPlantation_ach()==null?BigDecimal.ZERO:achList.get(i).getPlantation_ach());
						no_plantation=no_plantation.add(achList.get(i).getNo_plantation_ach()==null?BigDecimal.ZERO:achList.get(i).getNo_plantation_ach());
						others_classification=others_classification.add(achList.get(i).getOthers_classification_ach()==null?BigDecimal.ZERO:achList.get(i).getOthers_classification_ach());
						no_crop=no_crop.add(achList.get(i).getNo_crop_ach()==null?BigDecimal.ZERO:achList.get(i).getNo_crop_ach());
						single_crop=single_crop.add(achList.get(i).getSingle_crop_ach()==null?BigDecimal.ZERO:achList.get(i).getSingle_crop_ach());
						double_crop=double_crop.add(achList.get(i).getDouble_crop_ach()==null?BigDecimal.ZERO:achList.get(i).getDouble_crop_ach());
						multiple_crop=multiple_crop.add(achList.get(i).getMultiple_crop_ach()==null?BigDecimal.ZERO:achList.get(i).getMultiple_crop_ach());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(assured_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(private_owner), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(govt_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(community_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_classification), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(single_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(double_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(multiple_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(achList.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-O6-projectwise.pdf");
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
	
	@RequestMapping(value = "/downloadExcelProjectWiseCropAreaSrvy", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjectWiseCropAreaSrvy(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projId=request.getParameter("projId");
		List<BaselineStatewiseCropDetailBean> list = new ArrayList<BaselineStatewiseCropDetailBean>();
		list=blsAreawiseDtlsrvc.getProjWiseblsCrpareaSrvyDetail(Integer.parseInt(projId));
		
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report SB4 - Project wise Distribution of Area According to Crop Type as per Baseline Survey ");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report SB4 - Project wise Distribution of Area According to Crop Type as per Baseline Survey for District "+"'"+distName+"' "+"of State "+" '"+stateName+"' ";
			String areaAmtValDetail = "All area in ha. and All Production in unit Quintal";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 19, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,19); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,6,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,8,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,10,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,12,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,16,17);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,18,19);
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Crop Type");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =5; i<20;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Cereals");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(5);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Pulses");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(7);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Oil seeds");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(9);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Fiber Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(11);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Pastures");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(13);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Horticultures");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(15);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Plantation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(17);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(19);
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<4;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			for(int i =4; i<20;i++) {
				cell = rowhead2.createCell(i);
				cell.setCellValue("Area");
				cell.setCellStyle(style);
				cell = rowhead2.createCell(i+1);
				cell.setCellValue("Production");
				cell.setCellStyle(style);
				i++;
			}
	        
	        int sno = 1;
	        int rowno  =8;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totCerealArea = 0;
	        double totCerealPro = 0;
	        double totPulseArea = 0;
	        double totPulsePro = 0;
	        double totOilArea = 0;
	        double totOilPro = 0;
	        double totFibrArea = 0;
	        double totFibrPro = 0;
	        double totPastureArea = 0;
	        double totPasturePro = 0;
	        double totHortiArea = 0;
	        double totHortiPro = 0;
	        double totPlantnArea = 0;
	        double totPlantnPro = 0;
	        double totOthrArea = 0;
	        double totOthrPro = 0;
	        for(BaselineStatewiseCropDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getProjname());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getCereals()==null?0.0:bean.getCereals().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getCereals_prod()==null?0.0:bean.getCereals_prod().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getPulses()==null?0.0:bean.getPulses().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPulses_prod()==null?0.0:bean.getPulses_prod().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getOilseeds()==null?0.0:bean.getOilseeds().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getOilseeds_prod()==null?0.0:bean.getOilseeds_prod().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getFiber_crops()==null?0.0:bean.getFiber_crops().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getFiber_crops_prod()==null?0.0:bean.getFiber_crops_prod().doubleValue());
	        	
	        	row.createCell(12).setCellValue(bean.getPasture()==null?0.0:bean.getPasture().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getPasture_prod()==null?0.0:bean.getPasture_prod().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getHorticulture()==null?0.0:bean.getHorticulture().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getHorticulture_prod()==null?0.0:bean.getHorticulture_prod().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getPlantation_crops()==null?0.0:bean.getPlantation_crops().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getPlantation_crops_prod()==null?0.0:bean.getPlantation_crops_prod().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getOther_crops()==null?0.0:bean.getOther_crops().doubleValue()); 
	        	row.createCell(19).setCellValue(bean.getOther_crops_prod()==null?0.0:bean.getOther_crops_prod().doubleValue());  
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());
	        	totCerealArea = totCerealArea + (bean.getCereals()==null?0.0:bean.getCereals().doubleValue());
	        	totCerealPro = totCerealPro + (bean.getCereals_prod()==null?0.0:bean.getCereals_prod().doubleValue()); 
	        	totPulseArea = totPulseArea + (bean.getPulses()==null?0.0:bean.getPulses().doubleValue());
	        	totPulsePro = totPulsePro + (bean.getPulses_prod()==null?0.0:bean.getPulses_prod().doubleValue()); 
	        	totOilArea = totOilArea + (bean.getOilseeds()==null?0.0:bean.getOilseeds().doubleValue());
	        	totOilPro = totOilPro + (bean.getOilseeds_prod()==null?0.0:bean.getOilseeds_prod().doubleValue());
	        	totFibrArea = totFibrArea + (bean.getFiber_crops()==null?0.0:bean.getFiber_crops().doubleValue());
	        	totFibrPro = totFibrPro + (bean.getFiber_crops_prod()==null?0.0:bean.getFiber_crops_prod().doubleValue());
	        	totPastureArea = totPastureArea + (bean.getPasture()==null?0.0:bean.getPasture().doubleValue());
	        	totPasturePro = totPasturePro + (bean.getPasture_prod()==null?0.0:bean.getPasture_prod().doubleValue());
	        	totHortiArea = totHortiArea + (bean.getHorticulture()==null?0.0:bean.getHorticulture().doubleValue());
	        	totHortiPro = totHortiPro + (bean.getHorticulture_prod()==null?0.0:bean.getHorticulture_prod().doubleValue());
	        	totPlantnArea = totPlantnArea + (bean.getPlantation_crops()==null?0.0:bean.getPlantation_crops().doubleValue());
	        	totPlantnPro = totPlantnPro + (bean.getPlantation_crops_prod()==null?0.0:bean.getPlantation_crops_prod().doubleValue());
	        	totOthrArea = totOthrArea + (bean.getOther_crops()==null?0.0:bean.getOther_crops().doubleValue());
	        	totOthrPro = totOthrPro + (bean.getOther_crops_prod()==null?0.0:bean.getOther_crops_prod().doubleValue());
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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCerealArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCerealPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totPulseArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totPulsePro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totOilArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOilPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totFibrArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totFibrPro);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totPastureArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totPasturePro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totHortiArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totHortiPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totPlantnArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totPlantnPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totOthrArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totOthrPro);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 19);
	        String fileName = "attachment; filename=Report SB4 - Project.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/blsProjCropWiseAreaSrvy";
		
	}
	
	@RequestMapping(value = "/downloadExcelDistWiseCropAreaSrvy", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistWiseCropAreaSrvy(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String distCode=request.getParameter("distCode");
		List<BaselineStatewiseCropDetailBean> list = new ArrayList<BaselineStatewiseCropDetailBean>();
		list=blsAreawiseDtlsrvc.getDistblsCrpareaSrvyDetail(Integer.parseInt( distCode));
		
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report SB4 - District wise Distribution of Area According to Crop Type as per Baseline Survey ");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report SB4 - District wise Distribution of Area According to Crop Type as per Baseline Survey for State \r\n"+"'"+stateName+"'";
			String areaAmtValDetail = "All area in ha. and All Production in unit Quintal";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 19, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,19); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,6,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,8,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,10,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,12,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,16,17);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,18,19);
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Crop Type");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =5; i<20;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Cereals");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(5);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Pulses");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(7);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Oil seeds");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(9);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Fiber Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(11);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Pastures");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(13);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Horticultures");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(15);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Plantation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(17);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(19);
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<4;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			for(int i =4; i<20;i++) {
				cell = rowhead2.createCell(i);
				cell.setCellValue("Area");
				cell.setCellStyle(style);
				cell = rowhead2.createCell(i+1);
				cell.setCellValue("Production");
				cell.setCellStyle(style);
				i++;
			}
	        
	        int sno = 1;
	        int rowno  =8;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totCerealArea = 0;
	        double totCerealPro = 0;
	        double totPulseArea = 0;
	        double totPulsePro = 0;
	        double totOilArea = 0;
	        double totOilPro = 0;
	        double totFibrArea = 0;
	        double totFibrPro = 0;
	        double totPastureArea = 0;
	        double totPasturePro = 0;
	        double totHortiArea = 0;
	        double totHortiPro = 0;
	        double totPlantnArea = 0;
	        double totPlantnPro = 0;
	        double totOthrArea = 0;
	        double totOthrPro = 0;
	        for(BaselineStatewiseCropDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDist_name());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getCereals()==null?0.0:bean.getCereals().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getCereals_prod()==null?0.0:bean.getCereals_prod().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getPulses()==null?0.0:bean.getPulses().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPulses_prod()==null?0.0:bean.getPulses_prod().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getOilseeds()==null?0.0:bean.getOilseeds().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getOilseeds_prod()==null?0.0:bean.getOilseeds_prod().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getFiber_crops()==null?0.0:bean.getFiber_crops().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getFiber_crops_prod()==null?0.0:bean.getFiber_crops_prod().doubleValue());
	        	
	        	row.createCell(12).setCellValue(bean.getPasture()==null?0.0:bean.getPasture().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getPasture_prod()==null?0.0:bean.getPasture_prod().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getHorticulture()==null?0.0:bean.getHorticulture().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getHorticulture_prod()==null?0.0:bean.getHorticulture_prod().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getPlantation_crops()==null?0.0:bean.getPlantation_crops().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getPlantation_crops_prod()==null?0.0:bean.getPlantation_crops_prod().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getOther_crops()==null?0.0:bean.getOther_crops().doubleValue()); 
	        	row.createCell(19).setCellValue(bean.getOther_crops_prod()==null?0.0:bean.getOther_crops_prod().doubleValue());  
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());
	        	totCerealArea = totCerealArea + (bean.getCereals()==null?0.0:bean.getCereals().doubleValue());
	        	totCerealPro = totCerealPro + (bean.getCereals_prod()==null?0.0:bean.getCereals_prod().doubleValue()); 
	        	totPulseArea = totPulseArea + (bean.getPulses()==null?0.0:bean.getPulses().doubleValue());
	        	totPulsePro = totPulsePro + (bean.getPulses_prod()==null?0.0:bean.getPulses_prod().doubleValue()); 
	        	totOilArea = totOilArea + (bean.getOilseeds()==null?0.0:bean.getOilseeds().doubleValue());
	        	totOilPro = totOilPro + (bean.getOilseeds_prod()==null?0.0:bean.getOilseeds_prod().doubleValue());
	        	totFibrArea = totFibrArea + (bean.getFiber_crops()==null?0.0:bean.getFiber_crops().doubleValue());
	        	totFibrPro = totFibrPro + (bean.getFiber_crops_prod()==null?0.0:bean.getFiber_crops_prod().doubleValue());
	        	totPastureArea = totPastureArea + (bean.getPasture()==null?0.0:bean.getPasture().doubleValue());
	        	totPasturePro = totPasturePro + (bean.getPasture_prod()==null?0.0:bean.getPasture_prod().doubleValue());
	        	totHortiArea = totHortiArea + (bean.getHorticulture()==null?0.0:bean.getHorticulture().doubleValue());
	        	totHortiPro = totHortiPro + (bean.getHorticulture_prod()==null?0.0:bean.getHorticulture_prod().doubleValue());
	        	totPlantnArea = totPlantnArea + (bean.getPlantation_crops()==null?0.0:bean.getPlantation_crops().doubleValue());
	        	totPlantnPro = totPlantnPro + (bean.getPlantation_crops_prod()==null?0.0:bean.getPlantation_crops_prod().doubleValue());
	        	totOthrArea = totOthrArea + (bean.getOther_crops()==null?0.0:bean.getOther_crops().doubleValue());
	        	totOthrPro = totOthrPro + (bean.getOther_crops_prod()==null?0.0:bean.getOther_crops_prod().doubleValue());
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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCerealArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCerealPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totPulseArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totPulsePro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totOilArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOilPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totFibrArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totFibrPro);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totPastureArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totPasturePro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totHortiArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totHortiPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totPlantnArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totPlantnPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totOthrArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totOthrPro);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 19);
	        String fileName = "attachment; filename=Report SB4 - District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/blsDistCropwiseAreaSrvy";
		
	}
	
	
	@RequestMapping(value = "/downloadExcelCropWiseAreaSrvy", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelCropWiseAreaSrvy(HttpServletRequest request, HttpServletResponse response) 
	{
		List<BaselineStatewiseCropDetailBean> list = new ArrayList<>();
			list = blsAreawiseDtlsrvc.getStwiseCropTypeSurveyDetail();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report SB4 - State wise Distribution of Area According to Crop Type as per Baseline Survey");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report SB4 - State wise Distribution of Area According to Crop Type as per Baseline Survey";
			String areaAmtValDetail = "All area in ha. and All Production in unit Quintal";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 19, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,19); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,6,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,8,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,10,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,12,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,16,17);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,18,19);
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Crop Type");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =5; i<20;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Cereals");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(5);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Pulses");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(7);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Oil seeds");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(9);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Fiber Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(11);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Pastures");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(13);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Horticultures");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(15);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Plantation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(17);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(19);
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<4;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			for(int i =4; i<20;i++) {
				cell = rowhead2.createCell(i);
				cell.setCellValue("Area");
				cell.setCellStyle(style);
				cell = rowhead2.createCell(i+1);
				cell.setCellValue("Production");
				cell.setCellStyle(style);
				i++;
			}
	        
	        int sno = 1;
	        int rowno  =8;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totCerealArea = 0;
	        double totCerealPro = 0;
	        double totPulseArea = 0;
	        double totPulsePro = 0;
	        double totOilArea = 0;
	        double totOilPro = 0;
	        double totFibrArea = 0;
	        double totFibrPro = 0;
	        double totPastureArea = 0;
	        double totPasturePro = 0;
	        double totHortiArea = 0;
	        double totHortiPro = 0;
	        double totPlantnArea = 0;
	        double totPlantnPro = 0;
	        double totOthrArea = 0;
	        double totOthrPro = 0;
	        for(BaselineStatewiseCropDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getCereals()==null?0.0:bean.getCereals().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getCereals_prod()==null?0.0:bean.getCereals_prod().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getPulses()==null?0.0:bean.getPulses().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPulses_prod()==null?0.0:bean.getPulses_prod().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getOilseeds()==null?0.0:bean.getOilseeds().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getOilseeds_prod()==null?0.0:bean.getOilseeds_prod().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getFiber_crops()==null?0.0:bean.getFiber_crops().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getFiber_crops_prod()==null?0.0:bean.getFiber_crops_prod().doubleValue());
	        	
	        	row.createCell(12).setCellValue(bean.getPasture()==null?0.0:bean.getPasture().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getPasture_prod()==null?0.0:bean.getPasture_prod().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getHorticulture()==null?0.0:bean.getHorticulture().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getHorticulture_prod()==null?0.0:bean.getHorticulture_prod().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getPlantation_crops()==null?0.0:bean.getPlantation_crops().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getPlantation_crops_prod()==null?0.0:bean.getPlantation_crops_prod().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getOther_crops()==null?0.0:bean.getOther_crops().doubleValue()); 
	        	row.createCell(19).setCellValue(bean.getOther_crops_prod()==null?0.0:bean.getOther_crops_prod().doubleValue());  
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());
	        	totCerealArea = totCerealArea + (bean.getCereals()==null?0.0:bean.getCereals().doubleValue());
	        	totCerealPro = totCerealPro + (bean.getCereals_prod()==null?0.0:bean.getCereals_prod().doubleValue()); 
	        	totPulseArea = totPulseArea + (bean.getPulses()==null?0.0:bean.getPulses().doubleValue());
	        	totPulsePro = totPulsePro + (bean.getPulses_prod()==null?0.0:bean.getPulses_prod().doubleValue()); 
	        	totOilArea = totOilArea + (bean.getOilseeds()==null?0.0:bean.getOilseeds().doubleValue());
	        	totOilPro = totOilPro + (bean.getOilseeds_prod()==null?0.0:bean.getOilseeds_prod().doubleValue());
	        	totFibrArea = totFibrArea + (bean.getFiber_crops()==null?0.0:bean.getFiber_crops().doubleValue());
	        	totFibrPro = totFibrPro + (bean.getFiber_crops_prod()==null?0.0:bean.getFiber_crops_prod().doubleValue());
	        	totPastureArea = totPastureArea + (bean.getPasture()==null?0.0:bean.getPasture().doubleValue());
	        	totPasturePro = totPasturePro + (bean.getPasture_prod()==null?0.0:bean.getPasture_prod().doubleValue());
	        	totHortiArea = totHortiArea + (bean.getHorticulture()==null?0.0:bean.getHorticulture().doubleValue());
	        	totHortiPro = totHortiPro + (bean.getHorticulture_prod()==null?0.0:bean.getHorticulture_prod().doubleValue());
	        	totPlantnArea = totPlantnArea + (bean.getPlantation_crops()==null?0.0:bean.getPlantation_crops().doubleValue());
	        	totPlantnPro = totPlantnPro + (bean.getPlantation_crops_prod()==null?0.0:bean.getPlantation_crops_prod().doubleValue());
	        	totOthrArea = totOthrArea + (bean.getOther_crops()==null?0.0:bean.getOther_crops().doubleValue());
	        	totOthrPro = totOthrPro + (bean.getOther_crops_prod()==null?0.0:bean.getOther_crops_prod().doubleValue());
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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCerealArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCerealPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totPulseArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totPulsePro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totOilArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOilPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totFibrArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totFibrPro);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totPastureArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totPasturePro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totHortiArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totHortiPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totPlantnArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totPlantnPro);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totOthrArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totOthrPro);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 19);
	        String fileName = "attachment; filename=Report SB4 - State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/blsCropwiseAreaSrvy";
		
	}
	
	@RequestMapping(value = "/downloadExcelblsareaoutcomedtlrpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelblsareaoutcomedtlrpt(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		list=blsAreawiseDtlsrvc.getStwiseAreaAchievDetail();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O6- State wise Distribution of Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O6- State wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and No of Crops as On Date";
			String areaAmtValDetail = "All area in ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,7,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,11,16); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,17,20); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,4,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,5,5);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,6,6);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,7,7);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,9,9);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,11,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,12,12);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,13,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
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
	
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Irrigation Status(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =5; i<7;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Ownership(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =8; i<11;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			cell = rowhead.createCell(11);
			cell.setCellValue("Classification of Land(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =12; i<17;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			cell = rowhead.createCell(17);
			cell.setCellValue("No. of Crops(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =18; i<20;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
					
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Protective Irrigation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Assured Irrigation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("No Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Private");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Government");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Community");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(11);
			cell.setCellValue("Cultural Wasteland");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(13);
			cell.setCellValue("Rainfed Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Forest Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);		
			rowhead1.createCell(15).setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(17);
			cell.setCellValue("No Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Single Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(19);
			cell.setCellValue("Double Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(20);
			cell.setCellValue("Multiple Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<14;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(14);
			cell.setCellValue("Plantation");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("No Plantation");
			cell.setCellStyle(style);
			
			for(int i =16; i<21;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totProtIrr = 0;
	        double totAssIrr = 0;
	        double totNoIrr = 0;
	        double totPvtOwn = 0;
	        double totGovOwn = 0;
	        double totComOwn = 0;
	        double totOtherOwn = 0;
	        double totCulWasteland = 0;
	        double totDegLand = 0;
	        double totRainfedLand = 0;
	        double totPlant = 0;
	        double totNoPlant = 0;
	        double totOthers = 0;
	        double totNoCrp = 0;
	        double totSinCrp = 0;
	        double totDouCrp = 0;
	        double totMulCrp = 0;
	        
	        for(BaselineStateWiseAreaDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue()); 
	        	row.createCell(3).setCellValue(bean.getPlot_area_ach()==null?0.0:bean.getPlot_area_ach().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getProtective_irrigation_ach()==null?0.0:bean.getProtective_irrigation_ach().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getAssured_irrigation_ach()==null?0.0:bean.getAssured_irrigation_ach().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getNo_irrigation_ach()==null?0.0:bean.getNo_irrigation_ach().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPrivate_owner_ach()==null?0.0:bean.getPrivate_owner_ach().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getGovt_owned_ach()==null?0.0:bean.getGovt_owned_ach().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getCommunity_owned_ach()==null?0.0:bean.getCommunity_owned_ach().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getOthers_owned_ach()==null?0.0:bean.getOthers_owned_ach().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getCultivable_wasteland_ach()==null?0.0:bean.getCultivable_wasteland_ach().doubleValue());
	        	row.createCell(12).setCellValue(bean.getDegraded_ach()==null?0.0:bean.getDegraded_ach().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getRainfed_ach()==null?0.0:bean.getRainfed_ach().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getPlantation_ach()==null?0.0:bean.getPlantation_ach().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getNo_plantation_ach()==null?0.0:bean.getNo_plantation_ach().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getOthers_classification_ach()==null?0.0:bean.getOthers_classification_ach().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getNo_crop_ach()==null?0.0:bean.getNo_crop_ach().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getSingle_crop_ach()==null?0.0:bean.getSingle_crop_ach().doubleValue());
	        	row.createCell(19).setCellValue(bean.getDouble_crop_ach()==null?0.0:bean.getDouble_crop_ach().doubleValue());
	        	row.createCell(20).setCellValue(bean.getMultiple_crop_ach()==null?0.0:bean.getMultiple_crop_ach().doubleValue());
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area_ach()==null?0.0:bean.getPlot_area_ach().doubleValue());
	        	totProtIrr = totProtIrr + (bean.getProtective_irrigation_ach()==null?0.0:bean.getProtective_irrigation_ach().doubleValue());
	        	totAssIrr = totAssIrr + (bean.getAssured_irrigation_ach()==null?0.0:bean.getAssured_irrigation_ach().doubleValue());
	        	totNoIrr = totNoIrr + (bean.getNo_irrigation_ach()==null?0.0:bean.getNo_irrigation_ach().doubleValue()); 
	        	totPvtOwn = totPvtOwn + (bean.getPrivate_owner_ach()==null?0.0:bean.getPrivate_owner_ach().doubleValue()); 
	        	totGovOwn = totGovOwn + (bean.getGovt_owned_ach()==null?0.0:bean.getGovt_owned_ach().doubleValue());
	        	totComOwn = totComOwn + (bean.getCommunity_owned_ach()==null?0.0:bean.getCommunity_owned_ach().doubleValue());
	        	totOtherOwn = totOtherOwn + (bean.getOthers_owned_ach()==null?0.0:bean.getOthers_owned_ach().doubleValue());
	        	totCulWasteland = totCulWasteland + (bean.getCultivable_wasteland_ach()==null?0.0:bean.getCultivable_wasteland_ach().doubleValue());
	        	totDegLand = totDegLand + (bean.getDegraded_ach()==null?0.0:bean.getDegraded_ach().doubleValue());
	        	totRainfedLand = totRainfedLand + (bean.getRainfed_ach()==null?0.0:bean.getRainfed_ach().doubleValue());
	        	totPlant = totPlant + (bean.getPlantation_ach()==null?0.0:bean.getPlantation_ach().doubleValue());
	        	totNoPlant = totNoPlant + (bean.getNo_plantation_ach()==null?0.0:bean.getNo_plantation_ach().doubleValue());
	        	totOthers = totOthers + (bean.getOthers_classification_ach()==null?0.0:bean.getOthers_classification_ach().doubleValue());
	        	totNoCrp = totNoCrp + (bean.getNo_crop_ach()==null?0.0:bean.getNo_crop_ach().doubleValue());
	        	totSinCrp = totSinCrp + (bean.getSingle_crop_ach()==null?0.0:bean.getSingle_crop_ach().doubleValue());
	        	totDouCrp = totDouCrp + (bean.getDouble_crop_ach()==null?0.0:bean.getDouble_crop_ach().doubleValue());
	        	totMulCrp = totMulCrp + (bean.getMultiple_crop_ach()==null?0.0:bean.getMultiple_crop_ach().doubleValue());

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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totProtIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totAssIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNoIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totPvtOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totGovOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totComOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totOtherOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totCulWasteland);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totDegLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totRainfedLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totPlant);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totNoPlant); 
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOthers);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totNoCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totSinCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totDouCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totMulCrp);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
	        String fileName = "attachment; filename=Report O6- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/blsAreawiseOutcome";
		
	}
	
	@RequestMapping(value = "/downloadExcelDistblscrpwiseareaoutdtlrpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistblscrpwiseareaoutdtlrpt(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcode=request.getParameter("stcode");
		String statename=request.getParameter("statename");
		List<BaselineStatewiseCropDetailBean> list = new ArrayList<BaselineStatewiseCropDetailBean>();
		list = blsAreawiseDtlsrvc.getDistwiseAreaCrpDetail(Integer.parseInt(statename));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O7- District wise Distribution of Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O7-District wise Distribution of Area According to Crop Type as On Date for State  '"+stcode+"' ";
			String areaAmtValDetail = "All area in ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 19, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,19); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,6,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,8,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,10,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,12,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,16,17);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,18,19);
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Crop Type");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =5; i<20;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
					
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Cereals");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(5).setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Pulses");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(7).setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Oil seeds");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(9).setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Fiber Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(11).setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Pastures");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(13).setCellStyle(style);

			cell = rowhead1.createCell(14);
			cell.setCellValue("Horticultures");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(15).setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Plantation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(17).setCellStyle(style);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(19).setCellStyle(style);
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<4;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(4);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(5);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(6);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(7);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(8);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(9);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(10);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(11);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(13);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(14);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(16);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(17);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
					
			cell = rowhead2.createCell(18);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(19);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
	        int sno = 1;
	        int rowno  = 8;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totCerArea = 0;
	        double totCerProd = 0;
	        double totPulArea = 0;
	        double totPulProd = 0;
	        double totOilSeedsArea = 0;
	        double totOilSeedsProd = 0;
	        double totFibArea = 0;
	        double totFibProd = 0;
	        double totPasArea = 0;
	        double totPasProd = 0;
	        double totHortArea = 0;
	        double totHortProd = 0;
	        double totPlantArea = 0;
	        double totPlantProd = 0;
	        double totOthersArea = 0;
	        double totOthersProd = 0;
	        
	        for(BaselineStatewiseCropDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDist_name());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue()); 
	        	row.createCell(3).setCellValue(bean.getPlot_area_ach()==null?0.0:bean.getPlot_area_ach().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getCereals_ach()==null?0.0:bean.getCereals_ach().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getCereals_prod_ach()==null?0.0:bean.getCereals_prod_ach().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getPulses_ach()==null?0.0:bean.getPulses_ach().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPulses_prod_ach()==null?0.0:bean.getPulses_prod_ach().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getOilseeds_ach()==null?0.0:bean.getOilseeds_ach().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getOilseeds_prod_ach()==null?0.0:bean.getOilseeds_prod_ach().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getFiber_crops_ach()==null?0.0:bean.getFiber_crops_ach().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getFiber_crops_prod_ach()==null?0.0:bean.getFiber_crops_prod_ach().doubleValue());
	        	row.createCell(12).setCellValue(bean.getPasture_ach()==null?0.0:bean.getPasture_ach().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getPasture_prod_ach()==null?0.0:bean.getPasture_prod_ach().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getHorticulture_ach()==null?0.0:bean.getHorticulture_ach().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getHorticulture_prod_ach()==null?0.0:bean.getHorticulture_prod_ach().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getPlantation_crops_ach()==null?0.0:bean.getPlantation_crops_ach().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getPlantation_crops_prod_ach()==null?0.0:bean.getPlantation_crops_prod_ach().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getOther_crops_ach()==null?0.0:bean.getOther_crops_ach().doubleValue());
	        	row.createCell(19).setCellValue(bean.getOther_crops_prod_ach()==null?0.0:bean.getOther_crops_prod_ach().doubleValue()); 
	        	
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area_ach()==null?0.0:bean.getPlot_area_ach().doubleValue());
	        	totCerArea = totCerArea + (bean.getCereals_ach()==null?0.0:bean.getCereals_ach().doubleValue());
	        	totCerProd = totCerProd + (bean.getCereals_prod_ach()==null?0.0:bean.getCereals_prod_ach().doubleValue());
	        	totPulArea = totPulArea + (bean.getPulses_ach()==null?0.0:bean.getPulses_ach().doubleValue()); 
	        	totPulProd = totPulProd + (bean.getPulses_prod_ach()==null?0.0:bean.getPulses_prod_ach().doubleValue()); 
	        	totOilSeedsArea = totOilSeedsArea + (bean.getOilseeds_ach()==null?0.0:bean.getOilseeds_ach().doubleValue());
	        	totOilSeedsProd = totOilSeedsProd + (bean.getOilseeds_prod_ach()==null?0.0:bean.getOilseeds_prod_ach().doubleValue());
	        	totFibArea = totFibArea + (bean.getFiber_crops_ach()==null?0.0:bean.getFiber_crops_ach().doubleValue());
	        	totFibProd = totFibProd + (bean.getFiber_crops_prod_ach()==null?0.0:bean.getFiber_crops_prod_ach().doubleValue());
	        	totPasArea = totPasArea + (bean.getPasture_ach()==null?0.0:bean.getPasture_ach().doubleValue());
	        	totPasProd = totPasProd + (bean.getPasture_prod_ach()==null?0.0:bean.getPasture_prod_ach().doubleValue());
	        	totHortArea = totHortArea + (bean.getHorticulture_ach()==null?0.0:bean.getHorticulture_ach().doubleValue());
	        	totHortProd = totHortProd + (bean.getHorticulture_prod_ach()==null?0.0:bean.getHorticulture_prod_ach().doubleValue());
	        	totPlantArea = totPlantArea + (bean.getPlantation_crops_ach()==null?0.0:bean.getPlantation_crops_ach().doubleValue());
	        	totPlantProd = totPlantProd + (bean.getPlantation_crops_prod_ach()==null?0.0:bean.getPlantation_crops_prod_ach().doubleValue());
	        	totOthersArea = totOthersArea + (bean.getOther_crops_ach()==null?0.0:bean.getOther_crops_ach().doubleValue());
	        	totOthersProd = totOthersProd + (bean.getOther_crops_prod_ach()==null?0.0:bean.getOther_crops_prod_ach().doubleValue());

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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCerArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCerProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totPulArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totPulProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totOilSeedsArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOilSeedsProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totFibArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totFibProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totPasArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totPasProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totHortArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totHortProd); 
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totPlantArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totPlantProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totOthersArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totOthersProd);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 19);
	        String fileName = "attachment; filename=Report O7- District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/DistblsCropwiseAreaOutcome";
		
	}
	
	@RequestMapping(value = "/downloadExcelblscrpwiseareaoutdtlrpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelblscrpwiseareaoutdtlrpt(HttpServletRequest request, HttpServletResponse response) 
	{
		String state=request.getParameter("state");
		
		List<BaselineStatewiseCropDetailBean> list = new ArrayList<BaselineStatewiseCropDetailBean>();
		
		list=blsAreawiseDtlsrvc.getStwiseCropTypeOutcomeDetail();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O7- State wise Distribution of Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O7-State wise Distribution of Area According to Crop Type as On Date";
			String areaAmtValDetail = "All area in ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 19, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,19); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,6,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,8,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,10,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,12,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,16,17);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,18,19);
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Crop Type");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =5; i<20;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
					
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Cereals");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(5).setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Pulses");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(7).setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Oil seeds");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(9).setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Fiber Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(11).setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Pastures");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(13).setCellStyle(style);

			cell = rowhead1.createCell(14);
			cell.setCellValue("Horticultures");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(15).setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Plantation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(17).setCellStyle(style);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(19).setCellStyle(style);
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<4;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(4);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(5);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(6);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(7);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(8);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(9);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(10);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(11);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(13);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(14);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(16);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(17);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
					
			cell = rowhead2.createCell(18);
			cell.setCellValue("Area");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(19);
			cell.setCellValue("Production");
			cell.setCellStyle(style);
			
	        int sno = 1;
	        int rowno  = 8;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totCerArea = 0;
	        double totCerProd = 0;
	        double totPulArea = 0;
	        double totPulProd = 0;
	        double totOilSeedsArea = 0;
	        double totOilSeedsProd = 0;
	        double totFibArea = 0;
	        double totFibProd = 0;
	        double totPasArea = 0;
	        double totPasProd = 0;
	        double totHortArea = 0;
	        double totHortProd = 0;
	        double totPlantArea = 0;
	        double totPlantProd = 0;
	        double totOthersArea = 0;
	        double totOthersProd = 0;
	        
	        for(BaselineStatewiseCropDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue()); 
	        	row.createCell(3).setCellValue(bean.getPlot_area_ach()==null?0.0:bean.getPlot_area_ach().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getCereals_ach()==null?0.0:bean.getCereals_ach().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getCereals_prod_ach()==null?0.0:bean.getCereals_prod_ach().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getPulses_ach()==null?0.0:bean.getPulses_ach().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPulses_prod_ach()==null?0.0:bean.getPulses_prod_ach().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getOilseeds_ach()==null?0.0:bean.getOilseeds_ach().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getOilseeds_prod_ach()==null?0.0:bean.getOilseeds_prod_ach().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getFiber_crops_ach()==null?0.0:bean.getFiber_crops_ach().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getFiber_crops_prod_ach()==null?0.0:bean.getFiber_crops_prod_ach().doubleValue());
	        	row.createCell(12).setCellValue(bean.getPasture_ach()==null?0.0:bean.getPasture_ach().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getPasture_prod_ach()==null?0.0:bean.getPasture_prod_ach().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getHorticulture_ach()==null?0.0:bean.getHorticulture_ach().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getHorticulture_prod_ach()==null?0.0:bean.getHorticulture_prod_ach().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getPlantation_crops_ach()==null?0.0:bean.getPlantation_crops_ach().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getPlantation_crops_prod_ach()==null?0.0:bean.getPlantation_crops_prod_ach().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getOther_crops_ach()==null?0.0:bean.getOther_crops_ach().doubleValue());
	        	row.createCell(19).setCellValue(bean.getOther_crops_prod_ach()==null?0.0:bean.getOther_crops_prod_ach().doubleValue()); 
	        	
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area_ach()==null?0.0:bean.getPlot_area_ach().doubleValue());
	        	totCerArea = totCerArea + (bean.getCereals_ach()==null?0.0:bean.getCereals_ach().doubleValue());
	        	totCerProd = totCerProd + (bean.getCereals_prod_ach()==null?0.0:bean.getCereals_prod_ach().doubleValue());
	        	totPulArea = totPulArea + (bean.getPulses_ach()==null?0.0:bean.getPulses_ach().doubleValue()); 
	        	totPulProd = totPulProd + (bean.getPulses_prod_ach()==null?0.0:bean.getPulses_prod_ach().doubleValue()); 
	        	totOilSeedsArea = totOilSeedsArea + (bean.getOilseeds_ach()==null?0.0:bean.getOilseeds_ach().doubleValue());
	        	totOilSeedsProd = totOilSeedsProd + (bean.getOilseeds_prod_ach()==null?0.0:bean.getOilseeds_prod_ach().doubleValue());
	        	totFibArea = totFibArea + (bean.getFiber_crops_ach()==null?0.0:bean.getFiber_crops_ach().doubleValue());
	        	totFibProd = totFibProd + (bean.getFiber_crops_prod_ach()==null?0.0:bean.getFiber_crops_prod_ach().doubleValue());
	        	totPasArea = totPasArea + (bean.getPasture_ach()==null?0.0:bean.getPasture_ach().doubleValue());
	        	totPasProd = totPasProd + (bean.getPasture_prod_ach()==null?0.0:bean.getPasture_prod_ach().doubleValue());
	        	totHortArea = totHortArea + (bean.getHorticulture_ach()==null?0.0:bean.getHorticulture_ach().doubleValue());
	        	totHortProd = totHortProd + (bean.getHorticulture_prod_ach()==null?0.0:bean.getHorticulture_prod_ach().doubleValue());
	        	totPlantArea = totPlantArea + (bean.getPlantation_crops_ach()==null?0.0:bean.getPlantation_crops_ach().doubleValue());
	        	totPlantProd = totPlantProd + (bean.getPlantation_crops_prod_ach()==null?0.0:bean.getPlantation_crops_prod_ach().doubleValue());
	        	totOthersArea = totOthersArea + (bean.getOther_crops_ach()==null?0.0:bean.getOther_crops_ach().doubleValue());
	        	totOthersProd = totOthersProd + (bean.getOther_crops_prod_ach()==null?0.0:bean.getOther_crops_prod_ach().doubleValue());

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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCerArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCerProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totPulArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totPulProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totOilSeedsArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOilSeedsProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totFibArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totFibProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totPasArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totPasProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totHortArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totHortProd); 
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totPlantArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totPlantProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totOthersArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totOthersProd);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 19);
	        String fileName = "attachment; filename=Report O7- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/blsCropwiseAreaOutcome";
		
	}
	
	
	@RequestMapping(value = "/downloadExcelDistrictWiseBaselineArea", method = RequestMethod.POST)
	public ModelAndView downloadExcelDistrictWiseBaselineArea(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String stcode=request.getParameter("stcode");
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
			list = blsAreawiseDtlsrvc.getDistWiseAreaDetail(Integer.parseInt(stcode));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report SB3- District wise Distribution of Area According Baseline Survey");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report SB3-District wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and Number of Crops as per Baseline Survey";
			String areaAmtValDetail = "State: "+stateName+"                                           All area in ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,7,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,11,16); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,17,20); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,4,4);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,5,5);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,6,6);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,7,7);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,9,9);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,11,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,12,12);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,13,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
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
	        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Irrigation Status(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellStyle(style);
			
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Ownership(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(8);
			cell.setCellStyle(style);
			cell = rowhead.createCell(9);
			cell.setCellStyle(style);
			cell = rowhead.createCell(10);
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Classification of Land(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =12; i<17;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(17);
			cell.setCellValue("No. of Crops(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(18);
			cell.setCellStyle(style);
			cell = rowhead.createCell(19);
			cell.setCellStyle(style);
			cell = rowhead.createCell(20);
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Protective Irrigation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Assured Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("No Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Private");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Government");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Community");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(11);
			cell.setCellValue("Culturable Wasteland");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(13);
			cell.setCellValue("Rainfed Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Forest Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(15);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(17);
			cell.setCellValue("No Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Single Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(19);
			cell.setCellValue("Double Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(20);
			cell.setCellValue("Multiple Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<14;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(14);
			cell.setCellValue("Plantation");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("No Plantation");
			cell.setCellStyle(style);
			
			for(int i =16; i<21;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			
			Row rowhead3 = sheet.createRow(8);
			for(int i=0;i<21;i++)
			{
				cell =rowhead3.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        int sno = 1;
	        int rowno  =9;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totProIrr = 0;
	        double totAssured = 0;
	        double totNoIrri = 0;
	        double totprivt = 0;
	        double totGovt = 0;
	        double totCmnty = 0;
	        double totOtherOwnr = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totPlanArea = 0;
	        double totNoPlanArea = 0;
	        double totOtherLnd = 0;
	        double totNoCrop = 0;
	        double totSingle = 0;
	        double totDouble = 0;
	        double totMulti = 0;
	        for(BaselineStateWiseAreaDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getProtective_irrigation()==null?0.0:bean.getProtective_irrigation().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getAssured_irrigation()==null?0.0:bean.getAssured_irrigation().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getNo_irrigation()==null?0.0:bean.getNo_irrigation().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPrivate_owner()==null?0.0:bean.getPrivate_owner().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getGovt_owned()==null?0.0:bean.getGovt_owned().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getCommunity_owned()==null?0.0:bean.getCommunity_owned().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getOthers_owned()==null?0.0:bean.getOthers_owned().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	
	        	row.createCell(12).setCellValue(bean.getDegraded()==null?0.0:bean.getDegraded().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getPlantation()==null?0.0:bean.getPlantation().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getNo_plantation()==null?0.0:bean.getNo_plantation().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getOthers_classification()==null?0.0:bean.getOthers_classification().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getNo_crop()==null?0.0:bean.getNo_crop().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getSingle_crop()==null?0.0:bean.getSingle_crop().doubleValue()); 
	        	row.createCell(19).setCellValue(bean.getDouble_crop()==null?0.0:bean.getDouble_crop().doubleValue());  
	        	row.createCell(20).setCellValue(bean.getMultiple_crop()==null?0.0:bean.getMultiple_crop().doubleValue()); 
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());
	        	totProIrr = totProIrr + (bean.getProtective_irrigation()==null?0.0:bean.getProtective_irrigation().doubleValue());
	        	totAssured = totAssured + (bean.getAssured_irrigation()==null?0.0:bean.getAssured_irrigation().doubleValue());
	        	totNoIrri = totNoIrri + (bean.getNo_irrigation()==null?0.0:bean.getNo_irrigation().doubleValue()); 
	        	totprivt = totprivt + (bean.getPrivate_owner()==null?0.0:bean.getPrivate_owner().doubleValue()); 
	        	totGovt = totGovt + (bean.getGovt_owned()==null?0.0:bean.getGovt_owned().doubleValue());
	        	totCmnty = totCmnty + (bean.getCommunity_owned()==null?0.0:bean.getCommunity_owned().doubleValue());
	        	totOtherOwnr = totOtherOwnr + (bean.getOthers_owned()==null?0.0:bean.getOthers_owned().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded()==null?0.0:bean.getDegraded().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totPlanArea = totPlanArea + (bean.getPlantation()==null?0.0:bean.getPlantation().doubleValue());
	        	totNoPlanArea = totNoPlanArea + (bean.getNo_plantation()==null?0.0:bean.getNo_plantation().doubleValue());
	        	totOtherLnd = totOtherLnd + (bean.getOthers_classification()==null?0.0:bean.getOthers_classification().doubleValue());
	        	totNoCrop = totNoCrop + (bean.getNo_crop()==null?0.0:bean.getNo_crop().doubleValue());
	        	totSingle = totSingle + (bean.getSingle_crop()==null?0.0:bean.getSingle_crop().doubleValue());
	        	totDouble = totDouble + (bean.getDouble_crop()==null?0.0:bean.getDouble_crop().doubleValue());
	        	totMulti = totMulti + (bean.getMultiple_crop()==null?0.0:bean.getMultiple_crop().doubleValue());
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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totProIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totAssured);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNoIrri);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totprivt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totGovt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totCmnty);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totOtherOwnr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totPlanArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totNoPlanArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOtherLnd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totNoCrop);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totSingle);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totDouble);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totMulti);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
	        String fileName = "attachment; filename=Report SB3- district_wise.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}
	
	@RequestMapping(value = "/downloadExcelProjectWiseBaselineArea", method = RequestMethod.POST)
	public ModelAndView downloadExcelProjectWiseBaselineArea(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String distname=request.getParameter("distname");
		String distcode=request.getParameter("distcode");
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
			list = blsAreawiseDtlsrvc.getProjWiseAreaDetail(Integer.parseInt(distcode));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report SB3- Project wise Distribution of Area According Baseline Survey");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report SB3-Project wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and Number of Crops as per Baseline Survey";
			String areaAmtValDetail = "State: "+stateName+"  District : "+distname+"                                          All area in ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,7,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,11,16); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,17,20); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,4,4);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,5,5);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,6,6);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,7,7);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,9,9);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,11,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,12,12);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,13,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
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
	        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Irrigation Status(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellStyle(style);
			
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Ownership(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(8);
			cell.setCellStyle(style);
			cell = rowhead.createCell(9);
			cell.setCellStyle(style);
			cell = rowhead.createCell(10);
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Classification of Land(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =12; i<17;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(17);
			cell.setCellValue("No. of Crops(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(18);
			cell.setCellStyle(style);
			cell = rowhead.createCell(19);
			cell.setCellStyle(style);
			cell = rowhead.createCell(20);
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Protective Irrigation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Assured Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("No Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Private");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Government");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Community");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(11);
			cell.setCellValue("Culturable Wasteland");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(13);
			cell.setCellValue("Rainfed Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Forest Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(15);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(17);
			cell.setCellValue("No Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Single Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(19);
			cell.setCellValue("Double Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(20);
			cell.setCellValue("Multiple Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<14;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(14);
			cell.setCellValue("Plantation");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("No Plantation");
			cell.setCellStyle(style);
			
			for(int i =16; i<21;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			
			Row rowhead3 = sheet.createRow(8);
			for(int i=0;i<21;i++)
			{
				cell =rowhead3.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        int sno = 1;
	        int rowno  =9;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totProIrr = 0;
	        double totAssured = 0;
	        double totNoIrri = 0;
	        double totprivt = 0;
	        double totGovt = 0;
	        double totCmnty = 0;
	        double totOtherOwnr = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totPlanArea = 0;
	        double totNoPlanArea = 0;
	        double totOtherLnd = 0;
	        double totNoCrop = 0;
	        double totSingle = 0;
	        double totDouble = 0;
	        double totMulti = 0;
	        for(BaselineStateWiseAreaDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getProjname());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getProtective_irrigation()==null?0.0:bean.getProtective_irrigation().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getAssured_irrigation()==null?0.0:bean.getAssured_irrigation().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getNo_irrigation()==null?0.0:bean.getNo_irrigation().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPrivate_owner()==null?0.0:bean.getPrivate_owner().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getGovt_owned()==null?0.0:bean.getGovt_owned().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getCommunity_owned()==null?0.0:bean.getCommunity_owned().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getOthers_owned()==null?0.0:bean.getOthers_owned().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	
	        	row.createCell(12).setCellValue(bean.getDegraded()==null?0.0:bean.getDegraded().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getPlantation()==null?0.0:bean.getPlantation().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getNo_plantation()==null?0.0:bean.getNo_plantation().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getOthers_classification()==null?0.0:bean.getOthers_classification().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getNo_crop()==null?0.0:bean.getNo_crop().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getSingle_crop()==null?0.0:bean.getSingle_crop().doubleValue()); 
	        	row.createCell(19).setCellValue(bean.getDouble_crop()==null?0.0:bean.getDouble_crop().doubleValue());  
	        	row.createCell(20).setCellValue(bean.getMultiple_crop()==null?0.0:bean.getMultiple_crop().doubleValue()); 
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());
	        	totProIrr = totProIrr + (bean.getProtective_irrigation()==null?0.0:bean.getProtective_irrigation().doubleValue());
	        	totAssured = totAssured + (bean.getAssured_irrigation()==null?0.0:bean.getAssured_irrigation().doubleValue());
	        	totNoIrri = totNoIrri + (bean.getNo_irrigation()==null?0.0:bean.getNo_irrigation().doubleValue()); 
	        	totprivt = totprivt + (bean.getPrivate_owner()==null?0.0:bean.getPrivate_owner().doubleValue()); 
	        	totGovt = totGovt + (bean.getGovt_owned()==null?0.0:bean.getGovt_owned().doubleValue());
	        	totCmnty = totCmnty + (bean.getCommunity_owned()==null?0.0:bean.getCommunity_owned().doubleValue());
	        	totOtherOwnr = totOtherOwnr + (bean.getOthers_owned()==null?0.0:bean.getOthers_owned().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded()==null?0.0:bean.getDegraded().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totPlanArea = totPlanArea + (bean.getPlantation()==null?0.0:bean.getPlantation().doubleValue());
	        	totNoPlanArea = totNoPlanArea + (bean.getNo_plantation()==null?0.0:bean.getNo_plantation().doubleValue());
	        	totOtherLnd = totOtherLnd + (bean.getOthers_classification()==null?0.0:bean.getOthers_classification().doubleValue());
	        	totNoCrop = totNoCrop + (bean.getNo_crop()==null?0.0:bean.getNo_crop().doubleValue());
	        	totSingle = totSingle + (bean.getSingle_crop()==null?0.0:bean.getSingle_crop().doubleValue());
	        	totDouble = totDouble + (bean.getDouble_crop()==null?0.0:bean.getDouble_crop().doubleValue());
	        	totMulti = totMulti + (bean.getMultiple_crop()==null?0.0:bean.getMultiple_crop().doubleValue());
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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totProIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totAssured);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNoIrri);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totprivt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totGovt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totCmnty);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totOtherOwnr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totPlanArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totNoPlanArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOtherLnd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totNoCrop);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totSingle);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totDouble);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totMulti);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
	        String fileName = "attachment; filename=Report SB3- Project_wise.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}
	

	@RequestMapping(value = "/downloadExcelDistAreaAchvDetail", method = RequestMethod.POST)
	public ModelAndView downloadExcelDistAreaAchvDetail(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String stcode=request.getParameter("stcode");
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
			list = blsAreawiseDtlsrvc.getDistwiseAreaAchieveDetail(Integer.parseInt(stcode));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O6- District wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and No of Crops as On Date");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O6- District wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and No of Crops as On Date";
			String areaAmtValDetail = "State: "+stateName+"                                           All area in ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,7,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,11,16); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,17,20); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,4,4);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,5,5);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,6,6);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,7,7);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,9,9);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,11,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,12,12);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,13,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
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
	        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Irrigation Status(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellStyle(style);
			
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Ownership(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(8);
			cell.setCellStyle(style);
			cell = rowhead.createCell(9);
			cell.setCellStyle(style);
			cell = rowhead.createCell(10);
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Classification of Land(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =12; i<17;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(17);
			cell.setCellValue("No. of Crops(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(18);
			cell.setCellStyle(style);
			cell = rowhead.createCell(19);
			cell.setCellStyle(style);
			cell = rowhead.createCell(20);
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Protective Irrigation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Assured Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("No Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Private");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Government");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Community");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(11);
			cell.setCellValue("Culturable Wasteland");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(13);
			cell.setCellValue("Rainfed Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Forest Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(15);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(17);
			cell.setCellValue("No Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Single Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(19);
			cell.setCellValue("Double Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(20);
			cell.setCellValue("Multiple Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<14;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(14);
			cell.setCellValue("Plantation");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("No Plantation");
			cell.setCellStyle(style);
			
			for(int i =16; i<21;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			
			Row rowhead3 = sheet.createRow(8);
			for(int i=0;i<21;i++)
			{
				cell =rowhead3.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        int sno = 1;
	        int rowno  =9;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totProIrr = 0;
	        double totAssured = 0;
	        double totNoIrri = 0;
	        double totprivt = 0;
	        double totGovt = 0;
	        double totCmnty = 0;
	        double totOtherOwnr = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totPlanArea = 0;
	        double totNoPlanArea = 0;
	        double totOtherLnd = 0;
	        double totNoCrop = 0;
	        double totSingle = 0;
	        double totDouble = 0;
	        double totMulti = 0;
	        for(BaselineStateWiseAreaDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDist_name());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getPlot_area_ach()==null?0.0:bean.getPlot_area_ach().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getProtective_irrigation_ach()==null?0.0:bean.getProtective_irrigation_ach().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getAssured_irrigation_ach()==null?0.0:bean.getAssured_irrigation_ach().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getNo_irrigation_ach()==null?0.0:bean.getNo_irrigation_ach().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPrivate_owner_ach()==null?0.0:bean.getPrivate_owner_ach().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getGovt_owned_ach()==null?0.0:bean.getGovt_owned_ach().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getCommunity_owned_ach()==null?0.0:bean.getCommunity_owned_ach().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getOthers_owned_ach()==null?0.0:bean.getOthers_owned_ach().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getCultivable_wasteland_ach()==null?0.0:bean.getCultivable_wasteland_ach().doubleValue());
	        	
	        	row.createCell(12).setCellValue(bean.getDegraded_ach()==null?0.0:bean.getDegraded_ach().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getRainfed_ach()==null?0.0:bean.getRainfed_ach().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getPlantation_ach()==null?0.0:bean.getPlantation_ach().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getNo_plantation_ach()==null?0.0:bean.getNo_plantation_ach().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getOthers_classification_ach()==null?0.0:bean.getOthers_classification_ach().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getNo_crop_ach()==null?0.0:bean.getNo_crop_ach().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getSingle_crop_ach()==null?0.0:bean.getSingle_crop_ach().doubleValue()); 
	        	row.createCell(19).setCellValue(bean.getDouble_crop_ach()==null?0.0:bean.getDouble_crop_ach().doubleValue());  
	        	row.createCell(20).setCellValue(bean.getMultiple_crop_ach()==null?0.0:bean.getMultiple_crop_ach().doubleValue()); 
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area_ach()==null?0.0:bean.getPlot_area_ach().doubleValue());
	        	totProIrr = totProIrr + (bean.getProtective_irrigation_ach()==null?0.0:bean.getProtective_irrigation_ach().doubleValue());
	        	totAssured = totAssured + (bean.getAssured_irrigation_ach()==null?0.0:bean.getAssured_irrigation_ach().doubleValue());
	        	totNoIrri = totNoIrri + (bean.getNo_irrigation_ach()==null?0.0:bean.getNo_irrigation_ach().doubleValue()); 
	        	totprivt = totprivt + (bean.getPrivate_owner_ach()==null?0.0:bean.getPrivate_owner_ach().doubleValue()); 
	        	totGovt = totGovt + (bean.getGovt_owned_ach()==null?0.0:bean.getGovt_owned_ach().doubleValue());
	        	totCmnty = totCmnty + (bean.getCommunity_owned_ach()==null?0.0:bean.getCommunity_owned_ach().doubleValue());
	        	totOtherOwnr = totOtherOwnr + (bean.getOthers_owned_ach()==null?0.0:bean.getOthers_owned_ach().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland_ach()==null?0.0:bean.getCultivable_wasteland_ach().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded_ach()==null?0.0:bean.getDegraded_ach().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed_ach()==null?0.0:bean.getRainfed_ach().doubleValue());
	        	totPlanArea = totPlanArea + (bean.getPlantation_ach()==null?0.0:bean.getPlantation_ach().doubleValue());
	        	totNoPlanArea = totNoPlanArea + (bean.getNo_plantation_ach()==null?0.0:bean.getNo_plantation_ach().doubleValue());
	        	totOtherLnd = totOtherLnd + (bean.getOthers_classification_ach()==null?0.0:bean.getOthers_classification_ach().doubleValue());
	        	totNoCrop = totNoCrop + (bean.getNo_crop_ach()==null?0.0:bean.getNo_crop_ach().doubleValue());
	        	totSingle = totSingle + (bean.getSingle_crop_ach()==null?0.0:bean.getSingle_crop_ach().doubleValue());
	        	totDouble = totDouble + (bean.getDouble_crop_ach()==null?0.0:bean.getDouble_crop_ach().doubleValue());
	        	totMulti = totMulti + (bean.getMultiple_crop_ach()==null?0.0:bean.getMultiple_crop_ach().doubleValue());
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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totProIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totAssured);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNoIrri);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totprivt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totGovt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totCmnty);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totOtherOwnr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totPlanArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totNoPlanArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOtherLnd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totNoCrop);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totSingle);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totDouble);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totMulti);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
	        String fileName = "attachment; filename=Report O6- district_wise.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}

	@RequestMapping(value = "/downloadExcelProjAreaAchvDetail", method = RequestMethod.POST)
	public ModelAndView downloadExcelProjAreaAchvDetail(HttpServletRequest request, HttpServletResponse response) 
	{
		String distName=request.getParameter("distName");
		String statename=request.getParameter("statename");
		String dcode=request.getParameter("dcode");
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
			list = blsAreawiseDtlsrvc.getProjwiseAreaAchieveDetail(Integer.parseInt(dcode));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O6- Project wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and No of Crops as On Date");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O6- Project wise Distribution of Area According to Irrigation Status, Ownership Status, Land Classification and No of Crops as On Date";
			String areaAmtValDetail = "State: "+statename+ "   District: "+distName+"                                           All area in ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,7,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,11,16); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,17,20); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,4,4);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,5,5);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,6,6);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,7,7);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,9,9);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,11,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,12,12);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,13,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,14,15);
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
	        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Irrigation Status(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(5);
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellStyle(style);
			
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Ownership(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(8);
			cell.setCellStyle(style);
			cell = rowhead.createCell(9);
			cell.setCellStyle(style);
			cell = rowhead.createCell(10);
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Classification of Land(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =12; i<17;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(17);
			cell.setCellValue("No. of Crops(Area)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(18);
			cell.setCellStyle(style);
			cell = rowhead.createCell(19);
			cell.setCellStyle(style);
			cell = rowhead.createCell(20);
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Protective Irrigation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Assured Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("No Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Private");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Government");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Community");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(11);
			cell.setCellValue("Culturable Wasteland");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(13);
			cell.setCellValue("Rainfed Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Forest Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead1.createCell(15);
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(17);
			cell.setCellValue("No Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Single Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(19);
			cell.setCellValue("Double Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(20);
			cell.setCellValue("Multiple Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<14;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(14);
			cell.setCellValue("Plantation");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("No Plantation");
			cell.setCellStyle(style);
			
			for(int i =16; i<21;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			
			Row rowhead3 = sheet.createRow(8);
			for(int i=0;i<21;i++)
			{
				cell =rowhead3.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        int sno = 1;
	        int rowno  =9;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totProIrr = 0;
	        double totAssured = 0;
	        double totNoIrri = 0;
	        double totprivt = 0;
	        double totGovt = 0;
	        double totCmnty = 0;
	        double totOtherOwnr = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totPlanArea = 0;
	        double totNoPlanArea = 0;
	        double totOtherLnd = 0;
	        double totNoCrop = 0;
	        double totSingle = 0;
	        double totDouble = 0;
	        double totMulti = 0;
	        for(BaselineStateWiseAreaDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getProj_name());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getPlot_area_ach()==null?0.0:bean.getPlot_area_ach().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getProtective_irrigation_ach()==null?0.0:bean.getProtective_irrigation_ach().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getAssured_irrigation_ach()==null?0.0:bean.getAssured_irrigation_ach().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getNo_irrigation_ach()==null?0.0:bean.getNo_irrigation_ach().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getPrivate_owner_ach()==null?0.0:bean.getPrivate_owner_ach().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getGovt_owned_ach()==null?0.0:bean.getGovt_owned_ach().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getCommunity_owned_ach()==null?0.0:bean.getCommunity_owned_ach().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getOthers_owned_ach()==null?0.0:bean.getOthers_owned_ach().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getCultivable_wasteland_ach()==null?0.0:bean.getCultivable_wasteland_ach().doubleValue());
	        	
	        	row.createCell(12).setCellValue(bean.getDegraded_ach()==null?0.0:bean.getDegraded_ach().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getRainfed_ach()==null?0.0:bean.getRainfed_ach().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getPlantation_ach()==null?0.0:bean.getPlantation_ach().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getNo_plantation_ach()==null?0.0:bean.getNo_plantation_ach().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getOthers_classification_ach()==null?0.0:bean.getOthers_classification_ach().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getNo_crop_ach()==null?0.0:bean.getNo_crop_ach().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getSingle_crop_ach()==null?0.0:bean.getSingle_crop_ach().doubleValue()); 
	        	row.createCell(19).setCellValue(bean.getDouble_crop_ach()==null?0.0:bean.getDouble_crop_ach().doubleValue());  
	        	row.createCell(20).setCellValue(bean.getMultiple_crop_ach()==null?0.0:bean.getMultiple_crop_ach().doubleValue()); 
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area_ach()==null?0.0:bean.getPlot_area_ach().doubleValue());
	        	totProIrr = totProIrr + (bean.getProtective_irrigation_ach()==null?0.0:bean.getProtective_irrigation_ach().doubleValue());
	        	totAssured = totAssured + (bean.getAssured_irrigation_ach()==null?0.0:bean.getAssured_irrigation_ach().doubleValue());
	        	totNoIrri = totNoIrri + (bean.getNo_irrigation_ach()==null?0.0:bean.getNo_irrigation_ach().doubleValue()); 
	        	totprivt = totprivt + (bean.getPrivate_owner_ach()==null?0.0:bean.getPrivate_owner_ach().doubleValue()); 
	        	totGovt = totGovt + (bean.getGovt_owned_ach()==null?0.0:bean.getGovt_owned_ach().doubleValue());
	        	totCmnty = totCmnty + (bean.getCommunity_owned_ach()==null?0.0:bean.getCommunity_owned_ach().doubleValue());
	        	totOtherOwnr = totOtherOwnr + (bean.getOthers_owned_ach()==null?0.0:bean.getOthers_owned_ach().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland_ach()==null?0.0:bean.getCultivable_wasteland_ach().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded_ach()==null?0.0:bean.getDegraded_ach().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed_ach()==null?0.0:bean.getRainfed_ach().doubleValue());
	        	totPlanArea = totPlanArea + (bean.getPlantation_ach()==null?0.0:bean.getPlantation_ach().doubleValue());
	        	totNoPlanArea = totNoPlanArea + (bean.getNo_plantation_ach()==null?0.0:bean.getNo_plantation_ach().doubleValue());
	        	totOtherLnd = totOtherLnd + (bean.getOthers_classification_ach()==null?0.0:bean.getOthers_classification_ach().doubleValue());
	        	totNoCrop = totNoCrop + (bean.getNo_crop_ach()==null?0.0:bean.getNo_crop_ach().doubleValue());
	        	totSingle = totSingle + (bean.getSingle_crop_ach()==null?0.0:bean.getSingle_crop_ach().doubleValue());
	        	totDouble = totDouble + (bean.getDouble_crop_ach()==null?0.0:bean.getDouble_crop_ach().doubleValue());
	        	totMulti = totMulti + (bean.getMultiple_crop_ach()==null?0.0:bean.getMultiple_crop_ach().doubleValue());
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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totProIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totAssured);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNoIrri);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totprivt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totGovt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totCmnty);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totOtherOwnr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totPlanArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totNoPlanArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOtherLnd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totNoCrop);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totSingle);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totDouble);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totMulti);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
	        String fileName = "attachment; filename=Report O6- projectwise.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}
}


	

	
	
	
	

