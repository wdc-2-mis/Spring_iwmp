package app.controllers;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.AuditReportBean;
import app.bean.UserBean;
import app.service.AuditService;
import app.util.SessionUtility;

@Controller("auditReportController")
public class AuditReportController {
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	AuditService auditService;
	
	// showLogin will return login page
		@SuppressWarnings({ "null", "unchecked" })
		@RequestMapping(value = "/auditRprt", method = RequestMethod.GET)
		public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView mav = new ModelAndView("reports/onlineusers");
			//mav.addObject("menu", menuController.getMenuUserId(request));
			mav.addObject("userList",auditService.getOnlineUserList());
			//mav.addObject("login", new Login());
			return mav;
		}

}
