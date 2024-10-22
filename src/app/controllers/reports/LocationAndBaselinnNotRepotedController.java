package app.controllers.reports;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.ProfileBean;
import app.bean.ProjectLocationBean;
import app.service.ProfileService;
import app.service.ProjectLocationService;

@Controller("LocationAndBaselinnNotRepoted")
public class LocationAndBaselinnNotRepotedController {
	
	
	HttpSession session;
	
	@Autowired(required = true)
	public ProjectLocationService plSer;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@RequestMapping(value="/getLocationAndBaselineDraft", method = RequestMethod.GET)
	public ModelAndView getLocationAndBaselineDraft(HttpServletRequest request, HttpServletResponse response)
	{
		int stCode = 0;
		int distCode = 0;
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		String userType= session.getAttribute("userType").toString();
		try {
			if(session!=null && session.getAttribute("loginID")!=null) 
			{
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				mav = new ModelAndView("reports/locationAndBaselinnNotRepoted");
				
				List<ProjectLocationBean> list = new ArrayList<ProjectLocationBean>();
				if(userType.equals("DI")) {
					List<ProfileBean> listm=new  ArrayList<ProfileBean>();
					listm=profileService.getMapstate(regId, userType);
					String distName = "";
					String stateName = "";
					
					for(ProfileBean bean : listm) {
						distName =bean.getDistrictname();
						distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
						stateName = bean.getStatename();
						stCode = bean.getStatecode()==null?0:bean.getStatecode();
					}
				list = plSer.getLocationAndBaselineDraft(distCode, userType);
				}
				else {
					list = plSer.getLocationAndBaselineDraft(Integer.parseInt(st_code), userType);
				}	
				mav.addObject("Listofstatetovill", list);
				mav.addObject("userType", userType);
			
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}

}
