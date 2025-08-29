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

import app.bean.GroundWaterTableBean;
import app.bean.Login;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.unfreeze.UnfreezeGroundWaterService;

@Controller("UnfrzGroundWaterTableController")
public class UnfrzGroundWaterTableController {
	
	HttpSession session;
	
	@Autowired
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public UnfreezeGroundWaterService gwService;
	
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	private Map<Integer, String> finYear;

	
	@RequestMapping(value="/unfreezeGroundWaterTable", method=RequestMethod.GET)
	public ModelAndView unfreezeGroundWaterTable(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String proj = request.getParameter("proj");
		String level = request.getParameter("level");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("unfreeze/unfreezeGroundWaterTable");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			// Load States
			stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			// District List
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
				
			// Project List
			if(district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			// Dropdown for Level (Project / Baseline)
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("project", "During Project");
			map.put("basel", "Baseline Survey");
			
			mav.addObject("maphead", map);
			mav.addObject("proj", proj);
			mav.addObject("level", level);
			
			// Show financial year only if project is selected
			if("project".equalsIgnoreCase(level)) {
				finYear = userService.getCurrentFinYear();
				mav.addObject("finYear", finYear);
			}

		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 	
	}
	
	@RequestMapping(value="/unfreezeListGWT", method=RequestMethod.POST)
	public ModelAndView unfreezeListGWT(HttpServletRequest request, HttpServletResponse response) {
		
		session = request.getSession(true);

		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String proj = request.getParameter("proj");
		String level = request.getParameter("level");
		String fyear = request.getParameter("year");  // comes only for project
		List<GroundWaterTableBean> list = new ArrayList<>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeGroundWaterTable");
			
			// Call service based on level
			if("project".equalsIgnoreCase(level)) {
				list = gwService.unfreezeListGW(Integer.parseInt(proj), level, Integer.parseInt(district), Integer.parseInt(fyear));
				finYear = userService.getCurrentFinYear();
				mav.addObject("finYear", finYear);
			} else {
				list = gwService.unfreezeListGW(Integer.parseInt(proj), level, Integer.parseInt(district), null);
			}
			
			mav.addObject("gwList", list);
			mav.addObject("gwListSize", list.size());
			
			// Reload dropdowns
			stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			
			if(district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("project", "During Project");
			map.put("basel", "Baseline Survey");
			mav.addObject("maphead", map);
			mav.addObject("proj", proj);
			mav.addObject("level", level);
			mav.addObject("year", fyear);
			
		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	

	
	@RequestMapping(value="/unfreezeGWTData", method=RequestMethod.POST)
	public ModelAndView unfreezeGWTData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		

		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String proj = request.getParameter("proj");
		String level = request.getParameter("level");
		String proj_id[]= request.getParameterValues("proj_id");
		String fyear = request.getParameter("year");  // comes only for project
		
		boolean projId=false;
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeGroundWaterTable");
			
			if("project".equalsIgnoreCase(level)) {
				projId= gwService.unfreezeGWTData( proj_id, level, Integer.parseInt(district), Integer.parseInt(fyear));
				finYear = userService.getCurrentFinYear();
				mav.addObject("finYear", finYear);
			} else {
				projId= gwService.unfreezeGWTData( proj_id, level, Integer.parseInt(district), null);
			}
			if(projId ) 
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
			map.put("project", "During Project");
			map.put("basel", "Baseline Survey");
			mav.addObject("maphead", map);
			mav.addObject("proj", proj);
			mav.addObject("level", level);
			mav.addObject("year", fyear);
			mav.addObject("proj_id", proj_id);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
}

