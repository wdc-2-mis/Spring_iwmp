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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.UnfreezeEpaLivProdBean;
import app.service.CreateAssetIdService;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.unfreeze.UnfreezeEpaLivProdService;

@Controller("UnfreezeEpaLivProd")
public class UnfreezeEpaLivProdController {
	
	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public UnfreezeEpaLivProdService elpser;
	
	@Autowired(required = true)
	CreateAssetIdService createAssetIdService;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	LinkedHashMap<String, String> hmap = new LinkedHashMap<String, String>();
	LinkedHashMap<Integer,String> amap = new LinkedHashMap<Integer,String>();
	
	
	public UnfreezeEpaLivProdController() {
        hmap = new LinkedHashMap<>();
        initializeHeadMap();
    }
	
	private void initializeHeadMap() {
        hmap.put("e", "Entry Point Activity (EPAs)");
        hmap.put("l", "Livelihood Activities for the Assetless Persons & Microenterprises");
        hmap.put("p", "Production System");
    }
	
	@RequestMapping(value="/unfreezeEpaLivProd", method=RequestMethod.GET)
	public ModelAndView listofStToHead(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String head = request.getParameter("head");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("unfreeze/unfreezeEpaLivProd");
			
			stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
				
			if(district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			mav.addObject("maphead", hmap);
			
			mav.addObject("project", project);
			mav.addObject("head", head);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 	
	}
	
	@RequestMapping(value="/getUnfreezeEpaLivProdData", method=RequestMethod.POST)
	public ModelAndView getUnfreezeEpaLivProd(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);

		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String head = request.getParameter("head");
		
		List<UnfreezeEpaLivProdBean> list = new  ArrayList<UnfreezeEpaLivProdBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeEpaLivProd");
			
			list=elpser.getUnfreezeEpaLivProdData(Integer.parseInt(project),head);
			
			mav.addObject("epaLivProdList",list);
			mav.addObject("epaLivProdListSize", list.size());
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			
			if(district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			mav.addObject("maphead", hmap);
			
			mav.addObject("project", project);
			mav.addObject("head", head);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value="/unfreezeEpaLivProdData", method=RequestMethod.POST)
	public ModelAndView unfreezeEpaLivProd(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String head = request.getParameter("head");
		String actCode[]= request.getParameterValues("elp_id");
		boolean actId=false;
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeEpaLivProd");
			
			actId= elpser.unfreezeEpaLivProdData(actCode, head);
			if(actId )
					mav.addObject("messageUpload", "Data Unfreeze Successfully!");
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
			
			if(district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			mav.addObject("maphead", hmap);
			
			mav.addObject("project", project);
			mav.addObject("head", head);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value="/unfreezeWorkIdEpaLivProd", method=RequestMethod.GET)
	public ModelAndView listofStToHeadAct(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String head = request.getParameter("head");
		String act = request.getParameter("act");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("unfreeze/unfreezeWorkIdEpaLivProd");
			
			stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
				
			if(district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			mav.addObject("maphead", hmap);
			mav.addObject("head", head);
			
			if(head!=null) 
			{
				amap=createAssetIdService.getHeadActivitydesc(head);
				mav.addObject("mapact", amap);
			}
			
			mav.addObject("project", project);
			mav.addObject("act", act);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 	
	} 
	
	@RequestMapping(value="/getUnfreezeWorkIdEpaLivProd", method=RequestMethod.POST)
	public ModelAndView getUnfreezeWorkIdEpaLivProd(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);

		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String head = request.getParameter("head");
		String act = request.getParameter("act");
		
		List<UnfreezeEpaLivProdBean> list = new  ArrayList<UnfreezeEpaLivProdBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeWorkIdEpaLivProd");
			
			list=elpser.getUnfreezeWorkIdEpaLivProd(Integer.parseInt(project), head, Integer.parseInt(act));
			
			mav.addObject("epaLivProdWorkIdList",list);
			mav.addObject("epaLivProdWorkIdListSize", list.size());
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			
			if(district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			mav.addObject("maphead", hmap);
			
			amap=createAssetIdService.getHeadActivitydesc(head);
			mav.addObject("mapact", amap);
			
			mav.addObject("project", project);
			mav.addObject("head", head);
			mav.addObject("act", act);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value="/unfreezeWorkIdEpaLivProdData", method=RequestMethod.POST)
	public ModelAndView unfreezeWorkIdEpaLivProd(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		String head = request.getParameter("head");
		String act = request.getParameter("act");
		String actCode[]= request.getParameterValues("elp_id");
		boolean actId=false;
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeWorkIdEpaLivProd");
			
			actId= elpser.unfreezeWorkIdEpaLivProdData(actCode, head);
			if(actId )
					mav.addObject("messageUpload", "Data Unfreeze Successfully!");
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
			
			if(district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			
			mav.addObject("maphead", hmap);
			
			amap=createAssetIdService.getHeadActivitydesc(head);
			mav.addObject("mapact", amap);
			
			mav.addObject("project", project);
			mav.addObject("head", head);
			mav.addObject("act", act);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
}
