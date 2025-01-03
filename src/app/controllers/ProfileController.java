package app.controllers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.ProfileBean;
import app.bean.User;
import app.bean.UserBean;
import app.model.UserMap;
import app.model.UserReg;
import app.service.ProfileService;
import app.service.UserService;


@Controller("profileController")
public class ProfileController 
{
	@Autowired(required = true)
	ProfileService profileService;
	
	HttpSession session;
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	public UserReg userReg;
	
	@Autowired(required = true)
	public UserService userService;
	
	private Map<String, String> stateList;
	
	// editProfile use to get the data of user from database
	@RequestMapping(value = "/editprofile", method = RequestMethod.GET)
	public ModelAndView editProfile(HttpServletRequest request, HttpServletResponse response) 
	{
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			String regid = session.getAttribute("regId").toString();
			String userType = session.getAttribute("userType").toString();
			String userId = session.getAttribute("loginID").toString();
			return getProfileData(request,response,regid,userType,userId);
		}else {
			ModelAndView mav = new ModelAndView("login");
			return mav;
		}
		
		
	}
	
	public ModelAndView getProfileData(HttpServletRequest request, HttpServletResponse response,String regid,String userType, String userId) 
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView("editProfile");
		LinkedHashMap<Integer, List<ProfileBean>> map = new LinkedHashMap<Integer, List<ProfileBean>>();
		
			stateList = userService.getStateList();
			List<UserReg> list=new  ArrayList<UserReg>();
			List<ProfileBean> listm=new  ArrayList<ProfileBean>();
			list=profileService.getUserDetail(Integer.parseInt(regid));
			listm=profileService.getMapstate(Integer.parseInt(regid), userType);
			List<ProfileBean> sublist = new ArrayList<ProfileBean>();
			if(userType.equals("ADMIN") || userType.equals("DL") )
			{
				sublist = new ArrayList<ProfileBean>();
				ProfileBean profileBean = new ProfileBean();
				if ((listm != null) && (listm.size() > 0)) 
				{
					for (Map.Entry<String, String> entry : stateList.entrySet()) 
					{
						for (ProfileBean row : listm) 
						{
							profileBean = new ProfileBean();
				        	if(!map.containsKey(row.getStatecode()) && stateList.containsKey(row.getStatecode().toString())) 
				        	{
				        		profileBean.setSelected("selected");
				        		profileBean.setStatecode(row.getStatecode());
				        		profileBean.setStatename(stateList.get(row.getStatecode().toString()));
								sublist = new ArrayList<ProfileBean>();
								sublist.add(profileBean);
								map.put(row.getStatecode(), sublist);
								 //break second ;
							}
				        	else if(!map.containsKey(Integer.parseInt(entry.getKey())) )
				        	{ 
									 sublist = new ArrayList<ProfileBean>();
									 profileBean.setSelected(" ");
									 profileBean.setStatecode(Integer.parseInt(entry.getKey()));
									 profileBean.setStatename(stateList.get(entry.getKey()));
									 sublist.add(profileBean);
									 map.put(Integer.parseInt(entry.getKey()), sublist);
									// break second ;
							}
							else {
								  
							}				        	
				        }
				    }
				}
			}
			else if(userType.equals("SL") || userType.equals("DI") ) 
			{
				sublist = new ArrayList<ProfileBean>();
				for (ProfileBean row : listm) 
				{
					sublist.add(row);
					map.put(row.getStatecode(), sublist);
				}
			}
			else if(userType.equalsIgnoreCase("PI")  ) 
			{
				sublist = new ArrayList<ProfileBean>();
				for (ProfileBean row : listm) 
				{
					sublist.add(row);
					map.put(row.getStatecode(), sublist);
				}
			}
				 
			mav.addObject("listm", map);
			//mav.addObject("menu", menuController.getMenuUserId(request));
			mav.addObject("loginId", session.getAttribute("loginID"));
			mav.addObject("list", list);
			mav.addObject("stateList", stateList);
			mav.addObject("regId",regid);
			mav.addObject("userType",userType);
			mav.addObject("userId",userId);
			//session.removeAttribute("editRegId");
		return mav;
		
	}
	
	// profileSave use to update the user details
	@RequestMapping(value = "/profileSave", method = RequestMethod.POST)
	public ModelAndView profileSave(HttpServletRequest request, HttpServletResponse response, 
			 @ModelAttribute("userReg") UserBean userReg, @RequestParam(value ="userType") String utype) throws UnknownHostException 
	{
		session = request.getSession(true);
		ModelAndView mav;
		Integer regid=Integer.parseInt(userReg.getRegID());
		List<UserReg> list=new  ArrayList<UserReg>();
		String uname=userReg.getUserName().toUpperCase();
		String tbUName=null;
		int his=0;
		String captcha = request.getParameter("CAPTCHAcode");
//		if(app.util.Util.getCSRFflag(captcha, session, request) == false)
//		{
//			System.out.println("csrf error >>>>>>>>>>");
//			mav =  new ModelAndView("CSRFerror");
//		}
//		else {
	//	String sutype=session.getAttribute("userType").toString().toUpperCase();
		//String utype=request.getParameter("userType");
		int j=0;
	//	String stList =request.getParameter("userState").toString();
		
		if(userReg.getUserType().equals("ADMIN") || userReg.getUserType().equals("DL"))
		{	
			String [] stateList=userReg.getUserState().split(",");
			j=profileService.deleteInsertDolrState(regid, stateList);
		}
		
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			list=profileService.getUserDetail(regid);
			for(int i=0;i<list.size();i++) 
			{
				tbUName=list.get(i).getUserName().toUpperCase();
				if(!uname.equals(tbUName))
				{
					 his =profileService.saveProfileHistory(regid,session.getAttribute("loginID").toString());
				}
			}
			Integer res =profileService.saveProfile(userReg);
			mav = getProfileData(request,response,Integer.toString(regid),userReg.getUserType(), userReg.getUserid());
			
			if(res==1 && !(userReg.getUserType().equals("ADMIN") && utype.equals("DL"))) 
			{
				mav.addObject("message", "Profile updated Successfully.");
			}
			if(j==1 && (userReg.getUserType().equals("ADMIN") || utype.equals("DL"))) 
			{
				mav.addObject("message", "Profile/State updated Successfully. ");
			}
			if(res!=1)
			{ 
				mav.addObject("message", "Profile Update Failed. Please try again !");
			}
			 
			mav.addObject("menu", menuController.getMenuUserId(request));
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
//		}
		//mav.addObject("menu", menuController.getMenuUserId(request));
		mav.addObject("loginId", session.getAttribute("loginID"));
		
		return mav;
	}
	
	
	@RequestMapping(value = "/getUserId", method = RequestMethod.POST)
	@ResponseBody
	public Integer getUserId(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="regId") Integer regId) 
	{
		return regId;
	}
	
	@RequestMapping(value = "/getUserDetail", method = RequestMethod.POST)
	public ModelAndView getUserDetail(HttpServletRequest request, HttpServletResponse response, 
			 @ModelAttribute("user") UserReg userReg) 
	{
		
		ModelAndView mav = new ModelAndView("user/userDetail");
		mav.addObject("regId",userReg.getRegId());
		mav.addObject("userReg",profileService.getUserDetailasUserReg(userReg.getRegId()));
		UserReg uReg =profileService.getUserDetailasUserReg(userReg.getRegId());
	//	System.out.println("userReg: "+profileService.getUserDetailasUserReg(userReg.getRegId()).getIwmpUserMaps().size());
		
		return mav;
	}
	
	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public ModelAndView editUserDetail(HttpServletRequest request, HttpServletResponse response, 
			 @ModelAttribute("user") UserReg userReg) 
	{
		System.out.println("regId: "+userReg.getRegId()+" : "+userReg.getUserType()+" : "+userReg.getUserId());
		session = request.getSession(true);
		//session.setAttribute("userType",userReg.getUserType());
		return getProfileData(request,response,Integer.toString(userReg.getRegId()),userReg.getUserType(), userReg.getUserId());
	}

	
}
