package app.controllers.baseline;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.AddOutcomeParaBean;
import app.bean.AssetIdBean;
import app.bean.GroundWaterTableBean;
import app.bean.Login;
import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.model.IwmpMProject;
import app.model.project.IwmpProjectPhysicalAssetTemp;
import app.model.project.WdcpmksyAdditionalBroughtFarmerCrop;
import app.service.DistrictMasterService;
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

	@Autowired
	DistrictMasterService districtMasterService;
	
	@RequestMapping(value = "/getAdditionalBroughtFarmerCropArea", method = RequestMethod.GET)
	public ModelAndView getAdditionalBroughtFarmerCropArea(HttpServletRequest request) {
			
			ModelAndView mav = new ModelAndView();
			session = request.getSession(true);
			String project = request.getParameter("project");
			String month = request.getParameter("month");
			String financial = request.getParameter("financial");
		//	List<AdditionalBroughtFarmerCropAreaBean> draft = new ArrayList<AdditionalBroughtFarmerCropAreaBean>(); 
			List<AdditionalBroughtFarmerCropAreaBean> complete = new ArrayList<AdditionalBroughtFarmerCropAreaBean>();  
			//HashMap<String,String> userType = new HashMap<String,String>();
			HashMap<String,String> halfyearmont = new HashMap<String,String>();
			LinkedHashMap<Integer, String> finYear = new LinkedHashMap<Integer, String>();
			if(session!=null && session.getAttribute("loginID")!=null) 
			{
				int regId = (int)session.getAttribute("regId");
				mav = new ModelAndView("baseline/additionalBroughtFarmerCropArea");
				
				if(project!=null && !project.equals("")) 
				{
					//draft =Ser.getAdditionalBroughtFarmerCropDraft(Integer.parseInt(project), Integer.parseInt(month), Integer.parseInt(financial));
					complete = Ser.getAdditionalBroughtFarmerCropComplete(Integer.parseInt(project));
					finYear = Ser.getFarmerCropFinYear();
					if(complete.isEmpty()) 
					{ 
						 complete =null; 
						 mav.addObject("completesize", 0);
					}
					else {
						mav.addObject("complete", complete);
						mav.addObject("completesize", complete.size());
					}
				    mav.addObject("finYear", finYear);
				} 
				int montid=Ser.getmonthidtoclosed();
				
				java.util.Date date1 = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);
				int monthid = cal.get(Calendar.MONTH);
				
				//userType.put("fy","Year-Wise");
				//userType.put("m","Half-Yearly");
				
				if(montid==9)
				if(monthid==9 && monthid<=8)
				halfyearmont.put("9","April-September");
				
				if(montid==3)
				if(monthid<=2 || monthid==3)
				halfyearmont.put("3","October-March");
				
				//mav.addObject("groupType",userType);
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
	
	
	@RequestMapping(value = "/getCompletedDataByProject", method = RequestMethod.GET)
	public void getCompletedDataByProject(@RequestParam("project") Integer projectId,
	                                      HttpServletRequest request,
	                                      HttpServletResponse response) throws ServletException, IOException {
	    List<AdditionalBroughtFarmerCropAreaBean> complete = Ser.getAdditionalBroughtFarmerCropComplete(projectId);
	    request.setAttribute("complete", complete);
	    request.setAttribute("completesize", complete.size());

	    // Forward request to JSP fragment
	    request.getRequestDispatcher("/WEB-INF/jsp/baseline/completeTable.jsp").forward(request, response);
	}

	
	@ResponseBody
	@RequestMapping(value = "/getFinYearByProject", method = RequestMethod.GET)
	public LinkedHashMap<Integer, String> getFinYearByProject(@RequestParam("project") int projectId) {
	    // Later if needed, you can filter years based on project
	    LinkedHashMap<Integer, String> finYear = Ser.getFarmerCropFinYear();
	    return finYear;
	}

	@ResponseBody
	@RequestMapping(value = "/getAchievementTypeByFinancialYear", method = RequestMethod.GET)
	public Map<String, Object> getAchievementTypeByFinancialYear(@RequestParam("financial") String financial) {
	    Map<String, Object> response = new HashMap<>();
	    List<Map<String, String>> types = new ArrayList<>();

	    try {
	        int yearPart = Integer.parseInt(financial);
	        if (yearPart >= 25) {
	            Map<String, String> option = new HashMap<>();
	            option.put("key", "fy");
	            option.put("value", "Year-Wise");
	            types.add(option);
	        } else {
	             Map<String, String> half = new HashMap<>();
	            half.put("key", "m");
	            half.put("value", "Half-Yearly");

	            Map<String, String> yearly = new HashMap<>();
	            yearly.put("key", "fy");
	            yearly.put("value", "Year-Wise");

	            types.add(half);
	            types.add(yearly);
	        }

	        response.put("achievementTypes", types);
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("achievementTypes", Collections.emptyList());
	    }

	    return response;
	}

	@ResponseBody
	@RequestMapping(value = "/getHalfYearPeriod", method = RequestMethod.GET)
	public Map<String, String> getHalfYearPeriod() {
	    Map<String, String> halfyearmont = new LinkedHashMap<>();

	    try {
	        int montid = Ser.getmonthidtoclosed();

	        java.util.Date date1 = new Date();
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date1);
	        int monthid = cal.get(Calendar.MONTH);

	        if (montid == 9) {
	            if (monthid == 9 && monthid <= 8) {
	                halfyearmont.put("9", "April - September");
	            }
	        }

	        if (montid == 3) {
	            if (monthid <= 2 || monthid == 3) {
	                halfyearmont.put("3", "October - March");
	            }
	        }

	        // If nothing matched, you can add both options as fallback
	        if (halfyearmont.isEmpty()) {
	            halfyearmont.put("9", "April - September");
	            halfyearmont.put("3", "October - March");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return halfyearmont;
	}

	@ResponseBody
	@RequestMapping(value = "/getFinancialYearPeriod", method = RequestMethod.GET)
	public Map<String, String> getFinancialYearPeriod(@RequestParam("financial") String financialYear) {

	    Map<String, String> response = new HashMap<>();
	    AdditionalBroughtFarmerCropAreaBean fy = Ser.findByYear(financialYear);

	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	    if (fy != null && fy.getStart_from() != null) {

	        response.put("start_from", sdf.format(fy.getStart_from()));

	        if (fy.getEnd_to() != null) {
	            response.put("end_to", sdf.format(fy.getEnd_to()));
	        } else {
	            response.put("end_to", "");   // end date not given
	        }

	    } else {
	        response.put("start_from", "");
	        response.put("end_to", "");
	    }

	    return response;
	}




	@RequestMapping(value = "/AdditionalBroughtFarmerforwardToSLNA", method = RequestMethod.POST)
	@ResponseBody
	public String AdditionalBroughtFarmerforwardToSLNA(HttpServletRequest request, HttpServletResponse response, @RequestParam("additionalid") Integer additionalid)
	{
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) { 
			res  = Ser.AdditionalBroughtFarmerSLNAforward(session.getAttribute("loginID").toString(), additionalid);
		}
		return res;
	}
	
	@RequestMapping(value="/slnaAdditionalBroughtFarmerCrop", method = RequestMethod.GET)
	public ModelAndView slnaAdditionalBroughtFarmerCrop(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		Integer stcode = Integer.parseInt(session.getAttribute("stateCode").toString());
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			 map = new LinkedHashMap<Integer,String>();
			 mav = new ModelAndView("baseline/slnaAddBroughtFarmerCropArea");
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			Integer stateCode = Integer.parseInt(session.getAttribute("stateCode").toString());
			mav.addObject("distList",districtMasterService.getDistrictByStateCodeWithDcode(stateCode));
		   
			LinkedHashMap<Integer, String> finYear = Ser.getFarmerCropFinYear();
		    List<AdditionalBroughtFarmerCropAreaBean> completeSLNA = Ser.getSLNACompleteAddBroughtFarmer(stcode);
		    request.setAttribute("completeSLNA", completeSLNA);
			mav.addObject("finYear", finYear); 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value = "/updateAdditionalFarmerCropStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateAdditionalFarmerCropStatus(@RequestParam("action") String action, @RequestParam("ids") List<Integer> ids, 
			@RequestParam(value = "remarks", required = false) List<String> remarks, HttpSession session) {

	    String userId = (String) session.getAttribute("userId");

	    try {
	        for (int i = 0; i < ids.size(); i++) {

	            String remark = null;
               if (remarks != null && remarks.size() > i) {
	                remark = remarks.get(i);
	            }

	            boolean updated = Ser.updateAdditionalFarmerCropStatus(ids.get(i), action, remark, userId);
	            System.out.println("Updated ID " + ids.get(i) + " : " + updated);
	            if (!updated) {
	                return "ERROR";
	            }
	        }
	        return "SUCCESS";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "ERROR";
	    }
	}



	
	@RequestMapping(value="/getPendingAdditionalFarmerCrop", method = RequestMethod.POST)
	@ResponseBody
	public List<AdditionalBroughtFarmerCropAreaBean> getPendingAdditionalFarmerCrop(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value ="projectId") Integer projectId, @RequestParam(value ="district") Integer district, @RequestParam(value ="year") Integer fyear)
	{
		session = request.getSession(true);
		List<AdditionalBroughtFarmerCropAreaBean> list = new  ArrayList<AdditionalBroughtFarmerCropAreaBean>();
		if(session!=null && session.getAttribute("loginID")!=null) { 
		list = Ser.getSLNAPendingAddFarmerCrop(projectId, district, fyear);
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/getAddBroughtFamerProjFromDistrict", method = RequestMethod.POST)
	public Map<Integer, String> getAddBroughtFamerProjFromDistrict(@RequestParam("dCode") int dCode) {
	    List<Object[]> list = Ser.getAddBroughtProjFromDist(dCode);
	    Map<Integer, String> projectMap = new LinkedHashMap<>();

	    for (Object[] obj : list) {
	        if (obj[0] != null && obj[1] != null) {
	            projectMap.put((Integer) obj[0], (String) obj[1]);
	        }
	    }
	    return projectMap; 
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
		
		int montid=Ser.getmonthidtoclosed();
		
		if(fyear==23 || fyear==22) {
			halfyearmont.put(3,"October-March");
		} 
		else {				 
			
			/*if(monthid>=3 && monthid<=9)
				halfyearmont.put(9,"April-September");
							
			if(monthid<=3 || monthid>=9)
				halfyearmont.put(3,"October-March");  */
			
			//commit when slna service start
			halfyearmont=projectMasterService.getAdditionalMonth();  
			
			//uncommit when slna service start
			/*if(montid==9)
				if(monthid>=8 && monthid<=9)
				halfyearmont.put(9,"April-September");
				
			if(montid==3)
				if(monthid>=2 || monthid<=3)
				halfyearmont.put(3,"October-March"); */
		}
		
		return halfyearmont;
	}



}
