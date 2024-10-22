package app.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.PhysicalActionPlanBean;
import app.service.FinancialYearMasterService;
import app.service.PhysicalActionPlanService;
import app.service.ProjectMasterService;
import app.service.StateMasterService;
import app.service.reports.PhysicalActionAchievementService;

@Controller("PhysicalActionPlanExistController")
public class PhysicalActionPlanExistController {
	
	HttpSession session;
	
	@Autowired
	ProjectMasterService pmservice;
	
	@Autowired
	FinancialYearMasterService fyservice;
	
	@Autowired
	PhysicalActionPlanService physicalActionPlanService;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@RequestMapping(value="/piaPhyActPlanExits", method = RequestMethod.GET)
	public ModelAndView getPhysicalActionPlanExits(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
			mav = new ModelAndView("physicalActionPlanExits");
			mav.addObject("projectList",physicalActionPlanService.getProjectBystate(stcode));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getFinYearExistPlan", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getFinYearProjectWise(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> yearMap = new LinkedHashMap<Integer,String>();
		yearMap.putAll(physicalActionPlanService.getFinYearExistPlan(projectId));
		return yearMap; 
	}
	
	
	
	
	@RequestMapping(value="/getListofPhysicalActionAchiev", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActionPlanBean> getListofPhysicalActionAchiev(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="yearcd") Integer yearcd, @RequestParam(value ="projectcd") Integer projectcd)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer, List<PhysicalActionPlanBean>> map = new LinkedHashMap<Integer, List<PhysicalActionPlanBean>>();
		
		List<PhysicalActionPlanBean> res = new ArrayList<PhysicalActionPlanBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=physicalActionPlanService.getListofPhysicalActionAchiev(projectcd,yearcd);
			
			map.put(0,res);
		}else {
			map.put(0,null);
		}
		return res; 
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value="/saveAsExisting", method = RequestMethod.POST)
	@ResponseBody
	public String saveAsExisting(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="plans") String plan, @RequestParam(value ="activities") String activity,
			@RequestParam(value ="yearcd") Integer yearcd, @RequestParam(value ="projectcd") Integer projectcd)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			res=physicalActionPlanService.saveAsExisting(plan,activity,projectcd,yearcd,session.getAttribute("loginID").toString());
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/getListofPhysicalActionPlanExist", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getListofPhysicalActionPlanExist(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		Integer projectcd= Integer.parseInt(request.getParameter("project"));
		Integer yearcd= Integer.parseInt(request.getParameter("year"));
		LinkedHashMap<Integer, List<PhysicalActionPlanBean>> map = new LinkedHashMap<Integer, List<PhysicalActionPlanBean>>();
		ModelAndView mav = new ModelAndView();
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		mav = new ModelAndView("physicalActionPlanExitsUpdate");
		mav.addObject("projectList",physicalActionPlanService.getProjectBystate(stcode));
		mav.addObject("yearMapList",physicalActionPlanService.getFinYearExistPlan(projectcd));
		mav.addObject("yearMapSize",physicalActionPlanService.getFinYearExistPlan(projectcd).size());
		Integer i=0;
		PhysicalActionPlanBean bean = new PhysicalActionPlanBean();
		List<PhysicalActionPlanBean> beanList = new ArrayList<PhysicalActionPlanBean>();
		List<PhysicalActionPlanBean> res = new ArrayList<PhysicalActionPlanBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=physicalActionPlanService.getListofPhysicalActionPlan(projectcd,yearcd);
			
			map.put(0,res);
		}else {
			map.put(0,null);
		}
		mav.addObject("phyActPlanExistList",res);
		mav.addObject("phyActPlanExistListSize",res.size());
		mav.addObject("projectcd",projectcd);
		mav.addObject("yearcd",yearcd);
		return mav; 
	}
	
	@RequestMapping(value="/updatePhyActPlanExits", method = RequestMethod.GET)
	public ModelAndView updatePhyActPlanExits(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
			mav = new ModelAndView("physicalActionPlanExitsUpdate");
			mav.addObject("projectList",physicalActionPlanService.getProjectBystate(stcode));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/updateAsExisting", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateAsExisting(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		Integer projectcd= Integer.parseInt(request.getParameter("ddproject"));
		Integer yearcd= Integer.parseInt(request.getParameter("ddyear"));
		String activity= request.getParameter("ddactivity");
		String plan= request.getParameter("plan");
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("physicalActionPlanExitsUpdate");
		mav.addObject("projectList",physicalActionPlanService.getProjectBystate(stcode));
		mav.addObject("yearMapList",physicalActionPlanService.getFinYearExistPlan(projectcd));
		mav.addObject("yearMapSize",physicalActionPlanService.getFinYearExistPlan(projectcd).size());
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			try {
			res=physicalActionPlanService.updateAsExisting(plan,activity,projectcd,yearcd,session.getAttribute("loginID").toString());
//			if(res.equals("success")) {
//				res = "plan saved successfully";
//			}else if(res.equals("fail")) {
//				res = "plan not saved. Plan remains same";
//			}else {
//				res = "plan should be greater than the existing plan";
//			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		mav.addObject("phyActPlanExistList",physicalActionPlanService.getListofPhysicalActionPlan(projectcd,yearcd));
		mav.addObject("phyActPlanExistListSize",physicalActionPlanService.getListofPhysicalActionPlan(projectcd,yearcd).size());
		mav.addObject("projectcd",projectcd);
		mav.addObject("yearcd",yearcd);
		mav.addObject("msg",res);
		
		return mav; 
	}

}
