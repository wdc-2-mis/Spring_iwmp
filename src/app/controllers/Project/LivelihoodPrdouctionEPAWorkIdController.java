package app.controllers.Project;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import app.bean.Login;
import app.bean.PhysicalActionPlanBean;
import app.bean.ProfileBean;
import app.bean.reports.LivelihoodPrdouctionEPAWorkIdBean;
import app.model.IwmpMProject;
import app.model.master.IwmpBlock;
import app.model.master.IwmpMPhySubactivity;
import app.model.outcome.EpaDetail;
import app.model.outcome.LivelihoodDetail;
import app.model.outcome.ProductionDetail;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.IwmpProjectPhysicalAssetTemp;
import app.model.project.WdcpmksyEpaWorkid;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyPrdouctionWorkid;
import app.service.CreateAssetIdService;
import app.service.ProfileService;
import app.service.ProjectMasterService;
import app.service.master.LivelihoodPrdouctionEPAWorkIdService;
import app.service.master.PfmsService;

@Controller("LivelihoodPrdouctionEPAWorkId")
public class LivelihoodPrdouctionEPAWorkIdController {
	
	HttpSession session=null;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	ProjectMasterService pmservice;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	LivelihoodPrdouctionEPAWorkIdService ser;
	
	@Autowired(required = true)
	CreateAssetIdService createAssetIdService;
	
	@RequestMapping(value="/createLivelihoodPrdouctionEPAWorkId", method = RequestMethod.GET)
	public ModelAndView createLivelihoodPrdouctionEPAWorkId(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userId = session.getAttribute("loginID").toString();
			String userType = session.getAttribute("userType").toString();
			Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		    mav = new ModelAndView("project/livelihoodPrdouctionEPAWorkId");
			mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
			
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getActivityEPALivelihoodProduction", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getActivity(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="projId") Integer projId, @RequestParam(value ="headcd") String headcd)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		map=ser.getActivityEPALivelihoodProduction(projId, headcd);
		return map; 
	}
	
