package app.controllers;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import app.model.IwmpMFinYear;
import app.model.IwmpMMonth;
import app.model.IwmpProjectAssetStatus;
import app.model.project.IwmpProjectPhysicalAsset;
import app.service.CreateAssetIdService;
import app.service.PhysicalAchievementService;
import app.service.PhysicalActionPlanService;
import app.service.ProjectMasterService;

@Controller("assetWiseStatus")
public class AssetWiseStatusController {

	HttpSession session=null;
	@Autowired(required = true)
	CreateAssetIdService createAssetIdService;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired
	PhysicalActionPlanService physicalActionPlanService;
	
	@Autowired(required = true)
	PhysicalAchievementService physicalAchievementService;
	
	@GetMapping("assetwisestatus")
	public ModelAndView assetWiseStatus(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("assetwisestatus");
			mav.addObject("getproject", projectMasterService.getProjectByRegId(regId));
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
	return mav;
	}
	
	@RequestMapping(value="/getYearForAssetWise", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getYearForAssetWise(HttpServletRequest request,@RequestParam(value ="pCode") Integer pCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		//map.put(0, "All");
		map.putAll(physicalActionPlanService.getFromYearForPhysicalActionPlanReport(pCode));
		return map;
	}

	@PostMapping("/getassetwisestatusdata")
	@ResponseBody
	public LinkedHashMap<String, List<AssetIdBean>> getassetwisestatusdata(HttpServletRequest request, HttpServletResponse response, @RequestParam("pCode") Integer pCode,
			@RequestParam("fYear") Integer fYear)
	{
		String userId = session.getAttribute("loginID").toString();
	//	System.out.println("userId:" +userId);
		LinkedHashMap<String, List<AssetIdBean>> map = new LinkedHashMap<String, List<AssetIdBean>>();
		List<IwmpProjectPhysicalAsset> list = new ArrayList<IwmpProjectPhysicalAsset>();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		AssetIdBean bean = new AssetIdBean();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=createAssetIdService.getassetWorkWiseList(pCode, userId, fYear);
			for(IwmpProjectPhysicalAsset phyas : list) {
				bean = new AssetIdBean();
				bean.setAssetid(phyas.getAssetid());
				bean.setHeaddesc(phyas.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());			
				bean.setActivitydesc(phyas.getIwmpMPhyActivity().getActivityDesc());
				bean.setBname(phyas.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
				bean.setVname(phyas.getIwmpVillage().getVillageName());
				List<IwmpProjectAssetStatus> getProjectPhysicalAssetStatus=physicalAchievementService.getProjectPhysicalAssetStatus(phyas.getAssetid());
				for(IwmpProjectAssetStatus status:getProjectPhysicalAssetStatus) 
				{
					bean.setStatus(status.getStatus());
				    bean.setReason(status.getReason());
				    bean.setConvergence(status.getConvergence());
				    bean.setSdate(dateFormat.format(status.getStartdate()));
				    bean.setStatusid(status.getStatusid());
				    if(status.getCompletiondate()!=null) 
				    {
				    	bean.setCdate(dateFormat.format(status.getCompletiondate()));
				    }
				}
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

	@PostMapping("/getassetwisecompletedata")
	@ResponseBody
	public List<AssetIdBean> getassetwisecompletedata(HttpServletRequest request, HttpServletResponse response,@RequestParam("pCode") Integer pCode, @RequestParam("fCode") Integer fCode)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<AssetIdBean>> map = new LinkedHashMap<Integer,List<AssetIdBean>>();
		List<AssetIdBean> proj = new ArrayList<AssetIdBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = createAssetIdService.getassetcompletiondata(pCode, fCode);
			
			
		}else {
			proj=null;
			
		}
		return proj;  
	}	
	
	
		
	
	@PostMapping("/getassetwiseforcloaseddata")
	@ResponseBody
	public List<AssetIdBean> getassetwiseforcloaseddata(HttpServletRequest request, HttpServletResponse response,@RequestParam("pCode") Integer pCode, @RequestParam("fCode") Integer fCode)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,List<AssetIdBean>> map = new LinkedHashMap<Integer,List<AssetIdBean>>();
		List<AssetIdBean> proj = new ArrayList<AssetIdBean>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			proj = createAssetIdService.getassetforcloseddata(pCode, fCode);
			
			
		}else {
			proj=null;
			
		}
		return proj;  
	}		
	
	@PostMapping("/saveassetstatus")
	@ResponseBody
	public String saveassetstatus(HttpServletRequest request, HttpServletResponse response, @RequestParam("finalAssetid")String finalAssetid)
	{
		String res="";
		ModelAndView mav = new ModelAndView();
		//String finalAssetid = request.getParameter("finalAssetid");
		String assetid[]=request.getParameterValues("assetid");
		String projid = request.getParameter("project");
		String finYear = request.getParameter("year");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=createAssetIdService.saveAssetStatus(request, assetid,projid, finYear, finalAssetid);
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

	
	
	
	@PostMapping("/updateassetstatus")
	@ResponseBody
	public String updateassetstatus(HttpServletRequest request, HttpServletResponse response)
	{
		String res="";
		ModelAndView mav = new ModelAndView();
		
		String assetid[]=request.getParameterValues("assetid");
		String statusid[]=request.getParameterValues("statusid");
		
		/* int projid = (Integer.parseInt(request.getParameter("asswiseproject"))); */
		
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=createAssetIdService.updateAssetStatus(request, assetid, statusid);
           if(res!=null)
           {
        	   mav = new ModelAndView("assetwisestatus");
           }
            
		}
		else {
			return "fail";
		}
		
		
	return res;
	}
	
	@RequestMapping(value="/getAllFinYearDetails", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer, Integer> getAllFinYearDetails()
	{
		List<IwmpMFinYear> list = new ArrayList<>();
		List<IwmpMMonth> mlist =  new ArrayList<>();
		Map<Integer, Integer> map = new LinkedHashMap<>();
		
		try {
		mlist = createAssetIdService.getAllMonthDetailData();
		
		list= createAssetIdService.getAllFinancialYearDetails();
		 
		for (IwmpMFinYear f : list) {
			if (f.getAchievStatus() == null) {
				for (IwmpMMonth m : mlist) {
					if (m.getAchievStatus() == null) {
						map.put(m.getMonthId(),  f.getFinYrCd());
					}
				}
			}
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	

}
