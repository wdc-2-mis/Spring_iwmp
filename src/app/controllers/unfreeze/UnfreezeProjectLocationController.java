package app.controllers.unfreeze;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.StateToVillageBean;
import app.bean.UnfreezeProjectLoactionBean;
import app.bean.reports.UserFileUploadBean;
import app.bean.reports.UserUploadDetailsBean;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.reports.ListofStateToVillageService;
import app.service.unfreeze.UnfreezeLocationService;
@Controller("UnfreezeProjectLocation")
public class UnfreezeProjectLocationController {

	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public UnfreezeLocationService unfreezeService;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> ProjectList;
	
	@RequestMapping(value="/unfreezeProjectLocation", method = RequestMethod.GET)
	public ModelAndView listofsttovill(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeProjectLocation");
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
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getUnfreezeProjectLocation", method = RequestMethod.POST)
	public ModelAndView getUnfreezeProjectLocation(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		
		List<UnfreezeProjectLoactionBean> list=new  ArrayList<UnfreezeProjectLoactionBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeProjectLocation");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			list=unfreezeService.getUnfreezeProjectLocation(Integer.parseInt(district), Integer.parseInt(project));
			mav.addObject("projlistUnfreezeBasel", list);
			
		
			/*
			 * for(int i=0;i<list.size();i++) {
			 * System.out.println(list.get(i).getSt_name()+"= kedar"); }
			 */
			 
			
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
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value = "/deletePhysicalWorkIdTemp", method = RequestMethod.POST)
	public ModelAndView deletePhysicalWorkIdTemp(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		boolean WorkIdTemp=false;
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String projid=request.getParameter("projid");
		
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("unfreeze/unfreezeProjectLocation");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			WorkIdTemp= unfreezeService.deletePhysicalWorkIdTemp(Integer.parseInt(projid));
			if(WorkIdTemp ) 
					mav.addObject("messageUpload", "Data Deleted Successfully on this table iwmp_project_physical_asset_temp ");
			else
					mav.addObject("messageUpload", "Data not Deleted!");
			//Upper code is done for file delete 
			List<UnfreezeProjectLoactionBean> list=new  ArrayList<UnfreezeProjectLoactionBean>();
			
			list=unfreezeService.getUnfreezeProjectLocation(Integer.parseInt(district), Integer.parseInt(project));
			mav.addObject("projlistUnfreezeBasel", list);
			
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
	
	@RequestMapping(value = "/unfreezeBaselineSurveyPlotWise", method = RequestMethod.POST)
	public ModelAndView unfreezeBaselineSurveyPlotWise(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		String id = request.getParameter("id");
		boolean WorkIdTemp=false;
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String projid= request.getParameter("projid");
		
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("unfreeze/unfreezeProjectLocation");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			WorkIdTemp= unfreezeService.unfreezeBaselineSurveyPlotWise(Integer.parseInt(projid));
			if(WorkIdTemp ) 
					mav.addObject("messageUpload", "Data Unfreeze Successfully ");
			else
					mav.addObject("messageUpload", "Data not Unfreeze!");
			//Upper code is done for file delete  https://7starhd.monster
			List<UnfreezeProjectLoactionBean> list=new  ArrayList<UnfreezeProjectLoactionBean>();
			
			list=unfreezeService.getUnfreezeProjectLocation(Integer.parseInt(district), Integer.parseInt(project));
			mav.addObject("projlistUnfreezeBasel", list);
			
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

	@RequestMapping(value = "/unfreezeProjectLocatin", method = RequestMethod.POST)
	public ModelAndView unfreezeProjectLocatin(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		String id = request.getParameter("id");
		boolean WorkIdTemp=false;
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String projid=request.getParameter("projid");
		
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("unfreeze/unfreezeProjectLocation");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			WorkIdTemp= unfreezeService.unfreezeProjectLocatin(Integer.parseInt(projid));
			if(WorkIdTemp ) 
					mav.addObject("messageUpload", "Project Location Unfreeze Successfully ");
			else
					mav.addObject("messageUpload", "Project Location not Unfreeze!");
			
			List<UnfreezeProjectLoactionBean> list=new  ArrayList<UnfreezeProjectLoactionBean>();
			
			list=unfreezeService.getUnfreezeProjectLocation(Integer.parseInt(district), Integer.parseInt(project));
			mav.addObject("projlistUnfreezeBasel", list);
			
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
