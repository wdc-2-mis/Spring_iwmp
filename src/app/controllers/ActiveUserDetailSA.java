package app.controllers;

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
import org.springframework.web.servlet.ModelAndView;

import app.bean.LastActionLogBean;
import app.bean.Login;
import app.bean.User;
import app.model.IwmpUserActionLog;
import app.model.IwmpUserProjectMap;
import app.model.UserMap;
import app.model.UserReg;
import app.service.ActiveUserDetailService;
import app.service.StateMasterService;

@Controller("ActiveUserDetailSA")
public class ActiveUserDetailSA {
	
	HttpSession session;
	
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	ActiveUserDetailService audServies;
	
	@Autowired
	StateMasterService stateMasterService;
	
	private Map<Integer, String> stateList;
	
	@RequestMapping(value="/acitiveUserDetailsSA", method = RequestMethod.GET)
	public ModelAndView userRoleMap(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("user/activeUserDetailSA");
			mav.addObject("menu", menuController.getMenuUserId(request));
			mav.addObject("type", session.getAttribute("userType"));
			String usertype = (String)session.getAttribute("userType");
			mav.addObject("uktype", usertype);
			mav.addObject("uktype1", usertype);
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	@RequestMapping(value="/acitiveUserDetailsSA", method = RequestMethod.POST)
	public ModelAndView getUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("userReg") UserReg userReg )
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav;
		String utype=request.getParameter("userType").toString();
		String state=request.getParameter("state").toString();
		String status=request.getParameter("status").toString();
//		List<UserReg> list=new  ArrayList<UserReg>();
//		list=audServies.getUserDetail(Long.parseLong(state), utype);
		List<UserMap> list=new  ArrayList<UserMap>();
		List<IwmpUserProjectMap> listpm=new  ArrayList<IwmpUserProjectMap>();
		
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("user/activeUserDetailSA");
			mav.addObject("menu", menuController.getMenuUserId(request));
			if(utype.equals("SL"))
			{
				list=audServies.getUserMap(Long.parseLong(state), status);
				mav.addObject("userList", list);
			}
			if(utype.equals("PI"))
			{
				listpm=audServies.getUserMap(Long.parseLong(state), utype, status);
				mav.addObject("userList", listpm);
			}
			mav.addObject("uktype", utype);
			String usertype = (String)session.getAttribute("userType");
			mav.addObject("uktype1", usertype);
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			
//			 for(int i=0;i<list.size();i++) {
//			 System.out.println("userId: "+list.get(i).getUserReg().getUserName());
//			 }
			 
			 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
		
	}
	
	@RequestMapping(value="/lastActivity", method = RequestMethod.GET)
	public ModelAndView lastActivityByUser(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String state= request.getParameter("state"); 
		ModelAndView mav = new ModelAndView();
		
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("reports/lastActivity");
			mav.addObject("menu", menuController.getMenuUserId(request));
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/lastActivity", method = RequestMethod.POST)
	public ModelAndView lastActivityByUserSD(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);
		String state= request.getParameter("state"); 
		String SL;
	    String DI;
		ModelAndView mav = new ModelAndView();
	//	List<IwmpUserActionLog> list=new  ArrayList<IwmpUserActionLog>();
		List<LastActionLogBean> list=new  ArrayList<LastActionLogBean>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("reports/lastActivity");
			mav.addObject("menu", menuController.getMenuUserId(request));
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
	    	if(state.length()==2)
	    	{
	    		SL="SL"+state+"%";
	    		DI="DI"+state+"%";
	    	}
	    	else{
	    		SL="SL0"+state+"%";
		    	DI="DI0"+state+"%";
	    	}
	    //	list=audServies.getUserLogMap(Long.parseLong(state), SL, DI);
	    	list=audServies.getUserDetailLog(Long.parseLong(state), SL, DI);
			mav.addObject("userLogList", list);
			mav.addObject("state", state);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}

}
