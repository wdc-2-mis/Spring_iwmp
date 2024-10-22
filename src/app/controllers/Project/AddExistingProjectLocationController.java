package app.controllers.Project;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
import app.bean.ProjectLocationBean;
import app.service.BlockMasterService;
import app.service.ProjectLocationService;
import app.service.ProjectMasterService;
import app.service.master.AddExistingProjectLocationService;

@Controller("AddExistingProjectLocationController")
public class AddExistingProjectLocationController {
	
HttpSession session;
	
	@Autowired
	ProjectLocationService plservice;
	
	@Autowired
	ProjectMasterService pmservice;
	
	@Autowired
	AddExistingProjectLocationService service;
	
	@Autowired
	BlockMasterService blockservice;
	
	@RequestMapping(value="/addExistingProjectLocation", method = RequestMethod.GET)
	public ModelAndView addExistingProjectLocation(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		//String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String user_id = (String) session.getAttribute("loginID");
			
			mav = new ModelAndView("project/addExistingProjectLocation");
			mav.addObject("type", session.getAttribute("userType"));
			mav.addObject("projectList",service.getProjectLocationProject(regId));
			
			mav.addObject("getmappingproject",plservice.getmpngprolist(user_id));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getVillageBlockProjWise", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getVillageBlockWise(HttpServletRequest request, @RequestParam(value ="bcode") Integer bcode,
			@RequestParam(value ="project") Integer project)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(service.getVillageBlockWise(bcode, project));
		return map;
	}
	
	
	@RequestMapping(value="/saveasdraftexisting", method = RequestMethod.POST)
	@ResponseBody
	public Boolean saveasdraft(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="vCode") List<Integer> vCode,@RequestParam(value ="pcode") Integer pcode)
	{
		String result="";
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = service.saveProjectLocationAsDraft(vCode,pcode,session.getAttribute("loginID").toString());
			}else {
				flag=false;
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return flag; 
	}
	
	@RequestMapping(value="/completeExistingData", method = RequestMethod.POST)
	@ResponseBody
	public Boolean completedData(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="vCode") List<Integer> vCode,@RequestParam(value ="pcode") Integer pcode)
	{
		String result="";
		Boolean flag=false;     ///   https://hdmovies23.wiki
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = plservice.completeProjectLocation(vCode,pcode,session.getAttribute("loginID").toString());
			}else {
				flag=false;
			}
			
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		
		return flag; 
	}
	
	@RequestMapping(value="/existingWatershedData", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<ProjectLocationBean>> watersheddata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<ProjectLocationBean>> map = new LinkedHashMap<Integer,List<ProjectLocationBean>>();
		List<ProjectLocationBean> proj = new ArrayList<ProjectLocationBean>();
		List<ProjectLocationBean> proj1 = new ArrayList<ProjectLocationBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			//proj = plservice.getPreFilledProjectLocationData(projectId);
		//	proj = service.getPiaAssignWc(projectId);
		//	proj = plservice.getPiaAssignWc(projectId);
			proj = service.getPiaAssignWc(projectId);
			proj1 = plservice.getWsCommittee(projectId);
			map.put(0, proj);
			map.put(1, proj1);
		}else {
			proj=null;
			proj1=null;
		}
		return map;  
	}
	
	@RequestMapping(value="/saveaswcdraftExisting", method = RequestMethod.POST)
	@ResponseBody
	public Boolean saveaswcdraft(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="final") String final1)
//	public Boolean saveaswcdraft(HttpServletRequest request, HttpServletResponse response, @RequestParam("activity") List<String> activity, @RequestParam("plid") List<Integer> plid, @RequestParam("project") Integer project)
	{
		String result="";
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				//flag = service.saveWCLocationAsDraft(activity, plid, session.getAttribute("loginID").toString(), project);
				flag = plservice.saveWCLocationAsDraft(final1,session.getAttribute("loginID").toString());
			}else {
				flag=false;
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return flag; 
	}
	
	@RequestMapping(value="/completeWCdataExisting", method = RequestMethod.POST)
	@ResponseBody
	public Boolean completeWCdata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projwcid") String projwcid, 
			@RequestParam(value ="projid") String projid)
	{
		String result="";
		Boolean flag=false;
		//System.out.println("value of proj:" +projwcid);
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = service.completeWCMappingExisting(session.getAttribute("loginID").toString(), Integer.parseInt(projid));
			}else {
				flag=false;
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return flag; 
	}

}
