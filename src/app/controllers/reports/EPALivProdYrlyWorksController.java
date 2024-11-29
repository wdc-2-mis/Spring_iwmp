package app.controllers.reports;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.bean.reports.ActivityWiseUptoPlanAchieveWorkBean;
import app.service.StateMasterService;
import app.service.reports.PhysicalActionAchievementService;

@Controller
public class EPALivProdYrlyWorksController {
	
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@RequestMapping(value="/epaLivProdWiseYearlyWork", method = RequestMethod.GET)
	public ModelAndView yearWisePhyActPlan(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/epaLivProdYearlyWorks");
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		return mav;
	}
	
	
	@RequestMapping(value="/epaLivProdWiseYearlyWork", method = RequestMethod.POST)
	public ModelAndView getActivityWiseUptoPlanAchievWorkReport(HttpServletRequest request,@RequestParam(value ="state") Integer stCode,
			@RequestParam(value ="district") Integer distCode,@RequestParam(value ="project") Integer projId,
			@RequestParam(value ="fromYear") Integer fromYear, @RequestParam(value ="headother") String head)
	{
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		String headName = request.getParameter("headName");
		//System.out.println("state: "+stCode+" district: "+distCode);
		ModelAndView mav = new ModelAndView();
		List<String[]> dataList = new ArrayList<String[]>();
		String str[] = null;
		List<String> month= new ArrayList<String>();
		Integer monthListSize =1;
		//String strYr[] = new String[toYear-fromYear];
		String yrChk="";
		List<String> monthList = new ArrayList<String>();
		Integer headSeq=0;
		List<Integer> headCode = new ArrayList<Integer>();
		Integer activitySeq =1;
		List<String> activityCode = new ArrayList<String>();
		
		try {
				mav = new ModelAndView("reports/epaLivProdYearlyWorks");
				List<ActivityWiseUptoPlanAchieveWorkBean> beanList = new ArrayList<ActivityWiseUptoPlanAchieveWorkBean>();
				beanList=pAAservices.getActivityWiseEPALivProdWorkReport(stCode, distCode, projId,fromYear,head);
			
			 mav.addObject("dataList",beanList);
			 mav.addObject("dataListSize",beanList.size());
			
			 mav.addObject("stateList",stateMasterService.getAllState());
			 mav.addObject("stCode",stCode);
			 mav.addObject("distCode",distCode);
			 mav.addObject("projectId",projId);
			 mav.addObject("fromYear",fromYear);
			 mav.addObject("head",head);
			 mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
			 
			 mav.addObject("stName",stName);
			 mav.addObject("distName",distName);
			 mav.addObject("projName",projName);
			 mav.addObject("yearName",yearName);
			 mav.addObject("headName",headName);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 
		return mav;
	}

}
