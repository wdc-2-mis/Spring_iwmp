package app.mahotsav.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
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
import app.bean.Login;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.service.WMSocialMediaAnalysisService;
import app.service.StateMasterService;

@Controller("WMSocialMediaAnalysisReport")
public class WMSocialMediaAnalysisReport {

	HttpSession session;
    @Autowired(required = true)
    MenuController menuController;

    @Autowired
    StateMasterService stateMasterService;

    @Autowired
    WMSocialMediaAnalysisService wmService;

    private Map<Integer, String> stateList;
    private Map<String, String> districtList;
    private Map<Integer, String> platformList;


    
    @RequestMapping(value = "/getWMSocialMediaAnalysisReport", method = RequestMethod.GET)
    public ModelAndView getWMSocialMediaAnalysisReport(HttpServletRequest request, HttpServletResponse response) {
    	session = request.getSession(true);
    	 ModelAndView mav = new ModelAndView("mahotsav/WMSocialMediaAnalysisReport");
    	
    	 if(session!=null && session.getAttribute("loginID")!=null) {
         stateList = stateMasterService.getAllState();
         districtList = new LinkedHashMap<>();
         platformList = wmService.getPlatformList();

        mav.addObject("stateList", stateList);
        mav.addObject("districtList", districtList);
        mav.addObject("platformList", platformList);

//        List<SocialMediaReport> list = wmService.getWMSocialMediaAnalysisReport(0, 0, 0, "views");
//        mav.addObject("wmList", null);
//        mav.addObject("wmListSize", null);

//        mav.addObject("state", 0);
//        mav.addObject("district", 0);
//        mav.addObject("platform", 0);
//        mav.addObject("orderBy", "views");
    	 }else {
 			mav = new ModelAndView("login");
 			mav.addObject("login", new Login());
 		}
       return mav;
    }

    @RequestMapping(value = "/wmSocialMediaAnalysisReport", method = RequestMethod.POST)
    public ModelAndView wmSocialMediaAnalysisReport(HttpServletRequest request) {
    	session = request.getSession(true);
        ModelAndView mav = new ModelAndView("mahotsav/WMSocialMediaAnalysisReport");
    	
        int stcd = Integer.parseInt(request.getParameter("state"));
        int dcode = Integer.parseInt(request.getParameter("district"));
        int media = Integer.parseInt(request.getParameter("platform"));

        String orderBy = request.getParameter("orderBy");

        if (orderBy == null || orderBy.trim().isEmpty()) {
            orderBy = "views";
        }
        String screenshotOnly = request.getParameter("screenshotOnly");
        boolean isScreenshotOnly = "Y".equals(screenshotOnly);
        
        System.out.println("Order By = " + orderBy);

        mav.addObject("stateList", stateMasterService.getAllState());
        mav.addObject("districtList", wmService.getDistrictList(stcd));
        mav.addObject("platformList", wmService.getPlatformList());

        List<SocialMediaReport> list;
        if (isScreenshotOnly) {
            list = wmService.getWmAnalysisReportScreenshot(stcd, dcode, media, orderBy);
        } else {
            list = wmService.getWMSocialMediaAnalysisReport(stcd, dcode, media, orderBy);
        }
        mav.addObject("wmList", list);
        mav.addObject("wmListSize", list.size());
        mav.addObject("screenshotOnly", screenshotOnly);
        mav.addObject("state", stcd);
        mav.addObject("district", dcode);
        mav.addObject("platform", media);
        mav.addObject("orderBy", orderBy); 


        return mav;
    }


