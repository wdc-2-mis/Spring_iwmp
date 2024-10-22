package app.controllers.Project;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.AssetIdBean;
import app.bean.BaselineUpdateAchievementBean;
import app.bean.Login;
import app.bean.menu.MappingNRMWorkwithOtherworkBean;
import app.bean.reports.LivelihoodPrdouctionEPAWorkIdBean;
import app.model.IwmpProjectAssetStatus;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.WdcpmksyEpaWorkid;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyMappingNRMWorkOtherWork;
import app.model.project.WdcpmksyPrdouctionWorkid;
import app.service.PhysicalActionPlanService;
import app.service.outcome.MappingNRMWorkwithOtherworkServices;

@Controller("MappingNRMWorkwithOtherworkController")
public class MappingNRMWorkwithOtherworkController {
	
	HttpSession session;
	
	@Autowired
	PhysicalActionPlanService phyService;
	
	@Autowired
	MappingNRMWorkwithOtherworkServices ser;
	
	@RequestMapping(value="/mappingNRMWork", method = RequestMethod.GET)
	public ModelAndView mappingNRMWork(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			mav = new ModelAndView("project/mappingNRMWorkwithOtherWork");
			mav.addObject("projectList",phyService.getProjectByRegId(regId));
			mav.addObject("headList",phyService.getHead());
			
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@PostMapping("/getmappingNRMWork")
	@ResponseBody
	public List<MappingNRMWorkwithOtherworkBean> getmappingNRMWork(HttpServletRequest request, HttpServletResponse response, @RequestParam("pCode") Integer pCode,
			@RequestParam("fYear") Integer fYear, @RequestParam("head") Integer head, @RequestParam("activity") Integer activity, @RequestParam("nrmwork") Integer nrmwork)
	{
		session = request.getSession(true);
		String userId = session.getAttribute("loginID").toString();
	//	System.out.println("userId:" +userId);
		LinkedHashMap<String, List<MappingNRMWorkwithOtherworkBean>> map = new LinkedHashMap<String, List<MappingNRMWorkwithOtherworkBean>>();
		List<IwmpProjectPhysicalAsset> list = new ArrayList<IwmpProjectPhysicalAsset>();
		 
		List<MappingNRMWorkwithOtherworkBean> sublist = new ArrayList<MappingNRMWorkwithOtherworkBean>();
		MappingNRMWorkwithOtherworkBean bean = new MappingNRMWorkwithOtherworkBean();
		
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			list=ser.getassetWorkWiseList(pCode, userId, fYear, head, activity, nrmwork);
			for(IwmpProjectPhysicalAsset p : list) 
			{
				bean = new MappingNRMWorkwithOtherworkBean();
				bean.setAssetid(p.getAssetid());
				bean.setHeaddesc(p.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());			
				bean.setActivitydesc(p.getIwmpMPhyActivity().getActivityDesc());
				bean.setBname(p.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(p.getIwmpVillage().getVillageName());
				bean.setDname(p.getIwmpMProject().getIwmpDistrict().getDistName());
				sublist.add(bean);
				
			}
		}
		else {
	
		}
		return sublist;
	}
	
	@RequestMapping(value="/getOtherHeadActivity", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getOtherHeadActivity(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="headtyp") String headtype, @RequestParam(value ="workid") Integer workid)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			map=ser.getOtherHeadActivity(headtype, workid);
			
		}else {

		}
		return map; 
	}
	
	@RequestMapping(value="/getOtherHeadActivityWork", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getOtherHeadActivityWork(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") Integer assetid,
			@RequestParam(value ="headtyp") String headtyp, @RequestParam(value ="acttypes") Integer acttypes, @RequestParam(value ="proj") Integer proj)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			map=ser.getOtherHeadActivityWork(headtyp, acttypes, proj, assetid);
			
		}else {

		}
		return map; 
	}
	@PostMapping("/getCompletedNRMRelatedOtherWork")
	@ResponseBody
	public LinkedHashMap<String, List<MappingNRMWorkwithOtherworkBean>> getCompletedNRMRelatedOtherWork(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="scheme") String scheme, @RequestParam(value="projcd") Integer projcd)
	{
		List<MappingNRMWorkwithOtherworkBean> list = new ArrayList<MappingNRMWorkwithOtherworkBean>();
		LinkedHashMap<String, List<MappingNRMWorkwithOtherworkBean>> map = new LinkedHashMap<String, List<MappingNRMWorkwithOtherworkBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			list= ser.getCompletedNRMRelatedOtherWork(scheme,projcd);
			List<MappingNRMWorkwithOtherworkBean> sublist = new ArrayList<MappingNRMWorkwithOtherworkBean>();
			if ((list != null) && (list.size() > 0)) {
				for (MappingNRMWorkwithOtherworkBean row : list){
					sublist = new ArrayList<MappingNRMWorkwithOtherworkBean>();
					if (!map.containsKey(row.getProjectdesc())) {
						sublist.add(row);
						map.put(row.getProjectdesc(), sublist);
					} else {
						sublist.addAll(map.get(row.getProjectdesc()));
						sublist.add(row);
						map.put(row.getProjectdesc(), sublist);
					}
				}
			}
		}
		return map; 
	}
	
	@RequestMapping(value="/checkNRMandOtherWorkMatch", method = RequestMethod.POST)
	@ResponseBody
	public String checkNRMandOtherWorkMatch(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="nrmwork") Integer nrmwork, 
			@RequestParam(value ="headtyp") String headtyp, @RequestParam(value ="otherwork") Integer otherwork, @RequestParam(value ="proj") Integer proj)
	{
		String str="fail";
		//LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			str=ser.checkNRMandOtherWorkMatch(headtyp, otherwork, proj, nrmwork);
			
		}else {

		}
		return str; 
	}
	
	@RequestMapping(value="/saveNRMwithOtherAsset", method = RequestMethod.POST)
	@ResponseBody
	public String saveNRMwithOtherAsset(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<BigInteger> assetid,
			@RequestParam(value ="headtype") List<String> headtype, @RequestParam(value ="otherwork") List<Integer> otherwork, @RequestParam(value ="projcd") Integer projcd)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.saveNRMwithOtherAsset(assetid,projcd,session.getAttribute("loginID").toString(), headtype, otherwork);
		 
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@PostMapping("/getmappingNRMWorkDraft")
	@ResponseBody
	public List<MappingNRMWorkwithOtherworkBean> getmappingNRMWorkDraft(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("pCode") Integer pCode)
	{
		session = request.getSession(true);
		String userId = session.getAttribute("loginID").toString();
	//	System.out.println("userId:" +userId);
		LinkedHashMap<String, List<MappingNRMWorkwithOtherworkBean>> map = new LinkedHashMap<String, List<MappingNRMWorkwithOtherworkBean>>();
		List<WdcpmksyMappingNRMWorkOtherWork> list = new ArrayList<WdcpmksyMappingNRMWorkOtherWork>();
		 
		List<MappingNRMWorkwithOtherworkBean> sublist = new ArrayList<MappingNRMWorkwithOtherworkBean>();
		MappingNRMWorkwithOtherworkBean bean = new MappingNRMWorkwithOtherworkBean();
		
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			list=ser.getmappingNRMWorkDraft(pCode);
			for(WdcpmksyMappingNRMWorkOtherWork p : list) 
			{
				bean = new MappingNRMWorkwithOtherworkBean();
				bean.setNrmworkOtherworkId(p.getNrmworkOtherworkId());
				bean.setOtherHead(p.getOtherHead());
				bean.setOtherworkId(p.getOtherworkId());
				bean.setAssetid(p.getIwmpProjectPhysicalAsset().getAssetid());
				bean.setHeaddesc(p.getIwmpProjectPhysicalAsset().getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());			
				bean.setActivitydesc(p.getIwmpProjectPhysicalAsset().getIwmpMPhyActivity().getActivityDesc());
				bean.setBname(p.getIwmpProjectPhysicalAsset().getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(p.getIwmpProjectPhysicalAsset().getIwmpVillage().getVillageName());
				bean.setDname(p.getIwmpProjectPhysicalAsset().getIwmpMProject().getIwmpDistrict().getDistName());
				sublist.add(bean);
				
			}
		}
		else {
	
		}
		return sublist;
	}
	
	@RequestMapping(value="/deleteNRMwithOtherAsset", method = RequestMethod.POST)
	@ResponseBody
	public String deleteNRMwithOtherAsset(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.deleteNRMwithOtherAsset(assetid);
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/completeNRMwithOtherAsset", method = RequestMethod.POST)
	@ResponseBody
	public String completeNRMwithOtherAsset(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.completeNRMwithOtherAsset(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}


	
	@RequestMapping(value="/checkNRMValidORNot", method = RequestMethod.POST)
	@ResponseBody
	public String checkNRMValidORNot(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="nrmworkid") Integer nrmworkid)
	{
		String str="fail";
		//LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			str=ser.checkNRMValidORNot(nrmworkid, Integer.parseInt(session.getAttribute("regId").toString()));
			
		}
		else {

		}
		return str; 
	}
	
	
	@PostMapping("/getWorkIdWiseNRMWorkDraft")
	@ResponseBody
	public List<MappingNRMWorkwithOtherworkBean> getWorkIdWiseNRMWorkDraft(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("nrmwork") Integer nrmwork)
	{
		session = request.getSession(true);
		String userId = session.getAttribute("loginID").toString();
	//	System.out.println("userId:" +userId);
		List<WdcpmksyMappingNRMWorkOtherWork> list = new ArrayList<WdcpmksyMappingNRMWorkOtherWork>();
		 
		List<MappingNRMWorkwithOtherworkBean> sublist = new ArrayList<MappingNRMWorkwithOtherworkBean>();
		MappingNRMWorkwithOtherworkBean bean = new MappingNRMWorkwithOtherworkBean();
		
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			list=ser.getWorkIdWiseNRMWorkDraft(nrmwork);
			for(WdcpmksyMappingNRMWorkOtherWork p : list) 
			{
				bean = new MappingNRMWorkwithOtherworkBean();
				bean.setNrmworkOtherworkId(p.getNrmworkOtherworkId());
				bean.setOtherHead(p.getOtherHead());
				bean.setOtherworkId(p.getOtherworkId());
				bean.setAssetid(p.getIwmpProjectPhysicalAsset().getAssetid());
				bean.setHeaddesc(p.getIwmpProjectPhysicalAsset().getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());			
				bean.setActivitydesc(p.getIwmpProjectPhysicalAsset().getIwmpMPhyActivity().getActivityDesc());
				bean.setBname(p.getIwmpProjectPhysicalAsset().getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(p.getIwmpProjectPhysicalAsset().getIwmpVillage().getVillageName());
				bean.setDname(p.getIwmpProjectPhysicalAsset().getIwmpMProject().getIwmpDistrict().getDistName());
				sublist.add(bean);
				
			}
		}
		else {
	
		}
		return sublist;
	}

}
