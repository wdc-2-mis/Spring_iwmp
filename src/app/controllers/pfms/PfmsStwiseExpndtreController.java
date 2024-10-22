package app.controllers.pfms;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.util.StringUtil;
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

import app.PfmsStateDistProjExpndtrBean;
import app.PfmsTreasureBean;
import app.bean.pfms.PfmsStwiseExpndtreBean;
import app.bean.pfms.PfmsTranxMappedWithProjBean;
import app.common.CommonFunctions;
import app.model.PfmsTreasuryreceiptDetaildata;
import app.service.DistrictMasterService;
import app.service.PfmsStateDistProjExpndtrService;
import app.service.PfmsStwiseExpndtreService;
import app.service.StateMasterService;

@Controller
public class PfmsStwiseExpndtreController {
	
	HttpSession session;
	
	@Autowired(required = true)
	private PfmsStwiseExpndtreService pfmsStwiseExpSrvc;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	PfmsStateDistProjExpndtrService pfmsStDistProjExpndtrsrvc;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@RequestMapping(value = "/statewiseExpenditure", method = RequestMethod.GET)
	public ModelAndView stwiseExpndreRpt(HttpServletRequest request, Model model) {
		session = request.getSession(true);
		List<PfmsStwiseExpndtreBean> list = new ArrayList<>();
		list = pfmsStwiseExpSrvc.getStwiseExpndtr();
		model.addAttribute("pfmsExpndtreList", list);
		ModelAndView mav = new ModelAndView("reports/pfmsExpanditureRpt");
		
		return mav;
		
	}
	
	@RequestMapping(value = "/distwiseExpenditure", method = RequestMethod.GET)
	public String distwiseExpenditure(HttpServletRequest request, Model model) {
		session = request.getSession(true);
		int stCode = Integer.parseInt(request.getParameter("stcode"));
		//String stname=request.getParameter("stname");
		ModelAndView mav = new ModelAndView("reports/pfmsExpanditureRpt");
		List<PfmsStwiseExpndtreBean> list = new ArrayList<>();
		list = pfmsStwiseExpSrvc.getDistwiseExpndtr(stCode);
		String stname = "";
		LinkedHashMap<Integer, String> stateList = new LinkedHashMap<>();
		stateList = stateMasterService.getAllState();
		for(Map.Entry<Integer, String> map : stateList.entrySet()) {
			if(map.getKey()==stCode) {
				stname = map.getValue();
			}
		}
		model.addAttribute("distwiseExpList", list);
		model.addAttribute("stcode", stCode);
		model.addAttribute("stname", stname);
		
		return "reports/pfmsExpanditureRpt";
		
	}
	
	@RequestMapping(value = "/getTranxMappedwithProj", method = RequestMethod.GET)
	public String getTranxMappedwithProj(HttpServletRequest request, Model model) {
		session = request.getSession(true);
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String dname = request.getParameter("distname");
		String stname=request.getParameter("state");
		ModelAndView mav = new ModelAndView("reports/pfmsExpanditureRpt");
		List<PfmsTranxMappedWithProjBean> list = null;
		list = pfmsStwiseExpSrvc.getTrnxMappedWithProjData(dcode);
		model.addAttribute("projWiseList" , list);
		model.addAttribute("dcode" , dcode);
		model.addAttribute("dname" , dname);
		model.addAttribute("stname", stname);
		return "reports/pfmsExpanditureRpt";
	
	}
	
	@RequestMapping(value="/pfmsStwiseExpndr", method = RequestMethod.GET)
	public ModelAndView getStatewiseExpndr(HttpServletRequest request, Model model){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<PfmsStateDistProjExpndtrBean> list = new ArrayList<>();
		list =pfmsStDistProjExpndtrsrvc.getStatewiseExpndtr();
		mav = new ModelAndView("reports/stateDistProjExpndtrReport");
		model.addAttribute("stwiselist", list);
		return mav;
		
	}
	
	@RequestMapping(value = "/pfmsDistwiseExpndtr", method = RequestMethod.GET)
	public String pfmsDistwiseExpndtr(HttpServletRequest request, Model model) {
		session = request.getSession(true);
		
		List<PfmsStateDistProjExpndtrBean> list = new ArrayList<>();
		int stCode = Integer.parseInt(request.getParameter("stcode"));                 
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(stateMasterService.getAllState());
		String state ="";
		for(Map.Entry<Integer, String> st:map.entrySet()) {
			if(st.getKey()==stCode) {
				state = st.getValue();
			}
		}
		list =pfmsStDistProjExpndtrsrvc.getDistwiseExpndtr(stCode);
		
		model.addAttribute("distwiseExpList", list);
		model.addAttribute("stname", state);
		model.addAttribute("stcode", stCode);
		
		return "reports/stateDistProjExpndtrReport";
		
	}
	
	@RequestMapping(value = "/pfmsProjwiseExpndtr", method = RequestMethod.GET)
	public String pfmsProjwiseExpndtr(HttpServletRequest request, Model model) {
		session = request.getSession(true);
		List<PfmsStateDistProjExpndtrBean> list = new ArrayList<>();
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(districtMasterService.getDistrictByDistCode(dcode));
		String dist ="";
		for(Map.Entry<Integer, String> dt:map.entrySet()) {
			if(dt.getKey()==dcode) {
				dist = dt.getValue();
			}
		}
		list = pfmsStDistProjExpndtrsrvc.getProjwiseExpndtr(dcode);
		model.addAttribute("projwiseExpList", list);
		model.addAttribute("distname", dist);
		model.addAttribute("dcode", dcode);
		
		return "reports/stateDistProjExpndtrReport";
		
	}
	
	@RequestMapping(value = "/downloadStateExpndtrPFMSPDF", method = RequestMethod.POST)
	public ModelAndView downloadStateExpndtrPFMSPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113

