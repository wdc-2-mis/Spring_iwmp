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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.log.SysoCounter;

import app.bean.AssetIdBean;
import app.bean.Login;
import app.bean.PhysicalActionPlanBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.model.IwmpMProject;
import app.model.master.IwmpBlock;
import app.model.master.IwmpMPhyActivity;
import app.model.master.IwmpMPhySubactivity;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.IwmpProjectPhysicalAssetTemp;
import app.service.CreateAssetIdService;
import app.service.DistrictMasterService;
import app.service.PhysicalActionPlanService;
import app.service.ProjectMasterService;

@Controller("CreateAssetId")
public class CreateAssetIdController {
	
	HttpSession session=null;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	CreateAssetIdService createAssetIdService;
	
	@Autowired
	PhysicalActionPlanService physicalActionPlanService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@RequestMapping(value="/createAssetId", method = RequestMethod.GET)
	public ModelAndView createAssetId(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userId = session.getAttribute("loginID").toString();
			String userType = session.getAttribute("userType").toString();
			Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		    mav = new ModelAndView("createAssetId");
			mav.addObject("projectList", projectMasterService.getProjectByRegIdPlan(regId));
			mav.addObject("districtList", districtMasterService.getDistrictByStateCodeWithDcode(stcode));
			mav.addObject("rejectedAssetList", createAssetIdService.getRejectedAssetList(userId));
			mav.addObject("forwardedAssetList", createAssetIdService.getforwardedAssetUserWise(regId,userType,userId));
			map = new LinkedHashMap<Integer,String>();
			for(IwmpMProject list :createAssetIdService.getProjectForForwardedAsset(regId,userType,userId) ) {
				map.put(list.getProjectId(), list.getProjName());
			}
			mav.addObject("forwardedProjectList", map);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/refreshData", method = RequestMethod.POST)
	@ResponseBody
	public Integer refreshData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		List<IwmpProjectPhysicalAssetTemp> list = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		Integer size=0;
		if(session!=null && session.getAttribute("loginID")!=null) {
			String userId = session.getAttribute("loginID").toString();
			size= createAssetIdService.getRejectedAssetList(userId).size();
		}else {
			size=0;
		}
		return size; 
	}
	
	
	
