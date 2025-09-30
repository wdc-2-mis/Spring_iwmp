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
import app.model.project.WdcpmksyProjectPhysicalAchievementDetails;
import app.projectevaluation.bean.ProjectEvaluationBean;
import app.projectevaluation.model.WdcpmksyProjectProfileEvaluation;
import app.projectevaluation.service.ProjectEvaluationService;
import app.service.StateMasterService;
import app.service.UserService;
import app.watershedyatra.service.WatershedYatraService;

@Controller("unfreezeMidTermController")
public class UnfreezeMidTermController {

	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired
	WatershedYatraService ser;
	
	@Autowired
	ProjectEvaluationService PEService;
	
	private Map<Integer, String> stateList;
	private Map<Integer, String> districtList;
	private Map<String, String> projectList;
	
	@RequestMapping(value="/unfreezeMidTerm", method=RequestMethod.GET)
	public ModelAndView unfreezeMidTerm(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("unfreeze/unfreezeMidTermEval");
			stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
					districtList = ser.getDistrictList(Integer.parseInt(userState));
					mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getMidTermProjList(Integer.parseInt(district));
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
	
	@RequestMapping(value="/showMidTermDetails", method=RequestMethod.POST)
	public ModelAndView showMidTermDetails(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);

		ModelAndView mav = new ModelAndView();
		String userState = request.getParameter("state");
		String district = request.getParameter("district");
		String project = request.getParameter("project");
		
		List<WdcpmksyProjectProfileEvaluation> list = new  ArrayList<WdcpmksyProjectProfileEvaluation>();
		List<ProjectEvaluationBean>beanList = new ArrayList<ProjectEvaluationBean>();
        if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeMidTermEval");
			list = PEService.showMidTermDetails(Integer.parseInt(project));
			
			for(WdcpmksyProjectProfileEvaluation lst: list) {
				ProjectEvaluationBean bean = new ProjectEvaluationBean();
				bean.setFin_yr(lst.getIwmpMFinYear().getFinYrDesc());
				bean.setMonthname(lst.getIwmpMMonth().getMonthName());
				bean.setPagency(lst.getPagency());
				beanList.add(bean);
			}
			mav.addObject("midtermList", beanList);
			mav.addObject("midtermSize", beanList.size());
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			mav.addObject("project", project);
			if(userState!=null && !userState.equals("") && !userState.equals("0")) 
			{
				districtList = ser.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) 
			{
				projectList = userService.getMidTermProjList(Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
        }
        else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
        return mav;
	}   
	
	@RequestMapping(value="/unfreezeMidTermData", method=RequestMethod.POST)
	public ModelAndView unfreezeMidTermData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		String project = request.getParameter("project");
		boolean status=false;
          if(session!=null && session.getAttribute("loginID")!=null) {
			mav = new ModelAndView("unfreeze/unfreezeMidTermEval");
			status= PEService.unfreezeMidTermProj(Integer.parseInt(project));
			if(status) 
				mav.addObject("messageUpload", "Mid Term Project Unfreezed Successfully!");
		    else
				mav.addObject("messageUpload", "Project not Unfreeze!");
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
