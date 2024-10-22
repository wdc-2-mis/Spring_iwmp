package app.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import app.bean.ProjectPrepareBean;
import app.model.master.IwmpMProjectPrepare;
import app.model.project.IwmpProjectPrepare;
import app.service.ProjectPrepareService;
import app.service.WSCommiteeService;

@Controller("ProjectPrepareController")
public class ProjectPrepareController {
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	public WSCommiteeService wscommitteeser;
	
	@Autowired(required = true) 
	public ProjectPrepareService projectPrepareService;
	 
	
	private Map<String, String> projectList;
	
	@RequestMapping(value="/projectPreparedness", method = RequestMethod.GET) 
	public ModelAndView getProjectPreparedness(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		String regid=session.getAttribute("regId").toString();
		String ProjId=request.getParameter("projectId");
		ModelAndView mav = new ModelAndView();
		List<IwmpMProjectPrepare> ProjectPreparelist=new  ArrayList<IwmpMProjectPrepare>();
		List<ProjectPrepareBean> addedPrepareList=new  ArrayList<ProjectPrepareBean>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("project/projectPreparedness");
			mav.addObject("menu", menuController.getMenuUserId(request));
			projectList = wscommitteeser.getUserProjectList(Integer.parseInt(regid));
			ProjectPreparelist=projectPrepareService.getprojectPreparedness();
			if(ProjId!=null && ProjId!="")
				addedPrepareList=projectPrepareService.getAddPreparedness(Integer.parseInt(ProjId));
			
			mav.addObject("projectList", projectList);
			mav.addObject("ProjectPreparelist", ProjectPreparelist);
			mav.addObject("addedPrepareList", addedPrepareList);
			mav.addObject("ProjId", ProjId);
			/*
			 * for(int i=0;i<ProjectPreparelist.size();i++) {
			 * System.out.println(ProjectPreparelist.get(i).getProjectPrepareId()); }
			 */
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/saveProjectPreparedness", method = RequestMethod.POST) 
	public ModelAndView saveProjectPreparedness(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		String regid=session.getAttribute("regId").toString();
		String ProjId=request.getParameter("projectId");
		String yes=request.getParameter("res"); 
		ModelAndView mav = new ModelAndView();
		List<IwmpMProjectPrepare> ProjectPreparelist=new  ArrayList<IwmpMProjectPrepare>();
		List<ProjectPrepareBean> addedPrepareList=new  ArrayList<ProjectPrepareBean>();
		Integer i=0;
		String [] allcheck=yes.split(",");
		HashMap<Integer,String> map = new HashMap<Integer,String>();
				for(String str:allcheck) 
				{
					String[] st = str.split("-");
					map.put(Integer.parseInt(st[1]), st[0]);
				}
				if(session!=null && session.getAttribute("loginID")!=null) 
				{
					mav = new ModelAndView("project/projectPreparedness");
					mav.addObject("menu", menuController.getMenuUserId(request));
					projectList = wscommitteeser.getUserProjectList(Integer.parseInt(regid));
					ProjectPreparelist=projectPrepareService.getprojectPreparedness();
					
					i=projectPrepareService.saveProjectPreparedness(ProjId, session, map);
					
					if(ProjId!=null && ProjId!="")
						addedPrepareList=projectPrepareService.getAddPreparedness(Integer.parseInt(ProjId));
					
					if(i==1) {
						mav.addObject("message", "Status of Project Preparedness save successfully.");
					}
					mav.addObject("projectList", projectList);
					mav.addObject("ProjectPreparelist", ProjectPreparelist);
					mav.addObject("ProjId", ProjId);
					mav.addObject("addedPrepareList", addedPrepareList);
					/*
					 * for(int i=0;i<ProjectPreparelist.size();i++) {
					 * System.out.println(ProjectPreparelist.get(i).getProjectPrepareId()); }
					 */
				}
				else {
					session.invalidate();
					mav = new ModelAndView("login");
					mav.addObject("login", new Login());
				}
				
		return mav; 
	}

}
