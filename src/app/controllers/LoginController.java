package app.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import app.model.UserReg;
import app.model.IwmpLoginLog;
import app.bean.AuditReportBean;
import app.bean.Login;
import app.bean.ProfileBean;
import app.bean.User;
import app.bean.UserBean;

import app.service.LoginService;
import app.service.ProfileService;
import app.service.StateMasterService;
import app.service.UserService;
import app.util.SHA512;
import app.util.SessionUtility;

@Controller("loginController")
public class LoginController {
	@Autowired(required=true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	private LinkedHashMap<Integer, String> stateList;
	
	/*
	 * @Value("${login.failure.alert}") String loginFail;
	 */
	

	
	// getRequestHeadersInMap will return all headers
	private Map<String, String> getRequestHeadersInMap(HttpServletRequest request) {

		Map<String, String> result = new HashMap<>();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			result.put(key, value);
		}

		return result;
	}

	UserService userService;
	HttpSession session;

	@Autowired
	public LoginService loginService;

	// login will verify user credentials and return success page after logged in
	@SuppressWarnings("null")
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	@ResponseBody
	public String login(@ModelAttribute("login") Login login, HttpServletRequest request,
			HttpServletResponse response, BindingResult result1, @RequestParam("salt") String salt) {

		session = request.getSession(true);
		String captcha = (String) session.getAttribute("CAPTCHA");
		String password = null;
		boolean result = false;
		boolean flagLoginSuccess = false;
		Integer count = 0;
		//Logger logger = Logger.getRootLogger();
		String userSts = null;
		ResourceBundle appresourcesprop = null;
		String loginid = null;
		boolean userExists = false;
		List<UserReg> userList = new ArrayList<UserReg>();
		 
		String CSRFcodeSes = (String) session.getAttribute("CAPTCHA");
		if (captcha != null && !captcha.equals(login.getCaptcha())) {
			login.setCaptcha("");
			return "captchaerror";
		}

		 loginid = login.getUser_id();
		 
		password = login.getEncrypted_pass();
		
		/*
		 * String message=SessionUtility.isAlreadyLoggedInOtherTab(loginid, session);
		 * if(!message.equalsIgnoreCase("no")) {
		 * request.setAttribute("alreadyLoginMessage", message); return "loginbrowser";
		 * }
		 */
		 
		String loginid1 = (String) session.getAttribute("loginID");
		//System.out.println("value of loginid1: " + loginid1);
		
		 if(SessionUtility.isAlreadyLoggedIn(loginid,session))
		  { 
			 System.out.println("already login with same login id:"); 
			 return "alreadylogin"; 
		  }
		
		try {
			userList = loginService.checkUser(login.getUser_id());
			if(userList.size()>0)
			userExists = true;
			System.out.println("user exists:" +userExists+"CSRFCode: "+ CSRFcodeSes);
			if (userExists) 
			{
				UserBean userRegisterationSession= null;
				InetAddress inetAddress = InetAddress.getLocalHost(); 
				String ipadd=inetAddress.getHostAddress();
				result = loginService.authenticateUser(loginid, password, salt, request);
				if (result) 
				{
					try {
						  String loginAtmpt = "success";
						  result = loginService.insertloginlog(userList, loginAtmpt, userSts, request);
							
						  userRegisterationSession = loginService.authenticatedUser(loginid, password, salt, request);
						  if(userRegisterationSession != null){
                             // logger.info("User validation successful.");
                              session= request.getSession(true);
                              userRegisterationSession.setSessionCreationTime(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(Calendar.getInstance().getTime()));
                              userRegisterationSession.setRequestIP(ipadd);
                              userRegisterationSession.setReferrer(request.getHeader("referer"));
                              userRegisterationSession.setUserAgent(request.getHeader( "user-agent"));
                              session.setAttribute("loginUser", userRegisterationSession);
                              //logger.debug("Setting user info to session...");
                          }
					}
					catch(Exception ex)
					{ 
						ex.printStackTrace();
						System.out.println("error on inserting1212:("+ex.getStackTrace()[0].getLineNumber());
						System.out.println("error on inserting:("+ex.getMessage());
					}
					session.setAttribute("loginid",loginid);
					return "loginsuccess";
				} 
				else {
					 String loginAtmpt = "failed";
					 count = loginService.invalidattempt(loginid);
					  
					 try {
						 if(count == 0)
						 {
							 result = loginService.insertloginlog(userList, loginAtmpt, userSts, request);
							 return "invalidcredentials";
						 }
						 else if(count == 1 )
						 {
                              result = loginService.insertloginlog(userList, loginAtmpt, userSts, request);
							  return "loginerror"; 
						
						 }
						 else if(count == 2)
						 {
							  userSts = "L";
							  result = loginService.insertloginlog(userList, loginAtmpt, userSts, request);
							  return "userlocked"; 
						 }
						 else {
							  System.out.println("locked");
							  return "locked";
						 }					 
					 }
					 catch(Exception ex)
					 {
							 System.out.println("error on inserting1:("+ex.getStackTrace()[0].getLineNumber());
							  System.out.println("error on inserting:("+ex.getMessage());
					 }
				}
			} else {
				try {
					   String loginAtmpt = "failed";
					   flagLoginSuccess = loginService.insertloginlog(userList, loginAtmpt, userSts, request);
					  }
					
					  catch(Exception ex){ 
						  System.out.println("error on inserting2:("+ex.getStackTrace()[0].getLineNumber());
						  System.out.println("error on inserting:("+ex.getMessage());
						  }
				return "invalidcredentials";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("error on inserting3:("+ex.getStackTrace()[0].getLineNumber());
			 System.out.println("error on inserting:("+ex.getMessage());
			  
		}
		return "invalidcredentials";
	}

	private String handleFailedLoginAttempt( String userId, List<UserReg> userList ) {
		HttpServletRequest request = null;
		String loginAtmpt = "failed";
		String userSts = null;
		int count = loginService.invalidattempt(userId);
        if (count == 0) {
        	loginService.insertloginlog(userList, loginAtmpt, userSts, request);
        	return "invalidcredentials";
        } else if (count == 1) {
        	loginService.insertloginlog(userList, loginAtmpt, userSts, request);
        	return "loginerror";
        } else if (count == 2) {
        	loginService.insertloginlog(userList, loginAtmpt, userSts, request);
        	return "userlocked";
        } else {
            return "locked";
        }
    }
	
	
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public ModelAndView forgotpassword(HttpServletRequest request) {
		return new ModelAndView("forgotPassword");
	}
	
	// showLogin will return login page
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) throws UnknownHostException {
		String user = "";
		session = request.getSession();
		
		ModelAndView mav = new ModelAndView("login");
		
		  user = loginService.checkloginuser(request);
		  
		  Login login = new Login(); 
		  login.setUser_id(user); 
		  mav.addObject("user", login);
		 	
		response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	     response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
	     response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
	     response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
		mav.addObject("login", new Login());
		
		return mav;
	}

