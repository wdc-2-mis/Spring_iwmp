package app.controllers.unfreeze;

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
import app.bean.UnfreezeBaselineSurveyDataBean;
import app.bean.UnfreezeProjectLoactionBean;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.unfreeze.UnfreezeBaselineServices;
import app.service.unfreeze.UnfreezeLocationService;

@Controller("UnfreezeBaselineSurveyData")
public class UnfreezeBaselineSurveyDataController {
	
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public UnfreezeBaselineServices ser;
	
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> ProjectList;
	private Map<String, String> villList;
	
	@RequestMapping(value="/unfreezeBaselineSurveyData", method = RequestMethod.GET)
	public ModelAndView listofsttovill(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String village= request.getParameter("village");
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeBaselineSurveyData");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);}
				mav.addObject("project", project);
				
			if( project!=null && !project.equalsIgnoreCase("") && !project.equals("0")) {
				villList = ser.getBaselineVillageList(Integer.parseInt(project));
				mav.addObject("villList", villList);}
				mav.addObject("vill", village);	
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getUnfreezeBaselineSurveyData", method = RequestMethod.POST)
	public ModelAndView getUnfreezeProjectLocation(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String project= request.getParameter("project");
			String village= request.getParameter("village");
			
			List<UnfreezeBaselineSurveyDataBean> list=new  ArrayList<UnfreezeBaselineSurveyDataBean>();
			
			
			if(session!=null && session.getAttribute("loginID")!=null) {
				
				mav = new ModelAndView("unfreeze/unfreezeBaselineSurveyData");
				mav.addObject("menu", menuController.getMenuUserId(request));
				
				list=ser.getUnfreezeBaselineSurveyData(Integer.parseInt(village), Integer.parseInt(project));
				mav.addObject("projlistUnfreezeBasel", list);
				mav.addObject("projlistUnfreezeBaselSize", list.size());
				
				
				stateList=stateMasterService.getAllState();
				mav.addObject("stateList", stateList);
				mav.addObject("state", userState);
				
				if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);}
				mav.addObject("district", district);
				
				if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
					ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
					mav.addObject("ProjectList", ProjectList);}
					mav.addObject("project", project);
					
				if( project!=null && !project.equalsIgnoreCase("") && !project.equals("0")) {
					villList = ser.getBaselineVillageList(Integer.parseInt(project));
					mav.addObject("villList", villList);}
					mav.addObject("vill", village);	
				
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
			return mav; 
	}
	
	@RequestMapping(value = "/unfreezeBaselineSurveyDataComplete", method = RequestMethod.POST)
	public ModelAndView unfreezeBaselineSurveyPlotWise(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		
		boolean WorkIdTemp=false;
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String village= request.getParameter("village");
		String villcode[]= request.getParameterValues("villcode");
		
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("unfreeze/unfreezeBaselineSurveyData");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			WorkIdTemp= ser.unfreezeBaselineSurveyDataComplete(villcode);
			if(WorkIdTemp ) 
					mav.addObject("messageUpload", "Data Unfreeze Successfully !");
			else
					mav.addObject("messageUpload", "Data not Unfreeze!");
			//Upper code is done for file delete  https://7starhd.monster
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);}
				mav.addObject("project", project);

		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	

}
