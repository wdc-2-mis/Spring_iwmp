package app.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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

import app.bean.AddOutcomeParaBean;
import app.bean.FPOReportBean;
import app.bean.Login;
import app.bean.PhysicalActionPlanBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.model.WdcpmksyMQuadIndicators;
import app.service.FinYrServices;
import app.service.PhysicalActionPlanService;
import app.service.ProjectMasterService;
import app.service.master.OutcomeMasterServices;
import app.service.outcome.AdditionalBroughtFarmerCropAreaServices;

@Controller("addOutcomeParameterController")
public class AddOutcomeParameterController {

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

	@RequestMapping(value = "/outcomeaddpara", method = RequestMethod.GET)
	public ModelAndView outcomeaddpara(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		 String project = request.getParameter("project");
		 String month = request.getParameter("month");
		 String financial = request.getParameter("financial");
		 LinkedHashMap<Integer, List<AddOutcomeParaBean>> draft = null;
		 LinkedHashMap<Integer, List<AddOutcomeParaBean>> complete = null;
		 LinkedHashMap<Integer,List<AddOutcomeParaBean>>indicators = null;
		 if(session!=null && session.getAttribute("loginID")!=null) {
			int regId = (int)session.getAttribute("regId");
			mav = new ModelAndView("outcomeaddpara");
			if(project!= null && financial!=null) {
				indicators = outcomeserv.getoutcomeDegradedData(project);
				System.out.println("value of indicators:" +indicators);
				draft = outcomeserv.getOutcomeparadraft(Integer.parseInt(project), Integer.parseInt(month), Integer.parseInt(financial));
				complete = outcomeserv.getOutcomeparacomplete(Integer.parseInt(project), Integer.parseInt(month), Integer.parseInt(financial));
				if(draft.isEmpty())
				{
					draft = null;
				}
				if(complete.isEmpty())
				{
					complete = null;
				}
			}
			/*
			 * if(financial != null) { mav.addObject("financialYear",
			 * physicalActionPlanService.getFinYearProjectWise(Integer.parseInt(project)));
			 * }
			 */
			
			mav.addObject("financialYear", Ser.getFinYearonward22());
			mav.addObject("projectList",  projectMasterService.getProjectByRegId(regId));
			mav.addObject("monthList",  outcomeserv.getmonthcode());
			mav.addObject("outcomedesc", outcomeserv.getoutcomedesc());
			mav.addObject("project", project);
			mav.addObject("financial", financial);
			mav.addObject("month", month);
			mav.addObject("indi", indicators);
			mav.addObject("draft", draft);
			mav.addObject("complete", complete);
        	
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());

		}
		return mav; 
	}


	@RequestMapping(value = "/saveOutcomePara", method = RequestMethod.POST)
	@ResponseBody
	public String saveOutcomePara(HttpServletRequest request, HttpServletResponse response, @RequestParam("projId")Integer projId, @RequestParam("month")Integer month,
			@RequestParam("year")Integer year, @RequestParam("degradedrainf")BigDecimal degradedrainf,  @RequestParam("noofman")String noofman, @RequestParam("sc")String sc,
			@RequestParam("st")String st, @RequestParam("Others")String Others, @RequestParam("female")String female, @RequestParam("smallfarmer")String smallfarmer, @RequestParam("marginalfarmer")String marginalfarmer,
			@RequestParam("landless")String landless, @RequestParam("bpl")String bpl, @RequestParam("outcome2_id")Integer outcome2id, @RequestParam("status")Character status)
	{
		System.out.println("hello");
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		String res = new String("fail");
		if(session!=null && session.getAttribute("loginID")!=null) {
			res  = outcomeserv.draftOutcomeParam(projId, month, year, degradedrainf, noofman, sc, st, Others, female, smallfarmer, marginalfarmer, landless, bpl, outcome2id, status, session.getAttribute("loginID").toString());
		}
		return res;
	}

	/*
	 * @RequestMapping(value="/fetchOutcomeParam", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public LinkedHashMap<Integer,List<AddOutcomeParaBean>>
	 * fetchOutcomePara(HttpServletRequest request, HttpServletResponse
	 * response,@RequestParam(value ="projId") Integer projectId,
	 * 
	 * @RequestParam(value="month") Integer month, @RequestParam(value="year")
	 * Integer year) { session = request.getSession(true); ModelAndView mav = new
	 * ModelAndView(); LinkedHashMap<Integer,List<AddOutcomeParaBean>> map = new
	 * LinkedHashMap<Integer,List<AddOutcomeParaBean>>(); List<AddOutcomeParaBean>
	 * proj = new ArrayList<AddOutcomeParaBean>(); List<AddOutcomeParaBean> sublist
	 * = new ArrayList<AddOutcomeParaBean>(); if(session!=null &&
	 * session.getAttribute("loginID")!=null) { proj =
	 * outcomeserv.getOutcomeparadraft(projectId, month, year);
	 * 
	 * for(AddOutcomeParaBean bean: proj) { if
	 * (!map.containsKey(bean.getOutcome2_id())) { sublist = new
	 * ArrayList<AddOutcomeParaBean>(); sublist.add(bean);
	 * map.put(bean.getOutcome2_id(), sublist); } else {
	 * sublist=(map.get(bean.getOutcome2_id())); sublist.add(bean);
	 * map.put(bean.getOutcome2_id(), sublist); }
	 * 
	 * }
	 * 
	 * 
	 * }else { proj=null;
	 * 
	 * } return map; }
	 */

	@RequestMapping(value="/outcomefinaldata", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<AddOutcomeParaBean>> outcomefinaldata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="projId") Integer projectId, 
			@RequestParam(value="month") Integer month, @RequestParam(value="year") Integer year)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,List<AddOutcomeParaBean>> map = new LinkedHashMap<Integer,List<AddOutcomeParaBean>>();
		List<AddOutcomeParaBean> proj = new ArrayList<AddOutcomeParaBean>();
		List<AddOutcomeParaBean> sublist = new ArrayList<AddOutcomeParaBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			proj = outcomeserv.getoutcomefinaldata(projectId, month, year);

			for(AddOutcomeParaBean bean: proj) 
			{ 
				if (!map.containsKey(bean.getOutcome2_id()))
				{ 
					sublist = new ArrayList<AddOutcomeParaBean>(); 
					sublist.add(bean);
					map.put(bean.getOutcome2_id(), sublist); 
				} 
				else 
				{
					sublist=(map.get(bean.getOutcome2_id())); 
					sublist.add(bean);
					map.put(bean.getOutcome2_id(), sublist); }

			}


		}else {
			proj=null;

		}
		return map;  
	}


	@RequestMapping(value="/delOutcomeParadraftdata", method = RequestMethod.POST)
	@ResponseBody
	public String delOutcomeParadraftdata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="draftid") Integer draftid)
	{
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=outcomeserv.detOutcomedraftdata(draftid);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}

	@RequestMapping(value="/completeOutcomeParadraftdata", method = RequestMethod.POST)
	@ResponseBody
	public String completeOutcomeParadraftdata(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="draftid") Integer draftid)
	{
		ModelAndView mav = new ModelAndView();
		String res = new String("fail");
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			res=outcomeserv.finalSaveOutcomeParadraftdata(draftid);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}

	@RequestMapping(value = "/getStateWiseStatusAdditionalParameter", method = RequestMethod.GET)
	public ModelAndView getstateWiseAdditionalParameter(HttpServletRequest request, HttpServletResponse response) {
		finyear=request.getParameter("fyear");
		ModelAndView mav = new ModelAndView();
		List<AddOutcomeParaBean> list = new ArrayList<AddOutcomeParaBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/stateWiseAdditionalParameter");
			mav.addObject("yearList",finYrServices.getAllFinYear());	
			mav.addObject("netTreatledata",outcomeserv.getstateWiseAdditionalParameter(finyear));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			for (List<AddOutcomeParaBean> l : outcomeserv.getstateWiseAdditionalParameter(finyear).values())

				for(AddOutcomeParaBean bean : l){


					dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getMan_day();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getSc();
					dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getSt();
					dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getOther();
					dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTotal();
					dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getFemale();
					dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getSmall();
					dataArrNetTotal[7] = dataArrNetTotal[7] + bean.getMirginal();
					dataArrNetTotal[8] = dataArrNetTotal[8] + bean.getLandless();
					dataArrNetTotal[9] = dataArrNetTotal[9] + bean.getBpl();

				}
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotal[0].toString();
			dataArrNetTotalStr[1] = dataArrNetTotal[1].toString();
			dataArrNetTotalStr[2] = dataArrNetTotal[2].toString();
			dataArrNetTotalStr[3] = dataArrNetTotal[3].toString();
			dataArrNetTotalStr[4] = dataArrNetTotal[4].toString();
			dataArrNetTotalStr[5] = dataArrNetTotal[5].toString();
			dataArrNetTotalStr[6] = dataArrNetTotal[6].toString();
			dataArrNetTotalStr[7] = dataArrNetTotal[7].toString();
			dataArrNetTotalStr[8] = dataArrNetTotal[8].toString();
			dataArrNetTotalStr[9] = dataArrNetTotal[9].toString();

			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			mav.addObject("fyear", finyear);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/getDistWiseStatusAdditionalParameter", method = RequestMethod.GET)
	public ModelAndView getDistWiseStatusAdditionalParameter(HttpServletRequest request, HttpServletResponse response) {
		finyear=request.getParameter("fyear");
		String finYrName = finyear.equals("")?"All":request.getParameter("fyrname");
		String stcd=request.getParameter("stcode");
		ModelAndView mav = new ModelAndView();
		List<AddOutcomeParaBean> list = new ArrayList<AddOutcomeParaBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		String stname="";
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/districtWiseAdditionalParameter");
			mav.addObject("yearList",finYrServices.getAllFinYear());	
			mav.addObject("netTreatledata",outcomeserv.getDistWiseStatusAdditionalParameter(stcd, finyear));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			for (List<AddOutcomeParaBean> l : outcomeserv.getDistWiseStatusAdditionalParameter(stcd, finyear).values())

				for(AddOutcomeParaBean bean : l){

					stname=bean.getSt_name();
					dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getMan_day();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getSc();
					dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getSt();
					dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getOther();
					dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTotal();
					dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getFemale();
					dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getSmall();
					dataArrNetTotal[7] = dataArrNetTotal[7] + bean.getMirginal();
					dataArrNetTotal[8] = dataArrNetTotal[8] + bean.getLandless();
					dataArrNetTotal[9] = dataArrNetTotal[9] + bean.getBpl();

				}
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotal[0].toString();
			dataArrNetTotalStr[1] = dataArrNetTotal[1].toString();
			dataArrNetTotalStr[2] = dataArrNetTotal[2].toString();
			dataArrNetTotalStr[3] = dataArrNetTotal[3].toString();
			dataArrNetTotalStr[4] = dataArrNetTotal[4].toString();
			dataArrNetTotalStr[5] = dataArrNetTotal[5].toString();
			dataArrNetTotalStr[6] = dataArrNetTotal[6].toString();
			dataArrNetTotalStr[7] = dataArrNetTotal[7].toString();
			dataArrNetTotalStr[8] = dataArrNetTotal[8].toString();
			dataArrNetTotalStr[9] = dataArrNetTotal[9].toString();

			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			mav.addObject("fyear", finyear);
			mav.addObject("finYrName",finYrName);
			mav.addObject("stname", stname);
			mav.addObject("stcode", stcd);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/getProjWiseStatusAdditionalParameter", method = RequestMethod.GET)
	public ModelAndView getProjWiseStatusAdditionalParameter(HttpServletRequest request, HttpServletResponse response) {
		finyear=request.getParameter("fyear");
		String dcode=request.getParameter("dcode");
		String finYrName = finyear.equals("")?"All":request.getParameter("fyrname");
		ModelAndView mav = new ModelAndView();
		List<AddOutcomeParaBean> list = new ArrayList<AddOutcomeParaBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		String stname="";
		String distname="";
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/projectWiseAdditionalParameter");
			mav.addObject("yearList",finYrServices.getAllFinYear());	
			mav.addObject("netTreatledata",outcomeserv.getPorjWiseStatusAdditionalParameter(dcode, finyear));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			for (List<AddOutcomeParaBean> l : outcomeserv.getPorjWiseStatusAdditionalParameter(dcode, finyear).values())

				for(AddOutcomeParaBean bean : l){

					stname=bean.getSt_name();
					distname=bean.getDist_name();
					dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getMan_day();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getSc();
					dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getSt();
					dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getOther();
					dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTotal();
					dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getFemale();
					dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getSmall();
					dataArrNetTotal[7] = dataArrNetTotal[7] + bean.getMirginal();
					dataArrNetTotal[8] = dataArrNetTotal[8] + bean.getLandless();
					dataArrNetTotal[9] = dataArrNetTotal[9] + bean.getBpl();

				}
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotal[0].toString();
			dataArrNetTotalStr[1] = dataArrNetTotal[1].toString();
			dataArrNetTotalStr[2] = dataArrNetTotal[2].toString();
			dataArrNetTotalStr[3] = dataArrNetTotal[3].toString();
			dataArrNetTotalStr[4] = dataArrNetTotal[4].toString();
			dataArrNetTotalStr[5] = dataArrNetTotal[5].toString();
			dataArrNetTotalStr[6] = dataArrNetTotal[6].toString();
			dataArrNetTotalStr[7] = dataArrNetTotal[7].toString();
			dataArrNetTotalStr[8] = dataArrNetTotal[8].toString();
			dataArrNetTotalStr[9] = dataArrNetTotal[9].toString();

			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			mav.addObject("fyear", finyear);
			mav.addObject("finYrName",finYrName);
			mav.addObject("stname", stname);
			mav.addObject("distname", distname);
			mav.addObject("dcode", dcode);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/downloadAdditionalParameterPDF", method = RequestMethod.POST)
	public ModelAndView downloadAdditionalParameterPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String finyear= request.getParameter("finyear");
		String finYrName=request.getParameter("finYrName");
		finYrName = finYrName.equals("--All--")?"All":finYrName;
		List<AddOutcomeParaBean> outComeBean = new ArrayList<AddOutcomeParaBean>();

		try {


			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("OutcomeReport");
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

			paragraph3 = new Paragraph("Report O1- State wise Mandays Generated and Farmers benefitted Details for "+finYrName+ " Financial Year.", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			table = new PdfPTable(12);
			table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);
			
			int man_day=0,sc=0,st=0,other=0, total=0, female=0,small=0, mirginal=0,landless=0, bpl=0;

			for (List<AddOutcomeParaBean> l : outcomeserv.getstateWiseAdditionalParameter(finyear).values())
				for(AddOutcomeParaBean bean : l){

					outComeBean.add(bean);
				}
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of Mandays generated(in mandays)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No of Farmers benefitted", Element.ALIGN_CENTER, 9, 1, bf8Bold);

			CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 1,bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Female", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Small Farmers", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Marginal Farmers", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Landless", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "BPL", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			int k=1;
			if(outComeBean.size()!=0)
				for(int i=0;i<outComeBean.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, outComeBean.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeBean.get(i).getMan_day()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeBean.get(i).getSc()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeBean.get(i).getSt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeBean.get(i).getOther()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeBean.get(i).getTotal()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeBean.get(i).getFemale()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeBean.get(i).getSmall()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeBean.get(i).getMirginal()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeBean.get(i).getLandless()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeBean.get(i).getBpl()), Element.ALIGN_RIGHT, 1, 1, bf8);

					k=k+1;
					man_day=man_day+outComeBean.get(i).getMan_day();
					sc=sc+outComeBean.get(i).getSc();
					st=st+outComeBean.get(i).getSt();
					other=other+outComeBean.get(i).getOther();
					total=total+outComeBean.get(i).getTotal();
					female=female+outComeBean.get(i).getFemale();
					small=small+outComeBean.get(i).getSmall();
					mirginal=mirginal+outComeBean.get(i).getMirginal();
					landless=landless+outComeBean.get(i).getLandless();
					bpl=bpl+outComeBean.get(i).getBpl();


				}
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(man_day), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(female), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(small), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(mirginal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(landless), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(bpl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);


			if(outComeBean.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 12, 1, bf8);


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
			response.setHeader("Content-Disposition", "attachment;filename=O1-Report.pdf");
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

	@RequestMapping(value = "/downloadDistrictAdditionalParameterPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictAdditionalParameterPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String check= finyear;
		String stcd=request.getParameter("stcode");
		String stname=request.getParameter("stname");
		String finYrName=request.getParameter("finYrName");
		finYrName = finYrName.equals("--All--")?"All":finYrName;
		List<AddOutcomeParaBean> outComedistBean = new ArrayList<AddOutcomeParaBean>();

		try {


			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("OutcomeDistrictReport");
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

			paragraph3 = new Paragraph("Report O1- District wise Mandays Generated and Farmers benefitted Details for "+finYrName+ " Financial Year.", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			table = new PdfPTable(12);
			table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);

			int man_day=0,sc=0,st=0,other=0, total=0, female=0,small=0, mirginal=0,landless=0, bpl=0;

			for (List<AddOutcomeParaBean> l : outcomeserv.getDistWiseStatusAdditionalParameter(stcd, finyear).values())
				for(AddOutcomeParaBean bean : l){
					outComedistBean.add(bean);
				}
			CommonFunctions.insertCellHeader(table, "State Name:"+stname, Element.ALIGN_LEFT, 12, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of Mandays generated(in mandays)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No of Farmers benefitted", Element.ALIGN_CENTER, 9, 1, bf8Bold);

			CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 1,bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Female", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Small Farmers", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Marginal Farmers", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Landless", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "BPL", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			int k=1;
			if(outComedistBean.size()!=0)
				for(int i=0;i<outComedistBean.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, outComedistBean.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComedistBean.get(i).getMan_day()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComedistBean.get(i).getSc()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComedistBean.get(i).getSt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComedistBean.get(i).getOther()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComedistBean.get(i).getTotal()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComedistBean.get(i).getFemale()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComedistBean.get(i).getSmall()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComedistBean.get(i).getMirginal()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComedistBean.get(i).getLandless()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComedistBean.get(i).getBpl()), Element.ALIGN_RIGHT, 1, 1, bf8);

					k=k+1;
					man_day=man_day+outComedistBean.get(i).getMan_day();
					sc=sc+outComedistBean.get(i).getSc();
					st=st+outComedistBean.get(i).getSt();
					other=other+outComedistBean.get(i).getOther();
					total=total+outComedistBean.get(i).getTotal();
					female=female+outComedistBean.get(i).getFemale();
					small=small+outComedistBean.get(i).getSmall();
					mirginal=mirginal+outComedistBean.get(i).getMirginal();
					landless=landless+outComedistBean.get(i).getLandless();
					bpl=bpl+outComedistBean.get(i).getBpl();


				}
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(man_day), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(female), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(small), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(mirginal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(landless), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(bpl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);


			if(outComedistBean.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 12, 1, bf8);


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
			response.setHeader("Content-Disposition", "attachment;filename=O1-DistrictReport.pdf");
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
	

	@RequestMapping(value = "/downloadProjectWiseAdditionalParameterPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectWiseAdditionalParameterPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String check= finyear;
		String stname=request.getParameter("stname");
		String distname=request.getParameter("distname");
		String dcode=request.getParameter("dcode");
		String finYrName=request.getParameter("finYrName");
		finYrName = finYrName.equals("--All--")?"All":finYrName;
		List<AddOutcomeParaBean> outComeProjBean = new ArrayList<AddOutcomeParaBean>();

		try {


			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("OutcomeProjectReport");
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

			paragraph3 = new Paragraph("Report O1- Project wise Mandays Generated and Farmers benefitted Details for "+finYrName+ " Financial Year", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			table = new PdfPTable(12);
			table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);

			int man_day=0,sc=0,st=0,other=0, total=0, female=0,small=0, mirginal=0,landless=0, bpl=0;
			
			for (List<AddOutcomeParaBean> l : outcomeserv.getPorjWiseStatusAdditionalParameter(dcode, finyear).values())

				for(AddOutcomeParaBean bean : l){
					outComeProjBean.add(bean);
				}
			CommonFunctions.insertCellHeader(table, "State Name :"  + stname +  " District Name :" + distname, Element.ALIGN_LEFT, 12, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of Mandays generated(in mandays)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No of Farmers benefitted", Element.ALIGN_CENTER, 9, 1, bf8Bold);

			CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 1,bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Female", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Small Farmers", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Marginal Farmers", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Landless", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "BPL", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			int k=1;
			if(outComeProjBean.size()!=0)
				for(int i=0;i<outComeProjBean.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, outComeProjBean.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeProjBean.get(i).getMan_day()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeProjBean.get(i).getSc()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeProjBean.get(i).getSt()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeProjBean.get(i).getOther()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeProjBean.get(i).getTotal()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeProjBean.get(i).getFemale()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeProjBean.get(i).getSmall()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeProjBean.get(i).getMirginal()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeProjBean.get(i).getLandless()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, Integer.toString(outComeProjBean.get(i).getBpl()), Element.ALIGN_RIGHT, 1, 1, bf8);

					k=k+1;
					man_day=man_day+outComeProjBean.get(i).getMan_day();
					sc=sc+outComeProjBean.get(i).getSc();
					st=st+outComeProjBean.get(i).getSt();
					other=other+outComeProjBean.get(i).getOther();
					total=total+outComeProjBean.get(i).getTotal();
					female=female+outComeProjBean.get(i).getFemale();
					small=small+outComeProjBean.get(i).getSmall();
					mirginal=mirginal+outComeProjBean.get(i).getMirginal();
					landless=landless+outComeProjBean.get(i).getLandless();
					bpl=bpl+outComeProjBean.get(i).getBpl();


				}
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(man_day), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(female), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(small), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(mirginal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(landless), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(bpl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);


			if(outComeProjBean.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 12, 1, bf8);


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
			response.setHeader("Content-Disposition", "attachment;filename=O1-ProjectReport.pdf");
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
	
	@RequestMapping(value = "/getStatewiseMandaysExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getStatewiseMandays(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		finyear=request.getParameter("finyear");
		String finYrName=request.getParameter("finYrName");
		finYrName = finYrName.equals("--All--")?"All":finYrName;
		
		List<AddOutcomeParaBean> beanList = new ArrayList<AddOutcomeParaBean>();
		
		for (List<AddOutcomeParaBean> l : outcomeserv.getstateWiseAdditionalParameter(finyear).values()) {

			for(AddOutcomeParaBean bean : l){
				beanList.add(bean);
			}
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O1- State wise Mandays Generated and Farmers benefitted Details");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O1- State wise Mandays Generated and Farmers benefitted Details for "+finYrName+ " Financial Year.";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,2,2);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,3,11); 
	        sheet.addMergedRegion(mergedRegion);
	        	
	        
	        mergedRegion = new CellRangeAddress(beanList.size()+8,beanList.size()+8,0,1); 
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
			cell.setCellValue("No. of Mandays generated (in mandays)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(3);
			cell.setCellValue("No. of Farmers benefitted");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i=4;i<12;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
	        
			Row rowhead1 = sheet.createRow(6);
			for(int i=0;i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			cell = rowhead1.createCell(3);
			cell.setCellValue("SC");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(4);
			cell.setCellValue("ST");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("Others");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellValue("Female");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("Small Farmers");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(9);
			cell.setCellValue("Marginal Farmers");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(10);
			cell.setCellValue("Landless");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(11);
			cell.setCellValue("BPL");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<12;i++) {
				cell = rowhead2.createCell(i);
				cell.setCellValue(i+1);  
				cell.setCellStyle(style);
			}
			
	        int rowno  =8;
	        double mandayTot = 0.0;
	        double scTot = 0.0;
	        double stTot = 0.0;
	        double othrTot = 0.0;
	        double femaleTot = 0.0;
	        double smallTot = 0.0;
	        double mirgnlTot = 0.0;
	        double lndlsTot = 0.0;
	        double bplTot = 0.0;
	        double totTot = 0.0;
	        
	        for(AddOutcomeParaBean bean: beanList) {
	        	Row row = sheet.createRow(rowno);
	        	cell = row.createCell(0);
	        	cell.setCellValue(rowno-7);
	        	cell = row.createCell(1);
	        	cell.setCellValue(bean.getSt_name());
	        	cell = row.createCell(2);
	        	cell.setCellValue(bean.getMan_day());
	        	cell = row.createCell(3);
	        	cell.setCellValue(bean.getSc());
	        	cell = row.createCell(4);
	        	cell.setCellValue(bean.getSt());
	        	cell = row.createCell(5);
	        	cell.setCellValue(bean.getOther());
	        	cell = row.createCell(6);
	        	cell.setCellValue(bean.getTotal());
	        	cell = row.createCell(7);
	        	cell.setCellValue(bean.getFemale());
	        	cell = row.createCell(8);
	        	cell.setCellValue(bean.getSmall());
	        	cell = row.createCell(9);
	        	cell.setCellValue(bean.getMirginal());
	        	cell = row.createCell(10);
	        	cell.setCellValue(bean.getLandless());
	        	cell = row.createCell(11);
	        	cell.setCellValue(bean.getBpl());
	        	
	        	mandayTot = mandayTot + bean.getMan_day();
	        	scTot = scTot + bean.getSc();
	        	stTot = stTot + bean.getSt();
	        	othrTot = othrTot + bean.getOther();
	        	totTot = totTot + bean.getTotal();
	        	femaleTot = femaleTot + bean.getFemale();
	        	smallTot = smallTot + bean.getSmall();
	        	mirgnlTot = mirgnlTot + bean.getMirginal();
	        	lndlsTot = lndlsTot + bean.getLandless();
	        	bplTot = bplTot + bean.getBpl();
	        	rowno++;
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
			
			Row rowtot = sheet.createRow(beanList.size()+8);
			cell = rowtot.createCell(0);
			cell.setCellValue("Grand Total");
			cell.setCellStyle(style1);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			rowtot.createCell(1).setCellStyle(style1);
			cell = rowtot.createCell(2);
			cell.setCellValue(mandayTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(3);
			cell.setCellValue(scTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(4);
			cell.setCellValue(stTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(5);
			cell.setCellValue(othrTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(6);
			cell.setCellValue(totTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(7);
			cell.setCellValue(femaleTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(8);
			cell.setCellValue(smallTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(9);
			cell.setCellValue(mirgnlTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(10);
			cell.setCellValue(lndlsTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(11);
			cell.setCellValue(bplTot);
			cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, beanList.size(), 11);
	        String fileName = "attachment; filename=Report O1 - State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/stateWiseAdditionalParameter";
		
	}
	
	@RequestMapping(value = "/getDistWiseMandaysExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getDistrictwiseMandays(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		
		String stcode = request.getParameter("stcode");
		String stName = request.getParameter("stname");
		finyear=request.getParameter("finyear");
		String finYrName=request.getParameter("finYrName");
		finYrName = finYrName.equals("--All--")?"All":finYrName;
		
		List<AddOutcomeParaBean> beanList = new ArrayList<AddOutcomeParaBean>();
		
		for (List<AddOutcomeParaBean> l : outcomeserv.getDistWiseStatusAdditionalParameter(stcode, finyear).values()) {

			for(AddOutcomeParaBean bean : l){
				beanList.add(bean);
			}
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O1- District wise Mandays Generated and Farmers benefitted Details");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O1- District wise Mandays Generated and Farmers benefitted Details for "+finYrName+ " Financial Year.";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,11);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,2,2);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,11); 
	        sheet.addMergedRegion(mergedRegion);
	        	
	        
	        mergedRegion = new CellRangeAddress(beanList.size()+9,beanList.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row rowhead0 = sheet.createRow(5);
	        Cell cell = rowhead0.createCell(0);
	        cell.setCellValue("State Name :   "+stName);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
	        
	        Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(2);
			cell.setCellValue("No. of Mandays generated (in mandays)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(3);
			cell.setCellValue("No. of Farmers benefitted");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i=4;i<12;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
	        
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			cell = rowhead1.createCell(3);
			cell.setCellValue("SC");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(4);
			cell.setCellValue("ST");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("Others");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellValue("Female");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("Small Farmers");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(9);
			cell.setCellValue("Marginal Farmers");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(10);
			cell.setCellValue("Landless");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(11);
			cell.setCellValue("BPL");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(8);
			for(int i =0; i<12;i++) {
				cell = rowhead2.createCell(i);
				cell.setCellValue(i+1);  
				cell.setCellStyle(style);
			}
			
	        int rowno  =9;
	        double mandayTot = 0.0;
	        double scTot = 0.0;
	        double stTot = 0.0;
	        double othrTot = 0.0;
	        double femaleTot = 0.0;
	        double smallTot = 0.0;
	        double mirgnlTot = 0.0;
	        double lndlsTot = 0.0;
	        double bplTot = 0.0;
	        double totTot = 0.0;
	        
	        for(AddOutcomeParaBean bean: beanList) {
	        	Row row = sheet.createRow(rowno);
	        	cell = row.createCell(0);
	        	cell.setCellValue(rowno-8);
	        	cell = row.createCell(1);
	        	cell.setCellValue(bean.getDist_name());
	        	cell = row.createCell(2);
	        	cell.setCellValue(bean.getMan_day());
	        	cell = row.createCell(3);
	        	cell.setCellValue(bean.getSc());
	        	cell = row.createCell(4);
	        	cell.setCellValue(bean.getSt());
	        	cell = row.createCell(5);
	        	cell.setCellValue(bean.getOther());
	        	cell = row.createCell(6);
	        	cell.setCellValue(bean.getTotal());
	        	cell = row.createCell(7);
	        	cell.setCellValue(bean.getFemale());
	        	cell = row.createCell(8);
	        	cell.setCellValue(bean.getSmall());
	        	cell = row.createCell(9);
	        	cell.setCellValue(bean.getMirginal());
	        	cell = row.createCell(10);
	        	cell.setCellValue(bean.getLandless());
	        	cell = row.createCell(11);
	        	cell.setCellValue(bean.getBpl());
	        	
	        	mandayTot = mandayTot + bean.getMan_day();
	        	scTot = scTot + bean.getSc();
	        	stTot = stTot + bean.getSt();
	        	othrTot = othrTot + bean.getOther();
	        	totTot = totTot + bean.getTotal();
	        	femaleTot = femaleTot + bean.getFemale();
	        	smallTot = smallTot + bean.getSmall();
	        	mirgnlTot = mirgnlTot + bean.getMirginal();
	        	lndlsTot = lndlsTot + bean.getLandless();
	        	bplTot = bplTot + bean.getBpl();
	        	rowno++;
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
			
			Row rowtot = sheet.createRow(beanList.size()+9);
			cell = rowtot.createCell(0);
			cell.setCellValue("Grand Total");
			cell.setCellStyle(style1);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			rowtot.createCell(1).setCellStyle(style1);
			cell = rowtot.createCell(2);
			cell.setCellValue(mandayTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(3);
			cell.setCellValue(scTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(4);
			cell.setCellValue(stTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(5);
			cell.setCellValue(othrTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(6);
			cell.setCellValue(totTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(7);
			cell.setCellValue(femaleTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(8);
			cell.setCellValue(smallTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(9);
			cell.setCellValue(mirgnlTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(10);
			cell.setCellValue(lndlsTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(11);
			cell.setCellValue(bplTot);
			cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, beanList.size(), 11);
	        String fileName = "attachment; filename=Report O1 - District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/districtWiseAdditionalParameter";
		
	}
	
	
	@RequestMapping(value = "/getProjectwiseMandaysExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getProjectwiseMandays(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		
		String dcode = request.getParameter("dcode");
		String stName = request.getParameter("stname");
		String distName = request.getParameter("distname");
		finyear=request.getParameter("finyear");
		String finYrName=request.getParameter("finYrName");
		finYrName = finYrName.equals("--All--")?"All":finYrName;
		
		List<AddOutcomeParaBean> beanList = new ArrayList<AddOutcomeParaBean>();
		
		for (List<AddOutcomeParaBean> l : outcomeserv.getPorjWiseStatusAdditionalParameter(dcode, finyear).values()) {

			for(AddOutcomeParaBean bean : l){
				beanList.add(bean);
			}
		}
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O1- Project wise Mandays Generated and Farmers benefitted Details");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O1- Project wise Mandays Generated and Farmers benefitted Details for "+finYrName+ " Financial Year";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,11);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,2,2);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,11); 
	        sheet.addMergedRegion(mergedRegion);
	        	
	        
	        mergedRegion = new CellRangeAddress(beanList.size()+9,beanList.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row rowhead0 = sheet.createRow(5);
	        Cell cell = rowhead0.createCell(0);
	        cell.setCellValue("State Name :   "+stName + "  District Name :  " +distName);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
	        
	        Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(2);
			cell.setCellValue("No. of Mandays generated (in mandays)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(3);
			cell.setCellValue("No. of Farmers benefitted");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i=4;i<12;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
	        
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			cell = rowhead1.createCell(3);
			cell.setCellValue("SC");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(4);
			cell.setCellValue("ST");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("Others");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellValue("Female");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("Small Farmers");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(9);
			cell.setCellValue("Marginal Farmers");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(10);
			cell.setCellValue("Landless");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(11);
			cell.setCellValue("BPL");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(8);
			for(int i =0; i<12;i++) {
				cell = rowhead2.createCell(i);
				cell.setCellValue(i+1);  
				cell.setCellStyle(style);
			}
			
	        int rowno  =9;
	        double mandayTot = 0.0;
	        double scTot = 0.0;
	        double stTot = 0.0;
	        double othrTot = 0.0;
	        double femaleTot = 0.0;
	        double smallTot = 0.0;
	        double mirgnlTot = 0.0;
	        double lndlsTot = 0.0;
	        double bplTot = 0.0;
	        double totTot = 0.0;
	        
	        for(AddOutcomeParaBean bean: beanList) {
	        	Row row = sheet.createRow(rowno);
	        	cell = row.createCell(0);
	        	cell.setCellValue(rowno-8);
	        	cell = row.createCell(1);
	        	cell.setCellValue(bean.getProj_name());
	        	cell = row.createCell(2);
	        	cell.setCellValue(bean.getMan_day());
	        	cell = row.createCell(3);
	        	cell.setCellValue(bean.getSc());
	        	cell = row.createCell(4);
	        	cell.setCellValue(bean.getSt());
	        	cell = row.createCell(5);
	        	cell.setCellValue(bean.getOther());
	        	cell = row.createCell(6);
	        	cell.setCellValue(bean.getTotal());
	        	cell = row.createCell(7);
	        	cell.setCellValue(bean.getFemale());
	        	cell = row.createCell(8);
	        	cell.setCellValue(bean.getSmall());
	        	cell = row.createCell(9);
	        	cell.setCellValue(bean.getMirginal());
	        	cell = row.createCell(10);
	        	cell.setCellValue(bean.getLandless());
	        	cell = row.createCell(11);
	        	cell.setCellValue(bean.getBpl());
	        	
	        	mandayTot = mandayTot + bean.getMan_day();
	        	scTot = scTot + bean.getSc();
	        	stTot = stTot + bean.getSt();
	        	othrTot = othrTot + bean.getOther();
	        	totTot = totTot + bean.getTotal();
	        	femaleTot = femaleTot + bean.getFemale();
	        	smallTot = smallTot + bean.getSmall();
	        	mirgnlTot = mirgnlTot + bean.getMirginal();
	        	lndlsTot = lndlsTot + bean.getLandless();
	        	bplTot = bplTot + bean.getBpl();
	        	rowno++;
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
			
			Row rowtot = sheet.createRow(beanList.size()+9);
			cell = rowtot.createCell(0);
			cell.setCellValue("Grand Total");
			cell.setCellStyle(style1);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			rowtot.createCell(1).setCellStyle(style1);
			cell = rowtot.createCell(2);
			cell.setCellValue(mandayTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(3);
			cell.setCellValue(scTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(4);
			cell.setCellValue(stTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(5);
			cell.setCellValue(othrTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(6);
			cell.setCellValue(totTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(7);
			cell.setCellValue(femaleTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(8);
			cell.setCellValue(smallTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(9);
			cell.setCellValue(mirgnlTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(10);
			cell.setCellValue(lndlsTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(11);
			cell.setCellValue(bplTot);
			cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, beanList.size(), 11);
	        String fileName = "attachment; filename=Report O1 - Project.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/projectWiseAdditionalParameter";
		
	}
}
