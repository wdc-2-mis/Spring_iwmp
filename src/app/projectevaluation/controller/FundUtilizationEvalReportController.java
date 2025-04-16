package app.projectevaluation.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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
		    CommonFunctions.insertCellHeader(table, "Sanctioned Project Area", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Cost of", Element.ALIGN_CENTER, 3, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Fund Utilization", Element.ALIGN_CENTER, 4, 1, bf8Bold);

		    CommonFunctions.insertCellHeader(table, "Central Share", Element.ALIGN_CENTER, 1, 2, bf8Bold); // SC
		    CommonFunctions.insertCellHeader(table, "State Share", Element.ALIGN_CENTER, 1, 2, bf8Bold);   // SS
		    CommonFunctions.insertCellHeader(table, "Total Share", Element.ALIGN_CENTER, 1, 2, bf8Bold);   // TS
		    CommonFunctions.insertCellHeader(table, "Fund Received", Element.ALIGN_CENTER, 3, 1, bf8Bold); // FR
		    CommonFunctions.insertCellHeader(table, "Total Expenditure", Element.ALIGN_CENTER, 1, 2, bf8Bold); // TE

		    CommonFunctions.insertCellHeader(table, "Central Share", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR-CS
		    CommonFunctions.insertCellHeader(table, "State Share", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // FR-SS
		    CommonFunctions.insertCellHeader(table, "Total Share", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // FR-TS

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
					CommonFunctions.insertCell(table, list.get(i).getTotal_fund_central_share().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_fund_state_share().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_fund().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getTotal_expenditure().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						
				    	
				    
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
	
}
