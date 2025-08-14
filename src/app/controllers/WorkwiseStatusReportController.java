package app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.AssetIdBean;
import app.bean.Login;
import app.bean.ProfileBean;
import app.bean.StateToVillageBean;
import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaService;
import app.service.CreateAssetIdService;
import app.service.PhysicalActionPlanService;
import app.service.ProfileService;
import app.watershedyatra.service.WatershedYatraService;

@Controller("workwiseStatusReportController")
public class WorkwiseStatusReportController {

	HttpSession session;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	WatershedYatraService ser;
	
	@Autowired(required = true)
	CreateAssetIdService createAssetIdService;
	
	@Autowired
	PhysicalActionPlanService physicalActionPlanService;
	
	@Autowired
	JanbhagidariPratiyogitaService serk;
	
	private Map<String, Integer> ProjectList;
	private Map<Integer, String> financial;
	
	@RequestMapping(value = "/workwiseStatusReport", method = RequestMethod.GET)
	public ModelAndView workwiseStatusReport(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();

		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("listofWorkWiseStatus");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				String stateName = "";
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				for (ProfileBean bean : listm) {
					stateName = bean.getStatename();
					
				}
				mav.addObject("stateName", stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));
}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return mav;
	}
	
	@RequestMapping(value="/listofWorkWiseStatus", method = RequestMethod.POST)
	public ModelAndView listofWorkWiseStatus(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		Integer projid= Integer.parseInt(request.getParameter("projid"));
		String yearParam = request.getParameter("year");
		int fyear = 0;
        if (yearParam != null && !yearParam.trim().isEmpty()) {
		    try {
		        fyear = Integer.parseInt(yearParam.trim());
		    } catch (NumberFormatException e) {
		        // Optional: log the error if needed
		        fyear = 0; // fallback to 0 on parse failure
		    }
		}

		
		String hactivity= request.getParameter("activityid");
		String wstatus= request.getParameter("status");
		String district = request.getParameter("district");
		String stateName = "";
		List<AssetIdBean> list=new  ArrayList<AssetIdBean>();
		
        if(session!=null && session.getAttribute("loginID")!=null) {
        	Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
        	Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
        	String userType = session.getAttribute("userType").toString();
        	List<ProfileBean> listm = new ArrayList<ProfileBean>();
			listm = profileService.getMapstate(regId, userType);
			for (ProfileBean bean : listm) {
				stateName = bean.getStatename();
				
			}
			
			mav = new ModelAndView("listofWorkWiseStatus");
			list=createAssetIdService.getListofWorkWiseStatus(projid, fyear, hactivity, wstatus);
			ProjectList = serk.getJanbhagidariPratiyogitaProject(Integer.parseInt(district));
			financial = physicalActionPlanService.getFromYearForPhysicalActionPlanReport(projid);
			mav = new ModelAndView("listofWorkWiseStatus");
			mav.addObject("ListofworkWiseStatus", list);
			mav.addObject("ListCount", list.size());
			mav.addObject("stateName", stateName);
			mav.addObject("hactivity", hactivity);
			mav.addObject("wstatus", wstatus);
			mav.addObject("district", district);
			mav.addObject("financial", financial);
			mav.addObject("finyear", fyear);
			mav.addObject("ProjectList", ProjectList);
			mav.addObject("projId", projid);
			mav.addObject("distList", ser.getDistrictList(stcd));
        }
        else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value="/getworkWiseStatus", method = RequestMethod.POST)
	public ModelAndView getworkWiseStatus(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		Integer workid  = Integer.parseInt(request.getParameter("workid"));
		String activityid= request.getParameter("activityid1");
		List<AssetIdBean> list=new  ArrayList<AssetIdBean>();
		 if(session!=null && session.getAttribute("loginID")!=null) {
			 mav = new ModelAndView("listofWorkWiseStatus");
			 Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
			 Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			 list=createAssetIdService.getWorkWiseStatus(workid, activityid, stcd);
			 String userType = session.getAttribute("userType").toString();
			 String stateName = "";
			 List<ProfileBean> listm = new ArrayList<ProfileBean>();
			 listm = profileService.getMapstate(regId, userType);
			 for (ProfileBean bean : listm) {
					stateName = bean.getStatename();
					
				}
			 mav.addObject("workWiseStatusRecords", list);
			 mav.addObject("RecordsCount", list.size());
			 mav.addObject("stateName", stateName);
			 mav.addObject("distList", ser.getDistrictList(stcd));	
			 mav.addObject("hactivity1", activityid);	
			 
		 }
		 else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
			return mav;
	}
}