package app.controllers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.PfmsTransactionBean;
import app.bean.TargetAchDashboardBean;
import app.bean.reports.DolrDashboardBean;
import app.service.DashBoardService;
import app.service.PhysicalHeadservice;
import app.service.StateMasterService;
import app.service.reports.PhysicalActionAchievementService;

@Controller("tarAchDashboardController")
public class TarAchDashboardController {

	@Autowired(required = true)
	DashBoardService dashBoardService;
	
	@Autowired(required = true)
	StateMasterService stateMasterService;
	private Map<Integer, String> stateList;
	
	@Autowired(required = true)
	PhysicalHeadservice physicalheadservice;
	private Map<Integer, String> headList;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	String finyear;
	String state;
	Integer checkdata = 0;
	@RequestMapping(value="/tarachDashBoard", method = RequestMethod.GET)
	public ModelAndView tarAchDashBoard(HttpServletRequest request, HttpServletResponse response,Model model)
	{
		Integer finYear = 0;
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		/* String getyear = year.toString(); */
		/* finYear = Integer.parseInt(getyear.substring(2,3)); */
		if(month>=4) {
			finYear = year - 2000;
		}
		else {
			finYear = year - 2001;
		}
		System.out.println(month);
		Integer state = 0;
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
	try {
		mav = new ModelAndView("tarAchDashBoard");
		stateList=stateMasterService.getAllState();
		mav.addObject("stateList", stateList);
		mav.addObject("state", userState);
		mav.addObject("tarandachiev",  dashBoardService.gettarachdata(finYear, state));
		mav.addObject("monthWiseAch1", dashBoardService.getMonthWiseAch(finYear, state));
		mav.addObject("monthWiseAch2", dashBoardService.getMonthWiseAch2(finYear, state));
		mav.addObject("monthWiseAch3", dashBoardService.getMonthWiseAch3(finYear, state));
		mav.addObject("monthWiseAch4", dashBoardService.getMonthWiseAch4(finYear, state));
		mav.addObject("monthWiseAch5", dashBoardService.getMonthWiseAch5(finYear, state));
		mav.addObject("monthWiseAch6", dashBoardService.getMonthWiseAch6(finYear, state));
		mav.addObject("monthWiseAch7", dashBoardService.getMonthWiseAch7(finYear, state));
		mav.addObject("topstates",  dashBoardService.getTopStates(finYear));
		mav.addObject("topstates2", dashBoardService.getTopStates2(finYear));
		mav.addObject("topstates3", dashBoardService.getTopStates3(finYear));
		mav.addObject("topstates4", dashBoardService.getTopStates4(finYear));
		mav.addObject("topstates5", dashBoardService.getTopStates5(finYear));
		mav.addObject("topstates6", dashBoardService.getTopStates6(finYear));
		mav.addObject("topstates7", dashBoardService.getTopStates7(finYear));
		mav.addObject("belowstates",  dashBoardService.getBelowStates(finYear));
		mav.addObject("belowstates2", dashBoardService.getBelowStates2(finYear));
		mav.addObject("belowstates3", dashBoardService.getBelowStates3(finYear));
		mav.addObject("belowstates4", dashBoardService.getBelowStates4(finYear));
		mav.addObject("belowstates5", dashBoardService.getBelowStates5(finYear));
		mav.addObject("belowstates6", dashBoardService.getBelowStates6(finYear));
		mav.addObject("belowstates7", dashBoardService.getBelowStates7(finYear));
		
		mav.addObject("yearList",pAAservices.getYearForAchDashboard());
		mav.addObject("tarachdashdata",dashBoardService.getheadDescFinYearState(finYear.toString(), state.toString()));
		
	}
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return mav;
	}
	