	@RequestMapping(value="/getlivelihoodPrdouctionEPAbyProjScheme", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<LivelihoodPrdouctionEPAWorkIdBean>> getlivelihoodPrdouctionEPAbyProjScheme(HttpServletRequest request, HttpServletResponse response,
				@RequestParam(value ="projId") Integer projId, @RequestParam(value ="yrCode") String scheme, @RequestParam(value ="act") Integer activity)
	{
		LinkedHashMap<String, List<LivelihoodPrdouctionEPAWorkIdBean>> map = new LinkedHashMap<String, List<LivelihoodPrdouctionEPAWorkIdBean>>();
		List<EpaDetail> list = new ArrayList<EpaDetail>();
		List<LivelihoodDetail> listl = new ArrayList<LivelihoodDetail>();
		List<ProductionDetail> listp = new ArrayList<ProductionDetail>();
		List<LivelihoodPrdouctionEPAWorkIdBean> sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
		LivelihoodPrdouctionEPAWorkIdBean bean = new LivelihoodPrdouctionEPAWorkIdBean(); 
		Integer assetCount=0;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			
			if(scheme.equals("epa")) {
				list=ser.getEpaDetails(projId, scheme, activity);
				for(EpaDetail aap : list) {
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					bean.setActivitycode(aap.getMEpaCoreactivity().getEpaActivityId());
					bean.setActivityname(aap.getMEpaCoreactivity().getEpaDesc());
					bean.setProjectdesc(aap.getLivelihoodEpaProd().getIwmpMProject().getProjName());
					bean.setScheme(aap.getLivelihoodEpaProd().getHeadType());
					bean.setNoActivities(aap.getNoActivities());
					bean.setStatus(aap.getStatus());
					bean.setEpaDetailId(aap.getEpaDetailId());
					assetCount=0;
					List<WdcpmksyEpaWorkid> getEPAWorkId = ser.getEPAWorkId(aap.getMEpaCoreactivity().getEpaActivityId(), projId);  
					for(WdcpmksyEpaWorkid asset : getEPAWorkId)
						if(asset.getEpaDetail().getEpaDetailId()==aap.getEpaDetailId() && asset.getIwmpMProject().getProjectId()==projId )
						assetCount++;
					bean.setAssetcreated(assetCount);
					if (!map.containsKey(aap.getMEpaCoreactivity().getEpaDesc())) {
						sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
						sublist.add(bean);
						map.put(aap.getMEpaCoreactivity().getEpaDesc(), sublist);
					} else {
						sublist=(map.get(aap.getMEpaCoreactivity().getEpaDesc()));
						sublist.add(bean);
						map.put(aap.getMEpaCoreactivity().getEpaDesc(), sublist);
					}
				}
			}
			
			if(scheme.equals("livelihood")) {
				listl=ser.getLivelihoodDetails(projId, scheme, activity);
				for(LivelihoodDetail aap : listl) {
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					bean.setActivitycode(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityId());
					bean.setActivityname(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc());
					bean.setProjectdesc(aap.getLivelihoodEpaProd().getIwmpMProject().getProjName());
					bean.setScheme(aap.getLivelihoodEpaProd().getHeadType());
					bean.setNoActivities(aap.getNoActivities());
					bean.setStatus(aap.getStatus());
					bean.setEpaDetailId(aap.getLivelihoodDetailId());
					assetCount=0;
					List<WdcpmksyLivelihoodWorkid> getLivelihoodWorkid = ser.getLivelihoodWorkid(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityId(), projId);
					for(WdcpmksyLivelihoodWorkid asset : getLivelihoodWorkid)
						if(asset.getLivelihoodDetail().getLivelihoodDetailId()==aap.getLivelihoodDetailId() && asset.getIwmpMProject().getProjectId()==projId )
						assetCount++;
					bean.setAssetcreated(assetCount);
					if (!map.containsKey(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc())) {
						sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
						sublist.add(bean);
						map.put(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc(), sublist);
					} else {
						sublist=(map.get(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc()));
						sublist.add(bean);
						map.put(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc(), sublist);
					}
				}
			}
			
			if(scheme.equals("production")) {
				listp=ser.getProductionDetails(projId, scheme, activity);
				for(ProductionDetail aap : listp) {
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					if(aap.getMProductivityCoreactivity().getCoverArea()==null)
						bean.setCovera("N");
					else
						bean.setCovera(aap.getMProductivityCoreactivity().getCoverArea().toString());
					bean.setActivitycode(aap.getMProductivityCoreactivity().getProductivityCoreactivityId());
					bean.setActivityname(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc());
					bean.setProjectdesc(aap.getLivelihoodEpaProd().getIwmpMProject().getProjName());
					bean.setScheme(aap.getLivelihoodEpaProd().getHeadType());
					bean.setNoActivities(aap.getNoActivities());
					bean.setStatus(aap.getStatus());
					bean.setEpaDetailId(aap.getProductionDetailId());
					assetCount=0;
					List<WdcpmksyPrdouctionWorkid> getProductionWorkId = ser.getProductionWorkId(aap.getMProductivityCoreactivity().getProductivityCoreactivityId(), projId);
					for(WdcpmksyPrdouctionWorkid asset : getProductionWorkId)
						if(asset.getProductionDetail().getProductionDetailId()==aap.getProductionDetailId() && asset.getIwmpMProject().getProjectId()==projId )
						assetCount++;
					bean.setAssetcreated(assetCount);
					if (!map.containsKey(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc())) {
						sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
						sublist.add(bean);
						map.put(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc(), sublist);
					} else {
						sublist=(map.get(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc()));
						sublist.add(bean);
						map.put(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc(), sublist);
					}
				}
			}
			
		}else {

		}
		return map; 
	}
	
