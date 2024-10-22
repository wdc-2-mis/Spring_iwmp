package app.controllers;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.bean.DisApprovalBean;
import app.bean.Login;
import app.service.Disapprovalservice;

@Controller("distappController")
public class DistAppController {

	HttpSession session;

	@Autowired(required = true)
	Disapprovalservice disApprovalService;

	@RequestMapping(value = "/slnaDistPermission", method = RequestMethod.GET)
	public ModelAndView slnapermission(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		if (session != null && session.getAttribute("loginID") != null) {
			mav = new ModelAndView("model/slnadiscpermsn");
			mav.addObject("stateVector", disApprovalService.getAllState());
			
			Integer stcode = (Integer) session.getAttribute("stateCode");
			//System.out.println("stcode:" +stcode);
			mav.addObject("stateCode", stcode);
			List<DisApprovalBean> statusMsg = disApprovalService.checkstatus(stcode);
			
			DisApprovalBean dis = new DisApprovalBean();
			String abc = "Decision Pending";
			if (statusMsg.contains(abc)) {
				mav.addObject("statusMsg", statusMsg);
				mav.addObject("DesicionDate", null);
			} else {
				mav.addObject("statusMsg", statusMsg);
				mav.addObject("DesicionDate", statusMsg);
			}

		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());

		}
		return mav;
	}

	@RequestMapping(value = "/updateslnadisper", method = RequestMethod.POST)
	public ModelAndView updateslnadisper(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "val") String val, @RequestParam(value = "state") int state) {

		Integer stcode = (Integer) session.getAttribute("stateCode");
		System.out.println("value of state:" +state);
		if(state != 0)
		{
			stcode = state;
		}
		System.out.println("value of stcode:" +stcode);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/slnadiscpermsn");
		Boolean flag = false;

		
		try {
			session = request.getSession(true);
			if (session != null && session.getAttribute("loginID") != null) {
				flag = disApprovalService.updateslnadisapp(val, stcode);
				//mav.addObject("message", "Data has been saved successfully");

			} else {
				mav.addObject("message", "Problem on saved data");
			}

			// mav.addObject("phyact",physicalActservice.getPhyActdata());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/selectdolr", method = RequestMethod.POST)
	public ModelAndView selectdolr(HttpServletRequest request, HttpServletResponse response) {

		
		int  state = Integer.parseInt(request.getParameter("state"));
		int stcode = state;
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("model/slnadiscpermsn");
		Boolean flag = false;
		List<DisApprovalBean> statusMsg = disApprovalService.checkstatus(stcode);
		String abc = "Decision Pending"; 
		if (statusMsg.contains(abc))
		  {
		  mav.addObject("statusMsg", statusMsg); 
		  mav.addObject("DesicionDate", null); 
		  mav.addObject("stCode", stcode); 
		  }
		  else { 
		  mav.addObject("statusMsg", statusMsg);
		  mav.addObject("DesicionDate",statusMsg);
		  mav.addObject("stCode",stcode);
		  
		  }
		mav.addObject("stateVector", disApprovalService.getAllState());
		return mav;
	}
}
