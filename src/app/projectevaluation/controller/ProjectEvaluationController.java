package app.projectevaluation.controller;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.sl.usermodel.VerticalAlignment;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
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

import app.bean.AssetIdBean;
import app.bean.Login;
import app.bean.ProfileBean;
import app.common.CommonFunctions;
import app.projectevaluation.service.ProjectEvaluationService;
import app.projectevaluation.bean.CroppedDetailsReportBean;
import app.projectevaluation.bean.ProductionDetailsBean;
import app.projectevaluation.bean.ProjectEvaluationBean;
import app.service.DistrictMasterService;
import app.service.PhysicalActionPlanService;
import app.service.ProfileService;
import app.watershedyatra.service.WatershedYatraService;
import app.projectevaluation.model.WdcpmksyCroppedDetails1;
import app.projectevaluation.model.WdcpmksyCroppedDetails2;
import app.projectevaluation.model.WdcpmksyCroppedDetails3;
import app.projectevaluation.model.WdcpmksyEquityAspect;
import app.projectevaluation.model.WdcpmksyProductionDetails;


@Controller("projectEvaluationController")
public class ProjectEvaluationController {

	HttpSession session;
	
	@Autowired
	DistrictMasterService districtMasterService;

	@Autowired
	PhysicalActionPlanService physicalActionPlanService;
	
	@Autowired
	ProjectEvaluationService PEService;
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	WatershedYatraService ser;
	
	private Map<Integer, String> projectList;
	private Map<Integer, String> monthList;

