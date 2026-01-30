package app.mahotsav.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import app.bean.Login;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.mahotsav.bean.InaugurationMahotsavBean;
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

        List<SocialMediaReport> list = wmService.getWMSocialMediaAnalysisReport(0, 0, 0, "views");
        mav.addObject("wmList", list);
        mav.addObject("wmListSize", list.size());

        mav.addObject("state", 0);
        mav.addObject("district", 0);
        mav.addObject("platform", 0);
        mav.addObject("orderBy", "views");
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

        System.out.println("Order By = " + orderBy);

        mav.addObject("stateList", stateMasterService.getAllState());
        mav.addObject("districtList", wmService.getDistrictList(stcd));
        mav.addObject("platformList", wmService.getPlatformList());

        List<SocialMediaReport> list =
                wmService.getWMSocialMediaAnalysisReport(stcd, dcode, media, orderBy);

        mav.addObject("wmList", list);
        mav.addObject("wmListSize", list.size());

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

        if (orderBy == null || orderBy.trim().isEmpty()) {
            orderBy = "views";
        }

        List<SocialMediaReport> list =
                wmService.getWMSocialMediaAnalysisReport(stcd, dcode, media, orderBy);

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
            BaseColor HEADER_BG = new BaseColor(0, 70, 140);        // NIC Dark Blue
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
                    "Report WM5 - Watershed Mahotsav Social Media Analysis as per Entries",
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
            table.setHeaderRows(3);

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
                    "attachment;filename=Report_WM5_SocialMedia_Analysis.pdf");
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