	@RequestMapping(value = "/getTarAchDashboardData", method = RequestMethod.GET)
	public ModelAndView getTarAchDashboardData(HttpServletRequest request, HttpServletResponse response) {
		
		finyear=request.getParameter("finyear");
		Integer finYear = Integer.parseInt(finyear);
		state=request.getParameter("state");
		ModelAndView mav = new ModelAndView();
		List<TargetAchDashboardBean>list = new ArrayList<TargetAchDashboardBean>();
		try {
			mav = new ModelAndView("tarAchDashBoard");
			mav.addObject("tarandachiev",  dashBoardService.gettarachdata(finYear, Integer.parseInt(state)));
			mav.addObject("monthWiseAch1", dashBoardService.getMonthWiseAch(finYear, Integer.parseInt(state)));
			mav.addObject("monthWiseAch2", dashBoardService.getMonthWiseAch2(finYear, Integer.parseInt(state)));
			mav.addObject("monthWiseAch3", dashBoardService.getMonthWiseAch3(finYear, Integer.parseInt(state)));
			mav.addObject("monthWiseAch4", dashBoardService.getMonthWiseAch4(finYear, Integer.parseInt(state)));
			mav.addObject("monthWiseAch5", dashBoardService.getMonthWiseAch5(finYear, Integer.parseInt(state)));
			mav.addObject("monthWiseAch6", dashBoardService.getMonthWiseAch6(finYear, Integer.parseInt(state)));
			mav.addObject("monthWiseAch7", dashBoardService.getMonthWiseAch7(finYear, Integer.parseInt(state)));
			mav.addObject("tarachdashdata",dashBoardService.getheadDescFinYearState(finyear, state));
			mav.addObject("yearList",pAAservices.getYearForAchDashboard());
			mav.addObject("topstates", dashBoardService.getTopStates(finYear));
			mav.addObject("topstates2", dashBoardService.getTopStates2(finYear));
			mav.addObject("topstates3", dashBoardService.getTopStates3(finYear));
			mav.addObject("topstates4", dashBoardService.getTopStates4(finYear));
			mav.addObject("topstates5", dashBoardService.getTopStates5(finYear));
			mav.addObject("topstates6", dashBoardService.getTopStates6(finYear));
			mav.addObject("topstates7", dashBoardService.getTopStates7(finYear));
			mav.addObject("belowstates",  dashBoardService.getBelowStates(finYear));
			mav.addObject("belowstates2", dashBoardService.getBelowStates2(finYear));
			mav.addObject("belowstates3", dashBoardService.getBelowStates3(finYear));
			mav.addObject("belowstates4", dashBoardService.getBelowStates4(finYear));
			mav.addObject("belowstates5", dashBoardService.getBelowStates5(finYear));
			mav.addObject("belowstates6", dashBoardService.getBelowStates6(finYear));
			mav.addObject("belowstates7", dashBoardService.getBelowStates7(finYear));
			mav.addObject("state", state);
			mav.addObject("fyear", finyear);
			mav.addObject("stateList", stateList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}

	@RequestMapping(value = "/tarAchProgDashboardData", method = RequestMethod.GET)
	public ModelAndView tarAchProgDashboardData(HttpServletRequest request, HttpServletResponse response) {
		
		
		ModelAndView mav = new ModelAndView();
		List<TargetAchDashboardBean>list = new ArrayList<TargetAchDashboardBean>();
		Integer headCode = 1;
		Integer stCode = 0;
		try {
			mav = new ModelAndView("tarAchProgDashBoard");
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			headList=physicalheadservice.getAllPrayasHeadList();
			mav.addObject("headList", headList);
			mav.addObject("progressiveData", dashBoardService.getProgressiveData(headCode, stCode));
			mav.addObject("statewiseProgressive", dashBoardService.getstatewiseProgressive(headCode));
			mav.addObject("headname", dashBoardService.getheadname(headCode));
			mav.addObject("stCode", stCode);
			mav.addObject("headcode", headCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/getTarAchProDashData", method = RequestMethod.GET)
	public ModelAndView getTarAchProDashData(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		Integer stCode=Integer.parseInt(request.getParameter("state"));
		Integer headCode=Integer.parseInt(request.getParameter("head"));
		
		String headname = request.getParameter("headname");
		List<TargetAchDashboardBean>list = new ArrayList<TargetAchDashboardBean>();
		List<TargetAchDashboardBean>statetabular = new ArrayList<TargetAchDashboardBean>();
		List<TargetAchDashboardBean>districttabular = new ArrayList<TargetAchDashboardBean>();
		List<TargetAchDashboardBean>districtfirstdata = new ArrayList<TargetAchDashboardBean>();
		try {
			mav = new ModelAndView("tarAchProgDashBoard");
			stateList=stateMasterService.getAllState();
            mav.addObject("state", stCode);
			headList=physicalheadservice.getAllPrayasHeadList();
			mav.addObject("head", headCode);
			mav.addObject("stateList", stateList);
			mav.addObject("headList", headList);
			mav.addObject("progressiveData", dashBoardService.getProgressiveData(headCode, stCode));
			mav.addObject("headname", dashBoardService.getheadname(headCode));
			if(stCode == 0)
			{
				statetabular =  dashBoardService.getstatewiseProgressive(headCode);
			}
			else {
			districttabular =  dashBoardService.getdistrictwiseProgressive(headCode, stCode);
			districtfirstdata = dashBoardService.getdistrictfirstRecords(headCode, stCode);
			}
			
			mav.addObject("statewiseProgressive", statetabular);
			mav.addObject("districtwiseProgressive", districttabular);
			mav.addObject("stCode", stCode);
			mav.addObject("headcode", headCode);
			 mav.addObject("districtDtl", districtfirstdata); 
			/*
			 * ScriptEngineManager manager = new ScriptEngineManager(); ScriptEngine engine
			 * = manager.getEngineByName("JavaScript");
			 * engine.eval(Files.newBufferedReader(Paths.get(
			 * "D:/eclipse-workspace/Spring_latest/WebContent/WEB-INF/resources/js/tarachProgdashboardscript.js"
			 * ), StandardCharsets.UTF_8));
			 * 
			 * Invocable inv = (Invocable) engine; inv.invokeFunction("myOverFunction",
			 * "headcode");
			 */
			
			mav.addObject("checkdata", 1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	
@RequestMapping(value = "/getDistrictAchievement", method = RequestMethod.POST)
@ResponseBody
public List<TargetAchDashboardBean> getDistrictAchievement(HttpServletRequest request, HttpServletResponse response, @RequestParam("dcode") Integer dcode,
		@RequestParam("headcode") Integer headcode){
	    String msg = null;
	  ModelAndView mav  = new ModelAndView();
	 List<TargetAchDashboardBean> list = new ArrayList<TargetAchDashboardBean>();
	 	    mav.addObject("checkdata", 2); 
			list = dashBoardService.getDistrictProgressiveData(headcode, dcode);
	 msg = "hello";
	return list;
}	

@RequestMapping(value = "/getStateAchievement", method = RequestMethod.POST)
@ResponseBody
public List<TargetAchDashboardBean> getStateAchievement(HttpServletRequest request, HttpServletResponse response, @RequestParam("scode") Integer scode,
		@RequestParam("headcode") Integer headcode){
	    String msg = null;
	 List<TargetAchDashboardBean> list = new ArrayList<TargetAchDashboardBean>();
	 	    list = dashBoardService.getStateProgressiveData(headcode, scode);
	 msg = "hello";
	return list;
}	


@RequestMapping(value = "/getDistrictDBName", method = RequestMethod.POST)
@ResponseBody
public List<TargetAchDashboardBean> getDistrictDBName(HttpServletRequest request, HttpServletResponse response, @RequestParam("dcode") Integer dcode){
	    String msg = null;
	  ModelAndView mav  = new ModelAndView();
	 List<TargetAchDashboardBean> list = new ArrayList<TargetAchDashboardBean>();
	 	    list = dashBoardService.getDistrictNameData(dcode);
	return list;
}
	
@RequestMapping(value = "/getStateDBName", method = RequestMethod.POST)
@ResponseBody
public List<TargetAchDashboardBean> getStateDBName(HttpServletRequest request, HttpServletResponse response, @RequestParam("scode") Integer scode){
	    String msg = null;
	  ModelAndView mav  = new ModelAndView();
	 List<TargetAchDashboardBean> list = new ArrayList<TargetAchDashboardBean>();
	 	    list = dashBoardService.getStateNameData(scode);
	return list;
}


	@RequestMapping(value="/baselineSurveyDashBoard1", method = RequestMethod.GET)
	public ModelAndView baselineSurveyDashBoard1(HttpServletRequest request, HttpServletResponse response,Model model)
	{
		Integer finYear = 0;
		Integer state = 0;
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		try {
			mav = new ModelAndView("baselineDashBoard");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/baselineSurveyDashBoard", method = RequestMethod.GET)
	public ModelAndView baselineSurveyDashBoard(HttpServletRequest request, HttpServletResponse response,Model model)
	{
		Integer finYear = 0;
		Integer state = 0;
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		try {
			mav = new ModelAndView("baselineDashBoard1");
			mav.addObject("classificationbaseline", dashBoardService.getClassificationBase());
			mav.addObject("classificationondate", dashBoardService.getClassificationDate());
			mav.addObject("irrigationbaseline", dashBoardService.getIrrigationBase());
			mav.addObject("irrigationondate", dashBoardService.getIrrigationonDate());
			mav.addObject("ownershipbaseline", dashBoardService.getOwnershipBase());
			mav.addObject("ownershipondate", dashBoardService.getOwnershipDate());
			mav.addObject("noofcropsbaseline", dashBoardService.getNoofCropBase());
			mav.addObject("noofcropsondate", dashBoardService.getNoofCropDate());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/getMonthWiseAchievement", method = RequestMethod.POST)
	@ResponseBody
	public List<TargetAchDashboardBean> getMonthWiseAchievement(HttpServletRequest request){
		
		return null;
	}
}
