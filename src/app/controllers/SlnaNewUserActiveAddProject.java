package app.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.User;
import app.bean.UserBean;
import app.bean.UserRoleMapBean;
import app.common.CommonFunctions;
import app.model.IwmpUserProjectMap;
import app.service.UserProjectService;
import app.service.UserRoleMapService;
import app.service.UserService;
import app.util.CommonUtility;

@Controller("SlnaNewUserActiveAddProject")
public class SlnaNewUserActiveAddProject {

	@Autowired(required = true)
	UserRoleMapService userRoleMapService;
	
	HttpSession session;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	private Map<String, String> useridList;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	CommonFunctions commonFunction;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	StateMasterController stateMasterController;
	
	@Autowired(required = true)
	IwmpUserProjectMap iwmpUserProjectMap;
	
	@Autowired
	public CommonUtility utility;
	
	@Autowired(required = true)
	UserProjectService userProjectService;
	
	Object[] showproject;
	// userRoleMap is used to get user role map page with all data
	@RequestMapping(value="/addExistUserProject", method = RequestMethod.GET)
	public ModelAndView userRoleMap(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		if(session!=null && session.getAttribute("loginid")!=null) {
			mav = new ModelAndView("user/slnauserrolemap");
			mav.addObject("menu", menuController.getMenuUserId(request));
			//mav.addObject("allApplicationList",userRoleMapService.getApplicationList());
			mav.addObject("allRoleList",userRoleMapService.getRoleList(session));
			districtList = userService.getDistrictList(Integer.parseInt(st_code));
			/*
			 * userType.put(null, "All"); userType.putAll(commonFunction.getUserTypeList());
			 * mav.addObject("userType",userType);
			 */
			mav.addObject("districtList", districtList);
			mav.addObject("roleId","6");
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/addExistUserProject", method = RequestMethod.POST)
	public ModelAndView userRoleMapPOST(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("user") User user)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		if(session!=null && session.getAttribute("loginid")!=null) {
			mav = new ModelAndView("user/slnauserrolemap");
			mav.addObject("menu", menuController.getMenuUserId(request));
			//mav.addObject("allApplicationList",userRoleMapService.getApplicationList());
			mav.addObject("allRoleList",userRoleMapService.getRoleList(session));
			districtList = userService.getDistrictList(Integer.parseInt(st_code));
			/*
			 * userType.put(null, "All"); userType.putAll(commonFunction.getUserTypeList());
			 * mav.addObject("userType",userType);
			 */
			mav.addObject("districtList", districtList);
			mav.addObject("roleId","6");
			mav.addObject("newuserid",user.getUserid());
			mav.addObject("newpassword",user.getPlainpassword());
			mav.addObject("status",user.getUserstatus());
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/newUserProjectAssign", method = RequestMethod.POST)
	public ModelAndView userProjectMap(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String distCode=request.getParameter("district");
		String st_code=session.getAttribute("stateCode").toString();
		String regId=request.getParameter("user");
		String projectName=null;
		String projId;
		String projConcat=null;
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		if(session!=null && session.getAttribute("loginid")!=null) 
		{
			mav = new ModelAndView("user/slnauserrolemap");
			mav.addObject("menu", menuController.getMenuUserId(request));
			//mav.addObject("allApplicationList",userRoleMapService.getApplicationList());
			mav.addObject("allRoleList",userRoleMapService.getRoleList(session));
			districtList = userService.getDistrictList(Integer.parseInt(st_code));
			projectList = userService.getUserProjectList(st_code, distCode);
			useridList = userService.getUseridList(st_code, distCode);
			if(regId!= null && regId!="") 
			{
				projConcat=userProjectService.showProject(Integer.parseInt(regId));
			}
			
			// System.out.println( "kedar= "+useridList);
			/*
			 * userType.put(null, "All"); userType.putAll(commonFunction.getUserTypeList());
			 * mav.addObject("userType",userType);
			 */
			mav.addObject("districtList", districtList);
			mav.addObject("projectList", projectList);
			mav.addObject("useridList", useridList);
			mav.addObject("district", distCode);
			mav.addObject("regId",regId);
			mav.addObject("projConcat",projConcat);
			mav.addObject("roleId","6");
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/saveUserProjectAssign", method = RequestMethod.POST)
	public ModelAndView saveUserProjectMap(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("userProjectMap") UserRoleMapBean userProjectMap)
	{
		session = request.getSession(true);
		String distCode=request.getParameter("district");
		
		String st_code=session.getAttribute("stateCode").toString();
		String regid = userProjectMap.getUser();
		String [] projList=userProjectMap.getProject().split(",");
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		
		if(session!=null && session.getAttribute("loginid")!=null) 
		{
			mav = new ModelAndView("user/slnauserrolemap");
			mav.addObject("menu", menuController.getMenuUserId(request));
			mav.addObject("allRoleList",userRoleMapService.getRoleList(session));
			userProjectMap.setUpdateby(session.getAttribute("loginid").toString());
			
			Integer i=userProjectService.saveUserProject(userProjectMap, session);
			if(i==1) {
				mav.addObject("message", "Project added Successfully !");
			}else {
				mav.addObject("message", "Project added Failed. Please try again !");
			}
			
			districtList = userService.getDistrictList(Integer.parseInt(st_code));
			projectList = userService.getUserProjectList(st_code, distCode);
			useridList = userService.getUseridList(st_code, distCode);
			// System.out.println( "kedar= "+useridList);
			mav.addObject("districtList", districtList);
			mav.addObject("projectList", projectList);
			mav.addObject("useridList", useridList);
			mav.addObject("district", distCode);
			mav.addObject("roleId","6");
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		//System.out.println("distCode: "+distCode+" user: "+regid);
		return mav; 
	}


}
