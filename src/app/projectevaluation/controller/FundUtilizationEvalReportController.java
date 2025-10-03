package app.projectevaluation.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.projectevaluation.bean.FundUtilizationEvalReportBean;
import app.projectevaluation.service.FundUtilizationEvalReportService;
import app.service.DistrictMasterService;
import app.service.StateMasterService;

@Controller("FundUtilizationEvalReportController")
public class FundUtilizationEvalReportController {
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
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
	
	@RequestMapping(value = "/getProjFundUtilizationEvalReport", method = RequestMethod.GET)
	public ModelAndView getProjFundUtilizationEvalReport(HttpServletRequest request, HttpServletResponse response) {
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		
		ModelAndView mav = new ModelAndView();
		try {
				mav = new ModelAndView("reports/ProjFundEvalReport");
				List<FundUtilizationEvalReportBean> listP = new ArrayList<FundUtilizationEvalReportBean>();
				 listP = fundService.getProjFundUtilizationEvalReport(dcode);
					
				 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(dcode).entrySet().iterator().next();
				 mav.addObject("stName",stateMasterService.getStateByDistCode(dcode).get(entry.getKey()));	
				 mav.addObject("distName",districtMasterService.getDistrictByDistCode(dcode).get(dcode));	
				 mav.addObject("fundPList",listP);
				mav.addObject("fundListPSize",listP.size());
				mav.addObject("dcode", dcode);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		

		@RequestMapping(value = "/getDistMandaysDetailsReport", method = RequestMethod.GET)
		public ModelAndView a(HttpServletRequest request) {
			
			String stcd = request.getParameter("stcd");
			String stName = request.getParameter("stName");
			String distName = request.getParameter("distName");
			
			List<FundUtilizationEvalReportBean> listD = new ArrayList<FundUtilizationEvalReportBean>();
			ModelAndView mav = new ModelAndView("reports/DistMandaysDetailsReport"); 
			
			listD = fundService.getDistMandaysDetailsReport(Integer.parseInt(stcd));
			
			mav.addObject("stcd",stcd);
			mav.addObject("stName",stName);
			mav.addObject("distName",distName);
			mav.addObject("manDList",listD);
			mav.addObject("manDListSize",listD.size());

			return mav;
		}
		
		@RequestMapping(value = "/getProdDetailsReport", method = RequestMethod.GET)
		public ModelAndView getProdDetailsReport(HttpServletRequest request) 
		{
		
			List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();
			ModelAndView mav = new ModelAndView("reports/ProdDetailsReport");
		
			list = fundService.getProdDetailsReport();
				
			mav.addObject("pList",list);
			mav.addObject("pListSize",list.size());
		
			return mav;
		}
		@RequestMapping(value = "/getDistProdDetailsReport", method = RequestMethod.GET)
		public ModelAndView getDistProdDetailsReport(HttpServletRequest request) {
			
			String stcd = request.getParameter("stcd");
			String stName = request.getParameter("stName");
			String distName = request.getParameter("distName");
			
			List<FundUtilizationEvalReportBean> listPD = new ArrayList<FundUtilizationEvalReportBean>();
			ModelAndView mav = new ModelAndView("reports/DistProdDetailReport"); 
			
			listPD = fundService.getDistProdDetailsReport(Integer.parseInt(stcd));
			
			mav.addObject("stcd",stcd);
			mav.addObject("stName",stName);
			mav.addObject("distName",distName);
			mav.addObject("prodDList",listPD);
			mav.addObject("prodListDSize",listPD.size());

			return mav;
		}
		
		@RequestMapping(value = "/getGradeEvaluationReport", method = RequestMethod.GET)
		public ModelAndView getGradeEvaluationReport(HttpServletRequest request) 
		{
			
			List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();
			ModelAndView mav = new ModelAndView("reports/GradeEvalReport");
			
			list = fundService.getGradeEvaluationReport();
					
			mav.addObject("gradeList",list);
			mav.addObject("gradeListSize",list.size());
			
			return mav;
		}
		
		@RequestMapping(value = "/downloadDistProdDetailsReportPdf", method = RequestMethod.POST)
		public ModelAndView downloadDistProdDetailsReportPdf(HttpServletRequest request, HttpServletResponse response) 
		{
			String stcd = request.getParameter("stcd");
			String stName = request.getParameter("stName");
			List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();

			list = fundService.getDistProdDetailsReport(Integer.parseInt(stcd));

			try {
				Rectangle layout = new Rectangle(PageSize.A4.rotate());
				layout.setBackgroundColor(new BaseColor(255, 255, 255));
				Document document = new Document(layout, 25, 10, 10, 0);
				document.addTitle("ProdReport");
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
				
				paragraph3 = new Paragraph("Report PE8- District-wise Mid Term Evaluation of Production Details (Milk, Fodder, Migration from Rural to Urban, Springs Rejuvenated and Persons Benefitted)", f3);
					
				paragraph2.setAlignment(Element.ALIGN_CENTER);
			    paragraph3.setAlignment(Element.ALIGN_CENTER);
			    paragraph2.setSpacingAfter(10);
			    paragraph3.setSpacingAfter(10);
			    CommonFunctions.addHeader(document);
			    document.add(paragraph2);
			    document.add(paragraph3);
			    table = new PdfPTable(18);
			    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
			    table.setWidthPercentage(90);
			    table.setSpacingBefore(0f);
			    table.setSpacingAfter(0f);
			    table.setHeaderRows(4);
			    CommonFunctions.insertCellHeader(table,"State Name : "+ stName, Element.ALIGN_LEFT, 18, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Total No. of Projects", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 6, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Annual Migration from Rural to Urban Area in Project Area (Nos.)", Element.ALIGN_CENTER, 3, 2, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "No. of Springs Rejuvenated", Element.ALIGN_CENTER, 3, 2, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "No. of Persons Benefitted due to Rejuvenation of Springs", Element.ALIGN_CENTER, 3, 2, bf8Bold);
			    
			    CommonFunctions.insertCellHeader(table, "Milk Production of Milch Cattle (Kl/Yr.)	", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Fodder Production (Qt./Yr.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			    
			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // SC
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // SS
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // SC
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // SS
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // SC
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // SS
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // SC
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // SS
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // SC
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // SS
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);


			    for (int i = 1; i <= 18; i++) {
			    	CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_LEFT, 1, 1, bf8Bold);
			    }
				int k = 1;
				int totproj = 0;
				BigDecimal premilk = BigDecimal.ZERO;
				BigDecimal midmilk = BigDecimal.ZERO;
				BigDecimal controlmilk = BigDecimal.ZERO;
				BigDecimal prefod = BigDecimal.ZERO;
				BigDecimal midfod = BigDecimal.ZERO;
				BigDecimal controlfod = BigDecimal.ZERO;
				BigInteger prerural = BigInteger.ZERO;
				BigInteger midrural = BigInteger.ZERO;
				BigInteger controlrural = BigInteger.ZERO;
				BigInteger spring = BigInteger.ZERO;
				BigInteger mspring = BigInteger.ZERO;
				BigInteger controlspring = BigInteger.ZERO;
				BigInteger personb = BigInteger.ZERO;
				BigInteger mpersonb = BigInteger.ZERO;
				BigInteger controlpersonb = BigInteger.ZERO;
				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getTotal_project().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_milch_cattle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_milch_cattle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getControl_milch_cattle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_fodder_production().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_fodder_production().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getControl_fodder_production().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_rural_urban().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_rural_urban().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getControl_rural_urban().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_spring_rejuvenated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_spring_rejuvenated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_person_benefitte().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_person_benefitte().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
						totproj = totproj + list.get(i).getTotal_project();
						premilk = premilk.add(list.get(i).getPre_milch_cattle());
					    midmilk = midmilk.add(list.get(i).getMid_milch_cattle());
					    controlmilk = controlmilk.add(list.get(i).getControl_milch_cattle());
					    prefod = prefod.add( list.get(i).getPre_fodder_production());
					    midfod = midfod.add(list.get(i).getMid_fodder_production());
					    controlfod = controlfod.add(list.get(i).getControl_fodder_production());
					    prerural = prerural.add(list.get(i).getPre_rural_urban());
					    midrural = midrural.add(list.get(i).getMid_rural_urban());
					    controlrural = controlrural.add(list.get(i).getControl_rural_urban());
					    spring = spring.add(list.get(i).getPre_spring_rejuvenated());
					    mspring = mspring.add(list.get(i).getMid_spring_rejuvenated());
					    controlspring = controlspring.add(list.get(i).getControl_spring_rejuvenated());
					    personb = personb.add(list.get(i).getPre_person_benefitte());
					    mpersonb = mpersonb.add(list.get(i).getMid_person_benefitte());
					    controlpersonb = controlpersonb.add(list.get(i).getControl_person_benefitte());
						k++;
					}
					
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(premilk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(midmilk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(controlmilk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(prefod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(midfod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(controlfod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(prerural), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(midrural), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(controlrural), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(spring), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(mspring), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(personb), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(mpersonb), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(list.size()==0) 
						CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 18, 1, bf8);
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
			response.setHeader("Content-Disposition", "attachment;filename=Report PE8- District.pdf");
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

		@RequestMapping(value = "/downloadProdDetailsReportPdf", method = RequestMethod.POST)
		public ModelAndView downloadProdDetailsReportPdf(HttpServletRequest request, HttpServletResponse response) 
		{
			
			List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();

			list = fundService.getProdDetailsReport();
			
			try {
				
				Rectangle layout = new Rectangle(PageSize.A4.rotate());
				layout.setBackgroundColor(new BaseColor(255, 255, 255));
				Document document = new Document(layout, 25, 10, 10, 0);
				document.addTitle("ProdReport");
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
				
				paragraph3 = new Paragraph("Report PE8- State-wise Mid Term Evaluation of Production Details(Milk, Fodder, Migration from Rural to Urban, Springs Rejuvenated and Persons Benefitted)", f3);
					
				paragraph2.setAlignment(Element.ALIGN_CENTER);
			    paragraph3.setAlignment(Element.ALIGN_CENTER);
			    paragraph2.setSpacingAfter(10);
			    paragraph3.setSpacingAfter(10);
			    CommonFunctions.addHeader(document);
			    document.add(paragraph2);
			    document.add(paragraph3);
			    table = new PdfPTable(18);
			    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
			    table.setWidthPercentage(90);
			    table.setSpacingBefore(0f);
			    table.setSpacingAfter(0f);
			    table.setHeaderRows(4);
			    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Total No. of Projects", Element.ALIGN_CENTER, 1, 3, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 6, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Annual Migration from Rural to Urban Area in Project Area (Nos.)", Element.ALIGN_CENTER, 3, 2, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "No. of Springs Rejuvenated", Element.ALIGN_CENTER, 3, 2, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "No. of Persons Benefitted due to Rejuvenation of Springs", Element.ALIGN_CENTER, 3, 2, bf8Bold);
			    
			    CommonFunctions.insertCellHeader(table, "Milk Production of Milch Cattle (Kl/Yr.)	", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Fodder Production (Qt./Yr.)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			    
			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // SC
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // SS
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // SC
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // SS
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // SC
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // SS
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold); // TE
			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR-CS

			    for (int i = 1; i <= 18; i++) {
			    	CommonFunctions.insertCellHeader(table, String.valueOf(i), Element.ALIGN_LEFT, 1, 1, bf8Bold);
			    }
				int k = 1;
				int totproj = 0;
				BigDecimal premilk = BigDecimal.ZERO;
				BigDecimal midmilk = BigDecimal.ZERO;
				BigDecimal controlmilk = BigDecimal.ZERO;
				BigDecimal prefod = BigDecimal.ZERO;
				BigDecimal midfod = BigDecimal.ZERO;
				BigDecimal controlfod = BigDecimal.ZERO;
				BigInteger prerural = BigInteger.ZERO;
				BigInteger midrural = BigInteger.ZERO;
				BigInteger controlrural = BigInteger.ZERO;
				BigInteger spring = BigInteger.ZERO;
				BigInteger mspring = BigInteger.ZERO;
				BigInteger controlspring = BigInteger.ZERO;
				BigInteger personb = BigInteger.ZERO;
				BigInteger mpersonb = BigInteger.ZERO;
				BigInteger controlpersonb = BigInteger.ZERO;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getTotal_project().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_milch_cattle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_milch_cattle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getControl_milch_cattle().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_fodder_production().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_fodder_production().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getControl_fodder_production().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_rural_urban().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_rural_urban().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getControl_rural_urban().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_spring_rejuvenated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_spring_rejuvenated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_person_benefitte().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_person_benefitte().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
						totproj = totproj + list.get(i).getTotal_project();
						premilk = premilk.add(list.get(i).getPre_milch_cattle());
					    midmilk = midmilk.add(list.get(i).getMid_milch_cattle());
					    controlmilk = controlmilk.add(list.get(i).getControl_milch_cattle());
					    prefod = prefod.add( list.get(i).getPre_fodder_production());
					    midfod = midfod.add(list.get(i).getMid_fodder_production());
					    controlfod = controlfod.add(list.get(i).getControl_fodder_production());
					    prerural = prerural.add(list.get(i).getPre_rural_urban());
					    midrural = midrural.add(list.get(i).getMid_rural_urban());
					    controlrural = controlrural.add(list.get(i).getControl_rural_urban());
					    spring = spring.add(list.get(i).getPre_spring_rejuvenated());
					    mspring = mspring.add(list.get(i).getMid_spring_rejuvenated());
					    controlspring = controlspring.add(list.get(i).getControl_spring_rejuvenated());
					    personb = personb.add(list.get(i).getPre_person_benefitte());
					    mpersonb = mpersonb.add(list.get(i).getMid_person_benefitte());
					    controlpersonb = controlpersonb.add(list.get(i).getControl_person_benefitte());
						k++;
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(premilk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(midmilk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(controlmilk), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(prefod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(midfod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(controlfod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(prerural), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(midrural), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(controlrural), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(spring), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(mspring), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(personb), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(mpersonb), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(list.size()==0) 
						CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 18, 1, bf8);
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
			response.setHeader("Content-Disposition", "attachment;filename=Report PE8- State.pdf");
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

		@RequestMapping(value = "/downloadDistMandaysDetailsReportPdf", method = RequestMethod.POST)
		public ModelAndView downloadDistMandaysDetailsReportPdf(HttpServletRequest request, HttpServletResponse response) 
		{
			String stcd = request.getParameter("stcd");
			String stName = request.getParameter("stName");
			List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();

			list = fundService.getDistMandaysDetailsReport(Integer.parseInt(stcd));
			
			try {
				Rectangle layout = new Rectangle(PageSize.A4.rotate());
				layout.setBackgroundColor(new BaseColor(255, 255, 255));
				Document document = new Document(layout, 25, 10, 10, 0);
				document.addTitle("MandaysReport");
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
				paragraph3 = new Paragraph("Report PE7- District-wise Mid Term Evaluation of Mandays Details ", f3);
				paragraph2.setAlignment(Element.ALIGN_CENTER);
			    paragraph3.setAlignment(Element.ALIGN_CENTER);
			    paragraph2.setSpacingAfter(10);
			    paragraph3.setSpacingAfter(10);
			    CommonFunctions.addHeader(document);
			    document.add(paragraph2);
			    document.add(paragraph3);
			    table = new PdfPTable(10);
			    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5});
			    table.setWidthPercentage(90);
			    table.setSpacingBefore(0f);
			    table.setSpacingAfter(0f);
			    table.setHeaderRows(3);

			    CommonFunctions.insertCellHeader(table, "State Name:  "+ stName, Element.ALIGN_LEFT, 10, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Total No. of Projects", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Farmer`s Average Household Income per Annum (Rs. in Lakhs)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "No. of Farmers Benefited", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "No. of Persondays Generated (man-days)", Element.ALIGN_CENTER, 2, 1, bf8Bold);

			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // SC
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // SS
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // TS
			    CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold); // TE
			    CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR-CS

			    CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "10",Element.ALIGN_CENTER, 1, 1, bf8Bold);
				int k = 1;
				int totproj = 0;
				BigInteger farbenefited = BigInteger.ZERO;
				BigInteger mandays = BigInteger.ZERO;
				BigDecimal pref = BigDecimal.ZERO;
				BigDecimal midf = BigDecimal.ZERO;
				BigDecimal conf = BigDecimal.ZERO;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getTotal_project().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_farmer_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_farmer_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getControl_farmer_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFarmer_benefited().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMandays_generated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
					    totproj = totproj + list.get(i).getTotal_project();
					    pref = pref.add(list.get(i).getPre_farmer_income());
					    midf = midf.add(list.get(i).getMid_farmer_income());
					    conf = conf.add(list.get(i).getControl_farmer_income());
					    farbenefited = farbenefited.add( list.get(i).getFarmer_benefited());
					    mandays = mandays.add(list.get(i).getMandays_generated());
						k++;
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pref), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(midf), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(conf), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(farbenefited), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(mandays), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
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
			response.setHeader("Content-Disposition", "attachment;filename=Report PE7- District.pdf");
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
		
		@RequestMapping(value = "/downloadMandaysDetailsReportPdf", method = RequestMethod.POST)
		public ModelAndView downloadMandaysDetailsReportPdf(HttpServletRequest request, HttpServletResponse response) 
		{
			
			List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();

			list = fundService.getMandaysDetailsReport();
			
			try {
				
				Rectangle layout = new Rectangle(PageSize.A4.rotate());
				layout.setBackgroundColor(new BaseColor(255, 255, 255));
				Document document = new Document(layout, 25, 10, 10, 0);
				document.addTitle("MandaysReport");
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
				
				paragraph3 = new Paragraph("Report PE7- State-wise Mid Term Evaluation of Mandays Details ", f3);
					
				paragraph2.setAlignment(Element.ALIGN_CENTER);
			    paragraph3.setAlignment(Element.ALIGN_CENTER);
			    paragraph2.setSpacingAfter(10);
			    paragraph3.setSpacingAfter(10);
			    CommonFunctions.addHeader(document);
			    document.add(paragraph2);
			    document.add(paragraph3);
			    table = new PdfPTable(10);
			    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5});
			    table.setWidthPercentage(90);
			    table.setSpacingBefore(0f);
			    table.setSpacingAfter(0f);
			    table.setHeaderRows(3);

			    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Total No. of Projects", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "Farmer`s Average Household Income per Annum (Rs. in Lakhs)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "No. of Farmers Benefited", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "No. of Persondays Generated (man-days)", Element.ALIGN_CENTER, 2, 1, bf8Bold);

			    CommonFunctions.insertCellHeader(table, "Pre - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold); // SC
			    CommonFunctions.insertCellHeader(table, "Mid - Project Status(Aggregate)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // SS
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);   // TS
			    CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold); // TE
			    CommonFunctions.insertCellHeader(table, "Project Area", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR
			    CommonFunctions.insertCellHeader(table, "Controlled Area", Element.ALIGN_CENTER, 1, 1, bf8Bold); // FR-CS

			    CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			    CommonFunctions.insertCellHeader(table, "10",Element.ALIGN_CENTER, 1, 1, bf8Bold);
				int k = 1;
				int totproj = 0;
				BigInteger farbenefited = BigInteger.ZERO;
				BigInteger contrfarbenefited = BigInteger.ZERO;
				BigInteger mandays = BigInteger.ZERO;
				BigInteger conmandays = BigInteger.ZERO;
				BigDecimal pref = BigDecimal.ZERO;
				BigDecimal midf = BigDecimal.ZERO;
				BigDecimal conf = BigDecimal.ZERO;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getTotal_project().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getPre_farmer_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMid_farmer_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getControl_farmer_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getFarmer_benefited().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getMandays_generated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf8);
					    totproj = totproj + list.get(i).getTotal_project();
					    pref = pref.add(list.get(i).getPre_farmer_income());
					    midf = midf.add(list.get(i).getMid_farmer_income());
					    conf = conf.add(list.get(i).getControl_farmer_income());
					    farbenefited = farbenefited.add( list.get(i).getFarmer_benefited());
					    contrfarbenefited = contrfarbenefited.add(list.get(i).getControl_farmer_benefited());
					    mandays = mandays.add(list.get(i).getMandays_generated());
					    conmandays = conmandays.add(list.get(i).getControl_mandays_generated());
						k++;
					}
					
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(pref), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(midf), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(conf), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(farbenefited), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(mandays), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "N/A", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
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
			response.setHeader("Content-Disposition", "attachment;filename=Report PE7- State.pdf");
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
		    table.setHeaderRows(3);

		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Total No. of Projects", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Project Area (in ha.)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Cost", Element.ALIGN_CENTER, 3, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Fund Utilization", Element.ALIGN_CENTER, 4, 1, bf8Bold);

		    CommonFunctions.insertCellHeader(table, "Central Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold); 
		    CommonFunctions.insertCellHeader(table, "State Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold);   
		    CommonFunctions.insertCellHeader(table, "Total Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold);   
		    CommonFunctions.insertCellHeader(table, "Fund Received (Rs. Crores)", Element.ALIGN_CENTER, 3, 1, bf8Bold); 
		    CommonFunctions.insertCellHeader(table, "Total Expenditure (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold); 

		    CommonFunctions.insertCellHeader(table, "Central Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
		    CommonFunctions.insertCellHeader(table, "State Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   
		    CommonFunctions.insertCellHeader(table, "Total Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   

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
	
	@RequestMapping(value = "/downloadProjFundUtilizationEvalReportPdf", method = RequestMethod.POST)
	public ModelAndView downloadProjFundUtilizationEvalReportPdf(HttpServletRequest request, HttpServletResponse response) 
	{
//		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		String dcode=request.getParameter("dcode");
		String distName=request.getParameter("distName");
		
		List<FundUtilizationEvalReportBean> listP = new ArrayList<FundUtilizationEvalReportBean>();

		listP = fundService.getProjFundUtilizationEvalReport(Integer.parseInt(dcode));
		
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
			
			paragraph3 = new Paragraph("Report PE2-  Project-wise Mid Term Evaluation of Sanctioned Cost and  Fund Utilization for District  '"+distName+ "' ", f3);
				
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(10);
		    paragraph3.setSpacingAfter(10);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(10);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(90);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table,"State Name : "+ stName, Element.ALIGN_LEFT, 11, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Project Area (in ha.)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Cost", Element.ALIGN_CENTER, 3, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Fund Utilization", Element.ALIGN_CENTER, 4, 1, bf8Bold);

		    CommonFunctions.insertCellHeader(table, "Central Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold); 
		    CommonFunctions.insertCellHeader(table, "State Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold);   
		    CommonFunctions.insertCellHeader(table, "Total Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold);   
		    CommonFunctions.insertCellHeader(table, "Fund Received (Rs. Crores)", Element.ALIGN_CENTER, 3, 1, bf8Bold); 
		    CommonFunctions.insertCellHeader(table, "Total Expenditure (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold); 

		    CommonFunctions.insertCellHeader(table, "Central Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
		    CommonFunctions.insertCellHeader(table, "State Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   
		    CommonFunctions.insertCellHeader(table, "Total Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   

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
				
			int k = 1;
			BigDecimal projArea = BigDecimal.ZERO;
			BigDecimal evCentralShare = BigDecimal.ZERO;
			BigDecimal evStateShare = BigDecimal.ZERO;
			BigDecimal evTotalshare = BigDecimal.ZERO;
			BigDecimal fundCentralshare = BigDecimal.ZERO;
			BigDecimal fundStateshare = BigDecimal.ZERO;
			BigDecimal fundTotalshare = BigDecimal.ZERO;
			BigDecimal projExpenditure = BigDecimal.ZERO;
			
			if(listP.size()!=0)
				for(int i=0;i<listP.size();i++) 
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listP.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listP.get(i).getTotal_project_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listP.get(i).getTotal_evaluation_central_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listP.get(i).getTotal_evaluation_state_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listP.get(i).getTotal_share_evaluation().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listP.get(i).getTotal_fund_central_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listP.get(i).getTotal_fund_state_share().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listP.get(i).getTotal_fund().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, listP.get(i).getTotal_expenditure().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
				    projArea = projArea.add(listP.get(i).getTotal_project_area());
				    evCentralShare = evCentralShare.add(listP.get(i).getTotal_evaluation_central_share());
				    evStateShare = evStateShare.add(listP.get(i).getTotal_evaluation_state_share());
				    evTotalshare = evTotalshare.add(listP.get(i).getTotal_share_evaluation());
				    fundCentralshare = fundCentralshare.add( listP.get(i).getTotal_fund_central_share());
				    fundStateshare = fundStateshare.add(listP.get(i).getTotal_fund_state_share());
				    fundTotalshare = fundTotalshare.add(listP.get(i).getTotal_fund());
				    projExpenditure = projExpenditure.add(listP.get(i).getTotal_expenditure());
					k++;
				}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(projArea), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(evCentralShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(evStateShare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(evTotalshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(fundCentralshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(fundStateshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(fundTotalshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(projExpenditure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(listP.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=Report PE2- Project.pdf");
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
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table,"State Name : "+ stName, Element.ALIGN_LEFT, 11, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Total No. of Projects", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Project Area (in ha.)", Element.ALIGN_CENTER, 1, 3, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Sanctioned Cost", Element.ALIGN_CENTER, 3, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "Fund Utilization", Element.ALIGN_CENTER, 4, 1, bf8Bold);

		    CommonFunctions.insertCellHeader(table, "Central Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold); 
		    CommonFunctions.insertCellHeader(table, "State Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold);   
		    CommonFunctions.insertCellHeader(table, "Total Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold);   
		    CommonFunctions.insertCellHeader(table, "Fund Received (Rs. Crores)", Element.ALIGN_CENTER, 3, 1, bf8Bold); 
		    CommonFunctions.insertCellHeader(table, "Total Expenditure (Rs. Crores)", Element.ALIGN_CENTER, 1, 2, bf8Bold); 

		    CommonFunctions.insertCellHeader(table, "Central Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold); 
		    CommonFunctions.insertCellHeader(table, "State Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   
		    CommonFunctions.insertCellHeader(table, "Total Share (Rs. Crores)", Element.ALIGN_CENTER, 1, 1, bf8Bold);   

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
	
	@RequestMapping(value = "/downloadExcelDistProdDetailsReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistProdDetailsReport(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();

		list = fundService.getDistProdDetailsReport(Integer.parseInt(stcd));
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report PE8- District-wise Mid Term Evaluation of Production Details(Milk, Fodder, Migration from Rural to Urban, Springs Rejuvenated and Persons Benefitted)");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE8- District-wise Mid Term Evaluation of Production Details(Milk, Fodder, Migration from Rural to Urban, Springs Rejuvenated and Persons Benefitted) for State '"+ stName+ "' ";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 17, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,0,0); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,1,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,2,2); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,3,8); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,9,11); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,12,14); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,15,17);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,5); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,6,8); 
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
		cell.setCellValue("Production");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<9;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Annual Migration from Rural to Urban Area in Project Area (Nos.)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<12;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(12);
		cell.setCellValue("No. of Springs Rejuvenated");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=13;i<15;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(15);
		cell.setCellValue("No. of Persons Benefitted due to Rejuvenation of Springs");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=16;i<18;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		Row rowhead1 = sheet.createRow(6);
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}

		cell = rowhead1.createCell(3);
		cell.setCellValue("Milk Production of Milch Cattle (Kl/Yr.)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<6;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Fodder Production (Qt./Yr.)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=7;i<18;i++)
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
		cell.setCellValue("Pre - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(4);
		cell.setCellValue("Mid - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(5);
		cell.setCellValue("Controlled Area");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(6);
		cell.setCellValue("Pre - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(7);
		cell.setCellValue("Mid - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(8);
		cell.setCellValue("Controlled Area");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Pre - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(10);
		cell.setCellValue("Mid - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(11);
		cell.setCellValue("Controlled Area");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(12);
		cell.setCellValue("Pre - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(13);
		cell.setCellValue("Mid - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(14);
		cell.setCellValue("Controlled Area");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(15);
		cell.setCellValue("Pre - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(16);
		cell.setCellValue("Mid - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(17);
		cell.setCellValue("Controlled Area");  
		cell.setCellStyle(style);
		
		
		Row rowhead3 = sheet.createRow(8);
		for(int i=0;i<18;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		int totproj = 0;
		BigDecimal premilk = BigDecimal.ZERO;
		BigDecimal midmilk = BigDecimal.ZERO;
		BigDecimal conmilk = BigDecimal.ZERO;
		BigDecimal prefod = BigDecimal.ZERO;
		BigDecimal midfod = BigDecimal.ZERO;
		BigDecimal confod = BigDecimal.ZERO;
		BigInteger prerural = BigInteger.ZERO;
		BigInteger midrural = BigInteger.ZERO;
		BigInteger conrural = BigInteger.ZERO;
		BigInteger prespring = BigInteger.ZERO;
		BigInteger midspring = BigInteger.ZERO;
		BigInteger conspring = BigInteger.ZERO;
		BigInteger prepersonb = BigInteger.ZERO;
		BigInteger midpersonb = BigInteger.ZERO;
		BigInteger conpersonb = BigInteger.ZERO;
		
	    for(FundUtilizationEvalReportBean bean: list) 
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getDist_name());
	    	row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getPre_milch_cattle().doubleValue());
	    	row.createCell(4).setCellValue(bean.getMid_milch_cattle().doubleValue());
	    	row.createCell(5).setCellValue(bean.getControl_milch_cattle().doubleValue());
	    	row.createCell(6).setCellValue(bean.getPre_fodder_production().doubleValue());
	    	row.createCell(7).setCellValue(bean.getMid_fodder_production().doubleValue());
	    	row.createCell(8).setCellValue(bean.getControl_fodder_production().doubleValue());
	    	row.createCell(9).setCellValue(bean.getPre_rural_urban().doubleValue());
	    	row.createCell(10).setCellValue(bean.getMid_rural_urban().doubleValue());
	    	row.createCell(11).setCellValue(bean.getControl_rural_urban().doubleValue());
	    	row.createCell(12).setCellValue(bean.getPre_spring_rejuvenated().doubleValue());
	    	row.createCell(13).setCellValue(bean.getMid_spring_rejuvenated().doubleValue());
	    	row.createCell(14).setCellValue("N/A");
	    	row.createCell(15).setCellValue(bean.getPre_person_benefitte().doubleValue());
	    	row.createCell(16).setCellValue(bean.getMid_person_benefitte().doubleValue());
	    	row.createCell(17).setCellValue("N/A");
	    	
	    	totproj = totproj + bean.getTotal_project();
			premilk = premilk.add(bean.getPre_milch_cattle());
			midmilk = midmilk.add(bean.getMid_milch_cattle());
			conmilk = conmilk.add(bean.getControl_milch_cattle());
			prefod = prefod.add(bean.getPre_fodder_production());
			midfod= midfod.add(bean.getMid_fodder_production());
			confod = confod.add(bean.getControl_fodder_production());
			prerural = prerural.add(bean.getPre_rural_urban());
			midrural = midrural.add(bean.getMid_rural_urban());
			conrural = conrural.add(bean.getControl_rural_urban());
			prespring = prespring.add(bean.getPre_spring_rejuvenated());
			midspring = midspring.add(bean.getMid_spring_rejuvenated());
			conspring = conspring.add(bean.getControl_spring_rejuvenated());
			prepersonb = prepersonb.add(bean.getPre_person_benefitte());
			midpersonb = midpersonb.add(bean.getMid_person_benefitte());
			conpersonb =  conpersonb.add(bean.getControl_person_benefitte());
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
		cell.setCellValue(totproj);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(premilk.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(midmilk.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(conmilk.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(prefod.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(midfod.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(confod.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(prerural.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(midrural.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(conrural.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(prespring.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(midspring.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue("N/A");
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(prepersonb.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(midpersonb.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(17);
		cell.setCellValue("N/A");
		cell.setCellStyle(style1);
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 17);
	    String fileName = "attachment; filename=Report PE8- District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/DistProdDetailReport";
	}
	
	@RequestMapping(value = "/downloadExcelProdDetailsReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProdDetailsReport(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<FundUtilizationEvalReportBean> list = new ArrayList<FundUtilizationEvalReportBean>();

		list = fundService.getProdDetailsReport();
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report PE8- State-wise Mid Term Evaluation of Production Details(Milk, Fodder, Migration from rural to urban, Springs rejuvenated and Persons benefitted)");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report PE8- State-wise Mid Term Evaluation of Production Details(Milk, Fodder, Migration from rural to urban, Springs rejuvenated and Persons benefitted)";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 17, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,0,0); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,1,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,7,2,2); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,3,8); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,9,11); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,12,14); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,15,17); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,5); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,6,8); 
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
		cell.setCellValue("Production");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<9;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Annual Migration from Rural to Urban area in project area (Nos.)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=10;i<12;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}

		cell = rowhead.createCell(12);
		cell.setCellValue("No. of Springs Rejuvenated");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

		for(int i=13;i<15;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(15);
		cell.setCellValue("No. of Persons Benefitted due to Rejuvenation of Springs");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=16;i<18;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		Row rowhead1 = sheet.createRow(6);
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}

		cell = rowhead1.createCell(3);
		cell.setCellValue("Milk Production of Milch Cattle (Kl/Yr.)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=4;i<6;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		cell = rowhead1.createCell(6);
		cell.setCellValue("Fodder Production (Qt./Yr.)");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=7;i<18;i++)
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
		cell.setCellValue("Pre - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(4);
		cell.setCellValue("Mid - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(5);
		cell.setCellValue("Controlled Area");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(6);
		cell.setCellValue("Pre - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(7);
		cell.setCellValue("Mid - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(8);
		cell.setCellValue("Controlled Area");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(9);
		cell.setCellValue("Pre - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(10);
		cell.setCellValue("Mid - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(11);
		cell.setCellValue("Controlled Area");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(12);
		cell.setCellValue("Pre - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(13);
		cell.setCellValue("Mid - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(14);
		cell.setCellValue("Controlled Area");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(15);
		cell.setCellValue("Pre - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(16);
		cell.setCellValue("Mid - Project Status(Aggregate)");  
		cell.setCellStyle(style);
		
		cell = rowhead2.createCell(17);
		cell.setCellValue("Controlled Area");  
		cell.setCellStyle(style);
		
		
		Row rowhead3 = sheet.createRow(8);
		for(int i=0;i<18;i++)
		{
			cell =rowhead3.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		int totproj = 0;
		BigDecimal premilk = BigDecimal.ZERO;
		BigDecimal midmilk = BigDecimal.ZERO;
		BigDecimal conmilk = BigDecimal.ZERO;
		BigDecimal prefod = BigDecimal.ZERO;
		BigDecimal midfod = BigDecimal.ZERO;
		BigDecimal confod = BigDecimal.ZERO;
		BigInteger prerural = BigInteger.ZERO;
		BigInteger midrural = BigInteger.ZERO;
		BigInteger conrural = BigInteger.ZERO;
		BigInteger prespring = BigInteger.ZERO;
		BigInteger midspring = BigInteger.ZERO;
		BigInteger conspring = BigInteger.ZERO;
		BigInteger prepersonb = BigInteger.ZERO;
		BigInteger midpersonb = BigInteger.ZERO;
		BigInteger conpersonb = BigInteger.ZERO;
		
	    for(FundUtilizationEvalReportBean bean: list) 
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getSt_name());
	    	row.createCell(2).setCellValue(bean.getTotal_project());
	    	row.createCell(3).setCellValue(bean.getPre_milch_cattle().doubleValue());
	    	row.createCell(4).setCellValue(bean.getMid_milch_cattle().doubleValue());
	    	row.createCell(5).setCellValue(bean.getControl_milch_cattle().doubleValue());
	    	row.createCell(6).setCellValue(bean.getPre_fodder_production().doubleValue());
	    	row.createCell(7).setCellValue(bean.getMid_fodder_production().doubleValue());
	    	row.createCell(8).setCellValue(bean.getControl_fodder_production().doubleValue());
	    	row.createCell(9).setCellValue(bean.getPre_rural_urban().doubleValue());
	    	row.createCell(10).setCellValue(bean.getMid_rural_urban().doubleValue());
	    	row.createCell(11).setCellValue(bean.getControl_rural_urban().doubleValue());
	    	row.createCell(12).setCellValue(bean.getPre_spring_rejuvenated().doubleValue());
	    	row.createCell(13).setCellValue(bean.getMid_spring_rejuvenated().doubleValue());
	    	row.createCell(14).setCellValue("N/A");
	    	row.createCell(15).setCellValue(bean.getPre_person_benefitte().doubleValue());
	    	row.createCell(16).setCellValue(bean.getMid_person_benefitte().doubleValue());
	    	row.createCell(17).setCellValue("N/A");
	    	
	    	totproj = totproj + bean.getTotal_project();
			premilk = premilk.add(bean.getPre_milch_cattle());
			midmilk = midmilk.add(bean.getMid_milch_cattle());
			conmilk = conmilk.add(bean.getControl_milch_cattle());
			prefod = prefod.add(bean.getPre_fodder_production());
			midfod= midfod.add(bean.getMid_fodder_production());
			confod = confod.add(bean.getControl_fodder_production());
			prerural = prerural.add(bean.getPre_rural_urban());
			midrural = midrural.add(bean.getMid_rural_urban());
			conrural = conrural.add(bean.getControl_rural_urban());
			prespring = prespring.add(bean.getPre_spring_rejuvenated());
			midspring = midspring.add(bean.getMid_spring_rejuvenated());
			conspring = conspring.add(bean.getControl_spring_rejuvenated());
			prepersonb = prepersonb.add(bean.getPre_person_benefitte());
			midpersonb = midpersonb.add(bean.getMid_person_benefitte());
			conpersonb =  conpersonb.add(bean.getControl_person_benefitte());
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
		cell.setCellValue(totproj);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(premilk.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(midmilk.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(conmilk.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(prefod.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(midfod.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(confod.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(prerural.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(midrural.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(conrural.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(prespring.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(midspring.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue("N/A");
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(prepersonb.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(midpersonb.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(17);
		cell.setCellValue("N/A");
		cell.setCellStyle(style1);
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 17);
	    String fileName = "attachment; filename=Report PE8- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/ProdDetailsReport";
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
		Sheet sheet = workbook.createSheet("Report PE2-  District-wise Mid Term Evaluation of Sanctioned Cost and  Fund Utilization");   
		
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
	
	@RequestMapping(value = "/downloadExcelProjFundUtilizationEvalReport", method = RequestMethod.POST)
	public void downloadExcelProjFundUtilizationEvalReport(HttpServletRequest request, HttpServletResponse response) {
	    String stName = request.getParameter("stName");
	    String dcode = request.getParameter("dcode");
	    String distName = request.getParameter("distName");

	    List<FundUtilizationEvalReportBean> listP =
	            fundService.getProjFundUtilizationEvalReport(Integer.parseInt(dcode));

	    Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Report PE2- Project-wise Mid Term Evaluation of Sanctioned Cost and Fund Utilization");

	    // Style
	    CellStyle style = CommonFunctions.getStyle(workbook);

	    // Header Title
	    String rptName = "Report PE2- Project-wise Mid Term Evaluation of Sanctioned Cost and Fund Utilization for State '" + stName + "'";
	    CommonFunctions.getExcelHeader(sheet, new CellRangeAddress(0, 0, 0, 0), rptName, 9, "", workbook);

	    // ===== MERGED REGIONS =====
	    sheet.addMergedRegion(new CellRangeAddress(5,7,0,0)); // S.No.
	    sheet.addMergedRegion(new CellRangeAddress(5,7,1,1)); // Project Name
	    sheet.addMergedRegion(new CellRangeAddress(5,7,2,2)); // Sanctioned Project Area
	    sheet.addMergedRegion(new CellRangeAddress(5,5,3,5)); // Sanctioned Cost
	    sheet.addMergedRegion(new CellRangeAddress(5,5,6,9)); // Fund Utilization
	    sheet.addMergedRegion(new CellRangeAddress(6,7,3,3)); // Central
	    sheet.addMergedRegion(new CellRangeAddress(6,7,4,4)); // State
	    sheet.addMergedRegion(new CellRangeAddress(6,7,5,5)); // Total
	    sheet.addMergedRegion(new CellRangeAddress(6,6,6,8)); // Fund Received
	    sheet.addMergedRegion(new CellRangeAddress(6,7,9,9)); // Expenditure

	    // ===== HEADER ROWS =====
	    Row rowhead = sheet.createRow(5);

	    Cell cell = rowhead.createCell(0);
	    cell.setCellValue("S.No.");  
	    cell.setCellStyle(style);

	    cell = rowhead.createCell(1);
	    cell.setCellValue("Project Name");  
	    cell.setCellStyle(style);

	    cell = rowhead.createCell(2);
	    cell.setCellValue("Sanctioned Project Area (in ha.)");  
	    cell.setCellStyle(style);

	    cell = rowhead.createCell(3);
	    cell.setCellValue("Sanctioned Cost");  
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	    cell = rowhead.createCell(6);
	    cell.setCellValue("Fund Utilization");  
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	    Row rowhead1 = sheet.createRow(6);

	    for(int i=0;i<3;i++) {
	        cell = rowhead1.createCell(i);
	        cell.setCellStyle(style);
	    }

	    cell = rowhead1.createCell(3);
	    cell.setCellValue("Central Share (Rs. Cr.)");
	    cell.setCellStyle(style);

	    cell = rowhead1.createCell(4);
	    cell.setCellValue("State Share (Rs. Cr.)");
	    cell.setCellStyle(style);

	    cell = rowhead1.createCell(5);
	    cell.setCellValue("Total Share (Rs. Cr.)");
	    cell.setCellStyle(style);

	    cell = rowhead1.createCell(6);
	    cell.setCellValue("Fund Received");
	    cell.setCellStyle(style);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	    
	    cell = rowhead1.createCell(9);
	    cell.setCellValue("Total Expenditure (Rs. Cr.)");
	    cell.setCellStyle(style);

	    Row rowhead2 = sheet.createRow(7);

	    for(int i=0;i<6;i++) {
	        cell = rowhead2.createCell(i);
	        cell.setCellStyle(style);
	    }

	    cell = rowhead2.createCell(6);
	    cell.setCellValue("Central Share (Rs. Cr.)");
	    cell.setCellStyle(style);

	    cell = rowhead2.createCell(7);
	    cell.setCellValue("State Share (Rs. Cr.)");
	    cell.setCellStyle(style);

	    cell = rowhead2.createCell(8);
	    cell.setCellValue("Total Share (Rs. Cr.)");
	    cell.setCellStyle(style);

	    cell = rowhead2.createCell(9);
	    cell.setCellStyle(style);

	    // ===== COLUMN NUMBERS =====
	    Row rowhead3 = sheet.createRow(8);
	    for(int i=0;i<10;i++) {
	        cell = rowhead3.createCell(i);
	        cell.setCellValue(i+1);
	        cell.setCellStyle(style);
	    }

	    // ===== DATA ROWS =====
	    int sno = 1;
	    int rowno = 9;
	    BigDecimal projArea = BigDecimal.ZERO;
	    BigDecimal evCentralShare = BigDecimal.ZERO;
	    BigDecimal evStateShare = BigDecimal.ZERO;
	    BigDecimal evTotalshare = BigDecimal.ZERO;
	    BigDecimal fundCentralshare = BigDecimal.ZERO;
	    BigDecimal fundStateshare = BigDecimal.ZERO;
	    BigDecimal fundTotalshare = BigDecimal.ZERO;
	    BigDecimal projExpenditure = BigDecimal.ZERO;

	    for(FundUtilizationEvalReportBean bean : listP) {
	        Row row = sheet.createRow(rowno);
	        row.createCell(0).setCellValue(sno);
	        row.createCell(1).setCellValue(bean.getProj_name());
	        row.createCell(2).setCellValue(bean.getTotal_project_area().doubleValue());
	        row.createCell(3).setCellValue(bean.getTotal_evaluation_central_share().doubleValue());
	        row.createCell(4).setCellValue(bean.getTotal_evaluation_state_share().doubleValue());
	        row.createCell(5).setCellValue(bean.getTotal_share_evaluation().doubleValue());
	        row.createCell(6).setCellValue(bean.getTotal_fund_central_share().doubleValue());
	        row.createCell(7).setCellValue(bean.getTotal_fund_state_share().doubleValue());
	        row.createCell(8).setCellValue(bean.getTotal_fund().doubleValue());
	        row.createCell(9).setCellValue(bean.getTotal_expenditure().doubleValue());

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

	    // ===== GRAND TOTAL =====
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

	    Row row = sheet.createRow(listP.size() + 9);
	    cell = row.createCell(0);
	    cell.setCellValue("Grand Total");
	    cell.setCellStyle(style1);
	    CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	    cell = row.createCell(1);
	    cell.setCellStyle(style1);

	    cell = row.createCell(2);
	    cell.setCellValue(projArea.doubleValue());
	    cell.setCellStyle(style1);

	    cell = row.createCell(3);
	    cell.setCellValue(evCentralShare.doubleValue());
	    cell.setCellStyle(style1);

	    cell = row.createCell(4);
	    cell.setCellValue(evStateShare.doubleValue());
	    cell.setCellStyle(style1);

	    cell = row.createCell(5);
	    cell.setCellValue(evTotalshare.doubleValue());
	    cell.setCellStyle(style1);

	    cell = row.createCell(6);
	    cell.setCellValue(fundCentralshare.doubleValue());
	    cell.setCellStyle(style1);

	    cell = row.createCell(7);
	    cell.setCellValue(fundStateshare.doubleValue());
	    cell.setCellStyle(style1);

	    cell = row.createCell(8);
	    cell.setCellValue(fundTotalshare.doubleValue());
	    cell.setCellStyle(style1);

	    cell = row.createCell(9);
	    cell.setCellValue(projExpenditure.doubleValue());
	    cell.setCellStyle(style1);

	    // Footer
	    CommonFunctions.getExcelFooter(sheet, new CellRangeAddress(0,0,0,0), listP.size(), 9);

	    // Download
	    String fileName = "attachment; filename=Report_PE2_Project.xlsx";
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	}


	    
	@RequestMapping(value = "/downloadExcelDistrictMandaysDetailsReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistrictMandaysDetailsReport(HttpServletRequest request, HttpServletResponse response) {
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
	    List<FundUtilizationEvalReportBean> list = fundService.getDistMandaysDetailsReport(Integer.parseInt(stcd));

	    Workbook workbook = new XSSFWorkbook();  
	    Sheet sheet = workbook.createSheet("Report PE7 - District-wise Mid Term Evaluation of Mandays Details");   

	    CellStyle style = CommonFunctions.getStyle(workbook);

	    String rptName = "Report PE7 - District-wise Mid Term Evaluation of Mandays Details for State '"+stName+"' ";
	    String areaAmtValDetail = "";

	    CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 9);
	    CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);

	    // ========== HEADER MERGES ==========
	    sheet.addMergedRegion(new CellRangeAddress(list.size()+8,list.size()+8,0,1));
	    sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0)); 
	    sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1)); 
	    sheet.addMergedRegion(new CellRangeAddress(5, 6, 2, 2)); 
	    sheet.addMergedRegion(new CellRangeAddress(5, 5, 3, 5)); 
	    sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 7)); 
	    sheet.addMergedRegion(new CellRangeAddress(5, 5, 8, 9)); 

	    // ========== HEADER ROWS ==========
	    Row row5 = sheet.createRow(5);
	    Cell cell = row5.createCell(0); 
	    cell.setCellValue("S.No."); 
	    cell.setCellStyle(style);
	    cell = row5.createCell(1); 
	    cell.setCellValue("District Name"); 
	    cell.setCellStyle(style);
	    cell = row5.createCell(2); 
	    cell.setCellValue("Total No. of Projects"); 
	    cell.setCellStyle(style);
	    cell = row5.createCell(3); 
	    cell.setCellValue("Farmer`s Average Household Income per Annum (Rs. in Lakhs)"); 
	    cell.setCellStyle(style);
	    cell = row5.createCell(6); 
	    cell.setCellValue("No. of Farmers Benefited"); 
	    cell.setCellStyle(style);
	    cell = row5.createCell(8); 
	    cell.setCellValue("No. of Persondays Generated (man-days)"); 
	    cell.setCellStyle(style);

	    Row row6 = sheet.createRow(6);
	    for (int i = 0; i <= 9; i++) row6.createCell(i).setCellStyle(style);

	    row6.getCell(3).setCellValue("Pre - Project Status(Aggregate)");
	    row6.getCell(4).setCellValue("Mid - Project Status(Aggregate)");
	    row6.getCell(5).setCellValue("Controlled Area");
	    row6.getCell(6).setCellValue("Project Area");
	    row6.getCell(7).setCellValue("Controlled Area");
	    row6.getCell(8).setCellValue("Project Area");
	    row6.getCell(9).setCellValue("Controlled Area");


	    Row row7 = sheet.createRow(7);
	    for (int i = 0; i <= 9; i++) {
	        Cell c = row7.createCell(i);
	        c.setCellValue(i + 1);
	        c.setCellStyle(style);
	    }

	    int sno = 1;
	    int rowno = 8;
	    int totproj = 0;
	    BigDecimal pref = BigDecimal.ZERO;
	    BigDecimal midf = BigDecimal.ZERO;
	    BigDecimal cf = BigDecimal.ZERO;
	    BigInteger fb = BigInteger.ZERO;
//	    BigInteger fbc = BigInteger.ZERO;
	    BigInteger man = BigInteger.ZERO;
//	    BigInteger cman = BigInteger.ZERO;

	    for (FundUtilizationEvalReportBean bean : list) {
	        Row row = sheet.createRow(rowno);
	        row.createCell(0).setCellValue(sno);
	        row.createCell(1).setCellValue(bean.getDist_name());
	        row.createCell(2).setCellValue(bean.getTotal_project());
	        row.createCell(3).setCellValue(bean.getPre_farmer_income().doubleValue());
	        row.createCell(4).setCellValue(bean.getMid_farmer_income().doubleValue());
	        row.createCell(5).setCellValue(bean.getControl_farmer_income().doubleValue());
	        row.createCell(6).setCellValue(bean.getFarmer_benefited().doubleValue());
	        row.createCell(7).setCellValue("N/A");
	        row.createCell(8).setCellValue(bean.getMandays_generated().doubleValue());
	        row.createCell(9).setCellValue("N/A");

	        totproj += bean.getTotal_project();
	        pref = pref.add(bean.getPre_farmer_income());
	        midf = midf.add(bean.getMid_farmer_income());
	        cf = cf.add(bean.getControl_farmer_income());
	        fb = fb.add(bean.getFarmer_benefited());
//	        fbc = fbc.add(bean.getControl_farmer_benefited());
	        man = man.add(bean.getMandays_generated());
//	        cman = cman.add(bean.getControl_mandays_generated());
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
	    cell = row.createCell(1); 
	    cell.setCellStyle(style1);
	    row.createCell(2).setCellValue(totproj); 
	    row.getCell(2).setCellStyle(style1);
	    row.createCell(3).setCellValue(pref.doubleValue()); 
	    row.getCell(3).setCellStyle(style1);
	    row.createCell(4).setCellValue(midf.doubleValue());
	    row.getCell(4).setCellStyle(style1);
	    row.createCell(5).setCellValue(cf.doubleValue());
	    row.getCell(5).setCellStyle(style1);
	    row.createCell(6).setCellValue(fb.doubleValue());
	    row.getCell(6).setCellStyle(style1);
	    row.createCell(7).setCellValue("N/A"); 
	    row.getCell(7).setCellStyle(style1);
	    row.createCell(8).setCellValue(man.doubleValue()); 
	    row.getCell(8).setCellStyle(style1);
	    row.createCell(9).setCellValue("N/A"); 
	    row.getCell(9).setCellStyle(style1);

	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);

	    for (int i = 0; i <= 9; i++) sheet.autoSizeColumn(i);

	    String fileName = "attachment; filename=Report PE7- District.xlsx";
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/DistMandaysDetailsReport";
	}

	@RequestMapping(value = "/downloadExcelMandaysDetailsReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelMandaysDetailsReport(HttpServletRequest request, HttpServletResponse response) {
	    
	    List<FundUtilizationEvalReportBean> list = fundService.getMandaysDetailsReport();

	    Workbook workbook = new XSSFWorkbook();  
	    Sheet sheet = workbook.createSheet("Report PE7 - State-wise Mid Term Evaluation of Mandays Details");   

	    CellStyle style = CommonFunctions.getStyle(workbook);

	    String rptName = "Report PE7 - State-wise Mid Term Evaluation of Mandays Details";
	    String areaAmtValDetail = "";

	    CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 9);
	    CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);

	    // ========== HEADER MERGES ==========
	    sheet.addMergedRegion(new CellRangeAddress(list.size()+8,list.size()+8,0,1));
	    sheet.addMergedRegion(new CellRangeAddress(5, 6, 0, 0)); 
	    sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1)); 
	    sheet.addMergedRegion(new CellRangeAddress(5, 6, 2, 2)); 
	    sheet.addMergedRegion(new CellRangeAddress(5, 5, 3, 5)); 
	    sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 7)); 
	    sheet.addMergedRegion(new CellRangeAddress(5, 5, 8, 9)); 

	    // ========== HEADER ROWS ==========
	    Row row5 = sheet.createRow(5);
	    Cell cell = row5.createCell(0); 
	    cell.setCellValue("S.No."); 
	    cell.setCellStyle(style);
	    cell = row5.createCell(1); 
	    cell.setCellValue("State Name"); 
	    cell.setCellStyle(style);
	    cell = row5.createCell(2); 
	    cell.setCellValue("Total No. of Projects"); 
	    cell.setCellStyle(style);
	    cell = row5.createCell(3); 
	    cell.setCellValue("Farmer`s Average Household Income per Annum (Rs. in Lakhs)"); 
	    cell.setCellStyle(style);
	    cell = row5.createCell(6); 
	    cell.setCellValue("No. of Farmers Benefited"); 
	    cell.setCellStyle(style);
	    cell = row5.createCell(8); 
	    cell.setCellValue("No. of Persondays Generated (man-days)"); 
	    cell.setCellStyle(style);

	    Row row6 = sheet.createRow(6);
	    for (int i = 0; i <= 9; i++) row6.createCell(i).setCellStyle(style);

	    row6.getCell(3).setCellValue("Pre - Project Status(Aggregate)");
	    row6.getCell(4).setCellValue("Mid - Project Status(Aggregate)");
	    row6.getCell(5).setCellValue("Controlled Area");
	    row6.getCell(6).setCellValue("Project Area");
	    row6.getCell(7).setCellValue("Controlled Area");
	    row6.getCell(8).setCellValue("Project Area");
	    row6.getCell(9).setCellValue("Controlled Area");


	    Row row7 = sheet.createRow(7);
	    for (int i = 0; i <= 9; i++) {
	        Cell c = row7.createCell(i);
	        c.setCellValue(i + 1);
	        c.setCellStyle(style);
	    }

	    int sno = 1;
	    int rowno = 8;
	    int totproj = 0;
	    BigDecimal pref = BigDecimal.ZERO;
	    BigDecimal midf = BigDecimal.ZERO;
	    BigDecimal cf = BigDecimal.ZERO;
	    BigInteger fb = BigInteger.ZERO;
	    BigInteger fbc = BigInteger.ZERO;
	    BigInteger man = BigInteger.ZERO;
	    BigInteger cman = BigInteger.ZERO;
	    for (FundUtilizationEvalReportBean bean : list) {
	        Row row = sheet.createRow(rowno);
	        row.createCell(0).setCellValue(sno);
	        row.createCell(1).setCellValue(bean.getSt_name());
	        row.createCell(2).setCellValue(bean.getTotal_project());
	        row.createCell(3).setCellValue(bean.getPre_farmer_income().doubleValue());
	        row.createCell(4).setCellValue(bean.getMid_farmer_income().doubleValue());
	        row.createCell(5).setCellValue(bean.getControl_farmer_income().doubleValue());
	        row.createCell(6).setCellValue(bean.getFarmer_benefited().doubleValue());
	        row.createCell(7).setCellValue("N/A");
	        row.createCell(8).setCellValue(bean.getMandays_generated().doubleValue());
	        row.createCell(9).setCellValue("N/A");

	        totproj += bean.getTotal_project();
	        pref = pref.add(bean.getPre_farmer_income());
	        midf = midf.add(bean.getMid_farmer_income());
	        cf = cf.add(bean.getControl_farmer_income());
	        fb = fb.add(bean.getFarmer_benefited());
	        fbc = fbc.add(bean.getControl_farmer_benefited());
	        man = man.add(bean.getMandays_generated());
	        cman = cman.add(bean.getControl_mandays_generated());
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
	    cell = row.createCell(1); 
	    cell.setCellStyle(style1);
	    row.createCell(2).setCellValue(totproj); 
	    row.getCell(2).setCellStyle(style1);
	    row.createCell(3).setCellValue(pref.doubleValue()); 
	    row.getCell(3).setCellStyle(style1);
	    row.createCell(4).setCellValue(midf.doubleValue());
	    row.getCell(4).setCellStyle(style1);
	    row.createCell(5).setCellValue(cf.doubleValue());
	    row.getCell(5).setCellStyle(style1);
	    row.createCell(6).setCellValue(fb.doubleValue());
	    row.getCell(6).setCellStyle(style1);
	    row.createCell(7).setCellValue("N/A"); 
	    row.getCell(7).setCellStyle(style1);
	    row.createCell(8).setCellValue(man.doubleValue()); 
	    row.getCell(8).setCellStyle(style1);
	    row.createCell(9).setCellValue("N/A"); 
	    row.getCell(9).setCellStyle(style1);

	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);

	    for (int i = 0; i <= 9; i++) sheet.autoSizeColumn(i);

	    String fileName = "attachment; filename=Report PE7- State.xlsx";
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/MandaysDetailsReport";
	}

	
}
