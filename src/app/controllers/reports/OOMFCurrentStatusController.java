package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import app.bean.reports.OOMFCurrentStatusBean;
import app.common.CommonFunctions;
import app.service.reports.OOMFService;

@Controller("OOMFCurrentStatusController")
public class OOMFCurrentStatusController {
	
	@Autowired
	OOMFService ser;
	
	@RequestMapping(value="/getOOMFCurrentStatusReport", method = RequestMethod.GET)
	public ModelAndView getOOMFCurrentStatusReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		List<OOMFCurrentStatusBean> data = new ArrayList<OOMFCurrentStatusBean>();
		try {
			
			mav = new ModelAndView("reports/oomfCurrentRepotedProject");
			
			String finyr=ser.getOOMFFinYear();
			String month=ser.getOOMFFinyearMonth();
			
			data=ser.getOOMFCurrentStatusReport();
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			mav.addObject("finyr",finyr);
			mav.addObject("month",month);
			
			int totaldistt=0, totalproject1=0, degraded_land_proj_no1=0, soilmoisture_proj_no1=0, afforestation_horticulture_proj_no1=0, water_harvest_proj_no1=0,
					farmer_benefitte_proj_no1=0, protective_irrigation_proj_no1=0, mandays_generated_proj_no1=0, halfyearfill1=0, yearwisefill1=0;
			
			
			if(data != null) 
			{
				for(OOMFCurrentStatusBean bean : data) 
				{
					totaldistt=totaldistt+bean.getTotaldist();
					totalproject1=totalproject1+bean.getTotalproject();
					degraded_land_proj_no1=degraded_land_proj_no1+bean.getDegraded_land_proj_no();
					soilmoisture_proj_no1=soilmoisture_proj_no1+bean.getSoilmoisture_proj_no();
					afforestation_horticulture_proj_no1=afforestation_horticulture_proj_no1+bean.getAfforestation_horticulture_proj_no();
					water_harvest_proj_no1=water_harvest_proj_no1+bean.getWater_harvest_proj_no();
					farmer_benefitte_proj_no1=farmer_benefitte_proj_no1+bean.getFarmer_benefitte_proj_no();
					protective_irrigation_proj_no1=protective_irrigation_proj_no1+bean.getProtective_irrigation_proj_no();
					mandays_generated_proj_no1=mandays_generated_proj_no1+bean.getMandays_generated_proj_no();
					halfyearfill1=halfyearfill1+bean.getHalfyearfill();
					yearwisefill1=yearwisefill1+bean.getYearwisefill();
				}
			}	
			mav.addObject("totaldistt",totaldistt);
			mav.addObject("totalproject1",totalproject1);
			mav.addObject("degraded_land_proj_no1",degraded_land_proj_no1);
			mav.addObject("soilmoisture_proj_no1",soilmoisture_proj_no1);
			mav.addObject("afforestation_horticulture_proj_no1",afforestation_horticulture_proj_no1);
			mav.addObject("water_harvest_proj_no1",water_harvest_proj_no1);
			mav.addObject("farmer_benefitte_proj_no1",farmer_benefitte_proj_no1);
			mav.addObject("protective_irrigation_proj_no1",protective_irrigation_proj_no1);
			mav.addObject("mandays_generated_proj_no1",mandays_generated_proj_no1);
			mav.addObject("halfyearfill1",halfyearfill1);
			mav.addObject("yearwisefill1",yearwisefill1);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	} 
	