	@RequestMapping(value="/getYearForAssetId", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getYearForAssetId(HttpServletRequest request,@RequestParam(value ="pCode") Integer pCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		//map.put(0, "All");
		map.putAll(physicalActionPlanService.getFromYearForPhysicalActionPlanReport(pCode));
		return map;
	}
	@RequestMapping(value="/getprojectfordis", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getprojectfordis(HttpServletRequest request,@RequestParam(value ="dCode") Integer dCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		map=physicalActionPlanService.getprojectfordistrict(dCode, stcode);
		return map;
	}
	
	@RequestMapping(value="/getPlanDetailsByProjYr", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<PhysicalActionPlanBean>> getPlanDetails(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projId,@RequestParam(value ="yrCode") Integer finYr)
	{
		LinkedHashMap<String, List<PhysicalActionPlanBean>> map = new LinkedHashMap<String, List<PhysicalActionPlanBean>>();
		List<IwmpProjectPhysicalAap> list = new ArrayList<IwmpProjectPhysicalAap>();
		List<PhysicalActionPlanBean> sublist = new ArrayList<PhysicalActionPlanBean>();
		List<IwmpProjectPhysicalAssetTemp> listt = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		List<IwmpProjectPhysicalAsset> lista = new ArrayList<IwmpProjectPhysicalAsset>();
		
		PhysicalActionPlanBean bean = new PhysicalActionPlanBean();
		Integer assetCount=0;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			list=createAssetIdService.getActionPlan(projId, finYr);
			for(IwmpProjectPhysicalAap aap : list) {
				bean = new PhysicalActionPlanBean();
				bean.setActivitycode(aap.getIwmpMPhyActivity().getActivityCode());
				bean.setActivityname(aap.getIwmpMPhyActivity().getActivityDesc());
				bean.setHeadcode(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadCode());
				bean.setHeadname(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
				bean.setUnitcode(aap.getIwmpMPhyActivity().getIwmpMUnit().getUnitCode());
				bean.setUnitname(aap.getIwmpMPhyActivity().getIwmpMUnit().getUnitDesc());
				bean.setPlan(aap.getQtyPlanned());
				bean.setAapid(aap.getAapid());
				bean.setProjectdesc(aap.getIwmpProjectPhysicalPlan().getIwmpMProject().getProjName());
				bean.setYrdesc(aap.getIwmpProjectPhysicalPlan().getIwmpMFinYear().getFinYrDesc());
				bean.setAsset(aap.getIwmpMPhyActivity().getAsset());
				assetCount=0;
				listt=createAssetIdService.getAssetTemp(projId, finYr, aap.getAapid());
				lista=createAssetIdService.getAssetAsset(projId, finYr, aap.getAapid());
				
				for(IwmpProjectPhysicalAssetTemp temp : listt) {
					if(temp.getIwmpProjectPhysicalAap().getAapid()==aap.getAapid() && temp.getIwmpMProject().getProjectId()==projId && temp.getIwmpMFinYear().getFinYrCd()==finYr)
						assetCount++;
				}
				for(IwmpProjectPhysicalAsset ass : lista) {
					if(ass.getIwmpProjectPhysicalAap().getAapid()==aap.getAapid() && ass.getIwmpMProject().getProjectId()==projId && ass.getIwmpMFinYear().getFinYrCd()==finYr)
						assetCount++;
				}  
				
				
				/*		
				  for(IwmpProjectPhysicalAssetTemp tempasset :aap.getIwmpProjectPhysicalAssetTemp())
				  if(tempasset.getIwmpProjectPhysicalAap().getAapid()==aap.getAapid() && tempasset.getIwmpMProject().getProjectId()==projId && tempasset.getIwmpMFinYear().getFinYrCd()==finYr)
					  assetCount++;
				  for(IwmpProjectPhysicalAsset asset : aap.getIwmpProjectPhysicalAsset())
				  if(asset.getIwmpProjectPhysicalAap().getAapid()==aap.getAapid() && asset.getIwmpMProject().getProjectId()==projId && asset.getIwmpMFinYear().getFinYrCd()==finYr) 
					  assetCount++;
				  */
				
				
				bean.setAssetcreated(assetCount);
			//	System.out.println("asset"+assetCount);
				if (!map.containsKey(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc())) {
					sublist = new ArrayList<PhysicalActionPlanBean>();
					sublist.add(bean);
					map.put(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc(), sublist);
				} else {
					sublist=(map.get(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc()));
					sublist.add(bean);
					map.put(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc(), sublist);
				}
			}
		}else {

		}
		return map; 
	}

	
	@RequestMapping(value="/getAssetIdForCreation", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getAssetIdForCreation(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="pCode") Integer projId, @RequestParam(value ="yrCode") Integer finYr, @RequestParam(value ="activityCode") Integer activityCode)
	{
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<AssetIdBean> list = new ArrayList<AssetIdBean>();
		List<IwmpBlock> listBlock = new ArrayList<IwmpBlock>();
		List<IwmpMPhySubactivity> listsubactivity = new ArrayList<IwmpMPhySubactivity>();
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		IwmpMPhySubactivity sub = new IwmpMPhySubactivity();
		sub.setSubActivityCode(1);
		sub.setSubActivityDesc("Test");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=createAssetIdService.getAssetIdForCreation(activityCode);
			listBlock=createAssetIdService.getBlockFromProjectLocation(projId);
			listsubactivity=createAssetIdService.getSubactivityByActivityCode(activityCode);
			for(AssetIdBean asset : list) {
				bean = new AssetIdBean();
				bean.setActivitycd(asset.getActivitycd());
				bean.setActivitydesc(asset.getActivitydesc());
				bean.setHeaddesc(asset.getHeaddesc());
				bean.setBlocklist(listBlock);
				//listsubactivity.add(sub);
				bean.setSubactivitylist(listsubactivity);
				
				if (!map.containsKey(asset.getActivitydesc())) {
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(asset.getActivitydesc(), sublist);
				} else {
					sublist=(map.get(asset.getActivitydesc()));
					sublist.add(bean);
					map.put(asset.getActivitydesc(), sublist);
				}
			}
		}else {

		}
		return map; 
	}
	
	@RequestMapping(value="/getVillageFromProjectLocationBlockWise", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getVillageFromProjectLocationBlockWise(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="pCode") Integer projId,@RequestParam(value ="bCode") Integer bCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			map=createAssetIdService.getVillageFromProjectLocationBlockWise(projId,bCode);
			
		}else {

		}
		return map; 
	}
	
	@RequestMapping(value="/saveAssetAsDraft", method = RequestMethod.POST)
	@ResponseBody
	public String saveAssetAsDraft(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="finyr") Integer finyr,@RequestParam(value ="projcd") Integer projcd,@RequestParam(value ="aapid") Integer[] aapid,
			@RequestParam(value ="activity") Integer[] activity,@RequestParam(value ="vcode") Integer[] vcode,@RequestParam(value ="subactivity") Integer[] subactivity, @RequestParam("landid")String[] landid)
	{
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			//map=createAssetIdService.getVillageFromProjectLocationBlockWise(projId,bCode);
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			System.out.println("finyr "+finyr+" projcd "+projcd+" aapid "+aapid+" activity "+activity+" vcode "+vcode+" subactivity "+subactivity);
			if(aapid.length==activity.length && activity.length==vcode.length && finyr!=null && projcd !=null) {
				res=createAssetIdService.saveAssetAsDraft(finyr,projcd,aapid,activity,vcode,sentFrom,subactivity,landid);
			}else {
				return "fail";
			}
			
		}else {

		}
		return res; 
	}
	
	
	@RequestMapping(value="/getDraftPlanDetailsByProjYr", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getDraftPlanDetailsByProjYr(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projId,@RequestParam(value ="yrCode") Integer finYr)
	{
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<IwmpProjectPhysicalAssetTemp> list = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		Integer assetCount=0;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			list=createAssetIdService.getDraftPlanDetailsByProjYr(projId, finYr,regId);
			for(IwmpProjectPhysicalAssetTemp aap : list) {
				bean = new AssetIdBean();
				bean.setAapid(aap.getIwmpProjectPhysicalAap().getAapid());
				bean.setActivitycd(aap.getIwmpMPhyActivity().getActivityCode());
				bean.setActivitydesc(aap.getIwmpMPhyActivity().getActivityDesc());
				bean.setTempassetid(aap.getTempassetid());
				bean.setBcode(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
				bean.setBname(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setFinyrcd(aap.getIwmpMFinYear().getFinYrCd());
				bean.setFinyrdesc(aap.getIwmpMFinYear().getFinYrDesc());
				bean.setHeaddesc(aap.getIwmpProjectPhysicalAap().getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
				bean.setProjdesc(aap.getIwmpMProject().getProjName());
				bean.setProjid(aap.getIwmpMProject().getProjectId());
				bean.setNearby(aap.getNearby()==null?"":aap.getNearby());
				bean.setVcode(aap.getIwmpVillage().getVcode());
				bean.setVname(aap.getIwmpVillage().getVillageName());
				bean.setDistApprovalReq(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getIwmpState().getApprovalReq()=='Y'?true:false);
				bean.setSubactivitycode(aap.getIwmpMPhySubactivity()==null?0:aap.getIwmpMPhySubactivity().getSubActivityCode());
				bean.setSubactivitydesc(aap.getIwmpMPhySubactivity()==null?"":aap.getIwmpMPhySubactivity().getSubActivityDesc());
		//		System.out.println("asset"+assetCount);
				if (!map.containsKey(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc())) {
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc(), sublist);
				} else {
					sublist=(map.get(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc()));
					sublist.add(bean);
					map.put(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc(), sublist);
				}
			}
		}else {

		}
		return map; 
	}
	
	@RequestMapping(value="/deleteAsset", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAsset(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="tempassetid") Integer tempassetid)
	{
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			//map=createAssetIdService.getVillageFromProjectLocationBlockWise(projId,bCode);
			if(tempassetid!=null) {
				res=createAssetIdService.deleteAsset(tempassetid);
			}else {
				return "fail";
			}
			
		}else {

		}
		return res; 
	}
	
	@RequestMapping(value="/forwardAssetPIA", method = RequestMethod.POST)
	@ResponseBody
	public String forwardAssetToWCDC(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="tempassetid") List<BigInteger> tempassetid,@RequestParam(value ="sentto") Integer sentto)
	{
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			//map=createAssetIdService.getVillageFromProjectLocationBlockWise(projId,bCode);
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String createdby = session.getAttribute("loginID").toString();
			String userType = session.getAttribute("userType").toString();
			if(sentto==0) {
			 sentto = createAssetIdService.getSentToSLNAForAsset(sentfrom);
			 userType="SL";
			}
			if(tempassetid!=null) {
				if(userType.equals("PI"))
				res=createAssetIdService.forwardAssetToWCDC(tempassetid,sentfrom,sentto,createdby);
				if(userType.equals("SL"))
					res=createAssetIdService.forwardAssettoSLNA(sentfrom,tempassetid,sentto);
			}else {
				return "fail";
			}
			
		}else {

		}
		return res; 
	}
	

	@PostMapping("/getassetlist")
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getassetlist(HttpServletRequest request, HttpServletResponse response)
	{
		
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<IwmpProjectPhysicalAsset> list = new ArrayList<IwmpProjectPhysicalAsset>();
		
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			String userId = session.getAttribute("loginID").toString();
			String stcd=session.getAttribute("stateCode").toString();
			list=createAssetIdService.getassetList(userId);
			for(IwmpProjectPhysicalAsset phyas : list) {
				bean = new AssetIdBean();
				bean.setProjdesc(phyas.getIwmpMProject().getProjName());
				bean.setFinyrdesc(phyas.getIwmpMFinYear().getFinYrDesc());
				bean.setAssetid(phyas.getAssetid());
				bean.setActivitydesc(phyas.getIwmpMPhyActivity().getActivityDesc());
				bean.setBname(phyas.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(phyas.getIwmpVillage().getVillageName());
				bean.setNearby(phyas.getNearby()==null?"":phyas.getNearby());
				bean.setSubactivitycode(phyas.getIwmpMPhySubactivity()==null?0:phyas.getIwmpMPhySubactivity().getSubActivityCode());
				bean.setSubactivitydesc(phyas.getIwmpMPhySubactivity()==null?"":phyas.getIwmpMPhySubactivity().getSubActivityDesc());
				if (!map.containsKey(phyas.getIwmpMProject().getProjName()))
						{
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(phyas.getIwmpMProject().getProjName(), sublist);
						}
				else {
					sublist=(map.get(phyas.getIwmpMProject().getProjName()));
					sublist.add(bean);
					map.put(phyas.getIwmpMProject().getProjName(), sublist);
				}
			}
		}
		else {
			
		}
	return map;
	}
	
	@PostMapping("/getassetlistkd")
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getassetlistkd(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam(value ="pCode") Integer pCode)
	{
		
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<IwmpProjectPhysicalAsset> list = new ArrayList<IwmpProjectPhysicalAsset>();
		
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			String userId = session.getAttribute("loginID").toString();
			String stcd=session.getAttribute("stateCode").toString();
			list=createAssetIdService.getassetListkd(pCode);
			for(IwmpProjectPhysicalAsset phyas : list) {
				bean = new AssetIdBean();
				bean.setProjdesc(phyas.getIwmpMProject().getProjName());
				bean.setFinyrdesc(phyas.getIwmpMFinYear().getFinYrDesc());
				bean.setAssetid(phyas.getAssetid());
				bean.setHeaddesc(phyas.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());				
				bean.setActivitydesc(phyas.getIwmpMPhyActivity().getActivityDesc());
				bean.setBname(phyas.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(phyas.getIwmpVillage().getVillageName());
				bean.setNearby(phyas.getNearby()==null?"":phyas.getNearby());
				bean.setSubactivitycode(phyas.getIwmpMPhySubactivity()==null?0:phyas.getIwmpMPhySubactivity().getSubActivityCode());
				bean.setSubactivitydesc("");
				if (!map.containsKey(phyas.getIwmpMProject().getProjName()))
				{
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(phyas.getIwmpMProject().getProjName(), sublist);
				}
				else {
					sublist=(map.get(phyas.getIwmpMProject().getProjName()));
					sublist.add(bean);
					map.put(phyas.getIwmpMProject().getProjName(), sublist);
				}
			}
		}
		else {
			
		}
	return map;
	}
	

	@RequestMapping(value="/getUser", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getUserToForward(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> res = new LinkedHashMap<Integer,String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			res=createAssetIdService.getUserToForward(regId);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/viewforwardedAsset", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getforwardedAssetUserWise(HttpServletRequest request, HttpServletResponse response)
	{
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<IwmpProjectPhysicalAssetTemp> list = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		Integer assetCount=0;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userType = session.getAttribute("userType").toString();
			String userid = session.getAttribute("loginID").toString();
			list=createAssetIdService.getforwardedAssetUserWise(regId,userType,userid);
			for(IwmpProjectPhysicalAssetTemp aap : list) {
				bean = new AssetIdBean();
				bean.setAapid(aap.getIwmpProjectPhysicalAap().getAapid());
				bean.setActivitycd(aap.getIwmpMPhyActivity().getActivityCode());
				bean.setActivitydesc(aap.getIwmpMPhyActivity().getActivityDesc());
				bean.setTempassetid(aap.getTempassetid());
				bean.setBcode(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
				bean.setBname(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setFinyrcd(aap.getIwmpMFinYear().getFinYrCd());
				bean.setFinyrdesc(aap.getIwmpMFinYear().getFinYrDesc());
				bean.setNearby(aap.getNearby());
				bean.setHeaddesc(aap.getIwmpProjectPhysicalAap().getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
				bean.setProjdesc(aap.getIwmpMProject().getProjName());
				bean.setProjid(aap.getIwmpMProject().getProjectId());
				bean.setVcode(aap.getIwmpVillage().getVcode());
				bean.setVname(aap.getIwmpVillage().getVillageName());
				bean.setDistApprovalReq(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getIwmpState().getApprovalReq()=='Y'?true:false);
				bean.setForwardedTo((aap.getIwmpUserRegBySentTo().getUserType().equals("SL")?"SLNA":aap.getIwmpUserRegBySentTo().getUserType().equals("DI")?"WCDC":"")+"("+aap.getIwmpUserRegBySentTo().getUserName()+")");
				bean.setSubactivitycode(aap.getIwmpMPhySubactivity()==null?0:aap.getIwmpMPhySubactivity().getSubActivityCode());
				bean.setSubactivitydesc(aap.getIwmpMPhySubactivity()==null?"":aap.getIwmpMPhySubactivity().getSubActivityDesc());
				System.out.println("asset"+assetCount);
				if (!map.containsKey(aap.getIwmpMProject().getProjName())) {
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(aap.getIwmpMProject().getProjName(), sublist);
				} else {
					sublist=(map.get(aap.getIwmpMProject().getProjName()));
					sublist.add(bean);
					map.put(aap.getIwmpMProject().getProjName(), sublist);
				}
			}
		}else {

		}
		return map; 
	}
	
	@RequestMapping(value="/viewrejectedAsset", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getrejectedAssetUserWise(HttpServletRequest request, HttpServletResponse response)
	{
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<IwmpProjectPhysicalAssetTemp> list = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		Integer assetCount=0;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			String userId = session.getAttribute("loginID").toString();
			list=createAssetIdService.getRejectedAssetList(userId);
			for(IwmpProjectPhysicalAssetTemp aap : list) {
				bean = new AssetIdBean();
				bean.setAapid(aap.getIwmpProjectPhysicalAap().getAapid());
				bean.setActivitycd(aap.getIwmpMPhyActivity().getActivityCode());
				bean.setActivitydesc(aap.getIwmpMPhyActivity().getActivityDesc());
				bean.setTempassetid(aap.getTempassetid());
				bean.setBcode(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
				bean.setBname(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setFinyrcd(aap.getIwmpMFinYear().getFinYrCd());
				bean.setFinyrdesc(aap.getIwmpMFinYear().getFinYrDesc());
				bean.setNearby(aap.getNearby());
				bean.setHeaddesc(aap.getIwmpProjectPhysicalAap().getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
				bean.setProjdesc(aap.getIwmpMProject().getProjName());
				bean.setProjid(aap.getIwmpMProject().getProjectId());
				bean.setVcode(aap.getIwmpVillage().getVcode());
				bean.setVname(aap.getIwmpVillage().getVillageName());
				bean.setDistApprovalReq(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getIwmpState().getApprovalReq()=='Y'?true:false);
				bean.setRejectedBy((aap.getIwmpUserRegBySentFrom().getUserType().equals("SL")?"SLNA":aap.getIwmpUserRegBySentFrom().getUserType().equals("DI")?"WCDC":"")+"("+aap.getIwmpUserRegBySentFrom().getUserId()+")");
				bean.setRemarks(aap.getRemarks());
				bean.setSubactivitycode(aap.getIwmpMPhySubactivity()==null?0:aap.getIwmpMPhySubactivity().getSubActivityCode());
				bean.setSubactivitydesc(aap.getIwmpMPhySubactivity()==null?"":aap.getIwmpMPhySubactivity().getSubActivityDesc());
				System.out.println("asset"+assetCount);
				if (!map.containsKey(aap.getIwmpMProject().getProjName())) {
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(aap.getIwmpMProject().getProjName(), sublist);
				} else {
					sublist=(map.get(aap.getIwmpMProject().getProjName()));
					sublist.add(bean);
					map.put(aap.getIwmpMProject().getProjName(), sublist);
				}
			}
		}else {

		}
		return map; 
	}
	
	/******************************************************* For WCDC/SLNA **************************************************/
	@RequestMapping(value="/wcdcActionOnAsset", method = RequestMethod.GET)
	public ModelAndView wcdcActionOnAsset(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			 map = new LinkedHashMap<Integer,String>();
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			
			String userType= session.getAttribute("userType").toString();
			String userId= session.getAttribute("loginID").toString();
			mav = new ModelAndView("createAssetAtWCDCSLNA");
			mav.addObject("assetlist",createAssetIdService.getListOfAssetUserWise(regId,userType));
			
			mav.addObject("getproject", createAssetIdService.getListOfProjects(regId));
			
			mav.addObject("userType",userType);
			mav.addObject("forwardedAssetList", createAssetIdService.getforwardedAssetUserWise(regId,userType,userId));
			for(IwmpMProject list :createAssetIdService.getProjectForPendingAsset(regId) ) {
				map.put(list.getProjectId(), list.getProjName());
			}
			mav.addObject("pendingProjectList", map);
			map = new LinkedHashMap<Integer,String>();
			for(IwmpMProject list :createAssetIdService.getProjectForForwardedAsset(regId,userType,userId) ) {
				map.put(list.getProjectId(), list.getProjName());
			}
			mav.addObject("forwardedProjectList", map);
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getProjectForForwardedAsset", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getProjectForForwardedAsset(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			String userId= session.getAttribute("loginID").toString();
		for(IwmpMProject list :createAssetIdService.getProjectForForwardedAsset(regId,userType,userId) ) {
			map.put(list.getProjectId(), list.getProjName());
		}
		}
		return map;
		
	}
	
	@RequestMapping(value="/getProjectForPendingAsset", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getProjectForPendingAsset(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			for(IwmpMProject list :createAssetIdService.getProjectForPendingAsset(regId) ) {
				map.put(list.getProjectId(), list.getProjName());
			}
		}
		return map;
		
	}
	
	@RequestMapping(value="/forwardAsset", method = RequestMethod.POST)
	@ResponseBody
	public String forwardAssettoSLNA(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="assetid") List<BigInteger> tempassetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			Integer sentto = createAssetIdService.getSentToSLNAForAsset(sentfrom);
			if(userType.equals("DI"))
			res=createAssetIdService.forwardAssettoSLNA(sentfrom,tempassetid,sentto);
			if(userType.equals("SL"))
				res=createAssetIdService.completeAsset(sentfrom,tempassetid,sentto);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/rejectAsset", method = RequestMethod.POST)
	@ResponseBody
	public String rejectAsset(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="assetid") List<BigInteger> assetid,@RequestParam(value ="remarks") List<String> remarks)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			
			if(userType.equals("DI"))
			res=createAssetIdService.rejectAssetbyWCDC(sentfrom,assetid,remarks);
		
		  if(userType.equals("SL"))
			  res=createAssetIdService.rejectAssetbySLNA(sentfrom,assetid,remarks);
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/slnaActionOnAsset", method = RequestMethod.GET)
	public ModelAndView slnaActionOnAsset(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			 map = new LinkedHashMap<Integer,String>();
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			Integer stateCode = Integer.parseInt(session.getAttribute("stateCode").toString());
			mav = new ModelAndView("createAssetAtWCDCSLNA");
			mav.addObject("districtList", districtMasterService.getDistrictByStateCodeWithDcode(stcode));
			mav.addObject("assetlist",createAssetIdService.getListOfAssetUserWise(regId,userType));
			mav.addObject("distList",districtMasterService.getDistrictByStateCodeWithDcode(stateCode));
			for(IwmpMProject list :createAssetIdService.getProjectForPendingAsset(regId) ) {
				map.put(list.getProjectId(), list.getProjName());
			}
			mav.addObject("pendingProjectList", map);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	
	@PostMapping("/getalreadyassetdata")
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getalreadyassetdata(HttpServletRequest request, HttpServletResponse response, @RequestParam("dCode") Integer dCode, @RequestParam("pCode") Integer pCode)
	{
		session = request.getSession(true);
		String userId = session.getAttribute("loginID").toString();
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<IwmpProjectPhysicalAsset> list = new ArrayList<IwmpProjectPhysicalAsset>();
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=createAssetIdService.getfinalassetList(dCode, pCode, userId);
			for(IwmpProjectPhysicalAsset phyas : list) {
				bean = new AssetIdBean();
				bean.setProjdesc(phyas.getIwmpMProject().getProjName());
				bean.setFinyrdesc(phyas.getIwmpMFinYear().getFinYrDesc());
				bean.setAssetid(phyas.getAssetid());
				bean.setActivitydesc(phyas.getIwmpMPhyActivity().getActivityDesc());
				/*
				 * if(phyas.getIwmpMPhySubactivity()!=null)
				 * bean.setSubactivitydesc(phyas.getIwmpMPhySubactivity().getSubActivityDesc());
				 * else
				 */
				bean.setSubactivitydesc("");
				//bean.setSubactivitydesc(phyas.getIwmpMPhySubactivity().getSubActivityDesc());
				bean.setBname(phyas.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(phyas.getIwmpVillage().getVillageName());
				bean.setNearby(phyas.getNearby()==null?"":phyas.getNearby());
				if (!map.containsKey(phyas.getIwmpMProject().getProjName()))
						{
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(phyas.getIwmpMProject().getProjName(), sublist);
						}
				else {
					sublist=(map.get(phyas.getIwmpMProject().getProjName()));
					sublist.add(bean);
					map.put(phyas.getIwmpMProject().getProjName(), sublist);
				}
			}
		}
		else {
			
		}
	return map;
	}	
	
	@PostMapping("/getalreadyassetdidata")
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getalreadyassetdidata(HttpServletRequest request, HttpServletResponse response, @RequestParam("pCode") Integer pCode)
	{
		session = request.getSession(true);
		String userId = session.getAttribute("loginID").toString();
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<IwmpProjectPhysicalAsset> list = new ArrayList<IwmpProjectPhysicalAsset>();
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=createAssetIdService.getfinalassetdiList(pCode, userId);
			for(IwmpProjectPhysicalAsset phyas : list) {
				bean = new AssetIdBean();
				bean.setProjdesc(phyas.getIwmpMProject().getProjName());
				bean.setFinyrdesc(phyas.getIwmpMFinYear().getFinYrDesc());
				bean.setAssetid(phyas.getAssetid());
				bean.setActivitydesc(phyas.getIwmpMPhyActivity().getActivityDesc());
				//if(!phyas.getIwmpMPhySubactivity().equals(null)) 
				//	bean.setSubactivitydesc(phyas.getIwmpMPhySubactivity().getSubActivityDesc());
				//else
				bean.setSubactivitydesc("");
				bean.setBname(phyas.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(phyas.getIwmpVillage().getVillageName());
				bean.setNearby(phyas.getNearby()==null?"":phyas.getNearby());
				if (!map.containsKey(phyas.getIwmpMProject().getProjName()))
						{
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(phyas.getIwmpMProject().getProjName(), sublist);
						}
				else {
					sublist=(map.get(phyas.getIwmpMProject().getProjName()));
					sublist.add(bean);
					map.put(phyas.getIwmpMProject().getProjName(), sublist);
				}
			}
		}
		else {
			
		}
	return map;
	}
	
	@RequestMapping(value="/getPendingAssetAtWCDCSLNA", method = RequestMethod.POST)
	@ResponseBody
	public List<AssetIdBean> getPendngAssetAtWCDCProjectWise(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="pCode") Integer projId,@RequestParam(value ="dCode") Integer dCode)
	{
		ModelAndView mav = new ModelAndView();
		List<IwmpProjectPhysicalAssetTemp> list = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		List<AssetIdBean> assetList = new ArrayList<AssetIdBean>();
		session = request.getSession(true);
		AssetIdBean bean = new AssetIdBean();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentTo = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			if(userType.equals("DI") && projId==0)
			list=createAssetIdService.getListOfAssetUserWise(sentTo,userType);
			if(userType.equals("DI") && projId>0)
			list=createAssetIdService.getPendngAssetAtWCDCProjectWise(sentTo,userType,projId,dCode);
			if(userType.equals("SL"))
				list=createAssetIdService.getPendngAssetAtWCDCProjectWise(sentTo,userType,projId,dCode);
			for(IwmpProjectPhysicalAssetTemp temp : list) {
				bean = new AssetIdBean();
				bean.setProjdesc(temp.getIwmpMProject().getProjName());
				bean.setFinyrdesc(temp.getIwmpMFinYear().getFinYrDesc());
				bean.setTempassetid(temp.getTempassetid());
				bean.setNearby(temp.getNearby());
				bean.setActivitydesc(temp.getIwmpMPhyActivity().getActivityDesc());
				bean.setBname(temp.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(temp.getIwmpVillage().getVillageName());
				bean.setRemarks(temp.getRemarks());
				bean.setUsertype(temp.getIwmpUserRegBySentTo().getUserType());
				assetList.add(bean);
			}
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return assetList; 
	}
	
	
	@RequestMapping(value="/getProjectFromDistrict", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getProjectFromDistrictForPendingAsset(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="dCode") Integer dCode)
	{
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		session = request.getSession(true);
		List<IwmpProjectPhysicalAssetTemp> list = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			if(dCode>0) {
			  list=createAssetIdService.getProjectFromDistrictForPendingAsset(dCode,regId);
			  for(IwmpProjectPhysicalAssetTemp temp : list) {
				  map.put(temp.getIwmpMProject().getProjectId(), temp.getIwmpMProject().getProjName());
			  }
			}
			if(dCode==0) {
			for(IwmpMProject li :createAssetIdService.getProjectForPendingAsset(regId) ) {
				map.put(li.getProjectId(), li.getProjName());
			}
			}
			  
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return map; 
	}
	
	@RequestMapping(value="/getForwardedAssetUserWiseForProject", method = RequestMethod.POST)
	@ResponseBody
	public List<AssetIdBean> getForwardedAssetUserWiseForProject(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="pCode") Integer projId,@RequestParam(value ="dCode") Integer dCode)
	{
		ModelAndView mav = new ModelAndView();
		List<IwmpProjectPhysicalAssetTemp> list = new ArrayList<IwmpProjectPhysicalAssetTemp>();
		List<AssetIdBean> assetList = new ArrayList<AssetIdBean>();
		session = request.getSession(true);
		AssetIdBean bean = new AssetIdBean();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userType = session.getAttribute("userType").toString();
			String userid = session.getAttribute("loginID").toString();
			
			if((userType.equals("DI") || userType.equals("PI")) && projId==0)
				list=createAssetIdService.getforwardedAssetUserWise(regId,userType,userid);
			if((userType.equals("DI") || userType.equals("PI")) && projId>0)
			list=createAssetIdService.getforwardedAssetUserWiseForProject(regId,userType,projId,dCode);
			if(userType.equals("SL"))
				list=createAssetIdService.getforwardedAssetUserWiseForProject(regId,userType,projId,dCode);
			for(IwmpProjectPhysicalAssetTemp temp : list) {
				bean = new AssetIdBean();
				bean.setProjdesc(temp.getIwmpMProject().getProjName());
				bean.setFinyrdesc(temp.getIwmpMFinYear().getFinYrDesc());
				bean.setTempassetid(temp.getTempassetid());
				bean.setActivitydesc(temp.getIwmpMPhyActivity().getActivityDesc());
				bean.setNearby(temp.getNearby());
				bean.setBname(temp.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(temp.getIwmpVillage().getVillageName());
				bean.setRemarks(temp.getRemarks());
				bean.setUsertype(temp.getIwmpUserRegBySentTo().getUserType());
				bean.setForwardedTo((temp.getIwmpUserRegBySentTo().getUserType().equals("SL")?"SLNA":temp.getIwmpUserRegBySentTo().getUserType().equals("DI")?"WCDC":"")+"("+temp.getIwmpUserRegBySentTo().getUserName()+")");
				assetList.add(bean);
			}
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return assetList; 
	}
	
}
