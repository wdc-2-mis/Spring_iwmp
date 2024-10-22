package app.controllers;
import java.util.Optional;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import app.bean.Login;
import app.bean.PhysicalActionPlanBean;
import app.bean.PhysicalActionPlanTranxBean;
import app.common.CommonFunctions;
import app.model.IwmpMFinYear;
import app.model.UserMap;
import app.model.project.IwmpProjectPhysicalAap;
import app.model.project.IwmpProjectPhysicalTranx;
import app.service.FinancialYearMasterService;
import app.service.PhysicalActionPlanService;
import app.service.ProjectMasterService;
import app.service.StateMasterService;
import app.service.reports.PhysicalActionAchievementService;
import app.service.reports.TargetAchievementQuarterService;

@Controller("PhysicalActionPlanController")
public class PhysicalActionPlanController {
	
	HttpSession session;
	
	@Autowired
	ProjectMasterService pmservice;
	
	@Autowired
	FinancialYearMasterService fyservice;
	
	@Autowired
	PhysicalActionPlanService physicalActionPlanService;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@RequestMapping(value="/piaPhyActPlan", method = RequestMethod.GET)
	public ModelAndView getPhysicalActionPlan(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			mav = new ModelAndView("physicalActionPlan");
			mav.addObject("projectList",physicalActionPlanService.getProjectByRegId(regId));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/wcdcslnaPhyActPlan", method = RequestMethod.GET)
	public ModelAndView getPhysicalActionPlanForWCDCSLNA(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			System.out.println("value of regId:" +regId);
			mav = new ModelAndView("physicalActionPlanForWCDCSLNA");
			
			
			  mav.addObject("planList",physicalActionPlanService.viewARPlan(regId,session.
			  getAttribute("userType").toString()));
			 
			 
			 
			/* mav.addObject("projectList", viewMovement(request, response)); */
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getProjectForAnnualPlan", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getProjectByRegId(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			map.putAll(physicalActionPlanService.getProjectByRegId(regId));
		}else {
			map=null;
		}
		return map; 
	}
	
	@RequestMapping(value="/getFinYearProjectWise", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getFinYearProjectWise(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> yearMap = new LinkedHashMap<Integer,String>();
		yearMap.putAll(physicalActionPlanService.getFinYearProjectWise(projectId));
		return yearMap; 
	}
	
	@RequestMapping(value="/getFinYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getFinYearMonth(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,String> yearMap = new LinkedHashMap<Integer,String>();
		yearMap.putAll(physicalActionPlanService.getFinYearMonth());
		return yearMap; 
	}
	
	
	@RequestMapping(value="/getHead", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getHead(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			map=physicalActionPlanService.getHead();
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return map; 
	}
	
	@RequestMapping(value="/getActivity", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getActivity(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="headId") Integer headId)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		map=physicalActionPlanService.getActivity(headId);
		return map; 
	}
	
	@RequestMapping(value="/getUnit", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getUnit(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="activityId") Integer activityId)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			map=physicalActionPlanService.getUnit(activityId);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return map; 
	}
	
