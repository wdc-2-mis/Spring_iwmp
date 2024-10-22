package app.controllers.model;

import java.util.LinkedHashMap;

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
import app.service.master.ActivityAssetParameterService;

@Controller("ActivityAssetParameter")
public class ActivityAssetParameter {
	
	HttpSession session;
	
	@Autowired
	ActivityAssetParameterService assetParameterService;
	
	@RequestMapping(value="/getActivityAssetParameter", method = RequestMethod.GET)
	public ModelAndView getActivityAssetParameter(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("master/activityAssetParameter");
			mav.addObject("headList",assetParameterService.getHead());
			mav.addObject("unitList",assetParameterService.getUnitMasters());
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/saveAssetParameter", method = RequestMethod.POST)
	@ResponseBody
	public String saveAsDraft(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="ahead") Integer ahead,
			@RequestParam(value ="aActivity") Integer aActivity, @RequestParam(value ="parameterDesc") String parameterDesc,
			@RequestParam(value ="aUnit") Integer aUnit)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			res=assetParameterService.saveAssetParameter(ahead,aActivity,parameterDesc,aUnit,session.getAttribute("loginID").toString());
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}

}
