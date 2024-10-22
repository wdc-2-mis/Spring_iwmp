package app.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

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

import app.bean.ConvergenceStatusBean;
import app.bean.ConvergenceWorksBean;
import app.bean.Login;
import app.bean.NRSCWorksBean;
import app.bean.OOMFAchvDetailsBean;
import app.bean.PfmsTransactionBean;
import app.bean.ProfileBean;
import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.common.CommonFunctions;
import app.model.ConvergenceWorkDetail;
import app.model.master.IwmpMConvergenceScheme;
import app.service.CommonService;
import app.service.ConvergenceWorksService;
import app.service.PhysicalActionPlanService;
import app.service.ProfileService;
import app.service.ProjectMasterService;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.master.PfmsService;
import app.service.reports.PhysicalActionAchievementService;

@Controller("ConvergenceWorksController")
public class ConvergenceWorksController {
	
HttpSession session;
	
@Autowired
ProjectMasterService pmservice;

@Autowired
StateMasterService stateMasterService;

@Autowired(required = true)
private CommonService commonService;

@Autowired
PhysicalActionAchievementService pAAservices;
	
@Autowired
ConvergenceWorksService convergenceWorksService;

@Autowired(required = true)
public UserService userService;

@Autowired
PhysicalActionPlanService physicalActionPlanService;

@Autowired(required = true)
ProfileService profileService;

@Autowired
PfmsService pfmsSrvc;


@RequestMapping(value="/getConvergenceWorks", method = RequestMethod.GET)
public ModelAndView getConvergenceWorks(HttpServletRequest request, HttpServletResponse response)
{
//	String stcd1 = request.getParameter("state");
//	String dcode1 = request.getParameter("district");
//	String projid1 = request.getParameter("project");
	String stName = "All States";
	String dName = "All Districts";
	String pName = "All Projects";

	ModelAndView mav = new ModelAndView();
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	 
	mav = new ModelAndView("reports/ConvergenceWorks");
	
		Integer stcd=0;
		Integer dcode=0;
		Integer projid=0;
	list = convergenceWorksService.getConvergenceWorks(stcd, dcode, projid);
	mav.addObject("convergenceList",list);
	mav.addObject("convergenceListSize",list.size());
	
	mav.addObject("stateList",stateMasterService.getAllState());
	mav.addObject("stName", stName);
	mav.addObject("dName", dName);
	mav.addObject("pName", pName);
	return mav;
}


@RequestMapping(value = "/getConvergenceWorkDetails", method = RequestMethod.POST)
public ModelAndView getConvergenceWorksRecord(HttpServletRequest request) 
{
	
	String stcd = request.getParameter("state");
	String dcode = request.getParameter("district");
	String projid = request.getParameter("project");
	String stName = request.getParameter("stName");
	String dName = request.getParameter("dName");
	String pName = request.getParameter("pName");
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	ModelAndView mav = new ModelAndView("reports/ConvergenceWorks");
	list = convergenceWorksService.getConvergenceWorks(Integer.parseInt(stcd), Integer.parseInt(dcode), Integer.parseInt(projid));
	
	mav.addObject("stcd", stcd);
	mav.addObject("dcode", dcode);
	mav.addObject("projid", projid);
	mav.addObject("stName", stName);
	mav.addObject("dName", dName);
	mav.addObject("pName", pName);
	mav.addObject("convergenceList",list);
	mav.addObject("convergenceListSize",list.size());
	mav.addObject("stateList",stateMasterService.getAllState());
	
	return mav;
}

@RequestMapping(value="/getConvergedWorks", method = RequestMethod.GET)
public ModelAndView getConvergedWorksRecord(HttpServletRequest request, HttpServletResponse response)
{
//	String finyr = request.getParameter("finyr");
//	String finName = request.getParameter("finName");
//	finyr = "0";
//	finName = "All Financial Years";
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	ModelAndView mav = new ModelAndView("reports/ConvergedWorks");
	list = convergenceWorksService.getConvergedWorks();	
	
//	mav.addObject("finyr",finyr);
//	mav.addObject("finName",finName);
	mav.addObject("ConvergedWorksList",list);
	mav.addObject("ConvergedWorksListSize",list.size());
//	mav.addObject("financialYear", commonService.getAllFinancialYear());
	
	return mav;
}

//@RequestMapping(value="/getConvergedWorksDetails", method = RequestMethod.POST)
//public ModelAndView getConvergedWorks(HttpServletRequest request, HttpServletResponse response)
//{
//	String finyr = request.getParameter("fincode");
//	String finName = request.getParameter("finName");
//	String stName = request.getParameter("stName");
//	
//	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
//	ModelAndView mav = new ModelAndView("reports/ConvergedWorks");
//	list = convergenceWorksService.getConvergedWorks(Integer.parseInt(finyr));	
//	
//	mav.addObject("finyr",finyr);
//	mav.addObject("finName",finName);
//	mav.addObject("stName",stName);
//	mav.addObject("ConvergedWorksList",list);
//	mav.addObject("ConvergedWorksListSize",list.size());
//	mav.addObject("financialYear", commonService.getAllFinancialYear());
//	
//	return mav;
//}

@RequestMapping(value = "/getConvergedDistWorks", method = RequestMethod.GET)
public ModelAndView getConvergedDistWorks(HttpServletRequest request) {
	
//	String finyr = request.getParameter("finyr");
////	String finName = request.getParameter("finName");
//	String finName = finyr.equals("0")?"All Financial Years":request.getParameter("finName");
	String stcd = request.getParameter("stcd");
	String stName = request.getParameter("stName");
	String distName = request.getParameter("distName");
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	ModelAndView mav = new ModelAndView("reports/ConvergedDistWorks"); 
	
	list = convergenceWorksService.getConvergedDistWorks(Integer.parseInt(stcd));
	
	mav.addObject("stcd",stcd);
	mav.addObject("stName",stName);
	mav.addObject("distName",distName);
//	mav.addObject("finyr",finyr);
//	mav.addObject("finName",finName);
	mav.addObject("ConvergedDistWorksList",list);
	mav.addObject("ConvergedDistWorksListSize",list.size());

	return mav;
}

@RequestMapping(value = "/getConvergedProjWorks", method = RequestMethod.GET)
public ModelAndView getConvergedProjWorks(HttpServletRequest request) {
	
//	String finyr = request.getParameter("finyr");
//	String finName = request.getParameter("finName");
	String dcode = request.getParameter("dcode");
	String distName = request.getParameter("distName");
	String stName = request.getParameter("stName");
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	ModelAndView mav = new ModelAndView("reports/ConvergedProjWorks"); 
	
	list = convergenceWorksService.getConvergedProjWorks(Integer.parseInt(dcode));
	
	mav.addObject("dcode",dcode);
	mav.addObject("distName",distName);
	mav.addObject("stName",stName);
//	mav.addObject("finyr",finyr);
//	mav.addObject("finName",finName);
	mav.addObject("ConvergedProjWorksList",list);
	mav.addObject("ConvergedProjWorksListSize",list.size());

	return mav;
}

@RequestMapping(value = "/getConvergedProjDetailWorks", method = RequestMethod.GET)
public ModelAndView getConvergedProjDetailWorks(HttpServletRequest request) {
	
//	String finyr = request.getParameter("finyr");
//	String finName = request.getParameter("finName");
	String projid = request.getParameter("projid");
	String distName = request.getParameter("distName");
	String stName = request.getParameter("stName");
	Map<String,Map<BigDecimal,String>> stgeMap = new LinkedHashMap<>();
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	list = convergenceWorksService.getConvergedProjDetailWorks(Integer.parseInt(projid));
	
	list.forEach(s->{
		Map<BigDecimal,String> map = new LinkedHashMap<>();
			stgeMap.put("Pre Implementation"+","+s.getWorkcode(), map);
			stgeMap.put("Mid Implementation"+","+s.getWorkcode(), map);
			stgeMap.put("Post Implementation"+","+s.getWorkcode(), map);
	});
	
	list.forEach(s->{
		Map<BigDecimal,String> map = new LinkedHashMap<>();
		if(stgeMap.containsKey(s.getStage()+","+s.getWorkcode())) {
			if(stgeMap.get(s.getStage()+","+s.getWorkcode())!=null)
				map.putAll(stgeMap.get(s.getStage()+","+s.getWorkcode()));
			
			map.put(s.getCollection_sno(), s.getStatus());
			stgeMap.put(s.getStage()+","+s.getWorkcode(), map);
		}
	});
	
	ModelAndView mav = new ModelAndView("reports/ConvergedProjDetailWorks"); 
	
	
	mav.addObject("projid",projid);
	mav.addObject("distName",distName);
	mav.addObject("stName",stName);
//	mav.addObject("finyr",finyr);
//	mav.addObject("finName",finName);
	mav.addObject("stgeMap",stgeMap);
	mav.addObject("ConvergedProjDetailWorksList",list);
	mav.addObject("ConvergedProjDetailWorksListSize",list.size());

	return mav;
}

@RequestMapping(value = "/getConvergedExpndtr", method = RequestMethod.GET)
public ModelAndView getConvergedExpndtrReport(HttpServletRequest request) 
{
	ModelAndView mav = new ModelAndView();
	mav = new ModelAndView("reports/ConvergedExpndtr");
	mav.addObject("stateList",stateMasterService.getAllState());
	mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
	mav.addObject("SchemeList",convergenceWorksService.getAllSchemes());
	return mav;
}

@RequestMapping(value = "/getConvergedExpndtrReport", method = RequestMethod.POST)
public ModelAndView getConvergedExpndtrReport(HttpServletRequest request, HttpServletResponse response) 
{
//	HttpSession session = request.getSession(true);
	
	int stcd= Integer.parseInt(request.getParameter("state"));
	int dcode= Integer.parseInt(request.getParameter("district"));
	int projid= Integer.parseInt(request.getParameter("project"));
	int sid= Integer.parseInt(request.getParameter("scheme"));
	int finyr= Integer.parseInt(request.getParameter("finYear"));
		
	String distName= request.getParameter("distName");
	String projName= request.getParameter("projName");
	String yearName= request.getParameter("yearName");
	String stName= request.getParameter("stName");
	String schemeName= request.getParameter("schemeName");
	
	ModelAndView mav = new ModelAndView();
	mav = new ModelAndView("reports/ConvergedExpndtr");
	
	List<ConvergenceWorksBean> beanList = new ArrayList<ConvergenceWorksBean>();
	beanList=convergenceWorksService.getConvergedExpndtr(stcd, dcode, projid, finyr, sid);
			
		 mav.addObject("beanList",convergenceWorksService.getConvergedExpndtr(stcd, dcode, projid, finyr, sid));
		 mav.addObject("beanListSize",beanList.size());
		 mav.addObject("stateList",stateMasterService.getAllState());
		 mav.addObject("stcd",stcd);
		 mav.addObject("dcode",dcode);
		 mav.addObject("projid",projid);
		 mav.addObject("finyr",finyr);
		 mav.addObject("sid",sid);
		 mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		 mav.addObject("SchemeList",convergenceWorksService.getAllSchemes());
		 
		 mav.addObject("stName",stName);
		 mav.addObject("distName",distName);
		 mav.addObject("projName",projName);
		 mav.addObject("yearName",yearName);
		 mav.addObject("schemeName",schemeName);
			
	return mav;
}

@RequestMapping(value = "/getEnteredConvergedWorks", method = RequestMethod.GET)
public ModelAndView getEnteredConWorksRecord(HttpServletRequest request) 
{
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	ModelAndView mav = new ModelAndView("reports/EnteredConvergedWorks");
	
	list = convergenceWorksService.getEnteredConWorks();
			
	mav.addObject("enteredConList",list);
	mav.addObject("enteredConListSize",list.size());
	
	return mav;
}

@RequestMapping(value = "/getStatusConvergedWorks", method = RequestMethod.GET)
public ModelAndView getStatusConWorks(HttpServletRequest request) 
{
	session = request.getSession(true);
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	ModelAndView mav = new ModelAndView();
	
	try {
		if (session != null && session.getAttribute("loginID") != null) 
		{
			mav = new ModelAndView("reports/StatusConvergedWorks");
			
			Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
			
			list = convergenceWorksService.getStatusConWorks(stcd);
			
			mav.addObject("stcd",stcd);
			mav.addObject("statusConList",list);
			mav.addObject("statusConListSize",list.size());
		} 
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	
	return mav;
}

@RequestMapping(value="/getRemainingConvergedWorks", method = RequestMethod.POST)
@ResponseBody
public List<ConvergenceWorksBean> getRemainingConWorks(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="dcode") Integer dcode)
{
	session = request.getSession(true);
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
    if(session!=null && session.getAttribute("loginID")!=null) {
		
    	list = convergenceWorksService.getRemainingConWorks(dcode);
		
		
	}else {
		list=null;
		
	}
	return list;
}

@RequestMapping(value="/getConvergedStateExpndtr", method = RequestMethod.GET)
public ModelAndView getConvergedStateExpndtrRecord(HttpServletRequest request, HttpServletResponse response)
{
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	Map<Integer, String> schlist = new LinkedHashMap<>();
	ModelAndView mav = new ModelAndView("reports/ConvergedStateExpndtr");
	
	list = convergenceWorksService.getConvergedStateExpndtr();	
	schlist = convergenceWorksService.getAllSchemes();
	
	Map<String, String> schMap = new LinkedHashMap<>();
	Map<String,BigDecimal> totMap = new LinkedHashMap<>();
	for(ConvergenceWorksBean l: list) {
		for(Map.Entry<Integer, String> map : schlist.entrySet()) {
		schMap.put(l.getSt_code()+",cw-"+map.getKey(), "0");
		schMap.put(l.getSt_code()+",es-"+map.getKey(), "0");
		schMap.put(l.getSt_code()+",ew-"+map.getKey(), "0");
		}
	}
		
	for(ConvergenceWorksBean l: list) {	
		schMap.put(l.getSt_code()+",cw-"+l.getScheme_id(), l.getSchmconvergedworks().toString());
		schMap.put(l.getSt_code()+",es-"+l.getScheme_id(), l.getExpndtr_cnvrgd_scheme().toString());
		schMap.put(l.getSt_code()+",ew-"+l.getScheme_id(), l.getExpndtr_wdc2().toString());
	}
	
	for (Map.Entry<String, String> entry : schMap.entrySet()) {
	    
		String originalKey = entry.getKey();
	    int commaIndex = originalKey.indexOf(',');
	    if (commaIndex == -1) {
	        continue; 
	    }
	    String newKey = originalKey.substring(commaIndex + 1).trim(); 

	    BigDecimal value = new BigDecimal(entry.getValue());

	    if (totMap.containsKey(newKey)) {
	        BigDecimal existingValue = totMap.get(newKey);
	        BigDecimal newValue = existingValue.add(value);
	        totMap.put(newKey, newValue);
	    } else {
	        totMap.put(newKey, value);
	    }
	}


	
	mav.addObject("schMap",schMap);
	mav.addObject("SchemeList",convergenceWorksService.getAllSchemes());
	mav.addObject("SchemeListSize",convergenceWorksService.getAllSchemes().size());
	mav.addObject("ConStateExpndtrList",list);
	mav.addObject("ConStateExpndtrListSize",list.size());
	mav.addObject("totMap",totMap);
	return mav;
}


@RequestMapping(value = "/downloadExcelConvergenceWorks", method = RequestMethod.POST)
@ResponseBody
public String downloadExcelConvergenceWorks(HttpServletRequest request, HttpServletResponse response) 
{
	String stcd = request.getParameter("state");
	String dcode = request.getParameter("district");
	String projid = request.getParameter("project");
	String stName = request.getParameter("stName");
	String dName = request.getParameter("dName");
	String pName = request.getParameter("pName");
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	
	list = convergenceWorksService.getConvergenceWorks(Integer.parseInt(stcd), Integer.parseInt(dcode), Integer.parseInt(projid));
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("Report CW3- State-wise, District-wise and Project-wise Convergence Work with Expenditure Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "Report CW3- State-wise, District-wise and Project-wise Convergence Work with Expenditure Details";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,0,5); 
	sheet.addMergedRegion(mergedRegion);
	
	Row rowDetail = sheet.createRow(5);
	
	Cell cell = rowDetail.createCell(0);
	cell.setCellValue("State : "+ stName+"     District : "+dName+"     Project : "+pName);  
	cell.setCellStyle(style);
	
	for(int i=1;i<6;i++)
	{
		cell =rowDetail.createCell(i);
		cell.setCellStyle(style);
	}
	
	
	Row rowhead = sheet.createRow(6);
	
	cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("Head Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(4);
	cell.setCellValue("Expenditure Under WDC-PMKSY 2.0 (in Rs.)	");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(5);
	cell.setCellValue("Expenditure Under Converged Scheme (in Rs.)");  
	cell.setCellStyle(style);

		
	Row rowhead1 = sheet.createRow(7);
	
	for(int i=0;i<6;i++)
	{
		cell =rowhead1.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno  = 8;
	int totalWorks = 0;
	int totConvergedworks = 0;
	double expndwdc=0.0;
	double exscheme=0.0;
	
	
    for(ConvergenceWorksBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getHead_desc());
    	row.createCell(2).setCellValue(bean.getTotalworks());
    	row.createCell(3).setCellValue(bean.getConvergedworks());
    	row.createCell(4).setCellValue(bean.getExpndtr_wdc2().doubleValue());
    	row.createCell(5).setCellValue(bean.getExpndtr_cnvrgd_scheme().doubleValue());
    	
    	
    	totalWorks = totalWorks + bean.getTotalworks();
    	totConvergedworks = totConvergedworks + bean.getConvergedworks();
    	expndwdc = expndwdc + bean.getExpndtr_wdc2().doubleValue();
    	exscheme = exscheme + bean.getExpndtr_cnvrgd_scheme().doubleValue();

    	sno++;
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

	Row row = sheet.createRow(list.size()+8);
	cell = row.createCell(0);
	cell.setCellValue("Grand Total");
	cell.setCellStyle(style1);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	cell = row.createCell(1);
	cell.setCellStyle(style1);
	cell = row.createCell(2);
	cell.setCellValue(totalWorks);
	cell.setCellStyle(style1);
	cell = row.createCell(3);
	cell.setCellValue(totConvergedworks);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(expndwdc);
	cell.setCellStyle(style1);
	cell = row.createCell(5);
	cell.setCellValue(exscheme);
	cell.setCellStyle(style1);

	
    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 5);
    String fileName = "attachment; filename=Report CW3- State.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/ConvergenceWorks";
}


@RequestMapping(value = "/downloadPDFConvergenceWorks", method = RequestMethod.POST)
public ModelAndView downloadPDFConvergenceWorks(HttpServletRequest request, HttpServletResponse response) 
{
	
	String stcd = request.getParameter("state");
	String dcode = request.getParameter("district");
	String projid = request.getParameter("project");
	String stName = request.getParameter("stName");
	String dName = request.getParameter("dName");
	String pName = request.getParameter("pName");
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	
	list = convergenceWorksService.getConvergenceWorks(Integer.parseInt(stcd), Integer.parseInt(dcode), Integer.parseInt(projid));
	
	try {
		
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("ConvergenceWorksReport");
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
		
			paragraph3 = new Paragraph("Report CW3- State-wise, District-wise and Project-wise Convergence Work with Expenditure Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(6);
	        table.setWidths(new int[]{2, 8, 5, 5, 8, 8});
	        table.setWidthPercentage(80);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);
						
	        CommonFunctions.insertCellHeader(table, "State : "+stName+"     District : "+dName+"     Project : "+pName, Element.ALIGN_LEFT, 6, 1, bf8Bold);
	        
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Head Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure Under WDC-PMKSY 2.0 (in Rs.)	", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Expenditure Under Converged Scheme (in Rs.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
	        
			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			
			int k = 1;
			int totalWorks = 0;
			int totConvergedworks = 0;
			BigDecimal expwdc= new BigDecimal(0);
			BigDecimal expwdcscheme= new BigDecimal(0);
			
			
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getHead_desc(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getConvergedworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getExpndtr_wdc2()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getExpndtr_cnvrgd_scheme()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					
					totalWorks = totalWorks + list.get(i).getTotalworks();
			    	totConvergedworks = totConvergedworks + list.get(i).getConvergedworks();
			    	expwdc=expwdc.add(list.get(i).getExpndtr_wdc2());
			    	expwdcscheme=expwdcscheme.add(list.get(i).getExpndtr_cnvrgd_scheme());
					
					k++;
				}
				
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totalWorks), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totConvergedworks), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(expwdc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(expwdcscheme), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
			
			if(list.size()==0) 
				CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 6, 1, bf8);
			
			
	document.add(table);
	table = new PdfPTable(1);
	table.setWidthPercentage(90);
	table.setSpacingBefore(15f);
	table.setSpacingAfter(0f);
	CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
	CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
	document.add(table);
	document.close();
	response.setContentType("application/pdf");
	response.setHeader("Expires", "0");
	response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	response.setHeader("Content-Disposition", "attachment;filename=Report CW3- State.pdf");
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


@RequestMapping(value = "/downloadExcelConvergedWorks", method = RequestMethod.POST)
@ResponseBody
public String downloadExcelConvergedWorks(HttpServletRequest request, HttpServletResponse response) 
{
//	String finyr = request.getParameter("fincode");
//	String finName = request.getParameter("finName");
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	
	list = convergenceWorksService.getConvergedWorks();
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 17, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,0,17); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,0,0); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,1,1); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,2,2); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,3,3); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,4,5); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,6,7); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,8,9); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,10,11); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,12,13); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,14,15); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,16,17); 
	sheet.addMergedRegion(mergedRegion);
	
	
