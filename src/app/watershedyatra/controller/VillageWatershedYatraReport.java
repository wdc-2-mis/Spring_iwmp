package app.watershedyatra.controller;

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

import app.bean.Login;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.service.VillageWatershedYatraReportService;

@Controller("VillageWatershedYatraReport")
public class VillageWatershedYatraReport {


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
	private Map<String, String> districtList;
	private Map<String, String> blockList;
	private Map<String, String> gpList;
	
	@RequestMapping(value="/getWatershedYatraVillageReport", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraVillageReport(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String block= request.getParameter("block");
		String grampan= request.getParameter("grampan");
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("WatershedYatra/wyVillageReport");
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
	@RequestMapping(value="/showWatershedYatraVillageReport", method = RequestMethod.POST)
	public ModelAndView showWatershedYatraVillageReport(HttpServletRequest request, HttpServletResponse response)
	{
			session = request.getSession(true);
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String block= request.getParameter("block");
			String grampan= request.getParameter("grampan");
			
			List<WatershedYatraBean> list = new ArrayList<WatershedYatraBean>();
			
			
			if(session!=null && session.getAttribute("loginID")!=null) {
				
				mav = new ModelAndView("WatershedYatra/wyVillageReport");
				mav.addObject("menu", menuController.getMenuUserId(request));
				
				list=service.showWatershedYatraVillageReport(Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(block), Integer.parseInt(grampan));
				mav.addObject("dataList", list);
				mav.addObject("dataListSize", list.size());
				
				
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
}
