package app.watershedyatra.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.ProfileBean;
import app.service.ProfileService;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.service.RoutePlanVanTravelingServices;
import app.watershedyatra.service.WatershedYatraService;

@Controller("RoutePlanVanTravelingController")
public class RoutePlanVanTravelingController {
	
	
HttpSession session;
	
	@Autowired
	WatershedYatraService  ser;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	RoutePlanVanTravelingServices serr;
	
	@RequestMapping(value = "/getRoutePlanVanTravelingHeader", method = RequestMethod.GET)
	public ModelAndView getRoutePlanVanTravelingHeader(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<NodalOfficerBean> draft = new ArrayList<NodalOfficerBean>();
		try {
			
			if (session != null && session.getAttribute("loginID") != null) 
			{
				mav = new ModelAndView("WatershedYatra/routePlanVanTraveling");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				listm=profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				for(ProfileBean bean : listm) {
					distName =bean.getDistrictname();
					distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
				}
				mav.addObject("userType",userType);
			//	mav.addObject("distName",distName);
				mav.addObject("stateName",stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));
				draft=serr.getRoutePlanVanTraveling(stcd);
				mav.addObject("draftList",draft);
				mav.addObject("draftListSize",draft.size());

			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getWatershedYatraBlock1", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getWatershedYatraBlock1(HttpServletRequest request, @RequestParam("stateCode") int stateCode) {
		
		return ser.getWatershedYatraBlock(stateCode);
	}

	@RequestMapping(value = "/getWatershedYatraGPs1", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getWatershedYatraGPs1(HttpServletRequest request, @RequestParam("blkCode") int blkCode) {
		
		return ser.getWatershedYatraGPs(blkCode);
	}
	
	@RequestMapping(value = "/getWatershedYatraVillage1", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getWatershedYatraVillage1(HttpServletRequest request, @RequestParam("gpscode") int gpCode) {
		
		return ser.getWatershedYatraVillage(gpCode);
	}
	
	@RequestMapping(value="/saveRoutePlanVanTravelingLMS", method = RequestMethod.POST)
	@ResponseBody
	public String saveRoutePlanVanTravelingLMS(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="district") Integer district, 
			@RequestParam(value ="block") Integer block, @RequestParam(value ="grampan") Integer grampan, @RequestParam(value ="village") Integer village, 
			@RequestParam(value ="location") String location, @RequestParam(value ="datetime") String datetime, @RequestParam(value ="district1") Integer district1, 
			@RequestParam(value ="block1") Integer block1, @RequestParam(value ="grampan1") Integer grampan1, @RequestParam(value ="village1") Integer village1, 
			@RequestParam(value ="location1") String location1, @RequestParam(value ="datetime1") String datetime1)
	{
		String res="fail";
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			res=serr.saveRoutePlanVanTravelingLMS(Integer.parseInt(state), district, block, grampan, village, location, datetime, session, district1, block1, grampan1, village1, location1, datetime1);
		}
		return res; 
	}
	

}
