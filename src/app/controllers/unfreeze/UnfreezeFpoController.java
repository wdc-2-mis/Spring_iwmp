package app.controllers.unfreeze;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.FPOReportBean;
import app.bean.Login;
import app.bean.ShgDetailBean;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.unfreeze.UnfreezeFpoDetailService;

@Controller("UnfreezeFpoController")
public class UnfreezeFpoController {
	
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public UnfreezeFpoDetailService fpoService;
	
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	
	@RequestMapping(value="/unfreezeFPODetails", method=RequestMethod.GET)
	public ModelAndView unfreezeFPODetails(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String head = request.getParameter("head");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("unfreeze/unfreezeFpoDetails");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
				
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("newFPO", "Newly created FPO");
			map.put("oldFPO", "Existing FPO");
			
			mav.addObject("maphead", map);
			mav.addObject("project", project);
			mav.addObject("head", head);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 	
	}


	@RequestMapping(value="/unfreezeListFpoDetails", method=RequestMethod.POST)
	public ModelAndView unfreezeListFpoDetails(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);

		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String group = request.getParameter("head");
		
		List<FPOReportBean> list = new  ArrayList<FPOReportBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeFpoDetails");
			
			list = fpoService.unfreezeFpoDetail(Integer.parseInt(project), group);
			
			mav.addObject("fpoList",list);
			mav.addObject("fpoListSize", list.size());
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("newFPO", "Newly Created FPO");
			map.put("oldFPO", "Existing FPO");
			mav.addObject("maphead", map);
			mav.addObject("project", project);
			mav.addObject("head", group);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	
	@RequestMapping(value="/unfreezeFpoDetailsData", method=RequestMethod.POST)
	public ModelAndView unfreezeFpoDetailsData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String fpo_id[]= request.getParameterValues("fpo_detail_id");
		String group =  request.getParameter("head");
		
		boolean fpoId=false;
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeFpoDetails");
			
			fpoId= fpoService.unfreezeFpoDetailsData(fpo_id, group);
			if(fpoId ) 
					mav.addObject("messageUpload", "Data Unfreezed Successfully!");
			else
					mav.addObject("messageUpload", "Data not Unfreeze!");
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("newFPO", "Newly Created FPO");
			map.put("oldFPO", "Existing FPO");
			mav.addObject("maphead", map);
			mav.addObject("project", project);
			mav.addObject("head", group);
			mav.addObject("fpo_detail_id", fpo_id);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
}
