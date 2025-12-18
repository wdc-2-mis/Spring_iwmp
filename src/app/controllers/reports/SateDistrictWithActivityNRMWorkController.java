package app.controllers.reports;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

import app.bean.NRSCWorksBean;
import app.controllers.MenuController;
import app.service.PhysicalActionPlanService;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.reports.PhysicalActionAchievementService;
import app.watershedyatra.service.VillageWatershedYatraReportService;

@Controller("SateDistrictWithActivityNRMWorkController")
public class SateDistrictWithActivityNRMWorkController {
	
	HttpSession session; 
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired
	PhysicalActionPlanService physicalActionPlanService;
	
	@Autowired(required = true)
	VillageWatershedYatraReportService service;
	
	private Map<String, String> actList;
	private Map<String, String> districtList;
	
	@RequestMapping(value="/getActivityNRMWorkReport", method = RequestMethod.GET)
	public ModelAndView getActivityNRMWorkReport(HttpServletRequest request, HttpServletResponse response)
	{
		
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String finyr= request.getParameter("finyear");
			String head= request.getParameter("head");
			
			mav = new ModelAndView("reports/sateDistrictWithActivityNRMWork");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			LinkedHashMap<Integer, String> headList = userService.getHead();
			mav.addObject("headList",headList);
			
			mav.addObject("stateList",stateMasterService.getAllState());
			mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
			
			if(userState!=null && !userState.equals("")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			
			if(head!=null && !head.equals("")) 
			{
				actList = userService.getActivityList(Integer.parseInt(head));
				mav.addObject("activityList", actList);
			}
			
			mav.addObject("state", userState);
			mav.addObject("dist", district);
			mav.addObject("yrcd", finyr);
			mav.addObject("headcd", head);
			
		return mav; 
	}
	
	@RequestMapping(value="/getActivityNRMWorkReportPost", method = RequestMethod.POST)
	public ModelAndView getActivityNRMWorkReportPost(HttpServletRequest request, HttpServletResponse response)
	{
			
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String finyr= request.getParameter("finyear");
			String head= request.getParameter("head");
			String activity= request.getParameter("activity");
			
			mav = new ModelAndView("reports/sateDistrictWithActivityNRMWork");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
			String fromDateStr=null;
			String toDateStr =null;
			
			list=service.getActivityNRMWorkReportPost(Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(finyr), Integer.parseInt(activity));
				
				int createdwork=0;
				int startedwork=0;
				int ongoing=0;
				int completed=0;
				int forclosed=0;
				
				
				for(NRSCWorksBean bean: list) 
				{
					
					createdwork = createdwork + bean.getCreatedwork();
					
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
				
				LinkedHashMap<Integer, String> headList = userService.getHead();
				mav.addObject("headList",headList);
				
				mav.addObject("stateList",stateMasterService.getAllState());
				mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
				
				if(userState!=null && !userState.equals("")) 
				{
					districtList = userService.getDistrictList(Integer.parseInt(userState));
					mav.addObject("districtList", districtList);
				}
				
				if(head!=null && !head.equals("")) 
				{
					actList = userService.getActivityList(Integer.parseInt(head));
					mav.addObject("activityList", actList);
				}
				
				mav.addObject("state", userState);
				mav.addObject("dist", district);
				mav.addObject("yrcd", finyr);
				mav.addObject("headcd", head);
				mav.addObject("actcd", activity);
				
			//	mav.addObject("stName", stName);
				
			
			return mav; 
	}
	
	@RequestMapping(value="/getActivityNRMWorkJalSakati", method = RequestMethod.GET)
	public ModelAndView getActivityNRMWorkJalSakati(HttpServletRequest request, HttpServletResponse response)
	{
			
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String finyr= request.getParameter("finyear");
			String head= request.getParameter("head");
			String activity= request.getParameter("activity");
			
			mav = new ModelAndView("reports/activityNRMWorkJalSakati");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
			list=service.getActivityNRMWorkJalSakati();
			
			BigInteger projcountt = BigInteger.ZERO;
			BigDecimal area_proposedt=BigDecimal.ZERO;
			BigDecimal project_costt=BigDecimal.ZERO;
			BigInteger farmpondst = BigInteger.ZERO;
			BigInteger checkdamst = BigInteger.ZERO;
			BigInteger nallahbundst = BigInteger.ZERO;
			BigInteger percolationtankst = BigInteger.ZERO;
			BigInteger groundwaterrechargestructuret = BigInteger.ZERO;
			BigInteger gullyplugst = BigInteger.ZERO;
			BigInteger otherst = BigInteger.ZERO;
			BigInteger amritsarovart = BigInteger.ZERO;
			
				for(NRSCWorksBean bean: list) 
				{
					projcountt=projcountt.add(bean.getProjcount());
					area_proposedt=area_proposedt.add(bean.getArea_proposed());
					project_costt=project_costt.add(bean.getProject_cost());
					
					farmpondst=farmpondst.add(bean.getFarmponds());
					checkdamst=checkdamst.add(bean.getCheckdams());
					nallahbundst=nallahbundst.add(bean.getNallahbunds());
					percolationtankst=percolationtankst.add(bean.getPercolationtanks());
					groundwaterrechargestructuret=groundwaterrechargestructuret.add(bean.getGroundwaterrechargestructure());
					gullyplugst=gullyplugst.add(bean.getGullyplugs());
					otherst=otherst.add(bean.getOthers());
					amritsarovart=amritsarovart.add(bean.getAmritsarovar());
				}
				mav.addObject("dataList", list);
				mav.addObject("dataListSize", list.size());
				
				mav.addObject("projcount", projcountt);
				mav.addObject("area_proposed", area_proposedt);
				mav.addObject("project_cost", project_costt);
				mav.addObject("farmponds", farmpondst);
				mav.addObject("checkdams", checkdamst);
				mav.addObject("nallahbunds", nallahbundst);
				mav.addObject("percolationtanks", percolationtankst);
				mav.addObject("groundwaterrechargestructure", groundwaterrechargestructuret);
				mav.addObject("gullyplugs", gullyplugst);
				mav.addObject("others", otherst);
				mav.addObject("amritsarovar", amritsarovart);
				
			return mav; 
	}

}
