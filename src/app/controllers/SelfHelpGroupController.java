package app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import app.bean.AssetIdBean;
import app.bean.Login;
import app.bean.SelfHelpGroupBean;
import app.bean.reports.SelfHelpGroupReportBean;
import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaBean;
import app.model.outcome.ShgCoreactivityDetail;
import app.service.ProjectMasterService;
import app.service.SelfHelpGroupService;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.unfreeze.UnfreezeBaselineServices;

@Controller("SelfHelpGroupController")
public class SelfHelpGroupController {
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	SelfHelpGroupService selfHelpGroupService;
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public UnfreezeBaselineServices ser;
	
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> ProjectList;
	private Map<String, String> shgType;

	@RequestMapping(value = "/newshg", method = RequestMethod.GET)
	public ModelAndView getNewSHG(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			userType.put("newSHG","Newly Created SHG");
			mav = new ModelAndView("selfHelpGroup");
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/eshg", method = RequestMethod.GET)
	public ModelAndView getExistingSHG(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			userType.put("oldSHG","Existing SHG");
			mav = new ModelAndView("selfHelpGroup");
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/ug", method = RequestMethod.GET)
	public ModelAndView getUserGroup(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			userType.put("group","User Group");
			mav = new ModelAndView("selfHelpGroup");
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/getSHGCoreActivity", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,Integer> getSHGCoreActivity(HttpServletRequest request) {
		session = request.getSession(true);
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		LinkedHashMap<String,Integer> remap = new LinkedHashMap<>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			map = selfHelpGroupService.getSHGCoreActivity();
			for(Map.Entry<Integer, String> remp : map.entrySet()) {
				remap.put(remp.getValue(), remp.getKey());
			}
			/*
			 * map.put(1, "A"); map.put(2, "B");
			 */
		}else {
			
		}
		
		return remap;
	}
	
	
	@RequestMapping(value = "/saveDraftSHG", method = RequestMethod.POST)
	@ResponseBody
	public String saveDraftSHGData(HttpServletRequest request,@RequestParam("project") Integer projectCd,@RequestParam("group") String group,@RequestParam("no") Integer shgno,
			@RequestParam("name") List<String> name,@RequestParam("sc") List<Integer> sc,@RequestParam("st") List<Integer> st,@RequestParam("others") List<Integer> others,
			@RequestParam("women") List<Integer> women,@RequestParam("activity") List<String> activity,@RequestParam("turnover") List<BigDecimal> turnover,@RequestParam("income") List<BigDecimal> income,
			@RequestParam("pmbima") List<Integer> pmbima,@RequestParam("fedrated") List<String> fedrated,@RequestParam("department") List<Integer> department,@RequestParam("regdate") List<String> regdate,
			@RequestParam("revolve_amount") List<BigDecimal> revolve_amount,@RequestParam("threft_credit") List<Boolean> threft_credit) {
		session = request.getSession(true);
		String res="fail";
		if(session!=null && session.getAttribute("loginID")!=null) {
			String updatedby = session.getAttribute("loginID").toString();
			System.out.println(projectCd+" : "+group+" : "+shgno+" : "+name+" : "+sc+" : "+st+" : "+others+" : "+women+" : "+activity+" : "+turnover+" : "+income+" : "+pmbima+" : "+fedrated+" : "+department+" : "+regdate+" : "+revolve_amount+" : "+threft_credit);
			res= selfHelpGroupService.saveDraftSHGData(projectCd,group,shgno,name,sc,st,others,women,activity,turnover,income,pmbima,fedrated,updatedby,department,regdate,revolve_amount,threft_credit);
			
		}else {
			
		}
		
		return res;
	}
	
	@RequestMapping(value = "/getSHGDraftData", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<SelfHelpGroupBean>> getSHGDraftData(HttpServletRequest request,@RequestParam("project") Integer projectCd,@RequestParam("group") String group) {
		session = request.getSession(true);
		LinkedHashMap<Integer,List<SelfHelpGroupBean>> map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			System.out.println(projectCd+" : "+group);
			map= selfHelpGroupService.getSHGDraftData(projectCd,group);
			
		}else {
			map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		}
		
		return map;
	}
	
	@RequestMapping(value = "/deleteSHG", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSHG(HttpServletRequest request,@RequestParam("shgid") Integer shgid,@RequestParam("group") String group) {
		session = request.getSession(true);
		String res="fail";
		if(session!=null && session.getAttribute("loginID")!=null) {
			res= selfHelpGroupService.deleteSHG(shgid,group);
			
		}else {
			//map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		}
		
		return res;
	}
	
	@RequestMapping(value = "/completeSHG", method = RequestMethod.POST)
	@ResponseBody
	public String completeSHG(HttpServletRequest request,@RequestParam("shgid") Integer shgid) {
		session = request.getSession(true);
		String res="fail";
		if(session!=null && session.getAttribute("loginID")!=null) {
			System.out.println(shgid);
			res= selfHelpGroupService.completeSHG(shgid);
			
		}else {
			//map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		}
		
		return res;
	}
	
	@RequestMapping(value = "/getSHGDepartment", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getSHGDepartment(HttpServletRequest request) {
		session = request.getSession(true);
		LinkedHashMap<Integer,String> res=new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer stCode = Integer.parseInt(session.getAttribute("stateCode").toString());
			res= selfHelpGroupService.getSHGDepartment(stCode);
			
		}else {
			//map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		}
		
		return res;
	}
	
	@RequestMapping(value="/selfHelpGroupNameAccountReport", method = RequestMethod.GET)
	public ModelAndView selfHelpGroupNameAccountReport(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		List<SelfHelpGroupReportBean> data =new ArrayList<SelfHelpGroupReportBean>();
				
		/* if(session!=null && session.getAttribute("loginID")!=null) { */
			
			mav = new ModelAndView("reports/selfHelpGroupNameAccount");
			
			shgType= new HashMap<String, String>();
			shgType.put("newSHG", "Newly Created SHG");
			shgType.put("oldSHG", "Existing Created SHG");
			shgType.put("both", "Both");
			mav.addObject("shgType", shgType);
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);}
				mav.addObject("project", project);
				
				if(userState==null) {
					data=selfHelpGroupService.getselfHelpGroupNameAccountReport();
					mav.addObject("dataList",data);
					mav.addObject("dataListSize",data.size());	
				}
		/*}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}*/
		return mav; 
	}
	
	@RequestMapping(value="/selfHelpGroupNameAccountReport", method = RequestMethod.POST)
	public ModelAndView selfHelpGroupNameAccountReport1(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String shgty=request.getParameter("shg");
		List<SelfHelpGroupReportBean> data =new ArrayList<SelfHelpGroupReportBean>();
				
		/* if(session!=null && session.getAttribute("loginID")!=null) { */
			
			mav = new ModelAndView("reports/selfHelpGroupNameAccount");
			
			shgType= new HashMap<String, String>();
			shgType.put("newSHG", "Newly Created SHG");
			shgType.put("oldSHG", "Existing Created SHG");
			shgType.put("both", "Both");
			mav.addObject("shgType", shgType);
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);}
				mav.addObject("project", project);
				
				
					data=selfHelpGroupService.getselfHelpGroupNameAccountReportshg(userState, district, project, shgty);
					mav.addObject("dataList",data);
					mav.addObject("dataListSize",data.size());	
					
					mav.addObject("shg", shgty);
		/*}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}*/
		return mav; 
	}
}
