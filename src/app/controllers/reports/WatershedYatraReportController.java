package app.controllers.reports;

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

import app.bean.Login;
import app.bean.UnfreezeBaselineSurveyDataBean;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;
import app.watershedyatra.bean.NodalOfficerBean;

@Controller("WatershedYatraReportController")
public class WatershedYatraReportController {
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	WatershedYatraReportService ser;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> blockList;
	private Map<String, String> gpList;
	
	@RequestMapping(value="/getWatershedYatraReport", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraReport(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		String grampan= request.getParameter("grampan");
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("WatershedYatra/watershedYatraReportRoutePlan");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = ser.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				blockList = ser.getblockList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("blockList", blockList);}
				mav.addObject("blkd", block);
				
			if( block!=null && !block.equalsIgnoreCase("") && !block.equals("0")) {
				gpList = ser.getGramPanchyatList(Integer.parseInt(block));
				mav.addObject("gpList", gpList);}
				mav.addObject("grampn", grampan);	
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getRoutePlanReportData", method = RequestMethod.POST)
	public ModelAndView getRoutePlanReportData(HttpServletRequest request, HttpServletResponse response)
	{
			session = request.getSession(true);
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String block= request.getParameter("block");
			String grampan= request.getParameter("grampan");
			
			List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
			
			
			if(session!=null && session.getAttribute("loginID")!=null) {
				
				mav = new ModelAndView("WatershedYatra/watershedYatraReportRoutePlan");
				mav.addObject("menu", menuController.getMenuUserId(request));
				
				list=ser.getRoutePlanReportData(Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(block), Integer.parseInt(grampan));
				mav.addObject("routePlanList", list);
				mav.addObject("routePlanListSize", list.size());
				
				
				stateList=stateMasterService.getAllState();
				mav.addObject("stateList", stateList);
				mav.addObject("state", userState);
				
				if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = ser.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);}
				mav.addObject("district", district);
				
				if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
					blockList = ser.getblockList(Integer.parseInt(userState), Integer.parseInt(district));
					mav.addObject("blockList", blockList);}
					mav.addObject("blkd", block);
					
				if( block!=null && !block.equalsIgnoreCase("") && !block.equals("0")) {
					gpList = ser.getGramPanchyatList(Integer.parseInt(block));
					mav.addObject("gpList", gpList);}
					mav.addObject("grampn", grampan);	
				
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
			return mav; 
	}
	
	@RequestMapping(value="/getWatershedYatraNodalReport", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraNodalReport(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String lvl= request.getParameter("level");
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("WatershedYatra/watershedYatraReportNodalOfficer");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("state", "State");
			map.put("district", "District");
			map.put("block", "Block/Project");
			map.put("village", "Village/Van Standing Point");
			mav.addObject("level",map);
			mav.addObject("lvl",lvl);
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = ser.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);}
				mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				blockList = ser.getblockList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("blockList", blockList);}
				mav.addObject("blkd", block);
				
		
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}

	@RequestMapping(value="/getNodalOfficerReportData", method = RequestMethod.POST)
	public ModelAndView getNodalOfficerReportData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String lvl= request.getParameter("level");
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		
		List<NodalOfficerBean> list = new ArrayList<NodalOfficerBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			if(district==null)
				district="0";
			if(block==null)
				block="0";
			
			mav = new ModelAndView("WatershedYatra/watershedYatraReportNodalOfficer");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			list=ser.getNodalOfficerReportData(lvl, Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(block));
			mav.addObject("routePlanList", list);
			mav.addObject("routePlanListSize", list.size());
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("state", "State");
			map.put("district", "District");
			map.put("block", "Block/Project");
			map.put("village", "Village/Van Standing Point");
			mav.addObject("level",map);
			mav.addObject("lvl",lvl);
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = ser.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);}
				mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				blockList = ser.getblockList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("blockList", blockList);}
				mav.addObject("blkd", block);
				
		
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
}
