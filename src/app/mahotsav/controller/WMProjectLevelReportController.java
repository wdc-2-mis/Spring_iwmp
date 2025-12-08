package app.mahotsav.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.service.WMReportService;
import app.projectevaluation.bean.ProjectEvaluationBean;

@Controller("wMProjectLevelReportController")
public class WMProjectLevelReportController {

	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	WMReportService WMService;
	
	@RequestMapping(value = "/projectWMProgramReport", method = RequestMethod.GET)
	public ModelAndView getprojectWMProgramReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		mav = new ModelAndView("mahotsav/wmProjLvlProgReport");
		mav.addObject("menu", menuController.getMenuUserId(request));
		
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		
		list = WMService.getProjLvlWMPrgReport();
		
		mav.addObject("projLvlWMPrgList",list);
		mav.addObject("projLvlWMPrgListSize",list.size());
		
		return mav; 
	}
	
	@RequestMapping(value = "/getImageMahotProgRpt", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageMahotProgRpt(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("stCode") Integer stCode, @RequestParam("imgType") String imgType){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = WMService.getImageMahotsavProjAtStLVL(stCode, imgType);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}
	
	@RequestMapping(value = "/getDistImageMahotProgRpt", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getDistImageMahotProgRpt(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("distCode") Integer distCode, @RequestParam("imgType") String imgType){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = WMService.getImageMahotsavProjAtDistLVL(distCode, imgType);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}
	
	@RequestMapping(value = "/distWMProjLvlProgRpt", method = RequestMethod.GET)
	public ModelAndView distWMProjLvlProgRpt(HttpServletRequest request, HttpServletResponse response) {
		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		
		ModelAndView mav = new ModelAndView("mahotsav/wmProjLvlProgReport");
		
		list = WMService.getdistWMProjLvlProgRpt(Integer.parseInt(stcd));
		
		mav.addObject("stcd",stcd);
		mav.addObject("stName",stName);
		mav.addObject("distWMProjList",list);
		mav.addObject("distWMProjListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/downloadExcelProjLvlProgram", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjLvlProgram(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		
		list = WMService.getProjLvlWMPrgReport();
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report WM2 - State Wise Project Level Watershed Mahotsav Program");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report WM2 - State Wise Project Level Watershed Mahotsav Program";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 19, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,2,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,7,7);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,8,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,14,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,15,15);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,16,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,18,18);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,19,19);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,6,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,8,10);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,11,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,12,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,13,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,16,16);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,17,17);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowhead = sheet.createRow(5);
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Prabhat Pheri");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=3;i<7;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(7);
		cell.setCellValue("No. of Project Level Watershed Mahotsav Organized");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("No. of Participation in Project Level Waterhshed Mahotsav");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=9;i<14;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(14);
		cell.setCellValue("Number of Works for Bhoomi Poojan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(15);
		cell.setCellValue("Number of Works for Lokarpan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(16);
		cell.setCellValue("Shramdaan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(17);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(18);
		cell.setCellValue("Agro forestry / Horticulture Plantation (Number of Sapling)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(19);
		cell.setCellValue("No of Photographs Uploaded");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<2;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(2);
		cell.setCellValue("No. of Prabhat Pheri Organized");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Participants/Villagers");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<6;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("No of Photographs Uploaded");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(7);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Participants/Villagers");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=9;i<11;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(11);
		cell.setCellValue("Public Representatives");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(12);
		cell.setCellValue("Government Officials");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(13);
		cell.setCellValue("Total Participation (6+11+12+13)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=14;i<16;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(16);
		cell.setCellValue("Number of Locations");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(17);
		cell.setCellValue("No. of people participated");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=18;i<20;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<2;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(2);
		cell.setCellValue("No. of Prabhat Pheri Organized");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(3);
		cell.setCellValue("Male");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(4);
		cell.setCellValue("Female");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(5);
		cell.setCellValue("Total (4+5)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=6;i<8;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(8);
		cell.setCellValue("Male");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Female");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(10);
		cell.setCellValue("Total (8+9)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=11;i<20;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead3 = sheet.createRow(8);
		
		for(int i=0;i<20;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		BigInteger totPPOrganized = BigInteger.ZERO;
		BigInteger totPPMale = BigInteger.ZERO;
		BigInteger totPPFemale = BigInteger.ZERO;
		BigInteger totPPParticipants = BigInteger.ZERO;
		BigInteger totPPPhoto = BigInteger.ZERO;
		
		BigInteger totPLOrganized = BigInteger.ZERO;
		BigInteger totPLMale = BigInteger.ZERO;
		BigInteger totPLFemale = BigInteger.ZERO;
		BigInteger totPLMFParticipants = BigInteger.ZERO;
		BigInteger totPLRepresentatives = BigInteger.ZERO;
		BigInteger totPLGoverment = BigInteger.ZERO;
		BigInteger totPLParticipants = BigInteger.ZERO;
		BigInteger totPLBhoomiPoojan = BigInteger.ZERO;
		BigInteger totPLLokarpan = BigInteger.ZERO;
		BigInteger totPLShramdaanLoc = BigInteger.ZERO;
		BigInteger totPLShramdaanPeople = BigInteger.ZERO;
		BigInteger totPLPlantation = BigInteger.ZERO;
		BigInteger totPLImage = BigInteger.ZERO;
		
		
	    for(InaugurationMahotsavBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getStname());
	    	row.createCell(2).setCellValue(bean.getNo_of_prabhat().doubleValue());
	    	row.createCell(3).setCellValue(bean.getPr_male().doubleValue());
	    	row.createCell(4).setCellValue(bean.getPr_female().doubleValue());
	    	row.createCell(5).setCellValue(bean.getPr_total_male_female().doubleValue());
	    	row.createCell(6).setCellValue(bean.getTotal_prabhat_photo().doubleValue());
	    	row.createCell(7).setCellValue(bean.getNo_of_projectlvl().doubleValue());
	    	row.createCell(8).setCellValue(bean.getPl_male().doubleValue());
	    	row.createCell(9).setCellValue(bean.getPl_female().doubleValue());
	    	row.createCell(10).setCellValue(bean.getPl_total_male_female().doubleValue());
	    	row.createCell(11).setCellValue(bean.getRepresentatives().doubleValue());
	    	row.createCell(12).setCellValue(bean.getGovt_officials().doubleValue());
	    	row.createCell(13).setCellValue(bean.getTotal_participations().doubleValue());
	    	row.createCell(14).setCellValue(bean.getBhoomi_poojan().doubleValue());
	    	row.createCell(15).setCellValue(bean.getLokarpans().doubleValue());
	    	row.createCell(16).setCellValue(bean.getShramdaan_location().doubleValue());
	    	row.createCell(17).setCellValue(bean.getShramdaan_participated().doubleValue());
	    	row.createCell(18).setCellValue(bean.getNo_of_agro().doubleValue());
	    	row.createCell(19).setCellValue(bean.getTotal_projlvl_photo().doubleValue());
	    	
	    	
	    	totPPOrganized = totPPOrganized.add(bean.getNo_of_prabhat());
			totPPMale = totPPMale.add(bean.getPr_male());
			totPPFemale = totPPFemale.add(bean.getPr_female());
			totPPParticipants = totPPParticipants.add(bean.getPr_total_male_female());
			totPPPhoto = totPPPhoto.add(bean.getTotal_prabhat_photo());
			
			totPLOrganized = totPLOrganized.add(bean.getNo_of_projectlvl());
			totPLMale = totPLMale.add(bean.getPl_male());
			totPLFemale = totPLFemale.add(bean.getPl_female());
			totPLMFParticipants = totPLMFParticipants.add(bean.getPl_total_male_female());
			totPLRepresentatives = totPLRepresentatives.add(bean.getRepresentatives());
			totPLGoverment = totPLGoverment.add(bean.getGovt_officials());
			totPLParticipants = totPLParticipants.add(bean.getTotal_participations());
			totPLBhoomiPoojan = totPLBhoomiPoojan.add(bean.getBhoomi_poojan());
			totPLLokarpan = totPLLokarpan.add(bean.getLokarpans());
			totPLShramdaanLoc = totPLShramdaanLoc.add(bean.getShramdaan_location());
			totPLShramdaanPeople = totPLShramdaanPeople.add(bean.getShramdaan_participated());
			totPLPlantation = totPLPlantation.add(bean.getNo_of_agro());
			totPLImage = totPLImage.add(bean.getTotal_projlvl_photo());
	    	
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
		cell.setCellValue(totPPOrganized.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totPPMale.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totPPFemale.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totPPParticipants.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totPPPhoto.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totPLOrganized.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totPLMale.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totPLFemale.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totPLMFParticipants.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totPLRepresentatives.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totPLGoverment.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(totPLParticipants.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(totPLBhoomiPoojan.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(totPLLokarpan.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(totPLShramdaanLoc.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(17);
		cell.setCellValue(totPLShramdaanPeople.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(18);
		cell.setCellValue(totPLPlantation.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(19);
		cell.setCellValue(totPLImage.doubleValue());
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 19);
	    String fileName = "attachment; filename=Report WM2- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "mahotsav/wmProjLvlProgReport";
	}
	
	@RequestMapping(value = "/downloadPDFProjLvlProgram", method = RequestMethod.POST)
	public ModelAndView downloadPDFProjLvlProgram(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		
		list = WMService.getProjLvlWMPrgReport();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 14, 14, 0);
			document.addTitle("WM2 - WMProjectLevelReport");
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
			
			paragraph3 = new Paragraph("Report WM2 - State Wise Project Level Watershed Mahotsav Program", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(14);
		    paragraph3.setSpacingAfter(14);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(20);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Prabhat Pheri", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Project Level Watershed Mahotsav Organized", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Participation in Project Level Watershed Mahotsav", Element.ALIGN_CENTER, 6, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Works for Bhoomi Poojan", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Works for Lokarpan", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Shramdaan", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Agro forestry / Horticulture Plantation (Number of Sapling)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No of Photographs Uploaded", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Numbers of Prabhat Pheri Organized", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Participants/Villagers", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of Photographs Uploaded", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Participants/Villagers", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Public Representatives", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Government Officials", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Participation (7+11+12+13)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Locations", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of people participated", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Male", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Female", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total (4+5)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Male", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Female", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total (8+9)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
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
			
			
			int k = 1;
			BigInteger totPPOrganized = BigInteger.ZERO;
			BigInteger totPPMale = BigInteger.ZERO;
			BigInteger totPPFemale = BigInteger.ZERO;
			BigInteger totPPParticipants = BigInteger.ZERO;
			BigInteger totPPPhoto = BigInteger.ZERO;
			
			BigInteger totPLOrganized = BigInteger.ZERO;
			BigInteger totPLMale = BigInteger.ZERO;
			BigInteger totPLFemale = BigInteger.ZERO;
			BigInteger totPLMFParticipants = BigInteger.ZERO;
			BigInteger totPLRepresentatives = BigInteger.ZERO;
			BigInteger totPLGoverment = BigInteger.ZERO;
			BigInteger totPLParticipants = BigInteger.ZERO;
			BigInteger totPLBhoomiPoojan = BigInteger.ZERO;
			BigInteger totPLLokarpan = BigInteger.ZERO;
			BigInteger totPLShramdaanLoc = BigInteger.ZERO;
			BigInteger totPLShramdaanPeople = BigInteger.ZERO;
			BigInteger totPLPlantation = BigInteger.ZERO;
			BigInteger totPLImage = BigInteger.ZERO;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getNo_of_prabhat() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getNo_of_prabhat()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPr_male() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPr_male()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPr_female() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPr_female()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getPr_total_male_female() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPr_total_male_female()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getTotal_prabhat_photo() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getTotal_prabhat_photo()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        
			        CommonFunctions.insertCell(table, list.get(i).getNo_of_projectlvl() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getNo_of_projectlvl()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getPl_male() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPl_male()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getPl_female() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPl_female()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getPl_total_male_female() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPl_total_male_female()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getRepresentatives() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getRepresentatives()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getGovt_officials() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getGovt_officials()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getTotal_participations() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getTotal_participations()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getBhoomi_poojan() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getBhoomi_poojan()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getLokarpans() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getLokarpans()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getShramdaan_location() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getShramdaan_location()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getShramdaan_participated() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getShramdaan_participated()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getNo_of_agro() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getNo_of_agro()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getTotal_projlvl_photo() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getTotal_projlvl_photo()), Element.ALIGN_RIGHT, 1, 1, bf8);

					
			        totPPOrganized = totPPOrganized.add(list.get(i).getNo_of_prabhat());
			        totPPMale = totPPMale.add(list.get(i).getPr_male());
			        totPPFemale = totPPFemale.add(list.get(i).getPr_female());
			        totPPParticipants = totPPParticipants.add(list.get(i).getPr_total_male_female());
			        totPPPhoto = totPPPhoto.add(list.get(i).getTotal_prabhat_photo());
			        
			        totPLOrganized = totPLOrganized.add(list.get(i).getNo_of_projectlvl());
			        totPLMale = totPLMale.add(list.get(i).getPl_male());
			        totPLFemale = totPLFemale.add(list.get(i).getPl_female());
			        totPLMFParticipants = totPLMFParticipants.add(list.get(i).getPl_total_male_female());
			        totPLRepresentatives = totPLRepresentatives.add(list.get(i).getRepresentatives());
			        totPLGoverment = totPLGoverment.add(list.get(i).getGovt_officials());
			        totPLParticipants = totPLParticipants.add(list.get(i).getTotal_participations());
			        totPLBhoomiPoojan = totPLBhoomiPoojan.add(list.get(i).getBhoomi_poojan());
			        totPLLokarpan = totPLLokarpan.add(list.get(i).getLokarpans());
			        totPLShramdaanLoc = totPLShramdaanLoc.add(list.get(i).getShramdaan_location());
			        totPLShramdaanPeople = totPLShramdaanPeople.add(list.get(i).getShramdaan_participated());
			        totPLPlantation = totPLPlantation.add(list.get(i).getNo_of_agro());
			        totPLImage = totPLImage.add(list.get(i).getTotal_projlvl_photo());
			        		
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPPOrganized), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPPMale), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPPFemale), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPPParticipants), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPPPhoto), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				CommonFunctions.insertCell3(table, String.valueOf(totPLOrganized), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLMale), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLFemale), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLMFParticipants), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLRepresentatives), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLGoverment), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLParticipants), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLBhoomiPoojan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLLokarpan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLShramdaanLoc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLShramdaanPeople), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLPlantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLImage), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 20, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WM2- State.pdf");
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
	
	@RequestMapping(value = "/downloadDistPDFProjLvlProgram", method = RequestMethod.POST)
	public ModelAndView downloadDistPDFProjLvlProgram(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		String stcd = request.getParameter("stcd");
		list = WMService.getdistWMProjLvlProgRpt(Integer.parseInt(stcd));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 14, 14, 0);
			document.addTitle("WM2 - WMProjectLevelReport");
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
			
			paragraph3 = new Paragraph("Report WM2 - District Level Project Level Watershed Mahotsav Program", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(14);
		    paragraph3.setSpacingAfter(14);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(20);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Prabhat Pheri", Element.ALIGN_CENTER, 5, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Project Level Watershed Mahotsav Organized", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Participation in Project Level Watershed Mahotsav", Element.ALIGN_CENTER, 6, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Works for Bhoomi Poojan", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Works for Lokarpan", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Shramdaan", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Agro forestry / Horticulture Plantation (Number of Sapling)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No of Photographs Uploaded", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Numbers of Prabhat Pheri Organized", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Participants/Villagers", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of Photographs Uploaded", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Participants/Villagers", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Public Representatives", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Government Officials", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Participation (7+11+12+13)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Locations", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of people participated", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Male", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Female", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total (4+5)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Male", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Female", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total (8+9)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
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
			
			
			int k = 1;
			BigInteger totPPOrganized = BigInteger.ZERO;
			BigInteger totPPMale = BigInteger.ZERO;
			BigInteger totPPFemale = BigInteger.ZERO;
			BigInteger totPPParticipants = BigInteger.ZERO;
			BigInteger totPPPhoto = BigInteger.ZERO;
			
			BigInteger totPLOrganized = BigInteger.ZERO;
			BigInteger totPLMale = BigInteger.ZERO;
			BigInteger totPLFemale = BigInteger.ZERO;
			BigInteger totPLMFParticipants = BigInteger.ZERO;
			BigInteger totPLRepresentatives = BigInteger.ZERO;
			BigInteger totPLGoverment = BigInteger.ZERO;
			BigInteger totPLParticipants = BigInteger.ZERO;
			BigInteger totPLBhoomiPoojan = BigInteger.ZERO;
			BigInteger totPLLokarpan = BigInteger.ZERO;
			BigInteger totPLShramdaanLoc = BigInteger.ZERO;
			BigInteger totPLShramdaanPeople = BigInteger.ZERO;
			BigInteger totPLPlantation = BigInteger.ZERO;
			BigInteger totPLImage = BigInteger.ZERO;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getNo_of_prabhat() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getNo_of_prabhat()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPr_male() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPr_male()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getPr_female() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPr_female()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getPr_total_male_female() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPr_total_male_female()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getTotal_prabhat_photo() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getTotal_prabhat_photo()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        
			        CommonFunctions.insertCell(table, list.get(i).getNo_of_projectlvl() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getNo_of_projectlvl()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getPl_male() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPl_male()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getPl_female() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPl_female()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getPl_total_male_female() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getPl_total_male_female()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getRepresentatives() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getRepresentatives()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getGovt_officials() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getGovt_officials()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getTotal_participations() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getTotal_participations()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getBhoomi_poojan() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getBhoomi_poojan()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getLokarpans() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getLokarpans()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getShramdaan_location() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getShramdaan_location()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getShramdaan_participated() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getShramdaan_participated()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getNo_of_agro() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getNo_of_agro()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getTotal_projlvl_photo() == BigInteger.ZERO ? "" : String.valueOf(list.get(i).getTotal_projlvl_photo()), Element.ALIGN_RIGHT, 1, 1, bf8);

					
			        totPPOrganized = totPPOrganized.add(list.get(i).getNo_of_prabhat());
			        totPPMale = totPPMale.add(list.get(i).getPr_male());
			        totPPFemale = totPPFemale.add(list.get(i).getPr_female());
			        totPPParticipants = totPPParticipants.add(list.get(i).getPr_total_male_female());
			        totPPPhoto = totPPPhoto.add(list.get(i).getTotal_prabhat_photo());
			        
			        totPLOrganized = totPLOrganized.add(list.get(i).getNo_of_projectlvl());
			        totPLMale = totPLMale.add(list.get(i).getPl_male());
			        totPLFemale = totPLFemale.add(list.get(i).getPl_female());
			        totPLMFParticipants = totPLMFParticipants.add(list.get(i).getPl_total_male_female());
			        totPLRepresentatives = totPLRepresentatives.add(list.get(i).getRepresentatives());
			        totPLGoverment = totPLGoverment.add(list.get(i).getGovt_officials());
			        totPLParticipants = totPLParticipants.add(list.get(i).getTotal_participations());
			        totPLBhoomiPoojan = totPLBhoomiPoojan.add(list.get(i).getBhoomi_poojan());
			        totPLLokarpan = totPLLokarpan.add(list.get(i).getLokarpans());
			        totPLShramdaanLoc = totPLShramdaanLoc.add(list.get(i).getShramdaan_location());
			        totPLShramdaanPeople = totPLShramdaanPeople.add(list.get(i).getShramdaan_participated());
			        totPLPlantation = totPLPlantation.add(list.get(i).getNo_of_agro());
			        totPLImage = totPLImage.add(list.get(i).getTotal_projlvl_photo());
			        		
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPPOrganized), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPPMale), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPPFemale), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPPParticipants), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPPPhoto), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				CommonFunctions.insertCell3(table, String.valueOf(totPLOrganized), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLMale), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLFemale), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLMFParticipants), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLRepresentatives), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLGoverment), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLParticipants), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLBhoomiPoojan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLLokarpan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLShramdaanLoc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLShramdaanPeople), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLPlantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPLImage), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 20, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WM2- District.pdf");
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
	
	@RequestMapping(value = "/downloadDistExcelProjLvlProgram", method = RequestMethod.POST)
	@ResponseBody
	public String downloadDistExcelProjLvlProgram(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		String stcd = request.getParameter("stcd");
		list = WMService.getdistWMProjLvlProgRpt(Integer.parseInt(stcd));
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report WM2 - District Wise Project Level Watershed Mahotsav Program");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report WM2 - District Wise Project Level Watershed Mahotsav Program";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 19, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,2,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,7,7);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,8,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,14,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,15,15);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,16,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,18,18);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,19,19);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,6,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,8,10);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,11,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,12,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,13,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,16,16);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,17,17);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowhead = sheet.createRow(5);
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Prabhat Pheri");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=3;i<7;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(7);
		cell.setCellValue("No. of Project Level Watershed Mahotsav Organized");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("No. of Participation in Project Level Waterhshed Mahotsav");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=9;i<14;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(14);
		cell.setCellValue("Number of Works for Bhoomi Poojan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(15);
		cell.setCellValue("Number of Works for Lokarpan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(16);
		cell.setCellValue("Shramdaan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(17);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(18);
		cell.setCellValue("Agro forestry / Horticulture Plantation (Number of Sapling)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(19);
		cell.setCellValue("No of Photographs Uploaded");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<2;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(2);
		cell.setCellValue("No. of Prabhat Pheri Organized");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Participants/Villagers");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<6;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("No of Photographs Uploaded");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(7);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Participants/Villagers");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=9;i<11;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(11);
		cell.setCellValue("Public Representatives");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(12);
		cell.setCellValue("Government Officials");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(13);
		cell.setCellValue("Total Participation (6+11+12+13)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=14;i<16;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(16);
		cell.setCellValue("Number of Locations");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(17);
		cell.setCellValue("No. of people participated");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=18;i<20;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<2;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(2);
		cell.setCellValue("No. of Prabhat Pheri Organized");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(3);
		cell.setCellValue("Male");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(4);
		cell.setCellValue("Female");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(5);
		cell.setCellValue("Total (4+5)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=6;i<8;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead2.createCell(8);
		cell.setCellValue("Male");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Female");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead2.createCell(10);
		cell.setCellValue("Total (8+9)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=11;i<20;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead3 = sheet.createRow(8);
		
		for(int i=0;i<20;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		BigInteger totPPOrganized = BigInteger.ZERO;
		BigInteger totPPMale = BigInteger.ZERO;
		BigInteger totPPFemale = BigInteger.ZERO;
		BigInteger totPPParticipants = BigInteger.ZERO;
		BigInteger totPPPhoto = BigInteger.ZERO;
		
		BigInteger totPLOrganized = BigInteger.ZERO;
		BigInteger totPLMale = BigInteger.ZERO;
		BigInteger totPLFemale = BigInteger.ZERO;
		BigInteger totPLMFParticipants = BigInteger.ZERO;
		BigInteger totPLRepresentatives = BigInteger.ZERO;
		BigInteger totPLGoverment = BigInteger.ZERO;
		BigInteger totPLParticipants = BigInteger.ZERO;
		BigInteger totPLBhoomiPoojan = BigInteger.ZERO;
		BigInteger totPLLokarpan = BigInteger.ZERO;
		BigInteger totPLShramdaanLoc = BigInteger.ZERO;
		BigInteger totPLShramdaanPeople = BigInteger.ZERO;
		BigInteger totPLPlantation = BigInteger.ZERO;
		BigInteger totPLImage = BigInteger.ZERO;
		
		
	    for(InaugurationMahotsavBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getDistname());
	    	row.createCell(2).setCellValue(bean.getNo_of_prabhat().doubleValue());
	    	row.createCell(3).setCellValue(bean.getPr_male().doubleValue());
	    	row.createCell(4).setCellValue(bean.getPr_female().doubleValue());
	    	row.createCell(5).setCellValue(bean.getPr_total_male_female().doubleValue());
	    	row.createCell(6).setCellValue(bean.getTotal_prabhat_photo().doubleValue());
	    	row.createCell(7).setCellValue(bean.getNo_of_projectlvl().doubleValue());
	    	row.createCell(8).setCellValue(bean.getPl_male().doubleValue());
	    	row.createCell(9).setCellValue(bean.getPl_female().doubleValue());
	    	row.createCell(10).setCellValue(bean.getPl_total_male_female().doubleValue());
	    	row.createCell(11).setCellValue(bean.getRepresentatives().doubleValue());
	    	row.createCell(12).setCellValue(bean.getGovt_officials().doubleValue());
	    	row.createCell(13).setCellValue(bean.getTotal_participations().doubleValue());
	    	row.createCell(14).setCellValue(bean.getBhoomi_poojan().doubleValue());
	    	row.createCell(15).setCellValue(bean.getLokarpans().doubleValue());
	    	row.createCell(16).setCellValue(bean.getShramdaan_location().doubleValue());
	    	row.createCell(17).setCellValue(bean.getShramdaan_participated().doubleValue());
	    	row.createCell(18).setCellValue(bean.getNo_of_agro().doubleValue());
	    	row.createCell(19).setCellValue(bean.getTotal_projlvl_photo().doubleValue());
	    	
	    	
	    	totPPOrganized = totPPOrganized.add(bean.getNo_of_prabhat());
			totPPMale = totPPMale.add(bean.getPr_male());
			totPPFemale = totPPFemale.add(bean.getPr_female());
			totPPParticipants = totPPParticipants.add(bean.getPr_total_male_female());
			totPPPhoto = totPPPhoto.add(bean.getTotal_prabhat_photo());
			
			totPLOrganized = totPLOrganized.add(bean.getNo_of_projectlvl());
			totPLMale = totPLMale.add(bean.getPl_male());
			totPLFemale = totPLFemale.add(bean.getPl_female());
			totPLMFParticipants = totPLMFParticipants.add(bean.getPl_total_male_female());
			totPLRepresentatives = totPLRepresentatives.add(bean.getRepresentatives());
			totPLGoverment = totPLGoverment.add(bean.getGovt_officials());
			totPLParticipants = totPLParticipants.add(bean.getTotal_participations());
			totPLBhoomiPoojan = totPLBhoomiPoojan.add(bean.getBhoomi_poojan());
			totPLLokarpan = totPLLokarpan.add(bean.getLokarpans());
			totPLShramdaanLoc = totPLShramdaanLoc.add(bean.getShramdaan_location());
			totPLShramdaanPeople = totPLShramdaanPeople.add(bean.getShramdaan_participated());
			totPLPlantation = totPLPlantation.add(bean.getNo_of_agro());
			totPLImage = totPLImage.add(bean.getTotal_projlvl_photo());
	    	
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
		cell.setCellValue(totPPOrganized.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totPPMale.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totPPFemale.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totPPParticipants.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totPPPhoto.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totPLOrganized.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totPLMale.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totPLFemale.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totPLMFParticipants.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totPLRepresentatives.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totPLGoverment.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(totPLParticipants.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(totPLBhoomiPoojan.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(totPLLokarpan.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(totPLShramdaanLoc.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(17);
		cell.setCellValue(totPLShramdaanPeople.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(18);
		cell.setCellValue(totPLPlantation.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(19);
		cell.setCellValue(totPLImage.doubleValue());
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 19);
	    String fileName = "attachment; filename=Report WM2- District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "mahotsav/wmProjLvlProgReport";
	}
}
