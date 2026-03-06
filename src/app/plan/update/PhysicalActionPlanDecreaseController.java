package app.plan.update;

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
import app.service.PhysicalActionPlanService;

@Controller("PhysicalActionPlanDecreaseController")
public class PhysicalActionPlanDecreaseController {
	
	HttpSession session;
	
	@Autowired
	PhysicalActionPlanService planService;
	
	@Autowired
	PhysicalActionPlanDecreaseService ser;
	
	
	@RequestMapping(value="/updatePhyActPlanDecrease", method = RequestMethod.GET)
	public ModelAndView updatePhyActPlanExits(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
			mav = new ModelAndView("physicalActionPlanDecrease");
			mav.addObject("projectList",planService.getProjectBystate(stcode));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getListofPhysicalActionPlanWithAchiev", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getListofPhysicalActionPlanWithAchiev(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		Integer projectcd= Integer.parseInt(request.getParameter("project"));
		Integer yearcd= Integer.parseInt(request.getParameter("year"));
		LinkedHashMap<Integer, List<PhysicalActionPlanBean>> map = new LinkedHashMap<Integer, List<PhysicalActionPlanBean>>();
		ModelAndView mav = new ModelAndView();
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		mav = new ModelAndView("physicalActionPlanDecrease");
		mav.addObject("projectList",planService.getProjectBystate(stcode));
		mav.addObject("yearMapList",planService.getFinYearExistPlan(projectcd));
		mav.addObject("yearMapSize",planService.getFinYearExistPlan(projectcd).size());
		Integer i=0;
		PhysicalActionPlanBean bean = new PhysicalActionPlanBean();
		List<PhysicalActionPlanBean> beanList = new ArrayList<PhysicalActionPlanBean>();
		List<PhysicalActionPlanBean> res = new ArrayList<PhysicalActionPlanBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=ser.getListofPhysicalActionPlanWithAchiev(projectcd,yearcd);
			
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
	
	@RequestMapping(value="/getListofPhysicalActionPlanAchiev", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActionPlanBean> getListofPhysicalActionPlanAchiev(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="yearcd") Integer yearcd,@RequestParam(value ="projectcd") Integer projectcd)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer, List<PhysicalActionPlanBean>> map = new LinkedHashMap<Integer, List<PhysicalActionPlanBean>>();
		ModelAndView mav = new ModelAndView();
		Integer i=0;
		PhysicalActionPlanBean bean = new PhysicalActionPlanBean();
		List<PhysicalActionPlanBean> beanList = new ArrayList<PhysicalActionPlanBean>();
		List<PhysicalActionPlanBean> res = new ArrayList<PhysicalActionPlanBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=ser.getListofPhysicalActionPlanAchiev(projectcd,yearcd);
			
			map.put(0,res);
		}else {
			map.put(0,null);
		}
		return res; 
	}
	
	@RequestMapping(value="/updatePlanAcordingAchievement", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updatePlanAcordingAchievement(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		Integer projectcd= Integer.parseInt(request.getParameter("ddproject"));
		Integer yearcd= Integer.parseInt(request.getParameter("ddyear"));
		String activity= request.getParameter("ddactivity");
		String plan= request.getParameter("plan");
		String achiev= request.getParameter("ddlachievement");
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		ModelAndView mav = new ModelAndView();
		
		try {
		
			mav = new ModelAndView("physicalActionPlanDecrease");
			mav.addObject("projectList",planService.getProjectBystate(stcode));
			mav.addObject("yearMapList",planService.getFinYearExistPlan(projectcd));
			mav.addObject("yearMapSize",planService.getFinYearExistPlan(projectcd).size());
			String res ="fail";
			if(session!=null && session.getAttribute("loginID")!=null) 
			{
				res=ser.updatePlanAcordingAchievement(plan,activity,projectcd,yearcd,session.getAttribute("loginID").toString());
			}
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
			mav.addObject("phyActPlanExistList",ser.getListofPhysicalActionPlanWithAchiev(projectcd,yearcd));
			mav.addObject("phyActPlanExistListSize",ser.getListofPhysicalActionPlanWithAchiev(projectcd,yearcd).size());
			mav.addObject("projectcd",projectcd);
			mav.addObject("yearcd",yearcd);
			mav.addObject("msg",res);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return mav; 
	}

}
