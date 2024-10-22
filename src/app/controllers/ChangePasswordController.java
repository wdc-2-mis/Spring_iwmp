package app.controllers;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import app.model.UserReg;
import app.bean.SecondPasswordBean;
import app.service.ChangePasswordService;
import app.service.StateMasterService;
import app.service.UserRoleMapService;

@Controller("changePasswordController")
public class ChangePasswordController {

	 HttpSession session;
	 
	 @Autowired(required = true)
	 MenuController menuController;
	 
	 @Autowired(required = true)
	 StateMasterService stateMasterService;
	 
	 @Autowired(required = true)
	 UserRoleMapService userRoleMapService;
	 
	 @Autowired(required = true)
	 ChangePasswordService changePasswordService;
	 
	// getChangePasswordPage is used to get the Change Password page
		@RequestMapping(value="/secondPasswordSet", method = RequestMethod.GET)
		public ModelAndView getChangePasswordPage(HttpServletRequest request)
		{
			session=request.getSession(true);
			ModelAndView mav = new ModelAndView();
			if(session!=null && session.getAttribute("loginid")!=null) {
			mav = new ModelAndView("user/secondpwdreset");
			//mav.addObject("menu", menuController.getMenuUserId(request));
			LinkedHashMap<Integer, String> stateList=new LinkedHashMap<Integer, String>();
			stateList.put(0,"All");
			stateList.putAll(stateMasterService.getAllState());
			mav.addObject("stateList",stateList);
			mav.addObject("roleList",userRoleMapService.getSpecificRoleList());
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new UserReg());
			}
			return mav; 
		}
		
		// getChangePasswordPage is used to get the Change Password page
				@RequestMapping(value="/saveSecondPassword", method = RequestMethod.POST)
				public ModelAndView saveSecondPassword(HttpServletRequest request, @ModelAttribute("secondPassword") SecondPasswordBean secondPasswordBean)
				{
					session=request.getSession(true);
					ModelAndView mav = new ModelAndView();
					Integer userType = secondPasswordBean.getUserType();
					if(userType==null)
						userType=0;
					Integer stCode = secondPasswordBean.getState();
					if(stCode==null)
						stCode=0;
					Integer distCode = secondPasswordBean.getDistrict();
					if(distCode==null)
						distCode=0;
					String project = secondPasswordBean.getProject();
					if(project == null)
						project="0";
					String newPassword = secondPasswordBean.getNewPassword();
					//System.out.println("userType: "+userType+" stCode: "+stCode+" distCode: "+distCode+" project: "+project+" newPassword: "+newPassword);
					if(session!=null && session.getAttribute("loginid")!=null) {
					mav = new ModelAndView("user/secondpwdreset");
					//mav.addObject("menu", menuController.getMenuUserId(request));
					mav.addObject("stateList",stateMasterService.getAllState());
					mav.addObject("roleList",userRoleMapService.getSpecificRoleList());
					
					boolean result = changePasswordService.saveSecondPassword(userType,stCode,distCode,project,newPassword);
					if(result) {
						//System.out.println("Ho gya: ");
						mav.addObject("message","Second Password change successfully.");
						mav.addObject("result","success");
					}else {
						//System.out.println("Nahi hua");
						mav.addObject("message","There is some problem while changing password, Please try again !!");
						mav.addObject("result","fail");
					}
					}else {
						mav = new ModelAndView("login");
						mav.addObject("login", new UserReg());
					}
					return mav; 
				}
}
