package app.controllers.baseline;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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

import app.bean.AddOutcomeParaBean;
import app.bean.GroundWaterTableBean;
import app.bean.Login;
import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.service.FinYrServices;
import app.service.PhysicalActionPlanService;
import app.service.ProjectMasterService;
import app.service.master.OutcomeMasterServices;
import app.service.outcome.AdditionalBroughtFarmerCropAreaServices;

@Controller("AdditionalBroughtFarmerCropArea")
public class AdditionalBroughtFarmerCropArea {
	
	
	HttpSession session;

	String finyear;

	@Autowired(required = true)
	ProjectMasterService projectMasterService;

	@Autowired(required = true)
	OutcomeMasterServices outcomeserv;

	@Autowired
	AdditionalBroughtFarmerCropAreaServices Ser;

	@Autowired(required = true)
	FinYrServices finYrServices;

	@RequestMapping(value = "/getAdditionalBroughtFarmerCropArea", method = RequestMethod.GET)
	public ModelAndView getAdditionalBroughtFarmerCropArea(HttpServletRequest request) {
			
			ModelAndView mav = new ModelAndView();
			session = request.getSession(true);
			String project = request.getParameter("project");
			String month = request.getParameter("month");
			String financial = request.getParameter("financial");
		//	List<AdditionalBroughtFarmerCropAreaBean> draft = new ArrayList<AdditionalBroughtFarmerCropAreaBean>(); 
			List<AdditionalBroughtFarmerCropAreaBean> complete = new ArrayList<AdditionalBroughtFarmerCropAreaBean>();  
			HashMap<String,String> userType = new HashMap<String,String>();
			HashMap<String,String> halfyearmont = new HashMap<String,String>();
			if(session!=null && session.getAttribute("loginID")!=null) 
			{
				int regId = (int)session.getAttribute("regId");
				mav = new ModelAndView("baseline/additionalBroughtFarmerCropArea");
				
				if(project!=null && !project.equals("")) 
				{
					//draft =Ser.getAdditionalBroughtFarmerCropDraft(Integer.parseInt(project), Integer.parseInt(month), Integer.parseInt(financial));
					complete = Ser.getAdditionalBroughtFarmerCropComplete(Integer.parseInt(project));
					if(complete.isEmpty()) 
					{ 
						 complete =null; 
						 mav.addObject("completesize", 0);
					}
					else {
						mav.addObject("complete", complete);
						mav.addObject("completesize", complete.size());
					}
				} 
				
				java.util.Date date1 = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);
				int monthid = cal.get(Calendar.MONTH);
				
				userType.put("fy","Year-Wise");
				userType.put("m","Half-Yearly");
				if(monthid>=3 && monthid<=8)
				halfyearmont.put("9","April-September");
				
				if(monthid<=2 || monthid>=9)
				halfyearmont.put("3","October-March");
				
				mav.addObject("groupType",userType);
		    //  mav.addObject("financialYear", Ser.getFinYearonward22());
			//	mav.addObject("financialYear", Ser.getFinYearAdditional());
				mav.addObject("projectList",  projectMasterService.getProjectByRegId(regId));
			//	mav.addObject("monthList",  outcomeserv.getmonthcode());  01471
				
			//	mav.addObject("monthList",  halfyearmont);
				
				mav.addObject("project", project);
				mav.addObject("financial", financial);
				mav.addObject("month", month);
				
        	
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());

		}
		return mav; 
	}
	
	@RequestMapping(value = "/saveAdditionalBroughtFarmerCropArea", method = RequestMethod.POST)
	@ResponseBody
	public String saveAdditionalBroughtFarmerCropArea(HttpServletRequest request, HttpServletResponse response, @RequestParam("projId") Integer projId, @RequestParam("month") Integer month,
			@RequestParam("year") Integer year, @RequestParam("diversified") BigDecimal diversified, @RequestParam("chnagesingle") BigDecimal chnagesingle, @RequestParam("farmer") BigDecimal farmer,
			@RequestParam("changecorp") BigDecimal changecorp, @RequestParam("status") Character status, @RequestParam("additionalid") Integer additionalid, @RequestParam("atype") String atype,
			@RequestParam("pulses") BigDecimal pulses, @RequestParam("oilseeds") BigDecimal oilseeds)
	{
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) { 
			res  = Ser.saveAdditionalBroughtFarmerCropArea(projId, month, year, diversified, chnagesingle, farmer, changecorp, status, session.getAttribute("loginID").toString(), additionalid, atype, pulses, oilseeds);
		}
		return res;
	}
	
	@RequestMapping(value = "/completeAdditionalBroughtFarmerCropArea", method = RequestMethod.POST)
	@ResponseBody
	public String completeAdditionalBroughtFarmerCropArea(HttpServletRequest request, HttpServletResponse response, @RequestParam("projId") Integer projId, @RequestParam("month") Integer month,
			@RequestParam("year") Integer year, @RequestParam("status") Character status, @RequestParam("additionalid") Integer additionalid)
	{
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) { 
			res  = Ser.completeAdditionalBroughtFarmerCropArea(projId, month, year, status, session.getAttribute("loginID").toString(), additionalid);
		}
		return res;
	}
	
	@RequestMapping(value="/getAdditionalBroughtYearDraft", method = RequestMethod.POST)
	@ResponseBody
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtYearDraft(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="group") String atline, @RequestParam(value ="project") Integer project,
			@RequestParam(value ="fyear") Integer fyear, @RequestParam(value ="mont") Integer month)
	{
		String res="fail";
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		List<AdditionalBroughtFarmerCropAreaBean> list=new  ArrayList<AdditionalBroughtFarmerCropAreaBean>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			list=Ser.getAdditionalBroughtFarmerCropAreaDraft(project, atline, fyear, month);
		}
		return list; 
	}
	
	@RequestMapping(value="/getAdditionalBroughtMonthComplt", method = RequestMethod.POST)
	@ResponseBody
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtMonthComplt(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="group") String atline, @RequestParam(value ="project") Integer project,
			@RequestParam(value ="fyear") Integer fyear, @RequestParam(value ="mont") Integer month)
	{
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		List<AdditionalBroughtFarmerCropAreaBean> list=new  ArrayList<AdditionalBroughtFarmerCropAreaBean>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			list=Ser.getAdditionalBroughtMonthComplt(project, atline, fyear, month);
		}
		
		return list; 
	}
	
	@RequestMapping(value="/getAdditionalBroughtYearComplt", method = RequestMethod.POST)
	@ResponseBody
	public List<AdditionalBroughtFarmerCropAreaBean> getAdditionalBroughtYearComplt(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="group") String atline, @RequestParam(value ="project") Integer project,
			@RequestParam(value ="fyear") Integer fyear)
	{
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		List<AdditionalBroughtFarmerCropAreaBean> list=new  ArrayList<AdditionalBroughtFarmerCropAreaBean>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			list=Ser.getAdditionalBroughtYearComplt(project, atline, fyear);
		}
		
		return list; 
	}
	
	@RequestMapping(value = "/getRemainYear", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String > getRemainYear(HttpServletRequest request, @RequestParam("project") Integer project,
			@RequestParam(value ="group") String atline) {
		
		return Ser.getFinYearAdditional(project, atline);
	}
	
	@RequestMapping(value = "/getRemainYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String > getRemainYearMonth(HttpServletRequest request, @RequestParam("project") Integer project,
			@RequestParam(value ="group") String atline, @RequestParam("fyear") Integer fyear ) {
		
		LinkedHashMap<Integer, String> halfyearmont = new LinkedHashMap<Integer, String>();
		java.util.Date date1 = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int monthid = cal.get(Calendar.MONTH);
		
		if(fyear==23 || fyear==22) {
			halfyearmont.put(3,"October-March");
		}
		else {				 
			if(monthid>=3 && monthid<=9)
				halfyearmont.put(9,"April-September");
							
			if(monthid<=3 || monthid>=9)
				halfyearmont.put(3,"October-March");
		}
		
		return halfyearmont;
	}



}