	@RequestMapping(value="/saveAsDraft", method = RequestMethod.POST)
	@ResponseBody
	public String saveAsDraft(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="plans") String plan,@RequestParam(value ="activities") String activity,@RequestParam(value ="yearcd") Integer yearcd,@RequestParam(value ="projectcd") Integer projectcd)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=physicalActionPlanService.saveAsDraft(plan,activity,projectcd,yearcd,session.getAttribute("loginID").toString());
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/getListofPhysicalActionPlan", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActionPlanBean> getListofPhysicalActionPlan(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="yearcd") Integer yearcd,@RequestParam(value ="projectcd") Integer projectcd)
	{
		session = request.getSession(true);
		LinkedHashMap<Integer, List<PhysicalActionPlanBean>> map = new LinkedHashMap<Integer, List<PhysicalActionPlanBean>>();
		ModelAndView mav = new ModelAndView();
		Integer i=0;
		PhysicalActionPlanBean bean = new PhysicalActionPlanBean();
		List<PhysicalActionPlanBean> beanList = new ArrayList<PhysicalActionPlanBean>();
		List<PhysicalActionPlanBean> res = new ArrayList<PhysicalActionPlanBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=physicalActionPlanService.getListofPhysicalActionPlan(projectcd,yearcd);
			
			map.put(0,res);
		}else {
			map.put(0,null);
		}
		return res; 
	}
	
	@RequestMapping(value="/deleteActivityFromAnnualActionPlan", method = RequestMethod.POST)
	@ResponseBody
	public String deleteActivityFromAnnualActionPlan(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="aapid") Integer aapid)
	{
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=physicalActionPlanService.deleteActivityFromAnnualActionPlan(aapid);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/getUserToForward", method = RequestMethod.POST)
	@ResponseBody
	public List<PhysicalActionPlanBean> getUserToForward(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<PhysicalActionPlanBean> res = new ArrayList<PhysicalActionPlanBean>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			res=physicalActionPlanService.getUserToForward(regId);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/forward", method = RequestMethod.POST)
	@ResponseBody
	public String forward(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="user") String sentto,
			@RequestParam(value ="yearcd") Integer yearcd,@RequestParam(value ="projectcd") Integer projectcd,
			@RequestParam(value ="remarks") String remarks)
	{
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		Integer sentto1=0;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			if(sentto.equals(null) || sentto=="") 
			{
				sentto="0";
				String userType="PI";
				Integer to = physicalActionPlanService.getSentToByRegId(sentfrom,'F',0,userType);
				if(to==null)
					sentto1=to;
			}
			else {
				sentto1=Integer.parseInt(sentto);
			}	
			res=physicalActionPlanService.forward(sentto1,yearcd,projectcd,sentfrom,remarks);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/forwardByWCDC", method = RequestMethod.POST)
	@ResponseBody
	public String forwardByWCDC(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="planid") Integer planid,@RequestParam(value ="action") Character action,@RequestParam(value ="remarks") String remarks)
	{
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		String value=null;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			Integer sentto = physicalActionPlanService.getSentToByRegId(sentfrom,action,planid,userType);
			System.out.println("Sento: "+sentto);
			if(sentto>0 && sentfrom>0)
			value=physicalActionPlanService.forwardByWCDC(sentto,sentfrom,planid,action,remarks,userType);
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
	
	@RequestMapping(value="/checkForAlreadyForwardedPlan", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,List<String>> checkForAlreadyForwardedPlan(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="yearcd") Integer yearcd,@RequestParam(value ="projectcd") Integer projectcd,@RequestParam(value ="planid") Integer planid)
	{
		ModelAndView mav = new ModelAndView();
		List<IwmpProjectPhysicalTranx> res = new ArrayList<IwmpProjectPhysicalTranx>();
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		String str = new String();
		List<String> list = new ArrayList<String>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId=Integer.parseInt(session.getAttribute("regId").toString());
			if(yearcd>0 && projectcd>0 && planid==0)
			res=physicalActionPlanService.checkForAlreadyForwardedPlan(yearcd,projectcd,regId);
			if(yearcd==0 && projectcd==0 && planid>0)
				res=physicalActionPlanService.checkForAlreadyForwardedPlan(planid,regId);
			for(IwmpProjectPhysicalTranx l: res) {
				if(l.getAction().equals('F') && l.getIwmpProjectPhysicalPlan().getStatus().toString().equals("D") && l.getIwmpUserRegBySentfrom().getRegId().equals(regId)) {
					map = new LinkedHashMap<String,List<String>>();
					for(UserMap umap:l.getIwmpUserRegBySentTo().getIwmpUserMaps()) {
					list = new ArrayList<String>();
					if(l.getIwmpUserRegBySentTo().getUserType().equals("DI")) {
						str="District";
						list.add("District");
						if(!session.getAttribute("userType").equals(l.getIwmpUserRegBySentfrom().getUserType()))
						list.add("show");
						else
							list.add("hide");
					}
					if(l.getIwmpUserRegBySentTo().getUserType().equals("SL")) {
						str="SLNA";
					list.add("SLNA");
					if(!session.getAttribute("userType").equals(l.getIwmpUserRegBySentfrom().getUserType()))
						list.add("show");
						else
							list.add("hide");
					}
					if(l.getIwmpUserRegBySentTo().getUserType().equals("PI")) {
						str="PIA";
						list.add("PIA");
						if(!session.getAttribute("userType").equals(l.getIwmpUserRegBySentfrom().getUserType()))
							list.add("show");
							else
								list.add("hide");
					}
				}
				map.put("ForwardedTo",list);
				}
				
				if(l.getAction().equals('R') && l.getIwmpProjectPhysicalPlan().getStatus().toString().equals("D") && l.getIwmpUserRegBySentTo().getRegId().equals(regId)) {
					map = new LinkedHashMap<String,List<String>>();
					for(UserMap umap:l.getIwmpUserRegBySentTo().getIwmpUserMaps()) {
						list = new ArrayList<String>();
						if(l.getIwmpUserRegBySentfrom().getUserType().equals("DI")) {
							str="District";
							list.add("District");
							if(session.getAttribute("userType").equals(l.getIwmpUserRegBySentTo().getUserType()))
							list.add("show");
						}
						if(l.getIwmpUserRegBySentfrom().getUserType().equals("SL")) {
							str="SLNA";
							list.add("SLNA");
							if(session.getAttribute("userType").equals(l.getIwmpUserRegBySentTo().getUserType()))
								list.add("show");
						}
						if(l.getIwmpUserRegBySentfrom().getUserType().equals("PI")) {
							str="PIA";
							list.add("PIA");
							if(session.getAttribute("userType").equals(l.getIwmpUserRegBySentTo().getUserType()))
								list.add("show");
						}
					}
					map.put("RejectedBy",list);
					}
				
				if(l.getIwmpProjectPhysicalPlan().getStatus().toString().equals("C")) {
					map = new LinkedHashMap<String,List<String>>();
					for(UserMap umap:l.getIwmpUserRegBySentTo().getIwmpUserMaps()) {
						list = new ArrayList<String>();
						if(l.getIwmpUserRegBySentfrom().getUserType().equals("DI")) {
							str="District";
							list.add("District");
							list.add("hide");
						}
						if(l.getIwmpUserRegBySentfrom().getUserType().equals("SL")) {
							str="SLNA";
						list.add("SLNA");
						list.add("hide");
						}
						if(l.getIwmpUserRegBySentfrom().getUserType().equals("PI")) {
							str="PIA";
						list.add("PIA");
						list.add("hide");
						}
					}
					map.put("ApprovedBy",list);
					}
			
			}
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return map; 
	}
	
	@RequestMapping(value="/viewMovement", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<PhysicalActionPlanTranxBean>> viewMovement(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<PhysicalActionPlanTranxBean> list = new ArrayList<PhysicalActionPlanTranxBean>();
		LinkedHashMap<String, List<PhysicalActionPlanTranxBean>> map = new LinkedHashMap<String, List<PhysicalActionPlanTranxBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=physicalActionPlanService.viewMovement(Integer.parseInt(session.getAttribute("regId").toString()),session.getAttribute("userType").toString());
			List<PhysicalActionPlanTranxBean> sublist = new ArrayList<PhysicalActionPlanTranxBean>();
			if ((list != null) && (list.size() > 0)) {
				for (PhysicalActionPlanTranxBean row : list){
					sublist = new ArrayList<PhysicalActionPlanTranxBean>();
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
	
	@RequestMapping(value="/viewARPlan", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<PhysicalActionPlanTranxBean>> viewARPlan(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<PhysicalActionPlanTranxBean> list = new ArrayList<PhysicalActionPlanTranxBean>();
		LinkedHashMap<String, List<PhysicalActionPlanTranxBean>> map = new LinkedHashMap<String, List<PhysicalActionPlanTranxBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=physicalActionPlanService.viewARPlan(Integer.parseInt(session.getAttribute("regId").toString()),session.getAttribute("userType").toString());
			List<PhysicalActionPlanTranxBean> sublist = new ArrayList<PhysicalActionPlanTranxBean>();
			if ((list != null) && (list.size() > 0)) {
				for (PhysicalActionPlanTranxBean row : list){
					sublist = new ArrayList<PhysicalActionPlanTranxBean>();
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
	
	@RequestMapping(value="/viewCompleteMovement", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<PhysicalActionPlanTranxBean>> viewCompleteMovement(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<PhysicalActionPlanTranxBean> list = new ArrayList<PhysicalActionPlanTranxBean>();
		LinkedHashMap<String, List<PhysicalActionPlanTranxBean>> map = new LinkedHashMap<String, List<PhysicalActionPlanTranxBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=physicalActionPlanService.viewCMovement(Integer.parseInt(session.getAttribute("regId").toString()),session.getAttribute("userType").toString());
			List<PhysicalActionPlanTranxBean> sublist = new ArrayList<PhysicalActionPlanTranxBean>();
			if ((list != null) && (list.size() > 0)) {
				for (PhysicalActionPlanTranxBean row : list){
					sublist = new ArrayList<PhysicalActionPlanTranxBean>();
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
	
	
	@RequestMapping(value="/getPlanMovementDetails", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<PhysicalActionPlanTranxBean>> getPlanMovementDetails(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="planid") Integer planid)
	{
		ModelAndView mav = new ModelAndView();
		List<PhysicalActionPlanTranxBean> list = new ArrayList<PhysicalActionPlanTranxBean>();
		LinkedHashMap<String, List<PhysicalActionPlanTranxBean>> map = new LinkedHashMap<String, List<PhysicalActionPlanTranxBean>>();
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			list=physicalActionPlanService.getPlanMovementDetails(planid);
			List<PhysicalActionPlanTranxBean> sublist = new ArrayList<PhysicalActionPlanTranxBean>();
			if ((list != null) && (list.size() > 0)) {
				for (PhysicalActionPlanTranxBean row : list){
					sublist = new ArrayList<PhysicalActionPlanTranxBean>();
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
	
	@RequestMapping(value="/getPlanDetails", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, List<PhysicalActionPlanBean>> getPlanDetails(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="planid") Integer planid)
	{
		ModelAndView mav = new ModelAndView();
		PhysicalActionPlanBean bean = new PhysicalActionPlanBean();
		List<IwmpProjectPhysicalAap> res = new ArrayList<IwmpProjectPhysicalAap>();
		List<PhysicalActionPlanTranxBean> lst = new ArrayList<PhysicalActionPlanTranxBean>();
		LinkedHashMap<String, List<PhysicalActionPlanBean>> map = new LinkedHashMap<String, List<PhysicalActionPlanBean>>();
		Integer i=1;
		String status=null;
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=physicalActionPlanService.getPlanDetails(planid);
			lst=physicalActionPlanService.getPlanMovementDetails(planid);
			for (PhysicalActionPlanTranxBean row : lst){
				
				if(i==1) {
				if(row.getAction().toString().equals("F") ) {
					status="Pending at "+row.getSentto();
				} if(row.getAction().toString().equals("R") ) {
					status="Rejected By " +row.getSentfrom();
				} if( row.getAction().toString().equals("C")) { 
					status="Completed";
				}
				i++;
				 }
				 
			}
			List<PhysicalActionPlanBean> sublist = new ArrayList<PhysicalActionPlanBean>();
				if ((res != null) && (res.size() > 0)) {
					for(IwmpProjectPhysicalAap list : res) {
						bean = new PhysicalActionPlanBean();
						bean.setActivitycode(list.getIwmpMPhyActivity().getActivityCode());
						bean.setActivityname(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getSeqNo()+"."+list.getIwmpMPhyActivity().getSeqNo()+". "+list.getIwmpMPhyActivity().getActivityDesc());
						bean.setHeadcode(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadCode());
						bean.setHeadname(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getSeqNo()+". "+list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc());
						bean.setUnitcode(list.getIwmpMPhyActivity().getIwmpMUnit().getUnitCode());
						bean.setUnitname(list.getIwmpMPhyActivity().getIwmpMUnit().getUnitDesc());
						bean.setPlan(list.getQtyPlanned());
						bean.setAapid(list.getAapid());
						bean.setProjectdesc(list.getIwmpProjectPhysicalPlan().getIwmpMProject().getProjName());
						bean.setYrdesc(list.getIwmpProjectPhysicalPlan().getIwmpMFinYear().getFinYrDesc());
						if((status.contains("Pending") || status.contains("Rejected")) && list.getIwmpProjectPhysicalPlan().getStatus().toString().equals("D")) {
							bean.setStatus(status);
						}if( status.contains("Completed") && list.getIwmpProjectPhysicalPlan().getStatus().toString().equals("C")) { 
							bean.setStatus(status);
						}
						if (!map.containsKey(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc())) {
							sublist = new ArrayList<PhysicalActionPlanBean>();
							sublist.add(bean);
							map.put(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc(), sublist);
						} else {
							sublist=(map.get(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc()));
							sublist.add(bean);
							map.put(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc(), sublist);
						}
					
						
					}
				
			}
			System.out.println("List: "+map);
		}else {

		}
		return map; 
	}
	
	@RequestMapping(value="/getRemarks", method = RequestMethod.POST)
	@ResponseBody
	public String getRemarks(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="yearcd") Integer yearcd,@RequestParam(value ="projectcd") Integer projectcd)
	{
		session = request.getSession(true);
		Integer i=0;
		String res="";
		if(session!=null && session.getAttribute("loginID")!=null) {
			List<IwmpProjectPhysicalTranx> tranx=physicalActionPlanService.getRemarks(projectcd,yearcd,Integer.parseInt(session.getAttribute("regId").toString()));
			for(IwmpProjectPhysicalTranx list : tranx) {
				if(i==0)
					res=list.getRemarks();
				i++;
			}
		}else {
			res="null";
		}
		return res; 
	}

	@RequestMapping(value="/yearWisePhyActPlan", method = RequestMethod.GET)
	public ModelAndView yearWisePhyActPlan(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>21).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		mav = new ModelAndView("reports/yearWisePhyActPlan");
		mav.addObject("yrList",finMap);
		mav.addObject("yrListSize",finMap.size());
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		return mav;
	}
	
	@RequestMapping(value="/getProjectPhysicalActionPlan", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getProjectPhysicalActionPlan(HttpServletRequest request,@RequestParam(value ="dCode") Integer dCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(physicalActionPlanService.getProjectPhysicalActionPlan(dCode));
		return map;
	}
	
	@RequestMapping(value="/getFromYearForPhysicalActionPlanReport", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getFromYearForPhysicalActionPlanReport(HttpServletRequest request,@RequestParam(value ="pCode") Integer pCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(physicalActionPlanService.getFromYearForPhysicalActionPlanReport(pCode));
		return map;
	}
	
	@RequestMapping(value="/getToYearForPhysicalActionPlanReport", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getToYearForPhysicalActionPlanReport(HttpServletRequest request,@RequestParam(value ="fromYear") Integer fromYear,@RequestParam(value ="projId") Integer projId)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(physicalActionPlanService.getToYearForPhysicalActionPlanReport(fromYear,projId));
		return map;
	}
	
	@RequestMapping(value="/viewHeadActivity", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,List<String>> viewHeadActivity(HttpServletRequest request)
	{
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String,List<String>>();
		map=physicalActionPlanService.viewHeadActivity();
		return map;
	}
	
	
	
	@RequestMapping(value="/yearWisePhyActPlan", method = RequestMethod.POST)
	public ModelAndView getPhysicalActionReport(HttpServletRequest request,@RequestParam(value ="state") Integer stCode,@RequestParam(value ="district") Integer distCode,@RequestParam(value ="project") Integer projId,@RequestParam(value ="fromYear") Integer fromYear,@RequestParam(value ="toYear") Integer toYear)
	{
		System.out.println("state: "+stCode+" district: "+distCode);
		ModelAndView mav = new ModelAndView("reports/yearWisePhyActPlan");
		
		Map<String,BigDecimal> hdActMap = new LinkedHashMap<>();
		Map<String,BigDecimal> subTotMap = new LinkedHashMap<>();
		List<PhysicalActionPlanBean> beanList = new ArrayList<PhysicalActionPlanBean>();
		List<PhysicalActionPlanBean> hdActList = new ArrayList<PhysicalActionPlanBean>();
		hdActList = physicalActionPlanService.getListofHeadAndActivityDesc();
		
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>=fromYear && s.getFinYrCd()<=toYear).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		
		hdActList.forEach(s->{
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				hdActMap.put(s.getActivitycode() + "," + map.getValue(), BigDecimal.ZERO);
				subTotMap.put(s.getHeadcode() + "," + map.getValue(), BigDecimal.ZERO);
			}
		});
		beanList=physicalActionPlanService.getPhysicalActionReport(stCode,distCode,projId,fromYear,toYear);
		for(PhysicalActionPlanBean bean : beanList) {
			if(hdActMap.containsKey(bean.getActivitycode()+","+bean.getYrdesc()))
				hdActMap.put(bean.getActivitycode()+","+bean.getYrdesc(), bean.getPlan()==null?BigDecimal.ZERO: bean.getPlan());
			if(subTotMap.containsKey(bean.getHeadcode()+","+bean.getYrdesc()))
				subTotMap.put(bean.getHeadcode()+","+bean.getYrdesc(), subTotMap.get(bean.getHeadcode()+","+bean.getYrdesc()).add(bean.getPlan()==null?BigDecimal.ZERO: bean.getPlan()));
		}
		
		
		 mav.addObject("dataList",hdActList);
		 mav.addObject("datalistsize", beanList.size());
		 mav.addObject("hdActMap",hdActMap);
		 mav.addObject("subTotMap",subTotMap);
		 mav.addObject("yrList",finMap);
		 mav.addObject("yrListSize",finMap.size());
		 mav.addObject("stateList",stateMasterService.getAllState());
		 mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		 mav.addObject("stCode",stCode);
		 mav.addObject("distCode",distCode);
		 mav.addObject("projectId",projId);
		 mav.addObject("fromYear",fromYear);
		 mav.addObject("toYear",toYear);
		 
		 
		return mav;
	}
	
	@RequestMapping(value = "/getyearWisePhyActPlanExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getyearWisePhyActPlanExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		int stCode= Integer.parseInt(request.getParameter("stCode"));
		int distCode= Integer.parseInt(request.getParameter("distCode"));
		int projId= Integer.parseInt(request.getParameter("projId"));
		int fromYear= Integer.parseInt(request.getParameter("fYear"));
		int toYear= Integer.parseInt(request.getParameter("tYear"));
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String frmYrName= request.getParameter("frmYrName");
		String toYrName= request.getParameter("toYrName");
		
		Map<String,BigDecimal> hdActMap = new LinkedHashMap<>();
		Map<String,BigDecimal> subTotMap = new LinkedHashMap<>();
		List<PhysicalActionPlanBean> beanList = new ArrayList<PhysicalActionPlanBean>();
		List<PhysicalActionPlanBean> hdActList = new ArrayList<PhysicalActionPlanBean>();
		hdActList = physicalActionPlanService.getListofHeadAndActivityDesc();
		
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>=fromYear && s.getFinYrCd()<=toYear).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		
		hdActList.forEach(s->{
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				hdActMap.put(s.getActivitycode() + "," + map.getValue(), BigDecimal.ZERO);
				subTotMap.put(s.getHeadcode() + "," + map.getValue(), BigDecimal.ZERO);
			}
		});
		beanList=physicalActionPlanService.getPhysicalActionReport(stCode,distCode,projId,fromYear,toYear);
		for(PhysicalActionPlanBean bean : beanList) {
			if(hdActMap.containsKey(bean.getActivitycode()+","+bean.getYrdesc()))
				hdActMap.put(bean.getActivitycode()+","+bean.getYrdesc(), bean.getPlan()==null?BigDecimal.ZERO: bean.getPlan());
			if(subTotMap.containsKey(bean.getHeadcode()+","+bean.getYrdesc()))
				subTotMap.put(bean.getHeadcode()+","+bean.getYrdesc(), subTotMap.get(bean.getHeadcode()+","+bean.getYrdesc()).add(bean.getPlan()==null?BigDecimal.ZERO: bean.getPlan()));
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report T1- Year-wise Details of Physical Action Plan Targets as per List of Activities");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report T1- State-wise, District-wise,Project-wise and Year-wise Details of Physical Action Plan Targets as per List of Activities";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, finMap.size()+2, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,finMap.size()+2);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        if(finMap.size()>1) {
	        mergedRegion = new CellRangeAddress(6,6,2,finMap.size()+1);
	        sheet.addMergedRegion(mergedRegion);
	        }
			mergedRegion = new CellRangeAddress(6,7,finMap.size()+2,finMap.size()+2);
	        sheet.addMergedRegion(mergedRegion);

	        
	        Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("State: "+stName+"  District: "+(distName.equalsIgnoreCase("--Select--")?"--ALL--":distName) + "  Project: "+ (projName.equalsIgnoreCase("--Select--")?"--ALL--":projName) + "  From Year: "+frmYrName + "  To Year: " +toYrName);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<finMap.size()+3;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
	        
			Row rowhead1 = sheet.createRow(6);
			cell = rowhead1.createCell(0);
			cell.setCellValue("Name of the Activity");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(1);
			cell.setCellValue("Unit");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(2);
			cell.setCellValue("Physical Target");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =3; i<finMap.size()+1;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			cell = rowhead1.createCell(finMap.size()+2);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<2;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
				
			int no = 2;
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				cell = rowhead2.createCell(no);
				cell.setCellValue(map.getValue()); 
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				no++;
		    }
			rowhead2.createCell(no).setCellStyle(style);
		
			Row rowhead3 = sheet.createRow(8);
			for(int i =0;i<finMap.size()+3;i++) {
				cell = rowhead3.createCell(i);
				cell.setCellValue(i+1);  
				cell.setCellStyle(style);
			}
			
	        int rowno  = 9;
	        int headVal = 0;
			int actCount = 1;
			double colTotal = 0;
			double total = 0;
	        
	        
	        Row row = sheet.createRow(rowno);
	        for(PhysicalActionPlanBean bean: hdActList) {
	        	if(bean.getHeadcode()!=headVal) {
	        		if(headVal!=0 && headVal<8) {
	        			rowno++;
	        			mergedRegion = new CellRangeAddress(rowno,rowno,0,1);
	        			sheet.addMergedRegion(mergedRegion);
	        			row = sheet.createRow(rowno);
	        			cell = row.createCell(0);
	        			cell.setCellValue("Sub-Total");
	        			cell.setCellStyle(style);
	        			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
	        			row.createCell(1).setCellStyle(style);
	        			int n = 2;
	    				for (Map.Entry<String,BigDecimal> tot : subTotMap.entrySet()) {
	    					String[] parts = tot.getKey().split(",");
	    					if(Integer.parseInt(parts[0])==headVal) {
	    						cell=row.createCell(n);
	    						cell.setCellValue(tot.getValue()==null?0.00:tot.getValue().doubleValue()); 
	    						cell.setCellStyle(style);
	    						n++;
	    					}
	    					cell=row.createCell(n);
	    					cell.setCellValue(total);
	    					cell.setCellStyle(style);
	    					
	    				}
	    				total=0;
	        			rowno++;
	        		}
	        		if(headVal==8)
	        		rowno++;
	        		row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(bean.getHeadcode()+". "+bean.getHeadname());
	        		headVal++;
	        		actCount = 1;
	        	}
	        	rowno++;
	        	row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(bean.getHeadcode()+"."+actCount+" "+bean.getActivityname());
	        	row.createCell(1).setCellValue(bean.getUnitname());
	        	
	        	int n = 2;
				for (Map.Entry<String,BigDecimal> actmap : hdActMap.entrySet()) {
					String[] parts = actmap.getKey().split(",");
					if(Integer.parseInt(parts[0])==bean.getActivitycode()) {
						row.createCell(n).setCellValue(actmap.getValue()==null?0.00:actmap.getValue().doubleValue()); 
						colTotal = colTotal + actmap.getValue().doubleValue();
						n++;
					}
					row.createCell(n).setCellValue(colTotal);
				}
				total = total + colTotal;
				colTotal=0;
				actCount++;
	        }
	        
	        CellStyle style1 = workbook.createCellStyle();
	        style1.setBorderTop(BorderStyle.THIN); 
			style1.setBorderBottom(BorderStyle.THIN);
			style1.setBorderLeft(BorderStyle.THIN);
			style1.setBorderRight(BorderStyle.THIN);
	        style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 12);
			font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion,hdActList.size()+16, finMap.size()+2);
	        String fileName = "attachment; filename=Report T1.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/yearWisePhyActPlan";
		
	}
	


		
		
		
	
@RequestMapping(value = "/downloadyrWisePhyActPlanPDF", method = RequestMethod.POST)
public ModelAndView downloadyrWisePhyActPlanPDF(HttpServletRequest request, HttpServletResponse response) 	{
	//WDC-PMKSY-0001113	
	int stCode= Integer.parseInt(request.getParameter("stCode"));	
	int distCode= Integer.parseInt(request.getParameter("distCode"));	
	int projId= Integer.parseInt(request.getParameter("projId"));	
	int fromYear= Integer.parseInt(request.getParameter("fYear"));	
	int toYear= Integer.parseInt(request.getParameter("toYear"));
	String stName= request.getParameter("stName");	
	String distName= request.getParameter("distName");
	String projName= request.getParameter("projName");
	String frmYrName= request.getParameter("frmYrName");
	String toYrName= request.getParameter("toYrName");
	
	Map<String,BigDecimal> hdActMap = new LinkedHashMap<>();
	Map<String,BigDecimal> subTotMap = new LinkedHashMap<>();
	List<PhysicalActionPlanBean> beanList = new ArrayList<PhysicalActionPlanBean>();
	List<PhysicalActionPlanBean> hdActList = new ArrayList<PhysicalActionPlanBean>();
	hdActList = physicalActionPlanService.getListofHeadAndActivityDesc();
	
	List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
	Map<Integer, String> finMap = new LinkedHashMap<>();
	finList.stream().filter(s-> s.getFinYrCd()>=fromYear && s.getFinYrCd()<=toYear).forEach(s->{
		finMap.put(s.getFinYrCd(), s.getFinYrDesc());
	});
	
	hdActList.forEach(s->{
		for (Map.Entry<Integer, String> map : finMap.entrySet()) {
			hdActMap.put(s.getActivitycode() + "," + map.getValue(), BigDecimal.ZERO);
			subTotMap.put(s.getHeadcode() + "," + map.getValue(), BigDecimal.ZERO);
		}
	});
	beanList=physicalActionPlanService.getPhysicalActionReport(stCode,distCode,projId,fromYear,toYear);
	for(PhysicalActionPlanBean bean : beanList) {
		if(hdActMap.containsKey(bean.getActivitycode()+","+bean.getYrdesc()))
			hdActMap.put(bean.getActivitycode()+","+bean.getYrdesc(), bean.getPlan()==null?BigDecimal.ZERO: bean.getPlan());
		if(subTotMap.containsKey(bean.getHeadcode()+","+bean.getYrdesc()))
			subTotMap.put(bean.getHeadcode()+","+bean.getYrdesc(), subTotMap.get(bean.getHeadcode()+","+bean.getYrdesc()).add(bean.getPlan()==null?BigDecimal.ZERO: bean.getPlan()));
	}
	
	try {	
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("monthwisePhyActPlanReport");	
		document.addCreationDate();			
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer=PdfWriter.getInstance(document, baos);
		document.open(); 	 
		Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
		Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD );
		Font bf8 = new Font(FontFamily.HELVETICA, 8);			
		Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
		Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);
		PdfPTable table = null;	
		document.newPage();	
		Paragraph paragraph3 = null; 
		Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
		paragraph3 = new Paragraph("Report T1- State-wise, District-wise,Project-wise and Year-wise Details of Physical Action Plan Targets as per List of Activities", f3);
		paragraph2.setAlignment(Element.ALIGN_CENTER);
		paragraph3.setAlignment(Element.ALIGN_CENTER);
		paragraph2.setSpacingAfter(10);
		paragraph3.setSpacingAfter(10);
		CommonFunctions.addHeader(document);
		document.add(paragraph2);	
		document.add(paragraph3);	
		if(beanList.size()!=0)
			table = new PdfPTable(finMap.size()+3);
			int[] arr = new int[finMap.size()+3];
			for(int i =0; i<finMap.size()+3;i++) {
				arr[i] = 5;
			}
		table.setWidths(arr);
		table.setWidthPercentage(70);	
		table.setWidthPercentage(100);	
		table.setSpacingBefore(0f);
		table.setSpacingAfter(0f);
		table.setHeaderRows(4);
		
		CommonFunctions.insertCellHeader(table, "State:  "+stName+"   District:  "+ (distName.equalsIgnoreCase("--Select--")?"--ALL--":distName) + "   Project:  "+ projName + "   From Year:  "+frmYrName +"   To Year:  "+toYrName, Element.ALIGN_LEFT, finMap.size()+3, 1, bf8Bold);
		
		CommonFunctions.insertCellHeader(table, "Name of the Activity ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Unit ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Physical Target", Element.ALIGN_CENTER, finMap.size(), 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Total ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
		
		for (Map.Entry<Integer, String> map : finMap.entrySet()) {
			CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
        }
		
		for(int i =1;i<finMap.size()+4;i++) {
			CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
		}
		
		
        int headVal = 0;
		int actCount = 1;
		double colTotal = 0;
		double total = 0;
		
			if(hdActList.size()!=0)
				for(int i=0;i<hdActList.size();i++) 
				{
					if(hdActList.get(i).getHeadcode()!=headVal) {
						if(headVal!=0 && headVal<8) {
							CommonFunctions.insertCell3(table,"Sub-Total", Element.ALIGN_LEFT, 2, 1, bf10Bold);
							for (Map.Entry<String,BigDecimal> tot : subTotMap.entrySet()) {
								String[] parts = tot.getKey().split(",");
								if(Integer.parseInt(parts[0])==headVal) {
									CommonFunctions.insertCell3(table,tot.getValue()==null?"0.00":String.valueOf(tot.getValue()), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								}
							}
							CommonFunctions.insertCell3(table,String.valueOf(String.format(Locale.US,"%.2f",total)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							total=0;
						}
						CommonFunctions.insertCell(table,(hdActList.get(i).getHeadcode()+". "+ hdActList.get(i).getHeadname()), Element.ALIGN_LEFT, finMap.size()+3, 1, bf8);
						actCount = 1;
						headVal++;
					}
					CommonFunctions.insertCell(table,(hdActList.get(i).getHeadcode()+"."+actCount+" "+hdActList.get(i).getActivityname()), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table,hdActList.get(i).getUnitname(), Element.ALIGN_LEFT, 1, 1, bf8);
					for (Map.Entry<String,BigDecimal> actmap : hdActMap.entrySet()) {
						String[] parts = actmap.getKey().split(",");
						if(Integer.parseInt(parts[0])==hdActList.get(i).getActivitycode()) {
							CommonFunctions.insertCell(table,actmap.getValue()==null?"0.00":String.valueOf(actmap.getValue()), Element.ALIGN_RIGHT, 1, 1, bf8);
							colTotal = colTotal + (actmap.getValue().doubleValue());
						}
					}
					total = total + colTotal;
					CommonFunctions.insertCell(table,String.valueOf(String.format(Locale.US,"%.2f",colTotal)), Element.ALIGN_RIGHT, 1, 1, bf8);
					colTotal=0;
					actCount++;
				}
			
			
			if(hdActList.size()==0) {				
			CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, finMap.size()+3, 1, bf8);		
			}
			
		document.add(table);		
		table = new PdfPTable(1);		
		table.setWidthPercentage(70);		
		table.setSpacingBefore(15f);		
		table.setSpacingAfter(0f);		
		CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 	
		CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
		document.add(table);		
		document.close();		
		response.setContentType("application/pdf");		
		response.setHeader("Expires", "0");		
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");		
		response.setHeader("Content-Disposition", "attachment;filename=T1-Report.pdf");		
		response.setHeader("Pragma", "public");		
		response.setContentLength(baos.size());		
		OutputStream os = response.getOutputStream();		
		baos.writeTo(os);		
		os.flush();		
		os.close();		
		} 	catch (Exception ex) 	
	{
			ex.printStackTrace();	
			
	} 		
	return null;	
			
}	



public String sum4(String i, String j) {	
	if (i == null || i.isEmpty()) {	 i = "0";	
	}	
	if (j == null || j.isEmpty()) {	 j = "0";
	}
	try {	
		BigDecimal a = new BigDecimal(i);	 
		BigDecimal b = new BigDecimal(j);	
		BigDecimal sum = a.add(b);	 
		return sum.toString();	 
		} 
	catch (NumberFormatException e) {
		return "0";	
		}	
	}

}

