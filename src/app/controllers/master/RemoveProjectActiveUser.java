package app.controllers.master;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.UserRoleMapBean;
import app.controllers.MenuController;
import app.controllers.StateMasterController;
import app.service.StateMasterService;
import app.service.UserRoleMapService;
import app.service.UserService;
import app.service.master.RemoveActiveUserProjectService;

@Controller("RemoveProjectActiveUser")
public class RemoveProjectActiveUser {

	@Autowired(required = true)
	UserRoleMapService userRoleMapService;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public RemoveActiveUserProjectService userProjectService;
	
	HttpSession session;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	private Map<String, String> useridList;
	
	
	@RequestMapping(value="/removeProjectActiveUser", method = RequestMethod.GET)
	public ModelAndView removeProjectActiveUser(HttpServletRequest request, HttpServletResponse response)
	{
		
		String state=null;
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		state= request.getParameter("state");
		if(userType.equals("SL"))
		state=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
	//	LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		if(session!=null && session.getAttribute("loginid")!=null) 
		{
			try {
					mav = new ModelAndView("master/removeProjectActiveUser");
					//mav.addObject("menu", menuController.getMenuUserId(request));
					//mav.addObject("allApplicationList",userRoleMapService.getApplicationList());
					//mav.addObject("allRoleList",userRoleMapService.getRoleList());
					mav.addObject("stateList",stateMasterService.getAllState());
					if(state !=null) {
						districtList = userService.getDistrictList(Integer.parseInt(state));
						mav.addObject("districtList", districtList);
					}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/removeProjectActiveUser", method = RequestMethod.POST)
	public ModelAndView userProjectRemove(HttpServletRequest request, HttpServletResponse response)
	{
		String state=null;
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		state= request.getParameter("state");
		if(userType.equals("SL"))
		state=session.getAttribute("stateCode").toString();
		String distCode=request.getParameter("district");
		String regId=request.getParameter("user");
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
	//	LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		if(session!=null && session.getAttribute("loginid")!=null) 
		{
			try {
				mav = new ModelAndView("master/removeProjectActiveUser");
				//mav.addObject("menu", menuController.getMenuUserId(request));
				//mav.addObject("allApplicationList",userRoleMapService.getApplicationList());
				//mav.addObject("allRoleList",userRoleMapService.getRoleList());
				mav.addObject("stateList",stateMasterService.getAllState());
				if(state !=null) {
					districtList = userService.getDistrictList(Integer.parseInt(state));
					mav.addObject("districtList", districtList);
				}
				if(distCode!=null && !distCode.equals("")) {
					useridList = userService.getUseridList(state, distCode);
					mav.addObject("useridList", useridList);
				}
				if(regId!=null && !regId.equals("")) {
					projectList = userProjectService.getUserProjectList(regId);
					mav.addObject("projectList", projectList);
				}	
			
				mav.addObject("statecd", state);
				mav.addObject("district", distCode);
				mav.addObject("regId",regId);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/deleteActiveUserProject", method = RequestMethod.POST)
	public ModelAndView deleteActiveUserProject(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("userProjectMap") UserRoleMapBean userProjectMap)
	{
		String state=null;
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		state= request.getParameter("state");
		if(userType.equals("SL"))
		state=session.getAttribute("stateCode").toString();
		String distCode=request.getParameter("district");
		String regId=request.getParameter("user");
	//	String st_code=session.getAttribute("stateCode").toString();
		//String regid = userProjectMap.getUser();
		String project=request.getParameter("project");
		//String [] projList=userProjectMap.getProject().split(",");
		ModelAndView mav = new ModelAndView();
	//	LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		
		if(session!=null && session.getAttribute("loginid")!=null) 
		{
			mav = new ModelAndView("master/removeProjectActiveUser");
		//	mav.addObject("menu", menuController.getMenuUserId(request));
		//	mav.addObject("allRoleList",userRoleMapService.getRoleList());
		//	userProjectMap.setUpdateby(session.getAttribute("loginid").toString());
			
			Integer i=userProjectService.userProjectRemove(regId, project);
			if(i==1) {
				mav.addObject("message", "Project remove Successfully !");
			}else {
				mav.addObject("message", "Project remove Failed. Please try again !");
			}
			
			mav.addObject("stateList",stateMasterService.getAllState());
			if(state !=null) {
				districtList = userService.getDistrictList(Integer.parseInt(state));
				mav.addObject("districtList", districtList);
			}
			if(distCode!=null && !distCode.equals("")) {
				useridList = userService.getUseridList(state, distCode);
				mav.addObject("useridList", useridList);
			}
			if(regId!=null && !regId.equals("")) {
				projectList = userProjectService.getUserProjectList(regId);
				mav.addObject("projectList", projectList);
			}	
		
			mav.addObject("statecd", state);
			mav.addObject("district", distCode);
			mav.addObject("regId",regId);
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		//System.out.println("distCode: "+distCode+" user: "+regid);
		return mav; 
	}
}
