package app.controllers;

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

import app.bean.Login;
import app.bean.OutcomeAchievementBean;
import app.bean.OutcomeTargetBean;
import app.bean.PhysicalAchievementBean;
import app.service.OutcomeAchievementService;
import app.service.ProjectMasterService;

@Controller("outcomeAchievementController")
public class OutcomeAchievementController {
	
	HttpSession session=null;
	
	private String pattern = "dd-MM-yyyy";
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	OutcomeAchievementService outcomeAchievementService;
	
	@RequestMapping(value="/addOutcomeAchievement", method = RequestMethod.GET)
	public ModelAndView addOutcomeAchievement(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("addOutcomeAchievement");
		    mav.addObject("projectList", outcomeAchievementService.getProjectByRegIdForOutcomeAchievement(regId));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getActivityWithTargetForOutcomeAchievement", method = RequestMethod.POST)
	@ResponseBody
	public List<OutcomeAchievementBean> getActivityWithTarget(HttpServletRequest request,@RequestParam(value ="pCode") Integer pCode)
	{
		LinkedHashMap<Integer,List<OutcomeAchievementBean>> map = new LinkedHashMap<Integer,List<OutcomeAchievementBean>>();
		OutcomeAchievementBean bean = new OutcomeAchievementBean();
		List<OutcomeAchievementBean> sublist = new ArrayList<OutcomeAchievementBean>();
		session = request.getSession(true);
		List<OutcomeAchievementBean> list = new ArrayList<OutcomeAchievementBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer finYr=0;
			Integer monthId=0;
			String[] strArr =null;
			String listYr = outcomeAchievementService.getFinancialYearForOutcomeAchievement(pCode);
			List<String> yrMonth = outcomeAchievementService.getCurrentFinYrMonthId(pCode,listYr==null?"":listYr);
			if(!yrMonth.get(0).split("-")[0].equals("na")) {
			strArr = yrMonth.get(0).split("-");	
			finYr=Integer.parseInt(strArr[0]);
			monthId=Integer.parseInt(strArr[1]);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Calendar.MONTH, monthId-1);
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
		
		for(OutcomeAchievementBean b : outcomeAchievementService.getActivityWithTarget(pCode,finYr,monthId)) {
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
		System.out.println(list.size());
		
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
	
	@RequestMapping(value="/forwardOutcomeAchievementFromPIA", method = RequestMethod.POST)
	@ResponseBody
	public String saveAchievementAsDraft(HttpServletRequest request,@RequestParam(value ="pCode") Integer pCode,@RequestParam(value ="head") String headCode,@RequestParam(value ="activity") String activityCode,@RequestParam(value ="ach") String ach,@RequestParam(value ="sentTo") Integer sentTo)
	{
		System.out.println("pCode: "+pCode+"head: "+headCode+" activity: "+activityCode+" ach: "+ach +" sentTo: "+sentTo);
		String res="";
		Integer finYr=0;
		Integer monthId=0;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			String createdBy = session.getAttribute("loginID").toString();
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType = session.getAttribute("userType").toString();
			Character action='F';
			Integer achid=0;		
			if(sentTo==null)
			for (Map.Entry<Integer, String> entry : outcomeAchievementService.getUserToForward(sentFrom,userType,action,achid).entrySet()) {
				Integer key = entry.getKey();
				sentTo = key;
				}
			
			
		String listYr = outcomeAchievementService.getFinancialYearForOutcomeAchievement(pCode);
		List<String> yrMonth = outcomeAchievementService.getCurrentFinYrMonthId(pCode,listYr);
		String[] strArr = yrMonth.get(0).split("-");	
		finYr=Integer.parseInt(strArr[0]);
		monthId=Integer.parseInt(strArr[1]);
		//System.out.println(pCode+" : act "+activityCode+" : ach "+ach+" : yr "+finYr+" : month "+monthId+" : SentFrom "+sentFrom+" : SentTo "+sentTo);
		res=outcomeAchievementService.saveAchievementAsDraft(pCode,headCode,activityCode,ach,finYr,monthId,sentFrom,sentTo,createdBy);
		}
		return res;
		
	}
	

	@RequestMapping(value="/outcomeAchievementWCDCSLNA", method = RequestMethod.GET)
	public ModelAndView outcomeAchievementWCDCSLNA(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("actionOnOutcomeAchievement");
		    mav.addObject("projectList", outcomeAchievementService.viewMovement(regId));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/checkForAlreadyForwardedOutcomeAchievement", method = RequestMethod.POST)
	@ResponseBody
	public List<OutcomeAchievementBean> checkForAlreadyForwardedOutcomeAchievement(HttpServletRequest request,@RequestParam(value ="achid") Integer achid)
	{
		
		List<OutcomeAchievementBean> list = new ArrayList<OutcomeAchievementBean>();
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			list=outcomeAchievementService.checkForAlreadyForwardedOutcomeAchievement(achid,sentFrom);
		}
		return list;
		
	}
	
	@RequestMapping(value="/forwardOutcomeAchievementByWCDCSLNA", method = RequestMethod.POST)
	@ResponseBody
	public String forwardOutcomeAchievementByWCDCSLNA(HttpServletRequest request,@RequestParam(value ="achid") Integer achid,@RequestParam(value ="action") Character action,@RequestParam(value ="remarks") String remarks)
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
					for (Map.Entry<Integer, String> entry : outcomeAchievementService.getUserToForward(sentFrom,userType,action,achid).entrySet()) {
						Integer key = entry.getKey();
						sentTo = key;
						}
			  //Integer sentto = physicalActionPlanService.getSentToByRegId(sentfrom,action,planid,userType);
			  System.out.println("Sento: "+sentTo); 
				 if(userType.equals("SL") && action.equals('F')) 
					 action='C';
				 
			  if(sentTo>0 && sentFrom>0)
			  value=outcomeAchievementService.forwardOutcomeAchievementByWCDCSLNA(sentTo,sentFrom,achid,action, remarks,userType);
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
	
	
	@RequestMapping(value="/viewOutcomeAchievementMovement", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<OutcomeAchievementBean>> viewOutcomeAchievementMovement(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<OutcomeAchievementBean> list = new ArrayList<OutcomeAchievementBean>();
		LinkedHashMap<String, List<OutcomeAchievementBean>> map = new LinkedHashMap<String, List<OutcomeAchievementBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			list=outcomeAchievementService.viewMovement(regId);
			List<OutcomeAchievementBean> sublist = new ArrayList<OutcomeAchievementBean>();
			if ((list != null) && (list.size() > 0)) {
				for (OutcomeAchievementBean row : list){
					sublist = new ArrayList<OutcomeAchievementBean>();
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
	
	@RequestMapping(value="/getOutcomeAchievementDetails", method = RequestMethod.POST)
	@ResponseBody
	public List<OutcomeAchievementBean> getOutcomeAchievementDetails(HttpServletRequest request,@RequestParam(value ="achId") Integer achId)
	{
		
		List<OutcomeAchievementBean> list = new ArrayList<OutcomeAchievementBean>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=outcomeAchievementService.getOutcomeAchievementDetails(achId);
		}
		return list;
		
	}
	
	@RequestMapping(value="/getOutcomeAchievementMovementDetails", method = RequestMethod.POST)
	@ResponseBody
	public List<OutcomeAchievementBean> getOutcomeAchievementMovementDetails(HttpServletRequest request,@RequestParam(value ="achId") Integer achId)
	{
		
		List<OutcomeAchievementBean> list = new ArrayList<OutcomeAchievementBean>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=outcomeAchievementService.getOutcomeAchievementMovementDetails(achId);
		}
		return list;
		
	}
	
}
