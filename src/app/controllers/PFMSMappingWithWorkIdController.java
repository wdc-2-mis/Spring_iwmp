package app.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.PfmsTransactionBean;
import app.bean.ProfileBean;
import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.service.ProfileService;
import app.service.ProjectMasterService;
import app.service.master.PfmsService;
import app.service.reports.PhysicalActionAchievementService;

@Controller
public class PFMSMappingWithWorkIdController {

	HttpSession session;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	ProjectMasterService pmservice;
	
	@Autowired
	PfmsService pfmsSrvc;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@RequestMapping(value ="/pfmsMappingWithWorkId", method = RequestMethod.GET)
	public ModelAndView pfmsMappingWithWorkId(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<PfmsTransactionBean> list = new ArrayList<>();
		int projId = 0;
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("pfmsMappingWithWorkId");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				mav.addObject("projectList",pmservice.getProjectByRegId(regId));
				mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
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

	@RequestMapping(value ="/pfmstesting", method = RequestMethod.GET)
	public ModelAndView pfmstesting(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<PfmsTransactionBean> list = new ArrayList<>();
		int projId = 0;
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("pfmstestingpage");
				
				
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
	
	@RequestMapping(value = "/getPfmsWorkIdTransaction", method = RequestMethod.POST)
	@ResponseBody
	public List<PfmsTransactionBean> getPfmsWorkIdTransaction(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("projId") Integer projId, @ModelAttribute("yearId") Integer yearId){
		session = request.getSession(true);
		Integer finyear = 0;
		ModelAndView mav = new ModelAndView();
		String userType = session.getAttribute("userType").toString();
		int aa=yearId+1;
		String fyear = "20"+aa;
		finyear = Integer.parseInt(fyear);
		List<PfmsTransactionBean> list = new ArrayList<>();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				list = pfmsSrvc.getPfmsWorkTransaction(regId, projId, finyear);
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/getWorkIdDetail", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getWorkIdDetail(HttpServletRequest request, @RequestParam("projId") Integer projId)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map = pfmsSrvc.getworkiddtl(projId);
		return map;
	}
	
	@RequestMapping(value = "/getWorkIdExpDetail", method = RequestMethod.POST)
	@ResponseBody
	public List<PfmsTransactionBean> getWorkIdExpDetail(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("id") Integer id){
		System.out.println("starting......");
		
		session = request.getSession(true);
		
		List<PfmsTransactionBean> list = new ArrayList<PfmsTransactionBean>();
		 if(session!=null && session.getAttribute("loginID")!=null) {	
				list = pfmsSrvc.getworkidexpdtl(id);
		 }
		 else {
			 list=null;
		 }
		return list;
	}
	
	
	@RequestMapping(value = "/getWorkIddraftDetail", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getWorkIddraftDetail(HttpServletRequest request, @RequestParam("wicode") List<Integer> wicode)
	{
		System.out.println("wicode:" +wicode);
		session = request.getSession(true);
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		 map = pfmsSrvc.getworkiddraftdtl(wicode); 
		return map;
	}
	
	@RequestMapping(value = "/saveasdraftpfmsworkid", method = RequestMethod.POST)
	@ResponseBody
	public String saveAsDraftPfmsTransaction(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="eatmisdataId") List<String> eatmisdataId, @RequestParam(value ="workid") List<String> workid, @RequestParam(value ="expenditure") List<String> expenditure,
			@RequestParam(value ="totalworks") List<Integer> totalworks){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
//		Integer projId= Integer.parseInt(request.getParameter("project"));
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				String createdby = session.getAttribute("loginID").toString();
				res = pfmsSrvc.saveAsDraftPfmsWorkId(workid, eatmisdataId, expenditure, totalworks, createdby, request); 
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}

	@RequestMapping(value = "/deletePfmsworkidTransaction", method = RequestMethod.POST)
	@ResponseBody
	public String deletePfmsworkidTransaction(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("eatmisdataId") Integer eatmisdataId){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = "fail";
		List<PfmsTransactionBean> list = new ArrayList<>();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = pfmsSrvc.deletePfmsworkTransaction(eatmisdataId);
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
	@RequestMapping(value = "/completePfmsWorkTransaction", method = RequestMethod.POST)
	@ResponseBody
	public String completePfmsWorkTransaction(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="eatmisdataId[]") String eatmisdataId[]){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = pfmsSrvc.completePfmsWorkIdTransaction(eatmisdataId);
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
}
