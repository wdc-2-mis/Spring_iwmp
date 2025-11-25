package app.mahotsav.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.controllers.MenuController;
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.service.WMReportService;

@Controller("wMProjectLevelReportController")
public class WMProjectLevelReportController {

	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	WMReportService WMSerice;
	
	@RequestMapping(value = "/projectWMProgramReport", method = RequestMethod.GET)
	public ModelAndView getprojectWMProgramReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		mav = new ModelAndView("mahotsav/wmProjLvlProgReport");
		mav.addObject("menu", menuController.getMenuUserId(request));
		
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		
		list = WMSerice.getProjLvlWMPrgReport();
		
		mav.addObject("projLvlWMPrgList",list);
		mav.addObject("projLvlWMPrgListSize",list.size());
		
		return mav; 
	}
}