    @RequestMapping(value = "/getDistrictsByStateForWM", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getDistrictsByStateForWM(int state) {
        return wmService.getDistrictList(state);
    }

    @RequestMapping(value = "/getPlatformList", method = RequestMethod.POST)
    @ResponseBody
    public Map<Integer, String> getPlatformList() {
        return wmService.getPlatformList();
    }
    
    @RequestMapping(value = "/downloadPDFwmSocialMediaAnalysisReport", method = RequestMethod.POST)
    public ModelAndView downloadPDFwmSocialMediaAnalysisReport(
            HttpServletRequest request,
            HttpServletResponse response) {

        int stcd = Integer.parseInt(request.getParameter("state"));
        int dcode = Integer.parseInt(request.getParameter("district"));
        int media = Integer.parseInt(request.getParameter("platform"));

        String stName = request.getParameter("stName");
        String distName = request.getParameter("distName");
        String platformName = request.getParameter("mediaName");
        String orderBy = request.getParameter("orderBy");

        String screenshotOnly = request.getParameter("screenshotOnly");
        boolean isScreenshotOnly = "Y".equals(screenshotOnly);
        
        if (orderBy == null || orderBy.trim().isEmpty()) {
            orderBy = "views";
        }

        List<SocialMediaReport> list;
        if (isScreenshotOnly) {
            list = wmService.getWmAnalysisReportScreenshot(stcd, dcode, media, orderBy);
        } else {
            list = wmService.getWMSocialMediaAnalysisReport(stcd, dcode, media, orderBy);
        }
        System.out.println("PDF LIST SIZE = " + list.size());

        try {

            /* ================= PAGE SETUP ================= */
            Rectangle layout = new Rectangle(PageSize.A4.rotate());
            layout.setBackgroundColor(BaseColor.WHITE);

            Document document = new Document(layout, 25, 14, 14, 20);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            /* ================= COLORS ================= */
            BaseColor HEADER_BG = new BaseColor(0, 40, 80);        // NIC Dark Blue
            BaseColor HEADER_TEXT = new BaseColor(255, 255, 240);  // Ivory

            /* ================= FONTS ================= */
            Font deptFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLDITALIC);
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, HEADER_TEXT);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 8);

            /* ================= HEADER ================= */
            CommonFunctions.addHeader(document);

            Paragraph dept = new Paragraph(
                    "Department of Land Resources, Ministry of Rural Development\n",
                    deptFont
            );
            dept.setAlignment(Element.ALIGN_CENTER);
            dept.setSpacingAfter(6);

