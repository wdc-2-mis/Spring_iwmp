package app.projectevaluation.controller;

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
import org.apache.poi.ss.usermodel.VerticalAlignment;
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

import app.bean.ConvergenceWorksBean;
import app.common.CommonFunctions;
import app.projectevaluation.bean.FundUtilizationEvalReportBean;
import app.projectevaluation.service.FundUtilizationEvalReportService;

@Controller("FundUtilizationEvalReportController")
public class FundUtilizationEvalReportController {
	
	@Autowired
	FundUtilizationEvalReportService fundService;
	
	@RequestMapping(value = "/getFundUtilizationEvalReport", method = RequestMethod.GET)
	public ModelAndView getFundUtilizationEvalReport(HttpServletRequest request) 
	{
		
		List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();
		ModelAndView mav = new ModelAndView("reports/FundEvalReport");
		
		list = fundService.getFundUtilizationEvalReport();
				
		mav.addObject("fundList",list);
		mav.addObject("fundListSize",list.size());
		
		return mav;
	}

	@RequestMapping(value = "/getDistFundUtilizationEvalReport", method = RequestMethod.GET)
	public ModelAndView getDistFundUtilizationEvalReport(HttpServletRequest request) {
		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
		
		List<FundUtilizationEvalReportBean> listD = new ArrayList<FundUtilizationEvalReportBean>();
		ModelAndView mav = new ModelAndView("reports/DistFundEvalReport"); 
		
		listD = fundService.getDistFundUtilizationEvalReport(Integer.parseInt(stcd));
		
		mav.addObject("stcd",stcd);
		mav.addObject("stName",stName);
		mav.addObject("distName",distName);
		mav.addObject("fundDList",listD);
		mav.addObject("fundListDSize",listD.size());

		return mav;
	}

		@RequestMapping(value = "/getMandaysDetailsReport", method = RequestMethod.GET)
		public ModelAndView getMandaysDetailsReport(HttpServletRequest request) 
		{
		
			List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();
			ModelAndView mav = new ModelAndView("reports/MandaysDetailsReport");
		
			list = fundService.getMandaysDetailsReport();
				
			mav.addObject("mList",list);
			mav.addObject("mListSize",list.size());
		
			return mav;
		}
	 
