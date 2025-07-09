package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaBean;
import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaService;
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
	        document.addTitle("O12");
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

	        paragraph3 = new Paragraph("Report O12- OOMF State and Activities wise Project Reported Status for Financal Year "+finyr +" and Month "+month , f3);

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
	      //  table.setHeaderRows(3);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area of Degraded Land Covered/Rainfed Area Developed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area covered with soil and moisture conservation activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project submitted Area Brought under Plantation (Afforestation/Horticulture)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
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
	        document.addTitle("O12");
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

	        paragraph3 = new Paragraph("Report O13- State and Activities Wise OOMF Before Pushing Data for Financal Year "+finyr +" and Month "+month , f3);

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
	      //  table.setHeaderRows(3);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area of Degraded Land Covered/Rainfed Area Developed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area covered with soil and moisture conservation activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area Brought under Plantation (Afforestation/Horticulture)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Water Harvesting Structure (created/renovated)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
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
	
	
}
