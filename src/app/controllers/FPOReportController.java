package app.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import app.bean.AddOutcomeParaBean;
import app.bean.BaseLineOutcomeBean;
import app.bean.FPOReportBean;
import app.bean.TarAchProjOutcomeBean;
import app.common.CommonFunctions;
import app.model.IwmpMProject;
import app.model.PfmsCgireceiptDetaildata;
import app.model.outcome.FpoDetail;
import app.service.FPOMasterService;
import app.service.StateMasterService;

@Controller("FPOReportController")
public class FPOReportController {

	@Autowired
	StateMasterService stateMasterService;

	@Autowired(required = true)
	FPOMasterService fpoMasterService;

	@RequestMapping(value = "/fporeport", method = RequestMethod.GET)
	public ModelAndView fporeport(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/fpodatareport");
		mav.addObject("stateList", stateMasterService.getAllState());

		return mav;
	}

	@RequestMapping(value = "/getfpodata", method = RequestMethod.POST)
	public String getfpodata(HttpServletRequest request, @RequestParam(value = "state") Integer state, Model model) {
		ModelAndView mav = new ModelAndView("reports/fpodatareport");
		List<FPOReportBean> beanList = new ArrayList<FPOReportBean>();
		beanList = fpoMasterService.getfpodatastatewise(state);
		model.addAttribute("state", state);
		model.addAttribute("projectList", beanList);
		model.addAttribute("stateList", stateMasterService.getAllState());
		return "reports/fpodatareport";

	}

	@RequestMapping(value = "/newFPODist", method = RequestMethod.GET)
	public String newFPODist(HttpServletRequest request, Model model) {
		int statecode = Integer.parseInt(request.getParameter("stcode"));
		String stname = "";
		LinkedHashMap<Integer, String> stateList = new LinkedHashMap<>();
		stateList = stateMasterService.getAllState();
		for(Map.Entry<Integer, String> map : stateList.entrySet()) {
			if(map.getKey()==statecode) {
				stname = map.getValue();
			}
		}
		System.out.println("statecode:" + statecode +"stname : "+stname);
		ModelAndView mav = new ModelAndView("reports/fpodatareport");
		List<FPOReportBean> beanList = new ArrayList<FPOReportBean>();
		List<FPOReportBean> beansList = new ArrayList<FPOReportBean>();
		beanList = fpoMasterService.getfpodatadistwise(statecode);
		beansList = fpoMasterService.getfpodatastatewise(statecode);
		model.addAttribute("projectDList", beanList);
		model.addAttribute("projectsList", beansList);
		model.addAttribute("state", statecode);
		model.addAttribute("stname", stname);
		model.addAttribute("stateList", stateMasterService.getAllState());
		return "reports/fpodatareport";

	}

	@RequestMapping(value = "/newFPOProj", method = RequestMethod.GET)
	public String newFPOProj(HttpServletRequest request, Model model) {
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String fpoType = request.getParameter("fpoType");
		String stname = "";
		LinkedHashMap<Integer, String> stateList = new LinkedHashMap<>();
		stateList = stateMasterService.getAllState();
		for(Map.Entry<Integer, String> map : stateList.entrySet()) {
			if(map.getKey()==stcode) {
				stname = map.getValue();
			}
		}
		ModelAndView mav = new ModelAndView("reports/fpodatareport");
		List<FpoDetail> beanList = new ArrayList<FpoDetail>();
		beanList = fpoMasterService.getfpodataprojwise(dcode, fpoType);
		model.addAttribute("projectpList", beanList);
		model.addAttribute("dcode", dcode);
		model.addAttribute("state", stcode);
		model.addAttribute("fpoType", fpoType);
		model.addAttribute("stname", stname);
		model.addAttribute("stateList", stateMasterService.getAllState());
		return "reports/fpodatareport";

	}

