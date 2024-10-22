package app.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.model.IwmpMProject;
import app.model.master.IwmpMWc;
import app.service.WSCommiteeService;

@Controller("WatershedCommitteeController")
public class WatershedCommitteeController {

	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired(required = true)
	public WSCommiteeService wscommitteeser;
	
	private Map<String, String> projectList;
	private Map<String, String> wcCommitteeList;
	
	
	@RequestMapping(value="/piaListWSComittee", method = RequestMethod.GET) 
	public ModelAndView getWatershedCommittee(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		String regid=session.getAttribute("regId").toString();
		String ProjId=request.getParameter("projectId");
		ModelAndView mav = new ModelAndView();
		List<IwmpMWc> wcli=new ArrayList<IwmpMWc>();
		
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("project/projectLocationMapping");
			mav.addObject("menu", menuController.getMenuUserId(request));
			projectList = wscommitteeser.getUserProjectList(Integer.parseInt(regid));
			if(ProjId!=null) {
			wcCommitteeList=wscommitteeser.getWatershedCommitteeList(Integer.parseInt(ProjId));
			//wcli=wscommitteeser.getWCLocationAdded(Integer.parseInt(ProjId));
			mav.addObject("wcCommitteeList", wcCommitteeList);
			}
			mav.addObject("projectList", projectList);
			mav.addObject("ProjId", ProjId);
			mav.addObject("wcli", wcli);
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/piaListWSComitteeList", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, String> getWatershedCommitteeList(HttpServletRequest request, HttpServletResponse response,@RequestParam("projectId") Integer ProjId)
	{
		session = request.getSession(true);
		//String ProjId=request.getParameter("projectId");
		List<IwmpMWc> wcli=new ArrayList<IwmpMWc>();
		System.out.println("pid "+ProjId);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			if(ProjId!=null) {
			wcCommitteeList=wscommitteeser.getWatershedCommitteeList(ProjId);
			//wcli=wscommitteeser.getWCLocationAdded(Integer.parseInt(ProjId));
			}
		}
		else {
		}
		return wcCommitteeList; 
	}
	
	@RequestMapping(value="/saveWSComittee", method = RequestMethod.POST) 
	@ResponseBody
	public String  saveWSComittee(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		String regid=session.getAttribute("regId").toString();
		String ProjId=request.getParameter("projectId");
		String wcname=request.getParameter("wcname");
		ModelAndView mav = new ModelAndView();
		List<IwmpMWc> wcli=new ArrayList<IwmpMWc>();
		int save=0;
		String result=null;
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			save=wscommitteeser.saveWatershedCommittee(Integer.parseInt(ProjId), wcname, session);
			
			if(save!=0)
				result="success";
			else
				result="fail";
			
		}
		else {
			session.invalidate();
			result="fail";
		}
		return result;  
	}
	
	@RequestMapping(value="/updateWSComittee", method = RequestMethod.POST) 
	@ResponseBody
	public String updateWSComittee(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("wscommittee") IwmpMWc mwc)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		String regid=session.getAttribute("regId").toString();
		String ProjId=request.getParameter("projectId");
		String wcname=request.getParameter("wcname");
		ModelAndView mav = new ModelAndView();
		List<IwmpMWc> wcli=new ArrayList<IwmpMWc>();
		Integer res=0;
		IwmpMProject iwmpMProject=new IwmpMProject();
		iwmpMProject.setProjectId(Integer.parseInt(ProjId));
		mwc.setIwmpMProject(iwmpMProject);
		String result=null;
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			
		//	wcli=wscommitteeser.getWCLocationAdded(Integer.parseInt(ProjId));
			res =wscommitteeser.updateWatershedCommittee(mwc, session);
			//save=wscommitteeser.saveWatershedCommittee(Integer.parseInt(ProjId), wcname, session);
			if(res!=0)
					result="success";
				else
					result="fail";
				
		}
		else {
			session.invalidate();
			result="fail";
		}
		return result; 
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/deleteWSCommittee", method = RequestMethod.POST) 
	@ResponseBody
	public String deleteWSComittee(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		String regid=session.getAttribute("regId").toString();
		String ProjId=request.getParameter("projectId");
		String wc_code=request.getParameter("wc_code");
		List<IwmpMWc> wcli=new ArrayList<IwmpMWc>();
		String result=null;
		List<Integer> listofApprove;
		String[] str;
		Integer res=0;
		List<Integer> set = new ArrayList<Integer>(); 
		String wc= new String("");
	
		try {
				if(session!=null && session.getAttribute("loginID")!=null) 
				{
					str = wc_code.split(",");
					listofApprove=wscommitteeser.getlistofApprove();
					for(int j=0; j<str.length; j++) 
					{ 
						set.add(Integer.parseInt(str[j])); 
					}
					for(int i=0;i<set.size();i++) 
					{
							if(listofApprove.contains((set.get(i)))) 
							{
								System.out.println("not delete");
							}
							else 
							{
								if(wc.length()==0)
								wc=Integer.toString(set.get(i));
								else
								wc+=","+set.get(i);
							}
					}
					System.out.println("Delete value "+wc);
					if(wc.length()>0)
					res=wscommitteeser.deleteWatershedCommittee(wc);
				//	Integer res =wscommitteeser.deleteWatershedCommittee(wc_code);
				
					if(res!=0)
					result="success";
					else
						result="fail";
				}
				else {
					result="fail";
				}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result; 
	}
	
	@RequestMapping(value="/checkWSCommittee", method = RequestMethod.POST) 
	@ResponseBody
	public String checkWSCommittee(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("wscommittee") IwmpMWc mwc)
	{
		session = request.getSession(true);
		String st_code=session.getAttribute("stateCode").toString();
		String regid=session.getAttribute("regId").toString();
		String ProjId=request.getParameter("projectId");
	
		ModelAndView mav = new ModelAndView();
	
		List res;
		String result=null;
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			res=wscommitteeser.checkWSCommittee(Integer.parseInt(ProjId));
			for(int i=0;i<res.size();i++) 
			{
				//  System.out.println(res.get(i)) ;
				  
				  if(res.get(i)!=null)
						result="success";
					else
						result="fail";
			}
				
		}
		else {
			session.invalidate();
			result="fail";
		}
		return result; 
	}
}
