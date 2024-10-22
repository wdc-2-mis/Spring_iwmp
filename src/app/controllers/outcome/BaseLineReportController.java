package app.controllers.outcome;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
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

import app.bean.BaseLineOutcomeBean;
import app.bean.NewBaseLineSurveyBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.model.GetGoiReleaseToStateTreasury;
import app.service.DistrictMasterService;
import app.service.ProjectMasterService;
import app.service.StateMasterService;
import app.service.outcome.BaseLineOutcomeReportService;
import app.service.outcome.BaseLineReportService;

@Controller("BaseLineReportController")
public class BaseLineReportController {
	
	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	BaseLineReportService reportService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@RequestMapping(value = "/blsrpt", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurvey(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/baselineReport");
		mav.addObject("projectList",reportService.getBlsReportStateLevel());
		mav.addObject("stateList",stateMasterService.getAllState());	
		return mav;
	}
	
	@RequestMapping(value = "/blsrpt", method = RequestMethod.POST)
	public ModelAndView getBaseLineSurveyPost(HttpServletRequest request,@RequestParam("state") int stCode) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/baselineReport");
		if(stCode>0)
		mav.addObject("projectList",reportService.getBlsReportStateLevelForStCode(stCode));
		else
		mav.addObject("projectList",reportService.getBlsReportStateLevel());
		mav.addObject("stateList",stateMasterService.getAllState());	
		mav.addObject("stCode",stCode);	
		return mav;
	}
	
	
	@RequestMapping(value = "/blsrptdist", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveyReoprtDistrict(HttpServletRequest request,@RequestParam("id") int id) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/baselineDistrictReport");
		 mav.addObject("projectList",reportService.getBlsReportDistrictLevel(id));
		 mav.addObject("stateName",stateMasterService.getStateByStateCode(id).get(id));	
		 mav.addObject("distCode",id);
			//System.out.println(id+"a"+stateMasterService.getStateByStateCode(id).get(id)+"b");
		//System.out.println(id);
			
		return mav;
	}
	
	@RequestMapping(value = "/blsrptproj", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveyReoprtProject(HttpServletRequest request,@RequestParam("id") int id) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/baselineProjectReport");
		 mav.addObject("projectList",reportService.getBlsReportProjectLevel(id));
		 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(id).entrySet().iterator().next();
		 mav.addObject("stateName",stateMasterService.getStateByDistCode(id).get(entry.getKey()));	
		 mav.addObject("distName",districtMasterService.getDistrictByDistCode(id).get(id));	
		 mav.addObject("projId",id);
		 mav.addObject("id", id);
		//System.out.println(id);
			
		return mav;
	}
	
	@RequestMapping(value = "/blsrptdetail", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveyReoprtDetail(HttpServletRequest request,@RequestParam("id") int id, @RequestParam("totplt") int totplot) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/baselineDetailReport");
		// mav.addObject("projectList",reportService.getBlsOutReportDetailLevel(id));
		 List<NewBaseLineSurveyBean> list= new ArrayList<NewBaseLineSurveyBean>();
		 List<NewBaseLineSurveyBean> sublist= new ArrayList<NewBaseLineSurveyBean>();
		 LinkedHashMap<String,List<NewBaseLineSurveyBean>> map = new LinkedHashMap<String,List<NewBaseLineSurveyBean>>();
		    list = reportService.getBlsReportDetailLevel(id);
			int i = 0;
			String data[] = null;
			ArrayList dataList = new ArrayList();
			for (NewBaseLineSurveyBean bean : list) 
			{
				if(!map.containsKey(bean.getPlot_no().toString() + bean.getVillage_name())) {
					sublist= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					map.put(bean.getPlot_no().toString() + bean.getVillage_name(), sublist);
				}else {
					sublist=map.get(bean.getPlot_no().toString() + bean.getVillage_name());
					sublist.add(bean);
					map.put(bean.getPlot_no().toString() + bean.getVillage_name(), sublist);
				}
				
			}
			
			 mav.addObject("stateName",projectMasterService.getProjectByProjectId(id).getIwmpDistrict().getIwmpState().getStName());	
			 mav.addObject("distName",projectMasterService.getProjectByProjectId(id).getIwmpDistrict().getDistName());	
			 mav.addObject("valueList",map);
		    mav.addObject("projName", projectMasterService.getProjectByProjectId(id).getProjName());
		    mav.addObject("projectcd", id);
		    mav.addObject("id", id);
		    mav.addObject("totplot", totplot);
		    
		//System.out.println(id);
			
		return mav;
	}
	
	@RequestMapping(value = "/downloadblsrptDetailOfOutcomePDF", method = RequestMethod.POST)
	public ModelAndView downloadblsrptDetailOfOutcomePDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String id = request.getParameter("id");
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projName=request.getParameter("projName");
		String projId=request.getParameter("projId");
		String totplot=request.getParameter("totplot");
		
		List<NewBaseLineSurveyBean> listt= new ArrayList<NewBaseLineSurveyBean>();
		listt = reportService.getBlsReportDetailLevel(Integer.parseInt(id));
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineProjectDetailReport");
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
			
				paragraph3 = new Paragraph("Report SB1-  Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey for Project "+" '"+projName+"' ", f3);
				
