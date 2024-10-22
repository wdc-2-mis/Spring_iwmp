package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import app.bean.BaseLineOutcomeBean;
import app.bean.CapicityBuildingTrainingBean;
import app.bean.GroundWaterTableBean;
import app.bean.Login;
import app.common.CommonFunctions;
import app.service.CommonService;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.reports.TargetAchievementQuarterService;

@Controller("TargetAchievementofIndicator")
public class TargetAchievementofIndicatorController {

	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@Autowired(required = true)
	private CommonService commonService;
	
	private Map<Integer, String> stateList;
	
	@RequestMapping(value="/targetAchievementofIndicators", method = RequestMethod.GET)
	public ModelAndView targetAchievementofIndicators(HttpServletRequest request, HttpServletResponse response)
	{
		
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		
			mav = new ModelAndView("reports/targetAchievementofIndicators");
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			mav.addObject("financialYear", ser.getFinancialYearonward21());
			mav.addObject("quaterMaster", commonService.getQuaterMaster());
		
		return mav; 
	}
	
	@RequestMapping(value="/getQuarterReport", method = RequestMethod.POST)
	public ModelAndView getQuarterReport(HttpServletRequest request, HttpServletResponse response)
	{
	
		ModelAndView mav = new ModelAndView();
		String userState=request.getParameter("state");
		String year= request.getParameter("year");
		String quarter= request.getParameter("quarter");
		
		String stName= request.getParameter("stName");
		String finName= request.getParameter("finName");
		String quartename= request.getParameter("quartename");
		String proj="";
		String proj1=null;
		String stname="";
		String stname1=null;
		String data[] = null;
		int i=1;
		List<String[]> dataList = new ArrayList<String[]>();
		ArrayList dataListNetTotal = new ArrayList();
		List<TargetAchievementQuarterBean> list=new  ArrayList<TargetAchievementQuarterBean>();
			mav = new ModelAndView("reports/targetAchievementofIndicators");
			String[] dataArrNetTotalStr = null;
			Integer[] dataArrNetTotal = null;
			BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO,  
					BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
			list=ser.getQuarterReport(Integer.parseInt(userState), Integer.parseInt(year),  Integer.parseInt(quarter));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
		//	dataArrNetTotal = new Integer[] { 0, 0, 0, 0};
		if(list != null) 
		{
			for(TargetAchievementQuarterBean bean : list) 
			{
				  data = new String[25]; 
				  if(bean.getSt_code()!=null) {
						
						data[0] = String.valueOf(i); // serial no
				  		data[1] = bean.getSt_code().toString();
						data[2] = bean.getSt_name();	// projName
						
						if(bean.getArea_soilmoisture_activities_tar()==null)
							data[3]="0.00";
						else
							data[3]=bean.getArea_soilmoisture_activities_tar().toString();
						if(bean.getArea_soilmoisture_activities_achie()==null)
							data[4]="0.00";
						else
							data[4]=bean.getArea_soilmoisture_activities_achie().toString();
						
						if(bean.getArea_afforestation_horticulture_tar()==null)
							data[5]="0.00";
						else
							data[5]=bean.getArea_afforestation_horticulture_tar().toString();
						if(bean.getArea_afforestation_horticulture_achie()==null)
							data[6]="0.00";
						else
							data[6]=bean.getArea_afforestation_horticulture_achie().toString();
						
						if(bean.getWater_created_renovated_tar()==null)	
							data[7]="0";
						else
							data[7]=bean.getWater_created_renovated_tar().toString();
						if(bean.getWater_created_renovated_achie()==null)
							data[8]="0";
						else
							data[8]=bean.getWater_created_renovated_achie().toString();
						
						
						  if(bean.getArea_diversified_crops_tar()==null) 
							  data[17]="0.00"; 
						  else
							  data[17]=bean.getArea_diversified_crops_tar().toString();
						  if(bean.getArea_diversified_crops_achie()==null) 
							  data[18]="0.00"; 
						  else
							  data[18]=bean.getArea_diversified_crops_achie().toString();
						  
						  if(bean.getArea_nilsingto_doubmulcrop_tar()==null) 
							  data[19]="0.00"; 
						  else
							  data[19]=bean.getArea_nilsingto_doubmulcrop_tar().toString();
						  if(bean.getArea_nilsingto_doubmulcrop_achie()==null) 
							  data[20]="0.00"; 
						  else
							  data[20]=bean.getArea_nilsingto_doubmulcrop_achie().toString();
						  
						  
						  if(bean.getIncrease_croparea_tar()==null) 
							  data[21]="0.00"; 
						  else
							  data[21]=bean.getIncrease_croparea_tar().toString();
						
						  if(bean.getIncrease_croparea_achie()==null) 
							  data[22]="0.00"; 
						  else
							  data[22]=bean.getIncrease_croparea_achie().toString();
						
								
						  if(bean.getIncrease_farmerincome_tar()==null)
							  data[23]="0.00";
						  else
							  data[23]=bean.getIncrease_farmerincome_tar().toString();
						  if(bean.getIncrease_farmerincome_achie()==null)
							  data[24]="0.00";
						  else
							  data[24]=bean.getIncrease_farmerincome_achie().toString();  
						
						
								
						if(bean.getFarmer_benefitte_tar()==null)
							data[9]="0.00";
						else
							data[9]=bean.getFarmer_benefitte_tar().toString();
						if(bean.getFarmer_benefitte_achie()==null)
							data[10]="0.00";
						else
							data[10]=bean.getFarmer_benefitte_achie().toString();
							
						if(bean.getProtective_irrigation_tar()==null)
							data[11]="0.00";
						else
							data[11]=bean.getProtective_irrigation_tar().toString();
						if(bean.getProtective_irrigation_achie()==null)
							data[12]="0.00";
						else
							data[12]=bean.getProtective_irrigation_achie().toString();
						
						if(bean.getMandays_generated_tar()==null)
							data[13]="0.00";
						else
							data[13]=bean.getMandays_generated_tar().toString();
						if(bean.getMandays_generated_achie()==null)
							data[14]="0.00";
						else
							data[14]=bean.getMandays_generated_achie().toString();
						
						if(bean.getAreaof_degraded_land_tar()==null) 
							data[15] ="0.00"; 
						else 
							data[15] =bean.getAreaof_degraded_land_tar().toString();
						
						if(bean.getAreaof_degraded_land_achie()==null) 
							data[16] ="0.00"; 
						else 
							data[16] =bean.getAreaof_degraded_land_achie().toString();
						 
							
							
							i++;
							
							
							dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(BigDecimal.valueOf(Double.valueOf(data[3])));
							dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(BigDecimal.valueOf(Double.valueOf(data[4])));
							dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(BigDecimal.valueOf(Double.valueOf(data[5])));
							dataArrNetTotalBD[3] = dataArrNetTotalBD[3].add(BigDecimal.valueOf(Double.valueOf(data[6])));
							dataArrNetTotalBD[4] = dataArrNetTotalBD[4].add(BigDecimal.valueOf(Double.valueOf(data[7])));
							dataArrNetTotalBD[5] = dataArrNetTotalBD[5].add(BigDecimal.valueOf(Double.valueOf(data[8])));
							dataArrNetTotalBD[6] = dataArrNetTotalBD[6].add(BigDecimal.valueOf(Double.valueOf(data[9])));
							dataArrNetTotalBD[7] = dataArrNetTotalBD[7].add(BigDecimal.valueOf(Double.valueOf(data[10])));
							dataArrNetTotalBD[8] = dataArrNetTotalBD[8].add(BigDecimal.valueOf(Double.valueOf(data[11])));
							dataArrNetTotalBD[9] = dataArrNetTotalBD[9].add(BigDecimal.valueOf(Double.valueOf(data[12])));
							dataArrNetTotalBD[10] = dataArrNetTotalBD[10].add(BigDecimal.valueOf(Double.valueOf(data[13])));
							dataArrNetTotalBD[11] = dataArrNetTotalBD[11].add(BigDecimal.valueOf(Double.valueOf(data[14])));
							dataArrNetTotalBD[12] =dataArrNetTotalBD[12].add(BigDecimal.valueOf(Double.valueOf(data[15])));
							dataArrNetTotalBD[13] =dataArrNetTotalBD[13].add(BigDecimal.valueOf(Double.valueOf(data[16])));
							dataArrNetTotalBD[14] = dataArrNetTotalBD[14].add(BigDecimal.valueOf(Double.valueOf(data[17])));
							dataArrNetTotalBD[15] = dataArrNetTotalBD[15].add(BigDecimal.valueOf(Double.valueOf(data[18])));
							dataArrNetTotalBD[16] = dataArrNetTotalBD[16].add(BigDecimal.valueOf(Double.valueOf(data[19])));
							dataArrNetTotalBD[17] = dataArrNetTotalBD[17].add(BigDecimal.valueOf(Double.valueOf(data[20])));
							
							dataArrNetTotalBD[18] = dataArrNetTotalBD[18].add(BigDecimal.valueOf(Double.valueOf(data[21])));
							dataArrNetTotalBD[19] = dataArrNetTotalBD[19].add(BigDecimal.valueOf(Double.valueOf(data[22])));
							dataArrNetTotalBD[20] = dataArrNetTotalBD[20].add(BigDecimal.valueOf(Double.valueOf(data[23])));
							dataArrNetTotalBD[21] = dataArrNetTotalBD[21].add(BigDecimal.valueOf(Double.valueOf(data[24])));
							
							
							
							/*
							 * dataArrNetTotalBD[14] =
							 * dataArrNetTotalBD[14].add(BigDecimal.valueOf(Double.valueOf(data[19])));
							 * dataArrNetTotalBD[15] =
							 * dataArrNetTotalBD[15].add(BigDecimal.valueOf(Double.valueOf(data[20])));
							 * dataArrNetTotalBD[16] =
							 * dataArrNetTotalBD[16].add(BigDecimal.valueOf(Double.valueOf(data[21])));
							 * dataArrNetTotalBD[17] =
							 * dataArrNetTotalBD[17].add(BigDecimal.valueOf(Double.valueOf(data[22])));
							 * dataArrNetTotalBD[18] =
							 * dataArrNetTotalBD[18].add(BigDecimal.valueOf(Double.valueOf(data[9])));
							 * dataArrNetTotalBD[19] =
							 * dataArrNetTotalBD[19].add(BigDecimal.valueOf(Double.valueOf(data[10])));
							 * dataArrNetTotalBD[20] =
							 * dataArrNetTotalBD[20].add(BigDecimal.valueOf(Double.valueOf(data[23])));
							 * dataArrNetTotalBD[21] =
							 * dataArrNetTotalBD[21].add(BigDecimal.valueOf(Double.valueOf(data[24])));
							 */
							
							
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
			dataArrNetTotalStr[7] = dataArrNetTotalBD[7].toString();
			dataArrNetTotalStr[8] = dataArrNetTotalBD[8].toString();
			dataArrNetTotalStr[9] = dataArrNetTotalBD[9].toString();
			dataArrNetTotalStr[10] = dataArrNetTotalBD[10].toString();
			dataArrNetTotalStr[11] = dataArrNetTotalBD[11].toString();
			dataArrNetTotalStr[12] = dataArrNetTotalBD[12].toString();
			dataArrNetTotalStr[13] = dataArrNetTotalBD[13].toString();
			dataArrNetTotalStr[14] = dataArrNetTotalBD[14].toString();
			dataArrNetTotalStr[15] = dataArrNetTotalBD[15].toString();
			dataArrNetTotalStr[16] = dataArrNetTotalBD[16].toString();
			dataArrNetTotalStr[17] = dataArrNetTotalBD[17].toString();
			
			dataArrNetTotalStr[18] = dataArrNetTotalBD[18].toString();
			dataArrNetTotalStr[19] = dataArrNetTotalBD[19].toString();
			dataArrNetTotalStr[20] = dataArrNetTotalBD[20].toString();
			dataArrNetTotalStr[21] = dataArrNetTotalBD[21].toString();
			
			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			
			
			
			
		}
			
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			mav.addObject("year", year);
			mav.addObject("quarter", quarter);
			mav.addObject("financialYear", ser.getFinancialYearonward21());
			mav.addObject("quaterMaster", commonService.getQuaterMaster());
			mav.addObject("stName", stName);
			mav.addObject("finName", finName);
			mav.addObject("quartename", quartename);
			if(userState.equals("0"))
				mav.addObject("stcount", 30);
			
			if(!userState.equals("0"))	
				mav.addObject("stcount", 1);
		
		return mav; 
	}
	
	@RequestMapping(value = "/downloadQuarterReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadQuarterReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String userState= request.getParameter("state");
		String year= request.getParameter("year");
		String quarter= request.getParameter("quarter");
		
		String stName= request.getParameter("stName");
		String finName= request.getParameter("finName");
		String quartename= request.getParameter("quartename");
		
		
		List<TargetAchievementQuarterBean> list=new  ArrayList<TargetAchievementQuarterBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("QuarterReport");
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
			
			int m=Integer.parseInt(year);
			
			 if(Integer.parseInt(year)==0) {
				paragraph3 = new Paragraph("Report O9- State-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators", f3);
			 }
			 else {
				 paragraph3 = new Paragraph("Report O9- State-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators", f3);
			 }
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			//	table = new PdfPTable(24);
			//	table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,5,5,5,5,5,5,5,5});
				
				table = new PdfPTable(13);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				
				
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				 
				BigDecimal area_soilmoisture_activities_achie= BigDecimal.valueOf(0);
				BigDecimal area_soilmoisture_activities_tar= BigDecimal.valueOf(0);
				
				BigDecimal area_afforestation_horticulture_achie= BigDecimal.valueOf(0);
				BigDecimal area_afforestation_horticulture_tar= BigDecimal.valueOf(0);
				
				BigDecimal water_created_renovated_achie= BigDecimal.valueOf(0);
				BigDecimal water_created_renovated_tar= BigDecimal.valueOf(0);
				
				BigDecimal farmer_benefitte_achie= BigDecimal.valueOf(0);
				BigDecimal farmer_benefitte_tar= BigDecimal.valueOf(0);
				
				BigDecimal protective_irrigation_achie= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation_tar= BigDecimal.valueOf(0);
				
				BigDecimal mandays_generated_achie= BigDecimal.valueOf(0);
				BigDecimal mandays_generated_tar= BigDecimal.valueOf(0);
				
				BigDecimal areaof_degraded_land_tar= BigDecimal.valueOf(0);
				BigDecimal areaof_degraded_land_achie= BigDecimal.valueOf(0);
				
				BigDecimal area_diversified_crops_achie= BigDecimal.valueOf(0);
				BigDecimal area_diversified_crops_tar= BigDecimal.valueOf(0);
			
				BigDecimal area_nilsingto_doubmulcrop_achie= BigDecimal.valueOf(0);
				BigDecimal area_nilsingto_doubmulcrop_tar= BigDecimal.valueOf(0);
				
				BigDecimal increase_croparea_tar= BigDecimal.valueOf(0);
				BigDecimal increase_croparea_achie= BigDecimal.valueOf(0);
			
				BigDecimal increase_farmerincome_tar= BigDecimal.valueOf(0);
				BigDecimal increase_farmerincome_achie= BigDecimal.valueOf(0);
				
				list=ser.getQuarterReport(Integer.parseInt(userState), Integer.parseInt(year),  Integer.parseInt(quarter));
				
				CommonFunctions.insertCellHeader(table, "State: "+stName+", Financial Year: "+finName+", Quarter: "+quartename, Element.ALIGN_LEFT, 7, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area of degraded land covered/Rainfed area developed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area covered with soil and moisture conservation activities ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought under plantation (Afforestation/Horticulture))", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of water harvesting structure created/renovated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of farmers benefitted", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought under protective irrigation (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of man-days generated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Additional area brought under diversified crops/change in cropping system", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought from no crop/single crop to single/multiple crop", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Increase in cropped area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Increase in farmers income (%) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
				
				
		/*		CommonFunctions.insertCellHeader(table, "State: "+stName+", Financial Year: "+finName+", Quarter: "+quartename, Element.ALIGN_LEFT, 10, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 14, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area of degraded land covered/Rainfed area developed", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area covered with soil and moisture conservation activities ", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought under plantation (Afforestation/Horticulture))", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of water harvesting structure created/renovated", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of farmers benefitted", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought under protective irrigation (created/renovated)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of man-days generated", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Additional area brought under diversified crops/change in cropping system", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought from no crop/single crop to single/multiple crop", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Increase in cropped area", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Increase in farmers income (%) ", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);   */
				
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
			/*	CommonFunctions.insertCellHeader(table, "14", Element.ALIGN_CENTER, 1, 1, bf8Bold);
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
				*/
				int k=1;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					//	CommonFunctions.insertCell(table, list.get(i).getAreaof_degraded_land_tar()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getAreaof_degraded_land_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getAreaof_degraded_land_achie()==null?"0.00":  String.format(Locale.US, "%.4f",list.get(i).getAreaof_degraded_land_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
					//	CommonFunctions.insertCell(table, list.get(i).getArea_soilmoisture_activities_tar()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getArea_soilmoisture_activities_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_soilmoisture_activities_achie()==null?"0.00":  String.format(Locale.US, "%.4f",list.get(i).getArea_soilmoisture_activities_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
					//	CommonFunctions.insertCell(table, list.get(i).getArea_afforestation_horticulture_tar()==null?"0.00": String.format(Locale.US, "%.4f", list.get(i).getArea_afforestation_horticulture_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_afforestation_horticulture_achie()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getArea_afforestation_horticulture_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
					//	CommonFunctions.insertCell(table, list.get(i).getWater_created_renovated_tar()==null?"0":String.format(Locale.US, "%.0f",list.get(i).getWater_created_renovated_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getWater_created_renovated_achie()==null?"0":  String.format(Locale.US, "%.0f",list.get(i).getWater_created_renovated_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
					//	CommonFunctions.insertCell(table, list.get(i).getFarmer_benefitte_tar()==null?"0":list.get(i).getFarmer_benefitte_tar().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFarmer_benefitte_achie()==null?"0":  String.format(Locale.US, "%.0f",list.get(i).getFarmer_benefitte_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
					//	CommonFunctions.insertCell(table, list.get(i).getProtective_irrigation_tar()==null?"": String.format(Locale.US, "%.4f",list.get(i).getProtective_irrigation_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProtective_irrigation_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getProtective_irrigation_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
					//	CommonFunctions.insertCell(table, list.get(i).getMandays_generated_tar()==null?"0":list.get(i).getMandays_generated_tar().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMandays_generated_achie()==null?"0":  String.format(Locale.US, "%.0f",list.get(i).getMandays_generated_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
					//	CommonFunctions.insertCell(table, list.get(i).getArea_diversified_crops_tar()==null?"": String.format(Locale.US, "%.4f",list.get(i).getArea_diversified_crops_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_diversified_crops_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getArea_diversified_crops_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
					//	CommonFunctions.insertCell(table, list.get(i).getArea_nilsingto_doubmulcrop_tar()==null?"": String.format(Locale.US, "%.4f",list.get(i).getArea_nilsingto_doubmulcrop_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_nilsingto_doubmulcrop_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getArea_nilsingto_doubmulcrop_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
					//	CommonFunctions.insertCell(table, list.get(i).getIncrease_croparea_tar()==null?"": String.format(Locale.US, "%.4f",list.get(i).getIncrease_croparea_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getIncrease_croparea_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getIncrease_croparea_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
					//	CommonFunctions.insertCell(table, list.get(i).getIncrease_farmerincome_tar()==null?"": String.format(Locale.US, "%.4f",list.get(i).getIncrease_farmerincome_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getIncrease_farmerincome_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getIncrease_farmerincome_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						
						k=k+1;
						
						areaof_degraded_land_tar=areaof_degraded_land_tar.add(list.get(i).getAreaof_degraded_land_tar()==null?BigDecimal.ZERO:list.get(i).getAreaof_degraded_land_tar());
						areaof_degraded_land_achie=areaof_degraded_land_achie.add(list.get(i).getAreaof_degraded_land_achie()==null?BigDecimal.ZERO:list.get(i).getAreaof_degraded_land_achie());
						
						area_soilmoisture_activities_tar=area_soilmoisture_activities_tar.add(list.get(i).getArea_soilmoisture_activities_tar()==null?BigDecimal.ZERO:list.get(i).getArea_soilmoisture_activities_tar());
						area_soilmoisture_activities_achie=area_soilmoisture_activities_achie.add(list.get(i).getArea_soilmoisture_activities_achie()==null?BigDecimal.ZERO:list.get(i).getArea_soilmoisture_activities_achie());
						
						area_afforestation_horticulture_tar=area_afforestation_horticulture_tar.add(list.get(i).getArea_afforestation_horticulture_tar()==null?BigDecimal.ZERO:list.get(i).getArea_afforestation_horticulture_tar());
						area_afforestation_horticulture_achie=area_afforestation_horticulture_achie.add(list.get(i).getArea_afforestation_horticulture_achie()==null?BigDecimal.ZERO:list.get(i).getArea_afforestation_horticulture_achie());
						
						water_created_renovated_tar=water_created_renovated_tar.add(list.get(i).getWater_created_renovated_tar()==null?BigDecimal.ZERO:list.get(i).getWater_created_renovated_tar());
						water_created_renovated_achie=water_created_renovated_achie.add(list.get(i).getWater_created_renovated_achie()==null?BigDecimal.ZERO:list.get(i).getWater_created_renovated_achie());
						
						farmer_benefitte_tar=farmer_benefitte_tar.add(list.get(i).getFarmer_benefitte_tar()==null?BigDecimal.ZERO:list.get(i).getFarmer_benefitte_tar());
						farmer_benefitte_achie=farmer_benefitte_achie.add(list.get(i).getFarmer_benefitte_achie()==null?BigDecimal.ZERO:list.get(i).getFarmer_benefitte_achie());
						
						protective_irrigation_tar=protective_irrigation_tar.add(list.get(i).getProtective_irrigation_tar()==null?BigDecimal.ZERO:list.get(i).getProtective_irrigation_tar());
						protective_irrigation_achie=protective_irrigation_achie.add(list.get(i).getProtective_irrigation_achie()==null?BigDecimal.ZERO:list.get(i).getProtective_irrigation_achie());
						mandays_generated_tar=mandays_generated_tar.add(list.get(i).getMandays_generated_tar()==null?BigDecimal.ZERO:list.get(i).getMandays_generated_tar());
						mandays_generated_achie=mandays_generated_achie.add(list.get(i).getMandays_generated_achie()==null?BigDecimal.ZERO:list.get(i).getMandays_generated_achie());
						
						area_diversified_crops_achie=area_diversified_crops_achie.add(list.get(i).getArea_diversified_crops_achie()==null?BigDecimal.ZERO:list.get(i).getArea_diversified_crops_achie());
						area_diversified_crops_tar=area_diversified_crops_tar.add(list.get(i).getArea_diversified_crops_tar()==null?BigDecimal.ZERO:list.get(i).getArea_diversified_crops_tar());
						area_nilsingto_doubmulcrop_achie=area_nilsingto_doubmulcrop_achie.add(list.get(i).getArea_nilsingto_doubmulcrop_achie()==null?BigDecimal.ZERO:list.get(i).getArea_nilsingto_doubmulcrop_achie());
						area_nilsingto_doubmulcrop_tar=area_nilsingto_doubmulcrop_tar.add(list.get(i).getArea_nilsingto_doubmulcrop_tar()==null?BigDecimal.ZERO:list.get(i).getArea_nilsingto_doubmulcrop_tar());
						
						increase_croparea_tar=increase_croparea_tar.add(list.get(i).getIncrease_croparea_tar()==null?BigDecimal.ZERO:list.get(i).getIncrease_croparea_tar());
						increase_croparea_achie=increase_croparea_achie.add(list.get(i).getIncrease_croparea_achie()==null?BigDecimal.ZERO:list.get(i).getIncrease_croparea_achie());
						increase_farmerincome_tar=increase_farmerincome_tar.add(list.get(i).getIncrease_farmerincome_tar()==null?BigDecimal.ZERO:list.get(i).getIncrease_farmerincome_tar());
						increase_farmerincome_achie=increase_farmerincome_achie.add(list.get(i).getIncrease_farmerincome_achie()==null?BigDecimal.ZERO:list.get(i).getIncrease_farmerincome_achie());
						
					}
				
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
				//	CommonFunctions.insertCell3(table, String.valueOf(areaof_degraded_land_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_degraded_land_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				//	CommonFunctions.insertCell3(table, String.valueOf(area_soilmoisture_activities_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_soilmoisture_activities_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				//	CommonFunctions.insertCell3(table, String.valueOf(area_afforestation_horticulture_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_afforestation_horticulture_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				//	CommonFunctions.insertCell3(table, String.valueOf(water_created_renovated_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table,String.format(Locale.US, "%.0f", water_created_renovated_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
				//	CommonFunctions.insertCell3(table, String.valueOf(farmer_benefitte_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table,String.format(Locale.US, "%.0f", farmer_benefitte_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
				//	CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				//	CommonFunctions.insertCell3(table, String.valueOf(mandays_generated_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table,String.format(Locale.US, "%.0f", mandays_generated_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
				//	CommonFunctions.insertCell3(table, String.valueOf(area_diversified_crops_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_diversified_crops_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				//	CommonFunctions.insertCell3(table, String.valueOf(area_nilsingto_doubmulcrop_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_nilsingto_doubmulcrop_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
				//	CommonFunctions.insertCell3(table, String.valueOf(increase_croparea_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(increase_croparea_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				//	BigDecimal vart= new BigDecimal(30);
					
					BigDecimal vara= BigDecimal.valueOf(0);
					if(userState.equals("0"))
						 vara= new BigDecimal(30);
					
					if(!userState.equals("0"))	
						vara= new BigDecimal(1);
					
					
				//	CommonFunctions.insertCell3(table, String.valueOf(increase_farmerincome_tar.divide(vart, 4, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(increase_farmerincome_achie.divide(vara, 4, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
				//	CommonFunctions.insertCell3(table, "", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				//	CommonFunctions.insertCell3(table, "", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					if(list.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 13, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=O9-Report.pdf");
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
	
	@RequestMapping(value = "/downloadExceltargetAchievementofIndicators", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExceltargetAchievementofIndicators(HttpServletRequest request, HttpServletResponse response) 
	{

		String userState= request.getParameter("state");
		String year= request.getParameter("year");
		String quarter= request.getParameter("quarter");
		
		String stName= request.getParameter("stName");
		String finName= request.getParameter("finName");
		String quartename= request.getParameter("quartename");
		
		List<TargetAchievementQuarterBean> list=new  ArrayList<TargetAchievementQuarterBean>();
		
		list=ser.getQuarterReport(Integer.parseInt(userState), Integer.parseInt(year),  Integer.parseInt(quarter));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O9- State-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			int y = Integer.parseInt(year);
			
			String rptName = "Report O9- State-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators ";
			String areaAmtValDetail ="";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,7);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,8,12);
	        sheet.addMergedRegion(mergedRegion);
	        
		
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stName +"   Financial Year : "+finName +"   Quarter : "+quartename);  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<8;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
			cell = detail.createCell(8);
			cell.setCellValue("All Area in Ha.");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =9; i<13;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
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
			cell.setCellValue("Area of degraded land covered/Rainfed area developed");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
						
			cell = rowhead.createCell(3);
			cell.setCellValue("Area covered with soil and moisture conservation activities");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Area brought under plantation (Afforestation/Horticulture)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(5);
			cell.setCellValue("No. of water harvesting structure created/renovated");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(6);
			cell.setCellValue("No. of farmers benefitted");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Area brought under protective irrigation (created/renovated)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(8);
			cell.setCellValue("No. of man-days generated");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Additional area brought under diversified crops/change in cropping system");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Area brought from no crop/single crop to single/multiple crop");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Increase in cropped area");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Average Increase in farmers income (%)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
		
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<13;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totAreaCovSoilTar = 0;
	        double totAreaCovSoilAch = 0;
	        double totAreaCovPlantTar = 0;
	        double totAreaCovPlantAch = 0;
	        double totWaterHarvTar = 0;
	        double totWaterHarvAch = 0;
	        double totFarmerBenTar = 0;
	        double totFarmerBenAch = 0;
	        double totAreaCovIrrTar = 0;
	        double totAreaCovIrrAch = 0;
	        double totManDaysGenTar = 0;
	        double totManDaysGenAch = 0;
	        
	        double totareaof_degraded_land_tar = 0;
	        double totareaof_degraded_land_achie = 0;
	        double totAreaCovDiversifiedCropTar = 0;
	        double totAreaCovDiversifiedCropAch = 0;
	        double totAreaNilSingtoDoubMulCropTar = 0;
	        double totAreaNilSingtoDoubMulCropAch = 0;
	        double totCroppedAreaTar = 0;
	        double totCroppedAreaAch = 0;
	        double totFarmerIncomeTar = 0;
	        double totFarmerIncomeAch = 0;
	        
	        for(TargetAchievementQuarterBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getSt_name());
	        	row.createCell(2).setCellValue(bean.getAreaof_degraded_land_achie()==null?0.0:bean.getAreaof_degraded_land_achie().doubleValue());
	        	row.createCell(3).setCellValue(bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue()); 
	        	row.createCell(4).setCellValue(bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getFarmer_benefitte_achie()==null?0.0:bean.getFarmer_benefitte_achie().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getMandays_generated_achie()==null?0.0:bean.getMandays_generated_achie().doubleValue());
	        	row.createCell(9).setCellValue(bean.getArea_diversified_crops_achie()==null?0.0:bean.getArea_diversified_crops_achie().doubleValue());
	        	row.createCell(10).setCellValue(bean.getArea_nilsingto_doubmulcrop_achie()==null?0.0:bean.getArea_nilsingto_doubmulcrop_achie().doubleValue());
	        	row.createCell(11).setCellValue(bean.getIncrease_croparea_achie()==null?0.0:bean.getIncrease_croparea_achie().doubleValue());
	        	row.createCell(12).setCellValue(bean.getIncrease_farmerincome_achie()==null?0.0:bean.getIncrease_farmerincome_achie().doubleValue());
	        	
	        	
	        	totAreaCovSoilTar = totAreaCovSoilTar + (bean.getArea_soilmoisture_activities_tar()==null?0.0:bean.getArea_soilmoisture_activities_tar().doubleValue());
	        	totAreaCovSoilAch = totAreaCovSoilAch + (bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue());
	        	totAreaCovPlantTar = totAreaCovPlantTar + (bean.getArea_afforestation_horticulture_tar()==null?0.0:bean.getArea_afforestation_horticulture_tar().doubleValue());
	        	totAreaCovPlantAch = totAreaCovPlantAch + (bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue());
	        	totWaterHarvTar = totWaterHarvTar + (bean.getWater_created_renovated_tar()==null?0.0:bean.getWater_created_renovated_tar().doubleValue());
	        	totWaterHarvAch = totWaterHarvAch + (bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue());
	        	totFarmerBenTar = totFarmerBenTar + (bean.getFarmer_benefitte_tar()==null?0.0:bean.getFarmer_benefitte_tar().doubleValue());
	        	totFarmerBenAch = totFarmerBenAch + (bean.getFarmer_benefitte_achie()==null?0.0:bean.getFarmer_benefitte_achie().doubleValue());
	        	totAreaCovIrrTar = totAreaCovIrrTar + (bean.getProtective_irrigation_tar()==null?0.0:bean.getProtective_irrigation_tar().doubleValue());
	        	totAreaCovIrrAch = totAreaCovIrrAch + (bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue());
	        	totManDaysGenTar = totManDaysGenTar + (bean.getMandays_generated_tar()==null?0.0:bean.getMandays_generated_tar().doubleValue());
	        	totManDaysGenAch = totManDaysGenAch + (bean.getMandays_generated_achie()==null?0.0:bean.getMandays_generated_achie().doubleValue());
				
	        	totareaof_degraded_land_tar = totareaof_degraded_land_tar + (bean.getAreaof_degraded_land_tar()==null?0.0:bean.getAreaof_degraded_land_tar().doubleValue());
	        	totareaof_degraded_land_achie = totareaof_degraded_land_achie + (bean.getAreaof_degraded_land_achie()==null?0.0:bean.getAreaof_degraded_land_achie().doubleValue());
	        	totAreaCovDiversifiedCropTar = totAreaCovDiversifiedCropTar + (bean.getArea_diversified_crops_tar()==null?0.0:bean.getArea_diversified_crops_tar().doubleValue());
	        	totAreaCovDiversifiedCropAch = totAreaCovDiversifiedCropAch + (bean.getArea_diversified_crops_achie()==null?0.0:bean.getArea_diversified_crops_achie().doubleValue());
	        	totAreaNilSingtoDoubMulCropTar = totAreaNilSingtoDoubMulCropTar + (bean.getArea_nilsingto_doubmulcrop_tar()==null?0.0:bean.getArea_nilsingto_doubmulcrop_tar().doubleValue());
	        	totAreaNilSingtoDoubMulCropAch = totAreaNilSingtoDoubMulCropAch + (bean.getArea_nilsingto_doubmulcrop_achie()==null?0.0:bean.getArea_nilsingto_doubmulcrop_achie().doubleValue());
	        	
	        	totCroppedAreaTar = totCroppedAreaTar + (bean.getIncrease_croparea_tar()==null?0.0:bean.getIncrease_croparea_tar().doubleValue());
	        	totCroppedAreaAch = totCroppedAreaAch + (bean.getIncrease_croparea_achie()==null?0.0:bean.getIncrease_croparea_achie().doubleValue());
	        	totFarmerIncomeTar = totFarmerIncomeTar + (bean.getIncrease_farmerincome_tar()==null?0.0:bean.getIncrease_farmerincome_tar().doubleValue());
	        	totFarmerIncomeAch = totFarmerIncomeAch + (bean.getIncrease_farmerincome_achie()==null?0.0:bean.getIncrease_farmerincome_achie().doubleValue());
	        	
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
	        cell.setCellValue(totareaof_degraded_land_achie);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(totAreaCovSoilAch);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totAreaCovPlantAch);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(5);
	        cell.setCellValue(totWaterHarvAch);
	        cell.setCellStyle(style1);
	       
