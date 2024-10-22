package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedHashMap;
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

import app.bean.BaseLineOutcomeBean;
import app.bean.PhysicalActionPlanBean;
import app.bean.reports.PhysicalActionAchievementBean;
import app.common.CommonFunctions;
import app.service.StateMasterService;
import app.service.reports.PhysicalActionAchievementService;

@Controller("PhysicalActionAchievementController")
public class PhysicalActionAchievementController {
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	
	@RequestMapping(value="/monthsWisePhyActAchievement", method = RequestMethod.GET)
	public ModelAndView yearWisePhyActPlan(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/physicalActionAchievement");
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		return mav;
	}
	@RequestMapping(value="/getYearForPhysicalActionAchievementReport", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getFromYearForPhysicalActionPlanReport(HttpServletRequest request,@RequestParam(value ="pCode") Integer pCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(pAAservices.getYearForPhysicalActionAchievementReport(pCode));
		return map;
	}
	
	@RequestMapping(value="/monthWisePhyActAchievement", method = RequestMethod.POST)
	public ModelAndView getPhysicalActionReport(HttpServletRequest request,@RequestParam(value ="state") Integer stCode,@RequestParam(value ="district") Integer distCode,@RequestParam(value ="project") Integer projId,@RequestParam(value ="fromYear") Integer fromYear)
	{
		
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		//System.out.println("state: "+stCode+" district: "+distCode);
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
				mav = new ModelAndView("reports/physicalActionAchievement");
				List<PhysicalActionAchievementBean> beanList = new ArrayList<PhysicalActionAchievementBean>();
				beanList=pAAservices.getPhysicalActionAchievementReport(stCode, distCode, projId,fromYear);
				for(PhysicalActionAchievementBean bean : beanList) 
				{
					str = new String[12];
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
				
					str[0] = bean.getMonthid().toString(); //" minthid"
					str[1] = bean.getMonthname();//" month_desc"
					str[2] = bean.getHead_code().toString();//"head_code"
					str[3] = headSeq+". "+bean.getHead_desc();//"head_desc"
					str[4] = bean.getActivity_code().toString();//"activity_code"
					str[5] = headSeq+"."+activitySeq+" "+bean.getActivity_desc();//"activity_desc"
					str[6] = "0";//"sub_act_code"
					str[7] = "Sub Activity";//"sub_act_desc"
					if(bean.getAchievement()==null) {
						str[8]="0.0";
					}
					else
					str[8] = bean.getAchievement().toString();//Achievement
					str[9] = bean.getUnitname();//"uom_code"
					if(bean.getPlan()!=null)
						str[10] = bean.getPlan().toString();// plan
					else
						str[10] ="0.00";
					if(bean.getMonthstatus()!=null)
						str[11] = bean.getMonthstatus();
					else
						str[11] = "";
					if (yrChk.indexOf(str[1]) == -1) {
						yrChk = yrChk + str[1] + ",";
					}
					if(month!=null && !month.contains(bean.getMonthname()))
						month.add(bean.getMonthname());
					dataList.add(str);
				}
				/*
				 * String yrNotIncluded = ""; for(int i=0;i<12;i++) { monthList=month; }
				 * for(String y:monthList) { if(!monthList.contains(y)) {
				 * if(yrNotIncluded.length()==0) yrNotIncluded=""+y; else
				 * yrNotIncluded=yrNotIncluded+","+y; }
				 * 
				 * }
				 */
			 if(month.size()>0)
				 monthListSize = month.size();
			// System.out.println("monthsize--------->"+month.size()+" : "+monthListSize);
			// strYr = yrChk.split(",");
			 mav.addObject("dataList",dataList);
			 mav.addObject("monthList",month);
			 mav.addObject("monthListSize",monthListSize);
			 mav.addObject("target","P");
			 mav.addObject("totalRec",1);
			 mav.addObject("amtSum",0);
			 mav.addObject("grandAmt",0);
			 mav.addObject("headSumMap",0);
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 
		return mav;
	}
	
	@RequestMapping(value = "/getMnthWisePhyActAchievExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getMnthWisePhyActAchievExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		int stCode= Integer.parseInt(request.getParameter("stCode"));
		int distCode= Integer.parseInt(request.getParameter("distCode"));
		int projectId= Integer.parseInt(request.getParameter("projId"));
		int fromYear= Integer.parseInt(request.getParameter("fYear"));
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		List<PhysicalActionAchievementBean> beanList = new ArrayList<PhysicalActionAchievementBean>();
		beanList=pAAservices.getPhysicalActionAchievementReport(stCode, distCode, projectId,fromYear);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report A1- Monthly Achievements Against Annual Physical Action Plan Targets");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report A1- State-wise, District-wise, Project-wise and Year-wise Details of Monthly Achievements Against Annual Physical Action Plan Targets as per List of Activities";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 16, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,16);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,15); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,16,16);
	        sheet.addMergedRegion(mergedRegion);
	        
