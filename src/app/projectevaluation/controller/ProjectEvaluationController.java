package app.projectevaluation.controller;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import app.bean.ProfileBean;
import app.common.CommonFunctions;
import app.projectevaluation.service.ProjectEvaluationService;
import app.projectevaluation.bean.ProjectEvaluationBean;
import app.service.DistrictMasterService;
import app.service.PhysicalActionPlanService;
import app.service.ProfileService;
import app.projectevaluation.model.WdcpmksyCroppedDetails1;
import app.projectevaluation.model.WdcpmksyCroppedDetails2;
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
			mav.addObject("districtList", districtMasterService.getDistrictByStateCodeWithDcode(stcode));
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
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String finName= request.getParameter("finName");
		String monthName= request.getParameter("monthName");
		String finid = request.getParameter("finyear");
		String monthid = request.getParameter("month");
		
		/*
		 * List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
		 * list = PEService.monthYear(); for(ProjectEvaluationBean bean : list) { data =
		 * new String[4]; data[0] = bean.getMonthid().toString(); data[1] =
		 * bean.getMonthname(); data[2] = bean.getFin_cd().toString(); data[3] =
		 * bean.getFin_yr(); }
		 */
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
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
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			if ("7".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	        }
			if ("8".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			if ("9".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			if ("10".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			if ("11".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			if ("12".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
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
			mav.addObject("distName",distName);
			mav.addObject("projName",projName);
			mav.addObject("dcode",district);
			mav.addObject("projid",project);
			mav.addObject("monthid", monthid);
			mav.addObject("monthname", monthName);
			mav.addObject("fincd", finid);
			mav.addObject("finyr", finName);
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
		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		Integer pcode = Integer.parseInt(request.getParameter("pcode"));
		String dname = request.getParameter("dname");
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		String mname = request.getParameter("mname");
		String fname = request.getParameter("fname");
		
		Integer profile_id=0;
		 String admiMechanism=null;
		 String admiMechanismRemark=null;
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
					
				 admiMechanism=bean.getAdmin_mechanism().toString();
				 admiMechanismRemark=bean.getAdmin_mechanism_remark().toString();
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
			mav.addObject("dcode",dcode);
			mav.addObject("pcode",pcode);
			mav.addObject("distName",dname);
			mav.addObject("projName",pname);
			mav.addObject("mcode", mcode);
			mav.addObject("month", mname);
			mav.addObject("fcode", fcode);
			mav.addObject("finyear", fname);
			mav.addObject("projProfId",projProfId);
			
			mav.addObject("am",admiMechanism);
			mav.addObject("amd",admiMechanismRemark);
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
	        String admiMechanism= request.getParameter("am");
	        String admiMechanismRemark=request.getParameter("amd");
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

	     try {
	    if (session != null && session.getAttribute("loginID") != null) {
	       
	    	mav.setViewName("projectEvaluation/projectProfileMain");
	        	Integer profile_id=0;
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
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
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
	                wcdc, wc, pia, admiMechanism, admiMechanismRemark, dprSlna,
	                dprSlnaRemark, allManpower, allManpowerRemark, wcdcRemark,
	                piaRemark, wcRemark, session);
	            
	            		
	            		if ("success".equals(result)) {
	            			
	        	            request.setAttribute("evaluationDetailConfirmed", "true");
	        	        }

	        	        mav.addObject("distName", distName);
	        	        mav.addObject("projName", projName);
	        	        mav.addObject("monthname", mname);
	        	        mav.addObject("fincd", fcode);
	        	        mav.addObject("dcode", dcode);
	        	        mav.addObject("projid", projid);
	        	        mav.addObject("monthid", mcode);
	        	        mav.addObject("finyr", fname);
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
		
		
		try {
			
			List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
			
			if(session != null && session.getAttribute("loginID") != null) 
			{	
				
				mav = new ModelAndView("projectEvaluation/fundUtilization");
				
				projectProfileId=PEService.getProjectProfileId( pcode, fcode, mcode);
				
				list=PEService.getFundUtilization(projectProfileId);
				
				 for(ProjectEvaluationBean bean : list) {
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
				 }
				 
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
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			if ("7".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	        }
			if ("8".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			if ("9".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			if ("10".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			if ("11".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			if ("12".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
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
					conPlannedFund, rmkConPlannedFund, exCon, rmkExCon, session, fromno);
			
			if ("success".equals(result)) {
	            request.setAttribute("fundUtilizationConfirmed", "true");
	        }
			
			request.setAttribute("projectProfileConfirmed", "true");
			request.setAttribute("evaluationDetailConfirmed", "true");

	        mav.addObject("distName", distName);
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
    
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
	        projectProfileId=PEService.getProjectProfileId( projid, fcode, mcode);
			
	        
	        Boolean pWatershedCom = Boolean.parseBoolean(request.getParameter("pWatershedCom"));
	        Boolean cWatershedCom = Boolean.parseBoolean(request.getParameter("cWatershedCom"));
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
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			if ("7".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	        }
			if ("8".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			if ("9".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			if ("10".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			if ("11".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			if ("12".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
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
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
    
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
		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		Integer pcode = Integer.parseInt(request.getParameter("pcode"));
		String project = request.getParameter("pcode");
		String dname = request.getParameter("dname");
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		String mname = request.getParameter("mname");
		String fname = request.getParameter("fname");
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
			mav.addObject("dcode",dcode);
			mav.addObject("pcode",pcode);
			mav.addObject("dname",dname);
			mav.addObject("pname",pname);
			mav.addObject("mcode", mcode);
			mav.addObject("mname", mname);
			mav.addObject("fcode", fcode);
			mav.addObject("fname", fname);
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
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			if ("7".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	        }
			if ("8".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			if ("9".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			if ("10".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			if ("11".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			if ("12".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
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
	                waterC, membersWC, householdsC, session, request);

	        if ("success".equals(result)) {
	            request.setAttribute("projectProfileConfirmed", "true");
	        }

	        mav.addObject("distName", distName);
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
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
		 Integer dcode = Integer.parseInt(request.getParameter("dcode")); 
		 Integer pcode = Integer.parseInt(request.getParameter("pcode")); 
		 String dname = request.getParameter("dname"); 
		 String pname = request.getParameter("pname");
		 Integer mcode = Integer.parseInt(request.getParameter("mcode")); 
		 Integer fcode = Integer.parseInt(request.getParameter("fcode")); 
		 String mname =request.getParameter("mname"); 
		 String fname = request.getParameter("fname");
		 Integer profile_id=0;
		 
		 String pcradio=null;
		 String culturablew=null;
		 String whs=null;
		 String soil=null;
		 String protective=null;
		 String degraded=null;
		 String income=null;
		 String benefited=null;
		 String mandays=null;
		 String dug=null;
		 String tube=null;
		 
		 String conculturablew=null;
		 String conwhs=null;
		 String consoil=null;
		 String conprotective=null;
		 String condegraded=null;
		 String conincome=null;
		 String conbenefited=null;
		 String conmandays=null;
		 String condug=null;
		 String contube=null;
		 
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
						
						// pcradio=bean.getProject_controlled().toString();
						 culturablew=bean.getCulturable_wasteland().toString();
						 whs=bean.getWhs_constructed_rejuvenated().toString();
						 soil=bean.getSoil_moisture().toString();
						 protective=bean.getProtective_irrigation().toString();
						 degraded=bean.getDegraded_rainfed().toString();
						 income=bean.getFarmer_income().toString();
						 benefited=bean.getFarmer_benefited().toString();
						 mandays=bean.getMandays_generated().toString();
						 dug=bean.getDug_well().toString();
						 tube=bean.getTube_well().toString();
						 
						 conculturablew=bean.getControl_culturable_wasteland().toString();
						 conwhs=bean.getControl_whs_constructed_rejuvenated().toString();
						 consoil=bean.getControl_soil_moisture().toString();
						 conprotective=bean.getControl_protective_irrigation().toString();
						 condegraded=bean.getControl_degraded_rainfed().toString();
						 conincome=bean.getControl_farmer_income().toString();
						 conbenefited=bean.getControl_farmer_benefited().toString();
						 conmandays=bean.getControl_mandays_generated().toString();
						 condug=bean.getControl_dug_well().toString();
						 contube=bean.getControl_tube_well().toString();
						 
					 }
					 
					 mav.addObject("areaType",pcradio); 
					 mav.addObject("culturablew",culturablew); 
					 mav.addObject("whs",whs); 
					 mav.addObject("soil",soil); 
					 mav.addObject("protective",protective); 
					 mav.addObject("degraded",degraded); 
					 mav.addObject("income",income); 
					 mav.addObject("benefited",benefited); 
					 mav.addObject("mandays",mandays); 
					 mav.addObject("dug",dug); 
					 mav.addObject("tube",tube); 
					 
					 mav.addObject("conculturablew",conculturablew); 
					 mav.addObject("conwhs",conwhs); 
					 mav.addObject("consoil",consoil); 
					 mav.addObject("conprotective",conprotective); 
					 mav.addObject("condegraded",condegraded); 
					 mav.addObject("conincome",conincome); 
					 mav.addObject("conbenefited",conbenefited); 
					 mav.addObject("conmandays",conmandays); 
					 mav.addObject("condug",condug); 
					 mav.addObject("contube",contube); 
			
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
	        BigDecimal culturable_wasteland = new BigDecimal(request.getParameter("culturable_wasteland"));
	        Integer whs_constructed_rejuvenated = Integer.parseInt(request.getParameter("whs_constructed_rejuvenated"));
	        BigDecimal soil_moisture = new BigDecimal(request.getParameter("soil_moisture"));
	        BigDecimal protective_irrigation = new BigDecimal(request.getParameter("protective_irrigation"));
	        BigDecimal degraded_rainfed = new BigDecimal(request.getParameter("degraded_rainfed"));
	        BigDecimal farmer_income = new BigDecimal(request.getParameter("farmer_income"));
	        Integer farmer_benefited = Integer.parseInt(request.getParameter("farmer_benefited"));
	        Integer mandays_generated = Integer.parseInt(request.getParameter("mandays_generated"));
	        BigDecimal dug_well = new BigDecimal(request.getParameter("dug_well"));
	        BigDecimal tube_well = new BigDecimal(request.getParameter("tube_well"));
	        
	        BigDecimal conculturable_wasteland = new BigDecimal(request.getParameter("conculturable_wasteland"));
	        Integer conwhs_constructed_rejuvenated = Integer.parseInt(request.getParameter("conwhs_constructed_rejuvenated"));
	        BigDecimal consoil_moisture = new BigDecimal(request.getParameter("consoil_moisture"));
	        BigDecimal conprotective_irrigation = new BigDecimal(request.getParameter("conprotective_irrigation"));
	        BigDecimal condegraded_rainfed = new BigDecimal(request.getParameter("condegraded_rainfed")); 
	        BigDecimal confarmer_income = new BigDecimal(request.getParameter("confarmer_income"));
	        Integer confarmer_benefited = Integer.parseInt(request.getParameter("confarmer_benefited"));
	        Integer conmandays_generated = Integer.parseInt(request.getParameter("conmandays_generated"));
	        BigDecimal condug_well = new BigDecimal(request.getParameter("condug_well"));
	        BigDecimal contube_well = new BigDecimal(request.getParameter("contube_well"));
	        
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
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			if ("7".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	        }
			if ("8".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			if ("9".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			if ("10".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			if ("11".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			if ("12".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	            request.setAttribute("geoTagDetailsConfirmed", "true");
	        }
			}
	        
	        result=PEService.saveMandaysDetails(profile_id, culturable_wasteland, whs_constructed_rejuvenated, soil_moisture, protective_irrigation, degraded_rainfed, farmer_income,
					farmer_benefited, dug_well, fromno, mandays_generated, tube_well, session, area, conculturable_wasteland, conwhs_constructed_rejuvenated,  consoil_moisture, 
					conprotective_irrigation, condegraded_rainfed, confarmer_income, confarmer_benefited, conmandays_generated, condug_well, contube_well );
	   
	        if ("success".equals(result)) {
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			
			request.setAttribute("projectProfileConfirmed", "true");
			request.setAttribute("evaluationDetailConfirmed", "true");
			request.setAttribute("fundUtilizationConfirmed", "true");
			request.setAttribute("croppedDetails1Confirmed", "true");

	        mav.addObject("distName", distName);
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        
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
	public ModelAndView getExecutionPlanWork(HttpServletRequest request, HttpServletResponse response)
	{
			session = request.getSession(true);
		
			Integer dcode = Integer.parseInt(request.getParameter("dcode")); 
			Integer pcode = Integer.parseInt(request.getParameter("pcode")); 
			String dname = request.getParameter("dname"); 
			String pname = request.getParameter("pname");
			Integer mcode = Integer.parseInt(request.getParameter("mcode")); 
			Integer fcode = Integer.parseInt(request.getParameter("fcode")); 
			String mname =request.getParameter("mname"); 
			String fname = request.getParameter("fname");
			String created_work=null;
			String created_work_remark=null;
			String completed_work=null;
			String completed_work_remark=null;
			String ongoing_work=null;
			String ongoing_work_remark=null;
			Integer profile_id=0;
			ModelAndView mav = new ModelAndView();
			
			
		
			try {
		
			Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
			if(session!=null && session.getAttribute("loginID")!=null) {
				mav = new ModelAndView("projectEvaluation/executionPlanWork");
				List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
				profile_id=PEService.getProjectProfileId( pcode, fcode, mcode);
				list=PEService.getExecutionPlanWork(profile_id);
				for(ProjectEvaluationBean bean : list) {
					
					created_work=bean.getCreated_work().toString();
					created_work_remark=bean.getCreated_work_remark().toString();
					completed_work=bean.getCompleted_work().toString();
					completed_work_remark=bean.getCompleted_work_remark().toString();
					ongoing_work=bean.getOngoing_work().toString();
					ongoing_work_remark=bean.getOngoing_work_remark().toString();
					
				 }
				 
				 mav.addObject("crw",created_work); 
				 mav.addObject("crwre",created_work_remark); 
				 mav.addObject("comw",completed_work); 
				 mav.addObject("comwre",completed_work_remark); 
				 mav.addObject("ongw",ongoing_work); 
				 mav.addObject("ongwre",ongoing_work_remark); 
				
			}
			
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
			
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
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			if ("7".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	        }
			if ("8".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			if ("9".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			if ("10".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			if ("11".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			if ("12".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
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
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        
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
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			if ("7".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	        }
			if ("8".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			if ("9".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			if ("10".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			if ("11".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			if ("12".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
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
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        
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
	public ModelAndView getstatusGeotagWork(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		ModelAndView mav = new ModelAndView();
		
		Integer dcode = Integer.parseInt(request.getParameter("dcode")); 
		Integer pcode = Integer.parseInt(request.getParameter("pcode")); 
		String dname = request.getParameter("dname"); 
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode")); 
		Integer fcode = Integer.parseInt(request.getParameter("fcode")); 
		String mname =request.getParameter("mname"); 
		String fname = request.getParameter("fname");
		
		String twork=null;
		String tworkre=null;
		
		
		Integer profile_id=0;
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("projectEvaluation/statusGeotagWork");
			

			List<ProjectEvaluationBean> list = new ArrayList<ProjectEvaluationBean>();
			profile_id=PEService.getProjectProfileId( pcode, fcode, mcode);
			list=PEService.getStatusGeotagWork(profile_id);
			for(ProjectEvaluationBean bean : list) {
				
				twork=bean.getGeo_tagg_work().toString();
				tworkre=bean.getGeo_tagg_work_remark().toString();
				
			 }
			 
			 mav.addObject("twork",twork); 
			 mav.addObject("tworkre",tworkre); 
			
			 
			
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		 mav.addObject("dcode",dcode); 
		 mav.addObject("pcode",pcode);
		 mav.addObject("distName",dname); 
		 mav.addObject("projName",pname);
		 mav.addObject("mcode", mcode); 
		 mav.addObject("month", mname);
		 mav.addObject("fcode", fcode); 
		 mav.addObject("finyear", fname);
		
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
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			if ("7".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	        }
			if ("8".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			if ("9".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			if ("10".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			if ("11".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			if ("12".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
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
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        
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
		String res="";
		session = request.getSession(true);
		
		try { 
			if (session != null && session.getAttribute("loginID") != null) {
				Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		        String distName = request.getParameter("dname");
		        Integer projid = Integer.parseInt(request.getParameter("pcode"));
		        String project = request.getParameter("pcode");
		        String projName = request.getParameter("pname");
		        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		        String mname = request.getParameter("mname");
		        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		        String fname = request.getParameter("fname");
		        Integer projProfId = Integer.parseInt(request.getParameter("projProfId"));
				
				BigDecimal kharifCrop = new BigDecimal(request.getParameter("kharif"));
				
				BigDecimal rabiCrop = new BigDecimal(request.getParameter("rabi"));
				BigDecimal thirdCrop = new BigDecimal(request.getParameter("thirdCrop"));
				
				BigDecimal cereals = new BigDecimal(request.getParameter("cereals"));
				BigDecimal pulses = new BigDecimal(request.getParameter("pulses"));
				
				BigDecimal oilSeed = new BigDecimal(request.getParameter("oilSeed"));
				BigDecimal millets = new BigDecimal(request.getParameter("millets"));
				
				BigDecimal others = new BigDecimal(request.getParameter("others"));
				BigDecimal horticulture = new BigDecimal(request.getParameter("horticulture"));
				
				BigDecimal netSown = new BigDecimal(request.getParameter("netSown"));
				BigDecimal cropIntensity = new BigDecimal(request.getParameter("cropIntensity"));
				
				BigDecimal diversifiedCrop = new BigDecimal(request.getParameter("diversifiedCrop"));
				
				BigDecimal ckharifCrop = new BigDecimal(request.getParameter("ckharif"));
				
				BigDecimal crabiCrop = new BigDecimal(request.getParameter("crabi"));
				BigDecimal cthirdCrop = new BigDecimal(request.getParameter("cthirdCrop"));
				
				BigDecimal ccereals = new BigDecimal(request.getParameter("ccereals"));
				BigDecimal cpulses = new BigDecimal(request.getParameter("cpulses"));
				
				BigDecimal coilSeed = new BigDecimal(request.getParameter("coilSeed"));
				BigDecimal cmillets = new BigDecimal(request.getParameter("cmillets"));
				
				BigDecimal cothers = new BigDecimal(request.getParameter("cothers"));
				BigDecimal chorticulture = new BigDecimal(request.getParameter("chorticulture"));
				
				BigDecimal cnetSown = new BigDecimal(request.getParameter("cnetSown"));
				BigDecimal ccropIntensity = new BigDecimal(request.getParameter("ccropIntensity"));
				
				BigDecimal cdiversifiedCrop = new BigDecimal(request.getParameter("cdiversifiedCrop"));
				
				
							
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
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
				String result = PEService.saveOrUpdateCroppedDetails(request, session, projProfId, kharifCrop, rabiCrop, thirdCrop, cereals, pulses, oilSeed, millets, others, horticulture, netSown, cropIntensity, diversifiedCrop,
						ckharifCrop, crabiCrop, cthirdCrop, ccereals, cpulses, coilSeed, cmillets, cothers, chorticulture, cnetSown, ccropIntensity, cdiversifiedCrop);
				
				if ("success".equals(result)) {
		            request.setAttribute("croppedDetails1Confirmed", "true");
		        }
				
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
				request.setAttribute("fundUtilizationConfirmed","true");

		        mav.addObject("distName", distName);
		        mav.addObject("projName", projName);
		        mav.addObject("monthname", mname);
		        mav.addObject("fincd", fcode);
		        mav.addObject("dcode", dcode);
		        mav.addObject("projid", projid);
		        mav.addObject("monthid", mcode);
		        mav.addObject("finyr", fname);
	    
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
	@RequestMapping(value="/ecologicalPerspective", method=RequestMethod.GET)
	public ModelAndView ecologicalPerspective(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
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
	            request.setAttribute("manDaysDetailConfirmed", "true");
	        }
			if ("7".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	        }
			if ("8".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	        }
			if ("9".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	        }
			if ("10".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	        }
			if ("11".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
	            request.setAttribute("manDaysDetailConfirmed", "true");
	            request.setAttribute("productionDetailsConfirmed", "true");
	            request.setAttribute("ecoPerspectiveConfirmed", "true");
	            request.setAttribute("equityAspectConfirmed", "true");
	            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
	            request.setAttribute("qualityShapeFileConfirmed", "true");
	        }
			if ("12".equals(projProfilestatus)) {
				request.setAttribute("projectProfileConfirmed", "true");
				request.setAttribute("evaluationDetailConfirmed", "true");
	            request.setAttribute("fundUtilizationConfirmed", "true");
	            request.setAttribute("croppedDetails1Confirmed", "true");
	            request.setAttribute("croppedDetails2Confirmed", "true");
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
	        mav.addObject("projName", projName);
	        mav.addObject("monthname", mname);
	        mav.addObject("fincd", fcode);
	        mav.addObject("dcode", dcode);
	        mav.addObject("projid", projid);
	        mav.addObject("monthid", mcode);
	        mav.addObject("finyr", fname);
	        
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
				Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		        String distName = request.getParameter("dname");
		        Integer projid = Integer.parseInt(request.getParameter("pcode"));
		        String project = request.getParameter("pcode");
		        String projName = request.getParameter("pname");
		        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		        String mname = request.getParameter("mname");
		        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		        String fname = request.getParameter("fname");
		        Integer projProfId = Integer.parseInt(request.getParameter("projProfId"));
				
				BigDecimal milch = new BigDecimal(request.getParameter("milch"));
				
				BigDecimal fodder = new BigDecimal(request.getParameter("fodder"));
				Integer ruralUrban = Integer.parseInt(request.getParameter("ruralUrban"));
				
				Integer spring = Integer.parseInt(request.getParameter("spring"));
				Integer benefit = Integer.parseInt(request.getParameter("benefit"));
				
				Integer shg = Integer.parseInt(request.getParameter("shg"));
				Integer fpo = Integer.parseInt(request.getParameter("fpo"));
				
				Integer ug = Integer.parseInt(request.getParameter("ug"));
				Integer mshg = Integer.parseInt(request.getParameter("mshg"));
				
				Integer mfpo = Integer.parseInt(request.getParameter("mfpo"));
				Integer mug = Integer.parseInt(request.getParameter("mug"));
				
				BigDecimal trunoverFpo = new BigDecimal(request.getParameter("trunoverFpo"));
				
				BigDecimal incomeFpo = new BigDecimal(request.getParameter("incomeFpo"));
				
				BigDecimal annualIncomeShg = new BigDecimal(request.getParameter("annualIncomeShg"));
				
				BigDecimal cmilch = new BigDecimal(request.getParameter("cmilch"));
				
				BigDecimal cfodder = new BigDecimal(request.getParameter("cfodder"));
				Integer cruralUrban = Integer.parseInt(request.getParameter("cruralUrban"));
				
				Integer cspring = Integer.parseInt(request.getParameter("cspring"));
				Integer cbenefit = Integer.parseInt(request.getParameter("cbenefit"));
				
				Integer cshg = Integer.parseInt(request.getParameter("cshg"));
				Integer cfpo = Integer.parseInt(request.getParameter("cfpo"));
				
				Integer cug = Integer.parseInt(request.getParameter("cug"));
				Integer cmshg = Integer.parseInt(request.getParameter("cmshg"));
				
				Integer cmfpo = Integer.parseInt(request.getParameter("cmfpo"));
				Integer cmug = Integer.parseInt(request.getParameter("cmug"));
				
				BigDecimal ctrunoverFpo = new BigDecimal(request.getParameter("ctrunoverFpo"));
				
				BigDecimal cincomeFpo = new BigDecimal(request.getParameter("cincomeFpo"));
				
				BigDecimal cannualIncomeShg = new BigDecimal(request.getParameter("cannualIncomeShg"));
				
				
							
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
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
				String result = PEService.saveOrUpdateProductionDetails(request, session, projProfId, milch, fodder, ruralUrban, spring, benefit, shg, fpo, ug, mshg, mfpo, mug, trunoverFpo,
						incomeFpo, annualIncomeShg, cmilch, cfodder, cruralUrban, cspring, cbenefit, cshg, cfpo, cug, cmshg, cmfpo, cmug, ctrunoverFpo, cincomeFpo, cannualIncomeShg);
				
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
		        mav.addObject("projName", projName);
		        mav.addObject("monthname", mname);
		        mav.addObject("fincd", fcode);
		        mav.addObject("dcode", dcode);
		        mav.addObject("projid", projid);
		        mav.addObject("monthid", mcode);
		        mav.addObject("finyr", fname);
	    
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
				Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		        String distName = request.getParameter("dname");
		        Integer projid = Integer.parseInt(request.getParameter("pcode"));
		        String project = request.getParameter("pcode");
		        String projName = request.getParameter("pname");
		        Integer mcode = Integer.parseInt(request.getParameter("mcode"));
		        String mname = request.getParameter("mname");
		        Integer fcode = Integer.parseInt(request.getParameter("fcode"));
		        String fname = request.getParameter("fname");
		        Integer projProfId = Integer.parseInt(request.getParameter("projProfId"));
				
				BigDecimal niltosingle = new BigDecimal(request.getParameter("niltosingle"));
				BigDecimal sdcrop = new BigDecimal(request.getParameter("sdcrop"));
				BigDecimal plantation = new BigDecimal(request.getParameter("plantation"));
				BigDecimal rice = new BigDecimal(request.getParameter("rice"));
				BigDecimal wheat = new BigDecimal(request.getParameter("wheat"));
				BigDecimal pulses = new BigDecimal(request.getParameter("pulses"));
				BigDecimal millets = new BigDecimal(request.getParameter("millets"));
				BigDecimal oilseed = new BigDecimal(request.getParameter("oilseed"));
				BigDecimal others = new BigDecimal(request.getParameter("others"));
				
				BigDecimal cniltosingle = new BigDecimal(request.getParameter("cniltosingle"));
				BigDecimal csdcrop = new BigDecimal(request.getParameter("csdcrop"));
				BigDecimal cplantation = new BigDecimal(request.getParameter("cplantation"));
				BigDecimal crice = new BigDecimal(request.getParameter("crice"));
				BigDecimal cwheat = new BigDecimal(request.getParameter("cwheat"));
				BigDecimal cpulses = new BigDecimal(request.getParameter("cpulses"));
				BigDecimal cmillets = new BigDecimal(request.getParameter("cmillets"));
				BigDecimal coilseed = new BigDecimal(request.getParameter("coilseed"));
				BigDecimal cothers = new BigDecimal(request.getParameter("cothers"));
				
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
		            request.setAttribute("manDaysDetailConfirmed", "true");
		        }
				if ("7".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		        }
				if ("8".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		        }
				if ("9".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		        }
				if ("10".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		        }
				if ("11".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		             request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		        }
				if ("12".equals(projProfilestatus)) {
					request.setAttribute("projectProfileConfirmed", "true");
					request.setAttribute("evaluationDetailConfirmed", "true");
		            request.setAttribute("fundUtilizationConfirmed", "true");
		            request.setAttribute("croppedDetails1Confirmed", "true");
		            request.setAttribute("croppedDetails2Confirmed", "true");
		            request.setAttribute("manDaysDetailConfirmed", "true");
		            request.setAttribute("productionDetailsConfirmed", "true");
		            request.setAttribute("ecoPerspectiveConfirmed", "true");
		            request.setAttribute("equityAspectConfirmed", "true");
		            request.setAttribute("executionOfPlannedWorkConfirmed", "true");
		            request.setAttribute("qualityShapeFileConfirmed", "true");
		            request.setAttribute("geoTagDetailsConfirmed", "true");
		        }
				}
				String result = PEService.saveOrUpdateCroppedDetails2(request, session, profile_id, niltosingle, sdcrop, plantation, rice, wheat, pulses,
						millets, oilseed, others, cniltosingle, csdcrop, cplantation, crice, cwheat, cpulses, cmillets, coilseed, cothers);
				
				if ("success".equals(result)) {
		            request.setAttribute("croppedDetails2Confirmed", "true");
		        }
				
		        mav.addObject("distName", distName);
		        mav.addObject("projName", projName);
		        mav.addObject("monthname", mname);
		        mav.addObject("fincd", fcode);
		        mav.addObject("dcode", dcode);
		        mav.addObject("projid", projid);
		        mav.addObject("monthid", mcode);
		        mav.addObject("finyr", fname);
	    
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
		
		Integer dcode = Integer.parseInt(request.getParameter("dcode")); 
		Integer pcode = Integer.parseInt(request.getParameter("pcode")); 
		String dname = request.getParameter("dname"); 
		String pname = request.getParameter("pname");
		Integer mcode = Integer.parseInt(request.getParameter("mcode")); 
		Integer fcode = Integer.parseInt(request.getParameter("fcode")); 
		String mname =request.getParameter("mname"); 
		String fname = request.getParameter("fname");
		Integer profile_id=0;
		String admiMechanism=null;
		String admiMechanismRemark=null;
		Boolean dprSlna=null;
		String dprSlnaRemark=null;
		Boolean allManpower=null;
		String allManpowerRemark=null;
		Integer wcdc=null;
		String wcdcRemark=null;
		Integer pia=null;
		String piaRemark=null;
		Integer wc=null;
		String wcRemark=null;
		String preCentralShare = null;
		String midCentralShare = null;
		String rmkCentralShare = null;
		String preStateShare = null;
		String midStateShare = null;
		String rmkStateShare = null;
		String preTotalFund = null;
		String midTotalFund = null;
		String rmkTotalFund = null;
		String preConPlannedFund = null;
		String midConPlannedFund = null;
		String rmkConPlannedFund = null;
		String preExCon = null;
		String midExCon = null;
		String rmkExCon = null;
		String pcradio=null;
		String culturablew=null;
		String whs=null;
		String soil=null;
		String protective=null;
		String degraded=null;
		String income=null;
		String benefited=null;
		String mandays=null;
		String dug=null;
		String tube=null;
		 
		String conculturablew=null;
		String conwhs=null;
		String consoil=null;
		String conprotective=null;
		String condegraded=null;
		String conincome=null;
		String conbenefited=null;
		String conmandays=null;
		String condug=null;
		String contube=null;
		
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
		
		LinkedHashMap<Integer, List<ProjectEvaluationBean>> getprojectProfileData = null;
		List<ProjectEvaluationBean> indicatorslist = new ArrayList<ProjectEvaluationBean>();  
		List<ProjectEvaluationBean> utilizationlist = new ArrayList<ProjectEvaluationBean>();
		List<ProjectEvaluationBean> mandayslist = new ArrayList<ProjectEvaluationBean>();
		List<ProjectEvaluationBean> ecoperlist = new ArrayList<ProjectEvaluationBean>();
		Integer projProfId = PEService.getProjectProfileId(pcode, fcode, mcode);
		if(session!=null && session.getAttribute("loginID")!=null) {
			getprojectProfileData = PEService.fetchprojProfileData(pcode);
			 profile_id=PEService.getProjectProfileId( pcode, fcode, mcode);
			 indicatorslist =PEService.getIndicatorEvaluation(profile_id);
			 utilizationlist=PEService.getFundUtilization(profile_id);
			 List<WdcpmksyCroppedDetails1> wdcCrpDtlList = new ArrayList<>();
			 wdcCrpDtlList = PEService.getCroppedDetails(profile_id);
			 List<WdcpmksyCroppedDetails2> wdcCrpDtlList2 = new ArrayList<>();
			 wdcCrpDtlList2 = PEService.getCroppedDetails2(profile_id);
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
					
				 admiMechanism=bean.getAdmin_mechanism().toString();
				 admiMechanismRemark=bean.getAdmin_mechanism_remark().toString();
				 //dprSlna=bean.getDpr_slna();
				 dprSlnaRemark=bean.getDpr_slna_remark().toString();
				 //allManpower=bean.getAll_manpower();
				 allManpowerRemark=bean.getAll_manpower_remark().toString();
				 wcdc=bean.getWcdc();
				 wcdcRemark=bean.getWcdc_remark().toString();
				 pia=bean.getPia();
				 piaRemark=bean.getPia_remark().toString();
				 wc=bean.getWc();
				 wcRemark=bean.getWc_remark().toString();
			}
			 for(ProjectEvaluationBean bean : utilizationlist) {
				 preCentralShare = bean.getCentral_share().toString();
				 rmkCentralShare = bean.getCentral_share_remark();
				 preStateShare = bean.getState_share().toString();
				 rmkStateShare = bean.getState_share_remark();
				 preTotalFund = bean.getTotal_fund().toString();
				 rmkTotalFund = bean.getTotal_fund_remark();
				 preConPlannedFund = bean.getTotal_fund_planned().toString();
				 rmkConPlannedFund = bean.getTotal_fund_planned_remark();
				 preExCon = bean.getTotal_expenditure().toString();
				 rmkExCon = bean.getTotal_expenditure_remark();
			 }
			 
			 for(ProjectEvaluationBean bean : mandayslist) 
			 {
				
				 culturablew=bean.getCulturable_wasteland().toString();
				 whs=bean.getWhs_constructed_rejuvenated().toString();
				 soil=bean.getSoil_moisture().toString();
				 protective=bean.getProtective_irrigation().toString();
				 degraded=bean.getDegraded_rainfed().toString();
				 income=bean.getFarmer_income().toString();
				 benefited=bean.getFarmer_benefited().toString();
				 mandays=bean.getMandays_generated().toString();
				 dug=bean.getDug_well().toString();
				 tube=bean.getTube_well().toString();
				 
				 conculturablew=bean.getControl_culturable_wasteland().toString();
				 conwhs=bean.getControl_whs_constructed_rejuvenated().toString();
				 consoil=bean.getControl_soil_moisture().toString();
				 conprotective=bean.getControl_protective_irrigation().toString();
				 condegraded=bean.getControl_degraded_rainfed().toString();
				 conincome=bean.getControl_farmer_income().toString();
				 conbenefited=bean.getControl_farmer_benefited().toString();
				 conmandays=bean.getControl_mandays_generated().toString();
				 condug=bean.getControl_dug_well().toString();
				 contube=bean.getControl_tube_well().toString();
				 
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
			 mav.addObject("am",admiMechanism);
			 mav.addObject("amd",admiMechanismRemark);
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
			 
			 mav.addObject("preCentralShare",preCentralShare);
			 mav.addObject("midCentralShare",midCentralShare);
			 mav.addObject("rmkCentralShare",rmkCentralShare);
			 mav.addObject("preStateShare",preStateShare);
			 mav.addObject("midStateShare", midStateShare);
			 mav.addObject("rmkStateShare", rmkStateShare);
			 mav.addObject("preTotalFund", preTotalFund);
			 mav.addObject("midTotalFund", midTotalFund);				
			 mav.addObject("rmkTotalFund",rmkTotalFund);
			 mav.addObject("preConPlannedFund",preConPlannedFund);
			 mav.addObject("midConPlannedFund",midConPlannedFund);
			 mav.addObject("rmkConPlannedFund",rmkConPlannedFund);
			 mav.addObject("preExCon", preExCon);
			 mav.addObject("midExCon", midExCon);
			 mav.addObject("rmkExCon", rmkExCon);
			 mav.addObject("wdcCrpDtlList", wdcCrpDtlList);
			 mav.addObject("wdcCrpDtlList2", wdcCrpDtlList2);
			 
			 mav.addObject("areaType",pcradio); 
			 mav.addObject("culturablew",culturablew); 
			 mav.addObject("whs",whs); 
			 mav.addObject("soil",soil); 
			 mav.addObject("protective",protective); 
			 mav.addObject("degraded",degraded); 
			 mav.addObject("income",income); 
			 mav.addObject("benefited",benefited); 
			 mav.addObject("mandays",mandays); 
			 mav.addObject("dug",dug); 
			 mav.addObject("tube",tube); 
			 mav.addObject("conculturablew",conculturablew); 
			 mav.addObject("conwhs",conwhs); 
			 mav.addObject("consoil",consoil); 
			 mav.addObject("conprotective",conprotective); 
			 mav.addObject("condegraded",condegraded); 
			 mav.addObject("conincome",conincome); 
			 mav.addObject("conbenefited",conbenefited); 
			 mav.addObject("conmandays",conmandays); 
			 mav.addObject("condug",condug); 
			 mav.addObject("contube",contube);
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
			 
		}
		
		return mav;
	}

	@RequestMapping(value="/completeprojEvaldata", method = RequestMethod.POST)
	@ResponseBody
	public String completeprojEvaldata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projProfId") Integer projProfId)
	{
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=PEService.completeprojEvaldata(projProfId);
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
		
		Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		String userType = session.getAttribute("userType").toString();
		List<ProfileBean> listm=new  ArrayList<ProfileBean>();
		listm=profileService.getMapstate(regId, userType);
		String distName = "";
		String stateName = "";
		int stCode = 0;
		int distCode = 0;
		for(ProfileBean bean : listm) {
			distName =bean.getDistrictname();
			distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
			stateName = bean.getStatename();
			stCode = bean.getStatecode()==null?0:bean.getStatecode();
		}
		String projProfilestatus = PEService.checkProjectProfileStatus(pcode.toString());
		LinkedHashMap<Integer, List<ProjectEvaluationBean>> getprojectProfileData = null;
		getprojectProfileData = PEService.fetchprojProfileData(pcode);
		
		
//		projProfId=PEService.getProjectProfileId( pcode, fcode, mcode);
			List<ProjectEvaluationBean> indicatorslist = new ArrayList<ProjectEvaluationBean>();
			indicatorslist =PEService.getIndicatorEvaluation(projProfId);
			List<ProjectEvaluationBean> utilizationlist = new ArrayList<ProjectEvaluationBean>();
			utilizationlist =PEService.getFundUtilization(projProfId);
			List<WdcpmksyCroppedDetails1> wdcCrpDtlList = new ArrayList<>();
			 wdcCrpDtlList = PEService.getCroppedDetails(projProfId);
			 List<WdcpmksyCroppedDetails2> wdcCrpDtlList2 = new ArrayList<>();
			 wdcCrpDtlList2 = PEService.getCroppedDetails2(projProfId);
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

			paragraph3 = new Paragraph("Project Evaluation - View & Complete ", f3);
			paragraph4 = new Paragraph("District Name : "  + dname + ", Project Name : "+   pname+     ", Month Name : "  + mname+  ",  Financial Year : " +  fname, bf9);
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
			table = new PdfPTable(5);
			table.setWidths(new int[] { 2, 8, 5, 5, 5});
			table.setWidthPercentage(85);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			
//			if(indicatorslist.size()!=0)
			CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 5, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Profile", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			
			for(Map.Entry<Integer, List<ProjectEvaluationBean>> bean: getprojectProfileData.entrySet()) {
//				   System.out.println("Key: " + bean.getKey() + ", Value: " + bean.getValue());
				if(bean.getKey().equals(stCode)) {
					for (ProjectEvaluationBean obj : bean.getValue()) {
						CommonFunctions.insertCell(table, "Total Sanctioned Cost of the Project (Rs. Crore)",Element.ALIGN_LEFT, 2, 1, bf8);
						CommonFunctions.insertCell(table, obj.getSanctioned_cost().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "Central Share (Rs. Crore)", Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, obj.getCentral().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "State Share (Rs. Crore)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, obj.getState().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total Sanctioned Project Area (in ha.)", Element.ALIGN_LEFT,1, 1, bf8);
					CommonFunctions.insertCell(table, obj.getSanctioned_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "Number of Village Covered", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, obj.getNo_vc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Number of Watershed Committees", Element.ALIGN_LEFT, 1, 1,bf8);
					CommonFunctions.insertCell(table, obj.getNo_wc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "Number of Members in Watershed Committees",Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, obj.getMember_wc().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Number of Households Covered in the Project area",Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, obj.getHousehold().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					}
				}
			}
			
			CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 5, 1, bf8);
			
			CommonFunctions.insertCellHeader(table, "Indicators for Evaluation", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Indicators", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);


			
//			
			if(indicatorslist.get(0).getAll_manpower().equals(true)){
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Administrative Mechanism", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, indicatorslist.get(0).getAdmin_mechanism().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, indicatorslist.get(0).getAdmin_mechanism_remark(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether DPR approved by SLNA", Element.ALIGN_LEFT, 2, 1, bf8);
					if(indicatorslist.get(0).getDpr_slna().equals(true)){
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_CENTER, 1, 1, bf8);
					
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_CENTER, 1, 1, bf8);
					}
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getDpr_slna_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether all manpower positions in place at", Element.ALIGN_LEFT, 2, 1, bf8);
					if(indicatorslist.get(0).getAll_manpower().equals(true)){
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_CENTER, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_CENTER, 1, 1, bf8);
					}
					
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getAll_manpower_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "I", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "WCDC Level", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWcdc()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWcdc_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "II", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "PIA Level", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getPia()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getPia_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "III", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "WC Level", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWc()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWc_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
			}
			
			if(indicatorslist.get(0).getAll_manpower().equals(false)){
				CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, "Administrative Mechanism", Element.ALIGN_LEFT, 2, 1, bf8);
				CommonFunctions.insertCell(table, indicatorslist.get(0).getAdmin_mechanism().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, indicatorslist.get(0).getAdmin_mechanism_remark(), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, "Whether DPR approved by SLNA", Element.ALIGN_LEFT, 2, 1, bf8);
				if(indicatorslist.get(0).getDpr_slna().equals(true)){
				CommonFunctions.insertCell(table, "Yes", Element.ALIGN_CENTER, 1, 1, bf8);
				
				}
				else {
					CommonFunctions.insertCell(table, "No", Element.ALIGN_CENTER, 1, 1, bf8);
				}
				CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getDpr_slna_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, "Whether all manpower positions in place at", Element.ALIGN_LEFT, 2, 1, bf8);
				if(indicatorslist.get(0).getAll_manpower().equals(true)){
				CommonFunctions.insertCell(table, "Yes", Element.ALIGN_CENTER, 1, 1, bf8);
				}
				else {
					CommonFunctions.insertCell(table, "No", Element.ALIGN_CENTER, 1, 1, bf8);
				}
				
				CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getAll_manpower_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
//				CommonFunctions.insertCell(table, "I", Element.ALIGN_LEFT, 1, 1, bf8);
//				CommonFunctions.insertCell(table, "WCDC Level", Element.ALIGN_LEFT, 2, 1, bf8);
//				CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWcdc()), Element.ALIGN_RIGHT, 1, 1, bf8);
//				CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWcdc_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
//				CommonFunctions.insertCell(table, "II", Element.ALIGN_LEFT, 1, 1, bf8);
//				CommonFunctions.insertCell(table, "PIA Level", Element.ALIGN_LEFT, 2, 1, bf8);
//				CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getPia()), Element.ALIGN_RIGHT, 1, 1, bf8);
//				CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getPia_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
//				CommonFunctions.insertCell(table, "III", Element.ALIGN_LEFT, 1, 1, bf8);
//				CommonFunctions.insertCell(table, "WC Level", Element.ALIGN_LEFT, 2, 1, bf8);
//				CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWc()), Element.ALIGN_RIGHT, 1, 1, bf8);
//				CommonFunctions.insertCell(table, String.valueOf(indicatorslist.get(0).getWc_remark()), Element.ALIGN_LEFT, 1, 1, bf8);
		}
					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 5, 1, bf8);
				
					CommonFunctions.insertCellHeader(table, "Fund Utilization", Element.ALIGN_CENTER, 5, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Indicators", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Pre-Project Status", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Mid-Project Status Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Fund Utilization", Element.ALIGN_LEFT, 4, 1, bf8);
					
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Amount of sanctioned Central share received (Rs. Crores)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getCentral_share().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getCentral_share_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Amount of sanctioned State share received (Rs. Crores)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getState_share().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getState_share_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					
					CommonFunctions.insertCell(table, "c", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Amount of Total funds (central + state share) Utilized (Rs. Crores)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (utilizationlist.get(0).getTotal_fund().toString()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_fund_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "d", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total funds planned through convergence in the project area (Rs. Crores)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_fund_planned().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_fund_planned_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "e", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total expenditure incurred through convergence (Rs. Crores)", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_expenditure().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, utilizationlist.get(0).getTotal_expenditure_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 5, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Cropped Details-1", Element.ALIGN_CENTER, 5, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Crop Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Gross Cropped Area(ha)", Element.ALIGN_LEFT, 4, 1, bf8);
					
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under kharif crop(ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getGrossKharifCropArea().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_gross_kharif_crop_area().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under rabi crop(ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getGrossRabiCropArea().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_gross_rabi_crop_area().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "c", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under Third crop(ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList.get(0).getGrossThirdCropArea().toString()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (wdcCrpDtlList.get(0).getControl_gross_third_crop_area().toString()), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under different Crops(ha)", Element.ALIGN_LEFT, 4, 1, bf8);
					
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Cereals", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getDifferentCropCereals().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_different_crop_cereals().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Pulses", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getDifferentCropPulses().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_different_crop_pulses().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "c", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Oil seed", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getDifferentCropOilSeed().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_different_crop_oil_seed().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "d", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Millets", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getDifferentCropMillets().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_different_crop_millets().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "e", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Others", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getDifferentCropOther().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_different_crop_other().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area of horticulture crop(ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getHorticultureArea().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_horticulture_area().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "4", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Net Sown Area(ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getNetsownArea().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_netsown_area().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "5", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Cropping Intensity", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getCroppingIntensity().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_cropping_intensity().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "6", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area covered under diversified crops/change in cropping systems(ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getDiversifiedChange().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, wdcCrpDtlList.get(0).getControl_diversified_change().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 5, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Cropped Details-2", Element.ALIGN_CENTER, 5, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Crop Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Gross Cropped Area(ha)", Element.ALIGN_LEFT, 4, 1, bf8);
					
					
					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Nil to single crop(ha.)", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getNillSingle().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControl_nill_single().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Single to double or more crop(ha.)", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, (wdcCrpDtlList2.get(0).getSingelDoublemore().toString()), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, (wdcCrpDtlList2.get(0).getControl_singel_doublemore().toString()), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area under plantation cover", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getPlantationCover().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControl_plantation_cover().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Yeild per hectare of major crops(Qtl./ha.)", Element.ALIGN_LEFT, 4, 1, bf8);

					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Rice", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getRice().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControl_rice().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Wheat", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getWheat().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControl_wheat().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "c", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Pulses", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getPulses().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControl_pulses().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "d", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Millets", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getMillets().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControl_millets().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "e", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Oil Seed", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getOil_seed().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControl_oil_seed().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "f", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Others", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getOther().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcCrpDtlList2.get(0).getControl_other().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 5, 1, bf8);
					

					CommonFunctions.insertCellHeader(table, "No. of Man-days Details", Element.ALIGN_CENTER, 5, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Indicators", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Area of Culturable Wasteland (ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getCulturable_wasteland().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_culturable_wasteland().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Number of Water Harvesting Structures(WHS) constructed/rejuvenated", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, (mandayslist.get(0).getWhs_constructed_rejuvenated().toString()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (mandayslist.get(0).getControl_whs_constructed_rejuvenated().toString()), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area Covered with Soil and Moisture Conservation Activities (ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getSoil_moisture().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_soil_moisture().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "4", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Area under Protective Irrigation (ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getProtective_irrigation().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_protective_irrigation().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "5", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area of degraded land covered/ rainfed area development (ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getDegraded_rainfed().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_degraded_rainfed().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "6", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Farmer`s Average Household Income per Annum (Rs. in Lakhs)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getFarmer_income().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_farmer_income().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "7", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "No. of Farmers Benefited", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getFarmer_benefited().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_farmer_income().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "8", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "No. of Persondays Generated (man-days)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getMandays_generated().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_mandays_generated().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "9", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Average depth of Water table in dug wells (mts.)- Summer Season", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getDug_well().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_dug_well().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "10", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Average depth of Water table in tube wells (mts.)- Summer Season", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getTube_well().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, mandayslist.get(0).getControl_tube_well().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					
					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 5, 1, bf8);


					CommonFunctions.insertCellHeader(table, "Production Details", Element.ALIGN_CENTER, 5, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Production Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Milk Production of Milch Cattle(Kl/Yr.)", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMilchCattle().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_milch_cattle().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Fodder Production(Qt./Yr.)", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, (wdcPrdDtlList.get(0).getFodderProduction().toString()), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, (wdcPrdDtlList.get(0).getControl_fodder_production().toString()), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Annual Migration from rural to urban area in project area(Nos.)", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getRuralUrban().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_rural_urban().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "4", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "No. of springs rejuvenated(if applicable)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getSpringRejuvenated().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_spring_rejuvenated().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "5", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "No. of persons benefitted due to rejuvenation of springs", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getPersonBenefitte().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_person_benefitte().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "6", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	No. of Community Based Organization", Element.ALIGN_LEFT, 4, 1, bf8);

					CommonFunctions.insertCell(table, "a", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "SHG", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getCommunityBasedShg().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_community_based_shg().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "b", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "FPO", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getCommunityBasedFpo().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_community_based_fpo().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "c", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "UG", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getCommunityBasedUg().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_community_based_ug().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "7", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Average depth of Water table in tube wells (mts.)- Summer Season", Element.ALIGN_LEFT, 4, 1, bf8);

					CommonFunctions.insertCell(table, "a", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "SHG", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMemberBasedShg().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_member_based_shg().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "b", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "FPO", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMemberBasedFpo().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_member_based_fpo().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "c", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "UG", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getMemberBasedUg().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_member_based_ug().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "8", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Avergage Annual Turnover of FPOs", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getTrunoverFpo().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_trunover_fpo().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "9", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Average annual net income of an FPO Member", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getIncomeFpo().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_income_fpo().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "10", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Average annual net income of an SHG Member", Element.ALIGN_LEFT, 2, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getAnnualIncomeShg().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, wdcPrdDtlList.get(0).getControl_annual_income_shg().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 5, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Ecological Perspective", Element.ALIGN_CENTER, 5, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Perspective Description", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Is there a system of auditing of status of natural resources intervals", Element.ALIGN_LEFT, 1, 1, bf8);
					
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
					CommonFunctions.insertCell(table, "Whether Gram Panchayats (GPs) and UGs enforcing the norms relating to sharing of usufructs rights", Element.ALIGN_LEFT, 1, 1, bf8);
					
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
					CommonFunctions.insertCell(table, "Whether all members of GPs and UGs trained to maintain and monitor all the natural resources and assets created", Element.ALIGN_LEFT, 1, 1, bf8);
					
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
					
					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 5, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Equity Aspect", Element.ALIGN_CENTER, 5, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Aspect Description", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether landless poor and women find a place in watershed units like watershed committee", Element.ALIGN_LEFT, 1, 1, bf8);
					
					if(equityAspectList.get(0).getWaterCommittee()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					if(equityAspectList.get(0).getControlWaterCommittee()==true) {
					CommonFunctions.insertCell(table, "Yes", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "No", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					CommonFunctions.insertCell(table, equityAspectList.get(0).getWaterCommitteeRemark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					
					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Whether landless poor and women are active member of FPO, SHG, Village level Institutions (VLIs) and various UGs", Element.ALIGN_LEFT, 1, 1, bf8);
					
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
					CommonFunctions.insertCell(table, "Whether landless and asset-less poor benefited from activities that promote alternate livelihood options", Element.ALIGN_LEFT, 1, 1, bf8);
					
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
					
					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 5, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Execution of Planned Works against Targets", Element.ALIGN_CENTER, 5, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total No. of Work IDs Created", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, targetlist.get(0).getCreated_work().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, targetlist.get(0).getCreated_work_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Total No. of Works Completed", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, (targetlist.get(0).getCompleted_work().toString()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (targetlist.get(0).getCompleted_work_remark().toString()), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "3", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total No. of Works Ongoing", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, targetlist.get(0).getOngoing_work().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, targetlist.get(0).getOngoing_work_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 5, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Quality of Project Shape Files", Element.ALIGN_CENTER, 5, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Area of Shape File (ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, projshapelist.get(0).getShape_file_area().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, projshapelist.get(0).getShape_file_area_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "2", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "	Variation of area under shape file as compared to sanctioned project area (ha)", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, (projshapelist.get(0).getVariation_area().toString()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (projshapelist.get(0).getVariation_area_remark().toString()), Element.ALIGN_LEFT, 1, 1, bf8);

					CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 5, 1, bf8);
					
					CommonFunctions.insertCellHeader(table, "Status of Geo-tagging of Works", Element.ALIGN_CENTER, 5, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Sl. No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 2, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Remarks", Element.ALIGN_CENTER, 1, 1, bf8Bold);

					
					CommonFunctions.insertCell(table, "1", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Total No. of Works Geo-Tagged", Element.ALIGN_LEFT, 2, 1, bf8);
					CommonFunctions.insertCell(table, geotagginglist.get(0).getGeo_tagg_work().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, geotagginglist.get(0).getGeo_tagg_work_remark().toString(), Element.ALIGN_LEFT, 1, 1, bf8);


			if(indicatorslist.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 5, 1, bf8);


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
}

