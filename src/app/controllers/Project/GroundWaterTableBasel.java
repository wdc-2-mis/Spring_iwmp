package app.controllers.Project;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import app.bean.GroundWaterTableBean;
import app.bean.Login;

import app.service.GroundWaterTableService;
import app.service.ProjectMasterService;
import app.service.StateMasterService;

@Controller("GroundWaterTableBasel")
public class GroundWaterTableBasel {
	
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	GroundWaterTableService gwtService;
	
	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;

	@RequestMapping(value = "/groundWaterTableBasel", method = RequestMethod.GET)
	public ModelAndView groundWaterTableBasel(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("project/groundWaterTableBasel");
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			userType.put("basel","Base line Survey");
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
			mav.addObject("yearList",gwtService.getFinYear());
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login"); 
			mav.addObject("login", new Login());
		}
			 
		
		return mav;
	}
	
	@RequestMapping(value = "/gwtDetaildataBasel", method = RequestMethod.POST)
	public ModelAndView gwtDetaildataDCBasel(HttpServletRequest request) {
		session = request.getSession(true);
		String project=request.getParameter("project");
		String groupType=request.getParameter("groupType");
		String fyear=request.getParameter("fyear");
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
		List<GroundWaterTableBean> list=new  ArrayList<GroundWaterTableBean>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("project/groundWaterTableBasel");
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			if(project!="")
			list=gwtService.gwtDetaildataDCBasel(Integer.parseInt(project));
			userType.put("basel","Base line Survey");
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
			mav.addObject("yearList",gwtService.getFinYear());
			mav.addObject("dataList",list);
			mav.addObject("projectcd",project);
			mav.addObject("timof",groupType);
			mav.addObject("finy",fyear);
			
			
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login"); 
			mav.addObject("login", new Login());
		}
			 
		
		return mav;
	}
	
	@RequestMapping(value = "/deleteGWTdraftBasel", method = RequestMethod.POST)
	public ModelAndView deleteGWTdraftdataBasel(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		session = request.getSession(true);
		String stcode = session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String gwtid = request.getParameter("gwtid");
		String gwtdtlid = request.getParameter("gwtdtlid");
		String project=request.getParameter("project");
		String groupType=request.getParameter("groupType");
		String fyear=request.getParameter("fyear");
		HashMap<String,String> userType = new HashMap<String,String>();
		List<GroundWaterTableBean> list=new  ArrayList<GroundWaterTableBean>();
		boolean userUploadDeleteSLNA=false;
		Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("project/groundWaterTableBasel");
			userUploadDeleteSLNA= gwtService.deleteGWTdraft(Integer.parseInt(gwtid), Integer.parseInt(gwtdtlid));
			if(userUploadDeleteSLNA ) 
					mav.addObject("messageGWT", "Data Deleted Successfully");
			else
					mav.addObject("messageGWT", "Data not Deleted!");
			
			list=gwtService.gwtDetaildataDCBasel(Integer.parseInt(project));
			userType.put("basel","Base line Survey");
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
			mav.addObject("yearList",gwtService.getFinYear());
			mav.addObject("dataList",list);
			mav.addObject("projectcd",project);
			
		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	//completeGWTDraftData
	
	@RequestMapping(value = "/completeGWTDraftDataBasel", method = RequestMethod.POST)
	public ModelAndView completeGWTDraftDataBasel(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		session = request.getSession(true);
		String stcode = session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String gwtid = request.getParameter("gwtid");
		String gwtdtlid = request.getParameter("gwtdtlid");
		String project=request.getParameter("project");
		String groupType=request.getParameter("groupType");
		String fyear=request.getParameter("fyear");
		HashMap<String,String> userType = new HashMap<String,String>();
		List<GroundWaterTableBean> list=new  ArrayList<GroundWaterTableBean>();
		boolean userUploadDeleteSLNA=false;
		Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("project/groundWaterTableBasel");
			userUploadDeleteSLNA= gwtService.completeGWTDraftData(Integer.parseInt(gwtid), Integer.parseInt(gwtdtlid));
			if(userUploadDeleteSLNA ) 
					mav.addObject("messageGWT", "Data complete/Lock Successfully");
			else
					mav.addObject("messageGWT", "Data not complete/Lock!");
			
			list=gwtService.gwtDetaildataDCBasel(Integer.parseInt(project));
			userType.put("basel","Base line Survey");
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
			mav.addObject("yearList",gwtService.getFinYear());
			mav.addObject("dataList",list);
			mav.addObject("projectcd",project);
		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	
	@RequestMapping(value="/saveGroundWaterTableBasel", method = RequestMethod.POST)
	@ResponseBody
	public String saveGroundWaterTableBasel(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="atline") String atline, 
			@RequestParam(value ="project") Integer project, @RequestParam(value ="fyear") Integer fyear, @RequestParam(value ="preMonsoon") BigDecimal preMonsoon, 
			@RequestParam(value ="postMonsoon") BigDecimal postMonsoon, @RequestParam(value ="gwtid1") Integer gwtid, @RequestParam(value ="gwtdtlid1") String gwtdtlid,  
			@RequestParam(value ="ph") BigDecimal ph, @RequestParam(value ="alkalinity") Integer alkalinity, @RequestParam(value ="hardness") Integer hardness,
			@RequestParam(value ="calcium") Integer calcium, @RequestParam(value ="chloride") Integer chloride, @RequestParam(value ="nitrate") BigDecimal nitrate,
			@RequestParam(value ="dissolved") Integer dissolved, @RequestParam(value ="fluoride") BigDecimal fluoride)
	{
		String res="fail";
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			if(atline.equals("basel"))
				res=gwtService.balselinCheck(project, atline);
			
			if(atline.equals("project"))
				res=gwtService.duringProjCheck(project, atline, fyear);
		
			if( res.equals("fail")) {
				res=gwtService.saveGroundWaterTable(atline, project, fyear, preMonsoon, postMonsoon, session, gwtid, ph, alkalinity, hardness, calcium, chloride, nitrate, dissolved, fluoride);
			}
			else {
				res= "fail";
			}
			
		}
		
		return res; 
	}
	
	
	@RequestMapping(value="/getGroundWaterTableDraftBasel", method = RequestMethod.POST)
	@ResponseBody
	public List<GroundWaterTableBean> getGroundWaterTableDraftBasel(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="group") String atline, @RequestParam(value ="project") Integer project,
			@RequestParam(value ="fyear") Integer fyear)
	{
		String res="fail";
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		List<GroundWaterTableBean> list=new  ArrayList<GroundWaterTableBean>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			
				list=gwtService.getGroundWaterTableDraft(project, atline, fyear);
		}
		
		return list; 
	}
	
	
	
	

}
