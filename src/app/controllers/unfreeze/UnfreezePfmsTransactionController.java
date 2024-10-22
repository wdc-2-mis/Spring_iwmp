package app.controllers.unfreeze;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import app.controllers.MenuController;
import app.service.ProfileService;
import app.service.ProjectMasterService;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.master.PfmsService;
import app.service.reports.TargetAchievementQuarterService;
import app.service.unfreeze.UnfreezePfmsTransactionMappingService;

@Controller
public class UnfreezePfmsTransactionController {
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	ProjectMasterService pmservice;
	
	@Autowired
	PfmsService pfmsSrvc;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@Autowired
	public UnfreezePfmsTransactionMappingService unfreezePfmsSrvc;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> ProjectList;
	
	@RequestMapping(value ="/unfreezePfmsMappingWithProject", method = RequestMethod.GET)
	public ModelAndView unfreezePfmsMappingWithProject(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezePfmsMappingWithProject");
//			mav.addObject("menu", menuController.getMenuUserId(request));
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);}
				mav.addObject("project", project);
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	
	}
	
	@RequestMapping(value="/getCompletedPfmsTransaction", method = RequestMethod.POST)
	@ResponseBody
	public List<PfmsTransactionBean> getCompletedPfmsTransaction(HttpServletRequest request, HttpServletResponse response, @RequestParam("projId") Integer project)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		
		List<PfmsTransactionBean> list=new  ArrayList<PfmsTransactionBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
//			mav = new ModelAndView("unfreeze/unfreezePfmsMappingWithProject");
//			mav.addObject("menu", menuController.getMenuUserId(request));
			
			list=unfreezePfmsSrvc.getCompPfmsTranMapWithProj(project);
			
//			stateList=stateMasterService.getAllState();
//			mav.addObject("stateList", stateList);
//			mav.addObject("state", userState);
			
//			if(userState!=null && !userState.equals(0)) {
//			districtList = userService.getDistrictList(userState);
//			mav.addObject("districtList", districtList);}
//			mav.addObject("district", district);
			
//			if( district!=null && !district.equals(0)) {
//				ProjectList = userService.getProjectList(userState, district);
//				mav.addObject("ProjectList", ProjectList);}
//				mav.addObject("project", project);
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return list; 
	}
	
	
	@RequestMapping(value="/unfreezePfmsMappingWithWorkId", method = RequestMethod.GET)
	public ModelAndView unfreezePfmsMappingWithWorkId(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String finCode = request.getParameter("year");
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezePfmsMappingWithWorkId");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			mav.addObject("financialYear", ser.getYearonward22());
			mav.addObject("year", finCode);
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);
			}
			mav.addObject("project", project);
				
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value = "/UpdateAsDraftPfmsTransaction", method = RequestMethod.POST)
	@ResponseBody
	public String UpdateAsDraftPfmsTransaction(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("projId") Integer projId, @RequestParam(value ="eatmisdataId[]") String eatmisdataId[]){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
//		Integer projId= Integer.parseInt(request.getParameter("project"));
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = unfreezePfmsSrvc.updateAsDraftPfmsTransaction(eatmisdataId, projId);
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
	

	@RequestMapping(value="/getCompPfmsTranMapWithWorkId", method = RequestMethod.POST)
	@ResponseBody
	public List<PfmsTransactionBean> getCompPfmsTranMapWithWorkId(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("projId") Integer project, @ModelAttribute("yearId") Integer yearId)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		int finyear = 0;
		List<PfmsTransactionBean> list=new  ArrayList<PfmsTransactionBean>();
		int aa=yearId+1;
		String fyear = "20"+aa;
		finyear = Integer.parseInt(fyear);
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			list=unfreezePfmsSrvc.getCompPfmsTranMapWithWorkId(finyear, project);
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return list; 
	}

}
