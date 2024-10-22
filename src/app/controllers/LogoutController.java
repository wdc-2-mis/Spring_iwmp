/*
 * 20180227 RBSN084 Implemented Security against CSRF using random token
 * 20180226 RBSN082 Implemented Spring Security framework for Login Module
 */
package app.controllers;



import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.model.UserReg;
import app.service.LoginService;
import app.util.SessionUtility;



@Controller("logoutController")
public class LogoutController {
	@Autowired
	public LoginService loginService;
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logOut(Login login, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("login");
		String loginid = null;
	    String loginAtmpt = "Logout Successful";
	    String userSts = null;
	    boolean result = false;
	    HttpSession session = request.getSession(true);
	    String username=null;
	    List<UserReg> userList = new ArrayList<UserReg>();
	    
	    if(session!=null && session.getAttribute("username")!=null)
	    username=session.getAttribute("username").toString();
	    System.out.println("value of username:" +username);
	    
	    try {
	    	if(username!=null)
	    	{
	    	System.out.println("i am in :)");
	    	loginid = session.getAttribute("loginid").toString();
	    	userList = loginService.checkUser(loginid);
	    	result = loginService.insertloginlog(userList, loginAtmpt, userSts, request);
	    	
	    	}
		} catch (Exception ex) {
			  System.out.println("error on inserting:("+ex.getStackTrace()[0].getLineNumber());
			  System.out.println("error on inserting:("+ex.getMessage());
		}
		
		     session.invalidate();
		     SessionUtility.removeSession(loginid);
		     Cookie[] cookies = request.getCookies();
		     
		     if(cookies!=null)
		     for(Cookie cookie: cookies) {
			 cookie.setMaxAge(0);
			 cookie.setValue(null);
			 cookie.setPath("/");
			 response.addCookie(cookie);
			 response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			 response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			 response.setDateHeader("Expires", 0); // Proxies.
			 System.out.println("Deleted Cookie Is?= " + cookie.getName( ) + "\n");
		 }
		
		mav.addObject("login", new Login());
		/* mav.addObject("message", "Successfully logout !!"); */
		return mav;
	}
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logOut(@RequestParam("login") String login, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("login");
		String loginid = null;
	    String loginAtmpt = "Logout Successful";
	    String userSts = null;
	    boolean result = false;
	    HttpSession session = request.getSession(true);
	    String username=null;
	    List<UserReg> userList = new ArrayList<UserReg>();
	    System.out.println("logout "+login);
	    username=login;
	    if(session!=null && session.getAttribute("username")!=null)
	    username=session.getAttribute("username").toString();
	    System.out.println("value of username:" +username);
	    
	    try {
	    	if(username!=null)
	    	{
	    	System.out.println("i am in :)");
	    	loginid = username;
	    	userList = loginService.checkUser(loginid);
	    	result = loginService.insertloginlog(userList, loginAtmpt, userSts, request);
	    	
	    	}
		} catch (Exception ex) {
			  System.out.println("error on inserting:("+ex.getStackTrace()[0].getLineNumber());
			  System.out.println("error on inserting:("+ex.getMessage());
		}
		
		    session.invalidate();
		     SessionUtility.removeSession(loginid);
		     Cookie[] cookies = request.getCookies();
		     
		     if(cookies!=null)
		     for(Cookie cookie: cookies) {
			 cookie.setMaxAge(10);
			 cookie.setValue(null);
			 cookie.setPath("/");
			 response.addCookie(cookie);
			 response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			 response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			 response.setDateHeader("Expires", 0); // Proxies.
			 System.out.println("Deleted Cookie Is?= " + cookie.getName( ) + "\n");
		 }
		     //session.invalidate();
		mav.addObject("login", new Login());
		/* mav.addObject("message", "Successfully logout !!"); */
		return mav;
	}
	
}