	@RequestMapping(value="/getDistOOMFCurrentStatusReport", method = RequestMethod.GET)
	public ModelAndView getDistOOMFCurrentStatusReport(HttpServletRequest request, HttpServletResponse response)
	{
		//
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
		ModelAndView mav = new ModelAndView();
		
		List<OOMFCurrentStatusBean> list = new ArrayList<OOMFCurrentStatusBean>();
		try {
			
			mav = new ModelAndView("reports/oomfCurrentDistReport");
			
			String finyr=ser.getOOMFFinYear();
			String month=ser.getOOMFFinyearMonth();
			
			list=ser.getDistOOMFCurrentStatusReport(Integer.parseInt(stcd));
			mav.addObject("dataList",list);
			mav.addObject("dataListSize",list.size());
			mav.addObject("stcd",stcd);
			mav.addObject("stName",stName);
			mav.addObject("distName",distName);
			mav.addObject("finyr",finyr);
			mav.addObject("month",month);
			
			int totalproject1=0, degraded_land_proj_no1=0, soilmoisture_proj_no1=0, afforestation_horticulture_proj_no1=0, water_harvest_proj_no1=0,
					farmer_benefitte_proj_no1=0, protective_irrigation_proj_no1=0, mandays_generated_proj_no1=0, halfyearfill1=0, yearwisefill1=0;
			
			
			if(list != null) 
			{
				for(OOMFCurrentStatusBean bean : list) 
				{
					totalproject1=totalproject1+bean.getTotalproject();
					degraded_land_proj_no1=degraded_land_proj_no1+bean.getDegraded_land_proj_no();
					soilmoisture_proj_no1=soilmoisture_proj_no1+bean.getSoilmoisture_proj_no();
					afforestation_horticulture_proj_no1=afforestation_horticulture_proj_no1+bean.getAfforestation_horticulture_proj_no();
					water_harvest_proj_no1=water_harvest_proj_no1+bean.getWater_harvest_proj_no();
					farmer_benefitte_proj_no1=farmer_benefitte_proj_no1+bean.getFarmer_benefitte_proj_no();
					protective_irrigation_proj_no1=protective_irrigation_proj_no1+bean.getProtective_irrigation_proj_no();
					mandays_generated_proj_no1=mandays_generated_proj_no1+bean.getMandays_generated_proj_no();
					halfyearfill1=halfyearfill1+bean.getHalfyearfill();
					yearwisefill1=yearwisefill1+bean.getYearwisefill();
				}
			}	
			mav.addObject("totalproject1",totalproject1);
			mav.addObject("degraded_land_proj_no1",degraded_land_proj_no1);
			mav.addObject("soilmoisture_proj_no1",soilmoisture_proj_no1);
			mav.addObject("afforestation_horticulture_proj_no1",afforestation_horticulture_proj_no1);
			mav.addObject("water_harvest_proj_no1",water_harvest_proj_no1);
			mav.addObject("farmer_benefitte_proj_no1",farmer_benefitte_proj_no1);
			mav.addObject("protective_irrigation_proj_no1",protective_irrigation_proj_no1);
			mav.addObject("mandays_generated_proj_no1",mandays_generated_proj_no1);
			mav.addObject("halfyearfill1",halfyearfill1);
			mav.addObject("yearwisefill1",yearwisefill1);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value="/getProjOOMFCurrentStatusReport", method = RequestMethod.GET)
	public ModelAndView getProjOOMFCurrentStatusReport(HttpServletRequest request, HttpServletResponse response)
	{
		String dcode = request.getParameter("dcode");
		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
		ModelAndView mav = new ModelAndView(); 
		
		List<OOMFCurrentStatusBean> list = new ArrayList<OOMFCurrentStatusBean>();
		try {
			
			mav = new ModelAndView("reports/oomfCurrentProjReport");
			
			String finyr=ser.getOOMFFinYear();
			String month=ser.getOOMFFinyearMonth();
			
			list=ser.getprojOOMFCurrentStatusReport(Integer.parseInt(dcode));
			mav.addObject("dataList",list);
			mav.addObject("dataListSize",list.size());
			mav.addObject("dcode",dcode);
			mav.addObject("stName",stName);
			mav.addObject("distName",distName);
			mav.addObject("finyr",finyr);
			mav.addObject("month",month);
			
			int totalproject1=0, degraded_land_proj_no1=0, soilmoisture_proj_no1=0, afforestation_horticulture_proj_no1=0, water_harvest_proj_no1=0,
					farmer_benefitte_proj_no1=0, protective_irrigation_proj_no1=0, mandays_generated_proj_no1=0, halfyearfill1=0, yearwisefill1=0;
			
			
			if(list != null) 
			{
				for(OOMFCurrentStatusBean bean : list) 
				{
					totalproject1=totalproject1+bean.getTotalproject();
					degraded_land_proj_no1=degraded_land_proj_no1+bean.getDegraded_land_proj_no();
					soilmoisture_proj_no1=soilmoisture_proj_no1+bean.getSoilmoisture_proj_no();
					afforestation_horticulture_proj_no1=afforestation_horticulture_proj_no1+bean.getAfforestation_horticulture_proj_no();
					water_harvest_proj_no1=water_harvest_proj_no1+bean.getWater_harvest_proj_no();
					farmer_benefitte_proj_no1=farmer_benefitte_proj_no1+bean.getFarmer_benefitte_proj_no();
					protective_irrigation_proj_no1=protective_irrigation_proj_no1+bean.getProtective_irrigation_proj_no();
					mandays_generated_proj_no1=mandays_generated_proj_no1+bean.getMandays_generated_proj_no();
					halfyearfill1=halfyearfill1+bean.getHalfyearfill();
					yearwisefill1=yearwisefill1+bean.getYearwisefill();
				}
			}	
			mav.addObject("totalproject1",totalproject1);
			mav.addObject("degraded_land_proj_no1",degraded_land_proj_no1);
			mav.addObject("soilmoisture_proj_no1",soilmoisture_proj_no1);
			mav.addObject("afforestation_horticulture_proj_no1",afforestation_horticulture_proj_no1);
			mav.addObject("water_harvest_proj_no1",water_harvest_proj_no1);
			mav.addObject("farmer_benefitte_proj_no1",farmer_benefitte_proj_no1);
			mav.addObject("protective_irrigation_proj_no1",protective_irrigation_proj_no1);
			mav.addObject("mandays_generated_proj_no1",mandays_generated_proj_no1);
			mav.addObject("halfyearfill1",halfyearfill1);
			mav.addObject("yearwisefill1",yearwisefill1);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value="/getOOMFBeforePrayashData", method = RequestMethod.GET)
	public ModelAndView getOOMFBeforePrayashData(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		List<OOMFCurrentStatusBean> data = new ArrayList<OOMFCurrentStatusBean>();
		try {
			
			mav = new ModelAndView("reports/oomfBeforePrayashData");
			
			String finyr=ser.getOOMFFinYear();
			String month=ser.getOOMFFinyearMonth();
			
			data=ser.getOOMFBeforePrayashData();
			
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			mav.addObject("finyr",finyr);
			mav.addObject("month",month);
			
			BigDecimal degraded_landt = BigDecimal.valueOf(0);
			BigDecimal soilmoisturet = BigDecimal.valueOf(0);
			BigDecimal afforestation_horticulturet=BigDecimal.valueOf(0);
			BigDecimal water_harvestt = BigDecimal.valueOf(0);
			BigDecimal farmer_benefittet = BigDecimal.valueOf(0);
			BigDecimal protective_irrigationt = BigDecimal.valueOf(0);
			BigDecimal mandays_generatedt = BigDecimal.valueOf(0);
			
			if(data != null) 
			{
				for(OOMFCurrentStatusBean bean : data) 
				{
					degraded_landt=degraded_landt.add(bean.getDegraded_land());
					soilmoisturet=soilmoisturet.add(bean.getSoilmoisture());
					afforestation_horticulturet=afforestation_horticulturet.add(bean.getAfforestation_horticulture());
					water_harvestt=water_harvestt.add(bean.getWater_harvest());
					farmer_benefittet=farmer_benefittet.add(bean.getFarmer_benefitte());
					protective_irrigationt=protective_irrigationt.add(bean.getProtective_irrigation());
					mandays_generatedt=mandays_generatedt.add(bean.getMandays_generated());
				}
			}	
			mav.addObject("degraded_landt",degraded_landt);
			mav.addObject("soilmoisturet",soilmoisturet);
			mav.addObject("afforestation_horticulturet",afforestation_horticulturet);
			mav.addObject("water_harvestt",water_harvestt);
			mav.addObject("farmer_benefittet",farmer_benefittet);
			mav.addObject("protective_irrigationt",protective_irrigationt);
			mav.addObject("mandays_generatedt",mandays_generatedt);
			
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	} 
	
	@RequestMapping(value="/getDistOOMFBeforePrayashData", method = RequestMethod.GET)
	public ModelAndView getDistOOMFBeforePrayashData(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		List<OOMFCurrentStatusBean> data = new ArrayList<OOMFCurrentStatusBean>();
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
//		String distName = request.getParameter("distName");
		
		try {
			
			mav = new ModelAndView("reports/distOOMFBeforePrayashData");
			
			String finyr=ser.getOOMFFinYear();
			String month=ser.getOOMFFinyearMonth();
			
			data=ser.getDistOOMFBeforePrayashData(Integer.parseInt(stcd));
			
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			mav.addObject("finyr",finyr);
			mav.addObject("month",month);
			mav.addObject("stcd",stcd);
			mav.addObject("stName",stName);
//			mav.addObject("distName",distName);
			
			BigDecimal degraded_landt = BigDecimal.valueOf(0);
			BigDecimal soilmoisturet = BigDecimal.valueOf(0);
			BigDecimal afforestation_horticulturet=BigDecimal.valueOf(0);
			BigDecimal water_harvestt = BigDecimal.valueOf(0);
			BigDecimal farmer_benefittet = BigDecimal.valueOf(0);
			BigDecimal protective_irrigationt = BigDecimal.valueOf(0);
			BigDecimal mandays_generatedt = BigDecimal.valueOf(0);
			
			if(data != null) 
			{
				for(OOMFCurrentStatusBean bean : data) 
				{
					degraded_landt=degraded_landt.add(bean.getDegraded_land());
					soilmoisturet=soilmoisturet.add(bean.getSoilmoisture());
					afforestation_horticulturet=afforestation_horticulturet.add(bean.getAfforestation_horticulture());
					water_harvestt=water_harvestt.add(bean.getWater_harvest());
					farmer_benefittet=farmer_benefittet.add(bean.getFarmer_benefitte());
					protective_irrigationt=protective_irrigationt.add(bean.getProtective_irrigation());
					mandays_generatedt=mandays_generatedt.add(bean.getMandays_generated());
				}
			}	
			mav.addObject("degraded_landt",degraded_landt);
			mav.addObject("soilmoisturet",soilmoisturet);
			mav.addObject("afforestation_horticulturet",afforestation_horticulturet);
			mav.addObject("water_harvestt",water_harvestt);
			mav.addObject("farmer_benefittet",farmer_benefittet);
			mav.addObject("protective_irrigationt",protective_irrigationt);
			mav.addObject("mandays_generatedt",mandays_generatedt);
			
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value="/getProjOOMFBeforePrayashData", method = RequestMethod.GET)
	public ModelAndView getProjOOMFBeforePrayashData(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		List<OOMFCurrentStatusBean> data = new ArrayList<OOMFCurrentStatusBean>();
		String dcode = request.getParameter("dcode");
		String distName = request.getParameter("distName");
		String stName = request.getParameter("stName");
		
		try {
			
			mav = new ModelAndView("reports/projOOMFBeforePrayashData");
			
			String finyr=ser.getOOMFFinYear();
			String month=ser.getOOMFFinyearMonth();
			
			data=ser.getProjOOMFBeforePrayashData(Integer.parseInt(dcode));
			
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			mav.addObject("finyr",finyr);
			mav.addObject("month",month);
			mav.addObject("dcode",dcode);
			mav.addObject("distName",distName);
			mav.addObject("stName",stName);
			
			BigDecimal degraded_landt = BigDecimal.valueOf(0);
			BigDecimal soilmoisturet = BigDecimal.valueOf(0);
			BigDecimal afforestation_horticulturet=BigDecimal.valueOf(0);
			BigDecimal water_harvestt = BigDecimal.valueOf(0);
			BigDecimal farmer_benefittet = BigDecimal.valueOf(0);
			BigDecimal protective_irrigationt = BigDecimal.valueOf(0);
			BigDecimal mandays_generatedt = BigDecimal.valueOf(0);
			
			if(data != null) 
			{
				for(OOMFCurrentStatusBean bean : data) 
				{
					degraded_landt=degraded_landt.add(bean.getDegraded_land());
					soilmoisturet=soilmoisturet.add(bean.getSoilmoisture());
					afforestation_horticulturet=afforestation_horticulturet.add(bean.getAfforestation_horticulture());
					water_harvestt=water_harvestt.add(bean.getWater_harvest());
					farmer_benefittet=farmer_benefittet.add(bean.getFarmer_benefitte());
					protective_irrigationt=protective_irrigationt.add(bean.getProtective_irrigation());
					mandays_generatedt=mandays_generatedt.add(bean.getMandays_generated());
				}
			}	
			mav.addObject("degraded_landt",degraded_landt);
			mav.addObject("soilmoisturet",soilmoisturet);
			mav.addObject("afforestation_horticulturet",afforestation_horticulturet);
			mav.addObject("water_harvestt",water_harvestt);
			mav.addObject("farmer_benefittet",farmer_benefittet);
			mav.addObject("protective_irrigationt",protective_irrigationt);
			mav.addObject("mandays_generatedt",mandays_generatedt);
			
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	

	@RequestMapping(value = "/getOOMFCurrentStatusReportPDF", method = RequestMethod.POST)
	public ModelAndView getOOMFCurrentStatusReportPDF(HttpServletRequest request, HttpServletResponse response) {
		
	  
	    try {
	    	
	    	List<OOMFCurrentStatusBean> data = new ArrayList<OOMFCurrentStatusBean>();
	    	
	    	String finyr=ser.getOOMFFinYear();
			String month=ser.getOOMFFinyearMonth();
			
			data=ser.getOOMFCurrentStatusReport();
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME-6");
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

	        paragraph3 = new Paragraph("Report ME6 -State and Activities wise No. of Project Achievement Status for the Financial Year "+finyr +" and Month "+month , f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(14);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(2);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area of Degraded Land Covered/Rainfed Area Developed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area covered with soil and moisture conservation activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area Brought under Plantation (Afforestation/Horticulture)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted No. of Water Harvesting Structure (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted No. of Farmers Benefited", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area Brought under Protective Irrigation (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted No. of man-days generated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Half Year Wise Additional area brought under diversified crops/change in cropping system", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Half Year Wise  Area brought from no crop/single crop to single/multiple crop", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Year Wise Increase in Cropped Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Increase in Farmer's Income", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
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
	        
	        int totaldistt=0, totalproject1=0, degraded_land_proj_no1=0, soilmoisture_proj_no1=0, afforestation_horticulture_proj_no1=0, water_harvest_proj_no1=0,
					farmer_benefitte_proj_no1=0, protective_irrigation_proj_no1=0, mandays_generated_proj_no1=0, halfyearfill1=0, yearwisefill1=0;
			
			int k = 1;
			if(data != null) 
			{
				for(OOMFCurrentStatusBean bean : data) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, bean.getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getTotalproject()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getDegraded_land_proj_no()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getSoilmoisture_proj_no()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getAfforestation_horticulture_proj_no()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getWater_harvest_proj_no()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                CommonFunctions.insertCell(table, String.valueOf(bean.getFarmer_benefitte_proj_no()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProtective_irrigation_proj_no()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getMandays_generated_proj_no()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getHalfyearfill()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getHalfyearfill()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getYearwisefill()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getYearwisefill()), Element.ALIGN_RIGHT, 1,1, bf8);
	                
	                totaldistt=totaldistt+bean.getTotaldist();
					totalproject1=totalproject1+bean.getTotalproject();
					degraded_land_proj_no1=degraded_land_proj_no1+bean.getDegraded_land_proj_no();
					soilmoisture_proj_no1=soilmoisture_proj_no1+bean.getSoilmoisture_proj_no();
					afforestation_horticulture_proj_no1=afforestation_horticulture_proj_no1+bean.getAfforestation_horticulture_proj_no();
					water_harvest_proj_no1=water_harvest_proj_no1+bean.getWater_harvest_proj_no();
					farmer_benefitte_proj_no1=farmer_benefitte_proj_no1+bean.getFarmer_benefitte_proj_no();
					protective_irrigation_proj_no1=protective_irrigation_proj_no1+bean.getProtective_irrigation_proj_no();
					mandays_generated_proj_no1=mandays_generated_proj_no1+bean.getMandays_generated_proj_no();
					halfyearfill1=halfyearfill1+bean.getHalfyearfill();
					yearwisefill1=yearwisefill1+bean.getYearwisefill();
					
					k = k + 1;
					
				}
			}	

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totalproject1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(degraded_land_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(soilmoisture_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(afforestation_horticulture_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(water_harvest_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(farmer_benefitte_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(mandays_generated_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(halfyearfill1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(halfyearfill1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(yearwisefill1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(yearwisefill1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

	            document.add(table);
	            table = new PdfPTable(1);
				table.setWidthPercentage(70);
				table.setSpacingBefore(15f);
				table.setSpacingAfter(0f);
				CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
				CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
				document.add(table);
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=ME6.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/getOOMFCurrentStatusReportExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getOOMFCurrentStatusReportExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String finyr=ser.getOOMFFinYear();
		String month=ser.getOOMFFinyearMonth();
		
		List<OOMFCurrentStatusBean> list = new ArrayList<OOMFCurrentStatusBean>();
		
		list = ser.getOOMFCurrentStatusReport();
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME6 -State and Activities wise Project Achievement Status for the Financial Year");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
		
		String rptName = "Report ME6 -State and Activities wise Project Achievement Status for the Financial Year "+finyr +" and Month "+month;
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 13, areaAmtValDetail, workbook);
		
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
		cell.setCellValue("Total No. of Project");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total No. of Project submitted Area of Degraded Land Covered/Rainfed Area Developed");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Total No. of Project submitted Area covered with soil and moisture conservation activities");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Total No. of Project submitted Area Brought under Plantation (Afforestation/Horticulture)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Total No. of Project submitted No. of Water Harvesting Structure (created/renovated)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total No. of Project submitted No. of Farmers Benefited");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Total No. of Project submitted Area Brought under Protective Irrigation (created/renovated)");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Total No. of Project submitted No. of man-days generated");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Total No. of Project submitted Half Year Wise Additional area brought under diversified crops/change in cropping system");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("Total No. of Project submitted Half Year Wise Area brought from no crop/single crop to single/multiple crop");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(12);
		cell.setCellValue("Total No. of Project submitted Year Wise Increase in Cropped Area");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(13);
		
		cell.setCellValue("Total No. of Project submitted Increase in Farmer's Income");
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<14;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 7;
		int totaldistt=0, totalproject1=0, degraded_land_proj_no1=0, soilmoisture_proj_no1=0, afforestation_horticulture_proj_no1=0, water_harvest_proj_no1=0,
			farmer_benefitte_proj_no1=0, protective_irrigation_proj_no1=0, mandays_generated_proj_no1=0, halfyearfill1=0, yearwisefill1=0;
		
		
		
	    for(OOMFCurrentStatusBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getSt_name());
	    	row.createCell(2).setCellValue(bean.getTotalproject());
	    	row.createCell(3).setCellValue(bean.getDegraded_land_proj_no());
	    	row.createCell(4).setCellValue(bean.getSoilmoisture_proj_no());
	    	row.createCell(5).setCellValue(bean.getAfforestation_horticulture_proj_no());
	    	row.createCell(6).setCellValue(bean.getWater_harvest_proj_no());
	    	row.createCell(7).setCellValue(bean.getFarmer_benefitte_proj_no());
	    	row.createCell(8).setCellValue(bean.getProtective_irrigation_proj_no());
	    	row.createCell(9).setCellValue(bean.getMandays_generated_proj_no());
	    	row.createCell(10).setCellValue(bean.getHalfyearfill());
	    	row.createCell(11).setCellValue(bean.getHalfyearfill());
	    	row.createCell(12).setCellValue(bean.getYearwisefill());
	    	row.createCell(13).setCellValue(bean.getYearwisefill());
	    	
	    	
	    	totaldistt=totaldistt+bean.getTotaldist();
			totalproject1=totalproject1+bean.getTotalproject();
			degraded_land_proj_no1=degraded_land_proj_no1+bean.getDegraded_land_proj_no();
			soilmoisture_proj_no1=soilmoisture_proj_no1+bean.getSoilmoisture_proj_no();
			afforestation_horticulture_proj_no1=afforestation_horticulture_proj_no1+bean.getAfforestation_horticulture_proj_no();
			water_harvest_proj_no1=water_harvest_proj_no1+bean.getWater_harvest_proj_no();
			farmer_benefitte_proj_no1=farmer_benefitte_proj_no1+bean.getFarmer_benefitte_proj_no();
			protective_irrigation_proj_no1=protective_irrigation_proj_no1+bean.getProtective_irrigation_proj_no();
			mandays_generated_proj_no1=mandays_generated_proj_no1+bean.getMandays_generated_proj_no();
			halfyearfill1=halfyearfill1+bean.getHalfyearfill();
			yearwisefill1=yearwisefill1+bean.getYearwisefill();
			
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
		cell.setCellValue(totalproject1);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(degraded_land_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(soilmoisture_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(afforestation_horticulture_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(water_harvest_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(farmer_benefitte_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(protective_irrigation_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(mandays_generated_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(halfyearfill1);
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(halfyearfill1);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(yearwisefill1);
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(yearwisefill1);
		cell.setCellStyle(style1);
		

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 13);
	    String fileName = "attachment; filename=Report ME6- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/oomfCurrentRepotedProject";
	}
	
	@RequestMapping(value = "/getOOMFCurrentStatusDistrictReportPDF", method = RequestMethod.POST)
	public ModelAndView getOOMFCurrentStatusDistrictReportPDF(HttpServletRequest request, HttpServletResponse response) {
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
	  
	    try {
	    	
	    	List<OOMFCurrentStatusBean> data = new ArrayList<OOMFCurrentStatusBean>();
	    	
	    	String finyr=ser.getOOMFFinYear();
			String month=ser.getOOMFFinyearMonth();
			
			data=ser.getDistOOMFCurrentStatusReport(Integer.parseInt(stcd));
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME-6");
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

	        paragraph3 = new Paragraph("Report ME6 -District and Activities wise No. of Project Achievement Status for the Financial Year "+finyr +" and Month "+ month+ " for State " + " '"+stName+"' " , f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(14);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(2);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area of Degraded Land Covered/Rainfed Area Developed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area covered with soil and moisture conservation activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area Brought under Plantation (Afforestation/Horticulture)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted No. of Water Harvesting Structure (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted No. of Farmers Benefited", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area Brought under Protective Irrigation (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted No. of man-days generated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Half Year Wise Additional area brought under diversified crops/change in cropping system", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Half Year Wise  Area brought from no crop/single crop to single/multiple crop", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Year Wise Increase in Cropped Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Increase in Farmer`s Income", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
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
	        
	        int totaldistt=0, totalproject1=0, degraded_land_proj_no1=0, soilmoisture_proj_no1=0, afforestation_horticulture_proj_no1=0, water_harvest_proj_no1=0,
					farmer_benefitte_proj_no1=0, protective_irrigation_proj_no1=0, mandays_generated_proj_no1=0, halfyearfill1=0, yearwisefill1=0;
			
			int k = 1;
			if(data != null) 
			{
				for(OOMFCurrentStatusBean bean : data) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, bean.getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getTotalproject()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getDegraded_land_proj_no()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getSoilmoisture_proj_no()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getAfforestation_horticulture_proj_no()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getWater_harvest_proj_no()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                CommonFunctions.insertCell(table, String.valueOf(bean.getFarmer_benefitte_proj_no()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProtective_irrigation_proj_no()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getMandays_generated_proj_no()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getHalfyearfill()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getHalfyearfill()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getYearwisefill()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getYearwisefill()), Element.ALIGN_RIGHT, 1,1, bf8);
	                
	                totaldistt=totaldistt+bean.getTotaldist();
					totalproject1=totalproject1+bean.getTotalproject();
					degraded_land_proj_no1=degraded_land_proj_no1+bean.getDegraded_land_proj_no();
					soilmoisture_proj_no1=soilmoisture_proj_no1+bean.getSoilmoisture_proj_no();
					afforestation_horticulture_proj_no1=afforestation_horticulture_proj_no1+bean.getAfforestation_horticulture_proj_no();
					water_harvest_proj_no1=water_harvest_proj_no1+bean.getWater_harvest_proj_no();
					farmer_benefitte_proj_no1=farmer_benefitte_proj_no1+bean.getFarmer_benefitte_proj_no();
					protective_irrigation_proj_no1=protective_irrigation_proj_no1+bean.getProtective_irrigation_proj_no();
					mandays_generated_proj_no1=mandays_generated_proj_no1+bean.getMandays_generated_proj_no();
					halfyearfill1=halfyearfill1+bean.getHalfyearfill();
					yearwisefill1=yearwisefill1+bean.getYearwisefill();
					
					k = k + 1;
					
				}
			}	

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totalproject1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(degraded_land_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(soilmoisture_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(afforestation_horticulture_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(water_harvest_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(farmer_benefitte_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(mandays_generated_proj_no1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(halfyearfill1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(halfyearfill1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(yearwisefill1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(yearwisefill1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

	            document.add(table);
	            table = new PdfPTable(1);
				table.setWidthPercentage(70);
				table.setSpacingBefore(15f);
				table.setSpacingAfter(0f);
				CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
				CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
				document.add(table);
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=DistrictReportME6.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}


//excel

@RequestMapping(value = "/getOOMFCurrentStatusDistrictReportExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getOOMFCurrentStatusDistrictReportExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
	  
		String finyr=ser.getOOMFFinYear();
		String month=ser.getOOMFFinyearMonth();
		
		List<OOMFCurrentStatusBean> list = new ArrayList<OOMFCurrentStatusBean>();
		
		list = ser.getDistOOMFCurrentStatusReport(Integer.parseInt(stcd));
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME6 -State and Activities wise Project Achievement Status for the Financial Year");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
		
		String rptName = "Report ME6 -District and Activities wise Project Achievement Status for the Financial Year "+finyr +" and Month "+month+ " for State  '" +stName +"' ";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 13, areaAmtValDetail, workbook);
		
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
		cell.setCellValue("Total No. of Project submitted Area of Degraded Land Covered/Rainfed Area Developed");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Total No. of Project submitted Area covered with soil and moisture conservation activities");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Total No. of Project submitted Area Brought under Plantation (Afforestation/Horticulture)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Total No. of Project submitted No. of Water Harvesting Structure (created/renovated)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total No. of Project submitted No. of Farmers Benefited");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Total No. of Project submitted Area Brought under Protective Irrigation (created/renovated)");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Total No. of Project submitted No. of man-days generated");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Total No. of Project submitted Half Year Wise Additional area brought under diversified crops/change in cropping system");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("Total No. of Project submitted Half Year Wise Area brought from no crop/single crop to single/multiple crop");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(12);
		cell.setCellValue("Total No. of Project submitted Year Wise Increase in Cropped Area");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(13);
		cell.setCellValue("Total No. of Project submitted Increase in Farmer's Income");
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<14;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 7;
		int totaldistt=0, totalproject1=0, degraded_land_proj_no1=0, soilmoisture_proj_no1=0, afforestation_horticulture_proj_no1=0, water_harvest_proj_no1=0,
			farmer_benefitte_proj_no1=0, protective_irrigation_proj_no1=0, mandays_generated_proj_no1=0, halfyearfill1=0, yearwisefill1=0;
		
		
		
	    for(OOMFCurrentStatusBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getDist_name());
	    	row.createCell(2).setCellValue(bean.getTotalproject());
	    	row.createCell(3).setCellValue(bean.getDegraded_land_proj_no());
	    	row.createCell(4).setCellValue(bean.getSoilmoisture_proj_no());
	    	row.createCell(5).setCellValue(bean.getAfforestation_horticulture_proj_no());
	    	row.createCell(6).setCellValue(bean.getWater_harvest_proj_no());
	    	row.createCell(7).setCellValue(bean.getFarmer_benefitte_proj_no());
	    	row.createCell(8).setCellValue(bean.getProtective_irrigation_proj_no());
	    	row.createCell(9).setCellValue(bean.getMandays_generated_proj_no());
	    	row.createCell(10).setCellValue(bean.getHalfyearfill());
	    	row.createCell(11).setCellValue(bean.getHalfyearfill());
	    	row.createCell(12).setCellValue(bean.getYearwisefill());
	    	row.createCell(13).setCellValue(bean.getYearwisefill());
	    	
	    	
	    	totaldistt=totaldistt+bean.getTotaldist();
			totalproject1=totalproject1+bean.getTotalproject();
			degraded_land_proj_no1=degraded_land_proj_no1+bean.getDegraded_land_proj_no();
			soilmoisture_proj_no1=soilmoisture_proj_no1+bean.getSoilmoisture_proj_no();
			afforestation_horticulture_proj_no1=afforestation_horticulture_proj_no1+bean.getAfforestation_horticulture_proj_no();
			water_harvest_proj_no1=water_harvest_proj_no1+bean.getWater_harvest_proj_no();
			farmer_benefitte_proj_no1=farmer_benefitte_proj_no1+bean.getFarmer_benefitte_proj_no();
			protective_irrigation_proj_no1=protective_irrigation_proj_no1+bean.getProtective_irrigation_proj_no();
			mandays_generated_proj_no1=mandays_generated_proj_no1+bean.getMandays_generated_proj_no();
			halfyearfill1=halfyearfill1+bean.getHalfyearfill();
			yearwisefill1=yearwisefill1+bean.getYearwisefill();
			
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
		cell.setCellValue(totalproject1);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(degraded_land_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(soilmoisture_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(afforestation_horticulture_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(water_harvest_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(farmer_benefitte_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(protective_irrigation_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(mandays_generated_proj_no1);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(halfyearfill1);
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(halfyearfill1);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(yearwisefill1);
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(yearwisefill1);
		cell.setCellStyle(style1);
		

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 13);
	    String fileName = "attachment; filename=Report ME6- District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/oomfCurrentDistReport";
	}
	
	
	@RequestMapping(value = "/getOOMFBeforePrayashDataPDF", method = RequestMethod.POST)
	public ModelAndView getOOMFBeforePrayashDataPDF(HttpServletRequest request, HttpServletResponse response) {
		
	  
	    try {
	    	
	    	List<OOMFCurrentStatusBean> data = new ArrayList<OOMFCurrentStatusBean>();
	    	
	    	String finyr=ser.getOOMFFinYear();
			String month=ser.getOOMFFinyearMonth();
			
			data=ser.getOOMFBeforePrayashData();
			
			BigDecimal degraded_landt = BigDecimal.valueOf(0);
			BigDecimal soilmoisturet = BigDecimal.valueOf(0);
			BigDecimal afforestation_horticulturet=BigDecimal.valueOf(0);
			BigDecimal water_harvestt = BigDecimal.valueOf(0);
			BigDecimal farmer_benefittet = BigDecimal.valueOf(0);
			BigDecimal protective_irrigationt = BigDecimal.valueOf(0);
			BigDecimal mandays_generatedt = BigDecimal.valueOf(0);
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME-7");
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

	        paragraph3 = new Paragraph("Report ME7- State and Activities Wise Current Achievement for the Financial Year "+finyr +" and Month "+month , f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(9);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(2);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area of Degraded Land Covered/Rainfed Area Developed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area covered with soil and moisture conservation activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area Brought under Plantation (Afforestation/Horticulture)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Water Harvesting Structure (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Farmers Benefited", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area Brought under Protective Irrigation (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of man-days generated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
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
			if(data != null) 
			{
				for(OOMFCurrentStatusBean bean : data) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, bean.getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getDegraded_land()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getSoilmoisture()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getAfforestation_horticulture()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getWater_harvest()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getFarmer_benefitte()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProtective_irrigation()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getMandays_generated()), Element.ALIGN_RIGHT, 1,1, bf8);
	                
	                degraded_landt=degraded_landt.add(bean.getDegraded_land());
					soilmoisturet=soilmoisturet.add(bean.getSoilmoisture());
					afforestation_horticulturet=afforestation_horticulturet.add(bean.getAfforestation_horticulture());
					water_harvestt=water_harvestt.add(bean.getWater_harvest());
					farmer_benefittet=farmer_benefittet.add(bean.getFarmer_benefitte());
					protective_irrigationt=protective_irrigationt.add(bean.getProtective_irrigation());
					mandays_generatedt=mandays_generatedt.add(bean.getMandays_generated());
					
					k = k + 1;
					
				}
			}	

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(degraded_landt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(soilmoisturet), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(afforestation_horticulturet), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(water_harvestt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(farmer_benefittet), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(protective_irrigationt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(mandays_generatedt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	           
	            document.add(table);
	            table = new PdfPTable(1);
				table.setWidthPercentage(70);
				table.setSpacingBefore(15f);
				table.setSpacingAfter(0f);
				CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
				CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
				document.add(table);
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=ME7.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/downloadExcelOOMFBeforePrayashData", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelOOMFBeforePrayashData(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String finyr=ser.getOOMFFinYear();
		String month=ser.getOOMFFinyearMonth();
		
		List<OOMFCurrentStatusBean> list = new ArrayList<OOMFCurrentStatusBean>();
		
		list = ser.getOOMFBeforePrayashData();
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME7- State and Activities Wise Current Achievement for the Financial Year");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
		
		String rptName = "Report ME7- State and Activities Wise Current Achievement for the Financial Year "+finyr +" and Month "+month;
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
		
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
		cell.setCellValue("Total Area of Degraded Land Covered/Rainfed Area Developed");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total Area covered with soil and moisture conservation activities");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Total Area Brought under Plantation (Afforestation/Horticulture)");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Total No. of Water Harvesting Structure (created/renovated)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Total No. of Farmers Benefited");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total Area Brought under Protective Irrigation (created/renovated)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Total No. of man-days generated");  
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 7;
		BigDecimal degraded_landt = BigDecimal.ZERO;
		BigDecimal soilmoisturet = BigDecimal.ZERO;
		BigDecimal afforestation_horticulturet=BigDecimal.ZERO;
		BigDecimal water_harvestt = BigDecimal.ZERO;
		BigDecimal farmer_benefittet = BigDecimal.ZERO;
		BigDecimal protective_irrigationt = BigDecimal.ZERO;
		BigDecimal mandays_generatedt = BigDecimal.ZERO;
		
		
	    for(OOMFCurrentStatusBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getSt_name());
	    	row.createCell(2).setCellValue(bean.getDegraded_land().doubleValue());
	    	row.createCell(3).setCellValue(bean.getSoilmoisture().doubleValue());
	    	row.createCell(4).setCellValue(bean.getAfforestation_horticulture().doubleValue());
	    	row.createCell(5).setCellValue(bean.getWater_harvest().doubleValue());
	    	row.createCell(6).setCellValue(bean.getFarmer_benefitte().doubleValue());
	    	row.createCell(7).setCellValue(bean.getProtective_irrigation().doubleValue());
	    	row.createCell(8).setCellValue(bean.getMandays_generated().doubleValue());
	    	
	    	
	    	degraded_landt = degraded_landt.add(bean.getDegraded_land());
	    	soilmoisturet = soilmoisturet.add(bean.getSoilmoisture());
			afforestation_horticulturet = afforestation_horticulturet.add(bean.getAfforestation_horticulture());
			water_harvestt = water_harvestt.add(bean.getWater_harvest());
			farmer_benefittet = farmer_benefittet.add(bean.getFarmer_benefitte());
			protective_irrigationt = protective_irrigationt.add(bean.getProtective_irrigation());
			mandays_generatedt = mandays_generatedt.add(bean.getMandays_generated());
			
			
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
		cell.setCellValue(degraded_landt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(soilmoisturet.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(afforestation_horticulturet.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(water_harvestt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(farmer_benefittet.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(protective_irrigationt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(mandays_generatedt.doubleValue());
		cell.setCellStyle(style1);
		

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 8);
	    String fileName = "attachment; filename=Report ME7- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/oomfBeforePrayashData";
	}
	
	@RequestMapping(value = "/downloadPDFDistOOMFBeforePrayashData", method = RequestMethod.POST)
	public ModelAndView downloadPDFDistOOMFBeforePrayashData(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String finyr=ser.getOOMFFinYear();
		String month=ser.getOOMFFinyearMonth();
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<OOMFCurrentStatusBean> list = new ArrayList<OOMFCurrentStatusBean>();
		
		list = ser.getDistOOMFBeforePrayashData(Integer.parseInt(stcd));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("OOMFBeforePrayashReport");
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
			
				paragraph3 = new Paragraph("Report ME7- District and Activities Wise Current Achievement for the Financial Year "+finyr +" and Month "+month+" for State '"+stName+"'"  , f3);

				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(9);
		        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(2);
							
		        
		        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total Area of Degraded Land Covered/Rainfed Area Developed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total Area covered with soil and moisture conservation activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total Area Brought under Plantation (Afforestation/Horticulture)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total No. of Water Harvesting Structure (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total No. of Farmers Benefited", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total Area Brought under Protective Irrigation (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total No. of man-days generated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
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
				BigDecimal degraded_landt = BigDecimal.ZERO;
				BigDecimal soilmoisturet = BigDecimal.ZERO;
				BigDecimal afforestation_horticulturet=BigDecimal.ZERO;
				BigDecimal water_harvestt = BigDecimal.ZERO;
				BigDecimal farmer_benefittet = BigDecimal.ZERO;
				BigDecimal protective_irrigationt = BigDecimal.ZERO;
				BigDecimal mandays_generatedt = BigDecimal.ZERO;

				
				if(list != null) 
				{
					for(OOMFCurrentStatusBean bean : list) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, bean.getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getDegraded_land()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getSoilmoisture()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getAfforestation_horticulture()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getWater_harvest()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getFarmer_benefitte()), Element.ALIGN_RIGHT, 1,1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getProtective_irrigation()), Element.ALIGN_RIGHT, 1,1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getMandays_generated()), Element.ALIGN_RIGHT, 1,1, bf8);
		                
		                degraded_landt=degraded_landt.add(bean.getDegraded_land());
						soilmoisturet=soilmoisturet.add(bean.getSoilmoisture());
						afforestation_horticulturet=afforestation_horticulturet.add(bean.getAfforestation_horticulture());
						water_harvestt=water_harvestt.add(bean.getWater_harvest());
						farmer_benefittet=farmer_benefittet.add(bean.getFarmer_benefitte());
						protective_irrigationt=protective_irrigationt.add(bean.getProtective_irrigation());
						mandays_generatedt=mandays_generatedt.add(bean.getMandays_generated());
						
						k = k + 1;
						
					}
				}
					
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(degraded_landt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(soilmoisturet), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(afforestation_horticulturet), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(water_harvestt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(farmer_benefittet), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(protective_irrigationt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(mandays_generatedt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	           
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report ME7- District.pdf");
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

	@RequestMapping(value = "/downloadExcelDistOOMFBeforePrayashData", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistOOMFBeforePrayashData(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String finyr=ser.getOOMFFinYear();
		String month=ser.getOOMFFinyearMonth();
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<OOMFCurrentStatusBean> list = new ArrayList<OOMFCurrentStatusBean>();
		
		list = ser.getDistOOMFBeforePrayashData(Integer.parseInt(stcd));
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME7- District and Activities Wise Current Achievement for the Financial Year");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
		
		String rptName = "Report ME7- District and Activities Wise Current Achievement for the Financial Year "+finyr +" and Month "+month+" for State '"+stName+"'";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
		
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
		cell.setCellValue("Total Area of Degraded Land Covered/Rainfed Area Developed");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total Area covered with soil and moisture conservation activities");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Total Area Brought under Plantation (Afforestation/Horticulture)");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Total No. of Water Harvesting Structure (created/renovated)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Total No. of Farmers Benefited");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total Area Brought under Protective Irrigation (created/renovated)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Total No. of man-days generated");  
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 7;
		BigDecimal degraded_landt = BigDecimal.ZERO;
		BigDecimal soilmoisturet = BigDecimal.ZERO;
		BigDecimal afforestation_horticulturet=BigDecimal.ZERO;
		BigDecimal water_harvestt = BigDecimal.ZERO;
		BigDecimal farmer_benefittet = BigDecimal.ZERO;
		BigDecimal protective_irrigationt = BigDecimal.ZERO;
		BigDecimal mandays_generatedt = BigDecimal.ZERO;
		
		
	    for(OOMFCurrentStatusBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getDist_name());
	    	row.createCell(2).setCellValue(bean.getDegraded_land().doubleValue());
	    	row.createCell(3).setCellValue(bean.getSoilmoisture().doubleValue());
	    	row.createCell(4).setCellValue(bean.getAfforestation_horticulture().doubleValue());
	    	row.createCell(5).setCellValue(bean.getWater_harvest().doubleValue());
	    	row.createCell(6).setCellValue(bean.getFarmer_benefitte().doubleValue());
	    	row.createCell(7).setCellValue(bean.getProtective_irrigation().doubleValue());
	    	row.createCell(8).setCellValue(bean.getMandays_generated().doubleValue());
	    	
	    	
	    	degraded_landt = degraded_landt.add(bean.getDegraded_land());
	    	soilmoisturet = soilmoisturet.add(bean.getSoilmoisture());
			afforestation_horticulturet = afforestation_horticulturet.add(bean.getAfforestation_horticulture());
			water_harvestt = water_harvestt.add(bean.getWater_harvest());
			farmer_benefittet = farmer_benefittet.add(bean.getFarmer_benefitte());
			protective_irrigationt = protective_irrigationt.add(bean.getProtective_irrigation());
			mandays_generatedt = mandays_generatedt.add(bean.getMandays_generated());
			
			
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
		cell.setCellValue(degraded_landt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(soilmoisturet.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(afforestation_horticulturet.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(water_harvestt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(farmer_benefittet.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(protective_irrigationt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(mandays_generatedt.doubleValue());
		cell.setCellStyle(style1);
		

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 8);
	    String fileName = "attachment; filename=Report ME7- District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/distOOMFBeforePrayashData";
	}
	
	@RequestMapping(value = "/downloadPDFProjOOMFBeforePrayashData", method = RequestMethod.POST)
	public ModelAndView downloadPDFProjOOMFBeforePrayashData(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String finyr=ser.getOOMFFinYear();
		String month=ser.getOOMFFinyearMonth();
		String dcode = request.getParameter("dcode");
		String distName = request.getParameter("distName");
		String stName = request.getParameter("stName");
		
		List<OOMFCurrentStatusBean> list = new ArrayList<OOMFCurrentStatusBean>();
		
		list = ser.getProjOOMFBeforePrayashData(Integer.parseInt(dcode));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("OOMFBeforePrayashReport");
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
			
				paragraph3 = new Paragraph("Report ME7- Project and Activities Wise Current Achievement for the Financial Year "+finyr +" and Month "+month+" for District '"+distName+"' of State '"+stName+"'"  , f3);

				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(9);
		        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(2);
							
		        
		        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total Area of Degraded Land Covered/Rainfed Area Developed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total Area covered with soil and moisture conservation activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total Area Brought under Plantation (Afforestation/Horticulture)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total No. of Water Harvesting Structure (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total No. of Farmers Benefited", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total Area Brought under Protective Irrigation (created/renovated)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Total No. of man-days generated", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
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
				BigDecimal degraded_landt = BigDecimal.ZERO;
				BigDecimal soilmoisturet = BigDecimal.ZERO;
				BigDecimal afforestation_horticulturet=BigDecimal.ZERO;
				BigDecimal water_harvestt = BigDecimal.ZERO;
				BigDecimal farmer_benefittet = BigDecimal.ZERO;
				BigDecimal protective_irrigationt = BigDecimal.ZERO;
				BigDecimal mandays_generatedt = BigDecimal.ZERO;

				
				if(list != null) 
				{
					for(OOMFCurrentStatusBean bean : list) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, bean.getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getDegraded_land()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getSoilmoisture()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getAfforestation_horticulture()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getWater_harvest()), Element.ALIGN_RIGHT, 1, 1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getFarmer_benefitte()), Element.ALIGN_RIGHT, 1,1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getProtective_irrigation()), Element.ALIGN_RIGHT, 1,1, bf8);
		                CommonFunctions.insertCell(table, String.valueOf(bean.getMandays_generated()), Element.ALIGN_RIGHT, 1,1, bf8);
		                
		                degraded_landt=degraded_landt.add(bean.getDegraded_land());
						soilmoisturet=soilmoisturet.add(bean.getSoilmoisture());
						afforestation_horticulturet=afforestation_horticulturet.add(bean.getAfforestation_horticulture());
						water_harvestt=water_harvestt.add(bean.getWater_harvest());
						farmer_benefittet=farmer_benefittet.add(bean.getFarmer_benefitte());
						protective_irrigationt=protective_irrigationt.add(bean.getProtective_irrigation());
						mandays_generatedt=mandays_generatedt.add(bean.getMandays_generated());
						
						k = k + 1;
						
					}
				}
					
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(degraded_landt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(soilmoisturet), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(afforestation_horticulturet), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(water_harvestt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(farmer_benefittet), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(protective_irrigationt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(mandays_generatedt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	           
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report ME7- Project.pdf");
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

	@RequestMapping(value = "/downloadExcelProjOOMFBeforePrayashData", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjOOMFBeforePrayashData(HttpServletRequest request, HttpServletResponse response) 
	{
		
		String finyr=ser.getOOMFFinYear();
		String month=ser.getOOMFFinyearMonth();
		String dcode = request.getParameter("dcode");
		String distName = request.getParameter("distName");
		String stName = request.getParameter("stName");
		
		List<OOMFCurrentStatusBean> list = new ArrayList<OOMFCurrentStatusBean>();
		
		list = ser.getProjOOMFBeforePrayashData(Integer.parseInt(dcode));
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME7- Project and Activities Wise Current Achievement for the Financial Year");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
		
		String rptName = "Report ME7- Project and Activities Wise Current Achievement for the Financial Year "+finyr +" and Month "+month+" for District '"+distName+"' of State '"+stName+"'";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowhead = sheet.createRow(5);
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Project Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total Area of Degraded Land Covered/Rainfed Area Developed");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total Area covered with soil and moisture conservation activities");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Total Area Brought under Plantation (Afforestation/Horticulture)");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("Total No. of Water Harvesting Structure (created/renovated)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("Total No. of Farmers Benefited");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("Total Area Brought under Protective Irrigation (created/renovated)");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Total No. of man-days generated");  
		cell.setCellStyle(style);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		int sno = 1;
		int rowno  = 7;
		BigDecimal degraded_landt = BigDecimal.ZERO;
		BigDecimal soilmoisturet = BigDecimal.ZERO;
		BigDecimal afforestation_horticulturet=BigDecimal.ZERO;
		BigDecimal water_harvestt = BigDecimal.ZERO;
		BigDecimal farmer_benefittet = BigDecimal.ZERO;
		BigDecimal protective_irrigationt = BigDecimal.ZERO;
		BigDecimal mandays_generatedt = BigDecimal.ZERO;
		
		
	    for(OOMFCurrentStatusBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getProj_name());
	    	row.createCell(2).setCellValue(bean.getDegraded_land().doubleValue());
	    	row.createCell(3).setCellValue(bean.getSoilmoisture().doubleValue());
	    	row.createCell(4).setCellValue(bean.getAfforestation_horticulture().doubleValue());
	    	row.createCell(5).setCellValue(bean.getWater_harvest().doubleValue());
	    	row.createCell(6).setCellValue(bean.getFarmer_benefitte().doubleValue());
	    	row.createCell(7).setCellValue(bean.getProtective_irrigation().doubleValue());
	    	row.createCell(8).setCellValue(bean.getMandays_generated().doubleValue());
	    	
	    	
	    	degraded_landt = degraded_landt.add(bean.getDegraded_land());
	    	soilmoisturet = soilmoisturet.add(bean.getSoilmoisture());
			afforestation_horticulturet = afforestation_horticulturet.add(bean.getAfforestation_horticulture());
			water_harvestt = water_harvestt.add(bean.getWater_harvest());
			farmer_benefittet = farmer_benefittet.add(bean.getFarmer_benefitte());
			protective_irrigationt = protective_irrigationt.add(bean.getProtective_irrigation());
			mandays_generatedt = mandays_generatedt.add(bean.getMandays_generated());
			
			
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
		cell.setCellValue(degraded_landt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(soilmoisturet.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(afforestation_horticulturet.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(water_harvestt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(farmer_benefittet.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(protective_irrigationt.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(mandays_generatedt.doubleValue());
		cell.setCellStyle(style1);
		

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 8);
	    String fileName = "attachment; filename=Report ME7- Project.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/projOOMFBeforePrayashData";
	}
	
}