			Paragraph paragraph4 = new Paragraph("Note: Total Income is the Total Value of Output.", f1);	
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph4.setAlignment(Element.ALIGN_LEFT);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			paragraph4.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			document.add(paragraph4);
				table = new PdfPTable(17);
				table.setWidths(new int[] { 5, 6, 7, 5, 5, 5, 5, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(2);
				
				BigDecimal area= BigDecimal.valueOf(0);
				BigDecimal crop_area= BigDecimal.valueOf(0);
				BigDecimal avg_income= BigDecimal.valueOf(0);
				BigDecimal crop_production= BigDecimal.valueOf(0);
				
				BigDecimal totalcrop= BigDecimal.valueOf(0);
				BigDecimal totalavgincome= BigDecimal.valueOf(0);
				BigDecimal totalcropprod= BigDecimal.valueOf(0);
				
				CommonFunctions.insertCellHeader(table, "State:- "+stateName+"\r\n"+ "District:- "+distName+ "\r\n" + "Project:- " + projName+"\r\n", Element.ALIGN_LEFT, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 13, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Block", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Gram Panchayat ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot/Gata No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Irrigation Status", Element.ALIGN_CENTER,  1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Ownership ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Owner Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "No. of Crop", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Forest Land type", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Season", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Crop Type", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Area (Col-A)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Crop Production per Hectare (in Quintal)(Col-B) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Avg. Income per Quintal(Col-C)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Income (A*B*C)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				 
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
				
				int k=1;
				String blockk="";
				String gpp = "";
				String villl="";
				String plgg="";
				String irss="";
				String ownss="";
				String ownamee="";
				String cslandd="";
				String nofcrpp="";
				double total=0;
				double tototal=0;
				String areaa="";
				BigDecimal arr=new BigDecimal(0);
				if(listt.size()!=0)
					for(int i=0;i<listt.size();i++)
					{
						
						if(i!=0&&!plgg.equalsIgnoreCase(listt.get(i).getPlot_no())){
							
							CommonFunctions.insertCell3(table, " Total", Element.ALIGN_RIGHT, 13, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(crop_area), Element.ALIGN_CENTER, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(crop_production), Element.ALIGN_CENTER, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(avg_income), Element.ALIGN_CENTER, 1, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.format(Locale.US,"%1$.2f",total), Element.ALIGN_CENTER, 1, 1, bf10Bold);
							total = 0.0;
							
							crop_area=crop_area.add(listt.get(i).getCrop_area()==null?BigDecimal.ZERO:listt.get(i).getCrop_area());
							avg_income=avg_income.add(listt.get(i).getAvg_income()==null?BigDecimal.ZERO:listt.get(i).getAvg_income());
							crop_production=crop_production.add(listt.get(i).getCrop_production()==null?BigDecimal.ZERO:listt.get(i).getCrop_production());
							
							crop_area=BigDecimal.valueOf(0);
							crop_production=BigDecimal.valueOf(0);
							avg_income=BigDecimal.valueOf(0);
						
						}
						if (!blockk.equalsIgnoreCase(listt.get(i).getBlock_name())) 
						{
								CommonFunctions.insertCell(table, listt.get(i).getBlock_name(), Element.ALIGN_LEFT, 1, 1, bf8);}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!gpp.equalsIgnoreCase(listt.get(i).getGram_panchayat_name())) 
						{
							CommonFunctions.insertCell(table, listt.get(i).getGram_panchayat_name(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!villl.equalsIgnoreCase(listt.get(i).getVillage_name())) 
						{
							CommonFunctions.insertCell(table, listt.get(i).getVillage_name(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!plgg.equalsIgnoreCase(listt.get(i).getPlot_no())) 
						{
							CommonFunctions.insertCell(table, listt.get(i).getPlot_no(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!areaa.equals(listt.get(i).getPlot_no())) 
						{
							CommonFunctions.insertCell(table, listt.get(i).getArea()==null?"":listt.get(i).getArea().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							arr=arr.add(listt.get(i).getArea()==null?BigDecimal.ZERO:listt.get(i).getArea());
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						} 
						
						if (!irss.equalsIgnoreCase(listt.get(i).getPlot_no())) 
						{
							CommonFunctions.insertCell(table, listt.get(i).getIrrigation(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!ownss.equalsIgnoreCase(listt.get(i).getPlot_no())) 
						{
							CommonFunctions.insertCell(table, listt.get(i).getOwnership(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!ownamee.equalsIgnoreCase(listt.get(i).getOwner_name())) 
						{
							CommonFunctions.insertCell(table, listt.get(i).getOwner_name(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!cslandd.equalsIgnoreCase(listt.get(i).getPlot_no())) 
						{
							CommonFunctions.insertCell(table, listt.get(i).getClassification_land(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (nofcrpp != null && !nofcrpp.equalsIgnoreCase(listt.get(i).getPlot_no())) {
						    CommonFunctions.insertCell(table, listt.get(i).getNo_crop(), Element.ALIGN_LEFT, 1, 1, bf8);
						} else {
						    CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						}

						CommonFunctions.insertCell(table, listt.get(i).getForest_land(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listt.get(i).getSeason(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listt.get(i).getCrop_type(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listt.get(i).getCrop_area()==null?"":listt.get(i).getCrop_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listt.get(i).getCrop_production()==null?"":listt.get(i).getCrop_production().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listt.get(i).getAvg_income()==null?"":listt.get(i).getAvg_income().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);

						String cropAreaStr = listt.get(i).getCrop_area() == null ? "" : listt.get(i).getCrop_area().toString();
						String avgIncomeStr = listt.get(i).getAvg_income() == null ? "" : listt.get(i).getAvg_income().toString();
						String cropProductionStr = listt.get(i).getCrop_production() == null ? "" : listt.get(i).getCrop_production().toString();

						double cropArea = cropAreaStr.isEmpty() ? 0.0 : Double.parseDouble(cropAreaStr);
						double avgIncome = avgIncomeStr.isEmpty() ? 0.0 : Double.parseDouble(avgIncomeStr);
						double cropProduction = cropProductionStr.isEmpty() ? 0.0 : Double.parseDouble(cropProductionStr);
						double product = cropArea * avgIncome * cropProduction;
						CommonFunctions.insertCell(table, String.format(Locale.US,"%1$.2f", product), Element.ALIGN_RIGHT, 1, 1, bf8);
						total = total+ product;
						
						blockk=listt.get(i).getBlock_name();
						gpp=listt.get(i).getGram_panchayat_name();
						villl=listt.get(i).getVillage_name();
						plgg=listt.get(i).getPlot_no();
						irss=listt.get(i).getPlot_no();
						ownss=listt.get(i).getPlot_no();
						ownamee=listt.get(i).getPlot_no();
						cslandd=listt.get(i).getPlot_no();
						nofcrpp=listt.get(i).getPlot_no();
						areaa=listt.get(i).getPlot_no();
						
						area=area.add(listt.get(i).getArea()==null?BigDecimal.ZERO:listt.get(i).getArea());
						crop_area=crop_area.add(listt.get(i).getCrop_area()==null?BigDecimal.ZERO:listt.get(i).getCrop_area());
						crop_production=crop_production.add(listt.get(i).getCrop_production()==null?BigDecimal.ZERO:listt.get(i).getCrop_production());
						avg_income=avg_income.add(listt.get(i).getAvg_income()==null?BigDecimal.ZERO:listt.get(i).getAvg_income());
						
						totalcrop=totalcrop.add(listt.get(i).getCrop_area()==null?BigDecimal.ZERO:listt.get(i).getCrop_area());
						totalcropprod=totalcropprod.add(listt.get(i).getCrop_production()==null?BigDecimal.ZERO:listt.get(i).getCrop_production());
						totalavgincome=totalavgincome.add(listt.get(i).getAvg_income()==null?BigDecimal.ZERO:listt.get(i).getAvg_income());
						tototal = tototal+ product;
					}
					CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 13, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(crop_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(crop_production), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(avg_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table,  String.format(Locale.US,"%1$.2f",total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 3, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totplot), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(arr), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "", Element.ALIGN_RIGHT, 8, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totalcrop), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totalcropprod), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, "", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US,"%1$.2f",tototal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					if(listt.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 17, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=SB1-Report(ProjectDetail).pdf");
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


	
	
	@RequestMapping(value = "/downloadbaselinePDF", method = RequestMethod.POST)
	public ModelAndView downloadblsPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String stCode=request.getParameter("stCode");
		String stname=request.getParameter("stname");
		List<BaseLineOutcomeBean> listblBean = new ArrayList<BaseLineOutcomeBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineReport");
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
			
				paragraph3 = new Paragraph("Report SB1- State wise Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey  ", f3);
				Paragraph paragraph4 = new Paragraph("Note: Total Income is the Total Value of Output.", f1);	
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph4.setAlignment(Element.ALIGN_LEFT);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			paragraph4.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			document.add(paragraph4);
				table = new PdfPTable(13);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				Integer totPlotNo = 0;
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal treatable_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal degraded_land= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal others= BigDecimal.valueOf(0);
				BigDecimal forest_land= BigDecimal.valueOf(0);
				BigDecimal net_sown_area= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area= BigDecimal.valueOf(0);
				BigDecimal total_income= BigDecimal.valueOf(0);

				if(Integer.parseInt(stCode.equals("")?"0":stCode)!=0) {
					listblBean = reportService.getBlsReportStateLevelForStCode(Integer.parseInt(stCode));
				}
				else {
					listblBean = reportService.getBlsReportStateLevel();
				}
				
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 13, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Plot No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Area ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Cropped Area)", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Net Sown Area", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Grossed Cropped Area", Element.ALIGN_CENTER,  1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Income", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultivable Wasteland", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Forest Land.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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
				if(listblBean.size()!=0)
					for(int i=0;i<listblBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblBean.get(i).getTotal_plot()==null?"":listblBean.get(i).getTotal_plot().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblBean.get(i).getProject_area()==null?"":listblBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblBean.get(i).getTreatable_area()==null?"":listblBean.get(i).getTreatable_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblBean.get(i).getCultivable_wasteland()==null?"":listblBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblBean.get(i).getDegraded_land()==null?"":listblBean.get(i).getDegraded_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblBean.get(i).getRainfed()==null?"":listblBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblBean.get(i).getForest_land()==null?"":listblBean.get(i).getForest_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblBean.get(i).getOthers()==null?"":listblBean.get(i).getOthers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					
						if (listblBean.get(i).getNet_sown_area().compareTo(listblBean.get(i).getGross_cropped_area()) == 0) { 
							 CommonFunctions.insertCell(table, listblBean.get(i).getNet_sown_area()==null?"":listblBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    } 
					    else if (listblBean.get(i).getNet_sown_area().compareTo(listblBean.get(i).getGross_cropped_area()) == 1) { 
					    	CommonFunctions.insertCell(table, listblBean.get(i).getGross_cropped_area()==null?"":listblBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    } 
					        else { 
					        	 CommonFunctions.insertCell(table, listblBean.get(i).getNet_sown_area()==null?"":listblBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    } 
					
						CommonFunctions.insertCell(table, listblBean.get(i).getGross_cropped_area()==null?"":listblBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", listblBean.get(i).getTotal_income()==null?BigDecimal.ZERO:listblBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						k=k+1;
						totPlotNo = (listblBean.get(i).getTotal_plot()==null?0:listblBean.get(i).getTotal_plot().intValue()) +totPlotNo;
						project_area=project_area.add(listblBean.get(i).getProject_area()==null?BigDecimal.ZERO:listblBean.get(i).getProject_area());
						treatable_area=treatable_area.add(listblBean.get(i).getTreatable_area()==null?BigDecimal.ZERO:listblBean.get(i).getTreatable_area());
						cultivable_wasteland=cultivable_wasteland.add(listblBean.get(i).getCultivable_wasteland()==null?BigDecimal.ZERO:listblBean.get(i).getCultivable_wasteland());
						degraded_land=degraded_land.add(listblBean.get(i).getDegraded_land()==null?BigDecimal.ZERO:listblBean.get(i).getDegraded_land());
						rainfed=rainfed.add(listblBean.get(i).getRainfed()==null?BigDecimal.ZERO:listblBean.get(i).getRainfed());
						forest_land=forest_land.add(listblBean.get(i).getForest_land()==null?BigDecimal.ZERO:listblBean.get(i).getForest_land());
						others=others.add(listblBean.get(i).getOthers()==null?BigDecimal.ZERO:listblBean.get(i).getOthers());
						
						if (listblBean.get(i).getNet_sown_area().compareTo(listblBean.get(i).getGross_cropped_area()) == 0) { 
							net_sown_area=net_sown_area.add(listblBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:listblBean.get(i).getNet_sown_area());
					    } 
					    else if (listblBean.get(i).getNet_sown_area().compareTo(listblBean.get(i).getGross_cropped_area()) == 1) { 
					    	net_sown_area=net_sown_area.add(listblBean.get(i).getGross_cropped_area()==null?BigDecimal.ZERO:listblBean.get(i).getGross_cropped_area());
					    } 
					    else { 
					    	net_sown_area=net_sown_area.add(listblBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:listblBean.get(i).getNet_sown_area());
					    } 
						
						gross_cropped_area=gross_cropped_area.add(listblBean.get(i).getGross_cropped_area()==null?BigDecimal.ZERO:listblBean.get(i).getGross_cropped_area());
						total_income=total_income.add(listblBean.get(i).getTotal_income()==null?BigDecimal.ZERO:listblBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000)));
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totPlotNo), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(treatable_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(forest_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listblBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-SB1.pdf");
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
	
	@RequestMapping(value = "/downloaDistWisebaselinePDF", method = RequestMethod.POST)
	public ModelAndView downloaDistWisebaselinePDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String stateName=request.getParameter("stateName");
		String distCode=request.getParameter("distCode");
		List<BaseLineOutcomeBean> listDistblBean = new ArrayList<BaseLineOutcomeBean>();
		listDistblBean=reportService.getBlsReportDistrictLevel(Integer.parseInt(distCode));
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineDistReport");
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
			
			paragraph3 = new Paragraph("Report SB1- Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey for State '"+stateName+"'", f3);
			Paragraph paragraph4 = new Paragraph("Note: Total Income is the Total Value of Output.", f1);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph4.setAlignment(Element.ALIGN_LEFT);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			paragraph4.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			document.add(paragraph4);
				table = new PdfPTable(13);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				Integer totPlotNo = 0;
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal treatable_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal degraded_land= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal others= BigDecimal.valueOf(0);
				BigDecimal forest_land= BigDecimal.valueOf(0);
				BigDecimal net_sown_area= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area= BigDecimal.valueOf(0);
				BigDecimal total_income= BigDecimal.valueOf(0);

				CommonFunctions.insertCellHeader(table, "State:- "+stateName, Element.ALIGN_LEFT, 3, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Plot No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Area ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Cropped Area)", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Net Sown Area", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Grossed Cropped Area", Element.ALIGN_CENTER,  1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Income", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultivable Wasteland", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Forest Land.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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
				if(listDistblBean.size()!=0)
					for(int i=0;i<listDistblBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblBean.get(i).getTotal_plot().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblBean.get(i).getTreatable_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblBean.get(i).getDegraded_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblBean.get(i).getForest_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblBean.get(i).getOthers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						if (listDistblBean.get(i).getNet_sown_area().compareTo(listDistblBean.get(i).getGross_cropped_area()) == 0) { 
							CommonFunctions.insertCell(table, listDistblBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    } 
					    else if (listDistblBean.get(i).getNet_sown_area().compareTo(listDistblBean.get(i).getGross_cropped_area()) == 1) { 
					    	CommonFunctions.insertCell(table, listDistblBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    } 
					    else { 
					    	CommonFunctions.insertCell(table, listDistblBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    } 
						
					//	CommonFunctions.insertCell(table, listDistblBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", listDistblBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						totPlotNo = (listDistblBean.get(i).getTotal_plot()==null?0:listDistblBean.get(i).getTotal_plot().intValue()) +totPlotNo;
						project_area=project_area.add(listDistblBean.get(i).getProject_area());
						treatable_area=treatable_area.add(listDistblBean.get(i).getTreatable_area());
						cultivable_wasteland=cultivable_wasteland.add(listDistblBean.get(i).getCultivable_wasteland());
						degraded_land=degraded_land.add(listDistblBean.get(i).getDegraded_land());
						rainfed=rainfed.add(listDistblBean.get(i).getRainfed());
						forest_land=forest_land.add(listDistblBean.get(i).getForest_land());
						others=others.add(listDistblBean.get(i).getOthers());
						
						if (listDistblBean.get(i).getNet_sown_area().compareTo(listDistblBean.get(i).getGross_cropped_area()) == 0) { 
							net_sown_area=net_sown_area.add(listDistblBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:listDistblBean.get(i).getNet_sown_area());
					    } 
					    else if (listDistblBean.get(i).getNet_sown_area().compareTo(listDistblBean.get(i).getGross_cropped_area()) == 1) { 
					    	net_sown_area=net_sown_area.add(listDistblBean.get(i).getGross_cropped_area()==null?BigDecimal.ZERO:listDistblBean.get(i).getGross_cropped_area());
					    } 
					    else { 
					    	net_sown_area=net_sown_area.add(listDistblBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:listDistblBean.get(i).getNet_sown_area());
					    } 
						
					//	net_sown_area=net_sown_area.add(listDistblBean.get(i).getNet_sown_area());
						gross_cropped_area=gross_cropped_area.add(listDistblBean.get(i).getGross_cropped_area());
						total_income=total_income.add(listDistblBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000)));

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totPlotNo), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(treatable_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(forest_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listDistblBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-SB1(Districtwise).pdf");
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
	@RequestMapping(value = "/downloaProjectWisebaselinePDF", method = RequestMethod.POST)
	public ModelAndView downloaProjectWisebaselinePDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projId=request.getParameter("projId");
		List<BaseLineOutcomeBean> listProjblBean = new ArrayList<BaseLineOutcomeBean>();
		listProjblBean=reportService.getBlsReportProjectLevel(Integer.parseInt(projId));
		
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineProjectReport");
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
			
			paragraph3 = new Paragraph("Report SB1- Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey for District '"+distName+"' of State '"+stateName + "'", f3);
			Paragraph paragraph4 = new Paragraph("Note: Total Income is the Total Value of Output.", f1);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph4.setAlignment(Element.ALIGN_LEFT);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			paragraph4.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			document.add(paragraph4);
				table = new PdfPTable(13);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				Integer totPlotNo = 0;
				BigDecimal project_area= BigDecimal.valueOf(0);
				BigDecimal treatable_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal degraded_land= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal others= BigDecimal.valueOf(0);
				BigDecimal forest_land= BigDecimal.valueOf(0);
				BigDecimal net_sown_area= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area= BigDecimal.valueOf(0);
				BigDecimal total_income= BigDecimal.valueOf(0);

				CommonFunctions.insertCellHeader(table, "State:- "+stateName +"\r\n"+  "District:- "+distName+"\r\n", Element.ALIGN_LEFT, 3, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Plot No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Area ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Cropped Area)", Element.ALIGN_CENTER, 5, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Net Sown Area", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Grossed Cropped Area", Element.ALIGN_CENTER,  1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Income", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultivable Wasteland.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed", Element.ALIGN_CENTER, 1, 1,bf8Bold);
				CommonFunctions.insertCellHeader(table, "Forest Land.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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
				if(listProjblBean.size()!=0)
					for(int i=0;i<listProjblBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listProjblBean.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listProjblBean.get(i).getTotal_plot()==null?"":listProjblBean.get(i).getTotal_plot().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listProjblBean.get(i).getProject_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listProjblBean.get(i).getTreatable_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listProjblBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listProjblBean.get(i).getDegraded_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listProjblBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listProjblBean.get(i).getForest_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listProjblBean.get(i).getOthers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						if (listProjblBean.get(i).getNet_sown_area().compareTo(listProjblBean.get(i).getGross_cropped_area()) == 0) { 
							CommonFunctions.insertCell(table, listProjblBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    } 
					    else if (listProjblBean.get(i).getNet_sown_area().compareTo(listProjblBean.get(i).getGross_cropped_area()) == 1) { 
					    	CommonFunctions.insertCell(table, listProjblBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    } 
					    else { 
					    	CommonFunctions.insertCell(table, listProjblBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
					    } 
						
					//	CommonFunctions.insertCell(table, listProjblBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listProjblBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", listProjblBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						totPlotNo = (listProjblBean.get(i).getTotal_plot()==null?0:listProjblBean.get(i).getTotal_plot().intValue()) +totPlotNo;
						project_area=project_area.add(listProjblBean.get(i).getProject_area());
						treatable_area=treatable_area.add(listProjblBean.get(i).getTreatable_area());
						cultivable_wasteland=cultivable_wasteland.add(listProjblBean.get(i).getCultivable_wasteland());
						degraded_land=degraded_land.add(listProjblBean.get(i).getDegraded_land());
						rainfed=rainfed.add(listProjblBean.get(i).getRainfed());
						forest_land=forest_land.add(listProjblBean.get(i).getForest_land());
						others=others.add(listProjblBean.get(i).getOthers());
						
						if (listProjblBean.get(i).getNet_sown_area().compareTo(listProjblBean.get(i).getGross_cropped_area()) == 0) { 
							net_sown_area=net_sown_area.add(listProjblBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:listProjblBean.get(i).getNet_sown_area());
					    } 
					    else if (listProjblBean.get(i).getNet_sown_area().compareTo(listProjblBean.get(i).getGross_cropped_area()) == 1) { 
					    	net_sown_area=net_sown_area.add(listProjblBean.get(i).getGross_cropped_area()==null?BigDecimal.ZERO:listProjblBean.get(i).getGross_cropped_area());
					    } 
					    else { 
					    	net_sown_area=net_sown_area.add(listProjblBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:listProjblBean.get(i).getNet_sown_area());
					    } 
						
						//net_sown_area=net_sown_area.add(listProjblBean.get(i).getNet_sown_area());
						gross_cropped_area=gross_cropped_area.add(listProjblBean.get(i).getGross_cropped_area());
						total_income=total_income.add(listProjblBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000)));

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(totPlotNo), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(treatable_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(forest_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listProjblBean.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 12, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-SB1(Projectwise).pdf");
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

	
	@RequestMapping(value = "/downloadExcelStateWiseBaseline", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStateWiseBaseline(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String stCode=request.getParameter("stCode");
		List<BaseLineOutcomeBean> list = new ArrayList<>();
		if(Integer.parseInt(stCode.equals("")?"0":stCode)!=0) {
			list = reportService.getBlsReportStateLevelForStCode(Integer.parseInt(stCode));
		}else {
			list = reportService.getBlsReportStateLevel();
		}
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report SB1- State wise Details of Baseline");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report SB1- State wise Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,12);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,6,0,12);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(7,8,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(7,8,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,4,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,5,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,11,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,12,12); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(list.size()+10,list.size()+10,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row note = sheet.createRow(5);
	        note.createCell(0).setCellValue("Note: Total Income is the Total Value of Output.");
	        
	        Row detail = sheet.createRow(6);
	        
			Cell cell = detail.createCell(0);
			cell.setCellValue("All area in ha. And All amounts are Rs. in Lakh.");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =1; i<13;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
			Row rowhead = sheet.createRow(7); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Plot No.");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Classification of Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =6; i<10;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Net Sown Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Grossed Cropped Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Total Income");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead1 = sheet.createRow(8);
			for(int i =0; i<5;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Cultivable Wasteland");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Rainfed");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Forest Land");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			
			for(int i =10; i<13;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			Row rowhead2 = sheet.createRow(9);
			for(int i=0;i<13;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  =10;
	        int totPlotNo = 0;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totOther = 0;
	        double totFrstArea = 0;
	        double totNewSownArea = 0;
	        double totGrssCrpdArea = 0;
	        double totTotalIncm = 0;
	        for(BaseLineOutcomeBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getTotal_plot().intValue());
	        	row.createCell(3).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());  
	        	row.createCell(5).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());  
	        	row.createCell(7).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getForest_land()==null?0.0:bean.getForest_land().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getOthers()==null?0.0:bean.getOthers().doubleValue()); 
	        	
	        	if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 0) { 
	        		row.createCell(10).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
			    else if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 1) { 
			    	row.createCell(10).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
			    } 
			    else { 
			        row.createCell(10).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
	        	  
	        	row.createCell(11).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue()); 
	        	row.createCell(12).setCellValue(bean.getTotal_income()==null?0.0:(double)Math.round(bean.getTotal_income().doubleValue()/100000*100)/100);
	        	
	        	totPlotNo = totPlotNo + (bean.getTotal_plot()==null?0:bean.getTotal_plot().intValue());
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totFrstArea = totFrstArea + (bean.getForest_land()==null?0.0:bean.getForest_land().doubleValue());
	        	totOther = totOther + (bean.getOthers()==null?0.0:bean.getOthers().doubleValue());
	        	
	        	if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 0) { 
	        		totNewSownArea = totNewSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
			    else if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 1) { 
			    	totNewSownArea = totNewSownArea + (bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
			    } 
			    else { 
			    	totNewSownArea = totNewSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
	        	
	        	totGrssCrpdArea = totGrssCrpdArea + (bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
	        	totTotalIncm = totTotalIncm + (bean.getTotal_income()==null?0.0:bean.getTotal_income().doubleValue()/100000);
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
	        
	        Row row = sheet.createRow(list.size()+10);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totPlotNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totFrstArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totNewSownArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totGrssCrpdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue((double)Math.round(totTotalIncm*100)/100);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
	        String fileName = "attachment; filename=Report SB1- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/baselineReport";
		
	}
	
	@RequestMapping(value = "/downloadExcelDistWiseBaseline", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistWiseBaseline(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String stateName=request.getParameter("stateName");
		String distCode=request.getParameter("distCode");
		List<BaseLineOutcomeBean> list = new ArrayList<>();
			list = reportService.getBlsReportDistrictLevel(Integer.parseInt(distCode));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report SB1- District wise Details of Baseline");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report SB1- Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey for State '"+stateName+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,12);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,6,1,12);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(7,8,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(7,8,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,4,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,5,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,11,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,12,12); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(list.size()+10,list.size()+10,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row note = sheet.createRow(5);
	        note.createCell(0).setCellValue("Note: Total Income is the Total Value of Output.");
	        
	        Row detail = sheet.createRow(6);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stateName);  
	        cell.setCellStyle(style);
//			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
//			for(int i =1; i<4;i++) {
//				detail.createCell(i).setCellStyle(style);
//			}
			
			cell = detail.createCell(1);
			cell.setCellValue("All area in ha. And All amounts are Rs. in Lakh.");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =2; i<12;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
			Row rowhead = sheet.createRow(7); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Plot No.");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Classification of Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =6; i<10;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Net Sown Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Grossed Cropped Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Total Income");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead1 = sheet.createRow(8);
			for(int i =0; i<5;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Cultivable Wasteland");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Rainfed");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Forest Land");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			
			for(int i =10; i<13;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			Row rowhead2 = sheet.createRow(9);
			for(int i=0;i<13;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 10;
	        int totPlotNo = 0;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totOther = 0;
	        double totFrstArea = 0;
	        double totNewSownArea = 0;
	        double totGrssCrpdArea = 0;
	        double totTotalIncm = 0;
	        for(BaseLineOutcomeBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getTotal_plot().intValue());
	        	row.createCell(3).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());  
	        	row.createCell(5).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());  
	        	row.createCell(7).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getForest_land()==null?0.0:bean.getForest_land().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getOthers()==null?0.0:bean.getOthers().doubleValue()); 
	        	
	        	if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 0) { 
	        		row.createCell(10).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
			    else if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 1) { 
			    	row.createCell(10).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
			    } 
			    else { 
			        row.createCell(10).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 

	        	row.createCell(11).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue()); 
	        	row.createCell(12).setCellValue(bean.getTotal_income()==null?0.0:(double)Math.round(bean.getTotal_income().doubleValue()/100000*100)/100);
	        	
	        	totPlotNo = totPlotNo + (bean.getTotal_plot()==null?0:bean.getTotal_plot().intValue());
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totFrstArea = totFrstArea + (bean.getForest_land()==null?0.0:bean.getForest_land().doubleValue());
	        	totOther = totOther + (bean.getOthers()==null?0.0:bean.getOthers().doubleValue());
	        	
	        	if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 0) { 
	        		totNewSownArea = totNewSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
			    else if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 1) { 
			    	totNewSownArea = totNewSownArea + (bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
			    } 
			    else { 
			    	totNewSownArea = totNewSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
	        	
	        	totGrssCrpdArea = totGrssCrpdArea + (bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
	        	totTotalIncm = totTotalIncm + (bean.getTotal_income()==null?0.0:bean.getTotal_income().doubleValue()/100000);
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
	        
	        Row row = sheet.createRow(list.size()+10);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totPlotNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totFrstArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totNewSownArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totGrssCrpdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue((double)Math.round(totTotalIncm*100)/100);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
	        String fileName = "attachment; filename=Report SB1- District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/baselineDistrictReport";
		
	}
	
	@RequestMapping(value = "/downloadExcelProjWiseBaseline", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjWiseBaseline(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projId=request.getParameter("projId");
		List<BaseLineOutcomeBean> list = new ArrayList<>();
			list = reportService.getBlsReportProjectLevel(Integer.parseInt(projId));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report SB1- Project wise Details of Baseline");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report SB1- Details of Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey for District '"+distName+"' of State '"+stateName+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,12);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,6,1,12);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(7,8,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(7,8,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,4,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,7,5,9); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,10,10);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,11,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(7,8,12,12); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(list.size()+10,list.size()+10,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row note = sheet.createRow(5);
	        note.createCell(0).setCellValue("Note: Total Income is the Total Value of Output.");
	        
	        Row detail = sheet.createRow(6);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stateName +"   District : "+distName);  
	        cell.setCellStyle(style);
//			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
//			for(int i =1; i<4;i++) {
//				detail.createCell(i).setCellStyle(style);
//			}
			
			cell = detail.createCell(1);
			cell.setCellValue("All area in ha. And All amounts are Rs. in Lakh.");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =2; i<12;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
			Row rowhead = sheet.createRow(7); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Plot No.");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Project Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Classification of Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =6; i<10;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Net Sown Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Grossed Cropped Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Total Income");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead1 = sheet.createRow(8);
			for(int i =0; i<5;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Cultivable Wasteland");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Rainfed");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Forest Land");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			
			for(int i =10; i<13;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			Row rowhead2 = sheet.createRow(9);
			for(int i=0;i<13;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  = 10;
	        int totPlotNo = 0;
	        double totProjArea = 0;
	        double totPlotArea = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totOther = 0;
	        double totFrstArea = 0;
	        double totNewSownArea = 0;
	        double totGrssCrpdArea = 0;
	        double totTotalIncm = 0;
	        for(BaseLineOutcomeBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getProjname());
	        	row.createCell(2).setCellValue(bean.getTotal_plot().intValue());
	        	row.createCell(3).setCellValue(bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());  
	        	row.createCell(5).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());  
	        	row.createCell(7).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getForest_land()==null?0.0:bean.getForest_land().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getOthers()==null?0.0:bean.getOthers().doubleValue()); 
	        	
	        	if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 0) { 
	        		row.createCell(10).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
			    else if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 1) { 
			    	row.createCell(10).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
			    } 
			    else { 
			        row.createCell(10).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
	        	
	      //  	row.createCell(10).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());  
	        	row.createCell(11).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue()); 
	        	row.createCell(12).setCellValue(bean.getTotal_income()==null?0.0:(double)Math.round(bean.getTotal_income().doubleValue()/100000*100)/100);
	        	
	        	totPlotNo = totPlotNo + (bean.getTotal_plot()==null?0:bean.getTotal_plot().intValue());
	        	totProjArea = totProjArea + (bean.getProject_area()==null?0.0:bean.getProject_area().doubleValue());
	        	totPlotArea = totPlotArea + (bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totFrstArea = totFrstArea + (bean.getForest_land()==null?0.0:bean.getForest_land().doubleValue());
	        	totOther = totOther + (bean.getOthers()==null?0.0:bean.getOthers().doubleValue());
	        	
	        	if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 0) { 
	        		totNewSownArea = totNewSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
			    else if (bean.getNet_sown_area().compareTo(bean.getGross_cropped_area()) == 1) { 
			    	totNewSownArea = totNewSownArea + (bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
			    } 
			    else { 
			    	totNewSownArea = totNewSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
			    } 
	        	
	        //	totNewSownArea = totNewSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
	        	totGrssCrpdArea = totGrssCrpdArea + (bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
	        	totTotalIncm = totTotalIncm + (bean.getTotal_income()==null?0.0:bean.getTotal_income().doubleValue()/100000);
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
	        
	        Row row = sheet.createRow(list.size()+10);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totPlotNo);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totProjArea);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(4);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totFrstArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totNewSownArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totGrssCrpdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue((double)Math.round(totTotalIncm*100)/100);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
	        String fileName = "attachment; filename=Report SB1- Project.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/baselineProjectReport";
		
	}
	
	@RequestMapping(value = "/downloadExcelblsrptdetail", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelblsrptdetail(HttpServletRequest request, HttpServletResponse response) 
	{
		String id = request.getParameter("id");
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projName=request.getParameter("projName");
		String projId=request.getParameter("projId");
		String totplot=request.getParameter("totplot");
		
		List<NewBaseLineSurveyBean> list= new ArrayList<NewBaseLineSurveyBean>();
		
		list = reportService.getBlsReportDetailLevel(Integer.parseInt(id));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report SB1- Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey for Project");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report SB1- Details of classification of Land, Net Sown Area, Gross Cropped Area and Total Income as per Baseline Survey for Project "+"'"+projName+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 16, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,16);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,0,3);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,4,16);
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row note = sheet.createRow(5);
	        note.createCell(0).setCellValue("Note: Total Income is the Total Value of Output.");
	        
	        Row detail = sheet.createRow(6);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stateName +"   District : "+distName +"   Project : "+projName);  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<4;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
			cell = detail.createCell(4);
			cell.setCellValue("All area in ha. And All amounts are in Rs.");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =5; i<16;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
			Row rowhead = sheet.createRow(7); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("Block");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Gram Panchayat");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Village");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Plot/Gata No.");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Irrigation Status");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Ownership");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Owner Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Classification of Land");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("No. of Crop");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Forest Land type");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Season");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Crop Type");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(13);
			cell.setCellValue("Area (Col-A)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(14);
			cell.setCellValue("Crop Production per Hectare (in Quintal) (Col-B)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(15);
			cell.setCellValue("Avg. Income per Quintal (Col-C)");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(16);
			cell.setCellValue("Total Income (A*B*C)");  
			cell.setCellStyle(style);
			
	        int sno = 1;
	        int rowno  = 8;
	        BigDecimal calIncome = new BigDecimal("0");
	        double totalIncome = 0.0;
	        double gdPlotArea = 0.0;
	        double gdcolA = 0.0;
	        double gdcolB = 0.0;
	        double gdcolC = 0.0;
	        double gdTotalIncome = 0.0;
	        double totcolA = 0.0;
	        double totcolB = 0.0;
	        double totcolC = 0.0;
	        double totIncome = 0.0;
	        String blkName = "";
	        String grampytName = "";
	        String villName = "";
	        String plotNo = "";
	        String noOfCrop = "";
	        String ownerName = "";
	        String cropType = "";
	        String season = "";
	        
	        for(NewBaseLineSurveyBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	if(!plotNo.equalsIgnoreCase(bean.getPlot_no())) {
	        		gdPlotArea = gdPlotArea + (bean.getArea()==null?0.0:bean.getArea().doubleValue());
	        	}
	        	if(!blkName.equalsIgnoreCase(bean.getBlock_name())|| !grampytName.equalsIgnoreCase(bean.getGram_panchayat_name()) || 
	        			!villName.equalsIgnoreCase(bean.getVillage_name())|| !plotNo.equalsIgnoreCase(bean.getPlot_no())) {
	        		if(sno!=1) {
	        			mergedRegion = new CellRangeAddress(rowno,rowno,0,12); 
	       	 	        sheet.addMergedRegion(mergedRegion);
	       	 	       
	       		        cell = row.createCell(0);
	       		        cell.setCellValue("Total");
	       		        cell.setCellStyle(style);
	       		        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	       		        for(int i=1; i<=12; i++) {
	       		        	cell = row.createCell(i);
	       	   		        cell.setCellStyle(style);
	       		        }
	       		        cell = row.createCell(13);
	       		        cell.setCellValue(totcolA);
	       		        cell.setCellStyle(style);
	       		        cell = row.createCell(14);
	    		        cell.setCellValue(totcolB);
	    		        cell.setCellStyle(style);
	    		        cell = row.createCell(15);
	       		        cell.setCellValue(totcolC);
	       		        cell.setCellStyle(style);
	       		        cell = row.createCell(16);
	    		        cell.setCellValue(totIncome);
	    		        cell.setCellStyle(style);

	    		        gdcolA = gdcolA + totcolA;
	    		        gdcolB = gdcolB + totcolB;
	    		        gdcolC = gdcolC + totcolC;
	    		        gdTotalIncome = gdTotalIncome + totIncome;
	    		        totcolA = 0.0;
	    		        totcolB = 0.0;
	    		        totcolC = 0.0;
	    		        totIncome = 0.0;
	    		        
	       		        rowno++;
	       		     row = sheet.createRow(rowno);
	        		}
	        		
	        	}
	        	if(!blkName.equalsIgnoreCase(bean.getBlock_name())) {
	        		
	        		row.createCell(0).setCellValue(bean.getBlock_name());
	        		row.createCell(1).setCellValue(bean.getGram_panchayat_name()); 
	        		row.createCell(2).setCellValue(bean.getVillage_name());
	        		row.createCell(3).setCellValue(bean.getPlot_no());
        			row.createCell(4).setCellValue(bean.getArea()==null?0.0:bean.getArea().doubleValue());
        			row.createCell(5).setCellValue(bean.getIrrigation());
        			row.createCell(6).setCellValue(bean.getOwnership());
        			row.createCell(7).setCellValue(bean.getOwner_name());
        			row.createCell(8).setCellValue(bean.getClassification_land());
        			if(bean.getClassification_land().equals("Forest Land")) {
        				row.createCell(9);
        				row.createCell(10).setCellValue(bean.getForest_land());
        				for(int i=11; i<=16; i++) {
        					row.createCell(i);
	       		        }
        			}else {
        				row.createCell(9).setCellValue(bean.getNo_crop());
        				row.createCell(10);
        				row.createCell(11).setCellValue(bean.getSeason());
        				row.createCell(12).setCellValue(bean.getCrop_type());
        				row.createCell(13).setCellValue(bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
    		        	row.createCell(14).setCellValue(bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
    		        	row.createCell(15).setCellValue(bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());

    		        	calIncome = ((bean.getCrop_area()==null?BigDecimal.ZERO:bean.getCrop_area()).multiply(bean.getCrop_production()==null?BigDecimal.ZERO:bean.getCrop_production())).multiply(bean.getAvg_income()==null?BigDecimal.ZERO:bean.getAvg_income());
    		        	totalIncome = calIncome.doubleValue();
    		        	row.createCell(16).setCellValue(totalIncome);
        			}
        			
        			blkName = bean.getBlock_name();
        			grampytName = bean.getGram_panchayat_name();
        			villName = bean.getVillage_name();
        			plotNo = bean.getPlot_no();
        			noOfCrop = bean.getNo_crop();
        			ownerName = bean.getOwner_name();
        			cropType = bean.getCrop_type();
        			
        			totcolA = totcolA + (bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
		        	totcolB = totcolB + (bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
		        	totcolC = totcolC + (bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());
		        	totIncome = totIncome + totalIncome;
        			
        			rowno++;
        			sno++;
        			
	        	}else if(!grampytName.equalsIgnoreCase(bean.getGram_panchayat_name())) {
	        		
	        		row.createCell(0);
	        		row.createCell(1).setCellValue(bean.getGram_panchayat_name());
	        		row.createCell(2).setCellValue(bean.getVillage_name());
	        		row.createCell(3).setCellValue(bean.getPlot_no());
        			row.createCell(4).setCellValue(bean.getArea()==null?0.0:bean.getArea().doubleValue());
        			row.createCell(5).setCellValue(bean.getIrrigation());
        			row.createCell(6).setCellValue(bean.getOwnership());
        			row.createCell(7).setCellValue(bean.getOwner_name());
        			row.createCell(8).setCellValue(bean.getClassification_land());
        			if(bean.getClassification_land().equalsIgnoreCase("Forest Land")) {
        				row.createCell(9);
        				row.createCell(10).setCellValue(bean.getForest_land());
        				for(int i=11; i<=16; i++) {
        					row.createCell(i);
	       		        }
        			}else {
        				row.createCell(9).setCellValue(bean.getNo_crop());
        				row.createCell(10);
        				row.createCell(11).setCellValue(bean.getSeason());
        				row.createCell(12).setCellValue(bean.getCrop_type());
        				row.createCell(13).setCellValue(bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
    		        	row.createCell(14).setCellValue(bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
    		        	row.createCell(15).setCellValue(bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());

    		        	calIncome = ((bean.getCrop_area()==null?BigDecimal.ZERO:bean.getCrop_area()).multiply(bean.getCrop_production()==null?BigDecimal.ZERO:bean.getCrop_production())).multiply(bean.getAvg_income()==null?BigDecimal.ZERO:bean.getAvg_income());
    		        	totalIncome = calIncome.doubleValue();
    		        	row.createCell(16).setCellValue(totalIncome);
        			}
        			
        			grampytName = bean.getGram_panchayat_name();
        			villName = bean.getVillage_name();
        			plotNo = bean.getPlot_no();
        			noOfCrop = bean.getNo_crop();
        			ownerName = bean.getOwner_name();
        			cropType = bean.getCrop_type();
        			
        			totcolA = totcolA + (bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
		        	totcolB = totcolB + (bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
		        	totcolC = totcolC + (bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());
		        	totIncome = totIncome + totalIncome;
        			
        			rowno++;
        			sno++;
	        	}else if(!villName.equalsIgnoreCase(bean.getVillage_name())){
	        		row.createCell(0);
	        		row.createCell(1);
	        		row.createCell(2).setCellValue(bean.getVillage_name());
	        		row.createCell(3).setCellValue(bean.getPlot_no());
        			row.createCell(4).setCellValue(bean.getArea()==null?0.0:bean.getArea().doubleValue());
        			row.createCell(5).setCellValue(bean.getIrrigation());
        			row.createCell(6).setCellValue(bean.getOwnership());
        			row.createCell(7).setCellValue(bean.getOwner_name());
        			row.createCell(8).setCellValue(bean.getClassification_land());
        			if(bean.getClassification_land().equalsIgnoreCase("Forest Land")) {
        				row.createCell(9);
        				row.createCell(10).setCellValue(bean.getForest_land());
        				for(int i=11; i<=16; i++) {
        					row.createCell(i);
	       		        }
        			}else {
        				row.createCell(9).setCellValue(bean.getNo_crop());
        				row.createCell(10);
        				row.createCell(11).setCellValue(bean.getSeason());
        				row.createCell(12).setCellValue(bean.getCrop_type());
        				row.createCell(13).setCellValue(bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
    		        	row.createCell(14).setCellValue(bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
    		        	row.createCell(15).setCellValue(bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());

    		        	calIncome = ((bean.getCrop_area()==null?BigDecimal.ZERO:bean.getCrop_area()).multiply(bean.getCrop_production()==null?BigDecimal.ZERO:bean.getCrop_production())).multiply(bean.getAvg_income()==null?BigDecimal.ZERO:bean.getAvg_income());
    		        	totalIncome = calIncome.doubleValue();
    		        	row.createCell(16).setCellValue(totalIncome);
        			}
        			
        			villName = bean.getVillage_name();
        			plotNo = bean.getPlot_no();
        			noOfCrop = bean.getNo_crop();
        			ownerName = bean.getOwner_name();
        			cropType = bean.getCrop_type();
        			
        			totcolA = totcolA + (bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
		        	totcolB = totcolB + (bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
		        	totcolC = totcolC + (bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());
		        	totIncome = totIncome + totalIncome;
        			
        			rowno++;
        			sno++;
	        	}else if(!plotNo.equalsIgnoreCase(bean.getPlot_no())) {
	        		row.createCell(0);
	        		row.createCell(1);
	        		row.createCell(2);
	        		row.createCell(3).setCellValue(bean.getPlot_no());
        			row.createCell(4).setCellValue(bean.getArea()==null?0.0:bean.getArea().doubleValue());
        			row.createCell(5).setCellValue(bean.getIrrigation());
        			row.createCell(6).setCellValue(bean.getOwnership());
        			row.createCell(7).setCellValue(bean.getOwner_name());
        			row.createCell(8).setCellValue(bean.getClassification_land());
        			if(bean.getClassification_land().equalsIgnoreCase("Forest Land")) {
        				row.createCell(9);
        				row.createCell(10).setCellValue(bean.getForest_land());
        				for(int i=11; i<=16; i++) {
        					row.createCell(i);
	       		        }
        			}else {
        				row.createCell(9).setCellValue(bean.getNo_crop());
        				row.createCell(10);
        				row.createCell(11).setCellValue(bean.getSeason());
        				row.createCell(12).setCellValue(bean.getCrop_type());
        				row.createCell(13).setCellValue(bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
    		        	row.createCell(14).setCellValue(bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
    		        	row.createCell(15).setCellValue(bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());

    		        	calIncome = ((bean.getCrop_area()==null?BigDecimal.ZERO:bean.getCrop_area()).multiply(bean.getCrop_production()==null?BigDecimal.ZERO:bean.getCrop_production())).multiply(bean.getAvg_income()==null?BigDecimal.ZERO:bean.getAvg_income());
    		        	totalIncome = calIncome.doubleValue();
    		        	row.createCell(16).setCellValue(totalIncome);
        			}
        			
        			plotNo = bean.getPlot_no();
        			noOfCrop = bean.getNo_crop();
        			ownerName = bean.getOwner_name();
        			cropType = bean.getCrop_type();
        			season = bean.getSeason();
        			
        			totcolA = totcolA + (bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
		        	totcolB = totcolB + (bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
		        	totcolC = totcolC + (bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());
		        	totIncome = totIncome + totalIncome;
		        	
        			rowno++;
        			sno++;
	        	}else {
	        		for(int i=0; i<=10; i++) {
    					row.createCell(i);
       		        }
    				if (season != null && !season.equalsIgnoreCase(bean.getSeason())) 
    					row.createCell(11).setCellValue(bean.getSeason());
    				else
    					row.createCell(11);
    				
    				row.createCell(12).setCellValue(bean.getCrop_type());
    				row.createCell(13).setCellValue(bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
		        	row.createCell(14).setCellValue(bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
		        	row.createCell(15).setCellValue(bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());

		        	calIncome = ((bean.getCrop_area()==null?BigDecimal.ZERO:bean.getCrop_area()).multiply(bean.getCrop_production()==null?BigDecimal.ZERO:bean.getCrop_production())).multiply(bean.getAvg_income()==null?BigDecimal.ZERO:bean.getAvg_income());
		        	totalIncome = calIncome.doubleValue();
		        	row.createCell(16).setCellValue(totalIncome);
		        	
		        	totcolA = totcolA + (bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
		        	totcolB = totcolB + (bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
		        	totcolC = totcolC + (bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());
		        	totIncome = totIncome + totalIncome;
		        	
		        	rowno++;
		        	sno++;
	        		
	        	}
	        	
	        	if(sno==list.size()+1) {
	        		row = sheet.createRow(rowno);
		        	mergedRegion = new CellRangeAddress(rowno,rowno,0,12); 
       	 	        sheet.addMergedRegion(mergedRegion);
       	 	       
       		        cell = row.createCell(0);
       		        cell.setCellValue("Total");
       		        cell.setCellStyle(style);
       		        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
       		        for(int i=1; i<=12; i++) {
       		        	cell = row.createCell(i);
       	   		        cell.setCellStyle(style);
       		        }
       		        cell = row.createCell(13);
       		        cell.setCellValue(totcolA);
       		        cell.setCellStyle(style);
       		        cell = row.createCell(14);
    		        cell.setCellValue(totcolB);
    		        cell.setCellStyle(style);
    		        cell = row.createCell(15);
       		        cell.setCellValue(totcolC);
       		        cell.setCellStyle(style);
       		        cell = row.createCell(16);
    		        cell.setCellValue(totIncome);
    		        cell.setCellStyle(style);

    		        gdcolA = gdcolA + totcolA;
    		        gdcolB = gdcolB + totcolB;
    		        gdcolC = gdcolC + totcolC;
    		        gdTotalIncome = gdTotalIncome + totIncome;
    		        totcolA = 0.0;
    		        totcolB = 0.0;
    		        totcolC = 0.0;
    		        totIncome = 0.0;
        		}
	        	
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
	        
			mergedRegion = new CellRangeAddress(rowno+1,rowno+1,0,2); 
	        sheet.addMergedRegion(mergedRegion);
			
	        Row row1 = sheet.createRow(rowno+1);
	        cell = row1.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		    row1.createCell(1).setCellStyle(style1);
		    row1.createCell(2).setCellStyle(style1);
		    cell = row1.createCell(3);
		    cell.setCellValue(Integer.parseInt(totplot));
		    cell.setCellStyle(style1);
	        cell = row1.createCell(4);
	        cell.setCellValue(gdPlotArea);
	        cell.setCellStyle(style1);
	        for(int i=5; i<=12; i++) {
	        	cell = row1.createCell(i);
   		        cell.setCellStyle(style1);
	        }
	        cell = row1.createCell(13);
	        cell.setCellValue(gdcolA);
	        cell.setCellStyle(style1);
	        cell = row1.createCell(14);
	        cell.setCellValue(gdcolB);
	        cell.setCellStyle(style1);
	        cell = row1.createCell(15);
//	        cell.setCellValue(gdcolC);
	        cell.setCellStyle(style1);
	        cell = row1.createCell(16);
	        cell.setCellValue(gdTotalIncome);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowno, 16);
	        String fileName = "attachment; filename=Report SB1- Project Detail.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/baselineDetailReport";
		
	}

}
