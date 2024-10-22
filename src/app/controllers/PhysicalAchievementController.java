package app.controllers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import app.bean.PhysicalAchievementBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.model.IwmpActivityAssetAddonParameter;
import app.model.IwmpActivityAssetAddonParameterAchievement;
import app.model.IwmpMProject;
import app.model.IwmpProjectAssetStatus;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalAsset;
import app.model.project.WdcpmksyProjectPhyAssetAchievement;
import app.model.project.WdcpmksyProjectPhysicalAchievementDetails;
import app.service.PhysicalAchievementService;
import app.service.ProjectMasterService;

@Controller("PhysicalAchievement")
public class PhysicalAchievementController {
	
	HttpSession session=null;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	PhysicalAchievementService physicalAchievementService;
	
	private String pattern = "dd-MM-yyyy";
	private String patternk = "yyyy-MM-dd";
	
	@RequestMapping(value="/physicalAchievement", method = RequestMethod.GET)
	public ModelAndView addPhysicalAchievement(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("addPhysicalAchievement");
		//    mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
		    mav.addObject("projectList", projectMasterService.getProjectByRegIdPlan(regId));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getActivityWithTarget", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalAchievementBean> getActivityWithTarget(HttpServletRequest request,@RequestParam(value ="pCode") Integer pCode)
	{
		LinkedHashMap<Integer,List<PhysicalAchievementBean>> map = new LinkedHashMap<Integer,List<PhysicalAchievementBean>>();
		PhysicalAchievementBean bean = new PhysicalAchievementBean();
		List<PhysicalAchievementBean> sublist = new ArrayList<PhysicalAchievementBean>();
		session = request.getSession(true);
		session.setAttribute("endDate", null);
		List<PhysicalAchievementBean> list = new ArrayList<PhysicalAchievementBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer finYr=0;
			Integer monthId=0;
			String[] strArr =null;
			String pyear=null;
			List<String> yrMonth = new ArrayList<String>();
			String listYr = physicalAchievementService.getFinancialYearForPlan(pCode);
		//	pyear=physicalAchievementService.getMinumFinYear(pCode);
		//	bean.setFin_yr_desc(pyear);
			if(listYr!=null) {
			yrMonth = physicalAchievementService.getCurrentFinYrMonthId(pCode,listYr);
			strArr = yrMonth.get(0).split("-");
			
		//	if(strArr[0].equals("na"))
				
			
			if(yrMonth.size()>0 && !yrMonth.get(0).split("-")[0].equals("na")) {
				
			finYr=Integer.parseInt(strArr[0]);
			if(Integer.parseInt(strArr[1])==13) {
				monthId=1;
			}else
			if(Integer.parseInt(strArr[1])==14) {
				monthId=2;
			}else
			if(Integer.parseInt(strArr[1])==15) {
				monthId=3;
			}else
				monthId=Integer.parseInt(strArr[1]);
			
			}
			}
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Calendar.MONTH, monthId-1);
			
			  if(monthId.equals(1) || monthId.equals(3) || monthId.equals(2))
				  calendar.set(Calendar.YEAR, Integer.parseInt("20"+(finYr+1))); 
			  else
				  calendar.set(Calendar.YEAR, Integer.parseInt("20"+finYr));
			  
			calendar.set(Calendar.DAY_OF_MONTH,
	        calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	        setTimeToEndofDay(calendar);
			Date endDate = calendar.getTime();
			
			calendar.set(Calendar.DAY_OF_MONTH,
			 calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			setTimeToBeginningOfDay(calendar);
			Date startDate = calendar.getTime();
			String sDate =new SimpleDateFormat(pattern).format(startDate);
			String eDate =new SimpleDateFormat(pattern).format(endDate);  
			String stDate =new SimpleDateFormat(patternk).format(startDate);
			session.setAttribute("endDate", stDate);
		for(PhysicalAchievementBean b : physicalAchievementService.getActivityWithTarget(pCode,finYr,monthId)) {
			if(!yrMonth.get(0).split("-")[0].equals("na")) {
			b.setStartdate(sDate);
			b.setEnddate(eDate);
			}else {
				b.setStartdate(null);
				b.setEnddate(null);
			}
				
			b.setStartmonth(monthId);
			b.setFinyr(finYr);
			list.add(b);
		}
	//	System.out.println(list.size());
		/*
		 * if(yrMonth.get(0).split("-")[0].equals("na")) { list = new
		 * ArrayList<PhysicalAchievementBean>(); PhysicalAchievementBean b = new
		 * PhysicalAchievementBean(); b.setStartdate(null); b.setEnddate(null);
		 * 
		 * list.add(b); }
		 */
		/*
		 * for(PhysicalAchievementBean aap : list) { bean = new
		 * PhysicalAchievementBean();
		 * bean.setActivitycd(aap.getIwmpMPhyActivity().getActivityCode());
		 * bean.setActivitydesc(aap.getIwmpMPhyActivity().getActivityDesc());
		 * bean.setHeadcode(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadCode());
		 * bean.setHeaddesc(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
		 * bean.setUnitcode(aap.getIwmpMPhyActivity().getIwmpMUnit().getUnitCode());
		 * bean.setUnitdesc(aap.getIwmpMPhyActivity().getIwmpMUnit().getUnitDesc());
		 * bean.setTarget(aap.getQtyPlanned()); if
		 * (!map.containsKey(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadCode())
		 * ) { sublist = new ArrayList<PhysicalAchievementBean>(); sublist.add(bean);
		 * map.put(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadCode(), sublist);
		 * } else {
		 * sublist=(map.get(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadCode()))
		 * ; sublist.add(bean);
		 * map.put(aap.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadCode(), sublist);
		 * } }
		 */
		}else {
			
		}
		
		return list;
	}
	
	private static void setTimeToBeginningOfDay(Calendar calendar) {
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	}

	private static void setTimeToEndofDay(Calendar calendar) {
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	}
	
	@RequestMapping(value="/getAssetofActivity", method = RequestMethod.POST)
	@ResponseBody
	public List<AssetIdBean> getAssetofActivity(HttpServletRequest request,@RequestParam(value ="activityCode") Integer activityCode,@RequestParam(value ="pCode") Integer pCode) throws ParseException
	{
		LinkedHashMap<Integer,List<AssetIdBean>> map = new LinkedHashMap<Integer,List<AssetIdBean>>();
		AssetIdBean bean = new AssetIdBean();
		List<AssetIdBean> sublist = new ArrayList<AssetIdBean>();
		session = request.getSession(true);
		List<IwmpProjectPhysicalAsset> list = new ArrayList<IwmpProjectPhysicalAsset>();
		List<String> paramList = new ArrayList<String>();
		List<Long> paramIdList = new ArrayList<Long>();
		LinkedHashMap<String,BigDecimal> paramAchievementMap = new LinkedHashMap<String,BigDecimal>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			String fin=null;
			Integer finyrch=0;
			Integer finYr=0;
			Integer monthId=0;
			Integer monthcc=0;
			Integer monthc=0;
			Integer yrc=0;
			String endDate=session.getAttribute("endDate").toString();
			String listYr = physicalAchievementService.getFinancialYearForPlan(pCode);
			List<String> yrMonth = physicalAchievementService.getCurrentFinYrMonthId(pCode,listYr);
			String[] strArr = yrMonth.get(0).split("-");	
			finYr=Integer.parseInt(strArr[0]);
		//	monthId=Integer.parseInt(strArr[1]);
			String inputDate = "1-"+monthId+"-"+finYr;
			Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);  
		//	  System.out.println(pCode+" : "+activityCode+" : "+finYr +" : "+monthId);
			  
			  if(Integer.parseInt(strArr[1])==13) {
					monthId=1;
				}else
				if(Integer.parseInt(strArr[1])==14) {
					monthId=2;
				}else
				if(Integer.parseInt(strArr[1])==15) {
					monthId=3;
				}else
					monthId=Integer.parseInt(strArr[1]);
			  
		list = physicalAchievementService.getAssetofActivity(pCode,activityCode,finYr, endDate);
		for(IwmpProjectPhysicalAsset asset : list) {
			paramList = new ArrayList<String>();
			paramIdList = new ArrayList<Long>();
			paramAchievementMap = new LinkedHashMap<String,BigDecimal>();
			bean = new AssetIdBean();
			bean.setActivitydesc(asset.getIwmpMPhyActivity().getActivityDesc());
			bean.setHeaddesc(asset.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
			bean.setAssetid(asset.getAssetid());
			bean.setBname(asset.getIwmpVillage().getIwmpGramPanchayat().getIwmpBlock().getBlockName());
			bean.setVname(asset.getIwmpVillage().getVillageName());
			bean.setProjdesc(asset.getIwmpMProject().getProjName());
			bean.setFinyrdesc(asset.getIwmpMFinYear().getFinYrDesc());
			bean.setUnit(asset.getIwmpMPhyActivity().getIwmpMUnit().getUnitDesc());
			
			if(asset.getNearby()==null)
				bean.setNearby("");
			else
				bean.setNearby(asset.getNearby());
			
		    if(monthId==1) {
		    	 fin= "20"+(finYr+1);
		    	 finyrch=Integer.parseInt(fin);
		    }	
		    else if(monthId==2) {
		    	 fin= "20"+(finYr+1);
		    	 finyrch=Integer.parseInt(fin);
		    }	
		    else if(monthId==3) {
		    	 fin= "20"+(finYr+1);
		    	 finyrch=Integer.parseInt(fin);
		    }
		    else {
		    	fin= "20"+finYr;
		    	finyrch=Integer.parseInt(fin);
		    }
		    	
		    	
		    	
			if(asset.getIwmpMPhyActivity().getIwmpMUnit().getUnitDesc().equalsIgnoreCase("nos")) 
			{
				paramList.add("Completed");
				List<IwmpProjectAssetStatus> getProjectPhysicalAssetStatus=physicalAchievementService.getProjectPhysicalAssetStatus(asset.getAssetid());
				for(IwmpProjectAssetStatus status : getProjectPhysicalAssetStatus) 
				{
					java.util.Date date= status.getCompletiondate();
					if(status.getStatus().equals('C')) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					monthc = cal.get(Calendar.MONTH);
					yrc = cal.get(Calendar.YEAR);
					monthcc=monthc+1;
				//	System.out.println(status.getCompletiondate()+", monkd=" +monthcc);
				//	System.out.println( asset.getAssetid()+"_"+status.getStatusid() +", "+status.getStatus() );
					}
					//for(WdcpmksyProjectPhyAssetAchievement ach:status.getIwmpProjectPhysicalAsset().getWdcpmksyProjectPhyAssetAchievements())
					if(!paramAchievementMap.containsKey(asset.getAssetid()+"_"+status.getStatusid())  && (status.getStartdate().after(date1))) 
					{
						paramAchievementMap.put(asset.getAssetid()+"_"+status.getStatusid(),status.getStatus()==null?BigDecimal.ZERO:(status.getStatus().equals('C')
								&& monthcc.equals(monthId) && finyrch.equals(yrc))?BigDecimal.ONE:BigDecimal.ZERO);
					//	System.out.println( asset.getAssetid()+"_"+status.getStatusid() +", "+status.getStatus() +" kdy = "+ status.getStatus()==null?BigDecimal.ZERO:(status.getStatus().equals('C')&& monthcc==monthId)?BigDecimal.ONE:BigDecimal.ZERO);
					}/*
					 * if(!paramIdList.contains(ach.getAssetAchievementId()))
					 * paramIdList.add(ach.getAssetAchievementId());
					 */
					
				}
			}
			else {
				paramList.add("Achievement Area in("+asset.getIwmpMPhyActivity().getIwmpMUnit().getUnitDesc()+")");
				List<WdcpmksyProjectPhyAssetAchievement> getProjectPhysicalAssetAchievement=physicalAchievementService.getProjectPhysicalAssetAchievement(asset.getAssetid());
				for(WdcpmksyProjectPhyAssetAchievement ach : getProjectPhysicalAssetAchievement) 
				{
					if(!paramAchievementMap.containsKey(asset.getAssetid()+"_"+ach.getAssetAchievementId()) 
							&& ach.getIwmpProjectPhysicalAsset().getAssetid()==asset.getAssetid() && ach.getIwmpMMonth().getMonthId()==monthId && ach.getIwmpMFinYear().getFinYrCd()==finYr)
						paramAchievementMap.put(asset.getAssetid()+"_"+ach.getAssetAchievementId(),ach.getAchievement());
					
				}
			}
			bean.setAssetParameter(paramList);
			bean.setAssetParameterId(paramIdList);
			bean.setAssetParameterAchievement(paramAchievementMap);
			sublist.add(bean);
		}
	}
	else {
			
	}
		