	@RequestMapping(value = "/downloadFundUtilizationEvalReportPdf", method = RequestMethod.POST)
	public ModelAndView downloadFundUtilizationEvalReportPdf(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();

		list = fundService.getFundUtilizationEvalReport();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("FundUtilizationEvalReport");
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
			
			paragraph3 = new Paragraph("Report PE2-  State-wise Mid Term Evaluation of Sanctioned Cost and  Fund Utilization ", f3);
				
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(11);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(90);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(5);

		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Total No. of Projects", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Project Area (in ha.)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Cost", Element.ALIGN_CENTER, 3, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Fund Utilization", Element.ALIGN_CENTER, 4, 1, bf8Bold);

		    CommonFunctions.insertCellHeader(table, "Central Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold); // SC
		    CommonFunctions.insertCellHeader(table, "State Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold);   // SS
		    CommonFunctions.insertCellHeader(table, "Total Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold);   // TS
		    CommonFunctions.insertCellHeader(table, "Fund Received (Rs. Crores)", Element.ALIGN_CENTER, 3, 1, bf8Bold); // FR
		    CommonFunctions.insertCellHeader(table, "Total Expenditure (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold); // TE

		    CommonFunctions.insertCellHeader(table, "Central Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR-CS
		    CommonFunctions.insertCellHeader(table, "State Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // FR-SS
		    CommonFunctions.insertCellHeader(table, "Total Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // FR-TS

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
				
			int k = 1;
			
			int totproj = 0;
			BigDecimal projArea = BigDecimal.ZERO;
			BigDecimal evCentralShare = BigDecimal.ZERO;
			BigDecimal evStateShare = BigDecimal.ZERO;
			BigDecimal evTotalshare = BigDecimal.ZERO;
			
			BigDecimal fundCentralshare = BigDecimal.ZERO;
			BigDecimal fundStateshare = BigDecimal.ZERO;
			BigDecimal fundTotalshare = BigDecimal.ZERO;
			BigDecimal projExpenditure = BigDecimal.ZERO;
			




				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_project().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_project_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_evaluation_central_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_evaluation_state_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_share_evaluation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_fund_central_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_fund_state_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_fund().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_expenditure().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
				    	
				    
				    totproj = totproj + list.get(i).getTotal_project();
				    projArea = projArea.add(list.get(i).getTotal_project_area());
				    evCentralShare = evCentralShare.add(list.get(i).getTotal_evaluation_central_share());
				    evStateShare = evStateShare.add(list.get(i).getTotal_evaluation_state_share());
				    evTotalshare = evTotalshare.add(list.get(i).getTotal_share_evaluation());
				    
				    fundCentralshare = fundCentralshare.add( list.get(i).getTotal_fund_central_share());
				    fundStateshare = fundStateshare.add(list.get(i).getTotal_fund_state_share());
				    fundTotalshare = fundTotalshare.add(list.get(i).getTotal_fund());
				    
				    projExpenditure = projExpenditure.add(list.get(i).getTotal_expenditure());
				   
						
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(projArea), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(evCentralShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(evStateShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(evTotalshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(fundCentralshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(fundStateshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(fundTotalshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(projExpenditure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 11, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE2- State.pdf");
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
	
	@RequestMapping(value = "/downloadDistFundUtilizationEvalReportPdf", method = RequestMethod.POST)
	public ModelAndView downloadDistFundUtilizationEvalReportPdf(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		List<FundUtilizationEvalReportBean> listD = new ArrayList<FundUtilizationEvalReportBean>();

		listD = fundService.getDistFundUtilizationEvalReport(Integer.parseInt(stcd));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("FundUtilizationEvalReport");
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
			
			paragraph3 = new Paragraph("Report PE2-  District-wise Mid Term Evaluation of Sanctioned Cost and  Fund Utilization ", f3);
				
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(11);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(90);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(5);
		    
		    CommonFunctions.insertCellHeader(table,"State Name : "+ stName, Element.ALIGN_LEFT, 11, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Total No. of Projects", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Project Area (in ha.)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Cost", Element.ALIGN_CENTER, 3, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Fund Utilization", Element.ALIGN_CENTER, 4, 1, bf8Bold);

		    CommonFunctions.insertCellHeader(table, "Central Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold); // SC
		    CommonFunctions.insertCellHeader(table, "State Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold);   // SS
		    CommonFunctions.insertCellHeader(table, "Total Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold);   // TS
		    CommonFunctions.insertCellHeader(table, "Fund Received (Rs. Crores)", Element.ALIGN_CENTER, 3, 1, bf8Bold); // FR
		    CommonFunctions.insertCellHeader(table, "Total Expenditure (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold); // TE

		    CommonFunctions.insertCellHeader(table, "Central Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR-CS
		    CommonFunctions.insertCellHeader(table, "State Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // FR-SS
		    CommonFunctions.insertCellHeader(table, "Total Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // FR-TS

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
				
			int k = 1;
			
			int totproj = 0;
			BigDecimal projArea = BigDecimal.ZERO;
			BigDecimal evCentralShare = BigDecimal.ZERO;
			BigDecimal evStateShare = BigDecimal.ZERO;
			BigDecimal evTotalshare = BigDecimal.ZERO;
			
			BigDecimal fundCentralshare = BigDecimal.ZERO;
			BigDecimal fundStateshare = BigDecimal.ZERO;
			BigDecimal fundTotalshare = BigDecimal.ZERO;
			BigDecimal projExpenditure = BigDecimal.ZERO;
			




				
			if(listD.size()!=0)
				for(int i=0;i<listD.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listD.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listD.get(i).getTotal_project().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listD.get(i).getTotal_project_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listD.get(i).getTotal_evaluation_central_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listD.get(i).getTotal_evaluation_state_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listD.get(i).getTotal_share_evaluation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listD.get(i).getTotal_fund_central_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listD.get(i).getTotal_fund_state_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listD.get(i).getTotal_fund().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listD.get(i).getTotal_expenditure().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
				    	
				    
				    totproj = totproj + listD.get(i).getTotal_project();
				    projArea = projArea.add(listD.get(i).getTotal_project_area());
				    evCentralShare = evCentralShare.add(listD.get(i).getTotal_evaluation_central_share());
				    evStateShare = evStateShare.add(listD.get(i).getTotal_evaluation_state_share());
				    evTotalshare = evTotalshare.add(listD.get(i).getTotal_share_evaluation());
				    
				    fundCentralshare = fundCentralshare.add( listD.get(i).getTotal_fund_central_share());
				    fundStateshare = fundStateshare.add(listD.get(i).getTotal_fund_state_share());
				    fundTotalshare = fundTotalshare.add(listD.get(i).getTotal_fund());
				    
				    projExpenditure = projExpenditure.add(listD.get(i).getTotal_expenditure());
				   
						
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(projArea), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(evCentralShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(evStateShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(evTotalshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(fundCentralshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(fundStateshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(fundTotalshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(projExpenditure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(listD.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 11, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE2- District.pdf");
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

	@RequestMapping(value = "/downloadExcelFundUtilizationEvalReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelFundUtilizationEvalReport(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();

		list = fundService.getFundUtilizationEvalReport();
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report PE2-  State-wise Mid Term Evaluation of Sanctioned Cost and  Fund Utilization");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE2-  State-wise Mid Term Evaluation of Sanctioned Cost and  Fund Utilization";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 10, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,0,0); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,1,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,2,2); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,3,3); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,4,6); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,7,10); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,4,4); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,5,5); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,6,6); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,7,9); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,10,10); 
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
		cell.setCellValue("Total No. of Project");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Sanctioned Project Area (in ha.)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Sanctioned Cost");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(5);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellStyle(style);
		

		cell = rowhead.createCell(7);
		cell.setCellValue("Fund Utilization");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=8;i<11;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<4;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}

		cell = rowhead1.createCell(4);
		cell.setCellValue("Central Share (Rs. Crores)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("State Share (Rs. Crores)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

		cell = rowhead1.createCell(6);
		cell.setCellValue("Total Share (Rs. Crores)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Fund Received");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(8);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(9);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(10);
		cell.setCellValue("Total Expenditure (Rs. Crores)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<7;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}

		cell = rowhead2.createCell(7);
		cell.setCellValue("Central Share (Rs. Crores)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(8);
		cell.setCellValue("State Share (Rs. Crores)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Total Share (Rs. Crores)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(10);
		cell.setCellStyle(style);
		
		
		Row rowhead3 = sheet.createRow(8);
		
		for(int i=0;i<11;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 8;
		int totproj = 0;
		BigDecimal projArea = BigDecimal.ZERO;
		BigDecimal evCentralShare = BigDecimal.ZERO;
		BigDecimal evStateShare = BigDecimal.ZERO;
		BigDecimal evTotalshare = BigDecimal.ZERO;
		
		BigDecimal fundCentralshare = BigDecimal.ZERO;
		BigDecimal fundStateshare = BigDecimal.ZERO;
		BigDecimal fundTotalshare = BigDecimal.ZERO;
		BigDecimal projExpenditure = BigDecimal.ZERO;
		
		
	    for(FundUtilizationEvalReportBean bean: list) 
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getSt_name());
	    	row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getTotal_project_area().doubleValue());
	    	row.createCell(4).setCellValue(bean.getTotal_evaluation_central_share().doubleValue());
	    	row.createCell(5).setCellValue(bean.getTotal_evaluation_state_share().doubleValue());
	    	row.createCell(6).setCellValue(bean.getTotal_share_evaluation().doubleValue());
	    	row.createCell(7).setCellValue(bean.getTotal_fund_central_share().doubleValue());
	    	row.createCell(8).setCellValue(bean.getTotal_fund_state_share().doubleValue());
	    	row.createCell(9).setCellValue(bean.getTotal_fund().doubleValue());
	    	row.createCell(10).setCellValue(bean.getTotal_expenditure().doubleValue());
	    	
	    	    totproj = totproj + bean.getTotal_project();
			    projArea = projArea.add(bean.getTotal_project_area());
			    evCentralShare = evCentralShare.add(bean.getTotal_evaluation_central_share());
			    evStateShare = evStateShare.add(bean.getTotal_evaluation_state_share());
			    evTotalshare = evTotalshare.add(bean.getTotal_share_evaluation());
			    
			    fundCentralshare = fundCentralshare.add(bean.getTotal_fund_central_share());
			    fundStateshare = fundStateshare.add(bean.getTotal_fund_state_share());
			    fundTotalshare = fundTotalshare.add(bean.getTotal_fund());
			    
			    projExpenditure = projExpenditure.add(bean.getTotal_expenditure());

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
		cell.setCellValue(totproj);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(projArea.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(evCentralShare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(evStateShare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(evTotalshare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(fundCentralshare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(fundStateshare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(fundTotalshare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(projExpenditure.doubleValue());
		cell.setCellStyle(style1);

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 10);
	    String fileName = "attachment; filename=Report PE2- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/FundEvalReport";
	}
	
	@RequestMapping(value = "/downloadExcelDistFundUtilizationEvalReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistFundUtilizationEvalReport(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		List<FundUtilizationEvalReportBean> listD = new ArrayList<FundUtilizationEvalReportBean>();

		listD = fundService.getDistFundUtilizationEvalReport(Integer.parseInt(stcd));
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report PE2-  State-wise Mid Term Evaluation of Sanctioned Cost and  Fund Utilization");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE2-  District-wise Mid Term Evaluation of Sanctioned Cost and  Fund Utilization for State '"+ stName+"' ";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 10, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(listD.size()+8,listD.size()+8,0,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,0,0); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,1,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,2,2); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,3,3); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,4,6); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,7,10); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,4,4); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,5,5); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,6,6); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,7,9); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,10,10); 
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
		cell.setCellValue("Total No. of Project");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Sanctioned Project Area (in ha.)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Sanctioned Cost");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(5);
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(6);
		cell.setCellStyle(style);
		

		cell = rowhead.createCell(7);
		cell.setCellValue("Fund Utilization");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=8;i<11;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<4;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}

		cell = rowhead1.createCell(4);
		cell.setCellValue("Central Share (Rs. Crores)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("State Share (Rs. Crores)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

		cell = rowhead1.createCell(6);
		cell.setCellValue("Total Share (Rs. Crores)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Fund Received");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead1.createCell(8);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(9);
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(10);
		cell.setCellValue("Total Expenditure (Rs. Crores)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<7;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellStyle(style);
		}

		cell = rowhead2.createCell(7);
		cell.setCellValue("Central Share (Rs. Crores)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(8);
		cell.setCellValue("State Share (Rs. Crores)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Total Share (Rs. Crores)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(10);
		cell.setCellStyle(style);
		
		
		Row rowhead3 = sheet.createRow(8);
		
		for(int i=0;i<11;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 8;
		int totproj = 0;
		BigDecimal projArea = BigDecimal.ZERO;
		BigDecimal evCentralShare = BigDecimal.ZERO;
		BigDecimal evStateShare = BigDecimal.ZERO;
		BigDecimal evTotalshare = BigDecimal.ZERO;
		
		BigDecimal fundCentralshare = BigDecimal.ZERO;
		BigDecimal fundStateshare = BigDecimal.ZERO;
		BigDecimal fundTotalshare = BigDecimal.ZERO;
		BigDecimal projExpenditure = BigDecimal.ZERO;
		
		
	    for(FundUtilizationEvalReportBean bean: listD) 
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getDist_name());
	    	row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getTotal_project_area().doubleValue());
	    	row.createCell(4).setCellValue(bean.getTotal_evaluation_central_share().doubleValue());
	    	row.createCell(5).setCellValue(bean.getTotal_evaluation_state_share().doubleValue());
	    	row.createCell(6).setCellValue(bean.getTotal_share_evaluation().doubleValue());
	    	row.createCell(7).setCellValue(bean.getTotal_fund_central_share().doubleValue());
	    	row.createCell(8).setCellValue(bean.getTotal_fund_state_share().doubleValue());
	    	row.createCell(9).setCellValue(bean.getTotal_fund().doubleValue());
	    	row.createCell(10).setCellValue(bean.getTotal_expenditure().doubleValue());
	    	
	    	    totproj = totproj + bean.getTotal_project();
			    projArea = projArea.add(bean.getTotal_project_area());
			    evCentralShare = evCentralShare.add(bean.getTotal_evaluation_central_share());
			    evStateShare = evStateShare.add(bean.getTotal_evaluation_state_share());
			    evTotalshare = evTotalshare.add(bean.getTotal_share_evaluation());
			    
			    fundCentralshare = fundCentralshare.add(bean.getTotal_fund_central_share());
			    fundStateshare = fundStateshare.add(bean.getTotal_fund_state_share());
			    fundTotalshare = fundTotalshare.add(bean.getTotal_fund());
			    
			    projExpenditure = projExpenditure.add(bean.getTotal_expenditure());

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

		Row row = sheet.createRow(listD.size()+8);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellValue(totproj);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(projArea.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(evCentralShare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(evStateShare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(evTotalshare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(fundCentralshare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(fundStateshare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(fundTotalshare.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(projExpenditure.doubleValue());
		cell.setCellStyle(style1);

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, listD.size(), 10);
	    String fileName = "attachment; filename=Report PE2- District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/DistFundEvalReport";
	}

}