	/*
	 * @RequestMapping(value = "/extendSession", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public String extendSession(HttpServletRequest request) {
	 * request.getSession().setMaxInactiveInterval(30 * 60); return
	 * "Session extended"; }
	 */
	
	// afterlogin will return success page after logged in
	@RequestMapping(value = "/loginsuccess", method = RequestMethod.GET)
	public ModelAndView afterlogin(HttpServletRequest request,
			HttpServletResponse response, Login login) {
		session = request.getSession();
		ModelAndView mav = null;
		if(session!=null && session.getAttribute("loginid")!=null){
			System.out.println("login success");
			mav = new ModelAndView("afterlogin");
			mav.addObject("loginId", session.getAttribute("loginid"));
			mav.addObject("userType",session.getAttribute("roleName"));
			//mav.addObject("menu", menuController.getMenuUserId(request));
			
			String regid = session.getAttribute("regId").toString();
			String userType = session.getAttribute("userType").toString();
			String userId = session.getAttribute("loginID").toString();
			
			LinkedHashMap<Integer, List<ProfileBean>> map = new LinkedHashMap<Integer, List<ProfileBean>>();
			
				stateList = stateMasterService.getAllState();
				List<UserReg> list=new  ArrayList<UserReg>();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				list=profileService.getUserDetail(Integer.parseInt(regid));
				listm=profileService.getMapstate(Integer.parseInt(regid), userType);
				List<ProfileBean> sublist = new ArrayList<ProfileBean>();
				if(userType.equals("ADMIN") || userType.equals("DL") )
				{
					sublist = new ArrayList<ProfileBean>();
					ProfileBean profileBean = new ProfileBean();
					if ((listm != null) && (listm.size() > 0)) 
					{
						for (Map.Entry<Integer, String> entry : stateList.entrySet()) 
						{
							for (ProfileBean row : listm) 
							{
								profileBean = new ProfileBean();
					        	if(!map.containsKey(row.getStatecode()) && stateList.containsKey(row.getStatecode().toString())) 
					        	{
					        		profileBean.setSelected("selected");
					        		profileBean.setStatecode(row.getStatecode());
					        		profileBean.setStatename(stateList.get(row.getStatecode().toString()));
									sublist = new ArrayList<ProfileBean>();
									sublist.add(profileBean);
									map.put(row.getStatecode(), sublist);
									 //break second ;
								}
					        	else if(!map.containsKey(entry.getKey()) )
					        	{ 
										 sublist = new ArrayList<ProfileBean>();
										 profileBean.setSelected(" ");
										 profileBean.setStatecode(entry.getKey());
										 profileBean.setStatename(stateList.get(entry.getKey()));
										 sublist.add(profileBean);
										 map.put(entry.getKey(), sublist);
										// break second ;
								}
								else {
									  
								}				        	
					        }
					    }
					}
				}
				else if(userType.equals("SL") || userType.equals("DI") ) 
				{
					sublist = new ArrayList<ProfileBean>();
					for (ProfileBean row : listm) 
					{
						sublist.add(row);
						map.put(row.getStatecode(), sublist);
					}
				}
				else if(userType.equalsIgnoreCase("PI")  ) 
				{
					sublist = new ArrayList<ProfileBean>();
					for (ProfileBean row : listm) 
					{
						sublist.add(row);
						map.put(row.getStatecode(), sublist);
					}
				}
				String email="",phone="",mobile="",fax="",address="";
				for(UserReg u : list) {
					email=u.getEmail();
					phone=u.getPhoneNo();
					mobile=u.getMobileNo();
					fax=u.getFaxNo();
					address=u.getPerAddress();
				}
				mav.addObject("listm", map);
				//mav.addObject("menu", menuController.getMenuUserId(request));
				mav.addObject("loginId", session.getAttribute("loginID"));
				mav.addObject("email", email);
				mav.addObject("phone", phone);
				mav.addObject("mobile", mobile);
				mav.addObject("fax", fax);
				mav.addObject("address", address);
				mav.addObject("stateList", stateList);
				mav.addObject("regId",regid);
				mav.addObject("userType",userType);
				mav.addObject("userId",userId);
		}else
		{
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		if(cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if(cookie.getName( ).compareTo("menuid") == 0) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					System.out.println("Deleted Cookie Is?= " + cookie.getName( ) + "\n");
				}
		}
		}
		return mav;
		
	}
}

/*
 * @RequestMapping(value = "/loginProcess", method = RequestMethod.POST) public
 * ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse
 * response, @ModelAttribute("login") Login login) { ModelAndView mav = null;
 * session = request.getSession(true); List<User> user =
 * userService.validateUser(login); if (!user.isEmpty()) { mav = new
 * ModelAndView("welcome"); mav.addObject("firstname", user.get(0).getUserId());
 * } else { mav = new ModelAndView("login"); mav.addObject("message",
 * "Username or Password is wrong!!"); } return mav; }
 * 
 * }
 */
