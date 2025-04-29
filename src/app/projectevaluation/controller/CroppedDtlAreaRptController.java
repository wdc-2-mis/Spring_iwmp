package app.projectevaluation.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import java.io.IOException;
import app.common.CommonFunctions;
import app.projectevaluation.bean.CroppedDetailBean;
import app.projectevaluation.service.CroppedDtlRptService;

@Controller("croppedDtlAreaRptController")
public class CroppedDtlAreaRptController {
	
	HttpSession session;
	
	@Autowired
	CroppedDtlRptService cropservice;
	
	@RequestMapping(value = "/croppedDtlAreaRpt", method = RequestMethod.GET)
	public ModelAndView getcroppedDtlAreaRpt(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("projectEvaluation/croppedDetailAreaRpt");
		mav.addObject("stwiseAreacroppeddtl",cropservice.getcroppedDtlAreaDtl());
		return mav;
	}

	@RequestMapping(value = "/croppedDtlAreaOtherRpt", method = RequestMethod.GET)
	public ModelAndView getcroppedDtlAreaOtherRpt(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("projectEvaluation/croppedDetailAreaOthRpt");
		mav.addObject("stwiseAreacroppedothsdtl",cropservice.getcroppedDtlAreaOthsDtl());
		return mav;
	}
	
	@RequestMapping(value = "/downloadStWiseCropDtlAreaPDF", method = RequestMethod.POST)
	public void downloadStWiseCropDtlAreaPDF(HttpServletRequest request, HttpServletResponse response) {
	    List<CroppedDetailBean> list = cropservice.getcroppedDtlAreaDtl();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Document document = null;
	    PdfWriter writer = null;

	    try {
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        document = new Document(layout, 25, 10, 10, 0);
	        writer = PdfWriter.getInstance(document, baos);

	        document.open();
	        Font f1 = new Font(Font.FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC);
	        Font f3 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.BOLD);
	        Font bf8 = new Font(Font.FontFamily.HELVETICA, 8);
	        Font bf8Bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
	        Font bf10Bold = new Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD);

	        Paragraph heading1 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
	        Paragraph heading2 = new Paragraph("Report MT1 - State wise Cropped Detail Area", f3);
	        heading1.setAlignment(Element.ALIGN_CENTER);
	        heading2.setAlignment(Element.ALIGN_CENTER);
	        heading1.setSpacingAfter(10);
	        heading2.setSpacingAfter(10);

	        CommonFunctions.addHeader(document); // Assuming you already have a header function

	        document.add(heading1);
	        document.add(heading2);

