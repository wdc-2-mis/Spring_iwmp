package app.controllers.outcome;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
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

import app.bean.RPCLivelihoodBean;
import app.bean.reports.SelfHelpGroupReportBean;
import app.common.CommonFunctions;
import app.service.StateMasterService;
import app.service.outcome.LivelihoodProductionEpaService;

@Controller("RPCLivelihoodReportController")
public class RPCLivelihoodReportController {

	@Autowired
	StateMasterService stateMasterService;
	@Autowired(required = true)
	private LivelihoodProductionEpaService lhoodproductepaserice;

	@RequestMapping(value = "/rpcreport", method = RequestMethod.GET)
	public ModelAndView rpcreport(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("output/rpclivelihoodstate");
		mav.addObject("stateList", stateMasterService.getAllState());
		Map<String, String> map = new HashMap<String, String>();
		map.put("livelihood", "Livelihood Activities for the Asst-less Person");
		map.put("production", "Production System");
		map.put("epa", "Entry point Activities(EPAs)");
		mav.addObject("headtype", map); 
		return mav;

	}

	@RequestMapping(value = "/getRptLiveProdEpa", method = RequestMethod.POST)  
	public String listRptdata(Locale locale, Model model, HttpServletRequest request,
			@RequestParam("headtype") String headtype, @RequestParam("state") Integer stcode) {
		List<RPCLivelihoodBean> beanList = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> beanListEpa = new ArrayList<RPCLivelihoodBean>();
		if (headtype.equals("livelihood")) {

			beanList = lhoodproductepaserice.getRptLivelihoodList(stcode);
		}
		if (headtype.equals("production")) {

			beanList = lhoodproductepaserice.getRptProductionList(stcode);
		}
		if (headtype.equals("epa")) {

			beanListEpa = lhoodproductepaserice.getRptEpaList(stcode);
		}
		model.addAttribute("rptList", beanList);
		model.addAttribute("rptListSize", beanList.size());
		model.addAttribute("beanListEpa", beanListEpa);
		model.addAttribute("beanListEpaSize", beanListEpa.size());
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtypec", headtype);
		model.addAttribute("stCode", stcode);
		Map<String, String> map = new HashMap<String, String>();
		map.put("livelihood", "Livelihood Activities for the Asst-less Person");
		map.put("production", "Production System");
		map.put("epa", "Entry point Activities(EPAs)");
		model.addAttribute("headtype", map); 
		return "output/rpclivelihoodstate";
	}
	
	@RequestMapping(value = "/getRptLiveProdEpaDist", method = RequestMethod.POST)   //  rpcreport
	public String getRptLiveProdEpaDist(Locale locale, Model model, HttpServletRequest request) 
	{
		String stcd=request.getParameter("stateid");
		String headtypeh= request.getParameter("headtypeh");
		String stname= request.getParameter("stname");
		List<RPCLivelihoodBean> distList = new ArrayList<RPCLivelihoodBean>();
		distList = lhoodproductepaserice.getRptLivelihoodDistList(stcd, headtypeh);
		model.addAttribute("distList", distList);
		model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtype", headtypeh);
		model.addAttribute("stname", stname);
		model.addAttribute("stcd", stcd);
		return "output/rpclivelihoodDistrict";  
	}
	
	
	@RequestMapping(value = "/getRptLiveProdEpaProjectAvgWise", method = RequestMethod.POST)  
	public String getRptLiveProdEpaProjectAvgWise(Locale locale, Model model, HttpServletRequest request) 
	{
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
		List<RPCLivelihoodBean> projavgwiseList = new ArrayList<RPCLivelihoodBean>();
		if(headtypeh.equals("epa")) {
			projavgwiseList=lhoodproductepaserice.getLiveEPAProdDetailProjectWise( Integer.parseInt(distid), headtypeh);
			model.addAttribute("projwiseList", projavgwiseList);
			model.addAttribute("projwiseListSize", projavgwiseList.size());
			model.addAttribute("headtype", headtypeh);
			model.addAttribute("distname", distname);
			model.addAttribute("stname", stname);
			model.addAttribute("distid", distid);
			model.addAttribute("distcode", distid);
			model.addAttribute("headtypeh", headtypeh);
			return "output/rpclivelihoodProject"; 
			}
		else {
		projavgwiseList=lhoodproductepaserice.getLiveEPAProdDetailAvgProjectWise( Integer.parseInt(distid), headtypeh);
		model.addAttribute("projavgwiseList", projavgwiseList);
		model.addAttribute("projavgwiseListSize", projavgwiseList.size());
		model.addAttribute("headtype", headtypeh);
		model.addAttribute("distname", distname);
		model.addAttribute("stname", stname);
		model.addAttribute("distcode", distid);
		model.addAttribute("distid", distid);
		model.addAttribute("headtypeh", headtypeh);
		return "output/rpclivelihoodAvgProject"; 
		}
	
	}
	
	
	@RequestMapping(value = "/getRptLiveProdEpaProjectWise", method = RequestMethod.POST)  
	public String getRptLiveProdEpaProjectWise(Locale locale, Model model, HttpServletRequest request) 
	{
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
		List<RPCLivelihoodBean> projwiseList = new ArrayList<RPCLivelihoodBean>();
		
		/*
		 * if (headtypeh.equals("livelihood")) { model.addAttribute("livelihoodData",
		 * lhoodproductepaserice.getLivelihoodDetailProjectWise(Integer.parseInt(distid)
		 * )); } if (headtypeh.equals("production")) {
		 * model.addAttribute("productionData",
		 * lhoodproductepaserice.getProductionDetailProjectWise(Integer.parseInt(distid)
		 * )); } if (headtypeh.equals("epa")) { model.addAttribute("epaData",
		 * lhoodproductepaserice.getEpaDetailProjectWise( Integer.parseInt(distid))); }
		 */
		projwiseList=lhoodproductepaserice.getLiveEPAProdDetailProjectWise( Integer.parseInt(distid), headtypeh);
		model.addAttribute("projwiseList", projwiseList);
	//	List<RPCLivelihoodBean> distList = new ArrayList<RPCLivelihoodBean>();
	//	distList = lhoodproductepaserice.getRptLivelihoodDistList(stcd, headtypeh);
	//	model.addAttribute("distList", distList);
	//	model.addAttribute("stateList", stateMasterService.getAllState());
		model.addAttribute("headtype", headtypeh);
		model.addAttribute("distname", distname);
		model.addAttribute("stname", stname);
		return "output/rpclivelihoodProject";  
	}

	
	@RequestMapping(value = "/getRptLiveProdEpaSingleProjectWise", method = RequestMethod.POST)  
	public String getRptLiveProdEpaSingleProjectWise(Locale locale, Model model, HttpServletRequest request) 
	{
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String projcode = request.getParameter("projcode");
		String stname= request.getParameter("stname");
		List<RPCLivelihoodBean> projwiseList = new ArrayList<RPCLivelihoodBean>();
		
		projwiseList=lhoodproductepaserice.getLiveEPAProdDetailSProjectWise( Integer.parseInt(distid), headtypeh, Integer.parseInt(projcode));
		model.addAttribute("projwiseList", projwiseList);
		model.addAttribute("projwiseListSize", projwiseList.size());
		model.addAttribute("headtype", headtypeh);
		model.addAttribute("distname", distname);
		model.addAttribute("stname", stname);
		model.addAttribute("distid", distid);
		model.addAttribute("projcode", projcode);
		return "output/rpclivelihoodProject";  
	}
	