		List<PfmsStwiseExpndtreBean> expndtrBean = new ArrayList<PfmsStwiseExpndtreBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("StateExpenditureReport");
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
			
				paragraph3 = new Paragraph("Report PF3 - State-wise Project Expenditure", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(9);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5,5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				BigDecimal dexpndtr= BigDecimal.valueOf(0);
				BigDecimal bexpndtr= BigDecimal.valueOf(0);
				BigDecimal gexpndtr= BigDecimal.valueOf(0);
				BigDecimal vexpndtr= BigDecimal.valueOf(0);
				BigInteger totaltranx= BigInteger.valueOf(0);
				BigInteger mappedtranx= BigInteger.valueOf(0);
				BigInteger mappedworkid= BigInteger.valueOf(0);
				
				expndtrBean=pfmsStwiseExpSrvc.getStwiseExpndtr();
				
				CommonFunctions.insertCellHeader(table,  "All amounts are Rs.", Element.ALIGN_RIGHT, 9, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Expenditure ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Block Expenditure ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Gram Expenditure", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village Expenditure", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Transactions", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transactions Mapped with Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transactions Mapped with Work-Id", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				if(expndtrBean.size()!=0)
					for(int i=0;i<expndtrBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, expndtrBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, expndtrBean.get(i).getDexpndtr() == null ? "" : String.format(Locale.US, "%.2f", expndtrBean.get(i).getDexpndtr()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, expndtrBean.get(i).getBexpndtr() == null ? "" : String.format(Locale.US, "%.2f", expndtrBean.get(i).getBexpndtr()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, expndtrBean.get(i).getGexpndtr() == null ? "" : String.format(Locale.US, "%.2f", expndtrBean.get(i).getGexpndtr()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, expndtrBean.get(i).getVexpndtr() == null ? "" : String.format(Locale.US, "%.2f", expndtrBean.get(i).getVexpndtr()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, expndtrBean.get(i).getTotaltranx() == null ?"" : String.valueOf (expndtrBean.get(i).getTotaltranx()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, expndtrBean.get(i).getMappedtranx() == null ? "" : String.valueOf(expndtrBean.get(i).getMappedtranx()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, expndtrBean.get(i).getMappedworkid() == null ? "" : String.valueOf(expndtrBean.get(i).getMappedworkid()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						dexpndtr=dexpndtr.add(expndtrBean.get(i).getDexpndtr()==null?BigDecimal.ZERO:expndtrBean.get(i).getDexpndtr());
						bexpndtr=bexpndtr.add(expndtrBean.get(i).getBexpndtr()==null?BigDecimal.ZERO:expndtrBean.get(i).getBexpndtr());
						gexpndtr=gexpndtr.add(expndtrBean.get(i).getGexpndtr()==null?BigDecimal.ZERO:expndtrBean.get(i).getGexpndtr());
						vexpndtr=vexpndtr.add(expndtrBean.get(i).getVexpndtr()==null?BigDecimal.ZERO:expndtrBean.get(i).getVexpndtr());
						totaltranx=totaltranx.add(expndtrBean.get(i).getTotaltranx()==null?BigInteger.ZERO:expndtrBean.get(i).getTotaltranx());
						mappedtranx=mappedtranx.add(expndtrBean.get(i).getMappedtranx()==null?BigInteger.ZERO:expndtrBean.get(i).getMappedtranx());
						mappedworkid=mappedworkid.add(expndtrBean.get(i).getMappedworkid()==null?BigInteger.ZERO:expndtrBean.get(i).getMappedworkid());
					}
				
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", dexpndtr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", bexpndtr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", gexpndtr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", vexpndtr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totaltranx), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf( mappedtranx), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf( mappedworkid), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(expndtrBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=PF3-Report(State).pdf");
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

	@RequestMapping(value = "/downloadDistrictExpndtrPFMSPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictExpndtrPFMSPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String stname=request.getParameter("stname");
		List<PfmsStwiseExpndtreBean> distexpndtrBean = new ArrayList<PfmsStwiseExpndtreBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("DistrictExpenditureReport");
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
			
			paragraph3 = new Paragraph("Report PF3 - District-wise Project Expenditure for State " + "'"+stname +"' ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
			
				table = new PdfPTable(9);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				BigDecimal dexpndtr= BigDecimal.valueOf(0);
				BigDecimal bexpndtr= BigDecimal.valueOf(0);
				BigDecimal gexpndtr= BigDecimal.valueOf(0);
				BigDecimal vexpndtr= BigDecimal.valueOf(0);
				BigInteger totaltranx= BigInteger.valueOf(0);
				BigInteger mappedtranx= BigInteger.valueOf(0);
				BigInteger mappedworkid= BigInteger.valueOf(0);
				
				distexpndtrBean=pfmsStwiseExpSrvc.getDistwiseExpndtr(stcode);
				
				CommonFunctions.insertCellHeader(table,  "State Name: " +stname, Element.ALIGN_LEFT, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table,  "All amounts are Rs.", Element.ALIGN_RIGHT, 9, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Expenditure ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Block Expenditure ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Gram Expenditure", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village Expenditure", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Transactions", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transactions Mapped with Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transactions Mapped with Work-Id", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				if(distexpndtrBean.size()!=0)
					for(int i=0;i<distexpndtrBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distexpndtrBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distexpndtrBean.get(i).getDexpndtr() == null ? "" : String.format(Locale.US, "%.2f", distexpndtrBean.get(i).getDexpndtr()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distexpndtrBean.get(i).getBexpndtr() == null ? "" : String.format(Locale.US, "%.2f", distexpndtrBean.get(i).getBexpndtr()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distexpndtrBean.get(i).getGexpndtr() == null ? "" : String.format(Locale.US, "%.2f", distexpndtrBean.get(i).getGexpndtr()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distexpndtrBean.get(i).getVexpndtr() == null ? "" : String.format(Locale.US, "%.2f", distexpndtrBean.get(i).getVexpndtr()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, distexpndtrBean.get(i).getTotaltranx() == null ?"" : String.valueOf (distexpndtrBean.get(i).getTotaltranx()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distexpndtrBean.get(i).getMappedtranx() == null ? "" : String.valueOf(distexpndtrBean.get(i).getMappedtranx()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distexpndtrBean.get(i).getMappedworkid() == null ? "" : String.valueOf(distexpndtrBean.get(i).getMappedworkid()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						dexpndtr=dexpndtr.add(distexpndtrBean.get(i).getDexpndtr()==null?BigDecimal.ZERO:distexpndtrBean.get(i).getDexpndtr());
						bexpndtr=bexpndtr.add(distexpndtrBean.get(i).getBexpndtr()==null?BigDecimal.ZERO:distexpndtrBean.get(i).getBexpndtr());
						gexpndtr=gexpndtr.add(distexpndtrBean.get(i).getGexpndtr()==null?BigDecimal.ZERO:distexpndtrBean.get(i).getGexpndtr());
						vexpndtr=vexpndtr.add(distexpndtrBean.get(i).getVexpndtr()==null?BigDecimal.ZERO:distexpndtrBean.get(i).getVexpndtr());
						totaltranx=totaltranx.add(distexpndtrBean.get(i).getTotaltranx()==null?BigInteger.ZERO:distexpndtrBean.get(i).getTotaltranx());
						mappedtranx=mappedtranx.add(distexpndtrBean.get(i).getMappedtranx()==null?BigInteger.ZERO:distexpndtrBean.get(i).getMappedtranx());
						mappedworkid=mappedworkid.add(distexpndtrBean.get(i).getMappedworkid()==null?BigInteger.ZERO:distexpndtrBean.get(i).getMappedworkid());
					}
				
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", dexpndtr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", bexpndtr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", gexpndtr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", vexpndtr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totaltranx), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf( mappedtranx), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf( mappedworkid), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(distexpndtrBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=PF3-Report(District).pdf");
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

	@RequestMapping(value = "/downloadProjectExpndtrPFMSPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectExpndtrPFMSPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		List<PfmsTranxMappedWithProjBean> projexpndtrBean = new ArrayList<PfmsTranxMappedWithProjBean>();
		String stname = request.getParameter("stname");
		String dname = request.getParameter("dname");
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ProjectExpenditureReport");
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
			
				paragraph3 = new Paragraph("Report PF3 - Transaction Details Mapped with Project", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(13);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				BigDecimal totalamount= BigDecimal.valueOf(0);
				int villcode=0, projid=0;
				projexpndtrBean=pfmsStwiseExpSrvc.getTrnxMappedWithProjData(dcode);
				CommonFunctions.insertCellHeader(table,  "State Name. " +stname, Element.ALIGN_LEFT, 3, 1, bf8Bold); 
				CommonFunctions.insertCellHeader(table,  "District Name. " +dname, Element.ALIGN_LEFT, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table,  "All amounts are Rs.", Element.ALIGN_RIGHT, 13, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transaction Id", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transaction Level ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Agency Code ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Agency Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transaction Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Invoice No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Block Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Gram Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village Census Code", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Transaction Amount", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Id", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int k=1;
				if(projexpndtrBean.size()!=0)
					for(int i=0;i<projexpndtrBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projexpndtrBean.get(i).getTranxid(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projexpndtrBean.get(i).getTranxlevel(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projexpndtrBean.get(i).getAgencycode(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projexpndtrBean.get(i).getAgencyname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projexpndtrBean.get(i).getTranxdate(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projexpndtrBean.get(i).getInvoiceno(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projexpndtrBean.get(i).getBlockname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projexpndtrBean.get(i).getGramname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(projexpndtrBean.get(i).getVillcode()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table,projexpndtrBean.get(i).getTotalamount() == null ? "" : projexpndtrBean.get(i).getTotalamount().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table,projexpndtrBean.get(i).getProjid() == null ? "" : projexpndtrBean.get(i).getProjid().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projexpndtrBean.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						k=k+1;
						totalamount=totalamount.add(projexpndtrBean.get(i).getTotalamount()==null?BigDecimal.ZERO:projexpndtrBean.get(i).getTotalamount());
						villcode=villcode+projexpndtrBean.get(i).getVillcode();
						projid=projid+projexpndtrBean.get(i).getProjid();
						
					}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 10, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", totalamount), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.format(""), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.format(""), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(projexpndtrBean.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 13, 1, bf8);
				
					
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
		response.setHeader("Content-Disposition", "attachment;filename=PF3-Report(Project).pdf");
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

	@RequestMapping(value = "/downloadStateExpenditurePFMSPDF", method = RequestMethod.POST)
	public ModelAndView downloadStateExpenditurePFMSPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
	
		List<PfmsStateDistProjExpndtrBean> stBean = new ArrayList<PfmsStateDistProjExpndtrBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("StateUtExpenditureReport");
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
			
				paragraph3 = new Paragraph("Report PF5 - State/UT wise Expenditure", f3);
			
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
				
				BigDecimal project_cost= BigDecimal.valueOf(0);
				BigDecimal centralshare= BigDecimal.valueOf(0);
				BigDecimal stateshare= BigDecimal.valueOf(0);
				BigDecimal stateexpen= BigDecimal.valueOf(0);
				
				stBean =pfmsStDistProjExpndtrsrvc.getStatewiseExpndtr();

				CommonFunctions.insertCell(table, "Note: This Report is based on the mapping of PFMS Transactions with their respective Project Names.", Element.ALIGN_LEFT, 6, 1, bf8);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Sanctioned Amount(In Lakhs) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Central Share(In Lakhs) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Share(In Lakhs)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Expenditure(In Lakhs)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(stBean.size()!=0)
					for(int i=0;i<stBean.size();i++) 
					{
						for(PfmsStateDistProjExpndtrBean bean: stBean) {
					
			        	if(!StringUtil.startsWithIgnoreCase(bean.getStname(), "ut ")) {
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, bean.getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, bean.getProject_cost() == null ? "" : String.format(Locale.US, "%.2f", bean.getProject_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, bean.getCentralshare() == null ? "" : String.format(Locale.US, "%.2f", bean.getCentralshare()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, bean.getStateshare() == null ? "" : String.format(Locale.US, "%.2f", bean.getStateshare()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, bean.getStateexpen() == null ? "" : String.format(Locale.US, "%.2f", bean.getStateexpen()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						k=k+1;
						project_cost=project_cost.add(bean.getProject_cost()==null?BigDecimal.ZERO:bean.getProject_cost());
						centralshare=centralshare.add(bean.getCentralshare()==null?BigDecimal.ZERO:bean.getCentralshare());
						stateshare=stateshare.add(bean.getStateshare()==null?BigDecimal.ZERO:bean.getStateshare());
						stateexpen=stateexpen.add(bean.getStateexpen()==null?BigDecimal.ZERO:bean.getStateexpen());
					}
						}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", project_cost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", centralshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", stateshare), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", stateexpen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					  
					int k2=1;
					
					BigDecimal project_costt= BigDecimal.valueOf(0);
					BigDecimal centralshares= BigDecimal.valueOf(0);
					BigDecimal stateshares= BigDecimal.valueOf(0);
					BigDecimal stateexpens= BigDecimal.valueOf(0);
					CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "UT Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Total Sanctioned Amount(In Lakhs) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Central Share(In Lakhs) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "State Share(In Lakhs)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Total Expenditure(In Lakhs)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
					CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					
						for(PfmsStateDistProjExpndtrBean beans: stBean) {
						
			        	if(StringUtil.startsWithIgnoreCase(beans.getStname(), "ut ")) {
			        		
			        		CommonFunctions.insertCell(table, String.valueOf(k2), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, beans.getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, beans.getProject_cost() == null ? "" : String.format(Locale.US, "%.2f", beans.getProject_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, beans.getCentralshare() == null ? "" : String.format(Locale.US, "%.2f", beans.getCentralshare()), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, beans.getStateshare() == null ? "" : String.format(Locale.US, "%.2f", beans.getStateshare()), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, beans.getStateexpen() == null ? "" : String.format(Locale.US, "%.2f", beans.getStateexpen()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        		
							k2=k2+1;
			        		
							project_costt=project_costt.add(beans.getProject_cost()==null?BigDecimal.ZERO:beans.getProject_cost());
							centralshares=centralshares.add(beans.getCentralshare()==null?BigDecimal.ZERO:beans.getCentralshare());
							stateshares=stateshares.add(beans.getStateshare()==null?BigDecimal.ZERO:beans.getStateshare());
							stateexpens=stateexpens.add(beans.getStateexpen()==null?BigDecimal.ZERO:beans.getStateexpen());
			        	}
						}	
						CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", project_costt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", centralshares), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", stateshares), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", stateexpens), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						
					if(stBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=PF5-Report(State).pdf");
		response.setHeader("Pragma", "public");
		response.setContentLength(baos.size());
		OutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
						}
	} 
	catch (Exception ex) 
	{
		ex.printStackTrace();
	} 
	
	return null;
}

	@RequestMapping(value = "/downloadDistrictExpenditurePFMSPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictExpenditurePFMSPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		int stCode = Integer.parseInt(request.getParameter("stcode"));
		String stname=request.getParameter("stname");
		List<PfmsStateDistProjExpndtrBean> distBean = new ArrayList<PfmsStateDistProjExpndtrBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("DistrictExpenditureReport");
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
			
				paragraph3 = new Paragraph("Report PF5 - District-wise Project Expenditure for State '"+ stname+"' ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(4);
				table.setWidths(new int[] { 2, 8, 5, 5});
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				
				BigDecimal project_cost= BigDecimal.valueOf(0);
				BigDecimal distexpen= BigDecimal.valueOf(0);
				distBean =pfmsStDistProjExpndtrsrvc.getDistwiseExpndtr(stCode);
				
				CommonFunctions.insertCell(table, "Note: This Report is based on the mapping of PFMS Transactions with their respective Project Names.", Element.ALIGN_LEFT, 4, 1, bf8);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Sanctioned Amount(In Lakhs) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Expenditure(In Lakhs)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(distBean.size()!=0)
					for(int i=0;i<distBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distBean.get(i).getProject_cost() == null ? "" : String.format(Locale.US, "%.2f", distBean.get(i).getProject_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, distBean.get(i).getDistexpen() == null ? "" : String.format(Locale.US, "%.2f", distBean.get(i).getDistexpen()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						k=k+1;
						project_cost=project_cost.add(distBean.get(i).getProject_cost()==null?BigDecimal.ZERO:distBean.get(i).getProject_cost());
						distexpen=distexpen.add(distBean.get(i).getDistexpen()==null?BigDecimal.ZERO:distBean.get(i).getDistexpen());
					}
				
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", project_cost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", distexpen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(distBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 4, 1, bf8);
					
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
		response.setHeader("Content-Disposition", "attachment;filename=PF5-Report(District).pdf");
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
	
	
	@RequestMapping(value = "/downloadProjectExpenditurePFMSPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectExpenditurePFMSPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		
//		int stCode = Integer.parseInt(request.getParameter("stcode"));
		int dcode = Integer.parseInt(request.getParameter("dcode"));
//		String stname=request.getParameter("stname");
		String distname=request.getParameter("distname");
		List<PfmsStateDistProjExpndtrBean> projectBean = new ArrayList<PfmsStateDistProjExpndtrBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ProjectExpenditureReport");
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
			
				paragraph3 = new Paragraph("Report PF5 - Project-wise Expenditure for "+ distname, f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(4);
				table.setWidths(new int[] { 2, 8, 5, 5});
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				
				BigDecimal project_cost= BigDecimal.valueOf(0);
				BigDecimal projexpen= BigDecimal.valueOf(0);
				projectBean =pfmsStDistProjExpndtrsrvc.getProjwiseExpndtr(dcode);
				
				CommonFunctions.insertCell(table, "Note: This Report is based on the mapping of PFMS Transactions with their respective Project Names.", Element.ALIGN_LEFT, 4, 1, bf8);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Sanctioned Amount(In Lakhs) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Expenditure(In Lakhs)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(projectBean.size()!=0)
					for(int i=0;i<projectBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projectBean.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projectBean.get(i).getProject_cost() == null ? "" : String.format(Locale.US, "%.2f", projectBean.get(i).getProject_cost()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projectBean.get(i).getProjexpen() == null ? "" : String.format(Locale.US, "%.2f", projectBean.get(i).getProjexpen()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						k=k+1;
						project_cost=project_cost.add(projectBean.get(i).getProject_cost()==null?BigDecimal.ZERO:projectBean.get(i).getProject_cost());
						projexpen=projexpen.add(projectBean.get(i).getProjexpen()==null?BigDecimal.ZERO:projectBean.get(i).getProjexpen());
					}
				
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", project_cost), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f", projexpen), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(projectBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 4, 1, bf8);
					
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
		response.setHeader("Content-Disposition", "attachment;filename=PF5-Report(Project).pdf");
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
	
	@RequestMapping(value = "/downloadExcelStateWiseExpenditure", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStateWiseExpenditure(HttpServletRequest request, HttpServletResponse response) 
	{
		//String state=request.getParameter("state");
		int statecode =0;
		List<PfmsStwiseExpndtreBean> list = new ArrayList<PfmsStwiseExpndtreBean>();
		
		list = pfmsStwiseExpSrvc.getStwiseExpndtr();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF3 - State-wise Project Expenditure");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF3 - State-wise Project Expenditure";
			String areaAmtValDetail = "All amounts are Rs.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
			
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("District Expenditure");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Block Expenditure");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Gram Expenditure");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Village Expenditure");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Total Transactions");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Transactions Mapped with Project");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Transactions Mapped with Work-Id	");  
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
	        double totDistEx = 0.0;
	        double totBlkEx = 0.0;
	        double totGramEx = 0.0;
	        double totVilEx = 0.0;
	        double totTotalTrans = 0.0;
	        double totTransMapProj = 0.0;
	        double totTransMapworkid = 0.0;
	        
	        for(PfmsStwiseExpndtreBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getDexpndtr()==null?0.0:bean.getDexpndtr().doubleValue());
	        	row.createCell(3).setCellValue(bean.getBexpndtr()==null?0.0:bean.getBexpndtr().doubleValue());
	        	row.createCell(4).setCellValue(bean.getGexpndtr()==null?0.0:bean.getGexpndtr().doubleValue());
	        	row.createCell(5).setCellValue(bean.getVexpndtr()==null?0.0:bean.getVexpndtr().doubleValue());
	        	row.createCell(6).setCellValue(bean.getTotaltranx()==null?0.0:bean.getTotaltranx().doubleValue());
	        	row.createCell(7).setCellValue(bean.getMappedtranx()==null?0.0:bean.getMappedtranx().doubleValue());
	        	row.createCell(8).setCellValue(bean.getMappedworkid()==null?0.0:bean.getMappedworkid().doubleValue());
	        	
	        	
	        	totDistEx = totDistEx + (bean.getDexpndtr()==null?0.0:bean.getDexpndtr().doubleValue());
	        	totBlkEx = totBlkEx + (bean.getBexpndtr()==null?0.0:bean.getBexpndtr().doubleValue());
	        	totGramEx = totGramEx + (bean.getGexpndtr()==null?0.0:bean.getGexpndtr().doubleValue());
	        	totVilEx = totVilEx + (bean.getVexpndtr()==null?0.0:bean.getVexpndtr().doubleValue());
	        	totTotalTrans = totTotalTrans + (bean.getTotaltranx()==null?0.0:bean.getTotaltranx().doubleValue());
	        	totTransMapProj = totTransMapProj + (bean.getMappedtranx()==null?0.0:bean.getMappedtranx().doubleValue());
	        	totTransMapworkid = totTransMapworkid + (bean.getMappedworkid()==null?0.0:bean.getMappedworkid().doubleValue());
				
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
	        cell.setCellValue(totDistEx);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totBlkEx);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totGramEx);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totVilEx);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totTotalTrans);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totTransMapProj);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totTransMapworkid);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 8);
	        String fileName = "attachment; filename=Report PF3- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/pfmsExpanditureRpt";
		
	}
	
	@RequestMapping(value = "/downloadExcelDistWiseExpenditure", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistWiseExpenditure(HttpServletRequest request, HttpServletResponse response) 
	{
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String stname=request.getParameter("stname");
		List<PfmsStwiseExpndtreBean> list = new ArrayList<PfmsStwiseExpndtreBean>();
		list=pfmsStwiseExpSrvc.getDistwiseExpndtr(stcode);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF3 - District-wise Project Expenditure");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF3 - District-wise Project Expenditure for State '"+stname+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
			
			 
			mergedRegion = new CellRangeAddress(5,5,1,8); 
	        sheet.addMergedRegion(mergedRegion);
	       
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row rowhead0 = sheet.createRow(5);
	        Cell cell = rowhead0.createCell(0);
	        cell.setCellValue("State Name :   "+stname);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
	       
			cell = rowhead0.createCell(1);
			cell.setCellValue("All amounts are Rs.");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =2; i<9;i++) {
				rowhead0.createCell(i).setCellStyle(style);
			}
			
			Row rowhead = sheet.createRow(6); 
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("District Expenditure");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Block Expenditure");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Gram Expenditure");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Village Expenditure");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Total Transactions");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Transactions Mapped with Project");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Transactions Mapped with Work-Id");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(8);
			for(int i=0;i<9;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totDistEx = 0.0;
	        double totBlkEx = 0.0;
	        double totGramEx = 0.0;
	        double totVilEx = 0.0;
	        double totTotalTrans = 0.0;
	        double totTransMapProj = 0.0;
	        double totTransMapWorkid = 0.0;
	        
	        for(PfmsStwiseExpndtreBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getDexpndtr()==null?0.0:bean.getDexpndtr().doubleValue());
	        	row.createCell(3).setCellValue(bean.getBexpndtr()==null?0.0:bean.getBexpndtr().doubleValue());
	        	row.createCell(4).setCellValue(bean.getGexpndtr()==null?0.0:bean.getGexpndtr().doubleValue());
	        	row.createCell(5).setCellValue(bean.getVexpndtr()==null?0.0:bean.getVexpndtr().doubleValue());
	        	row.createCell(6).setCellValue(bean.getTotaltranx()==null?0.0:bean.getTotaltranx().doubleValue());
	        	row.createCell(7).setCellValue(bean.getMappedtranx()==null?0.0:bean.getMappedtranx().doubleValue());
	        	row.createCell(8).setCellValue(bean.getMappedworkid()==null?0.0:bean.getMappedworkid().doubleValue());
	        	
	        	
	        	totDistEx = totDistEx + (bean.getDexpndtr()==null?0.0:bean.getDexpndtr().doubleValue());
	        	totBlkEx = totBlkEx + (bean.getBexpndtr()==null?0.0:bean.getBexpndtr().doubleValue());
	        	totGramEx = totGramEx + (bean.getGexpndtr()==null?0.0:bean.getGexpndtr().doubleValue());
	        	totVilEx = totVilEx + (bean.getVexpndtr()==null?0.0:bean.getVexpndtr().doubleValue());
	        	totTotalTrans = totTotalTrans + (bean.getTotaltranx()==null?0.0:bean.getTotaltranx().doubleValue());
	        	totTransMapProj = totTransMapProj + (bean.getMappedtranx()==null?0.0:bean.getMappedtranx().doubleValue());
	        	totTransMapWorkid = totTransMapWorkid + (bean.getMappedworkid()==null?0.0:bean.getMappedworkid().doubleValue());
				
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
	        cell.setCellValue(totDistEx);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totBlkEx);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totGramEx);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totVilEx);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totTotalTrans);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totTransMapProj);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totTransMapWorkid);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 8);
	        String fileName = "attachment; filename=Report PF3- District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/pfmsExpanditureRpt";
		
	}
	
	@RequestMapping(value = "/downloadExcelTranxMappedwithProj", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelTranxMappedwithProj(HttpServletRequest request, HttpServletResponse response) 
	{
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		String stname = request.getParameter("stname");
		String dname = request.getParameter("dname");
		List<PfmsTranxMappedWithProjBean> list = null;
		
		list = pfmsStwiseExpSrvc.getTrnxMappedWithProjData(dcode);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF3 - Transaction Details Mapped with Project");   
			
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	       String rptName = "Report PF3 - Transaction Details Mapped with Project";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet,  mergedRegion, rptName, 12, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,3); 
	        sheet.addMergedRegion(mergedRegion);
	       
	        mergedRegion = new CellRangeAddress(5,5,4,12); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,9); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,11,12); 
	        sheet.addMergedRegion(mergedRegion);
	        
			Row rowhead0 = sheet.createRow(5);
	        Cell cell = rowhead0.createCell(0);
	        cell.setCellValue("State Name :   "+stname +"      "+ "District Name :" +dname);
	        
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<4;i++) {
				rowhead0.createCell(i).setCellStyle(style);
			}
			cell = rowhead0.createCell(4);
			cell.setCellValue("All amounts are Rs.");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =5; i<12;i++) {
				rowhead0.createCell(i).setCellStyle(style);
			}
        
			Row rowhead = sheet.createRow(6); 
			
			 cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Transaction Id");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Transaction Level");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Agency Code");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Agency Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Transaction Date");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Invoice No.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Block Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Gram Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Village Census Code");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Transaction Amount");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Project Id");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<13;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totalamnt=0.0;
	        for(PfmsTranxMappedWithProjBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getTranxid());
	        	row.createCell(2).setCellValue(bean.getTranxlevel());
	        	row.createCell(3).setCellValue(bean.getAgencycode());
	        	row.createCell(4).setCellValue(bean.getAgencyname());
	        	row.createCell(5).setCellValue(bean.getTranxdate());
	        	row.createCell(6).setCellValue(bean.getInvoiceno());
	        	row.createCell(7).setCellValue(bean.getBlockname());
	        	row.createCell(8).setCellValue(bean.getGramname());
	        	row.createCell(9).setCellValue(bean.getVillcode());
	        	row.createCell(10).setCellValue(bean.getTotalamount()==null?0.0:bean.getTotalamount().doubleValue());
	        	row.createCell(11).setCellValue(bean.getProjid());
	        	row.createCell(12).setCellValue(bean.getProjname());
	        	
	        	totalamnt = totalamnt + (bean.getTotalamount()==null?0.0:bean.getTotalamount().doubleValue());
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
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totalamnt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
	        String fileName = "attachment; filename=Report PF3- Project.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/pfmsExpanditureRpt";
		
	}
	
	@RequestMapping(value = "/downloadExcelpfmsStwiseExpndr", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelpfmsStwiseExpndr(HttpServletRequest request, HttpServletResponse response) 
	{
		List<PfmsStateDistProjExpndtrBean> list = new ArrayList<PfmsStateDistProjExpndtrBean>();
		
		list =pfmsStDistProjExpndtrsrvc.getStatewiseExpndtr();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF5 - State-UT wise Expenditure");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF5 - State/UT wise Expenditure";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,5);
	        sheet.addMergedRegion(mergedRegion);
			
			Row note = sheet.createRow(5); 
			
			Cell cell = note.createCell(0);
			cell.setCellValue("Note: This Report is based on the mapping of PFMS Transactions with their respective Project Names.");
        
	        //State
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Sanctioned Amount(In Lakhs)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Central Share(In Lakhs)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("State Share(In Lakhs)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Total Expenditure(In Lakhs)");  
			cell.setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<6;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totTotalSanAmt = 0.0;
	        double totCenShare = 0.0;
	        double totStateShare = 0.0;
	        double totTotalExp = 0.0;
	        
	        for(PfmsStateDistProjExpndtrBean bean: list) {
	        	if(!StringUtil.startsWithIgnoreCase(bean.getStname(), "ut ")) {
	        		Row row = sheet.createRow(rowno);
		        	row.createCell(0).setCellValue(sno); 
		        	row.createCell(1).setCellValue(bean.getStname());
		        	row.createCell(2).setCellValue(bean.getProject_cost()==null?0.0:bean.getProject_cost().doubleValue());
		        	row.createCell(3).setCellValue(bean.getCentralshare()==null?0.0:bean.getCentralshare().doubleValue());
		        	row.createCell(4).setCellValue(bean.getStateshare()==null?0.0:bean.getStateshare().doubleValue());
		        	row.createCell(5).setCellValue(bean.getStateexpen()==null?0.0:bean.getStateexpen().doubleValue());
		        	
		        	
		        	totTotalSanAmt = totTotalSanAmt + (bean.getProject_cost()==null?0.0:bean.getProject_cost().doubleValue());
		        	totCenShare = totCenShare + (bean.getCentralshare()==null?0.0:bean.getCentralshare().doubleValue());
		        	totStateShare = totStateShare + (bean.getStateshare()==null?0.0:bean.getStateshare().doubleValue());
		        	totTotalExp = totTotalExp + (bean.getStateexpen()==null?0.0:bean.getStateexpen().doubleValue());
		        	sno++;
		        	rowno++;
	        	}
	        	
	        	
	        }
	        mergedRegion = new CellRangeAddress(sno+7,sno+7,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
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
	        
			
			
	        Row row = sheet.createRow(sno+7);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(Math.round(totTotalSanAmt*100.0)/100.0);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(Math.round(totCenShare*100.0)/100.0);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(Math.round(totStateShare*100.0)/100.0);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(Math.round(totTotalExp*100.0)/100.0);
	        cell.setCellStyle(style1);
	       
	        
	        //UT
	        Row rowheadut = sheet.createRow(sno+10); 
			
			Cell cell1 = rowheadut.createCell(0);
			cell1.setCellValue("S.No.");
			cell1.setCellStyle(style);
			
			cell1 = rowheadut.createCell(1);
			cell1.setCellValue("UT Name");  
			cell1.setCellStyle(style);
				
			cell1 = rowheadut.createCell(2);
			cell1.setCellValue("Total Sanctioned Amount");  
			cell1.setCellStyle(style);
			
			cell1 = rowheadut.createCell(3);
			cell1.setCellValue("Central Share(In Lakhs)");  
			cell1.setCellStyle(style);
			
			cell1 = rowheadut.createCell(4);
			cell1.setCellValue("State Share(In Lakhs)");  
			cell1.setCellStyle(style);
			
			cell1 = rowheadut.createCell(5);
			cell1.setCellValue("Total Expenditure(In Lakhs)");  
			cell1.setCellStyle(style);
			
			
			Row rowheadut1 = sheet.createRow(sno+11);
			for(int i=0;i<6;i++)
			{
				cell =rowheadut1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int utsno = 1;
	        int utrowno  = sno+12;
	        double totTotalSanAmtut = 0.0;
	        double totCenShareut = 0.0;
	        double totStateShareut = 0.0;
	        double totTotalExput = 0.0;
	        
	        for(PfmsStateDistProjExpndtrBean bean: list) {
	        	if(StringUtil.startsWithIgnoreCase(bean.getStname(), "ut ")) {
	        		Row rowut = sheet.createRow(utrowno);
		        	rowut.createCell(0).setCellValue(utsno); 
		        	rowut.createCell(1).setCellValue(bean.getStname());
		        	rowut.createCell(2).setCellValue(bean.getProject_cost()==null?0.0:bean.getProject_cost().doubleValue());
		        	rowut.createCell(3).setCellValue(bean.getCentralshare()==null?0.0:bean.getCentralshare().doubleValue());
		        	rowut.createCell(4).setCellValue(bean.getStateshare()==null?0.0:bean.getStateshare().doubleValue());
		        	rowut.createCell(5).setCellValue(bean.getStateexpen()==null?0.0:bean.getStateexpen().doubleValue());
		        	
		        	
		        	totTotalSanAmtut = totTotalSanAmtut + (bean.getProject_cost()==null?0.0:bean.getProject_cost().doubleValue());
		        	totCenShareut = totCenShareut + (bean.getCentralshare()==null?0.0:bean.getCentralshare().doubleValue());
		        	totStateShareut = totStateShareut + (bean.getStateshare()==null?0.0:bean.getStateshare().doubleValue());
		        	totTotalExput = totTotalExput + (bean.getStateexpen()==null?0.0:bean.getStateexpen().doubleValue());
		        	utsno++;
		        	utrowno++;
	        	}
	        	
	        	
	        }
	        mergedRegion = new CellRangeAddress(utsno+sno+11,utsno+sno+11,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        CellStyle style2 = workbook.createCellStyle();
	        style2.setBorderTop(BorderStyle.THIN); 
			style2.setBorderBottom(BorderStyle.THIN);
			style2.setBorderLeft(BorderStyle.THIN);
			style2.setBorderRight(BorderStyle.THIN);
	        style2.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());  
	        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			org.apache.poi.ss.usermodel.Font font2 = workbook.createFont();
			font2.setFontHeightInPoints((short) 12);
			font2.setBold(true);
//			font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font2);
	        
			
			
	        Row rowut = sheet.createRow(sno+14);
	        cell = rowut.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = rowut.createCell(1);
	        cell.setCellStyle(style1);
	        cell = rowut.createCell(2);
	        cell.setCellValue(Math.round(totTotalSanAmtut*100.0)/100.0);
	        cell.setCellStyle(style1);
	        cell = rowut.createCell(3);
	        cell.setCellValue(Math.round(totCenShareut*100.0)/100.0);
	        cell.setCellStyle(style1);
	        cell = rowut.createCell(4);
	        cell.setCellValue(Math.round(totStateShareut*100.0)/100.0);
	        cell.setCellStyle(style1);
	        cell = rowut.createCell(5);
	        cell.setCellValue(Math.round(totTotalExput*100.0)/100.0);
	        cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size()+6, 5);
	        String fileName = "attachment; filename=Report PF5- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/stateDistProjExpndtrReport";
		
	}
	
	@RequestMapping(value = "/downloadExcelpfmsDistwiseExpndtr", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelpfmsDistwiseExpndtr(HttpServletRequest request, HttpServletResponse response) 
	{
		int stCode = Integer.parseInt(request.getParameter("stcode"));
		String stname=request.getParameter("stname");
		List<PfmsStateDistProjExpndtrBean> list = new ArrayList<PfmsStateDistProjExpndtrBean>();
		
		list =pfmsStDistProjExpndtrsrvc.getDistwiseExpndtr(stCode);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF5 - District-wise Project Expenditure for "+stname);   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF5 - District-wise Project Expenditure for State  '"+stname+"' ";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 3, areaAmtValDetail, workbook);
			
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(5,5,0,3);
	        sheet.addMergedRegion(mergedRegion);
			
			Row note = sheet.createRow(5); 
			
			Cell cell = note.createCell(0);
			cell.setCellValue("Note: This Report is based on the mapping of PFMS Transactions with their respective Project Names.");
	        
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Sanctioned Amount(In Lakhs)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total Expenditure(In Lakhs)");  
			cell.setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<4;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totSanAmt = 0.0;
	        double totExp = 0.0;
	       
	        
	        
	        for(PfmsStateDistProjExpndtrBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getProject_cost()==null?0.0:bean.getProject_cost().doubleValue());
	        	row.createCell(3).setCellValue(bean.getDistexpen()==null?0.0:bean.getDistexpen().doubleValue());
	        	
	        	
	        	totSanAmt = totSanAmt + (bean.getProject_cost()==null?0.0:bean.getProject_cost().doubleValue());
	        	totExp = totExp + (bean.getDistexpen()==null?0.0:bean.getDistexpen().doubleValue());
				
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
	        cell.setCellValue(totSanAmt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totExp);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 7);
	        String fileName = "attachment; filename=Report PF5- District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/stateDistProjExpndtrReport";
		
	}
	
	@RequestMapping(value = "/downloadExcelpfmsProjwiseExpndtr", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelpfmsProjwiseExpndtr(HttpServletRequest request, HttpServletResponse response) 
	{
		//int stCode = Integer.parseInt(request.getParameter("stcode"));
		int dcode = Integer.parseInt(request.getParameter("dcode"));
//		String stname=request.getParameter("stname");
		String distname=request.getParameter("distname");
		List<PfmsStateDistProjExpndtrBean> list = new ArrayList<PfmsStateDistProjExpndtrBean>();
		
		list =pfmsStDistProjExpndtrsrvc.getProjwiseExpndtr(dcode);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF5 - Project-wise Expenditure for "+distname);   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF5 - Project-wise Expenditure for "+distname;
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 3, areaAmtValDetail, workbook);
			
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(5,5,0,3);
	        sheet.addMergedRegion(mergedRegion);
			
			Row note = sheet.createRow(5); 
			
			Cell cell = note.createCell(0);
			cell.setCellValue("Note: This Report is based on the mapping of PFMS Transactions with their respective Project Names.");
	        
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Sanctioned Amount(In Lakhs)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total Expenditure(In Lakhs)");  
			cell.setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<4;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totSanAmt = 0.0;
	        double totExp = 0.0;
	       
	        
	        
	        for(PfmsStateDistProjExpndtrBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getProjname());
	        	row.createCell(2).setCellValue(bean.getProject_cost()==null?0.0:bean.getProject_cost().doubleValue());
	        	row.createCell(3).setCellValue(bean.getProjexpen()==null?0.0:bean.getProjexpen().doubleValue());
	        	
	        	
	        	totSanAmt = totSanAmt + (bean.getProject_cost()==null?0.0:bean.getProject_cost().doubleValue());
	        	totExp = totExp + (bean.getProjexpen()==null?0.0:bean.getProjexpen().doubleValue());
				
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
	        cell.setCellValue(totSanAmt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totExp);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 7);
	        String fileName = "attachment; filename=Report PF5- Project.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/stateDistProjExpndtrReport";
		
	}
	
}