	        PdfPTable table = new PdfPTable(15);
	        table.setWidths(new int[]{3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(100);
	        table.setHeaderRows(3);

	       CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Projects", Element.ALIGN_CENTER, 1, 3, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Area covered under diversified crops/ change in cropping system", Element.ALIGN_CENTER, 2, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Area brought from Nil/Single crop to double or more crop", Element.ALIGN_CENTER, 4, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Water Harvesting Structure (WHS) constructed /rejuvenated", Element.ALIGN_CENTER, 2, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Area Covered with soil and Moisture", Element.ALIGN_CENTER, 2, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Area of degraded land covered /rainfed area developed", Element.ALIGN_CENTER, 2, 2, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "Nil to single crops", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Single to double or more crop", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			
			
	        for (int i = 0; i < 6; i++) {
	            CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	            CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        }

	        for (int i = 1; i <= 15; i++) {
	            CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        }

	        int k = 1;
	        int totalproject = 0;
	        BigDecimal totalproj_div_change = BigDecimal.ZERO;
	        BigDecimal totalcon_div_change = BigDecimal.ZERO;
	        BigDecimal totalproj_nil_single = BigDecimal.ZERO;
	        BigDecimal totalcon_nil_single = BigDecimal.ZERO;
	        BigDecimal totalproj_single_double = BigDecimal.ZERO;
	        BigDecimal totalcon_single_double = BigDecimal.ZERO;
	        int totalproj_whs_rejuvenated = 0;
	        int totalcon_whs_rejuvenated = 0;
	        BigDecimal totalproj_soil_moist = BigDecimal.ZERO;
	        BigDecimal totalcon_soil_moist = BigDecimal.ZERO;
	        BigDecimal totalproj_deg_rain = BigDecimal.ZERO;
	        BigDecimal totalcon_deg_rain = BigDecimal.ZERO;

	        for (CroppedDetailBean bean : list) {
	            CommonFunctions.insertCell(table, String.valueOf(k++), Element.ALIGN_LEFT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, String.valueOf(bean.getProject()), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getProj_div_change().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getCon_div_change().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getProj_nil_single().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getCon_nil_single().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getProj_single_double().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getCon_single_double().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, String.valueOf(bean.getProj_whs_rejuvenated()), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, String.valueOf(bean.getCon_whs_rejuvenated()), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getProj_soil_moist().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getCon_soil_moist().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getProj_deg_rain().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getCon_deg_rain().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);

	            // Totals
	            totalproject += bean.getProject();
	            totalproj_div_change = totalproj_div_change.add(bean.getProj_div_change());
	            totalcon_div_change = totalcon_div_change.add(bean.getCon_div_change());
	            totalproj_nil_single = totalproj_nil_single.add(bean.getProj_nil_single());
	            totalcon_nil_single = totalcon_nil_single.add(bean.getCon_nil_single());
	            totalproj_single_double = totalproj_single_double.add(bean.getProj_single_double());
	            totalcon_single_double = totalcon_single_double.add(bean.getCon_single_double());
	            totalproj_whs_rejuvenated += bean.getProj_whs_rejuvenated();
	            totalcon_whs_rejuvenated += bean.getCon_whs_rejuvenated();
	            totalproj_soil_moist = totalproj_soil_moist.add(bean.getProj_soil_moist());
	            totalcon_soil_moist = totalcon_soil_moist.add(bean.getCon_soil_moist());
	            totalproj_deg_rain = totalproj_deg_rain.add(bean.getProj_deg_rain());
	            totalcon_deg_rain = totalcon_deg_rain.add(bean.getCon_deg_rain());
	        }

	        if (list.isEmpty()) {
	            CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 15, 1, bf8);
	        } else {
	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totalproject), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totalproj_div_change), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totalcon_div_change), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totalproj_nil_single), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totalcon_nil_single), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totalproj_single_double), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totalcon_single_double), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totalproj_whs_rejuvenated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totalcon_whs_rejuvenated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totalproj_soil_moist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totalcon_soil_moist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totalproj_deg_rain), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totalcon_deg_rain), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	        }

	        document.add(table);

	        // Footer
	        PdfPTable footer = new PdfPTable(1);
	        footer.setWidthPercentage(70);
	        footer.setSpacingBefore(15f);
	        CommonFunctions.insertCellPageHeader(
	            footer,
	            "wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR " +
	            CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
	            Element.ALIGN_LEFT,
	            1,
	            4,
	            bf8
	        );
	        document.add(footer);

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (document != null && document.isOpen()) document.close();
	    }

	    try {
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=Report_MT1_Projects.pdf");
	        response.setContentLength(baos.size());
	        response.setHeader("Expires", "0");
	        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        response.setHeader("Pragma", "public");

	        OutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();
	        os.close();
	    } catch (IOException ioEx) {
	        ioEx.printStackTrace();
	    }
	}


	@RequestMapping(value = "/downloadStWiseCropDtlAreaOthPDF", method = RequestMethod.POST)
	public void downloadStWiseCropDtlAreaOthPDF(HttpServletRequest request, HttpServletResponse response) {
	    List<CroppedDetailBean> list = cropservice.getcroppedDtlAreaOthsDtl();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Document document = null;
	    PdfWriter writer = null;

	    try {
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        document = new Document(layout, 25, 10, 10, 0);
	        writer = PdfWriter.getInstance(document, baos);

	        document.open();
	        Font f1 = new Font(Font.FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC);
	        Font f3 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.BOLD);
	        Font bf8 = new Font(Font.FontFamily.HELVETICA, 8);
	        Font bf8Bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
	        Font bf10Bold = new Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD);

	        Paragraph heading1 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);
	        Paragraph heading2 = new Paragraph("Report MT2-State wise Cropped Others Detail Report", f3);
	        heading1.setAlignment(Element.ALIGN_CENTER);
	        heading2.setAlignment(Element.ALIGN_CENTER);
	        heading1.setSpacingAfter(10);
	        heading2.setSpacingAfter(10);

	        CommonFunctions.addHeader(document); // Assuming you already have a header function

	        document.add(heading1);
	        document.add(heading2);

	        PdfPTable table = new PdfPTable(18);
	        table.setWidths(new int[]{3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(100);
	        table.setHeaderRows(3);
	        
	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Projects", Element.ALIGN_CENTER, 1, 3, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Gross Cropped Aream", Element.ALIGN_CENTER, 3, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Area of horticulture crop", Element.ALIGN_CENTER, 3, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Net Sown Area", Element.ALIGN_CENTER, 3, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Cropping Intensity (%)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Area under protective irrigation", Element.ALIGN_CENTER, 3, 1, bf8Bold);

	        
	        for (int i = 0; i < 5; i++) {
	            CommonFunctions.insertCellHeader(table, "Project Area Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
	            CommonFunctions.insertCellHeader(table, "Controlled Area Details", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	         }
	        for (int i = 0; i < 5; i++) {
	            CommonFunctions.insertCellHeader(table, "Pre Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	            CommonFunctions.insertCellHeader(table, "Mid Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	         }
	        int k = 1;
	        int totalproject = 0;
	        BigDecimal total_pre_gross_cropped = BigDecimal.ZERO;
            BigDecimal total_mid_gross_cropped = BigDecimal.ZERO;
	        BigDecimal total_control_gross_cropped = BigDecimal.ZERO;
	        BigDecimal pre_horticulture = BigDecimal.ZERO;
	        BigDecimal mid_horticulture = BigDecimal.ZERO;
	        BigDecimal control_horticulture = BigDecimal.ZERO;
	        BigDecimal pre_netsown = BigDecimal.ZERO;
	        BigDecimal mid_netsown = BigDecimal.ZERO;
	        BigDecimal control_netsown = BigDecimal.ZERO;
	        BigDecimal pre_cropping = BigDecimal.ZERO;
	        BigDecimal mid_cropping = BigDecimal.ZERO;
	        BigDecimal control_cropping = BigDecimal.ZERO;
	        BigDecimal pre_protective = BigDecimal.ZERO;
	        BigDecimal mid_protective = BigDecimal.ZERO;
	        BigDecimal control_protective = BigDecimal.ZERO;
	        
	        for (CroppedDetailBean bean : list) {
	            CommonFunctions.insertCell(table, String.valueOf(k++), Element.ALIGN_LEFT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, String.valueOf(bean.getProject()), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getTotal_pre_gross_cropped().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getTotal_mid_gross_cropped().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getTotal_control_gross_cropped().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            
	            CommonFunctions.insertCell(table, bean.getPre_horticulture().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getMid_horticulture().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getControl_horticulture().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            
	            CommonFunctions.insertCell(table, bean.getPre_netsown().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getMid_netsown().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getControl_netsown().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            
	            CommonFunctions.insertCell(table, bean.getPre_cropping().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getMid_cropping().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getControl_cropping().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            
	            CommonFunctions.insertCell(table, bean.getPre_protective().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getMid_protective().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            CommonFunctions.insertCell(table, bean.getControl_protective().setScale(4, RoundingMode.HALF_UP).toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
	            
	            totalproject += bean.getProject();
	            total_pre_gross_cropped = total_pre_gross_cropped.add(bean.getTotal_pre_gross_cropped());
	            total_mid_gross_cropped = total_mid_gross_cropped.add(bean.getTotal_mid_gross_cropped());
	            total_control_gross_cropped = total_control_gross_cropped.add(bean.getTotal_control_gross_cropped());
	            
	            pre_horticulture = pre_horticulture.add(bean.getPre_horticulture());
	            mid_horticulture = mid_horticulture.add(bean.getMid_horticulture());
	            control_horticulture = control_horticulture.add(bean.getControl_horticulture());
	            
	            pre_netsown = pre_netsown.add(bean.getPre_netsown());
	            mid_netsown = mid_netsown.add(bean.getMid_netsown());
	            control_netsown = control_netsown.add(bean.getControl_netsown());
	            
	            pre_cropping = pre_cropping.add(bean.getPre_cropping());
	            mid_cropping = mid_cropping.add(bean.getMid_cropping());
	            control_cropping = control_cropping.add(bean.getControl_cropping());
	            
	            pre_protective = pre_protective.add(bean.getPre_protective());
	            mid_protective = mid_protective.add(bean.getMid_protective());
	            control_protective = control_protective.add(bean.getControl_protective());
	        }
	        
	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totalproject), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", total_pre_gross_cropped), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", total_mid_gross_cropped), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", total_control_gross_cropped), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", pre_horticulture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", mid_horticulture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", control_horticulture), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", pre_netsown), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", mid_netsown), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", control_netsown), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", pre_cropping), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", mid_cropping), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", control_cropping), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", pre_protective), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", mid_protective), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", control_protective), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            document.add(table);

	            
	            PdfPTable footer = new PdfPTable(1);
		        footer.setWidthPercentage(70);
		        footer.setSpacingBefore(15f);
		        CommonFunctions.insertCellPageHeader(
		            footer,
		            "wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR " +
		            CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
		            Element.ALIGN_LEFT,
		            1,
		            4,
		            bf8
		        );
		        document.add(footer);

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        if (document != null && document.isOpen()) document.close();
		    }

		    try {
		        response.setContentType("application/pdf");
		        response.setHeader("Content-Disposition", "attachment; filename=Report_MT2_Projects.pdf");
		        response.setContentLength(baos.size());
		        response.setHeader("Expires", "0");
		        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		        response.setHeader("Pragma", "public");

		        OutputStream os = response.getOutputStream();
		        baos.writeTo(os);
		        os.flush();
		        os.close();
		    } catch (IOException ioEx) {
		        ioEx.printStackTrace();
		    }
		}
}
