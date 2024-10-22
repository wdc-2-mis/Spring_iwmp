package app.controllers.outcome;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import app.common.CommonFunctions;
import app.service.DistrictMasterService;
import app.service.ProjectMasterService;
import app.service.StateMasterService;
import app.service.outcome.BaseLineReportService;
import app.service.outcome.OutcomeReportService;

@Controller("OutcomeReportController")
public class OutcomeReportController {
HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	OutcomeReportService reportService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@RequestMapping(value = "/outrpt", method = RequestMethod.GET)
	public ModelAndView getOutcomeReport(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/outcomeReport");
		mav.addObject("projectList",reportService.getBlsReportStateLevel());
		mav.addObject("stateList",stateMasterService.getAllState());	
		return mav;
	}
	
	@RequestMapping(value = "/outrpt", method = RequestMethod.POST)
	public ModelAndView getBaseLineSurveyPost(HttpServletRequest request,@RequestParam("state") int stCode) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/outcomeReport");
		if(stCode>0)
		mav.addObject("projectList",reportService.getBlsReportStateLevelForStCode(stCode));
		else
		mav.addObject("projectList",reportService.getBlsReportStateLevel());
		mav.addObject("stateList",stateMasterService.getAllState());	
		mav.addObject("stCode",stCode);	
		return mav;
	}
	
	
	@RequestMapping(value = "/outrptdist", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveyReoprtDistrict(HttpServletRequest request,@RequestParam("id") int id) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/outcomeDistrictReport");
		 mav.addObject("projectList",reportService.getBlsReportDistrictLevel(id));
		 mav.addObject("stateName",stateMasterService.getStateByStateCode(id).get(id));	
		 mav.addObject("stcode",id);
//		System.out.println(id+"a"+stateMasterService.getStateByStateCode(id).get(id)+"b");
//		System.out.println(id);
			
		return mav;
	}
	
	@RequestMapping(value = "/outrptproj", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveyReoprtProject(HttpServletRequest request,@RequestParam("id") int id) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/outcomeProjectReport");
		 mav.addObject("projectList",reportService.getBlsReportProjectLevel(id));
		 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(id).entrySet().iterator().next();
		 mav.addObject("stateName",stateMasterService.getStateByDistCode(id).get(entry.getKey()));	
		 mav.addObject("distName",districtMasterService.getDistrictByDistCode(id).get(id));	
		 mav.addObject("projId",id);
		 mav.addObject("id",id);
		//System.out.println(id);
			
		return mav;
	}
	
	@RequestMapping(value = "/outrptdetail", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveyReoprtDetail(HttpServletRequest request,@RequestParam("id") int id) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/outcomeDetailReport");
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
				if(!map.containsKey(bean.getPlot_no())) {
					sublist= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					map.put(bean.getPlot_no(), sublist);
				}else {
					sublist=map.get(bean.getPlot_no());
					sublist.add(bean);
					map.put(bean.getPlot_no(), sublist);
				}
				
			}
			
			 mav.addObject("stateName",projectMasterService.getProjectByProjectId(id).getIwmpDistrict().getIwmpState().getStName());	
			 mav.addObject("distName",projectMasterService.getProjectByProjectId(id).getIwmpDistrict().getDistName());	
			 mav.addObject("valueList",map);
		    mav.addObject("projName", projectMasterService.getProjectByProjectId(id).getProjName());
		    mav.addObject("projectcd", id);
		    mav.addObject("id", id);
		//System.out.println(id);
			
		return mav;
	}
	
	@RequestMapping(value = "/downloadProjectDetailOfOutcomePDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectDetailOfOutcomePDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String id = request.getParameter("id");
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projName=request.getParameter("projName");
		String projId=request.getParameter("projId");
		 List<NewBaseLineSurveyBean> list= new ArrayList<NewBaseLineSurveyBean>();
		 list = reportService.getBlsReportDetailLevel(Integer.parseInt(id));
		
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
			
				paragraph3 = new Paragraph("Report O3-  Distribution of Gross Cropped Area as per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as on date for Project "+" '"+projName+"' ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(17);
				table.setWidths(new int[] { 5, 6, 7, 5, 5, 5, 5, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				BigDecimal crop_area_achv= BigDecimal.valueOf(0);
				BigDecimal avg_income_achv= BigDecimal.valueOf(0);
				BigDecimal crop_production_achv= BigDecimal.valueOf(0);
				
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
				String block="";
				String gp = "";
				String vill="";
				String plg="";
				String irs="";
				String owns="";
				String owname="";
				String csland="";
				String nofcrp="";
				double total=0.0;
				String area="";
				BigDecimal achh_area=new BigDecimal(0);
				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						if (!block.equalsIgnoreCase(list.get(i).getBlock_name_achv())) 
						{
								CommonFunctions.insertCell(table, list.get(i).getBlock_name_achv(), Element.ALIGN_LEFT, 1, 1, bf8);}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!gp.equalsIgnoreCase(list.get(i).getGram_panchayat_name_achv())) 
						{
							CommonFunctions.insertCell(table, list.get(i).getGram_panchayat_name_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!vill.equalsIgnoreCase(list.get(i).getVillage_name_achv())) 
						{
							CommonFunctions.insertCell(table, list.get(i).getVillage_name_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!plg.equalsIgnoreCase(list.get(i).getPlot_no_achv())) 
						{
							CommonFunctions.insertCell(table, list.get(i).getPlot_no_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!area.equals(list.get(i).getPlot_no_achv()))
						{
							CommonFunctions.insertCell(table, list.get(i).getArea_achv()==null?"":list.get(i).getArea_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							achh_area=achh_area.add(list.get(i).getArea_achv()==null?BigDecimal.ZERO:list.get(i).getArea_achv());
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						} 
						
						if (!irs.equalsIgnoreCase(list.get(i).getIrrigation_achv())) 
						{
							CommonFunctions.insertCell(table, list.get(i).getIrrigation_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!owns.equalsIgnoreCase(list.get(i).getOwnership_achv())) 
						{
							CommonFunctions.insertCell(table, list.get(i).getOwnership_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						
						if (!owname.equalsIgnoreCase(list.get(i).getOwner_name_achv())) 
						{
							CommonFunctions.insertCell(table, list.get(i).getOwner_name_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!csland.equalsIgnoreCase(list.get(i).getClassification_land_achv())) 
						{
							CommonFunctions.insertCell(table, list.get(i).getClassification_land_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (nofcrp != null && !nofcrp.equalsIgnoreCase(list.get(i).getNo_crop_achv())) {
						    CommonFunctions.insertCell(table, list.get(i).getNo_crop_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
						} else {
						    CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						}

						CommonFunctions.insertCell(table, list.get(i).getForest_land_achv(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSeason_achv(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCrop_type_achv(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCrop_area_achv()==null?"":list.get(i).getCrop_area_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getCrop_production_achv()==null?"":list.get(i).getCrop_production_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getAvg_income_achv()==null?"":list.get(i).getAvg_income_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);

						String cropAreaStr = list.get(i).getCrop_area_achv() == null ? "" : list.get(i).getCrop_area_achv().toString();
						String avgIncomeStr = list.get(i).getAvg_income_achv() == null ? "" : list.get(i).getAvg_income_achv().toString();
						String cropProductionStr = list.get(i).getCrop_production_achv() == null ? "" : list.get(i).getCrop_production_achv().toString();

						double cropArea = cropAreaStr.isEmpty() ? 0.0 : Double.parseDouble(cropAreaStr);
						double avgIncome = avgIncomeStr.isEmpty() ? 0.0 : Double.parseDouble(avgIncomeStr);
						double cropProduction = cropProductionStr.isEmpty() ? 0.0 : Double.parseDouble(cropProductionStr);
						double product = cropArea * avgIncome * cropProduction;
						CommonFunctions.insertCell(table, String.format(Locale.US,"%1$.2f", product), Element.ALIGN_RIGHT, 1, 1, bf8);
						 total=total+product;
						block=list.get(i).getBlock_name_achv();
						gp=list.get(i).getGram_panchayat_name_achv();
						vill=list.get(i).getVillage_name_achv();
						plg=list.get(i).getPlot_no_achv();
						irs=list.get(i).getIrrigation_achv();
						owns=list.get(i).getOwnership_achv();
						owname=list.get(i).getOwner_name_achv();
						csland=list.get(i).getClassification_land_achv();
						nofcrp=list.get(i).getNo_crop_achv();
						area=list.get(i).getPlot_no_achv();




//						area_achh=area_achh.add(list.get(i).getArea_achv()==null?BigDecimal.ZERO:list.get(i).getArea_achv());
						crop_area_achv=crop_area_achv.add(list.get(i).getCrop_area_achv()==null?BigDecimal.ZERO:list.get(i).getCrop_area_achv());
						crop_production_achv=crop_production_achv.add(list.get(i).getCrop_production_achv()==null?BigDecimal.ZERO:list.get(i).getCrop_production_achv());
						avg_income_achv=avg_income_achv.add(list.get(i).getAvg_income_achv()==null?BigDecimal.ZERO:list.get(i).getAvg_income_achv());
						
					}
				
				CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 13, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(crop_area_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(crop_production_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(avg_income_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.format(Locale.US,"%1$.2f",total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 4, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(achh_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(""), Element.ALIGN_RIGHT, 8, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(crop_area_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(crop_production_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(avg_income_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US,"%1$.2f",total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(""), Element.ALIGN_RIGHT, 10, 1, bf10Bold);
					
					if(list.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=O3-Report(ProjectDetail).pdf");
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

	
	@RequestMapping(value = "/downloadbaselineOutcomePDF", method = RequestMethod.POST)
	public ModelAndView downloadbaselineOutcomePDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String stCode = request.getParameter("stCode");
		List<BaseLineOutcomeBean> blsBean = new ArrayList<>();
		if(stCode.length()>0) {
			blsBean = reportService.getBlsReportStateLevelForStCode(Integer.parseInt(stCode));
		}else {
			blsBean = reportService.getBlsReportStateLevel();
		}
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
			
				paragraph3 = new Paragraph("Report O3- State wise Distribution of Gross Cropped Area As per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as on date  ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(10);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				BigDecimal treatable_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal degraded_land= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal others= BigDecimal.valueOf(0);
				BigDecimal net_sown_area= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area= BigDecimal.valueOf(0);
				BigDecimal total_income= BigDecimal.valueOf(0);
				
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 10, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Grossed Cropped Area", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Net Sown Area", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Grossed Cropped Area", Element.ALIGN_CENTER,  1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Income", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultivable Wasteland", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed", Element.ALIGN_CENTER, 1, 1,bf8Bold);
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
				
				int k=1;
				if(blsBean.size()!=0)
					for(int i=0;i<blsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getTreatable_area()==null?"":blsBean.get(i).getTreatable_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getCultivable_wasteland()==null?"":blsBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getDegraded_land()==null?"":blsBean.get(i).getDegraded_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getRainfed()==null?"":blsBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getOthers()==null?"":blsBean.get(i).getOthers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getNet_sown_area()==null?"":blsBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, blsBean.get(i).getGross_cropped_area()==null?"":blsBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", blsBean.get(i).getTotal_income()==null?BigDecimal.ZERO:blsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						k=k+1;
						treatable_area=treatable_area.add(blsBean.get(i).getTreatable_area()==null?BigDecimal.ZERO:blsBean.get(i).getTreatable_area());
						cultivable_wasteland=cultivable_wasteland.add(blsBean.get(i).getCultivable_wasteland()==null?BigDecimal.ZERO:blsBean.get(i).getCultivable_wasteland());
						degraded_land=degraded_land.add(blsBean.get(i).getDegraded_land()==null?BigDecimal.ZERO:blsBean.get(i).getDegraded_land());
						rainfed=rainfed.add(blsBean.get(i).getRainfed()==null?BigDecimal.ZERO:blsBean.get(i).getRainfed());
						others=others.add(blsBean.get(i).getOthers()==null?BigDecimal.ZERO:blsBean.get(i).getOthers());
						net_sown_area=net_sown_area.add(blsBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:blsBean.get(i).getNet_sown_area());
						gross_cropped_area=gross_cropped_area.add(blsBean.get(i).getGross_cropped_area()==null?BigDecimal.ZERO:blsBean.get(i).getGross_cropped_area());
						total_income=total_income.add(blsBean.get(i).getTotal_income()==null?BigDecimal.ZERO:blsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000)));

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(treatable_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(blsBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=O3-Report.pdf");
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
	
	@RequestMapping(value = "/downloaDistbaselineOutcomePDF", method = RequestMethod.POST)
	public ModelAndView downloaDistbaselineOutcomePDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String stateName=request.getParameter("stateName");
		String stcd=request.getParameter("stcd");
		List<BaseLineOutcomeBean> DistblBean = new ArrayList<BaseLineOutcomeBean>();
		DistblBean=reportService.getBlsReportDistrictLevel(Integer.parseInt(stcd));
				
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
			
				paragraph3 = new Paragraph("Report O3- Distribution of Gross Cropped Area As per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as on date for State '"+stateName+"'", f3);
			
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
				table = new PdfPTable(10);
				table.setWidths(new int[] { 5, 8, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				BigDecimal treatable_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal degraded_land= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal others= BigDecimal.valueOf(0);
				BigDecimal net_sown_area= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area= BigDecimal.valueOf(0);
				BigDecimal total_income= BigDecimal.valueOf(0);
				
				
				
				CommonFunctions.insertCellHeader(table, "State:- " +stateName, Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 9, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Grossed Cropped Area", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Net Sown Area", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Grossed Cropped Area", Element.ALIGN_CENTER,  1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Income", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultivable Wasteland", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed", Element.ALIGN_CENTER, 1, 1,bf8Bold);
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
				
				int k=1;
				if(DistblBean.size()!=0)
					for(int i=0;i<DistblBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, DistblBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, DistblBean.get(i).getTreatable_area()==null?"":DistblBean.get(i).getTreatable_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, DistblBean.get(i).getCultivable_wasteland()==null?"":DistblBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, DistblBean.get(i).getDegraded_land()==null?"":DistblBean.get(i).getDegraded_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, DistblBean.get(i).getRainfed()==null?"":DistblBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, DistblBean.get(i).getOthers()==null?"":DistblBean.get(i).getOthers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, DistblBean.get(i).getNet_sown_area()==null?"":DistblBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, DistblBean.get(i).getGross_cropped_area()==null?"":DistblBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", DistblBean.get(i).getTotal_income()==null?BigDecimal.ZERO:DistblBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						k=k+1;
						treatable_area=treatable_area.add(DistblBean.get(i).getTreatable_area()==null?BigDecimal.ZERO:DistblBean.get(i).getTreatable_area());
						cultivable_wasteland=cultivable_wasteland.add(DistblBean.get(i).getCultivable_wasteland()==null?BigDecimal.ZERO:DistblBean.get(i).getCultivable_wasteland());
						degraded_land=degraded_land.add(DistblBean.get(i).getDegraded_land()==null?BigDecimal.ZERO:DistblBean.get(i).getDegraded_land());
						rainfed=rainfed.add(DistblBean.get(i).getRainfed()==null?BigDecimal.ZERO:DistblBean.get(i).getRainfed());
						others=others.add(DistblBean.get(i).getOthers()==null?BigDecimal.ZERO:DistblBean.get(i).getOthers());
						net_sown_area=net_sown_area.add(DistblBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:DistblBean.get(i).getNet_sown_area());
						gross_cropped_area=gross_cropped_area.add(DistblBean.get(i).getGross_cropped_area()==null?BigDecimal.ZERO:DistblBean.get(i).getGross_cropped_area());
						total_income=total_income.add(DistblBean.get(i).getTotal_income()==null?BigDecimal.ZERO:DistblBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000)));

						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(treatable_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(DistblBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=O3-DistrictReport.pdf");
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
						
	@RequestMapping(value = "/downloaProjectWiseOutcomebaselinePDF", method = RequestMethod.POST)
	public ModelAndView downloaProjectWiseOutcomebaselinePDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projId=request.getParameter("projId");
		List<BaseLineOutcomeBean> ProjblsBean = new ArrayList<BaseLineOutcomeBean>();
		ProjblsBean=reportService.getBlsReportProjectLevel(Integer.parseInt(projId));
		
		
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
			
			paragraph3 = new Paragraph("Report O3-  Distribution of Gross Cropped Area as per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as on date for District '"+distName+"' of State '"+stateName + "'", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(10);
				table.setWidths(new int[] { 5, 8, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(4);
				
				BigDecimal treatable_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal degraded_land= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal others= BigDecimal.valueOf(0);
				BigDecimal net_sown_area= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area= BigDecimal.valueOf(0);
				BigDecimal total_income= BigDecimal.valueOf(0);

				CommonFunctions.insertCellHeader(table, "State:- "+stateName+"\r\n"
						+ "District:- "+ distName, Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 9, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Cropped Area)", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Net Sown Area", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Grossed Cropped Area", Element.ALIGN_CENTER,  1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Income", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultivable Wasteland.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed", Element.ALIGN_CENTER, 1, 1,bf8Bold);
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
				
				int k=1;
				if(ProjblsBean.size()!=0)
					for(int i=0;i<ProjblsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ProjblsBean.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ProjblsBean.get(i).getTreatable_area()==null?"":ProjblsBean.get(i).getTreatable_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ProjblsBean.get(i).getCultivable_wasteland()==null?"":ProjblsBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ProjblsBean.get(i).getDegraded_land()==null?"":ProjblsBean.get(i).getDegraded_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ProjblsBean.get(i).getRainfed()==null?"":ProjblsBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ProjblsBean.get(i).getOthers()==null?"":ProjblsBean.get(i).getOthers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ProjblsBean.get(i).getNet_sown_area()==null?"":ProjblsBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, ProjblsBean.get(i).getGross_cropped_area()==null?"":ProjblsBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", ProjblsBean.get(i).getTotal_income()==null?BigDecimal.ZERO:ProjblsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						treatable_area=treatable_area.add(ProjblsBean.get(i).getTreatable_area()==null?BigDecimal.ZERO:ProjblsBean.get(i).getTreatable_area());
						cultivable_wasteland=cultivable_wasteland.add(ProjblsBean.get(i).getCultivable_wasteland()==null?BigDecimal.ZERO:ProjblsBean.get(i).getCultivable_wasteland());
						degraded_land=degraded_land.add(ProjblsBean.get(i).getDegraded_land()==null?BigDecimal.ZERO:ProjblsBean.get(i).getDegraded_land());
						rainfed=rainfed.add(ProjblsBean.get(i).getRainfed()==null?BigDecimal.ZERO:ProjblsBean.get(i).getRainfed());
						others=others.add(ProjblsBean.get(i).getOthers()==null?BigDecimal.ZERO:ProjblsBean.get(i).getOthers());
						net_sown_area=net_sown_area.add(ProjblsBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:ProjblsBean.get(i).getNet_sown_area());
						gross_cropped_area=gross_cropped_area.add(ProjblsBean.get(i).getGross_cropped_area()==null?BigDecimal.ZERO:ProjblsBean.get(i).getGross_cropped_area());
						total_income=total_income.add(ProjblsBean.get(i).getTotal_income()==null?BigDecimal.ZERO:ProjblsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000)));


						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(treatable_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(ProjblsBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=O3-Report(Projectwise).pdf");
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
	
	
	
	@RequestMapping(value = "/downloadExceloutrpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExceloutrpt(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String stCode=request.getParameter("stCode");
		List<BaseLineOutcomeBean> list = new ArrayList<>();

		if(stCode.length()>0) {
			list = reportService.getBlsReportStateLevelForStCode(Integer.parseInt(stCode));
		}else {
			list = reportService.getBlsReportStateLevel();
		}
		
//		if(stCode>0)
//			mav.addObject("projectList",reportService.getBlsReportStateLevelForStCode(stCode));
//			else
//			mav.addObject("projectList",reportService.getBlsReportStateLevel());
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O3- State wise Distribution of Gross Cropped Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O3- State wise Distribution of Gross Cropped Area as per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as on date";
			String areaAmtValDetail = "All area in ha. And All amounts are Rs. in Lakh.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,3,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,7,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,8,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,9,9); 
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
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Classification of Grossed Cropped Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =4; i<7;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Net Sown Area");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Grossed Cropped Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Total Income");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Cultivable Wasteland");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Rainfed");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			
			for(int i =7; i<10;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			
				Row rowhead2 = sheet.createRow(7); 
				for(int i = 0; i<10; i++) {	
				Cell cell1 = rowhead2.createCell(i);
				cell1.setCellValue(i+1);
				cell1.setCellStyle(style);
				}
	        
	        int sno = 1;
	        int rowno  = 8;
	        double totPlotArea = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totOther = 0;
	        double totNetSownArea = 0;
	        double totGrssCrpdArea = 0;
	        double totTotalIncm = 0;
	        
	        for(BaseLineOutcomeBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());  
	        	row.createCell(4).setCellValue(bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue()); 
	        	row.createCell(5).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());  
	        	row.createCell(6).setCellValue(bean.getOthers()==null?0.0:bean.getOthers().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());  
	        	row.createCell(9).setCellValue(bean.getTotal_income()==null?0.0:(double)Math.round(bean.getTotal_income().doubleValue()/100000*100)/100);
	        	
	        	totPlotArea = totPlotArea + (bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totOther = totOther + (bean.getOthers()==null?0.0:bean.getOthers().doubleValue());
	        	totNetSownArea = totNetSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
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
	        
	        Row row = sheet.createRow(list.size()+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        
	        cell = row.createCell(3);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totNetSownArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totGrssCrpdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue((double)Math.round(totTotalIncm*100)/100);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);
	        String fileName = "attachment; filename=Report O3- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/outcomeReport";
		
	}

	@RequestMapping(value = "/downloadExceloutrptdist", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExceloutrptdist(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String stateName=request.getParameter("stateName");
		String stcd=request.getParameter("stcd");
		List<BaseLineOutcomeBean> list = new ArrayList<>();
			list = reportService.getBlsReportDistrictLevel(Integer.parseInt(stcd));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O3- Distribution of Gross Cropped Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O3- Distribution of Gross Cropped Area as per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as on date for State '"+stateName+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);
			
	        mergedRegion = new CellRangeAddress(5,5,1,9);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,7,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,9,9); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stateName);  
	        cell.setCellStyle(style);
			
			cell = detail.createCell(1);
			cell.setCellValue("All area in ha. And All amounts are Rs. in Lakh.");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =2; i<10;i++) {
				detail.createCell(i).setCellStyle(style);
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
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Classification of Grossed Cropped Area");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =4; i<7;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Net Sown Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Grossed Cropped Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Total Income");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead1 = sheet.createRow(7);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Cultivable Wasteland");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Rainfed");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			
			for(int i =7; i<10;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			Row rowhead2 = sheet.createRow(8); 
			for(int i = 0; i<10; i++) {	
			Cell cell1 = rowhead2.createCell(i);
			cell1.setCellValue(i+1);
			cell1.setCellStyle(style);
			}
	        
	        int sno = 1;
	        int rowno  =9;
	        double totPlotArea = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totOther = 0;
	        double totNewSownArea = 0;
	        double totGrssCrpdArea = 0;
	        double totTotalIncm = 0;
	        
	        for(BaseLineOutcomeBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname()); 
	        	row.createCell(2).setCellValue(bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue()); 
	        	row.createCell(4).setCellValue(bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());  
	        	row.createCell(5).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getOthers()==null?0.0:bean.getOthers().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());  
	        	row.createCell(8).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getTotal_income()==null?0.0:(double)Math.round(bean.getTotal_income().doubleValue()/100000*100)/100);
	        	
	        	totPlotArea = totPlotArea + (bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totOther = totOther + (bean.getOthers()==null?0.0:bean.getOthers().doubleValue());
	        	totNewSownArea = totNewSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
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
	        
	        Row row = sheet.createRow(list.size()+9);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);    
	        cell = row.createCell(2);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totNewSownArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totGrssCrpdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue((double)Math.round(totTotalIncm*100)/100);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);
	        String fileName = "attachment; filename=Report O3- District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/outcomeDistrictReport";
		
	}
	
	@RequestMapping(value = "/downloadExcelProjectReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjectReport(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projId=request.getParameter("projId");
		List<BaseLineOutcomeBean> list = new ArrayList<BaseLineOutcomeBean>();
		
			list = reportService.getBlsReportProjectLevel(Integer.parseInt(projId));
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O3- Distribution of Gross Cropped Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O3- Distribution of Gross Cropped Area as per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as on date for District '"+distName+"' of State "+"'"+stateName+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,1,9);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,7,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,8,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,9,9); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        
	        Cell cell = detail.createCell(0);
	        cell.setCellValue("State : "+stateName +"   District : "+distName);  
	        cell.setCellStyle(style);
			
			cell = detail.createCell(1);
			cell.setCellValue("All area in ha. And All amounts are Rs. in Lakh.");  
	        cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			for(int i =2; i<10;i++) {
				detail.createCell(i).setCellStyle(style);
			}
	        
			Row rowhead = sheet.createRow(6); 
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Classification of Grossed Cropped Area");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =4; i<7;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Net Sown Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Grossed Cropped Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Total Income");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			Row rowhead1 = sheet.createRow(7);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Cultivable Wasteland");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Rainfed");
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			
			for(int i =7; i<10;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			Row rowhead2 = sheet.createRow(8); 
			for(int i = 0; i<10; i++) {	
			Cell cell1 = rowhead2.createCell(i);
			cell1.setCellValue(i+1);
			cell1.setCellStyle(style);
			}
			
	        int sno = 1;
	        int rowno  = 9;
	        double totPlotArea = 0;
	        double totCultiArea = 0;
	        double totDegrdArea = 0;
	        double totRainfdArea = 0;
	        double totOther = 0;
	        double totNewSownArea = 0;
	        double totGrssCrpdArea = 0;
	        double totTotalIncm = 0;
	        
	        for(BaseLineOutcomeBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getProjname()); 
	        	row.createCell(2).setCellValue(bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue()); 
	        	row.createCell(4).setCellValue(bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());  
	        	row.createCell(5).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getOthers()==null?0.0:bean.getOthers().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());  
	        	row.createCell(8).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue()); 
	        	row.createCell(9).setCellValue(bean.getTotal_income()==null?0.0:(double)Math.round(bean.getTotal_income().doubleValue()/100000*100)/100);
	        	
	        	totPlotArea = totPlotArea + (bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());
	        	totCultiArea = totCultiArea + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totDegrdArea = totDegrdArea + (bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue());
	        	totRainfdArea = totRainfdArea + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totOther = totOther + (bean.getOthers()==null?0.0:bean.getOthers().doubleValue());
	        	totNewSownArea = totNewSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
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
	        
	        Row row = sheet.createRow(list.size()+9);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(totPlotArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totCultiArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totDegrdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totRainfdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totOther);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totNewSownArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totGrssCrpdArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue((double)Math.round(totTotalIncm*100)/100);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);
	        String fileName = "attachment; filename=Report O3- Project.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/outcomeProjectReport";
		
	}
	
	@RequestMapping(value = "/downloadExceloutrptdetail", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExceloutrptdetail(HttpServletRequest request, HttpServletResponse response) 
	{
		String id = request.getParameter("id");
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projName=request.getParameter("projName");
		String projId=request.getParameter("projId");
		
		 List<NewBaseLineSurveyBean> list= new ArrayList<NewBaseLineSurveyBean>();
		 
		 list = reportService.getBlsReportDetailLevel(Integer.parseInt(id));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O3- Distribution of Gross Cropped Area as per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as on date for Project");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O3- Distribution of Gross Cropped Area as per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as on date for Project "+"'"+projName+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 16, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,3);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,16);
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row detail = sheet.createRow(5);
	        
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
	        
			Row rowhead = sheet.createRow(6); 
			
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
	        int rowno  = 7;
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
	        String irrStatus = "";
	        String ownership = "";
	        String classOfLand = "";
	        String noOfCrop = "";
	        String ownerName = "";
	        String cropType = "";
	        String season = "";
	        String crop = "";
	        
	        for(NewBaseLineSurveyBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	if(!plotNo.equalsIgnoreCase(bean.getPlot_no_achv())) {
	        		gdPlotArea = gdPlotArea + (bean.getArea_achv()==null?0.0:bean.getArea_achv().doubleValue());
	        	}
	        	if(!blkName.equalsIgnoreCase(bean.getBlock_name_achv())) {
	        		
	        		row.createCell(0).setCellValue(bean.getBlock_name_achv());
	        		row.createCell(1).setCellValue(bean.getGram_panchayat_name_achv()); 
	        		row.createCell(2).setCellValue(bean.getVillage_name_achv());
	        		row.createCell(3).setCellValue(bean.getPlot_no_achv());
        			row.createCell(4).setCellValue(bean.getArea_achv()==null?0.0:bean.getArea_achv().doubleValue());
        			if(!irrStatus.equalsIgnoreCase(bean.getIrrigation_achv())) {
        			row.createCell(5).setCellValue(bean.getIrrigation_achv());
        			}
        			if(!ownership.equalsIgnoreCase(bean.getOwnership_achv())) {
        			row.createCell(6).setCellValue(bean.getOwnership_achv());
        			}
        			if(!ownerName.equalsIgnoreCase(bean.getOwner_name_achv())) {
        			row.createCell(7).setCellValue(bean.getOwner_name_achv());
        			}
        			if(!classOfLand.equalsIgnoreCase(bean.getClassification_land_achv())) {
        			row.createCell(8).setCellValue(bean.getClassification_land_achv());
        			}
        			if(bean.getClassification_land_achv().equals("Forest Land")) {
        				row.createCell(9);
        				row.createCell(10).setCellValue(bean.getForest_land_achv());
        				for(int i=11; i<=16; i++) {
        					row.createCell(i);
	       		        }
        			}else {
        				if(crop != null && !crop.equalsIgnoreCase(bean.getNo_crop_achv())) {
        				row.createCell(9).setCellValue(bean.getNo_crop_achv());
        				}
        				row.createCell(10);
        				row.createCell(11).setCellValue(bean.getSeason_achv());
        				row.createCell(12).setCellValue(bean.getCrop_type_achv());
        				row.createCell(13).setCellValue(bean.getCrop_area_achv()==null?0.0:bean.getCrop_area_achv().doubleValue());
    		        	row.createCell(14).setCellValue(bean.getCrop_production_achv()==null?0.0:bean.getCrop_production_achv().doubleValue());
    		        	row.createCell(15).setCellValue(bean.getAvg_income_achv()==null?0.0:bean.getAvg_income_achv().doubleValue());

    		        	calIncome = ((bean.getCrop_area_achv()==null?BigDecimal.ZERO:bean.getCrop_area_achv()).multiply(bean.getCrop_production_achv()==null?BigDecimal.ZERO:bean.getCrop_production_achv())).multiply(bean.getAvg_income_achv()==null?BigDecimal.ZERO:bean.getAvg_income_achv());
    		        	totalIncome = calIncome.doubleValue();
    		        	row.createCell(16).setCellValue(totalIncome);
        			}
        			
        			blkName = bean.getBlock_name_achv();
        			grampytName = bean.getGram_panchayat_name_achv();
        			villName = bean.getVillage_name_achv();
        			plotNo = bean.getPlot_no_achv();
        			noOfCrop = bean.getNo_crop_achv();
        			ownerName = bean.getOwner_name_achv();
        			cropType = bean.getCrop_type_achv();
        			
        			totcolA = totcolA + (bean.getCrop_area_achv()==null?0.0:bean.getCrop_area_achv().doubleValue());
		        	totcolB = totcolB + (bean.getCrop_production_achv()==null?0.0:bean.getCrop_production_achv().doubleValue());
		        	totcolC = totcolC + (bean.getAvg_income_achv()==null?0.0:bean.getAvg_income_achv().doubleValue());
		        	totIncome = totIncome + totalIncome;
        			
        			rowno++;
        			sno++;
        			
	        	}else if(!grampytName.equalsIgnoreCase(bean.getGram_panchayat_name_achv())) {
	        		
	        		row.createCell(0);
	        		row.createCell(1).setCellValue(bean.getGram_panchayat_name_achv());
	        		row.createCell(2).setCellValue(bean.getVillage_name_achv());
	        		row.createCell(3).setCellValue(bean.getPlot_no_achv());
        			row.createCell(4).setCellValue(bean.getArea_achv()==null?0.0:bean.getArea_achv().doubleValue());
        			if(!irrStatus.equalsIgnoreCase(bean.getIrrigation_achv())) {
        			row.createCell(5).setCellValue(bean.getIrrigation_achv());
        			}
        			if(!ownership.equalsIgnoreCase(bean.getOwnership_achv())) {
        			row.createCell(6).setCellValue(bean.getOwnership_achv());
        			}
        			if(!ownerName.equalsIgnoreCase(bean.getOwner_name_achv())) {
        			row.createCell(7).setCellValue(bean.getOwner_name_achv());
        			}
        			if(!classOfLand.equalsIgnoreCase(bean.getClassification_land_achv())) {
        			row.createCell(8).setCellValue(bean.getClassification_land_achv());
        			}
        			if(bean.getClassification_land_achv().equalsIgnoreCase("Forest Land")) {
        				row.createCell(9);
        				row.createCell(10).setCellValue(bean.getForest_land_achv());
        				for(int i=11; i<=16; i++) {
        					row.createCell(i);
	       		        }
        			}else {
        				if(crop != null && !crop.equalsIgnoreCase(bean.getNo_crop_achv())) {
        				row.createCell(9).setCellValue(bean.getNo_crop_achv());
        				}
        				row.createCell(10);
        				row.createCell(11).setCellValue(bean.getSeason_achv());
        				row.createCell(12).setCellValue(bean.getCrop_type_achv());
        				row.createCell(13).setCellValue(bean.getCrop_area_achv()==null?0.0:bean.getCrop_area_achv().doubleValue());
    		        	row.createCell(14).setCellValue(bean.getCrop_production_achv()==null?0.0:bean.getCrop_production_achv().doubleValue());
    		        	row.createCell(15).setCellValue(bean.getAvg_income_achv()==null?0.0:bean.getAvg_income_achv().doubleValue());

    		        	calIncome = ((bean.getCrop_area_achv()==null?BigDecimal.ZERO:bean.getCrop_area_achv()).multiply(bean.getCrop_production_achv()==null?BigDecimal.ZERO:bean.getCrop_production_achv())).multiply(bean.getAvg_income_achv()==null?BigDecimal.ZERO:bean.getAvg_income_achv());
    		        	totalIncome = calIncome.doubleValue();
    		        	row.createCell(16).setCellValue(totalIncome);
        			}
        			
        			grampytName = bean.getGram_panchayat_name_achv();
        			villName = bean.getVillage_name_achv();
        			plotNo = bean.getPlot_no_achv();
        			noOfCrop = bean.getNo_crop_achv();
        			ownerName = bean.getOwner_name_achv();
        			cropType = bean.getCrop_type_achv();
        			
        			totcolA = totcolA + (bean.getCrop_area_achv()==null?0.0:bean.getCrop_area_achv().doubleValue());
		        	totcolB = totcolB + (bean.getCrop_production_achv()==null?0.0:bean.getCrop_production_achv().doubleValue());
		        	totcolC = totcolC + (bean.getAvg_income_achv()==null?0.0:bean.getAvg_income_achv().doubleValue());
		        	totIncome = totIncome + totalIncome;
        			
        			rowno++;
        			sno++;
	        	}else if(!villName.equalsIgnoreCase(bean.getVillage_name_achv())){
	        		row.createCell(0);
	        		row.createCell(1);
	        		row.createCell(2).setCellValue(bean.getVillage_name_achv());
	        		row.createCell(3).setCellValue(bean.getPlot_no_achv());
        			row.createCell(4).setCellValue(bean.getArea_achv()==null?0.0:bean.getArea_achv().doubleValue());
        			if(!irrStatus.equalsIgnoreCase(bean.getIrrigation_achv())) {
        			row.createCell(5).setCellValue(bean.getIrrigation_achv());
        			}
        			if(!ownership.equalsIgnoreCase(bean.getOwnership_achv())) {
        			row.createCell(6).setCellValue(bean.getOwnership_achv());
        			}
        			if(!ownerName.equalsIgnoreCase(bean.getOwner_name_achv())) {
        			row.createCell(7).setCellValue(bean.getOwner_name_achv());
        			}
        			if(!classOfLand.equalsIgnoreCase(bean.getClassification_land_achv())) {
        			row.createCell(8).setCellValue(bean.getClassification_land_achv());
        			}
        			if(bean.getClassification_land_achv().equalsIgnoreCase("Forest Land")) {
        				row.createCell(9);
        				row.createCell(10).setCellValue(bean.getForest_land_achv());
        				for(int i=11; i<=16; i++) {
        					row.createCell(i);
	       		        }
        			}else {
        				if(crop != null && !crop.equalsIgnoreCase(bean.getNo_crop_achv())) {
        				row.createCell(9).setCellValue(bean.getNo_crop_achv());
        				}
        				row.createCell(10);
        				row.createCell(11).setCellValue(bean.getSeason_achv());
        				row.createCell(12).setCellValue(bean.getCrop_type_achv());
        				row.createCell(13).setCellValue(bean.getCrop_area_achv()==null?0.0:bean.getCrop_area_achv().doubleValue());
    		        	row.createCell(14).setCellValue(bean.getCrop_production()==null?0.0:bean.getCrop_production_achv().doubleValue());
    		        	row.createCell(15).setCellValue(bean.getAvg_income_achv()==null?0.0:bean.getAvg_income_achv().doubleValue());

    		        	calIncome = ((bean.getCrop_area_achv()==null?BigDecimal.ZERO:bean.getCrop_area_achv()).multiply(bean.getCrop_production_achv()==null?BigDecimal.ZERO:bean.getCrop_production_achv())).multiply(bean.getAvg_income_achv()==null?BigDecimal.ZERO:bean.getAvg_income_achv());
    		        	totalIncome = calIncome.doubleValue();
    		        	row.createCell(16).setCellValue(totalIncome);
        			}
        			
        			villName = bean.getVillage_name_achv();
        			plotNo = bean.getPlot_no_achv();
        			noOfCrop = bean.getNo_crop_achv();
        			ownerName = bean.getOwner_name_achv();
        			cropType = bean.getCrop_type_achv();
        			
        			totcolA = totcolA + (bean.getCrop_area_achv()==null?0.0:bean.getCrop_area_achv().doubleValue());
		        	totcolB = totcolB + (bean.getCrop_production_achv()==null?0.0:bean.getCrop_production_achv().doubleValue());
		        	totcolC = totcolC + (bean.getAvg_income_achv()==null?0.0:bean.getAvg_income_achv().doubleValue());
		        	totIncome = totIncome + totalIncome;
        			
        			rowno++;
        			sno++;
	        	}else if(!plotNo.equalsIgnoreCase(bean.getPlot_no_achv())) {
	        		row.createCell(0);
	        		row.createCell(1);
	        		row.createCell(2);
	        		row.createCell(3).setCellValue(bean.getPlot_no_achv());
        			row.createCell(4).setCellValue(bean.getArea_achv()==null?0.0:bean.getArea_achv().doubleValue());
	        		if(!irrStatus.equalsIgnoreCase(bean.getIrrigation_achv())) {
        			row.createCell(5).setCellValue(bean.getIrrigation_achv());
	        		}
	        		if(!ownership.equalsIgnoreCase(bean.getOwnership_achv())) {
        			row.createCell(6).setCellValue(bean.getOwnership_achv());
	        		}
	        		if(!ownerName.equalsIgnoreCase(bean.getOwner_name_achv())) {
        			row.createCell(7).setCellValue(bean.getOwner_name_achv());
	        		}
	        		if(!classOfLand.equalsIgnoreCase(bean.getClassification_land_achv())) {
        			row.createCell(8).setCellValue(bean.getClassification_land_achv());
	        		}
        			if(bean.getClassification_land_achv().equalsIgnoreCase("Forest Land")) {
        				row.createCell(9);
        				row.createCell(10).setCellValue(bean.getForest_land_achv());
        				for(int i=11; i<=16; i++) {
        					row.createCell(i);
	       		        }
        			}else {
        				if(crop != null && crop != null && !crop.equalsIgnoreCase(bean.getNo_crop_achv())) {
        				row.createCell(9).setCellValue(bean.getNo_crop_achv());
        				}
        				row.createCell(10);
        				row.createCell(11).setCellValue(bean.getSeason_achv());
        				row.createCell(12).setCellValue(bean.getCrop_type_achv());
        				row.createCell(13).setCellValue(bean.getCrop_area_achv()==null?0.0:bean.getCrop_area_achv().doubleValue());
    		        	row.createCell(14).setCellValue(bean.getCrop_production_achv()==null?0.0:bean.getCrop_production_achv().doubleValue());
    		        	row.createCell(15).setCellValue(bean.getAvg_income_achv()==null?0.0:bean.getAvg_income_achv().doubleValue());

    		        	calIncome = ((bean.getCrop_area_achv()==null?BigDecimal.ZERO:bean.getCrop_area_achv()).multiply(bean.getCrop_production_achv()==null?BigDecimal.ZERO:bean.getCrop_production_achv())).multiply(bean.getAvg_income_achv()==null?BigDecimal.ZERO:bean.getAvg_income_achv());
    		        	totalIncome = calIncome.doubleValue();
    		        	row.createCell(16).setCellValue(totalIncome);
        			}
        			
        			plotNo = bean.getPlot_no_achv();
        			noOfCrop = bean.getNo_crop_achv();
        			ownerName = bean.getOwner_name_achv();
        			cropType = bean.getCrop_type_achv();
        			season = bean.getSeason_achv();
        			
        			totcolA = totcolA + (bean.getCrop_area_achv()==null?0.0:bean.getCrop_area_achv().doubleValue());
		        	totcolB = totcolB + (bean.getCrop_production_achv()==null?0.0:bean.getCrop_production_achv().doubleValue());
		        	totcolC = totcolC + (bean.getAvg_income_achv()==null?0.0:bean.getAvg_income_achv().doubleValue());
		        	totIncome = totIncome + totalIncome;
		        	
        			rowno++;
        			sno++;
	        	}else {
	        		for(int i=0; i<=10; i++) {
    					row.createCell(i);
       		        }
    				if (season != null && !season.equalsIgnoreCase(bean.getSeason_achv())) 
    					row.createCell(11).setCellValue(bean.getSeason_achv());
    				else
    					row.createCell(11);
    				
    				row.createCell(12).setCellValue(bean.getCrop_type_achv());
    				row.createCell(13).setCellValue(bean.getCrop_area_achv()==null?0.0:bean.getCrop_area_achv().doubleValue());
		        	row.createCell(14).setCellValue(bean.getCrop_production_achv()==null?0.0:bean.getCrop_production_achv().doubleValue());
		        	row.createCell(15).setCellValue(bean.getAvg_income_achv()==null?0.0:bean.getAvg_income_achv().doubleValue());

		        	calIncome = ((bean.getCrop_area_achv()==null?BigDecimal.ZERO:bean.getCrop_area_achv()).multiply(bean.getCrop_production_achv()==null?BigDecimal.ZERO:bean.getCrop_production_achv())).multiply(bean.getAvg_income_achv()==null?BigDecimal.ZERO:bean.getAvg_income_achv());
		        	totalIncome = calIncome.doubleValue();
		        	row.createCell(16).setCellValue(totalIncome);
		        	
		        	totcolA = totcolA + (bean.getCrop_area_achv()==null?0.0:bean.getCrop_area_achv().doubleValue());
		        	totcolB = totcolB + (bean.getCrop_production_achv()==null?0.0:bean.getCrop_production_achv().doubleValue());
		        	totcolC = totcolC + (bean.getAvg_income_achv()==null?0.0:bean.getAvg_income_achv().doubleValue());
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
	        	
	        	irrStatus = bean.getIrrigation_achv();
	        	ownership = bean.getOwnership_achv();
	        	ownerName = bean.getOwner_name_achv();
	        	classOfLand = bean.getClassification_land_achv();
	        	crop = bean.getNo_crop_achv();
	        	
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
	        
			mergedRegion = new CellRangeAddress(rowno+1,rowno+1,0,3); 
	        sheet.addMergedRegion(mergedRegion);
			
	        Row row1 = sheet.createRow(rowno+1);
	        cell = row1.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        for(int i=1; i<=3; i++) {
		        	cell = row1.createCell(i);
	   		        cell.setCellStyle(style1);
		        }
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
	        cell.setCellValue(gdcolC);
	        cell.setCellStyle(style1);
	        cell = row1.createCell(16);
	        cell.setCellValue(gdTotalIncome);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowno, 16);
	        String fileName = "attachment; filename=Report O3- Project Detail.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/outcomeDetailReport";
		
	}
	
}
