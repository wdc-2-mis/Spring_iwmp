package app.controllers.unfreeze;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.ShgDetailBean;
import app.controllers.MenuController;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.unfreeze.UnfreezeShgDetailService;

@Controller("UnfreezeShgDetailController")
public class UnfreezeShgDetailController {
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public UnfreezeShgDetailService shgService;
	
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	
	

	@RequestMapping(value="/unfreezeShgDetails", method=RequestMethod.GET)
	public ModelAndView unfreezeShgDetails(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String head = request.getParameter("head");
		String headType = request.getParameter("headType");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("unfreeze/unfreezeShgDetails");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
				
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
			map2.put("ShgA", "SHG Account");
			map2.put("ShgD", "SHG Details");
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("newShg", "Newly Created SHG");
			map.put("exShg", "Existing SHG");
			
			mav.addObject("maphead", map);
			mav.addObject("maphd2", map2);
			mav.addObject("project", project);
			mav.addObject("head", head);
			mav.addObject("headType", headType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 	
	}
	

	@RequestMapping(value="/unfreezeListShgDetails", method=RequestMethod.POST)
	public ModelAndView unfreezeListShgDetails(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);

		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String grp = request.getParameter("head");
		String headType = request.getParameter("headType");
		
		List<ShgDetailBean> list = new  ArrayList<ShgDetailBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeShgDetails");
			
			list = shgService.unfreezeListShgDetails(Integer.parseInt(project), grp, headType);
			
			mav.addObject("shgList",list);
			mav.addObject("shgListSize", list.size());
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("newShg", "Newly Created SHG");
			map.put("exShg", "Existing SHG");
			mav.addObject("maphead", map);
			LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
			map2.put("ShgA", "SHG Account");
			map2.put("ShgD", "SHG Details");
			mav.addObject("project", project);
			mav.addObject("head", grp);
			mav.addObject("maphd2", map2);
			mav.addObject("headType", headType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value="/unfreezeShgDetailsData", method=RequestMethod.POST)
	public ModelAndView unfreezeShgDetailsData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String shg_id[]= request.getParameterValues("shg_detail_id");
		String grp = request.getParameter("head");
		String headType = request.getParameter("headType");
		
		boolean shgId=false;
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeShgDetails");
			
			shgId= shgService.unfreezeShgDetailsData(shg_id, headType);
			if(shgId ) 
					mav.addObject("messageUpload", "Data Unfreezed Successfully!");
			else
					mav.addObject("messageUpload", "Data not Unfreeze!");
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("newShg", "Newly Created SHG");
			map.put("exShg", "Existing SHG");
			mav.addObject("maphead", map);
			LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
			map2.put("ShgA", "SHG Account");
			map2.put("ShgD", "SHG Details");
			mav.addObject("project", project);
			mav.addObject("head", grp);
			mav.addObject("maphd2", map2);
			mav.addObject("headType", headType);
			mav.addObject("shg_detail_id", shg_id);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
}
