package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import app.bean.BaseLineOutcomeBean;
import app.bean.BaselineStateWiseAreaDetailBean;
import app.common.CommonFunctions;
import app.service.StateMasterService;
import app.service.outcome.BaseLineOutcomeReportService;
import app.service.reports.AreaWiseBaseLineService;
import app.service.reports.BaselineAreawiseDtlService;

@Controller
public class AreaWiseBaselineController {
	
	HttpSession session;
	
	@Autowired
	AreaWiseBaseLineService areaWiseBaseLineService;

	@RequestMapping(value = "/baselineareasrvydtlreport", method = RequestMethod.GET)
	public ModelAndView getBaseLineStatewiseAreaSurvey(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/AreaWiseblServey");
		mav.addObject("statewiseAreaDtlList",areaWiseBaseLineService.getStatewiseAreaDetail());
		return mav;
	}
	
	@RequestMapping(value = "/bslserveydetailreport", method = RequestMethod.GET)
	public ModelAndView getBaseLineStateWiseAreaSurveyDetail(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/AreaWise2BlsServey");
		mav.addObject("statewiseAreaDtlList",areaWiseBaseLineService.getStateWiseAreaDetail2());
		return mav;
	}
	
	@RequestMapping(value = "/distbslserveydetailreport", method = RequestMethod.GET)
	public ModelAndView getBaseLineDistWiseAreaSurveyDetail(HttpServletRequest request) {
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
		
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/DistWiseBlsDetails");
		
		list = areaWiseBaseLineService.getDistWiseAreaDetails(Integer.parseInt(stcd));
		
		mav.addObject("stcd",stcd);
		mav.addObject("stName",stName);
		mav.addObject("distName",distName);
		mav.addObject("DistWiseAreaDtlList",list);
		mav.addObject("DistWiseAreaDtlListSize",list.size());
		
		return mav;
	}
	

	@RequestMapping(value = "/downloadAreablsPDF", method = RequestMethod.POST)
	public ModelAndView downloadAreablsPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String state=request.getParameter("state");;
		
		List<BaselineStateWiseAreaDetailBean> listareablsBean = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineAreaReport");
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
			
				paragraph3 = new Paragraph("Report O5- State wise Comparison of Distribution of Area as Per Irrigation Status and Ownership Status as On Date with Baseline ", f3);
			
			//paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
//			addFooter(writer);
//			document.add(paragraph);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(18);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation_ach= BigDecimal.valueOf(0);
				BigDecimal assured_irrigation= BigDecimal.valueOf(0);
				BigDecimal assured_irrigation_ach= BigDecimal.valueOf(0);
				BigDecimal no_irrigation= BigDecimal.valueOf(0);
				BigDecimal no_irrigation_ach= BigDecimal.valueOf(0);
				BigDecimal private_owner= BigDecimal.valueOf(0);
				BigDecimal private_owner_ach= BigDecimal.valueOf(0);
				BigDecimal govt_owned= BigDecimal.valueOf(0);
				BigDecimal govt_owned_ach= BigDecimal.valueOf(0);
				BigDecimal community_owned= BigDecimal.valueOf(0);
				BigDecimal community_owned_ach= BigDecimal.valueOf(0);
				BigDecimal others_owned= BigDecimal.valueOf(0);
				BigDecimal others_owned_ach= BigDecimal.valueOf(0);
				
