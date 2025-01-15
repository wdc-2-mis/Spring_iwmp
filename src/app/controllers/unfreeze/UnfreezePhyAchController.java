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
import app.bean.PhysicalAchievementBean;
import app.bean.ShgDetailBean;
import app.model.project.WdcpmksyProjectPhysicalAchievementDetails;
import app.service.PhysicalAchievementService;
import app.service.StateMasterService;
import app.service.UserService;

@Controller("unfreezePhyAchController")
public class UnfreezePhyAchController {

	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	PhysicalAchievementService physicalAchievementService;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	private Map<Integer, String> finYear;
	private Map<Integer, String> month;
	
	@RequestMapping(value="/unfreezePhyAchievement", method=RequestMethod.GET)
	public ModelAndView unfreezePhyAchievement(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("unfreeze/unfreezePhyAchievement");
			stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
		    finYear = userService.getCurrentFinYear();    
		    mav.addObject("finYear", finYear);
		    month = userService.getnotcompletedmonth();
		    mav.addObject("NCmonth", month);
		    
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
		mav.addObject("project", project);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}

	@RequestMapping(value="/showPhyAchDetails", method=RequestMethod.POST)
	public ModelAndView showPhyAchDetails(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);

		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		
		
		String fyear = request.getParameter("year");
		String monthdtl = request.getParameter("month");
		
		List<WdcpmksyProjectPhysicalAchievementDetails> list = new  ArrayList<WdcpmksyProjectPhysicalAchievementDetails>();
		List<PhysicalAchievementBean>beanList = new ArrayList<PhysicalAchievementBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezePhyAchievement");
			
			list = physicalAchievementService.showPhyAchRecords(Integer.parseInt(project), Integer.parseInt(fyear), Integer.parseInt(monthdtl));
			
			for(WdcpmksyProjectPhysicalAchievementDetails lst : list) {
				PhysicalAchievementBean bean =  new PhysicalAchievementBean();
				bean.setAchievementid(lst.getWdcpmksyProjectPhysicalAchievement().getAchievementId());
				bean.setActivitydesc(lst.getIwmpMPhyActivity().getActivityDesc());
				bean.setAchievement(lst.getAchievement());
				bean.setHeaddesc(lst.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
				beanList.add(bean);
			}
			mav.addObject("phyachList", beanList);
			mav.addObject("phyachSize", beanList.size());
			finYear = userService.getCurrentFinYear();    
		    mav.addObject("finYear", finYear);
		    month = userService.getnotcompletedmonth();
		    mav.addObject("NCmonth", month);
			
			mav.addObject("month", monthdtl);
			mav.addObject("year", fyear);
			mav.addObject("project", project);
			
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
		
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}

	@RequestMapping(value="/unfreezePhyAchData", method=RequestMethod.POST)
	public ModelAndView unfreezePhyAchData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String project = request.getParameter("project");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		String achievementid = request.getParameter("achievementid");
		boolean status=false;
          if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("unfreeze/unfreezePhyAchievement");
			status= physicalAchievementService.unfreezePhyAchData(Integer.parseInt(project), Integer.parseInt(month), Integer.parseInt(year), Integer.parseInt(achievementid));
			if(status ) 
				mav.addObject("messageUpload", "Data Unfreezed Successfully!");
		    else
				mav.addObject("messageUpload", "Data not Unfreeze!");
          }
          
          else {
  			mav = new ModelAndView("login");
  			mav.addObject("login", new Login());
  		}
          stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
	return mav;
	}	
}
