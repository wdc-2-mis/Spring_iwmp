package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

import app.PfmsTreasureBean;
import app.bean.AddOutcomeParaBean;
import app.bean.reports.PMKSYBean;
import app.common.CommonFunctions;
import app.service.DistrictMasterService;
import app.service.PhysicalActionPlanService;
import app.service.StateMasterService;
import app.service.reports.PMKSYService;
import app.service.reports.PhysicalActionAchievementService;

@Controller
public class PMKSYController {
	
	HttpSession session;
	
	@Autowired
	PMKSYService pmksyService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired
	PhysicalActionPlanService physicalActionPlanService;

	@RequestMapping(value = "/getPMKSYDetails", method = RequestMethod.GET)
	public ModelAndView getPMKSYDetails(HttpServletRequest request) {
		session = request.getSession(true);
		List<PMKSYBean> frmrList = pmksyService.getBenifitedFarmersNo();
		List<PMKSYBean> wtrHrvstIrrgtnList = pmksyService.getWtrHrvstngStrctrNo();
		Map<String,BigInteger> frmrMap = new LinkedHashMap<>();
		Map<String,BigDecimal> wtrMap = new LinkedHashMap<>();
		Map<String,BigDecimal> irriMap = new LinkedHashMap<>();
		Map<Integer,String> yrMap = new LinkedHashMap<>();
		
		frmrList.forEach(s->{
			if(!yrMap.containsValue(s.getYrdesc())) {
				yrMap.put(Integer.parseInt(s.getYrdesc().substring(2,s.getYrdesc().indexOf("-"))), s.getYrdesc());
			}
			if(frmrMap.containsKey(s.getYrdesc())) {
				frmrMap.put(s.getYrdesc(),frmrMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}else {
				frmrMap.put(s.getYrdesc(),s.getBnftdfrmrs());
			}
		});	
		wtrHrvstIrrgtnList.forEach(s->{
			if(!yrMap.containsValue(s.getYrdesc())) {
				yrMap.put(Integer.parseInt(s.getYrdesc().substring(2,s.getYrdesc().indexOf("-"))), s.getYrdesc());
			}
			if(wtrMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				wtrMap.put(s.getYrdesc(),wtrMap.get(s.getYrdesc()).add(s.getPlan()));
			}else if(s.getHeadcode()==4 || s.getHeadcode()==5){
				wtrMap.put(s.getYrdesc(),s.getPlan());
			}
			else if(irriMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				irriMap.put(s.getYrdesc(),irriMap.get(s.getYrdesc()).add(s.getPlan()));
			}else if(s.getHeadcode()==6 || s.getHeadcode()==7){
				irriMap.put(s.getYrdesc(),s.getPlan());
			}
		});
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/yrWisePMKSYDetails");
		mav.addObject("yrMap",yrMap);
		mav.addObject("yrMapSize",yrMap.size());
		mav.addObject("frmrMap",frmrMap);
		mav.addObject("wtrMap",wtrMap);
		mav.addObject("irriMap",irriMap);
		return mav;
	}
	
	@RequestMapping(value = "/getStateWisePMKSYDetails", method = RequestMethod.GET)
	public ModelAndView getStateWisePMKSYDetails(HttpServletRequest request) {
		session = request.getSession(true);
		List<PMKSYBean> frmrList = pmksyService.getBenifitedFarmersNo();
		LinkedHashMap<Integer,String> stMap = stateMasterService.getAllState();
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigInteger> yrStList = new LinkedHashMap<>();
		Map<String,BigInteger> frmrMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> smap : stMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(smap.getKey()+","+ymap.getValue(),BigInteger.ZERO);
				frmrMap.put(ymap.getValue(),BigInteger.ZERO);
			}
		}
		frmrList.forEach(s->{
			if(yrStList.containsKey(s.getStcode()+","+s.getYrdesc())) {
				yrStList.put(s.getStcode()+","+s.getYrdesc(),s.getBnftdfrmrs());
			}
			if(frmrMap.containsKey(s.getYrdesc())) {
				frmrMap.put(s.getYrdesc(),frmrMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}else {
				frmrMap.put(s.getYrdesc(),s.getBnftdfrmrs());
			}
		});
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/stateDistProjYrlyFrmrBnftd");
		mav.addObject("frmrList", frmrList);
		mav.addObject("yrMap",yearMap);
		mav.addObject("yrMapSize",yearMap.size());
		mav.addObject("frmrMap",yrStList);
		mav.addObject("totFrmrMap",frmrMap);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getDistWisePMKSYDetails", method = RequestMethod.GET)
	public ModelAndView getDistWisePMKSYDetails(HttpServletRequest request) {
		session = request.getSession(true);
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String stname = request.getParameter("stname");
		List<PMKSYBean> frmrDistList = pmksyService.getDistWiseBenifitedFarmersNo(stcode);
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(stcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigInteger> yrStList = new LinkedHashMap<>();
		Map<String,BigInteger> frmrMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : distMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigInteger.ZERO);
				frmrMap.put(ymap.getValue(),BigInteger.ZERO);
			}
		}
		frmrDistList.forEach(s->{
			if(yrStList.containsKey(s.getDcode()+","+s.getYrdesc())) {
				yrStList.put(s.getDcode()+","+s.getYrdesc(),s.getBnftdfrmrs());
			}
			if(frmrMap.containsKey(s.getYrdesc())) {
				frmrMap.put(s.getYrdesc(),frmrMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}else {
				frmrMap.put(s.getYrdesc(),s.getBnftdfrmrs());
			}
		});
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/stateDistProjYrlyFrmrBnftd");
		mav.addObject("frmrDistList", frmrDistList);
		mav.addObject("yrMap",yearMap);
		mav.addObject("yrMapSize",yearMap.size());
		mav.addObject("frmrDistMap",yrStList);
		mav.addObject("stcode",stcode);
		mav.addObject("stname",stname);
		mav.addObject("totFrmrMap",frmrMap);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getProjWisePMKSYDetails", method = RequestMethod.GET)
	public ModelAndView getProjWisePMKSYDetails(HttpServletRequest request) {
		session = request.getSession(true);
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		String distname = request.getParameter("distname");
		List<PMKSYBean> frmrProjList = pmksyService.getProjWiseBenifitedFarmersNo(dcode);
		LinkedHashMap<Integer,String> projMap = physicalActionPlanService.getProjectPhysicalActionPlan(dcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigInteger> yrProjList = new LinkedHashMap<>();
		Map<String,BigInteger> frmrMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> pmap : projMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrProjList.put(pmap.getKey()+","+ymap.getValue(),BigInteger.ZERO);
				frmrMap.put(ymap.getValue(),BigInteger.ZERO);
			}
		}
		frmrProjList.forEach(s->{
			if(yrProjList.containsKey(s.getProjid()+","+s.getYrdesc())) {
				yrProjList.put(s.getProjid()+","+s.getYrdesc(),s.getBnftdfrmrs());
			}
			if(frmrMap.containsKey(s.getYrdesc())) {
				frmrMap.put(s.getYrdesc(),frmrMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}else {
				frmrMap.put(s.getYrdesc(),s.getBnftdfrmrs());
			}
		});
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/stateDistProjYrlyFrmrBnftd");
		mav.addObject("frmrProjList", frmrProjList);
		mav.addObject("yrMap",yearMap);
		mav.addObject("yrMapSize",yearMap.size());
		mav.addObject("frmrProjMap",yrProjList);
		mav.addObject("stname",stname);
		mav.addObject("distname",distname);
		mav.addObject("dcode",dcode);
		mav.addObject("totFrmrMap",frmrMap);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getStateWiseWtrStrDetails", method = RequestMethod.GET)
	public ModelAndView getStateWiseWtrStrDetails(HttpServletRequest request) {
		session = request.getSession(true);
		List<PMKSYBean> wtrStrList = pmksyService.getStWiseWtrHrvstngIrriDetail();
		LinkedHashMap<Integer,String> stMap = stateMasterService.getAllState();
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> smap : stMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(smap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		wtrStrList.forEach(s->{
			if(yrStList.containsKey(s.getStcode()+","+s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				yrStList.put(s.getStcode()+","+s.getYrdesc(),yrStList.get(s.getStcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
		});
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/stateDistProjWtrStrDetails");
		mav.addObject("wtrStrList", wtrStrList);
		mav.addObject("yrMap",yearMap);
		mav.addObject("yrMapSize",yearMap.size());
		mav.addObject("wtrMap",yrStList);
		mav.addObject("totMap",totMap);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getDistWiseWtrStrDetails", method = RequestMethod.GET)
	public ModelAndView getDistWiseWtrStrDetails(HttpServletRequest request) {
		session = request.getSession(true);
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String stname = request.getParameter("stname");
		List<PMKSYBean> wtrStrList = pmksyService.getDistWiseWtrHrvstngIrriDetail(stcode);
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(stcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : distMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		wtrStrList.forEach(s->{
			if(yrStList.containsKey(s.getDcode()+","+s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				yrStList.put(s.getDcode()+","+s.getYrdesc(),yrStList.get(s.getDcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
		});
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/stateDistProjWtrStrDetails");
		mav.addObject("wtrStrDList", wtrStrList);
		mav.addObject("yrMap",yearMap);
		mav.addObject("yrMapSize",yearMap.size());
		mav.addObject("wtrDMap",yrStList);
		mav.addObject("stname",stname);
		mav.addObject("stcode",stcode);
		mav.addObject("totMap",totMap);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getProjWiseWtrStrDetails", method = RequestMethod.GET)
	public ModelAndView getProjWiseWtrStrDetails(HttpServletRequest request) {
		session = request.getSession(true);
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		String distname = request.getParameter("distname");
		List<PMKSYBean> wtrStrList = pmksyService.getProjWiseWtrHrvstngIrriDetail(dcode);
		LinkedHashMap<Integer,String> projMap = physicalActionPlanService.getProjectPhysicalActionPlan(dcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> pmap : projMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(pmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		wtrStrList.forEach(s->{
			if(yrStList.containsKey(s.getProjid()+","+s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				yrStList.put(s.getProjid()+","+s.getYrdesc(),yrStList.get(s.getProjid()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
		});
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/stateDistProjWtrStrDetails");
		mav.addObject("wtrStrPList", wtrStrList);
		mav.addObject("yrMap",yearMap);
		mav.addObject("yrMapSize",yearMap.size());
		mav.addObject("wtrPMap",yrStList);
		mav.addObject("stname",stname);
		mav.addObject("distname",distname);
		mav.addObject("dcode",dcode);
		mav.addObject("totMap",totMap);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getStateWiseIrriDetails", method = RequestMethod.GET)
	public ModelAndView getStateWiseIrriDetails(HttpServletRequest request) {
		session = request.getSession(true);
		List<PMKSYBean> irriList = pmksyService.getStWiseWtrHrvstngIrriDetail();
		LinkedHashMap<Integer,String> stMap = stateMasterService.getAllState();
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> smap : stMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(smap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		irriList.forEach(s->{
			if(yrStList.containsKey(s.getStcode()+","+s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				yrStList.put(s.getStcode()+","+s.getYrdesc(),yrStList.get(s.getStcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
		});
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/stateDistProjIrriDetails");
		mav.addObject("irriList", irriList);
		mav.addObject("yrMap",yearMap);
		mav.addObject("yrMapSize",yearMap.size());
		mav.addObject("irriMap",yrStList);
		mav.addObject("totMap",totMap);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getDistWiseIrriDetails", method = RequestMethod.GET)
	public ModelAndView getDistWiseIrriDetails(HttpServletRequest request) {
		session = request.getSession(true);
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String stname = request.getParameter("stname");
		List<PMKSYBean> irriList = pmksyService.getDistWiseWtrHrvstngIrriDetail(stcode);
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(stcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : distMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		irriList.forEach(s->{
			if(yrStList.containsKey(s.getDcode()+","+s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				yrStList.put(s.getDcode()+","+s.getYrdesc(),yrStList.get(s.getDcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
		});
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/stateDistProjIrriDetails");
		mav.addObject("irriDList", irriList);
		mav.addObject("yrMap",yearMap);
		mav.addObject("yrMapSize",yearMap.size());
		mav.addObject("irriDMap",yrStList);
		mav.addObject("stcode",stcode);
		mav.addObject("stname",stname);
		mav.addObject("totMap",totMap);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getProjWiseIrriDetails", method = RequestMethod.GET)
	public ModelAndView getProjWiseIrriDetails(HttpServletRequest request) {
		session = request.getSession(true);
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		String distname = request.getParameter("distname");
		List<PMKSYBean> irriList = pmksyService.getProjWiseWtrHrvstngIrriDetail(dcode);
		LinkedHashMap<Integer,String> projMap = physicalActionPlanService.getProjectPhysicalActionPlan(dcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> pmap : projMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(pmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		irriList.forEach(s->{
			if(yrStList.containsKey(s.getProjid()+","+s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				yrStList.put(s.getProjid()+","+s.getYrdesc(),yrStList.get(s.getProjid()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
		});
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/stateDistProjIrriDetails");
		mav.addObject("irriPList", irriList);
		mav.addObject("yrMap",yearMap);
		mav.addObject("yrMapSize",yearMap.size());
		mav.addObject("irriPMap",yrStList);
		mav.addObject("stname",stname);
		mav.addObject("distname",distname);
		mav.addObject("dcode",dcode);
		mav.addObject("totMap",totMap);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getPMKSYDetailsExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getPMKSYDetailsExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		session = request.getSession(true);
		List<PMKSYBean> frmrList = pmksyService.getBenifitedFarmersNo();
		List<PMKSYBean> wtrHrvstIrrgtnList = pmksyService.getWtrHrvstngStrctrNo();
		Map<String,BigInteger> frmrMap = new LinkedHashMap<>();
		Map<String,BigDecimal> wtrMap = new LinkedHashMap<>();
		Map<String,BigDecimal> irriMap = new LinkedHashMap<>();
		Map<Integer,String> yrMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		
		frmrList.forEach(s->{
			if(frmrMap.containsKey(s.getYrdesc())) {
				frmrMap.put(s.getYrdesc(),frmrMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}else {
				frmrMap.put(s.getYrdesc(),s.getBnftdfrmrs());
			}
		});	
		wtrHrvstIrrgtnList.forEach(s->{
			if(wtrMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				wtrMap.put(s.getYrdesc(),wtrMap.get(s.getYrdesc()).add(s.getPlan()));
			}else if(s.getHeadcode()==4 || s.getHeadcode()==5){
				wtrMap.put(s.getYrdesc(),s.getPlan());
			}
			else if(irriMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				irriMap.put(s.getYrdesc(),irriMap.get(s.getYrdesc()).add(s.getPlan()));
			}else if(s.getHeadcode()==6 || s.getHeadcode()==7){
				irriMap.put(s.getYrdesc(),s.getPlan());
			}
		});
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report O12- Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)");   
		
		
		CellStyle style = CommonFunctions.getStyle(workbook);
        
		String rptName = "Report O12- Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)";
		String areaAmtValDetail = "";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, yrMap.size()+1, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(5,6,0,0);
        sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,1,1);
        sheet.addMergedRegion(mergedRegion);
        mergedRegion = new CellRangeAddress(5,5,2,yrMap.size()+1); 
        sheet.addMergedRegion(mergedRegion);
        	
        
        Row rowhead = sheet.createRow(5); 
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		cell = rowhead.createCell(1);
		cell.setCellValue("Components");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		cell = rowhead.createCell(2);
		cell.setCellValue("WDC 2.0");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		for(int i =3; i<yrMap.size()+2;i++) {
			rowhead.createCell(i).setCellStyle(style);
		}
		
        
		Row rowhead1 = sheet.createRow(6);
		
			rowhead1.createCell(0).setCellStyle(style);
			rowhead1.createCell(1).setCellStyle(style);
		int no = 2;
		for(Map.Entry<Integer, String> map : yrMap.entrySet()) {
			cell = rowhead1.createCell(no);
			cell.setCellValue(map.getValue());
			cell.setCellStyle(style);
			no++;
		}
		
		
		Row rowhead2 = sheet.createRow(7);
		for(int i =0; i<yrMap.size()+2;i++) {
			cell = rowhead2.createCell(i);
			cell.setCellValue(i+1);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell.setCellStyle(style);
		}
		
		Row row1 = sheet.createRow(8);
		cell = row1.createCell(0);
		cell.setCellValue(1);
		cell = row1.createCell(1);
		cell.setCellValue("No. of Water Harvesting Structures Constructed/Revived(No.)");
		no = 2;
		for(Map.Entry<String, BigDecimal> map : wtrMap.entrySet()) {
			cell = row1.createCell(no);
			cell.setCellValue(map.getValue().doubleValue());
			no++;
		}
		
		Row row2 = sheet.createRow(9);
		cell = row2.createCell(0);
		cell.setCellValue(2);
		cell = row2.createCell(1);
		cell.setCellValue("Additional Area Brought Under Irrigation(Ha.)");
		no = 2;
		for(Map.Entry<String, BigDecimal> map : irriMap.entrySet()) {
			cell = row2.createCell(no);
			cell.setCellValue(map.getValue().doubleValue());
			no++;
		}
		
		Row row3 = sheet.createRow(10);
		cell = row3.createCell(0);
		cell.setCellValue(2);
		cell = row3.createCell(1);
		cell.setCellValue("Benefited Farmers(No.)");
		no = 2;
		for(Map.Entry<String, BigInteger> map : frmrMap.entrySet()) {
			cell = row3.createCell(no);
			cell.setCellValue(map.getValue().doubleValue());
			no++;
		}
        
        
        CommonFunctions.getExcelFooter(sheet, mergedRegion, 4, yrMap.size()+1);
        String fileName = "attachment; filename=Report O12- WDC-PMKSY.xlsx";
        
        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
	}
	@RequestMapping(value = "/getProjWiseWtrPDF", method = RequestMethod.POST)
	public ModelAndView getProjWiseWtrPDF(HttpServletRequest request, HttpServletResponse response) 
	{

		String distname = request.getParameter("distname");
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		List<PMKSYBean> wtrStrList = pmksyService.getProjWiseWtrHrvstngIrriDetail(dcode);
		LinkedHashMap<Integer,String> projMap = physicalActionPlanService.getProjectPhysicalActionPlan(dcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : projMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :wtrStrList){
			if(s.getProjid()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getProjid()+","+s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				yrStList.put(s.getProjid()+","+s.getYrdesc(),yrStList.get(s.getProjid()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getProjid();
		}
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report O12- Project Wise No. of Water Harvesting Structures Constructed/Revived Status for District "+" '"+distname+"'  of State"+" '"+stname+"' ", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if(yearMap.size()!=0)
				table = new PdfPTable(yearMap.size()+2);
			int[]arr=new int[yearMap.size()+2];
			for(int i=0; i<yearMap.size()+2;i++) {
				arr[i]=3;
			}
				table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);


			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Water Harvesting Structures Constructed/Revived(in No.)", Element.ALIGN_CENTER, yearMap.size(), 1, bf8Bold);
			
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<yearMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			int k=1;
			if(wtrStrList.size()!=0)
				for(int i=0;i<wtrStrList.size();i++) 
				{
			
			if (wtrStrList.get(i).getProjid() != stcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, wtrStrList.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);

				for (Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
					if (wtrStrList.get(i).getProjid() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
				}

				k = k + 1;
				stcd = wtrStrList.get(i).getProjid();
			}
				
				}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			for (Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
				
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				
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
			response.setHeader("Content-Disposition", "attachment;filename=O12-ProjectReport.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

		return null;
	}
	@RequestMapping(value = "/getDistWiseWtrPDF", method = RequestMethod.POST)
	public ModelAndView getDistWiseWtrPDF(HttpServletRequest request, HttpServletResponse response) 
	{

		session = request.getSession(true);
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String stname = request.getParameter("stname");
		List<PMKSYBean> wtrStrList = pmksyService.getDistWiseWtrHrvstngIrriDetail(stcode);
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(stcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrDistList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : distMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrDistList.put(dmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}

		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :wtrStrList){
			if(s.getDcode()!=stcd) {
				stcnt++;
			}
			if(yrDistList.containsKey(s.getDcode()+","+s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				yrDistList.put(s.getDcode()+","+s.getYrdesc(),yrDistList.get(s.getDcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getDcode();
		}
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report O12- District Wise No. of Water Harvesting Structures Constructed/Revived Status for State "+" '"+stname+"' ", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if(yearMap.size()!=0)
				table = new PdfPTable(yearMap.size()+2);
			int[]arr=new int[yearMap.size()+2];
			for(int i=0; i<yearMap.size()+2;i++) {
				arr[i]=3;
			}
				table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);


			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Water Harvesting Structures Constructed/Revived(in No.)", Element.ALIGN_CENTER, yearMap.size(), 1, bf8Bold);
			
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<yearMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			int k=1;
			if(wtrStrList.size()!=0)
				for(int i=0;i<wtrStrList.size();i++) 
				{
			
			if (wtrStrList.get(i).getDcode() != stcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, wtrStrList.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);

				for (Map.Entry<String, BigDecimal> map : yrDistList.entrySet()) {
					if (wtrStrList.get(i).getDcode() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
				}

				k = k + 1;
				stcd = wtrStrList.get(i).getDcode();
			}
				
				}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			for (Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
				
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				
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
			response.setHeader("Content-Disposition", "attachment;filename=O12-DistrictReport.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

		return null;
	}
	@RequestMapping(value = "/getStwiseWTRPDF", method = RequestMethod.POST)
	public ModelAndView getStwiseWTRPDF(HttpServletRequest request, HttpServletResponse response) 
	{

		List<PMKSYBean> list = pmksyService.getStWiseWtrHrvstngIrriDetail();
		LinkedHashMap<Integer,String> stMap = stateMasterService.getAllState();
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> smap : stMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(smap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :list){
			if(s.getStcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getStcode()+","+s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				yrStList.put(s.getStcode()+","+s.getYrdesc(),yrStList.get(s.getStcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getStcode();
		}
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report O12- State Wise No. of Water Harvesting Structures Constructed/Revived Status", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if(yearMap.size()!=0)
				table = new PdfPTable(yearMap.size()+2);
			int[]arr=new int[yearMap.size()+2];
			for(int i=0; i<yearMap.size()+2;i++) {
				arr[i]=3;
			}
				table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);


			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Water Harvesting Structures Constructed/Revived(in No.)", Element.ALIGN_CENTER, yearMap.size(), 1, bf8Bold);
			
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<yearMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			int k=1;
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
			
			if (list.get(i).getStcode() != stcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);

				for (Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
					if (list.get(i).getStcode() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
				}

				k = k + 1;
				stcd = list.get(i).getStcode();
			}
				
				}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			for (Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
				
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				
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
			response.setHeader("Content-Disposition", "attachment;filename=O12-StateReport.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

		return null;
	}
	
	
	@RequestMapping(value = "/getPMKSYDetailsPDF", method = RequestMethod.POST)
	public ModelAndView getPMKSYDetailsPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		session = request.getSession(true);
		List<PMKSYBean> frmrList = pmksyService.getBenifitedFarmersNo();
		List<PMKSYBean> wtrHrvstIrrgtnList = pmksyService.getWtrHrvstngStrctrNo();
		Map<String,BigInteger> frmrMap = new LinkedHashMap<>();
		Map<String,BigDecimal> wtrMap = new LinkedHashMap<>();
		Map<String,BigDecimal> irriMap = new LinkedHashMap<>();
		Map<Integer,String> yrMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		
		frmrList.forEach(s->{
			if(frmrMap.containsKey(s.getYrdesc())) {
				frmrMap.put(s.getYrdesc(),frmrMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}else {
				frmrMap.put(s.getYrdesc(),s.getBnftdfrmrs());
			}
		});	
		wtrHrvstIrrgtnList.forEach(s->{
			if(wtrMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				wtrMap.put(s.getYrdesc(),wtrMap.get(s.getYrdesc()).add(s.getPlan()));
			}else if(s.getHeadcode()==4 || s.getHeadcode()==5){
				wtrMap.put(s.getYrdesc(),s.getPlan());
			}
			else if(irriMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				irriMap.put(s.getYrdesc(),irriMap.get(s.getYrdesc()).add(s.getPlan()));
			}else if(s.getHeadcode()==6 || s.getHeadcode()==7){
				irriMap.put(s.getYrdesc(),s.getPlan());
			}
		});

		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report O12- Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			table = new PdfPTable(4);
			table.setWidths(new int[] { 2, 8, 5, 5});
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);


			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Components", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "WDC 2.0", Element.ALIGN_CENTER, yrMap.size(), 1, bf8Bold);
			
			for(Map.Entry<Integer, String> map : yrMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<yrMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			

			
					CommonFunctions.insertCell(table, String.valueOf(1), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "No. of Water Harvesting Structures Constructed/Revived(No.)", Element.ALIGN_LEFT, 1, 1, bf8);
					for(Map.Entry<String, BigDecimal> map : wtrMap.entrySet()) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
					
					CommonFunctions.insertCell(table, String.valueOf(2), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Additional Area Brought Under Irrigation(Ha.)", Element.ALIGN_LEFT, 1, 1, bf8);
					for(Map.Entry<String, BigDecimal> map : irriMap.entrySet()) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
					
					CommonFunctions.insertCell(table, String.valueOf(3), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, "Benefited Farmers(No.)", Element.ALIGN_LEFT, 1, 1, bf8);
					for(Map.Entry<String, BigInteger> map : frmrMap.entrySet()) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
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
			response.setHeader("Content-Disposition", "attachment;filename=O12-Report.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

		return null;
	}
	
	
	@RequestMapping(value = "/getStWiseWtrExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getStWiseWtrExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		List<PMKSYBean> list = pmksyService.getStWiseWtrHrvstngIrriDetail();
		LinkedHashMap<Integer,String> stMap = stateMasterService.getAllState();
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> smap : stMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(smap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :list){
			if(s.getStcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getStcode()+","+s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				yrStList.put(s.getStcode()+","+s.getYrdesc(),yrStList.get(s.getStcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getStcode();
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O12 - State Wise No. of Water Harvesting Structures Constructed/Revived Status");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O12 - State Wise No. of Water Harvesting Structures Constructed/Revived Status";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, yearMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,yearMap.size()+1); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(stcnt+8,stcnt+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Water Harvesting Structures Constructed/Revived(in No.)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i=3;i<yearMap.size()+2;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<yearMap.size()+2;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int stcode = 0;
	        int rowno  = 8;
	        for(PMKSYBean bean: list) {
	        	
	        	if(bean.getStcode()!=stcode) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getStname());
	        		int i =2;
	        		for(Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getStcode() == st) {
	        				row.createCell(i).setCellValue(Math.round(map.getValue().doubleValue()));
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	stcode = bean.getStcode();
	        	
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
	        
			
			
	        Row row = sheet.createRow(stcnt+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()));
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, stcnt, yearMap.size()+2);
	        String fileName = "attachment; filename=Report O12- State Water.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "";
		
	}
	
	
	@RequestMapping(value = "/getDistWiseWtrExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getDistWiseWtrExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		String stname = request.getParameter("stname");
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		List<PMKSYBean> wtrStrList = pmksyService.getDistWiseWtrHrvstngIrriDetail(stcode);
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(stcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : distMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :wtrStrList){
			if(s.getDcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getDcode()+","+s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				yrStList.put(s.getDcode()+","+s.getYrdesc(),yrStList.get(s.getDcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getDcode();
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O12 - District Wise No. of Water Harvesting Structures Constructed/Revived Status for "+stname+" State");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O12 - District Wise No. of Water Harvesting Structures Constructed/Revived Status for "+stname+" State";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, yearMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,yearMap.size()+1); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(stcnt+8,stcnt+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Water Harvesting Structures Constructed/Revived(in No.)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i=3;i<yearMap.size()+2;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<yearMap.size()+2;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int stcod = 0;
	        int rowno  = 8;
	        for(PMKSYBean bean: wtrStrList) {
	        	
	        	if(bean.getDcode()!=stcod) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getDistname());
	        		int i =2;
	        		for(Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getDcode() == st) {
	        				row.createCell(i).setCellValue(Math.round(map.getValue().doubleValue()));
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	stcod = bean.getDcode();
	        	
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
	        
			
			
	        Row row = sheet.createRow(stcnt+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()));
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, stcnt, yearMap.size()+2);
	        String fileName = "attachment; filename=Report O12- District Water.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "";
		
	}
	
	@RequestMapping(value = "/getProjWiseWtrExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getProjWiseWtrExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		String distname = request.getParameter("distname");
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		List<PMKSYBean> wtrStrList = pmksyService.getProjWiseWtrHrvstngIrriDetail(dcode);
		LinkedHashMap<Integer,String> projMap = physicalActionPlanService.getProjectPhysicalActionPlan(dcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : projMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :wtrStrList){
			if(s.getProjid()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getProjid()+","+s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				yrStList.put(s.getProjid()+","+s.getYrdesc(),yrStList.get(s.getProjid()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==4 || s.getHeadcode()==5)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getProjid();
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O12 - Project Wise No. of Water Harvesting Structures Constructed/Revived Status");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O12 - Project Wise No. of Water Harvesting Structures Constructed/Revived Status for "+distname+" District of "+stname+" State";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, yearMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,yearMap.size()+1); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(stcnt+8,stcnt+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Water Harvesting Structures Constructed/Revived(in No.)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i=3;i<yearMap.size()+2;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<yearMap.size()+2;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int stcod = 0;
	        int rowno  = 8;
	        for(PMKSYBean bean: wtrStrList) {
	        	
	        	if(bean.getProjid()!=stcod) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getProjname());
	        		int i =2;
	        		for(Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getProjid() == st) {
	        				row.createCell(i).setCellValue(Math.round(map.getValue().doubleValue()));
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	stcod = bean.getProjid();
	        	
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
	        
			
			
	        Row row = sheet.createRow(stcnt+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()));
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, stcnt, yearMap.size()+2);
	        String fileName = "attachment; filename=Report O12- Project Water.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "";
		
	}
	
	@RequestMapping(value = "/getProjWiseIrriPDF", method = RequestMethod.POST)
	public ModelAndView getProjWiseIrriPDF(HttpServletRequest request, HttpServletResponse response) 
	{

		String distname = request.getParameter("distname");
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		List<PMKSYBean> wtrStrList = pmksyService.getProjWiseWtrHrvstngIrriDetail(dcode);
		LinkedHashMap<Integer,String> projMap = physicalActionPlanService.getProjectPhysicalActionPlan(dcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : projMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :wtrStrList){
			if(s.getProjid()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getProjid()+","+s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				yrStList.put(s.getProjid()+","+s.getYrdesc(),yrStList.get(s.getProjid()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getProjid();
		}
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report O12- Project Wise Additional Area Brought Under Irrigation Status for District"+ " '"+distname+"'  Of State"+" '"+stname+"' " , f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if(yearMap.size()!=0)
			table = new PdfPTable(yearMap.size()+2);
			int[]arr=new int[yearMap.size()+2];
			for(int i=0; i<yearMap.size()+2;i++) {
				arr[i]=3;
			}
			table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);


			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Additional Area Brought Under Irrigation(Ha.)", Element.ALIGN_CENTER, yearMap.size(), 1, bf8Bold);
			
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<yearMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			int k=1;
			if(wtrStrList.size()!=0)
				for(int i=0;i<wtrStrList.size();i++) 
				{
			
			if (wtrStrList.get(i).getProjid() != stcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, wtrStrList.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);

				for (Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
					if (wtrStrList.get(i).getProjid() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
				}

				k = k + 1;
				stcd = wtrStrList.get(i).getProjid();
			}
				
				}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			for (Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
				
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				
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
			response.setHeader("Content-Disposition", "attachment;filename=O12-ProjectIrrigationReport.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

		return null;
	}
	@RequestMapping(value = "/getDistrictWiseIrriPDF", method = RequestMethod.POST)
	public ModelAndView getDistrictWiseIrriPDF(HttpServletRequest request, HttpServletResponse response) 
	{

		String stname = request.getParameter("stname");
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		List<PMKSYBean> wtrStrList = pmksyService.getDistWiseWtrHrvstngIrriDetail(stcode);
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(stcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : distMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :wtrStrList){
			if(s.getDcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getDcode()+","+s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				yrStList.put(s.getDcode()+","+s.getYrdesc(),yrStList.get(s.getDcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getDcode();
		}
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report O12- District Wise Additional Area Brought Under Irrigation Status for State "+" '"+stname+"' ", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if(yearMap.size()!=0)
			table = new PdfPTable(yearMap.size()+2);
			int[]arr=new int[yearMap.size()+2];
			for(int i=0; i<yearMap.size()+2;i++) {
				arr[i]=3;
			}
			table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);


			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Additional Area Brought Under Irrigation(Ha.)", Element.ALIGN_CENTER, yearMap.size(), 1, bf8Bold);
			
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<yearMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			int k=1;
			if(wtrStrList.size()!=0)
				for(int i=0;i<wtrStrList.size();i++) 
				{
			
			if (wtrStrList.get(i).getDcode() != stcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, wtrStrList.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);

				for (Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
					if (wtrStrList.get(i).getDcode() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
				}

				k = k + 1;
				stcd = wtrStrList.get(i).getDcode();
			}
				
				}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			for (Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
				
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				
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
			response.setHeader("Content-Disposition", "attachment;filename=O12-DistrictIrrigationReport.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

		return null;
	}
	@RequestMapping(value = "/getStateWiseIrriPDF", method = RequestMethod.POST)
	public ModelAndView getStateWiseIrriPDF(HttpServletRequest request, HttpServletResponse response) 
	{

		List<PMKSYBean> list = pmksyService.getStWiseWtrHrvstngIrriDetail();
		LinkedHashMap<Integer,String> stMap = stateMasterService.getAllState();
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> smap : stMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(smap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :list){
			if(s.getStcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getStcode()+","+s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				yrStList.put(s.getStcode()+","+s.getYrdesc(),yrStList.get(s.getStcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getStcode();
		
		}
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report O12- State Wise Additional Area Brought Under Irrigation Status", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if(yearMap.size()!=0)
			table = new PdfPTable(yearMap.size()+2);
			int[]arr=new int[yearMap.size()+2];
			for(int i=0; i<yearMap.size()+2;i++) {
				arr[i]=3;
			}
			table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);


			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Additional Area Brought Under Irrigation(Ha.)", Element.ALIGN_CENTER, yearMap.size(), 1, bf8Bold);
			
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<yearMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			int k=1;
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
			
			if (list.get(i).getStcode() != stcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);

				for (Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
					if (list.get(i).getStcode() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
				}

				k = k + 1;
				stcd = list.get(i).getStcode();
			}
				
				}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			for (Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
				
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				
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
			response.setHeader("Content-Disposition", "attachment;filename=O12-StateIrrigationReport.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

		return null;
	}
	
		
	
	
	@RequestMapping(value = "/getStWiseIrriExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getStWiseIrriExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		List<PMKSYBean> list = pmksyService.getStWiseWtrHrvstngIrriDetail();
		LinkedHashMap<Integer,String> stMap = stateMasterService.getAllState();
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> smap : stMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(smap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :list){
			if(s.getStcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getStcode()+","+s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				yrStList.put(s.getStcode()+","+s.getYrdesc(),yrStList.get(s.getStcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getStcode();
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O12 - State Wise Additional Area Brought Under Irrigation Status");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O12 - State Wise Additional Area Brought Under Irrigation Status";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, yearMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,yearMap.size()+1); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(stcnt+8,stcnt+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Additional Area Brought Under Irrigation(Ha.)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i=3;i<yearMap.size()+2;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<yearMap.size()+2;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int stcode = 0;
	        int rowno  = 8;
	        for(PMKSYBean bean: list) {
	        	
	        	if(bean.getStcode()!=stcode) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getStname());
	        		int i =2;
	        		for(Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getStcode() == st) {
	        				row.createCell(i).setCellValue(Math.round(map.getValue().doubleValue()*100.0)/100.0);
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	stcode = bean.getStcode();
	        	
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
	        
			
			
	        Row row = sheet.createRow(stcnt+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()*100.0)/100.0);
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, stcnt, yearMap.size()+2);
	        String fileName = "attachment; filename=Report O12- State Irrigation.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "";
		
	}
	
	
	@RequestMapping(value = "/getDistWiseIrriExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getDistWiseIrriExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		String stname = request.getParameter("stname");
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		List<PMKSYBean> wtrStrList = pmksyService.getDistWiseWtrHrvstngIrriDetail(stcode);
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(stcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : distMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :wtrStrList){
			if(s.getDcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getDcode()+","+s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				yrStList.put(s.getDcode()+","+s.getYrdesc(),yrStList.get(s.getDcode()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getDcode();
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O12 - District Wise Additional Area Brought Under Irrigation Status for "+stname+" State");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O12 - District Wise Additional Area Brought Under Irrigation Status for "+stname+" State";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, yearMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,yearMap.size()+1); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(stcnt+8,stcnt+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Additional Area Brought Under Irrigation(Ha.)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i=3;i<yearMap.size()+2;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<yearMap.size()+2;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int stcod = 0;
	        int rowno  = 8;
	        for(PMKSYBean bean: wtrStrList) {
	        	
	        	if(bean.getDcode()!=stcod) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getDistname());
	        		int i =2;
	        		for(Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getDcode() == st) {
	        				row.createCell(i).setCellValue(Math.round(map.getValue().doubleValue()*100.0)/100.0);
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	stcod = bean.getDcode();
	        	
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
	        
			
			
	        Row row = sheet.createRow(stcnt+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()*100.0)/100.0);
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, stcnt, yearMap.size()+2);
	        String fileName = "attachment; filename=Report O12- District Irrigation.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "";
		
	}
	
	@RequestMapping(value = "/getProjWiseIrriExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getProjWiseIrriExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		String distname = request.getParameter("distname");
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		List<PMKSYBean> wtrStrList = pmksyService.getProjWiseWtrHrvstngIrriDetail(dcode);
		LinkedHashMap<Integer,String> projMap = physicalActionPlanService.getProjectPhysicalActionPlan(dcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigDecimal> yrStList = new LinkedHashMap<>();
		Map<String,BigDecimal> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : projMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigDecimal.ZERO);
				totMap.put(ymap.getValue(),BigDecimal.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :wtrStrList){
			if(s.getProjid()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getProjid()+","+s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				yrStList.put(s.getProjid()+","+s.getYrdesc(),yrStList.get(s.getProjid()+","+s.getYrdesc()).add(s.getPlan()));
			}
			if(totMap.containsKey(s.getYrdesc()) && (s.getHeadcode()==6 || s.getHeadcode()==7)) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getPlan()));
			}
			stcd = s.getProjid();
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O12 - Project Wise Additional Area Brought Under Irrigation Status");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O12 - Project Wise Additional Area Brought Under Irrigation Status for "+distname+" District of "+stname+" State";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, yearMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,yearMap.size()+1); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(stcnt+8,stcnt+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Additional Area Brought Under Irrigation(Ha.)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i=3;i<yearMap.size()+2;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<yearMap.size()+2;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int stcod = 0;
	        int rowno  = 8;
	        for(PMKSYBean bean: wtrStrList) {
	        	
	        	if(bean.getProjid()!=stcod) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getProjname());
	        		int i =2;
	        		for(Map.Entry<String, BigDecimal> map : yrStList.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getProjid() == st) {
	        				row.createCell(i).setCellValue(Math.round(map.getValue().doubleValue()*100.0)/100.0);
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	stcod = bean.getProjid();
	        	
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
	        
			
			
	        Row row = sheet.createRow(stcnt+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigDecimal> map : totMap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()*100.0)/100.0);
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, stcnt, yearMap.size()+2);
	        String fileName = "attachment; filename=Report O12- Project Irrigation.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "";
		
	}
	
	
	@RequestMapping(value = "/getProjectWiseFrmrPDF", method = RequestMethod.POST)
	public ModelAndView getProjectWiseFrmrPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String distname = request.getParameter("distname");
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		List<PMKSYBean> list = pmksyService.getProjWiseBenifitedFarmersNo(dcode);
		LinkedHashMap<Integer,String> projMap = physicalActionPlanService.getProjectPhysicalActionPlan(dcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigInteger> yrStList = new LinkedHashMap<>();
		Map<String,BigInteger> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : projMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigInteger.ZERO);
				totMap.put(ymap.getValue(),BigInteger.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :list){
			if(s.getProjid()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getProjid()+","+s.getYrdesc())) {
				yrStList.put(s.getProjid()+","+s.getYrdesc(),yrStList.get(s.getProjid()+","+s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			if(totMap.containsKey(s.getYrdesc())) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			stcd = s.getProjid();
		}
				
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report O12- Project Wise No. of Farmers Benefitted Under Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY) for State  "+" '"+stname+"' ", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if(yearMap.size()!=0)
			table = new PdfPTable(yearMap.size()+2);
			int[]arr=new int[yearMap.size()+2];
			for(int i=0; i<yearMap.size()+2;i++) {
				arr[i]=1;
			}
			table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Benefitted Farmers(No.)", Element.ALIGN_CENTER, yearMap.size(), 1, bf8Bold);
			
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<yearMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			int k=1;
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
			
			if (list.get(i).getProjid() != stcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);

				for (Map.Entry<String, BigInteger> map : yrStList.entrySet()) {
					if (list.get(i).getProjid() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
				}

				k = k + 1;
				stcd = list.get(i).getProjid();
			}
				
				}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			for (Map.Entry<String, BigInteger> map : totMap.entrySet()) {
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
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
			response.setHeader("Content-Disposition", "attachment;filename=O12-ProjectFarmersReport.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

		return null;
	}
	
	@RequestMapping(value = "/getDistWiseFrmrPDF", method = RequestMethod.POST)
	public ModelAndView getDistWiseFrmrPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String stname = request.getParameter("stname");
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		List<PMKSYBean> list = pmksyService.getDistWiseBenifitedFarmersNo(stcode);
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(stcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigInteger> yrStList = new LinkedHashMap<>();
		Map<String,BigInteger> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : distMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigInteger.ZERO);
				totMap.put(ymap.getValue(),BigInteger.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :list){
			if(s.getDcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getDcode()+","+s.getYrdesc()) ) {
				yrStList.put(s.getDcode()+","+s.getYrdesc(),yrStList.get(s.getDcode()+","+s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			if(totMap.containsKey(s.getYrdesc()) ) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			stcd = s.getDcode();
		}
				
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report O12- District Wise No. of Farmers Benefitted Under Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY) for State  '"+stname+"' ", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if(yearMap.size()!=0)
			table = new PdfPTable(yearMap.size()+2);
			int[]arr=new int[yearMap.size()+2];
			for(int i=0; i<yearMap.size()+2;i++) {
				arr[i]=1;
			}
			table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Benefitted Farmers(No.)", Element.ALIGN_CENTER, yearMap.size(), 1, bf8Bold);
			
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<yearMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			int k=1;
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
			
			if (list.get(i).getDcode() != stcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);

				for (Map.Entry<String, BigInteger> map : yrStList.entrySet()) {
					if (list.get(i).getDcode() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
				}

				k = k + 1;
				stcd = list.get(i).getDcode();
			}
				
				}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			for (Map.Entry<String, BigInteger> map : totMap.entrySet()) {
				
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				
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
			response.setHeader("Content-Disposition", "attachment;filename=O12-DistrictFarmersReport.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

		return null;
	}
	
	@RequestMapping(value = "/getStWiseFrmrPDF", method = RequestMethod.POST)
	public ModelAndView getStWiseFrmrPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		List<PMKSYBean> list = pmksyService.getBenifitedFarmersNo();
		LinkedHashMap<Integer,String> stMap = stateMasterService.getAllState();
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigInteger> yrStList = new LinkedHashMap<>();
		Map<String,BigInteger> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> smap : stMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(smap.getKey()+","+ymap.getValue(),BigInteger.ZERO);
				totMap.put(ymap.getValue(),BigInteger.ZERO);
			}
		}
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :list){
			if(s.getStcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getStcode()+","+s.getYrdesc())) {
				yrStList.put(s.getStcode()+","+s.getYrdesc(),yrStList.get(s.getStcode()+","+s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			if(totMap.containsKey(s.getYrdesc())) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			stcd = s.getStcode();
		}
		
		
				
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report O12- State Wise No. of Farmers Benefitted Under Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			if(yearMap.size()!=0)
			table = new PdfPTable(yearMap.size()+2);
			int[]arr=new int[yearMap.size()+2];
			for(int i=0; i<yearMap.size()+2;i++) {
				arr[i]=1;
			}
			table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Benefitted Farmers(No.)", Element.ALIGN_CENTER, yearMap.size(), 1, bf8Bold);
			
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<yearMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			int k=1;
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
			
			if (list.get(i).getStcode() != stcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);

				for (Map.Entry<String, BigInteger> map : yrStList.entrySet()) {
					if (list.get(i).getStcode() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
				}

				k = k + 1;
				stcd = list.get(i).getStcode();
			}
				
				}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			for (Map.Entry<String, BigInteger> map : totMap.entrySet()) {
				
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				
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
			response.setHeader("Content-Disposition", "attachment;filename=O12-StateFarmersReport.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 

		return null;
	}
	
	@RequestMapping(value = "/getStWiseFrmrExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getStWiseFrmrExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		List<PMKSYBean> list = pmksyService.getBenifitedFarmersNo();
		LinkedHashMap<Integer,String> stMap = stateMasterService.getAllState();
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigInteger> yrStList = new LinkedHashMap<>();
		Map<String,BigInteger> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> smap : stMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(smap.getKey()+","+ymap.getValue(),BigInteger.ZERO);
				totMap.put(ymap.getValue(),BigInteger.ZERO);
			}
		}
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :list){
			if(s.getStcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getStcode()+","+s.getYrdesc())) {
				yrStList.put(s.getStcode()+","+s.getYrdesc(),yrStList.get(s.getStcode()+","+s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			if(totMap.containsKey(s.getYrdesc())) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			stcd = s.getStcode();
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O12 - State Wise No. of Farmers Benefitted Under Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O12 - State Wise No. of Farmers Benefitted Under Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, yearMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,yearMap.size()+1); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(stcnt+8,stcnt+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Benefitted Farmers(No.)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i=3;i<yearMap.size()+2;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<yearMap.size()+2;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int stcode = 0;
	        int rowno  = 8;
	        for(PMKSYBean bean: list) {
	        	
	        	if(bean.getStcode()!=stcode) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getStname());
	        		int i =2;
	        		for(Map.Entry<String, BigInteger> map : yrStList.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getStcode() == st) {
	        				row.createCell(i).setCellValue(Math.round(map.getValue().doubleValue()));
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	stcode = bean.getStcode();
	        	
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
	        
			
			
	        Row row = sheet.createRow(stcnt+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigInteger> map : totMap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()));
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, stcnt, yearMap.size()+2);
	        String fileName = "attachment; filename=Report O12- State Farmer.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "";
		
	}
	
	
	@RequestMapping(value = "/getDistWiseFrmrExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getDistWiseFrmrExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		String stname = request.getParameter("stname");
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		List<PMKSYBean> list = pmksyService.getDistWiseBenifitedFarmersNo(stcode);
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(stcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigInteger> yrStList = new LinkedHashMap<>();
		Map<String,BigInteger> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : distMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigInteger.ZERO);
				totMap.put(ymap.getValue(),BigInteger.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :list){
			if(s.getDcode()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getDcode()+","+s.getYrdesc()) ) {
				yrStList.put(s.getDcode()+","+s.getYrdesc(),yrStList.get(s.getDcode()+","+s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			if(totMap.containsKey(s.getYrdesc()) ) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			stcd = s.getDcode();
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O12 - District Wise No. of Farmers Benefitted Under Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O12 - District Wise No. of Farmers Benefitted Under Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)  for "+stname+" State";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, yearMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,yearMap.size()+1); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(stcnt+8,stcnt+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Benefitted Farmers(No.)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i=3;i<yearMap.size()+2;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<yearMap.size()+2;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int stcod = 0;
	        int rowno  = 8;
	        for(PMKSYBean bean: list) {
	        	
	        	if(bean.getDcode()!=stcod) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getDistname());
	        		int i =2;
	        		for(Map.Entry<String, BigInteger> map : yrStList.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getDcode() == st) {
	        				row.createCell(i).setCellValue(Math.round(map.getValue().doubleValue()));
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	stcod = bean.getDcode();
	        	
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
	        
			
			
	        Row row = sheet.createRow(stcnt+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigInteger> map : totMap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()));
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, stcnt, yearMap.size()+2);
	        String fileName = "attachment; filename=Report O12- District Farmer.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "";
		
	}
	
	@RequestMapping(value = "/getProjWiseFrmrExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getProjWiseFrmrExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		String distname = request.getParameter("distname");
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		List<PMKSYBean> list = pmksyService.getProjWiseBenifitedFarmersNo(dcode);
		LinkedHashMap<Integer,String> projMap = physicalActionPlanService.getProjectPhysicalActionPlan(dcode);
		Map<Integer,String> yearMap = pAAservices.getYearForPhysicalActionAchievementReport(0);
		Map<String,BigInteger> yrStList = new LinkedHashMap<>();
		Map<String,BigInteger> totMap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> dmap : projMap.entrySet()) {
			for(Map.Entry<Integer,String> ymap : yearMap.entrySet()) {
				yrStList.put(dmap.getKey()+","+ymap.getValue(),BigInteger.ZERO);
				totMap.put(ymap.getValue(),BigInteger.ZERO);
			}
		}
		
		int stcnt = 0;
		int stcd = 0;
		for(PMKSYBean s :list){
			if(s.getProjid()!=stcd) {
				stcnt++;
			}
			if(yrStList.containsKey(s.getProjid()+","+s.getYrdesc())) {
				yrStList.put(s.getProjid()+","+s.getYrdesc(),yrStList.get(s.getProjid()+","+s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			if(totMap.containsKey(s.getYrdesc())) {
				totMap.put(s.getYrdesc(),totMap.get(s.getYrdesc()).add(s.getBnftdfrmrs()));
			}
			stcd = s.getProjid();
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O12 - Project Wise Wise No. of Farmers Benefitted Under Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY)");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O12 - Project Wise Wise No. of Farmers Benefitted Under Prime Minister Agricultural Irrigation Scheme(WDC - PMKSY) for "+distname+" District of "+stname+" State";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, yearMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,yearMap.size()+1); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(stcnt+8,stcnt+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Benefitted Farmers(No.)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i=3;i<yearMap.size()+2;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<Integer, String> map : yearMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<yearMap.size()+2;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int stcod = 0;
	        int rowno  = 8;
	        for(PMKSYBean bean: list) {
	        	
	        	if(bean.getProjid()!=stcod) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getProjname());
	        		int i =2;
	        		for(Map.Entry<String, BigInteger> map : yrStList.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getProjid() == st) {
	        				row.createCell(i).setCellValue(Math.round(map.getValue().doubleValue()));
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	stcod = bean.getProjid();
	        	
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
	        
			
			
	        Row row = sheet.createRow(stcnt+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigInteger> map : totMap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()));
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, stcnt, yearMap.size()+2);
	        String fileName = "attachment; filename=Report O12- Project Farmer.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "";
		
	}
	
}