	        cell = row.createCell(6);
	        cell.setCellValue(totFarmerBenAch);
	        cell.setCellStyle(style1);
	       
	        cell = row.createCell(7);
	        cell.setCellValue(totAreaCovIrrAch);
	        cell.setCellStyle(style1);
	       
	        cell = row.createCell(8);
	        cell.setCellValue(totManDaysGenAch);
	        cell.setCellStyle(style1);
	       
	        cell = row.createCell(9);
	        cell.setCellValue(totAreaCovDiversifiedCropAch);
	        cell.setCellStyle(style1);
	       
	        cell = row.createCell(10);
	        cell.setCellValue(totAreaNilSingtoDoubMulCropAch);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(11);
	        cell.setCellValue(totCroppedAreaAch);
	        cell.setCellStyle(style1);
	        
	        
			if(userState.equals("0")) 
			{
		        cell = row.createCell(12);
		        cell.setCellValue(String.format(Locale.US, "%.4f",totFarmerIncomeAch/30));
		        cell.setCellStyle(style1);
			}
			
			if(!userState.equals("0")) {
		        cell = row.createCell(12);
		        cell.setCellValue(String.format(Locale.US, "%.4f",totFarmerIncomeAch));
		        cell.setCellStyle(style1);
			}
			
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12); 
			
			
			
			
			
			
		/*	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 23, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,12,23);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,2,3); 
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
	        mergedRegion = new CellRangeAddress(6,6,18,19); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,20,21); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,22,23); 
	        sheet.addMergedRegion(mergedRegion);
	        
	
        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stName +"   Financial Year : "+finName +"   Quarter : "+quartename);  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<13;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
			cell = detail.createCell(12);
			cell.setCellValue("All Area in Ha.");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =13; i<24;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
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
			cell.setCellValue("Area of degraded land covered/Rainfed area developed");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(3).setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Area covered with soil and moisture conservation activities");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(5).setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Area brought under plantation (Afforestation/Horticulture)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(7).setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("No. of water harvesting structure created/renovated");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(9).setCellStyle(style);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("No. of farmers benefitted");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(11).setCellStyle(style);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Area brought under protective irrigation (created/renovated)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(13).setCellStyle(style);
			
			cell = rowhead.createCell(14);
			cell.setCellValue("No. of man-days generated");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(15).setCellStyle(style);
			
			cell = rowhead.createCell(16);
			cell.setCellValue("Additional area brought under diversified crops/change in cropping system");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(17).setCellStyle(style);
			
			cell = rowhead.createCell(18);
			cell.setCellValue("Area brought from no crop/single crop to single/multiple crop");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(19).setCellStyle(style);
			
			cell = rowhead.createCell(20);
			cell.setCellValue("Increase in cropped area");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(21).setCellStyle(style);
			
			cell = rowhead.createCell(22);
			cell.setCellValue("Average Increase in farmers income (%)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(23).setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(2);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(11);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(13);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(15);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(17);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(19);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(20);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(21);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(22);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(23);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			
			Row rowhead2 = sheet.createRow(8);
			for(int i=0;i<24;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 9;
	        double totAreaCovSoilTar = 0;
	        double totAreaCovSoilAch = 0;
	        double totAreaCovPlantTar = 0;
	        double totAreaCovPlantAch = 0;
	        double totWaterHarvTar = 0;
	        double totWaterHarvAch = 0;
	        double totFarmerBenTar = 0;
	        double totFarmerBenAch = 0;
	        double totAreaCovIrrTar = 0;
	        double totAreaCovIrrAch = 0;
	        double totManDaysGenTar = 0;
	        double totManDaysGenAch = 0;
	        
	        double totareaof_degraded_land_tar = 0;
	        double totareaof_degraded_land_achie = 0;
	        double totAreaCovDiversifiedCropTar = 0;
	        double totAreaCovDiversifiedCropAch = 0;
	        double totAreaNilSingtoDoubMulCropTar = 0;
	        double totAreaNilSingtoDoubMulCropAch = 0;
	        double totCroppedAreaTar = 0;
	        double totCroppedAreaAch = 0;
	        double totFarmerIncomeTar = 0;
	        double totFarmerIncomeAch = 0;
	        
	        for(TargetAchievementQuarterBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getSt_name());
	        	
	        	row.createCell(2).setCellValue(bean.getAreaof_degraded_land_tar()==null?0.0:bean.getAreaof_degraded_land_tar().doubleValue()); 
	        	row.createCell(3).setCellValue(bean.getAreaof_degraded_land_achie()==null?0.0:bean.getAreaof_degraded_land_achie().doubleValue());
	        	
	        	row.createCell(4).setCellValue(bean.getArea_soilmoisture_activities_tar()==null?0.0:bean.getArea_soilmoisture_activities_tar().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getArea_afforestation_horticulture_tar()==null?0.0:bean.getArea_afforestation_horticulture_tar().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getWater_created_renovated_tar()==null?0.0:bean.getWater_created_renovated_tar().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue()); 
	        	row.createCell(10).setCellValue(bean.getFarmer_benefitte_tar()==null?0.0:bean.getFarmer_benefitte_tar().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getFarmer_benefitte_achie()==null?0.0:bean.getFarmer_benefitte_achie().doubleValue()); 
	        	row.createCell(12).setCellValue(bean.getProtective_irrigation_tar()==null?0.0:bean.getProtective_irrigation_tar().doubleValue()); 
	        	row.createCell(13).setCellValue(bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue()); 
	        	row.createCell(14).setCellValue(bean.getMandays_generated_tar()==null?0.0:bean.getMandays_generated_tar().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getMandays_generated_achie()==null?0.0:bean.getMandays_generated_achie().doubleValue());
	        	row.createCell(16).setCellValue(bean.getArea_diversified_crops_tar()==null?0.0:bean.getArea_diversified_crops_tar().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getArea_diversified_crops_achie()==null?0.0:bean.getArea_diversified_crops_achie().doubleValue());
	        	row.createCell(18).setCellValue(bean.getArea_nilsingto_doubmulcrop_tar()==null?0.0:bean.getArea_nilsingto_doubmulcrop_tar().doubleValue()); 
	        	row.createCell(19).setCellValue(bean.getArea_nilsingto_doubmulcrop_achie()==null?0.0:bean.getArea_nilsingto_doubmulcrop_achie().doubleValue());
	        	
	        	row.createCell(20).setCellValue(bean.getIncrease_croparea_tar()==null?0.0:bean.getIncrease_croparea_tar().doubleValue()); 
	        	row.createCell(21).setCellValue(bean.getIncrease_croparea_achie()==null?0.0:bean.getIncrease_croparea_achie().doubleValue());
	        	row.createCell(22).setCellValue(bean.getIncrease_farmerincome_tar()==null?0.0:bean.getIncrease_farmerincome_tar().doubleValue()); 
	        	row.createCell(23).setCellValue(bean.getIncrease_farmerincome_achie()==null?0.0:bean.getIncrease_farmerincome_achie().doubleValue());
	        	
	        	
	        	totAreaCovSoilTar = totAreaCovSoilTar + (bean.getArea_soilmoisture_activities_tar()==null?0.0:bean.getArea_soilmoisture_activities_tar().doubleValue());
	        	totAreaCovSoilAch = totAreaCovSoilAch + (bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue());
	        	totAreaCovPlantTar = totAreaCovPlantTar + (bean.getArea_afforestation_horticulture_tar()==null?0.0:bean.getArea_afforestation_horticulture_tar().doubleValue());
	        	totAreaCovPlantAch = totAreaCovPlantAch + (bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue());
	        	totWaterHarvTar = totWaterHarvTar + (bean.getWater_created_renovated_tar()==null?0.0:bean.getWater_created_renovated_tar().doubleValue());
	        	totWaterHarvAch = totWaterHarvAch + (bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue());
	        	totFarmerBenTar = totFarmerBenTar + (bean.getFarmer_benefitte_tar()==null?0.0:bean.getFarmer_benefitte_tar().doubleValue());
	        	totFarmerBenAch = totFarmerBenAch + (bean.getFarmer_benefitte_achie()==null?0.0:bean.getFarmer_benefitte_achie().doubleValue());
	        	totAreaCovIrrTar = totAreaCovIrrTar + (bean.getProtective_irrigation_tar()==null?0.0:bean.getProtective_irrigation_tar().doubleValue());
	        	totAreaCovIrrAch = totAreaCovIrrAch + (bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue());
	        	totManDaysGenTar = totManDaysGenTar + (bean.getMandays_generated_tar()==null?0.0:bean.getMandays_generated_tar().doubleValue());
	        	totManDaysGenAch = totManDaysGenAch + (bean.getMandays_generated_achie()==null?0.0:bean.getMandays_generated_achie().doubleValue());
				
	        	totareaof_degraded_land_tar = totareaof_degraded_land_tar + (bean.getAreaof_degraded_land_tar()==null?0.0:bean.getAreaof_degraded_land_tar().doubleValue());
	        	totareaof_degraded_land_achie = totareaof_degraded_land_achie + (bean.getAreaof_degraded_land_achie()==null?0.0:bean.getAreaof_degraded_land_achie().doubleValue());
	        	totAreaCovDiversifiedCropTar = totAreaCovDiversifiedCropTar + (bean.getArea_diversified_crops_tar()==null?0.0:bean.getArea_diversified_crops_tar().doubleValue());
	        	totAreaCovDiversifiedCropAch = totAreaCovDiversifiedCropAch + (bean.getArea_diversified_crops_achie()==null?0.0:bean.getArea_diversified_crops_achie().doubleValue());
	        	totAreaNilSingtoDoubMulCropTar = totAreaNilSingtoDoubMulCropTar + (bean.getArea_nilsingto_doubmulcrop_tar()==null?0.0:bean.getArea_nilsingto_doubmulcrop_tar().doubleValue());
	        	totAreaNilSingtoDoubMulCropAch = totAreaNilSingtoDoubMulCropAch + (bean.getArea_nilsingto_doubmulcrop_achie()==null?0.0:bean.getArea_nilsingto_doubmulcrop_achie().doubleValue());
	        	
	        	totCroppedAreaTar = totCroppedAreaTar + (bean.getIncrease_croparea_tar()==null?0.0:bean.getIncrease_croparea_tar().doubleValue());
	        	totCroppedAreaAch = totCroppedAreaAch + (bean.getIncrease_croparea_achie()==null?0.0:bean.getIncrease_croparea_achie().doubleValue());
	        	totFarmerIncomeTar = totFarmerIncomeTar + (bean.getIncrease_farmerincome_tar()==null?0.0:bean.getIncrease_farmerincome_tar().doubleValue());
	        	totFarmerIncomeAch = totFarmerIncomeAch + (bean.getIncrease_farmerincome_achie()==null?0.0:bean.getIncrease_farmerincome_achie().doubleValue());
	        	
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
	        cell.setCellValue(totareaof_degraded_land_tar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totareaof_degraded_land_achie);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totAreaCovSoilTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totAreaCovSoilAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totAreaCovPlantTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totAreaCovPlantAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totWaterHarvTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totWaterHarvAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totFarmerBenTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totFarmerBenAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totAreaCovIrrTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totAreaCovIrrAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totManDaysGenTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totManDaysGenAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totAreaCovDiversifiedCropTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totAreaCovDiversifiedCropAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totAreaNilSingtoDoubMulCropTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totAreaNilSingtoDoubMulCropAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totCroppedAreaTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(21);
	        cell.setCellValue(totCroppedAreaAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(22);
	        cell.setCellValue(totFarmerIncomeTar/30);
	        cell.setCellStyle(style1);
	        cell = row.createCell(23);
	        cell.setCellValue(totFarmerIncomeAch/30);
	        cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 23);  */
	        String fileName = "attachment; filename=Report O9- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/targetAchievementofIndicators";
		
	}
	
	@RequestMapping(value="/getDistWiseQuarterReport", method = RequestMethod.GET)
	public ModelAndView getDistWiseQuarterReport(HttpServletRequest request, HttpServletResponse response)
	{
	
		ModelAndView mav = new ModelAndView();
		String userState=request.getParameter("state");
		String year= request.getParameter("year");
		String quarter= request.getParameter("quarter");
		
		String stName= request.getParameter("stName");
		String finName= request.getParameter("finName");
		String quartename= request.getParameter("quartename");
//		String proj="";
//		String proj1=null;
//		String stname="";
//		String stname1=null;
		String data[] = null;
		int i=1;
		List<String[]> dataList = new ArrayList<String[]>();
		ArrayList dataListNetTotal = new ArrayList();
		List<TargetAchievementQuarterBean> list=new  ArrayList<TargetAchievementQuarterBean>();
			mav = new ModelAndView("reports/distWiseTargetAchievementofIndicators");
			String[] dataArrNetTotalStr = null;
			Integer[] dataArrNetTotal = null;
			BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, 
					BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
					BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
			list=ser.getDistWiseQuarterReport(Integer.parseInt(userState), Integer.parseInt(year),  Integer.parseInt(quarter));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
		//	dataArrNetTotal = new Integer[] { 0, 0, 0, 0};
		if(list != null) 
		{
			for(TargetAchievementQuarterBean bean : list) 
			{
				  data = new String[25]; 
				  if(bean.getDcode()!=null) {
						
						data[0] = String.valueOf(i); // serial no
				  		data[1] = bean.getDcode().toString();
						data[2] = bean.getDistname();	// projName
						
						if(bean.getArea_soilmoisture_activities_tar()==null)
							data[3]="0.00";
						else
							data[3]=bean.getArea_soilmoisture_activities_tar().toString();
						if(bean.getArea_soilmoisture_activities_achie()==null)
							data[4]="0.00";
						else
							data[4]=bean.getArea_soilmoisture_activities_achie().toString();
						
						if(bean.getArea_afforestation_horticulture_tar()==null)
							data[5]="0.00";
						else
							data[5]=bean.getArea_afforestation_horticulture_tar().toString();
						if(bean.getArea_afforestation_horticulture_achie()==null)
							data[6]="0.00";
						else
							data[6]=bean.getArea_afforestation_horticulture_achie().toString();
						
						if(bean.getWater_created_renovated_tar()==null)	
							data[7]="0";
						else
							data[7]=bean.getWater_created_renovated_tar().toString();
						if(bean.getWater_created_renovated_achie()==null)
							data[8]="0";
						else
							data[8]=bean.getWater_created_renovated_achie().toString();
						
						
						 /* if(bean.getIncrease_croparea_tar()==null) data[15]="0.00"; else
						 * data[15]=bean.getIncrease_croparea_tar().toString();
						 * if(bean.getIncrease_croparea_achie()==null) data[16]="0.00"; else
						 * data[16]=bean.getIncrease_croparea_achie().toString();
						
								
						if(bean.getIncrease_farmerincome_tar()==null)
							data[17]="0.00";
						else
							data[17]=bean.getIncrease_farmerincome_tar().toString();
						if(bean.getIncrease_farmerincome_achie()==null)
							data[18]="0.00";
						else
							data[18]=bean.getIncrease_farmerincome_achie().toString();  */
								
						if(bean.getFarmer_benefitte_tar()==null)
							data[9]="0.00";
						else
							data[9]=bean.getFarmer_benefitte_tar().toString();
						if(bean.getFarmer_benefitte_achie()==null)
							data[10]="0.00";
						else
							data[10]=bean.getFarmer_benefitte_achie().toString();
							
						if(bean.getProtective_irrigation_tar()==null)
							data[11]="0.00";
						else
							data[11]=bean.getProtective_irrigation_tar().toString();
						if(bean.getProtective_irrigation_achie()==null)
							data[12]="0.00";
						else
							data[12]=bean.getProtective_irrigation_achie().toString();
						
						if(bean.getMandays_generated_tar()==null)
							data[13]="0.00";
						else
							data[13]=bean.getMandays_generated_tar().toString();
						if(bean.getMandays_generated_achie()==null)
							data[14]="0.00";
						else
							data[14]=bean.getMandays_generated_achie().toString();
						
						if(bean.getAreaof_degraded_land_tar()==null) 
							data[15] ="0.00"; 
						else 
							data[15] =bean.getAreaof_degraded_land_tar().toString();
						
						if(bean.getAreaof_degraded_land_achie()==null) 
							data[16] ="0.00"; 
						else 
							data[16] =bean.getAreaof_degraded_land_achie().toString();
						 
						if(bean.getArea_diversified_crops_tar()==null) 
							data[17]="0.00"; 
						else
							data[17]=bean.getArea_diversified_crops_tar().toString();
							  
						if(bean.getArea_diversified_crops_achie()==null) 
							data[18]="0.00"; 
						else
							data[18]=bean.getArea_diversified_crops_achie().toString();
							  
						if(bean.getArea_nilsingto_doubmulcrop_tar()==null) 
							data[19]="0.00"; 
						else
							data[19]=bean.getArea_nilsingto_doubmulcrop_tar().toString();
							  
						if(bean.getArea_nilsingto_doubmulcrop_achie()==null) 
							data[20]="0.00"; 
						else
							data[20]=bean.getArea_nilsingto_doubmulcrop_achie().toString();	
						
						 if(bean.getIncrease_croparea_tar()==null) 
							  data[21]="0.00"; 
						  else
							  data[21]=bean.getIncrease_croparea_tar().toString();
						
						  if(bean.getIncrease_croparea_achie()==null) 
							  data[22]="0.00"; 
						  else
							  data[22]=bean.getIncrease_croparea_achie().toString();
						
								
						  if(bean.getIncrease_farmerincome_tar()==null)
							  data[23]="0.00";
						  else
							  data[23]=bean.getIncrease_farmerincome_tar().toString();
						  if(bean.getIncrease_farmerincome_achie()==null)
							  data[24]="0.00";
						  else
							  data[24]=bean.getIncrease_farmerincome_achie().toString();  
						
							
							i++;
							
							
							
							 
							dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(BigDecimal.valueOf(Double.valueOf(data[3])));
							dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(BigDecimal.valueOf(Double.valueOf(data[4])));
							dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(BigDecimal.valueOf(Double.valueOf(data[5])));
							dataArrNetTotalBD[3] = dataArrNetTotalBD[3].add(BigDecimal.valueOf(Double.valueOf(data[6])));
							dataArrNetTotalBD[4] = dataArrNetTotalBD[4].add(BigDecimal.valueOf(Double.valueOf(data[7])));
							dataArrNetTotalBD[5] = dataArrNetTotalBD[5].add(BigDecimal.valueOf(Double.valueOf(data[8])));
							dataArrNetTotalBD[6] = dataArrNetTotalBD[6].add(BigDecimal.valueOf(Double.valueOf(data[9])));
							dataArrNetTotalBD[7] = dataArrNetTotalBD[7].add(BigDecimal.valueOf(Double.valueOf(data[10])));
							dataArrNetTotalBD[8] = dataArrNetTotalBD[8].add(BigDecimal.valueOf(Double.valueOf(data[11])));
							dataArrNetTotalBD[9] = dataArrNetTotalBD[9].add(BigDecimal.valueOf(Double.valueOf(data[12])));
							dataArrNetTotalBD[10] = dataArrNetTotalBD[10].add(BigDecimal.valueOf(Double.valueOf(data[13])));
							dataArrNetTotalBD[11] = dataArrNetTotalBD[11].add(BigDecimal.valueOf(Double.valueOf(data[14])));
							dataArrNetTotalBD[12] = dataArrNetTotalBD[12].add(BigDecimal.valueOf(Double.valueOf(data[15])));
							dataArrNetTotalBD[13] = dataArrNetTotalBD[13].add(BigDecimal.valueOf(Double.valueOf(data[16])));
							dataArrNetTotalBD[14] = dataArrNetTotalBD[14].add(BigDecimal.valueOf(Double.valueOf(data[17])));
							dataArrNetTotalBD[15] = dataArrNetTotalBD[15].add(BigDecimal.valueOf(Double.valueOf(data[18])));
							dataArrNetTotalBD[16] = dataArrNetTotalBD[16].add(BigDecimal.valueOf(Double.valueOf(data[19])));
							dataArrNetTotalBD[17] = dataArrNetTotalBD[17].add(BigDecimal.valueOf(Double.valueOf(data[20])));
							
							dataArrNetTotalBD[18] = dataArrNetTotalBD[18].add(BigDecimal.valueOf(Double.valueOf(data[21])));
							dataArrNetTotalBD[19] = dataArrNetTotalBD[19].add(BigDecimal.valueOf(Double.valueOf(data[22])));
							dataArrNetTotalBD[20] = dataArrNetTotalBD[20].add(BigDecimal.valueOf(Double.valueOf(data[23])));
							dataArrNetTotalBD[21] = dataArrNetTotalBD[21].add(BigDecimal.valueOf(Double.valueOf(data[24])));
							
							 /* dataArrNetTotalBD[18] =
							 * dataArrNetTotalBD[18].add(BigDecimal.valueOf(Double.valueOf(data[9])));
							 * dataArrNetTotalBD[19] =
							 * dataArrNetTotalBD[19].add(BigDecimal.valueOf(Double.valueOf(data[10])));
							 * dataArrNetTotalBD[20] =
							 * dataArrNetTotalBD[20].add(BigDecimal.valueOf(Double.valueOf(data[23])));
							 * dataArrNetTotalBD[21] =
							 * dataArrNetTotalBD[21].add(BigDecimal.valueOf(Double.valueOf(data[24])));
							 */
							
							
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
			dataArrNetTotalStr[7] = dataArrNetTotalBD[7].toString();
			dataArrNetTotalStr[8] = dataArrNetTotalBD[8].toString();
			dataArrNetTotalStr[9] = dataArrNetTotalBD[9].toString();
			dataArrNetTotalStr[10] = dataArrNetTotalBD[10].toString();
			dataArrNetTotalStr[11] = dataArrNetTotalBD[11].toString();
			dataArrNetTotalStr[12] = dataArrNetTotalBD[12].toString();
			dataArrNetTotalStr[13] = dataArrNetTotalBD[13].toString();
			dataArrNetTotalStr[14] = dataArrNetTotalBD[14].toString();
			dataArrNetTotalStr[15] = dataArrNetTotalBD[15].toString();
			dataArrNetTotalStr[16] = dataArrNetTotalBD[16].toString();
			dataArrNetTotalStr[17] = dataArrNetTotalBD[17].toString();
			
			dataArrNetTotalStr[18] = dataArrNetTotalBD[18].toString();
			dataArrNetTotalStr[19] = dataArrNetTotalBD[19].toString();
			dataArrNetTotalStr[20] = dataArrNetTotalBD[20].toString();
			dataArrNetTotalStr[21] = dataArrNetTotalBD[21].toString();
			
			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("distWiseDataListNetTotal", dataListNetTotal);
			
			
		
			
			
		}
			
			mav.addObject("distWiseDataList", dataList);
			mav.addObject("distWiseDataListsize", dataList.size());
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			mav.addObject("year", year);
			mav.addObject("quarter", quarter);
			mav.addObject("financialYear", ser.getFinancialYearonward21());
			mav.addObject("quaterMaster", commonService.getQuaterMaster());
			mav.addObject("stName", stName);
			mav.addObject("finName", finName);
			mav.addObject("quartename", quartename);
			mav.addObject("noofdistrict", ser.getnoofStateProj(Integer.parseInt(userState)));
		
		return mav; 
	}
	
	@RequestMapping(value = "/getDistWiseTargetAchievementofIndicators", method = RequestMethod.POST)
	@ResponseBody
	public String getDistWiseTargetAchievementofIndicators(HttpServletRequest request, HttpServletResponse response) 
	{

		String userState= request.getParameter("state");
		String year= request.getParameter("year");
		String quarter= request.getParameter("quarter");
		
		String stName= request.getParameter("stName");
		String finName= request.getParameter("finName");
		String quartename= request.getParameter("quartename");
		
		int noofdist=ser.getnoofStateProj(Integer.parseInt(userState));
		
		List<TargetAchievementQuarterBean> list=new  ArrayList<TargetAchievementQuarterBean>();
		
		list=ser.getDistWiseQuarterReport(Integer.parseInt(userState), Integer.parseInt(year),  Integer.parseInt(quarter));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O9- District-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			int y = Integer.parseInt(year);
			
			String rptName = "Report O9- District-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators ";
			String areaAmtValDetail ="All Area in Ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			
			

			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,12);
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stName +"   Financial Year : "+finName +"   Quarter : "+quartename);  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<13;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
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
			cell.setCellValue("Area of degraded land covered/Rainfed area developed");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Area covered with soil and moisture conservation activities");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Area brought under plantation (Afforestation/Horticulture)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("No. of water harvesting structure created/renovated");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("No. of farmers benefitted");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Area brought under protective irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("No. of man-days generated");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Additional area brought under diversified crops/change in cropping system");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Area brought from no crop/single crop to single/multiple crop");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Increase in cropped area");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Average Increase in farmers income (%) ");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<13;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totAreaCovSoilTar = 0;
	        double totAreaCovSoilAch = 0;
	        double totAreaCovPlantTar = 0;
	        double totAreaCovPlantAch = 0;
	        double totWaterHarvTar = 0;
	        double totWaterHarvAch = 0;
	        double totFarmerBenTar = 0;
	        double totFarmerBenAch = 0;
	        double totAreaCovIrrTar = 0;
	        double totAreaCovIrrAch = 0;
	        double totManDaysGenTar = 0;
	        double totManDaysGenAch = 0;
	        
	        double totareaof_degraded_land_tar = 0;
	        double totareaof_degraded_land_achie = 0;
	        
	        double totAreaCovDiversifiedCropTar = 0;
	        double totAreaCovDiversifiedCropAch = 0;
	        double totAreaNilSingtoDoubMulCropTar = 0;
	        double totAreaNilSingtoDoubMulCropAch = 0;
	        double totCroppedAreaTar = 0;
	        double totCroppedAreaAch = 0;
	        double totFarmerIncomeTar = 0;
	        double totFarmerIncomeAch = 0;
	        
	        for(TargetAchievementQuarterBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getAreaof_degraded_land_achie()==null?0.0:bean.getAreaof_degraded_land_achie().doubleValue());
	        	row.createCell(3).setCellValue(bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue()); 
	        	row.createCell(4).setCellValue(bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getFarmer_benefitte_achie()==null?0.0:bean.getFarmer_benefitte_achie().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getMandays_generated_achie()==null?0.0:bean.getMandays_generated_achie().doubleValue());
	        	row.createCell(9).setCellValue(bean.getArea_diversified_crops_achie()==null?0.0:bean.getArea_diversified_crops_achie().doubleValue()); 
	        	row.createCell(10).setCellValue(bean.getArea_nilsingto_doubmulcrop_achie()==null?0.0:bean.getArea_nilsingto_doubmulcrop_achie().doubleValue());
	        	row.createCell(11).setCellValue(bean.getIncrease_croparea_achie()==null?0.0:bean.getIncrease_croparea_achie().doubleValue()); 
	        	row.createCell(12).setCellValue(bean.getIncrease_farmerincome_achie()==null?0.0:bean.getIncrease_farmerincome_achie().doubleValue());
	        	
	        	totAreaCovSoilTar = totAreaCovSoilTar + (bean.getArea_soilmoisture_activities_tar()==null?0.0:bean.getArea_soilmoisture_activities_tar().doubleValue());
	        	totAreaCovSoilAch = totAreaCovSoilAch + (bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue());
	        	totAreaCovPlantTar = totAreaCovPlantTar + (bean.getArea_afforestation_horticulture_tar()==null?0.0:bean.getArea_afforestation_horticulture_tar().doubleValue());
	        	totAreaCovPlantAch = totAreaCovPlantAch + (bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue());
	        	totWaterHarvTar = totWaterHarvTar + (bean.getWater_created_renovated_tar()==null?0.0:bean.getWater_created_renovated_tar().doubleValue());
	        	totWaterHarvAch = totWaterHarvAch + (bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue());
	        	totFarmerBenTar = totFarmerBenTar + (bean.getFarmer_benefitte_tar()==null?0.0:bean.getFarmer_benefitte_tar().doubleValue());
	        	totFarmerBenAch = totFarmerBenAch + (bean.getFarmer_benefitte_achie()==null?0.0:bean.getFarmer_benefitte_achie().doubleValue());
	        	totAreaCovIrrTar = totAreaCovIrrTar + (bean.getProtective_irrigation_tar()==null?0.0:bean.getProtective_irrigation_tar().doubleValue());
	        	totAreaCovIrrAch = totAreaCovIrrAch + (bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue());
	        	totManDaysGenTar = totManDaysGenTar + (bean.getMandays_generated_tar()==null?0.0:bean.getMandays_generated_tar().doubleValue());
	        	totManDaysGenAch = totManDaysGenAch + (bean.getMandays_generated_achie()==null?0.0:bean.getMandays_generated_achie().doubleValue());
				
	        	totareaof_degraded_land_tar = totareaof_degraded_land_tar + (bean.getAreaof_degraded_land_tar()==null?0.0:bean.getAreaof_degraded_land_tar().doubleValue());
	        	totareaof_degraded_land_achie = totareaof_degraded_land_achie + (bean.getAreaof_degraded_land_achie()==null?0.0:bean.getAreaof_degraded_land_achie().doubleValue());
				
	        	totAreaCovDiversifiedCropTar = totAreaCovDiversifiedCropTar + (bean.getArea_diversified_crops_tar()==null?0.0:bean.getArea_diversified_crops_tar().doubleValue());
	        	totAreaCovDiversifiedCropAch = totAreaCovDiversifiedCropAch + (bean.getArea_diversified_crops_achie()==null?0.0:bean.getArea_diversified_crops_achie().doubleValue());
	        	totAreaNilSingtoDoubMulCropTar = totAreaNilSingtoDoubMulCropTar + (bean.getArea_nilsingto_doubmulcrop_tar()==null?0.0:bean.getArea_nilsingto_doubmulcrop_tar().doubleValue());
	        	totAreaNilSingtoDoubMulCropAch = totAreaNilSingtoDoubMulCropAch + (bean.getArea_nilsingto_doubmulcrop_achie()==null?0.0:bean.getArea_nilsingto_doubmulcrop_achie().doubleValue());
	        	
	        	totCroppedAreaTar = totCroppedAreaTar + (bean.getIncrease_croparea_tar()==null?0.0:bean.getIncrease_croparea_tar().doubleValue());
	        	totCroppedAreaAch = totCroppedAreaAch + (bean.getIncrease_croparea_achie()==null?0.0:bean.getIncrease_croparea_achie().doubleValue());
	        	totFarmerIncomeTar = totFarmerIncomeTar + (bean.getIncrease_farmerincome_tar()==null?0.0:bean.getIncrease_farmerincome_tar().doubleValue());
	        	totFarmerIncomeAch = totFarmerIncomeAch + (bean.getIncrease_farmerincome_achie()==null?0.0:bean.getIncrease_farmerincome_achie().doubleValue());
	        	
	        	
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
	        cell.setCellValue(totareaof_degraded_land_achie);
	        cell.setCellStyle(style1);
	        
	       
	        cell = row.createCell(3);
	        cell.setCellValue(totAreaCovSoilAch);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totAreaCovPlantAch);
	        cell.setCellStyle(style1);
	       
	        cell = row.createCell(5);
	        cell.setCellValue(totWaterHarvAch);
	        cell.setCellStyle(style1);
	       
	        cell = row.createCell(6);
	        cell.setCellValue(totFarmerBenAch);
	        cell.setCellStyle(style1);
	       
	        cell = row.createCell(7);
	        cell.setCellValue(totAreaCovIrrAch);
	        cell.setCellStyle(style1);
	       
	        cell = row.createCell(8);
	        cell.setCellValue(totManDaysGenAch);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(9);
	        cell.setCellValue(totAreaCovDiversifiedCropAch);
	        cell.setCellStyle(style1);
	      
	        cell = row.createCell(10);
	        cell.setCellValue(totAreaNilSingtoDoubMulCropAch);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(11);
	        cell.setCellValue(totCroppedAreaAch);
	        cell.setCellStyle(style1);
	       
	        cell = row.createCell(12);  
	        cell.setCellValue(String.format(Locale.US, "%.4f",totFarmerIncomeAch/noofdist));
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
			
			
			
			
			
			
			
		/*
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 23, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,23);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,2,3); 
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
	        mergedRegion = new CellRangeAddress(6,6,18,19); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,20,21); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,22,23); 
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stName +"   Financial Year : "+finName +"   Quarter : "+quartename);  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<24;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
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
			cell.setCellValue("Area of degraded land covered/Rainfed area developed");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(3).setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Area covered with soil and moisture conservation activities");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(5).setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Area brought under plantation (Afforestation/Horticulture)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(7).setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("No. of water harvesting structure created/renovated");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(9).setCellStyle(style);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("No. of farmers benefitted");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(11).setCellStyle(style);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Area brought under protective irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(13).setCellStyle(style);
			
			cell = rowhead.createCell(14);
			cell.setCellValue("No. of man-days generated");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(15).setCellStyle(style);
			
			cell = rowhead.createCell(16);
			cell.setCellValue("Additional area brought under diversified crops/change in cropping system");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(17).setCellStyle(style);
			
			cell = rowhead.createCell(18);
			cell.setCellValue("Area brought from no crop/single crop to single/multiple crop");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(19).setCellStyle(style);
			
			cell = rowhead.createCell(20);
			cell.setCellValue("Increase in cropped area");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(21).setCellStyle(style);
			
			cell = rowhead.createCell(22);
			cell.setCellValue("Average Increase in farmers income (%) ");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(23).setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(2);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(11);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(13);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(15);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(17);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(19);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(20);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(21);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(22);
			cell.setCellValue("Target");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(23);
			cell.setCellValue("Achievement");  
			cell.setCellStyle(style);
			
			
			Row rowhead2 = sheet.createRow(8);
			for(int i=0;i<24;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 9;
	        double totAreaCovSoilTar = 0;
	        double totAreaCovSoilAch = 0;
	        double totAreaCovPlantTar = 0;
	        double totAreaCovPlantAch = 0;
	        double totWaterHarvTar = 0;
	        double totWaterHarvAch = 0;
	        double totFarmerBenTar = 0;
	        double totFarmerBenAch = 0;
	        double totAreaCovIrrTar = 0;
	        double totAreaCovIrrAch = 0;
	        double totManDaysGenTar = 0;
	        double totManDaysGenAch = 0;
	        
	        double totareaof_degraded_land_tar = 0;
	        double totareaof_degraded_land_achie = 0;
	        
	        double totAreaCovDiversifiedCropTar = 0;
	        double totAreaCovDiversifiedCropAch = 0;
	        double totAreaNilSingtoDoubMulCropTar = 0;
	        double totAreaNilSingtoDoubMulCropAch = 0;
	        double totCroppedAreaTar = 0;
	        double totCroppedAreaAch = 0;
	        double totFarmerIncomeTar = 0;
	        double totFarmerIncomeAch = 0;
	        
	        for(TargetAchievementQuarterBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	
	        	row.createCell(2).setCellValue(bean.getAreaof_degraded_land_tar()==null?0.0:bean.getAreaof_degraded_land_tar().doubleValue()); 
	        	row.createCell(3).setCellValue(bean.getAreaof_degraded_land_achie()==null?0.0:bean.getAreaof_degraded_land_achie().doubleValue());
	        	
	        	row.createCell(4).setCellValue(bean.getArea_soilmoisture_activities_tar()==null?0.0:bean.getArea_soilmoisture_activities_tar().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getArea_afforestation_horticulture_tar()==null?0.0:bean.getArea_afforestation_horticulture_tar().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getWater_created_renovated_tar()==null?0.0:bean.getWater_created_renovated_tar().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue()); 
	        	row.createCell(10).setCellValue(bean.getFarmer_benefitte_tar()==null?0.0:bean.getFarmer_benefitte_tar().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getFarmer_benefitte_achie()==null?0.0:bean.getFarmer_benefitte_achie().doubleValue()); 
	        	row.createCell(12).setCellValue(bean.getProtective_irrigation_tar()==null?0.0:bean.getProtective_irrigation_tar().doubleValue()); 
	        	row.createCell(13).setCellValue(bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue()); 
	        	row.createCell(14).setCellValue(bean.getMandays_generated_tar()==null?0.0:bean.getMandays_generated_tar().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getMandays_generated_achie()==null?0.0:bean.getMandays_generated_achie().doubleValue());
	        	row.createCell(16).setCellValue(bean.getArea_diversified_crops_tar()==null?0.0:bean.getArea_diversified_crops_tar().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getArea_diversified_crops_achie()==null?0.0:bean.getArea_diversified_crops_achie().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getArea_nilsingto_doubmulcrop_tar()==null?0.0:bean.getArea_nilsingto_doubmulcrop_tar().doubleValue()); 
	        	row.createCell(19).setCellValue(bean.getArea_nilsingto_doubmulcrop_achie()==null?0.0:bean.getArea_nilsingto_doubmulcrop_achie().doubleValue());
	        	row.createCell(20).setCellValue(bean.getIncrease_croparea_tar()==null?0.0:bean.getIncrease_croparea_tar().doubleValue()); 
	        	row.createCell(21).setCellValue(bean.getIncrease_croparea_achie()==null?0.0:bean.getIncrease_croparea_achie().doubleValue()); 
	        	row.createCell(22).setCellValue(bean.getIncrease_farmerincome_tar()==null?0.0:bean.getIncrease_farmerincome_tar().doubleValue()); 
	        	row.createCell(23).setCellValue(bean.getIncrease_farmerincome_achie()==null?0.0:bean.getIncrease_farmerincome_achie().doubleValue());
	        	
	        	totAreaCovSoilTar = totAreaCovSoilTar + (bean.getArea_soilmoisture_activities_tar()==null?0.0:bean.getArea_soilmoisture_activities_tar().doubleValue());
	        	totAreaCovSoilAch = totAreaCovSoilAch + (bean.getArea_soilmoisture_activities_achie()==null?0.0:bean.getArea_soilmoisture_activities_achie().doubleValue());
	        	totAreaCovPlantTar = totAreaCovPlantTar + (bean.getArea_afforestation_horticulture_tar()==null?0.0:bean.getArea_afforestation_horticulture_tar().doubleValue());
	        	totAreaCovPlantAch = totAreaCovPlantAch + (bean.getArea_afforestation_horticulture_achie()==null?0.0:bean.getArea_afforestation_horticulture_achie().doubleValue());
	        	totWaterHarvTar = totWaterHarvTar + (bean.getWater_created_renovated_tar()==null?0.0:bean.getWater_created_renovated_tar().doubleValue());
	        	totWaterHarvAch = totWaterHarvAch + (bean.getWater_created_renovated_achie()==null?0.0:bean.getWater_created_renovated_achie().doubleValue());
	        	totFarmerBenTar = totFarmerBenTar + (bean.getFarmer_benefitte_tar()==null?0.0:bean.getFarmer_benefitte_tar().doubleValue());
	        	totFarmerBenAch = totFarmerBenAch + (bean.getFarmer_benefitte_achie()==null?0.0:bean.getFarmer_benefitte_achie().doubleValue());
	        	totAreaCovIrrTar = totAreaCovIrrTar + (bean.getProtective_irrigation_tar()==null?0.0:bean.getProtective_irrigation_tar().doubleValue());
	        	totAreaCovIrrAch = totAreaCovIrrAch + (bean.getProtective_irrigation_achie()==null?0.0:bean.getProtective_irrigation_achie().doubleValue());
	        	totManDaysGenTar = totManDaysGenTar + (bean.getMandays_generated_tar()==null?0.0:bean.getMandays_generated_tar().doubleValue());
	        	totManDaysGenAch = totManDaysGenAch + (bean.getMandays_generated_achie()==null?0.0:bean.getMandays_generated_achie().doubleValue());
				
	        	totareaof_degraded_land_tar = totareaof_degraded_land_tar + (bean.getAreaof_degraded_land_tar()==null?0.0:bean.getAreaof_degraded_land_tar().doubleValue());
	        	totareaof_degraded_land_achie = totareaof_degraded_land_achie + (bean.getAreaof_degraded_land_achie()==null?0.0:bean.getAreaof_degraded_land_achie().doubleValue());
				
	        	totAreaCovDiversifiedCropTar = totAreaCovDiversifiedCropTar + (bean.getArea_diversified_crops_tar()==null?0.0:bean.getArea_diversified_crops_tar().doubleValue());
	        	totAreaCovDiversifiedCropAch = totAreaCovDiversifiedCropAch + (bean.getArea_diversified_crops_achie()==null?0.0:bean.getArea_diversified_crops_achie().doubleValue());
	        	totAreaNilSingtoDoubMulCropTar = totAreaNilSingtoDoubMulCropTar + (bean.getArea_nilsingto_doubmulcrop_tar()==null?0.0:bean.getArea_nilsingto_doubmulcrop_tar().doubleValue());
	        	totAreaNilSingtoDoubMulCropAch = totAreaNilSingtoDoubMulCropAch + (bean.getArea_nilsingto_doubmulcrop_achie()==null?0.0:bean.getArea_nilsingto_doubmulcrop_achie().doubleValue());
	        	
	        	totCroppedAreaTar = totCroppedAreaTar + (bean.getIncrease_croparea_tar()==null?0.0:bean.getIncrease_croparea_tar().doubleValue());
	        	totCroppedAreaAch = totCroppedAreaAch + (bean.getIncrease_croparea_achie()==null?0.0:bean.getIncrease_croparea_achie().doubleValue());
	        	totFarmerIncomeTar = totFarmerIncomeTar + (bean.getIncrease_farmerincome_tar()==null?0.0:bean.getIncrease_farmerincome_tar().doubleValue());
	        	totFarmerIncomeAch = totFarmerIncomeAch + (bean.getIncrease_farmerincome_achie()==null?0.0:bean.getIncrease_farmerincome_achie().doubleValue());
	        	
	        	
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
	        cell.setCellValue(totareaof_degraded_land_tar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totareaof_degraded_land_achie);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totAreaCovSoilTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totAreaCovSoilAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totAreaCovPlantTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totAreaCovPlantAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totWaterHarvTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totWaterHarvAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totFarmerBenTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totFarmerBenAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totAreaCovIrrTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totAreaCovIrrAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totManDaysGenTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totManDaysGenAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totAreaCovDiversifiedCropTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totAreaCovDiversifiedCropAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totAreaNilSingtoDoubMulCropTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totAreaNilSingtoDoubMulCropAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totCroppedAreaTar);
	        cell.setCellStyle(style1);
	        cell = row.createCell(21);
	        cell.setCellValue(totCroppedAreaAch);
	        cell.setCellStyle(style1);
	        cell = row.createCell(22);
	       // cell.setCellValue("");
	        cell.setCellValue(totFarmerIncomeTar/noofdist);
	        cell.setCellStyle(style1);
	        cell = row.createCell(23);
	      //  cell.setCellValue("");
	        cell.setCellValue(totFarmerIncomeAch/noofdist);
	        cell.setCellStyle(style1);

	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 23);  */
	        String fileName = "attachment; filename=Report O9- District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/targetAchievementofIndicators";
		
	}
	
	@RequestMapping(value = "/getDistWiseQuarterReportPDF", method = RequestMethod.POST)
	public ModelAndView getDistWiseQuarterReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String userState= request.getParameter("state");
		String year= request.getParameter("year");
		String quarter= request.getParameter("quarter");
		
		String stName= request.getParameter("stName");
		String finName= request.getParameter("finName");
		String quartename= request.getParameter("quartename");
		
		int noofdist=ser.getnoofStateProj(Integer.parseInt(userState));
		
		List<TargetAchievementQuarterBean> list=new  ArrayList<TargetAchievementQuarterBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("QuarterReport");
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
			
			int m=Integer.parseInt(year);
			
			 if(Integer.parseInt(year)==0) {
				paragraph3 = new Paragraph("Report O9- District-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators", f3);
			 }
			 else {
				 paragraph3 = new Paragraph("Report O9- District-wise, Year-wise and Quarter-wise Achievements on Output Outcome Monitoring Framework(OOMF) Indicators", f3);
			 }
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				//table = new PdfPTable(24);
				//table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,5,5,5,5,5,5,5,5});
				
				table = new PdfPTable(13);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);

				
				BigDecimal area_soilmoisture_activities_achie= BigDecimal.valueOf(0);
				BigDecimal area_soilmoisture_activities_tar= BigDecimal.valueOf(0);
				
				BigDecimal area_afforestation_horticulture_achie= BigDecimal.valueOf(0);
				BigDecimal area_afforestation_horticulture_tar= BigDecimal.valueOf(0);
				
				BigDecimal water_created_renovated_achie= BigDecimal.valueOf(0);
				BigDecimal water_created_renovated_tar= BigDecimal.valueOf(0);
				
				BigDecimal farmer_benefitte_achie= BigDecimal.valueOf(0);
				BigDecimal farmer_benefitte_tar= BigDecimal.valueOf(0);
				
				BigDecimal protective_irrigation_achie= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation_tar= BigDecimal.valueOf(0);
				
				BigDecimal mandays_generated_achie= BigDecimal.valueOf(0);
				BigDecimal mandays_generated_tar= BigDecimal.valueOf(0);
				
				BigDecimal areaof_degraded_land_tar= BigDecimal.valueOf(0);
				BigDecimal areaof_degraded_land_achie= BigDecimal.valueOf(0);
				
				BigDecimal area_diversified_crops_tar= BigDecimal.valueOf(0);
				BigDecimal area_diversified_crops_achie= BigDecimal.valueOf(0);
				
				BigDecimal area_nilsingto_doubmulcrop_tar= BigDecimal.valueOf(0);
				BigDecimal area_nilsingto_doubmulcrop_achie= BigDecimal.valueOf(0);
				
				BigDecimal increase_croparea_tar= BigDecimal.valueOf(0);
				BigDecimal increase_croparea_achie= BigDecimal.valueOf(0);
			
				BigDecimal increase_farmerincome_tar= BigDecimal.valueOf(0);
				BigDecimal increase_farmerincome_achie= BigDecimal.valueOf(0);

				
				
				list=ser.getDistWiseQuarterReport(Integer.parseInt(userState), Integer.parseInt(year),  Integer.parseInt(quarter));
				
				CommonFunctions.insertCellHeader(table, "State: "+stName+", Financial Year: "+finName+", Quarter: "+quartename, Element.ALIGN_LEFT, 7, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area of degraded land covered/Rainfed area developed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area covered with soil and moisture conservation activities ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought under plantation (Afforestation/Horticulture))", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of water harvesting structure created/renovated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of farmers benefitted", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought under protective irrigation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of man-days generated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Additional area brought under diversified crops/change in cropping system", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought from no crop/single crop to single/multiple crop", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Increase in cropped area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Increase in farmers income (%) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
				
			/*	CommonFunctions.insertCellHeader(table, "State: "+stName+", Financial Year: "+finName+", Quarter: "+quartename, Element.ALIGN_LEFT, 10, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 14, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area of degraded land covered/Rainfed area developed", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area covered with soil and moisture conservation activities ", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought under plantation (Afforestation/Horticulture))", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of water harvesting structure created/renovated", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of farmers benefitted", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought under protective irrigation", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of man-days generated", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Additional area brought under diversified crops/change in cropping system", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area brought from no crop/single crop to single/multiple crop", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Increase in cropped area", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Increase in farmers income (%) ", Element.ALIGN_CENTER, 2, 1, bf8Bold);

				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Target", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Achievement", Element.ALIGN_CENTER, 1, 1, bf8Bold);   */

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
			/*	CommonFunctions.insertCellHeader(table, "14", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "15", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "16", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "17", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "18", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "19", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "20", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "22", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "23", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "24", Element.ALIGN_CENTER, 1, 1, bf8Bold);  */

				int k=1;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, list.get(i).getAreaof_degraded_land_achie()==null?"0.00":  String.format(Locale.US, "%.4f",list.get(i).getAreaof_degraded_land_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_soilmoisture_activities_achie()==null?"0.00":  String.format(Locale.US, "%.4f",list.get(i).getArea_soilmoisture_activities_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_afforestation_horticulture_achie()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getArea_afforestation_horticulture_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getWater_created_renovated_achie()==null?"0":list.get(i).getWater_created_renovated_achie().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFarmer_benefitte_achie()==null?"0":list.get(i).getFarmer_benefitte_achie() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, list.get(i).getProtective_irrigation_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getProtective_irrigation_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMandays_generated_achie()==null?"0":list.get(i).getMandays_generated_achie().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, list.get(i).getArea_diversified_crops_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getArea_diversified_crops_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_nilsingto_doubmulcrop_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getArea_nilsingto_doubmulcrop_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, list.get(i).getIncrease_croparea_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getIncrease_croparea_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getIncrease_farmerincome_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getIncrease_farmerincome_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);

						
						
						
				/*		CommonFunctions.insertCell(table, list.get(i).getAreaof_degraded_land_tar()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getAreaof_degraded_land_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getAreaof_degraded_land_achie()==null?"0.00":  String.format(Locale.US, "%.4f",list.get(i).getAreaof_degraded_land_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_soilmoisture_activities_tar()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getArea_soilmoisture_activities_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_soilmoisture_activities_achie()==null?"0.00":  String.format(Locale.US, "%.4f",list.get(i).getArea_soilmoisture_activities_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_afforestation_horticulture_tar()==null?"0.00": String.format(Locale.US, "%.4f", list.get(i).getArea_afforestation_horticulture_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_afforestation_horticulture_achie()==null?"0.00": String.format(Locale.US, "%.4f",list.get(i).getArea_afforestation_horticulture_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getWater_created_renovated_tar()==null?"0":list.get(i).getWater_created_renovated_tar().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getWater_created_renovated_achie()==null?"0":list.get(i).getWater_created_renovated_achie().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFarmer_benefitte_tar()==null?"0":list.get(i).getFarmer_benefitte_tar().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFarmer_benefitte_achie()==null?"0":list.get(i).getFarmer_benefitte_achie() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, list.get(i).getProtective_irrigation_tar()==null?"": String.format(Locale.US, "%.4f",list.get(i).getProtective_irrigation_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProtective_irrigation_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getProtective_irrigation_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMandays_generated_tar()==null?"0":list.get(i).getMandays_generated_tar().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMandays_generated_achie()==null?"0":list.get(i).getMandays_generated_achie().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, list.get(i).getArea_diversified_crops_tar()==null?"": String.format(Locale.US, "%.4f",list.get(i).getArea_diversified_crops_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_diversified_crops_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getArea_diversified_crops_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_nilsingto_doubmulcrop_tar()==null?"": String.format(Locale.US, "%.4f",list.get(i).getArea_nilsingto_doubmulcrop_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getArea_nilsingto_doubmulcrop_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getArea_nilsingto_doubmulcrop_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, list.get(i).getIncrease_croparea_tar()==null?"": String.format(Locale.US, "%.4f",list.get(i).getIncrease_croparea_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getIncrease_croparea_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getIncrease_croparea_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getIncrease_farmerincome_tar()==null?"": String.format(Locale.US, "%.4f",list.get(i).getIncrease_farmerincome_tar()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getIncrease_farmerincome_achie()==null?"":  String.format(Locale.US, "%.4f",list.get(i).getIncrease_farmerincome_achie()), Element.ALIGN_RIGHT, 1, 1, bf8);
*/
						k=k+1;
						
						areaof_degraded_land_tar=areaof_degraded_land_tar.add(list.get(i).getAreaof_degraded_land_tar()==null?BigDecimal.ZERO:list.get(i).getAreaof_degraded_land_tar());
						areaof_degraded_land_achie=areaof_degraded_land_achie.add(list.get(i).getAreaof_degraded_land_achie()==null?BigDecimal.ZERO:list.get(i).getAreaof_degraded_land_achie());
						
						area_soilmoisture_activities_tar=area_soilmoisture_activities_tar.add(list.get(i).getArea_soilmoisture_activities_tar()==null?BigDecimal.ZERO:list.get(i).getArea_soilmoisture_activities_tar());
						area_soilmoisture_activities_achie=area_soilmoisture_activities_achie.add(list.get(i).getArea_soilmoisture_activities_achie()==null?BigDecimal.ZERO:list.get(i).getArea_soilmoisture_activities_achie());
						
						area_afforestation_horticulture_tar=area_afforestation_horticulture_tar.add(list.get(i).getArea_afforestation_horticulture_tar()==null?BigDecimal.ZERO:list.get(i).getArea_afforestation_horticulture_tar());
						area_afforestation_horticulture_achie=area_afforestation_horticulture_achie.add(list.get(i).getArea_afforestation_horticulture_achie()==null?BigDecimal.ZERO:list.get(i).getArea_afforestation_horticulture_achie());
						
						water_created_renovated_tar=water_created_renovated_tar.add(list.get(i).getWater_created_renovated_tar()==null?BigDecimal.ZERO:list.get(i).getWater_created_renovated_tar());
						water_created_renovated_achie=water_created_renovated_achie.add(list.get(i).getWater_created_renovated_achie()==null?BigDecimal.ZERO:list.get(i).getWater_created_renovated_achie());
						
						farmer_benefitte_tar=farmer_benefitte_tar.add(list.get(i).getFarmer_benefitte_tar()==null?BigDecimal.ZERO:list.get(i).getFarmer_benefitte_tar());
						farmer_benefitte_achie=farmer_benefitte_achie.add(list.get(i).getFarmer_benefitte_achie()==null?BigDecimal.ZERO:list.get(i).getFarmer_benefitte_achie());
						
						protective_irrigation_tar=protective_irrigation_tar.add(list.get(i).getProtective_irrigation_tar()==null?BigDecimal.ZERO:list.get(i).getProtective_irrigation_tar());
						protective_irrigation_achie=protective_irrigation_achie.add(list.get(i).getProtective_irrigation_achie()==null?BigDecimal.ZERO:list.get(i).getProtective_irrigation_achie());
						mandays_generated_tar=mandays_generated_tar.add(list.get(i).getMandays_generated_tar()==null?BigDecimal.ZERO:list.get(i).getMandays_generated_tar());
						mandays_generated_achie=mandays_generated_achie.add(list.get(i).getMandays_generated_achie()==null?BigDecimal.ZERO:list.get(i).getMandays_generated_achie());
						
						area_diversified_crops_tar=area_diversified_crops_tar.add(list.get(i).getArea_diversified_crops_tar()==null?BigDecimal.ZERO:list.get(i).getArea_diversified_crops_tar());
						area_diversified_crops_achie=area_diversified_crops_achie.add(list.get(i).getArea_diversified_crops_achie()==null?BigDecimal.ZERO:list.get(i).getArea_diversified_crops_achie());
						area_nilsingto_doubmulcrop_tar=area_nilsingto_doubmulcrop_tar.add(list.get(i).getArea_nilsingto_doubmulcrop_tar()==null?BigDecimal.ZERO:list.get(i).getArea_nilsingto_doubmulcrop_tar());
						area_nilsingto_doubmulcrop_achie=area_nilsingto_doubmulcrop_achie.add(list.get(i).getArea_nilsingto_doubmulcrop_achie()==null?BigDecimal.ZERO:list.get(i).getArea_nilsingto_doubmulcrop_achie());
						
						increase_croparea_tar=increase_croparea_tar.add(list.get(i).getIncrease_croparea_tar()==null?BigDecimal.ZERO:list.get(i).getIncrease_croparea_tar());
						increase_croparea_achie=increase_croparea_achie.add(list.get(i).getIncrease_croparea_achie()==null?BigDecimal.ZERO:list.get(i).getIncrease_croparea_achie());
						increase_farmerincome_tar=increase_farmerincome_tar.add(list.get(i).getIncrease_farmerincome_tar()==null?BigDecimal.ZERO:list.get(i).getIncrease_farmerincome_tar());
						increase_farmerincome_achie=increase_farmerincome_achie.add(list.get(i).getIncrease_farmerincome_achie()==null?BigDecimal.ZERO:list.get(i).getIncrease_farmerincome_achie());

					}
				
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(areaof_degraded_land_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_soilmoisture_activities_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_afforestation_horticulture_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(water_created_renovated_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(farmer_benefitte_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(mandays_generated_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_diversified_crops_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_nilsingto_doubmulcrop_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(increase_croparea_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					BigDecimal vart= new BigDecimal(noofdist);
					BigDecimal vara= new BigDecimal(noofdist);
					CommonFunctions.insertCell3(table, String.valueOf(increase_farmerincome_achie.divide(vara, 4, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
			/*		CommonFunctions.insertCell3(table, String.valueOf(areaof_degraded_land_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(areaof_degraded_land_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_soilmoisture_activities_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_soilmoisture_activities_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_afforestation_horticulture_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_afforestation_horticulture_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(water_created_renovated_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(water_created_renovated_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(farmer_benefitte_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(farmer_benefitte_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(mandays_generated_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(mandays_generated_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_diversified_crops_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_diversified_crops_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_nilsingto_doubmulcrop_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(area_nilsingto_doubmulcrop_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(increase_croparea_tar), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(increase_croparea_achie), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					BigDecimal vart= new BigDecimal(30);
					BigDecimal vara= new BigDecimal(30);
					CommonFunctions.insertCell3(table, String.valueOf(increase_farmerincome_tar.divide(vart, 4, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(increase_farmerincome_achie.divide(vara, 4, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					*/
				//	CommonFunctions.insertCell3(table, "", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				//	CommonFunctions.insertCell3(table, "", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 13, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=O9 Report_District-wise.pdf");
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
