package app.controllers.reports;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.NRSCWorksBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.service.VillageWatershedYatraReportService;

@Controller("WorkStatusDateWiseReportController")
public class WorkStatusDateWiseReportController {
	
	HttpSession session; 
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	WatershedYatraReportService ser;
	
	@Autowired(required = true)
	VillageWatershedYatraReportService service;
	
	private Map<Integer, String> stateList;
	//private Map<String, String> districtList;
	//private Map<String, String> blockList;
	//private Map<String, String> gpList;
	
	
	@RequestMapping(value="/getWorkStatusReport", method = RequestMethod.GET)
	public ModelAndView getWorkStatusReport(HttpServletRequest request, HttpServletResponse response)
	{
		
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		
			mav = new ModelAndView("reports/WorkStatusDateWiseReport");
			mav.addObject("menu", menuController.getMenuUserId(request));
		
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
		return mav; 
	}
	
	@RequestMapping(value="/getWorkStatusReportpost", method = RequestMethod.POST)
	public ModelAndView getWorkStatusReportpost(HttpServletRequest request, HttpServletResponse response)
	{
			
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String userdate= request.getParameter("userdate");
			String userdateto= request.getParameter("userdateto");
			String stName= request.getParameter("stName");
			
			List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
			String fromDateStr=null;
			String toDateStr =null;
			
			if(!userdate.equals("")) {
	        LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
	        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			if(!userdateto.equals("")) {
	        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
	        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
				
				mav = new ModelAndView("reports/WorkStatusDateWiseReport");
				mav.addObject("menu", menuController.getMenuUserId(request));
				
				list=service.getWorkStatusReport(Integer.parseInt(userState), userdate, userdateto);
				
				int createdwork=0;
				int startedwork=0;
				int ongoing=0;
				int completed=0;
				int forclosed=0;
				
				
				for(NRSCWorksBean bean: list) {
					

					createdwork = createdwork + bean.getCreatedwork();
					startedwork = startedwork + bean.getStartedwork();
					ongoing = ongoing + bean.getOngoing();
					completed = completed + bean.getCompleted();
					forclosed = forclosed + bean.getForclosed();
					
				}
				
				
				mav.addObject("createdwork1", createdwork);
				mav.addObject("startedwork1", startedwork);
				mav.addObject("ongoing1", ongoing);
				mav.addObject("completed1", completed);
				mav.addObject("forclosed1", forclosed);
				
				mav.addObject("dataList", list);
				mav.addObject("dataListSize", list.size());
				
				mav.addObject("userdate", userdate);
				mav.addObject("dateto", userdateto);
				mav.addObject("fromDateStr", fromDateStr);
				mav.addObject("toDateStr", toDateStr);
				
				stateList=stateMasterService.getAllState();
				mav.addObject("stateList", stateList);
				mav.addObject("state", userState);
				
				mav.addObject("stName", stName);
				
			
			return mav; 
	}

}
