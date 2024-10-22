package app.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import app.bean.Login;
import app.bean.User;
import app.bean.UserRoleMapBean;
import app.common.CommonFunctions;
import app.model.IwmpAppRoleMap;
import app.model.IwmpMProject;
import app.model.IwmpUserAppRoleMap;
import app.model.IwmpUserProjectMap;
import app.model.UserMap;
import app.model.UserReg;
import app.service.UserRoleMapService;


@Controller("userRoleMapController")
public class UserRoleMapController {

	@Autowired(required = true)
	UserRoleMapService userRoleMapService;
	
	HttpSession session;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	CommonFunctions commonFunction;
	
	
	@Autowired(required = true)
	StateMasterController stateMasterController;
	
	// userRoleMap is used to get user role map page with all data
	@RequestMapping(value="/userRoleMap", method = RequestMethod.GET)
	public ModelAndView userRoleMap(HttpServletRequest request)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		if(session!=null && session.getAttribute("loginid")!=null) {
			mav = new ModelAndView("user/userrolemap");
			//mav.addObject("menu", menuController.getMenuUserId(request));
			//mav.addObject("allApplicationList",userRoleMapService.getApplicationList());
			mav.addObject("allRoleList",userRoleMapService.getRoleList(session));
			userType.put(null, "All");
			userType.putAll(commonFunction.getUserTypeList());
			
			mav.addObject("userType",userType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	 
	@RequestMapping(value="/userRoleMap", method = RequestMethod.POST)
	public ModelAndView userRoleMap(HttpServletRequest request,@ModelAttribute("user") User user)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		if(session!=null && session.getAttribute("loginid")!=null) {
			mav = new ModelAndView("user/userrolemap");
			//mav.addObject("menu", menuController.getMenuUserId(request));
			//mav.addObject("allApplicationList",userRoleMapService.getApplicationList());
			mav.addObject("allRoleList",userRoleMapService.getRoleList(session));
			userType.put(null, "All");
			userType.putAll(commonFunction.getUserTypeList());
			mav.addObject("newuserid",user.getUserid());
			mav.addObject("newpassword",user.getPlainpassword());
			mav.addObject("username",user.getUsername());
			mav.addObject("status",user.getUserstatus());
			mav.addObject("userType",userType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	// getUserListUnAssigned is used to get list of unassigned user by providing user type
	@RequestMapping(value="/getUserListUnAssigned", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,String> getUserListUnAssigned(HttpServletRequest request, @RequestParam(value ="id") String userType)
	{
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		map.putAll(userRoleMapService.getUserListUnAssigned(userType));
		return map;
	}
	
	// getUserListAssigned is used to get list of assigned user by providing user type
	@RequestMapping(value="/getUserListAssigned", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,String> getUserListAssigned(HttpServletRequest request, @RequestParam(value ="id") String userType)
	{
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		System.out.println("getUserListAssigned");
		map.putAll(userRoleMapService.getUserListAssigned(userType));
		return map;
	}

	// getUserListUnAssignedByState is used to get list of unassigned user  by providing state code and user type
	@RequestMapping(value="/getUserListUnAssignedByState", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,String> getUserListUnAssignedByState(HttpServletRequest request, @RequestParam(value ="userType") String userType, @RequestParam(value ="stateCode") Integer stateCode)
	{
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		map.putAll(userRoleMapService.getUserListUnAssignedByState(userType,stateCode));
		return map;
	}
	
	// // getUserListAssignedByState is used to get list of assigned user  by providing state code and user type
	@RequestMapping(value="/getUserListAssignedByState", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,String> getUserListAssignedByState(HttpServletRequest request, @RequestParam(value ="userType") String userType, @RequestParam(value ="stateCode") Integer stateCode)
	{
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		System.out.println("getUserListAssignedByState");
		map.putAll(userRoleMapService.getUserListAssignedByState(userType,stateCode));
		return map;
	}
	
	// getUserListUnAssignedByDistrict is used to get list of unassigned user  by providing district code, state code and user type
	@RequestMapping(value="/getUserListUnAssignedByDistrict", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,String> getUserListUnAssignedByDistrict(HttpServletRequest request, @RequestParam(value ="distCode") Integer distCode, @RequestParam(value ="userType") String userType, @RequestParam(value ="stateCode") Integer stateCode)
	{
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		map.putAll(userRoleMapService.getUserListUnAssignedByDistrict(userType,stateCode,distCode));
		return map;
	}
	
	// // getUserListAssignedByDistrict is used to get list of assigned user  by providing district code, state code and user type
	@RequestMapping(value="/getUserListAssignedByDistrict", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,String> getUserListAssignedByDistrict(HttpServletRequest request, @RequestParam(value ="distCode") Integer distCode, @RequestParam(value ="userType") String userType, @RequestParam(value ="stateCode") Integer stateCode)
	{
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		System.out.println("getUserListAssignedByDistrict");
		map.putAll(userRoleMapService.getUserListAssignedByDistrict(userType,stateCode,distCode));
		return map;
	}
	
	// getProjectByDistrict is used to get list of project by providing district code and state code
	@RequestMapping(value="/getProjectByDistrict", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getProjectByDistrict(HttpServletRequest request, @RequestParam(value ="distCode") Integer distCode, @RequestParam(value ="stateCode") Integer stateCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(userRoleMapService.getProjectByDistrict(stateCode,distCode));
		System.out.println("Map: "+map.size());
		return map;
	}
	
	// getRoleAssignedForUser is used to get the role assigned for user by providing user regId
	@RequestMapping(value="/getRoleAssignedForUser", method = RequestMethod.POST)
	@ResponseBody
	public List<LinkedHashMap<Integer,String>> getRoleAssignedForUser(HttpServletRequest request, @RequestParam(value ="regId") String regId)
	{
		List<LinkedHashMap<Integer,String>> list = new ArrayList<LinkedHashMap<Integer,String>>();
		Session sessionf = sessionFactory.getCurrentSession();
		sessionf.beginTransaction();
		UserReg userReg = (UserReg) sessionf.get(UserReg.class, Integer.parseInt(regId));
		sessionf.getTransaction().commit();
		list.add(userRoleMapService.getRoleAssignedForUser(userReg.getUserId()));
		list.add(userRoleMapService.getRoleList(session));
		System.out.println("Map: "+list.size());
		return list;
	}
	
	// getProjectByUser is used to get the list of projects by providing user regId, state code and district code
	@RequestMapping(value="/getProjectByUser", method = RequestMethod.POST)
	@ResponseBody
	public List<LinkedHashMap<Integer,String>> getProjectByUser(HttpServletRequest request, @RequestParam(value ="user") Integer regId, @RequestParam(value ="distCode") Integer distCode, @RequestParam(value ="stateCode") Integer stateCode)
	{
		List<LinkedHashMap<Integer,String>> list = new ArrayList<LinkedHashMap<Integer,String>>();
		//if(stateCode !=null && distCode !=null) {
		list.add(userRoleMapService.getProjectByUser(regId));
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		System.out.println("StateCode "+stateCode+" distCode "+distCode);
		UserReg userReg = (UserReg) session.get(UserReg.class, regId);
		session.getTransaction().commit();
		if(stateCode==null || stateCode==0) {
			for (UserMap umap : userReg.getIwmpUserMaps()) {
				stateCode=(int) umap.getIwmpState().getStCode();
			}
		}
		if(distCode==null || distCode==0) {
			for (UserMap umap : userReg.getIwmpUserMaps()) {
				distCode=umap.getIwmpDistrict().getDistCode();
			}
		}
		System.out.println("StateCode2 "+stateCode+" distCode2 "+distCode +" reg_id="+userReg.getRegId()+" tempdcode: ");
		list.add(userRoleMapService.getProjectByDistrict(stateCode,distCode));
		System.out.println("Map: "+list.size());
		//}
		return list;
	}
	
	// saveUnassignedUser is used to save the role of unassigned user.
	@RequestMapping(value="/saveUnassignedUser", method = RequestMethod.POST)
	public ModelAndView saveUnassignedUser(HttpServletRequest request, @ModelAttribute("userRoleMap") UserRoleMapBean userRoleMap) throws UnknownHostException
	{
		LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		userType.put(null, "All");
		userType.putAll(commonFunction.getUserTypeList());
		InetAddress inetAddress = InetAddress.getLocalHost(); 
		ModelAndView mav = new ModelAndView("user/userrolemap");
		session = request.getSession(true);
		
		
		String[] projectArray =null;
		if(session.getAttribute("loginid")!=null) {
		try {
		userRoleMap.setUpdateby(session.getAttribute("loginid").toString());
		userRoleMap.setRequestip(inetAddress.getHostAddress());
		System.out.println("UserRoleMap User_Id: "+userRoleMap.getUser()+" Role_Id: "+userRoleMap.getRole()+" App_Id: "
		+userRoleMap.getApplication()+" updateBy: "+userRoleMap.getUpdateby()+" requestIp: "+userRoleMap.getRequestip()+" project: "+userRoleMap.getProject()+
		" State: "+userRoleMap.getState()+" District: "+userRoleMap.getDistrict()+" Type: "+userRoleMap.getUsertype());
		if(userRoleMap.getProject()!=null)
		projectArray = userRoleMap.getProject().split(",");
		boolean resUserRoleMap= userRoleMapService.saveUserRoleMap(userRoleMap,projectArray);
		Session sessionF = sessionFactory.getCurrentSession();
		sessionF.beginTransaction();
		IwmpAppRoleMap iwmpAppRoleMap = (IwmpAppRoleMap) sessionF.get(IwmpAppRoleMap.class, Integer.parseInt(userRoleMap.getRole().toString()));
		UserReg userReg = (UserReg) sessionF.get(UserReg.class, Integer.parseInt(userRoleMap.getUser().toString()));
		sessionF.getTransaction().commit();
		if(resUserRoleMap) {
			mav.addObject("message",iwmpAppRoleMap.getRoleName()+" has been Assigned Successfully ! For user "+userReg.getUserId());
			mav.addObject("status","success");
		}else {
			mav.addObject("message","There is some error while assigning role of user !");
			mav.addObject("status","fail");
		}
		//mav.addObject("menu", menuController.getMenuUserId(request));
		//mav.addObject("allApplicationList",userRoleMapService.getApplicationList());
		mav.addObject("userType",userType);
		mav.addObject("allRoleList",userRoleMapService.getRoleList(session));
		
		}catch(Exception ex) {
			
		}finally {
			//sessionF.flush();
			//sessionF.close();
		}
		}else
			mav = new ModelAndView("/login");
		return mav;
	}
	
	// updateAssignedUser is used to update the details of already role assigned user
	@RequestMapping(value="/updateAssignedUser", method = RequestMethod.POST)
	public ModelAndView updateAssignedUser(HttpServletRequest request, @ModelAttribute("userRoleMap") UserRoleMapBean userRoleMap) throws UnknownHostException
	{
		LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		userType.put(null, "All");
		userType.putAll(commonFunction.getUserTypeList());
		InetAddress inetAddress = InetAddress.getLocalHost(); 
		ModelAndView mav = new ModelAndView("user/userrolemap");
		session = request.getSession(true);
		
		String[] projectArray =null;
		try {
		userRoleMap.setUpdateby(session.getAttribute("loginid").toString());
		userRoleMap.setRequestip(inetAddress.getHostAddress());
		System.out.println("UserRoleMap User_Id: "+userRoleMap.getUser()+" Role_Id: "+userRoleMap.getRole()+" App_Id: "
		+userRoleMap.getApplication()+" updateBy: "+userRoleMap.getUpdateby()+" requestIp: "+userRoleMap.getRequestip()+" project: "+userRoleMap.getProject()+
		" State: "+userRoleMap.getState()+" District: "+userRoleMap.getDistrict()+" Type: "+userRoleMap.getUsertype());
		if(userRoleMap.getProject()!=null && !userRoleMap.getProject().trim().equals(""))
		projectArray = userRoleMap.getProject().split(",");
		boolean resUserRoleMap= userRoleMapService.updateUserRoleMap(userRoleMap,projectArray);
		Session sessionF = sessionFactory.getCurrentSession();
		sessionF.beginTransaction();
		IwmpAppRoleMap iwmpAppRoleMap = (IwmpAppRoleMap) sessionF.get(IwmpAppRoleMap.class, Integer.parseInt(userRoleMap.getRole().toString()));
		UserReg userReg = (UserReg) sessionF.get(UserReg.class, Integer.parseInt(userRoleMap.getUser().toString()));
		sessionF.getTransaction().commit();
		if(resUserRoleMap) {
			mav.addObject("message",iwmpAppRoleMap.getRoleName()+" has been Assigned Successfully ! For user "+userReg.getUserId());
			mav.addObject("status","success");
		}else {
			mav.addObject("message","There is some error while assigning role of user !");
			mav.addObject("status","fail");
		}
		//mav.addObject("menu", menuController.getMenuUserId(request));
		//mav.addObject("allApplicationList",userRoleMapService.getApplicationList());
		mav.addObject("userType",userType);
		mav.addObject("allRoleList",userRoleMapService.getRoleList(session));
		}catch(Exception ex) {
			
		}finally {
			//sessionF.flush();
			//sessionF.close();
		}
		return mav;
	}
	
	// getUserTypeFromDB is used to get list of userType from database
		@RequestMapping(value="/getUserTypeFromDB", method = RequestMethod.POST)
		@ResponseBody
		public LinkedHashMap<Integer,String> getUserTypeFromDB(HttpServletRequest request)
		{
			LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
			map.putAll(userRoleMapService.getSpecificRoleList());
			return map;
		}
}