				listareablsBean=areaWiseBaseLineService.getStatewiseAreaDetail();
				
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 18, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Irrigation Status(Area)", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ownership(Area)", Element.ALIGN_CENTER, 8, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Protective Irrigation", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Assured Irrigation", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Irrigation", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Private", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Government", Element.ALIGN_CENTER, 2, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Community", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 2, 1, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int k=1;
				if(listareablsBean.size()!=0)
					for(int i=0;i<listareablsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProtective_irrigation() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProtective_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getAssured_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getAssured_irrigation_ach() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_irrigation_ach() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPrivate_owner() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPrivate_owner_ach() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, listareablsBean.get(i).getGovt_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getGovt_owned_ach()  .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getCommunity_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getCommunity_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getOthers_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getOthers_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						project_area=project_area.add(listareablsBean.get(i).getProject_area());
//						plot_area=plot_area.add(listareablsBean.get(i).getPlot_area());
						plot_area=plot_area.add(listareablsBean.get(i).getPlot_area());
						protective_irrigation=protective_irrigation.add(listareablsBean.get(i).getProtective_irrigation());
						protective_irrigation_ach=protective_irrigation_ach.add(listareablsBean.get(i).getProtective_irrigation_ach());
						assured_irrigation=assured_irrigation.add(listareablsBean.get(i).getAssured_irrigation());
						assured_irrigation_ach=assured_irrigation_ach.add(listareablsBean.get(i).getAssured_irrigation_ach());
						no_irrigation=no_irrigation.add(listareablsBean.get(i).getNo_irrigation());
						no_irrigation_ach=no_irrigation_ach.add(listareablsBean.get(i).getNo_irrigation_ach());
						private_owner=private_owner.add(listareablsBean.get(i).getPrivate_owner());
						private_owner_ach=private_owner_ach.add(listareablsBean.get(i).getPrivate_owner_ach());
						
						govt_owned=govt_owned.add(listareablsBean.get(i).getGovt_owned());
						govt_owned_ach=govt_owned_ach.add(listareablsBean.get(i).getGovt_owned_ach());
						community_owned=community_owned.add(listareablsBean.get(i).getCommunity_owned());
						community_owned_ach=community_owned_ach.add(listareablsBean.get(i).getCommunity_owned_ach());
						others_owned=others_owned.add(listareablsBean.get(i).getOthers_owned());
						others_owned_ach=others_owned_ach.add(listareablsBean.get(i).getOthers_owned_ach());

						
					}
//				System.out.println("hii"+treatable_area);
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(assured_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(assured_irrigation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_irrigation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(private_owner), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(private_owner_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(govt_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(govt_owned_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(community_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(community_owned_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_owned_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listareablsBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 18, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=report-O5.pdf");
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
	@RequestMapping(value = "/downloadblsurveyDetailPDF", method = RequestMethod.POST)
	public ModelAndView downloadblsurveyDetailPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String state=request.getParameter("state");;
		
		List<BaselineStateWiseAreaDetailBean> blsBean = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineAreaSurveyDetailReport");
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
			
				paragraph3 = new Paragraph("Report O8- State wise Comparison of Distribution of Total Area as Per Classification of Land and No. of Crops as On Date with Baseline ", f3);
			
			//paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
//			addFooter(writer);
//			document.add(paragraph);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(24);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(6);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland_ach= BigDecimal.valueOf(0);
				BigDecimal degraded= BigDecimal.valueOf(0);
				BigDecimal degraded_ach= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal rainfed_ach= BigDecimal.valueOf(0);
				BigDecimal plantation= BigDecimal.valueOf(0);
				BigDecimal plantation_ach= BigDecimal.valueOf(0);
				BigDecimal no_plantation= BigDecimal.valueOf(0);
				BigDecimal no_plantation_ach= BigDecimal.valueOf(0);
				BigDecimal others_classification= BigDecimal.valueOf(0);
				BigDecimal others_classification_ach= BigDecimal.valueOf(0);
				BigDecimal no_crop= BigDecimal.valueOf(0);
				BigDecimal no_crop_ach= BigDecimal.valueOf(0);
				BigDecimal single_crop= BigDecimal.valueOf(0);
				BigDecimal single_crop_ach= BigDecimal.valueOf(0);
				BigDecimal double_crop= BigDecimal.valueOf(0);
				BigDecimal double_crop_ach= BigDecimal.valueOf(0);
				BigDecimal multiple_crop= BigDecimal.valueOf(0);
				BigDecimal multiple_crop_ach= BigDecimal.valueOf(0);
				
				blsBean=areaWiseBaseLineService.getStateWiseAreaDetail2();
				
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 24, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 4, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 4, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 4, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 4, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Area)", Element.ALIGN_CENTER, 12, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No of Crops(Area)", Element.ALIGN_CENTER, 8, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultural Wasteland", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed Land", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Forest Land", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Others Classification", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Crops", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Single Crops", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Double Crops", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Multiple Crops", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Plantation", Element.ALIGN_CENTER, 2, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Plantation", Element.ALIGN_CENTER, 2, 1, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "22", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "23", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "24", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(blsBean.size()!=0)
					for(int i=0;i<blsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getProject_area()==null?"":blsBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
//						CommonFunctions.insertCell(table, blsBean.get(i).getPlot_area()==null?"":blsBean.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getPlot_area()==null?"":blsBean.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getCultivable_wasteland()==null?"":blsBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getCultivable_wasteland_ach()==null?"":blsBean.get(i).getCultivable_wasteland_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getDegraded()==null?"":blsBean.get(i).getDegraded().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getDegraded_ach()==null?"":blsBean.get(i).getDegraded_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getRainfed()==null?"":blsBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getRainfed_ach()==null?"":blsBean.get(i).getRainfed_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getPlantation()==null?"":blsBean.get(i).getPlantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getPlot_area()==null?"":blsBean.get(i).getPlantation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getPlantation_ach()==null?"":blsBean.get(i).getNo_plantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getNo_plantation_ach()==null?"":blsBean.get(i).getNo_plantation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getOthers_classification()==null?"":blsBean.get(i).getOthers_classification().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getOthers_classification_ach()==null?"":blsBean.get(i).getOthers_classification_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getNo_crop()==null?"":blsBean.get(i).getNo_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getNo_crop_ach()==null?"":blsBean.get(i).getNo_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getSingle_crop()==null?"":blsBean.get(i).getSingle_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getSingle_crop_ach()==null?"":blsBean.get(i).getSingle_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getDouble_crop()==null?"":blsBean.get(i).getDouble_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getDouble_crop_ach()==null?"":blsBean.get(i).getDouble_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getMultiple_crop()==null?"":blsBean.get(i).getMultiple_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getMultiple_crop_ach()==null?"":blsBean.get(i).getMultiple_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						
						k=k+1;
						project_area=project_area.add(blsBean.get(i).getProject_area()==null?BigDecimal.ZERO:blsBean.get(i).getProject_area());
						plot_area=plot_area.add(blsBean.get(i).getPlot_area()==null?BigDecimal.ZERO:blsBean.get(i).getPlot_area());
						cultivable_wasteland=cultivable_wasteland.add(blsBean.get(i).getCultivable_wasteland()==null?BigDecimal.ZERO:blsBean.get(i).getCultivable_wasteland());
						cultivable_wasteland_ach=cultivable_wasteland_ach.add(blsBean.get(i).getCultivable_wasteland_ach()==null?BigDecimal.ZERO:blsBean.get(i).getCultivable_wasteland_ach());
						degraded=degraded.add(blsBean.get(i).getDegraded()==null?BigDecimal.ZERO:blsBean.get(i).getDegraded());
						degraded_ach=degraded_ach.add(blsBean.get(i).getDegraded_ach()==null?BigDecimal.ZERO:blsBean.get(i).getDegraded_ach());
						rainfed=rainfed.add(blsBean.get(i).getRainfed()==null?BigDecimal.ZERO:blsBean.get(i).getRainfed());
						rainfed_ach=rainfed_ach.add(blsBean.get(i).getRainfed_ach()==null?BigDecimal.ZERO:blsBean.get(i).getRainfed_ach());
						plantation=plantation.add(blsBean.get(i).getPlantation()==null?BigDecimal.ZERO:blsBean.get(i).getPlantation());
						plantation_ach=plantation_ach.add(blsBean.get(i).getPlantation_ach()==null?BigDecimal.ZERO:blsBean.get(i).getPlantation_ach());
						no_plantation=no_plantation.add(blsBean.get(i).getNo_plantation()==null?BigDecimal.ZERO:blsBean.get(i).getNo_plantation());
						no_plantation_ach=no_plantation_ach.add(blsBean.get(i).getNo_plantation_ach()==null?BigDecimal.ZERO:blsBean.get(i).getNo_plantation_ach());
						others_classification=others_classification.add(blsBean.get(i).getOthers_classification()==null?BigDecimal.ZERO:blsBean.get(i).getOthers_classification());
						others_classification_ach=others_classification_ach.add(blsBean.get(i).getOthers_classification_ach()==null?BigDecimal.ZERO:blsBean.get(i).getOthers_classification_ach());
						no_crop=no_crop.add(blsBean.get(i).getNo_crop()==null?BigDecimal.ZERO:blsBean.get(i).getNo_crop());
						no_crop_ach=no_crop_ach.add(blsBean.get(i).getNo_crop_ach()==null?BigDecimal.ZERO:blsBean.get(i).getNo_crop_ach());
						single_crop=single_crop.add(blsBean.get(i).getSingle_crop()==null?BigDecimal.ZERO:blsBean.get(i).getSingle_crop());
						single_crop_ach=single_crop_ach.add(blsBean.get(i).getSingle_crop_ach()==null?BigDecimal.ZERO:blsBean.get(i).getSingle_crop_ach());
						double_crop=double_crop.add(blsBean.get(i).getDouble_crop()==null?BigDecimal.ZERO:blsBean.get(i).getDouble_crop());
						double_crop_ach=double_crop_ach.add(blsBean.get(i).getDouble_crop_ach()==null?BigDecimal.ZERO:blsBean.get(i).getDouble_crop_ach());
						multiple_crop=multiple_crop.add(blsBean.get(i).getMultiple_crop()==null?BigDecimal.ZERO:blsBean.get(i).getMultiple_crop());
						multiple_crop_ach=multiple_crop_ach.add(blsBean.get(i).getMultiple_crop_ach()==null?BigDecimal.ZERO:blsBean.get(i).getMultiple_crop_ach());
						

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_plantation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_classification), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_classification_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(single_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(single_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(double_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(double_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(multiple_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(multiple_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					if(blsBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 24, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-O8.pdf");
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
	

	@RequestMapping(value = "/downloadExcelbaselineareasrvydtlreport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelbaselineareasrvydtlreport(HttpServletRequest request, HttpServletResponse response) 
	{
		String state=request.getParameter("state");
		
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		list=areaWiseBaseLineService.getStatewiseAreaDetail();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O5- State wise Comparison of Distribution of Total Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O5- State wise Comparison of Distribution of Total Area as Per Irrigation Status and Ownership Status as On Date with Baseline";
			String areaAmtValDetail = "All area in ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 17, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,10,17); 
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
	
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Irrigation Status(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =5; i<10;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Ownership(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =11; i<18;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
					
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Protective Irrigation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(5).setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Assured Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(7).setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("No Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(9).setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Private");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(11).setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Government");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(13).setCellStyle(style);

			cell = rowhead1.createCell(14);
			cell.setCellValue("Community");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(15).setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(17).setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<4;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(4);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(5);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(6);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(7);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(8);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(9);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(10);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(11);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(13);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(14);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(16);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(17);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
					
	        
	        int sno = 1;
	        int rowno  =8;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totProtIrr = 0;
	        double totProtIrrAchv = 0;
	        double totAssIrr = 0;
	        double totAssIrrAchv = 0;
	        double totNoIrr = 0;
	        double totNoIrrAchv = 0;
	        double totPvtOwn = 0;
	        double totPvtOwnAchv = 0;
	        double totGovOwn = 0;
	        double totGovOwnAchv = 0;
	        double totComOwn = 0;
	        double totComOwnAchv = 0;
	        double totOtherOwn = 0;
	        double totOtherOwnAchv = 0;
	        
	        for(BaselineStateWiseAreaDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue()); 
	        	row.createCell(3).setCellValue(bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getProtective_irrigation()==null?0.0:bean.getProtective_irrigation().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getProtective_irrigation_ach()==null?0.0:bean.getProtective_irrigation_ach().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getAssured_irrigation()==null?0.0:bean.getAssured_irrigation().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getAssured_irrigation_ach()==null?0.0:bean.getAssured_irrigation_ach().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getNo_irrigation()==null?0.0:bean.getNo_irrigation().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getNo_irrigation_ach()==null?0.0:bean.getNo_irrigation_ach().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getPrivate_owner()==null?0.0:bean.getPrivate_owner().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getPrivate_owner_ach()==null?0.0:bean.getPrivate_owner_ach().doubleValue());
	        	row.createCell(12).setCellValue(bean.getGovt_owned()==null?0.0:bean.getGovt_owned().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getGovt_owned_ach()==null?0.0:bean.getGovt_owned_ach().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getCommunity_owned()==null?0.0:bean.getCommunity_owned().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getCommunity_owned_ach()==null?0.0:bean.getCommunity_owned_ach().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getOthers_owned()==null?0.0:bean.getOthers_owned().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getOthers_owned_ach()==null?0.0:bean.getOthers_owned_ach().doubleValue()); 

	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());
	        	totProtIrr = totProtIrr + (bean.getProtective_irrigation()==null?0.0:bean.getProtective_irrigation().doubleValue());
	        	totProtIrrAchv = totProtIrrAchv + (bean.getProtective_irrigation_ach()==null?0.0:bean.getProtective_irrigation_ach().doubleValue());
	        	totAssIrr = totAssIrr + (bean.getAssured_irrigation()==null?0.0:bean.getAssured_irrigation().doubleValue()); 
	        	totAssIrrAchv = totAssIrrAchv + (bean.getAssured_irrigation_ach()==null?0.0:bean.getAssured_irrigation_ach().doubleValue()); 
	        	totNoIrr = totNoIrr + (bean.getNo_irrigation()==null?0.0:bean.getNo_irrigation().doubleValue());
	        	totNoIrrAchv = totNoIrrAchv + (bean.getNo_irrigation_ach()==null?0.0:bean.getNo_irrigation_ach().doubleValue());
	        	totPvtOwn = totPvtOwn + (bean.getPrivate_owner()==null?0.0:bean.getPrivate_owner().doubleValue());
	        	totPvtOwnAchv = totPvtOwnAchv + (bean.getPrivate_owner_ach()==null?0.0:bean.getPrivate_owner_ach().doubleValue());
	        	totGovOwn = totGovOwn + (bean.getGovt_owned()==null?0.0:bean.getGovt_owned().doubleValue());
	        	totGovOwnAchv = totGovOwnAchv + (bean.getGovt_owned_ach()==null?0.0:bean.getGovt_owned_ach().doubleValue());
	        	totComOwn = totComOwn + (bean.getCommunity_owned()==null?0.0:bean.getCommunity_owned().doubleValue());
	        	totComOwnAchv = totComOwnAchv + (bean.getCommunity_owned_ach()==null?0.0:bean.getCommunity_owned_ach().doubleValue());
	        	totOtherOwn = totOtherOwn + (bean.getOthers_owned()==null?0.0:bean.getOthers_owned().doubleValue());
	        	totOtherOwnAchv = totOtherOwnAchv + (bean.getOthers_owned_ach()==null?0.0:bean.getOthers_owned_ach().doubleValue());

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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totProtIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totProtIrrAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totAssIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totAssIrrAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totNoIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totNoIrrAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totPvtOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totPvtOwnAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totGovOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totGovOwnAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totComOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totComOwnAchv); 
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOtherOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totOtherOwnAchv);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 17);
	        String fileName = "attachment; filename=Report O5- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/AreaWiseblServey";
		
	}
	
	@RequestMapping(value = "/downloadExcelbslserveydetailreport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelbslserveydetailreport(HttpServletRequest request, HttpServletResponse response) 
	{
		String state=request.getParameter("state");;
		
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		list=areaWiseBaseLineService.getStateWiseAreaDetail2();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O8- State wise Comparison of Distribution of Total Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O8-State wise Comparison of Distribution of Total Area as Per Classification of Land and No. of Crops as on Date with Baseline";
			String areaAmtValDetail = "All area in ha.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 23, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,8,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,8,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,8,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,8,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,15); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,16,23); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,6,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,10,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,14,15);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,16,17);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,18,19);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,20,21);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,22,23);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,10,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,12,13);
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Classification of Land(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =5; i<16;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(16);
			cell.setCellValue("No of Crops(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =17; i<24;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
					
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Cultural Wasteland");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead1.createCell(5).setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead1.createCell(7).setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Rainfed Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead1.createCell(9).setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Forest Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =11; i<14;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(14);
			cell.setCellValue("Others Classification");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead1.createCell(15).setCellStyle(style);

			cell = rowhead1.createCell(16);
			cell.setCellValue("No Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead1.createCell(17).setCellStyle(style);
			
			cell = rowhead1.createCell(18);
			cell.setCellValue("Single Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead1.createCell(19).setCellStyle(style);
			
			cell = rowhead1.createCell(20);
			cell.setCellValue("Double Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead1.createCell(21).setCellStyle(style);
			
			cell = rowhead1.createCell(22);
			cell.setCellValue("Multiple Crops");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead1.createCell(23).setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<10;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead2.createCell(10);
			cell.setCellValue("Plantation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead2.createCell(11).setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellValue("No Plantation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead2.createCell(13).setCellStyle(style);
			
			for(int i =14; i<24;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			
			Row rowhead3 = sheet.createRow(8);
			for(int i =0; i<4;i++) {
				rowhead3.createCell(i).setCellStyle(style);
			}
			cell = rowhead3.createCell(4);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(5);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(6);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(7);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(8);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(9);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(10);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(11);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(12);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(13);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(14);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(15);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(16);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(17);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(18);
			cell.setCellValue("As on Baseline");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(19);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(20);
			cell.setCellValue("As on Baseline");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(21);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(22);
			cell.setCellValue("As on Baseline");
			cell.setCellStyle(style);
			
			cell = rowhead3.createCell(23);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
					
	        
	        int sno = 1;
	        int rowno  = 9;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totCulWasteLand = 0;
	        double totCulWasteLandAchv = 0;
	        double totDegLand = 0;
	        double totDegLandAchv = 0;
	        double totRainfedLand = 0;
	        double totRainfedLandAchv = 0;
	        double totPlant = 0;
	        double totPlantAchv = 0;
	        double totNoPlant = 0;
	        double totNoPlantAchv = 0;
	        double totOtherClass = 0;
	        double totOtherClassAchv = 0;
	        double totNoCrp = 0;
	        double totNoCrpAchv = 0;
	        double totSinCrp = 0;
	        double totSinCrpAchv = 0;
	        double totDouCrp = 0;
	        double totDouCrpAchv = 0;
	        double totMulCrp = 0;
	        double totMulCrpAchv = 0;
	        
	        for(BaselineStateWiseAreaDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue()); 
	        	row.createCell(3).setCellValue(bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getCultivable_wasteland_ach()==null?0.0:bean.getCultivable_wasteland_ach().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getDegraded()==null?0.0:bean.getDegraded().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getDegraded_ach()==null?0.0:bean.getDegraded_ach().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getRainfed_ach()==null?0.0:bean.getRainfed_ach().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getPlantation()==null?0.0:bean.getPlantation().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getPlantation_ach()==null?0.0:bean.getPlantation_ach().doubleValue());
	        	row.createCell(12).setCellValue(bean.getNo_plantation()==null?0.0:bean.getNo_plantation().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getNo_plantation_ach()==null?0.0:bean.getNo_plantation_ach().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getOthers_classification()==null?0.0:bean.getOthers_classification().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getOthers_classification_ach()==null?0.0:bean.getOthers_classification_ach().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getNo_crop()==null?0.0:bean.getNo_crop().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getNo_crop_ach()==null?0.0:bean.getNo_crop_ach().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getSingle_crop()==null?0.0:bean.getSingle_crop().doubleValue());
	        	row.createCell(19).setCellValue(bean.getSingle_crop_ach()==null?0.0:bean.getSingle_crop_ach().doubleValue()); 
	        	row.createCell(20).setCellValue(bean.getDouble_crop()==null?0.0:bean.getDouble_crop().doubleValue()); 
	        	row.createCell(21).setCellValue(bean.getDouble_crop_ach()==null?0.0:bean.getDouble_crop_ach().doubleValue()); 
	        	row.createCell(22).setCellValue(bean.getMultiple_crop()==null?0.0:bean.getMultiple_crop().doubleValue()); 
	        	row.createCell(23).setCellValue(bean.getMultiple_crop_ach()==null?0.0:bean.getMultiple_crop_ach().doubleValue());
	        	
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());
	        	totCulWasteLand = totCulWasteLand + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totCulWasteLandAchv = totCulWasteLandAchv + (bean.getCultivable_wasteland_ach()==null?0.0:bean.getCultivable_wasteland_ach().doubleValue());
	        	totDegLand = totDegLand + (bean.getDegraded()==null?0.0:bean.getDegraded().doubleValue()); 
	        	totDegLandAchv = totDegLandAchv + (bean.getDegraded_ach()==null?0.0:bean.getDegraded_ach().doubleValue()); 
	        	totRainfedLand = totRainfedLand + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totRainfedLandAchv = totRainfedLandAchv + (bean.getRainfed_ach()==null?0.0:bean.getRainfed_ach().doubleValue());
	        	totPlant = totPlant + (bean.getPlantation()==null?0.0:bean.getPlantation().doubleValue());
	        	totPlantAchv = totPlantAchv + (bean.getPlantation_ach()==null?0.0:bean.getPlantation_ach().doubleValue());
	        	totNoPlant = totNoPlant + (bean.getNo_plantation()==null?0.0:bean.getNo_plantation().doubleValue());
	        	totNoPlantAchv = totNoPlantAchv + (bean.getNo_plantation_ach()==null?0.0:bean.getNo_plantation_ach().doubleValue());
	        	totOtherClass = totOtherClass + (bean.getOthers_classification()==null?0.0:bean.getOthers_classification().doubleValue());
	        	totOtherClassAchv = totOtherClassAchv + (bean.getOthers_classification_ach()==null?0.0:bean.getOthers_classification_ach().doubleValue());
	        	totNoCrp = totNoCrp + (bean.getNo_crop()==null?0.0:bean.getNo_crop().doubleValue());
	        	totNoCrpAchv = totNoCrpAchv + (bean.getNo_crop_ach()==null?0.0:bean.getNo_crop_ach().doubleValue());
	        	totSinCrp = totSinCrp + (bean.getSingle_crop()==null?0.0:bean.getSingle_crop().doubleValue());
	        	totSinCrpAchv = totSinCrpAchv + (bean.getSingle_crop_ach()==null?0.0:bean.getSingle_crop_ach().doubleValue());
	        	totDouCrp = totDouCrp + (bean.getDouble_crop()==null?0.0:bean.getDouble_crop().doubleValue());
	        	totDouCrpAchv = totDouCrpAchv + (bean.getDouble_crop_ach()==null?0.0:bean.getDouble_crop_ach().doubleValue());
	        	totMulCrp = totMulCrp + (bean.getMultiple_crop()==null?0.0:bean.getMultiple_crop().doubleValue());
	        	totMulCrpAchv = totMulCrpAchv + (bean.getMultiple_crop_ach()==null?0.0:bean.getMultiple_crop_ach().doubleValue());

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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCulWasteLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCulWasteLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totDegLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totDegLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totRainfedLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totRainfedLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totPlant);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totPlantAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totNoPlant);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totNoPlantAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totOtherClass);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totOtherClassAchv); 
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totNoCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totNoCrpAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totSinCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totSinCrpAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totDouCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(21);
	        cell.setCellValue(totDouCrpAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(22);
	        cell.setCellValue(totMulCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(23);
	        cell.setCellValue(totMulCrpAchv);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 23);
	        String fileName = "attachment; filename=Report O8- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/AreaWise2BlsServey";
		
	}
	

	@RequestMapping(value = "/downloadExcelbslserveydetail", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelbslserveydetail(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		list = areaWiseBaseLineService.getDistWiseAreaDetails(Integer.parseInt(stcd));
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report O8- District wise Comparison of Distribution of Total Area");   
			
		CellStyle style = CommonFunctions.getStyle(workbook);
	        
		String rptName = "Report O8- District wise Comparison of Distribution of Total Area as Per Classification of Land and No. of Crops as on Date with Baseline";
		String areaAmtValDetail = "";
			
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 23, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(5,5,0,11);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(5,5,12,23);
	    sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,9,0,0);
	    sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,9,1,1);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(6,9,2,2); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(6,9,3,3); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(6,6,4,15); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(6,6,16,23); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7,8,4,5); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7,8,6,7); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7,8,8,9); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7,7,10,13);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7,8,14,15);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7,8,16,17);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7,8,18,19);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7,8,20,21);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(7,8,22,23);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(8,8,10,11);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(8,8,12,13);
	    sheet.addMergedRegion(mergedRegion);
	
        
	    mergedRegion = new CellRangeAddress(list.size()+11,list.size()+11,0,1); 
	    sheet.addMergedRegion(mergedRegion);
	    
	    
	    Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State : "+ stName);  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
		for(int i = 1; i < 12; i++)
		{
			rowDetail.createCell(i).setCellStyle(style);
		}

		cell = rowDetail.createCell(12);
		cell.setCellValue("All area in ha.");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		for(int i = 13; i < 23; i++)
		{
			rowDetail.createCell(i).setCellStyle(style);
		}
		
	    
	    Row rowhead = sheet.createRow(6); 
			
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
		cell = rowhead.createCell(1);
		cell.setCellValue("District");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
		cell = rowhead.createCell(2);
		cell.setCellValue("Total Project Area");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
		cell = rowhead.createCell(3);
		cell.setCellValue("Plot Area");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
		cell = rowhead.createCell(4);
		cell.setCellValue("Classification of Land(Area)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
		for(int i = 5; i < 16; i++)
		{
			rowhead.createCell(i).setCellStyle(style);
		}
			
		cell = rowhead.createCell(16);
		cell.setCellValue("No of Crops(Area)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
		for(int i = 17; i < 24; i++) 
		{
			rowhead.createCell(i).setCellStyle(style);
		}
					
			
		Row rowhead1 = sheet.createRow(7);
		for(int i = 0; i < 4; i++) 
		{
			rowhead1.createCell(i).setCellStyle(style);
		}
			
		cell = rowhead1.createCell(4);
		cell.setCellValue("Cultural Wasteland");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		rowhead1.createCell(5).setCellStyle(style);
			
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Degraded Land");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		rowhead1.createCell(7).setCellStyle(style);
			
		cell = rowhead1.createCell(8);
		cell.setCellValue("Rainfed Land");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		rowhead1.createCell(9).setCellStyle(style);
			
		cell = rowhead1.createCell(10);
		cell.setCellValue("Forest Land");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
		for(int i = 11; i < 14; i++) 
		{
			rowhead1.createCell(i).setCellStyle(style);
		}
			
		cell = rowhead1.createCell(14);
		cell.setCellValue("Others Classification");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		rowhead1.createCell(15).setCellStyle(style);

		cell = rowhead1.createCell(16);
		cell.setCellValue("No Crops");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		rowhead1.createCell(17).setCellStyle(style);
			
		cell = rowhead1.createCell(18);
		cell.setCellValue("Single Crops");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		rowhead1.createCell(19).setCellStyle(style);
			
		cell = rowhead1.createCell(20);
		cell.setCellValue("Double Crops");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		rowhead1.createCell(21).setCellStyle(style);
			
		cell = rowhead1.createCell(22);
		cell.setCellValue("Multiple Crops");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		rowhead1.createCell(23).setCellStyle(style);
			
		
		Row rowhead2 = sheet.createRow(8);
		for(int i = 0; i < 10; i++) 
		{
			rowhead2.createCell(i).setCellStyle(style);
		}
			
		cell = rowhead2.createCell(10);
		cell.setCellValue("Plantation");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		rowhead2.createCell(11).setCellStyle(style);
			
		cell = rowhead2.createCell(12);
		cell.setCellValue("No Plantation");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		rowhead2.createCell(13).setCellStyle(style);
			
		for(int i = 14; i < 24; i++) 
		{
			rowhead2.createCell(i).setCellStyle(style);
		}
		
		
		Row rowhead3 = sheet.createRow(9);
		for(int i = 0; i < 4; i++) 
		{
			rowhead3.createCell(i).setCellStyle(style);
		}
			
		cell = rowhead3.createCell(4);
		cell.setCellValue("As on Baseline");  
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(5);
		cell.setCellValue("As on date");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(6);
		cell.setCellValue("As on Baseline");  
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(7);
		cell.setCellValue("As on date");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(8);
		cell.setCellValue("As on Baseline");  
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(9);
		cell.setCellValue("As on date");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(10);
		cell.setCellValue("As on Baseline");  
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(11);
		cell.setCellValue("As on date");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(12);
		cell.setCellValue("As on Baseline");  
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(13);
		cell.setCellValue("As on date");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(14);
		cell.setCellValue("As on Baseline");  
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(15);
		cell.setCellValue("As on date");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(16);
		cell.setCellValue("As on Baseline");  
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(17);
		cell.setCellValue("As on date");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(18);
		cell.setCellValue("As on Baseline");
		cell.setCellStyle(style);
		
		cell = rowhead3.createCell(19);
		cell.setCellValue("As on date");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(20);
		cell.setCellValue("As on Baseline");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(21);
		cell.setCellValue("As on date");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(22);
		cell.setCellValue("As on Baseline");
		cell.setCellStyle(style);
			
		cell = rowhead3.createCell(23);
		cell.setCellValue("As on date");
		cell.setCellStyle(style);
		
		
		Row rowhead4 = sheet.createRow(10);
		for(int i = 0; i < 24; i++) 
		{
			cell =rowhead4.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
	        int sno = 1;
	        int rowno  = 11;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totCulWasteLand = 0;
	        double totCulWasteLandAchv = 0;
	        double totDegLand = 0;
	        double totDegLandAchv = 0;
	        double totRainfedLand = 0;
	        double totRainfedLandAchv = 0;
	        double totPlant = 0;
	        double totPlantAchv = 0;
	        double totNoPlant = 0;
	        double totNoPlantAchv = 0;
	        double totOtherClass = 0;
	        double totOtherClassAchv = 0;
	        double totNoCrp = 0;
	        double totNoCrpAchv = 0;
	        double totSinCrp = 0;
	        double totSinCrpAchv = 0;
	        double totDouCrp = 0;
	        double totDouCrpAchv = 0;
	        double totMulCrp = 0;
	        double totMulCrpAchv = 0;
	        
	        
	        for(BaselineStateWiseAreaDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDist_name());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue()); 
	        	row.createCell(3).setCellValue(bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getCultivable_wasteland_ach()==null?0.0:bean.getCultivable_wasteland_ach().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getDegraded()==null?0.0:bean.getDegraded().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getDegraded_ach()==null?0.0:bean.getDegraded_ach().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getRainfed_ach()==null?0.0:bean.getRainfed_ach().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getPlantation()==null?0.0:bean.getPlantation().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getPlantation_ach()==null?0.0:bean.getPlantation_ach().doubleValue());
	        	row.createCell(12).setCellValue(bean.getNo_plantation()==null?0.0:bean.getNo_plantation().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getNo_plantation_ach()==null?0.0:bean.getNo_plantation_ach().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getOthers_classification()==null?0.0:bean.getOthers_classification().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getOthers_classification_ach()==null?0.0:bean.getOthers_classification_ach().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getNo_crop()==null?0.0:bean.getNo_crop().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getNo_crop_ach()==null?0.0:bean.getNo_crop_ach().doubleValue()); 
	        	row.createCell(18).setCellValue(bean.getSingle_crop()==null?0.0:bean.getSingle_crop().doubleValue());
	        	row.createCell(19).setCellValue(bean.getSingle_crop_ach()==null?0.0:bean.getSingle_crop_ach().doubleValue()); 
	        	row.createCell(20).setCellValue(bean.getDouble_crop()==null?0.0:bean.getDouble_crop().doubleValue()); 
	        	row.createCell(21).setCellValue(bean.getDouble_crop_ach()==null?0.0:bean.getDouble_crop_ach().doubleValue()); 
	        	row.createCell(22).setCellValue(bean.getMultiple_crop()==null?0.0:bean.getMultiple_crop().doubleValue()); 
	        	row.createCell(23).setCellValue(bean.getMultiple_crop_ach()==null?0.0:bean.getMultiple_crop_ach().doubleValue());
	        	
	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());
	        	totCulWasteLand = totCulWasteLand + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totCulWasteLandAchv = totCulWasteLandAchv + (bean.getCultivable_wasteland_ach()==null?0.0:bean.getCultivable_wasteland_ach().doubleValue());
	        	totDegLand = totDegLand + (bean.getDegraded()==null?0.0:bean.getDegraded().doubleValue()); 
	        	totDegLandAchv = totDegLandAchv + (bean.getDegraded_ach()==null?0.0:bean.getDegraded_ach().doubleValue()); 
	        	totRainfedLand = totRainfedLand + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totRainfedLandAchv = totRainfedLandAchv + (bean.getRainfed_ach()==null?0.0:bean.getRainfed_ach().doubleValue());
	        	totPlant = totPlant + (bean.getPlantation()==null?0.0:bean.getPlantation().doubleValue());
	        	totPlantAchv = totPlantAchv + (bean.getPlantation_ach()==null?0.0:bean.getPlantation_ach().doubleValue());
	        	totNoPlant = totNoPlant + (bean.getNo_plantation()==null?0.0:bean.getNo_plantation().doubleValue());
	        	totNoPlantAchv = totNoPlantAchv + (bean.getNo_plantation_ach()==null?0.0:bean.getNo_plantation_ach().doubleValue());
	        	totOtherClass = totOtherClass + (bean.getOthers_classification()==null?0.0:bean.getOthers_classification().doubleValue());
	        	totOtherClassAchv = totOtherClassAchv + (bean.getOthers_classification_ach()==null?0.0:bean.getOthers_classification_ach().doubleValue());
	        	totNoCrp = totNoCrp + (bean.getNo_crop()==null?0.0:bean.getNo_crop().doubleValue());
	        	totNoCrpAchv = totNoCrpAchv + (bean.getNo_crop_ach()==null?0.0:bean.getNo_crop_ach().doubleValue());
	        	totSinCrp = totSinCrp + (bean.getSingle_crop()==null?0.0:bean.getSingle_crop().doubleValue());
	        	totSinCrpAchv = totSinCrpAchv + (bean.getSingle_crop_ach()==null?0.0:bean.getSingle_crop_ach().doubleValue());
	        	totDouCrp = totDouCrp + (bean.getDouble_crop()==null?0.0:bean.getDouble_crop().doubleValue());
	        	totDouCrpAchv = totDouCrpAchv + (bean.getDouble_crop_ach()==null?0.0:bean.getDouble_crop_ach().doubleValue());
	        	totMulCrp = totMulCrp + (bean.getMultiple_crop()==null?0.0:bean.getMultiple_crop().doubleValue());
	        	totMulCrpAchv = totMulCrpAchv + (bean.getMultiple_crop_ach()==null?0.0:bean.getMultiple_crop_ach().doubleValue());

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
	        
	        Row row = sheet.createRow(list.size()+11);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCulWasteLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCulWasteLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totDegLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totDegLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totRainfedLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totRainfedLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totPlant);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totPlantAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totNoPlant);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totNoPlantAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totOtherClass);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totOtherClassAchv); 
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totNoCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totNoCrpAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(18);
	        cell.setCellValue(totSinCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(19);
	        cell.setCellValue(totSinCrpAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(20);
	        cell.setCellValue(totDouCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(21);
	        cell.setCellValue(totDouCrpAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(22);
	        cell.setCellValue(totMulCrp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(23);
	        cell.setCellValue(totMulCrpAchv);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size()+2, 23);
	        String fileName = "attachment; filename=Report O8- District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/DistWiseBlsDetails";
		
	}
	

	@RequestMapping(value = "/getDistAreaWiseblservey", method = RequestMethod.GET)
	public ModelAndView getDistAreaWiseblservey(HttpServletRequest request, @RequestParam("id") int stcd,  @RequestParam("stname") String stname) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();     //    getdistwiseAreaDetailO5
		mav = new ModelAndView("reports/baselineoutcome/distAreaWiseblservey");
		mav.addObject("statewiseAreaDtlList",areaWiseBaseLineService.getDistAreaWiseblservey(stcd, stname));
		mav.addObject("stname",stname);
		mav.addObject("stcd",stcd);
		return mav;
	}

	@RequestMapping(value = "/downloadPDFbslserveydetail", method = RequestMethod.POST)
	public ModelAndView downloadPDFbslserveydetail(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		list = areaWiseBaseLineService.getDistWiseAreaDetails(Integer.parseInt(stcd));
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("DistbaselineAreaSurveyDetailReport");
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
			
				paragraph3 = new Paragraph("Report O8- District wise Comparison of Distribution of Total Area as Per Classification of Land and No. of Crops as On Date with Baseline ", f3);
			
			//paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
//			addFooter(writer);
//			document.add(paragraph);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(24);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(6);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland_ach= BigDecimal.valueOf(0);
				BigDecimal degraded= BigDecimal.valueOf(0);
				BigDecimal degraded_ach= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal rainfed_ach= BigDecimal.valueOf(0);
				BigDecimal plantation= BigDecimal.valueOf(0);
				BigDecimal plantation_ach= BigDecimal.valueOf(0);
				BigDecimal no_plantation= BigDecimal.valueOf(0);
				BigDecimal no_plantation_ach= BigDecimal.valueOf(0);
				BigDecimal others_classification= BigDecimal.valueOf(0);
				BigDecimal others_classification_ach= BigDecimal.valueOf(0);
				BigDecimal no_crop= BigDecimal.valueOf(0);
				BigDecimal no_crop_ach= BigDecimal.valueOf(0);
				BigDecimal single_crop= BigDecimal.valueOf(0);
				BigDecimal single_crop_ach= BigDecimal.valueOf(0);
				BigDecimal double_crop= BigDecimal.valueOf(0);
				BigDecimal double_crop_ach= BigDecimal.valueOf(0);
				BigDecimal multiple_crop= BigDecimal.valueOf(0);
				BigDecimal multiple_crop_ach= BigDecimal.valueOf(0);
				
				
				CommonFunctions.insertCellHeader(table, "State : "+stName, Element.ALIGN_LEFT, 12, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 12, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 4, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District", Element.ALIGN_CENTER, 1, 4, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 4, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 4, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Area)", Element.ALIGN_CENTER, 12, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No of Crops(Area)", Element.ALIGN_CENTER, 8, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultural Wasteland", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed Land", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Forest Land", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Others Classification", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Crops", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Single Crops", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Double Crops", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Multiple Crops", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Plantation", Element.ALIGN_CENTER, 2, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Plantation", Element.ALIGN_CENTER, 2, 1, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "22", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "23", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "24", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProject_area()==null?"":list.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlot_area()==null?"":list.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCultivable_wasteland()==null?"":list.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCultivable_wasteland_ach()==null?"":list.get(i).getCultivable_wasteland_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDegraded()==null?"":list.get(i).getDegraded().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDegraded_ach()==null?"":list.get(i).getDegraded_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getRainfed()==null?"":list.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getRainfed_ach()==null?"":list.get(i).getRainfed_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlantation()==null?"":list.get(i).getPlantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlot_area()==null?"":list.get(i).getPlantation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPlantation_ach()==null?"":list.get(i).getNo_plantation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_plantation_ach()==null?"":list.get(i).getNo_plantation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOthers_classification()==null?"":list.get(i).getOthers_classification().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getOthers_classification_ach()==null?"":list.get(i).getOthers_classification_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_crop()==null?"":list.get(i).getNo_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNo_crop_ach()==null?"":list.get(i).getNo_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSingle_crop()==null?"":list.get(i).getSingle_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSingle_crop_ach()==null?"":list.get(i).getSingle_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDouble_crop()==null?"":list.get(i).getDouble_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDouble_crop_ach()==null?"":list.get(i).getDouble_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMultiple_crop()==null?"":list.get(i).getMultiple_crop().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMultiple_crop_ach()==null?"":list.get(i).getMultiple_crop_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						k=k+1;
						
						project_area=project_area.add(list.get(i).getProject_area()==null?BigDecimal.ZERO:list.get(i).getProject_area());
						plot_area=plot_area.add(list.get(i).getPlot_area()==null?BigDecimal.ZERO:list.get(i).getPlot_area());
						cultivable_wasteland=cultivable_wasteland.add(list.get(i).getCultivable_wasteland()==null?BigDecimal.ZERO:list.get(i).getCultivable_wasteland());
						cultivable_wasteland_ach=cultivable_wasteland_ach.add(list.get(i).getCultivable_wasteland_ach()==null?BigDecimal.ZERO:list.get(i).getCultivable_wasteland_ach());
						degraded=degraded.add(list.get(i).getDegraded()==null?BigDecimal.ZERO:list.get(i).getDegraded());
						degraded_ach=degraded_ach.add(list.get(i).getDegraded_ach()==null?BigDecimal.ZERO:list.get(i).getDegraded_ach());
						rainfed=rainfed.add(list.get(i).getRainfed()==null?BigDecimal.ZERO:list.get(i).getRainfed());
						rainfed_ach=rainfed_ach.add(list.get(i).getRainfed_ach()==null?BigDecimal.ZERO:list.get(i).getRainfed_ach());
						plantation=plantation.add(list.get(i).getPlantation()==null?BigDecimal.ZERO:list.get(i).getPlantation());
						plantation_ach=plantation_ach.add(list.get(i).getPlantation_ach()==null?BigDecimal.ZERO:list.get(i).getPlantation_ach());
						no_plantation=no_plantation.add(list.get(i).getNo_plantation()==null?BigDecimal.ZERO:list.get(i).getNo_plantation());
						no_plantation_ach=no_plantation_ach.add(list.get(i).getNo_plantation_ach()==null?BigDecimal.ZERO:list.get(i).getNo_plantation_ach());
						others_classification=others_classification.add(list.get(i).getOthers_classification()==null?BigDecimal.ZERO:list.get(i).getOthers_classification());
						others_classification_ach=others_classification_ach.add(list.get(i).getOthers_classification_ach()==null?BigDecimal.ZERO:list.get(i).getOthers_classification_ach());
						no_crop=no_crop.add(list.get(i).getNo_crop()==null?BigDecimal.ZERO:list.get(i).getNo_crop());
						no_crop_ach=no_crop_ach.add(list.get(i).getNo_crop_ach()==null?BigDecimal.ZERO:list.get(i).getNo_crop_ach());
						single_crop=single_crop.add(list.get(i).getSingle_crop()==null?BigDecimal.ZERO:list.get(i).getSingle_crop());
						single_crop_ach=single_crop_ach.add(list.get(i).getSingle_crop_ach()==null?BigDecimal.ZERO:list.get(i).getSingle_crop_ach());
						double_crop=double_crop.add(list.get(i).getDouble_crop()==null?BigDecimal.ZERO:list.get(i).getDouble_crop());
						double_crop_ach=double_crop_ach.add(list.get(i).getDouble_crop_ach()==null?BigDecimal.ZERO:list.get(i).getDouble_crop_ach());
						multiple_crop=multiple_crop.add(list.get(i).getMultiple_crop()==null?BigDecimal.ZERO:list.get(i).getMultiple_crop());
						multiple_crop_ach=multiple_crop_ach.add(list.get(i).getMultiple_crop_ach()==null?BigDecimal.ZERO:list.get(i).getMultiple_crop_ach());
						
					}
				
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plantation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_plantation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_plantation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_classification), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_classification_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(single_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(single_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(double_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(double_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(multiple_crop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(multiple_crop_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					if(list.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 24, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report O8- District.pdf");
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
			
	
			
		
	@RequestMapping(value = "/getDistAreaWiseblserveyPDF", method = RequestMethod.POST)
	public ModelAndView getDistAreaWiseblserveyPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String statename=request.getParameter("statename");
		int stcd=Integer.parseInt(request.getParameter("stcd"));
		
		List<BaselineStateWiseAreaDetailBean> listareablsBean = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineAreaReport");
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
			
				paragraph3 = new Paragraph("Report O5- District wise Comparison of Distribution of Area as Per Irrigation Status and Ownership Status as On Date with Baseline ", f3);
			
			//paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
//			addFooter(writer);
//			document.add(paragraph);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(18);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal plot_area= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation= BigDecimal.valueOf(0);
				BigDecimal protective_irrigation_ach= BigDecimal.valueOf(0);
				BigDecimal assured_irrigation= BigDecimal.valueOf(0);
				BigDecimal assured_irrigation_ach= BigDecimal.valueOf(0);
				BigDecimal no_irrigation= BigDecimal.valueOf(0);
				BigDecimal no_irrigation_ach= BigDecimal.valueOf(0);
				BigDecimal private_owner= BigDecimal.valueOf(0);
				BigDecimal private_owner_ach= BigDecimal.valueOf(0);
				BigDecimal govt_owned= BigDecimal.valueOf(0);
				BigDecimal govt_owned_ach= BigDecimal.valueOf(0);
				BigDecimal community_owned= BigDecimal.valueOf(0);
				BigDecimal community_owned_ach= BigDecimal.valueOf(0);
				BigDecimal others_owned= BigDecimal.valueOf(0);
				BigDecimal others_owned_ach= BigDecimal.valueOf(0);
				
				listareablsBean=areaWiseBaseLineService.getDistAreaWiseblservey(stcd, statename);
				
				CommonFunctions.insertCellHeader(table, "State Name: "+statename, Element.ALIGN_LEFT, 9, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.", Element.ALIGN_RIGHT, 9, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Irrigation Status(Area)", Element.ALIGN_CENTER, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ownership(Area)", Element.ALIGN_CENTER, 8, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Protective Irrigation", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Assured Irrigation", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No Irrigation", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Private", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Government", Element.ALIGN_CENTER, 2, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Community", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 2, 1, bf8Bold);

				
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As On Baseline", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "As on date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int k=1;
				if(listareablsBean.size()!=0)
					for(int i=0;i<listareablsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPlot_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProtective_irrigation() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getProtective_irrigation_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getAssured_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getAssured_irrigation_ach() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_irrigation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getNo_irrigation_ach() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPrivate_owner() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getPrivate_owner_ach() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, listareablsBean.get(i).getGovt_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getGovt_owned_ach()  .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getCommunity_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getCommunity_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getOthers_owned().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listareablsBean.get(i).getOthers_owned_ach().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						project_area=project_area.add(listareablsBean.get(i).getProject_area());
//						plot_area=plot_area.add(listareablsBean.get(i).getPlot_area());
						plot_area=plot_area.add(listareablsBean.get(i).getPlot_area());
						protective_irrigation=protective_irrigation.add(listareablsBean.get(i).getProtective_irrigation());
						protective_irrigation_ach=protective_irrigation_ach.add(listareablsBean.get(i).getProtective_irrigation_ach());
						assured_irrigation=assured_irrigation.add(listareablsBean.get(i).getAssured_irrigation());
						assured_irrigation_ach=assured_irrigation_ach.add(listareablsBean.get(i).getAssured_irrigation_ach());
						no_irrigation=no_irrigation.add(listareablsBean.get(i).getNo_irrigation());
						no_irrigation_ach=no_irrigation_ach.add(listareablsBean.get(i).getNo_irrigation_ach());
						private_owner=private_owner.add(listareablsBean.get(i).getPrivate_owner());
						private_owner_ach=private_owner_ach.add(listareablsBean.get(i).getPrivate_owner_ach());
						
						govt_owned=govt_owned.add(listareablsBean.get(i).getGovt_owned());
						govt_owned_ach=govt_owned_ach.add(listareablsBean.get(i).getGovt_owned_ach());
						community_owned=community_owned.add(listareablsBean.get(i).getCommunity_owned());
						community_owned_ach=community_owned_ach.add(listareablsBean.get(i).getCommunity_owned_ach());
						others_owned=others_owned.add(listareablsBean.get(i).getOthers_owned());
						others_owned_ach=others_owned_ach.add(listareablsBean.get(i).getOthers_owned_ach());

						
					}
//				System.out.println("hii"+treatable_area);
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(plot_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(protective_irrigation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(assured_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(assured_irrigation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_irrigation), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(no_irrigation_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(private_owner), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(private_owner_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(govt_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(govt_owned_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(community_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(community_owned_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_owned), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others_owned_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listareablsBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 18, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=report-O5-district.pdf");
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
	
	

	@RequestMapping(value = "/getDistAreaWiseblserveyExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getDistAreaWiseblserveyExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		String statename=request.getParameter("statename");
		int stcd=Integer.parseInt(request.getParameter("stcd"));
		
		List<BaselineStateWiseAreaDetailBean> list = new ArrayList<BaselineStateWiseAreaDetailBean>();
		
		list=areaWiseBaseLineService.getDistAreaWiseblservey(stcd, statename);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O5- District wise Comparison of Distribution of Total Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O5- District wise Comparison of Distribution of Total Area as Per Irrigation Status and Ownership Status as On Date with Baseline";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 17, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,9);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,5,10,17);
	        sheet.addMergedRegion(mergedRegion);
	        
			mergedRegion = new CellRangeAddress(6,8,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,8,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,8,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,8,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,4,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,10,17); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,6,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,8,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,10,11);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,12,13);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,14,15);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,16,17);
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row rowhead = sheet.createRow(5);  
	        
	        Cell cell = rowhead.createCell(0);
			cell.setCellValue("State : "+statename);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<10;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(10);
			cell.setCellValue("All area in ha.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =11; i<18;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Irrigation Status(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =5; i<10;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Ownership(Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =11; i<18;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
					
			
			Row rowhead1 = sheet.createRow(7);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Protective Irrigation");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(5).setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Assured Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(7).setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("No Irrigation");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(9).setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Private");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(11).setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Government");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(13).setCellStyle(style);

			cell = rowhead1.createCell(14);
			cell.setCellValue("Community");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(15).setCellStyle(style);
			
			cell = rowhead1.createCell(16);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(17).setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(8);
			for(int i =0; i<4;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(4);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(5);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(6);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(7);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(8);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(9);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(10);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(11);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(13);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(14);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(16);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(17);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
					
	        
	        int sno = 1;
	        int rowno  =9;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totProtIrr = 0;
	        double totProtIrrAchv = 0;
	        double totAssIrr = 0;
	        double totAssIrrAchv = 0;
	        double totNoIrr = 0;
	        double totNoIrrAchv = 0;
	        double totPvtOwn = 0;
	        double totPvtOwnAchv = 0;
	        double totGovOwn = 0;
	        double totGovOwnAchv = 0;
	        double totComOwn = 0;
	        double totComOwnAchv = 0;
	        double totOtherOwn = 0;
	        double totOtherOwnAchv = 0;
	        
	        for(BaselineStateWiseAreaDetailBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue()); 
	        	row.createCell(3).setCellValue(bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getProtective_irrigation()==null?0.0:bean.getProtective_irrigation().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getProtective_irrigation_ach()==null?0.0:bean.getProtective_irrigation_ach().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getAssured_irrigation()==null?0.0:bean.getAssured_irrigation().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getAssured_irrigation_ach()==null?0.0:bean.getAssured_irrigation_ach().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getNo_irrigation()==null?0.0:bean.getNo_irrigation().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getNo_irrigation_ach()==null?0.0:bean.getNo_irrigation_ach().doubleValue());  
	        	row.createCell(10).setCellValue(bean.getPrivate_owner()==null?0.0:bean.getPrivate_owner().doubleValue()); 
	        	row.createCell(11).setCellValue(bean.getPrivate_owner_ach()==null?0.0:bean.getPrivate_owner_ach().doubleValue());
	        	row.createCell(12).setCellValue(bean.getGovt_owned()==null?0.0:bean.getGovt_owned().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getGovt_owned_ach()==null?0.0:bean.getGovt_owned_ach().doubleValue());  
	        	row.createCell(14).setCellValue(bean.getCommunity_owned()==null?0.0:bean.getCommunity_owned().doubleValue()); 
	        	row.createCell(15).setCellValue(bean.getCommunity_owned_ach()==null?0.0:bean.getCommunity_owned_ach().doubleValue());  
	        	row.createCell(16).setCellValue(bean.getOthers_owned()==null?0.0:bean.getOthers_owned().doubleValue()); 
	        	row.createCell(17).setCellValue(bean.getOthers_owned_ach()==null?0.0:bean.getOthers_owned_ach().doubleValue()); 

	        	
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getPlot_area()==null?0.0:bean.getPlot_area().doubleValue());
	        	totProtIrr = totProtIrr + (bean.getProtective_irrigation()==null?0.0:bean.getProtective_irrigation().doubleValue());
	        	totProtIrrAchv = totProtIrrAchv + (bean.getProtective_irrigation_ach()==null?0.0:bean.getProtective_irrigation_ach().doubleValue());
	        	totAssIrr = totAssIrr + (bean.getAssured_irrigation()==null?0.0:bean.getAssured_irrigation().doubleValue()); 
	        	totAssIrrAchv = totAssIrrAchv + (bean.getAssured_irrigation_ach()==null?0.0:bean.getAssured_irrigation_ach().doubleValue()); 
	        	totNoIrr = totNoIrr + (bean.getNo_irrigation()==null?0.0:bean.getNo_irrigation().doubleValue());
	        	totNoIrrAchv = totNoIrrAchv + (bean.getNo_irrigation_ach()==null?0.0:bean.getNo_irrigation_ach().doubleValue());
	        	totPvtOwn = totPvtOwn + (bean.getPrivate_owner()==null?0.0:bean.getPrivate_owner().doubleValue());
	        	totPvtOwnAchv = totPvtOwnAchv + (bean.getPrivate_owner_ach()==null?0.0:bean.getPrivate_owner_ach().doubleValue());
	        	totGovOwn = totGovOwn + (bean.getGovt_owned()==null?0.0:bean.getGovt_owned().doubleValue());
	        	totGovOwnAchv = totGovOwnAchv + (bean.getGovt_owned_ach()==null?0.0:bean.getGovt_owned_ach().doubleValue());
	        	totComOwn = totComOwn + (bean.getCommunity_owned()==null?0.0:bean.getCommunity_owned().doubleValue());
	        	totComOwnAchv = totComOwnAchv + (bean.getCommunity_owned_ach()==null?0.0:bean.getCommunity_owned_ach().doubleValue());
	        	totOtherOwn = totOtherOwn + (bean.getOthers_owned()==null?0.0:bean.getOthers_owned().doubleValue());
	        	totOtherOwnAchv = totOtherOwnAchv + (bean.getOthers_owned_ach()==null?0.0:bean.getOthers_owned_ach().doubleValue());

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
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totProtIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totProtIrrAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totAssIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totAssIrrAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totNoIrr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totNoIrrAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totPvtOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totPvtOwnAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totGovOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totGovOwnAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totComOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totComOwnAchv); 
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totOtherOwn);
	        cell.setCellStyle(style1);
	        cell = row.createCell(17);
	        cell.setCellValue(totOtherOwnAchv);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 17);
	        String fileName = "attachment; filename=Report O5- District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}
	
}
	
	

	

