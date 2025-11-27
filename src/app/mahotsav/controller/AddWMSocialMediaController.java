package app.mahotsav.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.bind.annotation.*;
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

import app.common.CommonFunctions;
import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.model.WatershedMahotsavRegistration;
import app.mahotsav.service.AddWMSocialMediaService;
import app.model.IwmpMFinYear;
import app.model.IwmpMProject;
import app.projectevaluation.bean.FundUtilizationEvalReportBean;
import app.service.DistrictMasterService;
import app.service.StateMasterService;
import app.service.reports.WatershedYatraReportService;


@Controller
public class AddWMSocialMediaController {

    @Autowired
    private AddWMSocialMediaService addWMSocialMediaService;

    @Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired(required = true)
	WatershedYatraReportService ser;
	
    private Map<Integer, String> stateList;
	
	private Map<String, String> districtList;
	
	private Map<String, String> blockList;
	
	
	 @GetMapping("/getSocialMediaReport")
	    public ModelAndView getSocialMediaReport(
	            @RequestParam(value = "state", required = false) String userState,
	            @RequestParam(value = "district", required = false) String district) {

	        ModelAndView mav = new ModelAndView("mahotsav/socialMediaReport");

	        Map<Integer, String> stateList = stateMasterService.getAllState();
	        mav.addObject("stateList", stateList);
	        mav.addObject("state", userState);

	        if (userState != null && !userState.equals("") && !userState.equals("0")) {
	            Map<String, String> districtList = ser.getDistrictList(Integer.parseInt(userState));
	            mav.addObject("districtList", districtList);
	        }

	        mav.addObject("district", district);
	        List<SocialMediaReport> socialMediaList = addWMSocialMediaService.getSocialMediaReport(userState, district);
	        mav.addObject("socialMediaList", socialMediaList);
	        return mav;
	    }

	    @GetMapping("/getDistrictByState")
	    @ResponseBody
	    public Map<String, String> getDistrictByState(@RequestParam("stateCode") int stateCode) {
	        Map<String, String> districtMap = new LinkedHashMap<>();
	        if (stateCode > 0) {
	            districtMap = ser.getDistrictList(stateCode);
	        }
	        return districtMap;
	    }

    // Load Add Social Media form
    @GetMapping("/addWMSocialMedia")
    public ModelAndView addWMSocialMedia() {
        return new ModelAndView("mahotsav/addSocialMediaVideo");
    }
    
