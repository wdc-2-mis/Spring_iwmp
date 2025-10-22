package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import app.bean.reports.ActivityWiseUptoPlanAchieveWorkBean;
import app.bean.reports.PhysicalActionAchievementBean;
import app.bean.reports.SelfHelpGroupReportBean;
import app.common.CommonFunctions;
import app.model.PfmsCgireceiptDetaildata;
import app.service.StateMasterService;
import app.service.reports.PhysicalActionAchievementService;

@Controller("ActivityWiseUptoPlanAchievWork")
public class ActivityWiseUptoPlanAchievWork {
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@RequestMapping(value="/activityWiseUptoPlanAchievWork", method = RequestMethod.GET)
	public ModelAndView yearWisePhyActPlan(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/activityWiseUptoPlanAchievWork");
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		return mav;
	}
	
	@RequestMapping(value="/activityWiseUptoPlanAchievWork", method = RequestMethod.POST)
	public ModelAndView getActivityWiseUptoPlanAchievWorkReport(HttpServletRequest request,@RequestParam(value ="state") Integer stCode,
			@RequestParam(value ="district") Integer distCode,@RequestParam(value ="project") Integer projId, @RequestParam(value ="fromYear") String fromYear,
			@RequestParam(value ="userdate") String userdate, @RequestParam(value ="userdateto") String userdateto)
	{
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		
	//	System.out.println("userdate: "+userdate+" userdateto: "+userdateto);
		ModelAndView mav = new ModelAndView();
		List<String[]> dataList = new ArrayList<String[]>();
		String str[] = null;
		List<String> month= new ArrayList<String>();
		Integer monthListSize =1;
		//String strYr[] = new String[toYear-fromYear];
		String yrChk="";
		List<String> monthList = new ArrayList<String>();
		Integer headSeq=0;
		List<Integer> headCode = new ArrayList<Integer>();
		Integer activitySeq =1;
		List<String> activityCode = new ArrayList<String>();
		
		try {
			
			String fromDateStr=null;
			String toDateStr =null;
			if(!userdate.equals("")) {
		        LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
		        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				}
				if(!userdateto.equals("")) {
		        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
		        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				}
			
				mav = new ModelAndView("reports/activityWiseUptoPlanAchievWork");
				List<ActivityWiseUptoPlanAchieveWorkBean> beanList = new ArrayList<ActivityWiseUptoPlanAchieveWorkBean>();
				beanList=pAAservices.getActivityWiseUptoPlanAchievWorkReport(stCode, distCode, projId,fromYear, userdate, userdateto);
				for(ActivityWiseUptoPlanAchieveWorkBean bean : beanList) 
				{
					str = new String[13];
					if(!headCode.contains(bean.getHead_code()) && activityCode.contains(bean.getHead_code()+"."+bean.getActivity_code())) 
					{
						headCode.add(bean.getHead_code());
						headSeq++;
					}
					if(!headCode.contains(bean.getHead_code()) && !activityCode.contains(bean.getHead_code()+"."+bean.getActivity_code())) 
					{
						headCode.add(bean.getHead_code());
						activityCode.add(bean.getHead_code()+"."+bean.getActivity_code());
						headSeq++;
						activitySeq=1;
					}
					if(headCode.contains(bean.getHead_code()) && !activityCode.contains(bean.getHead_code()+"."+bean.getActivity_code())) 
					{
						activityCode.add(bean.getHead_code()+"."+bean.getActivity_code());
						activitySeq++;
					}
				
					str[0] = bean.getHead_code().toString();//"head_code"
					str[1] = headSeq+". "+bean.getHead_desc();//"head_desc"
					str[2] = bean.getActivity_code().toString();//"activity_code"
					str[3] = headSeq+"."+activitySeq+" "+bean.getActivity_desc();//"activity_desc"
					str[4] = bean.getUnitname();//"uom_code"
					str[5] = bean.getUptoplan().toString();//plan
					str[6] = bean.getUptoachieve().toString();//Achievement
					str[7] = bean.getUptototwork().toString();
					str[8] = bean.getUptoongoingwork().toString();
					str[9] = bean.getUptocompletework().toString();
					str[10] = bean.getUptoforclosework().toString();
					str[11] = bean.getAchievpercent().toString();
					str[12] = bean.getYearcompletework().toString();
					
					dataList.add(str);
				}
			
			 mav.addObject("dataList",dataList);
			 mav.addObject("dataListSize",dataList.size());
			
			 mav.addObject("stateList",stateMasterService.getAllState());
			 mav.addObject("stCode",stCode);
			 mav.addObject("distCode",distCode);
			 mav.addObject("projectId",projId);
			 mav.addObject("fromYear",fromYear);
			 mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
			 
			 mav.addObject("stName",stName);
			 mav.addObject("distName",distName);
			 mav.addObject("projName",projName);
			 mav.addObject("yearName",yearName);

			 mav.addObject("fromDateStr", fromDateStr);
			 mav.addObject("toDateStr", toDateStr);
			 mav.addObject("userdate1", userdate);
			 mav.addObject("userdateto1", userdateto);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 
		return mav;
	}
	
	@RequestMapping(value = "/activityWiseUptoPlanAchievWorkPDF", method = RequestMethod.POST)
	public ModelAndView activityWiseUptoPlanAchievWorkPDF(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="state") Integer stCode,
			@RequestParam(value ="district") Integer distCode,@RequestParam(value ="project") Integer projId, @RequestParam(value ="fromYear") String fromYear,
			@RequestParam(value ="userdate1") String userdate, @RequestParam(value ="userdateto1") String userdateto) 
	{
		//WDC-PMKSY-0001113
		String state=request.getParameter("state");;
		String headtype=request.getParameter("headtype");
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		
		ModelAndView mav = new ModelAndView("reports/activityWiseUptoPlanAchievWork");
		List<String[]> dataList = new ArrayList<String[]>();
		String str[] = null;
		Integer headSeq=0;
		List<Integer> headCode = new ArrayList<Integer>();
		Integer activitySeq =1;
		List<String> activityCode = new ArrayList<String>();
		
		try {
			
			String fromDateStr=null;
			String toDateStr =null;
			if(!userdate.equals("")) {
		        LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
		        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			if(!userdateto.equals("")) {
		        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
		        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("activityWiseUptoPlanAchievWork");
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
			
			paragraph3 = new Paragraph("Report T2- State-wise, District-wise and Project-wise Details of Targets, Achievement and Works of Activities till the Selected Financial Year", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(10);
				table.setWidths(new int[] { 15, 5, 5, 5, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(100);
			
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);
			
			if(!fromYear.equals(""))
				CommonFunctions.insertCellHeader(table, "State : "+stName+ " District : "+distName+ " Project : "+projName+"  Financial Year : "+yearName, Element.ALIGN_LEFT, 10, 1, bf8Bold);
			else	
				CommonFunctions.insertCellHeader(table, "State : "+stName+ " District : "+distName+ " Project : "+projName+"  From Date : "+fromDateStr+"  To Date : "+toDateStr, Element.ALIGN_LEFT, 10, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Name of the Activity", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Unit", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Physical Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Physical Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement %", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Work", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Work Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Work Completed", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Work Completed for "+yearName, Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Work forClosed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
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
				
				String headval="";
				String actval="";
				
				double totPhyTar = 0.0;
		        double totPhyAch = 0.0;
		        double totAchPer = 0.0;
		        int totWrk = 0;
		        int totOnWrk = 0;
		        int totCoWrk = 0;
		        int totYrlyWrk = 0;
		        int totClsWrk = 0;
		        String unitName = "";
		        int grndtotWrk = 0;
		        int grndtotOnWrk = 0;
		        int grndtotCoWrk = 0;
		        int grndtotYrlyWrk = 0;
		        int grndtotClsWrk = 0;

				List<ActivityWiseUptoPlanAchieveWorkBean> beanList = new ArrayList<ActivityWiseUptoPlanAchieveWorkBean>();
				beanList=pAAservices.getActivityWiseUptoPlanAchievWorkReport(stCode, distCode, projId,fromYear,userdate, userdateto);
				for(ActivityWiseUptoPlanAchieveWorkBean bean : beanList) 
				{
					str = new String[13];
					if(!headCode.contains(bean.getHead_code()) && activityCode.contains(bean.getHead_code()+"."+bean.getActivity_code())) 
					{
						headCode.add(bean.getHead_code());
						headSeq++;
					}
					if(!headCode.contains(bean.getHead_code()) && !activityCode.contains(bean.getHead_code()+"."+bean.getActivity_code())) 
					{
						headCode.add(bean.getHead_code());
						activityCode.add(bean.getHead_code()+"."+bean.getActivity_code());
						headSeq++;
						activitySeq=1;
					}
					if(headCode.contains(bean.getHead_code()) && !activityCode.contains(bean.getHead_code()+"."+bean.getActivity_code())) 
					{
						activityCode.add(bean.getHead_code()+"."+bean.getActivity_code());
						activitySeq++;
					}
				
					str[0] = bean.getHead_code().toString();//"head_code"
					str[1] = headSeq+". "+bean.getHead_desc();//"head_desc"
					str[2] = bean.getActivity_code().toString();//"activity_code"
					str[3] = headSeq+"."+activitySeq+" "+bean.getActivity_desc();//"activity_desc"
					str[4] = bean.getUnitname();//"uom_code"
					str[5] = bean.getUptoplan().toString();//plan
					str[6] = bean.getUptoachieve().toString();//Achievement
					str[7] = bean.getUptototwork().toString();
					str[8] = bean.getUptoongoingwork().toString();
					str[9] = bean.getUptocompletework().toString();
					str[10] = bean.getUptoforclosework().toString();
					str[11] = bean.getAchievpercent().toString();
					str[12] = bean.getYearcompletework().toString();
					if(str[1] != null) 
					{
						if(!headval.equals(str[0])  ) 
						{
							if(bean.getHead_code()>1 && bean.getHead_code()<9) {
								CommonFunctions.insertCell3(table, "Sub-Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
								if(unitName.equals("ha")) {
									CommonFunctions.insertCell3(table, String.valueOf(Math.round(totPhyTar*100.0)/100.0), Element.ALIGN_CENTER, 1, 1, bf10Bold);
									CommonFunctions.insertCell3(table, String.valueOf(Math.round(totPhyAch*100.0)/100.0), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								}else if(unitName.equals("nos")) {
									CommonFunctions.insertCell3(table, String.valueOf(Math.round(totPhyTar)), Element.ALIGN_CENTER, 1, 1, bf10Bold);
									CommonFunctions.insertCell3(table, String.valueOf(Math.round(totPhyAch)), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								}
//								CommonFunctions.insertCell3(table, String.valueOf(totPhyTar), Element.ALIGN_CENTER, 1, 1, bf10Bold);
//								CommonFunctions.insertCell3(table, String.valueOf(totPhyAch), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								totAchPer = (totPhyAch*100)/totPhyTar;
								CommonFunctions.insertCell3(table, String.valueOf(Math.round(totAchPer*100.0)/100.0)+"%", Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(totWrk), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(totOnWrk), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(totCoWrk), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(totYrlyWrk), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(totClsWrk), Element.ALIGN_CENTER, 1, 1, bf10Bold);
							}
							CommonFunctions.insertCell(table, str[1], Element.ALIGN_LEFT, 1, 1, bf8);
							headval=str[0];
							CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_RIGHT, 1, 1, bf8);
							
							totPhyTar =  0.0;
					        totPhyAch = 0.0;
					        totWrk = 0;
					        totOnWrk = 0;
					        totCoWrk = 0;
					        totYrlyWrk = 0;
					        totClsWrk = 0;
						}
						if(!actval.equals(str[2])) {
							
							CommonFunctions.insertCell(table, "  "+str[3], Element.ALIGN_LEFT, 1, 1, bf8);
							actval=str[2];
							CommonFunctions.insertCell(table, str[4], Element.ALIGN_LEFT, 1, 1, bf8);
							if(str[4].equals("nos")) 
							{
								CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.0f", bean.getUptoplan()), Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.0f", bean.getUptoachieve()), Element.ALIGN_RIGHT, 1, 1, bf8);
							}
							else {
								CommonFunctions.insertCell(table, str[5], Element.ALIGN_RIGHT, 1, 1, bf8);
								CommonFunctions.insertCell(table, str[6], Element.ALIGN_RIGHT, 1, 1, bf8);
							}
							CommonFunctions.insertCell(table, str[11]+"%", Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, str[7], Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, str[8], Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, str[9], Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, str[12], Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, str[10], Element.ALIGN_RIGHT, 1, 1, bf8);
							
							totPhyTar =  bean.getUptoplan()==null?0.0:bean.getUptoplan().doubleValue() +totPhyTar;
					        totPhyAch = bean.getUptoachieve()==null?0.0:bean.getUptoachieve().doubleValue() +totPhyAch;
					        totWrk = totWrk + bean.getUptototwork();
					        totOnWrk = totOnWrk + bean.getUptoongoingwork();
					        totCoWrk = totCoWrk + bean.getUptocompletework();
					        totYrlyWrk = totYrlyWrk + bean.getYearcompletework();
					        totClsWrk = totClsWrk + bean.getUptoforclosework();
					        unitName = bean.getUnitname();
						}
						grndtotWrk = grndtotWrk + bean.getUptototwork();
						grndtotOnWrk = grndtotOnWrk + bean.getUptoongoingwork();
						grndtotCoWrk = grndtotCoWrk + bean.getUptocompletework();
						grndtotYrlyWrk = grndtotYrlyWrk + bean.getYearcompletework();
						grndtotClsWrk = grndtotClsWrk + bean.getUptoforclosework();
					
					
					dataList.add(str);
					}
				}
				
				
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 5, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(grndtotWrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(grndtotOnWrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(grndtotCoWrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(grndtotYrlyWrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(grndtotClsWrk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					 
					
					
					if(dataList.size()==0)
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
				
			
			
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
		//	CommonFunctions.insertCellPageHeader(table, "Source : WDC-PMKSY ", Element.ALIGN_RIGHT, 1, 4, bf8);
			document.add(table);
			///addHeader(writer);
			CommonFunctions.addFooter(writer);
			document.close();
			//getHeaderForDilrmpMMP(document);
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report-T2.pdf");
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

	@RequestMapping(value = "/downloadExcelactivityWiseUptoPlanAchievWork", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelactivityWiseUptoPlanAchievWork(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="state") Integer stCode,
			@RequestParam(value ="district") Integer distCode,@RequestParam(value ="project") Integer projId, @RequestParam(value ="fromYear") String fromYear,
			@RequestParam(value ="userdate1") String userdate, @RequestParam(value ="userdateto1") String userdateto) 
	{
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		
		String fromDateStr=null;
		String toDateStr =null;
		if(!userdate.equals("")) {
	        LocalDate date = LocalDate.parse(userdate, DateTimeFormatter.ISO_LOCAL_DATE);
	        fromDateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
			if(!userdateto.equals("")) {
	        LocalDate date1 = LocalDate.parse(userdateto, DateTimeFormatter.ISO_LOCAL_DATE);
	        toDateStr = date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}
		
			List<ActivityWiseUptoPlanAchieveWorkBean> list = new ArrayList<ActivityWiseUptoPlanAchieveWorkBean>();
		
			list=pAAservices.getActivityWiseUptoPlanAchievWorkReport(stCode, distCode, projId,fromYear,userdate,userdateto);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report T2- State-wise, District-wise and Project-wise Details of Targets, Achievement and Works of Activities");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report T2- State-wise, District-wise and Project-wise Details of Targets, Achievement and Works of Activities till the Selected Financial Year";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);
			
        
	        mergedRegion = new CellRangeAddress(5,5,0,9); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        
	        if(!fromYear.equals(""))
	        	cell.setCellValue("State : "+stName +"   District : "+distName +"   Project : " +projName+"   Financial Year "+yearName); 
			else
				cell.setCellValue("State : "+stName +"   District : "+distName +"   Project : " +projName+"   From Date "+fromDateStr+"   To Date "+toDateStr);  
	        
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			
			for(int i =1; i<10; i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("Name of the Activity");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Unit");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Physical Target");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Physical Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Achievement %");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Total No. of Work");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Total No. of Work Ongoing");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Total No. of Work Completed");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Total No. of Work Completed for "+yearName);  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Total No. of Work forClosed");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<10;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int rowno  = 8;
	        int oldHeadCode = 0;
	        int actSeq = 0;
	        int oldActCode = 0;
	        double totPhyTar = 0.0;
	        double totPhyAch = 0.0;
	        double totAchPer = 0.0;
	        int totWrk = 0;
	        int totOnWrk = 0;
	        int totCoWrk = 0;
	        int totYrlyWrk = 0;
	        int totClsWrk = 0;
	        String unitName = "";
	        int grndtotWrk = 0;
	        int grndtotOnWrk = 0;
	        int grndtotCoWrk = 0;
	        int grndtotYrlyWrk = 0;
	        int grndtotClsWrk = 0;
	        Row row = sheet.createRow(rowno);
	        for(ActivityWiseUptoPlanAchieveWorkBean bean: list) {
	        	
	        	if(oldHeadCode != bean.getHead_code()) {
	        		if(bean.getHead_code() >1 && bean.getHead_code() <9) {
	        			mergedRegion = new CellRangeAddress(rowno,rowno,0,1);
	        		    sheet.addMergedRegion(mergedRegion);
	        			cell = row.createCell(0);
	        			cell.setCellValue("Sub-Total");
	        			cell.setCellStyle(style);
	        			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	        			row.createCell(1).setCellStyle(style);
	        			if(unitName.equals("ha")) {
	        				cell = row.createCell(2);
		        			cell.setCellValue(Math.round(totPhyTar*100.0)/100.0);
		        			cell.setCellStyle(style);
		        			cell = row.createCell(3);
		        			cell.setCellValue(Math.round(totPhyAch*100.0)/100.0);
		        			cell.setCellStyle(style);
	        			}else if(unitName.equals("nos")) {
	        				cell = row.createCell(2);
		        			cell.setCellValue(Math.round(totPhyTar));
		        			cell.setCellStyle(style);
		        			cell = row.createCell(3);
		        			cell.setCellValue(Math.round(totPhyAch));
		        			cell.setCellStyle(style);
	        			}
	        			cell = row.createCell(2);
	        			cell.setCellValue(totPhyTar);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(3);
	        			cell.setCellValue(totPhyAch);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(4);
	        			totAchPer = (totPhyAch*100)/totPhyTar;
	        			cell.setCellValue(Math.round(totAchPer*100.0)/100.0+"%");
	        			cell.setCellStyle(style);
	        			cell = row.createCell(5);
	        			cell.setCellValue(totWrk);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(6);
	        			cell.setCellValue(totOnWrk);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(7);
	        			cell.setCellValue(totCoWrk);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(8);
	        			cell.setCellValue(totYrlyWrk);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(9);
	        			cell.setCellValue(totClsWrk);
	        			cell.setCellStyle(style);
	        			rowno++;
	        			row = sheet.createRow(rowno);
	        		}
	        		totPhyTar = 0.0;
	    	        totPhyAch = 0.0;
	    	        totAchPer = 0.0;
	    	        totWrk = 0;
	    	        totOnWrk = 0;
	    	        totCoWrk = 0;
	    	        totYrlyWrk = 0;
	    	        totClsWrk = 0;
	    	        
	        		row.createCell(0).setCellValue(bean.getHead_code()+". "+bean.getHead_desc()); 
	        		row.createCell(1);
	        		row.createCell(2);
	        		row.createCell(3);
	        		row.createCell(4);
	        		row.createCell(5);
	        		row.createCell(6);
	        		row.createCell(7);
	        		row.createCell(8);
	        		row.createCell(9);
	        		actSeq = 1;
	        		rowno++;
	        		oldHeadCode = bean.getHead_code();
	        		row = sheet.createRow(rowno);
	        	}
	        	
	        	if(oldActCode!=bean.getActivity_code()) {
	        		row.createCell(0).setCellValue(bean.getHead_code()+"."+actSeq+" "+bean.getActivity_desc());
					row.createCell(1).setCellValue(bean.getUnitname());
					row.createCell(2).setCellValue(bean.getUptoplan()==null?0.0:bean.getUptoplan().doubleValue()); 
					row.createCell(3).setCellValue(bean.getUptoachieve()==null?0.0:bean.getUptoachieve().doubleValue());
					row.createCell(4).setCellValue((bean.getAchievpercent()==null?0.0:bean.getAchievpercent().doubleValue())+"%");
					row.createCell(5).setCellValue(bean.getUptototwork());
					row.createCell(6).setCellValue(bean.getUptoongoingwork());
					row.createCell(7).setCellValue(bean.getUptocompletework());
					row.createCell(8).setCellValue(bean.getYearcompletework());
					row.createCell(9).setCellValue(bean.getUptoforclosework());
					oldActCode = bean.getActivity_code();
					actSeq++;
		        	rowno++;
		        	row = sheet.createRow(rowno);
		        	totPhyTar = bean.getUptoplan()==null?0.0:bean.getUptoplan().doubleValue() +totPhyTar;
			        totPhyAch = bean.getUptoachieve()==null?0.0:bean.getUptoachieve().doubleValue() +totPhyAch;
			        totWrk = totWrk + bean.getUptototwork();
			        totOnWrk = totOnWrk + bean.getUptoongoingwork();
			        totCoWrk = totCoWrk + bean.getUptocompletework();
			        totYrlyWrk = totYrlyWrk + bean.getYearcompletework();
			        totClsWrk = totClsWrk + bean.getUptoforclosework();
			        unitName = bean.getUnitname();
		        	
	        	}
	        	grndtotWrk = grndtotWrk + bean.getUptototwork();
	        	grndtotOnWrk = grndtotOnWrk + bean.getUptoongoingwork();
	        	grndtotCoWrk = grndtotCoWrk + bean.getUptocompletework();
	        	grndtotYrlyWrk = grndtotYrlyWrk + bean.getYearcompletework();
	        	grndtotClsWrk = grndtotClsWrk + bean.getUptoforclosework();
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
	        
	        mergedRegion = new CellRangeAddress(list.size()+24,list.size()+24,0,4); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	        for(int i= 1;i<5;i++) {
	        	row.createCell(i).setCellStyle(style1);
	        }
	        cell = row.createCell(5);
	        cell.setCellValue(grndtotWrk);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(grndtotOnWrk);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(grndtotCoWrk);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(grndtotYrlyWrk);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(grndtotClsWrk);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowno-8, 9);
	        String fileName = "attachment; filename=Report T2.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/activityWiseUptoPlanAchievWork";
		
	}
	
}
