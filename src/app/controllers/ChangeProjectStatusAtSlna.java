package app.controllers;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.ProjectDetailsBean;
import app.service.ChangeProjectStatusService;
import app.watershedyatra.service.WatershedYatraService;

@Controller
public class ChangeProjectStatusAtSlna {

	HttpSession session;
	
	@Autowired
	WatershedYatraService ser;
	
	@Autowired
	ChangeProjectStatusService changeProjectStatusServ;
	
	@RequestMapping(value = "/changeProjectStatus", method = RequestMethod.GET)
	public ModelAndView changeProjectStatus(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();

		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("projectStatusComplete");
				
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				String username = session.getAttribute("loginID").toString();
				
				mav.addObject("userType", userType);
				mav.addObject("distList", ser.getDistrictList(stcd));
				mav.addObject("projectDetailsList", changeProjectStatusServ.getProjectDetails(stcd, 0));
				
			}else {
					mav = new ModelAndView("login");
					mav.addObject("login", new Login());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/getProjectDetailsByDistrict", method = RequestMethod.POST)
	@ResponseBody
	public List<ProjectDetailsBean> getProjectDetailsByDistrict(@RequestParam Integer dcode,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("stateCode") == null) {
			return Collections.emptyList();
		}
		Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
		return changeProjectStatusServ.getProjectDetails(stcd, dcode);
	}
	
	@RequestMapping(value = "/updateProjectStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateProjectStatus(
	        @RequestParam("projectIds") List<Integer> projectIds,
	        HttpServletRequest request) {

	    try {

	        HttpSession session = request.getSession(false);

	        if (session == null || session.getAttribute("loginID") == null) {
	            return "SESSION_EXPIRED";
	        }

	        String username = session.getAttribute("loginID").toString();

	        changeProjectStatusServ.updateProjectStatus(
	                projectIds,
	                username,
	                request.getRemoteAddr()
	        );

	        return "SUCCESS";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "ERROR";
	    }
	}
}