		return sublist;
	}
	
	
	@RequestMapping(value="/saveAssetAchievement", method = RequestMethod.POST)
	@ResponseBody
	public String saveAssetAchievement(HttpServletRequest request,@RequestParam(value ="value") List<String> assetAcheivement,@RequestParam(value ="finYr") Integer finYr,@RequestParam(value ="monthId") Integer monthId)
	{
		
		String res="";
		session = request.getSession(true);
		System.out.println(assetAcheivement+" : "+finYr+" : "+monthId);
		if(session!=null && session.getAttribute("loginID")!=null) {
		physicalAchievementService.saveAssetAchievement(assetAcheivement,finYr,monthId, session.getAttribute("loginID").toString());
		}
		return res;
		
	}
	
	@RequestMapping(value="/getUserToForwardForPhysicalAchievement", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getUserToForward(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> res = new LinkedHashMap<Integer,String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userType = session.getAttribute("userType").toString();
			String val = physicalAchievementService.checkDistrictApprovalReuiredOrNot(regId);
			Character action='F';
			Integer achid=0;
			if(val.equalsIgnoreCase("Y"))
			res=physicalAchievementService.getUserToForward(regId,userType,action,achid);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	 
	@RequestMapping(value="/forwardAchievementFromPIA", method = RequestMethod.POST)
	@ResponseBody
	public String saveAchievementAsDraft(HttpServletRequest request, @RequestParam(value ="pCode") Integer pCode,
			@RequestParam(value ="activity") String activityCode, @RequestParam(value ="ach") String ach,
			@RequestParam(value ="sentTo") String sentTo, @RequestParam(value ="blsconf") boolean blsconf)
	{
		
		String res="";  
		Integer finYr=0;
		Integer monthId=0;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			String createdBy = session.getAttribute("loginID").toString();
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType = session.getAttribute("userType").toString();
			Character action='F';
			Integer achid=0;
			Integer sentto1=0;
		//	if(sentTo==null)
			if(sentTo.equals(null) || sentTo=="") 	
			for (Map.Entry<Integer, String> entry : physicalAchievementService.getUserToForward(sentFrom,userType,action,achid).entrySet()) {
				Integer key = entry.getKey();
				sentto1 = key;
			//	System.out.println( "regidkd "+sentto1+ " key= "+key);
			}
			if(sentTo!="")
				sentto1 =Integer.parseInt(sentTo);
			
			String listYr = physicalAchievementService.getFinancialYearForPlan(pCode);
			List<String> yrMonth = physicalAchievementService.getCurrentFinYrMonthId(pCode,listYr);
			String[] strArr = yrMonth.get(0).split("-");	
			finYr=Integer.parseInt(strArr[0]);
			if(Integer.parseInt(strArr[1])==13) {
				monthId=1;
			}
			else if(Integer.parseInt(strArr[1])==14) {
				monthId=2;
			}
			else if(Integer.parseInt(strArr[1])==15) {
				monthId=3;
			}
			else
				monthId=Integer.parseInt(strArr[1]);
		//System.out.println(pCode+" : act "+activityCode+" : ach "+ach+" : yr "+finYr+" : month "+monthId+" : SentFrom "+sentFrom+" : SentTo "+sentTo);
			res=physicalAchievementService.saveAchievementAsDraft(pCode,activityCode,ach,finYr,monthId,sentFrom,sentto1,createdBy, blsconf);
		}
		return res;
		
	}
	
	
	/********************************* WCDC Code ***************************************************************/
	
	@RequestMapping(value="/wcdcActionOnPhyAch", method = RequestMethod.GET)
	public ModelAndView wcdcActionOnPhyAch(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("actionOnPhysicalAchievement");
		    mav.addObject("projectList", physicalAchievementService.viewAchievement(regId));//physicalAchievementService.getAchievementData(regId)
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/checkForAlreadyForwardedAchievement", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalAchievementBean> checkForAlreadyForwardedAchievement(HttpServletRequest request,@RequestParam(value ="achid") Integer achid)
	{
		
		List<PhysicalAchievementBean> list = new ArrayList<PhysicalAchievementBean>();
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			list=physicalAchievementService.checkForAlreadyForwardedAchievement(achid,sentFrom);
		}
		return list;
		
	}
	
	@RequestMapping(value="/forwardAchievementByWCDC", method = RequestMethod.POST)
	@ResponseBody
	public String forwardAchievementByWCDC(HttpServletRequest request,@RequestParam(value ="achid") Integer achid,@RequestParam(value ="action") Character action,@RequestParam(value ="remarks") String remarks)
	{
		
		System.out.println(achid+" : "+action+" : "+remarks);
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		String value=null;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			
			  Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			  String userType= session.getAttribute("userType").toString();
			  Integer sentTo=null;
					for (Map.Entry<Integer, String> entry : physicalAchievementService.getUserToForward(sentFrom,userType,action,achid).entrySet()) {
						Integer key = entry.getKey();
						sentTo = key;
						}
			  //Integer sentto = physicalActionPlanService.getSentToByRegId(sentfrom,action,planid,userType);
			  System.out.println("Sento: "+sentTo); 
				 if(userType.equals("SL") && action.equals('F')) 
					 action='C';
				 
			  if(sentTo>0 && sentFrom>0)
			  value=physicalAchievementService.forwardAchievementByWCDC(sentTo,sentFrom,achid,action, remarks,userType);
			  if(value.equals("success") && userType.equals("SL")) {
			  res="state"; 
			  } 
			  if(value.equals("success") && userType.equals("DI")) {
			  res="district"; 
			  }
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res;
		
	}
	
	
	@RequestMapping(value="/viewAchMovement", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<PhysicalAchievementBean>> viewMovement(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<PhysicalAchievementBean> list = new ArrayList<PhysicalAchievementBean>();
		LinkedHashMap<String, List<PhysicalAchievementBean>> map = new LinkedHashMap<String, List<PhysicalAchievementBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			list= physicalAchievementService.viewMovement(regId);
			List<PhysicalAchievementBean> sublist = new ArrayList<PhysicalAchievementBean>();
			if ((list != null) && (list.size() > 0)) {
				for (PhysicalAchievementBean row : list){
					sublist = new ArrayList<PhysicalAchievementBean>();
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
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return map; 
	}
	
	@RequestMapping(value="/CompletedAchPlan", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<PhysicalAchievementBean>> CompletedAchPlan(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<PhysicalAchievementBean> list = new ArrayList<PhysicalAchievementBean>();
		LinkedHashMap<String, List<PhysicalAchievementBean>> map = new LinkedHashMap<String, List<PhysicalAchievementBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			list= physicalAchievementService.viewCompletedMovement(regId);
			List<PhysicalAchievementBean> sublist = new ArrayList<PhysicalAchievementBean>();
			if ((list != null) && (list.size() > 0)) {
				for (PhysicalAchievementBean row : list){
					sublist = new ArrayList<PhysicalAchievementBean>();
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
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return map; 
	}
	
	
	@RequestMapping(value="/getAchievementDetails", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<PhysicalAchievementBean>> getAchievementDetails(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="achievementid") Integer achievementid)
	{
		ModelAndView mav = new ModelAndView();
		PhysicalAchievementBean bean = new PhysicalAchievementBean();
		List<WdcpmksyProjectPhysicalAchievementDetails> res = new ArrayList<WdcpmksyProjectPhysicalAchievementDetails>();
		List<PhysicalAchievementBean> lst = new ArrayList<PhysicalAchievementBean>();
		LinkedHashMap<String, List<PhysicalAchievementBean>> map = new LinkedHashMap<String, List<PhysicalAchievementBean>>();
		Integer i=1;
		String status=null;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=physicalAchievementService.getAchievementDetails(achievementid);
			
			
			 lst=physicalAchievementService.getAchievementMovementDetails(achievementid); 
			 for(PhysicalAchievementBean row : lst){
			  if(i==1) { 
				  if(row.getAction().toString().equals("F") ) {
			  status="Pending at "+row.getSentto(); 
			  }if(row.getAction().toString().equals("R") ) { 
				  status="Rejected By "+row.getSentfrom();
				  }if( row.getAction().toString().equals("C")) {
			  status="Completed"; 
			  } 
				  i++; 
				  }
			  
			  }
			
			List<PhysicalAchievementBean> sublist = new ArrayList<PhysicalAchievementBean>();
				if ((res != null) && (res.size() > 0)) {
					for(WdcpmksyProjectPhysicalAchievementDetails list : res) {
						bean = new PhysicalAchievementBean();
						bean.setActivitycd(list.getIwmpMPhyActivity().getActivityCode());
						bean.setActivitydesc(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getSeqNo()+"."+list.getIwmpMPhyActivity().getSeqNo()+". "+list.getIwmpMPhyActivity().getActivityDesc());
						bean.setHeadcode(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadCode());
						bean.setHeaddesc(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getSeqNo()+". "+list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
						bean.setUnitcode(list.getIwmpMPhyActivity().getIwmpMUnit().getUnitCode());
						bean.setUnitdesc(list.getIwmpMPhyActivity().getIwmpMUnit().getUnitDesc());
						bean.setAchievement(list.getAchievement());
						bean.setAchievementid(list.getAchievementDetailsId());
						bean.setProjectdesc(list.getWdcpmksyProjectPhysicalAchievement().getIwmpMProject().getProjName());
						bean.setFinyrdesc(list.getWdcpmksyProjectPhysicalAchievement().getIwmpMFinYear().getFinYrDesc());
						bean.setMonthdesc(list.getWdcpmksyProjectPhysicalAchievement().getIwmpMMonth().getMonthName());
						if((status.contains("Pending") || status.contains("Rejected")) && list.getWdcpmksyProjectPhysicalAchievement().getStatus().toString().equals("D")) {
							bean.setStatus(status);
						}if( status.contains("Completed") && list.getWdcpmksyProjectPhysicalAchievement().getStatus().toString().equals("C")) { 
							bean.setStatus(status);
						}
						System.out.println("kd "+bean.getStatus()+ " month -"+bean.getMonthdesc());
						if (!map.containsKey(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc())) {
							sublist = new ArrayList<PhysicalAchievementBean>();
							sublist.add(bean);
							map.put(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc(), sublist);
						} else {
							sublist=(map.get(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc()));
							sublist.add(bean);
							map.put(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc(), sublist);
						}
					
						
					}
				
				//beanList.add(bean);
			}
			System.out.println("List: "+map);
		}else {

		}
		return map; 
	}

	
	@RequestMapping(value="/getAchievementMovementDetails", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<PhysicalAchievementBean>> getAchievementMovementDetails(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="achievementid") Integer achievementid)
	{
		ModelAndView mav = new ModelAndView();
		List<PhysicalAchievementBean> list = new ArrayList<PhysicalAchievementBean>();
		LinkedHashMap<String, List<PhysicalAchievementBean>> map = new LinkedHashMap<String, List<PhysicalAchievementBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=physicalAchievementService.getAchievementMovementDetails(achievementid);
			List<PhysicalAchievementBean> sublist = new ArrayList<PhysicalAchievementBean>();
			if ((list != null) && (list.size() > 0)) {
				for (PhysicalAchievementBean row : list){
					sublist = new ArrayList<PhysicalAchievementBean>();
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
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return map; 
	}
	
	
	@RequestMapping(value="/getMinumFinYear", method = RequestMethod.POST)
	@ResponseBody
	public String getMinumFinYear(HttpServletRequest request,@RequestParam(value ="pCode") Integer pCode)
	{
		
		String pyear="";
		session = request.getSession(true);
		System.out.println();
		if(session!=null && session.getAttribute("loginID")!=null) {
	//	physicalAchievementService.saveAssetAchievement(assetAcheivement,finYr,monthId);
		pyear=physicalAchievementService.getMinumFinYear(pCode);
		}
		return pyear;
		
	}
	
	
	
}