	@RequestMapping(value="/getlivelihoodPrdouctionEPAbyProjSchemeDraft", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getlivelihoodPrdouctionEPAbyProjSchemeDraft(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="projId") Integer projId,@RequestParam(value ="yrCode") String finYr, @RequestParam(value ="act") Integer activity)
	{
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<WdcpmksyEpaWorkid> list = new ArrayList<WdcpmksyEpaWorkid>();
		List<WdcpmksyLivelihoodWorkid> listl = new ArrayList<WdcpmksyLivelihoodWorkid>();
		List<WdcpmksyPrdouctionWorkid> listp = new ArrayList<WdcpmksyPrdouctionWorkid>();
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		Integer assetCount=0;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			
			if(finYr.equals("epa")) 
			{
				list=ser.getEPAbyProjSchemeDraft(projId, finYr,regId);
				for(WdcpmksyEpaWorkid aap : list) {
					bean = new AssetIdBean();
					bean.setAapid(aap.getEpaDetail().getEpaDetailId());
					bean.setActivitycd(aap.getMEpaCoreactivity().getEpaActivityId());
					bean.setActivitydesc(aap.getMEpaCoreactivity().getEpaDesc());
					bean.setAsseteid(aap.getAssetid());
					bean.setBcode(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
					bean.setBname(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setNearby(aap.getNearby());
					bean.setProjdesc(aap.getIwmpMProject().getProjName());
					bean.setProjid(aap.getIwmpMProject().getProjectId());
					bean.setVcode(aap.getIwmpVillage().getVcode());
					bean.setVname(aap.getIwmpVillage().getVillageName());
					
					//System.out.println("asset"+assetCount);
					if (!map.containsKey(aap.getMEpaCoreactivity().getEpaDesc())) {
						sublist = new ArrayList<AssetIdBean>();
						sublist.add(bean);
						map.put(aap.getMEpaCoreactivity().getEpaDesc(), sublist);
					} else {
						sublist=(map.get(aap.getMEpaCoreactivity().getEpaDesc()));
						sublist.add(bean);
						map.put(aap.getMEpaCoreactivity().getEpaDesc(), sublist);
					}
				}
			}
			
			
			if(finYr.equals("livelihood")) 
			{
				listl=ser.getLivelihoodbyProjSchemeDraft(projId, finYr,regId);
				for(WdcpmksyLivelihoodWorkid aap : listl) {
					bean = new AssetIdBean();
					bean.setAapid(aap.getLivelihoodDetail().getLivelihoodDetailId());
					bean.setActivitycd(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityId());
					bean.setActivitydesc(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc());
					bean.setAsseteid(aap.getAssetid());
					bean.setBcode(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
					bean.setBname(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setNearby(aap.getNearby());
					bean.setProjdesc(aap.getIwmpMProject().getProjName());
					bean.setProjid(aap.getIwmpMProject().getProjectId());
					bean.setVcode(aap.getIwmpVillage().getVcode());
					bean.setVname(aap.getIwmpVillage().getVillageName());
					
					System.out.println("asset"+assetCount);
					if (!map.containsKey(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc())) {
						sublist = new ArrayList<AssetIdBean>();
						sublist.add(bean);
						map.put(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc(), sublist);
					} else {
						sublist=(map.get(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc()));
						sublist.add(bean);
						map.put(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc(), sublist);
					}
				}
			}
			
			
			if(finYr.equals("production")) 
			{
				listp=ser.getProductionbyProjSchemeDraft(projId, finYr,regId);
				for(WdcpmksyPrdouctionWorkid aap : listp) {
					bean = new AssetIdBean();
					bean.setAapid(aap.getProductionDetail().getProductionDetailId());
					bean.setActivitycd(aap.getMProductivityCoreactivity().getProductivityCoreactivityId());
					bean.setActivitydesc(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc());
					bean.setAsseteid(aap.getAssetid());
					bean.setBcode(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
					bean.setBname(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setNearby(aap.getNearby());
				//	areacov[i]==null || areacov[i]==""
					if(aap.getAreaCovered()==null)
						bean.setAreacovered("");
					else
						bean.setAreacovered(aap.getAreaCovered().toString());
					bean.setProjdesc(aap.getIwmpMProject().getProjName());
					bean.setProjid(aap.getIwmpMProject().getProjectId());
					bean.setVcode(aap.getIwmpVillage().getVcode());
					bean.setVname(aap.getIwmpVillage().getVillageName());
					
					//System.out.println("asset"+assetCount);
					if (!map.containsKey(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc())) {
						sublist = new ArrayList<AssetIdBean>();
						sublist.add(bean);
						map.put(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc(), sublist);
					} 
					else {
						sublist=(map.get(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc()));
						sublist.add(bean);
						map.put(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc(), sublist);
					}
				}
			}
		}
		else {

		}
		return map; 
	}
	
	@RequestMapping(value="/getAssetIdForCreationEpaLivProd", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getAssetIdForCreation(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="pCode") Integer projId,
			@RequestParam(value ="yrCode") String finYr, @RequestParam(value ="activityCode") Integer activityCode)
	{
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<AssetIdBean> list = new ArrayList<AssetIdBean>();
		List<IwmpBlock> listBlock = new ArrayList<IwmpBlock>();
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			listBlock=createAssetIdService.getBlockFromProjectLocation(projId);
			
			if(finYr.equals("epa")) {
				list=ser.getAssetIdForEPACreation(activityCode);
				for(AssetIdBean asset : list) {
					bean = new AssetIdBean();
					bean.setActivitycd(asset.getActivitycd());
					bean.setActivitydesc(asset.getActivitydesc());
					bean.setBlocklist(listBlock);
					
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
			}
			
			if(finYr.equals("livelihood")) {
				list=ser.getAssetIdForLivelihoodCreation(activityCode);
				for(AssetIdBean asset : list) {
					bean = new AssetIdBean();
					bean.setActivitycd(asset.getActivitycd());
					bean.setActivitydesc(asset.getActivitydesc());
					bean.setBlocklist(listBlock);
					
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
			}
			
			if(finYr.equals("production")) {
				list=ser.getAssetIdForProductionCreation(activityCode);
				for(AssetIdBean asset : list) {
					bean = new AssetIdBean();
					bean.setActivitycd(asset.getActivitycd());
					bean.setActivitydesc(asset.getActivitydesc());
					bean.setBlocklist(listBlock);
					
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
			}
			
			
		}else {

		}
		return map; 
	}
	
	@RequestMapping(value="/saveEPALivelihoodProducttionAssetAsDraft", method = RequestMethod.POST)
	@ResponseBody
	public String saveAssetAsDraft(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="finyr") String finyr, 
			@RequestParam(value ="projcd") Integer projcd, @RequestParam(value ="aapid") Integer[] aapid,
			@RequestParam(value ="activity") Integer[] activity, @RequestParam(value ="vcode") Integer[] vcode, 
			@RequestParam(value ="near") String[] nearby, @RequestParam(value ="areacov") String[] areacov)
	{
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			//map=createAssetIdService.getVillageFromProjectLocationBlockWise(projId,bCode);
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
		//	System.out.println("finyr "+finyr+" projcd "+projcd+" aapid "+aapid+" activity "+activity+" vcode "+vcode);
			if(aapid.length==activity.length && activity.length==vcode.length && finyr!=null && projcd !=null) {
				res=ser.saveEPALivelihoodProducttionAssetAsDraft(finyr,projcd,aapid,activity,vcode,sentFrom, nearby, areacov);
			}else {
				return "fail";
			}
			
		}else {

		}
		return res; 
	}
	
	@RequestMapping(value="/deleteAssetEPALivelihoodProducttion", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAssetEPALivelihoodProducttion(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="tempassetid") Integer tempassetid, 
			@RequestParam(value ="finyr") String finyr, @RequestParam(value ="projcd") Integer projcd)
	{
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			//map=createAssetIdService.getVillageFromProjectLocationBlockWise(projId,bCode);
			if(tempassetid!=null) {
				res=ser.deleteAssetEPALivelihoodProducttion(tempassetid, finyr, projcd);
			}else {
				return "fail";
			}
			
		}else {

		}
		return res; 
	}
	
	@RequestMapping(value="/completeAssetEPALivelihoodProducttion", method = RequestMethod.POST)
	@ResponseBody
	public String completeAssetEPALivelihoodProducttion(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="tempassetid") List<BigInteger> tempassetid,
			@RequestParam(value ="finyr") String finyr, @RequestParam(value ="projcd") Integer projcd)
	{
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			//map=createAssetIdService.getVillageFromProjectLocationBlockWise(projId,bCode);
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String createdby = session.getAttribute("loginID").toString();
			String userType = session.getAttribute("userType").toString();
			if(tempassetid!=null) {
				res=ser.completeAssetEPALivelihoodProducttion(tempassetid, finyr, projcd);
			}else {
				return "fail";
			}
			
		}else {

		}
		return res; 
	}
	
	@RequestMapping(value="/forwardAssetEPALivelihoodProducttionPIA", method = RequestMethod.POST)
	@ResponseBody
	public String forwardAssetEPALivelihoodProducttionPIA(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="tempassetid") List<BigInteger> tempassetid,
			@RequestParam(value ="sentto") Integer sentto, @RequestParam(value ="finyr") String scheme, @RequestParam(value ="projcd") Integer projcd)
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
				res=ser.forwardEPALivelihoodProducttionWCDC(tempassetid,sentfrom,sentto,session,scheme,projcd );
			//	if(userType.equals("SL"))
					//res=createAssetIdService.forwardAssettoSLNA(sentfrom,tempassetid,sentto);
			}else {
				return "fail";
			}
			
		}else {

		}
		return res; 
	}

	@RequestMapping(value="/wcdcEPALivelihoodProductionWorkId", method = RequestMethod.GET)
	public ModelAndView wcdcEPALivelihoodProductionWorkId(HttpServletRequest request, HttpServletResponse response)
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
			mav = new ModelAndView("project/wcdclivelihoodPrdouctionEPAWorkId");
			
			List<ProfileBean> listm=new  ArrayList<ProfileBean>();
			listm=profileService.getMapstate(regId, userType);
			String distName = "";
			String stateName = "";
			int stCode = 0;
			int distCode = 0;
			for(ProfileBean bean : listm) {
				distName =bean.getDistrictname();
				distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
				stateName = bean.getStatename();
				stCode = bean.getStatecode()==null?0:bean.getStatecode();
			}
			mav.addObject("pendingProjectList",pmservice.getProjNACByDcode(distCode));
			
		//	mav.addObject("districtList", districtMasterService.getDistrictByStateCodeWithDcode(stcode));
		//	mav.addObject("assetlist",createAssetIdService.getListOfAssetUserWise(regId,userType));
	//		mav.addObject("distList",districtMasterService.getDistrictByStateCodeWithDcode(stateCode));
			/*
			 * for(IwmpMProject list :createAssetIdService.getProjectForPendingAsset(regId)
			 * ) { map.put(list.getProjectId(), list.getProjName()); }
			 * mav.addObject("pendingProjectList", map);
			 */
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	@RequestMapping(value="/getPendingAssetEPALivelihoodProduction", method = RequestMethod.POST)
	@ResponseBody
	public List<LivelihoodPrdouctionEPAWorkIdBean> getPendingAssetEPALivelihoodProductionProjectWise(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="pCode") Integer projId,@RequestParam(value ="scheme") String scheme)
	{
		ModelAndView mav = new ModelAndView();
		List<WdcpmksyEpaWorkid> list = new ArrayList<WdcpmksyEpaWorkid>();
		List<WdcpmksyLivelihoodWorkid> listl = new ArrayList<WdcpmksyLivelihoodWorkid>();
		List<WdcpmksyPrdouctionWorkid> listp = new ArrayList<WdcpmksyPrdouctionWorkid>();
		List<LivelihoodPrdouctionEPAWorkIdBean> assetList = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
		session = request.getSession(true);
		LivelihoodPrdouctionEPAWorkIdBean bean = new LivelihoodPrdouctionEPAWorkIdBean();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentTo = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			if(scheme.equals("epa")) 
			{
				if(userType.equals("DI") && projId==0) 
					list=ser.getListOfAssetUserWiseEpa(sentTo,userType);
				
				if(userType.equals("DI") && projId>0) 
					list=ser.getPendngAssetAtWCDCProjectWiseEpa(sentTo,userType,projId);
			//	if(userType.equals("SL"))
			//		list=createAssetIdService.getPendngAssetAtWCDCProjectWise(sentTo,userType,projId,dCode);
				for(WdcpmksyEpaWorkid temp : list) 
				{
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					bean.setProjdesc(temp.getIwmpMProject().getProjName());
					bean.setTempassetid(temp.getAssetid());
					bean.setActivitydesc(temp.getMEpaCoreactivity().getEpaDesc());
					bean.setBname(temp.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setVname(temp.getIwmpVillage().getVillageName());
					bean.setRemarks(temp.getRemarks());
					bean.setUsertype(temp.getIwmpUserRegBySentTo().getUserType());
					assetList.add(bean);
				}
			
			}
			if(scheme.equals("livelihood")) 
			{
				if(userType.equals("DI") && projId==0) 
					listl=ser.getListOfAssetUserWiseLivelihood(sentTo,userType);
				
				if(userType.equals("DI") && projId>0) 
					listl=ser.getPendngAssetAtWCDCProjectWiseLivelihood(sentTo,userType,projId);
			
				for(WdcpmksyLivelihoodWorkid temp : listl) 
				{
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					bean.setProjdesc(temp.getIwmpMProject().getProjName());
					bean.setTempassetid(temp.getAssetid());
					bean.setActivitydesc(temp.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc());
					bean.setBname(temp.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setVname(temp.getIwmpVillage().getVillageName());
					bean.setRemarks(temp.getRemarks());
					bean.setUsertype(temp.getIwmpUserRegBySentTo().getUserType());
					assetList.add(bean);
				}
			
			}
			if(scheme.equals("production")) 
			{
				if(userType.equals("DI") && projId==0) 
					listp=ser.getListOfAssetUserWiseProduction(sentTo,userType);
				
				if(userType.equals("DI") && projId>0) 
					listp=ser.getPendngAssetAtWCDCProjectWiseProduction(sentTo,userType,projId);
				
				for(WdcpmksyPrdouctionWorkid temp : listp) 
				{
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					bean.setProjdesc(temp.getIwmpMProject().getProjName());
					bean.setTempassetid(temp.getAssetid());
					bean.setActivitydesc(temp.getMProductivityCoreactivity().getProductivityCoreactivityDesc());
					bean.setBname(temp.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setVname(temp.getIwmpVillage().getVillageName());
					bean.setRemarks(temp.getRemarks());
					bean.setUsertype(temp.getIwmpUserRegBySentTo().getUserType());
					assetList.add(bean);
				}
			
			}
			
		}
		else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
		}
		return assetList; 
	}
	
	@RequestMapping(value="/completeAssetEPALivelihoodProduction", method = RequestMethod.POST)
	@ResponseBody
	public String forwardAssettoSLNA(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="assetid") List<BigInteger> tempassetid, @RequestParam(value="scheme") String scheme)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
		//	Integer sentto = createAssetIdService.getSentToSLNAForAsset(sentfrom);
		//	if(userType.equals("DI"))
		//	res=createAssetIdService.forwardAssettoSLNA(sentfrom,tempassetid,sentto);
		//	if(userType.equals("SL"))
				res=ser.completeAsset(sentfrom,tempassetid,scheme);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}

	@RequestMapping(value="/rejectAssetEPALivelihoodProduction", method = RequestMethod.POST)
	@ResponseBody
	public String rejectAsset(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="assetid") List<BigInteger> assetid,@RequestParam(value ="remarks") List<String> remarks, @RequestParam(value="scheme") String scheme)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			
			if(userType.equals("DI"))
			res=ser.rejectAssetbyWCDC(sentfrom, assetid, remarks, scheme);
		
		//  if(userType.equals("SL"))
		//	  res=createAssetIdService.rejectAssetbySLNA(sentfrom,assetid,remarks);
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@PostMapping("/getCompletedAssetList")
	@ResponseBody
	public LinkedHashMap<String, List<LivelihoodPrdouctionEPAWorkIdBean>> getCompletedAssetList(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="scheme") String scheme, @RequestParam(value="projcd") Integer projcd)
	{
		LinkedHashMap<String, List<LivelihoodPrdouctionEPAWorkIdBean>> map = new LinkedHashMap<String, List<LivelihoodPrdouctionEPAWorkIdBean>>();
		List<WdcpmksyEpaWorkid> list = new ArrayList<WdcpmksyEpaWorkid>();
		List<WdcpmksyLivelihoodWorkid> listl = new ArrayList<WdcpmksyLivelihoodWorkid>();
		List<WdcpmksyPrdouctionWorkid> listp = new ArrayList<WdcpmksyPrdouctionWorkid>();
		List<LivelihoodPrdouctionEPAWorkIdBean> sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
		LivelihoodPrdouctionEPAWorkIdBean bean = new LivelihoodPrdouctionEPAWorkIdBean();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			String userId = session.getAttribute("loginID").toString();
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			if(scheme.equals("epa")) 
			{
				list=ser.getCompletedAssetListEPA(userId, projcd, regid);
				for(WdcpmksyEpaWorkid phyas : list) 
				{
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					bean.setProjdesc(phyas.getIwmpMProject().getProjName());
					
					bean.setAssetid(phyas.getAssetid());
					bean.setActivitydesc(phyas.getMEpaCoreactivity().getEpaDesc());
					bean.setBname(phyas.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setVname(phyas.getIwmpVillage().getVillageName());
					
					if (!map.containsKey(phyas.getIwmpMProject().getProjName()))
					{
						sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
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
			if(scheme.equals("livelihood")) 
			{
				listl=ser.getCompletedAssetListLivelihood(userId, projcd, regid);
				for(WdcpmksyLivelihoodWorkid phyas : listl) 
				{
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					bean.setProjdesc(phyas.getIwmpMProject().getProjName());
					
					bean.setAssetid(phyas.getAssetid());
					bean.setActivitydesc(phyas.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc());
					bean.setBname(phyas.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setVname(phyas.getIwmpVillage().getVillageName());
					
					if (!map.containsKey(phyas.getIwmpMProject().getProjName()))
					{
						sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
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
			if(scheme.equals("production")) 
			{
				listp=ser.getCompletedAssetListProduction(userId, projcd, regid);
				for(WdcpmksyPrdouctionWorkid phyas : listp) 
				{
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					bean.setProjdesc(phyas.getIwmpMProject().getProjName());
					
					bean.setAssetid(phyas.getAssetid());
					bean.setActivitydesc(phyas.getMProductivityCoreactivity().getProductivityCoreactivityDesc());
					bean.setBname(phyas.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setVname(phyas.getIwmpVillage().getVillageName());
					
					if (!map.containsKey(phyas.getIwmpMProject().getProjName()))
					{
						sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
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
		}
		else {
			
		}
	return map;
	}
	
	@RequestMapping(value="/viewforwardedAssetEPL", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<LivelihoodPrdouctionEPAWorkIdBean>> viewforwardedAssetEPL(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="head") String scheme, @RequestParam(value="projcd") Integer projcd)
	{
		LinkedHashMap<String, List<LivelihoodPrdouctionEPAWorkIdBean>> map = new LinkedHashMap<String, List<LivelihoodPrdouctionEPAWorkIdBean>>();
		List<WdcpmksyEpaWorkid> list = new ArrayList<WdcpmksyEpaWorkid>();
		List<WdcpmksyLivelihoodWorkid> listl = new ArrayList<WdcpmksyLivelihoodWorkid>();
		List<WdcpmksyPrdouctionWorkid> listp = new ArrayList<WdcpmksyPrdouctionWorkid>();
		
		
		List<LivelihoodPrdouctionEPAWorkIdBean> sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
		LivelihoodPrdouctionEPAWorkIdBean bean = new LivelihoodPrdouctionEPAWorkIdBean();
		Integer assetCount=0;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userType = session.getAttribute("userType").toString();
			String userid = session.getAttribute("loginID").toString();
			
			if(scheme.equals("epa")) 
			{
				list=ser.getviewforwardedAssetEPA(regId,userType,userid, projcd);
				for(WdcpmksyEpaWorkid aap : list) {
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					
					bean.setActivitycd(aap.getMEpaCoreactivity().getEpaActivityId());
					bean.setActivitydesc(aap.getMEpaCoreactivity().getEpaDesc());
					bean.setTempassetid(aap.getAssetid());
					bean.setBcode(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
					bean.setBname(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setProjdesc(aap.getIwmpMProject().getProjName());
					bean.setProjid(aap.getIwmpMProject().getProjectId());
					bean.setVcode(aap.getIwmpVillage().getVcode());
					bean.setVname(aap.getIwmpVillage().getVillageName());
				//	bean.setDistApprovalReq(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getIwmpState().getApprovalReq()=='Y'?true:false);
					bean.setForwardedTo((aap.getIwmpUserRegBySentTo().getUserType().equals("DI")?"WCDC":"")+"("+aap.getIwmpUserRegBySentTo().getUserName()+")");
					
					//System.out.println("asset"+assetCount);
					if (!map.containsKey(aap.getIwmpMProject().getProjName())) {
						sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
						sublist.add(bean);
						map.put(aap.getIwmpMProject().getProjName(), sublist);
					} else {
						sublist=(map.get(aap.getIwmpMProject().getProjName()));
						sublist.add(bean);
						map.put(aap.getIwmpMProject().getProjName(), sublist);
					}
				}
			}
			if(scheme.equals("livelihood")) 
			{
				listl=ser.getviewforwardedAssetLivelihood(regId,userType,userid, projcd);
				for(WdcpmksyLivelihoodWorkid aap : listl) {
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					
					bean.setActivitycd(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityId());
					bean.setActivitydesc(aap.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc());
					bean.setTempassetid(aap.getAssetid());
					bean.setBcode(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
					bean.setBname(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setProjdesc(aap.getIwmpMProject().getProjName());
					bean.setProjid(aap.getIwmpMProject().getProjectId());
					bean.setVcode(aap.getIwmpVillage().getVcode());
					bean.setVname(aap.getIwmpVillage().getVillageName());
				//	bean.setDistApprovalReq(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getIwmpState().getApprovalReq()=='Y'?true:false);
					bean.setForwardedTo((aap.getIwmpUserRegBySentTo().getUserType().equals("DI")?"WCDC":"")+"("+aap.getIwmpUserRegBySentTo().getUserName()+")");
					
					//System.out.println("asset"+assetCount);
					if (!map.containsKey(aap.getIwmpMProject().getProjName())) {
						sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
						sublist.add(bean);
						map.put(aap.getIwmpMProject().getProjName(), sublist);
					} else {
						sublist=(map.get(aap.getIwmpMProject().getProjName()));
						sublist.add(bean);
						map.put(aap.getIwmpMProject().getProjName(), sublist);
					}
				}
			}
			if(scheme.equals("production")) 
			{
				listp=ser.getviewforwardedAssetProduction(regId,userType,userid, projcd);
				for(WdcpmksyPrdouctionWorkid aap : listp) {
					bean = new LivelihoodPrdouctionEPAWorkIdBean();
					
					bean.setActivitycd(aap.getMProductivityCoreactivity().getProductivityCoreactivityId());
					bean.setActivitydesc(aap.getMProductivityCoreactivity().getProductivityCoreactivityDesc());
					bean.setTempassetid(aap.getAssetid());
					bean.setBcode(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBcode());
					bean.setBname(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
					bean.setProjdesc(aap.getIwmpMProject().getProjName());
					bean.setProjid(aap.getIwmpMProject().getProjectId());
					bean.setVcode(aap.getIwmpVillage().getVcode());
					bean.setVname(aap.getIwmpVillage().getVillageName());
				//	bean.setDistApprovalReq(aap.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getIwmpDistrict().getIwmpState().getApprovalReq()=='Y'?true:false);
					bean.setForwardedTo((aap.getIwmpUserRegBySentTo().getUserType().equals("DI")?"WCDC":"")+"("+aap.getIwmpUserRegBySentTo().getUserName()+")");
					
					//System.out.println("asset"+assetCount);
					if (!map.containsKey(aap.getIwmpMProject().getProjName())) {
						sublist = new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
						sublist.add(bean);
						map.put(aap.getIwmpMProject().getProjName(), sublist);
					} else {
						sublist=(map.get(aap.getIwmpMProject().getProjName()));
						sublist.add(bean);
						map.put(aap.getIwmpMProject().getProjName(), sublist);
					}
				}
			}
			
			
			
		}else {

		}
		return map; 
	}
	
	@RequestMapping(value="/getUserDistEpaLive", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getUserDistEpaLive(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> res = new LinkedHashMap<Integer,String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			res=ser.getUserToForward(regId);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}	
	
	
	
}