	@RequestMapping(value = "/downloadPDFRptLivProdEpa", method = RequestMethod.POST)
	public ModelAndView downloadPDFRptLivProdEpa(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String state=request.getParameter("state");;
		String headtype=request.getParameter("headtype");
		
		ModelAndView mav = new ModelAndView("output/rpclivelihoodstate");
		List<RPCLivelihoodBean> listlivBean = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> listprodBean = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> listepaBean = new ArrayList<RPCLivelihoodBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("Liv/Prod/Epa");
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
			
			if (headtype.equals("livelihood")) {
				paragraph3 = new Paragraph("Report OC3- State wise details of Livelihood Activities for the Asst-less Person ", f3);
			}
			if (headtype.equals("production")) {
				paragraph3 = new Paragraph("Report OC3- State wise details of Production System ", f3);
			}
			if (headtype.equals("epa")) {
				paragraph3 = new Paragraph("Report OC3- State wise details of Entry point Activities(EPAs)  ", f3);
			}
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
//			addFooter(writer);
//			document.add(paragraph);
			document.add(paragraph2);
			document.add(paragraph3);
			if (headtype.equals("livelihood")) {
				table = new PdfPTable(8);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
				table.setHeaderRows(3);
			}
			if (headtype.equals("production")) {
				table = new PdfPTable(8);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(70);
				table.setHeaderRows(3);
			}
			if (headtype.equals("epa")) {
				table = new PdfPTable(3);
				table.setWidths(new int[] { 2, 8, 5});
				table.setWidthPercentage(70);
				table.setHeaderRows(2);
			}
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			
			int noactivity=0, sc=0, st=0, other=0,  total=0, women=0;
			
			if (headtype.equals("livelihood")) {
				
				listlivBean = lhoodproductepaserice.getRptLivelihoodList(Integer.parseInt(state));
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of benificiaries", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
				int k=1;
				if(listlivBean.size()!=0)
					for(int i=0;i<listlivBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listlivBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listlivBean.get(i).getNoactivity()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listlivBean.get(i).getSc()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listlivBean.get(i).getSt()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listlivBean.get(i).getOther()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listlivBean.get(i).getTotal()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listlivBean.get(i).getWomen()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						noactivity=noactivity+listlivBean.get(i).getNoactivity();
						sc=sc+listlivBean.get(i).getSc();
						st=st+listlivBean.get(i).getSt();
						other=other+listlivBean.get(i).getOther();
						total=total+listlivBean.get(i).getTotal();
						women=women+listlivBean.get(i).getWomen();
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(women), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(listlivBean.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
			
				}
			
				if (headtype.equals("production")) {
				
					listprodBean = lhoodproductepaserice.getRptProductionList(Integer.parseInt(state));
					
					CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Total no. of activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Total no. of benificiaries", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				
					CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1,bf8Bold);
					CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
				int k=1;
				if(listprodBean.size()!=0)
					for(int i=0;i<listprodBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprodBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listprodBean.get(i).getNoactivity()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listprodBean.get(i).getSc()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listprodBean.get(i).getSt()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listprodBean.get(i).getOther()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listprodBean.get(i).getTotal()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listprodBean.get(i).getWomen()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						noactivity=noactivity+listprodBean.get(i).getNoactivity();
						sc=sc+listprodBean.get(i).getSc();
						st=st+listprodBean.get(i).getSt();
						other=other+listprodBean.get(i).getOther();
						total=total+listprodBean.get(i).getTotal();
						women=women+listprodBean.get(i).getWomen();
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(women), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(listprodBean.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
			
		}
				if (headtype.equals("epa")) {
					
					listepaBean = lhoodproductepaserice.getRptEpaList(Integer.parseInt(state));
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
				int k=1;
				if(listepaBean.size()!=0)
					for(int i=0;i<listepaBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listepaBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listepaBean.get(i).getNoactivity()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						noactivity=noactivity+listepaBean.get(i).getNoactivity();
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(listepaBean.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 3, 1, bf8);
			
		}
			
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
//			CommonFunctions.insertCellPageHeader(table, "Source : WDC-PMKSY ", Element.ALIGN_RIGHT, 1, 4, bf8);
			document.add(table);
			///addHeader(writer);
			CommonFunctions.addFooter(writer);
			document.close();
			//getHeaderForDilrmpMMP(document);
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Liv/Prod/Epa.pdf");
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
	
	@RequestMapping(value = "/downloadDistLivelihoodPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistLivelihoodPDF(HttpServletRequest request, HttpServletResponse response) 
	{
//		//WDC-PMKSY-0001113
		String stcd=request.getParameter("stcd");
		String headtypeh= request.getParameter("headtypeh");
		String stname= request.getParameter("stname");
		List<RPCLivelihoodBean> livBean = new ArrayList<RPCLivelihoodBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("LivelihoodReport");
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
			
			paragraph3 = new Paragraph("Report OC3- District wise details of Livelihood Activities for the Asst-less Person ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
//			addFooter(writer);
//			document.add(paragraph);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(9);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
			
				int noactivity=0, sc=0, st=0, other=0,  total=0, women=0, liveproducwork=0;
				
				livBean = lhoodproductepaserice.getRptLivelihoodDistList(stcd, headtypeh);
				
				CommonFunctions.insertCellHeader(table, "State : "  + stname +  "  Head : 	Livelihood Activities for the Asst-less Person"  , Element.ALIGN_LEFT, 9, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of benificiaries", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of Work Created", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				
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
				if(livBean.size()!=0)
					for(int i=0;i<livBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, livBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livBean.get(i).getNoactivity()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livBean.get(i).getSc()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livBean.get(i).getSt()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livBean.get(i).getOther()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livBean.get(i).getTotal()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livBean.get(i).getWomen()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livBean.get(i).getLiveproducwork()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						noactivity=noactivity+livBean.get(i).getNoactivity();
						sc=sc+livBean.get(i).getSc();
						st=st+livBean.get(i).getSt();
						other=other+livBean.get(i).getOther();
						total=total+livBean.get(i).getTotal();
						women=women+livBean.get(i).getWomen();
						liveproducwork=liveproducwork+livBean.get(i).getLiveproducwork();
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(women), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(liveproducwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(livBean.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 9, 1, bf8);
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
//			CommonFunctions.insertCellPageHeader(table, "Source : WDC-PMKSY ", Element.ALIGN_RIGHT, 1, 4, bf8);
			document.add(table);
			///addHeader(writer);
			CommonFunctions.addFooter(writer);
			document.close();
			//getHeaderForDilrmpMMP(document);
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC3(DistLiv).pdf");
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

	@RequestMapping(value = "/downloadProductionPDF", method = RequestMethod.POST)
	public ModelAndView downloadProductionPDF(HttpServletRequest request, HttpServletResponse response) 
	{
//		//WDC-PMKSY-0001113
		String stcd=request.getParameter("stcd");
		String headtypeh= request.getParameter("headtypeh");
		String stname= request.getParameter("stname");
		List<RPCLivelihoodBean> prodBean = new ArrayList<RPCLivelihoodBean>();
		
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ProductionReport");
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
			
			paragraph3 = new Paragraph("Report OC3- District wise details of Production System ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
//			addFooter(writer);
//			document.add(paragraph);
			
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(9);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5,5});
				table.setWidthPercentage(70);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
			
				int noactivity=0, sc=0, st=0, other=0,  total=0, women=0, liveproducwork=0;
				
				prodBean = lhoodproductepaserice.getRptLivelihoodDistList(stcd, headtypeh);
				
				CommonFunctions.insertCellHeader(table, "State : "  + stname +  "  Head : Production System"  , Element.ALIGN_LEFT, 9, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of benificiaries", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of Work Created", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				
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
				if(prodBean.size()!=0)
					for(int i=0;i<prodBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, prodBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodBean.get(i).getNoactivity()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodBean.get(i).getSc()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodBean.get(i).getSt()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodBean.get(i).getOther()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodBean.get(i).getTotal()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodBean.get(i).getWomen()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodBean.get(i).getLiveproducwork()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						noactivity=noactivity+prodBean.get(i).getNoactivity();
						sc=sc+prodBean.get(i).getSc();
						st=st+prodBean.get(i).getSt();
						other=other+prodBean.get(i).getOther();
						total=total+prodBean.get(i).getTotal();
						women=women+prodBean.get(i).getWomen();
						liveproducwork=liveproducwork+prodBean.get(i).getLiveproducwork();
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(women), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(liveproducwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(prodBean.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 9, 1, bf8);
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
//			CommonFunctions.insertCellPageHeader(table, "Source : WDC-PMKSY ", Element.ALIGN_RIGHT, 1, 4, bf8);
			document.add(table);
			///addHeader(writer);
			CommonFunctions.addFooter(writer);
			document.close();
			//getHeaderForDilrmpMMP(document);
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC3(DistProd).pdf");
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

	@RequestMapping(value = "/downloadEPAPDF", method = RequestMethod.POST)
	public ModelAndView downloadEPAPDF(HttpServletRequest request, HttpServletResponse response) 
	{
//		//WDC-PMKSY-0001113
		String stcd=request.getParameter("stcd");
		String headtypeh= request.getParameter("headtypeh");
		String stname= request.getParameter("stname");
		List<RPCLivelihoodBean> epaBean = new ArrayList<RPCLivelihoodBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("EPAReport");
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
			
			paragraph3 = new Paragraph("Report OC3- District wise details of Entry point Activities(EPAs) ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
//			addFooter(writer);
//			document.add(paragraph);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(4);
				table.setWidths(new int[] { 2, 8, 5, 5});
				table.setWidthPercentage(70);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
			
				int noactivity=0, liveproducwork=0;
				
				epaBean = lhoodproductepaserice.getRptLivelihoodDistList(stcd, headtypeh);
				
				CommonFunctions.insertCellHeader(table, "State : "  + stname +  "  Head :Entry point Activities(EPAs) "  , Element.ALIGN_LEFT, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of Work Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				int k=1;
				if(epaBean.size()!=0)
					for(int i=0;i<epaBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, epaBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(epaBean.get(i).getNoactivity()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(epaBean.get(i).getLiveproducwork()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						noactivity=noactivity+epaBean.get(i).getNoactivity();
						liveproducwork=liveproducwork+epaBean.get(i).getLiveproducwork();
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(liveproducwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(epaBean.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 4, 1, bf8);
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
					CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
//			CommonFunctions.insertCellPageHeader(table, "Source : WDC-PMKSY ", Element.ALIGN_RIGHT, 1, 4, bf8);
			document.add(table);
			///addHeader(writer);
			CommonFunctions.addFooter(writer);
			document.close();
			//getHeaderForDilrmpMMP(document);
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC3(DistEPA).pdf");
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

	@RequestMapping(value = "/downloadProjectLivelihoodPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectLivelihoodPDF(HttpServletRequest request, HttpServletResponse response) 
	{
//		//WDC-PMKSY-0001113
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
		List<RPCLivelihoodBean> projavgList = new ArrayList<RPCLivelihoodBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("LivelihoodProjectReport");
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
			
			paragraph3 = new Paragraph("Report OC3- Project wise details of Livelihood Activities for the Asst-less Person ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
				document.add(paragraph2);
				document.add(paragraph3);
				table = new PdfPTable(6);
				table.setWidths(new int[] { 2, 6, 5, 7, 7, 5 });
				table.setWidthPercentage(70);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
			
				int noactivity=0;
//				pre_total_avg_income=0, post_total_avg_income=0;
				BigDecimal  pre_total_avg_income=BigDecimal.valueOf(0);
				BigDecimal  post_total_avg_income=BigDecimal.valueOf(0);
				BigDecimal  difference=BigDecimal.valueOf(0);
				int avgincm=0;
				
				projavgList = lhoodproductepaserice.getLiveEPAProdDetailAvgProjectWise( Integer.parseInt(distid), headtypeh);
				
				CommonFunctions.insertCellHeader(table, "State :   "  + stname + "  District :   "+  distname + "  Head :   Livelihood Activities for the Asst-less Person "  , Element.ALIGN_LEFT, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Income of Beneficiaries Prior to Livelihood activities(in Rs.) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Income of Beneficiaries Post to Livelihood activities(in Rs.) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Difference", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				int cntrr=0;
				BigDecimal cntr = BigDecimal.ZERO;
				if(projavgList.size()!=0)
					for(int i=0;i<projavgList.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projavgList.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(projavgList.get(i).getNoactivity()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table,projavgList.get(i).getPre_total_avg_income()== null?"": projavgList.get(i).getPre_total_avg_income().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table,projavgList.get(i).getPost_total_avg_income() == null?"": projavgList.get(i).getPost_total_avg_income().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projavgList.get(i).getDifference()==null?"":projavgList.get(i).getDifference().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						k=k+1;
						noactivity=noactivity+projavgList.get(i).getNoactivity();
						pre_total_avg_income = pre_total_avg_income.add(projavgList.get(i).getPre_total_avg_income()==null?BigDecimal.ZERO:projavgList.get(i).getPre_total_avg_income());
						post_total_avg_income = post_total_avg_income.add(projavgList.get(i).getPost_total_avg_income()==null?BigDecimal.ZERO:projavgList.get(i).getPost_total_avg_income());
						difference=difference.add(projavgList.get(i).getDifference()==null?BigDecimal.ZERO:projavgList.get(i).getDifference());
						cntr=cntr.add(BigDecimal.ONE);
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.format("%.2f",pre_total_avg_income.doubleValue()/cntr.doubleValue()), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.format("%.2f",post_total_avg_income.doubleValue()/cntr.doubleValue()), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.format("%.2f",difference.doubleValue()/cntr.doubleValue()), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(projavgList.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 6, 1, bf8);
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
			CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			CommonFunctions.addFooter(writer);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC3(ProjectLiv).pdf");
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

	@RequestMapping(value = "/downloadProjectProductionPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectProductionPDF(HttpServletRequest request, HttpServletResponse response) 
	{
//		//WDC-PMKSY-0001113
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
		List<RPCLivelihoodBean> prodavgList = new ArrayList<RPCLivelihoodBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ProductionProjectReport");
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
			
			paragraph3 = new Paragraph("Report OC3- Project wise details of Production System ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
				document.add(paragraph2);
				document.add(paragraph3);
				table = new PdfPTable(6);
				table.setWidths(new int[] { 2, 6, 5, 7, 7, 5 });
				table.setWidthPercentage(70);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
			
				int noactivity=0;
//				pre_total_avg_income=0, post_total_avg_income=0;
				BigDecimal  pre_total_avg_income=BigDecimal.valueOf(0);
				BigDecimal  post_total_avg_income=BigDecimal.valueOf(0);
				BigDecimal  difference=BigDecimal.valueOf(0);
				
				prodavgList = lhoodproductepaserice.getLiveEPAProdDetailAvgProjectWise( Integer.parseInt(distid), headtypeh);
				
				CommonFunctions.insertCellHeader(table, "State :   "  + stname + "  District :   "+  distname + "  Head :  Production System "  , Element.ALIGN_LEFT, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Income of Beneficiaries Prior to Production System(in Rs.) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Income of Beneficiaries Post to Production System(in Rs.)  ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Difference", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				int counter=0;
				BigDecimal cntr=BigDecimal.ZERO;
				if(prodavgList.size()!=0)
					for(int i=0;i<prodavgList.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, prodavgList.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodavgList.get(i).getNoactivity()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table,prodavgList.get(i).getPre_total_avg_income()== null?"": prodavgList.get(i).getPre_total_avg_income().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table,prodavgList.get(i).getPost_total_avg_income() == null?"": prodavgList.get(i).getPost_total_avg_income().toString(),Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, prodavgList.get(i).getDifference()==null?"":prodavgList.get(i).getDifference().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						k=k+1;
						
						noactivity=noactivity+prodavgList.get(i).getNoactivity();
						pre_total_avg_income = pre_total_avg_income.add(prodavgList.get(i).getPre_total_avg_income() == null ? BigDecimal.ZERO : prodavgList.get(i).getPre_total_avg_income());
						post_total_avg_income = post_total_avg_income.add(prodavgList.get(i).getPost_total_avg_income() == null ? BigDecimal.ZERO : prodavgList.get(i).getPost_total_avg_income());
						difference = difference.add(prodavgList.get(i).getDifference() == null ? BigDecimal.ZERO : prodavgList.get(i).getDifference());
						cntr = cntr.add(BigDecimal.ONE);
						counter=counter+prodavgList.get(i).getCnt();
						}

						CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(pre_total_avg_income.divide(cntr, 2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(post_total_avg_income.divide(cntr, 2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
						CommonFunctions.insertCell3(table, String.valueOf(difference.divide(cntr, 2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

				if(prodavgList.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 6, 1, bf8);
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
			CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			CommonFunctions.addFooter(writer);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC3(ProjectProduction).pdf");
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


	@RequestMapping(value = "/downloadProjectEPAPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectEPAPDF(HttpServletRequest request, HttpServletResponse response) 
	{
//		//WDC-PMKSY-0001113
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
		List<RPCLivelihoodBean> projepaList = new ArrayList<RPCLivelihoodBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("EPAProjectReport");
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
			
			paragraph3 = new Paragraph("Report OC3- Project wise details of Entry point Activities(EPAs)  ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
				document.add(paragraph2);
				document.add(paragraph3);
				table = new PdfPTable(5);
				table.setWidths(new int[] { 2, 8, 5, 5, 5});
				table.setWidthPercentage(70);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
			
				int noactivity=0, liveproducwork=0;
				
				projepaList = lhoodproductepaserice.getLiveEPAProdDetailProjectWise( Integer.parseInt(distid), headtypeh);
				
				CommonFunctions.insertCellHeader(table, "State :   "  + stname + "  District :   "+  distname + "  Head :   Entry point Activities(EPAs)  "  , Element.ALIGN_LEFT, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Name of activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Activities ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of Work Created ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				int k=1;
				if(projepaList.size()!=0)
					for(int i=0;i<projepaList.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projepaList.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, projepaList.get(i).getActivitydesc(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(projepaList.get(i).getNoactivity()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(projepaList.get(i).getLiveproducwork()), Element.ALIGN_RIGHT, 1, 1, bf8);
						k=k+1;
						noactivity=noactivity+projepaList.get(i).getNoactivity();
						liveproducwork=liveproducwork+projepaList.get(i).getLiveproducwork();
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 3, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(liveproducwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(projepaList.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 5, 1, bf8);
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
			CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			CommonFunctions.addFooter(writer);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC3(ProjectEPA).pdf");
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
	
	@RequestMapping(value = "/downloadProjectDetailsofLivelihoodPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectDetailsofLivelihoodPDF(HttpServletRequest request, HttpServletResponse response) 
	{
//		//WDC-PMKSY-0001113
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
		String projcode = request.getParameter("projcode");
		List<RPCLivelihoodBean> livDtailList = new ArrayList<RPCLivelihoodBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("LivelihoodProjectDetailReport");
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
			
			paragraph3 = new Paragraph("Report OC3- Project wise details of Livelihood Activities for the Asst-less Person ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
				document.add(paragraph2);
				document.add(paragraph3);
				table = new PdfPTable(12);
				table.setWidths(new int[] { 2, 6, 5, 5, 5, 5, 5, 5, 5, 7, 7, 5  });
				table.setWidthPercentage(70);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
			
				int noactivity=0,sc=0, st=0,other=0,total=0, women=0, pre_avg_income=0, post_avg_income=0, livproducwork=0;
				
				livDtailList = lhoodproductepaserice.getLiveEPAProdDetailSProjectWise( Integer.parseInt(distid), headtypeh, Integer.parseInt(projcode));
				
				CommonFunctions.insertCellHeader(table, "State :   "  + stname + "  District :   "+  distname + "  Head :   Livelihood Activities for the Asst-less Person "  , Element.ALIGN_LEFT, 12, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Name of activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of beneficiaries", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Income of Beneficiaries Prior to Livelihood activities(in Rs.) ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Income of Beneficiaries Post to Livelihood activities(in Rs.) ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no of Work Created", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				
				int k=1;
				int cntr=0;
				String prj="";
				if(livDtailList.size()!=0)
					for(int i=0;i<livDtailList.size();i++) 
					{
						if (!prj.equals(livDtailList.get(i).getProj_name())) {
					
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, livDtailList.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						k=k+1;
						
						}else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCell(table, "", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						}
						prj=livDtailList.get(i).getProj_name();
						CommonFunctions.insertCell(table, livDtailList.get(i).getActivitydesc(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livDtailList.get(i).getNoactivity()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livDtailList.get(i).getSc()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livDtailList.get(i).getSt()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livDtailList.get(i).getOther()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livDtailList.get(i).getTotal()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livDtailList.get(i).getWomen()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livDtailList.get(i).getPre_avg_income()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livDtailList.get(i).getPost_avg_income()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(livDtailList.get(i).getLiveproducwork()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						
						noactivity=noactivity+livDtailList.get(i).getNoactivity();
						sc=sc+livDtailList.get(i).getSc();
						st=st+livDtailList.get(i).getSt();
						other=other+livDtailList.get(i).getOther();
						total=total+livDtailList.get(i).getTotal();
						women=women+livDtailList.get(i).getWomen();
						pre_avg_income=pre_avg_income+livDtailList.get(i).getPre_avg_income();
						post_avg_income=post_avg_income+livDtailList.get(i).getPost_avg_income();
						livproducwork=livproducwork+livDtailList.get(i).getLiveproducwork();
						cntr=cntr+livDtailList.get(i).getCnt();
						
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 3, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(women), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(pre_avg_income/cntr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(post_avg_income/cntr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(livproducwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(livDtailList.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 12, 1, bf8);
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
			CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			CommonFunctions.addFooter(writer);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC3(ProjectDetailLiv).pdf");
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
	@RequestMapping(value = "/downloadProjectDetailsofProductionPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectDetailsofProductionPDF(HttpServletRequest request, HttpServletResponse response) 
	{
//		//WDC-PMKSY-0001113
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
		String projcode = request.getParameter("projcode");
		List<RPCLivelihoodBean> prodDtailList = new ArrayList<RPCLivelihoodBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ProductionProjectDetailReport");
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
			
			paragraph3 = new Paragraph("Report OC3- Project wise details of Production System ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
				document.add(paragraph2);
				document.add(paragraph3);
				table = new PdfPTable(12);
				table.setWidths(new int[] { 2, 6, 5, 5, 5, 5, 5, 5, 5, 7, 7, 5  });
				table.setWidthPercentage(70);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
			
				int noactivity=0,sc=0, st=0,other=0,total=0, women=0, pre_avg_income=0, post_avg_income=0, livproducwork=0;
				
				prodDtailList = lhoodproductepaserice.getLiveEPAProdDetailSProjectWise( Integer.parseInt(distid), headtypeh, Integer.parseInt(projcode));
				
				CommonFunctions.insertCellHeader(table, "State :   "  + stname + "  District :   "+  distname + "  Head :   Production System "  , Element.ALIGN_LEFT, 12, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Name of activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of activities", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of beneficiaries", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Income of Beneficiaries Prior to Production System(in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Average Income of Beneficiaries Post to Production System(in Rs.)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no of Work Created", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "SC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "ST", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Other", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Women", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				int k=1;
				int cntr=0;
				String prj="";
				if(prodDtailList.size()!=0)
					for(int i=0;i<prodDtailList.size();i++) 
					{
						if (!prj.equals(prodDtailList.get(i).getProj_name())) {
					
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, prodDtailList.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						k=k+1;
						}else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						CommonFunctions.insertCell(table, "", Element.ALIGN_CENTER, 1, 1, bf8Bold);
						}
						prj=prodDtailList.get(i).getProj_name();
						
						CommonFunctions.insertCell(table, prodDtailList.get(i).getActivitydesc(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodDtailList.get(i).getNoactivity()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodDtailList.get(i).getSc()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodDtailList.get(i).getSt()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodDtailList.get(i).getOther()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodDtailList.get(i).getTotal()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodDtailList.get(i).getWomen()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodDtailList.get(i).getPre_avg_income()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodDtailList.get(i).getPost_avg_income()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(prodDtailList.get(i).getLiveproducwork()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						noactivity=noactivity+prodDtailList.get(i).getNoactivity();
						sc=sc+prodDtailList.get(i).getSc();
						st=st+prodDtailList.get(i).getSt();
						other=other+prodDtailList.get(i).getOther();
						total=total+prodDtailList.get(i).getTotal();
						women=women+prodDtailList.get(i).getWomen();
						pre_avg_income=pre_avg_income+prodDtailList.get(i).getPre_avg_income();
						post_avg_income=post_avg_income+prodDtailList.get(i).getPost_avg_income();
						livproducwork=livproducwork+prodDtailList.get(i).getLiveproducwork();
						cntr=cntr+prodDtailList.get(i).getCnt();
						
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 3, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(sc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(st), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(other), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(women), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(pre_avg_income/cntr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(post_avg_income/cntr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(livproducwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(prodDtailList.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 12, 1, bf8);
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
			CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			CommonFunctions.addFooter(writer);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC3(ProjectDetailProd).pdf");
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

	@RequestMapping(value = "/downloadProjectDetailsOfEPAPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectDetailsOfEPAPDF(HttpServletRequest request, HttpServletResponse response) 
	{
//		//WDC-PMKSY-0001113
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
//		String projcode = request.getParameter("projcode");
		List<RPCLivelihoodBean> epaDtailList = new ArrayList<RPCLivelihoodBean>();
		
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("EPAProjectDetailReport");
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
			
			paragraph3 = new Paragraph("Report OC3- Project wise details of Entry point Activities(EPAs)  ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
				document.add(paragraph2);
				document.add(paragraph3);
				table = new PdfPTable(5);
				table.setWidths(new int[] { 2, 8, 5, 5, 5});
				table.setWidthPercentage(70);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
			
				int noactivity=0, liveproducwork=0;
				
				epaDtailList = lhoodproductepaserice.getLiveEPAProdDetailProjectWise( Integer.parseInt(distid), headtypeh);
				
				CommonFunctions.insertCellHeader(table, "State :   "  + stname + "  District :   "+  distname + "  Head :   Entry point Activities(EPAs)  "  , Element.ALIGN_LEFT, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Name of activities", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Activities ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total no. of Work Created ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				int k=1;
				String prj="";
				if(epaDtailList.size()!=0)
					for(int i=0;i<epaDtailList.size();i++) 
					{
						if (!prj.equals(epaDtailList.get(i).getProj_name())) {
							
							CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, epaDtailList.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
							k=k+1;
							}else {
								CommonFunctions.insertCell(table, "", Element.ALIGN_CENTER, 1, 1, bf8Bold);
							CommonFunctions.insertCell(table, "", Element.ALIGN_CENTER, 1, 1, bf8Bold);
							}
							prj=epaDtailList.get(i).getProj_name();
						
						CommonFunctions.insertCell(table, epaDtailList.get(i).getActivitydesc(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(epaDtailList.get(i).getNoactivity()) ,Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(epaDtailList.get(i).getLiveproducwork()), Element.ALIGN_RIGHT, 1, 1, bf8);
						prj=epaDtailList.get(i).getProj_name();
						noactivity=noactivity+epaDtailList.get(i).getNoactivity();
						liveproducwork=liveproducwork+epaDtailList.get(i).getLiveproducwork();
			}
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 3, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(noactivity), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(liveproducwork), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(epaDtailList.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 5, 1, bf8);
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
			CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			CommonFunctions.addFooter(writer);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- OC3(ProjectEPA).pdf");
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

	@RequestMapping(value = "/downloadExcelStgetRptLiveProdEpa", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStgetRptLiveProdEpa(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String state=request.getParameter("state");
		String headtype=request.getParameter("headtype");
		
		List<RPCLivelihoodBean> listlivelihoodBean = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> listProdSysBean = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> listEPABean = new ArrayList<RPCLivelihoodBean>();
		
		if (headtype.equals("livelihood")) {
			listlivelihoodBean = lhoodproductepaserice.getRptLivelihoodList(Integer.parseInt(state));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- State wise details of Livelihood Activities for the Asst-less Person");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- State wise details of Livelihood Activities for the Asst-less Person";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,3,7);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,0,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(listlivelihoodBean.size()+7,listlivelihoodBean.size()+7,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total no. of Activities");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total no. of beneficiaries");  
			cell.setCellStyle(style);
			// set horizontal alignment center
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for (int i=4;i<8;i++)
			{
				cell = rowhead.createCell(i);
				cell.setCellStyle(style);
			}
			
			
			Row rowhead1 = sheet.createRow(6);
			
			
			for (int i=0;i<3;i++)
			{
				cell = rowhead1.createCell(i);
				cell.setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("SC");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("ST");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Other");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Women");  
			cell.setCellStyle(style);
			
	        
	        int sno = 7;
	        int totNoAct =0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        
	        for(RPCLivelihoodBean liBean: listlivelihoodBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-6);  
	        	row.createCell(1).setCellValue(liBean.getStname());  
	        	row.createCell(2).setCellValue(liBean.getNoactivity());  
	        	row.createCell(3).setCellValue(liBean.getSc());  
	        	row.createCell(4).setCellValue(liBean.getSt()); 
	        	row.createCell(5).setCellValue(liBean.getOther());  
	        	row.createCell(6).setCellValue(liBean.getTotal());  
	        	row.createCell(7).setCellValue(liBean.getWomen());
	        	sno++;
	        	
	        	totNoAct = totNoAct + liBean.getNoactivity();
	        	totSc = totSc + liBean.getSc();
	        	totSt = totSt + liBean.getSt();
	        	totOther = totOther + liBean.getOther();
	        	totTotal = totTotal + liBean.getTotal();
	        	totWomen = totWomen + liBean.getWomen();
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
	        
	        Row row = sheet.createRow(listlivelihoodBean.size()+7);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listlivelihoodBean.size(), 7);
	        String fileName = "attachment; filename=Report OC3- State wise Livelihood Activities Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
		}
		if (headtype.equals("production")) {
			listProdSysBean = lhoodproductepaserice.getRptProductionList(Integer.parseInt(state));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- State wise details of Production System");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- State wise details of Production System";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,3,7);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,0,2); 
	        sheet.addMergedRegion(mergedRegion);

	        mergedRegion = new CellRangeAddress(listProdSysBean.size()+7,listProdSysBean.size()+7,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total no. of Activites");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total no. of beneficiaries");  
			cell.setCellStyle(style);
			// set horizontal alignment center
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for (int i=4;i<8;i++)
			{
				cell = rowhead.createCell(i);
				cell.setCellStyle(style);
			}
			
			
			Row rowhead1 = sheet.createRow(6);
			
			for (int i=0;i<3;i++)
			{
				cell = rowhead1.createCell(i);
				cell.setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("SC");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("ST");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Other");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Women");  
			cell.setCellStyle(style);
			
	        
	        int sno = 7;
	        int totNoAct =0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        
	        for(RPCLivelihoodBean prodBean: listProdSysBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-6);  
	        	row.createCell(1).setCellValue(prodBean.getStname());  
	        	row.createCell(2).setCellValue(prodBean.getNoactivity());  
	        	row.createCell(3).setCellValue(prodBean.getSc());  
	        	row.createCell(4).setCellValue(prodBean.getSt()); 
	        	row.createCell(5).setCellValue(prodBean.getOther());  
	        	row.createCell(6).setCellValue(prodBean.getTotal());  
	        	row.createCell(7).setCellValue(prodBean.getWomen());
	        	sno++;
	        	
	        	totNoAct = totNoAct + prodBean.getNoactivity();
	        	totSc = totSc + prodBean.getSc();
	        	totSt = totSt + prodBean.getSt();
	        	totOther = totOther + prodBean.getOther();
	        	totTotal = totTotal + prodBean.getTotal();
	        	totWomen = totWomen + prodBean.getWomen();
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
//		font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listProdSysBean.size()+7);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listProdSysBean.size(), 7);
	        String fileName = "attachment; filename=Report OC3- State wise Production Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
		}
		
		if (headtype.equals("epa")) {
			listEPABean = lhoodproductepaserice.getRptEpaList(Integer.parseInt(state));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- State wise details of Entry point Activities(EPAs)");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- State wise details of Entry point Activities(EPAs)";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 2, areaAmtValDetail, workbook);
			
	        mergedRegion = new CellRangeAddress(listEPABean.size()+6,listEPABean.size()+6,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total no. of Activites");  
			cell.setCellStyle(style);
			
			
	        int sno = 6;
	        int totNoAct =0;
	        
	        
	        for(RPCLivelihoodBean epaBean: listEPABean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-5);  
	        	row.createCell(1).setCellValue(epaBean.getStname());  
	        	row.createCell(2).setCellValue(epaBean.getNoactivity());  
	        	sno++;
	        	
	        	totNoAct = totNoAct + epaBean.getNoactivity();
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
	        
	        Row row = sheet.createRow(listEPABean.size()+6);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	     
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listEPABean.size(), 2);
	        String fileName = "attachment; filename=Report OC3- State wise EPA Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
		}
		
		return "output/rpclivelihoodstate";
		
	}
	
	@RequestMapping(value = "/downloadExcelRptLiveProdEpaDist", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelRptLiveProdEpaDist(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String stcd=request.getParameter("stcd");
		String headtypeh= request.getParameter("headtypeh");
		String stname= request.getParameter("stname");
		
		List<RPCLivelihoodBean> listlivelihoodBean = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> listProdSysBean = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> listEPABean = new ArrayList<RPCLivelihoodBean>();
		
		if (headtypeh.equals("livelihood")) {
			listlivelihoodBean = lhoodproductepaserice.getRptLivelihoodDistList(stcd, headtypeh);
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- District wise details of Livelihood Activities for the Asst-less Person");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- District wise details of Livelihood Activities for the Asst-less Person";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,8);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,8); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,7); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(listlivelihoodBean.size()+8,listlivelihoodBean.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			
	        Row detail = sheet.createRow(5);
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   Head : Livelihood Activities for the Asst-less Person");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<9;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total no. of Activities");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total no. of beneficiaries");  
			cell.setCellStyle(style);
			// set horizontal alignment center
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for (int i=4;i<8;i++)
			{
				cell = rowhead.createCell(i);
				cell.setCellStyle(style);
			}
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Total no. of Work Created");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead1 = sheet.createRow(7);
			
			
			for (int i=0;i<3;i++)
			{
				cell = rowhead1.createCell(i);
				cell.setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("SC");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("ST");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Other");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Women");  
			cell.setCellStyle(style);
			rowhead1.createCell(8).setCellStyle(style);
	        
	        int sno = 8;
	        int totNoAct =0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        int totWorkCreat = 0;
	        
	        for(RPCLivelihoodBean liBean: listlivelihoodBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-7);  
	        	row.createCell(1).setCellValue(liBean.getDistname());  
	        	row.createCell(2).setCellValue(liBean.getNoactivity());  
	        	row.createCell(3).setCellValue(liBean.getSc());  
	        	row.createCell(4).setCellValue(liBean.getSt()); 
	        	row.createCell(5).setCellValue(liBean.getOther());  
	        	row.createCell(6).setCellValue(liBean.getTotal());  
	        	row.createCell(7).setCellValue(liBean.getWomen());
	        	row.createCell(8).setCellValue(liBean.getLiveproducwork());
	        	sno++;
	        	
	        	totNoAct = totNoAct + liBean.getNoactivity();
	        	totSc = totSc + liBean.getSc();
	        	totSt = totSt + liBean.getSt();
	        	totOther = totOther + liBean.getOther();
	        	totTotal = totTotal + liBean.getTotal();
	        	totWomen = totWomen + liBean.getWomen();
	        	totWorkCreat = totWorkCreat + liBean.getLiveproducwork();
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
	        
	        Row row = sheet.createRow(listlivelihoodBean.size()+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totWorkCreat);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listlivelihoodBean.size(), 7);
	        String fileName = "attachment; filename=Report OC3- District wise Livelihood Activities Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		if (headtypeh.equals("production")) {
			listProdSysBean = lhoodproductepaserice.getRptLivelihoodDistList(stcd, headtypeh);
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- District wise details of Production System");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- District wise details of Production System";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,8);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,8); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,7); 
	        sheet.addMergedRegion(mergedRegion);
	        

	        mergedRegion = new CellRangeAddress(listProdSysBean.size()+8,listProdSysBean.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
	        
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   Head : Production System");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<9;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total no. of Activities");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total no. of beneficiaries");  
			cell.setCellStyle(style);
			// set horizontal alignment center
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for (int i=4;i<8;i++)
			{
				cell = rowhead.createCell(i);
				cell.setCellStyle(style);
			}
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Total no. of Work Created");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead1 = sheet.createRow(7);
			
			
			for (int i=0;i<3;i++)
			{
				cell = rowhead1.createCell(i);
				cell.setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("SC");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("ST");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Other");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Women");  
			cell.setCellStyle(style);
			rowhead1.createCell(8).setCellStyle(style);
			
	        
	        int sno = 8;
	        int totNoAct =0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        int totWorkCreat = 0;
	        
	        for(RPCLivelihoodBean prodBean: listProdSysBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-7);  
	        	row.createCell(1).setCellValue(prodBean.getDistname());  
	        	row.createCell(2).setCellValue(prodBean.getNoactivity());  
	        	row.createCell(3).setCellValue(prodBean.getSc());  
	        	row.createCell(4).setCellValue(prodBean.getSt()); 
	        	row.createCell(5).setCellValue(prodBean.getOther());  
	        	row.createCell(6).setCellValue(prodBean.getTotal());  
	        	row.createCell(7).setCellValue(prodBean.getWomen());
	        	row.createCell(8).setCellValue(prodBean.getLiveproducwork());
	        	sno++;
	        	
	        	totNoAct = totNoAct + prodBean.getNoactivity();
	        	totSc = totSc + prodBean.getSc();
	        	totSt = totSt + prodBean.getSt();
	        	totOther = totOther + prodBean.getOther();
	        	totTotal = totTotal + prodBean.getTotal();
	        	totWomen = totWomen + prodBean.getWomen();
	        	totWorkCreat = totWorkCreat + prodBean.getLiveproducwork();
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
//		font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listProdSysBean.size()+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totWorkCreat);
	        cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listProdSysBean.size(), 8);
	        String fileName = "attachment; filename=Report OC3- District wise Production Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
		}
		
		if (headtypeh.equals("epa")) {
			listEPABean = lhoodproductepaserice.getRptLivelihoodDistList(stcd, headtypeh);
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- District wise details of Entry point Activities(EPAs)");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- District wise details of Entry point Activities(EPAs)";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 3, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,3);
	        sheet.addMergedRegion(mergedRegion);
	        

	        mergedRegion = new CellRangeAddress(listEPABean.size()+7,listEPABean.size()+7,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   Head : Entry point Activities(EPAs)");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<4;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        Row rowhead = sheet.createRow(6); 
			
	        cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total no. of Activites");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total no. of Work Created");  
			cell.setCellStyle(style);
			
			
	        int sno = 7;
	        int totNoAct = 0;
	        int totWorkCreat = 0;
	        
	        for(RPCLivelihoodBean epaBean: listEPABean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-6);  
	        	row.createCell(1).setCellValue(epaBean.getDistname());  
	        	row.createCell(2).setCellValue(epaBean.getNoactivity()); 
	        	row.createCell(3).setCellValue(epaBean.getLiveproducwork()); 
	        	sno++;
	        	
	        	totNoAct = totNoAct + epaBean.getNoactivity();
	        	totWorkCreat = totWorkCreat + epaBean.getLiveproducwork();
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
	        
	        Row row = sheet.createRow(listEPABean.size()+7);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totWorkCreat);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listEPABean.size(), 2);
	        String fileName = "attachment; filename=Report OC3- District wise EPA Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		
		
		return "output/rpclivelihoodDistrict";
		
	}

	@RequestMapping(value = "/downloadExcelRptLiveProdEpaProjectAvgWise", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelRptLiveProdEpaProjectAvgWise(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
		
		List<RPCLivelihoodBean> listlivelihoodBean = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> listProdSysBean = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> listEPABean = new ArrayList<RPCLivelihoodBean>();
		
		if (headtypeh.equals("livelihood")) {
			listlivelihoodBean = lhoodproductepaserice.getLiveEPAProdDetailAvgProjectWise(Integer.parseInt(distid), headtypeh);
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- Project wise details of Livelihood Activities for the Asst-less Person");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- Project wise details of Livelihood Activities for the Asst-less Person";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,5);
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(listlivelihoodBean.size()+7,listlivelihoodBean.size()+7,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   District : "+distname +"   Head : 	Livelihood Activities for the Asst-less Person");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			
			for(int i =1; i<6; i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total No. of Activities");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Average Income of Beneficiaries Prior to Livelihood activities(in Rs.)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Average Income of Beneficiaries Post to Livelihood activities(in Rs.)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Difference");  
			cell.setCellStyle(style);
			
	        
	        int sno = 7;
	        int totNoAct = 0;
	        double totAvgPrior = 0;
	        double totAvgPost = 0;
	        double totDiff = 0;
	        double cntr=0;
	        for(RPCLivelihoodBean liBean: listlivelihoodBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-6);  
	        	row.createCell(1).setCellValue(liBean.getProj_name());  
	        	row.createCell(2).setCellValue(liBean.getNoactivity());  
	        	row.createCell(3).setCellValue(liBean.getPre_total_avg_income()==null?0.0:liBean.getPre_total_avg_income().doubleValue());  
	        	row.createCell(4).setCellValue(liBean.getPost_total_avg_income()==null?0.0:liBean.getPost_total_avg_income().doubleValue()); 
	        	row.createCell(5).setCellValue(liBean.getDifference()==null?0.0:liBean.getDifference().doubleValue());  
	        	sno++;
	        	
	        	totNoAct = totNoAct + liBean.getNoactivity();
	        	totAvgPrior = totAvgPrior + (liBean.getPre_total_avg_income()==null?0.0:liBean.getPre_total_avg_income().doubleValue()); 
	        	totAvgPost = totAvgPost + (liBean.getPost_total_avg_income()==null?0.0:liBean.getPost_total_avg_income().doubleValue());
	        	totDiff = totDiff + (liBean.getDifference()==null?0.0:liBean.getDifference().doubleValue());
	        	cntr=cntr+1;
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
	        
	        Row row = sheet.createRow(listlivelihoodBean.size()+7);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totAvgPrior/cntr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totAvgPost/cntr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totDiff/cntr);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listlivelihoodBean.size(), 5);
	        String fileName = "attachment; filename=Report OC3- Project wise Livelihood Activities Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		if (headtypeh.equals("production")) {
			
			listProdSysBean = lhoodproductepaserice.getLiveEPAProdDetailAvgProjectWise( Integer.parseInt(distid), headtypeh);
			
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- Project wise details of Production System");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- Project wise details of Production System";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,5);
	        sheet.addMergedRegion(mergedRegion);
	        

	        mergedRegion = new CellRangeAddress(listProdSysBean.size()+7,listProdSysBean.size()+7,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
	        
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   District : "+distname +"   Head : Production System");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<6;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total No. of Activities");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Average Income of Beneficiaries Prior to Production System(in Rs.)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Average Income of Beneficiaries Post to Production System(in Rs.)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Difference");  
			cell.setCellStyle(style);
			
	        
	        int sno = 7;
	        int totNoAct = 0;
	        double totAvgPrior = 0;
	        double totAvgPost = 0;
	        double totDiff = 0;
	        double cntr=0;
	        for(RPCLivelihoodBean prodBean: listProdSysBean) {
	        	Row row = sheet.createRow(sno);
	        	row.createCell(0).setCellValue(sno-6);  
	        	row.createCell(1).setCellValue(prodBean.getProj_name());  
	        	row.createCell(2).setCellValue(prodBean.getNoactivity());  
	        	row.createCell(3).setCellValue(prodBean.getPre_total_avg_income()==null?0.0:prodBean.getPre_total_avg_income().doubleValue());  
	        	row.createCell(4).setCellValue(prodBean.getPost_total_avg_income()==null?0.0:prodBean.getPost_total_avg_income().doubleValue());
	        	row.createCell(5).setCellValue(prodBean.getDifference()==null?0.0:prodBean.getDifference().doubleValue());  
	        	sno++;
	        	
	        	totNoAct = totNoAct + prodBean.getNoactivity();
	        	totAvgPrior = totAvgPrior + (prodBean.getPre_total_avg_income()==null?0.0:prodBean.getPre_total_avg_income().doubleValue());
	        	totAvgPost = totAvgPost + (prodBean.getPost_total_avg_income()==null?0.0:prodBean.getPost_total_avg_income().doubleValue());
	        	totDiff = totDiff + (prodBean.getDifference()==null?0.0:prodBean.getDifference().doubleValue());
	        	cntr=cntr+1;
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
//		font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listProdSysBean.size()+7);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totAvgPrior/cntr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totAvgPost/cntr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totDiff/cntr);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listProdSysBean.size(), 5);
	        String fileName = "attachment; filename=Report OC3- Project wise Production Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
		}
		
		if (headtypeh.equals("epa")) {
			listEPABean = lhoodproductepaserice.getLiveEPAProdDetailProjectWise( Integer.parseInt(distid), headtypeh);
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- Project wise details of Entry point Activities(EPAs)");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- Project wise details of Entry point Activities(EPAs)";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 4, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,4);
	        sheet.addMergedRegion(mergedRegion);
	        

	        mergedRegion = new CellRangeAddress(listEPABean.size()+7,listEPABean.size()+7,0,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   District : "+distname +"   Head : Entry point Activities(EPAs)");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<5;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        Row rowhead = sheet.createRow(6); 
			
	        cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Name of Activites");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("No. of Activites");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Total no. of Work Created");  
			cell.setCellStyle(style);
			
	        int sno = 7;
	        int totNoAct = 0;
	        int totWorkCreat = 0;
	        String projName = "";
	        
	        for(RPCLivelihoodBean epaBean: listEPABean) {
	        	Row row = sheet.createRow(sno);
	        	if(!projName.equals(epaBean.getProj_name())) {
	        	row.createCell(0).setCellValue(sno-6);  
	        	row.createCell(1).setCellValue(epaBean.getProj_name()); 
	        	projName = epaBean.getProj_name();
	        	}
	        	row.createCell(2).setCellValue(epaBean.getActivitydesc());
	        	row.createCell(3).setCellValue(epaBean.getNoactivity()); 
	        	row.createCell(4).setCellValue(epaBean.getLiveproducwork()); 
	        	sno++;
	        	
	        	totNoAct = totNoAct + epaBean.getNoactivity();
	        	totWorkCreat = totWorkCreat + epaBean.getLiveproducwork();
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
	        
	        Row row = sheet.createRow(listEPABean.size()+7);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totWorkCreat);	
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listEPABean.size(), 4);
	        String fileName = "attachment; filename=Report OC3- Project wise EPA Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
			
		}
		
		return "output/rpclivelihoodAvgProject";
		
	}

	@RequestMapping(value = "/downloadExcelRptLiveProdEpaSingleProjectWise", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelRptLiveProdEpaSingleProjectWise(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String distid=request.getParameter("distid");
		String headtypeh= request.getParameter("headtypeh");
		String distname= request.getParameter("distname");
		String stname= request.getParameter("stname");
		String projcode = request.getParameter("projcode");
		
		List<RPCLivelihoodBean> listlivelihoodBean = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> listProdSysBean = new ArrayList<RPCLivelihoodBean>();
		List<RPCLivelihoodBean> listEPABean = new ArrayList<RPCLivelihoodBean>();
		
		if (headtypeh.equals("livelihood")) {
			listlivelihoodBean = lhoodproductepaserice.getLiveEPAProdDetailSProjectWise( Integer.parseInt(distid), headtypeh, Integer.parseInt(projcode));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- Project wise details of Livelihood Activities for the Asst-less Person");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- Project wise details of Livelihood Activities for the Asst-less Person";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,11);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,3,3);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,4,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,9,9);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,11,11);
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(listlivelihoodBean.size()+8,listlivelihoodBean.size()+8,0,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   District : "+distname +"   Head : 	Livelihood Activities for the Asst-less Person");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			
			for(int i =1; i<11; i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Name of Activities");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("No. of Activities");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("No. of beneficiaries");  
			cell.setCellStyle(style);
			// set horizontal alignment center
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for (int i=5;i<8;i++)
			{
				cell = rowhead.createCell(i);
				cell.setCellStyle(style);
			}
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Average Income of Beneficiaries Prior to Livelihood activities(in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Average Income of Beneficiaries Post to Livelihood activities(in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Total no. of Work Created");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	        
			Row rowhead1 = sheet.createRow(7);
			
			
			for (int i=0;i<4;i++)
			{
				cell = rowhead1.createCell(i);
				cell.setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("SC");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("ST");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Other");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Women");  
			cell.setCellStyle(style);
			
			for (int i=9;i<12;i++)
			{
				cell = rowhead1.createCell(i);
				cell.setCellStyle(style);
			}
			
			
			int sno = 8;
	        int totNoAct =0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        double totAvgPrior = 0;
	        double totAvgPost = 0;
	        int totWorkCreat = 0;
	        String projName = "";
	        double cntr = 0;
	        for(RPCLivelihoodBean liBean: listlivelihoodBean) {
	        	Row row = sheet.createRow(sno);
	        	 
	        	if(!projName.equals(liBean.getProj_name())) {
	        		row.createCell(0).setCellValue(sno-7); 
	        		row.createCell(1).setCellValue(liBean.getProj_name()); 
	        		projName = liBean.getProj_name();
		        	}
	        	row.createCell(2).setCellValue(liBean.getActivitydesc()); 
	        	row.createCell(3).setCellValue(liBean.getNoactivity());  
	        	row.createCell(4).setCellValue(liBean.getSc()); 
	        	row.createCell(5).setCellValue(liBean.getSt()); 
	        	row.createCell(6).setCellValue(liBean.getOther()); 
	        	row.createCell(7).setCellValue(liBean.getTotal()); 
	        	row.createCell(8).setCellValue(liBean.getWomen()); 
	        	row.createCell(9).setCellValue(liBean.getPre_avg_income()==null?0.0:liBean.getPre_avg_income().doubleValue());  
	        	row.createCell(10).setCellValue(liBean.getPost_avg_income()==null?0.0:liBean.getPost_avg_income().doubleValue()); 
	        	row.createCell(11).setCellValue(liBean.getLiveproducwork());   
	        	sno++;
	        	
	        	totNoAct = totNoAct + liBean.getNoactivity();
	        	totSc = totSc + liBean.getSc();
	        	totSt = totSt + liBean.getSt();
	        	totOther = totOther + liBean.getOther();
	        	totTotal = totTotal + liBean.getTotal();
	        	totWomen = totWomen + liBean.getWomen();
	        	totAvgPrior = totAvgPrior + (liBean.getPre_avg_income()==null?0.0:liBean.getPre_avg_income().doubleValue()); 
	        	totAvgPost = totAvgPost + (liBean.getPost_avg_income()==null?0.0:liBean.getPost_avg_income().doubleValue());
	        	totWorkCreat = totWorkCreat + liBean.getLiveproducwork();
//	        	cntr=cntr+1;
	        	cntr=cntr+liBean.getCnt();
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
	        
	        Row row = sheet.createRow(listlivelihoodBean.size()+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totAvgPrior/cntr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totAvgPost/cntr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totWorkCreat);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listlivelihoodBean.size(), 11);
	        String fileName = "attachment; filename=Report OC3- Project wise details of Livelihood Activities Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
		}
		if (headtypeh.equals("production")) {
			
			listProdSysBean = lhoodproductepaserice.getLiveEPAProdDetailSProjectWise( Integer.parseInt(distid), headtypeh, Integer.parseInt(projcode));
			
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- Project wise details of Production System");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- Project wise details of Production System ";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,11);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,3,3);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,4,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,9,9);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,11,11);
	        sheet.addMergedRegion(mergedRegion);
	        

	        mergedRegion = new CellRangeAddress(listProdSysBean.size()+8,listProdSysBean.size()+8,0,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
	        
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   District : "+distname +"   Head : Production System");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<11;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        Row rowhead = sheet.createRow(6); 
			
	        cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Name of Activities");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("No. of Activities");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("No. of beneficiaries");  
			cell.setCellStyle(style);
			// set horizontal alignment center
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for (int i=5;i<8;i++)
			{
				cell = rowhead.createCell(i);
				cell.setCellStyle(style);
			}
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Average Income of Beneficiaries Prior to Production System(in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Average Income of Beneficiaries Post to Production System(in Rs.)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Total no. of Work Created");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	        
			Row rowhead1 = sheet.createRow(7);
			
			
			for (int i=0;i<4;i++)
			{
				cell = rowhead1.createCell(i);
				cell.setCellStyle(style);
			}
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("SC");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("ST");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Other");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Total");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Women");  
			cell.setCellStyle(style);
			
			for (int i=9;i<12;i++)
			{
				cell = rowhead1.createCell(i);
				cell.setCellStyle(style);
			}
			
			
			int sno = 8;
	        int totNoAct =0;
	        int totSc = 0;
	        int totSt = 0;
	        int totOther = 0;
	        int totTotal = 0;
	        int totWomen = 0;
	        double totAvgPrior = 0;
	        double totAvgPost = 0;
	        int totWorkCreat = 0;
	        String projName = "";
	        double cntr=0;
	        for(RPCLivelihoodBean prodBean: listProdSysBean) {
	        	Row row = sheet.createRow(sno);
	        	 
	        	if(!projName.equals(prodBean.getProj_name())) {
	        		row.createCell(0).setCellValue(sno-7); 
	        		row.createCell(1).setCellValue(prodBean.getProj_name()); 
	        		projName = prodBean.getProj_name();
		        	}
	        	row.createCell(2).setCellValue(prodBean.getActivitydesc()); 
	        	row.createCell(3).setCellValue(prodBean.getNoactivity());  
	        	row.createCell(4).setCellValue(prodBean.getSc()); 
	        	row.createCell(5).setCellValue(prodBean.getSt()); 
	        	row.createCell(6).setCellValue(prodBean.getOther()); 
	        	row.createCell(7).setCellValue(prodBean.getTotal()); 
	        	row.createCell(8).setCellValue(prodBean.getWomen()); 
	        	row.createCell(9).setCellValue(prodBean.getPre_avg_income()==null?0.0:prodBean.getPre_avg_income().doubleValue());  
	        	row.createCell(10).setCellValue(prodBean.getPost_avg_income()==null?0.0:prodBean.getPost_avg_income().doubleValue()); 
	        	row.createCell(11).setCellValue(prodBean.getLiveproducwork());   
	        	sno++;
	        	
	        	totNoAct = totNoAct + prodBean.getNoactivity();
	        	totSc = totSc + prodBean.getSc();
	        	totSt = totSt + prodBean.getSt();
	        	totOther = totOther + prodBean.getOther();
	        	totTotal = totTotal + prodBean.getTotal();
	        	totWomen = totWomen + prodBean.getWomen();
	        	totAvgPrior = totAvgPrior + (prodBean.getPre_avg_income()==null?0.0:prodBean.getPre_avg_income().doubleValue()); 
	        	totAvgPost = totAvgPost + (prodBean.getPost_avg_income()==null?0.0:prodBean.getPost_avg_income().doubleValue());
	        	totWorkCreat = totWorkCreat + prodBean.getLiveproducwork();
	        	cntr=cntr+prodBean.getCnt();
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
//		font1.setColor(IndexedColors.WHITE.getIndex());
			style1.setFont(font1);
	        
	        Row row = sheet.createRow(listProdSysBean.size()+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSc);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totSt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totTotal);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totWomen);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totAvgPrior/cntr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totAvgPost/cntr);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totWorkCreat);
	        cell.setCellStyle(style1);
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listProdSysBean.size(), 11);
	        String fileName = "attachment; filename=Report OC3- Project wise details of Production Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
		}
		
		if (headtypeh.equals("epa")) {
			
			listEPABean = lhoodproductepaserice.getLiveEPAProdDetailProjectWise( Integer.parseInt(distid), headtypeh);
			
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report OC3- Project wise details of Entry point Activities(EPAs)");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report OC3- Project wise details of Entry point Activities(EPAs)";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 4, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,4);
	        sheet.addMergedRegion(mergedRegion);
	        

	        mergedRegion = new CellRangeAddress(listEPABean.size()+7,listEPABean.size()+7,0,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        //creating the 5th row using the createRow() method  
			
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stname +"   District : "+distname +"   Head : Entry point Activities(EPAs)");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<5;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
	        Row rowhead = sheet.createRow(6); 
			
	        cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Name of Activites");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("No. of Activites");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Total no. of Work Created");  
			cell.setCellStyle(style);
			
	        int sno = 1;
	        int rowno = 7;
	        int totNoAct = 0;
	        int totWorkCreat = 0;
	        String projName = "";
	        
	        for(RPCLivelihoodBean epaBean: listEPABean) {
	        	Row row = sheet.createRow(rowno);
	        	if(!projName.equals(epaBean.getProj_name())) {
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(epaBean.getProj_name()); 
	        		projName = epaBean.getProj_name();
	        		sno++;
		        	}
	        	row.createCell(2).setCellValue(epaBean.getActivitydesc());
	        	row.createCell(3).setCellValue(epaBean.getNoactivity()); 
	        	row.createCell(4).setCellValue(epaBean.getLiveproducwork()); 
	        	rowno++;
	        	
	        	
	        	totNoAct = totNoAct + epaBean.getNoactivity();
	        	totWorkCreat = totWorkCreat + epaBean.getLiveproducwork();
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
	        
	        Row row = sheet.createRow(listEPABean.size()+7);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totNoAct);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totWorkCreat);	
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, listEPABean.size(), 4);
	        String fileName = "attachment; filename=Report OC3- Project wise details of EPA Details.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
			
		}
		
		return "output/rpclivelihoodProject";
		
	}	
	
}
			
			
			
			
			
			
			
			