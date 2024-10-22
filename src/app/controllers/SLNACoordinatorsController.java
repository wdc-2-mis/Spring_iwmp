package app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.SLNACoordinatorsBean;
import app.service.SLNACoordinatorsService;

@Controller
public class SLNACoordinatorsController {

	HttpSession session;
	
	@Autowired
	private SLNACoordinatorsService slnaCoordinatorsService;
	
	
	// slnaCoordinators will return slna coordinator page
		@RequestMapping(value = "/slnacoordinators", method = RequestMethod.GET)
		public ModelAndView slnaCoordinators(HttpServletRequest request) {
			session = request.getSession(true);
			ModelAndView mav = new ModelAndView();
			List<SLNACoordinatorsBean> list = new ArrayList<>();
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("slnamiscoordinators");
				list = slnaCoordinatorsService.getSLNACoordinatorsList();
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
			mav.addObject("dataList", list);
			mav.addObject("dataListsize", list.size());
			return mav;
		}
		
//		@RequestMapping(value = "/getListOfSLNACoordinators", method = RequestMethod.POST)
//		public List<SLNACoordinatorsBean> getListOfSLNACoordinators(HttpServletRequest request) {
//			session = request.getSession(true);
//			ModelAndView mav = new ModelAndView();
//			List<SLNACoordinatorsBean> list = new ArrayList<>();
//			if (session != null && session.getAttribute("loginID") != null) {
////				mav = new ModelAndView("slnamiscoordinators");
//				list = slnaCoordinatorsService.getSLNACoordinatorsList();
//			}else {
//				mav = new ModelAndView("login");
//				mav.addObject("login", new Login());
//			}
////			mav.addObject("dataList", list);
////			mav.addObject("dataListsize", list.size());
//			return list;
//		}
		
		@RequestMapping(value = "/updateSLNACrdntrDetails", method = RequestMethod.POST)
		public ModelAndView updateSLNACrdntrDetails(HttpServletRequest request) {
			session = request.getSession(true);
			ModelAndView mav = new ModelAndView();
			String msg = "";
			List<SLNACoordinatorsBean> list = new ArrayList<>();
			if (session != null && session.getAttribute("loginID") != null) {
			mav = new ModelAndView("slnamiscoordinators");
			
			String email= request.getParameter("email");
			BigDecimal mobile= new BigDecimal(request.getParameter("mobile"));
			String stName= request.getParameter("state");
			String name= request.getParameter("name");
			Integer stcode = Integer.parseInt(request.getParameter("ddstcd"));
			try {
				msg = slnaCoordinatorsService.updateSLNACrdntrDetails(stcode, name, email, mobile);
				list = slnaCoordinatorsService.getSLNACoordinatorsList();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
//			msg = msg.equals("success")?"SLNA Coordinaters Details Updated Successfully":"Details not Updated";
			mav.addObject("dataList", list);
			mav.addObject("dataListsize", list.size());
			mav.addObject("msg", msg);
			return mav;
		}
		
		@RequestMapping(value = "/slnacoordinatorsdetails", method = RequestMethod.GET)
		public ModelAndView slnaCoordinatorsDetails(HttpServletRequest request) {
			session = request.getSession(true);
			ModelAndView mav = new ModelAndView();
			List<SLNACoordinatorsBean> list = new ArrayList<>();
			try {
				mav = new ModelAndView("slnaMISCoordinatorsDetails");
				list = slnaCoordinatorsService.getSLNACoordinatorsList();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			mav.addObject("dataList", list);
			mav.addObject("dataListsize", list.size());
			return mav;
		}
}
