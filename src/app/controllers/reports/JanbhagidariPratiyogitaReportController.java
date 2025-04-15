package app.controllers.reports;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.AssetIdBean;
import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaBean;
import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaService;
import app.service.StateMasterService;
import app.service.UserService;

@Controller("JanbhagidariPratiyogitaReportController")
public class JanbhagidariPratiyogitaReportController {
	
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired
	JanbhagidariPratiyogitaService serk;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	
	@RequestMapping(value="/janbhagidariPratiyogitaReport", method = RequestMethod.GET)
	public ModelAndView janbhagidariPratiyogitaReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
		try {
			
			mav = new ModelAndView("reports/janbhagidariPratiyogitaReport");
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			if(userState!=null && !userState.equals("")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			if(district!=null && !district.equals("")) 
			{
				projectList=userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			mav.addObject("district", district);
			mav.addObject("project", project);
			
			data=serk.getListJanbhagidariPratiyogitaDetails();
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value="/janbhagidariPratiyogitaALLReport", method = RequestMethod.POST)
	public ModelAndView janbhagidariPratiyogitaALLReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
		try {
			
			mav = new ModelAndView("reports/janbhagidariPratiyogitaReport");
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			if(userState!=null && !userState.equals("")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			if(district!=null && !district.equals("")) 
			{
				projectList=userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			mav.addObject("district", district);
			mav.addObject("project", project);
			
			data=serk.janbhagidariPratiyogitaALLReport(userState, district, project);
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value="/getListofNGONameWithGPandVillage", method = RequestMethod.POST)
	@ResponseBody
	public List<JanbhagidariPratiyogitaBean> getListofNGONameWithGPandVillage(HttpServletRequest request, 
			@RequestParam(value ="projid") Integer projid) throws ParseException
	{
		List<JanbhagidariPratiyogitaBean> sublist = new ArrayList<JanbhagidariPratiyogitaBean>();
		
		
		sublist=serk.getListofNGONameWithGPandVillage(projid);
		
		
		return sublist;
	}
	
	@RequestMapping(value="/getListofswckGPand", method = RequestMethod.POST)
	@ResponseBody
	public List<JanbhagidariPratiyogitaBean> getListofswckGPand(HttpServletRequest request, 
			@RequestParam(value ="projid") Integer projid) throws ParseException
	{
		List<JanbhagidariPratiyogitaBean> sublist = new ArrayList<JanbhagidariPratiyogitaBean>();
		
		
		sublist=serk.getListofswckGPand(projid);
		
		
		return sublist;
	}

}
