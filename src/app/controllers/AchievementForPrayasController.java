package app.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import org.apache.poi.ss.usermodel.VerticalAlignment;
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

import app.TargetAchievementQuarterBean;
import app.bean.PrayasAchievementBean;
import app.common.CommonFunctions;
import app.model.IwmpMMonth;
import app.service.master.OutcomeMasterServices;
import app.service.reports.TargetAchievementQuarterService;

@Controller("achievementForPrayasController")
public class AchievementForPrayasController {

	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@Autowired(required = true)
	OutcomeMasterServices outcomeserv;
	
	@RequestMapping(value="/achievementForPrayas", method = RequestMethod.GET)
	public ModelAndView achievementForPrayas(HttpServletRequest request, HttpServletResponse response)
	{
		String year= request.getParameter("year");
		String monthdtl = request.getParameter("monthdtl");
		List<IwmpMMonth> month = null;
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/achievementforprayas");
		mav.addObject("financialYear", ser.getYearonward22());
		mav.addObject("year", year);
		if(year!=null && !year.equals("") && !year.equals("0")) {
		month = outcomeserv.getAllMonths();
		mav.addObject("months", month);
		}
		mav.addObject("monthdtl", monthdtl);
		return mav;
	}

	@RequestMapping(value="/getPrayasAchievement", method = RequestMethod.POST)
	public ModelAndView getPrayasAchievement(HttpServletRequest request, HttpServletResponse response)
	{
	
		ModelAndView mav = new ModelAndView();
		Integer finCode = Integer.parseInt(request.getParameter("year"));
		Integer month = Integer.parseInt(request.getParameter("monthdtl"));
		Integer stcode = 0;
		String data[] = null;
		int i=1;
		List<String[]> dataList = new ArrayList<String[]>();
		ArrayList dataListNetTotal = new ArrayList();
		List<PrayasAchievementBean> list=new  ArrayList<PrayasAchievementBean>();
		mav = new ModelAndView("reports/achievementforprayas");
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO,};
		
		
		list=ser.finddistmonthachdata(stcode, finCode, month);
		dataArrNetTotalStr = new String[] { "", "", "", "", "", "", ""};
		if(list != null) 
		{
			for(PrayasAchievementBean bean : list) 
			{
				  data = new String[16]; 
				  if(bean.getSt_code()!=null) {
						
						data[0] = String.valueOf(i); // serial no
				  		data[1] = bean.getSt_code().toString();
						data[2] = bean.getSt_name();
						if(bean.getDegraded_rainfed()==null)
							data[9]="0.00";
						else
							data[9]=bean.getDegraded_rainfed().toString();
						if(bean.getArea_soilmoisture_activities_achie()==null)
							data[3]="0.00";
						else
							data[3]=bean.getArea_soilmoisture_activities_achie().toString();
						if(bean.getArea_afforestation_horticulture_achie()==null)
							data[4]="0.00";
						else
							data[4]=bean.getArea_afforestation_horticulture_achie().toString();
						if(bean.getWater_created_renovated_achie()==null)
							data[5]="0.00";
						else
							data[5]=bean.getWater_created_renovated_achie().toString();
						if(bean.getFarmer_benefitted_achie()==null)
							data[8]="0.00";
						else
							data[8]=bean.getFarmer_benefitted_achie().toString();
						if(bean.getProtective_irrigation_achie()==null)
							data[6]="0.00";
						else
							data[6]=bean.getProtective_irrigation_achie().toString();
						if(bean.getMan_days_gen()==null)
							data[7]="0.00";
						else
							data[7]=bean.getMan_days_gen().toString();
						i++;
						
						dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(BigDecimal.valueOf(Double.valueOf(data[9])));
						dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(BigDecimal.valueOf(Double.valueOf(data[3])));
						dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(BigDecimal.valueOf(Double.valueOf(data[4])));
						dataArrNetTotalBD[3] = dataArrNetTotalBD[3].add(BigDecimal.valueOf(Double.valueOf(data[5])));
						dataArrNetTotalBD[4] = dataArrNetTotalBD[4].add(BigDecimal.valueOf(Double.valueOf(data[8])));
						dataArrNetTotalBD[5] = dataArrNetTotalBD[5].add(BigDecimal.valueOf(Double.valueOf(data[6])));
						dataArrNetTotalBD[6] = dataArrNetTotalBD[6].add(BigDecimal.valueOf(Double.valueOf(data[7])));
						
				  }
				  dataList.add(data);	
			}
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotalBD[0].toString();
			dataArrNetTotalStr[1] = dataArrNetTotalBD[1].toString();
			dataArrNetTotalStr[2] = dataArrNetTotalBD[2].toString();
			dataArrNetTotalStr[3] = dataArrNetTotalBD[3].toString();
			dataArrNetTotalStr[4] = dataArrNetTotalBD[4].toString();
			dataArrNetTotalStr[5] = dataArrNetTotalBD[5].toString();
			dataArrNetTotalStr[6] = dataArrNetTotalBD[6].toString();
			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
		}
		mav.addObject("dataList", dataList);
		mav.addObject("dataListsize", dataList.size());
		mav.addObject("months", outcomeserv.getAllMonths());
		mav.addObject("financialYear", ser.getYearonward22());
		mav.addObject("monthdtl", month);
		mav.addObject("year", finCode);
		
