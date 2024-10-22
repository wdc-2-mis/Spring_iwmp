package app.controllers.Project;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
import app.bean.GroundWaterTableBean;
import app.bean.Login;
import app.bean.StateToVillageBean;
import app.bean.reports.UserFileUploadBean;
import app.bean.reports.UserUploadDetailsBean;
import app.common.CommonFunctions;
import app.service.DistrictMasterService;
import app.service.GroundWaterTableService;
import app.service.ProjectMasterService;
import app.service.SelfHelpGroupService;
import app.service.StateMasterService;
import app.service.UserService;

@Controller("GroundWaterTable")
public class GroundWaterTable {
	
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	GroundWaterTableService gwtService;
	
	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> ProjectList;

	@RequestMapping(value = "/groundWaterTable", method = RequestMethod.GET)
	public ModelAndView groundWaterTable(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("project/groundWaterTable");
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			userType.put("project","During Project");
			
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
			mav.addObject("yearList",gwtService.getFinYear());
			
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login"); 
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value = "/gwtDetaildata", method = RequestMethod.POST)
	public ModelAndView gwtDetaildataDC(HttpServletRequest request) {
		session = request.getSession(true);
		String project=request.getParameter("project");
		String groupType=request.getParameter("groupType");
		String fyear=request.getParameter("fyear");
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
		List<GroundWaterTableBean> list=new  ArrayList<GroundWaterTableBean>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			mav = new ModelAndView("project/groundWaterTable");
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			list=gwtService.gwtDetaildataDC(Integer.parseInt(project));
			userType.put("project","During Project");
			
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
			mav.addObject("yearList",gwtService.getFinYear());
			mav.addObject("dataList",list);
			mav.addObject("projectcd",project);
			mav.addObject("timof",groupType);
			mav.addObject("finy",fyear);
		}
		else {
			session.invalidate();
			mav = new ModelAndView("login"); 
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	@RequestMapping(value = "/deleteGWTdraft", method = RequestMethod.POST)
	public ModelAndView deleteGWTdraftdata(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		session = request.getSession(true);
		String stcode = session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String gwtid = request.getParameter("gwtid");
		String gwtdtlid = request.getParameter("gwtdtlid");
		String project=request.getParameter("project");
		String groupType=request.getParameter("groupType");
		String fyear=request.getParameter("fyear");
		HashMap<String,String> userType = new HashMap<String,String>();
		List<GroundWaterTableBean> list=new  ArrayList<GroundWaterTableBean>();
		boolean userUploadDeleteSLNA=false;
		Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("project/groundWaterTable");
			userUploadDeleteSLNA= gwtService.deleteGWTdraft(Integer.parseInt(gwtid), Integer.parseInt(gwtdtlid));
			if(userUploadDeleteSLNA ) 
					mav.addObject("messageGWT", "Data Deleted Successfully");
			else
					mav.addObject("messageGWT", "Data not Deleted!");
			
			list=gwtService.gwtDetaildataDC(Integer.parseInt(project));
			userType.put("project","During Project");
			
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
			mav.addObject("yearList",gwtService.getFinYear());
			mav.addObject("dataList",list);
			mav.addObject("projectcd",project);
			
		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	//completeGWTDraftData
	
	@RequestMapping(value = "/completeGWTDraftData", method = RequestMethod.POST)
	public ModelAndView completeGWTDraftData(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		session = request.getSession(true);
		String stcode = session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String gwtid = request.getParameter("gwtid");
		String gwtdtlid = request.getParameter("gwtdtlid");
		String project=request.getParameter("project");
		String groupType=request.getParameter("groupType");
		String fyear=request.getParameter("fyear");
		HashMap<String,String> userType = new HashMap<String,String>();
		List<GroundWaterTableBean> list=new  ArrayList<GroundWaterTableBean>();
		boolean userUploadDeleteSLNA=false;
		Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("project/groundWaterTable");
			userUploadDeleteSLNA= gwtService.completeGWTDraftData(Integer.parseInt(gwtid), Integer.parseInt(gwtdtlid));
			if(userUploadDeleteSLNA ) 
					mav.addObject("messageGWT", "Data complete/Lock Successfully");
			else
					mav.addObject("messageGWT", "Data not complete/Lock!");
			
			list=gwtService.gwtDetaildataDC(Integer.parseInt(project));
			userType.put("project","During Project");
			
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
			mav.addObject("yearList",gwtService.getFinYear());
			mav.addObject("dataList",list);
			mav.addObject("projectcd",project);
		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}
	
	
	@RequestMapping(value="/saveGroundWaterTable", method = RequestMethod.POST)
	@ResponseBody
	public String saveGroundWaterTable1(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="atline") String atline, 
			@RequestParam(value ="project") Integer project, @RequestParam(value ="fyear") Integer fyear, @RequestParam(value ="preMonsoon") BigDecimal preMonsoon, 
			@RequestParam(value ="postMonsoon") BigDecimal postMonsoon, @RequestParam(value ="gwtid1") Integer gwtid, @RequestParam(value ="gwtdtlid1") Integer gwtdtlid,
			@RequestParam(value ="ph") BigDecimal ph, @RequestParam(value ="alkalinity") Integer alkalinity, @RequestParam(value ="hardness") Integer hardness,
			@RequestParam(value ="calcium") Integer calcium, @RequestParam(value ="chloride") Integer chloride, @RequestParam(value ="nitrate") BigDecimal nitrate,
			@RequestParam(value ="dissolved") Integer dissolved, @RequestParam(value ="fluoride") BigDecimal fluoride)
	{
		String res="fail";
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			if(atline.equals("basel"))
				res=gwtService.balselinCheck(project, atline);
			
			if(atline.equals("project"))
				res=gwtService.duringProjCheck(project, atline, fyear);
		
			if( res.equals("fail")) {
				res=gwtService.saveGroundWaterTable(atline, project, fyear, preMonsoon, postMonsoon, session, gwtid, ph, alkalinity, hardness, calcium, chloride, nitrate, dissolved, fluoride);
			}
			else {
				res= "fail";
			}
			
		}
		
		return res; 
	}
	
	
	@RequestMapping(value="/gwtReports", method = RequestMethod.GET)
	public ModelAndView yearWisePhyActPlan(HttpServletRequest request, HttpServletResponse response)
	{
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String project= request.getParameter("project");
				
			ModelAndView mav = new ModelAndView();
			mav = new ModelAndView("reports/gwtReports");
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("")) {
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0") ) {
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);}
				mav.addObject("project", project);
		
		return mav;
	}
	
	
	@RequestMapping(value="/getGWTReport", method = RequestMethod.POST)
	public ModelAndView getGroundWaterTable(HttpServletRequest request, HttpServletResponse response)
	{
	//	getGWTReport
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String proj="";
		String proj1=null;
		String stname="";
		String stname1=null;
		String data[] = null;
		int i=1;
		List<String[]> dataList = new ArrayList<String[]>();
		List<GroundWaterTableBean> list=new  ArrayList<GroundWaterTableBean>();
			mav = new ModelAndView("reports/gwtReports");
			
			list=gwtService.getGroundWaterTableReport(Integer.parseInt(userState), Integer.parseInt(district),  Integer.parseInt(project));
			
		if(list != null) 
		{
			for(GroundWaterTableBean bean : list) 
			{
				data = new String[29];
				if(bean.getProj_id()!=null)
				data[1] = bean.getProj_id().toString();	// proj_code basel
				if(bean.getProjectcd()!=null)
				data[9] = bean.getProjectcd().toString(); // proj_code during project 
				
				if (proj.isEmpty()|| !proj.equals(bean.getProj_name()) || !proj1.equals(bean.getProj_name1())) 
				{
					//    dissolved_solid as bdissolved_solid, fluoride as bfluoride
					data[0] = String.valueOf(i); // serial no
					data[2] = bean.getProj_name();	// projName
					data[10] = bean.getProj_name1();
					
					data[27]=bean.getBstname();
					data[28]=bean.getStname();
					
					if(bean.getBpremon()==null)
						data[3] ="";
					else
						data[3] = bean.getBpremon().toString();
					if(bean.getBpostmon()==null)
						data[4] ="";
					else
						data[4]	= bean.getBpostmon().toString();
					
					if(bean.getBph()==null)
						data[11]="";
					else
						data[11]=bean.getBph().toString();
					if(bean.getBalkalinity()==null)
						data[12]="";
					else
						data[12]=bean.getBalkalinity().toString();
					if(bean.getBhardness()==null)
						data[13]="";
					else
						data[13]=bean.getBhardness().toString();
					if(bean.getBcalcium()==null)
						data[14]="";
					else
						data[14]=bean.getBcalcium().toString();
					if(bean.getBchloride()==null)	
						data[15]="";
					else
						data[15]=bean.getBchloride().toString();
					if(bean.getBnitrate()==null)
						data[16]="";
					else
						data[16]=bean.getBnitrate().toString();
					if(bean.getBdissolved_solid()==null)
						data[17]="";
					else
						data[17]=bean.getBdissolved_solid().toString();
					if(bean.getBfluoride()==null)
						data[18]="";
					else
						data[18]=bean.getBfluoride().toString();
					
					proj = bean.getProj_name()==null?"":bean.getProj_name();
					proj1= bean.getProj_name1();
					
					i++;
				} 
				else {
					data[0] = "";
					data[2] = "";
					data[10] = "";
					data[3] ="";
					data[4]	="";
					data[27]="";
					data[28]="";
				}
				if(bean.getFin_yr_cd()==null)
				{	
					data[5] = "";
				}
				else
					data[5] = bean.getFin_yr_cd().toString();
				
				data[6] = bean.getFinyear();
				if(bean.getPpremon()==null)
				{
					data[7] = "";
				}	
				else 
					data[7] = bean.getPpremon().toString();
				if(bean.getPpostmon()==null)
				{
					data[8] = "";
				}	
				else 
				data[8] = bean.getPpostmon().toString();
				
				if(bean.getPh()==null)
					data[19]="";
				else
					data[19]=bean.getPh().toString();
				if(bean.getAlkalinity()==null)
					data[20]="";
				else
					data[20]=bean.getAlkalinity().toString();
				if(bean.getHardness()==null)
					data[21]="";
				else
					data[21]=bean.getHardness().toString();
				if(bean.getCalcium()==null)
					data[22]="";
				else
					data[22]=bean.getCalcium().toString();
				if(bean.getChloride()==null)
					data[23]="";
				else
					data[23]=bean.getChloride().toString();
				if(bean.getNitrate()==null)
					data[24]="";
				else
					data[24]=bean.getNitrate().toString();
				if(bean.getDissolved_solid()==null)
					data[25]="";
				else
					data[25]=bean.getDissolved_solid().toString();
				if(bean.getFluoride()==null)
					data[26]="";
				else
					data[26]=bean.getFluoride().toString();
				
				/*
				 * if (stname.isEmpty()|| !stname.equals(bean.getBstname()) ||
				 * !stname1.equals(bean.getStname())) { data[27]=bean.getBstname();
				 * data[28]=bean.getStname();
				 * 
				 * stname=bean.getBstname()==null?"":bean.getBstname();
				 * stname1=bean.getStname(); } else {
				 * 
				 * data[27]=""; data[28]=""; }
				 * 
				 */
				dataList.add(data);
			}
		}
			
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());
			mav.addObject("checkState",Integer.parseInt(userState));
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("")) {
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0") ) {
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);}
				mav.addObject("project", project);
			mav.addObject("stName",stName);
			mav.addObject("distName",distName);
			mav.addObject("projName",projName);
			
			
		
		return mav; 
	}
	
	@RequestMapping(value="/getGroundWaterTableDraft", method = RequestMethod.POST)
	@ResponseBody
	public List<GroundWaterTableBean> getGroundWaterTableDraft(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="group") String atline, @RequestParam(value ="project") Integer project,
			@RequestParam(value ="fyear") Integer fyear)
	{
		String res="fail";
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		List<GroundWaterTableBean> list=new  ArrayList<GroundWaterTableBean>();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			
				list=gwtService.getGroundWaterTableDraft(project, atline, fyear);
		}
		
		return list; 
	}
	@RequestMapping(value = "/downloadGroundWaterTablePDF", method = RequestMethod.POST)
	public ModelAndView downloadGroundWaterTablePDF(HttpServletRequest request, HttpServletResponse response) {
		// WDC-PMKSY-0001113	
		String userState = request.getParameter("state");	
		String district = request.getParameter("district");	
		String project = request.getParameter("project");	
		String stName = request.getParameter("stName");		
		String distName = request.getParameter("distName");		
		String projName = request.getParameter("projName");		
		
		List<GroundWaterTableBean> GWTBean = new ArrayList<GroundWaterTableBean>();		
		GWTBean = gwtService.getGroundWaterTableReport(Integer.parseInt(userState), Integer.parseInt(district),	Integer.parseInt(project));	
		
		try {
			
		
			Rectangle layout = new Rectangle(PageSize.A4.rotate());			
			layout.setBackgroundColor(new BaseColor(255, 255, 255));	
			Document document = new Document(layout, 25, 10, 10, 0);			
			document.addTitle("GWTReport");	
			document.addCreationDate();			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();			
			PdfWriter writer = PdfWriter.getInstance(document, baos);			
			document.open();			
			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC);			
			Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD);	
			Font bf8 = new Font(FontFamily.HELVETICA, 8);	
			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));	
			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);
			PdfPTable table = null;	
			document.newPage();	
			Paragraph paragraph3 = null;	
			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);	
			paragraph3 = new Paragraph("Report O2- Project wise Ground Water Table and Water Quality Parameter", f3);	
			paragraph2.setAlignment(Element.ALIGN_CENTER);	
			paragraph3.setAlignment(Element.ALIGN_CENTER);	
			paragraph2.setSpacingAfter(10);	
			paragraph3.setSpacingAfter(10);	
			CommonFunctions.addHeader(document);	
			document.add(paragraph2);	
			document.add(paragraph3);	
			table = new PdfPTable(24);	
			table.setWidths(new int[] { 3, 8, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 });
			table.setWidthPercentage(100);	
			table.setSpacingBefore(0f);	
			table.setSpacingAfter(0f);	
			table.setHeaderRows(4);	
			
			BigDecimal bpremon  =  BigDecimal.valueOf(0);
			BigDecimal bpostmon =  BigDecimal.valueOf(0);
			BigDecimal bph      =  BigDecimal.valueOf(0);
			BigDecimal nitrate =  BigDecimal.valueOf(0);
			BigDecimal bnitrate = BigDecimal.valueOf(0);
			
			int balkalinity = 0, bhardness = 0, bcalcium = 0, bchloride = 0,  bdissolved_solid = 0,	
			alkalinity = 0, hardness = 0, calcium = 0, chloride = 0,  dissolved_solid = 0;
			
			BigDecimal bfluoride =    BigDecimal.valueOf(0);			
			BigDecimal ppremon   =    BigDecimal.valueOf(0);	
			BigDecimal ppostmon  =    BigDecimal.valueOf(0);		
			BigDecimal fluoride  =    BigDecimal.valueOf(0);	
			BigDecimal ph        =    BigDecimal.valueOf(0);
			
			CommonFunctions.insertCellHeader(table,	"State : " + stName + " District : " + distName + " Project : " + projName, Element.ALIGN_LEFT, 24,	1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);	
			CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);	
			CommonFunctions.insertCellHeader(table, "Depth of Ground Water (Baseline Survey)", Element.ALIGN_CENTER, 2,	1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Water Quality (Baseline Survey)", Element.ALIGN_CENTER, 8, 1,bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Depth of Ground Water (During Project Period)",Element.ALIGN_CENTER, 3, 1, bf8Bold);	
			CommonFunctions.insertCellHeader(table, "Water Quality (During Project Period)", Element.ALIGN_CENTER, 8, 1,bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Pre-Monsoon (in meter)", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Post-Monsoon (in meter)", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "pH", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Total Alkalinity", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Total Hardness", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Calcium", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Chloride", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Nitrate", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Total Dissolved", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Fluoride", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Financial Year", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Pre-Monsoon (in meter)", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Post-Monsoon (in meter)", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "pH", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Total Alkalinity", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Total Hardness", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Calcium", Element.ALIGN_CENTER, 1, 1, bf8Bold);	
			CommonFunctions.insertCellHeader(table, "Chloride", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Nitrate", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Total Dissolved", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "Fluoride", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
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
			CommonFunctions.insertCellHeader(table, "13", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "14", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "15", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "16", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "17", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "18", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "19", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "20", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "22", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "23", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			CommonFunctions.insertCellHeader(table, "24", Element.ALIGN_CENTER, 1, 1, bf8Bold);			
			
			int k = 1;	
			String proj="",proj1="";
			if (GWTBean.size() != 0)				
				for (int i = 0; i < GWTBean.size(); i++) {					
					if (proj.isEmpty() || !proj.equals(GWTBean.get(i).getProj_name()) || !proj1.equals(GWTBean.get(i).getProj_name1())) {	 	 
						proj = GWTBean.get(i).getProj_name() == null ? "" : GWTBean.get(i).getProj_name();		
						proj1 = GWTBean.get(i).getProj_name1();					
						
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				
				if(Integer.parseInt(userState)!=0) 
				CommonFunctions.insertCell(table, stName, Element.ALIGN_LEFT, 1, 1, bf8);
				
	        	else 
	        		CommonFunctions.insertCell(table, GWTBean.get(i).getBstname()==null?GWTBean.get(i).getStname():GWTBean.get(i).getBstname(), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, GWTBean.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);					
				CommonFunctions.insertCell(table, GWTBean.get(i).getBpremon() == null ? "" : GWTBean.get(i).getBpremon().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);					
				CommonFunctions.insertCell(table, GWTBean.get(i).getBpostmon() == null ? "" : GWTBean.get(i).getBpostmon().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);	
				CommonFunctions.insertCell(table, GWTBean.get(i).getBph() == null ? "" : GWTBean.get(i).getBph().toString(),	Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table,(GWTBean.get(i).getBalkalinity() != null)? Integer.toString(GWTBean.get(i).getBalkalinity()): "",Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table,(GWTBean.get(i).getBhardness() != null) ? Integer.toString(GWTBean.get(i).getBhardness()): "",	Element.ALIGN_RIGHT, 1, 1, bf8);					
				CommonFunctions.insertCell(table,(GWTBean.get(i).getBcalcium() != null) ? Integer.toString(GWTBean.get(i).getBcalcium()): "",Element.ALIGN_RIGHT, 1, 1, bf8);					
				CommonFunctions.insertCell(table,(GWTBean.get(i).getBchloride() != null) ? Integer.toString(GWTBean.get(i).getBchloride()): "",Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table,GWTBean.get(i).getBnitrate() == null ? "":  GWTBean.get(i).getBnitrate().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);	
				CommonFunctions.insertCell(table,(GWTBean.get(i).getBdissolved_solid() != null)? Integer.toString(GWTBean.get(i).getBdissolved_solid())	: "",Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, GWTBean.get(i).getBfluoride() == null ? "" : GWTBean.get(i).getBfluoride().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);					
									
						
						k = k + 1;					
						bpremon = bpremon.add(GWTBean.get(i).getBpremon() == null ? BigDecimal.ZERO : GWTBean.get(i).getBpremon());					
						bpostmon = bpostmon.add(GWTBean.get(i).getBpostmon() == null ? BigDecimal.ZERO : GWTBean.get(i).getBpostmon());	
						bph = bph.add(GWTBean.get(i).getBph() == null ? BigDecimal.ZERO : GWTBean.get(i).getBph());				
						balkalinity = balkalinity+ (GWTBean.get(i).getBalkalinity() != null ? GWTBean.get(i).getBalkalinity() : 0);		
						bhardness = bhardness + (GWTBean.get(i).getBhardness() != null ? GWTBean.get(i).getBhardness() : 0);				
						bcalcium = bcalcium + (GWTBean.get(i).getBcalcium() != null ? GWTBean.get(i).getBcalcium() : 0);				
						bchloride = bchloride + (GWTBean.get(i).getBchloride() != null ? GWTBean.get(i).getBchloride() : 0);			
						bnitrate = bnitrate.add(GWTBean.get(i).getBnitrate() == null ? BigDecimal.ZERO : GWTBean.get(i).getBnitrate());				
						bdissolved_solid = bdissolved_solid	+ (GWTBean.get(i).getBdissolved_solid() != null ? GWTBean.get(i).getBdissolved_solid() : 0);					
						bfluoride = bfluoride.add(GWTBean.get(i).getBfluoride() == null ? BigDecimal.ZERO : GWTBean.get(i).getBfluoride());				
						
					}
					else {
						CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);					
						CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);					
						CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);	
						CommonFunctions.insertCell(table, "" ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);					
						CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);					
						CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);	
						CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "",Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "",Element.ALIGN_RIGHT, 1, 1, bf8);
					}
					
					CommonFunctions.insertCell(table, GWTBean.get(i).getFinyear(), Element.ALIGN_LEFT, 1, 1, bf8);					
					CommonFunctions.insertCell(table, GWTBean.get(i).getPpremon() != null ?  GWTBean.get(i).getPpremon().toString(): "",	Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, GWTBean.get(i).getPpostmon() != null ?  GWTBean.get(i).getPpostmon().toString(): "",Element.ALIGN_RIGHT, 1, 1, bf8);					
					CommonFunctions.insertCell(table, GWTBean.get(i).getPh() != null ?  GWTBean.get(i).getPh().toString(): "",Element.ALIGN_RIGHT, 1, 1, bf8);	
					CommonFunctions.insertCell(table,(GWTBean.get(i).getAlkalinity() != null) ? Integer.toString(GWTBean.get(i).getAlkalinity()): "",Element.ALIGN_RIGHT, 1, 1, bf8);	
					CommonFunctions.insertCell(table,(GWTBean.get(i).getHardness() != null) ? Integer.toString(GWTBean.get(i).getHardness()): "",Element.ALIGN_RIGHT, 1, 1, bf8);		
					CommonFunctions.insertCell(table,(GWTBean.get(i).getCalcium() != null) ? Integer.toString(GWTBean.get(i).getCalcium()) : "",Element.ALIGN_RIGHT, 1, 1, bf8);				
					CommonFunctions.insertCell(table,(GWTBean.get(i).getChloride() != null) ? Integer.toString(GWTBean.get(i).getChloride()): "",Element.ALIGN_RIGHT, 1, 1, bf8);			
					CommonFunctions.insertCell(table, (GWTBean.get(i).getNitrate() != null) ? GWTBean.get(i).getNitrate().toString() : "", Element.ALIGN_RIGHT, 1, 1, bf8);

					CommonFunctions.insertCell(table,(GWTBean.get(i).getDissolved_solid() != null)? Integer.toString(GWTBean.get(i).getDissolved_solid()): "",Element.ALIGN_RIGHT, 1, 1, bf8);					
					CommonFunctions.insertCell(table, GWTBean.get(i).getFluoride() != null ?  GWTBean.get(i).getFluoride().toString(): "",Element.ALIGN_RIGHT, 1, 1, bf8);
					
					ppremon = ppremon.add(GWTBean.get(i).getPpremon() == null ? BigDecimal.ZERO : GWTBean.get(i).getPpremon());			
					ppostmon = ppostmon.add(GWTBean.get(i).getPpostmon() == null ? BigDecimal.ZERO : GWTBean.get(i).getPpostmon());				
					ph = ph.add(GWTBean.get(i).getPh() == null ? BigDecimal.ZERO : GWTBean.get(i).getPh());			
					alkalinity = alkalinity	+ (GWTBean.get(i).getAlkalinity() != null ? GWTBean.get(i).getAlkalinity() : 0);			
					hardness = hardness + (GWTBean.get(i).getHardness() != null ? GWTBean.get(i).getHardness() : 0);					
					calcium = calcium + (GWTBean.get(i).getCalcium() != null ? GWTBean.get(i).getCalcium() : 0);					
					chloride = chloride + (GWTBean.get(i).getChloride() != null ? GWTBean.get(i).getChloride() : 0);					
					nitrate = nitrate.add(GWTBean.get(i).getNitrate() == null ? BigDecimal.ZERO : GWTBean.get(i).getNitrate());					
					dissolved_solid = dissolved_solid+ (GWTBean.get(i).getDissolved_solid() != null ? GWTBean.get(i).getDissolved_solid() : 0);				
					fluoride = fluoride.add(GWTBean.get(i).getFluoride() == null ? BigDecimal.ZERO : GWTBean.get(i).getFluoride());
					
					}	
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 3, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(bpremon), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(bpostmon), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(bph), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(balkalinity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(bhardness), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(bcalcium), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(bchloride), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(bnitrate), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(bdissolved_solid), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(bfluoride), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, "", Element.ALIGN_CENTER, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(ppremon), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(ppostmon), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(ph), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(alkalinity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(hardness), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(calcium), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(chloride), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(nitrate), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(dissolved_solid), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			CommonFunctions.insertCell3(table, String.valueOf(fluoride), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			
				if (GWTBean.size() == 0)				
			CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 24, 1, bf8);			
			
			document.add(table);			
			table = new PdfPTable(1);			
			table.setWidthPercentage(70);			
			table.setSpacingBefore(15f);			
			table.setSpacingAfter(0f);			
			CommonFunctions.insertCellPageHeader(table,	"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
			CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),Element.ALIGN_LEFT, 1, 4, bf8);		
			document.add(table);			
			document.close();			
			response.setContentType("application/pdf");		
			response.setHeader("Expires", "0");			
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");		
			response.setHeader("Content-Disposition", "attachment;filename=O2-Report.pdf");			
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
	
	
	@RequestMapping(value = "/getProjectwiseGWTExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getProjectwiseGWTExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		
		List<GroundWaterTableBean> beanList = new  ArrayList<GroundWaterTableBean>();
		beanList = gwtService.getGroundWaterTableReport(Integer.parseInt(userState), Integer.parseInt(district),  Integer.parseInt(project));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O2- Project wise Ground Water Table and Water Quality Parameter");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O2- Project wise Ground Water Table and Water Quality Parameter";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 23, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,23);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,2,2);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,5,12); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,13,15); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,16,23); 
	        sheet.addMergedRegion(mergedRegion);
	        	
	        
	        mergedRegion = new CellRangeAddress(beanList.size()+9,beanList.size()+9,0,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row rowhead0 = sheet.createRow(5);
	        Cell cell = rowhead0.createCell(0);
	        cell.setCellValue("State :   "+stName + "  District :  " +distName + "  Project : " + projName);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
	        
	        Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(2);
			cell.setCellValue("Project Name");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(3);
			cell.setCellValue("Depth of Ground Water (Baseline Survey)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(4).setCellStyle(style);
			cell = rowhead.createCell(5);
			cell.setCellValue("Water Quality (Baseline Survey)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i=6;i<13;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			cell = rowhead.createCell(13);
			cell.setCellValue("Depth of Ground Water (During Project Period)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(14).setCellStyle(style);
			rowhead.createCell(15).setCellStyle(style);
			
			cell = rowhead.createCell(16);
			cell.setCellValue("Water Quality (During Project Period)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i=17;i<24;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
	        
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			cell = rowhead1.createCell(3);
			cell.setCellValue("Pre-Monsoon (in meter)");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(4);
			cell.setCellValue("Post-Monsoon (in meter)");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("pH");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total Alkalinity");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellValue("Total Hardness");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("Calcium");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(9);
			cell.setCellValue("Chloride");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(10);
			cell.setCellValue("Nitrate");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(11);
			cell.setCellValue("Total Dissolved");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(12);
			cell.setCellValue("Fluoride");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(13);
			cell.setCellValue("Financial Year");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(14);
			cell.setCellValue("Pre-Monsoon (in meter)");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(15);
			cell.setCellValue("Post-Monsoon (in meter)");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(16);
			cell.setCellValue("pH");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(17);
			cell.setCellValue("Total Alkalinity");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(18);
			cell.setCellValue("Total Hardness");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(19);
			cell.setCellValue("Calcium");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(20);
			cell.setCellValue("Chloride");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(21);
			cell.setCellValue("Nitrate");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(22);
			cell.setCellValue("Total Dissolved");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(23);
			cell.setCellValue("Fluoride");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(8);
			for(int i =0; i<24;i++) {
				cell = rowhead2.createCell(i);
				cell.setCellValue(i+1);  
				cell.setCellStyle(style);
			}
			
	        int rowno  =9;
	        int sno = 1;
	        double preMonTotBsl = 0.0;
	        double postMonTotBsl = 0.0;
	        double phTotBsl = 0.0;
	        double alkliTotBsl = 0.0;
	        double hrdTotBsl = 0.0;
	        double calTotBsl = 0.0;
	        double clTotBsl = 0.0;
	        double nitTotBsl = 0.0;
	        double disTotBsl = 0.0;
	        double flTotBsl = 0.0;
	        double preMonTot = 0.0;
	        double postMonTot = 0.0;
	        double phTot = 0.0;
	        double alkliTot = 0.0;
	        double hrdTot = 0.0;
	        double calTot = 0.0;
	        double clTot = 0.0;
	        double nitTot = 0.0;
	        double disTot = 0.0;
	        double flTot = 0.0;
	        String proj = "";
	        String proj1 = "";
	        
	        for(GroundWaterTableBean bean: beanList) {
	        	Row row = sheet.createRow(rowno);
	        	if (proj.isEmpty()|| !proj.equals(bean.getProj_name()) || !proj1.equals(bean.getProj_name1())) 
				{
	        	cell = row.createCell(0);
	        	cell.setCellValue(sno);
	        	cell = row.createCell(1);
	        	if(Integer.parseInt(userState)!=0)
	        		cell.setCellValue(stName);
	        	else
	        		cell.setCellValue(bean.getBstname()==null?bean.getStname():bean.getBstname());
	        	cell = row.createCell(2);
	        	cell.setCellValue(bean.getProj_name()==null?bean.getProj_name1():bean.getProj_name());
	        	cell = row.createCell(3);
	        	cell.setCellValue(bean.getBpremon()==null?0:bean.getBpremon().doubleValue());
	        	cell = row.createCell(4);
	        	cell.setCellValue(bean.getBpostmon()==null?0:bean.getBpostmon().doubleValue());
	        	cell = row.createCell(5);
	        	cell.setCellValue(bean.getBph()==null?0:bean.getBph().doubleValue());
	        	cell = row.createCell(6);
	        	cell.setCellValue(bean.getBalkalinity()==null?0:bean.getBalkalinity());
	        	cell = row.createCell(7);
	        	cell.setCellValue(bean.getBhardness()==null?0:bean.getBhardness());
	        	cell = row.createCell(8);
	        	cell.setCellValue(bean.getBcalcium()==null?0:bean.getBcalcium());
	        	cell = row.createCell(9);
	        	cell.setCellValue(bean.getBchloride()==null?0:bean.getBchloride());
	        	cell = row.createCell(10);
	        	cell.setCellValue(bean.getBnitrate()==null?0:bean.getBnitrate().doubleValue());
	        	cell = row.createCell(11);
	        	cell.setCellValue(bean.getBdissolved_solid()==null?0:bean.getBdissolved_solid());
	        	cell = row.createCell(12);
	        	cell.setCellValue(bean.getBfluoride()==null?0:bean.getBfluoride().doubleValue());
	        	
	        	preMonTotBsl = preMonTotBsl + (bean.getBpremon()==null?0:bean.getBpremon().doubleValue());
	        	postMonTotBsl = postMonTotBsl + (bean.getBpostmon()==null?0:bean.getBpostmon().doubleValue());
	        	phTotBsl = phTotBsl + (bean.getBph()==null?0:bean.getBph().doubleValue());
	        	alkliTotBsl = alkliTotBsl + (bean.getBalkalinity()==null?0:bean.getBalkalinity());
	        	hrdTotBsl = hrdTotBsl + (bean.getBhardness()==null?0:bean.getBhardness());
	        	calTotBsl = calTotBsl + (bean.getBcalcium()==null?0:bean.getBcalcium());
	        	clTotBsl = clTotBsl + (bean.getBchloride()==null?0:bean.getBchloride());
	        	nitTotBsl = nitTotBsl + (bean.getBnitrate()==null?0:bean.getBnitrate().doubleValue());
	        	disTotBsl = disTotBsl + (bean.getBdissolved_solid()==null?0:bean.getBdissolved_solid());
	        	flTotBsl = flTotBsl + (bean.getBfluoride()==null?0:bean.getBfluoride().doubleValue());
	        	proj = bean.getProj_name()==null?"":bean.getProj_name();
				proj1= bean.getProj_name1();
				sno++;
				}
	        	
	        	else {
	        		row.createCell(0);
		        	row.createCell(1);
		        	row.createCell(2);
		        	row.createCell(3);
		        	row.createCell(4);
		        	row.createCell(5);
		        	row.createCell(6);
		        	row.createCell(7);
		        	row.createCell(8);
		        	row.createCell(9);
		        	row.createCell(10);
		        	row.createCell(11);
		        	row.createCell(12);
	        	}
	        	cell = row.createCell(13);
	        	cell.setCellValue(bean.getFinyear()==null?"":bean.getFinyear());
	        	cell = row.createCell(14);
	        	cell.setCellValue(bean.getPpremon()==null?0.00:bean.getPpremon().doubleValue());
	        	cell = row.createCell(15);
	        	cell.setCellValue(bean.getPpostmon()==null?0.00:bean.getPpostmon().doubleValue());
	        	cell = row.createCell(16);
	        	cell.setCellValue(bean.getPh()==null?0.00:bean.getPh().doubleValue());
	        	cell = row.createCell(17);
	        	cell.setCellValue(bean.getAlkalinity()==null?0:bean.getAlkalinity());
	        	cell = row.createCell(18);
	        	cell.setCellValue(bean.getHardness()==null?0:bean.getHardness());
	        	cell = row.createCell(19);
	        	cell.setCellValue(bean.getCalcium()==null?0:bean.getCalcium());
	        	cell = row.createCell(20);
	        	cell.setCellValue(bean.getChloride()==null?0:bean.getChloride());
	        	cell = row.createCell(21);
	        	cell.setCellValue(bean.getNitrate()==null?0.00:bean.getNitrate().doubleValue());
	        	cell = row.createCell(22);
	        	cell.setCellValue(bean.getDissolved_solid()==null?0:bean.getDissolved_solid());
	        	cell = row.createCell(23);
	        	cell.setCellValue(bean.getFluoride()==null?0.00:bean.getFluoride().doubleValue());
	        	preMonTot = preMonTot + (bean.getPpremon()==null?0.00:bean.getPpremon().doubleValue());
	        	postMonTot = postMonTot + (bean.getPpostmon()==null?0.00:bean.getPpostmon().doubleValue());
	        	phTot = phTot + (bean.getPh()==null?0.00:bean.getPh().doubleValue());
	        	alkliTot = alkliTot + (bean.getAlkalinity()==null?0:bean.getAlkalinity());
	        	hrdTot = hrdTot + (bean.getHardness()==null?0:bean.getHardness());
	        	calTot = calTot + (bean.getCalcium()==null?0:bean.getCalcium());
	        	clTot = clTot + (bean.getChloride()==null?0:bean.getChloride());
	        	nitTot = nitTot + (bean.getNitrate()==null?0.00:bean.getNitrate().doubleValue());
	        	disTot = disTot + (bean.getDissolved_solid()==null?0:bean.getDissolved_solid());
	        	flTot = flTot + (bean.getFluoride()==null?0.00:bean.getFluoride().doubleValue());
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
			rowtot.createCell(2).setCellStyle(style1);
			cell = rowtot.createCell(3);
			cell.setCellValue(preMonTotBsl);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(4);
			cell.setCellValue(postMonTotBsl);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(5);
			cell.setCellValue(phTotBsl);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(6);
			cell.setCellValue(alkliTotBsl);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(7);
			cell.setCellValue(hrdTotBsl);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(8);
			cell.setCellValue(calTotBsl);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(9);
			cell.setCellValue(clTotBsl);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(10);
			cell.setCellValue(nitTotBsl);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(11);
			cell.setCellValue(disTotBsl);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(12);
			cell.setCellValue(flTotBsl);
			cell.setCellStyle(style1);
			rowtot.createCell(13).setCellStyle(style1);;
			cell = rowtot.createCell(14);
			cell.setCellValue(preMonTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(15);
			cell.setCellValue(postMonTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(16);
			cell.setCellValue(phTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(17);
			cell.setCellValue(alkliTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(18);
			cell.setCellValue(hrdTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(19);
			cell.setCellValue(calTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(20);
			cell.setCellValue(clTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(21);
			cell.setCellValue(nitTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(22);
			cell.setCellValue(disTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(23);
			cell.setCellValue(flTot);
			cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, beanList.size(), 23);
	        String fileName = "attachment; filename=Report O2.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/gwtReports";
		
	}

		}
			

	
	


