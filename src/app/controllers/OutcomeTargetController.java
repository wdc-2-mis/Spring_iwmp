package app.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import app.bean.OutcomeTargetBean;
import app.model.master.WdcpmksyMOutcome;
import app.model.master.WdcpmksyMOutcomeDetail;
import app.model.project.WdcpmksyOutcomeTargetDetails;
import app.model.project.WdcpmksyOutcomeTargetTranx;
import app.service.OutcomeTargetService;
import app.service.PhysicalAchievementService;
import app.service.ProjectMasterService;

@Controller("outcomeTargetController")
public class OutcomeTargetController {
	
	HttpSession session=null;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	OutcomeTargetService outcomeTargetService;
	
	@Autowired(required = true)
	PhysicalAchievementService physicalAchievementService;
	
	@RequestMapping(value="/addOutcomeTarget", method = RequestMethod.GET)   //  addBaselineNewDetComp
	public ModelAndView outcomeTarget(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("addOutcomeTarget");
		    mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getActivityDetail", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,List<String>> getActivityDetail(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer pCode,@RequestParam(value ="finYr") Integer finYr)
	{
		session = request.getSession(true);
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		List<String> sublist = new ArrayList<String>();
		BigDecimal target=BigDecimal.ZERO;
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			List<WdcpmksyMOutcome> list =outcomeTargetService.getActivityDetail(finYr);
			for(WdcpmksyMOutcome mout : list) {
				if(mout.getWdcpmksyMOutcomeDetails().size()>0) {
				for(WdcpmksyMOutcomeDetail outDetails: mout.getWdcpmksyMOutcomeDetails())
				{
					if (!map.containsKey(""+outDetails.getWdcpmksyMOutcome().getOutcomeDesc())) {
						sublist = new ArrayList<String>();
						for(WdcpmksyOutcomeTargetDetails outTargetDetails : outDetails.getWdcpmksyMOutcome().getWdcpmksyOutcomeTargetDetailses()) {
							if(outDetails.getOutcomeDetailId()==outTargetDetails.getWdcpmksyMOutcomeDetail().getOutcomeDetailId() && outDetails.getWdcpmksyMOutcome().getOutcomeId()==outTargetDetails.getWdcpmksyMOutcome().getOutcomeId() && outTargetDetails.getWdcpmksyOutcomeTarget().getIwmpMFinYear().getFinYrCd()==finYr && outTargetDetails.getWdcpmksyOutcomeTarget().getIwmpMProject().getProjectId()==pCode ) {
								target=outTargetDetails.getTarget();
							}
						}
							sublist.add(outDetails.getOutcomeDetailId()+"#"+outDetails.getWdcpmksyMOutcome().getOutcomeId()+"#"+outDetails.getOutcomeDetailDesc()+"#"+target);
						
						map.put(""+outDetails.getWdcpmksyMOutcome().getOutcomeDesc(), sublist);
					} else {
						
						sublist=(map.get(""+outDetails.getWdcpmksyMOutcome().getOutcomeDesc()));
						for(WdcpmksyOutcomeTargetDetails outTargetDetails : outDetails.getWdcpmksyMOutcome().getWdcpmksyOutcomeTargetDetailses()) {
							if(outDetails.getOutcomeDetailId()==outTargetDetails.getWdcpmksyMOutcomeDetail().getOutcomeDetailId() && outDetails.getWdcpmksyMOutcome().getOutcomeId()==outTargetDetails.getWdcpmksyMOutcome().getOutcomeId() && outTargetDetails.getWdcpmksyOutcomeTarget().getIwmpMFinYear().getFinYrCd()==finYr && outTargetDetails.getWdcpmksyOutcomeTarget().getIwmpMProject().getProjectId()==pCode)
								target=outTargetDetails.getTarget();		
						}
						sublist.add(outDetails.getOutcomeDetailId()+"#"+outDetails.getWdcpmksyMOutcome().getOutcomeId()+"#"+outDetails.getOutcomeDetailDesc()+"#"+target);
						map.put(""+outDetails.getWdcpmksyMOutcome().getOutcomeDesc(), sublist);
					}
					
				}
				}else {
					sublist = new ArrayList<String>();
					for(WdcpmksyOutcomeTargetDetails outTargetDetails : mout.getWdcpmksyOutcomeTargetDetailses()) {
						if(mout.getOutcomeId()==outTargetDetails.getWdcpmksyMOutcome().getOutcomeId() && outTargetDetails.getWdcpmksyOutcomeTarget().getIwmpMFinYear().getFinYrCd()==finYr && outTargetDetails.getWdcpmksyOutcomeTarget().getIwmpMProject().getProjectId()==pCode && !sublist.contains("#"+mout.getOutcomeId()+"#"+""+"#"+outTargetDetails.getTarget()))
							target=outTargetDetails.getTarget();
					}
					sublist.add("#"+mout.getOutcomeId()+"#"+""+"#"+target);	
					map.put(""+mout.getOutcomeDesc(), sublist);
				}
				
			}
		}else {
			map = new LinkedHashMap<String,List<String>>();
		}
		return map; 
	}
	
