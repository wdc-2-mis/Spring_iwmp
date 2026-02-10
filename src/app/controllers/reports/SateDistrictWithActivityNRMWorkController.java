package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;

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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

import app.bean.NRSCWorksBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.service.PhysicalActionPlanService;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.reports.PhysicalActionAchievementService;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.service.VillageWatershedYatraReportService;

@Controller("SateDistrictWithActivityNRMWorkController")
public class SateDistrictWithActivityNRMWorkController {
	
	HttpSession session; 
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired
	PhysicalActionPlanService physicalActionPlanService;
	
	@Autowired(required = true)
	VillageWatershedYatraReportService service;
	
	private Map<String, String> actList;
	private Map<String, String> districtList;
	
	@RequestMapping(value="/getActivityNRMWorkReport", method = RequestMethod.GET)
	public ModelAndView getActivityNRMWorkReport(HttpServletRequest request, HttpServletResponse response)
	{
		
			ModelAndView mav = new ModelAndView();
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String finyr= request.getParameter("finyear");
			String head= request.getParameter("head");
			
			mav = new ModelAndView("reports/sateDistrictWithActivityNRMWork");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			LinkedHashMap<Integer, String> headList = userService.getHead();
			mav.addObject("headList",headList);
			
			mav.addObject("stateList",stateMasterService.getAllState());
			mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
			
			if(userState!=null && !userState.equals("")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			
			if(head!=null && !head.equals("")) 
			{
				actList = userService.getActivityList(Integer.parseInt(head));
				mav.addObject("activityList", actList);
			}
			
			mav.addObject("state", userState);
			mav.addObject("dist", district);
			mav.addObject("yrcd", finyr);
			mav.addObject("headcd", head);
			
		return mav; 
	}
	
	@RequestMapping(value="/getActivityNRMWorkReportPost", method = RequestMethod.POST)
	public ModelAndView getActivityNRMWorkReportPost(HttpServletRequest request, HttpServletResponse response)
	{
			
		//	String st_code=session.getAttribute("stateCode").toString();
			ModelAndView mav = new ModelAndView();
			
			String userState= request.getParameter("state");
			String district= request.getParameter("district");
			String finyr= request.getParameter("finyear");
			String head= request.getParameter("head");
			String activity= request.getParameter("activity");
			
			mav = new ModelAndView("reports/sateDistrictWithActivityNRMWork");
			mav.addObject("menu", menuController.getMenuUserId(request));
			
			List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
			String fromDateStr=null;
			String toDateStr =null;
			
			list=service.getActivityNRMWorkReportPost(Integer.parseInt(userState), Integer.parseInt(district), Integer.parseInt(finyr), Integer.parseInt(activity));
				
				int createdwork=0;
				int startedwork=0;
				int ongoing=0;
				int completed=0;
				int forclosed=0;
				
				
				for(NRSCWorksBean bean: list) 
				{
					
					createdwork = createdwork + bean.getCreatedwork();
					
					ongoing = ongoing + bean.getOngoing();
					completed = completed + bean.getCompleted();
					forclosed = forclosed + bean.getForclosed();
					
				}
				mav.addObject("createdwork1", createdwork);
				mav.addObject("startedwork1", startedwork);
				mav.addObject("ongoing1", ongoing);
				mav.addObject("completed1", completed);
				mav.addObject("forclosed1", forclosed);
				
				
					mav.addObject("dataList", list);
					mav.addObject("dataListSize", list.size());
				
				LinkedHashMap<Integer, String> headList = userService.getHead();
				mav.addObject("headList",headList);
				
				mav.addObject("stateList",stateMasterService.getAllState());
				mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
				
				if(userState!=null && !userState.equals("")) 
				{
					districtList = userService.getDistrictList(Integer.parseInt(userState));
					mav.addObject("districtList", districtList);
				}
				
				if(head!=null && !head.equals("")) 
				{
					actList = userService.getActivityList(Integer.parseInt(head));
					mav.addObject("activityList", actList);
				}
				
				mav.addObject("state", userState);
				mav.addObject("dist", district);
				mav.addObject("yrcd", finyr);
				mav.addObject("headcd", head);
				mav.addObject("actcd", activity);
				
			//	mav.addObject("stName", stName);
				
			
			return mav; 
	}
	
	@RequestMapping(value="/getActivityNRMWorkJalSakati", method = RequestMethod.GET)
	public ModelAndView getActivityNRMWorkJalSakati(HttpServletRequest request, HttpServletResponse response)
	{
			
			ModelAndView mav = new ModelAndView();
			
			mav = new ModelAndView("reports/activityNRMWorkJalSakati");
			mav.addObject("menu", menuController.getMenuUserId(request));
			mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
			
			List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
			list=service.getActivityNRMWorkJalSakati(0);
			BigInteger projcountt = BigInteger.ZERO;
			BigDecimal area_proposedt=BigDecimal.ZERO;
			BigDecimal project_costt=BigDecimal.ZERO;
			BigInteger farmpondst = BigInteger.ZERO;
			BigInteger checkdamst = BigInteger.ZERO;
			BigInteger nallahbundst = BigInteger.ZERO;
			BigInteger percolationtankst = BigInteger.ZERO;
			BigInteger groundwaterrechargestructuret = BigInteger.ZERO;
			BigInteger gullyplugst = BigInteger.ZERO;
			BigInteger otherst = BigInteger.ZERO;
			BigInteger amritsarovart = BigInteger.ZERO;
			
				for(NRSCWorksBean bean: list) 
				{
					projcountt=projcountt.add(bean.getProjcount());
					area_proposedt=area_proposedt.add(bean.getArea_proposed());
					project_costt=project_costt.add(bean.getProject_cost());
					
					farmpondst=farmpondst.add(bean.getFarmponds());
					checkdamst=checkdamst.add(bean.getCheckdams());
					nallahbundst=nallahbundst.add(bean.getNallahbunds());
					percolationtankst=percolationtankst.add(bean.getPercolationtanks());
					groundwaterrechargestructuret=groundwaterrechargestructuret.add(bean.getGroundwaterrechargestructure());
					gullyplugst=gullyplugst.add(bean.getGullyplugs());
					otherst=otherst.add(bean.getOthers());
					amritsarovart=amritsarovart.add(bean.getAmritsarovar());
				}
				mav.addObject("dataList", list);
				mav.addObject("dataListSize", list.size());
				
				mav.addObject("projcount", projcountt);
				mav.addObject("area_proposed", area_proposedt);
				mav.addObject("project_cost", project_costt);
				mav.addObject("farmponds", farmpondst);
				mav.addObject("checkdams", checkdamst);
				mav.addObject("nallahbunds", nallahbundst);
				mav.addObject("percolationtanks", percolationtankst);
				mav.addObject("groundwaterrechargestructure", groundwaterrechargestructuret);
				mav.addObject("gullyplugs", gullyplugst);
				mav.addObject("others", otherst);
				mav.addObject("amritsarovar", amritsarovart);
				
			return mav; 
	}
	
	@RequestMapping(value="/getActivityNRMWorkJalSakati", method = RequestMethod.POST)
	public ModelAndView getActivityNRMWorkJalSakati1(HttpServletRequest request, HttpServletResponse response)
	{
			
			ModelAndView mav = new ModelAndView();
//			String userState= request.getParameter("state");
//			String district= request.getParameter("district");
			String finyr= request.getParameter("finyear");
//			String head= request.getParameter("head");
//			String activity= request.getParameter("activity");
			Integer yrcd = Integer.parseInt(finyr);
			mav = new ModelAndView("reports/activityNRMWorkJalSakati");
			mav.addObject("menu", menuController.getMenuUserId(request));
			mav.addObject("yrcd", finyr);
			mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
			
			List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
			list=service.getActivityNRMWorkJalSakati(yrcd);
			
			BigInteger projcountt = BigInteger.ZERO;
			BigDecimal area_proposedt=BigDecimal.ZERO;
			BigDecimal project_costt=BigDecimal.ZERO;
			BigInteger farmpondst = BigInteger.ZERO;
			BigInteger checkdamst = BigInteger.ZERO;
			BigInteger nallahbundst = BigInteger.ZERO;
			BigInteger percolationtankst = BigInteger.ZERO;
			BigInteger groundwaterrechargestructuret = BigInteger.ZERO;
			BigInteger gullyplugst = BigInteger.ZERO;
			BigInteger otherst = BigInteger.ZERO;
			BigInteger amritsarovart = BigInteger.ZERO;
			
				for(NRSCWorksBean bean: list) 
				{
					projcountt=projcountt.add(bean.getProjcount());
					area_proposedt=area_proposedt.add(bean.getArea_proposed());
					project_costt=project_costt.add(bean.getProject_cost());
					
					farmpondst=farmpondst.add(bean.getFarmponds());
					checkdamst=checkdamst.add(bean.getCheckdams());
					nallahbundst=nallahbundst.add(bean.getNallahbunds());
					percolationtankst=percolationtankst.add(bean.getPercolationtanks());
					groundwaterrechargestructuret=groundwaterrechargestructuret.add(bean.getGroundwaterrechargestructure());
					gullyplugst=gullyplugst.add(bean.getGullyplugs());
					otherst=otherst.add(bean.getOthers());
					amritsarovart=amritsarovart.add(bean.getAmritsarovar());
				}
				mav.addObject("dataList", list);
				mav.addObject("dataListSize", list.size());
				
				mav.addObject("projcount", projcountt);
				mav.addObject("area_proposed", area_proposedt);
				mav.addObject("project_cost", project_costt);
				mav.addObject("farmponds", farmpondst);
				mav.addObject("checkdams", checkdamst);
				mav.addObject("nallahbunds", nallahbundst);
				mav.addObject("percolationtanks", percolationtankst);
				mav.addObject("groundwaterrechargestructure", groundwaterrechargestructuret);
				mav.addObject("gullyplugs", gullyplugst);
				mav.addObject("others", otherst);
				mav.addObject("amritsarovar", amritsarovart);
				
			return mav; 
	}
	
	@RequestMapping(value = "/getActivityNRMWorkJalSakatiPDF", method = RequestMethod.POST)
	public ModelAndView getActivityNRMWorkJalSakatiPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String finyr= request.getParameter("finyear");
		Integer yrcd = Integer.parseInt(finyr);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/activityNRMWorkJalSakati");
		mav.addObject("menu", menuController.getMenuUserId(request));
		
		List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
		list=service.getActivityNRMWorkJalSakati(yrcd);
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Report ME10- Water Scarce Districts in The Country Identified under JSA:CTR 2025");
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
			
				paragraph3 = new Paragraph("Report ME10- Water Scarce Districts in The Country Identified under JSA:CTR 2025", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(14);
				table.setWidths(new int[] { 2, 8, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				String finyear = "20"+yrcd+"-"+(yrcd+1);
				CommonFunctions.insertCellHeader(table, "Financial Year: "+finyear, Element.ALIGN_LEFT, 14, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area Sanctioned (Ha)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Cost (Rs. Lakh)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Farm Ponds", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Check Dams", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Nallah Bunds", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Percolation Tanks", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ground water Recharge Structure", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Gully Plugs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Amrit Sarovar", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				for(int i =1;i<15;i++) {
					CommonFunctions.insertCellHeader(table, i+"", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				}
				
				String state = "";
				int i=0;
				int k=1;
				BigInteger projcountt = BigInteger.ZERO;
				BigDecimal area_proposedt=BigDecimal.ZERO;
				BigDecimal project_costt=BigDecimal.ZERO;
				BigInteger farmpondst = BigInteger.ZERO;
				BigInteger checkdamst = BigInteger.ZERO;
				BigInteger nallahbundst = BigInteger.ZERO;
				BigInteger percolationtankst = BigInteger.ZERO;
				BigInteger groundwaterrechargestructuret = BigInteger.ZERO;
				BigInteger gullyplugst = BigInteger.ZERO;
				BigInteger otherst = BigInteger.ZERO;
				BigInteger amritsarovart = BigInteger.ZERO;
				if(list.size()!=0)
					for( i=0;i<list.size();i++) 
					{
						if(!list.get(i).getStatename().equals(state)) {
							state = list.get(i).getStatename();
							CommonFunctions.insertCell(table, k+"", Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list.get(i).getStatename(), Element.ALIGN_LEFT, 1, 1, bf8);
							k = k+1;
						}else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						}
						CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProjcount().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_proposed().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProject_cost().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFarmponds().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCheckdams().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNallahbunds().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPercolationtanks().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getGroundwaterrechargestructure().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getGullyplugs().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getAmritsarovar().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOthers().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						projcountt=projcountt.add(list.get(i).getProjcount());
						area_proposedt=area_proposedt.add(list.get(i).getArea_proposed());
						project_costt=project_costt.add(list.get(i).getProject_cost());
						
						farmpondst=farmpondst.add(list.get(i).getFarmponds());
						checkdamst=checkdamst.add(list.get(i).getCheckdams());
						nallahbundst=nallahbundst.add(list.get(i).getNallahbunds());
						percolationtankst=percolationtankst.add(list.get(i).getPercolationtanks());
						groundwaterrechargestructuret=groundwaterrechargestructuret.add(list.get(i).getGroundwaterrechargestructure());
						gullyplugst=gullyplugst.add(list.get(i).getGullyplugs());
						otherst=otherst.add(list.get(i).getOthers());
						amritsarovart=amritsarovart.add(list.get(i).getAmritsarovar());
					}
				 CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 3, 1, bf10Bold);
		         CommonFunctions.insertCell3(table, String.valueOf(projcountt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		         CommonFunctions.insertCell3(table, String.format(Locale.US, "%.2f",area_proposedt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		         CommonFunctions.insertCell3(table, String.format(Locale.US, "%.2f",project_costt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	        	 CommonFunctions.insertCell3(table, String.valueOf(farmpondst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		         CommonFunctions.insertCell3(table, String.valueOf(checkdamst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		         CommonFunctions.insertCell3(table, String.valueOf(nallahbundst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		         CommonFunctions.insertCell3(table, String.valueOf(percolationtankst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		         CommonFunctions.insertCell3(table, String.valueOf(groundwaterrechargestructuret), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		         CommonFunctions.insertCell3(table, String.valueOf(gullyplugst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		         CommonFunctions.insertCell3(table, String.valueOf(amritsarovart), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		         CommonFunctions.insertCell3(table, String.valueOf(otherst), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		         
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 14, 1, bf8);
				
					
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
				response.setHeader("Content-Disposition", "attachment;filename=Report ME10.pdf");
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
	
	
	@RequestMapping(value = "/getActivityNRMWorkJalSakatiExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getActivityNRMWorkJalSakatiExcel(HttpServletRequest request, HttpServletResponse response)
	{
		String finyr= request.getParameter("finyear");
		Integer yrcd = Integer.parseInt(finyr);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/activityNRMWorkJalSakati");
		mav.addObject("menu", menuController.getMenuUserId(request));
		
		List<NRSCWorksBean> list = new ArrayList<NRSCWorksBean>();
		list=service.getActivityNRMWorkJalSakati(yrcd);
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report ME10- Water Scarce Districts in The Country Identified under JSA:CTR 2025");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report ME10- Water Scarce Districts in The Country Identified under JSA:CTR 2025";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 13, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(5,5,0,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,2);
		sheet.addMergedRegion(mergedRegion);
		
		String finyear = "20"+yrcd+"-"+(yrcd+1);
		Row rowhead = sheet.createRow(5);
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("Financial Year: "+finyear);
		cell.setCellStyle(style);
		
		for(int i=1;i<14;i++)
		{
			rowhead.createCell(i).setCellStyle(style);
		}
		
		rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("District");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("No. of Project");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Area Sanctioned (Ha)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Project Cost (Rs. Lakh)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Farm Ponds");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Check Dams");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Nallah Bunds");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Percolation Tanks");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Ground water Recharge Structure");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("Gully Plugs");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(12);
		cell.setCellValue("Amrit Sarovar");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(13);
		cell.setCellValue("Others");
		cell.setCellStyle(style);
		
			
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<14;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		// Freeze the top 7 rows (0–6) so headers stay visible
		sheet.createFreezePane(0, 8);

		int rowno  = 8;
		String state = "";
		int k=1;
		BigInteger projcountt = BigInteger.ZERO;
		BigDecimal area_proposedt=BigDecimal.ZERO;
		BigDecimal project_costt=BigDecimal.ZERO;
		BigInteger farmpondst = BigInteger.ZERO;
		BigInteger checkdamst = BigInteger.ZERO;
		BigInteger nallahbundst = BigInteger.ZERO;
		BigInteger percolationtankst = BigInteger.ZERO;
		BigInteger groundwaterrechargestructuret = BigInteger.ZERO;
		BigInteger gullyplugst = BigInteger.ZERO;
		BigInteger otherst = BigInteger.ZERO;
		BigInteger amritsarovart = BigInteger.ZERO;
		
	    for(NRSCWorksBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	if(!bean.getStatename().equals(state)){
	    		state = bean.getStatename();
	        	row.createCell(0).setCellValue(k);
	        	k= k+1; 
	        	row.createCell(1).setCellValue(bean.getStatename());
	        }
	    	
	    	row.createCell(2).setCellValue(bean.getDist_name());
	    	row.createCell(3).setCellValue(bean.getProjcount().doubleValue());
	    	row.createCell(4).setCellValue(bean.getArea_proposed().doubleValue());
	    	row.createCell(5).setCellValue(bean.getProject_cost().doubleValue());
	    	row.createCell(6).setCellValue(bean.getFarmponds().doubleValue());
		    row.createCell(7).setCellValue(bean.getCheckdams().doubleValue());
		    row.createCell(8).setCellValue(bean.getNallahbunds().doubleValue());
	    	row.createCell(9).setCellValue(bean.getPercolationtanks().doubleValue());
	    	row.createCell(10).setCellValue(bean.getGroundwaterrechargestructure().doubleValue());
	    	row.createCell(11).setCellValue(bean.getGullyplugs().doubleValue());
	    	row.createCell(12).setCellValue(bean.getAmritsarovar().doubleValue());
		    row.createCell(13).setCellValue(bean.getOthers().doubleValue());
	    	
		    projcountt=projcountt.add(bean.getProjcount());
			area_proposedt=area_proposedt.add(bean.getArea_proposed());
			project_costt=project_costt.add(bean.getProject_cost());
			
			farmpondst=farmpondst.add(bean.getFarmponds());
			checkdamst=checkdamst.add(bean.getCheckdams());
			nallahbundst=nallahbundst.add(bean.getNallahbunds());
			percolationtankst=percolationtankst.add(bean.getPercolationtanks());
			groundwaterrechargestructuret=groundwaterrechargestructuret.add(bean.getGroundwaterrechargestructure());
			gullyplugst=gullyplugst.add(bean.getGullyplugs());
			otherst=otherst.add(bean.getOthers());
			amritsarovart=amritsarovart.add(bean.getAmritsarovar());
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
        
		
		
        Row row = sheet.createRow(rowno);
        cell = row.createCell(0);
        cell.setCellValue("Grand Total");
        cell.setCellStyle(style1);
        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell = row.createCell(2);
        cell.setCellStyle(style1);
        
        cell = row.createCell(3);
        cell.setCellValue(projcountt.doubleValue());
        cell.setCellStyle(style1);
        
        cell = row.createCell(4);
        cell.setCellValue(area_proposedt.doubleValue());
        cell.setCellStyle(style1);
        
        cell = row.createCell(5);
        cell.setCellValue(project_costt.doubleValue());
        cell.setCellStyle(style1);
        
        cell = row.createCell(6);
        cell.setCellValue(farmpondst.doubleValue());
        cell.setCellStyle(style1);
        
        cell = row.createCell(7);
        cell.setCellValue(checkdamst.doubleValue());
        cell.setCellStyle(style1);
        
        cell = row.createCell(8);
        cell.setCellValue(nallahbundst.doubleValue());
        cell.setCellStyle(style1);
        
        cell = row.createCell(9);
        cell.setCellValue(percolationtankst.doubleValue());
        cell.setCellStyle(style1);
        
        cell = row.createCell(10);
        cell.setCellValue(groundwaterrechargestructuret.doubleValue());
        cell.setCellStyle(style1);
        
        cell = row.createCell(11);
        cell.setCellValue(gullyplugst.doubleValue());
        cell.setCellStyle(style1);
        
        cell = row.createCell(12);
        cell.setCellValue(amritsarovart.doubleValue());
        cell.setCellStyle(style1);
        
        cell = row.createCell(13);
        cell.setCellValue(otherst.doubleValue());
        cell.setCellStyle(style1);
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 13);
	    String fileName = "attachment; filename=Report ME10.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "reports/activityNRMWorkJalSakati";
	}

}
