package app.controllers;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.AssetIdBean;
import app.bean.Login;
import app.bean.PhysicalActBean;
import app.model.IwmpProjectAssetStatus;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.WdcpmksyEpaWorkid;
import app.model.project.WdcpmksyLivelihoodWorkid;
import app.model.project.WdcpmksyPrdouctionWorkid;
import app.service.CreateAssetIdService;
import app.service.PhysicalAchievementService;
import app.service.ProjectMasterService;
import app.model.WdcpmksyProjectAssetLivelihoodStatus;
import app.model.WdcpmksyProjectAssetProductionStatus;
import app.model.WdcpmksyProjectAssetEPAStatus;
@Controller("assetWiseHeadStatusController")
public class AssetWiseHeadStatusController {

	HttpSession session=null;
	@Autowired(required = true)
	CreateAssetIdService createAssetIdService;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	PhysicalAchievementService physicalAchievementService;
	 
	@GetMapping("assetwiseheadstatus")
	public ModelAndView assetWiseHeadStatus(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("assetwiseheadstatus");
			mav.addObject("getproject", projectMasterService.getProjectByRegId(regId));
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
	return mav;
	}

	@PostMapping("/getassetwiseheadstatusdata")
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getassetwiseheadstatusdata(HttpServletRequest request, HttpServletResponse response, @RequestParam("pCode") Integer pCode,
			@RequestParam("hCode") String hCode, @RequestParam("hActCode") Integer hActCode)
	{
		String userId = session.getAttribute("loginID").toString();
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<WdcpmksyLivelihoodWorkid> list = new ArrayList<WdcpmksyLivelihoodWorkid>();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=createAssetIdService.getassetHeadWiseList(pCode, userId, hCode, hActCode);
			for(WdcpmksyLivelihoodWorkid livelihood: list)
			{
				bean = new AssetIdBean();
				bean.setAssetid(BigInteger.valueOf(livelihood.getAssetid()));
				bean.setActivitydesc(livelihood.getMLivelihoodCoreactivity().getLivelihoodCoreactivityDesc());
				bean.setBname(livelihood.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(livelihood.getIwmpVillage().getVillageName());
				
				List<WdcpmksyProjectAssetLivelihoodStatus> getProjectLivelihoodAssetStatus=physicalAchievementService.getProjectLivelihoodAssetStatus(livelihood.getAssetid());
				for(WdcpmksyProjectAssetLivelihoodStatus status:getProjectLivelihoodAssetStatus) 
				{
					bean.setStatus(status.getStatus());
				    bean.setReason(status.getReason());
				    bean.setSdate(dateFormat.format(status.getStartdate()));
				    bean.setStatusid(status.getStatusid());
				    if(status.getCompletiondate()!=null) 
				    {
				    	bean.setCdate(dateFormat.format(status.getCompletiondate()));
				    }
				}
				if (!map.containsKey(livelihood.getIwmpMProject().getProjName()))
				{
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(livelihood.getIwmpMProject().getProjName(), sublist);
				}
				else {
					sublist=(map.get(livelihood.getIwmpMProject().getProjName()));
					sublist.add(bean);
					map.put(livelihood.getIwmpMProject().getProjName(), sublist);
				}
			}
		}
		else {}
		
		return map;
	}

