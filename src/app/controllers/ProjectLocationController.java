package app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
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
import app.bean.ProjectLocationBean;
import app.model.IwmpMProject;
import app.model.master.IwmpBlock;
import app.service.BlockMasterService;
import app.service.ProjectLocationService;
import app.service.ProjectMasterService;
import app.service.master.AddExistingProjectLocationService;

@Controller("projectLocationController")
public class ProjectLocationController {
	HttpSession session;
	
	@Autowired
	ProjectLocationService plservice;
	
	@Autowired
	ProjectMasterService pmservice;
	
	@Autowired
	BlockMasterService blockservice;
	
	@Autowired
	AddExistingProjectLocationService service;
	
	@RequestMapping(value="/piaPjtNotLocatiaon", method = RequestMethod.GET)
	public ModelAndView piaProjectLocationMapping(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		//String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String user_id = (String) session.getAttribute("loginID");
			
			mav = new ModelAndView("project/projectLocationMapping");
			mav.addObject("type", session.getAttribute("userType"));
			mav.addObject("projectList",pmservice.getProjectByRegId(regId));
			mav.addObject("getmappingproject",plservice.getmpngprolist(user_id));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/piaBlockProjectWise", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> piaBlockProjectWise(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,String> map =new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			IwmpMProject proj = pmservice.getProjectByProjectId(projectId);
			
			map=blockservice.getBlockByDistCode(proj.getIwmpDistrict().getDcode());
//			for(IwmpBlock block : proj.getIwmpDistrict().getIwmpBlocks()) {
//				map.put(block.getBcode(),block.getBlockName());
//			}
			
		}else {
			map=null;
		}
		return map; 
	}
	
	@RequestMapping(value="/piaVillageBlockWise", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> piaVillageBlockWise(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="bcode") Integer bCode)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,String> map =new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			IwmpMProject proj = pmservice.getProjectByProjectId(bCode);
			for(IwmpBlock block : proj.getIwmpDistrict().getIwmpBlocks()) {
				map.put(block.getBcode(),block.getBlockName());
			}
			
		}else {
			map=null;
		}
		return map; 
	}
	
	@RequestMapping(value="/saveasdraft", method = RequestMethod.POST)
	@ResponseBody
	public Boolean saveasdraft(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="vCode") List<Integer> vCode,@RequestParam(value ="pcode") Integer pcode)
	{
		String result="";
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = plservice.saveProjectLocationAsDraft(vCode,pcode,session.getAttribute("loginID").toString());
			}else {
				flag=false;
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return flag; 
	}
	
	@RequestMapping(value="/saveaswcdraft", method = RequestMethod.POST)
	@ResponseBody
	public Boolean saveaswcdraft(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="final") String final1)
	{
		
		//System.out.println("i am in" +final1);
		String result="";
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				//flag = plservice.saveWCLocationAsDraft(final1,session.getAttribute("loginID").toString());
				flag = plservice.saveWCLocationAsDraft(final1,session.getAttribute("loginID").toString());
			}else {
				flag=false;
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return flag; 
	}
	
	
	@RequestMapping(value="/completedata", method = RequestMethod.POST)
	@ResponseBody
	public Boolean completedData(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="vCode") List<Integer> vCode,@RequestParam(value ="pcode") Integer pcode)
	{
		String result="";
		Boolean flag=false;
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = plservice.completeProjectLocation(vCode,pcode,session.getAttribute("loginID").toString());
			}else {
				flag=false;
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return flag; 
	}
	
	@RequestMapping(value="/getPreFilledProjectLocationData", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<ProjectLocationBean>> getPreFilledProjectLocationData(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<ProjectLocationBean>> map = new LinkedHashMap<Integer,List<ProjectLocationBean>>();
		List<ProjectLocationBean> proj = new ArrayList<ProjectLocationBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			proj = plservice.getPreFilledProjectLocationData(projectId);
			map.put(0, proj);
		}else {
			proj=null;
		}
		return map;  
	}
	
	@RequestMapping(value="/watersheddata", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<ProjectLocationBean>> watersheddata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<ProjectLocationBean>> map = new LinkedHashMap<Integer,List<ProjectLocationBean>>();
		List<ProjectLocationBean> proj = new ArrayList<ProjectLocationBean>();
		List<ProjectLocationBean> proj1 = new ArrayList<ProjectLocationBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			//proj = plservice.getPreFilledProjectLocationData(projectId);
			proj = plservice.getPiaAssignWc(projectId);
			proj1 = plservice.getWsCommittee(projectId);
			map.put(0, proj);
			map.put(1, proj1);
		}else {
			proj=null;
			proj1=null;
		}
		return map;  
	}

	
	
	@RequestMapping(value="/watersheddraftdata", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<ProjectLocationBean>> watersheddraftdata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<ProjectLocationBean>> map = new LinkedHashMap<Integer,List<ProjectLocationBean>>();
		List<ProjectLocationBean> proj = new ArrayList<ProjectLocationBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			//proj = plservice.getPreFilledProjectLocationData(projectId);
			proj = plservice.getPiaAssigndraftWc(projectId);
			map.put(0, proj);
			
		}else {
			proj=null;
			
		}
		return map;  
	}
	@RequestMapping(value="/delwatersheddraftdata", method = RequestMethod.POST)
	@ResponseBody
	public Boolean delwatersheddraftdata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="pwccode") Integer pwccode)
	{
		//System.out.println("i am in" +final1);
				String result="";
				Boolean flag=false;
				try{
					session = request.getSession(true);
					if(session!=null && session.getAttribute("loginID")!=null) {
						flag = plservice.detPiaAssigndraftWc(pwccode);
					}else {
						flag=false;
					}
					
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
				return flag; 
		
	}

	@RequestMapping(value="/completeWCdata", method = RequestMethod.POST)
	@ResponseBody
	public Boolean completeWCdata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projwcid") String projwcid, 
			@RequestParam(value ="projid") String projid)
	{
		String result="";
		Boolean flag=false;
		System.out.println("value of proj:" +projwcid);
		try{
			session = request.getSession(true);
			if(session!=null && session.getAttribute("loginID")!=null) {
				flag = plservice.completeWCMapping(projwcid,session.getAttribute("loginID").toString(), Integer.parseInt(projid));
			}else {
				flag=false;
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return flag; 
	}

	@RequestMapping(value="/getcheckWCStatus", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<ProjectLocationBean>> getcheckWCStatus(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<ProjectLocationBean>> map = new LinkedHashMap<Integer,List<ProjectLocationBean>>();
		List<ProjectLocationBean> proj = new ArrayList<ProjectLocationBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			proj = plservice.getcheckWCStatus(projectId);
			map.put(0, proj);
		}else {
			proj=null;
		}
		return map;  
	}

	@RequestMapping(value="/watershedfinaldata", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<ProjectLocationBean>> watershedfinaldata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<ProjectLocationBean>> map = new LinkedHashMap<Integer,List<ProjectLocationBean>>();
		List<ProjectLocationBean> proj = new ArrayList<ProjectLocationBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			//proj = plservice.getPreFilledProjectLocationData(projectId);
			proj = plservice.getPiaAssignfinalWc(projectId);
			map.put(0, proj);
			
		}else {
			proj=null;
			
		}
		return map;  
	}
}