	@RequestMapping(value = "/newFPOAllProj", method = RequestMethod.GET)
	public String newFPOAllProj(HttpServletRequest request, Model model) {
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String fpoType = request.getParameter("fpoType");
		ModelAndView mav = new ModelAndView("reports/fpodatareport");
		List<FpoDetail> beanList = new ArrayList<FpoDetail>();
		List<FPOReportBean> beanTotalList = new ArrayList<FPOReportBean>();
		beanList = fpoMasterService.getfpodataallprojwise(stcode, fpoType);
		beanTotalList = fpoMasterService.getfpodatastatewise(stcode);
		String stname = "";
		LinkedHashMap<Integer, String> stateList = new LinkedHashMap<>();
		stateList = stateMasterService.getAllState();
		for(Map.Entry<Integer, String> map : stateList.entrySet()) {
			if(map.getKey()==stcode) {
				stname = map.getValue();
			}
		}
		model.addAttribute("projectAllList", beanList);
		model.addAttribute("projectTotalList", beanTotalList);
		model.addAttribute("state", stcode);
		model.addAttribute("stcode", stcode);
		model.addAttribute("stname", stname);
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("fpoType", fpoType);
		return "reports/fpodatareport";

	}
	
	@RequestMapping(value = "/downloadFpoDataPDF", method = RequestMethod.POST)
	public ModelAndView downloadFpoDataPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String state=request.getParameter("state");
		
		List<FPOReportBean> fpoBean = new ArrayList<FPOReportBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("FPODataReport");
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
			