    @PostMapping("/verifyRegistration")
    @ResponseBody
    public String verifyRegistration(@RequestParam("regNo") String regNo, HttpSession session) {
        try {
            WatershedMahotsavRegistration reg = addWMSocialMediaService.findByUserRegNo(regNo);

            if (reg == null) {
                return "invalidReg";
            }

            // store regNo in session
            session.setAttribute("regNo", regNo);

            // return pipe-separated response
            return String.format("%s|%s|%s|%s",
                    reg.getRegName(),
                    reg.getPhno(),
                    reg.getEmail(),
                    reg.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "/getWMSocialMediaReport", method = RequestMethod.GET)
	public ModelAndView getWMSocialMediaReport(HttpServletRequest request) 
	{
		
		List<SocialMediaReport> list = new ArrayList<SocialMediaReport>();
		ModelAndView mav = new ModelAndView("mahotsav/WMSocialMediaReport");
		
		list = addWMSocialMediaService.getWMSocialMediaReport();
				
		mav.addObject("WMList",list);
		mav.addObject("WMListSize",list.size());
		
		return mav;
	}
    
    @RequestMapping(value = "/getDistWMSocialMediaReport", method = RequestMethod.GET)
	public ModelAndView getDistWMSocialMediaReport(HttpServletRequest request) {
		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
		
		List<SocialMediaReport> listD = new ArrayList<SocialMediaReport>();
		ModelAndView mav = new ModelAndView("mahotsav/DistWMSocialMediaReport"); 
		
		listD = addWMSocialMediaService.getDistWMSocialMediaReport(Integer.parseInt(stcd));
		
		mav.addObject("stcd",stcd);
		mav.addObject("stName",stName);
		mav.addObject("distName",distName);
		mav.addObject("WMDList",listD);
		mav.addObject("WMDListSize",listD.size());

		return mav;
	}
    
    @RequestMapping(value = "/downloadWMSocialMediaReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadWMSocialMediaReportPDF( HttpServletRequest request, HttpServletResponse response)
	{
    	List<SocialMediaReport> list = new ArrayList<SocialMediaReport>();
    	list = addWMSocialMediaService.getWMSocialMediaReport();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("SocialMediaReport");
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
				paragraph3 = new Paragraph("Report WM3 - State-wise Total Video Uploaded for the Social Media Competition ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(9);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				BigInteger totvidupl= BigInteger.valueOf(0);
				BigInteger totreguser= BigInteger.valueOf(0);
				BigInteger nofb= BigInteger.valueOf(0);
				BigInteger noyt= BigInteger.valueOf(0);
				BigInteger noinsta= BigInteger.valueOf(0);
				BigInteger notwit= BigInteger.valueOf(0);
				BigInteger nolinkedin= BigInteger.valueOf(0);
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Registered User", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Video Uploaded", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Facebook", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of YouTube", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Instagram", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Twitter", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of LinkedIn", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
						CommonFunctions.insertCell(table, list.get(i).getStname() , Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getTotal_registered_user().toString() , Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getTotal_video_uploaded().toString() , Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_facebook().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_youtube().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_instagram().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_twitter().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_linkedin().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						

						k=k+1;
						totreguser=totreguser.add(list.get(i).getTotal_registered_user()==null?BigInteger.ZERO:list.get(i).getTotal_registered_user());
						totvidupl=totvidupl.add(list.get(i).getTotal_video_uploaded()==null?BigInteger.ZERO:list.get(i).getTotal_video_uploaded());
						nofb=nofb.add(list.get(i).getNo_facebook()==null?BigInteger.ZERO:list.get(i).getNo_facebook());
						noyt=noyt.add(list.get(i).getNo_youtube()==null?BigInteger.ZERO:list.get(i).getNo_youtube());
						noinsta=noinsta.add(list.get(i).getNo_instagram()==null?BigInteger.ZERO:list.get(i).getNo_instagram());
						notwit=notwit.add(list.get(i).getNo_twitter()==null?BigInteger.ZERO:list.get(i).getNo_twitter());
						nolinkedin=nolinkedin.add(list.get(i).getNo_linkedin()==null?BigInteger.ZERO:list.get(i).getNo_linkedin());
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totreguser), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totvidupl), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(nofb), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(noyt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(noinsta), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(notwit), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(nolinkedin), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(list.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
					
						
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
		response.setHeader("Content-Disposition", "attachment;filename=MahotsavSocialMediaReport.pdf");
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
	
	@RequestMapping(value = "/downloadExcelWMSocialMediaReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelWMSocialMediaReport(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcd=request.getParameter("stcode"); 
		List<SocialMediaReport> list = new ArrayList<SocialMediaReport>();
    	list = addWMSocialMediaService.getWMSocialMediaReport();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report WM3 - State-wise Total Video Uploaded for the Social Media Competition");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
			
			String rptName = "Report WM3 - State-wise Total Video Uploaded for the Social Media Competition";
			
			
			String areaAmtValDetail = "";
			
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
			cell.setCellValue("Total Registered User");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total Video Uploaded");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(4);
			cell.setCellValue("No. of Facebook");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("No. of YouTube");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("No. of Instagram");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("No. of Twitter");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("No. of LinkedIn");  
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
	        BigInteger totreguser= BigInteger.valueOf(0);
	        BigInteger totvideoup= BigInteger.valueOf(0);
			BigInteger nofb= BigInteger.valueOf(0);
			BigInteger noyt= BigInteger.valueOf(0);
			BigInteger noinsta= BigInteger.valueOf(0);
			BigInteger notwit= BigInteger.valueOf(0);
			BigInteger nolinkedin = BigInteger.valueOf(0);
	        
	        for(SocialMediaReport bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getTotal_registered_user().doubleValue());
	        	row.createCell(3).setCellValue(bean.getTotal_video_uploaded().doubleValue());
	        	row.createCell(4).setCellValue(bean.getNo_facebook().doubleValue());
				row.createCell(5).setCellValue(bean.getNo_youtube().doubleValue());
	        	row.createCell(6).setCellValue(bean.getNo_instagram().doubleValue());
	        	row.createCell(7).setCellValue(bean.getNo_twitter().doubleValue());
	        	row.createCell(8).setCellValue(bean.getNo_linkedin().doubleValue());
	        	
	        	totreguser = totreguser.add(bean.getTotal_registered_user() == null ? BigInteger.ZERO : bean.getTotal_registered_user());
	        	totvideoup = totvideoup.add(bean.getTotal_video_uploaded() == null ? BigInteger.ZERO : bean.getTotal_video_uploaded());
	        	nofb = nofb.add(bean.getNo_facebook() == null ? BigInteger.ZERO : bean.getNo_facebook());
	        	noyt = noyt.add(bean.getNo_youtube() == null ? BigInteger.ZERO : bean.getNo_youtube());
	        	noinsta = noinsta.add(bean.getNo_instagram() == null ? BigInteger.ZERO : bean.getNo_instagram());
	        	notwit = notwit.add(bean.getNo_twitter() == null ? BigInteger.ZERO : bean.getNo_twitter());
	        	nolinkedin = nolinkedin.add(bean.getNo_linkedin() == null ? BigInteger.ZERO : bean.getNo_linkedin());
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
	        cell.setCellValue(totreguser.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totvideoup.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(nofb.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(noyt.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(noinsta.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(notwit.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(nolinkedin.doubleValue());
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 7);
	        String fileName = "attachment; filename=Report SocialMedia.xlsx";
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "/mahotsav/WMSocialMediaReport";
		
	}
}

