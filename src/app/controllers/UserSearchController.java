package app.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import app.bean.User;
import app.common.CommonFunctions;
import app.model.UserReg;
import app.service.UserSearchService;


@Controller("userSearchController")
public class UserSearchController {
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	CommonFunctions commonFunction;
	
	@Autowired(required = true)
	UserSearchService userSearchService;
	
	@Autowired(required = true)
	User user;
	
	@Value("${password_length}")
	Integer passwordLength;
	
	@Autowired
	private SessionFactory sessionFactory;

	 HttpSession session;
	 
	// userSearchGet is used to get the user search page
	@RequestMapping(value="/usersrch", method = RequestMethod.GET)
	public ModelAndView userSearchGet(HttpServletRequest request)
	{
		session=request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginid")!=null) {
		mav = new ModelAndView("user/usersearch");
		//mav.addObject("menu", menuController.getMenuUserId(request));
		mav.addObject("userTypeList", commonFunction.getUserTypeList());
		mav.addObject("userStatusList", commonFunction.getUserStatus());
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	// getUser is used to get the user list on search button clicked or after activation of user
	@RequestMapping(value="/usersrch", method = RequestMethod.POST)
	public ModelAndView getUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("userReg") UserReg userReg, @ModelAttribute("user") User user)
	{
		System.out.println("usereg: "+userReg.getUserId()+" : "+userReg.getUserName()+" : "+userReg.getDepartment()+" : "+userReg.getDesignation()+" : "+userReg.getEmail()+" : "+userReg.getUserType()+" : "+userReg.getStatus());
		ModelAndView mav = new ModelAndView("user/usersearch");
		//mav.addObject("menu", menuController.getMenuUserId(request));
		mav.addObject("userList", userSearchService.getUserList(userReg));
		mav.addObject("newuserid", user.getUserid());
		mav.addObject("username", user.getUsername());
		mav.addObject("status", user.getUserstatus());
		mav.addObject("userTypeList", commonFunction.getUserTypeList());
		mav.addObject("userStatusList", commonFunction.getUserStatus());
		return mav; 
		
	}
	
	// activateUser is used to activate the user
	@RequestMapping(value="/activateUser", method = RequestMethod.POST)
	@ResponseBody
	public List<String> activateUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("userReg") UserReg userReg) throws UnknownHostException
	{
		List<String> list = new ArrayList<String>();
		HttpSession session=request.getSession();    
		InetAddress inetAddress = InetAddress.getLocalHost(); 
		String ipadd=inetAddress.getHostAddress(); 
		System.out.println("usereg: "+userReg.getRegId()+" : "+userReg.getUserType()+" : "+userReg.getUserName()+" : "+userReg.getDepartment()+" : "+userReg.getDesignation()+" : "+userReg.getEmail()+" : "+userReg.getUserType()+" : "+userReg.getStatus()+" : "+userReg.getUserId());
		ModelAndView mav = new ModelAndView("user/usersearch");
		//mav.addObject("menu", menuController.getMenuUserId(request));
		user=userSearchService.getUserByRegIdForUserBean(userReg.getRegId());
		System.out.println("Old UserId: "+user.getUserid());
		if(user.getUserid()==null) {
			Integer count = userSearchService.getNextUserNo();
			commonFunction.getUserId(user, count);
			String plainPassword=commonFunction.getPasswordString(passwordLength);
			String encryptedPassword=commonFunction.getEncryptedPassword(plainPassword);
			encryptedPassword=user.getUserid()+encryptedPassword;
			encryptedPassword=commonFunction.getEncryptedPassword(encryptedPassword);
			user.setEncryptedpassword(encryptedPassword);
			user.setPlainpassword(plainPassword);
			list.add(user.getPlainpassword());
		}
		
		
		userReg.setUserId(user.getUserid());
		userReg.setEncryptedPass(user.getEncryptedpassword());
		userReg.setAuthorizerId(session.getAttribute("loginid").toString());
		userReg.setRequestIp(ipadd);		
		boolean result = userSearchService.activateUser(userReg);
		
		if(result) {
		list.add(user.getUserid());
		list.add(user.getUsername());
		}
		return list; 
		
	}
	
	// inActivateUser is used to in activate the user
	@RequestMapping(value="/inActivateUser", method = RequestMethod.POST)
	@ResponseBody
	public List<String> inActivateUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("userReg") UserReg userReg) throws UnknownHostException
	{
		List<String> list = new ArrayList<String>();
		HttpSession session=request.getSession();    
		System.out.println("usereg: "+userReg.getRegId()+" : "+userReg.getUserType()+" : "+userReg.getUserName()+" : "+userReg.getDepartment()+" : "+userReg.getDesignation()+" : "+userReg.getEmail()+" : "+userReg.getUserType()+" : "+userReg.getStatus()+" : "+userReg.getUserId());
		ModelAndView mav = new ModelAndView("user/usersearch");
		//mav.addObject("menu", menuController.getMenuUserId(request));
		Session sessionF = sessionFactory.getCurrentSession();
		sessionF.beginTransaction();
		UserReg user = (UserReg) sessionF.get(UserReg.class, userReg.getRegId());
		sessionF.getTransaction().commit();
		userReg.setLastUpdatedBy(session.getAttribute("loginid").toString());
		boolean result = userSearchService.inActivateUser(userReg);
		if(result) {
		list.add(user.getUserId());
		list.add(user.getUserName());
		}
		//sessionF.flush();
		//sessionF.close();
		return list; 
		
	}
	
	// deleteUser is used to delete the user
	@RequestMapping(value="/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public List<String> deleteUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="regId") Integer regId) throws UnknownHostException
	{
		List<String> list = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("user/usersearch");
		//mav.addObject("menu", menuController.getMenuUserId(request));
		Session sessionF = sessionFactory.getCurrentSession();
		sessionF.beginTransaction();
		UserReg userReg = (UserReg) sessionF.get(UserReg.class, regId);
		sessionF.getTransaction().commit();
		//System.out.println("usereg: "+userReg.getRegId()+" : "+userReg.getUserType()+" : "+userReg.getUserName()+" : "+userReg.getDepartment()+" : "+userReg.getDesignation()+" : "+userReg.getEmail()+" : "+userReg.getUserType()+" : "+userReg.getStatus()+" : "+userReg.getUserId());
		boolean result = userSearchService.deleteUser(userReg);
		if(result) {
		list.add(userReg.getUserName());
		}
		//sessionF.flush();
		//sessionF.close();
		return list; 
		
	}
	
}
