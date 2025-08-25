package app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.AddOutcomeParaBean;
import app.bean.Login;
import app.model.WdcpmksyMQuadIndicators;
import app.model.WdcpmksyQuadTarget;
import app.model.master.IwmpMProjectPrepare;
import app.service.CommonService;
import app.service.FinYrServices;
import app.service.PhysicalActionPlanService;
import app.service.ProjectMasterService;
import app.service.QuarterlyTargetService;

@Controller("quarterlyTargetController")
public class QuarterlyTargetController {

	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	
	@Autowired(required = true) 
	QuarterlyTargetService quarterlyTargetService;
	 
	@Autowired
	PhysicalActionPlanService physicalActionPlanService;
	
	@Autowired(required = true)
	private CommonService commonService;
	
	HttpSession session;
	
	@RequestMapping(value = "/quadtarget", method = RequestMethod.GET)
	public ModelAndView quadTarget(HttpServletRequest request) {
	    ModelAndView mav = new ModelAndView();
	    HttpSession session = request.getSession(false);  // Avoids creating a new session unnecessarily

	    if (session != null && session.getAttribute("loginID") != null) {
	        try {
	            int regId = (int) session.getAttribute("regId");
	            mav.setViewName("quarterlytarget");

	            // Parse and validate input parameters
	            String projectParam = request.getParameter("project");
	            String financialParam = request.getParameter("financial");
	            Integer project = projectParam != null ? Integer.parseInt(projectParam) : null;
	            Integer financial = financialParam != null ? Integer.parseInt(financialParam) : null;

	            // Fetch data if project and financial parameters are provided
	            if (project != null && financial != null) {
	                LinkedHashMap<Integer, List<WdcpmksyMQuadIndicators>> quadTar = 
	                    quarterlyTargetService.getAnnualTargetData(project, financial);
	                List<WdcpmksyMQuadIndicators> indicators = 
	                    quarterlyTargetService.getindicatorsdata(project, financial);
	                List<WdcpmksyQuadTarget> checkstatus = 
	                    quarterlyTargetService.getcompletedstatus(project, financial);
                    
	                 mav.addObject("QuadTarget", quadTar);
	                mav.addObject("checkstatus", checkstatus.size());
	                mav.addObject("indi", indicators);
	                mav.addObject("financialYear", 
	                    physicalActionPlanService.getFinYearProjectWise(project));
	            }

	            // Always add these attributes, regardless of specific project/financial parameters
	            mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
	            mav.addObject("Target", quarterlyTargetService.getTargetData());
	            mav.addObject("project", projectParam);
	            mav.addObject("financial", financialParam);

	        } catch (NumberFormatException e) {
	            mav.setViewName("error");
	            mav.addObject("errorMessage", "Invalid project or financial year format.");
	        }
	    } else {
	        mav.setViewName("login");
	        mav.addObject("login", new Login());
	    }

	    return mav;
	}

	@RequestMapping(value="/getFinYearProjWise", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getFinYearProjectWise(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> yearMap = new LinkedHashMap<Integer,String>();
		yearMap.putAll(physicalActionPlanService.getFinYearProjectWise(projectId));
		return yearMap; 
	}
	
	
	@RequestMapping(value="/savequarterlytarget", method = RequestMethod.POST) 
	public ModelAndView saveProjectPreparedness(HttpServletRequest request, HttpServletResponse response, @RequestParam Character status, HttpSession session)
	{
		String[] quarter1 = request.getParameterValues("quarter1");
		String[] quarter2 = request.getParameterValues("quarter2");
		String[] quarter3 = request.getParameterValues("quarter3");
		String[] quarter4 = request.getParameterValues("quarter4");
		String[] indicatorid = request.getParameterValues("indicatorid");
		String q1s = request.getParameter("quad1");
		String q2s = request.getParameter("quad2");
		String q3s = request.getParameter("quad3");
		String q4s = request.getParameter("quad4");
		Integer stcode= (Integer) session.getAttribute("stateCode");
		
		String ProjId=request.getParameter("project");
		String financial=request.getParameter("financial");
		List<WdcpmksyMQuadIndicators>indicators = null;
		Integer i=0;
		String yes=request.getParameter("res"); 
		System.out.println("value:" +yes);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("quarterlytarget");
	//	List<WdcpmksyQuadTarget> Indicatorslist=new  ArrayList<WdcpmksyQuadTarget>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			int regId = (int)session.getAttribute("regId");
			i = quarterlyTargetService.saveprojectpreparedness(Integer.parseInt(ProjId), status, Integer.parseInt(financial), quarter1, quarter2, quarter3, quarter4, indicatorid, q1s, q2s, q3s, q4s, stcode, session.getAttribute("loginID").toString());
			if(i==1) {
				mav.addObject("message", "Data Successfully Saved.");
				indicators = quarterlyTargetService.getindicatorsdata(Integer.parseInt(ProjId), Integer.parseInt(financial));
			}
			if(i==2) {
				mav.addObject("message", "Data Successfully Forward to SLNA.");
				indicators = quarterlyTargetService.getindicatorsdata(Integer.parseInt(ProjId), Integer.parseInt(financial));
			}
			if(i==0) {
				mav.addObject("message", "Error or Saving Data.");
			}
		//	Indicatorslist = quarterlyTargetService.getindicatorsdata();
			mav.addObject("projectList",  projectMasterService.getProjectByRegId(regId));
        	mav.addObject("financialYear", commonService.getFinancialYear());
        	mav.addObject("Target", quarterlyTargetService.getTargetData());
        	mav.addObject("indi", indicators);
        //	mav.addObject("Indicatorslist", Indicatorslist);
		    
		}
		
	return mav;
	}
	
}