            Paragraph title = new Paragraph(
                    "Report SMC1 - Watershed Mahotsav Social Media Analysis as per Entries",
                    titleFont
            );
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);

            document.add(dept);
            document.add(title);

            /* ================= TABLE ================= */
            PdfPTable table = new PdfPTable(12);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
            table.setHeaderRows(2);

            /* ===== FILTER HEADER ROW ===== */
			/*
			 * PdfPCell filterCell = new PdfPCell(new Phrase( "State: " + stName +
			 * "   District: " + distName + "   Platform: " + platformName, headerFont ));
			 * filterCell.setColspan(12);
			 * filterCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * filterCell.setBackgroundColor(HEADER_BG);
			 * filterCell.setBorderColor(BaseColor.WHITE); filterCell.setPadding(6);
			 * table.addCell(filterCell);
			 */

            /* ===== COLUMN HEADERS ===== */
            String[] headers = {
                    "S.No.", "State", "District", "Reg. No", "Name", "Platform",
                    "Link", "Views", "Likes", "Comments", "Shares", "Image"
            };

            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(HEADER_BG);
                cell.setBorderColor(BaseColor.WHITE);
                cell.setPadding(4);
                table.addCell(cell);
            }

            /* ===== COLUMN NUMBERS ===== */
            for (int i = 1; i <= 12; i++) {
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(i), headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(HEADER_BG);
                cell.setBorderColor(BaseColor.WHITE);
                cell.setPadding(4);
                table.addCell(cell);
            }

            /* ================= DATA ================= */
            int k = 1;

            if (!list.isEmpty()) {
                for (SocialMediaReport r : list) {

                    table.addCell(new PdfPCell(new Phrase(String.valueOf(k++), normalFont)));
                    table.addCell(new PdfPCell(new Phrase(r.getStname(), normalFont)));
                    table.addCell(new PdfPCell(new Phrase(r.getDist_name(), normalFont)));
                    table.addCell(new PdfPCell(new Phrase(r.getUser_reg_no(), normalFont)));
                    table.addCell(new PdfPCell(new Phrase(r.getReg_name(), normalFont)));
                    table.addCell(new PdfPCell(new Phrase(r.getMedia_name(), normalFont)));
                    table.addCell(new PdfPCell(new Phrase(
                            r.getMedia_url() != null ? r.getMedia_url() : "", normalFont)));

                    table.addCell(new PdfPCell(new Phrase(
                            r.getNo_of_views() != null ? r.getNo_of_views().toString() : "", normalFont)));
                    table.addCell(new PdfPCell(new Phrase(
                            r.getNo_of_likes() != null ? r.getNo_of_likes().toString() : "", normalFont)));
                    table.addCell(new PdfPCell(new Phrase(
                            r.getNo_of_comments() != null ? r.getNo_of_comments().toString() : "", normalFont)));
                    table.addCell(new PdfPCell(new Phrase(
                            r.getNo_of_shares() != null ? r.getNo_of_shares().toString() : "", normalFont)));

                    table.addCell(new PdfPCell(new Phrase("view", normalFont)));
                }
            } else {
                PdfPCell noData = new PdfPCell(new Phrase("Data not found", normalFont));
                noData.setColspan(12);
                noData.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(noData);
            }

            document.add(table);

            /* ================= FOOTER ================= */
            PdfPTable footer = new PdfPTable(1);
            footer.setWidthPercentage(70);
            footer.setSpacingBefore(15f);

            CommonFunctions.insertCellPageHeader(
                    footer,
                    "wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. "
                            + "Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "
                            + CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
                    Element.ALIGN_LEFT, 1, 1, normalFont
            );

            document.add(footer);
            document.close();

            /* ================= RESPONSE ================= */
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "attachment;filename=Report_SMC1_SocialMedia_Analysis.pdf");
            response.setContentLength(baos.size());

            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @RequestMapping(value = "/downloadExcelwmSocialMediaAnalysisReport", method = RequestMethod.POST)
    @ResponseBody
    public String downloadExcelwmSocialMediaAnalysisReport(HttpServletRequest request,
                                                           HttpServletResponse response) {

        int stcd = Integer.parseInt(request.getParameter("state"));
        int dcode = Integer.parseInt(request.getParameter("district"));
        int media = Integer.parseInt(request.getParameter("platform"));

        String orderBy = request.getParameter("orderBy");
        if (orderBy == null || orderBy.trim().isEmpty()) {
            orderBy = "views";
        }

        String screenshotOnly = request.getParameter("screenshotOnly");
        boolean isScreenshotOnly = "Y".equals(screenshotOnly);

        List<SocialMediaReport> list = isScreenshotOnly
                ? wmService.getWmAnalysisReportScreenshot(stcd, dcode, media, orderBy)
                : wmService.getWMSocialMediaAnalysisReport(stcd, dcode, media, orderBy);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("WM Social Media Analysis");

        CellStyle headerStyle = CommonFunctions.getStyle(workbook);

        String rptName = "Report SMC1 - Watershed Mahotsav Social Media Analysis as per Entries";
        CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 11);
        CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, "", workbook);

        int headerRowIndex = 5;
        Row headerRow = sheet.createRow(headerRowIndex);

        String[] headers = {
            "S.No.", "State", "District", "Reg. No", "Name",
            "Platform", "Link", "Views", "Likes", "Comments", "Shares", "Image"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
            CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
        }

        Row numberingRow = sheet.createRow(headerRowIndex + 1);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = numberingRow.createCell(i);
            cell.setCellValue(i + 1);
            cell.setCellStyle(headerStyle);
            CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
        }

        int rowNo = headerRowIndex + 2;
        int sno = 1;

        for (SocialMediaReport r : list) {

            Row row = sheet.createRow(rowNo++);

            row.createCell(0).setCellValue(sno++);
            row.createCell(1).setCellValue(r.getStname());
            row.createCell(2).setCellValue(r.getDist_name());
            row.createCell(3).setCellValue(r.getUser_reg_no());
            row.createCell(4).setCellValue(r.getReg_name());
            row.createCell(5).setCellValue(r.getMedia_name());
            row.createCell(6).setCellValue(r.getMedia_url() != null ? r.getMedia_url() : "");
            row.createCell(7).setCellValue(r.getNo_of_views() != null ? r.getNo_of_views().doubleValue() : 0);
            row.createCell(8).setCellValue(r.getNo_of_likes() != null ? r.getNo_of_likes().doubleValue() : 0);
            row.createCell(9).setCellValue(r.getNo_of_comments() != null ? r.getNo_of_comments().doubleValue() : 0);
            row.createCell(10).setCellValue(r.getNo_of_shares() != null ? r.getNo_of_shares().doubleValue() : 0);
            row.createCell(11).setCellValue("view");
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);

        String fileName = "attachment; filename=Report_SMC1_Social_Media_Analysis.xlsx";
        CommonFunctions.downloadExcel(response, workbook, fileName);

        return "mahotsav/WMSocialMediaAnalysisReport";
    }

}