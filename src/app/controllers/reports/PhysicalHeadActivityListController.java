package app.controllers.reports;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.GroundWaterTableBean;
import app.bean.reports.PhysicalHeadActivityListBean;
import app.service.reports.PhysicalHeadActivityListServices;

@Controller("PhysicalHeadActivityListController")
public class PhysicalHeadActivityListController {
	
	@Autowired(required = true)	
	PhysicalHeadActivityListServices phalser;
	
	@RequestMapping(value="/getPhysicalHeadActivityList", method = RequestMethod.GET)
	public ModelAndView getPhysicalHeadActivityList(HttpServletRequest request, HttpServletResponse response)
	{
			
			ModelAndView mav = new ModelAndView();
			mav = new ModelAndView("reports/physicalHeadActivityList");
			
			List<PhysicalHeadActivityListBean> list=new  ArrayList<PhysicalHeadActivityListBean>();
			
			list = phalser.getPhysicalHeadActivityList();
			
			mav.addObject("dataList",list);
			mav.addObject("dataListsize",list.size());
			
		
		return mav;
	}

}
