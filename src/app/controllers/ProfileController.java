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
import java.util.regex.Pattern;

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
	private static final Pattern INVALID_PATTERN = Pattern.compile("[<>\"';`]");

	@RequestMapping(value = "/profileSave", method = RequestMethod.POST)
	public ModelAndView profileSave(HttpServletRequest request,
	                                HttpServletResponse response,
	                                @ModelAttribute("userReg") UserBean userReg,
	                                @RequestParam(value = "userType") String utype)
	        throws UnknownHostException {

	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("loginID") == null) {
	        ModelAndView mav = new ModelAndView("login");
	        mav.addObject("login", new Login());
	        return mav;
	    }

	    ModelAndView mav;
	    Integer regid;

	    try {
	        regid = Integer.parseInt(userReg.getRegID());
	    } catch (NumberFormatException ex) {
	        mav = new ModelAndView("login");
	        mav.addObject("message", "Invalid request.");
	        return mav;
	    }

	    // Trim input values
	    userReg.setUserName(userReg.getUserName() != null ? userReg.getUserName().trim() : "");
	    userReg.setUserDepartment(userReg.getUserDepartment() != null ? userReg.getUserDepartment().trim() : "");
	    userReg.setUserDesignation(userReg.getUserDesignation() != null ? userReg.getUserDesignation().trim() : "");
	    userReg.setUserEmailId(userReg.getUserEmailId() != null ? userReg.getUserEmailId().trim() : "");
	    userReg.setUserMobileNo(userReg.getUserMobileNo() != null ? userReg.getUserMobileNo().trim() : "");
	    userReg.setUserPhoneNo(userReg.getUserPhoneNo() != null ? userReg.getUserPhoneNo().trim() : "");
	    userReg.setUserFaxNo(userReg.getUserFaxNo() != null ? userReg.getUserFaxNo().trim() : "");
	    userReg.setUserAddres(userReg.getUserAddres() != null ? userReg.getUserAddres().trim() : "");

	    // Required field validation
	    if (userReg.getUserName().isEmpty()
	            || userReg.getUserDepartment().isEmpty()
	            || userReg.getUserDesignation().isEmpty()
	            || userReg.getUserEmailId().isEmpty()
	            || userReg.getUserMobileNo().isEmpty()
	            || userReg.getUserAddres().isEmpty()) {

	        mav = getProfileData(request, response,
	                Integer.toString(regid),
	                userReg.getUserType(),
	                userReg.getUserid());

	        mav.addObject("message", "All mandatory fields are required.");
	        return mav;
	    }

	    // Length validation
	    if (userReg.getUserName().length() > 100
	            || userReg.getUserDepartment().length() > 100
	            || userReg.getUserDesignation().length() > 100
	            || userReg.getUserEmailId().length() > 100
	            || userReg.getUserAddres().length() > 500) {

	        mav = getProfileData(request, response,
	                Integer.toString(regid),
	                userReg.getUserType(),
	                userReg.getUserid());

	        mav.addObject("message", "Input exceeds maximum allowed length.");
	        return mav;
	    }

	    // Email validation
	    if (!userReg.getUserEmailId()
	            .matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

	        mav = getProfileData(request, response,
	                Integer.toString(regid),
	                userReg.getUserType(),
	                userReg.getUserid());

	        mav.addObject("message", "Invalid email address.");
	        return mav;
	    }

	    // Mobile validation
	    if (!userReg.getUserMobileNo().matches("^[6-9][0-9]{9}$")) {

	        mav = getProfileData(request, response,
	                Integer.toString(regid),
	                userReg.getUserType(),
	                userReg.getUserid());

	        mav.addObject("message", "Invalid mobile number.");
	        return mav;
	    }

	    // Optional Phone validation
	    if (userReg.getUserPhoneNo() != null
	            && !userReg.getUserPhoneNo().trim().isEmpty()
	            && !userReg.getUserPhoneNo().matches("^[0-9\\- ]{6,15}$")) {

	        mav = getProfileData(request, response,
	                Integer.toString(regid),
	                userReg.getUserType(),
	                userReg.getUserid());

	        mav.addObject("message", "Invalid phone number.");
	        return mav;
	    }

	    // XSS validation
	    if (INVALID_PATTERN.matcher(userReg.getUserName()).find()
	            || INVALID_PATTERN.matcher(userReg.getUserDepartment()).find()
	            || INVALID_PATTERN.matcher(userReg.getUserDesignation()).find()
	            || INVALID_PATTERN.matcher(userReg.getUserAddres()).find()) {

	        mav = getProfileData(request, response,
	                Integer.toString(regid),
	                userReg.getUserType(),
	                userReg.getUserid());

	        mav.addObject("message", "Invalid characters detected.");
	        return mav;
	    }

	    // Update State Mapping
	    int j = 0;

	    if ("ADMIN".equals(userReg.getUserType())
	            || "DL".equals(userReg.getUserType())) {

	        if (userReg.getUserState() != null
	                && !userReg.getUserState().trim().isEmpty()) {

	            String[] stateList = userReg.getUserState().split(",");
	            j = profileService.deleteInsertDolrState(regid, stateList);
	        }
	    }

	    // Save Profile History
	    List<UserReg> list = profileService.getUserDetail(regid);

	    String newUserName = userReg.getUserName().toUpperCase();

	    for (UserReg user : list) {

	        String existingUserName = user.getUserName().toUpperCase();

	        if (!newUserName.equals(existingUserName)) {

	            profileService.saveProfileHistory(
	                    regid,
	                    session.getAttribute("loginID").toString());

	            break;
	        }
	    }

	    // Save Profile
	    Integer res = profileService.saveProfile(userReg);

	    mav = getProfileData(
	            request,
	            response,
	            Integer.toString(regid),
	            userReg.getUserType(),
	            userReg.getUserid());

	    if (res == 1 && !("ADMIN".equals(userReg.getUserType()) && "DL".equals(utype))) {

	        mav.addObject("message", "Profile updated Successfully.");
	    }

	    if (j == 1 && ("ADMIN".equals(userReg.getUserType()) || "DL".equals(utype))) {

	        mav.addObject("message", "Profile/State updated Successfully.");
	    }

	    if (res != 1) {

	        mav.addObject("message", "Profile Update Failed. Please try again!");
	    }

	    mav.addObject("menu", menuController.getMenuUserId(request));
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
