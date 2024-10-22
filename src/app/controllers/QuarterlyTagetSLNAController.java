package app.controllers;

import java.math.BigInteger;
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
import app.model.IwmpMProject;
import app.model.WdcpmksyMQuadIndicators;
import app.service.CreateAssetIdService;
import app.service.ProjectMasterService;
import app.service.QuarterlyTargetService;

@Controller("quarterlyTagetSLNAController")
public class QuarterlyTagetSLNAController {

	HttpSession session;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	CreateAssetIdService createAssetIdService;
	
	@Autowired(required = true) 
	QuarterlyTargetService quarterlyTargetService;
	
	@RequestMapping(value="/quarterlytargetslna", method = RequestMethod.GET)
	public ModelAndView getQuarterlyTargetSLNA(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			mav = new ModelAndView("quarterlytargetForSLNA");
			mav.addObject("getproject",  projectMasterService.getProjByStateCode(stcode));
			mav.addObject("projectReport", quarterlyTargetService.getSLNACompleteData(stcode));
			//mav.addObject("getproject", createAssetIdService.getListOfProjects(regId));
			for(IwmpMProject list :createAssetIdService.getProjectForPendingAsset(regId) ) {
				map.put(list.getProjectId(), list.getProjName());
			}
			mav.addObject("projectList", map);
			
			
			/* mav.addObject("planList",viewMovement(request,response)); */
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	
	@RequestMapping(value="/getquarterlytargetSLNAdata", method = RequestMethod.POST)
	@ResponseBody
	public List<WdcpmksyMQuadIndicators> getquarterlytargetSLNAdata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="pCode") Integer projId)
	{
		session = request.getSession(true);
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		LinkedHashMap<Integer,List<WdcpmksyMQuadIndicators>> map = new LinkedHashMap<Integer,List<WdcpmksyMQuadIndicators>>();
		List<WdcpmksyMQuadIndicators> proj = new ArrayList<WdcpmksyMQuadIndicators>();
        if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = quarterlyTargetService.getquartSLNAdata(projId, stcode);
			
			
		}else {
			proj=null;
			
		}
		return proj;
	}

	@RequestMapping(value="/getSingleQuartersDetails", method = RequestMethod.POST)
	@ResponseBody
	public List<WdcpmksyMQuadIndicators> getSingleQuartersDetails(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="achievementdtl") String achievementdtl)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<WdcpmksyMQuadIndicators>> map = new LinkedHashMap<Integer,List<WdcpmksyMQuadIndicators>>();
		List<WdcpmksyMQuadIndicators> proj = new ArrayList<WdcpmksyMQuadIndicators>();
        if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = quarterlyTargetService.getsingleslnadata(achievementdtl);
			
			
		}else {
			proj=null;
			
		}
		return proj;
	}

	
	@RequestMapping(value="/completeSLNAQuarter", method = RequestMethod.POST)
	@ResponseBody
	public String completeSLNAQuarter(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="quardDtl") String quardDtl)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		  session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			res=quarterlyTargetService.SLNAApproveService(quardDtl);
		}
		else
			{
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			}
	return res;
	}
	
	@RequestMapping(value="/updateallSLNAQuarters", method = RequestMethod.POST)
	@ResponseBody
	public String updateallSLNAQuarters(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="assetid") String quardDtl, @RequestParam(value ="status") String status)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		  session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			res=quarterlyTargetService.SLNAAllQuadApproveService(quardDtl, status);
		}
		else
			{
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
			}
	return res;
	}

	@RequestMapping(value="/getCompletedQuadDetails", method = RequestMethod.POST)
	@ResponseBody
	public List<WdcpmksyMQuadIndicators> getCompletedQuadDetails(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="quarterdtl") String quarterdtl)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<WdcpmksyMQuadIndicators>> map = new LinkedHashMap<Integer,List<WdcpmksyMQuadIndicators>>();
		List<WdcpmksyMQuadIndicators> proj = new ArrayList<WdcpmksyMQuadIndicators>();
        if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = quarterlyTargetService.getcompletedquaddata(quarterdtl);
			
			
		}else {
			proj=null;
			
		}
		return proj;
	}

}
