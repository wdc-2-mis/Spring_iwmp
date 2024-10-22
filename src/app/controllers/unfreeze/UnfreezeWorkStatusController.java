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

import app.UnfreezeWorkStatusDataBean;
import app.bean.Login;
import app.bean.UnfreezeBaselineSurveyDataBean;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.reports.TargetAchievementQuarterService;
import app.service.unfreeze.UnfreezeBaselineServices;
import app.service.unfreeze.UnfreezeWorkStatusServices;

@Controller("UnfreezeWorkStatusController")
public class UnfreezeWorkStatusController {
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@Autowired(required = true)
	UnfreezeWorkStatusServices serv;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> ProjectList;
	
	@RequestMapping(value="/unfreezeWorkStatusData", method = RequestMethod.GET)
	public ModelAndView listofsttovill(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String finCode = request.getParameter("year");
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeWorkStatus");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			mav.addObject("financialYear", ser.getYearonward22());
			mav.addObject("year", finCode);
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);
			}
			mav.addObject("project", project);
				
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getUnfreezeWorkStatusData", method = RequestMethod.POST)
	public ModelAndView getUnfreezeWorkStatusData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String project= request.getParameter("project");
			String finCode = request.getParameter("year");
			
			List<UnfreezeWorkStatusDataBean> list=new  ArrayList<UnfreezeWorkStatusDataBean>();
			
			
			if(session!=null && session.getAttribute("loginID")!=null) {
				
				mav = new ModelAndView("unfreeze/unfreezeWorkStatus");
				mav.addObject("menu", menuController.getMenuUserId(request));
				
				list=serv.getUnfreezeWorkStatusData(Integer.parseInt(finCode), Integer.parseInt(project), Integer.parseInt(district));
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
				
					mav.addObject("financialYear", ser.getYearonward22());
					mav.addObject("year", finCode);
				
				
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
			return mav; 
	}
	
	@RequestMapping(value = "/unfreezeWorkStatusComplete", method = RequestMethod.POST)
	public ModelAndView unfreezeWorkStatusComplete(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		
		boolean WorkIdTemp=false;
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String finCode = request.getParameter("year");
		String workcode[]= request.getParameterValues("workcode");
		
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("unfreeze/unfreezeWorkStatus");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			WorkIdTemp= serv.unfreezeWorkStatusComplete(workcode, project, finCode);
			if(WorkIdTemp ) 
					mav.addObject("messageUpload", "Data Unfreeze Successfully !");
			else
					mav.addObject("messageUpload", "Data Existing in Achievement. not Unfreeze!");
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
			
				mav.addObject("financialYear", ser.getYearonward22());
				mav.addObject("year", finCode);
		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}

}