	@RequestMapping(value="/saveAsDraftOutcomeTarget", method = RequestMethod.POST)
	@ResponseBody
	public String saveAsDraftOutcomeTarget(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer pCode,@RequestParam(value ="finYr") Integer finYr,@RequestParam(value ="outcome") String outcomeid,@RequestParam(value ="outcomedetail") String outcomedetailid,@RequestParam(value ="target") String target)
	{
		session = request.getSession(true);
		String res="fail";
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			System.out.println("----------->"+pCode+"#"+finYr+"#"+outcomeid+"#"+outcomedetailid+"#"+target);
			String createdby = session.getAttribute("loginID").toString();
			res=outcomeTargetService.saveAsDraftOutcomeTarget(pCode,finYr,outcomeid,outcomedetailid,target,createdby);
		}else {
			res="fail";
		}
		return res; 
	}
	
	@RequestMapping(value="/getUserToForwardForOutcome", method = RequestMethod.POST)
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
			if(val.equalsIgnoreCase("Y"))
			res=outcomeTargetService.getUserToForward(regId,userType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/forwardOutcomeTargetFromPIA", method = RequestMethod.POST)
	@ResponseBody
	public String forwardOutcomeTargetFromPIA(HttpServletRequest request,@RequestParam(value ="projId") Integer pCode,@RequestParam(value ="finYr") Integer finYr,@RequestParam(value ="outcome") String outcomeid,@RequestParam(value ="outcomedetail") String outcomedetailid,@RequestParam(value ="target") String target,@RequestParam(value ="sentTo") Integer sentTo)
	{
		
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			String createdBy = session.getAttribute("loginID").toString();
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType = session.getAttribute("userType").toString();
			Character action='F';
			if(sentTo==null)
			for (Map.Entry<Integer, String> entry : outcomeTargetService.getUserToForward(sentFrom,userType).entrySet()) {
				Integer key = entry.getKey();
				sentTo = key;
				}
			
		//System.out.println(pCode+" : act "+activityCode+" : ach "+ach+" : yr "+finYr+" : month "+monthId+" : SentFrom "+sentFrom+" : SentTo "+sentTo);
		res=outcomeTargetService.forwardOutcomeTargetFromPIA(pCode,finYr,outcomeid,outcomedetailid,target,sentFrom,sentTo,createdBy);
		}
		return res;
		
	}
	
	@RequestMapping(value="/getForwardedOutcome", method = RequestMethod.POST)
	@ResponseBody
	public String getForwardedOutcome(HttpServletRequest request,@RequestParam(value ="projId") Integer pCode,@RequestParam(value ="finYr") Integer finYr)
	{
		
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
		List<WdcpmksyOutcomeTargetTranx> val=outcomeTargetService.getForwardedOutcome(pCode,finYr);
		if(!val.isEmpty() && val.get(0).getAction()=='R')
			res="Reject";
		else if(!val.isEmpty() && val.get(0).getAction()=='C')
			res="Complete";
		else if(!val.isEmpty() && val.get(0).getAction()=='F')
			res="Forward";
		}
		return res;
		
	}
	
	/***************************************************** WCDC/SLNA Work**********************************************************/
	
	@RequestMapping(value="/outcomeTargetWCDCSLNA", method = RequestMethod.GET)
	public ModelAndView outcomeTargetWCDCSLNA(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("actionOnOutcomeTarget");
		    mav.addObject("projectList", outcomeTargetService.viewMovement(regId));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/checkForAlreadyForwardedOutcome", method = RequestMethod.POST)
	@ResponseBody
	public List<OutcomeTargetBean> checkForAlreadyForwardedOutcome(HttpServletRequest request,@RequestParam(value ="targetid") Integer targetid)
	{
		
		List<OutcomeTargetBean> list = new ArrayList<OutcomeTargetBean>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			list=outcomeTargetService.checkForAlreadyForwardedOutcome(targetid,sentFrom);
		}
		return list;
		
	}
	
	@RequestMapping(value="/actionByWCDCSLNA", method = RequestMethod.POST)
	@ResponseBody
	public String actionByWCDCSLNA(HttpServletRequest request,@RequestParam(value ="targetid") Integer targetid,@RequestParam(value ="action") Character action,@RequestParam(value ="remarks") String remarks)
	{
		
		String res = new String("fail");
		String value=null;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentFrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			Integer sentTo = 0;
				for (Map.Entry<Integer, String> entry : outcomeTargetService.getUserToForward(sentFrom,userType,action,targetid).entrySet()) {
					Integer key = entry.getKey();
					sentTo = key;
					}
		  //Integer sentto = physicalActionPlanService.getSentToByRegId(sentfrom,action,planid,userType);
		  System.out.println("Sento: "+sentTo); 
			 if(userType.equals("SL") && action.equals('F')) 
				 action='C';
			if(sentTo>0 && sentFrom>0)
			value=outcomeTargetService.actionByWCDCSLNA(sentTo,sentFrom,targetid,action,remarks,userType);
			if(value.equals("success") && userType.equals("SL")) {
				res="state";
			}
			if(value.equals("success") && userType.equals("DI")) {
				res="district";
			}
		}else {
			
		}
		return res; 
		
	}
	
	@RequestMapping(value="/viewOutcomeTargetMovement", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<OutcomeTargetBean>> viewOutcomeTargetMovement(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<OutcomeTargetBean> list = new ArrayList<OutcomeTargetBean>();
		LinkedHashMap<String, List<OutcomeTargetBean>> map = new LinkedHashMap<String, List<OutcomeTargetBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			list=outcomeTargetService.viewMovement(regId);
			List<OutcomeTargetBean> sublist = new ArrayList<OutcomeTargetBean>();
			if ((list != null) && (list.size() > 0)) {
				for (OutcomeTargetBean row : list){
					sublist = new ArrayList<OutcomeTargetBean>();
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
	
	@RequestMapping(value="/getOutcomeTargetDetails", method = RequestMethod.POST)
	@ResponseBody
	public List<OutcomeTargetBean> getOutcomeDetails(HttpServletRequest request,@RequestParam(value ="targetid") Integer targetid)
	{
		
		List<OutcomeTargetBean> list = new ArrayList<OutcomeTargetBean>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=outcomeTargetService.getOutcomeDetails(targetid);
		}
		return list;
		
	}
	
	@RequestMapping(value="/getOutcomeTargetMovementDetails", method = RequestMethod.POST)
	@ResponseBody
	public List<OutcomeTargetBean> getOutcomeTargetMovementDetails(HttpServletRequest request,@RequestParam(value ="targetid") Integer targetid)
	{
		
		List<OutcomeTargetBean> list = new ArrayList<OutcomeTargetBean>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=outcomeTargetService.getOutcomeTargetMovementDetails(targetid);
		}
		return list;
		
	}

}
