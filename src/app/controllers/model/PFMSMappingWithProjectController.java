package app.controllers.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import app.service.BlockMasterService;
import app.service.ProfileService;
import app.service.ProjectMasterService;
import app.service.master.PfmsService;

@Controller
public class PFMSMappingWithProjectController {
	
	HttpSession session;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	ProjectMasterService pmservice;
	
	@Autowired
	PfmsService pfmsSrvc;
	
	@RequestMapping(value ="/pfmsMappingWithProject", method = RequestMethod.GET)
	public ModelAndView pfmsMappingWithProject(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("pfmsMappingWithProject");
				
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				String userType = session.getAttribute("userType").toString();
				String userId = session.getAttribute("loginID").toString();
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
				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("stateName",stateName);
				if(userType.equals("SL")) {
					mav.addObject("projectList",pfmsSrvc.getProjectByStCode(stCode));
				}else if(userType.equals("DI")) {
					mav.addObject("projectList",pmservice.getProjNACByDcode(distCode));
				}else if(userType.equals("PI")) {
				mav.addObject("projectList",pmservice.getProjectByRegId(regId));
				}
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
	
	@RequestMapping(value = "/getPfmsTransaction", method = RequestMethod.POST)
	@ResponseBody
	public List<PfmsTransactionBean> getPfmsTransaction(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("masterId") Integer masterId, @ModelAttribute("projId") Integer projId){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String userType = session.getAttribute("userType").toString();

		List<PfmsTransactionBean> list = new ArrayList<>();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				list = pfmsSrvc.getPfmsTransaction(masterId, projId);
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
	
	@RequestMapping(value = "/saveAsDraftPfmsTransaction", method = RequestMethod.POST)
	@ResponseBody
	public String saveAsDraftPfmsTransaction(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("projId") Integer projId, @RequestParam(value ="eatmisdataId[]") String eatmisdataId[]){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
//		Integer projId= Integer.parseInt(request.getParameter("project"));
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = pfmsSrvc.saveAsDraftPfmsTransaction(eatmisdataId, projId);
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
	
	@RequestMapping(value = "/deletePfmsTransaction", method = RequestMethod.POST)
	@ResponseBody
	public String deletePfmsTransaction(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("eatmisdataId") Integer eatmisdataId){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = "fail";
		List<PfmsTransactionBean> list = new ArrayList<>();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = pfmsSrvc.deletePfmsTransaction(eatmisdataId);
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
	
	@RequestMapping(value = "/completePfmsTransaction", method = RequestMethod.POST)
	@ResponseBody
	public String completePfmsTransaction(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="eatmisdataId[]") String eatmisdataId[]){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = pfmsSrvc.completePfmsTransaction(eatmisdataId);
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
