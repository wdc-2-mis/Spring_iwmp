package app.controllers.pfms;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.springframework.ui.Model;
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

import app.PfmsTreasureBean;
import app.bean.BaseLineOutcomeBean;
import app.bean.FPOReportBean;
import app.bean.pfms.PfmsStwiseExpndtreBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.model.GetGoiReleaseToStateTreasury;
import app.model.PfmsCgireceiptDetaildata;
import app.model.PfmsTreasuryreceiptDetaildata;
import app.service.CommonService;
import app.service.FinYrServices;
import app.service.PfmsCgiDataService;
import app.service.reports.PhysicalActionAchievementService;

@Controller("pfmsCgiDetailController")
public class PfmsCgiDetailController {

	HttpSession session;
	int statecode;
	int finyr;
//	String finyear;
	@Autowired(required = true)
	private PfmsCgiDataService cgidata; 
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@Autowired(required = true)
	private CommonService commonService;
	
	@Autowired(required = true)
	FinYrServices finYrServices;
	
	@RequestMapping(value = "/cgidetailrpt", method = RequestMethod.GET)
	public ModelAndView cgidetailrpt(HttpServletRequest request, Model model) {
		session = request.getSession(true);
		List<PfmsCgireceiptDetaildata> pfmscgldata=null;
		pfmscgldata = cgidata.getPfmscgidetaildata();
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/pfmscgidetaildata");
		model.addAttribute("pfmscgldata", pfmscgldata);
		return mav;
	}

	@RequestMapping(value = "/pfmstrerecipt", method = RequestMethod.GET)
	public ModelAndView pfmstrerecipt(HttpServletRequest request, Model model) {
		session = request.getSession(true);
		List<PfmsTreasureBean> pfmstresure=null;
		pfmstresure = cgidata.getPfmstrereciptdata();
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/pfmstresuredata");
		model.addAttribute("pfmstresure", pfmstresure);
		return mav;
	}

	@RequestMapping(value = "/expandtreasure", method = RequestMethod.GET)
	public String expandtreasure(HttpServletRequest request, Model model) {
		statecode = Integer.parseInt(request.getParameter("stcode"));
		ModelAndView mav = new ModelAndView("reports/pfmstresuredata");
		List<PfmsTreasuryreceiptDetaildata> treasureList = null;
		treasureList = cgidata.getexpandtreasure(statecode);
		List<PfmsTreasureBean> pfmstresure=cgidata.gettotalPfmstrereciptdata(statecode);
		model.addAttribute("treasureList" , treasureList);
		model.addAttribute("treasuretotalList" , pfmstresure);
		model.addAttribute("stcode", statecode);
		return "reports/pfmstresuredata";
	
	}
	
	@RequestMapping(value = "/getGoiReleaseToStateTreasury", method = RequestMethod.GET)
	public ModelAndView getGoiReleaseToStateTreasury(HttpServletRequest request, Model model) {
		session = request.getSession(true);
		String fyearParam = request.getParameter("fyear");
		int finyr = 0; 
		if (fyearParam != null && !fyearParam.isEmpty()) {
		    finyr = Integer.parseInt(fyearParam);
		}
		List<GetGoiReleaseToStateTreasury> list=new ArrayList<>();;
		list = cgidata.getGoiReleaseToStateTreasury(finyr);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/pfmsGetGoiReleaseStTreasuryRpt");
		model.addAttribute("pfmsGoiReleaseData", list);
		model.addAttribute("pfmsGoiReleaseDataSize", list.size());
		mav.addObject("yearList",finYrServices.getAllFinYear());
		model.addAttribute("fyear", finyr);
		return mav;
	}

	@RequestMapping(value = "/downloadPFMSPDF", method = RequestMethod.POST)
	public ModelAndView downloadPFMSPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		int statecode =0;

		List<PfmsTreasureBean> pfmsBean = new ArrayList<PfmsTreasureBean>();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("pfmsReport");
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
			
