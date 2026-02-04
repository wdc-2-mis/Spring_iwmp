package app.mahotsav.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import app.bean.AssetIdBean;
import app.bean.Login;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.bean.WMMediaReviewBean;
import app.mahotsav.service.WMReportService;
import app.mahotsav.service.WMSocialMediaAnalysisService;
import app.service.StateMasterService;


@Controller("WMReportController")
public class WMReportController {
	
	@Autowired(required = true)
	MenuController menuController;
	
	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
		
	@Autowired
	WMReportService WMSerice;
	
	@Autowired
    WMSocialMediaAnalysisService wmService;
	
	private Map<Integer, String> stateList;
	
	private Map<String, String> districtList;
	
	private Map<String, String> blockList;
	
	private Map<String, String> villageList;
	
	
	
	@RequestMapping(value = "/getDistrictsByState", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getDistrictsByState(int state) {
        return WMSerice.getDistrictList(state);
    }

    @RequestMapping(value = "/getBlocksByDistrict", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getBlocksByDistrict(int state, int district) {
        return WMSerice.getblockList(state, district);
    }

    @RequestMapping(value = "/getVillagesByBlock", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getVillagesByBlock(int block) {
        return WMSerice.getmahotsavvillageList(block);
    }


	@RequestMapping(value = "/stateWMInaugurationReport", method = RequestMethod.GET)
	public ModelAndView getInaugurationReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		mav = new ModelAndView("mahotsav/wmInaugurationReport");
		mav.addObject("menu", menuController.getMenuUserId(request));
		
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		
		list = WMSerice.getStateWMInaugurationReport();
		
		mav.addObject("stateWMInaugurationList",list);
		mav.addObject("stateWMInaugurationListSize",list.size());
		
		return mav; 
	}
	
	@RequestMapping(value = "/wmSocialMediaReport", method = RequestMethod.GET)
	public ModelAndView wmSocialMediaReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		mav = new ModelAndView("mahotsav/wmSocailMediaReport");
		
		stateList = stateMasterService.getAllState();
		mav.addObject("stateList", stateList);
		
		Integer stcd=0;
		Integer dcode=0;
		Integer bcode=0;
		Integer vcode=0;
		List<SocialMediaReport> list = new ArrayList<SocialMediaReport>();
		
		list = WMSerice.getWMSocialMediaReport(stcd, dcode, bcode, vcode);
		
		mav.addObject("stateWMSocialMediaList",list);
		mav.addObject("stateWMSocialMediaListSize",list.size());
		
		return mav; 
	}
	
	@RequestMapping(value = "/wmSocialMediaReport", method = RequestMethod.POST)
	public ModelAndView wmSocialMediaReportData(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		mav = new ModelAndView("mahotsav/wmSocailMediaReport");
		
		String userState= request.getParameter("state"); 
        String district= request.getParameter("district");
        String block= request.getParameter("block");
        String village= request.getParameter("village");
        String mediaType = request.getParameter("mediaType");
        
        stateList = stateMasterService.getAllState();
        mav.addObject("stateList", stateList);
		 mav.addObject("state", userState);
		 
        if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = WMSerice.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
		
		if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
			blockList = WMSerice.getblockList(Integer.parseInt(userState), Integer.parseInt(district));
			mav.addObject("blockList", blockList);}
			mav.addObject("blkd", block);
			
		if( block!=null && !block.equalsIgnoreCase("") && !block.equals("0")) {
			villageList = WMSerice.getmahotsavvillageList(Integer.parseInt(block));
			mav.addObject("villageList", villageList);}
			mav.addObject("vlg", village);	
				
		 mav.addObject("stateList", stateList);
		
		 List<SocialMediaReport> list = new ArrayList<SocialMediaReport>();
		
		 int stcd = (userState != null && !userState.trim().isEmpty()) ? Integer.parseInt(userState) : 0;
		 int dcode = (district != null && !district.trim().isEmpty()) ? Integer.parseInt(district) : 0;
		 int bcode = (block != null && !block.trim().isEmpty()) ? Integer.parseInt(block) : 0;
		 int vcode = (village != null && !village.trim().isEmpty()) ? Integer.parseInt(village) : 0;

		 list = WMSerice.getWMSocialMediaReport(stcd, dcode, bcode, vcode);
		 
		 if (mediaType != null && !"ALL".equalsIgnoreCase(mediaType)) {
		        list = list.stream()
		                   .filter(r -> r.getMedia_type() != null && String.valueOf(r.getMedia_type().charAt(0)).equalsIgnoreCase(mediaType))
		                   .collect(Collectors.toList());
		    }

		 
		 mav.addObject("stateWMSocialMediaList",list);
		 mav.addObject("stateWMSocialMediaListSize",list.size());
		 mav.addObject("selectedMediaType", mediaType);
		 
		return mav; 
	}
	
	
	@RequestMapping(value = "/downloadExcelStWMInauguration", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStWMInauguration(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		
		list = WMSerice.getStateWMInaugurationReport();
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report WM1 - Watershed Mahotsav Inauguration Program");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report WM1 - Watershed Mahotsav Inauguration Program";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 15, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,3,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,9,9);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,10,10);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,11,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,13,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,14,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,15,15);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,5);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,6,6);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,7,7);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,8,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,11,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,12,12);
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
		cell.setCellValue("Date of Inauguration");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Number of Participation");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<9;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Number of Works for Bhoomi Poojan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Number of Works for Lokarpan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("Shramdaan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(12);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(13);
		cell.setCellValue("Agro forestry / Horticulture Plantation (Number of Sapling)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(14);
		cell.setCellValue("Number of Projects Awarded for Janbhagidari Cup 2025");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(15);
		cell.setCellValue("No of Photographs Uploaded");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
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
		cell.setCellValue("Public Representatives");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Government Officials");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Total Participation (6+7+8)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=9;i<11;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(11);
		cell.setCellValue("Number of Locations");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(12);
		cell.setCellValue("No. of people participated");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=13;i<16;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
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
		
		for(int i=6;i<16;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead3 = sheet.createRow(8);
		
		for(int i=0;i<16;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		int totMale = 0;
		int totFemale = 0;
		int totParticipants = 0;
		int totOthers = 0;
		int totGov = 0;
		int total = 0;
		int totBhoomiPoojan = 0;
		int totLokarpan = 0;
		int totShramdaanLoc = 0;
		int totShramdaanPeople = 0;
		int totPlantation = 0;
		int totAwards = 0;
		int totImage = 0;
		
		
	    for(InaugurationMahotsavBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getStname());
	    	row.createCell(2).setCellValue(bean.getDate());
	    	row.createCell(3).setCellValue(bean.getMale_participants());
	    	row.createCell(4).setCellValue(bean.getFemale_participants());
	    	row.createCell(5).setCellValue(bean.getParticipants());
	    	row.createCell(6).setCellValue(bean.getOthers());
	    	row.createCell(7).setCellValue(bean.getGov_officials());
	    	row.createCell(8).setCellValue(bean.getTotal_participation());
	    	row.createCell(9).setCellValue(bean.getNo_works_bhoomipoojan());
	    	row.createCell(10).setCellValue(bean.getNo_works_lokarpan());
	    	row.createCell(11).setCellValue(bean.getNo_location_shramdaan());
	    	row.createCell(12).setCellValue(bean.getNo_people_shramdaan());
	    	row.createCell(13).setCellValue(bean.getArea_plantation());
	    	row.createCell(14).setCellValue(bean.getNo_awards());
	    	row.createCell(15).setCellValue(bean.getImage_count());
	    	
	    	totMale = totMale + bean.getMale_participants();
			totFemale = totFemale + bean.getFemale_participants();
			totParticipants = totParticipants + bean.getParticipants();
			totOthers = totOthers + bean.getOthers();
			totGov = totGov + bean.getGov_officials();
			total = total + bean.getTotal_participation();
			totBhoomiPoojan = totBhoomiPoojan + bean.getNo_works_bhoomipoojan();
			totLokarpan = totLokarpan + bean.getNo_works_lokarpan();
			totShramdaanLoc = totShramdaanLoc + bean.getNo_location_shramdaan();
			totShramdaanPeople = totShramdaanPeople + bean.getNo_people_shramdaan();
			totPlantation = totPlantation + bean.getArea_plantation();
			totAwards = totAwards + bean.getNo_awards();
			totImage = totImage + bean.getImage_count();
	    	
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
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totMale);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totFemale);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totParticipants);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totOthers);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totGov);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(total);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totBhoomiPoojan);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totLokarpan);
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totShramdaanLoc);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totShramdaanPeople);
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(totPlantation);
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(totAwards);
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(totImage);
		cell.setCellStyle(style1);
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 15);
	    String fileName = "attachment; filename=Report WM1- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "mahotsav/wmInaugurationReport";
	}
	
	
	@RequestMapping(value = "/downloadPDFStWMInauguration", method = RequestMethod.POST)
	public ModelAndView downloadPDFStWMInauguration(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<InaugurationMahotsavBean> list = new ArrayList<InaugurationMahotsavBean>();
		
		list = WMSerice.getStateWMInaugurationReport();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 14, 14, 0);
			document.addTitle("WM1 - WMInaugurationReport");
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
			
			paragraph3 = new Paragraph("Report WM1 - Watershed Mahotsav Inauguration Program", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(14);
		    paragraph3.setSpacingAfter(14);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(16);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Date of Inauguration", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Participation", Element.ALIGN_CENTER, 6, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Works for Bhoomi Poojan", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Works for Lokarpan", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Shramdaan", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Agro forestry / Horticulture Plantation (Number of Sapling)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Projects Awarded for Janbhagidari Cup 2025", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No of Photographs Uploaded", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Participants/Villagers", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Public Representatives", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Government Officials", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Participation (6+7+8)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Locations", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "No. of people participated", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Male", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Female", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total (4+5)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
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
			
			
			int k = 1;
			int totMale = 0;
			int totFemale = 0;
			int totParticipants = 0;
			int totOthers = 0;
			int totGov = 0;
			int total = 0;
			int totBhoomiPoojan = 0;
			int totLokarpan = 0;
			int totShramdaanLoc = 0;
			int totShramdaanPeople = 0;
			int totPlantation = 0;
			int totAwards = 0;
			int totImage = 0;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDate() == null ? "" : String.valueOf(list.get(i).getDate()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getMale_participants() == 0 ? "" : String.valueOf(list.get(i).getMale_participants()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getFemale_participants() == 0 ? "" : String.valueOf(list.get(i).getFemale_participants()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getParticipants() == 0 ? "" : String.valueOf(list.get(i).getParticipants()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getOthers() == 0 ? "" : String.valueOf(list.get(i).getOthers()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getGov_officials() == 0 ? "" : String.valueOf(list.get(i).getGov_officials()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getTotal_participation() == 0 ? "" : String.valueOf(list.get(i).getTotal_participation()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getNo_works_bhoomipoojan() == 0 ? "" : String.valueOf(list.get(i).getNo_works_bhoomipoojan()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getNo_works_lokarpan() == 0 ? "" : String.valueOf(list.get(i).getNo_works_lokarpan()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getNo_location_shramdaan() == 0 ? "" : String.valueOf(list.get(i).getNo_location_shramdaan()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getNo_people_shramdaan() == 0 ? "" : String.valueOf(list.get(i).getNo_people_shramdaan()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getArea_plantation() == 0 ? "" : String.valueOf(list.get(i).getArea_plantation()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getNo_awards() == 0 ? "" : String.valueOf(list.get(i).getNo_awards()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getImage_count() == 0 ? "" : String.valueOf(list.get(i).getImage_count()), Element.ALIGN_RIGHT, 1, 1, bf8);

					
/*					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getDate()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getMale_participants()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getFemale_participants()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getParticipants()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOthers()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getGov_officials()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_participation()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_works_bhoomipoojan()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_works_lokarpan()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_location_shramdaan()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_people_shramdaan()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getArea_plantation()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_awards()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getImage_count()), Element.ALIGN_RIGHT, 1, 1, bf8);
*/					
					totMale = totMale + list.get(i).getMale_participants();
					totFemale = totFemale + list.get(i).getFemale_participants();
					totParticipants = totParticipants + list.get(i).getParticipants();
					totOthers = totOthers + list.get(i).getOthers();
					totGov = totGov + list.get(i).getGov_officials();
					total = total + list.get(i).getTotal_participation();
					totBhoomiPoojan = totBhoomiPoojan + list.get(i).getNo_works_bhoomipoojan();
					totLokarpan = totLokarpan + list.get(i).getNo_works_lokarpan();
					totShramdaanLoc = totShramdaanLoc + list.get(i).getNo_location_shramdaan();
					totShramdaanPeople = totShramdaanPeople + list.get(i).getNo_people_shramdaan();
					totPlantation = totPlantation + list.get(i).getArea_plantation();
					totAwards = totAwards + list.get(i).getNo_awards();
					totImage = totImage + list.get(i).getImage_count();
			    	
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 3, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totMale), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totFemale), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totParticipants), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totOthers), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totGov), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totBhoomiPoojan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totLokarpan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totShramdaanLoc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totShramdaanPeople), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totPlantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totAwards), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totImage), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 16, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WM1- State.pdf");
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
	
	
	@RequestMapping(value = "/downloadExcelSocialMediaReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelSocialMediaReport(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<SocialMediaReport> list = new ArrayList<SocialMediaReport>();

		String userState = request.getParameter("state");
	    String district = request.getParameter("district");
	    String block = request.getParameter("block");
	    String village = request.getParameter("village");
	    String mediaType = request.getParameter("mediaType");
	    

	    int stcd = (userState != null && !userState.trim().isEmpty()) ? Integer.parseInt(userState) : 0;
	    int dcode = (district != null && !district.trim().isEmpty()) ? Integer.parseInt(district) : 0;
	    int bcode = (block != null && !block.trim().isEmpty()) ? Integer.parseInt(block) : 0;
	    int vcode = (village != null && !village.trim().isEmpty()) ? Integer.parseInt(village) : 0;
	    

	    list = WMSerice.getWMSocialMediaReport(stcd, dcode, bcode, vcode);
		
	    if (mediaType != null && !"ALL".equalsIgnoreCase(mediaType)) {
	        list = list.stream()
	                   .filter(r -> r.getMedia_type() != null && String.valueOf(r.getMedia_type().charAt(0)).equalsIgnoreCase(mediaType))
	                   .collect(Collectors.toList());
	    }
	    

	    String stName = request.getParameter("stName");
	    String dName = request.getParameter("distName");
	    String bName = request.getParameter("blkName");
	    String vName = request.getParameter("vlgName");
	    String mName = request.getParameter("media");
	    int maxCols = (mediaType.equals("P")) ? 6 : 7;
	    
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report WM4 - Watershed Mahotsav Social Media");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report WM4 - Watershed Mahotsav Social Media";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, maxCols, areaAmtValDetail, workbook);
		

		mergedRegion = new CellRangeAddress(5,5,0,maxCols);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,2,2);
		sheet.addMergedRegion(mergedRegion);
//		mergedRegion = new CellRangeAddress(6,7,3,3);
//		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,maxCols);
		sheet.addMergedRegion(mergedRegion);
		
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State : "+ stName+"     District : "+dName+"     Block : "+bName+"     Village : "+vName+"     Media Type : "+mName);  
		cell.setCellStyle(style);
		
		for(int i=1;i<=maxCols;i++)
		{
			cell =rowDetail.createCell(i);
			cell.setCellStyle(style);
		}
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Registration Number");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
//		cell = rowhead.createCell(3);
//		cell.setCellValue("Contact Number");
//		cell.setCellStyle(style);
//		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
//		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		if (mediaType.equals("P")) {
		    cell.setCellValue("List of Uploaded Photos");
		} else {
		    cell.setCellValue("List of Uploaded Videos");
		}
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<=maxCols;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
				
		int colIndex = 3;

		cell = rowhead1.createCell(colIndex++);
		cell.setCellValue("Facebook");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

		if (!mediaType.equals("P")) {
		    cell = rowhead1.createCell(colIndex++);
		    cell.setCellValue("YouTube");
		    cell.setCellStyle(style);
		    CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		}

		String[] platforms = {"Instagram", "Twitter", "LinkedIn"};
		for (String platform : platforms) {
		    cell = rowhead1.createCell(colIndex++);
		    cell.setCellValue(platform);
		    cell.setCellStyle(style);
		    CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		}

		
		
		Row rowhead2 = sheet.createRow(8);
		
		for(int i=0;i<=maxCols;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		String regNo = "";
		String name = "";
//		String phno = "";
		
	    for(SocialMediaReport bean: list)
	    {
	    	Row row = sheet.createRow(rowno);

	    	if (!bean.getUser_reg_no().equals(regNo)) {
	            row.createCell(0).setCellValue(sno);
	            sno++;
	        } else {
	            row.createCell(0).setCellValue("");
	        }

	        row.createCell(1).setCellValue(!bean.getUser_reg_no().equals(regNo) ? bean.getUser_reg_no() : "");

	        row.createCell(2).setCellValue(
	            (!bean.getReg_name().equals(name) || !bean.getUser_reg_no().equals(regNo)) 
	            ? bean.getReg_name() 
	            : ""
	        );

//	        row.createCell(3).setCellValue(
//	            (!bean.getPhno().equals(phno) || !bean.getUser_reg_no().equals(regNo)) 
//	            ? bean.getPhno() 
//	            : ""
//	        );

	    	int col = 3;

	        row.createCell(col++).setCellValue(bean.getFacebook_urls());

	        if (!mediaType.equals("P")) {
	            row.createCell(col++).setCellValue(bean.getYoutube_urls());
	        }

	        row.createCell(col++).setCellValue(bean.getInstagram_urls());
	        row.createCell(col++).setCellValue(bean.getTwitter_urls());
	        row.createCell(col++).setCellValue(bean.getLinkedin_urls());


	        regNo = bean.getUser_reg_no();
	        name = bean.getReg_name();
//	        phno = bean.getPhno();

	    	rowno++;
	    }
	    
	    
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), maxCols);
	    String fileName = "attachment; filename=Report WM4.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "mahotsav/wmSocailMediaReport";
	}

	@RequestMapping(value = "/downloadPDFSocialMediaReport", method = RequestMethod.POST)
	public ModelAndView downloadPDFSocialMediaReport(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<SocialMediaReport> list = new ArrayList<SocialMediaReport>();

		String userState = request.getParameter("state");
	    String district = request.getParameter("district");
	    String block = request.getParameter("block");
	    String village = request.getParameter("village");
	    String mediaType = request.getParameter("mediaType");
	    

	    int stcd = (userState != null && !userState.trim().isEmpty()) ? Integer.parseInt(userState) : 0;
	    int dcode = (district != null && !district.trim().isEmpty()) ? Integer.parseInt(district) : 0;
	    int bcode = (block != null && !block.trim().isEmpty()) ? Integer.parseInt(block) : 0;
	    int vcode = (village != null && !village.trim().isEmpty()) ? Integer.parseInt(village) : 0;
	    

	    list = WMSerice.getWMSocialMediaReport(stcd, dcode, bcode, vcode);
		
	    if (mediaType != null && !"ALL".equalsIgnoreCase(mediaType)) {
	        list = list.stream()
	                   .filter(r -> r.getMedia_type() != null && String.valueOf(r.getMedia_type().charAt(0)).equalsIgnoreCase(mediaType))
	                   .collect(Collectors.toList());
	    }
	    

	    String stName = request.getParameter("stName");
	    String dName = request.getParameter("distName");
	    String bName = request.getParameter("blkName");
	    String vName = request.getParameter("vlgName");
	    String mName = request.getParameter("media");
	    int maxCols = (mediaType.equals("P")) ? 7 : 8;
	    
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 14, 14, 0);
			document.addTitle("WM4 - WMSocialMediaReport");
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
			
			paragraph3 = new Paragraph("Report WM4 - Watershed Mahotsav Social Media", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(14);
		    paragraph3.setSpacingAfter(14);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    
		    // For Photos (P) → 7 columns
		    int[] widthsPhotos = {2, 8, 5, 5, 5, 5, 5};

		    // For Videos (V) → 8 columns
		    int[] widthsVideos = {2, 8, 5, 5, 5, 5, 5, 5};

		    table = new PdfPTable(maxCols);
		    table.setWidths(mediaType.equals("P") ? widthsPhotos : widthsVideos);
		    
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(4);
		    
		    
		    CommonFunctions.insertCellHeader(table, "State : "+ stName+"     District : "+dName+"     Block : "+bName+"     Village : "+vName+"     "
		    		+ "Media Type : "+mName, Element.ALIGN_LEFT, maxCols, 1, bf8Bold);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Registration Number", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "Contact Number", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, mediaType.equals("P") ? "List of Uploaded Photos" : "List of Uploaded Videos", Element.ALIGN_CENTER, maxCols, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "Facebook", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			if (!mediaType.equals("P"))
				CommonFunctions.insertCellHeader(table, "Youtube", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Instagram", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Twitter", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "LinkedIn", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			for(int i=1; i<=maxCols; i++)
				CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
			int k = 1;
			String regNo = "";
			String name = "";
//			String phno = "";
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, !list.get(i).getUser_reg_no().equals(regNo) ? String.valueOf(k) : "", Element.ALIGN_LEFT, 1, 1, bf8);
					
					if (!list.get(i).getUser_reg_no().equals(regNo)) k++;
					
					CommonFunctions.insertCell(table, !list.get(i).getUser_reg_no().equals(regNo) ? list.get(i).getUser_reg_no() : "", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, (!list.get(i).getReg_name().equals(name) || !list.get(i).getUser_reg_no().equals(regNo)) ? list.get(i).getReg_name() : "", Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, (!list.get(i).getPhno().equals(phno) || !list.get(i).getUser_reg_no().equals(regNo)) ? list.get(i).getPhno() : "", Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getFacebook_urls(), Element.ALIGN_CENTER, 1, 1, bf8);
					if (!mediaType.equals("P"))
						CommonFunctions.insertCell(table, list.get(i).getYoutube_urls(), Element.ALIGN_CENTER, 1, 1, bf8);
					
			        CommonFunctions.insertCell(table, list.get(i).getInstagram_urls(), Element.ALIGN_CENTER, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getTwitter_urls(), Element.ALIGN_CENTER, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getLinkedin_urls(), Element.ALIGN_CENTER, 1, 1, bf8);

					
			        regNo = list.get(i).getUser_reg_no();
			        name = list.get(i).getReg_name();
//			        phno = list.get(i).getPhno();
				}
				
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, maxCols, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WM4.pdf");
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
	

    @RequestMapping(value = "/getWMSocialMediaCompAnalysis", method = RequestMethod.GET)
    public ModelAndView getWMSocialMediaCompAnalysis(HttpServletRequest request, HttpServletResponse response) {
    	session = request.getSession(true);
    	ModelAndView mav = new ModelAndView();
    	
		if (session != null && session.getAttribute("loginID") != null) {
			
			mav.addObject("menu", menuController.getMenuUserId(request));
			mav = new ModelAndView("mahotsav/wmSocialMediaCompAnalysis");
			Map<Integer, String> stateList = stateMasterService.getAllState();
			mav.addObject("stateList", stateList);

			Map<Integer, String> platformList = wmService.getPlatformList();
			mav.addObject("platformList", platformList);

			Map<String, String> statusList = new LinkedHashMap<String, String>();
			statusList.put("Valid", "Valid");
			statusList.put("Invalid", "Invalid");
			statusList.put("Pending", "Pending");
			mav.addObject("statusList", statusList);

			List<WMMediaReviewBean> list = WMSerice.getWMSocialMediaComDetails(0, 0, 0, "");
			mav.addObject("wmList", list);
			mav.addObject("wmListSize", list.size());
			mav.addObject("state",0);
			mav.addObject("stName", "All");
			mav.addObject("distName", "All");
			mav.addObject("mediaName", "All");
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}

        return mav;
    }
    
    @RequestMapping(value = "/getWMSocialMediaCompAnalysis", method = RequestMethod.POST)
    public ModelAndView getWMSocialMediaCompAnalysis(HttpServletRequest request) {

    	session = request.getSession(true);
    	ModelAndView mav = new ModelAndView();
    	
		if (session != null && session.getAttribute("loginID") != null) {
			mav = new ModelAndView("mahotsav/wmSocialMediaCompAnalysis");
			mav.addObject("menu", menuController.getMenuUserId(request));
			int stcd = Integer.parseInt(request.getParameter("state"));
			int dcode = Integer.parseInt(request.getParameter("district"));
			int media = Integer.parseInt(request.getParameter("platform"));
			String status = request.getParameter("status");
			String userdate = request.getParameter("userdate");
			String userdateto = request.getParameter("userdateto");
			String orderBy = request.getParameter("orderBy");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			String distcode = dcode + "";
			String stName = stcd == 0 ? "All" : stateMasterService.getAllState().get(stcd);
			String distName = dcode == 0 ? "All"
					: wmService.getDistrictList(stcd).entrySet().stream().filter(s -> distcode.equals(s.getValue()))
							.map(Map.Entry::getKey).findFirst().orElse(null);
			String platform = media == 0 ? "All" : wmService.getPlatformList().get(media);

			mav.addObject("stateList", stateMasterService.getAllState());
			mav.addObject("districtList", wmService.getDistrictList(stcd));
			mav.addObject("platformList", wmService.getPlatformList());
			Map<String, String> statusList = new LinkedHashMap<String, String>();
			statusList.put("Valid", "Valid");
			statusList.put("Invalid", "Invalid");
			statusList.put("Pending", "Pending");
			mav.addObject("statusList", statusList);

			List<WMMediaReviewBean> list = WMSerice.getWMSocialMediaComDetails(stcd, dcode, media, status);

			if (!userdate.equals("") && !userdateto.equals("")) {
				LocalDate fromDate = LocalDate.parse(userdate, formatter);
				LocalDate toDate = LocalDate.parse(userdateto, formatter);
				List<WMMediaReviewBean> filterList = list.stream().filter(bean -> bean.getCreated_date() != null)
						.filter(bean -> !bean.getCreated_date().before(Date.valueOf(fromDate))
								&& !bean.getCreated_date().after(Date.valueOf(toDate)))
						.collect(Collectors.toList());
				if (orderBy != null) {
					switch (orderBy) {
					case "viewsAsc":
						filterList.sort(Comparator.comparing(WMMediaReviewBean::getNo_of_views));
						break;
					case "viewsDesc":
						filterList.sort(Comparator.comparing(WMMediaReviewBean::getNo_of_views).reversed());
						break;
					case "likesAsc":
						filterList.sort(Comparator.comparing(WMMediaReviewBean::getNo_of_likes));
						break;
					case "likesDesc":
						filterList.sort(Comparator.comparing(WMMediaReviewBean::getNo_of_likes).reversed());
						break;
					}
				}
				mav.addObject("wmList", filterList);
				mav.addObject("wmListSize", filterList.size());
				mav.addObject("orderBy", orderBy);
			} else {
				if (orderBy != null) {
					switch (orderBy) {
					case "viewsAsc":
						list.sort(Comparator.comparing(WMMediaReviewBean::getNo_of_views));
						break;
					case "viewsDesc":
						list.sort(Comparator.comparing(WMMediaReviewBean::getNo_of_views).reversed());
						break;
					case "likesAsc":
						list.sort(Comparator.comparing(WMMediaReviewBean::getNo_of_likes));
						break;
					case "likesDesc":
						list.sort(Comparator.comparing(WMMediaReviewBean::getNo_of_likes).reversed());
						break;
					}
				}
				mav.addObject("wmList", list);
				mav.addObject("wmListSize", list.size());
				mav.addObject("orderBy", orderBy);
			}

			mav.addObject("state", stcd);
			mav.addObject("stName", stName);
			mav.addObject("district", dcode);
			mav.addObject("distName", distName);
			mav.addObject("platform", media);
			mav.addObject("mediaName", platform);
			mav.addObject("status", status);
			mav.addObject("fromdate", userdate);
			mav.addObject("todate", userdateto);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}

        return mav;
    }
    
    
    @RequestMapping(value = "/downloadWMSocialMediaCompAnalysisPDF", method = RequestMethod.POST)
	public ModelAndView downloadWMSocialMediaCompAnalysisPDF(HttpServletRequest request, HttpServletResponse response)
	{
    	int stcd = Integer.parseInt(request.getParameter("state"));
        int dcode = Integer.parseInt(request.getParameter("district"));
        int media = Integer.parseInt(request.getParameter("platform"));
        
        String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String mediaName= request.getParameter("mediaName");
        
		String status = request.getParameter("status");
        String userdate= request.getParameter("userdate");
		String userdateto= request.getParameter("userdateto");
		
		

        List<WMMediaReviewBean> list = WMSerice.getWMSocialMediaComDetails(stcd, dcode, media, status);
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 14, 14, 0);
			document.addTitle("WM6 - WMSocialMediaAnalysis");
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
			
			paragraph3 = new Paragraph("Report WM2 - Watershed Mahotsav Social Media Analysis", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(14);
		    paragraph3.setSpacingAfter(14);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(13);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(3);
		    if(userdate.equals("")) {
		    	CommonFunctions.insertCellHeader(table,"Platform : "+mediaName+"  Status : "+status, Element.ALIGN_LEFT, 13, 1, bf8Bold);
		    }else {
		    	CommonFunctions.insertCellHeader(table,"Platform : "+mediaName+"  Status : "+status +" From Date: "+ userdate+" To Date :"+userdateto, Element.ALIGN_LEFT, 13, 1, bf8Bold);
		    }
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Registration Number", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Platform", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Link", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Views", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Likes", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Comments", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Created Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Updated Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Status", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
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
			
			int k = 1;
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname()!= null?list.get(i).getStname():"", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname()!=null?list.get(i).getDistname():"", Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getReg_no(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getReg_name(), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getPlatform() , Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getMedia_url(), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, list.get(i).getNo_of_views() != null ? list.get(i).getNo_of_views().toString() : "0",   Element.ALIGN_RIGHT,1,1,bf8);
			        CommonFunctions.insertCell(table, list.get(i).getNo_of_likes() != null ? list.get(i).getNo_of_likes().toString() : "0",  Element.ALIGN_RIGHT,1, 1,bf8 );
			        CommonFunctions.insertCell(table, list.get(i).getNo_of_comments() != null ? list.get(i).getNo_of_comments().toString() : "0",    Element.ALIGN_RIGHT,1,1,bf8 );
			        CommonFunctions.insertCell(table, list.get(i).getCreated_date() != null ? list.get(i).getCreated_date().toString() : "", Element.ALIGN_RIGHT,1,1,bf8);
			        CommonFunctions.insertCell(table, list.get(i).getUpdated_date() != null ? list.get(i).getUpdated_date().toString() : "", Element.ALIGN_RIGHT,1,1,bf8);
			        CommonFunctions.insertCell(table, list.get(i).getStatus(), Element.ALIGN_RIGHT,1,1,bf8);
			            
					k++;
				}
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WM2- WMSocialMediaAnalysis.pdf");
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
	
    @RequestMapping(value = "/getTotNoOfScrnshtUploaded", method = RequestMethod.GET)
    public ModelAndView getTotNoOfScrnshtUploaded(HttpServletRequest request, HttpServletResponse response) {
    	session = request.getSession(true);
    	ModelAndView mav = new ModelAndView();
    	
		if (session != null && session.getAttribute("loginID") != null) {
			
			mav.addObject("menu", menuController.getMenuUserId(request));
			mav = new ModelAndView("mahotsav/wmTotNoOfScrnshtUploaded");

			List<WMMediaReviewBean> list = WMSerice.getTotNoOfScrnshtUploaded();
			mav.addObject("wmList", list);
			mav.addObject("wmListSize", list.size());
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}

        return mav;
    }
    
    @RequestMapping(value = "/downloadWMTotNoOfScrnshtUploadedPDF", method = RequestMethod.POST)
	public ModelAndView downloadWMTotNoOfScrnshtUploadedPDF(HttpServletRequest request, HttpServletResponse response)
	{
		
		

        List<WMMediaReviewBean> list = WMSerice.getTotNoOfScrnshtUploaded();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 14, 14, 0);
			document.addTitle("WM6 - WMSocialMediaAnalysis");
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
			
			paragraph3 = new Paragraph("Report WM3 - State Wise Total Watershed Mahotsav Social Media Views Screenshot Uploaded", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(14);
		    paragraph3.setSpacingAfter(14);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(4);
		    table.setWidths(new int[]{2, 8, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(3);
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Number of Screenshot Uploaded", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Videos", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Photos", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			int k = 1;
			Integer totvideos = 0;
			Integer totphotos = 0;
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname()!= null?list.get(i).getStname():"", Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotvideos().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotphotos().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
			            
					totvideos = totvideos + list.get(i).getTotvideos();
					totphotos = totphotos + list.get(i).getTotphotos();
					k++;
				}
			CommonFunctions.insertCell(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
			CommonFunctions.insertCell(table, totvideos.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell(table, totphotos.toString(), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WM3- WM Total Uploaded Screenshot.pdf");
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
    
    @RequestMapping(value = "/downloadExcelWMTotNoOfScrnshtUploaded", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelWMTotNoOfScrnshtUploaded(HttpServletRequest request, HttpServletResponse response) 
	{
		
    	List<WMMediaReviewBean> list = WMSerice.getTotNoOfScrnshtUploaded();
		
		  
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report WM3 - State Wise Total Watershed Mahotsav Social Media Views Screenshot Uploaded");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report WM3 - State Wise Total Watershed Mahotsav Social Media Views Screenshot Uploaded";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 3, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,3); 
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
			
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Number of Screenshot Uploaded");  
			cell.setCellStyle(style);
			
			rowhead.createCell(3).setCellStyle(style);
			
			rowhead = sheet.createRow(6);
			rowhead.createCell(0).setCellStyle(style);
			rowhead.createCell(1).setCellStyle(style);
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Videos");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total Photos");  
			cell.setCellStyle(style);
			
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<4;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 8;
	        Integer totvideos = 0;
	        Integer totphotos = 0;
	        for(WMMediaReviewBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getTotvideos());
	        	row.createCell(3).setCellValue(bean.getTotphotos());
	        	
	        	totvideos = totvideos + bean.getTotvideos();
	        	totphotos = totphotos + bean.getTotphotos();
	        	
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
			
			Row row = sheet.createRow(rowno);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(2);
	        cell.setCellValue(totvideos);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(totphotos);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 3);
	        String fileName = "attachment; filename=Report WM3 - State Wise.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "mahotsav/wmTotNoOfScrnshtUploaded";
		
	}
    
	
	
}