		return mav;
	}

	@RequestMapping(value = "/downloadExcelAchievementofDisha", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelAchievementofDisha(HttpServletRequest request, HttpServletResponse response) 
	{
		String finName= request.getParameter("finName");
		String monthname= request.getParameter("monthname");
		Integer stcode = 0;
		Integer month= Integer.parseInt(request.getParameter("monthdtl"));
		Integer finCode= Integer.parseInt(request.getParameter("year"));
		
		List<PrayasAchievementBean> list=new  ArrayList<PrayasAchievementBean>();
		
		list=ser.finddistmonthachdata(stcode, finCode, month);
		Workbook workbook = new XSSFWorkbook();  
		Sheet sheet = workbook.createSheet("Report O11- State-wise, District-Wise, Year-wise and Month-wise Achievements for Prayas");   

		CellStyle style = CommonFunctions.getStyle(workbook);
        
		int y = finCode;
		
		String rptName = "Report O11- State-wise, District-Wise, Year-wise and Month-wise Achievements for Prayas ";
		String areaAmtValDetail ="Financial Year : "+finName+" , Month : "+monthname;
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
	

    
        mergedRegion = new CellRangeAddress(list.size()+6,list.size()+6,0,1); 
        sheet.addMergedRegion(mergedRegion);
        
        
		Row rowhead = sheet.createRow(5); 
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");  
		cell.setCellStyle(style);
         
		/*
		 * cell = rowhead.createCell(2); cell.setCellValue("District Name");
		 * cell.setCellStyle(style);
		 */
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Area of degraded land covered and Rainfed area developed (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Area covered with Soil and Moisture conservation activities (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Area brought under Plantation Horticulture and Afforestation (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Water Harvesting Structures newly created and rejuvenated (in no.)");
		cell.setCellStyle(style);
		
		
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Farmers benefitted (in no.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Additional Area brought under Protective irrigation (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Employment Generated (in mandays)");
		cell.setCellStyle(style);
		
		
		 int sno = 1;
	     int rowno  = 6;
	     double totAreaDegLand = 0;
	     double totAreaSoilMoisture = 0;
	     double totAreaPlantation = 0;
	     double totNoofHarStruc = 0;
	     double totNoofFar = 0;
	     double totAreaBrouProIrr = 0;
	     double totNoofMan = 0;
	     
	     for(PrayasAchievementBean bean: list) {
	    	 Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getSt_name());
	        	row.createCell(2).setCellValue(bean.getDegraded_rainfed()==null?0.0:bean.getDegraded_rainfed().doubleValue());
				row.createCell(3).setCellValue(bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue());
	        	row.createCell(4).setCellValue(bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue());
	        	row.createCell(5).setCellValue(bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue());
	        	row.createCell(6).setCellValue(bean.getFarmer_benefitted_achie()==null?0.0:bean.getFarmer_benefitted_achie().doubleValue());
	        	row.createCell(7).setCellValue(bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue());
	        	row.createCell(8).setCellValue(bean.getMan_days_gen()==null?0.0:bean.getMan_days_gen().doubleValue());
	        	
	        	totAreaDegLand = totAreaDegLand + (bean.getDegraded_rainfed()==null?0.0:bean.getDegraded_rainfed().doubleValue());
	        	totAreaSoilMoisture = totAreaSoilMoisture + (bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue());
	        	totAreaPlantation = totAreaPlantation + (bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue());
	        	totNoofHarStruc = totNoofHarStruc + (bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue());
	        	totNoofFar = totNoofFar + (bean.getFarmer_benefitted_achie()==null?0.0:bean.getFarmer_benefitted_achie().doubleValue());
	        	totAreaBrouProIrr = totAreaBrouProIrr + (bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue());
	        	totNoofMan = totNoofMan + (bean.getMan_days_gen()==null?0.0:bean.getMan_days_gen().doubleValue());
	        	
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
			font1.setFontHeightInPoints((short) 7);
			font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
			
			Row row = sheet.createRow(list.size()+6);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totAreaDegLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totAreaSoilMoisture);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totAreaPlantation);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totNoofHarStruc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNoofFar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totAreaBrouProIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totNoofMan);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
	        String fileName = "attachment; filename=Report O11- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		return "reports/achievementforprayas";
	}
	
	@RequestMapping(value = "/downloadAchievementofDishaPDF", method = RequestMethod.POST)
	public ModelAndView downloadAchievementofDishaPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String finName= request.getParameter("finName");
		String monthname= request.getParameter("monthname");
		Integer stcode = 0;
		Integer month= Integer.parseInt(request.getParameter("monthdtl"));
		Integer finCode= Integer.parseInt(request.getParameter("year"));
		
		
		List<PrayasAchievementBean> list=new  ArrayList<PrayasAchievementBean>();
		
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("AchievementForPrayasReport");
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
			
			int m=finCode;
			
			 if(finCode==0) {
				paragraph3 = new Paragraph("Report O11- State-wise, District-Wise, Year-wise and Month-wise Achievements for Prayas", f3);
			 }
			 else {
				 paragraph3 = new Paragraph("Report O11- State-wise, District-Wise, Year-wise and Month-wise Achievements for Prayas", f3);
			 }
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(9);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5,});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				
				BigDecimal areaof_Rainfed_degraded= BigDecimal.valueOf(0);
				
				BigDecimal areaof_getCroppedarea= BigDecimal.valueOf(0);
				BigDecimal areaof_getIncome= BigDecimal.valueOf(0);
				
				BigDecimal areaof_getArea_soilmoisture_activities_achie= BigDecimal.valueOf(0);
				BigDecimal areaof_getArea_afforestation_horticulture_achie= BigDecimal.valueOf(0);
				
				BigDecimal areaof_getWater_created_renovated_achie= BigDecimal.valueOf(0);
				BigDecimal areaof_getFarmer_benefitted_achie= BigDecimal.valueOf(0);
				BigDecimal areaof_getProtective_irrigation_achie= BigDecimal.valueOf(0);
				BigDecimal areaof_getMandays_generated_achie= BigDecimal.valueOf(0);
				
				
				list=ser.finddistmonthachdata(stcode, finCode, month);
				CommonFunctions.insertCellHeader(table, "Year : "+finName+" , Month : "+monthname, Element.ALIGN_LEFT, 8, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "", Element.ALIGN_RIGHT, 8, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area of degraded land covered and Rainfed area developed (in ha.) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area covered with Soil and Moisture conservation activities (in ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought under Plantation Horticulture and Afforestation (in ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Water Harvesting Structures newly created and rejuvenated (in no.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Farmers benefitted (in no.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Additional Area brought under Protective irrigation (in ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Employment Generated (in mandays)", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
				int k=1;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDegraded_rainfed()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getDegraded_rainfed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_soilmoisture_activities_achie()==null?"0.00":  String.format(Locale.US, "%.4f",list.get(i).getArea_soilmoisture_activities_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_afforestation_horticulture_achie()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getArea_afforestation_horticulture_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getWater_created_renovated_achie()==null?"0":list.get(i).getWater_created_renovated_achie().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFarmer_benefitted_achie()==null?"0":list.get(i).getFarmer_benefitted_achie() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, list.get(i).getProtective_irrigation_achie()==null?"0.00":  String.format(Locale.US, "%.4f",list.get(i).getProtective_irrigation_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMan_days_gen()==null?"0":list.get(i).getMan_days_gen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						
						areaof_Rainfed_degraded=areaof_Rainfed_degraded.add(list.get(i).getDegraded_rainfed()==null?BigDecimal.ZERO:list.get(i).getDegraded_rainfed());
						areaof_getArea_soilmoisture_activities_achie=areaof_getArea_soilmoisture_activities_achie.add(list.get(i).getArea_soilmoisture_activities_achie()==null?BigDecimal.ZERO:list.get(i).getArea_soilmoisture_activities_achie());
						
						areaof_getArea_afforestation_horticulture_achie=areaof_getArea_afforestation_horticulture_achie.add(list.get(i).getArea_afforestation_horticulture_achie()==null?BigDecimal.ZERO:list.get(i).getArea_afforestation_horticulture_achie());
						areaof_getWater_created_renovated_achie=areaof_getWater_created_renovated_achie.add(list.get(i).getWater_created_renovated_achie()==null?BigDecimal.ZERO:list.get(i).getWater_created_renovated_achie());
						
						areaof_getFarmer_benefitted_achie=areaof_getFarmer_benefitted_achie.add(list.get(i).getFarmer_benefitted_achie()==null?BigDecimal.ZERO:list.get(i).getFarmer_benefitted_achie());
						areaof_getProtective_irrigation_achie=areaof_getProtective_irrigation_achie.add(list.get(i).getProtective_irrigation_achie()==null?BigDecimal.ZERO:list.get(i).getProtective_irrigation_achie());
						
						areaof_getMandays_generated_achie=areaof_getMandays_generated_achie.add(list.get(i).getMan_days_gen()==null?BigDecimal.ZERO:list.get(i).getMan_days_gen());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_Rainfed_degraded), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getArea_soilmoisture_activities_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getArea_afforestation_horticulture_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getWater_created_renovated_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getFarmer_benefitted_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getProtective_irrigation_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getMandays_generated_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(list.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 11, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=O11-Report.pdf");
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

	@RequestMapping(value = "/getDistWiseAchievementforPrayas", method = RequestMethod.GET)
	public ModelAndView getDistWiseAchievementforPrayas(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView();
		Integer month= Integer.parseInt(request.getParameter("month"));
		Integer finCode= Integer.parseInt(request.getParameter("finCode"));
		Integer stCode = Integer.parseInt(request.getParameter("stcode"));
		String stname = request.getParameter("stname");
		String data[] = null;
		int i=1;
		List<String[]> dataList = new ArrayList<String[]>();
		ArrayList dataListNetTotal = new ArrayList();
		List<PrayasAchievementBean> list=new  ArrayList<PrayasAchievementBean>();
		mav = new ModelAndView("reports/achievementforprayas");
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO,};
		
		
		list=ser.finddistWisemonthachdata(stCode, finCode, month);
		
		dataArrNetTotalStr = new String[] { "", "", "", "", "", "", ""};
		if(list != null) 
		{
			for(PrayasAchievementBean bean : list) 
			{
				  data = new String[16]; 
				  if(bean.getSt_code()!=null) {
						
						data[0] = String.valueOf(i); // serial no
				  		data[1] = bean.getSt_code().toString();
						data[2] = bean.getSt_name();
						data[3] = bean.getDcode().toString();
						data[4] = bean.getDist_name();
						if(bean.getDegraded_rainfed()==null)
							data[11]="0.00";
						else
							data[11]=bean.getDegraded_rainfed().toString();
						if(bean.getArea_soilmoisture_activities_achie()==null)
							data[5]="0.00";
						else
							data[5]=bean.getArea_soilmoisture_activities_achie().toString();
						if(bean.getArea_afforestation_horticulture_achie()==null)
							data[6]="0.00";
						else
							data[6]=bean.getArea_afforestation_horticulture_achie().toString();
						if(bean.getWater_created_renovated_achie()==null)
							data[7]="0.00";
						else
							data[7]=bean.getWater_created_renovated_achie().toString();
						if(bean.getFarmer_benefitted_achie()==null)
							data[10]="0.00";
						else
							data[10]=bean.getFarmer_benefitted_achie().toString();
						if(bean.getProtective_irrigation_achie()==null)
							data[8]="0.00";
						else
							data[8]=bean.getProtective_irrigation_achie().toString();
						if(bean.getMan_days_gen()==null)
							data[9]="0.00";
						else
							data[9]=bean.getMan_days_gen().toString();
						i++;
						
						dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(BigDecimal.valueOf(Double.valueOf(data[11])));
						dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(BigDecimal.valueOf(Double.valueOf(data[5])));
						dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(BigDecimal.valueOf(Double.valueOf(data[6])));
						dataArrNetTotalBD[3] = dataArrNetTotalBD[3].add(BigDecimal.valueOf(Double.valueOf(data[7])));
						dataArrNetTotalBD[4] = dataArrNetTotalBD[4].add(BigDecimal.valueOf(Double.valueOf(data[10])));
						dataArrNetTotalBD[5] = dataArrNetTotalBD[5].add(BigDecimal.valueOf(Double.valueOf(data[8])));
						dataArrNetTotalBD[6] = dataArrNetTotalBD[6].add(BigDecimal.valueOf(Double.valueOf(data[9])));
						
				  }
				  dataList.add(data);	
			}
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotalBD[0].toString();
			dataArrNetTotalStr[1] = dataArrNetTotalBD[1].toString();
			dataArrNetTotalStr[2] = dataArrNetTotalBD[2].toString();
			dataArrNetTotalStr[3] = dataArrNetTotalBD[3].toString();
			dataArrNetTotalStr[4] = dataArrNetTotalBD[4].toString();
			dataArrNetTotalStr[5] = dataArrNetTotalBD[5].toString();
			dataArrNetTotalStr[6] = dataArrNetTotalBD[6].toString();
			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
		}
		mav.addObject("dataList", dataList);
		mav.addObject("dataListsize", dataList.size());
		mav.addObject("months", outcomeserv.getAllMonths());
		mav.addObject("financialYear", ser.getYearonward22());
		mav.addObject("monthdtl", month);
		mav.addObject("year", finCode);
		mav.addObject("showdist", list);
		mav.addObject("stCode", stCode);
		mav.addObject("stname", stname);
		return mav;
	}

	@RequestMapping(value = "/downloadExcelDistAchievementofDisha", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistAchievementofDisha(HttpServletRequest request, HttpServletResponse response) 
	{
		String finName= request.getParameter("finName");
		String monthname= request.getParameter("monthname");
		String stname = request.getParameter("stname");
		Integer stcode = Integer.parseInt(request.getParameter("stCode"));
		Integer month= Integer.parseInt(request.getParameter("monthdtl"));
		Integer finCode= Integer.parseInt(request.getParameter("year"));
		
		List<PrayasAchievementBean> list=new  ArrayList<PrayasAchievementBean>();
		
		list=ser.finddistWisemonthachdata(stcode, finCode, month);
		Workbook workbook = new XSSFWorkbook();  
		Sheet sheet = workbook.createSheet("Report O11- State-wise, District-Wise, Year-wise and Month-wise Achievements for Prayas");   

		CellStyle style = CommonFunctions.getStyle(workbook);
        
		int y = finCode;
		
		String rptName = "Report O11- State-wise, District-Wise, Year-wise and Month-wise Achievements for Prayas ";
		String areaAmtValDetail ="Financial Year : "+finName+" , Month : "+monthname+" , State : "+stname+"";
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
	

    
        mergedRegion = new CellRangeAddress(list.size()+6,list.size()+6,0,1); 
        sheet.addMergedRegion(mergedRegion);
        
        
		Row rowhead = sheet.createRow(5); 
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");  
		cell.setCellStyle(style);
         
		/*
		 * cell = rowhead.createCell(2); cell.setCellValue("District Name");
		 * cell.setCellStyle(style);
		 */
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Area of degraded land covered and Rainfed area developed (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Area covered with Soil and Moisture conservation activities (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Area brought under Plantation Horticulture and Afforestation (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Water Harvesting Structures newly created and rejuvenated (in no.)");
		cell.setCellStyle(style);
		
		
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Farmers benefitted (in no.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Additional Area brought under Protective irrigation (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Employment Generated (in mandays)");
		cell.setCellStyle(style);
		
		
		 int sno = 1;
	     int rowno  = 6;
	     double totAreaDegLand = 0;
	     double totAreaSoilMoisture = 0;
	     double totAreaPlantation = 0;
	     double totNoofHarStruc = 0;
	     double totNoofFar = 0;
	     double totAreaBrouProIrr = 0;
	     double totNoofMan = 0;
	     
	     for(PrayasAchievementBean bean: list) {
	    	 Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDist_name());
	        	row.createCell(2).setCellValue(bean.getDegraded_rainfed()==null?0.0:bean.getDegraded_rainfed().doubleValue());
				row.createCell(3).setCellValue(bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue());
	        	row.createCell(4).setCellValue(bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue());
	        	row.createCell(5).setCellValue(bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue());
	        	row.createCell(6).setCellValue(bean.getFarmer_benefitted_achie()==null?0.0:bean.getFarmer_benefitted_achie().doubleValue());
	        	row.createCell(7).setCellValue(bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue());
	        	row.createCell(8).setCellValue(bean.getMan_days_gen()==null?0.0:bean.getMan_days_gen().doubleValue());
	        	
	        	totAreaDegLand = totAreaDegLand + (bean.getDegraded_rainfed()==null?0.0:bean.getDegraded_rainfed().doubleValue());
	        	totAreaSoilMoisture = totAreaSoilMoisture + (bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue());
	        	totAreaPlantation = totAreaPlantation + (bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue());
	        	totNoofHarStruc = totNoofHarStruc + (bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue());
	        	totNoofFar = totNoofFar + (bean.getFarmer_benefitted_achie()==null?0.0:bean.getFarmer_benefitted_achie().doubleValue());
	        	totAreaBrouProIrr = totAreaBrouProIrr + (bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue());
	        	totNoofMan = totNoofMan + (bean.getMan_days_gen()==null?0.0:bean.getMan_days_gen().doubleValue());
	        	
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
			font1.setFontHeightInPoints((short) 7);
			font1.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
			
			Row row = sheet.createRow(list.size()+6);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totAreaDegLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totAreaSoilMoisture);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totAreaPlantation);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totNoofHarStruc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNoofFar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totAreaBrouProIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totNoofMan);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
	        String fileName = "attachment; filename=Report O11- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		return "reports/achievementforprayas";
	}

	@RequestMapping(value = "/downloadDistAchievementofDishaPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistAchievementofDishaPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String finName= request.getParameter("finName");
		String monthname= request.getParameter("monthname");
		String stname = request.getParameter("stname");
		Integer stcode = Integer.parseInt(request.getParameter("stCode"));
		Integer month= Integer.parseInt(request.getParameter("monthdtl"));
		Integer finCode= Integer.parseInt(request.getParameter("year"));
		
		
		List<PrayasAchievementBean> list=new  ArrayList<PrayasAchievementBean>();
		
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("AchievementForPrayasReport");
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
			
			int m=finCode;
			
			 if(finCode==0) {
				paragraph3 = new Paragraph("Report O11- State-wise, District-Wise, Year-wise and Month-wise Achievements for Prayas", f3);
			 }
			 else {
				 paragraph3 = new Paragraph("Report O11- State-wise, District-Wise, Year-wise and Month-wise Achievements for Prayas", f3);
			 }
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(9);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5,});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				
				BigDecimal areaof_Rainfed_degraded= BigDecimal.valueOf(0);
				
				BigDecimal areaof_getCroppedarea= BigDecimal.valueOf(0);
				BigDecimal areaof_getIncome= BigDecimal.valueOf(0);
				
				BigDecimal areaof_getArea_soilmoisture_activities_achie= BigDecimal.valueOf(0);
				BigDecimal areaof_getArea_afforestation_horticulture_achie= BigDecimal.valueOf(0);
				
				BigDecimal areaof_getWater_created_renovated_achie= BigDecimal.valueOf(0);
				BigDecimal areaof_getFarmer_benefitted_achie= BigDecimal.valueOf(0);
				BigDecimal areaof_getProtective_irrigation_achie= BigDecimal.valueOf(0);
				BigDecimal areaof_getMandays_generated_achie= BigDecimal.valueOf(0);
				
				
				list=ser.finddistWisemonthachdata(stcode, finCode, month);
				CommonFunctions.insertCellHeader(table, "State : "+stname+" , Year : "+finName+" , Month : "+monthname, Element.ALIGN_LEFT, 8, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "", Element.ALIGN_RIGHT, 8, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area of degraded land covered and Rainfed area developed (in ha.) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area covered with Soil and Moisture conservation activities (in ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought under Plantation Horticulture and Afforestation (in ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Water Harvesting Structures newly created and rejuvenated (in no.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Farmers benefitted (in no.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Additional Area brought under Protective irrigation (in ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Employment Generated (in mandays)", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
				int k=1;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDegraded_rainfed()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getDegraded_rainfed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_soilmoisture_activities_achie()==null?"0.00":  String.format(Locale.US, "%.4f",list.get(i).getArea_soilmoisture_activities_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_afforestation_horticulture_achie()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getArea_afforestation_horticulture_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getWater_created_renovated_achie()==null?"0":list.get(i).getWater_created_renovated_achie().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFarmer_benefitted_achie()==null?"0":list.get(i).getFarmer_benefitted_achie() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, list.get(i).getProtective_irrigation_achie()==null?"0.00":  String.format(Locale.US, "%.4f",list.get(i).getProtective_irrigation_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMan_days_gen()==null?"0":list.get(i).getMan_days_gen().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						
						areaof_Rainfed_degraded=areaof_Rainfed_degraded.add(list.get(i).getDegraded_rainfed()==null?BigDecimal.ZERO:list.get(i).getDegraded_rainfed());
						areaof_getArea_soilmoisture_activities_achie=areaof_getArea_soilmoisture_activities_achie.add(list.get(i).getArea_soilmoisture_activities_achie()==null?BigDecimal.ZERO:list.get(i).getArea_soilmoisture_activities_achie());
						
						areaof_getArea_afforestation_horticulture_achie=areaof_getArea_afforestation_horticulture_achie.add(list.get(i).getArea_afforestation_horticulture_achie()==null?BigDecimal.ZERO:list.get(i).getArea_afforestation_horticulture_achie());
						areaof_getWater_created_renovated_achie=areaof_getWater_created_renovated_achie.add(list.get(i).getWater_created_renovated_achie()==null?BigDecimal.ZERO:list.get(i).getWater_created_renovated_achie());
						
						areaof_getFarmer_benefitted_achie=areaof_getFarmer_benefitted_achie.add(list.get(i).getFarmer_benefitted_achie()==null?BigDecimal.ZERO:list.get(i).getFarmer_benefitted_achie());
						areaof_getProtective_irrigation_achie=areaof_getProtective_irrigation_achie.add(list.get(i).getProtective_irrigation_achie()==null?BigDecimal.ZERO:list.get(i).getProtective_irrigation_achie());
						
						areaof_getMandays_generated_achie=areaof_getMandays_generated_achie.add(list.get(i).getMan_days_gen()==null?BigDecimal.ZERO:list.get(i).getMan_days_gen());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_Rainfed_degraded), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getArea_soilmoisture_activities_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getArea_afforestation_horticulture_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getWater_created_renovated_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getFarmer_benefitted_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getProtective_irrigation_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_getMandays_generated_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(list.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 11, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=O11-Report.pdf");
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
	
	
	@RequestMapping(value = "/getProjWiseAchievementforPrayas", method = RequestMethod.GET)
	public ModelAndView getProjWiseAchievementforPrayas(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView();
		Integer month= Integer.parseInt(request.getParameter("month"));
		Integer finCode= Integer.parseInt(request.getParameter("finCode"));
		String stname = request.getParameter("stname");
		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		String distname = request.getParameter("distname");
		String data[] = null;
		int i=1;
		List<String[]> dataList = new ArrayList<String[]>();
		ArrayList dataListNetTotal = new ArrayList();
		List<PrayasAchievementBean> list=new  ArrayList<PrayasAchievementBean>();
		mav = new ModelAndView("reports/achievementforprayas");
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO,};
		
		
		list=ser.findprojWisemonthachdata(dcode, finCode, month);
		
		dataArrNetTotalStr = new String[] { "", "", "", "", "", "", ""};
		if(list != null) 
		{
			for(PrayasAchievementBean bean : list) 
			{
				data = new String[16];

				data[0] = String.valueOf(i); // serial no
				data[1] = bean.getDcode().toString();
				data[2] = bean.getDist_name();
				data[3] = bean.getProj_id().toString();
				data[4] = bean.getProj_name();
				if (bean.getDegraded_rainfed() == null)
					data[11] = "0.00";
				else
					data[11] = bean.getDegraded_rainfed().toString();
				if (bean.getArea_soilmoisture_activities_achie() == null)
					data[5] = "0.00";
				else
					data[5] = bean.getArea_soilmoisture_activities_achie().toString();
				if (bean.getArea_afforestation_horticulture_achie() == null)
					data[6] = "0.00";
				else
					data[6] = bean.getArea_afforestation_horticulture_achie().toString();
				if (bean.getWater_created_renovated_achie() == null)
					data[7] = "0.00";
				else
					data[7] = bean.getWater_created_renovated_achie().toString();
				if (bean.getFarmer_benefitted_achie() == null)
					data[10] = "0.00";
				else
					data[10] = bean.getFarmer_benefitted_achie().toString();
				if (bean.getProtective_irrigation_achie() == null)
					data[8] = "0.00";
				else
					data[8] = bean.getProtective_irrigation_achie().toString();
				if (bean.getMan_days_gen() == null)
					data[9] = "0.00";
				else
					data[9] = bean.getMan_days_gen().toString();
				i++;

				dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(BigDecimal.valueOf(Double.valueOf(data[11])));
				dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(BigDecimal.valueOf(Double.valueOf(data[5])));
				dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(BigDecimal.valueOf(Double.valueOf(data[6])));
				dataArrNetTotalBD[3] = dataArrNetTotalBD[3].add(BigDecimal.valueOf(Double.valueOf(data[7])));
				dataArrNetTotalBD[4] = dataArrNetTotalBD[4].add(BigDecimal.valueOf(Double.valueOf(data[10])));
				dataArrNetTotalBD[5] = dataArrNetTotalBD[5].add(BigDecimal.valueOf(Double.valueOf(data[8])));
				dataArrNetTotalBD[6] = dataArrNetTotalBD[6].add(BigDecimal.valueOf(Double.valueOf(data[9])));

				dataList.add(data);
			}
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotalBD[0].toString();
			dataArrNetTotalStr[1] = dataArrNetTotalBD[1].toString();
			dataArrNetTotalStr[2] = dataArrNetTotalBD[2].toString();
			dataArrNetTotalStr[3] = dataArrNetTotalBD[3].toString();
			dataArrNetTotalStr[4] = dataArrNetTotalBD[4].toString();
			dataArrNetTotalStr[5] = dataArrNetTotalBD[5].toString();
			dataArrNetTotalStr[6] = dataArrNetTotalBD[6].toString();
			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
		}
		mav.addObject("dataPList", dataList);
		mav.addObject("dataPListsize", dataList.size());
		mav.addObject("months", outcomeserv.getAllMonths());
		mav.addObject("financialYear", ser.getYearonward22());
		mav.addObject("monthdtl", month);
		mav.addObject("year", finCode);
		mav.addObject("showproj", list);
		mav.addObject("stname", stname);
		mav.addObject("dcode", dcode);
		mav.addObject("distname", distname);
		return mav;
	}
	
	
	@RequestMapping(value = "/downloadExcelProjAchievementofDisha", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjAchievementofDisha(HttpServletRequest request, HttpServletResponse response) 
	{
		String finName= request.getParameter("finName");
		String monthname= request.getParameter("monthname");
		String stname = request.getParameter("stname");
		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		String distname = request.getParameter("distname");
		Integer month= Integer.parseInt(request.getParameter("monthdtl"));
		Integer finCode= Integer.parseInt(request.getParameter("year"));
		
		List<PrayasAchievementBean> list=new  ArrayList<PrayasAchievementBean>();
		
		list=ser.findprojWisemonthachdata(dcode, finCode, month);
		Workbook workbook = new XSSFWorkbook();  
		Sheet sheet = workbook.createSheet("Report O11- State-wise, District-Wise, Year-wise and Month-wise Achievements for Prayas");   

		CellStyle style = CommonFunctions.getStyle(workbook);
        
		int y = finCode;
		
		String rptName = "Report O11- Project-Wise, Year-wise and Month-wise Achievements for Prayas ";
		String areaAmtValDetail ="";
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
	
		mergedRegion = new CellRangeAddress(5,5,0,8); 
	    sheet.addMergedRegion(mergedRegion);
    
        mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
        sheet.addMergedRegion(mergedRegion);
        
        
		Row rowhead = sheet.createRow(5); 
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("State : "+stname+" , District : "+distname +" Financial Year : "+finName+" , Month : "+monthname);
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
		
		rowhead = sheet.createRow(6);
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Project Name");  
		cell.setCellStyle(style);
         
		/*
		 * cell = rowhead.createCell(2); cell.setCellValue("District Name");
		 * cell.setCellStyle(style);
		 */
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Area of degraded land covered and Rainfed area developed (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Area covered with Soil and Moisture conservation activities (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Area brought under Plantation Horticulture and Afforestation (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Water Harvesting Structures newly created and rejuvenated (in no.)");
		cell.setCellStyle(style);
		
		
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Farmers benefitted (in no.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Additional Area brought under Protective irrigation (in ha.)");
		cell.setCellStyle(style);
		
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Employment Generated (in mandays)");
		cell.setCellStyle(style);
		
		
		 int sno = 1;
	     int rowno  = 7;
	     double totAreaDegLand = 0;
	     double totAreaSoilMoisture = 0;
	     double totAreaPlantation = 0;
	     double totNoofHarStruc = 0;
	     double totNoofFar = 0;
	     double totAreaBrouProIrr = 0;
	     double totNoofMan = 0;
	     
	     for(PrayasAchievementBean bean: list) {
	    	 Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getProj_name());
	        	row.createCell(2).setCellValue(bean.getDegraded_rainfed()==null?0.0:bean.getDegraded_rainfed().doubleValue());
				row.createCell(3).setCellValue(bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue());
	        	row.createCell(4).setCellValue(bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue());
	        	row.createCell(5).setCellValue(bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue());
	        	row.createCell(6).setCellValue(bean.getFarmer_benefitted_achie()==null?0.0:bean.getFarmer_benefitted_achie().doubleValue());
	        	row.createCell(7).setCellValue(bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue());
	        	row.createCell(8).setCellValue(bean.getMan_days_gen()==null?0.0:bean.getMan_days_gen().doubleValue());
	        	
	        	totAreaDegLand = totAreaDegLand + (bean.getDegraded_rainfed()==null?0.0:bean.getDegraded_rainfed().doubleValue());
	        	totAreaSoilMoisture = totAreaSoilMoisture + (bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue());
	        	totAreaPlantation = totAreaPlantation + (bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue());
	        	totNoofHarStruc = totNoofHarStruc + (bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue());
	        	totNoofFar = totNoofFar + (bean.getFarmer_benefitted_achie()==null?0.0:bean.getFarmer_benefitted_achie().doubleValue());
	        	totAreaBrouProIrr = totAreaBrouProIrr + (bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue());
	        	totNoofMan = totNoofMan + (bean.getMan_days_gen()==null?0.0:bean.getMan_days_gen().doubleValue());
	        	
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
			font1.setFontHeightInPoints((short) 7);
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
	        cell.setCellValue(totAreaDegLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totAreaSoilMoisture);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totAreaPlantation);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totNoofHarStruc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totNoofFar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totAreaBrouProIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totNoofMan);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
	        String fileName = "attachment; filename=Report O11- Project.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		return "reports/achievementforprayas";
	}
	
	
	@RequestMapping(value = "/downloadProjAchievementofDishaPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjAchievementofDishaPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		// WDC-PMKSY-0001113
		String finName = request.getParameter("finName");
		String monthname = request.getParameter("monthname");
		String stname = request.getParameter("stname");
		Integer dcode = Integer.parseInt(request.getParameter("dcode"));
		String distname = request.getParameter("distname");
		Integer month = Integer.parseInt(request.getParameter("monthdtl"));
		Integer finCode = Integer.parseInt(request.getParameter("year"));

		List<PrayasAchievementBean> list = new ArrayList<PrayasAchievementBean>();

		try {

			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("AchievementForPrayasReport");
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

			int m = finCode;

			if (finCode == 0) {
				paragraph3 = new Paragraph(
						"Report O11- Project-Wise, Year-wise and Month-wise Achievements for Prayas", f3);
			} else {
				paragraph3 = new Paragraph(
						"Report O11- Project-Wise, Year-wise and Month-wise Achievements for Prayas", f3);
			}
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			table = new PdfPTable(9);
			table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, });
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);

			BigDecimal areaof_Rainfed_degraded = BigDecimal.valueOf(0);

			BigDecimal areaof_getCroppedarea = BigDecimal.valueOf(0);
			BigDecimal areaof_getIncome = BigDecimal.valueOf(0);

			BigDecimal areaof_getArea_soilmoisture_activities_achie = BigDecimal.valueOf(0);
			BigDecimal areaof_getArea_afforestation_horticulture_achie = BigDecimal.valueOf(0);

			BigDecimal areaof_getWater_created_renovated_achie = BigDecimal.valueOf(0);
			BigDecimal areaof_getFarmer_benefitted_achie = BigDecimal.valueOf(0);
			BigDecimal areaof_getProtective_irrigation_achie = BigDecimal.valueOf(0);
			BigDecimal areaof_getMandays_generated_achie = BigDecimal.valueOf(0);

			list = ser.findprojWisemonthachdata(dcode, finCode, month);
			CommonFunctions.insertCellHeader(table,
					"State : " + stname + ", District : "+distname+", Year : " + finName + " , Month : " + monthname, Element.ALIGN_LEFT, 8, 1,
					bf8Bold);
			CommonFunctions.insertCellHeader(table, "", Element.ALIGN_RIGHT, 8, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table,
					"Area of degraded land covered and Rainfed area developed (in ha.) ", Element.ALIGN_CENTER, 1, 1,
					bf8Bold);
			CommonFunctions.insertCellHeader(table,
					"Area covered with Soil and Moisture conservation activities (in ha.)", Element.ALIGN_CENTER, 1, 1,
					bf8Bold);
			CommonFunctions.insertCellHeader(table,
					"Area brought under Plantation Horticulture and Afforestation (in ha.)", Element.ALIGN_CENTER, 1, 1,
					bf8Bold);
			CommonFunctions.insertCellHeader(table,
					"Water Harvesting Structures newly created and rejuvenated (in no.)", Element.ALIGN_CENTER, 1, 1,
					bf8Bold);
			CommonFunctions.insertCellHeader(table, "Farmers benefitted (in no.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Additional Area brought under Protective irrigation (in ha.)",
					Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Employment Generated (in mandays)", Element.ALIGN_CENTER, 1, 1,
					bf8Bold);

			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			int k = 1;
			if (list.size() != 0)
				for (int i = 0; i < list.size(); i++) {
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table,
							list.get(i).getDegraded_rainfed() == null ? "0.00"
									: String.format(Locale.US, "%.4f", list.get(i).getDegraded_rainfed()),
							Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table,
							list.get(i).getArea_soilmoisture_activities_achie() == null ? "0.00"
									: String.format(Locale.US, "%.4f",
											list.get(i).getArea_soilmoisture_activities_achie()),
							Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table,
							list.get(i).getArea_afforestation_horticulture_achie() == null ? "0.00"
									: String.format(Locale.US, "%.4f",
											list.get(i).getArea_afforestation_horticulture_achie()),
							Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table,
							list.get(i).getWater_created_renovated_achie() == null ? "0"
									: list.get(i).getWater_created_renovated_achie().toString(),
							Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table,
							list.get(i).getFarmer_benefitted_achie() == null ? "0"
									: list.get(i).getFarmer_benefitted_achie().toString(),
							Element.ALIGN_RIGHT, 1, 1, bf8);

					CommonFunctions.insertCell(table,
							list.get(i).getProtective_irrigation_achie() == null ? "0.00"
									: String.format(Locale.US, "%.4f", list.get(i).getProtective_irrigation_achie()),
							Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table,
							list.get(i).getMan_days_gen() == null ? "0" : list.get(i).getMan_days_gen().toString(),
							Element.ALIGN_RIGHT, 1, 1, bf8);

					k = k + 1;

					areaof_Rainfed_degraded = areaof_Rainfed_degraded
							.add(list.get(i).getDegraded_rainfed() == null ? BigDecimal.ZERO
									: list.get(i).getDegraded_rainfed());
					areaof_getArea_soilmoisture_activities_achie = areaof_getArea_soilmoisture_activities_achie
							.add(list.get(i).getArea_soilmoisture_activities_achie() == null ? BigDecimal.ZERO
									: list.get(i).getArea_soilmoisture_activities_achie());

					areaof_getArea_afforestation_horticulture_achie = areaof_getArea_afforestation_horticulture_achie
							.add(list.get(i).getArea_afforestation_horticulture_achie() == null ? BigDecimal.ZERO
									: list.get(i).getArea_afforestation_horticulture_achie());
					areaof_getWater_created_renovated_achie = areaof_getWater_created_renovated_achie
							.add(list.get(i).getWater_created_renovated_achie() == null ? BigDecimal.ZERO
									: list.get(i).getWater_created_renovated_achie());

					areaof_getFarmer_benefitted_achie = areaof_getFarmer_benefitted_achie
							.add(list.get(i).getFarmer_benefitted_achie() == null ? BigDecimal.ZERO
									: list.get(i).getFarmer_benefitted_achie());
					areaof_getProtective_irrigation_achie = areaof_getProtective_irrigation_achie
							.add(list.get(i).getProtective_irrigation_achie() == null ? BigDecimal.ZERO
									: list.get(i).getProtective_irrigation_achie());

					areaof_getMandays_generated_achie = areaof_getMandays_generated_achie.add(
							list.get(i).getMan_days_gen() == null ? BigDecimal.ZERO : list.get(i).getMan_days_gen());

				}
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(areaof_Rainfed_degraded), Element.ALIGN_RIGHT, 1, 1,
					bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(areaof_getArea_soilmoisture_activities_achie),
					Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(areaof_getArea_afforestation_horticulture_achie),
					Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(areaof_getWater_created_renovated_achie),
					Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(areaof_getFarmer_benefitted_achie), Element.ALIGN_RIGHT,
					1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(areaof_getProtective_irrigation_achie),
					Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(areaof_getMandays_generated_achie), Element.ALIGN_RIGHT,
					1, 1, bf10Bold);

			if (list.size() == 0)
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 11, 1, bf8);

			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,
					"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "
							+ CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
					Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report O11 - Project.pdf");
			response.setHeader("Pragma", "public");
			response.setContentLength(baos.size());
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
