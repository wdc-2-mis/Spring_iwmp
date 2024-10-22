package app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Changepassword;
import app.bean.Login;
import app.bean.UserBean;
import app.service.LoginService;
import app.util.SHA512;

@Controller("changepwd")
public class Changepwd {
	HttpSession session;
	@Autowired(required = true)
	MenuController menuController;
	String loginId = null;
    String userType = null;
    String encryptedoldpwd = null;
    String encryptedoldpwd1 = null;
    String encryptednewpwd = null;
    boolean result = false;
    
    Boolean isAdminUser = false;
    @Autowired
	public LoginService loginService;
	
    @RequestMapping(value = "/pwdchange") 
	public ModelAndView pwdChange(HttpServletRequest request, HttpServletResponse
	  response) { 
		session = request.getSession(true);
		//System.out.println("in into password change process....");
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("changePassword");
		mav.addObject("menu", menuController.getMenuUserId(request));
		return mav; }

@RequestMapping(value = "/changepassword", method = RequestMethod.POST) 
@ResponseBody
public String changepassword(HttpServletRequest request, HttpServletResponse
  response, @ModelAttribute("changePassword")  Changepassword changePassword) { 
	boolean userValidate = false;
	boolean insthistory = false;
	session = request.getSession(true);
	//System.out.println("are you sure you want to change the password....");
	loginId   = (String) session.getAttribute("loginid");
	
    userType  = (String) session.getAttribute("userType");
    String oldPassword = changePassword.getOldpwd();
    SHA512 sha512 = new SHA512();
    oldPassword = sha512.stringify(oldPassword);
    String newPassword = changePassword.getNewpwd();
    newPassword = sha512.stringify(newPassword);
    String firstLogin="Y";
    String remoteAddr = request.getRemoteAddr();
    ModelAndView mav = new ModelAndView();
	mav = new ModelAndView("changePassword");
    try {
    	userValidate = loginService.userValidate(loginId, userType, request);
    	//System.out.println("userValidate:" +userValidate);
		if (userValidate) {
			System.out.println("user found:)");
			System.out.println("loginid:" +loginId);
			encryptedoldpwd = loginId+oldPassword;
			encryptedoldpwd = sha512.stringify(encryptedoldpwd);
	        
	        
		}
		int check =0;
	    encryptednewpwd = loginId + newPassword;
	    encryptednewpwd = sha512.stringify(encryptednewpwd);
	    String password = (String) session.getAttribute("changepassword");
	    
	    check = loginService.checkpasshistory(loginId, encryptednewpwd);
		if(check > 0)
		{
			return "checkpasshistory";
        }
		else if(password.equals(encryptedoldpwd)){
			//String loginAtmpt = "success";
			//System.out.println("password updated process start:");
			result = loginService.updatepassword(encryptednewpwd, firstLogin, loginId, userType);
			insthistory = loginService.Insertpwdhistroy(loginId, encryptednewpwd, userType,session);
			
		    if(insthistory)
		    {
		    	return "datainsert";
		    }
		    else {
		    	return "insertingerror";
		    }
		}
	} catch (Exception ex) {
		System.out.println("error on inserting:("+ex.getStackTrace()[0].getLineNumber());
		 System.out.println("error on inserting:("+ex.getMessage());
	}
    
    return "insertingerror";
   }

}
