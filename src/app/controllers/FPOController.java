package app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.AssetIdBean;
import app.bean.FPOReportBean;
import app.bean.Login;
import app.bean.ProjectLocationBean;
import app.service.FPOMasterService;
import app.service.ProjectMasterService;

@Controller("fpoController")
public class FPOController {

	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	FPOMasterService fpoMasterService;
	
	HttpSession session;
	
	@RequestMapping(value = "/newfpo", method = RequestMethod.GET)
	public ModelAndView newfpo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
        session = request.getSession(true);
        
        if(session!=null && session.getAttribute("loginID")!=null) {
        	int regId = (int)session.getAttribute("regId");
        	userType.put("newFPO","Newly created FPO");
        	mav = new ModelAndView("fpo");
        	mav.addObject("projectList",  projectMasterService.getProjectByRegId(regId));
        	mav.addObject("groupType",userType);
        }
        else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return mav; 
	}

	@RequestMapping(value = "/exisfpo", method = RequestMethod.GET)
	public ModelAndView exisfpo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
        session = request.getSession(true);
        
        if(session!=null && session.getAttribute("loginID")!=null) {
        	int regId = (int)session.getAttribute("regId");
        	userType.put("oldFPO","Existing FPO");
        	mav = new ModelAndView("fpo");
        	mav.addObject("projectList",  projectMasterService.getProjectByRegId(regId));
        	mav.addObject("groupType",userType);
        }
        else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			
		}
		return mav; 
	}
	
	@RequestMapping(value = "/getDepartmentorg", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getDepartmentorg(HttpServletRequest request)
	{
		session = request.getSession(true);
		int stCode = (int) session.getAttribute("stateCode");
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(fpoMasterService.getdeptorg(stCode));
		return map;
	}
	
	@RequestMapping(value = "/getCoreActivity", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getCoreActivity(HttpServletRequest request)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(fpoMasterService.getCoreActivity());
	
		//System.out.println("data:" +fpoMasterService.getCoreActivity());
		return map;
	}
	
	@RequestMapping(value = "/saveFPOdata", method = RequestMethod.POST)
	@ResponseBody
	public String saveFPOdata(HttpServletRequest request, HttpServletResponse response, @RequestParam("projId")Integer projId, @RequestParam("group")String group, @RequestParam("no")Integer no,
			@RequestParam("name") List<String> fponame, @RequestParam("deptorg") List<Integer> deptorg, @RequestParam("regno") List<String> regno, @RequestParam("regdt") List<String> regdt, 
			@RequestParam("noofmembers") List<Integer> noofmembers, @RequestParam("activity")List<String> fpoactivity, @RequestParam("avg") List<BigDecimal> fpoavg, @RequestParam("farm") List<Integer> nooffarm)
	{
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = fpoMasterService.savefpodata(projId, group, no, fponame, deptorg, regno, regdt, noofmembers,fpoactivity, fpoavg, nooffarm, session.getAttribute("loginID").toString());
		}
		return res;
	}
	
	
	@RequestMapping(value="/fpodraftdata", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<FPOReportBean>> fpodraftdata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId, @RequestParam(value="group") String group)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,List<FPOReportBean>> map = new LinkedHashMap<Integer,List<FPOReportBean>>();
		List<FPOReportBean> proj = new ArrayList<FPOReportBean>();
		List<FPOReportBean> sublist = new ArrayList<FPOReportBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			  proj = fpoMasterService.getfpodraftdata(projectId, group);
			  for(FPOReportBean bean: proj) {
				  if (!map.containsKey(bean.getFpo_id()))
					{
				sublist = new ArrayList<FPOReportBean>();
				sublist.add(bean);
				map.put(bean.getFpo_id(), sublist);
					}
			else {
				sublist=(map.get(bean.getFpo_id()));
				sublist.add(bean);
				map.put(bean.getFpo_id(), sublist);
			}  
				  
			  }
			  
		}else {
			proj=null;
			
		}
		return map;  
	}
	
	@RequestMapping(value="/fpofinaldata", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<FPOReportBean>> fpofinaldata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId, @RequestParam(value="group") String group)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,List<FPOReportBean>> map = new LinkedHashMap<Integer,List<FPOReportBean>>();
		List<FPOReportBean> proj = new ArrayList<FPOReportBean>();
		List<FPOReportBean> sublist = new ArrayList<FPOReportBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			  proj = fpoMasterService.fpofinaldata(projectId, group);
			  for(FPOReportBean bean: proj) {
				  if (!map.containsKey(bean.getFpo_id()))
					{
				sublist = new ArrayList<FPOReportBean>();
				sublist.add(bean);
				map.put(bean.getFpo_id(), sublist);
					}
			else {
				sublist=(map.get(bean.getFpo_id()));
				sublist.add(bean);
				map.put(bean.getFpo_id(), sublist);
			}  
				  
			  }
			  
		}else {
			proj=null;
			
		}
		return map;  
	}
	
	
	@RequestMapping(value="/delFPOdraftdata", method = RequestMethod.POST)
	@ResponseBody
	public String delFPOdraftdata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="fpoid") Integer fpoid)
	{
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=fpoMasterService.detFPOdraftdata(fpoid);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}

	@RequestMapping(value="/finalSaveFPOdraftdata", method = RequestMethod.POST)
	@ResponseBody
	public String finalSaveFPOdraftdata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="fpoid") Integer fpoid)
	{
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=fpoMasterService.finalSaveFPOdraftdata(fpoid);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}

}
