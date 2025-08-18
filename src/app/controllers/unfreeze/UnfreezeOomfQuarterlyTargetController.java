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
import app.controllers.MenuController;
import app.model.WdcpmksyQuadTarget;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.reports.TargetAchievementQuarterService;
import app.service.unfreeze.UnfreezeOomFQuarterlyTarService;

@Controller
public class UnfreezeOomfQuarterlyTargetController {
	
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired
	public UnfreezeOomFQuarterlyTarService serv;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> ProjectList;
	
	@RequestMapping(value="/unfreezeOomfQuarterlyTarget", method = RequestMethod.GET)
	public ModelAndView unfreezeOomfQuarterlyTarget(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String finCode = request.getParameter("year");
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			mav = new ModelAndView("unfreeze/unfreezeOomfQuarterlyTarget");
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
	
	@RequestMapping(value="/getOmmfQuarterlyTarData", method = RequestMethod.POST)
	public ModelAndView getOmmfQuarterlyTarData(HttpServletRequest request, HttpServletResponse response)
	{
		List<WdcpmksyQuadTarget> list = new ArrayList<>();
		session = request.getSession(true);
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String project= request.getParameter("project");
			String finCode = request.getParameter("year");
			
			list = serv.getOomFQuarterlyTardata(Integer.parseInt(finCode), Integer.parseInt(project));
			WdcpmksyQuadTarget wdc = new WdcpmksyQuadTarget();
			String all = "";
			if(!list.isEmpty()) {
				wdc = list.get(0);
				all = wdc.getQ1status()!='C'?"disabled":wdc.getQ2status()!='C'?"disabled":wdc.getQ3status()!='C'?"disabled":wdc.getQ4status()!='C'?"disabled":"";
			}
			
			if(session!=null && session.getAttribute("loginID")!=null) {
				
				mav = new ModelAndView("unfreeze/unfreezeOomfQuarterlyTarget");
//				mav.addObject("menu", menuController.getMenuUserId(request));
				if (!list.isEmpty()) {
					mav.addObject("qAllStatus", all);
					mav.addObject("q1status", wdc.getQ1status() == 'C' ? "" : "disabled");
					mav.addObject("q2status", wdc.getQ2status() == 'C' ? "" : "disabled");
					mav.addObject("q3status", wdc.getQ3status() == 'C' ? "" : "disabled");
					mav.addObject("q4status", wdc.getQ4status() == 'C' ? "" : "disabled");
				}
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
	
	@RequestMapping(value="/unfreezeOmmfQuarterlyTarData", method = RequestMethod.POST)
	public ModelAndView unfreezeOmmfQuarterlyTarData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		List<WdcpmksyQuadTarget> list = new ArrayList<>();
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String project= request.getParameter("project");
			String finCode = request.getParameter("year");
			String Quarter[] = request.getParameterValues("qurtr");
			
			String result =null;
			
			
			if(session!=null && session.getAttribute("loginID")!=null) {
				
				mav = new ModelAndView("unfreeze/unfreezeOomfQuarterlyTarget");
//				mav.addObject("menu", menuController.getMenuUserId(request));
				
				if(Quarter.length ==4 || Quarter.length ==1) {
					Integer qurtr = Quarter.length==4?0:Integer.parseInt(Quarter[0]);
					result=serv.unfreezeOomfQuarterlyTarData(Integer.parseInt(finCode), Integer.parseInt(project), qurtr);
				}else {
					for(String quartr : Quarter) {
						result=serv.unfreezeOomfQuarterlyTarData(Integer.parseInt(finCode), Integer.parseInt(project), Integer.parseInt(quartr));
					}
				}
				list = serv.getOomFQuarterlyTardata(Integer.parseInt(finCode), Integer.parseInt(project));
				WdcpmksyQuadTarget wdc = new WdcpmksyQuadTarget();
				String all = "";
				if(!list.isEmpty()) {
					wdc = list.get(0);
					all = wdc.getQ1status()!='C'?"disabled":wdc.getQ2status()!='C'?"disabled":wdc.getQ3status()!='C'?"disabled":wdc.getQ4status()!='C'?"disabled":"";
					mav.addObject("qAllStatus", all);
					mav.addObject("q1status", wdc.getQ1status() == 'C' ? "" : "disabled");
					mav.addObject("q2status", wdc.getQ2status() == 'C' ? "" : "disabled");
					mav.addObject("q3status", wdc.getQ3status() == 'C' ? "" : "disabled");
					mav.addObject("q4status", wdc.getQ4status() == 'C' ? "" : "disabled");
				}
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
