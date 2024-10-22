package app.controllers;

import java.util.LinkedHashMap;

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
import app.bean.User;
import app.common.CommonFunctions;
import app.model.UserReg;
import app.service.PiaUserActivationService;
import app.service.UserRoleMapService;

@Controller("piaUserActivationController")
public class PiaUserActivationController {
	
	HttpSession session;
	
	@Autowired(required = true)
	UserRoleMapService userRoleMapService;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	CommonFunctions commonFunction;
	
	@Autowired
	PiaUserActivationService piaUserActivationService;
	
	
	// slnaUserActive is used to get user detail page with all data
			@RequestMapping(value="/newUserActiveDelete", method = RequestMethod.GET)
			public ModelAndView slnaUserActive(HttpServletRequest request)
			{
				session = request.getSession(true);
				ModelAndView mav = new ModelAndView();
				LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
				if(session!=null && session.getAttribute("loginid")!=null) {
					mav = new ModelAndView("user/slnauseractivation");
					//mav.addObject("menu", menuController.getMenuUserId(request));
					userType.put("PI","PIA");
					mav.addObject("userStatusList", commonFunction.getUserStatus());
					mav.addObject("userTypeList",userType);
				}else {
					mav = new ModelAndView("login");
					mav.addObject("login", new Login());
				}
				return mav; 
			}
			
			
			// getUser is used to get the user list on search button clicked or after activation of user
			@RequestMapping(value="/slnausersrch", method = RequestMethod.POST)
			public ModelAndView getUser(HttpServletRequest request, HttpServletResponse response,
					@ModelAttribute("userReg") UserReg userReg, @ModelAttribute("user") User user)
			{
				System.out.println("usereg: "+userReg.getUserId()+" : "+userReg.getUserName()+" : "+userReg.getDepartment()+" : "+userReg.getDesignation()+" : "+userReg.getEmail()+" : "+userReg.getUserType()+" : "+userReg.getStatus());
				ModelAndView mav = new ModelAndView("user/slnauseractivation");
				LinkedHashMap<String, String> userType = new LinkedHashMap<String, String>();
				if(userReg!=null && userReg.getUserType()!=null)
					userType.put("PI", "PIA");
				else
				userType.put("PI","PIA");
				session=request.getSession(true);
				//mav.addObject("menu", menuController.getMenuUserId(request));
				String stCode="0";
				if(session==null)
					stCode="0";
				else
					stCode=session.getAttribute("stateCode").toString();
				System.out.println("StCode: "+stCode);
				mav.addObject("userList", piaUserActivationService.getUserList(userReg,stCode));
				mav.addObject("newuserid", user.getUserid());
				mav.addObject("username", user.getUsername());
				mav.addObject("status", user.getUserstatus());
				mav.addObject("userTypeList", userType);
				mav.addObject("userStatusList", commonFunction.getUserStatus());
				return mav; 
				
			}
			
}