//	Row rowDetail = sheet.createRow(5);
//	
//	Cell cell = rowDetail.createCell(0);
//	cell.setCellValue("Financial Year : "+ finName);  
//	cell.setCellStyle(style);
	
	
	Row rowhead = sheet.createRow(6);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("State Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(4);
	cell.setCellValue("Area brought under Afforestation / Agriculture / Pasture etc.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(5);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(6);
	cell.setCellValue("Area brought under Horticulture");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(7);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(8);
	cell.setCellValue("Area covered under Soil and Moisture conservation activities");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(9);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(10);
	cell.setCellValue("Water Harvesting Structure (New created)");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(11);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(12);
	cell.setCellValue("Water Harvesting Structure (Renovated)");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(13);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(14);
	cell.setCellValue("Vegetative and Engineering Structure");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(15);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(16);
	cell.setCellValue("Springsheds");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(17);
	cell.setCellStyle(style);
	
	
	Row rowhead1 = sheet.createRow(7);
	
	cell = rowhead1.createCell(0);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(1);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(2);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(3);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(4);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(5);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(6);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(7);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(8);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(9);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(10);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(11);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(12);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(13);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(14);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(15);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(16);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(17);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);

		
	Row rowhead2 = sheet.createRow(8);
	
	for(int i=0;i<18;i++)
	{
		cell =rowhead2.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno  = 9;
	int totwrk = 0;
	int conwrk = 0;
	int totAfforestation = 0;
	int totAfforestation_conv = 0;
	int totHorticulture = 0;
	int totHorticulture_conv = 0;
	int totSoilMoisture = 0;
	int totSoilMoisture_conv = 0;
	int totWaterHarvestingNew = 0;
	int totWaterHarvestingNew_conv = 0;
	int totWaterHarvestingRenovated = 0;
	int totWaterHarvestingRenovated_conv = 0;
	int totVegetativeStructure = 0;
	int totVegetativeStructure_conv = 0;
	int totSpringsheds = 0;
	int totSpringsheds_conv = 0;
	
	
    for(ConvergenceWorksBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getSt_name());
    	row.createCell(2).setCellValue(bean.getTotalworks());
    	row.createCell(3).setCellValue(bean.getConvergedworks());
    	row.createCell(4).setCellValue(bean.getAfforestation());
    	row.createCell(5).setCellValue(bean.getAfforestation_conv());
    	row.createCell(6).setCellValue(bean.getHorticulture());
    	row.createCell(7).setCellValue(bean.getHorticulture_conv());
    	row.createCell(8).setCellValue(bean.getSoil_moisture());
    	row.createCell(9).setCellValue(bean.getSoil_moisture_conv());
    	row.createCell(10).setCellValue(bean.getWater_harvesting_new());
    	row.createCell(11).setCellValue(bean.getWater_harvesting_new_conv());
    	row.createCell(12).setCellValue(bean.getWater_harvesting_renovated());
    	row.createCell(13).setCellValue(bean.getWater_harvesting_renovated_conv());
    	row.createCell(14).setCellValue(bean.getVegetative_structure());
    	row.createCell(15).setCellValue(bean.getVegetative_structure_conv());
    	row.createCell(16).setCellValue(bean.getSpringsheds());
    	row.createCell(17).setCellValue(bean.getSpringsheds_conv());
    	
    	totwrk = totwrk + bean.getTotalworks();
    	conwrk = conwrk + bean.getConvergedworks();
    	totAfforestation = totAfforestation + bean.getAfforestation();
    	totAfforestation_conv = totAfforestation_conv + bean.getAfforestation_conv();
    	totHorticulture = totHorticulture + bean.getHorticulture();
    	totHorticulture_conv = totHorticulture_conv + bean.getHorticulture_conv();
    	totSoilMoisture = totSoilMoisture + bean.getSoil_moisture();
    	totSoilMoisture_conv = totSoilMoisture_conv + bean.getSoil_moisture_conv();
    	totWaterHarvestingNew = totWaterHarvestingNew + bean.getWater_harvesting_new();
    	totWaterHarvestingNew_conv = totWaterHarvestingNew_conv + bean.getWater_harvesting_new_conv();
    	totWaterHarvestingRenovated = totWaterHarvestingRenovated + bean.getWater_harvesting_renovated();
    	totWaterHarvestingRenovated_conv = totWaterHarvestingRenovated_conv + bean.getWater_harvesting_renovated_conv();
    	totVegetativeStructure = totVegetativeStructure + bean.getVegetative_structure();
    	totVegetativeStructure_conv = totVegetativeStructure_conv + bean.getVegetative_structure_conv();
    	totSpringsheds = totSpringsheds + bean.getSpringsheds();
    	totSpringsheds_conv = totSpringsheds_conv + bean.getSpringsheds_conv();

    	sno++;
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

	Row row = sheet.createRow(list.size()+9);
	cell = row.createCell(0);
	cell.setCellValue("Grand Total");
	cell.setCellStyle(style1);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	cell = row.createCell(1);
	cell.setCellStyle(style1);
	cell = row.createCell(2);
	cell.setCellValue(totwrk);
	cell.setCellStyle(style1);
	cell = row.createCell(3);
	cell.setCellValue(conwrk);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totAfforestation);
	cell.setCellStyle(style1);
	cell = row.createCell(5);
	cell.setCellValue(totAfforestation_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(6);
	cell.setCellValue(totHorticulture);
	cell.setCellStyle(style1);
	cell = row.createCell(7);
	cell.setCellValue(totHorticulture_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(8);
	cell.setCellValue(totSoilMoisture);
	cell.setCellStyle(style1);
	cell = row.createCell(9);
	cell.setCellValue(totSoilMoisture_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(10);
	cell.setCellValue(totWaterHarvestingNew);
	cell.setCellStyle(style1);
	cell = row.createCell(11);
	cell.setCellValue(totWaterHarvestingNew_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(12);
	cell.setCellValue(totWaterHarvestingRenovated);
	cell.setCellStyle(style1);
	cell = row.createCell(13);
	cell.setCellValue(totWaterHarvestingRenovated_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(14);
	cell.setCellValue(totVegetativeStructure);
	cell.setCellStyle(style1);
	cell = row.createCell(15);
	cell.setCellValue(totVegetativeStructure_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(16);
	cell.setCellValue(totSpringsheds);
	cell.setCellStyle(style1);
	cell = row.createCell(17);
	cell.setCellValue(totSpringsheds_conv);
	cell.setCellStyle(style1);

	
    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 17);
    String fileName = "attachment; filename=Report CW2- State.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/ConvergedWorks";
}


@RequestMapping(value = "/downloadPDFConvergedWorks", method = RequestMethod.POST)
public ModelAndView downloadPDFConvergedWorks(HttpServletRequest request, HttpServletResponse response) 
{
	
//	String finyr = request.getParameter("fincode");
//	String finName = request.getParameter("finName");
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	
	list = convergenceWorksService.getConvergedWorks();
	
	try {
		
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("ConvergedWorksReport");
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
		
			paragraph3 = new Paragraph("Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(18);
	        table.setWidths(new int[]{2, 8, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6});
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);
						
//	        CommonFunctions.insertCellHeader(table, "Financial Year : "+ finName, Element.ALIGN_LEFT, 18, 1, bf8Bold);
	        
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area brought under Afforestation / Agriculture / Pasture etc.", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area brought under Horticulture", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area covered under Soil and Moisture conservation activities", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Water Harvesting Structure (New created)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Water Harvesting Structure (Renovated)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Vegetative and Engineering Structure", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Springsheds", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			
			
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
	        
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

			
			int k = 1;
			int totwrk = 0;
			int conwrk = 0;
			int totAfforestation = 0;
			int totAfforestation_conv = 0;
			int totHorticulture = 0;
			int totHorticulture_conv = 0;
			int totSoilMoisture = 0;
			int totSoilMoisture_conv = 0;
			int totWaterHarvestingNew = 0;
			int totWaterHarvestingNew_conv = 0;
			int totWaterHarvestingRenovated = 0;
			int totWaterHarvestingRenovated_conv = 0;
			int totVegetativeStructure = 0;
			int totVegetativeStructure_conv = 0;
			int totSpringsheds = 0;
			int totSpringsheds_conv = 0;

			
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getConvergedworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAfforestation()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAfforestation_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getHorticulture()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getHorticulture_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSoil_moisture()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSoil_moisture_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_new()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_new_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_renovated()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_renovated_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVegetative_structure()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVegetative_structure_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSpringsheds()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSpringsheds_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					
			    	
					totwrk = totwrk + list.get(i).getTotalworks();
					conwrk = conwrk + list.get(i).getConvergedworks();
			    	totAfforestation = totAfforestation + list.get(i).getAfforestation();
			    	totAfforestation_conv = totAfforestation_conv + list.get(i).getAfforestation_conv();
			    	totHorticulture = totHorticulture + list.get(i).getHorticulture();
			    	totHorticulture_conv = totHorticulture_conv + list.get(i).getHorticulture_conv();
			    	totSoilMoisture = totSoilMoisture + list.get(i).getSoil_moisture();
			    	totSoilMoisture_conv = totSoilMoisture_conv + list.get(i).getSoil_moisture_conv();
			    	totWaterHarvestingNew = totWaterHarvestingNew + list.get(i).getWater_harvesting_new();
			    	totWaterHarvestingNew_conv = totWaterHarvestingNew_conv + list.get(i).getWater_harvesting_new_conv();
			    	totWaterHarvestingRenovated = totWaterHarvestingRenovated + list.get(i).getWater_harvesting_renovated();
			    	totWaterHarvestingRenovated_conv = totWaterHarvestingRenovated_conv + list.get(i).getWater_harvesting_renovated_conv();
			    	totVegetativeStructure = totVegetativeStructure + list.get(i).getVegetative_structure();
			    	totVegetativeStructure_conv = totVegetativeStructure_conv + list.get(i).getVegetative_structure_conv();
			    	totSpringsheds = totSpringsheds + list.get(i).getSpringsheds();
			    	totSpringsheds_conv = totSpringsheds_conv + list.get(i).getSpringsheds_conv();
					
					k++;
				}
				
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totwrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(conwrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totAfforestation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totAfforestation_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totHorticulture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totHorticulture_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSoilMoisture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSoilMoisture_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingNew), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingNew_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingRenovated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingRenovated_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totVegetativeStructure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totVegetativeStructure_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSpringsheds), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSpringsheds_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			
			
			if(list.size()==0) 
				CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 18, 1, bf8);
			
			
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
	response.setHeader("Content-Disposition", "attachment;filename=Report CW2- State.pdf");
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


@RequestMapping(value = "/downloadExcelConvergedDistWorks", method = RequestMethod.POST)
@ResponseBody
public String downloadExcelConvergedDistWorks(HttpServletRequest request, HttpServletResponse response) 
{
//	String finyr = request.getParameter("finyr");
//	String finName = request.getParameter("finName");
	String stcd = request.getParameter("stcd");
	String stName = request.getParameter("stName");
	 	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	
	list = convergenceWorksService.getConvergedDistWorks(Integer.parseInt(stcd));
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 17, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,0,17); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,0,0); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,1,1); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,2,2); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,3,3); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,4,5); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,6,7); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,8,9); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,10,11); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,12,13); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,14,15); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,16,17); 
	sheet.addMergedRegion(mergedRegion);
	
	
	Row rowDetail = sheet.createRow(5);
	
	Cell cell = rowDetail.createCell(0);
	cell.setCellValue("State Name : "+ stName);  
	cell.setCellStyle(style);
	
	for(int i=1;i<18;i++)
	{
		cell = rowDetail.createCell(i);
		cell.setCellStyle(style);
	}
	
	
	Row rowhead = sheet.createRow(6);
	
	cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("District Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(4);
	cell.setCellValue("Area brought under Afforestation / Agriculture / Pasture etc.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(5);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(6);
	cell.setCellValue("Area brought under Horticulture");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(7);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(8);
	cell.setCellValue("Area covered under Soil and Moisture conservation activities");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(9);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(10);
	cell.setCellValue("Water Harvesting Structure (New created)");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(11);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(12);
	cell.setCellValue("Water Harvesting Structure (Renovated)");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(13);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(14);
	cell.setCellValue("Vegetative and Engineering Structure");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(15);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(16);
	cell.setCellValue("Springsheds");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(17);
	cell.setCellStyle(style);
	
	
	Row rowhead1 = sheet.createRow(7);
	
	cell = rowhead1.createCell(0);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(1);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(2);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(3);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(4);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(5);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(6);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(7);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(8);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(9);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(10);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(11);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(12);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(13);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(14);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(15);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(16);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(17);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);

		
	Row rowhead2 = sheet.createRow(8);
	
	for(int i=0;i<18;i++)
	{
		cell =rowhead2.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno  = 9;
	int totwrk = 0;
	int conwrk = 0;
	int totAfforestation = 0;
	int totAfforestation_conv = 0;
	int totHorticulture = 0;
	int totHorticulture_conv = 0;
	int totSoilMoisture = 0;
	int totSoilMoisture_conv = 0;
	int totWaterHarvestingNew = 0;
	int totWaterHarvestingNew_conv = 0;
	int totWaterHarvestingRenovated = 0;
	int totWaterHarvestingRenovated_conv = 0;
	int totVegetativeStructure = 0;
	int totVegetativeStructure_conv = 0;
	int totSpringsheds = 0;
	int totSpringsheds_conv = 0;
	
	
    for(ConvergenceWorksBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getDist_name());
    	row.createCell(2).setCellValue(bean.getTotalworks());
        row.createCell(3).setCellValue(bean.getConvergedworks());
    	row.createCell(4).setCellValue(bean.getAfforestation());
    	row.createCell(5).setCellValue(bean.getAfforestation_conv());
    	row.createCell(6).setCellValue(bean.getHorticulture());
    	row.createCell(7).setCellValue(bean.getHorticulture_conv());
    	row.createCell(8).setCellValue(bean.getSoil_moisture());
    	row.createCell(9).setCellValue(bean.getSoil_moisture_conv());
    	row.createCell(10).setCellValue(bean.getWater_harvesting_new());
    	row.createCell(11).setCellValue(bean.getWater_harvesting_new_conv());
    	row.createCell(12).setCellValue(bean.getWater_harvesting_renovated());
    	row.createCell(13).setCellValue(bean.getWater_harvesting_renovated_conv());
    	row.createCell(14).setCellValue(bean.getVegetative_structure());
    	row.createCell(15).setCellValue(bean.getVegetative_structure_conv());
    	row.createCell(16).setCellValue(bean.getSpringsheds());
    	row.createCell(17).setCellValue(bean.getSpringsheds_conv());
    	
    	totwrk = totwrk + bean.getTotalworks();
        conwrk = conwrk + bean.getConvergedworks();
    	totAfforestation = totAfforestation + bean.getAfforestation();
    	totAfforestation_conv = totAfforestation_conv + bean.getAfforestation_conv();
    	totHorticulture = totHorticulture + bean.getHorticulture();
    	totHorticulture_conv = totHorticulture_conv + bean.getHorticulture_conv();
    	totSoilMoisture = totSoilMoisture + bean.getSoil_moisture();
    	totSoilMoisture_conv = totSoilMoisture_conv + bean.getSoil_moisture_conv();
    	totWaterHarvestingNew = totWaterHarvestingNew + bean.getWater_harvesting_new();
    	totWaterHarvestingNew_conv = totWaterHarvestingNew_conv + bean.getWater_harvesting_new_conv();
    	totWaterHarvestingRenovated = totWaterHarvestingRenovated + bean.getWater_harvesting_renovated();
    	totWaterHarvestingRenovated_conv = totWaterHarvestingRenovated_conv + bean.getWater_harvesting_renovated_conv();
    	totVegetativeStructure = totVegetativeStructure + bean.getVegetative_structure();
    	totVegetativeStructure_conv = totVegetativeStructure_conv + bean.getVegetative_structure_conv();
    	totSpringsheds = totSpringsheds + bean.getSpringsheds();
    	totSpringsheds_conv = totSpringsheds_conv + bean.getSpringsheds_conv();

    	sno++;
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

	Row row = sheet.createRow(list.size()+9);
	cell = row.createCell(0);
	cell.setCellValue("Grand Total");
	cell.setCellStyle(style1);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	cell = row.createCell(1);
	cell.setCellStyle(style1);
	cell = row.createCell(2);
	cell.setCellValue(totwrk);
	cell.setCellStyle(style1);
	cell = row.createCell(3);
	cell.setCellValue(conwrk);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totAfforestation);
	cell.setCellStyle(style1);
	cell = row.createCell(5);
	cell.setCellValue(totAfforestation_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(6);
	cell.setCellValue(totHorticulture);
	cell.setCellStyle(style1);
	cell = row.createCell(7);
	cell.setCellValue(totHorticulture_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(8);
	cell.setCellValue(totSoilMoisture);
	cell.setCellStyle(style1);
	cell = row.createCell(9);
	cell.setCellValue(totSoilMoisture_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(10);
	cell.setCellValue(totWaterHarvestingNew);
	cell.setCellStyle(style1);
	cell = row.createCell(11);
	cell.setCellValue(totWaterHarvestingNew_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(12);
	cell.setCellValue(totWaterHarvestingRenovated);
	cell.setCellStyle(style1);
	cell = row.createCell(13);
	cell.setCellValue(totWaterHarvestingRenovated_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(14);
	cell.setCellValue(totVegetativeStructure);
	cell.setCellStyle(style1);
	cell = row.createCell(15);
	cell.setCellValue(totVegetativeStructure_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(16);
	cell.setCellValue(totSpringsheds);
	cell.setCellStyle(style1);
	cell = row.createCell(17);
	cell.setCellValue(totSpringsheds_conv);
	cell.setCellStyle(style1);

	
    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 17);
    String fileName = "attachment; filename=Report CW2- District.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/ConvergedDistWorks";
}


@RequestMapping(value = "/downloadPDFConvergedDistWorks", method = RequestMethod.POST)
public ModelAndView downloadPDFConvergedDistWorks(HttpServletRequest request, HttpServletResponse response) 
{
	
//	String finyr = request.getParameter("finyr");
//	String finName = request.getParameter("finName");
	String stcd = request.getParameter("stcd");
	String stName = request.getParameter("stName");
	 	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	
	list = convergenceWorksService.getConvergedDistWorks(Integer.parseInt(stcd));
	
	try {
		
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("ConvergedDistWorksReport");
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
		
			paragraph3 = new Paragraph("Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(18);
	        table.setWidths(new int[]{2, 8, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6});
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(4);
						
	        CommonFunctions.insertCellHeader(table,"State Name : "+ stName, Element.ALIGN_LEFT, 18, 1, bf8Bold);
	        
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area brought under Afforestation / Agriculture / Pasture etc.", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area brought under Horticulture", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area covered under Soil and Moisture conservation activities", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Water Harvesting Structure (New created)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Water Harvesting Structure (Renovated)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Vegetative and Engineering Structure", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Springsheds", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			
			
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
	        
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


			
			int k = 1;
			int totwrk = 0;
			int conwrk = 0;
			int totAfforestation = 0;
			int totAfforestation_conv = 0;
			int totHorticulture = 0;
			int totHorticulture_conv = 0;
			int totSoilMoisture = 0;
			int totSoilMoisture_conv = 0;
			int totWaterHarvestingNew = 0;
			int totWaterHarvestingNew_conv = 0;
			int totWaterHarvestingRenovated = 0;
			int totWaterHarvestingRenovated_conv = 0;
			int totVegetativeStructure = 0;
			int totVegetativeStructure_conv = 0;
			int totSpringsheds = 0;
			int totSpringsheds_conv = 0;

			
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getConvergedworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAfforestation()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAfforestation_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getHorticulture()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getHorticulture_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSoil_moisture()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSoil_moisture_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_new()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_new_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_renovated()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_renovated_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVegetative_structure()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVegetative_structure_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSpringsheds()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSpringsheds_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					
			    	
					totwrk = totwrk + list.get(i).getTotalworks();
					conwrk = conwrk + list.get(i).getConvergedworks();
			    	totAfforestation = totAfforestation + list.get(i).getAfforestation();
			    	totAfforestation_conv = totAfforestation_conv + list.get(i).getAfforestation_conv();
			    	totHorticulture = totHorticulture + list.get(i).getHorticulture();
			    	totHorticulture_conv = totHorticulture_conv + list.get(i).getHorticulture_conv();
			    	totSoilMoisture = totSoilMoisture + list.get(i).getSoil_moisture();
			    	totSoilMoisture_conv = totSoilMoisture_conv + list.get(i).getSoil_moisture_conv();
			    	totWaterHarvestingNew = totWaterHarvestingNew + list.get(i).getWater_harvesting_new();
			    	totWaterHarvestingNew_conv = totWaterHarvestingNew_conv + list.get(i).getWater_harvesting_new_conv();
			    	totWaterHarvestingRenovated = totWaterHarvestingRenovated + list.get(i).getWater_harvesting_renovated();
			    	totWaterHarvestingRenovated_conv = totWaterHarvestingRenovated_conv + list.get(i).getWater_harvesting_renovated_conv();
			    	totVegetativeStructure = totVegetativeStructure + list.get(i).getVegetative_structure();
			    	totVegetativeStructure_conv = totVegetativeStructure_conv + list.get(i).getVegetative_structure_conv();
			    	totSpringsheds = totSpringsheds + list.get(i).getSpringsheds();
			    	totSpringsheds_conv = totSpringsheds_conv + list.get(i).getSpringsheds_conv();
					
					k++;
				}
				
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totwrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(conwrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totAfforestation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totAfforestation_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totHorticulture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totHorticulture_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSoilMoisture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSoilMoisture_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingNew), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingNew_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingRenovated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingRenovated_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totVegetativeStructure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totVegetativeStructure_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSpringsheds), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSpringsheds_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			
			
			if(list.size()==0) 
				CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 18, 1, bf8);
			
			
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
	response.setHeader("Content-Disposition", "attachment;filename=Report CW2- District.pdf");
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


@RequestMapping(value = "/downloadExcelConvergedProjWorks", method = RequestMethod.POST)
@ResponseBody
public String downloadExcelConvergedProjWorks(HttpServletRequest request, HttpServletResponse response) 
{
//	String finyr = request.getParameter("finyr");
//	String finName = request.getParameter("finName");
	String dcode = request.getParameter("dcode");
	String distName = request.getParameter("distName");
	String stName = request.getParameter("stName");
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	
	list = convergenceWorksService.getConvergedProjWorks(Integer.parseInt(dcode));
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 17, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,0,17); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,0,0); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,1,1); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,2,2); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,7,3,3); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,4,5); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,6,7); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,8,9); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,10,11); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,12,13); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,14,15); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(6,6,16,17); 
	sheet.addMergedRegion(mergedRegion);
	
	
	Row rowDetail = sheet.createRow(5);
	
	Cell cell = rowDetail.createCell(0);
	cell.setCellValue("State Name : "+ stName+"     District Name : "+ distName);  
	cell.setCellStyle(style);
	
	for(int i=1;i<18;i++)
	{
		cell =rowDetail.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	
	Row rowhead = sheet.createRow(6);
	
	cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("Project Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(4);
	cell.setCellValue("Area brought under Afforestation / Agriculture / Pasture etc.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(5);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(6);
	cell.setCellValue("Area brought under Horticulture");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(7);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(8);
	cell.setCellValue("Area covered under Soil and Moisture conservation activities");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(9);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(10);
	cell.setCellValue("Water Harvesting Structure (New created)");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(11);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(12);
	cell.setCellValue("Water Harvesting Structure (Renovated)");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(13);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(14);
	cell.setCellValue("Vegetative and Engineering Structure");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(15);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(16);
	cell.setCellValue("Springsheds");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(17);
	cell.setCellStyle(style);
	
	
	Row rowhead1 = sheet.createRow(7);
	
	cell = rowhead1.createCell(0);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(1);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(2);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(3);
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(4);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(5);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(6);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(7);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(8);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(9);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(10);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(11);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(12);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(13);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(14);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(15);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(16);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(17);
	cell.setCellValue("Convergence Works");  
	cell.setCellStyle(style);

		
	Row rowhead2 = sheet.createRow(8);
	
	for(int i=0;i<18;i++)
	{
		cell =rowhead2.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno  = 9;
	int totwrk = 0;
	int conwrk = 0;
	int totAfforestation = 0;
	int totAfforestation_conv = 0;
	int totHorticulture = 0;
	int totHorticulture_conv = 0;
	int totSoilMoisture = 0;
	int totSoilMoisture_conv = 0;
	int totWaterHarvestingNew = 0;
	int totWaterHarvestingNew_conv = 0;
	int totWaterHarvestingRenovated = 0;
	int totWaterHarvestingRenovated_conv = 0;
	int totVegetativeStructure = 0;
	int totVegetativeStructure_conv = 0;
	int totSpringsheds = 0;
	int totSpringsheds_conv = 0;
	
	
    for(ConvergenceWorksBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getProj_name());
    	row.createCell(2).setCellValue(bean.getTotalworks());
        row.createCell(3).setCellValue(bean.getConvergedworks());
    	row.createCell(4).setCellValue(bean.getAfforestation());
    	row.createCell(5).setCellValue(bean.getAfforestation_conv());
    	row.createCell(6).setCellValue(bean.getHorticulture());
    	row.createCell(7).setCellValue(bean.getHorticulture_conv());
    	row.createCell(8).setCellValue(bean.getSoil_moisture());
    	row.createCell(9).setCellValue(bean.getSoil_moisture_conv());
    	row.createCell(10).setCellValue(bean.getWater_harvesting_new());
    	row.createCell(11).setCellValue(bean.getWater_harvesting_new_conv());
    	row.createCell(12).setCellValue(bean.getWater_harvesting_renovated());
    	row.createCell(13).setCellValue(bean.getWater_harvesting_renovated_conv());
    	row.createCell(14).setCellValue(bean.getVegetative_structure());
    	row.createCell(15).setCellValue(bean.getVegetative_structure_conv());
    	row.createCell(16).setCellValue(bean.getSpringsheds());
    	row.createCell(17).setCellValue(bean.getSpringsheds_conv());
    	
    	totwrk = totwrk + bean.getTotalworks();
        conwrk = conwrk + bean.getConvergedworks();
    	totAfforestation = totAfforestation + bean.getAfforestation();
    	totAfforestation_conv = totAfforestation_conv + bean.getAfforestation_conv();
    	totHorticulture = totHorticulture + bean.getHorticulture();
    	totHorticulture_conv = totHorticulture_conv + bean.getHorticulture_conv();
    	totSoilMoisture = totSoilMoisture + bean.getSoil_moisture();
    	totSoilMoisture_conv = totSoilMoisture_conv + bean.getSoil_moisture_conv();
    	totWaterHarvestingNew = totWaterHarvestingNew + bean.getWater_harvesting_new();
    	totWaterHarvestingNew_conv = totWaterHarvestingNew_conv + bean.getWater_harvesting_new_conv();
    	totWaterHarvestingRenovated = totWaterHarvestingRenovated + bean.getWater_harvesting_renovated();
    	totWaterHarvestingRenovated_conv = totWaterHarvestingRenovated_conv + bean.getWater_harvesting_renovated_conv();
    	totVegetativeStructure = totVegetativeStructure + bean.getVegetative_structure();
    	totVegetativeStructure_conv = totVegetativeStructure_conv + bean.getVegetative_structure_conv();
    	totSpringsheds = totSpringsheds + bean.getSpringsheds();
    	totSpringsheds_conv = totSpringsheds_conv + bean.getSpringsheds_conv();

    	sno++;
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

	Row row = sheet.createRow(list.size()+9);
	cell = row.createCell(0);
	cell.setCellValue("Grand Total");
	cell.setCellStyle(style1);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	cell = row.createCell(1);
	cell.setCellStyle(style1);
	cell = row.createCell(2);
	cell.setCellValue(totwrk);
	cell.setCellStyle(style1);
	cell = row.createCell(3);
	cell.setCellValue(conwrk);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totAfforestation);
	cell.setCellStyle(style1);
	cell = row.createCell(5);
	cell.setCellValue(totAfforestation_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(6);
	cell.setCellValue(totHorticulture);
	cell.setCellStyle(style1);
	cell = row.createCell(7);
	cell.setCellValue(totHorticulture_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(8);
	cell.setCellValue(totSoilMoisture);
	cell.setCellStyle(style1);
	cell = row.createCell(9);
	cell.setCellValue(totSoilMoisture_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(10);
	cell.setCellValue(totWaterHarvestingNew);
	cell.setCellStyle(style1);
	cell = row.createCell(11);
	cell.setCellValue(totWaterHarvestingNew_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(12);
	cell.setCellValue(totWaterHarvestingRenovated);
	cell.setCellStyle(style1);
	cell = row.createCell(13);
	cell.setCellValue(totWaterHarvestingRenovated_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(14);
	cell.setCellValue(totVegetativeStructure);
	cell.setCellStyle(style1);
	cell = row.createCell(15);
	cell.setCellValue(totVegetativeStructure_conv);
	cell.setCellStyle(style1);
	cell = row.createCell(16);
	cell.setCellValue(totSpringsheds);
	cell.setCellStyle(style1);
	cell = row.createCell(17);
	cell.setCellValue(totSpringsheds_conv);
	cell.setCellStyle(style1);

	
    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 17);
    String fileName = "attachment; filename=Report CW2- Project.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/ConvergedProjWorks";
}


@RequestMapping(value = "/downloadPDFConvergedProjWorks", method = RequestMethod.POST)
public ModelAndView downloadPDFConvergedProjWorks(HttpServletRequest request, HttpServletResponse response) 
{
	
//	String finyr = request.getParameter("finyr");
//	String finName = request.getParameter("finName");
	String dcode = request.getParameter("dcode");
	String distName = request.getParameter("distName");
	String stName = request.getParameter("stName");
	
	List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
	
	list = convergenceWorksService.getConvergedProjWorks(Integer.parseInt(dcode));
	 	
	try {
		
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("ConvergedProjWorksReport");
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
		
			paragraph3 = new Paragraph("Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(18);
	        table.setWidths(new int[]{2, 8, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6, 5, 6});
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(4);
						
	        CommonFunctions.insertCellHeader(table,"State Name : "+ stName+"     District Name : "+ distName, Element.ALIGN_LEFT, 18, 1, bf8Bold);
	        
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area brought under Afforestation / Agriculture / Pasture etc.", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area brought under Horticulture", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Area covered under Soil and Moisture conservation activities", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Water Harvesting Structure (New created)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Water Harvesting Structure (Renovated)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Vegetative and Engineering Structure", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Springsheds", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			
			
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Convergence Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
	        
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

			
			int k = 1;
			int totwrk = 0;
			int conwrk = 0;
			int totAfforestation = 0;
			int totAfforestation_conv = 0;
			int totHorticulture = 0;
			int totHorticulture_conv = 0;
			int totSoilMoisture = 0;
			int totSoilMoisture_conv = 0;
			int totWaterHarvestingNew = 0;
			int totWaterHarvestingNew_conv = 0;
			int totWaterHarvestingRenovated = 0;
			int totWaterHarvestingRenovated_conv = 0;
			int totVegetativeStructure = 0;
			int totVegetativeStructure_conv = 0;
			int totSpringsheds = 0;
			int totSpringsheds_conv = 0;

			
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getConvergedworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAfforestation()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAfforestation_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getHorticulture()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getHorticulture_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSoil_moisture()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSoil_moisture_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_new()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_new_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_renovated()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWater_harvesting_renovated_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVegetative_structure()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVegetative_structure_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSpringsheds()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getSpringsheds_conv()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
					
			    	
					totwrk = totwrk + list.get(i).getTotalworks();
					conwrk = conwrk + list.get(i).getConvergedworks();
			    	totAfforestation = totAfforestation + list.get(i).getAfforestation();
			    	totAfforestation_conv = totAfforestation_conv + list.get(i).getAfforestation_conv();
			    	totHorticulture = totHorticulture + list.get(i).getHorticulture();
			    	totHorticulture_conv = totHorticulture_conv + list.get(i).getHorticulture_conv();
			    	totSoilMoisture = totSoilMoisture + list.get(i).getSoil_moisture();
			    	totSoilMoisture_conv = totSoilMoisture_conv + list.get(i).getSoil_moisture_conv();
			    	totWaterHarvestingNew = totWaterHarvestingNew + list.get(i).getWater_harvesting_new();
			    	totWaterHarvestingNew_conv = totWaterHarvestingNew_conv + list.get(i).getWater_harvesting_new_conv();
			    	totWaterHarvestingRenovated = totWaterHarvestingRenovated + list.get(i).getWater_harvesting_renovated();
			    	totWaterHarvestingRenovated_conv = totWaterHarvestingRenovated_conv + list.get(i).getWater_harvesting_renovated_conv();
			    	totVegetativeStructure = totVegetativeStructure + list.get(i).getVegetative_structure();
			    	totVegetativeStructure_conv = totVegetativeStructure_conv + list.get(i).getVegetative_structure_conv();
			    	totSpringsheds = totSpringsheds + list.get(i).getSpringsheds();
			    	totSpringsheds_conv = totSpringsheds_conv + list.get(i).getSpringsheds_conv();
					
					k++;
				}
				
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totwrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(conwrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totAfforestation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totAfforestation_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totHorticulture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totHorticulture_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSoilMoisture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSoilMoisture_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingNew), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingNew_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingRenovated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totWaterHarvestingRenovated_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totVegetativeStructure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totVegetativeStructure_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSpringsheds), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totSpringsheds_conv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			
			
			if(list.size()==0) 
				CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 18, 1, bf8);
			
			
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
	response.setHeader("Content-Disposition", "attachment;filename=Report CW2- Project.pdf");
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
	
	@RequestMapping(value = "/getListConvergenceWork", method = RequestMethod.GET)
	public ModelAndView getListConvergenceWork(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("listOfConvergenceWorks");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				mav.addObject("projectList", pmservice.getProjectByRegId(regId));

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/getListConvergenceWork", method = RequestMethod.POST)
	@ResponseBody
	public List<ConvergenceWorksBean> getListConvergenceWork(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("projId") Integer projId){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();

		List<ConvergenceWorksBean> list = new ArrayList<>();
		List<ConvergenceWorkDetail> cnvrgList = new ArrayList<>();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				list = convergenceWorksService.getListConvergenceWorkd( projId);
				
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/getConvergenceScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer, String> getConvergenceScheme(HttpServletRequest request, HttpServletResponse response){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();

		Map<Integer, String> map = new LinkedHashMap<>();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				map = convergenceWorksService.getConvergenceSchemeDetails();
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "/saveasdraftcnvrgdtl", method = RequestMethod.POST)
	@ResponseBody
	public String saveasdraftcnvrgdtl(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="workcode") List<String> workcode, @RequestParam(value ="cnvrgstatus") List<String> cnvrgstatus, @RequestParam(value ="cnvrgschmeid") List<String> cnvrgschmeid,
			@RequestParam(value ="expndwdc") List<String> expndwdc, @RequestParam(value ="expndcnvrgschm") List<String> expndcnvrgschm){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
//		Integer projId= Integer.parseInt(request.getParameter("project"));
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				 res = convergenceWorksService.saveasdraftcnvrgdtl(workcode, cnvrgstatus, cnvrgschmeid, expndwdc, expndcnvrgschm); 
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value = "/deleteCnvrgnceWorkCode", method = RequestMethod.POST)
	@ResponseBody
	public String deleteCnvrgnceWorkCode(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("workcode") Integer workcode){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = "fail";
		List<PfmsTransactionBean> list = new ArrayList<>();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = convergenceWorksService.deleteCnvrgnceWorkCode(workcode);
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
		
	@RequestMapping(value = "/completeConvergenceWorkCode", method = RequestMethod.POST)
	@ResponseBody
	public String completeConvergenceWorkCode(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="workcode1[]") String workcode1[]){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = convergenceWorksService.completeConvergenceWorkCode(workcode1);
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
	
	
	@RequestMapping(value = "/downloadExcelConvergedExpndtr", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelConvergedExpndtr(HttpServletRequest request, HttpServletResponse response) 
	{
		int stcd= Integer.parseInt(request.getParameter("state"));
		int dcode= Integer.parseInt(request.getParameter("district"));
		int projid= Integer.parseInt(request.getParameter("project"));
		int sid= Integer.parseInt(request.getParameter("scheme"));
		int finyr= Integer.parseInt(request.getParameter("finYear"));
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");	
		String schemeName= request.getParameter("schemeName");
		
		List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
		list=convergenceWorksService.getConvergedExpndtr(stcd, dcode, projid, finyr, sid);
				
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report CW4- State-wise, District-wise, Project-wise, Year-wise and Scheme-wise Convergence Work Details");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report CW4- State-wise, District-wise, Project-wise, Year-wise and Scheme-wise Convergence Work Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,8); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,0,0); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,1,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,2,4); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,5,5); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,6,6); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,7,7); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,8,8); 
		sheet.addMergedRegion(mergedRegion);
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State : "+ stName+"     District : "+distName+"     Project : "+projName+"     Scheme Name : "+schemeName+"     Financial Year : "+yearName);  
		cell.setCellStyle(style);
		
		for(int i=1;i<9;i++)
		{
			cell =rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("Name of the Activity");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Total No of Converged Works");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Status of Converged Works");  
		cell.setCellStyle(style);
		
		for(int i=3; i<5; i++) 
		{
			cell = rowhead.createCell(i); 
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Achievement");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Unit");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Expenditure Under WDC-PMKSY 2.0 (in Rs.)");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Expenditure Under Converged Schemes (in Rs.)");  
		cell.setCellStyle(style);

			
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<2;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(2);
		cell.setCellValue("Ongoing");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Completed");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(4);
		cell.setCellValue("Foreclosed");  
		cell.setCellStyle(style);
		
		for(int i=5;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0;i<9;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int rowno  = 9;
		int headVal = 0;
		int actCount = 1;
		int totConWorks = 0;
		int totOngoing = 0;
		int totCompleted = 0;
		int totForeclosed = 0;
		BigDecimal totAchievement = BigDecimal.ZERO;
		BigDecimal totExpndtrWDC2 = BigDecimal.ZERO;
		BigDecimal totExpndtrCnvrgdScheme = BigDecimal.ZERO;
		BigDecimal totalExpndtrWDC2 = BigDecimal.ZERO;
		BigDecimal totalExpndtrCnvrgdScheme = BigDecimal.ZERO;
		String Unit="";
		
		Row row = sheet.createRow(rowno);
	    for(ConvergenceWorksBean bean: list) {
	    	if(bean.getHead_code()!=headVal) {
	    		if(headVal!=0 && headVal<8) {
	    			row = sheet.createRow(rowno);
	    			cell = row.createCell(0);
	    			cell.setCellValue("Sub-Total");
	    			cell.setCellStyle(style);
	    			cell = row.createCell(1);
	    			cell.setCellValue(totConWorks);
	    			cell.setCellStyle(style);
	    			cell = row.createCell(2);
	    			cell.setCellValue(totOngoing);
	    			cell.setCellStyle(style);
	    			cell = row.createCell(3);
	    			cell.setCellValue(totCompleted);
	    			cell.setCellStyle(style);
	    			cell = row.createCell(4);
	    			cell.setCellValue(totForeclosed);
	    			cell.setCellStyle(style);
	    			cell = row.createCell(5);
	    			cell.setCellValue(totAchievement.doubleValue());
	    			cell.setCellStyle(style);
	    			cell = row.createCell(6);
	    			cell.setCellValue(Unit);
	    			cell.setCellStyle(style);
	    			cell = row.createCell(7);
	    			cell.setCellValue(totExpndtrWDC2.doubleValue());
	    			cell.setCellStyle(style);
	    			cell = row.createCell(8);
	    			cell.setCellValue(totExpndtrCnvrgdScheme.doubleValue());
	    			cell.setCellStyle(style);
	    		
	    			totConWorks = 0;
	    	    	totOngoing = 0;
	    	    	totCompleted = 0;
	    	    	totForeclosed = 0;
	    	    	totAchievement = BigDecimal.ZERO;    	
	    	    	totExpndtrWDC2 =  BigDecimal.ZERO;
	    	    	totExpndtrCnvrgdScheme = BigDecimal.ZERO;
	    	    	rowno++;
	    		}
	    		
	    		row = sheet.createRow(rowno);
	    		row.createCell(0).setCellValue(bean.getHead_code()+". "+bean.getHead_desc());
	    		actCount=1;
	    		headVal++;
	        	rowno++;
	    	}
	    	row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(bean.getHead_code()+"."+actCount+" "+bean.getActname()); 
	    	row.createCell(1).setCellValue(bean.getConvergedworks());
	    	row.createCell(2).setCellValue(bean.getOngoing());
	    	row.createCell(3).setCellValue(bean.getCompleted());
	    	row.createCell(4).setCellValue(bean.getForeclosed());
	    	row.createCell(5).setCellValue(bean.getAchievement()==null?0.00:bean.getAchievement().doubleValue());
	    	row.createCell(6).setCellValue(bean.getUnitname());
	    	row.createCell(7).setCellValue(bean.getExpndtr_wdc2()==null?0.00:bean.getExpndtr_wdc2().doubleValue());
	    	row.createCell(8).setCellValue(bean.getExpndtr_cnvrgd_scheme()==null?0.00:bean.getExpndtr_cnvrgd_scheme().doubleValue());
	    	
	    	
	    	Unit = bean.getUnitname();
	    	totConWorks = totConWorks + bean.getConvergedworks();
	    	totOngoing = totOngoing + bean.getOngoing();
	    	totCompleted = totCompleted + bean.getCompleted();
	    	totForeclosed = totForeclosed + bean.getForeclosed();
	    	totAchievement = totAchievement.add(bean.getAchievement()==null?BigDecimal.ZERO:bean.getAchievement());    	
	    	totExpndtrWDC2 =  totExpndtrWDC2.add(bean.getExpndtr_wdc2()==null?BigDecimal.ZERO:bean.getExpndtr_wdc2());
	    	totExpndtrCnvrgdScheme =  totExpndtrCnvrgdScheme.add(bean.getExpndtr_cnvrgd_scheme()==null?BigDecimal.ZERO:bean.getExpndtr_cnvrgd_scheme());
	    	totalExpndtrWDC2 =  totalExpndtrWDC2.add(bean.getExpndtr_wdc2()==null?BigDecimal.ZERO:bean.getExpndtr_wdc2());
	    	totalExpndtrCnvrgdScheme =  totalExpndtrCnvrgdScheme.add(bean.getExpndtr_cnvrgd_scheme()==null?BigDecimal.ZERO:bean.getExpndtr_cnvrgd_scheme());
	    	headVal = bean.getHead_code();
	    	actCount++;
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
//		font1.setColor(IndexedColors.WHITE.getIndex());
		style1.setFont(font1);
		
		
		mergedRegion = new CellRangeAddress(rowno,rowno,0,6); 
		sheet.addMergedRegion(mergedRegion);
		
		row = sheet.createRow(rowno);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		
		for(int i=1;i<7;i++)
		{
		cell = row.createCell(i);
		cell.setCellStyle(style1);
		}
		cell = row.createCell(7);
		cell.setCellValue(totalExpndtrWDC2.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totalExpndtrCnvrgdScheme.doubleValue());
		cell.setCellStyle(style1);
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size()+16, 8);
	    String fileName = "attachment; filename=Report CW4- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/ConvergedExpndtr";
	}

	@RequestMapping(value = "/downloadPDFConvergedExpndtr", method = RequestMethod.POST)
	public ModelAndView downloadPDFConvergedExpndtr(HttpServletRequest request, HttpServletResponse response) 
	{
		
		int stcd= Integer.parseInt(request.getParameter("state"));
		int dcode= Integer.parseInt(request.getParameter("district"));
		int projid= Integer.parseInt(request.getParameter("project"));
		int sid= Integer.parseInt(request.getParameter("scheme"));
		int finyr= Integer.parseInt(request.getParameter("finYear"));
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");	
		String schemeName= request.getParameter("schemeName");
		
		List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
		list=convergenceWorksService.getConvergedExpndtr(stcd, dcode, projid, finyr, sid);
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ConvergenceWorksExpndtrReport");
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
			
				paragraph3 = new Paragraph("Report CW4- State-wise, District-wise, Project-wise, Year-wise and Scheme-wise Convergence Work Details", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(9);
		        table.setWidths(new int[]{14, 5, 5, 5, 5, 5, 5 ,5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(4);
				
		        CommonFunctions.insertCellHeader(table,"State : "+ stName+"     District : "+distName+"     Project : "+projName+"     Scheme Name : "+schemeName+"     Financial Year : "+yearName, Element.ALIGN_LEFT, 9, 1, bf8Bold);
		        
				CommonFunctions.insertCellHeader(table, "Name of the Activity", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No of Converged Works", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Status of Converged Works", Element.ALIGN_CENTER, 3, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Unit", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Expenditure Under WDC-PMKSY 2.0 (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Expenditure Under Converged Schemes (in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Foreclosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				int headVal = 0;
				int actCount = 1;
				int totConWorks = 0;
				int totOngoing = 0;
				int totCompleted = 0;
				int totForeclosed = 0;
				BigDecimal totAchievement = BigDecimal.ZERO;
				BigDecimal totExpndtrWDC2 = BigDecimal.ZERO;
				BigDecimal totExpndtrCnvrgdScheme = BigDecimal.ZERO;
				BigDecimal totalExpndtrWDC2 = BigDecimal.ZERO;
				BigDecimal totalExpndtrCnvrgdScheme = BigDecimal.ZERO;
				String Unit="";
						
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) {
						if(list.get(i).getHead_code()!=headVal) {
							if(headVal!=0 && headVal<8) {
								CommonFunctions.insertCell3(table,"Sub-Total", Element.ALIGN_LEFT, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table,String.valueOf(totConWorks), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table,String.valueOf(totOngoing), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table,String.valueOf(totCompleted), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table,String.valueOf(totForeclosed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table,String.valueOf(String.format(Locale.US,"%.2f",totAchievement)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table,Unit, Element.ALIGN_LEFT, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table,String.valueOf(totExpndtrWDC2), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table,String.valueOf(totExpndtrCnvrgdScheme), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
								
								totConWorks = 0;
				    	    	totOngoing = 0;
				    	    	totCompleted = 0;
				    	    	totForeclosed = 0;
				    	    	totAchievement = BigDecimal.ZERO;    	
				    	    	totExpndtrWDC2 =  BigDecimal.ZERO;
				    	    	totExpndtrCnvrgdScheme = BigDecimal.ZERO;
							}
							CommonFunctions.insertCell(table,(list.get(i).getHead_code()+". "+ list.get(i).getHead_desc()), Element.ALIGN_LEFT, 9, 1, bf8);
							actCount = 1;
							headVal++;
						}
						
						CommonFunctions.insertCell(table,(list.get(i).getHead_code()+"."+actCount+" "+list.get(i).getActname()), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table,String.valueOf(list.get(i).getConvergedworks()), Element.ALIGN_RIGHT, 1, 1, bf8); 
						CommonFunctions.insertCell(table,String.valueOf(list.get(i).getOngoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table,String.valueOf(list.get(i).getCompleted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table,String.valueOf(list.get(i).getForeclosed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getAchievement()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getUnitname()), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getExpndtr_wdc2()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getExpndtr_cnvrgd_scheme()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						Unit = list.get(i).getUnitname();
				    	totConWorks = totConWorks + list.get(i).getConvergedworks();
				    	totOngoing = totOngoing + list.get(i).getOngoing();
				    	totCompleted = totCompleted + list.get(i).getCompleted();
				    	totForeclosed = totForeclosed + list.get(i).getForeclosed();
				    	totAchievement = totAchievement.add(list.get(i).getAchievement()==null?BigDecimal.ZERO:list.get(i).getAchievement());    	
				    	totExpndtrWDC2 =  totExpndtrWDC2.add(list.get(i).getExpndtr_wdc2()==null?BigDecimal.ZERO:list.get(i).getExpndtr_wdc2());
				    	totExpndtrCnvrgdScheme =  totExpndtrCnvrgdScheme.add(list.get(i).getExpndtr_cnvrgd_scheme()==null?BigDecimal.ZERO:list.get(i).getExpndtr_cnvrgd_scheme());
				    	totalExpndtrWDC2 =  totalExpndtrWDC2.add(list.get(i).getExpndtr_wdc2()==null?BigDecimal.ZERO:list.get(i).getExpndtr_wdc2());
				    	totalExpndtrCnvrgdScheme =  totalExpndtrCnvrgdScheme.add(list.get(i).getExpndtr_cnvrgd_scheme()==null?BigDecimal.ZERO:list.get(i).getExpndtr_cnvrgd_scheme());
				    	headVal = list.get(i).getHead_code();
						actCount++;
					}
					
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 7, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totalExpndtrWDC2), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totalExpndtrCnvrgdScheme), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 9, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report CW4- State.pdf");
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

	@RequestMapping(value = "/downloadExcelEnteredConWorks", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelEnteredConWorks(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();

		list = convergenceWorksService.getEnteredConWorks();
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report CW1- State-wise Entry Status of Converged Work Details");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report CW1- State-wise Entry Status of Converged Work Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowhead = sheet.createRow(5);
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total No. of District");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total No. of Project");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Total No. of Works");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Total No. of Converged Works");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Total No. of Works Entered Convergence Detail");  
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<7;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 7;
		int totdist = 0;
		int totproj = 0;
		int totwrks = 0;
		int conwrks = 0;
		int enteredwrks = 0;
		
		
	    for(ConvergenceWorksBean bean: list) 
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getSt_name());
	    	row.createCell(2).setCellValue(bean.getTotaldist());
	    	row.createCell(3).setCellValue(bean.getTotalproject());
	    	row.createCell(4).setCellValue(bean.getTotalworks());
	    	row.createCell(5).setCellValue(bean.getConvergedworks());
	    	row.createCell(6).setCellValue(bean.getEnteredcon());
	    	
	    	totdist = totdist + bean.getTotaldist();
	    	totproj = totproj + bean.getTotalproject();
	    	totwrks = totwrks + bean.getTotalworks();
	    	conwrks = conwrks + bean.getConvergedworks();
	    	enteredwrks = enteredwrks + bean.getEnteredcon();

	    	sno++;
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

		Row row = sheet.createRow(list.size()+7);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellValue(totdist);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totproj);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totwrks);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(conwrks);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(enteredwrks);
		cell.setCellStyle(style1);

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 6);
	    String fileName = "attachment; filename=Report CW1- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/EnteredConvergedWorks";
	}


	@RequestMapping(value = "/downloadPDFEnteredConWorks", method = RequestMethod.POST)
	public ModelAndView downloadPDFEnteredConWorks(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();

		list = convergenceWorksService.getEnteredConWorks();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("EnteredConWorksReport");
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
			
			paragraph3 = new Paragraph("Report CW1- State-wise Entry Status of Converged Work Details", f3);
				
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(7);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(60);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(2);
		        
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of District", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Converged Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Works Entered Convergence Detail", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
		        
			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
			int k = 1;
			int totdist = 0;
			int totproj = 0;
			int totwrks = 0;
			int conwrks = 0;
			int enteredwrks = 0;

				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotaldist()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalproject()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getConvergedworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getEnteredcon()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
				    	
				    totdist = totdist + list.get(i).getTotaldist();
				    totproj = totproj + list.get(i).getTotalproject();
				    totwrks = totwrks + list.get(i).getTotalworks();
				    conwrks = conwrks + list.get(i).getConvergedworks();
				    enteredwrks = enteredwrks + list.get(i).getEnteredcon();
						
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totdist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totwrks), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(conwrks), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(enteredwrks), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report CW1- State.pdf");
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
	
	@RequestMapping(value = "/downloadExcelStatusConWorks", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStatusConWorks(HttpServletRequest request, HttpServletResponse response) 
	{
		
		Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
		
		List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
		
		list = convergenceWorksService.getStatusConWorks(stcd);
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("ES2- Entry Status of Converged Work Details");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "ES2- Entry Status of Converged Work Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowhead = sheet.createRow(5);
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total No. of Project");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total No. of Works");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Total No. of Converged Works");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Total No. of Works Entered Convergence Detail");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Total No. of Remaining Works");  
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<7;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 7;
		int totproj = 0;
		int totwrks = 0;
		int conwrks = 0;
		int enteredwrks = 0;
		int remainingwrks = 0;
		
		
	    for(ConvergenceWorksBean bean: list) 
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getDist_name());
	    	row.createCell(2).setCellValue(bean.getTotalproject());
	    	row.createCell(3).setCellValue(bean.getTotalworks());
	    	row.createCell(4).setCellValue(bean.getConvergedworks());
	    	row.createCell(5).setCellValue(bean.getEnteredcon());
	    	row.createCell(6).setCellValue(bean.getRemaining());
	    	
	    	totproj = totproj + bean.getTotalproject();
	    	totwrks = totwrks + bean.getTotalworks();
	    	conwrks = conwrks + bean.getConvergedworks();
	    	enteredwrks = enteredwrks + bean.getEnteredcon();
	    	remainingwrks = remainingwrks + bean.getRemaining();

	    	sno++;
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

		Row row = sheet.createRow(list.size()+7);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellValue(totproj);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totwrks);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(conwrks);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(enteredwrks);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(remainingwrks);
		cell.setCellStyle(style1);

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 6);
	    String fileName = "attachment; filename=ES2- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/StatusConvergedWorks";
	}


	@RequestMapping(value = "/downloadPDFStatusConWorks", method = RequestMethod.POST)
	public ModelAndView downloadPDFStatusConWorks(HttpServletRequest request, HttpServletResponse response) 
	{
		
		Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
		
		List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
		
		list = convergenceWorksService.getStatusConWorks(stcd);
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("StatusConWorksReport");
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
			
			paragraph3 = new Paragraph("ES2- Entry Status of Converged Work Details", f3);
				
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(7);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(60);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(2);
		        
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Converged Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Works Entered Convergence Detail", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total No. of Remaining Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
		        
			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
			int k = 1;
			int totproj = 0;
			int totwrks = 0;
			int conwrks = 0;
			int enteredwrks = 0;
			int remainingwrks = 0;

				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalproject()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getConvergedworks()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getEnteredcon()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getRemaining()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
				    	
				    totproj = totproj + list.get(i).getTotalproject();
				    totwrks = totwrks + list.get(i).getTotalworks();
				    conwrks = conwrks + list.get(i).getConvergedworks();
				    enteredwrks = enteredwrks + list.get(i).getEnteredcon();
				    remainingwrks = remainingwrks + list.get(i).getRemaining();
						
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totwrks), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(conwrks), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(enteredwrks), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(remainingwrks), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=ES2- State.pdf");
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
	
	@RequestMapping(value = "/getConvergenceStatus", method = RequestMethod.GET)
	public ModelAndView getConvergenceStatus(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("updateConvergenceStatus");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				listm=profileService.getMapstate(regId, userType);
				int stCode = 0;
				for(ProfileBean bean : listm) {
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
				}
				mav.addObject("projectList", pfmsSrvc.getProjectByStCode(stCode));
				mav.addObject("headList", physicalActionPlanService.getHead());

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/getConvergenceStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<ConvergenceStatusBean> getConvergenceStatus(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam(value ="projId") Integer projId, @RequestParam(value ="head") Integer head, @RequestParam(value ="activity") Integer activity) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<ConvergenceStatusBean> statusList = new ArrayList<>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("updateConvergenceStatus");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				mav.addObject("projectList", pmservice.getProjectByRegId(regId));
				mav.addObject("headList", physicalActionPlanService.getHead());
				statusList = convergenceWorksService.getConvergenceStatus(projId, head, activity);

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusList;
	}
	
	@RequestMapping(value = "/updateConvergenceStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateConvergenceStatus(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="workcode") List<String> workcode, @RequestParam(value ="cnvrgstatus") List<String> cnvrgstatus){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
//		Integer projId= Integer.parseInt(request.getParameter("project"));
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				 res = convergenceWorksService.updateConvergenceStatus(workcode, cnvrgstatus); 
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value = "/downloadExcelConvergedProjDetailWorks", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelConvergedProjDetailWorks(HttpServletRequest request, HttpServletResponse response) 
	{
//		String finyr = request.getParameter("finyr");
//		String finName = request.getParameter("finName");
		String projid = request.getParameter("projid");
		String distName = request.getParameter("distName");
		String stName = request.getParameter("stName");
		Map<String,Map<BigDecimal,String>> stgeMap = new LinkedHashMap<>();
		
		List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
		
		list = convergenceWorksService.getConvergedProjDetailWorks(Integer.parseInt(projid));
			
		list.forEach(s->{
			Map<BigDecimal,String> map = new LinkedHashMap<>();
				stgeMap.put("Pre Implementation"+","+s.getWorkcode(), map);
				stgeMap.put("Mid Implementation"+","+s.getWorkcode(), map);
				stgeMap.put("Post Implementation"+","+s.getWorkcode(), map);
		});
		
		list.forEach(s->{
			Map<BigDecimal,String> map = new LinkedHashMap<>();
			if(stgeMap.containsKey(s.getStage()+","+s.getWorkcode())) {
				if(stgeMap.get(s.getStage()+","+s.getWorkcode())!=null)
					map.putAll(stgeMap.get(s.getStage()+","+s.getWorkcode()));
				
				map.put(s.getCollection_sno(), s.getStatus());
				stgeMap.put(s.getStage()+","+s.getWorkcode(), map);
			}
		});
		
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 10, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(5,5,0,10); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,0,0); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,1,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,2,2); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,3,3); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,4,4); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,5,5); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,6,6); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,7,7); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,8,10); 
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State Name : "+ stName+"     District Name : "+ distName);  
		cell.setCellStyle(style);
		
		for(int i=1;i<11;i++)
		{
			cell = rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Project Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Block Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Gram Panchayat Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Village Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Work Code"); 
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Head Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Activity Name");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Stage");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(9);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(10);
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<8;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Pre Implementation");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Mid Implementation");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(10);
		cell.setCellValue("Post Implementation");  
		cell.setCellStyle(style);
		
			
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0;i<11;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		BigInteger wrkcd = BigInteger.ZERO;
		
	    for(ConvergenceWorksBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	
	    	if(!bean.getWorkcode().equals(wrkcd)) {
	    		row.createCell(0).setCellValue(sno); 
		    	row.createCell(1).setCellValue(bean.getProj_name());
		    	row.createCell(2).setCellValue(bean.getBlockname());
		        row.createCell(3).setCellValue(bean.getGrampanchayatname());
		    	row.createCell(4).setCellValue(bean.getVillagename());
		    	row.createCell(5).setCellValue(bean.getWorkcode().doubleValue());
		    	row.createCell(6).setCellValue(bean.getHeadname());
		    	row.createCell(7).setCellValue(bean.getActname());
    			int no = 8;
    			for(Map.Entry<String, Map<BigDecimal, String>> map:stgeMap.entrySet()) {
    				if(bean.getWorkcode().equals(new BigInteger(map.getKey().substring(map.getKey().indexOf(",")+1)))) {
    					for(Map.Entry<BigDecimal, String> subMap : map.getValue().entrySet()) {
    						row.createCell(no).setCellValue(subMap.getValue());
    					}
    					no++;
    				}
    			}
    			wrkcd = bean.getWorkcode();
    		}
	    	sno++;
	    	rowno++;
	    }
	    
	    
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 10);
	    String fileName = "attachment; filename=Report CW2- ProjectDetail.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/ConvergedProjDetailWorks";
	}
	
	@RequestMapping(value = "/downloadPDFConvergedProjDetailWorks", method = RequestMethod.POST)
	public ModelAndView downloadPDFConvergedProjDetailWorks(HttpServletRequest request, HttpServletResponse response) 
	{
//		String finyr = request.getParameter("finyr");
//		String finName = request.getParameter("finName");
		String projid = request.getParameter("projid");
		String distName = request.getParameter("distName");
		String stName = request.getParameter("stName");
		Map<String,Map<BigDecimal,String>> stgeMap = new LinkedHashMap<>();
		
		List<ConvergenceWorksBean> list = new ArrayList<ConvergenceWorksBean>();
		
		list = convergenceWorksService.getConvergedProjDetailWorks(Integer.parseInt(projid));
			
		list.forEach(s->{
			Map<BigDecimal,String> map = new LinkedHashMap<>();
				stgeMap.put("Pre Implementation"+","+s.getWorkcode(), map);
				stgeMap.put("Mid Implementation"+","+s.getWorkcode(), map);
				stgeMap.put("Post Implementation"+","+s.getWorkcode(), map);
		});
		
		list.forEach(s->{
			Map<BigDecimal,String> map = new LinkedHashMap<>();
			if(stgeMap.containsKey(s.getStage()+","+s.getWorkcode())) {
				if(stgeMap.get(s.getStage()+","+s.getWorkcode())!=null)
					map.putAll(stgeMap.get(s.getStage()+","+s.getWorkcode()));
				
				map.put(s.getCollection_sno(), s.getStatus());
				stgeMap.put(s.getStage()+","+s.getWorkcode(), map);
			}
		});
		 	
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ConvergedProjDetailWorksReport");
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
			
				paragraph3 = new Paragraph("Report CW2- State-wise, District-wise, Project-wise Convergence Work with Geotagging Details", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(11);
		        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(4);
							
		        CommonFunctions.insertCellHeader(table,"State Name : "+ stName+"     District Name : "+ distName, Element.ALIGN_LEFT, 11, 1, bf8Bold);
		        
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Block Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Gram Panchayat Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Work Code", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Head Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Activity Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Stage", Element.ALIGN_CENTER, 3, 1, bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "Pre Implementation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Mid Implementation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Post Implementation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
		        
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

				
				int k = 1;
				BigInteger wrkcd = BigInteger.ZERO;
				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						if(!list.get(i).getWorkcode().equals(wrkcd)) {
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getBlockname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getGrampanchayatname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getVillagename(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getWorkcode()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getHeadname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getActname(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						
						for(Map.Entry<String, Map<BigDecimal, String>> map : stgeMap.entrySet()) {
		    				if(list.get(i).getWorkcode().equals(new BigInteger(map.getKey().substring(map.getKey().indexOf(",")+1)))) {
		    					if(map.getValue().isEmpty()) {
			    					CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
			    				}
		    					
		    					for(Map.Entry<BigDecimal, String> subMap : map.getValue().entrySet()) {
		    						CommonFunctions.insertCell(table, subMap.getValue(), Element.ALIGN_CENTER, 1, 1, bf8);
		    					}
		    				}
		    			}
						
		    			wrkcd = list.get(i).getWorkcode();
						k++;
						}
					}
					
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 11, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report CW2- ProjectDetail.pdf");
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
	
}