				paragraph3 = new Paragraph("Report PF2 - Central/State Share Release from State Treasury to Slna", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(6);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				BigDecimal treasuryReceipt= BigDecimal.valueOf(0);
				BigDecimal centralShare= BigDecimal.valueOf(0);
				BigDecimal stateShare= BigDecimal.valueOf(0);
				
				pfmsBean=cgidata.getPfmstrereciptdata();
				
				CommonFunctions.insertCellHeader(table,  "All amounts are Rs.", Element.ALIGN_RIGHT, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Financial Year ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Treasury Receipt ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Treasury Receipt", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Central Share", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Share", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(pfmsBean.size()!=0)
					for(int i=0;i<pfmsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pfmsBean.get(i).getStName(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pfmsBean.get(i).getFinancialYear(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pfmsBean.get(i).getTreasuryReceipt() == null ? "" : String.format(Locale.US, "%.2f", pfmsBean.get(i).getTreasuryReceipt()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pfmsBean.get(i).getCentralShare() == null ? "" : String.format(Locale.US, "%.2f", pfmsBean.get(i).getCentralShare()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pfmsBean.get(i).getStateShare() == null ? "" : String.format(Locale.US, "%.2f", pfmsBean.get(i).getStateShare()), Element.ALIGN_RIGHT, 1, 1, bf8);

						k=k+1;               
						treasuryReceipt=treasuryReceipt.add(pfmsBean.get(i).getTreasuryReceipt()==null?BigDecimal.ZERO:pfmsBean.get(i).getTreasuryReceipt());
						centralShare=centralShare.add(pfmsBean.get(i).getCentralShare()==null?BigDecimal.ZERO:pfmsBean.get(i).getCentralShare());
						stateShare=stateShare.add(pfmsBean.get(i).getStateShare()==null?BigDecimal.ZERO:pfmsBean.get(i).getStateShare());
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 3, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",treasuryReceipt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",centralShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",stateShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(pfmsBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 6, 1, bf8);
					
						
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
		response.setHeader("Content-Disposition", "attachment;filename=PF2-Report.pdf");
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
	@RequestMapping(value = "/downloadPFMSDetailPDF", method = RequestMethod.POST)
	public ModelAndView downloadPFMSDetailPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String statecode=request.getParameter("stcode");			
		List<PfmsTreasuryreceiptDetaildata> pdBean = new ArrayList<PfmsTreasuryreceiptDetaildata>();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("pfmsDetailReport");
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
			
				paragraph3 = new Paragraph("Report PF2 - Central/State Share Release from State Treasury to Slna", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(7);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				BigDecimal treasuryReceipt= BigDecimal.valueOf(0);
				BigDecimal centralShare= BigDecimal.valueOf(0);
				BigDecimal stateShare= BigDecimal.valueOf(0);
				
				if (statecode != null && !statecode.isEmpty()) {		
					pdBean = cgidata.getexpandtreasure(Integer.parseInt(statecode));
						
				
				CommonFunctions.insertCellHeader(table,  "All amounts are Rs.", Element.ALIGN_RIGHT, 7, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Financial Year ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transaction Id", Element.ALIGN_CENTER, 1, 2, bf8Bold);		
				CommonFunctions.insertCellHeader(table, "Treasury Receipt ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Treasury Receipt", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Central Share", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Share", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(pdBean.size()!=0)
					for(int i=0;i<pdBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pdBean.get(i).getIwmpState().getStName(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pdBean.get(i).getFinancialYear(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pdBean.get(i).getTransactionId(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pdBean.get(i).getTreasuryReceipt() == null ? "" : String.format(Locale.US, "%.2f", pdBean.get(i).getTreasuryReceipt()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pdBean.get(i).getCentralShare() == null ? "" : String.format(Locale.US, "%.2f", pdBean.get(i).getCentralShare()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, pdBean.get(i).getStateShare() == null ? "" : String.format(Locale.US, "%.2f", pdBean.get(i).getStateShare()), Element.ALIGN_RIGHT, 1, 1, bf8);

						k=k+1;
						treasuryReceipt=treasuryReceipt.add(pdBean.get(i).getTreasuryReceipt()==null?BigDecimal.ZERO:pdBean.get(i).getTreasuryReceipt());
						centralShare=centralShare.add(pdBean.get(i).getCentralShare()==null?BigDecimal.ZERO:pdBean.get(i).getCentralShare());
						stateShare=stateShare.add(pdBean.get(i).getStateShare()==null?BigDecimal.ZERO:pdBean.get(i).getStateShare());
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 4, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",treasuryReceipt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",centralShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",stateShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(pdBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
					
				}			
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
		response.setHeader("Content-Disposition", "attachment;filename=PF2-Report.pdf");
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
	
	
	@RequestMapping(value = "/downloadcgiDetailPDF", method = RequestMethod.POST)
	public ModelAndView downloadcgiDetailPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		List<PfmsCgireceiptDetaildata> cgiBean = new ArrayList<PfmsCgireceiptDetaildata>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("cgiDetailReport");
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
			
				paragraph3 = new Paragraph("Report PF6 - GOI Release To State Level Implementing Agency ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(7);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				
				BigDecimal totalAmount= BigDecimal.valueOf(0);
				cgiBean= cgidata.getPfmscgidetaildata();
				
				CommonFunctions.insertCellHeader(table,  "All amounts are Rs.", Element.ALIGN_RIGHT, 7, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Financial Year ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Sanction No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transaction Id", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transaction Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Release from Govt. India (Central Share)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
				if(cgiBean.size()!=0)
					for(int i=0;i<cgiBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, cgiBean.get(i).getIwmpState().getStName(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, cgiBean.get(i).getFinancialYear(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, cgiBean.get(i).getSanctionNo()==null?"":cgiBean.get(i).getSanctionNo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, cgiBean.get(i).getTransactionId(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, DateFormat.format(cgiBean.get(i).getTransactionDate()==null?"":cgiBean.get(i).getTransactionDate()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, cgiBean.get(i).getTotalAmount()==null?"":cgiBean.get(i).getTotalAmount().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						k=k+1;
						totalAmount=totalAmount.add(cgiBean.get(i).getTotalAmount()==null?BigDecimal.ZERO:cgiBean.get(i).getTotalAmount());
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 6, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totalAmount), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					
					if(cgiBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=PF6-Report.pdf");
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
	
	
	@RequestMapping(value = "/DownloadGoiReleaseToStateTreasuryPDF", method = RequestMethod.POST)
	public ModelAndView DownloadGoiReleaseToStateTreasuryPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		List<GetGoiReleaseToStateTreasury> list=new ArrayList<GetGoiReleaseToStateTreasury>();
		String fyearParam = request.getParameter("fyear");
		int finyr = 0; 
		if (fyearParam != null && !fyearParam.isEmpty()) {
		    finyr = Integer.parseInt(fyearParam);
		}
		list = cgidata.getGoiReleaseToStateTreasury(finyr);
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("pfmsGoiReport");
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
			if(finyr==0) {
				paragraph3 = new Paragraph("Report PF1 - Central Share Release Data to Various State Treasury for All Financial Year", f3);
			}
			if(finyr>0) {
				paragraph3 = new Paragraph("Report PF1 - Central Share Release Data to Various State Treasury for Financial Year  '20"+finyr+"-"+"20"+(Integer.parseInt(fyearParam)+1)+"' ", f3);
			}
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(7);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(2);
				
				BigDecimal totsanctionAmount= BigDecimal.valueOf(0);
				BigDecimal sanctionAmount= BigDecimal.valueOf(0);
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Financial Year ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Sanction No. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transaction Id", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transaction Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Release from Govt. India (Central Share in Rs)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				String projn="";
				 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
				if(list.size()!=0)
					
					for(int i=0;i<list.size();i++) 
					{
						
						if(!list.get(i).getIwmpStateByStCode().getStName().equalsIgnoreCase(projn) && i!=0) {
							CommonFunctions.insertCell3(table, "State Total", Element.ALIGN_RIGHT, 6, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",sanctionAmount), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
							sanctionAmount =BigDecimal.ZERO;
							}
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getIwmpStateByStCode().getStName(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, list.get(i).getFinancialyear(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSanctionNumber(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getTransaction_id(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table,formatter.format(list.get(i).getTransactionDate()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSanctionAmount() == null ? "" : String.format(Locale.US, "%.2f", list.get(i).getSanctionAmount()), Element.ALIGN_RIGHT, 1, 1, bf8);

						k=k+1;
						projn=list.get(i).getIwmpStateByStCode().getStName();
						sanctionAmount=sanctionAmount.add(list.get(i).getSanctionAmount()==null?BigDecimal.ZERO:list.get(i).getSanctionAmount());
						totsanctionAmount=totsanctionAmount.add(list.get(i).getSanctionAmount()==null?BigDecimal.ZERO:list.get(i).getSanctionAmount());
					}
						CommonFunctions.insertCell3(table, "State Total", Element.ALIGN_RIGHT, 6, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",sanctionAmount), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
						CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 6, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",totsanctionAmount), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(list.size()==0) 
						CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
					
						
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
		response.setHeader("Content-Disposition", "attachment;filename=PF1-Report.pdf");
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
	
	@RequestMapping(value = "/downloadExcelpfmstrerecipt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelpfmstrerecipt(HttpServletRequest request, HttpServletResponse response) 
	{
		//String state=request.getParameter("state");
		int statecode =0;
		List<PfmsTreasureBean> list = new ArrayList<PfmsTreasureBean>();
		
		list = cgidata.getPfmstrereciptdata();
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF2 - Central OR State Share Release from State Treasury to Slna");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF2 - Central/State Share Release from State Treasury to Slna";
			String areaAmtValDetail = "All amounts are Rs.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Financial Year");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Treasury Receipt");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Treasury Reciept");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(5).setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<4;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Central Share");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("State Share");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<6;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totTreasRpt = 0;
	        double totCentralShare = 0;
	        double totStateShare = 0;
	        
	        
	        for(PfmsTreasureBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStName());
	        	row.createCell(2).setCellValue(bean.getFinancialYear());
	        	row.createCell(3).setCellValue(bean.getTreasuryReceipt()==null?0.0:bean.getTreasuryReceipt().doubleValue());
	        	row.createCell(4).setCellValue(bean.getCentralShare()==null?0.0:bean.getCentralShare().doubleValue());
	        	row.createCell(5).setCellValue(bean.getStateShare()==null?0.0:bean.getStateShare().doubleValue());
	        	
	        	totTreasRpt = totTreasRpt + (bean.getTreasuryReceipt()==null?0.0:bean.getTreasuryReceipt().doubleValue());
	        	totCentralShare = totCentralShare + (bean.getCentralShare()==null?0.0:bean.getCentralShare().doubleValue());
	        	totStateShare = totStateShare + (bean.getStateShare()==null?0.0:bean.getStateShare().doubleValue());
				
				
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
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totTreasRpt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCentralShare);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totStateShare);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 5);
	        String fileName = "attachment; filename=Report PF2- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/pfmstresuredata";
		
	}
	
	@RequestMapping(value = "/downloadExcelexpandtreasure", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelexpandtreasure(HttpServletRequest request, HttpServletResponse response) 
	{
		String statecode=request.getParameter("stcode");			
		List<PfmsTreasuryreceiptDetaildata> list = new ArrayList<PfmsTreasuryreceiptDetaildata>();
		
		list = cgidata.getexpandtreasure(Integer.parseInt(statecode));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF2 - Central OR State Share Release from State Treasury to Slna");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF2 - Central/State Share Release from State Treasury to Slna";
			String areaAmtValDetail = "All amounts are Rs.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,4,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,5,6); 
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,3); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Financial Year");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Transaction Id");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Treasury Receipt");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Treasury Reciept");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(6).setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<5;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Central Share");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("State Share");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<7;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totTreasRpt = 0;
	        double totCentralShare = 0;
	        double totStateShare = 0;
	        
	        
	        for(PfmsTreasuryreceiptDetaildata bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getIwmpState().getStName());
	        	row.createCell(2).setCellValue(bean.getFinancialYear());
	        	row.createCell(3).setCellValue(bean.getTransactionId());
	        	row.createCell(4).setCellValue(bean.getTreasuryReceipt()==null?0.0:bean.getTreasuryReceipt().doubleValue());
	        	row.createCell(5).setCellValue(bean.getCentralShare()==null?0.0:bean.getCentralShare().doubleValue());
	        	row.createCell(6).setCellValue(bean.getStateShare()==null?0.0:bean.getStateShare().doubleValue());
	        	
	        	totTreasRpt = totTreasRpt + (bean.getTreasuryReceipt()==null?0.0:bean.getTreasuryReceipt().doubleValue());
	        	totCentralShare = totCentralShare + (bean.getCentralShare()==null?0.0:bean.getCentralShare().doubleValue());
	        	totStateShare = totStateShare + (bean.getStateShare()==null?0.0:bean.getStateShare().doubleValue());
				
				
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
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totTreasRpt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCentralShare);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totStateShare);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 6);
	        String fileName = "attachment; filename=Report PF2- State Detail.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/pfmstresuredata";
		
	}
	
	@RequestMapping(value = "/downloadExcelcgidetailrpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelcgidetailrpt(HttpServletRequest request, HttpServletResponse response) 
	{
		List<PfmsCgireceiptDetaildata> list = new ArrayList<PfmsCgireceiptDetaildata>();
		list= cgidata.getPfmscgidetaildata();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF6 - GOI Release To State Level");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF6 - GOI Release To State Level Implementing Agency";
			String areaAmtValDetail = "All amounts are Rs.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);
			
        
	        mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,5); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Financial Year");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Sanction No.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Transaction Id");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Transaction Date");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Release from Govt. India (Central Share)");  
			cell.setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i=0;i<7;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 7;
	        double totTotalAmt = 0.0;
	        SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        for(PfmsCgireceiptDetaildata bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getIwmpState().getStName());
	        	row.createCell(2).setCellValue(bean.getFinancialYear());
	        	row.createCell(3).setCellValue(bean.getSanctionNo());
	        	row.createCell(4).setCellValue(bean.getTransactionId());
	        	row.createCell(5).setCellValue(DateFormat.format(bean.getTransactionDate()));
	        	row.createCell(6).setCellValue(bean.getTotalAmount()==null?0.0:bean.getTotalAmount().doubleValue());
	        	
	        	
	        	totTotalAmt = totTotalAmt + (bean.getTotalAmount()==null?0.0:bean.getTotalAmount().doubleValue());
				
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
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totTotalAmt);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 6);
	        String fileName = "attachment; filename=Report PF6.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/pfmscgidetaildata";
		
	}
	
	@RequestMapping(value = "/downloadExcelGoiReleaseToStateTreasury", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelGoiReleaseToStateTreasury(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<GetGoiReleaseToStateTreasury> list=new ArrayList<GetGoiReleaseToStateTreasury>();
		String fyearParam = request.getParameter("fyear");

		 finyr = 0; 
		 if (fyearParam != null && !fyearParam.isEmpty()) {
		   
			finyr = Integer.parseInt(fyearParam);
		}
		    list = cgidata.getGoiReleaseToStateTreasury(finyr);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF1 - Central Share Release Data to Various State Treasury");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
			
			String rptName = "";
			if(finyr==0) {
				rptName = "Report PF1 - Central Share Release Data to Various State Treasury for All Financial Year";
			}
			if(finyr>0) {
				rptName = "Report PF1 - Central Share Release Data to Various State Treasury for Financial Year "+" '20"+finyr+"-"+"20"+(Integer.parseInt(fyearParam)+1)+"' ";
			}
			String areaAmtValDetail = "";
	        
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Financial Year");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Sanction No.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Transaction Id");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Transaction Date");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Release from Govt. India(Central Share)(in Rs.)");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i=0;i<7;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 7;
	        double stTotalAmt = 0.0;
	        double totTotalAmt = 0.0;
	        SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        String StName = "";
	        
	        for(GetGoiReleaseToStateTreasury bean: list) {
	        	
	        	if( sno==list.size()) {
	        		Row row = sheet.createRow(rowno);
		        	row.createCell(0).setCellValue(sno); 
		        	row.createCell(1).setCellValue(bean.getIwmpStateByStCode().getStName());
		        	row.createCell(2).setCellValue(bean.getFinancialyear());
		        	row.createCell(3).setCellValue(bean.getSanctionNumber());
		        	row.createCell(4).setCellValue(bean.getTransaction_id());
		        	row.createCell(5).setCellValue(DateFormat.format(bean.getTransactionDate()));
		        	row.createCell(6).setCellValue(bean.getSanctionAmount()==null?0.0:bean.getSanctionAmount().doubleValue());
		        	stTotalAmt = stTotalAmt + (bean.getSanctionAmount()==null?0.0:bean.getSanctionAmount().doubleValue());
		        	rowno++;
	        	}
        		if((!StName.equals(bean.getIwmpStateByStCode().getStName()) && sno !=1)||sno==list.size()) {
        		Row row = sheet.createRow(rowno);
   	        	mergedRegion = new CellRangeAddress(rowno,rowno,0,5); 
   	 	        sheet.addMergedRegion(mergedRegion);
   	 	       
   		        cell = row.createCell(0);
   		        cell.setCellValue("State Total");
   		        cell.setCellStyle(style);
   		        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
   		        cell = row.createCell(1);
   		        cell.setCellStyle(style);
   		        cell = row.createCell(2);
   		        cell.setCellStyle(style);
   		        cell = row.createCell(3);
   		        cell.setCellStyle(style);
   		        cell = row.createCell(4);
   		        cell.setCellStyle(style);
   		        cell = row.createCell(5);
   		        cell.setCellStyle(style);
   		        cell = row.createCell(6);
   		        cell.setCellValue(stTotalAmt);
   		        cell.setCellStyle(style);
   		        stTotalAmt =0.0;
   		        rowno++;
   	        	}
        		if( sno!=list.size()) {
        		Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getIwmpStateByStCode().getStName());
	        	row.createCell(2).setCellValue(bean.getFinancialyear());
	        	row.createCell(3).setCellValue(bean.getSanctionNumber());
	        	row.createCell(4).setCellValue(bean.getTransaction_id());
	        	row.createCell(5).setCellValue(DateFormat.format(bean.getTransactionDate()));
	        	row.createCell(6).setCellValue(bean.getSanctionAmount()==null?0.0:bean.getSanctionAmount().doubleValue());
	        	stTotalAmt = stTotalAmt + (bean.getSanctionAmount()==null?0.0:bean.getSanctionAmount().doubleValue());
	        	rowno++;
        		}
        		sno++;
	        	totTotalAmt = totTotalAmt + (bean.getSanctionAmount()==null?0.0:bean.getSanctionAmount().doubleValue());
	        	
	        	StName = bean.getIwmpStateByStCode().getStName();
	        	
	        	//Row row1 = sheet.createRow(rowno);
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
			style1.setFont(font1);
	        
			mergedRegion = new CellRangeAddress(rowno,rowno,0,5); 
	        sheet.addMergedRegion(mergedRegion);
			
	        Row row1 = sheet.createRow(rowno);
	        cell = row1.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row1.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row1.createCell(2);
	        cell.setCellStyle(style1);
	        cell = row1.createCell(3);
	        cell.setCellStyle(style1);
	        cell = row1.createCell(4);
	        cell.setCellStyle(style1);
	        cell = row1.createCell(5);
	        cell.setCellStyle(style1);
	        cell = row1.createCell(6);
	        cell.setCellValue(totTotalAmt);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowno, 6);
	        String fileName = "attachment; filename=Report PF1.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/pfmsGetGoiReleaseStTreasuryRpt";
		
	}
	
}
