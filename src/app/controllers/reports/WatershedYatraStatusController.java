package app.controllers.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.service.reports.WatershedYatraReportService;



@Controller("watershedYatraStatusController")
public class WatershedYatraStatusController {
	
	@Autowired(required = true)
	WatershedYatraReportService ser;
	
	@RequestMapping(value = "/getWatershedYatraStatus", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraStatus(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		try {
			mav = new ModelAndView("WatershedYatra/watershedYatraStatus");
			mav.addObject("getRecords",ser.getStateWiseWatershedYatraStatus());
			
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		
		return mav;
	}
	

}