				paragraph3 = new Paragraph("Report OC2- State-wise Details of Farmer Producer Organisations (FPOs) ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(6);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(2);

				int newfpono=0,fpofarmerasso=0,oldfpono=0,oldfarmerasso=0;

				
				fpoBean=fpoMasterService.getfpodatastatewise(Integer.parseInt(state));
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "New FPO Created", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Existing FPO", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Farmers Associated with FPO", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "No", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Farmers Associated with FPO", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				
				
				int k=1;
				if(fpoBean.size()!=0)
					for(int i=0;i<fpoBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, fpoBean.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, (fpoBean.get(i).getNewfpono() != null) ? Integer.toString(fpoBean.get(i).getNewfpono()):"", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, (fpoBean.get(i).getFpofarmerasso() != null) ? Integer.toString(fpoBean.get(i).getFpofarmerasso()):"", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, (fpoBean.get(i).getOldfpono() != null) ? Integer.toString(fpoBean.get(i).getOldfpono()):"", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, (fpoBean.get(i).getOldfarmerasso() != null) ? Integer.toString(fpoBean.get(i).getOldfarmerasso()):"", Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						newfpono=newfpono+(fpoBean.get(i).getNewfpono()!= null ? fpoBean.get(i).getNewfpono() : 0);
						fpofarmerasso=fpofarmerasso+(fpoBean.get(i).getFpofarmerasso()!= null ? fpoBean.get(i).getFpofarmerasso() : 0);
						oldfpono=oldfpono+(fpoBean.get(i).getOldfpono()!= null ? fpoBean.get(i).getOldfpono() : 0);
						oldfarmerasso=oldfarmerasso+(fpoBean.get(i).getOldfarmerasso()!= null ? fpoBean.get(i).getOldfarmerasso() : 0);
						

					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newfpono), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fpofarmerasso), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldfpono), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldfarmerasso), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(fpoBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=OC2-Report.pdf");
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
		
	
	@RequestMapping(value = "/downloadDistrictFpoDataPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictFpoDataPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String state=request.getParameter("state");
		String stname = request.getParameter("stname");
		List<FPOReportBean> distBean = new ArrayList<FPOReportBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("DistrictFPODataReport");
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
			
				paragraph3 = new Paragraph("Report OC2- District-wise Details of Farmer Producer Organisations (FPOs) for State " + "'"+ stname+"' ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(6);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(2);

				int newfpono=0,fpofarmerasso=0,oldfpono=0,oldfarmerasso=0;

				
				distBean=fpoMasterService.getfpodatadistwise(Integer.parseInt(state));
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "New FPO Created", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Existing FPO", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Farmers Associated with FPO", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "No", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Farmers Associated with FPO", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				
				
				int k=1;
				if(distBean.size()!=0)
					for(int i=0;i<distBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, (distBean.get(i).getNewfpono() != null) ? Integer.toString(distBean.get(i).getNewfpono()):"", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, (distBean.get(i).getFpofarmerasso() != null) ? Integer.toString(distBean.get(i).getFpofarmerasso()):"", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, (distBean.get(i).getOldfpono() != null) ? Integer.toString(distBean.get(i).getOldfpono()):"", Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, (distBean.get(i).getOldfarmerasso() != null) ? Integer.toString(distBean.get(i).getOldfarmerasso()):"", Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						newfpono=newfpono+(distBean.get(i).getNewfpono()!= null ? distBean.get(i).getNewfpono() : 0);
						fpofarmerasso=fpofarmerasso+(distBean.get(i).getFpofarmerasso()!= null ? distBean.get(i).getFpofarmerasso() : 0);
						oldfpono=oldfpono+(distBean.get(i).getOldfpono()!= null ? distBean.get(i).getOldfpono() : 0);
						oldfarmerasso=oldfarmerasso+(distBean.get(i).getOldfarmerasso()!= null ? distBean.get(i).getOldfarmerasso() : 0);
						

					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(newfpono), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(fpofarmerasso), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldfpono), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(oldfarmerasso), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(distBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=OC2-DistrictReport.pdf");
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
	
	@RequestMapping(value = "/downloadProjectFpoDataPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectFpoDataPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String state=request.getParameter("state");
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		String fpoType = request.getParameter("fpoType");
		List<FpoDetail> beanList = new ArrayList<FpoDetail>();
		beanList = fpoMasterService.getfpodataprojwise(dcode, fpoType);
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("projectFPODataReport");
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
			
				paragraph3 = new Paragraph("Report OC2- Project-wise Details of Farmer Producer Organisations (FPOs) for State '"+stname+"'", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(9);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 8});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(1);
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Name of FPO.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Dept. /Org. /Scheme", Element.ALIGN_CENTER,1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Registration No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Date of Registration", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of members of FPO.", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Farmers Associated with FPO", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Turnover(in rs.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				

				int noOfMembers=0,noOfFarmerAssociated=0;
				
				BigDecimal  turnover= BigDecimal.valueOf(0);
				
				
				int k=1;
				String totNum="";
				BigDecimal count= BigDecimal.ZERO;
				if(beanList.size()!=0)
					for(int i=0;i<beanList.size();i++) 
					{
						
						if(!beanList.get(i).getFpoMain().getIwmpMProject().getProjName().equals(totNum)) {
							CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, beanList.get(i).getFpoMain().getIwmpMProject().getProjName().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
							k=k+1;
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						CommonFunctions.insertCell(table, beanList.get(i).getFpoName(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getMDepartmentScheme().getSchemeDescription().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getRegistrationNo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getRegistrationDate().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getNoOfMembers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getNoOfFarmerAssociated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getTurnover().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						totNum = beanList.get(i).getFpoMain().getIwmpMProject().getProjName();
						noOfMembers=noOfMembers+beanList.get(i).getNoOfMembers();
						turnover=turnover.add(beanList.get(i).getTurnover());
						noOfFarmerAssociated=noOfFarmerAssociated+beanList.get(i).getNoOfFarmerAssociated();
						
						count=count.add(BigDecimal.ONE);
		}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 6, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(noOfMembers), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(noOfFarmerAssociated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "Average Total="+String.valueOf(turnover.divide(count,2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(beanList.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 9, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=OC2-ProjectReport.pdf");
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
	
	@RequestMapping(value = "/downloadProjectDetailFpoDataPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectDetailFpoDataPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String state=request.getParameter("state");
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String fpoType = request.getParameter("fpoType");
		String stname = request.getParameter("stname");
		
		List<FpoDetail> beanList = new ArrayList<FpoDetail>();
		List<FPOReportBean> beanTotalList = new ArrayList<FPOReportBean>();
		beanList = fpoMasterService.getfpodataallprojwise(stcode, fpoType);
		beanTotalList = fpoMasterService.getfpodatastatewise(stcode);
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("projectDetailFPODataReport");
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
			
				paragraph3 = new Paragraph("Report OC2- Project-wise Details of Farmer Producer Organisations (FPOs) for State '"+stname+"'", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(10);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 8});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(1);
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project. ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Name of FPO.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Dept. /Org. /Scheme", Element.ALIGN_CENTER,1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Registration No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Date of Registration", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of members of FPO.", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Farmers Associated with FPO", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Turnover(in rs.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				

				int noOfMembers=0,noOfFarmerAssociated=0;
						
				BigDecimal  turnover= BigDecimal.valueOf(0);
				
				
				
				int k=1;
				BigDecimal count=BigDecimal.ZERO;
				String totNum="";
				String totN="";
				if(beanList.size()!=0)
					for(int i=0;i<beanList.size();i++) 
					{
						

						if(!beanList.get(i).getFpoMain().getIwmpMProject().getIwmpDistrict().getDistName().equals(totNum)) {
							CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, beanList.get(i).getFpoMain().getIwmpMProject().getIwmpDistrict().getDistName().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
							k=k+1;
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						
						if(!beanList.get(i).getFpoMain().getIwmpMProject().getProjName().equals(totN)) {
							CommonFunctions.insertCell(table, beanList.get(i).getFpoMain().getIwmpMProject().getProjName().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						CommonFunctions.insertCell(table, beanList.get(i).getFpoName(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getMDepartmentScheme().getSchemeDescription().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getRegistrationNo().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getRegistrationDate().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getNoOfMembers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getNoOfFarmerAssociated().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, beanList.get(i).getTurnover().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						totNum =beanList.get(i).getFpoMain().getIwmpMProject().getIwmpDistrict().getDistName();
						totN = beanList.get(i).getFpoMain().getIwmpMProject().getProjName();
					
						noOfMembers=noOfMembers+beanList.get(i).getNoOfMembers();
						turnover=turnover.add(beanList.get(i).getTurnover());
						noOfFarmerAssociated=noOfFarmerAssociated+beanList.get(i).getNoOfFarmerAssociated();
						count= count.add(BigDecimal.ONE);
						

		}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 7, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(noOfMembers), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(noOfFarmerAssociated), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "Average Total="+String.valueOf(turnover.divide(count,2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					
					if(beanList.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 10, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=OC2-ProjectDetailReport.pdf");
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
	
	
	@RequestMapping(value = "/getStateWiseFPOExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getStateWiseFPOExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		
		String state = request.getParameter("state");
		
		List<FPOReportBean> beanList = new ArrayList<FPOReportBean>();
		beanList = fpoMasterService.getfpodatastatewise(Integer.parseInt(state));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC2- State-wise Details of Farmer Producer Organisations (FPOs)");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC2- State-wise Details of Farmer Producer Organisations (FPOs)";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,5,2,3);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	        	
	        
	        mergedRegion = new CellRangeAddress(beanList.size()+8,beanList.size()+8,0,1); 
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
			cell.setCellValue("New FPO Created");
			cell.setCellStyle(style);
			rowhead.createCell(3).setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(4);
			cell.setCellValue("Existing FPO");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(5).setCellStyle(style);
			
	        
			Row rowhead1 = sheet.createRow(6);
			for(int i=0;i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			cell = rowhead1.createCell(2);
			cell.setCellValue("No.");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(3);
			cell.setCellValue("No. of Farmers Associated with FPO");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(4);
			cell.setCellValue("No.");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("No. of Farmers Associated with FPO");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<6;i++) {
				cell = rowhead2.createCell(i);
				cell.setCellValue(i+1);  
				cell.setCellStyle(style);
			}
			
	        int rowno  =8;
	        double newFpoNoTot = 0.0;
	        double newFrmrTot = 0.0;
	        double oldFpoNoTot = 0.0;
	        double oldFrmrTot = 0.0;
	        
	        for(FPOReportBean bean: beanList) {
	        	Row row = sheet.createRow(rowno);
	        	cell = row.createCell(0);
	        	cell.setCellValue(rowno-7);
	        	cell = row.createCell(1);
	        	cell.setCellValue(bean.getSt_name());
	        	cell = row.createCell(2);
	        	cell.setCellValue(bean.getNewfpono()==null?0:bean.getNewfpono());
	        	cell = row.createCell(3);
	        	cell.setCellValue(bean.getFpofarmerasso()==null?0:bean.getFpofarmerasso());
	        	cell = row.createCell(4);
	        	cell.setCellValue(bean.getOldfpono()==null?0:bean.getOldfpono());
	        	cell = row.createCell(5);
	        	cell.setCellValue(bean.getOldfarmerasso()==null?0:bean.getOldfarmerasso());
	        	
	        	newFpoNoTot = newFpoNoTot + (bean.getNewfpono()==null?0:bean.getNewfpono());
	        	newFrmrTot = newFrmrTot + (bean.getFpofarmerasso()==null?0:bean.getFpofarmerasso());
	        	oldFpoNoTot = oldFpoNoTot + (bean.getOldfpono()==null?0:bean.getOldfpono());
	        	oldFrmrTot = oldFrmrTot + (bean.getOldfarmerasso()==null?0:bean.getOldfarmerasso());
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
			
			Row rowtot = sheet.createRow(beanList.size()+8);
			cell = rowtot.createCell(0);
			cell.setCellValue("Grand Total");
			cell.setCellStyle(style1);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			rowtot.createCell(1).setCellStyle(style1);
			cell = rowtot.createCell(2);
			cell.setCellValue(newFpoNoTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(3);
			cell.setCellValue(newFrmrTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(4);
			cell.setCellValue(oldFpoNoTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(5);
			cell.setCellValue(oldFrmrTot);
			cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, beanList.size(), 5);
	        String fileName = "attachment; filename=Report OC2 - State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/fpodatareport";
		
	}
	
	
	@RequestMapping(value = "/getDistrictWiseFPOExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getDistrictWiseFPOExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		
		int state = Integer.parseInt(request.getParameter("state"));
		String stname = request.getParameter("stname");
		
		List<FPOReportBean> beanList = new ArrayList<FPOReportBean>();
		beanList = fpoMasterService.getfpodatadistwise(state);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC2- District-wise Details of Farmer Producer Organisations (FPOs)");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC2- District-wise Details of Farmer Producer Organisations (FPOs) for State '"+stname+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,5,2,3);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	        	
	        
	        mergedRegion = new CellRangeAddress(beanList.size()+8,beanList.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
	        Row rowhead = sheet.createRow(5); 
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(1);
			cell.setCellValue("District");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			cell = rowhead.createCell(2);
			cell.setCellValue("New FPO Created");
			cell.setCellStyle(style);
			rowhead.createCell(3).setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			cell = rowhead.createCell(4);
			cell.setCellValue("Existing FPO");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(5).setCellStyle(style);
			
	        
			Row rowhead1 = sheet.createRow(6);
			for(int i=0;i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			cell = rowhead1.createCell(2);
			cell.setCellValue("No.");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(3);
			cell.setCellValue("No. of Farmers Associated with FPO");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(4);
			cell.setCellValue("No.");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("No. of Farmers Associated with FPO");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<6;i++) {
				cell = rowhead2.createCell(i);
				cell.setCellValue(i+1);  
				cell.setCellStyle(style);
			}
			
	        int rowno  =8;
	        double newFpoNoTot = 0.0;
	        double newFrmrTot = 0.0;
	        double oldFpoNoTot = 0.0;
	        double oldFrmrTot = 0.0;
	        
	        for(FPOReportBean bean: beanList) {
	        	Row row = sheet.createRow(rowno);
	        	cell = row.createCell(0);
	        	cell.setCellValue(rowno-7);
	        	cell = row.createCell(1);
	        	cell.setCellValue(bean.getDistname());
	        	cell = row.createCell(2);
	        	cell.setCellValue(bean.getNewfpono()==null?0:bean.getNewfpono());
	        	cell = row.createCell(3);
	        	cell.setCellValue(bean.getFpofarmerasso()==null?0:bean.getFpofarmerasso());
	        	cell = row.createCell(4);
	        	cell.setCellValue(bean.getOldfpono()==null?0:bean.getOldfpono());
	        	cell = row.createCell(5);
	        	cell.setCellValue(bean.getOldfarmerasso()==null?0:bean.getOldfarmerasso());
	        	
	        	newFpoNoTot = newFpoNoTot + (bean.getNewfpono()==null?0:bean.getNewfpono());
	        	newFrmrTot = newFrmrTot + (bean.getFpofarmerasso()==null?0:bean.getFpofarmerasso());
	        	oldFpoNoTot = oldFpoNoTot + (bean.getOldfpono()==null?0:bean.getOldfpono());
	        	oldFrmrTot = oldFrmrTot + (bean.getOldfarmerasso()==null?0:bean.getOldfarmerasso());
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
			
			Row rowtot = sheet.createRow(beanList.size()+8);
			cell = rowtot.createCell(0);
			cell.setCellValue("Grand Total");
			cell.setCellStyle(style1);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			rowtot.createCell(1).setCellStyle(style1);
			cell = rowtot.createCell(2);
			cell.setCellValue(newFpoNoTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(3);
			cell.setCellValue(newFrmrTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(4);
			cell.setCellValue(oldFpoNoTot);
			cell.setCellStyle(style1);
			cell = rowtot.createCell(5);
			cell.setCellValue(oldFrmrTot);
			cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, beanList.size(), 5);
	        String fileName = "attachment; filename=Report OC2 - District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/fpodatareport";
		
	}

	@RequestMapping(value = "/downloadExcelnewFPOProj", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelnewFPOProj(HttpServletRequest request, HttpServletResponse response) 
	{
		String state=request.getParameter("state");
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String fpoType = request.getParameter("fpoType");
		String stname = request.getParameter("stname");
		
		List<FpoDetail> list = new ArrayList<FpoDetail>();
		
		list = fpoMasterService.getfpodataprojwise(dcode, fpoType);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC2- Project-wise Details of Farmer Producer Organisations");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC2- Project-wise Details of Farmer Producer Organisations (FPOs) for State '"+stname+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
			
        
	        mergedRegion = new CellRangeAddress(list.size()+6,list.size()+6,0,5); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project.");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Name of FPO.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Dept. /Org. /Scheme");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Registration No.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Date of Registration");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("No. of members of FPO.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("No. of Farmers Associated with FPO");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Average Turnover(in rs.)");  
			cell.setCellStyle(style);
			
			
			
	        int sno = 1;
	        int rowno  = 6;
	        int totFPOMem = 0;
	        double totAvgTurnOver = 0.0;
	        int totFPOFarmer = 0;
	        String pName = "";
	        double count=0.0;
	        SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        
	        for(FpoDetail bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	if(!pName.equals(bean.getFpoMain().getIwmpMProject().getProjName())) {
	        	row.createCell(0).setCellValue(sno);	
	        	row.createCell(1).setCellValue(bean.getFpoMain().getIwmpMProject().getProjName());
	        	sno++;
	        	}
	        	row.createCell(2).setCellValue(bean.getFpoName());
	        	row.createCell(3).setCellValue(bean.getMDepartmentScheme().getSchemeDescription());
	        	row.createCell(4).setCellValue(bean.getRegistrationNo());
	        	row.createCell(5).setCellValue(DateFormat.format(bean.getRegistrationDate()));
	        	row.createCell(6).setCellValue(bean.getNoOfMembers());
	        	row.createCell(7).setCellValue(bean.getNoOfFarmerAssociated());
	        	row.createCell(8).setCellValue(bean.getTurnover()==null?0.0:bean.getTurnover().doubleValue());
	        	
	        	count=count+1;
	        	totFPOMem = totFPOMem + bean.getNoOfMembers();
	        	totAvgTurnOver = totAvgTurnOver + (bean.getTurnover()==null?0.0:bean.getTurnover().doubleValue());
	        	totFPOFarmer = totFPOFarmer + bean.getNoOfFarmerAssociated();
	        	
	        	pName = bean.getFpoMain().getIwmpMProject().getProjName();
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
	        
			
			
	        Row row = sheet.createRow(list.size()+6);
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
	        cell.setCellValue(totFPOMem);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totFPOFarmer);
	        cell.setCellStyle(style1);
	        double average = totAvgTurnOver / count;
	        DecimalFormat df = new DecimalFormat("#.##");
		     long formattedAverage = Math.round(average);
	        cell = row.createCell(8);
	        cell.setCellValue("Average Total=" + formattedAverage);
	        cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 8);
	        String fileName = "attachment; filename=Report OC2- Project.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/fpodatareport";
		
	}
	
	@RequestMapping(value = "/downloadExcelnewFPOAllProj", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelnewFPOAllProj(HttpServletRequest request, HttpServletResponse response) 
	{
		String state=request.getParameter("state");
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String fpoType = request.getParameter("fpoType");
		String stname = request.getParameter("stname");
		
		List<FpoDetail> list = new ArrayList<FpoDetail>();
		List<FPOReportBean> beanTotalList = new ArrayList<FPOReportBean>();
		
		list = fpoMasterService.getfpodataallprojwise(stcode, fpoType);
		beanTotalList = fpoMasterService.getfpodatastatewise(stcode);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC2- Project-wise Details of Farmer Producer Organisations");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC2- Project-wise Details of Farmer Producer Organisations (FPOs) for State '"+stname+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);
			
        
	        mergedRegion = new CellRangeAddress(list.size()+6,list.size()+6,0,6); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Project.");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(3);
			cell.setCellValue("Name of FPO.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Dept. /Org. /Scheme");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Registration No.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Date of Registration");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("No. of members of FPO.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("No. of Farmers Associated with FPO");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Average Turnover(in rs.)");  
			cell.setCellStyle(style);
			
			
	        int sno = 1;
	        int rowno  = 6;
	        int totFPOMem = 0;
	        double totAvgTurnOver = 0.0;
	        int totFPOFarmer = 0;
	        String pName = "";
	        String dName = "";
	        double count=0.0;
	        SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        
	        for(FpoDetail bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	if(!dName.equals(bean.getFpoMain().getIwmpMProject().getIwmpDistrict().getDistName())) {
	        		row.createCell(0).setCellValue(sno);
		        	row.createCell(1).setCellValue(bean.getFpoMain().getIwmpMProject().getIwmpDistrict().getDistName());
		        	sno++;
		        }
	        	if(!pName.equals(bean.getFpoMain().getIwmpMProject().getProjName())) {
	        	row.createCell(2).setCellValue(bean.getFpoMain().getIwmpMProject().getProjName());
	        	}
	        	row.createCell(3).setCellValue(bean.getFpoName());
	        	row.createCell(4).setCellValue(bean.getMDepartmentScheme().getSchemeDescription());
	        	row.createCell(5).setCellValue(bean.getRegistrationNo());
	        	row.createCell(6).setCellValue(DateFormat.format(bean.getRegistrationDate()));
	        	row.createCell(7).setCellValue(bean.getNoOfMembers());
	        	row.createCell(8).setCellValue(bean.getNoOfFarmerAssociated());
	        	row.createCell(9).setCellValue(bean.getTurnover()==null?0.0:bean.getTurnover().doubleValue());
	        	
	        	
	        	count= count+1;
	        	totFPOMem = totFPOMem + bean.getNoOfMembers();
	        	totAvgTurnOver = totAvgTurnOver + (bean.getTurnover()==null?0.0:bean.getTurnover().doubleValue());
	        	totFPOFarmer = totFPOFarmer + bean.getNoOfFarmerAssociated();
	        	
	        	dName = bean.getFpoMain().getIwmpMProject().getIwmpDistrict().getDistName();
	        	pName = bean.getFpoMain().getIwmpMProject().getProjName();
	        	
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
	        
			
			
	        Row row = sheet.createRow(list.size()+6);
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
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totFPOMem);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totFPOFarmer);
	        cell.setCellStyle(style1);
	        double average = totAvgTurnOver / count;
	        DecimalFormat df = new DecimalFormat("#.##");
		     long formattedAverage = Math.round(average);
	        cell = row.createCell(9);
	        cell.setCellValue("Average Total=" + formattedAverage);
	        cell.setCellStyle(style1);

	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);
	        String fileName = "attachment; filename=Report OC2- Project Detail.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/fpodatareport";
		
	}
	
}
