package app.controllers.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.serviceImpl.DistrictMasterServiceImpl;

@Controller("DistrictController")
public class DistrictController {
HttpSession session;
	
	@Autowired(required = true)
	DistrictMasterServiceImpl DistrictActService;
	
	@RequestMapping(value = "/mstrDistrict", method = RequestMethod.GET)
	public ModelAndView modifyProjectList(HttpServletRequest request, HttpServletResponse response) {
		
          ModelAndView mav = new ModelAndView();
          session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("model/districtMaster");
			mav.addObject("distact",DistrictActService.getDistrictByStateCode(28));
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return mav; 
	}


}
