package app.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Changepassword;
import app.bean.Login;
import app.bean.User;
import app.bean.UserBean;
import app.service.LoginService;
import app.util.SHA512;

@Controller("changePassAtSlnaController")
public class ChangePassAtSlna {
	HttpSession session;
	String loginId = null;
	String userType = null;
	String encryptednewpwd = null;
	String encryptedoldpwd = null;
	boolean result = false;
	@Autowired
	public LoginService loginService;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@RequestMapping(value="/changePassAtSlna", method = RequestMethod.GET)
	public ModelAndView userRoleMap(HttpServletRequest request)
	{
		//System.out.println("starting");
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
		if(session!=null && session.getAttribute("loginid")!=null) {
			mav = new ModelAndView("user/changePassAtSlna");
			mav.addObject("menu", menuController.getMenuUserId(request));
			/*
			 * mav.addObject("allApplicationList",userRoleMapService.getApplicationList());
			 * mav.addObject("allRoleList",userRoleMapService.getRoleList());
			 * userType.put(null, "All"); userType.putAll(commonFunction.getUserTypeList());
			 */
			
			mav.addObject("userType",userType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}

	
	
	@RequestMapping(value="/changePasswordAtSlna", method = RequestMethod.GET)
	public ModelAndView userRoleMap1(HttpServletRequest request,@ModelAttribute("user") User user)
	{
		String userId=user.getUserid();
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginid")!=null) {
			
			mav = new ModelAndView("user/changePasswordAtSlna");
			mav.addObject("userId",user.getUserid());
			mav.addObject("userName",user.getUsername());
			mav.addObject("userType",user.getUsertype());
			//System.out.println(user.getUserid() +" "+ user.getUsertype());	
		
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value = "/userInfoAtSlna", method = RequestMethod.POST)
	
	public ModelAndView showData(HttpServletRequest request, HttpServletResponse
	  response, ModelMap model) { 
		boolean userValidate = false;
		String loginid = null;
		int  st_code1=1;
		int  st_code2=2;
		String userType="";
		String statename="";
	    String distname="";
	    String blockname="";
	    String projectName="";
		Object[] value;
		Object[] showdata;
		boolean flag=false;
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("user/changePassAtSlna");
		ArrayList <UserBean> userbeans =new ArrayList<UserBean>();
		UserBean userbean=null;
		try {
			String userId=request.getParameter("pia");
			//System.out.println("calling"+userId);
			session = request.getSession();
			loginid = (String)session.getAttribute("loginid");
	        if(loginid == null) {
	            request.setAttribute("sessionOut","sessionOut");
	            return null;
	        }
		int regid = Integer.valueOf(session.getAttribute("regId").toString());
		st_code1 = loginService.st_code1(regid);
			
		value = loginService.st_code2(userId);
		st_code2 = (Integer.parseInt(String.valueOf(value[0])));
		userType = String.valueOf(value[1]);
		
		
		if(st_code1 == st_code2)
        {
			if(userType.equalsIgnoreCase("PI"))
			{
				showdata = loginService.showDataPia(userId);
			}
			else {
				 showdata = loginService.showDataWcdc(userId);
			   }
			if(showdata!=null)
			{
			userId = String.valueOf(showdata[0]);
			statename = String.valueOf(showdata[1]);
			distname = String.valueOf(showdata[2]);
			if( userType.equalsIgnoreCase("PI"))
        	{
				//blockname = String.valueOf(value[3]);
				if(String.valueOf(showdata[3])!=null)
				{
					projectName = String.valueOf(showdata[4])+" , "+projectName;
				}
        	}
        flag=true;
        }
		if(flag) {
			userbean = new UserBean();
			userbean.setUserType(userType);
			userbean.setUserId(userId);
			userbean.setStatename(statename);
			userbean.setDistName(distname);
			
			if(userType.equalsIgnoreCase("PI"))
        	{
				userbean.setProjectName(projectName);
        	}
		     userbeans.add(userbean);    
		}
		
		
		
		mav.addObject("userbeans", userbeans);
		mav.addObject("dataListSize", userbeans.size());
		
		session.setAttribute("userId", userId);
		session.setAttribute("userType", userType);
		session.setAttribute("loginid", loginid);
		
        }
		else {
			mav.addObject("message","You are not authorised to know the information  of this User ID. !");
			//return "Not_authorised";
		}
		}
		catch (Exception ex) {
			System.out.println("error on inserting:("+ex.getStackTrace()[0].getLineNumber());
			 System.out.println("error on inserting:("+ex.getMessage());
		}
		
		return mav;
		
		}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/changepwdatslna", method = RequestMethod.POST) 
	@ResponseBody
	public String changepassword(HttpServletRequest request, HttpServletResponse
	  response, @ModelAttribute("changepwdatslna")  Changepassword changePassword) { 
		boolean userValidate = false;
		boolean insthistory = false;
		Boolean flag = false;
		int check =0;
		session = request.getSession(true);
		//System.out.println("are you sure you want to change the password....");
		loginId   = request.getParameter("userId");
		userType  = request.getParameter("userType");
	    String adminType = request.getParameter("adminType");
	    String roleName = request.getParameter("roleName");
	    //System.out.println("roleName:" +roleName);
	    SHA512 sha512 = new SHA512();
	    String newPassword = changePassword.getNewpwd();
		/* newPassword = sha512.stringify(newPassword); */  
	   
	    String oldPassword = changePassword.getOldpwd();
	    
		/* oldPassword = sha512.stringify(oldPassword); */
	    
	    String firstLogin="Y";
	    String remoteAddr = request.getRemoteAddr();
	    ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("changePassword");
	    try {
	    	encryptednewpwd = loginId + newPassword;
		    encryptednewpwd = sha512.stringify(encryptednewpwd);
		    
		    encryptedoldpwd = loginId + oldPassword;
		    encryptedoldpwd = sha512.stringify(encryptedoldpwd);
		    
		    String password = (String) session.getAttribute("changepassword");
		  
		    if(!adminType.equalsIgnoreCase("ADMIN") && !roleName.equalsIgnoreCase("SLNA Admin Role")) {
		    flag = loginService.checkoldpassword(loginId, encryptedoldpwd);
		    if(!flag ==true)
		    {
		    	return "oldpassworderror";
		    }
		    }
		    
		    check = loginService.checkpasshistory(loginId, encryptednewpwd);
			if(check > 0)
			{
				System.out.println("checkpasshistory!");
				return "checkpasshistory";
	        }
			else {
				result = loginService.updatepassword(encryptednewpwd, firstLogin, loginId, userType);
				insthistory = loginService.Insertpwdhistroy(loginId, encryptednewpwd, userType,session);
				
			    if(insthistory)
			    {
			    	//System.out.println("hello data insert");
			    	return "datainsert";
			    }
			    else {
			    	return "insertingerror";
			    }
			}
		} catch (Exception ex) {
			System.out.println("error on inserting:("+ex.getStackTrace()[0].getLineNumber());
			 System.out.println("error on inserting:("+ex.getMessage());
			 return "insertingerror";
		}
	    
	    
	   }

	}


