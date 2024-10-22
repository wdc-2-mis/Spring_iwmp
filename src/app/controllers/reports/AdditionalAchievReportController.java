package app.controllers.reports;

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
import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.model.WdcpmksyMQuadIndicators;
import app.service.CommonService;
import app.service.StateMasterService;
import app.service.outcome.AdditionalBroughtFarmerCropAreaServices;

@Controller("additionalAchievReportController")
public class AdditionalAchievReportController {
	 HttpSession session=null;
	
	 @Autowired
	 AdditionalBroughtFarmerCropAreaServices addService;
	 
	 @Autowired(required = true)
	 private CommonService commonService;
	 
	 @RequestMapping(value = "/getAddAchRecords", method = RequestMethod.GET)
		public ModelAndView addAchReport(HttpServletRequest request, HttpServletResponse response) {
			session = request.getSession(true);
			ModelAndView mav = new ModelAndView();

			if(session!=null && session.getAttribute("loginID")!=null) 
			{
				mav = new ModelAndView("reports/addAchRecords");
				mav.addObject("financialYear", commonService.getAllFinancialYear());
			}
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
			
			return mav;
		}
	
	@RequestMapping(value = "/getAddAchRecords", method = RequestMethod.POST)
	public ModelAndView addAchreport(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		Integer stateCode = Integer.parseInt(session.getAttribute("stateCode").toString());
		
		String finyr = request.getParameter("fincode");
		String finName = request.getParameter("finName");
		
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("reports/addAchRecords");
			mav.addObject("getachdata",addService.getstateWiseAdditionalAchievement(stateCode, Integer.parseInt(finyr)));
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		mav.addObject("finyr",finyr);
		mav.addObject("finName",finName);
		mav.addObject("financialYear", commonService.getAllFinancialYear());
		return mav;
	}

	@RequestMapping(value="/getNotAdditionalParameter", method = RequestMethod.POST)
	@ResponseBody
	public List<AdditionalBroughtFarmerCropAreaBean> getNotAdditionalParameter(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="dcode") Integer dcode, 
			@RequestParam(value="type")String type, @RequestParam(value="finyr")Integer finyr)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<AdditionalBroughtFarmerCropAreaBean>> map = new LinkedHashMap<Integer,List<AdditionalBroughtFarmerCropAreaBean>>();
		List<AdditionalBroughtFarmerCropAreaBean> proj = new ArrayList<AdditionalBroughtFarmerCropAreaBean>();
        if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = addService.getnotyearlyPara(dcode, type, finyr);
			
			
		}else {
			proj=null;
			
		}
		return proj;
	}
	
}