	@RequestMapping(value="/getHeadActivity", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getHeadActivity(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="headId") String headtype)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			map=createAssetIdService.getHeadActivitydesc(headtype);
			
		}else {

		}
		return map; 
	}
	

	@PostMapping("/getassetwiseproheadstatusdata")
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getassetwiseproheadstatusdata(HttpServletRequest request, HttpServletResponse response, @RequestParam("pCode") Integer pCode,
			@RequestParam("hCode") String hCode, @RequestParam("hActCode") Integer hActCode )
	{
		String userId = session.getAttribute("loginID").toString();
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<WdcpmksyPrdouctionWorkid> list = new ArrayList<WdcpmksyPrdouctionWorkid>();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=createAssetIdService.getassetProdHeadWiseList(pCode, userId, hActCode);
			for(WdcpmksyPrdouctionWorkid production: list)
			{
				bean = new AssetIdBean();
				bean.setAssetid(BigInteger.valueOf(production.getAssetid()));
				bean.setActivitydesc(production.getMProductivityCoreactivity().getProductivityCoreactivityDesc());
				bean.setBname(production.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(production.getIwmpVillage().getVillageName());
				
				List<WdcpmksyProjectAssetProductionStatus> getProjectProductionAssetStatus=physicalAchievementService.getProjectProductionAssetStatus(production.getAssetid());
				for(WdcpmksyProjectAssetProductionStatus status : getProjectProductionAssetStatus) 
				{
					bean.setStatus(status.getStatus());
				    bean.setReason(status.getReason());
				    bean.setSdate(dateFormat.format(status.getStartdate()));
				    bean.setStatusid(status.getStatusid());
				    if(status.getCompletiondate()!=null) 
				    {
				    	bean.setCdate(dateFormat.format(status.getCompletiondate()));
				    }
				}
				if (!map.containsKey(production.getIwmpMProject().getProjName()))
				{
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(production.getIwmpMProject().getProjName(), sublist);
				}
				else {
					sublist=(map.get(production.getIwmpMProject().getProjName()));
					sublist.add(bean);
					map.put(production.getIwmpMProject().getProjName(), sublist);
				}
			}
		}
		else {}
		
		return map;
	}
	
	@PostMapping("/getassetwiseheadcompletedata")
	@ResponseBody
	public List<AssetIdBean> getassetwiseheadcompletedata(HttpServletRequest request, HttpServletResponse response,@RequestParam("pCode") Integer pCode, @RequestParam("hCode") String hCode,
			@RequestParam("headactivity") Integer headactivity)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<AssetIdBean>> map = new LinkedHashMap<Integer,List<AssetIdBean>>();
		List<AssetIdBean> proj = new ArrayList<AssetIdBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = createAssetIdService.getassetheadcompletiondata(pCode, hCode, headactivity);
			
			
		}else {
			proj=null;
			
		}
		return proj;  
	}
	
	@PostMapping("/getassetwiseheadforcloseddata")
	@ResponseBody
	public List<AssetIdBean> getassetwiseheadforcloseddata(HttpServletRequest request, HttpServletResponse response,@RequestParam("pCode") Integer pCode, @RequestParam("hCode") String hCode)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<AssetIdBean>> map = new LinkedHashMap<Integer,List<AssetIdBean>>();
		List<AssetIdBean> proj = new ArrayList<AssetIdBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
				proj = createAssetIdService.getassetheadforcloseddata(pCode, hCode);
		
		}else {
			proj=null;
			
		}
		return proj;  
	}	
	
	@PostMapping("/getassetwiseepaheadstatusdata")
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getassetwiseepaheadstatusdata(HttpServletRequest request, HttpServletResponse response, @RequestParam("pCode") Integer pCode,
			@RequestParam("hCode") String hCode, @RequestParam("hActCode") Integer hActCode)
	{
		String userId = session.getAttribute("loginID").toString();
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<WdcpmksyEpaWorkid> list = new ArrayList<WdcpmksyEpaWorkid>();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=createAssetIdService.getassetepaHeadWiseList(pCode, userId, hActCode);
			for(WdcpmksyEpaWorkid epa: list)
			{
				bean = new AssetIdBean();
				bean.setAssetid(BigInteger.valueOf(epa.getAssetid()));
				bean.setActivitydesc(epa.getMEpaCoreactivity().getEpaDesc());
				bean.setBname(epa.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(epa.getIwmpVillage().getVillageName());
				List<WdcpmksyProjectAssetEPAStatus> getProjectEPAAssetStatus=physicalAchievementService.getProjectEPAAssetStatus(epa.getAssetid());
				for(WdcpmksyProjectAssetEPAStatus status : getProjectEPAAssetStatus) 
				{
					bean.setStatus(status.getStatus());
				    bean.setReason(status.getReason());
				    bean.setSdate(dateFormat.format(status.getStartdate()));
				    bean.setStatusid(status.getStatusid());
				    if(status.getCompletiondate()!=null) 
				    {
				    	bean.setCdate(dateFormat.format(status.getCompletiondate()));
				    }
				}
				if (!map.containsKey(epa.getIwmpMProject().getProjName()))
				{
					sublist = new ArrayList<AssetIdBean>();
					sublist.add(bean);
					map.put(epa.getIwmpMProject().getProjName(), sublist);
				}
				else {
					sublist=(map.get(epa.getIwmpMProject().getProjName()));
					sublist.add(bean);
					map.put(epa.getIwmpMProject().getProjName(), sublist);
				}
			}
		}
		else {}
		
		return map;
	}
	@PostMapping("/saveassetlivelihoodstatus")
	@ResponseBody
	public String saveassetlivelihoodstatus(HttpServletRequest request, HttpServletResponse response)
	{
		String res="";
		ModelAndView mav = new ModelAndView();
		String finalAssetid = request.getParameter("finalAssetid");
		String assetid[]=request.getParameterValues("assetid");
		String projid = request.getParameter("project");
		String head = request.getParameter("head");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=createAssetIdService.saveAssetLivelihoodStatus(request, assetid,projid, head, finalAssetid);
             if(res.equals("true"))
             {
            	 res="true";
             }
		}
		else {
			return "fail";
		}
		
		
	return res;
	}

	@PostMapping("/saveassetproductionstatus")
	@ResponseBody
	public String saveassetproductionstatus(HttpServletRequest request, HttpServletResponse response)
	{
		String res="";
		ModelAndView mav = new ModelAndView();
		String finalAssetid = request.getParameter("finalAssetid");
		String assetid[]=request.getParameterValues("assetid");
		String projid = request.getParameter("project");
		String head = request.getParameter("head");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=createAssetIdService.saveAssetProductionStatus(request, assetid,projid, head, finalAssetid);
             if(res.equals("true"))
             {
            	 res="true";
             }
		}
		else {
			return "fail";
		}
		
		
	return res;
	}


@PostMapping("/saveassetepastatus")
@ResponseBody
public String saveassetepastatus(HttpServletRequest request, HttpServletResponse response)
{
	String res="";
	ModelAndView mav = new ModelAndView();
	String finalAssetid = request.getParameter("finalAssetid");
	String assetid[]=request.getParameterValues("assetid");
	String projid = request.getParameter("project");
	String head = request.getParameter("head");
	session = request.getSession(true);
	if(session!=null && session.getAttribute("loginID")!=null) {
		res=createAssetIdService.saveAssetEPAStatus(request, assetid,projid, head, finalAssetid);
         if(res.equals("true"))
         {
        	 res="true";
         }
	}
	else {
		return "fail";
	}
	
	
return res;
}
}