//	        mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
//	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("State: "+stName+"  District: "+distName + "  Project: "+ projName + "  Financial Year: "+yearName);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<17;i++) {
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
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Physical Achievement");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =4; i<16;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Achievement % (16*100/3)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<3;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			
			String month[] = {"Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Jan","Feb","Mar","Total"};
			
			for(int i =0;i<13;i++) {
				cell = rowhead2.createCell(i+3);
				cell.setCellValue(month[i]);  
				cell.setCellStyle(style);
			}
			
			cell = rowhead2.createCell(16);
			cell.setCellStyle(style);
			
			Row rowhead3 = sheet.createRow(8);
			for(int i =0;i<17;i++) {
				cell = rowhead3.createCell(i);
				cell.setCellValue(i+1);  
				cell.setCellStyle(style);
			}
			
	        int rowno  =9;
	        int oldHeadCode  =0;
	        int headSeq = 1;
	        int oldActCd = 0;
	        int actSeq = 1;
	        int rowCnt = 0;
	        double total = 0;
	        double phyTar = 0.0;
	        double janTot = 0.0;
	        double febTot = 0.0;
	        double marTot = 0.0;
	        double aprTot = 0.0;
	        double mayTot = 0.0;
	        double junTot = 0.0;
	        double julTot = 0.0;
	        double augTot = 0.0;
	        double sepTot = 0.0;
	        double octTot = 0.0;
	        double novTot = 0.0;
	        double decTot = 0.0;
	        double totTot = 0.0;
	        double achTot = 0.0;
	        Row row = sheet.createRow(rowno);
	        for(PhysicalActionAchievementBean bean: beanList) {
	        	if(bean.getHead_code()!=oldHeadCode) {
	        		if(headSeq!=1 && headSeq < 9) {
	        			mergedRegion = new CellRangeAddress(rowno,rowno,0,1);
	        		    sheet.addMergedRegion(mergedRegion);
	        			cell = row.createCell(0);
	        			cell.setCellValue("Sub-Total");
	        			cell.setCellStyle(style);
	        			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
	        			row.createCell(1).setCellStyle(style);
	        			cell = row.createCell(2);
	        			cell.setCellValue(phyTar);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(3);
	        			cell.setCellValue(aprTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(4);
	        			cell.setCellValue(mayTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(5);
	        			cell.setCellValue(junTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(6);
	        			cell.setCellValue(julTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(7);
	        			cell.setCellValue(augTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(8);
	        			cell.setCellValue(sepTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(9);
	        			cell.setCellValue(octTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(10);
	        			cell.setCellValue(novTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(11);
	        			cell.setCellValue(decTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(12);
	        			cell.setCellValue(janTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(13);
	        			cell.setCellValue(febTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(14);
	        			cell.setCellValue(marTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(15);
	        			cell.setCellValue(totTot);
	        			cell.setCellStyle(style);
	        			cell = row.createCell(16);
	        			try (Formatter formatter = new Formatter()) {
							formatter.format("%.2f", phyTar==0?0.00:(totTot*100)/phyTar);
							cell.setCellValue(formatter.toString());
							cell.setCellStyle(style);
							CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
						}
	        			rowno++;
	        			rowCnt++;
	        			row = sheet.createRow(rowno);
	        		}
	        		phyTar = 0.0;
	    	        janTot = 0.0;
	    	        febTot = 0.0;
	    	        marTot = 0.0;
	    	        aprTot = 0.0;
	    	        mayTot = 0.0;
	    	        junTot = 0.0;
	    	        julTot = 0.0;
	    	        augTot = 0.0;
	    	        sepTot = 0.0;
	    	        octTot = 0.0;
	    	        novTot = 0.0;
	    	        decTot = 0.0;
	    	        totTot = 0.0;
	    	        achTot = 0.0;
	        		row.createCell(0).setCellValue(headSeq+"."+" "+bean.getHead_desc());
	        		for(int i =1;i<17;i++) {
	        			row.createCell(i);
	    			}
	        		headSeq++;
	        		rowno++;
	        		rowCnt++;
	        		actSeq = 1;
	        		oldHeadCode = bean.getHead_code();
	        		row = sheet.createRow(rowno);
	        	}
	        	oldActCd = bean.getActivity_code();
	        	if(bean.getActivity_code()==oldActCd) {
	        		if(month[0].equals(bean.getMonthname())) {
	        			total =0;
	        			row.createCell(0).setCellValue(headSeq-1+"."+actSeq+" "+bean.getActivity_desc());
		        		row.createCell(1).setCellValue(bean.getUnitname());
		        		row.createCell(2).setCellValue(bean.getPlan().doubleValue());
		        		if(bean.getMonthstatus()!= null &&  bean.getMonthstatus().equals("NS"))
		        			row.createCell(3).setCellValue("NS");
		        		else
		        			row.createCell(3).setCellValue(bean.getAchievement().doubleValue());
		        		total = total + bean.getAchievement().doubleValue();
		        		phyTar = phyTar + bean.getPlan().doubleValue();
		        		aprTot = aprTot + bean.getAchievement().doubleValue();
	        		}
	        		else{
	        			for(int i =1;i<12;i++) {
	        				if(month[i].equals(bean.getMonthname())) {
	        					if(bean.getMonthstatus()!= null &&  bean.getMonthstatus().equals("NS"))
	    		        			row.createCell(i+3).setCellValue("NS");
	    		        		else
	    		        			row.createCell(i+3).setCellValue(bean.getAchievement().doubleValue());
	        					total = total + bean.getAchievement().doubleValue();
	        					if(i==1)
	        						mayTot = mayTot + bean.getAchievement().doubleValue();
	        					if(i==2)
	        						junTot = junTot + bean.getAchievement().doubleValue();
	        					if(i==3)
	        						julTot = julTot + bean.getAchievement().doubleValue();
	        					if(i==4)
	        						augTot = augTot + bean.getAchievement().doubleValue();
	        					if(i==5)
	        						sepTot = sepTot + bean.getAchievement().doubleValue();
	        					if(i==6)
	        						octTot = octTot + bean.getAchievement().doubleValue();
	        					if(i==7)
	        						novTot = novTot + bean.getAchievement().doubleValue();
	        					if(i==8)
	        						decTot = decTot + bean.getAchievement().doubleValue();
	        					if(i==9)
	        						janTot = janTot + bean.getAchievement().doubleValue();
	        					if(i==10)
	        						febTot = febTot + bean.getAchievement().doubleValue();
	        					if(i==11)
	        						marTot = marTot + bean.getAchievement().doubleValue();
	        				}
	        				if(month[11].equals(bean.getMonthname()) && i == 11) {
	        					row.createCell(15).setCellValue(total);
	        					double prcntg = 0.00;
	        					try (Formatter formatter = new Formatter()) {
									prcntg = bean.getPlan().doubleValue()==0?0.00:total*100/bean.getPlan().doubleValue();
									formatter.format("%.2f", prcntg);
									prcntg = Double.valueOf(formatter.toString())>99.99?100:Double.valueOf(formatter.toString());
									row.createCell(16).setCellValue(prcntg);
								}
	        					totTot = totTot + total;
	        					achTot = achTot +  prcntg;
	        					actSeq++;
	        					rowno++;
	        					rowCnt++;
	        					row = sheet.createRow(rowno);
	        				}
	        			}
	        		}
	        	}
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
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowCnt, 16);
	        String fileName = "attachment; filename=Report A1.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/physicalActionAchievement";
		
	}
		
	
	@RequestMapping(value = "/downloadMonthWisePhyActPlanPDF", method = RequestMethod.POST)
	public ModelAndView downloadMonthWisePhyActPlanPDF(HttpServletRequest request, HttpServletResponse response) 	{
		//WDC-PMKSY-0001113	
		int stCode= Integer.parseInt(request.getParameter("stCode"));
		int distCode= Integer.parseInt(request.getParameter("distCode"));
		int projectId= Integer.parseInt(request.getParameter("projId"));
		int fromYear= Integer.parseInt(request.getParameter("fYear"));
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		List<PhysicalActionAchievementBean> beanList = new ArrayList<PhysicalActionAchievementBean>();
		beanList=pAAservices.getPhysicalActionAchievementReport(stCode, distCode, projectId,fromYear);
		
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
			paragraph3 = new Paragraph("Report A1- State-wise, District-wise, Project-wise and Year-wise Details of Monthly Achievements Against Annual Physical Action Plan Targets as per List of Activities", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);	
			document.add(paragraph3);	
			table = new PdfPTable(17);
			table.setWidths(new int[] { 8, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
			
			table.setWidthPercentage(70);	
			table.setWidthPercentage(100);	
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(4);
			String month[] = {"Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Jan","Feb","Mar"};
			
				CommonFunctions.insertCellHeader(table, "State: "+stName+"  District: "+distName + "  Project: "+ projName + "  Financial Year: "+yearName, Element.ALIGN_LEFT, 17, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Name of the Activity", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Unit ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Physical Target ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Physical Achievement", Element.ALIGN_CENTER, 13, 1, bf8Bold);	
				CommonFunctions.insertCellHeader(table, "Achievement % (16*100/3)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				 	 	 	 	 	 	 	 	 	 	
				CommonFunctions.insertCellHeader(table, "Apr", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "May", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Jun", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Jul", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Aug", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Sep", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Oct", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Nov", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Dec", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Jan", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Feb", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Mar", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int headCode=0;
				int oldActCd = 0;
				int actSeq = 0;
				int j  =0;
				BigDecimal phyTarTot = BigDecimal.ZERO;
				BigDecimal monthTot = BigDecimal.ZERO;
				BigDecimal totTotal  = BigDecimal.ZERO;
				BigDecimal[] total = {BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO};
				if(beanList.size()!=0)
					for(int i=0;i<beanList.size();i++) 
					{
						if(headCode != beanList.get(i).getHead_code()) {
							if(actSeq!=0 && beanList.get(i).getHead_code()<9) {
								CommonFunctions.insertCell3(table, "Sub-Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(phyTarTot), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[0]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[1]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[2]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[3]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[4]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[5]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[6]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[7]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[8]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[9]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[10]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(total[11]), Element.ALIGN_CENTER, 1, 1, bf10Bold);
								CommonFunctions.insertCell3(table, String.valueOf(totTotal), Element.ALIGN_CENTER, 1, 1, bf10Bold);
							        
								if (phyTarTot.compareTo(BigDecimal.ZERO) == 0) {
							        	 CommonFunctions.insertCell3(table, String.valueOf("0.00%"), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							        } 
								else {
							            BigDecimal result = totTotal.multiply(new BigDecimal("100")).divide(phyTarTot, 2, RoundingMode.HALF_UP);
							            CommonFunctions.insertCell3(table, String.valueOf(result+"%"), Element.ALIGN_CENTER, 1, 1, bf10Bold);
							        }
								totTotal = BigDecimal.ZERO;
								phyTarTot = BigDecimal.ZERO;
							}
							CommonFunctions.insertCell(table,(beanList.get(i).getHead_code()+"."+ beanList.get(i).getHead_desc()), Element.ALIGN_LEFT, 17, 1, bf8);
							headCode=beanList.get(i).getHead_code();		
							actSeq =1;
							j=0;
							total = new BigDecimal[]{BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO};
							
						}
						
						
						oldActCd = beanList.get(i).getActivity_code();
						if(beanList.get(i).getActivity_code()==oldActCd) {
							if(month[j].equals(beanList.get(i).getMonthname()) && j==0) {
								CommonFunctions.insertCell(table, beanList.get(i).getHead_code()+"."+actSeq+" "+beanList.get(i).getActivity_desc(), Element.ALIGN_LEFT, 1, 1, bf8);
								CommonFunctions.insertCell(table, beanList.get(i).getUnitname(), Element.ALIGN_LEFT, 1, 1, bf8);
								CommonFunctions.insertCell(table, beanList.get(i).getPlan() == null ? "" : String.format(Locale.US, "%.4f", beanList.get(i).getPlan()), Element.ALIGN_RIGHT, 1, 1, bf8);
									if(beanList.get(i).getMonthstatus()!= null &&  beanList.get(i).getMonthstatus().equals("NS"))
									CommonFunctions.insertCell(table, "NS", Element.ALIGN_LEFT, 1, 1, bf8);
								else 
				        			CommonFunctions.insertCell(table, beanList.get(i).getAchievement() == null ? "" : String.format(Locale.US, "%.4f", beanList.get(i).getAchievement()), Element.ALIGN_RIGHT, 1, 1, bf8);
								
								monthTot = monthTot.add(beanList.get(i).getAchievement() == null ? BigDecimal.ZERO : beanList.get(i).getAchievement());
								total[j] = total[j].add(beanList.get(i).getAchievement() == null ? BigDecimal.ZERO : beanList.get(i).getAchievement());
								phyTarTot = phyTarTot.add(beanList.get(i).getPlan() == null ? BigDecimal.ZERO : beanList.get(i).getPlan());
								j++;
							}
							
							
							if(month[j].equals(beanList.get(i).getMonthname()) && j!=0) {
								if(beanList.get(i).getMonthstatus()!= null &&  beanList.get(i).getMonthstatus().equals("NS")) 
									CommonFunctions.insertCell(table, "NS", Element.ALIGN_LEFT, 1, 1, bf8);
								else 
				        			CommonFunctions.insertCell(table, beanList.get(i).getAchievement() == null ? "" : String.format(Locale.US, "%.4f", beanList.get(i).getAchievement()), Element.ALIGN_RIGHT, 1, 1, bf8);
								
								total[j] = total[j].add(beanList.get(i).getAchievement() == null ? BigDecimal.ZERO : beanList.get(i).getAchievement());
								monthTot = monthTot.add(beanList.get(i).getAchievement() == null ? BigDecimal.ZERO : beanList.get(i).getAchievement());
								j++;
							}
							
							if(j==total.length) {
								CommonFunctions.insertCell(table, String.valueOf(monthTot), Element.ALIGN_RIGHT, 1, 1, bf8);
								        BigDecimal divisor = beanList.get(i).getPlan(); 
								        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
								        	 CommonFunctions.insertCell(table, String.valueOf("0.00%"), Element.ALIGN_RIGHT, 1, 1, bf8);
								        } else {
								            BigDecimal achPer = monthTot.multiply(new BigDecimal("100")).divide(divisor, 2, RoundingMode.HALF_UP);
								            CommonFunctions.insertCell(table, String.valueOf(achPer+"%"), Element.ALIGN_RIGHT, 1, 1, bf8);
								        }
								
								totTotal = totTotal.add(monthTot);
								monthTot = BigDecimal.ZERO;
								j=0;
								actSeq++;
							}
							
						}
					}
				if(beanList.size()==0) {				
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 17, 1, bf8);		
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
			response.setHeader("Content-Disposition", "attachment;filename=A1-Report.pdf");		
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
	


}
