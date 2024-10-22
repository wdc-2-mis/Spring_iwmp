package app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.FPOReportBean;
import app.bean.Login;
import app.bean.StateProfileWDCBean;
import app.service.Disapprovalservice;
import app.service.StateProfileWDCService;

@Controller("addStateProfileController")
public class AddStateProfileController {

	HttpSession session;
	
	@Autowired(required = true)
	Disapprovalservice disApprovalService;
	
	@Autowired(required = true)
	StateProfileWDCService stateProfileWDCService;
	
	@RequestMapping(value = "/addstateprofile", method = RequestMethod.GET)
	public ModelAndView addstateprofile(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		Integer stateCode = (Integer)session.getAttribute("stateCode");
        if(session!=null && session.getAttribute("loginID")!=null) {
        	mav = new ModelAndView("addstateprofile");
        	mav.addObject("stateVector", disApprovalService.getAllState());
            mav.addObject("stateprofiledata",stateProfileWDCService.getstateprofiledata(stateCode));
        }
        else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return mav; 
	}
	
	@RequestMapping(value = "/saveStateProfile", method = RequestMethod.POST)
	@ResponseBody
	public String saveStateProfile(HttpServletRequest request, HttpServletResponse response, @RequestParam("status")String status, @RequestParam("district")Integer district, @RequestParam("blocks")Integer blocks, @RequestParam("mircowatersheds")Integer mircowatersheds,
			@RequestParam("geoarea") BigDecimal geoarea, @RequestParam("untreatarea")BigDecimal untreatarea, @RequestParam("iwmpProjects") BigDecimal iwmpProjects, @RequestParam("WaterShedP") BigDecimal WaterShedP, @RequestParam("AssIrrigation") BigDecimal AssIrrigation,
			@RequestParam("areacoverwdc") BigDecimal areacoverwdciwmp)
	{
		ModelAndView mav = new ModelAndView();
		Integer stateCode = (Integer)session.getAttribute("stateCode");
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = stateProfileWDCService.savestateprofiledata(status, stateCode, district, blocks, mircowatersheds, geoarea, untreatarea, iwmpProjects, WaterShedP, AssIrrigation, session.getAttribute("loginID").toString(),areacoverwdciwmp);
		}
		return res;
	}
	
	
	
	
	@RequestMapping(value="/downloadpdf", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<StateProfileWDCBean>> getfpodata(HttpServletRequest request,@RequestParam(value ="sCode") Integer sCode)
	{
		LinkedHashMap<Integer,List<StateProfileWDCBean>> map = new LinkedHashMap<Integer,List<StateProfileWDCBean>>();
		map=stateProfileWDCService.getfpodatastatewise(sCode);
		//System.out.println("hi:" +stateProfileWDCService.getfpodatastatewise(sCode).size());
		return map;
	}
	
}
