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
import app.model.Outcome2Data;
import app.model.WdcpmksyQuadTarget;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.reports.TargetAchievementQuarterService;
import app.service.unfreeze.UnfreezeAddParaOutcomeService;

@Controller
public class UnfreezeAddParaOutcomeController {
	
HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired
	public UnfreezeAddParaOutcomeService serv;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> ProjectList;
	
	@RequestMapping(value="/unfreezeAddParaOutcome", method = RequestMethod.GET)
	public ModelAndView unfreezeAddParaOutcome(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String finCode = request.getParameter("year");
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeAddParaOutcome");
			
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
	
	
	@RequestMapping(value="/getAddParaOutcomeDetails", method = RequestMethod.POST)
	public ModelAndView getAddParaOutcomeDetails(HttpServletRequest request, HttpServletResponse response)
	{
		List<Outcome2Data> list = new ArrayList<>();
		session = request.getSession(true);
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String project= request.getParameter("project");
			String finCode = request.getParameter("year");
			
			list = serv.getAddParaOutcomeData(Integer.parseInt(finCode), Integer.parseInt(project));
			
			if(session!=null && session.getAttribute("loginID")!=null) {
				
				mav = new ModelAndView("unfreeze/unfreezeAddParaOutcome");
//				mav.addObject("menu", menuController.getMenuUserId(request));
				mav.addObject("projlistUnfreezeBasel", list);
				mav.addObject("projlistUnfreezeBaselSize", list.size());
				
				
				stateList=stateMasterService.getAllState();
				mav.addObject("stateList", stateList);
				mav.addObject("state", userState);
//				
				if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);}
				mav.addObject("district", district);
//				
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
	
	
	@RequestMapping(value="/unfreezeAddParaOutcomeData", method = RequestMethod.POST)
	public ModelAndView unfreezeAddParaOutcomeData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		List<Outcome2Data> list = new ArrayList<>();
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String project= request.getParameter("project");
			String finCode = request.getParameter("year");
			String id[] = request.getParameterValues("month");
			
			String result =null;
			
			
			if(session!=null && session.getAttribute("loginID")!=null) {
				
				mav = new ModelAndView("unfreeze/unfreezeAddParaOutcome");
//				mav.addObject("menu", menuController.getMenuUserId(request));
				for(String outId : id) {
					result = serv.unfreezeAddParaOutcomeData(Integer.parseInt(outId));
				}
				
				
				list = serv.getAddParaOutcomeData(Integer.parseInt(finCode), Integer.parseInt(project));
				mav.addObject("projlistUnfreezeBasel", list);
				mav.addObject("projlistUnfreezeBaselSize", list.size());
				
				
				if(result =="success") 
					mav.addObject("messageUpload", "Data Unfreeze Successfully !");
				else
					mav.addObject("messageUpload", "Data does not Unfreeze!");
//				mav.addObject("projlistUnfreezeBasel", list);
//				mav.addObject("projlistUnfreezeBaselSize", list.size());
				
				
				stateList=stateMasterService.getAllState();
				mav.addObject("stateList", stateList);
				mav.addObject("state", userState);
//				
				if(userState!=null && !userState.equals("") && !userState.equals("0")) {
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);}
				mav.addObject("district", district);
//				
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

}
