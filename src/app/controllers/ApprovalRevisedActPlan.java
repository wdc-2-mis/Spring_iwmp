package app.controllers;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import app.bean.RevisedActPlanBean;
import app.service.CommonService;
import app.service.DistrictMasterService;
import app.service.IwmpActPlanService;
import app.service.UserService;

@Controller("approvalRevisedActPlan")
public class ApprovalRevisedActPlan {
	
	HttpSession session=null;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	private CommonService commonService;
	
	@Autowired(required = true)
	private IwmpActPlanService iwmpActPlanService; 
	
	
	@RequestMapping(value ="/approvalRevisedActPlan", method = RequestMethod.GET)
	public ModelAndView approvalRevisedActPlan(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String district= request.getParameter("district");
		try {
//			mav = new ModelAndView("approvalRevisedActPlan");
			if(session!=null && session.getAttribute("loginID")!=null) {
//				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
//				String userType= session.getAttribute("userType").toString();
				Integer stateCode = Integer.parseInt(session.getAttribute("stateCode").toString());
				mav = new ModelAndView("approvalRevisedActPlan");
				mav.addObject("distList",districtMasterService.getDistrictByStateCodeWithDcode(stateCode));
				mav.addObject("financialYear", commonService.getAllFinancialYear());
			
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav;
		
	}
	
	@RequestMapping(value="/saveActPlan", method = RequestMethod.POST)
	@ResponseBody
	public String saveActPlan(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="dcode") Integer dcode,@RequestParam(value ="projid") Integer projid,@RequestParam(value ="finyear") Integer finyear,
			@RequestParam(value ="dprstatus") Character dprstatus,@RequestParam(value ="authname") String authname) {
		ModelAndView mav = new ModelAndView();
		String res = "";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			res = iwmpActPlanService.saveActPlan(dcode, projid, finyear, dprstatus, authname, sentFrom);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res;
	}
	
	@RequestMapping(value="/getProjFrmDist", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,String> getProjectFromDistrict(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="dCode") Integer dCode)
	{
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<String,String> projectList = new LinkedHashMap<String,String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer stateCode = Integer.parseInt(session.getAttribute("stateCode").toString());
			if(dCode!=null && !dCode.equals("")) 
				{
					projectList=(LinkedHashMap<String, String>) userService.getProjectList(stateCode, dCode);
				}
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return projectList;
	}
	
	@RequestMapping(value="/getAlreadyDistActPlanData", method = RequestMethod.POST)
	@ResponseBody
	public List<RevisedActPlanBean> getAlreadyDistActPlanData(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="dCode") Integer dCode){
		ModelAndView mav = new ModelAndView();
		List<RevisedActPlanBean> revisedActPlanList = new LinkedList<>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			if(dCode!=null && !dCode.equals("")) {
				revisedActPlanList= iwmpActPlanService.getAlreadyDistActPlanData(dCode);
			}
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return revisedActPlanList;
	}
	
	@RequestMapping(value="/completeStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateFinalStatus(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="actPlanId") Integer actPlanId) {
		ModelAndView mav = new ModelAndView();
		String res = "";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res = iwmpActPlanService.getFinalStatus(actPlanId);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res;
	}
	
	@RequestMapping(value="/deleteActPlan", method = RequestMethod.POST)
	@ResponseBody
	public String deleteFinalStatus(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="actPlanId") Integer actPlanId) {
		ModelAndView mav = new ModelAndView();
		String res = "";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res = iwmpActPlanService.deleteActPlan(actPlanId);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res;
	}
}