	@RequestMapping(value="getProfileStart", method=RequestMethod.GET)
	public ModelAndView getProfileStart(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String finyear = request.getParameter("finyear");
		String month = request.getParameter("month");
		String data[] = null;
		
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
			mav = new ModelAndView("projectEvaluation/profilestart");
			mav.addObject("districtList", ser.getDistrictList(stcode));
			mav.addObject("finYear", PEService.getCurrentFinYear());
			monthList = PEService.getmonthforproject();
			mav.addObject("monthList", monthList);
			
			if( district!=null && !district.equalsIgnoreCase("")) {
				projectList = physicalActionPlanService.getprojectfordistrict(Integer.parseInt(district), stcode);
				mav.addObject("projectList", projectList);}
			
			
			
			if( month!=null && !month.equalsIgnoreCase("")) {
				List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
				list = PEService.getprojectstatus(Integer.parseInt(project), Integer.parseInt(month));
				for(ProjectEvaluationBean bean : list) {
				data = new String[3];
				data[0] = bean.getProjname(); 
				data[1] = bean.getMonthname();
				data[2] = bean.getProjcount().toString();
				  if(data[2].equals("1"))
				  {
					  mav.addObject("error", "Project Evaluation of the" +" "+data[0]+" "+"for the month" +" "+data[1]+" "+"have already drafted and not Completed!");
				 }
				 
				}
				mav.addObject("monthList", monthList);
				
			}
			
			    mav.addObject("project", project);
			    mav.addObject("district", district);
			    mav.addObject("finyear", finyear);
			    mav.addObject("month", month);
		}  
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value="checkProjIdExists", method=RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> checkProjIdExists(@RequestParam("projectId") int projId) {
		Map<String, Object> response = new HashMap<>();
		ProjectEvaluationBean projData = PEService.getProjectDetails(projId);
		
		if (projData != null) {
	        response.put("exists", true);
	        response.put("projId", projData.getProject());
	        response.put("projName", projData.getProjname());
	        response.put("distCode", projData.getDistrict());
	        response.put("distName", projData.getDistname());
	        response.put("finYearCode", projData.getFin_cd());
	        response.put("finYearDesc", projData.getFin_yr());
	        response.put("monthId", projData.getMonthid());
	        response.put("monthName", projData.getMonthname());
	        response.put("status", projData.getStatus()); // Assuming status is present
	    } else {
	        response.put("exists", false);
	        response.put("message", "Project not found.");
	    }

	    return response;
    }

	
	@RequestMapping(value="getProjectProfile", method=RequestMethod.GET)
	public ModelAndView ProjectProfile(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String data[] = null;
		String stName = (String) session.getAttribute("stName");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String finName= request.getParameter("finName");
		String monthName= request.getParameter("monthName");
		String finid = request.getParameter("finyear");
		String monthid = request.getParameter("month");
		String pagency = request.getParameter("pagency");

		/*
		 * List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		 * list = PEService.monthYear(); for(ProjectEvaluationBean bean : list) { data =
		 * new String[4]; data[0] = bean.getMonthid().toString(); data[1] =
		 * bean.getMonthname(); data[2] = bean.getFin_cd().toString(); data[3] =
		 * bean.getFin_yr(); }
		 */
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			monthList = PEService.getmonthforproject();
            String projProfilestatus = PEService.checkProjectProfileStatus(project);
            String pAgency = PEService.getpAgency(project);
			if(projProfilestatus != null) {
				if ("1".equals(projProfilestatus)) {
		            request.setAttribute("projectProfileConfirmed", "true");
		        }
				if ("2".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		        }
				if ("3".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		        }
				if ("4".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		        }
				if ("5".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				if ("6".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("13".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
		    mav = new ModelAndView("projectEvaluation/projectProfileMain");
		    mav.addObject("monthList", monthList);
		    mav.addObject("stName",stName);
            mav.addObject("distName",distName);
			mav.addObject("projName",projName);
			mav.addObject("dcode",district);
			mav.addObject("projid",project);
			mav.addObject("monthid", monthid);
			mav.addObject("monthname", monthName);
			mav.addObject("fincd", finid);
			mav.addObject("finyr", finName);
			if(pAgency !=null)
			{
				mav.addObject("pagency", pAgency);
			}
			else {
			mav.addObject("pagency", pagency);
			}
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	
	@RequestMapping(value="/indicatorsEvaluation", method=RequestMethod.GET)
	public ModelAndView saveIndicatorEvaluation(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String stName = (String) session.getAttribute("stName");
		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		Integer pcode = Integer.parseInt(request.getParameter("pcode"));
		String dname = request.getParameter("dname");
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		String mname = request.getParameter("mname");
		String fname = request.getParameter("fname");
		
		Integer profile_id=0;
//		 String admiMechanism=null;
//		 String admiMechanismRemark=null;
		 Character dprSlna=null;
		 String dprSlnaRemark=null;
		 Character allManpower=null;
		 String allManpowerRemark=null;
		 Integer wcdc=null;
		 String wcdcRemark=null;
		 Integer pia=null;
		 String piaRemark=null;
		 Integer wc=null;
		 String wcRemark=null;
		 
		Integer projProfId = PEService.getProjectProfileId(pcode, fcode, mcode);
		ModelAndView mav = new ModelAndView();
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			 profile_id=PEService.getProjectProfileId( pcode, fcode, mcode);
			 list=PEService.getIndicatorEvaluation(profile_id);
			 for(ProjectEvaluationBean bean : list) {
					
				 dprSlna=bean.getDpr_slna();
				 dprSlnaRemark=bean.getDpr_slna_remark().toString();
				 allManpower=bean.getAll_manpower();
				 allManpowerRemark=bean.getAll_manpower_remark().toString();
				 wcdc=bean.getWcdc();
				 wcdcRemark=bean.getWcdc_remark().toString();
				 pia=bean.getPia();
				 piaRemark=bean.getPia_remark().toString();
				 wc=bean.getWc();
				 wcRemark=bean.getWc_remark().toString();
			}
			mav = new ModelAndView("projectEvaluation/indicatorEvltion");
			mav.addObject("stName",stName);
			mav.addObject("dcode",dcode);
			mav.addObject("pcode",pcode);
			mav.addObject("distName",dname);
			mav.addObject("projName",pname);
			mav.addObject("mcode", mcode);
			mav.addObject("month", mname);
			mav.addObject("fcode", fcode);
			mav.addObject("finyear", fname);
			mav.addObject("projProfId",projProfId);
			
			mav.addObject("dpr",dprSlna);
			mav.addObject("dprremark",dprSlnaRemark);
			mav.addObject("mp", allManpower);
			mav.addObject("mpremark", allManpowerRemark);
			mav.addObject("wdc", wcdc);
			mav.addObject("pi", pia);
			mav.addObject("wc",wc);
			
			mav.addObject("wdcd", wcdcRemark);
			mav.addObject("pid", piaRemark);
			mav.addObject("wcd",wcRemark);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value = "/saveIndicatorEvaluationDetails", method = RequestMethod.POST)
	public ModelAndView saveIndicatorEvaluationDetails(Model model, HttpServletRequest request) {
	    HttpSession session = null;
	    ModelAndView mav = new ModelAndView();
	    String result=null;
	     session = request.getSession(true);
	     Integer wcdc =0;
	     Integer pia =0;
     	Integer wc =0;

	     	//Character area = request.getParameter("area").charAt(0);
//	        String admiMechanism= request.getParameter("am");
//	        String admiMechanismRemark=request.getParameter("amd");
//	        Character dprSlna =request.getParameter("dpr");
	        String dpr = request.getParameter("dpr");
	        Character dprSlna = (dpr != null && !dpr.isEmpty()) ? dpr.charAt(0) : null;
	        String dprSlnaRemark= request.getParameter("dprremark");
//	        Character allManpower=request.getParameter("mp");
	        String mp = request.getParameter("mp");
	        Character allManpower = (mp != null && !mp.isEmpty()) ? mp.charAt(0) : null;
	        String allManpowerRemark=request.getParameter("mpremark");
//	        if(!allManpower.equals('F')) {
	        String wcdcParam = request.getParameter("wdc");
	        String piaParam = request.getParameter("pi");
	        String wcParam = request.getParameter("wc");

	        if (wcdcParam != null && !wcdcParam.trim().isEmpty()) {
	            wcdc = Integer.parseInt(wcdcParam);
	        }

	        if (piaParam != null && !piaParam.trim().isEmpty()) {
	            pia = Integer.parseInt(piaParam);
	        }

	        if (wcParam != null && !wcParam.trim().isEmpty()) {
	            wc = Integer.parseInt(wcParam);
	        }
	        
//	        }
	       
	        String wcdcRemark=request.getParameter("wdcd");
	        String piaRemark=request.getParameter("pid");
	        String wcRemark=request.getParameter("wcd");
	        Integer fromno = Integer.parseInt(request.getParameter("fromno"));
	        Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	        String distName = request.getParameter("distName");
	        Integer projid = Integer.parseInt(request.getParameter("projid"));
	        String project = request.getParameter("projid");
	        String projName = request.getParameter("projName");
	        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	        String mname = request.getParameter("mname");
	        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	        String fname = request.getParameter("fname");
	        String pagency = PEService.getpAgency(project);
	     try {
	    if (session != null && session.getAttribute("loginID") != null) {
	       
	    	mav.setViewName("projectEvaluation/projectProfileMain");
	        	Integer profile_id=0;
	        	monthList = PEService.getmonthforproject();
		        profile_id=PEService.getProjectProfileId( projid, fcode, mcode);
		        String projProfilestatus = PEService.checkProjectProfileStatus(project);
				if(projProfilestatus != null) {
					if ("1".equals(projProfilestatus)) {
			            request.setAttribute("projectProfileConfirmed", "true");
			        }
					if ("2".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			        }
					if ("3".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			        }
					if ("4".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			        }
					if ("5".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			        }
					if ("6".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			        }
					if ("7".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			        }
					if ("8".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			        }
					if ("9".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			        }
					if ("10".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			        }
					if ("11".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
			        }
					if ("12".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
			            request.setAttribute("qualityShapeFileConfirmed", "true");
			        }
					if ("13".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
			            request.setAttribute("qualityShapeFileConfirmed", "true");
			            request.setAttribute("geoTagDetailsConfirmed", "true");
			        }
					}
	            result = PEService.saveIndicatorEvaluationDetails(profile_id, fromno,
	                wcdc, wc, pia, dprSlna,
	                dprSlnaRemark, allManpower, allManpowerRemark, wcdcRemark,
	                piaRemark, wcRemark, session);
	            
	            		
	            		if ("success".equals(result)) {
	            			
	        	            request.setAttribute("evaluationDetailConfirmed", "true");
	        	        }

	        	        mav.addObject("distName", distName);
	        	        mav.addObject("monthList", monthList);
	        	        mav.addObject("projName", projName);
	        	        mav.addObject("monthname", mname);
	        	        mav.addObject("fincd", fcode);
	        	        mav.addObject("dcode", dcode);
	        	        mav.addObject("projid", projid);
	        	        mav.addObject("monthid", mcode);
	        	        mav.addObject("finyr", fname);
	        	        mav.addObject("pagency", pagency);
	        	        
	        	        request.setAttribute("projectProfileConfirmed", "true");
	        	    } 
	        	    else {
	        	        if (session != null) {
	        	            session.invalidate();
	        	        }
	        	        mav.setViewName("login");
	        	        mav.addObject("login", new Login());
	        	    }
	     }
	     catch (Exception e) {
			e.printStackTrace();
		}
	        	    return mav;
	    
	}
	
	@RequestMapping(value = "fundUtilization", method = RequestMethod.GET)
	public ModelAndView fundUtilization(HttpServletRequest request, HttpServletResponse response) {
		
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		
		String stName = (String) session.getAttribute("stName");
		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		Integer pcode = Integer.parseInt(request.getParameter("pcode"));
		String dname = request.getParameter("dname");
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		String mname = request.getParameter("mname");
		String fname = request.getParameter("fname");
		Integer projectProfileId = 0;
		
		String centralShare = null;
		String rmkCentralShare = null;
		String stateShare = null;
		String rmkStateShare = null;
		String totalFund = null;
		String rmkTotalFund = null;
		String conPlannedFund = null;
		String rmkConPlannedFund = null;
		String exCon = null;
		String rmkExCon = null;
		String wdf = null;
		String rmkWdf = null;
		
		try {
			
			List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
			List<ProjectEvaluationBean> fundData = new ArrayList<ProjectEvaluationBean>();
			
			if(session != null && session.getAttribute("loginID") != null) 
			{	
				
				mav = new ModelAndView("projectEvaluation/fundUtilization");
				
				projectProfileId=PEService.getProjectProfileId( pcode, fcode, mcode);
				
				list=PEService.getFundUtilization(projectProfileId);
				
				
				if (list == null || list.isEmpty()) 
				{
					fundData=PEService.getFundDetails(pcode);
					
					for (ProjectEvaluationBean bean : fundData) 
					{
						centralShare = bean.getCentral().toString();
						stateShare = bean.getState().toString();
						totalFund =  (bean.getCentral().add(bean.getState())).toString();
			        }
			    } else {
			    	for(ProjectEvaluationBean bean : list) 
			    	{
			    		centralShare = bean.getCentral_share().toString();
			    		rmkCentralShare = bean.getCentral_share_remark();
			    		stateShare = bean.getState_share().toString();
			    		rmkStateShare = bean.getState_share_remark();
			    		totalFund = bean.getTotal_fund().toString();
			    		rmkTotalFund = bean.getTotal_fund_remark();
			    		conPlannedFund = bean.getTotal_fund_planned().toString();
			    		rmkConPlannedFund = bean.getTotal_fund_planned_remark();
			    		exCon = bean.getTotal_expenditure().toString();
			    		rmkExCon = bean.getTotal_expenditure_remark();
			    		wdf = bean.getTotal_wdf().toString();
			    		rmkWdf = bean.getTotal_wdf_remark();
			    	}
			    }
				
				mav.addObject("stName",stName); 
				mav.addObject("dcode",dcode);
				mav.addObject("pcode",pcode);
				mav.addObject("dname",dname);
				mav.addObject("pname",pname);
				mav.addObject("mcode", mcode);
				mav.addObject("mname", mname);
				mav.addObject("fcode", fcode);
				mav.addObject("fname", fname);
				
				mav.addObject("centralShare",centralShare);
				mav.addObject("rmkCentralShare",rmkCentralShare);
				mav.addObject("stateShare",stateShare);
				mav.addObject("rmkStateShare", rmkStateShare);
				mav.addObject("totalFund", totalFund);
				mav.addObject("rmkTotalFund",rmkTotalFund);
				mav.addObject("conPlannedFund",conPlannedFund);
				mav.addObject("rmkConPlannedFund",rmkConPlannedFund);
				mav.addObject("exCon", exCon);
				mav.addObject("rmkExCon", rmkExCon);
				mav.addObject("wdf", wdf);
				mav.addObject("rmkWdf", rmkWdf);
				
			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		
		}
	 catch (Exception e) {
		e.printStackTrace();
	 	}
		
		return mav;
	}
		
	@RequestMapping(value = "saveFundUtilization", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveFundUtilization(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		ModelAndView mav = new ModelAndView();
		try {
			
		if (session != null && session.getAttribute("loginID") != null) {
			monthList = PEService.getmonthforproject();
			Integer projectProfileId = 0;
			Integer fromno = Integer.parseInt(request.getParameter("fromno"));
			Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	        String distName = request.getParameter("distName");
	        Integer projid = Integer.parseInt(request.getParameter("projid"));
	        String project = request.getParameter("projid");
	        String projName = request.getParameter("projName");
	        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	        String mname = request.getParameter("mname");
	        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	        String fname = request.getParameter("fname");
	        String pagency = PEService.getpAgency(project);
	        
	        projectProfileId=PEService.getProjectProfileId( projid, fcode, mcode);
			
	        BigDecimal centralShare = new BigDecimal(request.getParameter("centralShare"));
			String rmkCentralShare = request.getParameter("rmkCentralShare");
			BigDecimal stateShare = new BigDecimal(request.getParameter("stateShare"));
			String rmkStateShare = request.getParameter("rmkStateShare");
			BigDecimal totalFund = new BigDecimal(request.getParameter("totalFund"));
			String rmkTotalFund = request.getParameter("rmkTotalFund");
			BigDecimal conPlannedFund = new BigDecimal(request.getParameter("conPlannedFund"));
			String rmkConPlannedFund = request.getParameter("rmkConPlannedFund");
			BigDecimal exCon = new BigDecimal(request.getParameter("exCon"));
			String rmkExCon = request.getParameter("rmkExCon");
			BigDecimal wdf = new BigDecimal(request.getParameter("wdf"));
			String rmkWdf = request.getParameter("rmkWdf");
			
						
			mav.setViewName("projectEvaluation/projectProfileMain");
			String projProfilestatus = PEService.checkProjectProfileStatus(project);
			if(projProfilestatus != null) {
				if ("1".equals(projProfilestatus)) {
		            request.setAttribute("projectProfileConfirmed", "true");
		        }
				if ("2".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		        }
				if ("3".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		        }
				if ("4".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		        }
				if ("5".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				if ("6".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("13".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
			String result = PEService.saveFundUtilization(projectProfileId, centralShare, rmkCentralShare, stateShare, rmkStateShare, totalFund, rmkTotalFund, 
					conPlannedFund, rmkConPlannedFund, exCon, rmkExCon, wdf, rmkWdf, session, fromno);
			
			if ("success".equals(result)) {
	            request.setAttribute("fundUtilizationConfirmed", "true");
	        }
			
			request.setAttribute("projectProfileConfirmed", "true");
			request.setAttribute("evaluationDetailConfirmed", "true");

	        mav.addObject("distName", distName);
	        mav.addObject("monthList", monthList);
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        mav.addObject("pagency", pagency);
    
	    } else {
	        if (session != null) {
	            session.invalidate();
	        }
	        mav.setViewName("login");
	        mav.addObject("login", new Login());
	    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	    return mav;
	}
	
	@RequestMapping(value = "equityAspect", method = RequestMethod.GET)
	public ModelAndView equityAspect(HttpServletRequest request, HttpServletResponse response) {
		
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		
		String stName = (String) session.getAttribute("stName");
		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		Integer pcode = Integer.parseInt(request.getParameter("pcode"));
		String dname = request.getParameter("dname");
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		String mname = request.getParameter("mname");
		String fname = request.getParameter("fname");
		Integer projectProfileId = 0;
		
		
		try {
			
			if(session != null && session.getAttribute("loginID") != null) 
			{				
				projectProfileId = PEService.getProjectProfileId(pcode, fcode, mcode);
				List<WdcpmksyEquityAspect> equityAspectList = PEService.getEquityAspect(projectProfileId);
		        
		        if(!equityAspectList.isEmpty()) {
		        	WdcpmksyEquityAspect es = equityAspectList.get(0);
		            
		        mav = new ModelAndView("projectEvaluation/equityAspect");
				 
				mav.addObject("pWatershedCom", es.getWaterCommittee());
				mav.addObject("cWatershedCom", es.getControlWaterCommittee());
				mav.addObject("rmkWatershedCom", es.getWaterCommitteeRemark());
				mav.addObject("pFpoShgVli", es.getFpoShgVli());
				mav.addObject("cFpoShgVli", es.getControlFpoShgVli());
				mav.addObject("rmkFpoShgVli", es.getFpoShgVliRemark());
				mav.addObject("pLivelihood", es.getLivelihoodOption());
				mav.addObject("cLivelihood", es.getControlLivelihoodOption());				
				mav.addObject("rmkLivelihood", es.getLivelihoodOptionRemark());
		        } else {
		            mav = new ModelAndView("projectEvaluation/equityAspect");
		            mav.addObject("error", "No data found");
		        }
		        
		        mav.addObject("stName",stName);
		        mav.addObject("dcode",dcode);
				mav.addObject("pcode",pcode);
				mav.addObject("dname",dname);
				mav.addObject("pname",pname);
				mav.addObject("mcode", mcode);
				mav.addObject("mname", mname);
				mav.addObject("fcode", fcode);
				mav.addObject("fname", fname);
		        
			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
			
		}
	 catch (Exception e) {
		e.printStackTrace();
	 	}
		
		return mav; 
	}
	
	@RequestMapping(value = "saveEquityAspect", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveEquityAspect(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		ModelAndView mav = new ModelAndView();
		String result= null;
		try {
			
		if (session != null && session.getAttribute("loginID") != null) {
			monthList = PEService.getmonthforproject();
			Integer projectProfileId = 0;
			Integer fromno = Integer.parseInt(request.getParameter("fromno"));
			Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	        String distName = request.getParameter("distName");
	        Integer projid = Integer.parseInt(request.getParameter("projid"));
	        String project = request.getParameter("projid");
	        String projName = request.getParameter("projName");
	        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	        String mname = request.getParameter("mname");
	        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	        String fname = request.getParameter("fname");
	        String pagency = PEService.getpAgency(project);
	        
	        projectProfileId=PEService.getProjectProfileId( projid, fcode, mcode);
			
	        
	        Boolean pWatershedCom = Boolean.parseBoolean(request.getParameter("pWatershedCom"));
	        Boolean cWatershedCom =null; //Boolean.parseBoolean(request.getParameter("cWatershedCom"));
			String rmkWatershedCom = request.getParameter("rmkWatershedCom");
			Boolean pFpoShgVli = Boolean.parseBoolean(request.getParameter("pFpoShgVli"));
			Boolean cFpoShgVli = Boolean.parseBoolean(request.getParameter("cFpoShgVli"));
			String rmkFpoShgVli = request.getParameter("rmkFpoShgVli");
			Boolean pLivelihood = Boolean.parseBoolean(request.getParameter("pLivelihood"));
			Boolean cLivelihood = Boolean.parseBoolean(request.getParameter("cLivelihood"));
			String rmkLivelihood = request.getParameter("rmkLivelihood");
												
			mav.setViewName("projectEvaluation/projectProfileMain");
			String projProfilestatus = PEService.checkProjectProfileStatus(project);
			if(projProfilestatus != null) {
				if ("1".equals(projProfilestatus)) {
		            request.setAttribute("projectProfileConfirmed", "true");
		        }
				if ("2".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		        }
				if ("3".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		        }
				if ("4".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		        }
				if ("5".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				if ("6".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("13".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
			result = PEService.saveEquityAspect(projectProfileId, pWatershedCom, cWatershedCom, rmkWatershedCom, pFpoShgVli, cFpoShgVli, rmkFpoShgVli, 
												pLivelihood, cLivelihood, rmkLivelihood, session, fromno);
						
			if ("success".equals(result)) {
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			
			request.setAttribute("projectProfileConfirmed", "true");
			request.setAttribute("evaluationDetailConfirmed", "true");
			request.setAttribute("fundUtilizationConfirmed", "true");
			request.setAttribute("croppedDetails1Confirmed", "true");
			request.setAttribute("manDaysDetailConfirmed", "true");
			request.setAttribute("ecoPerspectiveConfirmed", "true");

	        mav.addObject("distName", distName);
	        mav.addObject("monthList", monthList);
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        mav.addObject("pagency", pagency);
    
	    } else {
	        if (session != null) {
	            session.invalidate();
	        }
	        mav.setViewName("login");
	        mav.addObject("login", new Login());
	    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	    return mav;
	}
	
	@RequestMapping(value="projectProfile", method=RequestMethod.GET)
    public ModelAndView projectProfile(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		String stName = (String) session.getAttribute("stName");
		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		Integer pcode = Integer.parseInt(request.getParameter("pcode"));
		String project = request.getParameter("pcode");
		String dname = request.getParameter("dname");
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		String mname = request.getParameter("mname");
		String fname = request.getParameter("fname");
		String pagency = request.getParameter("pagency");

		LinkedHashMap<Integer, List<ProjectEvaluationBean>> getprojectProfileData = null;
		
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			String projProfilestatus = PEService.checkProjectProfileStatus(project);
			if (projProfilestatus != null) {
				getprojectProfileData = PEService.fetchprojProfileData(pcode);
			}
			else {
				getprojectProfileData = PEService.getprojProfileData(dcode, pcode);
			     }
			mav = new ModelAndView("projectEvaluation/projectProfile");
			mav.addObject("projectList",getprojectProfileData);
			mav.addObject("stName",stName);
			mav.addObject("dcode",dcode);
			mav.addObject("pcode",pcode);
			mav.addObject("dname",dname);
			mav.addObject("pname",pname);
			mav.addObject("mcode", mcode);
			mav.addObject("mname", mname);
			mav.addObject("fcode", fcode);
			mav.addObject("fname", fname);
			mav.addObject("pagency", pagency);
			mav.addObject("blockList", PEService.getProjProfileBlock(pcode));
		}
		else {
			mav = new ModelAndView("login");										
			mav.addObject("login", new Login());
		}
		return mav;
    }
	
	@RequestMapping(value = "saveprojectProfile", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView submitProjectProfile(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession(false); 
	    ModelAndView mav = new ModelAndView();
        try {
	    if (session != null && session.getAttribute("loginID") != null) {
	    	monthList = PEService.getmonthforproject();
            BigDecimal sanctionedC = new BigDecimal(request.getParameter("sanctionedC"));
	        BigDecimal cShare = new BigDecimal(request.getParameter("cShare"));
	        BigDecimal sShare = new BigDecimal(request.getParameter("sShare"));
	        BigDecimal sancitonedP = new BigDecimal(request.getParameter("sancitonedP"));
	        Integer villageC = Integer.parseInt(request.getParameter("villageC"));
	        Integer waterC = Integer.parseInt(request.getParameter("waterC"));
	        Integer membersWC = Integer.parseInt(request.getParameter("membersWC"));
	        Integer householdsC = Integer.parseInt(request.getParameter("householdsC"));
	        Integer evaId = 1;
	        Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	        String distName = request.getParameter("distName");
	        Integer projid = Integer.parseInt(request.getParameter("projid"));
	        String project = request.getParameter("projid");
	        String projName = request.getParameter("projname");
	        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	        String mname = request.getParameter("mname");
	        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	        String fname = request.getParameter("fname");
	        String pagency = request.getParameter("pagency");
	        
	        mav.setViewName("projectEvaluation/projectProfileMain");
	        String projProfilestatus = PEService.checkProjectProfileStatus(project);
			if(projProfilestatus != null) {
				if ("1".equals(projProfilestatus)) {
		            request.setAttribute("projectProfileConfirmed", "true");
		        }
				if ("2".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		        }
				if ("3".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		        }
				if ("4".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		        }
				if ("5".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				if ("6".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("13".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
			String result = PEService.insertprojectProfile(projid, fcode, mcode, evaId, sanctionedC, cShare, sShare, sancitonedP, villageC,
	                waterC, membersWC, householdsC, pagency, session, request);

	        if ("success".equals(result)) {
	            request.setAttribute("projectProfileConfirmed", "true");
	        }
	        String pAgency = PEService.getpAgency(project);
	        mav.addObject("distName", distName);
	        mav.addObject("monthList", monthList);
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        mav.addObject("pagency", pAgency);
	    } else {
	        if (session != null) {
	            session.invalidate();
	        }
	        mav.setViewName("login");
	        mav.addObject("login", new Login());
	    }
        }
        catch (Exception e) {
			e.printStackTrace();
		}
	    return mav;
	}


	
	@RequestMapping(value="getMandayDeatails", method=RequestMethod.GET)
	public ModelAndView getMandayDeatails(HttpServletRequest request, HttpServletResponse response)
	{
		 session = request.getSession(true);
		 ModelAndView mav = new ModelAndView();
		 String stName = (String) session.getAttribute("stName");
		 Integer dcode = Integer.parseInt(request.getParameter("dcode")); 
		 Integer pcode = Integer.parseInt(request.getParameter("pcode")); 
		 String dname = request.getParameter("dname"); 
		 String pname = request.getParameter("pname");
		 Integer mcode = Integer.parseInt(request.getParameter("mcode")); 
		 Integer fcode = Integer.parseInt(request.getParameter("fcode")); 
		 String mname =request.getParameter("mname"); 
		 String fname = request.getParameter("fname");
		 Integer profile_id=0;
		 
		 String pre_farmer_income=null;
		 String mid_farmer_income=null;
		 String control_farmer_income=null;
		 String remark_farmer_income=null;
		 String farmer_benefited=null;
		 String control_farmer_benefited=null;
		 String remark_farmer_benefited=null;
		 String mandays_generated=null;
		 String control_mandays_generated=null;
		 String remark_mandays_generated=null;
		 String pre_dug_well=null;
		 String mid_dug_well=null;
		 String control_dug_well=null;
		 String remark_dug_well=null;
		 String pre_tube_well=null;
		 String mid_tube_well=null;
		 String control_tube_well=null;
		 String remark_tube_well=null;
		 
		 
		 try {
				 Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
				 
				 List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
				 if(session!=null && session.getAttribute("loginID")!=null) 
				 {
					 mav = new ModelAndView("projectEvaluation/manDaysDetails");
					 
					 profile_id=PEService.getProjectProfileId( pcode, fcode, mcode);
					 
					 list=PEService.getMandayDeatails(profile_id);
					 for(ProjectEvaluationBean bean : list) 
					 {
						
						 pre_farmer_income=bean.getPre_farmer_income().toString();
						 mid_farmer_income=bean.getMid_farmer_income().toString();
						 control_farmer_income=bean.getControl_farmer_income().toString();
						 remark_farmer_income=bean.getRemark_farmer_income();
						 
						 farmer_benefited=bean.getFarmer_benefited().toString();
//						 control_farmer_benefited=bean.getControl_farmer_benefited().toString();
						 remark_farmer_benefited=bean.getRemark_farmer_benefited();
						
						 mandays_generated=bean.getMandays_generated().toString();
//						 control_mandays_generated=bean.getControl_mandays_generated().toString();
						 remark_mandays_generated=bean.getRemark_mandays_generated();
						
						 pre_dug_well=bean.getPre_dug_well().toString();
						 mid_dug_well=bean.getMid_dug_well().toString();
						 control_dug_well=bean.getControl_dug_well().toString();
						 remark_dug_well=bean.getRemark_dug_well().toString();
						 
						 pre_tube_well=bean.getPre_tube_well().toString();
						 mid_tube_well=bean.getMid_tube_well().toString();
						 control_tube_well=bean.getControl_tube_well().toString();
						 remark_tube_well=bean.getRemark_tube_well().toString();
						 
					 }
					 
					 mav.addObject("pre_farmer_income",pre_farmer_income); 
					 mav.addObject("mid_farmer_income",mid_farmer_income); 
					 mav.addObject("control_farmer_income",control_farmer_income); 
					 mav.addObject("remark_farmer_income",remark_farmer_income); 
					 mav.addObject("farmer_benefited",farmer_benefited); 
//					 mav.addObject("control_farmer_benefited",control_farmer_benefited); 
					 mav.addObject("remark_farmer_benefited",remark_farmer_benefited); 
					 mav.addObject("mandays_generated",mandays_generated); 
//					 mav.addObject("control_mandays_generated",control_mandays_generated); 
					 mav.addObject("remark_mandays_generated",remark_mandays_generated);
					 
					 mav.addObject("pre_dug_well",pre_dug_well); 
					 mav.addObject("mid_dug_well",mid_dug_well); 
					 mav.addObject("control_dug_well",control_dug_well); 
					 mav.addObject("remark_dug_well",remark_dug_well); 
					 mav.addObject("pre_tube_well",pre_tube_well); 
					 mav.addObject("mid_tube_well",mid_tube_well); 
					 mav.addObject("control_tube_well",control_tube_well); 
					 mav.addObject("remark_tube_well",remark_tube_well);
					 
			
					 mav.addObject("stName",stName);
					 mav.addObject("dcode",dcode); 
					 mav.addObject("pcode",pcode);
					 mav.addObject("distName",dname); 
					 mav.addObject("projName",pname);
					 mav.addObject("mcode", mcode); 
					 mav.addObject("month", mname);
					 mav.addObject("fcode", fcode); 
					 mav.addObject("finyear", fname);
				
					
				}
				else {
					mav = new ModelAndView("login");
					mav.addObject("login", new Login());
				}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "saveMandaysDetails", method = RequestMethod.POST)
	public ModelAndView saveMandaysDetails(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession(false); 
	    ModelAndView mav = new ModelAndView();
	    String result=null;
	    
	    if (session != null && session.getAttribute("loginID") != null) 
	    {
	    	Character area = 'P';
	    	monthList = PEService.getmonthforproject();
	    	BigDecimal pre_farmer_income = new BigDecimal(request.getParameter("pre_farmer_income"));
	    	BigDecimal mid_farmer_income = new BigDecimal(request.getParameter("mid_farmer_income"));
	    	BigDecimal control_farmer_income = new BigDecimal(request.getParameter("control_farmer_income"));
	    	String remark_farmer_income = request.getParameter("remark_farmer_income");
	    	
	        Integer farmer_benefited = Integer.parseInt(request.getParameter("farmer_benefited"));
//	        Integer control_farmer_benefited = Integer.parseInt(request.getParameter("control_farmer_benefited"));
	        Integer control_farmer_benefited = null;
	        String remark_farmer_benefited = request.getParameter("remark_farmer_benefited");
	        
	        Integer mandays_generated = Integer.parseInt(request.getParameter("mandays_generated"));
//	        Integer control_mandays_generated = Integer.parseInt(request.getParameter("control_mandays_generated"));
	        Integer control_mandays_generated = null;
	        String remark_mandays_generated = request.getParameter("remark_mandays_generated");
	        
	        BigDecimal pre_dug_well = new BigDecimal(request.getParameter("pre_dug_well"));
	        BigDecimal mid_dug_well = new BigDecimal(request.getParameter("mid_dug_well"));
	        BigDecimal control_dug_well = new BigDecimal(request.getParameter("control_dug_well"));
	        String remark_dug_well = request.getParameter("remark_dug_well");
	        
	        BigDecimal pre_tube_well = new BigDecimal(request.getParameter("pre_tube_well"));
	        BigDecimal mid_tube_well = new BigDecimal(request.getParameter("mid_tube_well"));
	        BigDecimal control_tube_well = new BigDecimal(request.getParameter("control_tube_well"));
	        String remark_tube_well = request.getParameter("remark_tube_well");
	        
	        Integer fromno = Integer.parseInt(request.getParameter("fromno"));
	        Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	        String distName = request.getParameter("distName");
	        Integer projid = Integer.parseInt(request.getParameter("projid"));
	        String project = request.getParameter("projid");
	        String projName = request.getParameter("projName");
	        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	        String mname = request.getParameter("mname");
	        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	        String fname = request.getParameter("fname");
	        String pagency = PEService.getpAgency(project);
	        
	        Integer profile_id=0;
	        profile_id=PEService.getProjectProfileId( projid, fcode, mcode);
	        
	        mav.setViewName("projectEvaluation/projectProfileMain");
	        String projProfilestatus = PEService.checkProjectProfileStatus(project);
			if(projProfilestatus != null) {
				if ("1".equals(projProfilestatus)) {
		            request.setAttribute("projectProfileConfirmed", "true");
		        }
				if ("2".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		        }
				if ("3".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		        }
				if ("4".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		        }
				if ("5".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				if ("6".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("13".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
	        
	        result=PEService.saveMandaysDetails(profile_id,pre_farmer_income,mid_farmer_income,control_farmer_income,remark_farmer_income,
					farmer_benefited,control_farmer_benefited,remark_farmer_benefited, mandays_generated,control_mandays_generated,remark_mandays_generated,
					pre_dug_well,mid_dug_well,control_dug_well,remark_dug_well,pre_tube_well, mid_tube_well,control_tube_well,remark_tube_well,
					fromno,session, area 
					  );
	   
	        if ("success".equals(result)) {
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			
			request.setAttribute("projectProfileConfirmed", "true");
			request.setAttribute("evaluationDetailConfirmed", "true");
			request.setAttribute("fundUtilizationConfirmed", "true");
			request.setAttribute("croppedDetails1Confirmed", "true");

	        mav.addObject("distName", distName);
	        mav.addObject("monthList", monthList);
            mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        mav.addObject("pagency", pagency);
	        
	    } 
	    else {
	        if (session != null) {
	            session.invalidate();
	        }
	        mav.setViewName("login");
	        mav.addObject("login", new Login());
	    }
	    return mav;
	}
	
	@RequestMapping(value="getExecutionPlanWork", method=RequestMethod.GET)
	public ModelAndView getExecutionPlanWork(HttpServletRequest request, HttpServletResponse response) {
	    session = request.getSession(true);

	    Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	    Integer pcode = Integer.parseInt(request.getParameter("pcode"));
	    String dname = request.getParameter("dname");
	    String pname = request.getParameter("pname");
	    Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	    Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	    String mname = request.getParameter("mname");
	    String fname = request.getParameter("fname");

	    ModelAndView mav = new ModelAndView();

	    try {
	        Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());

	        if (session != null && session.getAttribute("loginID") != null) {
	            mav = new ModelAndView("projectEvaluation/executionPlanWork");

	            Integer profile_id = PEService.getProjectProfileId(pcode, fcode, mcode);
	            List<ProjectEvaluationBean> list = PEService.getExecutionPlanWork(profile_id);

	            if (list == null || list.isEmpty()) {
	                LinkedHashMap<Integer, List<ProjectEvaluationBean>> planWorkData = PEService.getPlanWorkData(pcode);
	                
	                if (!planWorkData.isEmpty()) {
	                    List<ProjectEvaluationBean> firstEntry = planWorkData.values().iterator().next();
	                    if (!firstEntry.isEmpty()) {
	                        ProjectEvaluationBean bean = firstEntry.get(0);
	                        mav.addObject("crw", bean.getCreatedwork());
	                        mav.addObject("comw", bean.getWorkcompleted());
	                        mav.addObject("ongw", bean.getWorkongoing());
	                    }
	                }
	            } else {
	                ProjectEvaluationBean bean = list.get(0);
	                mav.addObject("crw", bean.getCreated_work());
	                mav.addObject("crwre", bean.getCreated_work_remark());
	                mav.addObject("comw", bean.getCompleted_work());
	                mav.addObject("comwre", bean.getCompleted_work_remark());
	                mav.addObject("ongw", bean.getOngoing_work());
	                mav.addObject("ongwre", bean.getOngoing_work_remark());
	            }
	        } else {
	            mav = new ModelAndView("login");
	            mav.addObject("login", new Login());
	        }

	        mav.addObject("dcode", dcode);
	        mav.addObject("pcode", pcode);
	        mav.addObject("distName", dname);
	        mav.addObject("projName", pname);
	        mav.addObject("mcode", mcode);
	        mav.addObject("month", mname);
	        mav.addObject("fcode", fcode);
	        mav.addObject("finyear", fname);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return mav;
	}

	
	@RequestMapping(value = "saveExecutionPlanWork", method = RequestMethod.POST)
	public ModelAndView saveExecutionPlanWork(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession(false); 
	    ModelAndView mav = new ModelAndView();
	    String result=null;
	    
	    if (session != null && session.getAttribute("loginID") != null) 
	    {
	    	monthList = PEService.getmonthforproject();
            Integer created_work = Integer.parseInt(request.getParameter("created_work"));
	        String created_work_remark = request.getParameter("created_work_remark");
	        Integer completed_work = Integer.parseInt(request.getParameter("completed_work"));
	        String completed_work_remark = request.getParameter("completed_work_remark");
	        Integer ongoing_work = Integer.parseInt(request.getParameter("ongoing_work"));
	        String ongoing_work_remark = request.getParameter("ongoing_work_remark");
	        
	        Integer fromno = Integer.parseInt(request.getParameter("fromno"));
	        Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	        String distName = request.getParameter("distName");
	        Integer projid = Integer.parseInt(request.getParameter("projid"));
	        String project = request.getParameter("projid");
	        String projName = request.getParameter("projName");
	        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	        String mname = request.getParameter("mname");
	        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	        String fname = request.getParameter("fname");
	        String pagency = PEService.getpAgency(project);
	        
	        Integer profile_id=0;
	        profile_id=PEService.getProjectProfileId( projid, fcode, mcode);
	        
	        mav.setViewName("projectEvaluation/projectProfileMain");
	        String projProfilestatus = PEService.checkProjectProfileStatus(project);
			if(projProfilestatus != null) {
				if ("1".equals(projProfilestatus)) {
		            request.setAttribute("projectProfileConfirmed", "true");
		        }
				if ("2".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		        }
				if ("3".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		        }
				if ("4".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		        }
				if ("5".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				if ("6".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("13".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
	        result=PEService.saveExecutionPlanWork(profile_id, created_work, created_work_remark, completed_work, completed_work_remark, ongoing_work, ongoing_work_remark, fromno,  session );
	   
	        if ("success".equals(result)) {
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			
			request.setAttribute("projectProfileConfirmed", "true");
			request.setAttribute("evaluationDetailConfirmed", "true");
			request.setAttribute("fundUtilizationConfirmed", "true");
			request.setAttribute("croppedDetails1Confirmed", "true");
			request.setAttribute("manDaysDetailConfirmed", "true");
			request.setAttribute("ecoPerspectiveConfirmed", "true");
			request.setAttribute("equityAspectConfirmed", "true");

	        mav.addObject("distName", distName);
	        mav.addObject("monthList", monthList);
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        mav.addObject("pagency", pagency);
	        
	    } 
	    else {
	        if (session != null) {
	            session.invalidate();
	        }
	        mav.setViewName("login");
	        mav.addObject("login", new Login());
	    }
	    return mav;
	}
	
	@RequestMapping(value="getQualityShapeFile", method=RequestMethod.GET)
	public ModelAndView getQualityShapeFile(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		ModelAndView mav = new ModelAndView();
		
		String stName = (String) session.getAttribute("stName");
		Integer dcode = Integer.parseInt(request.getParameter("dcode")); 
		Integer pcode = Integer.parseInt(request.getParameter("pcode")); 
		String dname = request.getParameter("dname"); 
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode")); 
		Integer fcode = Integer.parseInt(request.getParameter("fcode")); 
		String mname =request.getParameter("mname"); 
		String fname = request.getParameter("fname");
		
		String shape_file_area=null;
		String shape_file_area_remark=null;
		String variation_area=null;
		String variation_area_remark=null;
		
		Integer profile_id=0;
		
		try {
		
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("projectEvaluation/qualityShapeFile");
			
			List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
			profile_id=PEService.getProjectProfileId( pcode, fcode, mcode);
			list=PEService.getQualityShapeFile(profile_id);
			for(ProjectEvaluationBean bean : list) {
				
				shape_file_area=bean.getShape_file_area().toString();
				shape_file_area_remark=bean.getShape_file_area_remark().toString();
				variation_area=bean.getVariation_area().toString();
				variation_area_remark=bean.getVariation_area_remark().toString();
				
				
			 }
			 
			 mav.addObject("sfile_area",shape_file_area); 
			 mav.addObject("sfile_areare",shape_file_area_remark); 
			 mav.addObject("variationarea",variation_area); 
			 mav.addObject("variationareare",variation_area_remark); 
			 
			
			
		}
		
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		 mav.addObject("stName",stName);
		 mav.addObject("dcode",dcode); 
		 mav.addObject("pcode",pcode);
		 mav.addObject("distName",dname); 
		 mav.addObject("projName",pname);
		 mav.addObject("mcode", mcode); 
		 mav.addObject("month", mname);
		 mav.addObject("fcode", fcode); 
		 mav.addObject("finyear", fname);
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return mav;
	}
	
	
	@RequestMapping(value = "saveQualityShapeFile", method = RequestMethod.POST)
	public ModelAndView saveQualityShapeFile(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession(true); 
	    ModelAndView mav = new ModelAndView();
	    String result=null;
	    
	    if (session != null && session.getAttribute("loginID") != null) 
	    {
	    	monthList = PEService.getmonthforproject();
	    	BigDecimal shape_file_area = new BigDecimal(request.getParameter("shape_file_area"));
	        String shape_file_area_remark = request.getParameter("shape_file_area_remark");
	        BigDecimal variation_area = new BigDecimal(request.getParameter("variation_area"));
	        String variation_area_remark = request.getParameter("variation_area_remark");
	        
	        Integer fromno = Integer.parseInt(request.getParameter("fromno"));
	        Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	        String distName = request.getParameter("distName");
	        Integer projid = Integer.parseInt(request.getParameter("projid"));
	        String project = request.getParameter("projid");
	        String projName = request.getParameter("projName");
	        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	        String mname = request.getParameter("mname");
	        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	        String fname = request.getParameter("fname");
	        String pagency = PEService.getpAgency(project);
	        
	        Integer profile_id=0;
	        profile_id=PEService.getProjectProfileId( projid, fcode, mcode);
	        
	        mav.setViewName("projectEvaluation/projectProfileMain");
	        String projProfilestatus = PEService.checkProjectProfileStatus(project);
			if(projProfilestatus != null) {
				if ("1".equals(projProfilestatus)) {
		            request.setAttribute("projectProfileConfirmed", "true");
		        }
				if ("2".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		        }
				if ("3".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		        }
				if ("4".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		        }
				if ("5".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				if ("6".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("13".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
	        result=PEService.saveQualityShapeFile(profile_id, shape_file_area, shape_file_area_remark, variation_area, variation_area_remark, fromno,  session );
	   
	        if ("success".equals(result)) {
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			
			request.setAttribute("projectProfileConfirmed", "true");
			request.setAttribute("evaluationDetailConfirmed", "true");
			request.setAttribute("fundUtilizationConfirmed", "true");
			request.setAttribute("croppedDetails1Confirmed", "true");
			request.setAttribute("manDaysDetailConfirmed", "true");
			request.setAttribute("ecoPerspectiveConfirmed", "true");
			request.setAttribute("equityAspectConfirmed", "true");
			request.setAttribute("executionOfPlannedWorkConfirmed", "true");

	        mav.addObject("distName", distName);
	        mav.addObject("monthList", monthList);
            mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        mav.addObject("pagency", pagency);
	        
	    } 
	    else {
	        if (session != null) {
	            session.invalidate();
	        }
	        mav.setViewName("login");
	        mav.addObject("login", new Login());
	    }
	    return mav;
	}
	
	
	@RequestMapping(value="getStatusGeotagWork", method=RequestMethod.GET)
	public ModelAndView getStatusGeotagWork(HttpServletRequest request, HttpServletResponse response) {
	    session = request.getSession(true);

	    Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	    Integer pcode = Integer.parseInt(request.getParameter("pcode"));
	    String dname = request.getParameter("dname");
	    String pname = request.getParameter("pname");
	    Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	    Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	    String mname = request.getParameter("mname");
	    String fname = request.getParameter("fname");
	    String twork=null;
	    //Integer profile_id=0;

	    ModelAndView mav = new ModelAndView();

	    try {
	        Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());

	        if (session != null && session.getAttribute("loginID") != null) {
	            mav = new ModelAndView("projectEvaluation/statusGeotagWork");

	            Integer profile_id = PEService.getProjectProfileId(pcode, fcode, mcode);
	            List<ProjectEvaluationBean> list = PEService.getStatusGeotagWork(profile_id);

	            if (list == null || list.isEmpty()) {
	                LinkedHashMap<Integer, List<ProjectEvaluationBean>> geoTagData = PEService.getGeoTaggingWorks(pcode);
	                
	                if (!geoTagData.isEmpty()) {
	                    List<ProjectEvaluationBean> firstEntry = geoTagData.values().iterator().next();
	                    if (!firstEntry.isEmpty()) {
	                        ProjectEvaluationBean bean = firstEntry.get(0);
	                        mav.addObject("twork",bean.getGeotag()); 
	                    }
	                }
	            } else {
	                ProjectEvaluationBean bean = list.get(0);
	                mav.addObject("twork", bean.getGeo_tagg_work());
	                mav.addObject("tworkre",bean.getGeo_tagg_work_remark());
	            }
	        } else {
	            mav = new ModelAndView("login");
	            mav.addObject("login", new Login());
	        }

	        mav.addObject("dcode", dcode);
	        mav.addObject("pcode", pcode);
	        mav.addObject("distName", dname);
	        mav.addObject("projName", pname);
	        mav.addObject("mcode", mcode);
	        mav.addObject("month", mname);
	        mav.addObject("fcode", fcode);
	        mav.addObject("finyear", fname);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return mav;
	}

	@RequestMapping(value = "saveGeoTagDetails", method = RequestMethod.POST)
	public ModelAndView saveGeoTagDetails(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession(true); 
	    ModelAndView mav = new ModelAndView();
	    String result=null;
	    
	    if (session != null && session.getAttribute("loginID") != null) 
	    {
	    	Integer geo_tagg_work = Integer.parseInt(request.getParameter("geo_tagg_work"));
	        String geo_tagg_work_remark = request.getParameter("geo_tagg_work_remark");
	        
	        monthList = PEService.getmonthforproject();
	        Integer fromno = Integer.parseInt(request.getParameter("fromno"));
	        Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	        String distName = request.getParameter("distName");
	        Integer projid = Integer.parseInt(request.getParameter("projid"));
	        String project = request.getParameter("projid");
	        String projName = request.getParameter("projName");
	        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	        String mname = request.getParameter("mname");
	        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	        String fname = request.getParameter("fname");
	        String pagency = PEService.getpAgency(project);
	        
	        Integer profile_id=0;
	        profile_id=PEService.getProjectProfileId( projid, fcode, mcode);
	        
	        mav.setViewName("projectEvaluation/projectProfileMain");
	        String projProfilestatus = PEService.checkProjectProfileStatus(project);
			if(projProfilestatus != null) {
				if ("1".equals(projProfilestatus)) {
		            request.setAttribute("projectProfileConfirmed", "true");
		        }
				if ("2".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		        }
				if ("3".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		        }
				if ("4".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		        }
				if ("5".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				if ("6".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("13".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
	        result=PEService.saveGeoTagDetails(profile_id, geo_tagg_work, geo_tagg_work_remark, fromno, session );
	   
	        if ("success".equals(result)) {
	        	request.setAttribute("geoTagDetailsConfirmed", "true");
	        }
			
			request.setAttribute("projectProfileConfirmed", "true");
			request.setAttribute("evaluationDetailConfirmed", "true");
			request.setAttribute("fundUtilizationConfirmed", "true");
			request.setAttribute("croppedDetails1Confirmed", "true");
			request.setAttribute("manDaysDetailConfirmed", "true");
			request.setAttribute("ecoPerspectiveConfirmed", "true");
			request.setAttribute("equityAspectConfirmed", "true");
			request.setAttribute("executionOfPlannedWorkConfirmed", "true");
			request.setAttribute("qualityShapeFileConfirmed", "true");
			request.setAttribute("geoTagDetailsConfirmed", "true");
			

	        mav.addObject("distName", distName);
	        mav.addObject("monthList", monthList);
            mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        mav.addObject("pagency", pagency);
	        
	    } 
	    else {
	        if (session != null) {
	            session.invalidate();
	        }
	        mav.setViewName("login");
	        mav.addObject("login", new Login());
	    }
	    return mav;
	}
	
	
	
	
	
	
/*	@RequestMapping(value="/saveGeoTagDetails", method = RequestMethod.POST)
	@ResponseBody
	public String saveGeoTagDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="profile_id") Integer profile_id, 
			@RequestParam(value ="geo_tagg_work") Integer geo_tagg_work, @RequestParam(value ="geo_tagg_work_remark") String geo_tagg_work_remark, 
			@RequestParam(value ="fromno") Integer fromno)
	{
		String res="fail";
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			
			if( res.equals("fail")) {
				res=PEService.saveGeoTagDetails(profile_id, geo_tagg_work, geo_tagg_work_remark, fromno, session );
			}
			else {
				res= "fail";
			}
			
		}
		
		return res; 
	}  */

	
	@RequestMapping(value = "/croppedDetails1", method = RequestMethod.GET)
	public ModelAndView croppedDetails1(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		 Integer pcode = Integer.parseInt(request.getParameter("pcode")==null?"0":request.getParameter("pcode"));
		 Integer fcode = Integer.parseInt(request.getParameter("fcode")==null?"0":request.getParameter("fcode"));
		 Integer mcode = Integer.parseInt(request.getParameter("mcode")==null?"0":request.getParameter("mcode"));
		 Integer dcode = Integer.parseInt(request.getParameter("dcode")==null?"0":request.getParameter("dcode"));
		 
		 String areaType = null;
		 String stName = (String) session.getAttribute("stName");
		 String dname = request.getParameter("dname");
		 String pname = request.getParameter("pname");
		 String mname = request.getParameter("mname");
		 String fname = request.getParameter("fname");
		 Integer projectProfileId = PEService.getProjectProfileId(pcode, fcode, mcode);
		 List<WdcpmksyCroppedDetails1> wdcCrpDtlList = new ArrayList<>();
		 wdcCrpDtlList = PEService.getCroppedDetails(projectProfileId);
		 if(session!=null && session.getAttribute("loginID")!=null) {
			int regId = (int)session.getAttribute("regId");
			mav = new ModelAndView("projectEvaluation/croppedDetails1");
			mav.addObject("wdcCrpDtlList", wdcCrpDtlList);
			mav.addObject("wdcCrpDtlListSize", wdcCrpDtlList.size());
			mav.addObject("projProId", projectProfileId);
			mav.addObject("areaType", areaType);
			mav.addObject("pcode", pcode);
			mav.addObject("fcode", fcode);
			mav.addObject("mcode", mcode);
			mav.addObject("dcode", dcode);
			mav.addObject("dname",dname);
			mav.addObject("pname", pname);
			mav.addObject("fname", fname);
			mav.addObject("mname", mname);
			mav.addObject("stName",stName);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	
	@RequestMapping(value = "/saveOrUpdateCroppedDetails", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveOrUpdateCroppedDetails(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String res = "";
		session = request.getSession(true);

		try {
			if (session != null && session.getAttribute("loginID") != null) {
				monthList = PEService.getmonthforproject();
				Integer dcode = Integer.parseInt(request.getParameter("dcode"));
				String distName = request.getParameter("dname");
				Integer projid = Integer.parseInt(request.getParameter("pcode"));
				String project = request.getParameter("pcode");
				String projName = request.getParameter("pname");
				Integer mcode = Integer.parseInt(request.getParameter("mcode"));
				String mname = request.getParameter("mname");
				Integer fcode = Integer.parseInt(request.getParameter("fcode"));
				String fname = request.getParameter("fname");
				String pagency = PEService.getpAgency(project);
				
				Integer projProfId = Integer.parseInt(request.getParameter("projProfId"));

				BigDecimal prekharifCrop = new BigDecimal(request.getParameter("prekharif"));

				BigDecimal prerabiCrop = new BigDecimal(request.getParameter("prerabi"));
				BigDecimal prethirdCrop = new BigDecimal(request.getParameter("prethirdCrop"));

				BigDecimal precereals = new BigDecimal(request.getParameter("precereals"));
				BigDecimal prepulses = new BigDecimal(request.getParameter("prepulses"));

				BigDecimal preoilSeed = new BigDecimal(request.getParameter("preoilSeed"));
				BigDecimal premillets = new BigDecimal(request.getParameter("premillets"));

				BigDecimal preothers = request.getParameter("preothers") == "" ? null
						: new BigDecimal(request.getParameter("preothers"));
				BigDecimal prehorticulture = new BigDecimal(request.getParameter("prehorticulture"));

				BigDecimal prenetSown = new BigDecimal(request.getParameter("prenetSown"));
				BigDecimal precropIntensity = new BigDecimal(request.getParameter("precropIntensity"));

				BigDecimal midkharifCrop = new BigDecimal(request.getParameter("midkharif"));

				BigDecimal midrabiCrop = new BigDecimal(request.getParameter("midrabi"));
				BigDecimal midthirdCrop = new BigDecimal(request.getParameter("midthirdCrop"));

				BigDecimal midcereals = new BigDecimal(request.getParameter("midcereals"));
				BigDecimal midpulses = new BigDecimal(request.getParameter("midpulses"));

				BigDecimal midoilSeed = new BigDecimal(request.getParameter("midoilSeed"));
				BigDecimal midmillets = new BigDecimal(request.getParameter("midmillets"));

				BigDecimal midothers = request.getParameter("midothers") == "" ? null
						: new BigDecimal(request.getParameter("midothers"));
				BigDecimal midhorticulture = new BigDecimal(request.getParameter("midhorticulture"));

				BigDecimal midnetSown = new BigDecimal(request.getParameter("midnetSown"));
				BigDecimal midcropIntensity = new BigDecimal(request.getParameter("midcropIntensity"));

//			BigDecimal diversifiedCrop = new BigDecimal(request.getParameter("diversifiedCrop"));

				BigDecimal ckharifCrop = new BigDecimal(request.getParameter("ckharif"));

				BigDecimal crabiCrop = new BigDecimal(request.getParameter("crabi"));
				BigDecimal cthirdCrop = new BigDecimal(request.getParameter("cthirdCrop"));

				BigDecimal ccereals = new BigDecimal(request.getParameter("ccereals"));
				BigDecimal cpulses = new BigDecimal(request.getParameter("cpulses"));

				BigDecimal coilSeed = new BigDecimal(request.getParameter("coilSeed"));
				BigDecimal cmillets = new BigDecimal(request.getParameter("cmillets"));

				BigDecimal cothers = request.getParameter("cothers") == "" ? null
						: new BigDecimal(request.getParameter("cothers"));
				BigDecimal chorticulture = new BigDecimal(request.getParameter("chorticulture"));

				BigDecimal cnetSown = new BigDecimal(request.getParameter("cnetSown"));
				BigDecimal ccropIntensity = new BigDecimal(request.getParameter("ccropIntensity"));

				String kharifCropremark = request.getParameter("kharifremark");

				String rabiCropremark = request.getParameter("rabiremark");
				String thirdCropremark = request.getParameter("thirdCropremark");

				String cerealsremark = request.getParameter("cerealsremark");
				String pulsesremark = request.getParameter("pulsesremark");

				String oilSeedremark = request.getParameter("oilSeedremark");
				String milletsremark = request.getParameter("milletsremark");

				String othersremark = request.getParameter("othersremark");
				String horticultureremark = request.getParameter("horticultureremark");

				String netSownremark = request.getParameter("netSownremark");
				String cropIntensityremark = request.getParameter("cropIntensityremark");

				String othercrop = request.getParameter("othercrop");

				mav.setViewName("projectEvaluation/projectProfileMain");
				String projProfilestatus = PEService.checkProjectProfileStatus(project);
				if (projProfilestatus != null) {
					if ("1".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
					}
					if ("2".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
					}
					if ("3".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
					}
					if ("4".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
						request.setAttribute("croppedDetails1Confirmed", "true");
					}
					if ("5".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
						request.setAttribute("croppedDetails1Confirmed", "true");
						request.setAttribute("croppedDetails2Confirmed", "true");
					}
					if ("6".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
						request.setAttribute("croppedDetails1Confirmed", "true");
						request.setAttribute("croppedDetails2Confirmed", "true");
						request.setAttribute("croppedDetails3Confirmed", "true");
					}
					if ("7".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
						request.setAttribute("croppedDetails1Confirmed", "true");
						request.setAttribute("croppedDetails2Confirmed", "true");
						request.setAttribute("croppedDetails3Confirmed", "true");
						request.setAttribute("manDaysDetailConfirmed", "true");
					}
					if ("8".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
						request.setAttribute("croppedDetails1Confirmed", "true");
						request.setAttribute("croppedDetails2Confirmed", "true");
						request.setAttribute("croppedDetails3Confirmed", "true");
						request.setAttribute("manDaysDetailConfirmed", "true");
						request.setAttribute("productionDetailsConfirmed", "true");
					}
					if ("9".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
						request.setAttribute("croppedDetails1Confirmed", "true");
						request.setAttribute("croppedDetails2Confirmed", "true");
						request.setAttribute("croppedDetails3Confirmed", "true");
						request.setAttribute("manDaysDetailConfirmed", "true");
						request.setAttribute("productionDetailsConfirmed", "true");
						request.setAttribute("ecoPerspectiveConfirmed", "true");
					}
					if ("10".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
						request.setAttribute("croppedDetails1Confirmed", "true");
						request.setAttribute("croppedDetails2Confirmed", "true");
						request.setAttribute("croppedDetails3Confirmed", "true");
						request.setAttribute("manDaysDetailConfirmed", "true");
						request.setAttribute("productionDetailsConfirmed", "true");
						request.setAttribute("ecoPerspectiveConfirmed", "true");
						request.setAttribute("equityAspectConfirmed", "true");
					}
					if ("11".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
						request.setAttribute("croppedDetails1Confirmed", "true");
						request.setAttribute("croppedDetails2Confirmed", "true");
						request.setAttribute("croppedDetails3Confirmed", "true");
						request.setAttribute("manDaysDetailConfirmed", "true");
						request.setAttribute("productionDetailsConfirmed", "true");
						request.setAttribute("ecoPerspectiveConfirmed", "true");
						request.setAttribute("equityAspectConfirmed", "true");
						request.setAttribute("executionOfPlannedWorkConfirmed", "true");
					}
					if ("12".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
						request.setAttribute("croppedDetails1Confirmed", "true");
						request.setAttribute("croppedDetails2Confirmed", "true");
						request.setAttribute("croppedDetails3Confirmed", "true");
						request.setAttribute("manDaysDetailConfirmed", "true");
						request.setAttribute("productionDetailsConfirmed", "true");
						request.setAttribute("ecoPerspectiveConfirmed", "true");
						request.setAttribute("equityAspectConfirmed", "true");
						request.setAttribute("executionOfPlannedWorkConfirmed", "true");
						request.setAttribute("qualityShapeFileConfirmed", "true");
					}
					if ("13".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
						request.setAttribute("fundUtilizationConfirmed", "true");
						request.setAttribute("croppedDetails1Confirmed", "true");
						request.setAttribute("croppedDetails2Confirmed", "true");
						request.setAttribute("croppedDetails3Confirmed", "true");
						request.setAttribute("manDaysDetailConfirmed", "true");
						request.setAttribute("productionDetailsConfirmed", "true");
						request.setAttribute("ecoPerspectiveConfirmed", "true");
						request.setAttribute("equityAspectConfirmed", "true");
						request.setAttribute("executionOfPlannedWorkConfirmed", "true");
						request.setAttribute("qualityShapeFileConfirmed", "true");
						request.setAttribute("geoTagDetailsConfirmed", "true");
					}
				}
				String result = PEService.saveOrUpdateCroppedDetails(request, session, projProfId, prekharifCrop,
						prerabiCrop, prethirdCrop, precereals, prepulses, preoilSeed, premillets, preothers,
						prehorticulture, prenetSown, precropIntensity, midkharifCrop, midrabiCrop, midthirdCrop,
						midcereals, midpulses, midoilSeed, midmillets, midothers, midhorticulture, midnetSown,
						midcropIntensity, ckharifCrop, crabiCrop, cthirdCrop, ccereals, cpulses, coilSeed, cmillets,
						cothers, chorticulture, cnetSown, ccropIntensity, kharifCropremark, rabiCropremark,
						thirdCropremark, cerealsremark, pulsesremark, oilSeedremark, milletsremark, othersremark,
						horticultureremark, netSownremark, cropIntensityremark, othercrop);

				if ("success".equals(result)) {
					request.setAttribute("croppedDetails1Confirmed", "true");
				}

				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
				request.setAttribute("fundUtilizationConfirmed", "true");

				mav.addObject("distName", distName);
				mav.addObject("projName", projName);
				mav.addObject("monthList", monthList);
				mav.addObject("monthname", mname);
				mav.addObject("fincd", fcode);
				mav.addObject("dcode", dcode);
				mav.addObject("projid", projid);
				mav.addObject("monthid", mcode);
				mav.addObject("finyr", fname);
				mav.addObject("pagency", pagency);

			} else {
				if (session != null) {
					session.invalidate();
				}
				mav.setViewName("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}
	@RequestMapping(value="/ecologicalPerspective", method=RequestMethod.GET)
	public ModelAndView ecologicalPerspective(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String stName = (String) session.getAttribute("stName");
		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		Integer pcode = Integer.parseInt(request.getParameter("pcode"));
		String dname = request.getParameter("dname");
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		String mname = request.getParameter("mname");
		String fname = request.getParameter("fname");
		
		    Integer profile_id=0;
		    Boolean naturalresource = null;
	        String naturalresourceRemark= null;
	        Boolean norm=null;
	        String normRemark=null;
	        Boolean antrlasset=null;
	        String antrlassetRemark=null;
	        Boolean controlntlresource=null;
	        Boolean controlnorm=null;
	        Boolean controlantrlasset=null;
		 
		Integer projProfId = PEService.getProjectProfileId(pcode, fcode, mcode);
		ModelAndView mav = new ModelAndView();
		List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			 profile_id=PEService.getProjectProfileId( pcode, fcode, mcode);
			 list=PEService.getEcoPerspective(profile_id);
			 for(ProjectEvaluationBean bean : list) {
				 
				 naturalresource=bean.getNatural_resource();
				 naturalresourceRemark=bean.getNatural_resource_remark().toString();
				 norm=bean.getNorms_relating();
				 normRemark=bean.getNorms_relating_remark().toString();
				 antrlasset=bean.getAntural_asset();
				 antrlassetRemark=bean.getAntural_asset_remark().toString();
				 controlntlresource=bean.getControl_natural_resource();
				 controlnorm=bean.getControl_norms_relating();
				 controlantrlasset=bean.getControl_antural_asset();
				 
			 }
			mav = new ModelAndView("projectEvaluation/EcoPerspective");
			mav.addObject("stName",stName);
			mav.addObject("dcode",dcode);
			mav.addObject("pcode",pcode);
			mav.addObject("distName",dname);
			mav.addObject("projName",pname);
			mav.addObject("mcode", mcode);
			mav.addObject("month", mname);
			mav.addObject("fcode", fcode);
			mav.addObject("finyear", fname);
			mav.addObject("projProfId",projProfId);
			
			mav.addObject("ntrlresource",naturalresource);
			mav.addObject("ntrlresourceremark",naturalresourceRemark);
			mav.addObject("norm",norm);
			mav.addObject("normremark",normRemark);
			mav.addObject("ntlasset", antrlasset);
			mav.addObject("ntlassetremark", antrlassetRemark);
			mav.addObject("controlntlresource", controlntlresource);
			mav.addObject("controlnorm", controlnorm);
			mav.addObject("controlantrlasset",controlantrlasset);
			
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value = "saveEcoPerspectiveDetails", method = RequestMethod.POST)
	public ModelAndView saveEcoPerspectiveDetails(Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession(false); 
	    ModelAndView mav = new ModelAndView();
	    String result=null;
	    
	    if (session != null && session.getAttribute("loginID") != null) 
	    {
	    	monthList = PEService.getmonthforproject();
            String naturalresource =request.getParameter("ntrlresource");
	        String naturalresourceRemark= request.getParameter("ntrlresourceremark");
	        String norm=request.getParameter("norm");
	        String normRemark=request.getParameter("normremark");
	        String antrlasset=request.getParameter("ntlasset");
	        String antrlassetRemark=request.getParameter("ntlassetremark");
	        String controlntlresource=request.getParameter("controlntlresource");
	        String controlnorm=request.getParameter("controlnorm");
	        String controlantrlasset=request.getParameter("controlantrlasset");
	        
	        Integer fromno = Integer.parseInt(request.getParameter("fromno"));
	        Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	        String distName = request.getParameter("distName");
	        Integer projid = Integer.parseInt(request.getParameter("projid"));
	        String project = request.getParameter("projid");
	        String projName = request.getParameter("projName");
	        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	        String mname = request.getParameter("mname");
	        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	        String fname = request.getParameter("fname");
	        String pagency = PEService.getpAgency(project);
	        
	        Integer profile_id=0;
	        profile_id=PEService.getProjectProfileId( projid, fcode, mcode);
	        
	        mav.setViewName("projectEvaluation/projectProfileMain");
	        String projProfilestatus = PEService.checkProjectProfileStatus(project);
			if(projProfilestatus != null) {
				if ("1".equals(projProfilestatus)) {
		            request.setAttribute("projectProfileConfirmed", "true");
		        }
				if ("2".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		        }
				if ("3".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		        }
				if ("4".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		        }
				if ("5".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				if ("6".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("13".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("croppedDetails3Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
	        result=PEService.saveEcoperspectiveDetails(profile_id, naturalresource, naturalresourceRemark, norm, normRemark, antrlasset, antrlassetRemark,
	        		controlntlresource, controlnorm, fromno, controlantrlasset, session );
	   
	        if ("success".equals(result)) {
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			
			request.setAttribute("projectProfileConfirmed", "true");
			request.setAttribute("evaluationDetailConfirmed", "true");
			request.setAttribute("fundUtilizationConfirmed", "true");
			request.setAttribute("croppedDetails1Confirmed", "true");
			request.setAttribute("manDaysDetailConfirmed", "true");
			mav.addObject("distName", distName);
			mav.addObject("monthList", monthList);
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        mav.addObject("pagency", pagency);
	        
	    } 
	    else {
	        if (session != null) {
	            session.invalidate();
	        }
	        mav.setViewName("login");
	        mav.addObject("login", new Login());
	    }
	    return mav;
	}
			
	@RequestMapping(value = "/croppedDetails2", method = RequestMethod.GET)
	public ModelAndView croppedDetails2(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		 Integer pcode = Integer.parseInt(request.getParameter("pcode")==null?"0":request.getParameter("pcode"));
		 Integer fcode = Integer.parseInt(request.getParameter("fcode")==null?"0":request.getParameter("fcode"));
		 Integer mcode = Integer.parseInt(request.getParameter("mcode")==null?"0":request.getParameter("mcode"));
		 Integer dcode = Integer.parseInt(request.getParameter("dcode")==null?"0":request.getParameter("dcode"));
		 
		 String areaType = null;
		 String stName = (String) session.getAttribute("stName");
		 String dname = request.getParameter("dname");
		 String pname = request.getParameter("pname");
		 String mname = request.getParameter("mname");
		 String fname = request.getParameter("fname");
		 Integer projectProfileId = PEService.getProjectProfileId(pcode, fcode, mcode);
		 List<WdcpmksyCroppedDetails2> wdcCrpDtlList = new ArrayList<>();
		 wdcCrpDtlList = PEService.getCroppedDetails2(projectProfileId);
		 if(session!=null && session.getAttribute("loginID")!=null) {
			int regId = (int)session.getAttribute("regId");
			mav = new ModelAndView("projectEvaluation/croppedDetails2");
			mav.addObject("wdcCrpDtlList", wdcCrpDtlList);
			mav.addObject("wdcCrpDtlListSize", wdcCrpDtlList.size());
			mav.addObject("projProId", projectProfileId);
			mav.addObject("areaType", areaType);
			mav.addObject("pcode", pcode);
			mav.addObject("fcode", fcode);
			mav.addObject("mcode", mcode);
			mav.addObject("dcode", dcode);
			mav.addObject("dname",dname);
			mav.addObject("pname", pname);
			mav.addObject("fname", fname);
			mav.addObject("mname", mname);
			mav.addObject("stName",stName);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	

	@RequestMapping(value = "/productionDetails", method = RequestMethod.GET)
	public ModelAndView productionDetails(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		 Integer pcode = Integer.parseInt(request.getParameter("pcode")==null?"0":request.getParameter("pcode"));
		 Integer fcode = Integer.parseInt(request.getParameter("fcode")==null?"0":request.getParameter("fcode"));
		 Integer mcode = Integer.parseInt(request.getParameter("mcode")==null?"0":request.getParameter("mcode"));
		 Integer dcode = Integer.parseInt(request.getParameter("dcode")==null?"0":request.getParameter("dcode"));
		 
		 String stName = (String) session.getAttribute("stName");
		 String dname = request.getParameter("dname");
		 String pname = request.getParameter("pname");
		 String mname = request.getParameter("mname");
		 String fname = request.getParameter("fname");
		 Integer projectProfileId = PEService.getProjectProfileId(pcode, fcode, mcode);
		 
		 
		 List<WdcpmksyProductionDetails> wdcPrdDtlList = new ArrayList<>();
		 wdcPrdDtlList = PEService.getProductionDetails(projectProfileId);
		 if(session!=null && session.getAttribute("loginID")!=null) {
			int regId = (int)session.getAttribute("regId");
			mav = new ModelAndView("projectEvaluation/productionDetails");
			mav.addObject("wdcPrdDtlList", wdcPrdDtlList);
			mav.addObject("wdcPrdDtlListSize", wdcPrdDtlList.size());
			mav.addObject("projProId", projectProfileId);
			mav.addObject("pcode", pcode);
			mav.addObject("fcode", fcode);
			mav.addObject("mcode", mcode);
			mav.addObject("dcode", dcode);
			mav.addObject("dname",dname);
			mav.addObject("pname", pname);
			mav.addObject("fname", fname);
			mav.addObject("mname", mname);
			mav.addObject("stName",stName);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value = "/saveOrUpdateProductionDetails", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveOrUpdateProductionDetails(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		
		try { 
			if (session != null && session.getAttribute("loginID") != null) {
				monthList = PEService.getmonthforproject();
                Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		        String distName = request.getParameter("dname");
		        Integer projid = Integer.parseInt(request.getParameter("pcode"));
		        String project = request.getParameter("pcode");
		        String projName = request.getParameter("pname");
		        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		        String mname = request.getParameter("mname");
		        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		        String fname = request.getParameter("fname");
		        String pagency = PEService.getpAgency(project);
		        
		        Integer projProfId = Integer.parseInt(request.getParameter("projProfId"));
				
		        BigDecimal preMilch = new BigDecimal(request.getParameter("preMilch"));
				BigDecimal midMilch = new BigDecimal(request.getParameter("midMilch"));
				BigDecimal cMilch = new BigDecimal(request.getParameter("cMilch"));
				String rmkMilch = request.getParameter("rmkMilch");
				
				BigDecimal preFodder = new BigDecimal(request.getParameter("preFodder"));
				BigDecimal midFodder = new BigDecimal(request.getParameter("midFodder"));
				BigDecimal cFodder = new BigDecimal(request.getParameter("cFodder"));
				String rmkFodder = request.getParameter("rmkFodder");
				
				Integer preRuralUrban = Integer.parseInt(request.getParameter("preRuralUrban"));
				Integer midRuralUrban = Integer.parseInt(request.getParameter("midRuralUrban"));
				Integer cRuralUrban = Integer.parseInt(request.getParameter("cRuralUrban"));
				String rmkRuralUrban = request.getParameter("rmkRuralUrban");
				
				Integer prespring = Integer.parseInt(request.getParameter("prespring"));
				Integer midspring = Integer.parseInt(request.getParameter("midspring"));
//				Integer cSpring = Integer.parseInt(request.getParameter("cSpring"));
				Integer cSpring = null;
				String rmkSpring = request.getParameter("rmkSpring");
				
				Integer prebenefit = Integer.parseInt(request.getParameter("prebenefit"));
				Integer midbenefit = Integer.parseInt(request.getParameter("midbenefit"));
//				Integer cBenefit = Integer.parseInt(request.getParameter("cBenefit"));
				Integer cBenefit = null;
				String rmkBenefit = request.getParameter("rmkBenefit");
				
				Integer preshg = Integer.parseInt(request.getParameter("preshg"));
				Integer midshg = Integer.parseInt(request.getParameter("midshg"));
				Integer cShg = Integer.parseInt(request.getParameter("cShg"));
				String rmkShg = request.getParameter("rmkShg");
				
				Integer prefpo = Integer.parseInt(request.getParameter("prefpo"));
				Integer midfpo = Integer.parseInt(request.getParameter("midfpo"));
				Integer cFpo = Integer.parseInt(request.getParameter("cFpo"));
				String rmkFpo = request.getParameter("rmkFpo");
				
				Integer preug = Integer.parseInt(request.getParameter("preug"));
				Integer midug = Integer.parseInt(request.getParameter("midug"));
				Integer cUg = Integer.parseInt(request.getParameter("cUg"));
				String rmkUg = request.getParameter("rmkUg");
				
				Integer preMShg = Integer.parseInt(request.getParameter("preMShg"));
				Integer midMShg = Integer.parseInt(request.getParameter("midMShg"));
				Integer cMshg = Integer.parseInt(request.getParameter("cMshg"));
				String rmkMshg = request.getParameter("rmkMshg");
				
				Integer preMFpo = Integer.parseInt(request.getParameter("preMFpo"));
				Integer midMFpo = Integer.parseInt(request.getParameter("midMFpo"));
				Integer cMfpo = Integer.parseInt(request.getParameter("cMfpo"));
				String rmkMfpo = request.getParameter("rmkMfpo");
				
				Integer preMUg = Integer.parseInt(request.getParameter("preMUg"));
				Integer midMUg = Integer.parseInt(request.getParameter("midMUg"));
				Integer cMug = Integer.parseInt(request.getParameter("cMug"));
				String rmkMug = request.getParameter("rmkMug");
				
				BigDecimal preTrunOverFpo = new BigDecimal(request.getParameter("preTrunOverFpo"));
				BigDecimal midTrunOverFpo = new BigDecimal(request.getParameter("midTrunOverFpo"));
				BigDecimal cTrunOverFpo = new BigDecimal(request.getParameter("cTrunOverFpo"));
				String rmkTrunOverFpo = request.getParameter("rmkTrunOverFpo");
				
				BigDecimal preIncomeFpo = new BigDecimal(request.getParameter("preIncomeFpo"));
				BigDecimal midIncomeFpo = new BigDecimal(request.getParameter("midIncomeFpo"));
				BigDecimal cIncomeFpo = new BigDecimal(request.getParameter("cIncomeFpo"));
				String rmkIncomeFpo = request.getParameter("rmkIncomeFpo");
				
				BigDecimal preAnnualIncomeShg = new BigDecimal(request.getParameter("preAnnualIncomeShg"));
				BigDecimal midAnnualIncomeShg = new BigDecimal(request.getParameter("midAnnualIncomeShg"));
				BigDecimal cAnnualIncomeShg = new BigDecimal(request.getParameter("cAnnualIncomeShg"));
				String rmkAnnualIncomeShg = request.getParameter("rmkAnnualIncomeShg");
				
				
							
				mav.setViewName("projectEvaluation/projectProfileMain");
				
				String projProfilestatus = PEService.checkProjectProfileStatus(project);
				
				if(projProfilestatus != null) {
					if ("1".equals(projProfilestatus)) {
			            request.setAttribute("projectProfileConfirmed", "true");
			        }
					if ("2".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			        }
					if ("3".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			        }
					if ("4".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			        }
					if ("5".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			        }
					if ("6".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			        }
					if ("7".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			        }
					if ("8".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			        }
					if ("9".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			        }
					if ("10".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			        }
					if ("11".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
			        }
					if ("12".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
			            request.setAttribute("qualityShapeFileConfirmed", "true");
			        }
					if ("13".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
			            request.setAttribute("qualityShapeFileConfirmed", "true");
			            request.setAttribute("geoTagDetailsConfirmed", "true");
			        }
					}
				
				String result = PEService.saveOrUpdateProductionDetails(request, session, projProfId, preMilch, midMilch, cMilch, rmkMilch, preFodder, midFodder, 
						cFodder, rmkFodder, preRuralUrban, midRuralUrban, cRuralUrban, rmkRuralUrban, prespring, midspring, cSpring, rmkSpring, prebenefit, midbenefit, cBenefit, rmkBenefit, 
						preshg, midshg, cShg, rmkShg, prefpo, midfpo, cFpo, rmkFpo, preug, midug, cUg, rmkUg, preMShg, midMShg, cMshg, rmkMshg, preMFpo, midMFpo, cMfpo, rmkMfpo, preMUg, midMUg, cMug, rmkMug, preTrunOverFpo, 
						midTrunOverFpo, cTrunOverFpo, rmkTrunOverFpo, preIncomeFpo, midIncomeFpo, cIncomeFpo, rmkIncomeFpo, preAnnualIncomeShg, midAnnualIncomeShg, 
						cAnnualIncomeShg, rmkAnnualIncomeShg);
				
				if ("success".equals(result)) {
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
				request.setAttribute("fundUtilizationConfirmed","true");
				request.setAttribute("croppedDetails1Confirmed", "true");
				request.setAttribute("croppedDetails2Confirmed", "true");
				request.setAttribute("manDaysDetailConfirmed", "true");

		        mav.addObject("distName", distName);
		        mav.addObject("monthList", monthList);
		        mav.addObject("projName", projName);
		        mav.addObject("monthname", mname);
		        mav.addObject("fincd", fcode);
		        mav.addObject("dcode", dcode);
		        mav.addObject("projid", projid);
		        mav.addObject("monthid", mcode);
		        mav.addObject("finyr", fname);
		        mav.addObject("pagency", pagency);
	    
		    } else {
		        if (session != null) {
		            session.invalidate();
		        }
		        mav.setViewName("login");
		        mav.addObject("login", new Login());
		    }
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
		return mav;
		
	}
	
		
	@RequestMapping(value = "/saveOrUpdateCroppedDetails2", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveOrUpdateCroppedDetails2(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		
		try { 
			if (session != null && session.getAttribute("loginID") != null) {
				monthList = PEService.getmonthforproject();
                Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		        String distName = request.getParameter("dname");
		        Integer projid = Integer.parseInt(request.getParameter("pcode"));
		        String project = request.getParameter("pcode");
		        String projName = request.getParameter("pname");
		        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		        String mname = request.getParameter("mname");
		        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		        String fname = request.getParameter("fname");
		        String pagency = PEService.getpAgency(project);
		        
		        Integer projProfId = Integer.parseInt(request.getParameter("projProfId"));
				
		        BigDecimal diversifiedcrops = new BigDecimal(request.getParameter("diversifiedcrops"));
				BigDecimal niltosingle = new BigDecimal(request.getParameter("niltosingle"));
				BigDecimal sdcrop = new BigDecimal(request.getParameter("sdcrop"));
				Integer WHSConReju = Integer.parseInt(request.getParameter("WHSConReju"));
                BigDecimal soilandmoiscrops = new BigDecimal(request.getParameter("soilandmoiscrops"));
                BigDecimal degradedrainfed = new BigDecimal(request.getParameter("degradedrainfed"));
				
				BigDecimal cdiversifiedcrops = new BigDecimal(request.getParameter("cdiversifiedcrops"));
				BigDecimal cniltosingle = new BigDecimal(request.getParameter("cniltosingle"));
				BigDecimal csdcrop = new BigDecimal(request.getParameter("csdcrop"));
				Integer cWHSConReju = Integer.parseInt(request.getParameter("cWHSConReju"));
				BigDecimal csoilandmoiscrops = new BigDecimal(request.getParameter("csoilandmoiscrops"));
                BigDecimal cdegradedrainfed = new BigDecimal(request.getParameter("cdegradedrainfed"));
                String diversifiedcropsremark = request.getParameter("diversifiedcropsremark");
                String niltosingleremark = request.getParameter("niltosingleremark");
                String sdcropremark = request.getParameter("sdcropremark");
                String WHSConRejuremark = request.getParameter("WHSConRejuremark");
                String soilandmoiscropsremark = request.getParameter("soilandmoiscropsremark");
                String degradedrainfedremark = request.getParameter("degradedrainfedremark");
				
				Integer profile_id=0;
		        profile_id=PEService.getProjectProfileId( projid, fcode, mcode);
							
				mav.setViewName("projectEvaluation/projectProfileMain");
				String projProfilestatus = PEService.checkProjectProfileStatus(project);
				if(projProfilestatus != null) {
					if ("1".equals(projProfilestatus)) {
			            request.setAttribute("projectProfileConfirmed", "true");
			        }
					if ("2".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			        }
					if ("3".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			        }
					if ("4".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			        }
					if ("5".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			        }
					if ("6".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			        }
					if ("7".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			        }
					if ("8".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			        }
					if ("9".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			        }
					if ("10".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			        }
					if ("11".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
			        }
					if ("12".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
			            request.setAttribute("qualityShapeFileConfirmed", "true");
			        }
					if ("13".equals(projProfilestatus)) {
						request.setAttribute("projectProfileConfirmed", "true");
						request.setAttribute("evaluationDetailConfirmed", "true");
			            request.setAttribute("fundUtilizationConfirmed", "true");
			            request.setAttribute("croppedDetails1Confirmed", "true");
			            request.setAttribute("croppedDetails2Confirmed", "true");
			            request.setAttribute("croppedDetails3Confirmed", "true");
			            request.setAttribute("manDaysDetailConfirmed", "true");
			            request.setAttribute("productionDetailsConfirmed", "true");
			            request.setAttribute("ecoPerspectiveConfirmed", "true");
			            request.setAttribute("equityAspectConfirmed", "true");
			            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
			            request.setAttribute("qualityShapeFileConfirmed", "true");
			            request.setAttribute("geoTagDetailsConfirmed", "true");
			        }
					}
				String result = PEService.saveOrUpdateCroppedDetails2(request, session, profile_id, projProfId, diversifiedcrops, niltosingle, sdcrop, WHSConReju, soilandmoiscrops,
						degradedrainfed, cdiversifiedcrops, cniltosingle, csdcrop, cWHSConReju, csoilandmoiscrops, cdegradedrainfed, diversifiedcropsremark,
						niltosingleremark, sdcropremark, WHSConRejuremark, soilandmoiscropsremark, degradedrainfedremark);
				
                if ("success".equals(result)) {
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				
		        mav.addObject("distName", distName);
		        mav.addObject("monthList", monthList);
		        mav.addObject("projName", projName);
		        mav.addObject("monthname", mname);
		        mav.addObject("fincd", fcode);
		        mav.addObject("dcode", dcode);
		        mav.addObject("projid", projid);
		        mav.addObject("monthid", mcode);
		        mav.addObject("finyr", fname);
		        mav.addObject("pagency", pagency);
	    
		    } else {
		        if (session != null) {
		            session.invalidate();
		        }
		        mav.setViewName("login");
		        mav.addObject("login", new Login());
		    }
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
		return mav;
		
	}
	        
	@RequestMapping(value="getviewcomplete", method=RequestMethod.GET)
	public ModelAndView getviewcomplete(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
		Integer dcode = Integer.parseInt(request.getParameter("district")); 
		Integer pcode = Integer.parseInt(request.getParameter("project")); 
		String dname = request.getParameter("distName"); 
		String pname = request.getParameter("projName");
		Integer mcode = Integer.parseInt(request.getParameter("month")); 
		Integer fcode = Integer.parseInt(request.getParameter("finyear")); 
		String mname =request.getParameter("monthName"); 
		String fname = request.getParameter("finName");
		String stName = (String) session.getAttribute("stName");
		Integer profile_id=0;
		Character dprSlna=null;
		String dprSlnaRemark=null;
		Character allManpower=null;
		String allManpowerRemark=null;
		Integer wcdc=null;
		String wcdcRemark=null;
		Integer pia=null;
		String piaRemark=null;
		Integer wc=null;
		String wcRemark=null;
		String centralShare = null;
		String rmkCentralShare = null;
		String stateShare = null;
		String rmkStateShare = null;
		String totalFund = null;
		String rmkTotalFund = null;
		String conPlannedFund = null;
		String rmkConPlannedFund = null;
		String exCon = null;
		String rmkExCon = null;
		String wdf = null;
		String rmkWdf = null;
		String areaType = null;
		String pre_farmer_income=null;
		String mid_farmer_income=null;
		String control_farmer_income=null;
		String remark_farmer_income=null;
		String farmer_benefited=null;
		String control_farmer_benefited=null;
		String remark_farmer_benefited=null;
		String mandays_generated=null;
		String control_mandays_generated=null;
		String remark_mandays_generated=null;
		String pre_dug_well=null;
		String mid_dug_well=null;
		String control_dug_well=null;
		String remark_dug_well=null;
		String pre_tube_well=null;
		String mid_tube_well=null;
		String control_tube_well=null;
		String remark_tube_well=null;
		Boolean naturalresource = null;
        String naturalresourceRemark= null;
        Boolean norm=null;
        String normRemark=null;
        Boolean antrlasset=null;
        String antrlassetRemark=null;
        Boolean controlntlresource=null;
        Boolean controlnorm=null;
        Boolean controlantrlasset=null;
        String created_work=null;
		String created_work_remark=null;
		String completed_work=null;
		String completed_work_remark=null;
		String ongoing_work=null;
		String ongoing_work_remark=null;
		String shape_file_area=null;
		String shape_file_area_remark=null;
		String variation_area=null;
		String variation_area_remark=null;
		String twork=null;
		String tworkre=null;
		Character status = null;
		String project = request.getParameter("project");
		String pagency = PEService.getpAgency(project);
		LinkedHashMap<Integer, List<ProjectEvaluationBean>> getprojectProfileData = null;
		List<ProjectEvaluationBean> indicatorslist = new ArrayList<ProjectEvaluationBean>();  
		List<ProjectEvaluationBean> utilizationlist = new ArrayList<ProjectEvaluationBean>();
		List<ProjectEvaluationBean> mandayslist = new ArrayList<ProjectEvaluationBean>();
		List<ProjectEvaluationBean> ecoperlist = new ArrayList<ProjectEvaluationBean>();
		Integer projProfId = PEService.getProjectProfileId(pcode, fcode, mcode);
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			
			 ProjectEvaluationBean projData = PEService.getProjectDetails(pcode);
			 status = projData.getStatus();
			 if(status == 'D') {
			 getprojectProfileData = PEService.fetchprojProfileData(pcode);
			 }
			 else if(status == 'C')
			 {
				 getprojectProfileData = PEService.fetchcompleteProjProfileData(pcode);
			 }
			 profile_id=PEService.getProjectProfileId( pcode, fcode, mcode);
			 indicatorslist =PEService.getIndicatorEvaluation(profile_id);
			 utilizationlist=PEService.getFundUtilization(profile_id);
			 List<WdcpmksyCroppedDetails1> wdcCrpDtlList = new ArrayList<>();
			 wdcCrpDtlList = PEService.getCroppedDetails(profile_id);
			 List<WdcpmksyCroppedDetails2> wdcCrpDtlList2 = new ArrayList<>();
			 wdcCrpDtlList2 = PEService.getCroppedDetails2(profile_id);
			 List<WdcpmksyCroppedDetails3> wdcCrpDtlList3 = PEService.getCroppedDetails3(profile_id);
			 mandayslist=PEService.getMandayDeatails(profile_id);
			 List<WdcpmksyProductionDetails> wdcPrdDtlList = new ArrayList<>();
			 wdcPrdDtlList = PEService.getProductionDetails(profile_id);
			 List<WdcpmksyEquityAspect> equityAspectList = PEService.getEquityAspect(profile_id);
			 List<ProjectEvaluationBean> targetlist = new ArrayList<ProjectEvaluationBean>();
			 List<ProjectEvaluationBean> projshapelist = new ArrayList<ProjectEvaluationBean>();
			 projshapelist=PEService.getQualityShapeFile(profile_id);
			 List<ProjectEvaluationBean> geotagginglist = new ArrayList<ProjectEvaluationBean>();
			 geotagginglist=PEService.getStatusGeotagWork(profile_id);
				
			 targetlist=PEService.getExecutionPlanWork(profile_id);
			 for(ProjectEvaluationBean bean : indicatorslist) {
					
					
				 dprSlna=bean.getDpr_slna();
				 dprSlnaRemark=bean.getDpr_slna_remark().toString();
				 allManpower=bean.getAll_manpower();
				 allManpowerRemark=bean.getAll_manpower_remark().toString();
				 wcdc=bean.getWcdc();
				 wcdcRemark=bean.getWcdc_remark().toString();
				 pia=bean.getPia();
				 piaRemark=bean.getPia_remark().toString();
				 wc=bean.getWc();
				 wcRemark=bean.getWc_remark().toString();

					 
			}
			 for(ProjectEvaluationBean bean : utilizationlist) {
				 centralShare = bean.getCentral_share().toString();
				 rmkCentralShare = bean.getCentral_share_remark();
				 stateShare = bean.getState_share().toString();
				 rmkStateShare = bean.getState_share_remark();
				 totalFund = bean.getTotal_fund().toString();
				 rmkTotalFund = bean.getTotal_fund_remark();
				 conPlannedFund = bean.getTotal_fund_planned().toString();
				 rmkConPlannedFund = bean.getTotal_fund_planned_remark();
				 exCon = bean.getTotal_expenditure().toString();
				 rmkExCon = bean.getTotal_expenditure_remark();
				 wdf = bean.getTotal_wdf().toString();
				 rmkWdf = bean.getTotal_wdf_remark();

			 }
			 
			 for(ProjectEvaluationBean bean : mandayslist) 
			 {
				 pre_farmer_income=bean.getPre_farmer_income().toString();
				 mid_farmer_income=bean.getMid_farmer_income().toString();
				 control_farmer_income=bean.getControl_farmer_income().toString();
				 remark_farmer_income=bean.getRemark_farmer_income();
				 
				 farmer_benefited=bean.getFarmer_benefited().toString();
//				 control_farmer_benefited=bean.getControl_farmer_benefited().toString();
				 remark_farmer_benefited=bean.getRemark_farmer_benefited();
				
				 mandays_generated=bean.getMandays_generated().toString();
//				 control_mandays_generated=bean.getControl_mandays_generated().toString();
				 remark_mandays_generated=bean.getRemark_mandays_generated();
				
				 pre_dug_well=bean.getPre_dug_well().toString();
				 mid_dug_well=bean.getMid_dug_well().toString();
				 control_dug_well=bean.getControl_dug_well().toString();
				 remark_dug_well=bean.getRemark_dug_well().toString();
				 
				 pre_tube_well=bean.getPre_tube_well().toString();
				 mid_tube_well=bean.getMid_tube_well().toString();
				 control_tube_well=bean.getControl_tube_well().toString();
				 remark_tube_well=bean.getRemark_tube_well().toString();

				 
			 }
			 ecoperlist=PEService.getEcoPerspective(profile_id);
			 for(ProjectEvaluationBean bean : ecoperlist) {
				 
					
					  naturalresource=bean.getNatural_resource();
					  naturalresourceRemark=bean.getNatural_resource_remark().toString();
					  norm=bean.getNorms_relating();
					  normRemark=bean.getNorms_relating_remark().toString();
					  antrlasset=bean.getAntural_asset();
					  antrlassetRemark=bean.getAntural_asset_remark().toString();
					  controlntlresource=bean.getControl_natural_resource();
					  controlnorm=bean.getControl_norms_relating();
					  controlantrlasset=bean.getControl_antural_asset();
					 
				 
			 }
			 for(ProjectEvaluationBean bean : targetlist) {
					
					
					  created_work=bean.getCreated_work().toString();
					  created_work_remark=bean.getCreated_work_remark().toString();
					  completed_work=bean.getCompleted_work().toString();
					  completed_work_remark=bean.getCompleted_work_remark().toString();
					  ongoing_work=bean.getOngoing_work().toString();
					  ongoing_work_remark=bean.getOngoing_work_remark().toString();
					 
					
				 }
			 for(ProjectEvaluationBean bean : projshapelist) {
					
					
					  shape_file_area=bean.getShape_file_area().toString();
					  shape_file_area_remark=bean.getShape_file_area_remark().toString();
					  variation_area=bean.getVariation_area().toString();
					  variation_area_remark=bean.getVariation_area_remark().toString();
					 
					
					
				 }
			 for(ProjectEvaluationBean bean : geotagginglist) {
					
					
					  twork=bean.getGeo_tagg_work().toString();
					  tworkre=bean.getGeo_tagg_work_remark().toString();
					 
					
				 }
		        WdcpmksyEquityAspect es = equityAspectList.get(0);
		        
		     mav = new ModelAndView("projectEvaluation/viewCompletePE");
			 mav.addObject("projProfId",projProfId);
			 mav.addObject("dpr",dprSlna);
			 mav.addObject("dprremark",dprSlnaRemark);
			 mav.addObject("mp", allManpower);
			 mav.addObject("mpremark", allManpowerRemark);
			 mav.addObject("wdc", wcdc);
			 mav.addObject("pi", pia);
			 mav.addObject("wc",wc);
			 mav.addObject("wdcd", wcdcRemark);
			 mav.addObject("pid", piaRemark);
			 mav.addObject("wcd",wcRemark);
			 
			 mav.addObject("projectList",getprojectProfileData);
			 mav.addObject("dname",dname);
			 mav.addObject("pname",pname);
			 mav.addObject("mname", mname);
			 mav.addObject("fname", fname);
			 mav.addObject("dcode",dcode);
			 mav.addObject("pcode",pcode);
			 mav.addObject("mcode",mcode);
			 mav.addObject("fcode",fcode);
			 mav.addObject("status", status);
			 mav.addObject("centralShare",centralShare);
			 mav.addObject("rmkCentralShare",rmkCentralShare);
			 mav.addObject("stateShare",stateShare);
			 mav.addObject("rmkStateShare", rmkStateShare);
			 mav.addObject("totalFund", totalFund);
			 mav.addObject("rmkTotalFund",rmkTotalFund);
			 mav.addObject("conPlannedFund",conPlannedFund);
			 mav.addObject("rmkConPlannedFund",rmkConPlannedFund);
			 mav.addObject("exCon", exCon);
			 mav.addObject("rmkExCon", rmkExCon);
			 mav.addObject("wdf", wdf);
			 mav.addObject("rmkWdf", rmkWdf);
			 mav.addObject("wdcCrpDtlList", wdcCrpDtlList);
			 mav.addObject("areaType", areaType);
             mav.addObject("wdcCrpDtlList2", wdcCrpDtlList2);
             mav.addObject("wdcCrpDtlList3", wdcCrpDtlList3);
             mav.addObject("pre_farmer_income",pre_farmer_income); 
			 mav.addObject("mid_farmer_income",mid_farmer_income); 
			 mav.addObject("control_farmer_income",control_farmer_income); 
			 mav.addObject("remark_farmer_income",remark_farmer_income); 
			 mav.addObject("farmer_benefited",farmer_benefited); 
//			 mav.addObject("control_farmer_benefited",control_farmer_benefited); 
			 mav.addObject("remark_farmer_benefited",remark_farmer_benefited); 
			 mav.addObject("mandays_generated",mandays_generated); 
//			 mav.addObject("control_mandays_generated",control_mandays_generated); 
			 mav.addObject("remark_mandays_generated",remark_mandays_generated);
			 
			 mav.addObject("pre_dug_well",pre_dug_well); 
			 mav.addObject("mid_dug_well",mid_dug_well); 
			 mav.addObject("control_dug_well",control_dug_well); 
			 mav.addObject("remark_dug_well",remark_dug_well); 
			 mav.addObject("pre_tube_well",pre_tube_well); 
			 mav.addObject("mid_tube_well",mid_tube_well); 
			 mav.addObject("control_tube_well",control_tube_well); 
			 mav.addObject("remark_tube_well",remark_tube_well);
			 mav.addObject("wdcPrdDtlList", wdcPrdDtlList);
			 
			 mav.addObject("ntrlresource",naturalresource);
			 mav.addObject("ntrlresourceremark",naturalresourceRemark);
			 mav.addObject("norm",norm);
			 mav.addObject("normremark",normRemark);
			 mav.addObject("ntlasset", antrlasset);
			 mav.addObject("ntlassetremark", antrlassetRemark);
			 mav.addObject("controlntlresource", controlntlresource);
			 mav.addObject("controlnorm", controlnorm);
			 mav.addObject("controlantrlasset",controlantrlasset);
			 
			 mav.addObject("pWatershedCom", es.getWaterCommittee());
			 mav.addObject("cWatershedCom", es.getControlWaterCommittee());
			 mav.addObject("rmkWatershedCom", es.getWaterCommitteeRemark());
			 mav.addObject("pFpoShgVli", es.getFpoShgVli());
			 mav.addObject("cFpoShgVli", es.getControlFpoShgVli());
			 mav.addObject("rmkFpoShgVli", es.getFpoShgVliRemark());
			 mav.addObject("pLivelihood", es.getLivelihoodOption());
			 mav.addObject("cLivelihood", es.getControlLivelihoodOption());				
			 mav.addObject("rmkLivelihood", es.getLivelihoodOptionRemark());
			 
			 mav.addObject("crw",created_work); 
			 mav.addObject("crwre",created_work_remark); 
			 mav.addObject("comw",completed_work); 
			 mav.addObject("comwre",completed_work_remark); 
			 mav.addObject("ongw",ongoing_work); 
			 mav.addObject("ongwre",ongoing_work_remark); 
			 
			 mav.addObject("sfile_area",shape_file_area); 
			 mav.addObject("sfile_areare",shape_file_area_remark); 
			 mav.addObject("variationarea",variation_area); 
			 mav.addObject("variationareare",variation_area_remark); 
			 mav.addObject("twork",twork); 
			 mav.addObject("tworkre",tworkre); 
			 mav.addObject("stName", stName);
			 mav.addObject("blockList", PEService.getProjProfileBlock(pcode));
			 mav.addObject("pagency", pagency);
			 
		}
		
		return mav;
	}


	@RequestMapping(value="/completeprojEvaldata", method = RequestMethod.POST)
	@ResponseBody
	public String completeprojEvaldata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projProfId") Integer projProfId,
			@RequestParam(value = "summary") String summary, @RequestParam(value = "grade") Character grade)
	{
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=PEService.completeprojEvaldata(projProfId, summary, grade);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	
	@RequestMapping(value = "/downloadViewCompleteFormPDF", method = RequestMethod.POST)
	public ModelAndView downloadViewCompleteFormPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		//		String state=request.getParameter("state");;
//		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
//		Integer profile_id=0;
		Integer dcode = Integer.parseInt(request.getParameter("dcode")); 
		Integer pcode = Integer.parseInt(request.getParameter("pcode")); 
		String dname = request.getParameter("dname"); 
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode")); 
		Integer fcode = Integer.parseInt(request.getParameter("fcode")); 
		String mname =request.getParameter("mname"); 
		String fname = request.getParameter("fname");
		Integer projProfId = Integer.parseInt(request.getParameter("projProfId"));
		String project = request.getParameter("pcode");
		
		String pagency = PEService.getpAgency(project);
		
		LinkedHashMap<Integer, String> block= PEService.getProjProfileBlock(pcode);
		String result = block.values().stream().collect(Collectors.joining(", "));
		
		Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		String userType = session.getAttribute("userType").toString();
		List<ProfileBean> listm=new  ArrayList<ProfileBean>();
		listm=profileService.getMapstate(regId, userType);
		String distName = "";
		String stateName = "";
		int stCode = 0;
		int distCode = 0;
		Character status = null;
		for(ProfileBean bean : listm) {
			distName =bean.getDistrictname();
			distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
			stateName = bean.getStatename();
			stCode = bean.getStatecode()==null?0:bean.getStatecode();
		}
		String projProfilestatus = PEService.checkProjectProfileStatus(pcode.toString());
			LinkedHashMap<Integer, List<ProjectEvaluationBean>> getprojectProfileData = null;
			
			getprojectProfileData = PEService.fetchprojProfileData(pcode);
			
			ProjectEvaluationBean projData = PEService.getProjectDetails(pcode);
			 status = projData.getStatus();
			 if(status == 'D') {
			 getprojectProfileData = PEService.fetchprojProfileData(pcode);
			 }
			 else if(status == 'C')
			 {
				 getprojectProfileData = PEService.fetchcompleteProjProfileData(pcode);
			 }
			projProfId=PEService.getProjectProfileId( pcode, fcode, mcode);
			
			List<ProjectEvaluationBean> indicatorslist = new ArrayList<ProjectEvaluationBean>();
			indicatorslist =PEService.getIndicatorEvaluation(projProfId);
			List<ProjectEvaluationBean> utilizationlist = new ArrayList<ProjectEvaluationBean>();
			utilizationlist =PEService.getFundUtilization(projProfId);
			List<WdcpmksyCroppedDetails1> wdcCrpDtlList = new ArrayList<>();
			 wdcCrpDtlList = PEService.getCroppedDetails(projProfId);
			 List<WdcpmksyCroppedDetails2> wdcCrpDtlList2 = new ArrayList<>();
			 wdcCrpDtlList2 = PEService.getCroppedDetails2(projProfId);
			 List<WdcpmksyCroppedDetails3> wdcCrpDtlList3 = new ArrayList<>();
			 wdcCrpDtlList3 = PEService.getCroppedDetails3(projProfId);
			List<ProjectEvaluationBean> mandayslist = new ArrayList<ProjectEvaluationBean>();
			mandayslist=PEService.getMandayDeatails(projProfId);
			 List<WdcpmksyProductionDetails> wdcPrdDtlList = new ArrayList<>();
			 wdcPrdDtlList = PEService.getProductionDetails(projProfId);
			List<ProjectEvaluationBean> ecoperlist = new ArrayList<ProjectEvaluationBean>();
			ecoperlist=PEService.getEcoPerspective(projProfId);
			List<WdcpmksyEquityAspect> equityAspectList = new ArrayList<>();
			equityAspectList=PEService.getEquityAspect(projProfId);
			List<ProjectEvaluationBean> targetlist = new ArrayList<ProjectEvaluationBean>();
			 targetlist=PEService.getExecutionPlanWork(projProfId);
			 List<ProjectEvaluationBean> projshapelist = new ArrayList<ProjectEvaluationBean>();
			 projshapelist=PEService.getQualityShapeFile(projProfId);
			 List<ProjectEvaluationBean> geotagginglist = new ArrayList<ProjectEvaluationBean>();
			 geotagginglist=PEService.getStatusGeotagWork(projProfId);

		try {

			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("viewcomplete");
			document.addCreationDate();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer=PdfWriter.getInstance(document, baos);

			document.open(); 

			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
			Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD );
			Font bf8 = new Font(FontFamily.HELVETICA, 8);
			Font bf9 = new Font(FontFamily.HELVETICA, 10.0f, Font.BOLD);
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

			PdfPTable table = null;
			document.newPage();
			Paragraph paragraph3 = null; 
			Paragraph paragraph4 = null; 
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
			 if(status == 'D') {
			paragraph3 = new Paragraph("Mid Term Project Evaluation - Draft ", f3);
			 }
			 if(status == 'C') {
			paragraph3 = new Paragraph("Mid Term Project Evaluation - View & Complete ", f3);
					 }
			paragraph4 = new Paragraph("State Name : "  + stateName + ", District Name : "  + dname + ", Project Name : "+   pname+  ", Block Name : "+ result + ",  \n Financial Year : " +  fname + ", Month Name : "  + mname + ",  Name of Project Evaluation Agency :"+ pagency, bf9);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph4.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			paragraph4.setSpacingAfter(15);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			document.add(paragraph4);
			table = new PdfPTable(6);
			table.setWidths(new int[] { 2, 8, 5, 5, 5, 5});
			table.setWidthPercentage(85);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			
			CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 6, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Profile", Element.ALIGN_CENTER, 6, 1, bf8Bold);
			
			for(Map.Entry<Integer, List<ProjectEvaluationBean>> bean: getprojectProfileData.entrySet()) {
				System.out.println("Key: " + bean.getKey() + ", Value: " + bean.getValue());
				if(bean.getKey().equals(stCode)) {
					for (ProjectEvaluationBean obj : bean.getValue()) {
						CommonFunctions.insertCell(table, "Total Sanctioned Project Area (in ha.)",Element.ALIGN_LEFT, 2, 1, bf8);
						CommonFunctions.insertCell(table, obj.getSanctioned_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "Total Sanctioned Cost of the Project (Rs. Crore)", Element.ALIGN_LEFT,2, 1, bf8);
						CommonFunctions.insertCell(table, obj.getSanctioned_cost().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "Central Share (Rs. Crore)", Element.ALIGN_LEFT, 2, 1, bf8);
						CommonFunctions.insertCell(table, obj.getCentral().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    CommonFunctions.insertCell(table, "State Share (Rs. Crore)", Element.ALIGN_LEFT, 2, 1, bf8);
					    CommonFunctions.insertCell(table, obj.getState().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    CommonFunctions.insertCell(table, "Number of Village Covered", Element.ALIGN_LEFT, 2, 1, bf8);
					    CommonFunctions.insertCell(table, obj.getNo_vc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    CommonFunctions.insertCell(table, "Number of Watershed Committees", Element.ALIGN_LEFT, 2, 1,bf8);
					    CommonFunctions.insertCell(table, obj.getNo_wc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    CommonFunctions.insertCell(table, "Number of Members in Watershed Committees",Element.ALIGN_LEFT, 2, 1, bf8);
					    CommonFunctions.insertCell(table, obj.getMember_wc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    CommonFunctions.insertCell(table, "Number of Households Covered in the Project area",Element.ALIGN_LEFT, 2, 1, bf8);
					    CommonFunctions.insertCell(table, obj.getHousehold().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					}
				}
			}
			
			CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 6, 1, bf8);
			
			CommonFunctions.insertCellHeader(table, "Indicators for Evaluation", Element.ALIGN_CENTER, 6, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Indicators", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Administrative Mechanism", Element.ALIGN_LEFT, 5, 1, bf8);
//					CommonFunctions.insertCell(table, indicatorslist.get(0).getAdmin_mechanism().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
//					CommonFunctions.insertCell(table, indicatorslist.get(0).getAdmin_mechanism_remark(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether all manpower positions at the sanctioned level as per the guidelines", Element.ALIGN_LEFT, 3, 1, bf8);
					if(indicatorslist.get(0).getDpr_slna().equals('Y')){
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_CENTER, 1, 1, bf8);
					
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_CENTER, 1, 1, bf8);
					}
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getDpr_slna_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether all manpower positions at the sanctioned level as per the guidelines", Element.ALIGN_LEFT, 3, 1, bf8);
					if(indicatorslist.get(0).getAll_manpower().equals('Y')){
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_CENTER, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_CENTER, 1, 1, bf8);
					}
					
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getAll_manpower_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "I", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "WCDC Level", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWcdc()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWcdc_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "II", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "PIA Level", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getPia()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getPia_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "III", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "WC Level", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWc()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWc_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
					 
					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);
				
					CommonFunctions.insertCellHeader(table, "Fund Utilization", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Indicators", Element.ALIGN_CENTER, 3, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Fund Utilization", Element.ALIGN_LEFT, 5, 1, bf8);
					
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Amount of sanctioned Central share received (Rs. Crores)", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getCentral_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getCentral_share_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Amount of sanctioned State share received (Rs. Crores)", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getState_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getState_share_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					
					CommonFunctions.insertCell(table, "c", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Amount of Total funds (central + state share) Utilized (Rs. Crores)", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, (utilizationlist.get(0).getTotal_fund().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_fund_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "d", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total funds planned through convergence in the project area (Rs. Crores)", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_fund_planned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_fund_planned_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "e", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total expenditure incurred through convergence (Rs. Crores)", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_expenditure().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_expenditure_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "f", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total WDF (Watershed Development Fund) collected so far (Rs. Crores)", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_wdf().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_wdf_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Cropped Details-1", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Crop Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Gross Cropped Area(ha)", Element.ALIGN_LEFT, 5, 1, bf8);
					
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under kharif crop(ha)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPregrossKharifCropArea().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMidgrossKharifCropArea().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_gross_kharif_crop_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getKharifCropremark(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under rabi crop(ha)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPregrossRabiCropArea().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMidgrossRabiCropArea().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_gross_rabi_crop_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getRabiCropremark(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "c", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under Third crop(ha)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList.get(0).getPregrossThirdCropArea().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList.get(0).getMidgrossThirdCropArea().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList.get(0).getControl_gross_third_crop_area().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList.get(0).getThirdCropremark()), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under different Crops(ha)", Element.ALIGN_LEFT, 5, 1, bf8);
					
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Cereals", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPredifferentCropCereals().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMiddifferentCropCereals().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_different_crop_cereals().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getCerealsremark(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Pulses", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPredifferentCropPulses().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMiddifferentCropPulses().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_different_crop_pulses().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPulsesremark(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "c", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Oil seed", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPredifferentCropOilSeed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMiddifferentCropOilSeed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_different_crop_oil_seed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getOilSeedremark(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "d", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Millets", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPredifferentCropMillets().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMiddifferentCropMillets().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_different_crop_millets().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMilletsremark(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "e", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Others  " +"("+ wdcCrpDtlList.get(0).getOthercrop()+")", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPredifferentCropOther().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMiddifferentCropOther().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_different_crop_other().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getOthersremark(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area of horticulture crop(ha)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPrehorticultureArea().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMidhorticultureArea().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_horticulture_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getHorticultureremark(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "4", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Net Sown Area(ha)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPrenetsownArea().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMidnetsownArea().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_netsown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getNetSownremark(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "5", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Cropping Intensity (%)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getPrecroppingIntensity().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getMidcroppingIntensity().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_cropping_intensity().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getCropIntensityremark(), Element.ALIGN_LEFT, 1, 1, bf8);


					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Cropped Details-2", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Crop Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area covered under diversified crops/ change in cropping system (Ha.)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getProjectDiversifiedChange().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControlDiversifiedChange().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getRemarkDiversifiedChange(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area brought from Nil/Single crop to double or more crop(ha.)", Element.ALIGN_LEFT, 5, 1, bf8);
					
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Nil to single crop(ha.)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getProjectNillSingle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControlNillSingle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getRemarkNillSingle(), Element.ALIGN_LEFT, 1, 1, bf8);
					

					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Single to double or more crop(ha.)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList2.get(0).getProjectSingleDoublemore().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList2.get(0).getControlSingleDoublemore().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table,  wdcCrpDtlList2.get(0).getRemarkSingleDoublemore(), Element.ALIGN_LEFT, 1, 1, bf8);
					

					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "No. of Water Harvesting Structure (WHS) constructed /rejuvenated", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getProjectWhsConstructedRejuvenated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControlWhsConstructedRejuvenated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getRemarkWhsConstructedRejuvenated(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "4", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area Covered with soil and Moisture (Ha.)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getProjectSoilMoisture().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControlSoilMoisture().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getRemarkSoilMoisture(), Element.ALIGN_LEFT, 1, 1, bf8);


					CommonFunctions.insertCell(table, "5", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area of degraded land covered /rainfed area developed (Ha.)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getProjectDegradedRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControlDegradedRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getRemarkDegradedRainfed(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Cropped Details-3", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Crop Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under plantation cover(Ha.)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getPrePlantationCover().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getMidPlantationCover().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getControlPlantationCover().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getRemarkPlantationCover(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Yield per hectare of major crops(Qt./Ha.)", Element.ALIGN_LEFT, 5, 1, bf8);
					
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Rice", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getPreRice().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getMidRice().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getControlRice().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getRemarkRice(), Element.ALIGN_LEFT, 1, 1, bf8);
					

					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Wheat", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList3.get(0).getPreWheat().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList3.get(0).getMidWheat().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList3.get(0).getControlWheat().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getRemarkWheat(), Element.ALIGN_LEFT, 1, 1, bf8);
					

					CommonFunctions.insertCell(table, "c", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Pulses", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getPrePulses().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getMidPulses().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getControlPulses().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getRemarkPulses(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "d", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Millets", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getPreMillets().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getMidMillets().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getControlMillets().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getRemarkMillets(), Element.ALIGN_LEFT, 1, 1, bf8);


					CommonFunctions.insertCell(table, "e", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Oil seeds", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getPreOilSeed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getMidOilSeed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getControlOilSeed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getRemarkOilSeed(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "f", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Other Crops "+"(" +wdcCrpDtlList3.get(0).getOthercrop()+")" , Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getPreOther().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getMidOther().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getControlOther().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getRemarkOther(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area of culturable wasteland(Ha.)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getPreCulturableWasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getMidCulturableWasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getControlCulturableWasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getRemarkCulturableWasteland(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "4", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under protective irrigation(Ha.)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getPreProtectiveIrrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getMidProtectiveIrrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getControlProtectiveIrrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList3.get(0).getRemarkProtectiveIrrigation(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);

					CommonFunctions.insertCellHeader(table, "No. of Man-days, Farmer and Water Table Details", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Indicators", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)	", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)	", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Farmer`s Average Household Income per Annum (Rs. in Lakhs)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getPre_farmer_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getMid_farmer_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_farmer_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getRemark_farmer_income(), Element.ALIGN_LEFT, 1, 1, bf8);

					

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Average depth of Water table in dug wells (mts.)- Summer Season(February - March)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getPre_dug_well().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getMid_dug_well().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_dug_well().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getRemark_dug_well(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Average depth of Water table in tube wells (mts.)- Summer Season(February - March)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getPre_tube_well().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getMid_tube_well().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_tube_well().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getRemark_tube_well(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Indicators", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
					CommonFunctions.insertCell(table, "4", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "No. of Farmers Benefited", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (mandayslist.get(0).getFarmer_benefited().toString()), Element.ALIGN_RIGHT, 2, 1, bf8);
					CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (mandayslist.get(0).getRemark_farmer_benefited()), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "5", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "No. of Persondays Generated (man-days)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getMandays_generated().toString(), Element.ALIGN_RIGHT, 2, 1, bf8);
					CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (mandayslist.get(0).getRemark_mandays_generated()), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);


					CommonFunctions.insertCellHeader(table, "Production, Spring and Community Details", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Production Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "Pre-Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Mid-Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Milk Production of Milch Cattle(Kl/Yr.)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreMilchCattle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidMilchCattle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlMilchCattle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkMilchCattle().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Fodder Production(Qt./Yr.)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcPrdDtlList.get(0).getPreFodderProduction().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcPrdDtlList.get(0).getMidFodderProduction().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlFodderProduction().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkFodderProduction(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Annual Migration from rural to urban area in project area(Nos.)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreRuralUrban().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidRuralUrban().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlRuralUrban().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkRuralUrban(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "4", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, " Avergage Annual Turnover of FPOs(Rs.)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreTrunoverFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidTrunoverFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlTrunoverFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkTrunoverFpo(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "5", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, " Average annual net income of an FPO Member(Rs.)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreIncomeFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidIncomeFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlIncomeFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkIncomeFpo(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "6", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, " Average annual net income of an SHG Member(Rs.)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreAnnualIncomeShg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidAnnualIncomeShg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlAnnualIncomeShg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkAnnualIncomeShg(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Spring Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "Pre-Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Mid-Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
					CommonFunctions.insertCell(table, "7", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "No. of springs rejuvenated(if applicable)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreSpringRejuvenated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidSpringRejuvenated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkSpringRejuvenated(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "8", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "No. of persons benefitted due to rejuvenation of springs", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPrePersonBenefitte().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidPersonBenefitte().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkPersonBenefitte(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Community Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "Pre-Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Mid-Project Status (Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					CommonFunctions.insertCell(table, "9", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, " No. of Community Based Organization", Element.ALIGN_LEFT, 5, 1, bf8);

					CommonFunctions.insertCell(table, "a", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "SHG", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreCommunityBasedShg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidCommunityBasedShg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlCommunityBasedShg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkCommunityBasedShg(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "b", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "FPO", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreCommunityBasedFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidCommunityBasedFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlCommunityBasedFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkCommunityBasedFpo(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "c", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "UG", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreCommunityBasedUg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidCommunityBasedUg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlCommunityBasedUg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkCommunityBasedUg(), Element.ALIGN_LEFT, 1, 1, bf8);

					Integer preshg= wdcPrdDtlList.get(0).getPreCommunityBasedShg();
					Integer preug= wdcPrdDtlList.get(0).getPreCommunityBasedUg();
					Integer prefpo= wdcPrdDtlList.get(0).getPreCommunityBasedFpo();
					Integer totalprecommunity= preshg+preug+prefpo;
					
					Integer midshg= wdcPrdDtlList.get(0).getMidCommunityBasedShg();
					Integer midug= wdcPrdDtlList.get(0).getMidCommunityBasedUg();
					Integer midfpo= wdcPrdDtlList.get(0).getMidCommunityBasedFpo();
					Integer totalmidcommunity= midshg+midug+midfpo;
					
					Integer shg1= wdcPrdDtlList.get(0).getControlCommunityBasedShg();
					Integer ug1= wdcPrdDtlList.get(0).getControlCommunityBasedUg();
					Integer fpo1= wdcPrdDtlList.get(0).getControlCommunityBasedFpo();
					Integer totalcommunity1= shg1+ug1+fpo1;
					
					CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total No. of Community Based Organization", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (totalprecommunity).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (totalmidcommunity).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (totalcommunity1).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);

					
					CommonFunctions.insertCell(table, "10", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	No. of Members in Community Based Organization", Element.ALIGN_LEFT, 5, 1, bf8);

					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "SHG", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreMemberBasedShg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidMemberBasedShg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlMemberBasedShg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkMemberBasedShg(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "FPO", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreMemberBasedFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidMemberBasedFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlMemberBasedFpo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkMemberBasedFpo(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "c", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "UG", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPreMemberBasedUg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMidMemberBasedUg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControlMemberBasedUg().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRemarkMemberBasedUg(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					Integer preshgm= wdcPrdDtlList.get(0).getPreMemberBasedShg();
					Integer preugm= wdcPrdDtlList.get(0).getPreMemberBasedUg();
					Integer prefpom= wdcPrdDtlList.get(0).getPreMemberBasedFpo();
					Integer totalprecommunitymember= preshgm+preugm+prefpom;
					
					Integer midshgm= wdcPrdDtlList.get(0).getMidMemberBasedShg();
					Integer midugm= wdcPrdDtlList.get(0).getMidMemberBasedUg();
					Integer midfpom= wdcPrdDtlList.get(0).getMidMemberBasedFpo();
					Integer totalmidcommunitymember= midshgm+midugm+midfpom;
					
					Integer shgm1= wdcPrdDtlList.get(0).getControlMemberBasedShg();
					Integer ugm1= wdcPrdDtlList.get(0).getControlMemberBasedUg();
					Integer fpom1= wdcPrdDtlList.get(0).getControlMemberBasedFpo();
					Integer totalcommunitym1= shgm1+ugm1+fpom1;
					
					CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Total No. of Members Community Based Organization", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (totalprecommunitymember).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (totalmidcommunitymember).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (totalcommunitym1).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);


					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Ecological Perspective", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Perspective Description", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Is there a system of auditing of status of natural resources at intervals", Element.ALIGN_LEFT, 2, 1, bf8);
					
					if(ecoperlist.get(0).getNatural_resource()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					if(ecoperlist.get(0).getControl_natural_resource()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					CommonFunctions.insertCell(table, ecoperlist.get(0).getNatural_resource_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether Gram Panchayats (GPs) and UGs enforcing the norms relating to sharing of usufructs rights", Element.ALIGN_LEFT, 2, 1, bf8);
					
					if(ecoperlist.get(0).getNorms_relating()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					if(ecoperlist.get(0).getControl_norms_relating()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					CommonFunctions.insertCell(table, ecoperlist.get(0).getNorms_relating_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether all members of GPs and UGs trained to maintain and monitor all the natural resources and assets created", Element.ALIGN_LEFT, 2, 1, bf8);
					
					if(ecoperlist.get(0).getAntural_asset()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					if(ecoperlist.get(0).getControl_antural_asset()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					CommonFunctions.insertCell(table, ecoperlist.get(0).getAntural_asset_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Equity Aspect", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Equity Aspect Description", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether landless poor and women find a place in watershed units like watershed committee", Element.ALIGN_LEFT, 2, 1, bf8);
					
					if(equityAspectList.get(0).getWaterCommittee()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
//					if(equityAspectList.get(0).getControlWaterCommittee()==true) {
//					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
//					}
//					else {
						CommonFunctions.insertCell(table, "N/A", Element.ALIGN_LEFT, 1, 1, bf8);
//					}
					CommonFunctions.insertCell(table, equityAspectList.get(0).getWaterCommitteeRemark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether landless poor and women are active member of FPO, SHG, Village level Institutions (VLIs) and various UGs", Element.ALIGN_LEFT, 2, 1, bf8);
					
					if(equityAspectList.get(0).getFpoShgVli()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					if(equityAspectList.get(0).getControlFpoShgVli()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					CommonFunctions.insertCell(table, equityAspectList.get(0).getFpoShgVliRemark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether landless and asset-less poor benefited from activities that promote alternate livelihood options", Element.ALIGN_LEFT, 2, 1, bf8);
					
					if(equityAspectList.get(0).getLivelihoodOption()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					if(equityAspectList.get(0).getControlLivelihoodOption()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					CommonFunctions.insertCell(table, equityAspectList.get(0).getLivelihoodOptionRemark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Execution of Planned Works against Targets", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 3, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total No. of Work IDs Created", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, targetlist.get(0).getCreated_work().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, targetlist.get(0).getCreated_work_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Total No. of Works Completed", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, (targetlist.get(0).getCompleted_work().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (targetlist.get(0).getCompleted_work_remark().toString()), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total No. of Works Ongoing", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, targetlist.get(0).getOngoing_work().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, targetlist.get(0).getOngoing_work_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Quality of Project Shape Files", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 3, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area of Shape File (ha)", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, projshapelist.get(0).getShape_file_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, projshapelist.get(0).getShape_file_area_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Variation of area under shape file as compared to sanctioned project area (ha)", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, (projshapelist.get(0).getVariation_area().toString()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (projshapelist.get(0).getVariation_area_remark().toString()), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 6, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Status of Geo-tagging of Works", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 3, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total No. of Works Geo-Tagged", Element.ALIGN_LEFT, 3, 1, bf8);
					CommonFunctions.insertCell(table, geotagginglist.get(0).getGeo_tagg_work().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, geotagginglist.get(0).getGeo_tagg_work_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "", Element.ALIGN_LEFT, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Summary of Evaluation:", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					for(Map.Entry<Integer, List<ProjectEvaluationBean>> bean: getprojectProfileData.entrySet()) {
						if(bean.getKey().equals(stCode)) {
							for (ProjectEvaluationBean obj : bean.getValue()) {
								CommonFunctions.insertCell(table, obj.getSummary(), Element.ALIGN_LEFT, 6, 1, bf8);
							}
						}
					}
					
					Map<String, String> gradeMapping = Map.of(
						    "E", "Excellent",
						    "G", "Very Good",
						    "S", "Satisfactory",
						    "A", "Average"
						);

					
					CommonFunctions.insertCellHeader(table, "", Element.ALIGN_LEFT, 6, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Based on your Summary Project Grades:", Element.ALIGN_CENTER, 6, 1, bf8Bold);
					for (Map.Entry<Integer, List<ProjectEvaluationBean>> bean : getprojectProfileData.entrySet()) {
					    if (bean.getKey().equals(stCode)) {
					        for (ProjectEvaluationBean obj : bean.getValue()) {
					            String gradeText = obj.getGrade() != null ? gradeMapping.getOrDefault(obj.getGrade().toString(), obj.getGrade().toString()) : "";
					            CommonFunctions.insertCell(table, gradeText, Element.ALIGN_LEFT, 6, 1, bf8);
					        }
					    }
					}



			if(indicatorslist.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 6, 1, bf8);


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
			response.setHeader("Content-Disposition", "attachment;filename=ViewComplete.pdf");
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
	@RequestMapping(value = "/updateMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateMonth(@RequestParam("projid") Integer projid, @RequestParam("monthid") Integer monthid) {
	    Map<String, String> response = new HashMap<>();
	    
	    try {
	        String res = PEService.updateProjProfileMonth(projid, monthid);

	        if ("success".equals(res)) {
	            response.put("status", "success");
	            response.put("message", "Month successfully updated!");
	            response.put("redirectUrl", "getProjectProfile?projid=" + projid); // Redirect to project profile
	        } else if ("not_found".equals(res)) {
	            response.put("status", "not_found");
	            response.put("message", "Project ID not found!");
	        } else {
	            response.put("status", "fail");
	            response.put("message", "Month update failed. Please try again.");
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        response.put("status", "error");
	        response.put("message", "An error occurred while updating the month.");
	    }

	    return response;
	}
	
	@RequestMapping(value = "/updateAgency", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateAgency(@RequestParam("projid") Integer projid, @RequestParam("updateAgencyName") String agencyName) {
	    Map<String, String> response = new HashMap<>();
	    
	    try {
	        String res = PEService.updateProjAgency(projid, agencyName);

	        if ("success".equals(res)) {
	            response.put("status", "success");
	            response.put("message", "Agency Name successfully updated!");
	            response.put("redirectUrl", "getProjectProfile?projid=" + projid); // Redirect to project profile
	        } else if ("not_found".equals(res)) {
	            response.put("status", "not_found");
	            response.put("message", "Project ID not found!");
	        } else {
	            response.put("status", "fail");
	            response.put("message", "Issue on Updating Agency Name. Please try again.");
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        response.put("status", "error");
	        response.put("message", "An error occurred while updating the Agency Name.");
	    }

	    return response;
	}
	
	@RequestMapping(value = "/croppedDetails3", method = RequestMethod.GET)
	public ModelAndView croppedDetails3(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		 Integer pcode = Integer.parseInt(request.getParameter("pcode")==null?"0":request.getParameter("pcode"));
		 Integer fcode = Integer.parseInt(request.getParameter("fcode")==null?"0":request.getParameter("fcode"));
		 Integer mcode = Integer.parseInt(request.getParameter("mcode")==null?"0":request.getParameter("mcode"));
		 Integer dcode = Integer.parseInt(request.getParameter("dcode")==null?"0":request.getParameter("dcode"));
		 
		 String areaType = null;
		 String stName = (String) session.getAttribute("stName");
		 String dname = request.getParameter("dname");
		 String pname = request.getParameter("pname");
		 String mname = request.getParameter("mname");
		 String fname = request.getParameter("fname");
		 Integer projectProfileId = PEService.getProjectProfileId(pcode, fcode, mcode);
		 List<WdcpmksyCroppedDetails3> wdcCrpDtlList = new ArrayList<>();
		 wdcCrpDtlList = PEService.getCroppedDetails3(projectProfileId);
		 if(session!=null && session.getAttribute("loginID")!=null) {
			int regId = (int)session.getAttribute("regId");
			mav = new ModelAndView("projectEvaluation/croppedDetails3");
			mav.addObject("wdcCrpDtlList", wdcCrpDtlList);
			mav.addObject("wdcCrpDtlListSize", wdcCrpDtlList.size());
			mav.addObject("projProId", projectProfileId);
			mav.addObject("areaType", areaType);
			mav.addObject("pcode", pcode);
			mav.addObject("fcode", fcode);
			mav.addObject("mcode", mcode);
			mav.addObject("dcode", dcode);
			mav.addObject("dname",dname);
			mav.addObject("pname", pname);
			mav.addObject("fname", fname);
			mav.addObject("mname", mname);
			mav.addObject("stName",stName);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value = "/saveOrUpdateCroppedDetails3", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveOrUpdateCroppedDetails3(HttpServletRequest request) {ModelAndView mav = new ModelAndView();
	String res="";
	session = request.getSession(true);
	
	try { 
		if (session != null && session.getAttribute("loginID") != null) {
			monthList = PEService.getmonthforproject();
			Integer dcode = Integer.parseInt(request.getParameter("dcode"));
	        String distName = request.getParameter("dname");
	        Integer projid = Integer.parseInt(request.getParameter("pcode"));
	        String project = request.getParameter("pcode");
	        String projName = request.getParameter("pname");
	        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
	        String mname = request.getParameter("mname");
	        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
	        String fname = request.getParameter("fname");
	        String pagency = PEService.getpAgency(project);
	        
	        Integer projProfId = Integer.parseInt(request.getParameter("projProfId"));
			
			BigDecimal prePlantationCover = new BigDecimal(request.getParameter("prePlantationCover"));
			BigDecimal preRice = new BigDecimal(request.getParameter("preRice"));
			BigDecimal preWheat = new BigDecimal(request.getParameter("preWheat"));
			BigDecimal prePulses = new BigDecimal(request.getParameter("prePulses"));
			BigDecimal preOilSeed = new BigDecimal(request.getParameter("preOilSeed"));
			BigDecimal preMillets = new BigDecimal(request.getParameter("preMillets"));
			BigDecimal preOther = request.getParameter("preOther")==""?null:new BigDecimal(request.getParameter("preOther"));
			BigDecimal preCulturableWasteland = new BigDecimal(request.getParameter("preCulturableWasteland"));
			BigDecimal preProtectiveIrrigation = new BigDecimal(request.getParameter("preProtectiveIrrigation"));
			
			BigDecimal midPlantationCover = new BigDecimal(request.getParameter("midPlantationCover"));
			BigDecimal midRice = new BigDecimal(request.getParameter("midRice"));
			BigDecimal midWheat = new BigDecimal(request.getParameter("midWheat"));
			BigDecimal midPulses = new BigDecimal(request.getParameter("midPulses"));
			BigDecimal midOilSeed = new BigDecimal(request.getParameter("midOilSeed"));
			BigDecimal midMillets = new BigDecimal(request.getParameter("midMillets"));
			BigDecimal midOther = request.getParameter("midOther")==""?null:new BigDecimal(request.getParameter("midOther"));
			BigDecimal midCulturableWasteland = new BigDecimal(request.getParameter("midCulturableWasteland"));
			BigDecimal midProtectiveIrrigation = new BigDecimal(request.getParameter("midProtectiveIrrigation"));
			
			BigDecimal controlPlantationCover = new BigDecimal(request.getParameter("controlPlantationCover"));
			BigDecimal controlRice = new BigDecimal(request.getParameter("controlRice"));
			BigDecimal controlWheat = new BigDecimal(request.getParameter("controlWheat"));
			BigDecimal controlPulses = new BigDecimal(request.getParameter("controlPulses"));
			BigDecimal controlOilSeed = new BigDecimal(request.getParameter("controlOilSeed"));
			BigDecimal controlMillets = new BigDecimal(request.getParameter("controlMillets"));
			BigDecimal controlOther = request.getParameter("controlOther")==""?null:new BigDecimal(request.getParameter("controlOther"));
			BigDecimal controlCulturableWasteland = new BigDecimal(request.getParameter("controlCulturableWasteland"));
			BigDecimal controlProtectiveIrrigation = new BigDecimal(request.getParameter("controlProtectiveIrrigation"));
			
			String remarkPlantationCover = request.getParameter("remarkPlantationCover");
			String remarkRice = request.getParameter("remarkRice");
			String remarkWheat = request.getParameter("remarkWheat");
			String remarkPulses = request.getParameter("remarkPulses");
			String remarkOilSeed = request.getParameter("remarkOilSeed");
			String remarkMillets = request.getParameter("remarkMillets");
			String remarkOther = request.getParameter("remarkOther");
			String remarkCulturableWasteland = request.getParameter("remarkCulturableWasteland");
			String remarkProtectiveIrrigation = request.getParameter("remarkProtectiveIrrigation");
			
			String othercrop = request.getParameter("othercrop");
			
			WdcpmksyCroppedDetails3 crpDtl = new WdcpmksyCroppedDetails3();
			
			crpDtl.setPrePlantationCover(prePlantationCover);
			crpDtl.setPreRice(preRice);
			crpDtl.setPreWheat(preWheat);
			crpDtl.setPrePulses(prePulses);
			crpDtl.setPreMillets(preMillets);
			crpDtl.setPreOilSeed(preOilSeed);
			crpDtl.setPreOther(preOther);
			crpDtl.setPreCulturableWasteland(preCulturableWasteland);
			crpDtl.setPreProtectiveIrrigation(preProtectiveIrrigation);	
			
			crpDtl.setMidPlantationCover(midPlantationCover);
			crpDtl.setMidRice(midRice);
			crpDtl.setMidWheat(midWheat);
			crpDtl.setMidPulses(midPulses);
			crpDtl.setMidMillets(midMillets);
			crpDtl.setMidOilSeed(midOilSeed);
			crpDtl.setMidOther(midOther);
			crpDtl.setMidCulturableWasteland(midCulturableWasteland);
			crpDtl.setMidProtectiveIrrigation(midProtectiveIrrigation);

			
			crpDtl.setControlPlantationCover(controlPlantationCover);
			crpDtl.setControlRice(controlRice);
			crpDtl.setControlWheat(controlWheat);
			crpDtl.setControlPulses(controlPulses);
			crpDtl.setControlMillets(controlMillets);
			crpDtl.setControlOilSeed(controlOilSeed);
			crpDtl.setControlOther(controlOther);
			crpDtl.setControlCulturableWasteland(controlCulturableWasteland);
			crpDtl.setControlProtectiveIrrigation(controlProtectiveIrrigation);
			
			crpDtl.setRemarkPlantationCover(remarkPlantationCover);
			crpDtl.setRemarkRice(remarkRice);
			crpDtl.setRemarkWheat(remarkWheat);
			crpDtl.setRemarkPulses(remarkPulses);
			crpDtl.setRemarkMillets(remarkMillets);
			crpDtl.setRemarkOilSeed(remarkOilSeed);
			crpDtl.setRemarkOther(remarkOther);
			crpDtl.setRemarkCulturableWasteland(remarkCulturableWasteland);
			crpDtl.setRemarkProtectiveIrrigation(remarkProtectiveIrrigation);
			crpDtl.setOthercrop(othercrop);
						
			mav.setViewName("projectEvaluation/projectProfileMain");
			String projProfilestatus = PEService.checkProjectProfileStatus(project);
			if(projProfilestatus != null) {
			if ("1".equals(projProfilestatus)) {
	            request.setAttribute("projectProfileConfirmed", "true");
	        }
			if ("2".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	        }
			if ("3".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	        }
			if ("4".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	        }
			if ("5".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	        }
			if ("6".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("croppedDetails3Confirmed", "true");
	        }
			if ("7".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("croppedDetails3Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			if ("8".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("croppedDetails3Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	        }
			if ("9".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("croppedDetails3Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			if ("10".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("croppedDetails3Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			if ("11".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("croppedDetails3Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			if ("12".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("croppedDetails3Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			if ("13".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("croppedDetails3Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	            request.setAttribute("geoTagDetailsConfirmed", "true");
	        }
			}
			String result = PEService.saveOrUpdateCroppedDetails3(request, session, projProfId, crpDtl);
			
			if ("success".equals(result)) {
	            request.setAttribute("croppedDetails3Confirmed", "true");
	        }
			
			request.setAttribute("projectProfileConfirmed", "true");
			request.setAttribute("evaluationDetailConfirmed", "true");
			request.setAttribute("fundUtilizationConfirmed","true");
			request.setAttribute("croppedDetails1Confirmed", "true");
            request.setAttribute("croppedDetails2Confirmed", "true");

	        mav.addObject("distName", distName);
	        mav.addObject("monthList", monthList);
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        mav.addObject("pagency", pagency);
    
	    } else {
	        if (session != null) {
	            session.invalidate();
	        }
	        mav.setViewName("login");
	        mav.addObject("login", new Login());
	    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
	return mav;
}
	
	@RequestMapping(value="/getCroppedDetailsReportData", method = RequestMethod.GET)
	public ModelAndView getCroppedDetailsReportData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/croppedDetailsReport");
		Map<String, List<CroppedDetailsReportBean>> map = PEService.getCroppedDetailsReportData();
		Gson gson = new Gson(); // Convert to JSON
		String jsonMap = gson.toJson(map);

		mav.addObject("mapJson",jsonMap);
		mav.addObject("mapJsonSize",jsonMap.length());
		
		return mav;
		
	}
	
	@RequestMapping(value="/getDistwiseCroppedDetailsReportData", method = RequestMethod.POST)
	public ModelAndView getDistwiseCroppedDetailsReportData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		int stCode = Integer.parseInt(request.getParameter("stcode"));    
		String state = request.getParameter("stname");
		String radioBtn = request.getParameter("radioBtn");
		String status = radioBtn.equals("pre")?"Pre Project Status(Aggregate)":radioBtn.equals("mid")?"Mid Project Status(Aggregate)":"Controlled Area";
		mav = new ModelAndView("reports/croppedDetailsReport");
		List<CroppedDetailsReportBean> distList = PEService.getDistwiseCropDetailsReportData(stCode,radioBtn);
		

		mav.addObject("distList",distList);
		mav.addObject("distListSize",distList.size());
		mav.addObject("stcode",stCode);
		mav.addObject("stname",state);
		mav.addObject("radioBtn",radioBtn);
		mav.addObject("status",status);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/downloadCroppedDetailsReportPdf", method = RequestMethod.POST)
	public ModelAndView downloadCroppedDetailsReportPdf(HttpServletRequest request, HttpServletResponse response) 
	{
		String radioBtn = request.getParameter("radioBtn");
		Map<String, List<CroppedDetailsReportBean>> map = PEService.getCroppedDetailsReportData();

		List<CroppedDetailsReportBean> list = map.get(radioBtn);
		String status = radioBtn.equals("pre")?"Pre Project Status(Aggregate)":radioBtn.equals("mid")?"Mid Project Status(Aggregate)":"Controlled Area";
		try {

			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Cropped Detail Report");
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

			paragraph3 = new Paragraph(
					"Report PE6- State-wise Mid Term Evaluation of Area Under Different Crops and Yield per Hectare of Major Crops for "+status,
					f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);

			table = new PdfPTable(14);
			table.setWidths(new int[] { 3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 });
			table.setWidthPercentage(70);

			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);

			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State", Element.ALIGN_RIGHT, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area Under Different Crops(in ha.)", Element.ALIGN_CENTER, 5, 1,
					bf8Bold);
			CommonFunctions.insertCellHeader(table, "Yield per Hectare of Major Crops", Element.ALIGN_CENTER, 6, 1,
					bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cereals ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Pulses", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Oil Seeds", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Millet", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Rice", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Wheat", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Pulses", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Millet", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Oil Seeds", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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

			Integer totproj = 0;
			BigDecimal totCropCereal = BigDecimal.ZERO;
			BigDecimal totCropPulses = BigDecimal.ZERO;
			BigDecimal totCropOilSeed = BigDecimal.ZERO;
			BigDecimal totCropMillet = BigDecimal.ZERO;
			BigDecimal totCropOther = BigDecimal.ZERO;
			BigDecimal totRice = BigDecimal.ZERO;
			BigDecimal totWheat = BigDecimal.ZERO;
			BigDecimal totPulses = BigDecimal.ZERO;
			BigDecimal totMillet = BigDecimal.ZERO;
			BigDecimal totOilSeed = BigDecimal.ZERO;
			BigDecimal totOther = BigDecimal.ZERO;
			if (list.size() != 0)
				for (int i = 0; i < list.size(); i++) {
					CommonFunctions.insertCell(table, String.valueOf(i + 1), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotproj().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getCropcereals().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getCroppulses().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getCropoilseed().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getCropmillets().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getCropother().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getRice().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getWheat().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getPulses().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getMillets().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getOilseed().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getOther().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);

					totproj = totproj + list.get(i).getTotproj();
					totCropCereal = totCropCereal.add(list.get(i).getCropcereals());
					totCropPulses = totCropPulses.add(list.get(i).getCroppulses());
					totCropOilSeed = totCropOilSeed.add(list.get(i).getCropoilseed());
					totCropMillet = totCropMillet.add(list.get(i).getCropmillets());
					totCropOther = totCropOther.add(list.get(i).getCropother());
					totRice = totRice.add(list.get(i).getRice());
					totWheat = totWheat.add(list.get(i).getWheat());
					totPulses = totPulses.add(list.get(i).getPulses());
					totMillet = totMillet.add(list.get(i).getMillets());
					totOilSeed = totOilSeed.add(list.get(i).getOilseed());
					totOther = totOther.add(list.get(i).getOther());

				}
			CommonFunctions.insertCell3(table, " Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totproj.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totCropCereal.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totCropPulses.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totCropOilSeed.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totCropMillet.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totCropOther.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totRice.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totWheat.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totPulses.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totMillet.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totOilSeed.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totOther.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

			if (list.size() == 0)
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 14, 1, bf8);

			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,
					"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "
							+ CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
					Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report PE6 - State.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}
	
	
	@RequestMapping(value = "/downloadExcelCroppedDetailsReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelCroppedDetailsReport(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String radioBtn = request.getParameter("radioBtn");
		Map<String, List<CroppedDetailsReportBean>> map = PEService.getCroppedDetailsReportData();

		List<CroppedDetailsReportBean> list = map.get(radioBtn);
		String status = radioBtn.equals("pre")?"Pre Project Status(Aggregate)":radioBtn.equals("mid")?"Mid Project Status(Aggregate)":"Controlled Area";
		  
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PE6- State");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PE6- State-wise Mid Term Evaluation of Area Under Different Crops and Yield per Hectare of Major Crops for "+status;
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 13, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,3,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,8,13); 
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
			
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total No. of Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

			cell = rowhead.createCell(3);
			cell.setCellValue("Area Under Different Crops(in ha.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i = 4; i<8; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Yield per Hectare of Major Crops");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i = 9; i<14; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			rowhead = sheet.createRow(6);
			
			for(int i = 0; i<3; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Cereals");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Pulses");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Oil Seeds");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Millet");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Other");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Rice");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Wheat");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Pulses");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Millet");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Oil Seeds");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(13);
			cell.setCellValue("Other");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<14;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 8;
	        Integer totproj = 0;
			BigDecimal totCropCereal = BigDecimal.ZERO;
			BigDecimal totCropPulses = BigDecimal.ZERO;
			BigDecimal totCropOilSeed = BigDecimal.ZERO;
			BigDecimal totCropMillet = BigDecimal.ZERO;
			BigDecimal totCropOther = BigDecimal.ZERO;
			BigDecimal totRice = BigDecimal.ZERO;
			BigDecimal totWheat = BigDecimal.ZERO;
			BigDecimal totPulses = BigDecimal.ZERO;
			BigDecimal totMillet = BigDecimal.ZERO;
			BigDecimal totOilSeed = BigDecimal.ZERO;
			BigDecimal totOther = BigDecimal.ZERO;
	        
	        for(CroppedDetailsReportBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getTotproj());
	        	row.createCell(3).setCellValue(bean.getCropcereals().doubleValue());
	        	row.createCell(4).setCellValue(bean.getCroppulses().doubleValue());
	        	row.createCell(5).setCellValue(bean.getCropoilseed().doubleValue());
	        	row.createCell(6).setCellValue(bean.getCropmillets().doubleValue());
	        	row.createCell(7).setCellValue(bean.getCropother().doubleValue());
	        	row.createCell(8).setCellValue(bean.getRice().doubleValue());
	        	row.createCell(9).setCellValue(bean.getWheat().doubleValue());
	        	row.createCell(10).setCellValue(bean.getPulses().doubleValue());
	        	row.createCell(11).setCellValue(bean.getMillets().doubleValue());
	        	row.createCell(12).setCellValue(bean.getOilseed().doubleValue());
	        	row.createCell(13).setCellValue(bean.getOther().doubleValue());
	        	
	        	totproj = totproj + bean.getTotproj();
				totCropCereal = totCropCereal.add(bean.getCropcereals());
				totCropPulses = totCropPulses.add(bean.getCroppulses());
				totCropOilSeed = totCropOilSeed.add(bean.getCropoilseed());
				totCropMillet = totCropMillet.add(bean.getCropmillets());
				totCropOther = totCropOther.add(bean.getCropother());
				totRice = totRice.add(bean.getRice());
				totWheat = totWheat.add(bean.getWheat());
				totPulses = totPulses.add(bean.getPulses());
				totMillet = totMillet.add(bean.getMillets());
				totOilSeed = totOilSeed.add(bean.getOilseed());
				totOther = totOther.add(bean.getOther());
	        	
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
			
			Row row = sheet.createRow(rowno);
	        cell = row.createCell(0);
	        cell.setCellValue("Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totproj);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(totCropCereal.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totCropPulses.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(5);
	        cell.setCellValue(totCropOilSeed.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(6);
	        cell.setCellValue(totCropMillet.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(7);
	        cell.setCellValue(totCropOther.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(8);
	        cell.setCellValue(totRice.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(9);
	        cell.setCellValue(totWheat.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(10);
	        cell.setCellValue(totPulses.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(11);
	        cell.setCellValue(totMillet.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totOilSeed.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(13);
	        cell.setCellValue(totOther.doubleValue());
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 13);
	        String fileName = "attachment; filename=Report PE6 - State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/croppedDetailsReport";
		
	}
	
	@RequestMapping(value="/getAverageAnnualIncome", method = RequestMethod.GET)
	public ModelAndView getAverageAnnualIncome(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/avrgAnnualIncmOfFpoShgRpt");
		List<ProductionDetailsBean> list = PEService.getAverageAnnualIncome();

		mav.addObject("list",list);
		mav.addObject("listsize",list.size());
		
		return mav;
		
	}
	
	@RequestMapping(value="/getCommunityBasedData", method = RequestMethod.GET)
	public ModelAndView getCommunityBasedData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/communityBasedShgFpoUgDataRpt");
		List<ProductionDetailsBean> list = PEService.getCommunityBasedData();

		mav.addObject("list",list);
		mav.addObject("listsize",list.size());
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getDistwiseAverageAnnualIncome", method = RequestMethod.GET)
	public String getDistwiseAverageAnnualIncome(HttpServletRequest request, Model model) {
		session = request.getSession(true);
		
		List<ProductionDetailsBean> list = new ArrayList<>();
		int stCode = Integer.parseInt(request.getParameter("stcode"));    
		String state = request.getParameter("stname");
		
		list =PEService.getDistwiseAverageAnnualIncome(stCode);
		
		model.addAttribute("distList", list);
		model.addAttribute("distListSize", list.size());
		model.addAttribute("stname", state);
		model.addAttribute("stcode", stCode);
		
		return "reports/avrgAnnualIncmOfFpoShgRpt";
		
	}
	
	@RequestMapping(value = "/getDistwiseCommunityBasedData", method = RequestMethod.GET)
	public String getDistwiseCommunityBasedData(HttpServletRequest request, Model model) {
		session = request.getSession(true);
		
		List<ProductionDetailsBean> list = new ArrayList<>();
		int stCode = Integer.parseInt(request.getParameter("stcode"));    
		String state = request.getParameter("stname");
		
		list =PEService.getDistwiseCommunityBasedData(stCode);
		
		model.addAttribute("distList", list);
		model.addAttribute("distListSize", list.size());
		model.addAttribute("stname", state);
		model.addAttribute("stcode", stCode);
		
		return "reports/communityBasedShgFpoUgDataRpt";
		
	}
	
	@RequestMapping(value = "/downloadExcelAvrgAnnualIncomeReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelAvrgAnnualIncomeReport(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<ProductionDetailsBean> list = PEService.getAverageAnnualIncome();
		  
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PE10 - State");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PE10 - State-wise Mid Term Evaluation of Average Annual Income FPOs, FPO and SHG members";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,3,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,6,8); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,9,11); 
	        sheet.addMergedRegion(mergedRegion);
	        
			mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
			
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total No. of Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

			cell = rowhead.createCell(3);
			cell.setCellValue("Average Annual Income of FPOs");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(4).setCellStyle(style);
			rowhead.createCell(5).setCellStyle(style);
			
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Average Annual Net Income of FPO Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(7).setCellStyle(style);
			rowhead.createCell(8).setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Average Annual Net Income of SHG Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(10).setCellStyle(style);
			rowhead.createCell(11).setCellStyle(style);
			
			rowhead = sheet.createRow(6);
			
			for(int i = 0; i<3; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			String head[] = {"Pre Project Status(Aggregate)","Mid Project Status(Aggregate)","Controlled Area"};
			int i = 3;
			while(i<=11) {
				cell = rowhead.createCell(i);
				cell.setCellValue(head[0]);  
				cell.setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue(head[1]);  
				cell.setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue(head[2]);  
				cell.setCellStyle(style);
				i++;
				
			}
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int j=0;j<12;j++)
			{
				cell =rowhead1.createCell(j);
				cell.setCellValue(j+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 8;
	        Integer totproj = 0;
			BigDecimal totprefpoturnover = BigDecimal.ZERO;
			BigDecimal totmidfpoturnover = BigDecimal.ZERO;
			BigDecimal totcontrolfpoturnover = BigDecimal.ZERO;
			BigDecimal totprefpoincome = BigDecimal.ZERO;
			BigDecimal totmidfpoincome = BigDecimal.ZERO;
			BigDecimal totcontrolfpoincome = BigDecimal.ZERO;
			BigDecimal totpreannualincomeshg = BigDecimal.ZERO;
			BigDecimal totmidannualincomeshg = BigDecimal.ZERO;
			BigDecimal totcontrolannualincomeshg = BigDecimal.ZERO;
	        
	        for(ProductionDetailsBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getTotproj());
	        	row.createCell(3).setCellValue(bean.getPrefpoturnover().doubleValue());
	        	row.createCell(4).setCellValue(bean.getMidfpoturnover().doubleValue());
	        	row.createCell(5).setCellValue(bean.getControlfpoturnover().doubleValue());
	        	row.createCell(6).setCellValue(bean.getPrefpoincome().doubleValue());
	        	row.createCell(7).setCellValue(bean.getMidfpoincome().doubleValue());
	        	row.createCell(8).setCellValue(bean.getControlfpoincome().doubleValue());
	        	row.createCell(9).setCellValue(bean.getPreannualincomeshg().doubleValue());
	        	row.createCell(10).setCellValue(bean.getMidannualincomeshg().doubleValue());
	        	row.createCell(11).setCellValue(bean.getControlannualincomeshg().doubleValue());
	        	
	        	totproj = totproj + bean.getTotproj();
	        	totprefpoturnover = totprefpoturnover.add(bean.getPrefpoturnover());
	        	totmidfpoturnover = totmidfpoturnover.add(bean.getMidfpoturnover());
	        	totcontrolfpoturnover = totcontrolfpoturnover.add(bean.getControlfpoturnover());
	        	totprefpoincome = totprefpoincome.add(bean.getPrefpoincome());
	        	totmidfpoincome = totmidfpoincome.add(bean.getMidfpoincome());
	        	totcontrolfpoincome = totcontrolfpoincome.add(bean.getControlfpoincome());
	        	totpreannualincomeshg = totpreannualincomeshg.add(bean.getPreannualincomeshg());
	        	totmidannualincomeshg = totmidannualincomeshg.add(bean.getMidannualincomeshg());
	        	totcontrolannualincomeshg = totcontrolannualincomeshg.add(bean.getControlannualincomeshg());
	        	
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
			
			Row row = sheet.createRow(rowno);
	        cell = row.createCell(0);
	        cell.setCellValue("Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totproj);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(totprefpoturnover.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totmidfpoturnover.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(5);
	        cell.setCellValue(totcontrolfpoturnover.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(6);
	        cell.setCellValue(totprefpoincome.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(7);
	        cell.setCellValue(totmidfpoincome.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(8);
	        cell.setCellValue(totcontrolfpoincome.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(9);
	        cell.setCellValue(totpreannualincomeshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(10);
	        cell.setCellValue(totmidannualincomeshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(11);
	        cell.setCellValue(totcontrolannualincomeshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
	        String fileName = "attachment; filename=Report PE10 - State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/avrgAnnualIncmOfFpoShgRpt";
		
	}
	
	
	@RequestMapping(value = "/downloadAvrgAnnualIncomeReportPdf", method = RequestMethod.POST)
	public ModelAndView downloadAvrgAnnualIncomeReportPdf(HttpServletRequest request, HttpServletResponse response) 
	{
		List<ProductionDetailsBean> list = PEService.getAverageAnnualIncome();

		try {

			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Avrage Annual Income of FPO and SHG");
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

			paragraph3 = new Paragraph(
					"Report PE10 - State-wise Mid Term Evaluation of Average Annual Income FPOs, FPO and SHG members",f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);

			table = new PdfPTable(12);
			table.setWidths(new int[] {3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});

			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);

			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Average Annual Income of FPOs", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Average Annual Net Income of FPO Members", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Average Annual Net Income of SHG Members", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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

			Integer totproj = 0;
			BigDecimal totprefpoturnover = BigDecimal.ZERO;
			BigDecimal totmidfpoturnover = BigDecimal.ZERO;
			BigDecimal totcontrolfpoturnover = BigDecimal.ZERO;
			BigDecimal totprefpoincome = BigDecimal.ZERO;
			BigDecimal totmidfpoincome = BigDecimal.ZERO;
			BigDecimal totcontrolfpoincome = BigDecimal.ZERO;
			BigDecimal totpreannualincomeshg = BigDecimal.ZERO;
			BigDecimal totmidannualincomeshg = BigDecimal.ZERO;
			BigDecimal totcontrolannualincomeshg = BigDecimal.ZERO;
			if (list.size() != 0)
				for (int i = 0; i < list.size(); i++) {
					CommonFunctions.insertCell(table, String.valueOf(i + 1), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotproj().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					
					
					CommonFunctions.insertCell(table, list.get(i).getPrefpoturnover().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidfpoturnover().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlfpoturnover().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrefpoincome().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidfpoincome().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlfpoincome().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getPreannualincomeshg().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidannualincomeshg().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlannualincomeshg().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);

					totproj = totproj + list.get(i).getTotproj();
		        	totprefpoturnover = totprefpoturnover.add(list.get(i).getPrefpoturnover());
		        	totmidfpoturnover = totmidfpoturnover.add(list.get(i).getMidfpoturnover());
		        	totcontrolfpoturnover = totcontrolfpoturnover.add(list.get(i).getControlfpoturnover());
		        	totprefpoincome = totprefpoincome.add(list.get(i).getPrefpoincome());
		        	totmidfpoincome = totmidfpoincome.add(list.get(i).getMidfpoincome());
		        	totcontrolfpoincome = totcontrolfpoincome.add(list.get(i).getControlfpoincome());
		        	totpreannualincomeshg = totpreannualincomeshg.add(list.get(i).getPreannualincomeshg());
		        	totmidannualincomeshg = totmidannualincomeshg.add(list.get(i).getMidannualincomeshg());
		        	totcontrolannualincomeshg = totcontrolannualincomeshg.add(list.get(i).getControlannualincomeshg());

				}
			CommonFunctions.insertCell3(table, " Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totproj.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprefpoturnover.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidfpoturnover.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolfpoturnover.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprefpoincome.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidfpoincome.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolfpoincome.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totpreannualincomeshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidannualincomeshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolannualincomeshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

			if (list.size() == 0)
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 12, 1, bf8);

			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,
					"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "
							+ CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
					Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report PE10 - State.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}
	
	@RequestMapping(value = "/downloadExcelDistAvrgAnnualIncomeReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistAvrgAnnualIncomeReport(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<ProductionDetailsBean> list = new ArrayList<>();
		int stCode = Integer.parseInt(request.getParameter("stcode"));    
		String state = request.getParameter("stname");
		
		list =PEService.getDistwiseAverageAnnualIncome(stCode);
		  
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PE10 - District");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PE10 - District-wise Mid Term Evaluation of Average Annual Income FPOs, FPO and SHG members of "+state;
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,3,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,6,8); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,9,11); 
	        sheet.addMergedRegion(mergedRegion);
	        
			mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
			
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total No. of Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

			cell = rowhead.createCell(3);
			cell.setCellValue("Average Annual Income of FPOs");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(4).setCellStyle(style);
			rowhead.createCell(5).setCellStyle(style);
			
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Average Annual Net Income of FPO Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(7).setCellStyle(style);
			rowhead.createCell(8).setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Average Annual Net Income of SHG Members");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(10).setCellStyle(style);
			rowhead.createCell(11).setCellStyle(style);
			
			rowhead = sheet.createRow(6);
			
			for(int i = 0; i<3; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			String head[] = {"Pre Project Status(Aggregate)","Mid Project Status(Aggregate)","Controlled Area"};
			int i = 3;
			while(i<=11) {
				cell = rowhead.createCell(i);
				cell.setCellValue(head[0]);  
				cell.setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue(head[1]);  
				cell.setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue(head[2]);  
				cell.setCellStyle(style);
				i++;
				
			}
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int j=0;j<12;j++)
			{
				cell =rowhead1.createCell(j);
				cell.setCellValue(j+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 8;
	        Integer totproj = 0;
			BigDecimal totprefpoturnover = BigDecimal.ZERO;
			BigDecimal totmidfpoturnover = BigDecimal.ZERO;
			BigDecimal totcontrolfpoturnover = BigDecimal.ZERO;
			BigDecimal totprefpoincome = BigDecimal.ZERO;
			BigDecimal totmidfpoincome = BigDecimal.ZERO;
			BigDecimal totcontrolfpoincome = BigDecimal.ZERO;
			BigDecimal totpreannualincomeshg = BigDecimal.ZERO;
			BigDecimal totmidannualincomeshg = BigDecimal.ZERO;
			BigDecimal totcontrolannualincomeshg = BigDecimal.ZERO;
	        
	        for(ProductionDetailsBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getTotproj());
	        	row.createCell(3).setCellValue(bean.getPrefpoturnover().doubleValue());
	        	row.createCell(4).setCellValue(bean.getMidfpoturnover().doubleValue());
	        	row.createCell(5).setCellValue(bean.getControlfpoturnover().doubleValue());
	        	row.createCell(6).setCellValue(bean.getPrefpoincome().doubleValue());
	        	row.createCell(7).setCellValue(bean.getMidfpoincome().doubleValue());
	        	row.createCell(8).setCellValue(bean.getControlfpoincome().doubleValue());
	        	row.createCell(9).setCellValue(bean.getPreannualincomeshg().doubleValue());
	        	row.createCell(10).setCellValue(bean.getMidannualincomeshg().doubleValue());
	        	row.createCell(11).setCellValue(bean.getControlannualincomeshg().doubleValue());
	        	
	        	totproj = totproj + bean.getTotproj();
	        	totprefpoturnover = totprefpoturnover.add(bean.getPrefpoturnover());
	        	totmidfpoturnover = totmidfpoturnover.add(bean.getMidfpoturnover());
	        	totcontrolfpoturnover = totcontrolfpoturnover.add(bean.getControlfpoturnover());
	        	totprefpoincome = totprefpoincome.add(bean.getPrefpoincome());
	        	totmidfpoincome = totmidfpoincome.add(bean.getMidfpoincome());
	        	totcontrolfpoincome = totcontrolfpoincome.add(bean.getControlfpoincome());
	        	totpreannualincomeshg = totpreannualincomeshg.add(bean.getPreannualincomeshg());
	        	totmidannualincomeshg = totmidannualincomeshg.add(bean.getMidannualincomeshg());
	        	totcontrolannualincomeshg = totcontrolannualincomeshg.add(bean.getControlannualincomeshg());
	        	
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
			
			Row row = sheet.createRow(rowno);
	        cell = row.createCell(0);
	        cell.setCellValue("Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totproj);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(totprefpoturnover.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totmidfpoturnover.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(5);
	        cell.setCellValue(totcontrolfpoturnover.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(6);
	        cell.setCellValue(totprefpoincome.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(7);
	        cell.setCellValue(totmidfpoincome.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(8);
	        cell.setCellValue(totcontrolfpoincome.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(9);
	        cell.setCellValue(totpreannualincomeshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(10);
	        cell.setCellValue(totmidannualincomeshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(11);
	        cell.setCellValue(totcontrolannualincomeshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
	        String fileName = "attachment; filename=Report PE10 - District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/avrgAnnualIncmOfFpoShgRpt";
		
	}
	
	
	@RequestMapping(value = "/downloadDistAvrgAnnualIncomePdf", method = RequestMethod.POST)
	public ModelAndView downloadDistAvrgAnnualIncomePdf(HttpServletRequest request, HttpServletResponse response) 
	{
		List<ProductionDetailsBean> list = new ArrayList<>();
		int stCode = Integer.parseInt(request.getParameter("stcode"));    
		String state = request.getParameter("stname");
		
		list =PEService.getDistwiseAverageAnnualIncome(stCode);

		try {

			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Avrage Annual Income of FPO and SHG");
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

			paragraph3 = new Paragraph(
					"Report PE10 - District-wise Mid Term Evaluation of Average Annual Income FPOs, FPO and SHG members of "+state,f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);

			table = new PdfPTable(12);
			table.setWidths(new int[] {3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});

			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);

			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Average Annual Income of FPOs", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Average Annual Net Income of FPO Members", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Average Annual Net Income of SHG Members", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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

			Integer totproj = 0;
			BigDecimal totprefpoturnover = BigDecimal.ZERO;
			BigDecimal totmidfpoturnover = BigDecimal.ZERO;
			BigDecimal totcontrolfpoturnover = BigDecimal.ZERO;
			BigDecimal totprefpoincome = BigDecimal.ZERO;
			BigDecimal totmidfpoincome = BigDecimal.ZERO;
			BigDecimal totcontrolfpoincome = BigDecimal.ZERO;
			BigDecimal totpreannualincomeshg = BigDecimal.ZERO;
			BigDecimal totmidannualincomeshg = BigDecimal.ZERO;
			BigDecimal totcontrolannualincomeshg = BigDecimal.ZERO;
			if (list.size() != 0)
				for (int i = 0; i < list.size(); i++) {
					CommonFunctions.insertCell(table, String.valueOf(i + 1), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotproj().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					
					
					CommonFunctions.insertCell(table, list.get(i).getPrefpoturnover().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidfpoturnover().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlfpoturnover().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrefpoincome().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidfpoincome().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlfpoincome().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getPreannualincomeshg().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidannualincomeshg().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlannualincomeshg().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);

					totproj = totproj + list.get(i).getTotproj();
		        	totprefpoturnover = totprefpoturnover.add(list.get(i).getPrefpoturnover());
		        	totmidfpoturnover = totmidfpoturnover.add(list.get(i).getMidfpoturnover());
		        	totcontrolfpoturnover = totcontrolfpoturnover.add(list.get(i).getControlfpoturnover());
		        	totprefpoincome = totprefpoincome.add(list.get(i).getPrefpoincome());
		        	totmidfpoincome = totmidfpoincome.add(list.get(i).getMidfpoincome());
		        	totcontrolfpoincome = totcontrolfpoincome.add(list.get(i).getControlfpoincome());
		        	totpreannualincomeshg = totpreannualincomeshg.add(list.get(i).getPreannualincomeshg());
		        	totmidannualincomeshg = totmidannualincomeshg.add(list.get(i).getMidannualincomeshg());
		        	totcontrolannualincomeshg = totcontrolannualincomeshg.add(list.get(i).getControlannualincomeshg());

				}
			CommonFunctions.insertCell3(table, " Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totproj.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprefpoturnover.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidfpoturnover.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolfpoturnover.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprefpoincome.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidfpoincome.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolfpoincome.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totpreannualincomeshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidannualincomeshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolannualincomeshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

			if (list.size() == 0)
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 12, 1, bf8);

			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,
					"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "
							+ CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
					Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report PE10 - District.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}
	
	@RequestMapping(value = "/downloadExcelComBasedShgFpoUgReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelComBasedShgFpoUgReport(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<ProductionDetailsBean> list = PEService.getCommunityBasedData();
		  
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PE11 - State");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PE11 - State-wise Mid Term Evaluation of Community Based SHG, FPO and UG";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 26, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(5,5,3,14); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,15,26); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(6,6,3,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,6,8); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,9,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,12,14); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,15,17); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,18,20); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,21,23); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,24,26); 
	        sheet.addMergedRegion(mergedRegion);
	        
			mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
			
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total No. of Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

			cell = rowhead.createCell(3);
			cell.setCellValue("Number of Community Based Organization");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =4;i<15;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(15);
			cell.setCellValue("Members in Community Based Organization");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =16;i<27;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			rowhead = sheet.createRow(6);
			for(int i = 0; i<3; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			int i = 3;
			while(i<27) {
				cell = rowhead.createCell(i);
				cell.setCellValue("SHG");  
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue("FPO");  
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue("UG");  
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue("Total");  
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
			}
			
			
			rowhead = sheet.createRow(7);
			
			for(i = 0; i<3; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			i = 3;
			while(i<27) {
				cell = rowhead.createCell(i);
				cell.setCellValue("Pre Project Status(Aggregate)");  
				cell.setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue("Mid Project Status(Aggregate)");  
				cell.setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue("Controlled Area");  
				cell.setCellStyle(style);
				i++;
			}
			
			
			Row rowhead1 = sheet.createRow(8);
			for(int j=0;j<27;j++)
			{
				cell =rowhead1.createCell(j);
				cell.setCellValue(j+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 9;
	        Integer totproj = 0;
	        BigInteger totprecommunitybasedshg = BigInteger.ZERO;
	        BigInteger totmidcommunitybasedshg = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedshg = BigInteger.ZERO;
			BigInteger totprecommunitybasedfpo = BigInteger.ZERO;
			BigInteger totmidcommunitybasedfpo = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedfpo = BigInteger.ZERO;
			BigInteger totprecommunitybasedug = BigInteger.ZERO;
			BigInteger totmidcommunitybasedug = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedug = BigInteger.ZERO;
			
			BigInteger totprecommunitybasedtot = BigInteger.ZERO;
			BigInteger totmidcommunitybasedtot = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedtot = BigInteger.ZERO;
			
			BigInteger totprememberbasedshg = BigInteger.ZERO;
			BigInteger totmidmemberbasedshg = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedshg = BigInteger.ZERO;
			BigInteger totprememberbasedfpo = BigInteger.ZERO;
			BigInteger totmidmemberbasedfpo = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedfpo = BigInteger.ZERO;
			BigInteger totprememberbasedug = BigInteger.ZERO;
			BigInteger totmidmemberbasedug = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedug = BigInteger.ZERO;
			
			BigInteger totprecmemberbasedtot = BigInteger.ZERO;
			BigInteger totmidcmemberbasedtot = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedtot = BigInteger.ZERO;
	        
	        for(ProductionDetailsBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getTotproj());
	        	row.createCell(3).setCellValue(bean.getPrecommunitybasedshg().doubleValue());
	        	row.createCell(4).setCellValue(bean.getMidcommunitybasedshg().doubleValue());
	        	row.createCell(5).setCellValue(bean.getControlcommunitybasedshg().doubleValue());
	        	row.createCell(6).setCellValue(bean.getPrecommunitybasedfpo().doubleValue());
	        	row.createCell(7).setCellValue(bean.getMidcommunitybasedfpo().doubleValue());
	        	row.createCell(8).setCellValue(bean.getControlcommunitybasedfpo().doubleValue());
	        	row.createCell(9).setCellValue(bean.getPrecommunitybasedug().doubleValue());
	        	row.createCell(10).setCellValue(bean.getMidcommunitybasedug().doubleValue());
	        	row.createCell(11).setCellValue(bean.getControlcommunitybasedug().doubleValue());
	        	
	        	row.createCell(12).setCellValue(bean.getPrecommunitybasedshg().doubleValue() + bean.getPrecommunitybasedfpo().doubleValue() + bean.getPrecommunitybasedug().doubleValue());
	        	row.createCell(13).setCellValue(bean.getMidcommunitybasedshg().doubleValue() + bean.getMidcommunitybasedfpo().doubleValue() + bean.getMidcommunitybasedug().doubleValue());
	        	row.createCell(14).setCellValue(bean.getControlcommunitybasedshg().doubleValue() + bean.getControlcommunitybasedfpo().doubleValue() + bean.getControlcommunitybasedug().doubleValue());
	        	
	        	row.createCell(15).setCellValue(bean.getPrememberbasedshg().doubleValue());
	        	row.createCell(16).setCellValue(bean.getMidmemberbasedshg().doubleValue());
	        	row.createCell(17).setCellValue(bean.getControlmemberbasedshg().doubleValue());
	        	row.createCell(18).setCellValue(bean.getPrememberbasedfpo().doubleValue());
	        	row.createCell(19).setCellValue(bean.getMidmemberbasedfpo().doubleValue());
	        	row.createCell(20).setCellValue(bean.getControlmemberbasedfpo().doubleValue());
	        	row.createCell(21).setCellValue(bean.getPrememberbasedug().doubleValue());
	        	row.createCell(22).setCellValue(bean.getMidmemberbasedug().doubleValue());
	        	row.createCell(23).setCellValue(bean.getControlmemberbasedug().doubleValue());
	        	row.createCell(24).setCellValue(bean.getPrememberbasedshg().doubleValue() + bean.getPrememberbasedfpo().doubleValue() + bean.getPrememberbasedug().doubleValue());
	        	row.createCell(25).setCellValue(bean.getMidmemberbasedshg().doubleValue() + bean.getMidmemberbasedfpo().doubleValue() + bean.getMidmemberbasedug().doubleValue());
	        	row.createCell(26).setCellValue(bean.getControlmemberbasedshg().doubleValue() + bean.getControlmemberbasedfpo().doubleValue() + bean.getControlmemberbasedug().doubleValue());
	        	
	        	totproj = totproj + bean.getTotproj();
	        	totprecommunitybasedshg = totprecommunitybasedshg.add(bean.getPrecommunitybasedshg());
	        	totmidcommunitybasedshg = totmidcommunitybasedshg.add(bean.getMidcommunitybasedshg());
	        	totcontrolcommunitybasedshg = totcontrolcommunitybasedshg.add(bean.getControlcommunitybasedshg());
	        	totprecommunitybasedfpo = totprecommunitybasedfpo.add(bean.getPrecommunitybasedfpo());
	        	totmidcommunitybasedfpo = totmidcommunitybasedfpo.add(bean.getMidcommunitybasedfpo());
	        	totcontrolcommunitybasedfpo = totcontrolcommunitybasedfpo.add(bean.getControlcommunitybasedfpo());
	        	totprecommunitybasedug = totprecommunitybasedug.add(bean.getPrecommunitybasedug());
	        	totmidcommunitybasedug = totmidcommunitybasedug.add(bean.getMidcommunitybasedug());
	        	totcontrolcommunitybasedug = totcontrolcommunitybasedug.add(bean.getControlcommunitybasedug());
	        	totprecommunitybasedtot = totprecommunitybasedtot.add(bean.getPrecommunitybasedshg().add(bean.getPrecommunitybasedfpo()).add(bean.getPrecommunitybasedug()));
	        	totmidcommunitybasedtot = totmidcommunitybasedtot.add(bean.getMidcommunitybasedshg().add(bean.getMidcommunitybasedfpo()).add(bean.getMidcommunitybasedug()));
	        	totcontrolcommunitybasedtot = totcontrolcommunitybasedtot.add(bean.getControlcommunitybasedshg().add(bean.getControlcommunitybasedfpo()).add(bean.getControlcommunitybasedug()));
	        	
	        	totprememberbasedshg = totprememberbasedshg.add(bean.getPrememberbasedshg());
	        	totmidmemberbasedshg = totmidmemberbasedshg.add(bean.getMidmemberbasedshg());
	        	totcontrolmemberbasedshg = totcontrolmemberbasedshg.add(bean.getControlmemberbasedshg());
	        	totprememberbasedfpo = totprememberbasedfpo.add(bean.getPrememberbasedfpo());
	        	totmidmemberbasedfpo = totmidmemberbasedfpo.add(bean.getMidmemberbasedfpo());
	        	totcontrolmemberbasedfpo = totcontrolmemberbasedfpo.add(bean.getControlmemberbasedfpo());
	        	totprememberbasedug = totprememberbasedug.add(bean.getPrememberbasedug());
	        	totmidmemberbasedug = totmidmemberbasedug.add(bean.getMidmemberbasedug());
	        	totcontrolmemberbasedug = totcontrolmemberbasedug.add(bean.getControlmemberbasedug());
	        	totprecmemberbasedtot = totprecmemberbasedtot.add(bean.getPrememberbasedshg().add(bean.getPrememberbasedfpo()).add(bean.getPrememberbasedug()));
	        	totmidcmemberbasedtot = totmidcmemberbasedtot.add(bean.getMidmemberbasedshg().add(bean.getMidmemberbasedfpo()).add(bean.getMidmemberbasedug()));
	        	totcontrolmemberbasedtot = totcontrolmemberbasedtot.add(bean.getControlmemberbasedshg().add(bean.getControlmemberbasedfpo()).add(bean.getControlmemberbasedug()));
	        	
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
			
			Row row = sheet.createRow(rowno);
	        cell = row.createCell(0);
	        cell.setCellValue("Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totproj);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(totprecommunitybasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totmidcommunitybasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(5);
	        cell.setCellValue(totcontrolcommunitybasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(6);
	        cell.setCellValue(totprecommunitybasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(7);
	        cell.setCellValue(totmidcommunitybasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(8);
	        cell.setCellValue(totcontrolcommunitybasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(9);
	        cell.setCellValue(totprecommunitybasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(10);
	        cell.setCellValue(totmidcommunitybasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(11);
	        cell.setCellValue(totcontrolcommunitybasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totprecommunitybasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(13);
	        cell.setCellValue(totmidcommunitybasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(14);
	        cell.setCellValue(totcontrolcommunitybasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(15);
	        cell.setCellValue(totprememberbasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(16);
	        cell.setCellValue(totmidmemberbasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(17);
	        cell.setCellValue(totcontrolmemberbasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(18);
	        cell.setCellValue(totprememberbasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(19);
	        cell.setCellValue(totmidmemberbasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(20);
	        cell.setCellValue(totcontrolmemberbasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(21);
	        cell.setCellValue(totprememberbasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(22);
	        cell.setCellValue(totmidmemberbasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(23);
	        cell.setCellValue(totcontrolmemberbasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(24);
	        cell.setCellValue(totprecmemberbasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(25);
	        cell.setCellValue(totmidcmemberbasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(26);
	        cell.setCellValue(totcontrolmemberbasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 26);
	        String fileName = "attachment; filename=Report PE11 - State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/communityBasedShgFpoUgDataRpt";
		
	}
	
	
	@RequestMapping(value = "/downloadComBasedShgFpoUgReportPdf", method = RequestMethod.POST)
	public ModelAndView downloadComBasedShgFpoUgReportPdf(HttpServletRequest request, HttpServletResponse response) 
	{
		List<ProductionDetailsBean> list = PEService.getCommunityBasedData();

		try {

			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Community Based Organization Details of FPO, SHG and UG");
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

			paragraph3 = new Paragraph(
					"Report PE11- State-wise Mid Term Evaluation of Community Based Organization Details of FPO, SHG and UG",f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);

			table = new PdfPTable(27);
			table.setWidths(new int[] {3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});

			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(4);

			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Community Based Organization", Element.ALIGN_CENTER, 12, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Members in Community Based Organization", Element.ALIGN_CENTER, 12, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "SHG", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "FPO", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "UG", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "SHG", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "FPO", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "UG", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			int i = 3;
			while(i<27) {
				CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				i++;
				CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				i++;
				CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				i++;
			}
			
			for(int j=0;j<27;j++)
			{
				Integer count = j+1;
				CommonFunctions.insertCellHeader(table, count.toString(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			Integer totproj = 0;
			BigInteger totprecommunitybasedshg = BigInteger.ZERO;
	        BigInteger totmidcommunitybasedshg = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedshg = BigInteger.ZERO;
			BigInteger totprecommunitybasedfpo = BigInteger.ZERO;
			BigInteger totmidcommunitybasedfpo = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedfpo = BigInteger.ZERO;
			BigInteger totprecommunitybasedug = BigInteger.ZERO;
			BigInteger totmidcommunitybasedug = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedug = BigInteger.ZERO;
			
			BigInteger totprecommunitybasedtot = BigInteger.ZERO;
			BigInteger totmidcommunitybasedtot = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedtot = BigInteger.ZERO;
			
			BigInteger totprememberbasedshg = BigInteger.ZERO;
			BigInteger totmidmemberbasedshg = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedshg = BigInteger.ZERO;
			BigInteger totprememberbasedfpo = BigInteger.ZERO;
			BigInteger totmidmemberbasedfpo = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedfpo = BigInteger.ZERO;
			BigInteger totprememberbasedug = BigInteger.ZERO;
			BigInteger totmidmemberbasedug = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedug = BigInteger.ZERO;
			
			BigInteger totprecmemberbasedtot = BigInteger.ZERO;
			BigInteger totmidcmemberbasedtot = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedtot = BigInteger.ZERO;
			if (list.size() != 0)
				for (i = 0; i < list.size(); i++) {
					CommonFunctions.insertCell(table, String.valueOf(i + 1), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotproj().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					
					
					CommonFunctions.insertCell(table, list.get(i).getPrecommunitybasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidcommunitybasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlcommunitybasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrecommunitybasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidcommunitybasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlcommunitybasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrecommunitybasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidcommunitybasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlcommunitybasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getPrecommunitybasedshg().add(list.get(i).getPrecommunitybasedfpo()).add(list.get(i).getPrecommunitybasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getMidcommunitybasedshg().add(list.get(i).getMidcommunitybasedfpo()).add(list.get(i).getMidcommunitybasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getControlcommunitybasedshg().add(list.get(i).getControlcommunitybasedfpo()).add(list.get(i).getControlcommunitybasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, list.get(i).getPrememberbasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidmemberbasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlmemberbasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrememberbasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidmemberbasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlmemberbasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrememberbasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidmemberbasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlmemberbasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getPrememberbasedshg().add(list.get(i).getPrememberbasedfpo()).add(list.get(i).getPrememberbasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getMidmemberbasedshg().add(list.get(i).getMidmemberbasedfpo()).add(list.get(i).getMidmemberbasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getControlmemberbasedshg().add(list.get(i).getControlmemberbasedfpo()).add(list.get(i).getControlmemberbasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);

					totproj = totproj + list.get(i).getTotproj();
					totprecommunitybasedshg = totprecommunitybasedshg.add(list.get(i).getPrecommunitybasedshg());
					totmidcommunitybasedshg = totmidcommunitybasedshg.add(list.get(i).getMidcommunitybasedshg());
		        	totcontrolcommunitybasedshg = totcontrolcommunitybasedshg.add(list.get(i).getControlcommunitybasedshg());
		        	totprecommunitybasedfpo = totprecommunitybasedfpo.add(list.get(i).getPrecommunitybasedfpo());
		        	totmidcommunitybasedfpo = totmidcommunitybasedfpo.add(list.get(i).getMidcommunitybasedfpo());
		        	totcontrolcommunitybasedfpo = totcontrolcommunitybasedfpo.add(list.get(i).getControlcommunitybasedfpo());
		        	totprecommunitybasedug = totprecommunitybasedug.add(list.get(i).getPrecommunitybasedug());
		        	totmidcommunitybasedug = totmidcommunitybasedug.add(list.get(i).getMidcommunitybasedug());
		        	totcontrolcommunitybasedug = totcontrolcommunitybasedug.add(list.get(i).getControlcommunitybasedug());
		        	totprecommunitybasedtot = totprecommunitybasedtot.add(list.get(i).getPrecommunitybasedshg().add(list.get(i).getPrecommunitybasedfpo()).add(list.get(i).getPrecommunitybasedug()));
		        	totmidcommunitybasedtot = totmidcommunitybasedtot.add(list.get(i).getMidcommunitybasedshg().add(list.get(i).getMidcommunitybasedfpo()).add(list.get(i).getMidcommunitybasedug()));
		        	totcontrolcommunitybasedtot = totcontrolcommunitybasedtot.add(list.get(i).getControlcommunitybasedshg().add(list.get(i).getControlcommunitybasedfpo()).add(list.get(i).getControlcommunitybasedug()));
		        	
		        	totprememberbasedshg = totprememberbasedshg.add(list.get(i).getPrememberbasedshg());
		        	totmidmemberbasedshg = totmidmemberbasedshg.add(list.get(i).getMidmemberbasedshg());
		        	totcontrolmemberbasedshg = totcontrolmemberbasedshg.add(list.get(i).getControlmemberbasedshg());
		        	totprememberbasedfpo = totprememberbasedfpo.add(list.get(i).getPrememberbasedfpo());
		        	totmidmemberbasedfpo = totmidmemberbasedfpo.add(list.get(i).getMidmemberbasedfpo());
		        	totcontrolmemberbasedfpo = totcontrolmemberbasedfpo.add(list.get(i).getControlmemberbasedfpo());
		        	totprememberbasedug = totprememberbasedug.add(list.get(i).getPrememberbasedug());
		        	totmidmemberbasedug = totmidmemberbasedug.add(list.get(i).getMidmemberbasedug());
		        	totcontrolmemberbasedug = totcontrolmemberbasedug.add(list.get(i).getControlmemberbasedshg());
		        	totprecmemberbasedtot = totprecmemberbasedtot.add(list.get(i).getPrememberbasedshg().add(list.get(i).getPrememberbasedfpo()).add(list.get(i).getPrememberbasedug()));
		        	totmidcmemberbasedtot = totmidcmemberbasedtot.add(list.get(i).getMidmemberbasedshg().add(list.get(i).getMidmemberbasedfpo()).add(list.get(i).getMidmemberbasedug()));
		        	totcontrolmemberbasedtot = totcontrolmemberbasedtot.add(list.get(i).getControlmemberbasedshg().add(list.get(i).getControlmemberbasedfpo()).add(list.get(i).getControlmemberbasedug()));

				}
			CommonFunctions.insertCell3(table, " Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totproj.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprecommunitybasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidcommunitybasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolcommunitybasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprecommunitybasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidcommunitybasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolcommunitybasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprecommunitybasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidcommunitybasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolcommunitybasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprecommunitybasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidcommunitybasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolcommunitybasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprememberbasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidmemberbasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolmemberbasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprememberbasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidmemberbasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolmemberbasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprememberbasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidmemberbasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolmemberbasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprecmemberbasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidcmemberbasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolmemberbasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

			if (list.size() == 0)
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 27, 1, bf8);

			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,
					"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "
							+ CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
					Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report PE11 -  State.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}
	
	
	@RequestMapping(value = "/downloadExcelDistComBasedShgFpoUgReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistComBasedShgFpoUgReport(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<ProductionDetailsBean> list = new ArrayList<>();
		int stCode = Integer.parseInt(request.getParameter("stcode"));    
		String state = request.getParameter("stname");
		list =PEService.getDistwiseCommunityBasedData(stCode);
		  
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PE11 - District");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PE11 - District-wise Mid Term Evaluation of Community Based SHG, FPO and UG of "+state;
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 26, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(5,5,3,14); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,15,26); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(6,6,3,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,6,8); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,9,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,12,14); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,15,17); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,18,20); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,21,23); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,24,26); 
	        sheet.addMergedRegion(mergedRegion);
	        
			mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
			
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total No. of Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

			cell = rowhead.createCell(3);
			cell.setCellValue("Number of Community Based Organization");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =4;i<15;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(15);
			cell.setCellValue("Members in Community Based Organization");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =16;i<27;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			rowhead = sheet.createRow(6);
			for(int i = 0; i<3; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			int i = 3;
			while(i<27) {
				cell = rowhead.createCell(i);
				cell.setCellValue("SHG");  
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue("FPO");  
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue("UG");  
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue("Total");  
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
				rowhead.createCell(i).setCellStyle(style);
				i++;
			}
			
			
			rowhead = sheet.createRow(7);
			
			for(i = 0; i<3; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			i = 3;
			while(i<27) {
				cell = rowhead.createCell(i);
				cell.setCellValue("Pre Project Status(Aggregate)");  
				cell.setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue("Mid Project Status(Aggregate)");  
				cell.setCellStyle(style);
				i++;
				cell = rowhead.createCell(i);
				cell.setCellValue("Controlled Area");  
				cell.setCellStyle(style);
				i++;
			}
			
			
			Row rowhead1 = sheet.createRow(8);
			for(int j=0;j<27;j++)
			{
				cell =rowhead1.createCell(j);
				cell.setCellValue(j+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 9;
	        Integer totproj = 0;
	        BigInteger totprecommunitybasedshg = BigInteger.ZERO;
	        BigInteger totmidcommunitybasedshg = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedshg = BigInteger.ZERO;
			BigInteger totprecommunitybasedfpo = BigInteger.ZERO;
			BigInteger totmidcommunitybasedfpo = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedfpo = BigInteger.ZERO;
			BigInteger totprecommunitybasedug = BigInteger.ZERO;
			BigInteger totmidcommunitybasedug = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedug = BigInteger.ZERO;
			
			BigInteger totprecommunitybasedtot = BigInteger.ZERO;
			BigInteger totmidcommunitybasedtot = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedtot = BigInteger.ZERO;
			
			BigInteger totprememberbasedshg = BigInteger.ZERO;
			BigInteger totmidmemberbasedshg = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedshg = BigInteger.ZERO;
			BigInteger totprememberbasedfpo = BigInteger.ZERO;
			BigInteger totmidmemberbasedfpo = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedfpo = BigInteger.ZERO;
			BigInteger totprememberbasedug = BigInteger.ZERO;
			BigInteger totmidmemberbasedug = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedug = BigInteger.ZERO;
			
			BigInteger totprecmemberbasedtot = BigInteger.ZERO;
			BigInteger totmidcmemberbasedtot = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedtot = BigInteger.ZERO;
	        
	        for(ProductionDetailsBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getTotproj());
	        	row.createCell(3).setCellValue(bean.getPrecommunitybasedshg().doubleValue());
	        	row.createCell(4).setCellValue(bean.getMidcommunitybasedshg().doubleValue());
	        	row.createCell(5).setCellValue(bean.getControlcommunitybasedshg().doubleValue());
	        	row.createCell(6).setCellValue(bean.getPrecommunitybasedfpo().doubleValue());
	        	row.createCell(7).setCellValue(bean.getMidcommunitybasedfpo().doubleValue());
	        	row.createCell(8).setCellValue(bean.getControlcommunitybasedfpo().doubleValue());
	        	row.createCell(9).setCellValue(bean.getPrecommunitybasedug().doubleValue());
	        	row.createCell(10).setCellValue(bean.getMidcommunitybasedug().doubleValue());
	        	row.createCell(11).setCellValue(bean.getControlcommunitybasedug().doubleValue());
	        	
	        	row.createCell(12).setCellValue(bean.getPrecommunitybasedshg().doubleValue() + bean.getPrecommunitybasedfpo().doubleValue() + bean.getPrecommunitybasedug().doubleValue());
	        	row.createCell(13).setCellValue(bean.getMidcommunitybasedshg().doubleValue() + bean.getMidcommunitybasedfpo().doubleValue() + bean.getMidcommunitybasedug().doubleValue());
	        	row.createCell(14).setCellValue(bean.getControlcommunitybasedshg().doubleValue() + bean.getControlcommunitybasedfpo().doubleValue() + bean.getControlcommunitybasedug().doubleValue());
	        	
	        	row.createCell(15).setCellValue(bean.getPrememberbasedshg().doubleValue());
	        	row.createCell(16).setCellValue(bean.getMidmemberbasedshg().doubleValue());
	        	row.createCell(17).setCellValue(bean.getControlmemberbasedshg().doubleValue());
	        	row.createCell(18).setCellValue(bean.getPrememberbasedfpo().doubleValue());
	        	row.createCell(19).setCellValue(bean.getMidmemberbasedfpo().doubleValue());
	        	row.createCell(20).setCellValue(bean.getControlmemberbasedfpo().doubleValue());
	        	row.createCell(21).setCellValue(bean.getPrememberbasedug().doubleValue());
	        	row.createCell(22).setCellValue(bean.getMidmemberbasedug().doubleValue());
	        	row.createCell(23).setCellValue(bean.getControlmemberbasedug().doubleValue());
	        	row.createCell(24).setCellValue(bean.getPrememberbasedshg().doubleValue() + bean.getPrememberbasedfpo().doubleValue() + bean.getPrememberbasedug().doubleValue());
	        	row.createCell(25).setCellValue(bean.getMidmemberbasedshg().doubleValue() + bean.getMidmemberbasedfpo().doubleValue() + bean.getMidmemberbasedug().doubleValue());
	        	row.createCell(26).setCellValue(bean.getControlmemberbasedshg().doubleValue() + bean.getControlmemberbasedfpo().doubleValue() + bean.getControlmemberbasedug().doubleValue());
	        	
	        	totproj = totproj + bean.getTotproj();
	        	totprecommunitybasedshg = totprecommunitybasedshg.add(bean.getPrecommunitybasedshg());
	        	totmidcommunitybasedshg = totmidcommunitybasedshg.add(bean.getMidcommunitybasedshg());
	        	totcontrolcommunitybasedshg = totcontrolcommunitybasedshg.add(bean.getControlcommunitybasedshg());
	        	totprecommunitybasedfpo = totprecommunitybasedfpo.add(bean.getPrecommunitybasedfpo());
	        	totmidcommunitybasedfpo = totmidcommunitybasedfpo.add(bean.getMidcommunitybasedfpo());
	        	totcontrolcommunitybasedfpo = totcontrolcommunitybasedfpo.add(bean.getControlcommunitybasedfpo());
	        	totprecommunitybasedug = totprecommunitybasedug.add(bean.getPrecommunitybasedug());
	        	totmidcommunitybasedug = totmidcommunitybasedug.add(bean.getMidcommunitybasedug());
	        	totcontrolcommunitybasedug = totcontrolcommunitybasedug.add(bean.getControlcommunitybasedug());
	        	totprecommunitybasedtot = totprecommunitybasedtot.add(bean.getPrecommunitybasedshg().add(bean.getPrecommunitybasedfpo()).add(bean.getPrecommunitybasedug()));
	        	totmidcommunitybasedtot = totmidcommunitybasedtot.add(bean.getMidcommunitybasedshg().add(bean.getMidcommunitybasedfpo()).add(bean.getMidcommunitybasedug()));
	        	totcontrolcommunitybasedtot = totcontrolcommunitybasedtot.add(bean.getControlcommunitybasedshg().add(bean.getControlcommunitybasedfpo()).add(bean.getControlcommunitybasedug()));
	        	
	        	totprememberbasedshg = totprememberbasedshg.add(bean.getPrememberbasedshg());
	        	totmidmemberbasedshg = totmidmemberbasedshg.add(bean.getMidmemberbasedshg());
	        	totcontrolmemberbasedshg = totcontrolmemberbasedshg.add(bean.getControlmemberbasedshg());
	        	totprememberbasedfpo = totprememberbasedfpo.add(bean.getPrememberbasedfpo());
	        	totmidmemberbasedfpo = totmidmemberbasedfpo.add(bean.getMidmemberbasedfpo());
	        	totcontrolmemberbasedfpo = totcontrolmemberbasedfpo.add(bean.getControlmemberbasedfpo());
	        	totprememberbasedug = totprememberbasedug.add(bean.getPrememberbasedug());
	        	totmidmemberbasedug = totmidmemberbasedug.add(bean.getMidmemberbasedug());
	        	totcontrolmemberbasedug = totcontrolmemberbasedug.add(bean.getControlmemberbasedug());
	        	totprecmemberbasedtot = totprecmemberbasedtot.add(bean.getPrememberbasedshg().add(bean.getPrememberbasedfpo()).add(bean.getPrememberbasedug()));
	        	totmidcmemberbasedtot = totmidcmemberbasedtot.add(bean.getMidmemberbasedshg().add(bean.getMidmemberbasedfpo()).add(bean.getMidmemberbasedug()));
	        	totcontrolmemberbasedtot = totcontrolmemberbasedtot.add(bean.getControlmemberbasedshg().add(bean.getControlmemberbasedfpo()).add(bean.getControlmemberbasedug()));
	        	
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
			
			Row row = sheet.createRow(rowno);
	        cell = row.createCell(0);
	        cell.setCellValue("Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totproj);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(totprecommunitybasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totmidcommunitybasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(5);
	        cell.setCellValue(totcontrolcommunitybasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(6);
	        cell.setCellValue(totprecommunitybasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(7);
	        cell.setCellValue(totmidcommunitybasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(8);
	        cell.setCellValue(totcontrolcommunitybasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(9);
	        cell.setCellValue(totprecommunitybasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(10);
	        cell.setCellValue(totmidcommunitybasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(11);
	        cell.setCellValue(totcontrolcommunitybasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totprecommunitybasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(13);
	        cell.setCellValue(totmidcommunitybasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(14);
	        cell.setCellValue(totcontrolcommunitybasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(15);
	        cell.setCellValue(totprememberbasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(16);
	        cell.setCellValue(totmidmemberbasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(17);
	        cell.setCellValue(totcontrolmemberbasedshg.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(18);
	        cell.setCellValue(totprememberbasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(19);
	        cell.setCellValue(totmidmemberbasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(20);
	        cell.setCellValue(totcontrolmemberbasedfpo.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(21);
	        cell.setCellValue(totprememberbasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(22);
	        cell.setCellValue(totmidmemberbasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(23);
	        cell.setCellValue(totcontrolmemberbasedug.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(24);
	        cell.setCellValue(totprecmemberbasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(25);
	        cell.setCellValue(totmidcmemberbasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(26);
	        cell.setCellValue(totcontrolmemberbasedtot.doubleValue());
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 18);
	        String fileName = "attachment; filename=Report PE11 - District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/communityBasedShgFpoUgDataRpt";
		
	}
	
	
	@RequestMapping(value = "/downloadDistComBasedShgFpoUgReportPdf", method = RequestMethod.POST)
	public ModelAndView downloadDistComBasedShgFpoUgReportPdf(HttpServletRequest request, HttpServletResponse response) 
	{
		List<ProductionDetailsBean> list = new ArrayList<>();
		int stCode = Integer.parseInt(request.getParameter("stcode"));    
		String state = request.getParameter("stname");
		list =PEService.getDistwiseCommunityBasedData(stCode);

		try {

			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Community Based Organization Details of FPO, SHG and UG");
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

			paragraph3 = new Paragraph(
					"Report PE11 - State-wise Mid Term Evaluation of Community Based Organization Details of FPO, SHG and UG of "+state,f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);

			table = new PdfPTable(27);
			table.setWidths(new int[] {3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});

			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(4);

			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Community Based Organization", Element.ALIGN_CENTER, 12, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Members in Community Based Organization", Element.ALIGN_CENTER, 12, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "SHG", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "FPO", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "UG", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "SHG", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "FPO", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "UG", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			int i = 3;
			while(i<27) {
				CommonFunctions.insertCellHeader(table, "Pre Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				i++;
				CommonFunctions.insertCellHeader(table, "Mid Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				i++;
				CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				i++;
			}
			
			for(int j=0;j<27;j++)
			{
				Integer count = j+1;
				CommonFunctions.insertCellHeader(table, count.toString(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			Integer totproj = 0;
			BigInteger totprecommunitybasedshg = BigInteger.ZERO;
	        BigInteger totmidcommunitybasedshg = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedshg = BigInteger.ZERO;
			BigInteger totprecommunitybasedfpo = BigInteger.ZERO;
			BigInteger totmidcommunitybasedfpo = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedfpo = BigInteger.ZERO;
			BigInteger totprecommunitybasedug = BigInteger.ZERO;
			BigInteger totmidcommunitybasedug = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedug = BigInteger.ZERO;
			
			BigInteger totprecommunitybasedtot = BigInteger.ZERO;
			BigInteger totmidcommunitybasedtot = BigInteger.ZERO;
			BigInteger totcontrolcommunitybasedtot = BigInteger.ZERO;
			
			BigInteger totprememberbasedshg = BigInteger.ZERO;
			BigInteger totmidmemberbasedshg = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedshg = BigInteger.ZERO;
			BigInteger totprememberbasedfpo = BigInteger.ZERO;
			BigInteger totmidmemberbasedfpo = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedfpo = BigInteger.ZERO;
			BigInteger totprememberbasedug = BigInteger.ZERO;
			BigInteger totmidmemberbasedug = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedug = BigInteger.ZERO;
			
			BigInteger totprecmemberbasedtot = BigInteger.ZERO;
			BigInteger totmidcmemberbasedtot = BigInteger.ZERO;
			BigInteger totcontrolmemberbasedtot = BigInteger.ZERO;
			if (list.size() != 0)
				for (i = 0; i < list.size(); i++) {
					CommonFunctions.insertCell(table, String.valueOf(i + 1), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotproj().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					
					
					CommonFunctions.insertCell(table, list.get(i).getPrecommunitybasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidcommunitybasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlcommunitybasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrecommunitybasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidcommunitybasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlcommunitybasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrecommunitybasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidcommunitybasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlcommunitybasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getPrecommunitybasedshg().add(list.get(i).getPrecommunitybasedfpo()).add(list.get(i).getPrecommunitybasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getMidcommunitybasedshg().add(list.get(i).getMidcommunitybasedfpo()).add(list.get(i).getMidcommunitybasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getControlcommunitybasedshg().add(list.get(i).getControlcommunitybasedfpo()).add(list.get(i).getControlcommunitybasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, list.get(i).getPrememberbasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidmemberbasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlmemberbasedshg().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrememberbasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidmemberbasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlmemberbasedfpo().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPrememberbasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMidmemberbasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getControlmemberbasedug().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getPrememberbasedshg().add(list.get(i).getPrememberbasedfpo()).add(list.get(i).getPrememberbasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getMidmemberbasedshg().add(list.get(i).getMidmemberbasedfpo()).add(list.get(i).getMidmemberbasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, (list.get(i).getControlmemberbasedshg().add(list.get(i).getControlmemberbasedfpo()).add(list.get(i).getControlmemberbasedug())).toString(), Element.ALIGN_CENTER, 1, 1, bf8);

					totproj = totproj + list.get(i).getTotproj();
					totprecommunitybasedshg = totprecommunitybasedshg.add(list.get(i).getPrecommunitybasedshg());
					totmidcommunitybasedshg = totmidcommunitybasedshg.add(list.get(i).getMidcommunitybasedshg());
		        	totcontrolcommunitybasedshg = totcontrolcommunitybasedshg.add(list.get(i).getControlcommunitybasedshg());
		        	totprecommunitybasedfpo = totprecommunitybasedfpo.add(list.get(i).getPrecommunitybasedfpo());
		        	totmidcommunitybasedfpo = totmidcommunitybasedfpo.add(list.get(i).getMidcommunitybasedfpo());
		        	totcontrolcommunitybasedfpo = totcontrolcommunitybasedfpo.add(list.get(i).getControlcommunitybasedfpo());
		        	totprecommunitybasedug = totprecommunitybasedug.add(list.get(i).getPrecommunitybasedug());
		        	totmidcommunitybasedug = totmidcommunitybasedug.add(list.get(i).getMidcommunitybasedug());
		        	totcontrolcommunitybasedug = totcontrolcommunitybasedug.add(list.get(i).getControlcommunitybasedug());
		        	totprecommunitybasedtot = totprecommunitybasedtot.add(list.get(i).getPrecommunitybasedshg().add(list.get(i).getPrecommunitybasedfpo()).add(list.get(i).getPrecommunitybasedug()));
		        	totmidcommunitybasedtot = totmidcommunitybasedtot.add(list.get(i).getMidcommunitybasedshg().add(list.get(i).getMidcommunitybasedfpo()).add(list.get(i).getMidcommunitybasedug()));
		        	totcontrolcommunitybasedtot = totcontrolcommunitybasedtot.add(list.get(i).getControlcommunitybasedshg().add(list.get(i).getControlcommunitybasedfpo()).add(list.get(i).getControlcommunitybasedug()));
		        	
		        	totprememberbasedshg = totprememberbasedshg.add(list.get(i).getPrememberbasedshg());
		        	totmidmemberbasedshg = totmidmemberbasedshg.add(list.get(i).getMidmemberbasedshg());
		        	totcontrolmemberbasedshg = totcontrolmemberbasedshg.add(list.get(i).getControlmemberbasedshg());
		        	totprememberbasedfpo = totprememberbasedfpo.add(list.get(i).getPrememberbasedfpo());
		        	totmidmemberbasedfpo = totmidmemberbasedfpo.add(list.get(i).getMidmemberbasedfpo());
		        	totcontrolmemberbasedfpo = totcontrolmemberbasedfpo.add(list.get(i).getControlmemberbasedfpo());
		        	totprememberbasedug = totprememberbasedug.add(list.get(i).getPrememberbasedug());
		        	totmidmemberbasedug = totmidmemberbasedug.add(list.get(i).getMidmemberbasedug());
		        	totcontrolmemberbasedug = totcontrolmemberbasedug.add(list.get(i).getControlmemberbasedshg());
		        	totprecmemberbasedtot = totprecmemberbasedtot.add(list.get(i).getPrememberbasedshg().add(list.get(i).getPrememberbasedfpo()).add(list.get(i).getPrememberbasedug()));
		        	totmidcmemberbasedtot = totmidcmemberbasedtot.add(list.get(i).getMidmemberbasedshg().add(list.get(i).getMidmemberbasedfpo()).add(list.get(i).getMidmemberbasedug()));
		        	totcontrolmemberbasedtot = totcontrolmemberbasedtot.add(list.get(i).getControlmemberbasedshg().add(list.get(i).getControlmemberbasedfpo()).add(list.get(i).getControlmemberbasedug()));

				}
			CommonFunctions.insertCell3(table, " Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totproj.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprecommunitybasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidcommunitybasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolcommunitybasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprecommunitybasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidcommunitybasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolcommunitybasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprecommunitybasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidcommunitybasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolcommunitybasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprecommunitybasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidcommunitybasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolcommunitybasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprememberbasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidmemberbasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolmemberbasedshg.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprememberbasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidmemberbasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolmemberbasedfpo.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprememberbasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidmemberbasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolmemberbasedug.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totprecmemberbasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totmidcmemberbasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totcontrolmemberbasedtot.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

			if (list.size() == 0)
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 27, 1, bf8);

			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,
					"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "
							+ CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
					Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report PE11 -  District.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}
	
	
	@RequestMapping(value = "/downloadDistwiseCroppedDetailsReportPdf", method = RequestMethod.POST)
	public ModelAndView downloadDistwiseCroppedDetailsReportPdf(HttpServletRequest request, HttpServletResponse response) 
	{
		String radioBtn = request.getParameter("radioBtn");
		int stCode = Integer.parseInt(request.getParameter("stcode"));    
		String state = request.getParameter("stname");
		List<CroppedDetailsReportBean> list = PEService.getDistwiseCropDetailsReportData(stCode,radioBtn);
		String status = radioBtn.equals("pre")?"Pre Project Status(Aggregate)":radioBtn.equals("mid")?"Mid Project Status(Aggregate)":"Controlled Area";
		try {

			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Cropped Detail Report");
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

			paragraph3 = new Paragraph(
					"Report PE6 - District-wise Mid Term Evaluation of Area Under Different Crops and Yield per Hectare of Major Crops for "+status+" of "+state,
					f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);

			table = new PdfPTable(14);
			table.setWidths(new int[] { 3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 });
			table.setWidthPercentage(70);

			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);

			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_RIGHT, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area Under Different Crops(in ha.)", Element.ALIGN_CENTER, 5, 1,
					bf8Bold);
			CommonFunctions.insertCellHeader(table, "Yield per Hectare of Major Crops", Element.ALIGN_CENTER, 6, 1,
					bf8Bold);
			CommonFunctions.insertCellHeader(table, "Cereals ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Pulses", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Oil Seeds", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Millet", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Rice", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Wheat", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Pulses", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Millet", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Oil Seeds", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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

			Integer totproj = 0;
			BigDecimal totCropCereal = BigDecimal.ZERO;
			BigDecimal totCropPulses = BigDecimal.ZERO;
			BigDecimal totCropOilSeed = BigDecimal.ZERO;
			BigDecimal totCropMillet = BigDecimal.ZERO;
			BigDecimal totCropOther = BigDecimal.ZERO;
			BigDecimal totRice = BigDecimal.ZERO;
			BigDecimal totWheat = BigDecimal.ZERO;
			BigDecimal totPulses = BigDecimal.ZERO;
			BigDecimal totMillet = BigDecimal.ZERO;
			BigDecimal totOilSeed = BigDecimal.ZERO;
			BigDecimal totOther = BigDecimal.ZERO;
			if (list.size() != 0)
				for (int i = 0; i < list.size(); i++) {
					CommonFunctions.insertCell(table, String.valueOf(i + 1), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotproj().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getCropcereals().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getCroppulses().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getCropoilseed().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getCropmillets().toString(), Element.ALIGN_CENTER, 1,
							1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getCropother().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getRice().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getWheat().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getPulses().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getMillets().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getOilseed().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);
					CommonFunctions.insertCell(table, list.get(i).getOther().toString(), Element.ALIGN_CENTER, 1, 1,
							bf8);

					totproj = totproj + list.get(i).getTotproj();
					totCropCereal = totCropCereal.add(list.get(i).getCropcereals());
					totCropPulses = totCropPulses.add(list.get(i).getCroppulses());
					totCropOilSeed = totCropOilSeed.add(list.get(i).getCropoilseed());
					totCropMillet = totCropMillet.add(list.get(i).getCropmillets());
					totCropOther = totCropOther.add(list.get(i).getCropother());
					totRice = totRice.add(list.get(i).getRice());
					totWheat = totWheat.add(list.get(i).getWheat());
					totPulses = totPulses.add(list.get(i).getPulses());
					totMillet = totMillet.add(list.get(i).getMillets());
					totOilSeed = totOilSeed.add(list.get(i).getOilseed());
					totOther = totOther.add(list.get(i).getOther());

				}
			CommonFunctions.insertCell3(table, " Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totproj.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totCropCereal.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totCropPulses.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totCropOilSeed.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totCropMillet.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totCropOther.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totRice.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totWheat.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totPulses.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totMillet.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totOilSeed.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, totOther.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

			if (list.size() == 0)
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 14, 1, bf8);

			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,
					"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "
							+ CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
					Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report - PE6 District.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}
	
	
	@RequestMapping(value = "/downloadDistwiseExcelCroppedDetailsReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadDistwiseExcelCroppedDetailsReport(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String radioBtn = request.getParameter("radioBtn");
		int stCode = Integer.parseInt(request.getParameter("stcode"));    
		String state = request.getParameter("stname");
		List<CroppedDetailsReportBean> list = PEService.getDistwiseCropDetailsReportData(stCode,radioBtn);
		String status = radioBtn.equals("pre")?"Pre Project Status(Aggregate)":radioBtn.equals("mid")?"Mid Project Status(Aggregate)":"Controlled Area";
		  
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PE6- District");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PE6- District-wise Mid Term Evaluation of Area Under Different Crops and Yield per Hectare of Major Crops for "+status+" of "+state;
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 13, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,3,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,8,13); 
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
			
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total No. of Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

			cell = rowhead.createCell(3);
			cell.setCellValue("Area Under Different Crops(in ha.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i = 4; i<8; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Yield per Hectare of Major Crops");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i = 9; i<14; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			rowhead = sheet.createRow(6);
			
			for(int i = 0; i<3; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Cereals");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Pulses");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Oil Seeds");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Millet");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Other");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Rice");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Wheat");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Pulses");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Millet");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Oil Seeds");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(13);
			cell.setCellValue("Other");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<14;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 8;
	        Integer totproj = 0;
			BigDecimal totCropCereal = BigDecimal.ZERO;
			BigDecimal totCropPulses = BigDecimal.ZERO;
			BigDecimal totCropOilSeed = BigDecimal.ZERO;
			BigDecimal totCropMillet = BigDecimal.ZERO;
			BigDecimal totCropOther = BigDecimal.ZERO;
			BigDecimal totRice = BigDecimal.ZERO;
			BigDecimal totWheat = BigDecimal.ZERO;
			BigDecimal totPulses = BigDecimal.ZERO;
			BigDecimal totMillet = BigDecimal.ZERO;
			BigDecimal totOilSeed = BigDecimal.ZERO;
			BigDecimal totOther = BigDecimal.ZERO;
	        
	        for(CroppedDetailsReportBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getTotproj());
	        	row.createCell(3).setCellValue(bean.getCropcereals().doubleValue());
	        	row.createCell(4).setCellValue(bean.getCroppulses().doubleValue());
	        	row.createCell(5).setCellValue(bean.getCropoilseed().doubleValue());
	        	row.createCell(6).setCellValue(bean.getCropmillets().doubleValue());
	        	row.createCell(7).setCellValue(bean.getCropother().doubleValue());
	        	row.createCell(8).setCellValue(bean.getRice().doubleValue());
	        	row.createCell(9).setCellValue(bean.getWheat().doubleValue());
	        	row.createCell(10).setCellValue(bean.getPulses().doubleValue());
	        	row.createCell(11).setCellValue(bean.getMillets().doubleValue());
	        	row.createCell(12).setCellValue(bean.getOilseed().doubleValue());
	        	row.createCell(13).setCellValue(bean.getOther().doubleValue());
	        	
	        	totproj = totproj + bean.getTotproj();
				totCropCereal = totCropCereal.add(bean.getCropcereals());
				totCropPulses = totCropPulses.add(bean.getCroppulses());
				totCropOilSeed = totCropOilSeed.add(bean.getCropoilseed());
				totCropMillet = totCropMillet.add(bean.getCropmillets());
				totCropOther = totCropOther.add(bean.getCropother());
				totRice = totRice.add(bean.getRice());
				totWheat = totWheat.add(bean.getWheat());
				totPulses = totPulses.add(bean.getPulses());
				totMillet = totMillet.add(bean.getMillets());
				totOilSeed = totOilSeed.add(bean.getOilseed());
				totOther = totOther.add(bean.getOther());
	        	
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
			
			Row row = sheet.createRow(rowno);
	        cell = row.createCell(0);
	        cell.setCellValue("Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totproj);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(totCropCereal.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totCropPulses.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(5);
	        cell.setCellValue(totCropOilSeed.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(6);
	        cell.setCellValue(totCropMillet.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(7);
	        cell.setCellValue(totCropOther.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(8);
	        cell.setCellValue(totRice.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(9);
	        cell.setCellValue(totWheat.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(10);
	        cell.setCellValue(totPulses.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(11);
	        cell.setCellValue(totMillet.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(12);
	        cell.setCellValue(totOilSeed.doubleValue());
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(13);
	        cell.setCellValue(totOther.doubleValue());
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 13);
	        String fileName = "attachment; filename=Report PE6- Disrict.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/croppedDetailsReport";
		
	}
	
}