//    @RequestMapping(value = "/downloadPDFwmSocialMediaAnalysisReport", method = RequestMethod.POST)
//	public ModelAndView downloadPDFwmSocialMediaAnalysisReport(HttpServletRequest request, HttpServletResponse response)
//	{
//    	int stcd = Integer.parseInt(request.getParameter("state"));
//        int dcode = Integer.parseInt(request.getParameter("district"));
//        int media = Integer.parseInt(request.getParameter("platform"));
//
//        String stName = request.getParameter("stName");
//        String distName = request.getParameter("distName");
//        String platformName = request.getParameter("mediaName");
////        String orderBy = request.getParameter("orderByVal");
//        String orderBy = request.getParameter("orderBy");
//
//        if (orderBy == null || orderBy.trim().isEmpty()) {
//            orderBy = "views";
//        }
//
//        
////        if (orderBy == null || orderBy.trim().isEmpty()) {
////            orderBy = "views";
////        }
////        List<SocialMediaReport> list;
////        if(dcode == 0) {
////            // Fetch all districts for selected state
////            list = wmService.getWMSocialMediaAnalysisReport(stcd, 0, media, orderBy);
////        } else {
////            // Fetch for specific district
////            list = wmService.getWMSocialMediaAnalysisReport(stcd, dcode, media, orderBy);
////        }
//        System.out.println("PDF district code = " + dcode+"fgfdg  "+distName);
//        List<SocialMediaReport> list;
//
//        if (dcode == 0) {
//            list = wmService.getWMSocialMediaAnalysisReport(stcd, 0, media, orderBy);
//        } else {
//            list = wmService.getWMSocialMediaAnalysisReport(stcd, dcode, media, orderBy);
//        }
//
//        System.out.println("PDF LIST SIZE = " + list.size());
//
////        List<SocialMediaReport> list =
////                wmService.getWMSocialMediaAnalysisReport(stcd, dcode, media, orderBy);
//        System.out.println("PDF Header -> State: " + stName + " District: " + distName + " Platform: " + platformName);
//        System.out.println("PDF PARAMS => "
//        	    + "state=" + stcd
//        	    + ", district=" + dcode
//        	    + ", media=" + media
//        	    + ", orderBy=" + orderBy
//        	);
//
//		try {
//			
//			Rectangle layout = new Rectangle(PageSize.A4.rotate());
//			layout.setBackgroundColor(new BaseColor(255, 255, 255));
//			Document document = new Document(layout, 25, 14, 14, 0);
//			document.addTitle("WM5 - WMSocialMediaAnalysis");
//			document.addCreationDate();
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			PdfWriter writer=PdfWriter.getInstance(document, baos);
//			document.open();
//			
//			Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC );
//			Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD );
//			Font bf8 = new Font(FontFamily.HELVETICA, 8);
//			Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
//			Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);
//			
//			PdfPTable table = null;
//			document.newPage();
//			Paragraph paragraph3 = null;
//			Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
//			
//			paragraph3 = new Paragraph("Report WM5 - Watershed Mahotsav Social Media Analysis as per Entries", f3);
//			
//			paragraph2.setAlignment(Element.ALIGN_CENTER);
//		    paragraph3.setAlignment(Element.ALIGN_CENTER);
//		    paragraph2.setSpacingAfter(14);
//		    paragraph3.setSpacingAfter(14);
//		    CommonFunctions.addHeader(document);
//		    document.add(paragraph2);
//		    document.add(paragraph3);
//		    table = new PdfPTable(12);
//		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
//		    table.setWidthPercentage(100);
//		    table.setSpacingBefore(0f);
//		    table.setSpacingAfter(0f);
//		    table.setHeaderRows(4);
//		    CommonFunctions.insertCellHeader( table, "State: " + stName + "   District: " + distName +"   Platform: " + platformName,Element.ALIGN_LEFT, 12, 1, bf8Bold);
//		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "State", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "District", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "Reg. No", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "Platform", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "Link", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "Views", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "Likes", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "Comments", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "Shares", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "Image", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			
//			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "10", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "11", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			CommonFunctions.insertCellHeader(table, "12", Element.ALIGN_CENTER, 1, 1, bf8Bold);
//			
//			int k = 1;
//			if(list.size()!=0)
//				for(int i=0;i<list.size();i++)
//				{
//					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_RIGHT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, list.get(i).getUser_reg_no(), Element.ALIGN_RIGHT, 1, 1, bf8);
//					CommonFunctions.insertCell(table, list.get(i).getReg_name(), Element.ALIGN_RIGHT, 1, 1, bf8);
//			        CommonFunctions.insertCell(table, list.get(i).getMedia_name() , Element.ALIGN_RIGHT, 1, 1, bf8);
//			        CommonFunctions.insertCell(table, (list.get(i).getMedia_url()), Element.ALIGN_RIGHT, 1, 1, bf8);
//			        BigInteger views = list.get(i).getNo_of_views();
//			        CommonFunctions.insertCell(table,views != null ? views.toString() : "",   Element.ALIGN_RIGHT,1,1,bf8);
//			        BigInteger likes = list.get(i).getNo_of_likes();
//			        CommonFunctions.insertCell(table,likes != null ? likes.toString() : "",  Element.ALIGN_RIGHT,1, 1,bf8 );
//			        BigInteger comments = list.get(i).getNo_of_comments();
//			        CommonFunctions.insertCell(   table,  comments != null ? comments.toString() : "",    Element.ALIGN_RIGHT,1,1,bf8 );
//			        BigInteger shares = list.get(i).getNo_of_shares();
//			        CommonFunctions.insertCell(table,shares != null ? shares.toString() : "", Element.ALIGN_RIGHT,1,1,bf8);
//			        CommonFunctions.insertCell(table, ("view"), Element.ALIGN_CENTER, 1, 1, bf8);
//			            
//					k++;
//				}
//				if(list.size()==0)
//					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 12, 1, bf8);
//				
//				
//		document.add(table);
//		table = new PdfPTable(1);
//		table.setWidthPercentage(70);
//		table.setSpacingBefore(15f);
//		table.setSpacingAfter(0f);
//		CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
//		CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
//		document.add(table);
//		document.close();
//		response.setContentType("application/pdf");
//		response.setHeader("Expires", "0");
//		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
//		response.setHeader("Content-Disposition", "attachment;filename=Report WM5- WMSocialMAnalysis.pdf");
//		response.setHeader("Pragma", "public");
//		response.setContentLength(baos.size());
//		OutputStream os = response.getOutputStream();
//		baos.writeTo(os);
//		os.flush();
//		os.close();
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		
//		return null;
//	}
//	
	
}
