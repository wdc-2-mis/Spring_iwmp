package app.controllers.outcome;

import java.math.BigDecimal;
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

import app.bean.BaselineUpdateAchievementBean;
import app.bean.Login;
import app.bean.PhysicalAchievementBean;
import app.model.project.WdcpmksyBaselineupdateAchievementDetail;
import app.model.project.WdcpmksyProjectPhysicalAchievementDetails;
import app.service.PhysicalAchievementService;
import app.service.ProjectMasterService;
import app.service.outcome.BaselineUpdateAchievementService;

@Controller("BaseLineUpdateAchievementController")
public class BaseLineUpdateAchievementController {
	
	HttpSession session=null;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	PhysicalAchievementService Service;
	
	@Autowired(required = true)
	BaselineUpdateAchievementService ser;
	
	private String pattern = "dd-MM-yyyy";
	private String patternk = "yyyy-MM-dd";
	
	@RequestMapping(value="/baseLineAchievement", method = RequestMethod.GET)
	public ModelAndView addPhysicalAchievement(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("baseline/addBaseLineAchievement");
		//    mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
		    mav.addObject("projectList", ser.getProjectByRegIdBaseline(regId));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getActivityWithBaselineORasOn", method = RequestMethod.POST)
	@ResponseBody
	public List<BaselineUpdateAchievementBean> getActivityWithTarget(HttpServletRequest request,@RequestParam(value ="pCode") Integer pCode)
	{
	//	LinkedHashMap<Integer,List<BaselineUpdateAchievementBean>> map = new LinkedHashMap<Integer,List<BaselineUpdateAchievementBean>>();
	//	BaselineUpdateAchievementBean bean = new BaselineUpdateAchievementBean();
	//	List<BaselineUpdateAchievementBean> sublist = new ArrayList<BaselineUpdateAchievementBean>();
		session = request.getSession(true);
		session.setAttribute("endDate", null);
		List<BaselineUpdateAchievementBean> list = new ArrayList<BaselineUpdateAchievementBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer finYr=0;
			Integer monthId=0;
			String[] strArr =null;
			List<String> yrMonth = new ArrayList<String>();
			String listYr = ser.getFinancialYearForPlan(pCode);
		
			if(listYr!=null) 
			{
				yrMonth = ser.getCurrentFinYrMonthId(pCode,listYr);
				strArr = yrMonth.get(0).split("-");
			
				if(yrMonth.size()>0 && !yrMonth.get(0).split("-")[0].equals("na")) 
				{
					finYr=Integer.parseInt(strArr[0]);
					if(Integer.parseInt(strArr[1])==13) 
					{
						monthId=1;
					}
					else if(Integer.parseInt(strArr[1])==14) 
					{
						monthId=2;
					}
					else if(Integer.parseInt(strArr[1])==15) 
					{
						monthId=3;
					}
					else
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
			for(BaselineUpdateAchievementBean b : ser.getActivityWithTarget(pCode,finYr,monthId)) 
			{
				if(!yrMonth.get(0).split("-")[0].equals("na")) 
				{
					b.setStartdate(sDate);
					b.setEnddate(eDate);
				}
				else {
					b.setStartdate(null);
					b.setEnddate(null);
				}
				b.setStartmonth(monthId);
				b.setFinyr(finYr);
				list.add(b);
			}
		}
		else {
			
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
	
	@RequestMapping(value="/forwardBaselineAchievementFromPIAtoWCDC", method = RequestMethod.POST)
	@ResponseBody
	public String forwardBaselineAchievementFromPIAtoWCDC(HttpServletRequest request, @RequestParam(value ="pCode") Integer pCode, @RequestParam(value ="sentTo") String sentTo, @RequestParam(value ="blsconf") boolean blsconf,
			@RequestParam(value ="gross") BigDecimal gross,@RequestParam(value ="grossachiv") BigDecimal grossachiv,@RequestParam(value ="total") BigDecimal total,@RequestParam(value ="totalachiv") BigDecimal totalachiv,
			@RequestParam(value ="cultivable") BigDecimal cultivable,@RequestParam(value ="cultivableachiv") BigDecimal cultivableachiv,@RequestParam(value ="degraded") BigDecimal degraded,
			@RequestParam(value ="degradedachiv") BigDecimal degradedachiv,@RequestParam(value ="rainfed") BigDecimal rainfed,@RequestParam(value ="rainfedachiv") BigDecimal rainfedachiv,@RequestParam(value ="others") BigDecimal others,
			@RequestParam(value ="othersachiv") BigDecimal othersachiv,@RequestParam(value ="protective") BigDecimal protective,@RequestParam(value ="protectiveachiv") BigDecimal protectiveachiv,
			@RequestParam(value ="assured") BigDecimal assured,@RequestParam(value ="assuredachiv") BigDecimal assuredachiv,@RequestParam(value ="noirri") BigDecimal noirri,@RequestParam(value ="noirriachiv") BigDecimal noirriachiv,
			@RequestParam(value ="single") BigDecimal single,@RequestParam(value ="singleachiv") BigDecimal singleachiv,@RequestParam(value ="double") BigDecimal doublec,@RequestParam(value ="doubleachiv") BigDecimal doubleachiv,
			@RequestParam(value ="multiple") BigDecimal multiple,@RequestParam(value ="multipleachiv") BigDecimal multipleachiv,@RequestParam(value ="nocrop") BigDecimal nocrop,@RequestParam(value ="nocropachiv") BigDecimal nocropachiv)
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
			for (Map.Entry<Integer, String> entry : ser.getUserToForwardkd(sentFrom,userType,action,achid).entrySet()) {
				Integer key = entry.getKey();
				sentto1 = key;
			//	System.out.println( "regidkd "+sentto1+ " key= "+key);
			}
			if(sentTo!="")
				sentto1 =Integer.parseInt(sentTo);
			
			String listYr = ser.getFinancialYearForPlan(pCode);
			List<String> yrMonth = ser.getCurrentFinYrMonthId(pCode,listYr);
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
			res=ser.saveBaselineAchievementAsDraft(pCode,finYr,monthId,sentFrom,sentto1,createdBy,blsconf,gross,grossachiv,total,totalachiv,cultivable,cultivableachiv,degraded,degradedachiv,rainfed,rainfedachiv,others,othersachiv,protective,protectiveachiv,
					assured,assuredachiv,noirri,noirriachiv,single,singleachiv,doublec,doubleachiv,multiple,multipleachiv,nocrop,nocropachiv);
		}
		return res;
		
	}
	
	@RequestMapping(value="/wcdcActionOnBaselineUpdateAchievement", method = RequestMethod.GET)
	public ModelAndView wcdcActionOnBaselineUpdateAchievement(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("baseline/actionOnBaselineUpdateAchievementWCDC");
		    mav.addObject("projectList", ser.viewAchievement(regId));//physicalAchievementService.getAchievementData(regId)
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/checkForAlreadyForwardedBaselineAchievement", method = RequestMethod.POST)
	@ResponseBody
	public List<BaselineUpdateAchievementBean> checkForAlreadyForwardedBaselineAchievement(HttpServletRequest request, @RequestParam(value ="achid") Integer achid)
	{ 
		
		List<BaselineUpdateAchievementBean> list = new ArrayList<BaselineUpdateAchievementBean>();
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			list=ser.checkAlreadyForwardedBaselinAchievement(achid,sentFrom);
		}
		return list;
		
	}
	
	@RequestMapping(value="/forwardBaselineUpdateAchievementWCDC", method = RequestMethod.POST)
	@ResponseBody
	public String forwardBaselineUpdateAchievementWCDC(HttpServletRequest request,@RequestParam(value ="achid") Integer achid,@RequestParam(value ="action") Character action,@RequestParam(value ="remarks") String remarks)
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
					for (Map.Entry<Integer, String> entry : ser.getUserToForwardkd(sentFrom,userType,action,achid).entrySet()) {
						Integer key = entry.getKey();
						sentTo = key;
						}
			  //Integer sentto = physicalActionPlanService.getSentToByRegId(sentfrom,action,planid,userType);
			  System.out.println("Sento: "+sentTo); 
				 if(userType.equals("SL") && action.equals('F')) 
					 action='C';
				 
			  if(sentTo>0 && sentFrom>0)
			  value=ser.forwardBaselineUpdateAchievementWCDC(sentTo,sentFrom,achid,action, remarks,userType);
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
	
	@RequestMapping(value="/viewBaselineAchMovement", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<BaselineUpdateAchievementBean>> viewBaselineAchMovement(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<BaselineUpdateAchievementBean> list = new ArrayList<BaselineUpdateAchievementBean>();
		LinkedHashMap<String, List<BaselineUpdateAchievementBean>> map = new LinkedHashMap<String, List<BaselineUpdateAchievementBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			list= ser.viewMovement(regId);
			List<BaselineUpdateAchievementBean> sublist = new ArrayList<BaselineUpdateAchievementBean>();
			if ((list != null) && (list.size() > 0)) {
				for (BaselineUpdateAchievementBean row : list){
					sublist = new ArrayList<BaselineUpdateAchievementBean>();
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
	
	@RequestMapping(value="/CompletedBaselineAchievement", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<BaselineUpdateAchievementBean>> CompletedAchPlan(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<BaselineUpdateAchievementBean> list = new ArrayList<BaselineUpdateAchievementBean>();
		LinkedHashMap<String, List<BaselineUpdateAchievementBean>> map = new LinkedHashMap<String, List<BaselineUpdateAchievementBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			list= ser.viewCompletedBaselineAchievement(regId);
			List<BaselineUpdateAchievementBean> sublist = new ArrayList<BaselineUpdateAchievementBean>();
			if ((list != null) && (list.size() > 0)) {
				for (BaselineUpdateAchievementBean row : list){
					sublist = new ArrayList<BaselineUpdateAchievementBean>();
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
	
	
	@RequestMapping(value="/getBaselineupdateAchievementDetails", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<BaselineUpdateAchievementBean>> getBaselineupdateAchievementDetails(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="achievementid") Integer achievementid)
	{
		ModelAndView mav = new ModelAndView();
		BaselineUpdateAchievementBean bean = new BaselineUpdateAchievementBean();
		List<WdcpmksyBaselineupdateAchievementDetail> res = new ArrayList<WdcpmksyBaselineupdateAchievementDetail>();
		List<BaselineUpdateAchievementBean> lst = new ArrayList<BaselineUpdateAchievementBean>();
		LinkedHashMap<String, List<BaselineUpdateAchievementBean>> map = new LinkedHashMap<String, List<BaselineUpdateAchievementBean>>();
		Integer i=1;
		String status=null;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			res=ser.getBaselineupdateAchievementDetails(achievementid);
			
			
			 lst=ser.getBaselineupdateAchievementMovementDetails(achievementid); 
			 for(BaselineUpdateAchievementBean row : lst){
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
			
			List<BaselineUpdateAchievementBean> sublist = new ArrayList<BaselineUpdateAchievementBean>();
				if ((res != null) && (res.size() > 0)) {
					for(WdcpmksyBaselineupdateAchievementDetail list : res) {
						bean = new BaselineUpdateAchievementBean();
						
						bean.setCultivable_wasteland(list.getCultivableWasteland());
						bean.setCultivable_wasteland_achv(list.getCultivableWastelandAchv());
						bean.setDegraded_land(list.getDegradedLand());
						bean.setDegraded_land_achv(list.getDegradedLandAchv());
						bean.setRainfed(list.getRainfed());
						bean.setRainfed_achv(list.getRainfedAchv());
						bean.setOthers(list.getOther());
						bean.setOthers_achv(list.getOthersAchv());
						bean.setTotal_income(list.getTotalIncome());
						bean.setTotal_income_achv(list.getTotalIncomeAchv());
						bean.setGross_cropped_area(list.getGrossCroppedArea());
						bean.setGross_cropped_area_achv(list.getGrossCroppedAreaAchv());
						bean.setProtective_irrigation(list.getProtectiveIrrigation());
						bean.setProtective_irrigation_achv(list.getProtectiveIrrigationAchv());
						bean.setAssured_irrigation(list.getAssuredIrrigation());
						bean.setAssured_irrigation_achv(list.getAssuredIrrigationAchv());
						bean.setNo_irrigation(list.getNoIrrigation());
						bean.setNo_irrigation_achv(list.getNoIrrigationAchv());
					    bean.setNo_crop(list.getNoCrop());
					    bean.setNo_crop_achv(list.getNoCropAchv());
					    bean.setSingle_crop(list.getSingleCrop());
					    bean.setSingle_crop_achv(list.getSingleCropAchv());
					    bean.setDouble_crop(list.getDoubleCrop());
					    bean.setDouble_crop_achv(list.getDoubleCropAchv());
					    bean.setMultiple_crop(list.getMultipleCrop());
					    bean.setMultiple_crop_achv(list.getMultipleCropAchv());
						bean.setAchievementid(list.getAchievementDetailsId());
						bean.setProjectdesc(list.getWdcpmksyBaselineupdateAchievement().getIwmpMProject().getProjName());
						bean.setFinyrdesc(list.getWdcpmksyBaselineupdateAchievement().getIwmpMFinYear().getFinYrDesc());
						bean.setMonthdesc(list.getWdcpmksyBaselineupdateAchievement().getIwmpMMonth().getMonthName());
						if((status.contains("Pending") || status.contains("Rejected")) && list.getWdcpmksyBaselineupdateAchievement().getStatus().toString().equals("D")) {
							bean.setStatus(status);
						}if( status.contains("Completed") && list.getWdcpmksyBaselineupdateAchievement().getStatus().toString().equals("C")) { 
							bean.setStatus(status);
						}
					//	System.out.println("kd "+bean.getStatus()+ " month -"+bean.getMonthdesc());
						if (!map.containsKey("Baselineupdate")) {
							sublist = new ArrayList<BaselineUpdateAchievementBean>();
							sublist.add(bean);
							map.put("Baselineupdate", sublist);
						} else {
							sublist=(map.get("Baselineupdate"));
							sublist.add(bean);
							map.put("Baselineupdate", sublist);
						}
					
						
					}
				
				//beanList.add(bean);
			}
			System.out.println("List: "+map);
		}else {

		}
		return map; 
	}
	
	@RequestMapping(value="/getBaselineupdateAchievementMovementDetails", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<BaselineUpdateAchievementBean>> getAchievementMovementDetails(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="achievementid") Integer achievementid)
	{
		ModelAndView mav = new ModelAndView();
		List<BaselineUpdateAchievementBean> list = new ArrayList<BaselineUpdateAchievementBean>();
		LinkedHashMap<String, List<BaselineUpdateAchievementBean>> map = new LinkedHashMap<String, List<BaselineUpdateAchievementBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=ser.getBaselineupdateAchievementMovementDetails(achievementid);
			List<BaselineUpdateAchievementBean> sublist = new ArrayList<BaselineUpdateAchievementBean>();
			if ((list != null) && (list.size() > 0)) {
				for (BaselineUpdateAchievementBean row : list){
					sublist = new ArrayList<BaselineUpdateAchievementBean>();
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
	
	

}
