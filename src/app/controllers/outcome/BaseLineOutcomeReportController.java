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
//import javax.websocket.server.PathParam;

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
import app.bean.Login;
import app.bean.NewBaseLineSurveyBean;
import app.bean.reports.SelfHelpGroupReportBean;
import app.common.CommonFunctions;
import app.service.DistrictMasterService;
import app.service.ProjectMasterService;
import app.service.StateMasterService;
import app.service.outcome.BaseLineOutcomeReportService;
import app.service.outcome.BaseLineOutcomeService;
import app.service.outcome.BaseLineReportService;

@Controller("BaseLineOutcomeReport")
public class BaseLineOutcomeReportController {
	
	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	BaseLineOutcomeReportService reportService;
	
	@Autowired
	BaseLineReportService rptService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@RequestMapping(value = "/blsoutrpt", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurvey(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/baseLineOutcomeReport");
		mav.addObject("projectList",reportService.getBlsOutReportStateLevel());
		mav.addObject("stateList",stateMasterService.getAllState());	
		return mav;
	}
	
	@RequestMapping(value = "/blsoutrpt", method = RequestMethod.POST)
	public ModelAndView getBaseLineSurveyPost(HttpServletRequest request,@RequestParam("state") int stCode) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/baselineoutcome/baseLineOutcomeReport");
		if(stCode>0)
		mav.addObject("projectList",reportService.getBlsOutReportStateLevelForStCode(stCode));
		else
		mav.addObject("projectList",reportService.getBlsOutReportStateLevel());
		mav.addObject("stateList",stateMasterService.getAllState());	
		mav.addObject("stCode",stCode);	
		return mav;
	}
	
	
	@RequestMapping(value = "/blsoutrptdist", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveyReoprtDistrict(HttpServletRequest request,@RequestParam("id") int id) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/baseLineOutcomeDistrictReport");
		 mav.addObject("projectList",reportService.getBlsOutReportDistrictLevel(id));
		 mav.addObject("stateName",stateMasterService.getStateByStateCode(id).get(id));	
		 mav.addObject("stcode",id);
			//System.out.println(id+"a"+stateMasterService.getStateByStateCode(id).get(id)+"b");
		//System.out.println(id);
			
		return mav;
	}
	
	@RequestMapping(value = "/blsoutrptproj", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveyReoprtProject(HttpServletRequest request,@RequestParam("id") int id) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/baseLineOutcomeProjectReport");
		 mav.addObject("projectList",reportService.getBlsOutReportProjectLevel(id));
		 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(id).entrySet().iterator().next();
		 mav.addObject("stateName",stateMasterService.getStateByDistCode(id).get(entry.getKey()));	
		 mav.addObject("distName",districtMasterService.getDistrictByDistCode(id).get(id));	
		 mav.addObject("projId",id);
		//System.out.println(id);
			
		return mav;
	}
	
	@RequestMapping(value = "/blsoutrptdetail", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurveyReoprtDetail(HttpServletRequest request,@RequestParam("id") int id) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		 mav = new ModelAndView("reports/baselineoutcome/baseLineOutcomeDetailReport");
		// mav.addObject("projectList",reportService.getBlsOutReportDetailLevel(id));
		 List<NewBaseLineSurveyBean> list1= new ArrayList<NewBaseLineSurveyBean>();
		 List<NewBaseLineSurveyBean> list2= new ArrayList<NewBaseLineSurveyBean>();
		 List<NewBaseLineSurveyBean> sublist= new ArrayList<NewBaseLineSurveyBean>();
		 List<NewBaseLineSurveyBean> sublist2= new ArrayList<NewBaseLineSurveyBean>();
		 LinkedHashMap<String,List<NewBaseLineSurveyBean>> blsmap = new LinkedHashMap<String,List<NewBaseLineSurveyBean>>();
		 LinkedHashMap<String,List<NewBaseLineSurveyBean>> outmap = new LinkedHashMap<String,List<NewBaseLineSurveyBean>>();
		 LinkedHashMap<String,List<NewBaseLineSurveyBean>> map = new LinkedHashMap<String,List<NewBaseLineSurveyBean>>();
		 
		    list2 = reportService.getBlsOutReportDetailLevel(id);
		    list1 = rptService.getBlsReportDetailLevel(id);
		    
			int i = 0;
			String data[] = null;
			ArrayList dataList = new ArrayList();
			String plotno=null;
			for (NewBaseLineSurveyBean bean : list1) 
			{
				if(!blsmap.containsKey(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name())) {
					sublist= new ArrayList<NewBaseLineSurveyBean>();
					//sublist2= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					blsmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name(), sublist);
				}else {
					sublist=blsmap.get(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name());
					//sublist= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					blsmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name(), sublist);
				}
				
			}
			
			for (NewBaseLineSurveyBean bean : list2) 
			{
				if(!outmap.containsKey(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv())) {
					sublist= new ArrayList<NewBaseLineSurveyBean>();
					//sublist2= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					outmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv(), sublist);
				}else {
					
					sublist=outmap.get(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv());
					//sublist= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					outmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv(), sublist);
				}
				
			}
			
			 for (Map.Entry<String,List<NewBaseLineSurveyBean>> entry : blsmap.entrySet()) {
					/*
					 * System.out.println("Key = " + entry.getKey() + ", Value = " +
					 * entry.getValue());
					 */
		            sublist2= entry.getValue();
		            sublist2.addAll(outmap.get(entry.getKey()));
		            map.put(entry.getKey(), sublist2);
		            
		            
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
		
	

	@RequestMapping(value = "/downloadblsPDF", method = RequestMethod.POST)
	public ModelAndView downloadblsPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String state=request.getParameter("state");
		
		List<BaseLineOutcomeBean> listblsBean = new ArrayList<BaseLineOutcomeBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineOutcomeReport");
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
			
				paragraph3 = new Paragraph("Report O4- State wise Comparison of Distribution of Gross Cropped Area as Per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status ", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(17);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal treatable_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland_achv= BigDecimal.valueOf(0);
				BigDecimal degraded_land= BigDecimal.valueOf(0);
				BigDecimal degraded_land_achv= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal rainfed_achv= BigDecimal.valueOf(0);
				BigDecimal others= BigDecimal.valueOf(0);
				BigDecimal others_achv= BigDecimal.valueOf(0);
				BigDecimal net_sown_area= BigDecimal.valueOf(0);
				BigDecimal net_sown_area_ach= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area_ach= BigDecimal.valueOf(0);
				BigDecimal total_income= BigDecimal.valueOf(0);
				BigDecimal total_income_ach= BigDecimal.valueOf(0);
				
			if(Integer.parseInt(state)>0)
				listblsBean=reportService.getBlsOutReportStateLevelForStCode(Integer.parseInt(state));
				else
					listblsBean=reportService.getBlsOutReportStateLevel();
				
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 17, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Cropped Area)", Element.ALIGN_CENTER, 8, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Net Sown Area", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Grossed Cropped Area", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Income", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultivable Wasteland", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed", Element.ALIGN_CENTER, 2, 1,bf8Bold);
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
				
				int k=1;
				if(listblsBean.size()!=0)
					for(int i=0;i<listblsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getTreatable_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getCultivable_wasteland_achv() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getDegraded_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getDegraded_land_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getRainfed() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getRainfed_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getOthers() .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, listblsBean.get(i).getOthers_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getNet_sown_area()  .toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getNet_sown_area_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listblsBean.get(i).getGross_cropped_area_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", listblsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", listblsBean.get(i).getTotal_income_achv().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						treatable_area=treatable_area.add(listblsBean.get(i).getTreatable_area());
						cultivable_wasteland=cultivable_wasteland.add(listblsBean.get(i).getCultivable_wasteland());
						cultivable_wasteland_achv=cultivable_wasteland_achv.add(listblsBean.get(i).getCultivable_wasteland_achv());
						degraded_land=degraded_land.add(listblsBean.get(i).getDegraded_land());
						degraded_land_achv=degraded_land_achv.add(listblsBean.get(i).getDegraded_land_achv());
						rainfed=rainfed.add(listblsBean.get(i).getRainfed());
						rainfed_achv=rainfed_achv.add(listblsBean.get(i).getRainfed_achv());
						others=others.add(listblsBean.get(i).getOthers());
						
						others_achv=others_achv.add(listblsBean.get(i).getOthers_achv());
						net_sown_area=net_sown_area.add(listblsBean.get(i).getNet_sown_area());
						net_sown_area_ach=net_sown_area_ach.add(listblsBean.get(i).getNet_sown_area_achv());
						gross_cropped_area=gross_cropped_area.add(listblsBean.get(i).getGross_cropped_area());
						gross_cropped_area_ach=gross_cropped_area_ach.add(listblsBean.get(i).getGross_cropped_area_achv());
						total_income=total_income.add(listblsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000)));

						total_income_ach=total_income_ach.add(listblsBean.get(i).getTotal_income_achv().divide(BigDecimal.valueOf(100000)));
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(treatable_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(others_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listblsBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=O4-Report.pdf");
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
	
	
	@RequestMapping(value = "/downloadDistrictblsPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictblsPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String stateName=request.getParameter("stateName");
		String stcd=request.getParameter("stcd");
		List<BaseLineOutcomeBean> listDistblsBean = new ArrayList<BaseLineOutcomeBean>();
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineOutcomeDistReport");
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
			
				paragraph3 = new Paragraph("Report O4- Details of Comparison of Distribution of Gross Cropped Area as Per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status  for State '"+stateName+"'", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(17);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal treatable_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland_achv= BigDecimal.valueOf(0);
				BigDecimal degraded_land= BigDecimal.valueOf(0);
				BigDecimal degraded_land_achv= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal rainfed_achv= BigDecimal.valueOf(0);
				BigDecimal others= BigDecimal.valueOf(0);
				BigDecimal others_achv= BigDecimal.valueOf(0);
				BigDecimal net_sown_area= BigDecimal.valueOf(0);
				BigDecimal net_sown_area_ach= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area_ach= BigDecimal.valueOf(0);
				BigDecimal total_income= BigDecimal.valueOf(0);
				BigDecimal total_income_ach= BigDecimal.valueOf(0);
				
				listDistblsBean=reportService.getBlsOutReportDistrictLevel(Integer.parseInt(stcd));
				
				
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 17, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Cropped Area)", Element.ALIGN_CENTER, 8, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Net Sown Area", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Grossed Cropped Area", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Income", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultivable Wasteland.", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed", Element.ALIGN_CENTER, 2, 1,bf8Bold);
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
				
				int k=1;
				if(listDistblsBean.size()!=0)
					for(int i=0;i<listDistblsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getTreatable_area()==null?"":listDistblsBean.get(i).getTreatable_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getCultivable_wasteland()==null?"":listDistblsBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getCultivable_wasteland_achv()==null?"":listDistblsBean.get(i).getCultivable_wasteland_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getDegraded_land()==null?"":listDistblsBean.get(i).getDegraded_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getDegraded_land_achv()==null?"":listDistblsBean.get(i).getDegraded_land_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getRainfed()==null?"":listDistblsBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getRainfed_achv()==null?"":listDistblsBean.get(i).getRainfed_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getOthers()==null?"":listDistblsBean.get(i).getOthers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getOthers_achv()==null?"":listDistblsBean.get(i).getOthers_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getNet_sown_area()==null?"":listDistblsBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getNet_sown_area_achv()==null?"":listDistblsBean.get(i).getNet_sown_area_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getGross_cropped_area()==null?"":listDistblsBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listDistblsBean.get(i).getGross_cropped_area_achv()==null?"":listDistblsBean.get(i).getGross_cropped_area_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", listDistblsBean.get(i).getTotal_income()==null?BigDecimal.ZERO:listDistblsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", listDistblsBean.get(i).getTotal_income_achv()==null?BigDecimal.ZERO:listDistblsBean.get(i).getTotal_income_achv().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						k=k+1;
						treatable_area=treatable_area.add(listDistblsBean.get(i).getTreatable_area()==null?BigDecimal.ZERO:listDistblsBean.get(i).getTreatable_area());
						cultivable_wasteland=cultivable_wasteland.add(listDistblsBean.get(i).getCultivable_wasteland()==null?BigDecimal.ZERO:listDistblsBean.get(i).getCultivable_wasteland());
						cultivable_wasteland_achv=cultivable_wasteland_achv.add(listDistblsBean.get(i).getCultivable_wasteland_achv()==null?BigDecimal.ZERO:listDistblsBean.get(i).getCultivable_wasteland_achv());
						degraded_land=degraded_land.add(listDistblsBean.get(i).getDegraded_land()==null?BigDecimal.ZERO:listDistblsBean.get(i).getDegraded_land());
						degraded_land_achv=degraded_land_achv.add(listDistblsBean.get(i).getDegraded_land_achv()==null?BigDecimal.ZERO:listDistblsBean.get(i).getDegraded_land_achv());
						rainfed=rainfed.add(listDistblsBean.get(i).getRainfed()==null?BigDecimal.ZERO:listDistblsBean.get(i).getRainfed());
						rainfed_achv=rainfed_achv.add(listDistblsBean.get(i).getRainfed_achv()==null?BigDecimal.ZERO:listDistblsBean.get(i).getRainfed_achv());
						others=others.add(listDistblsBean.get(i).getOthers()==null?BigDecimal.ZERO:listDistblsBean.get(i).getOthers());
						others_achv=others_achv.add(listDistblsBean.get(i).getOthers_achv()==null?BigDecimal.ZERO:listDistblsBean.get(i).getOthers_achv());
						net_sown_area=net_sown_area.add(listDistblsBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:listDistblsBean.get(i).getNet_sown_area());
						net_sown_area_ach=net_sown_area_ach.add(listDistblsBean.get(i).getNet_sown_area_achv()==null?BigDecimal.ZERO:listDistblsBean.get(i).getNet_sown_area_achv());
						gross_cropped_area=gross_cropped_area.add(listDistblsBean.get(i).getGross_cropped_area()==null?BigDecimal.ZERO:listDistblsBean.get(i).getGross_cropped_area());
						gross_cropped_area_ach=gross_cropped_area_ach.add(listDistblsBean.get(i).getGross_cropped_area_achv()==null?BigDecimal.ZERO:listDistblsBean.get(i).getGross_cropped_area_achv());
						total_income=total_income.add(listDistblsBean.get(i).getTotal_income()==null?BigDecimal.ZERO:listDistblsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000)));
						total_income=total_income.add(listDistblsBean.get(i).getTotal_income()==null?BigDecimal.ZERO:listDistblsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000)));
						total_income_ach=total_income_ach.add(listDistblsBean.get(i).getTotal_income_achv()==null?BigDecimal.ZERO:listDistblsBean.get(i).getTotal_income_achv().divide(BigDecimal.valueOf(100000)));
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(treatable_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(others_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listDistblsBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=O4-DistrictReport.pdf");
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
	@RequestMapping(value = "/downloadProjectblsPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectblsPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projId=request.getParameter("projId");
		List<BaseLineOutcomeBean> listprojblsBean = new ArrayList<BaseLineOutcomeBean>();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineOutcomeProjectReport");
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
			
				paragraph3 = new Paragraph("Report O4- Details of Comparison of Distribution of Gross Cropped Area as Per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status for District '"+distName+"' of State '"+stateName + "'", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(17);
				table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(5);
				
				BigDecimal treatable_area= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland= BigDecimal.valueOf(0);
				BigDecimal cultivable_wasteland_achv= BigDecimal.valueOf(0);
				BigDecimal degraded_land= BigDecimal.valueOf(0);
				BigDecimal degraded_land_achv= BigDecimal.valueOf(0);
				BigDecimal rainfed= BigDecimal.valueOf(0);
				BigDecimal rainfed_achv= BigDecimal.valueOf(0);
				BigDecimal others= BigDecimal.valueOf(0);
				BigDecimal others_achv= BigDecimal.valueOf(0);
				BigDecimal net_sown_area= BigDecimal.valueOf(0);
				BigDecimal net_sown_area_ach= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area= BigDecimal.valueOf(0);
				BigDecimal gross_cropped_area_ach= BigDecimal.valueOf(0);
				BigDecimal total_income= BigDecimal.valueOf(0);
				BigDecimal total_income_ach= BigDecimal.valueOf(0);
				
				listprojblsBean=reportService.getBlsOutReportProjectLevel(Integer.parseInt(projId));
				
				
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 17, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Plot Area ", Element.ALIGN_CENTER, 1, 3, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Classification of Land(Cropped Area)", Element.ALIGN_CENTER, 8, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Net Sown Area", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Grossed Cropped Area", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Income", Element.ALIGN_CENTER, 2, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Cultivable Wasteland.", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Degraded Land", Element.ALIGN_CENTER, 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Rainfed", Element.ALIGN_CENTER, 2, 1,bf8Bold);
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
				
				int k=1;
				if(listprojblsBean.size()!=0)
					for(int i=0;i<listprojblsBean.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getTreatable_area()==null?"":listprojblsBean.get(i).getTreatable_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getCultivable_wasteland()==null?"":listprojblsBean.get(i).getCultivable_wasteland().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getCultivable_wasteland_achv()==null?"":listprojblsBean.get(i).getCultivable_wasteland_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getDegraded_land()==null?"":listprojblsBean.get(i).getDegraded_land().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getDegraded_land_achv()==null?"":listprojblsBean.get(i).getDegraded_land_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getRainfed()==null?"":listprojblsBean.get(i).getRainfed().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getRainfed_achv()==null?"":listprojblsBean.get(i).getRainfed_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getOthers()==null?"":listprojblsBean.get(i).getOthers().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getOthers_achv()==null?"":listprojblsBean.get(i).getOthers_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getNet_sown_area()==null?"":listprojblsBean.get(i).getNet_sown_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getNet_sown_area_achv()==null?"":listprojblsBean.get(i).getNet_sown_area_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getGross_cropped_area()==null?"":listprojblsBean.get(i).getGross_cropped_area().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listprojblsBean.get(i).getGross_cropped_area_achv()==null?"":listprojblsBean.get(i).getGross_cropped_area_achv().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", listprojblsBean.get(i).getTotal_income()==null?"":listprojblsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.format(Locale.US, "%1$.2f", listprojblsBean.get(i).getTotal_income_achv()==null?"":listprojblsBean.get(i).getTotal_income_achv().divide(BigDecimal.valueOf(100000))), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						k=k+1;
						treatable_area=treatable_area.add(listprojblsBean.get(i).getTreatable_area()==null?BigDecimal.ZERO:listprojblsBean.get(i).getTreatable_area());
						cultivable_wasteland=cultivable_wasteland.add(listprojblsBean.get(i).getCultivable_wasteland()==null?BigDecimal.ZERO:listprojblsBean.get(i).getCultivable_wasteland());
						cultivable_wasteland_achv=cultivable_wasteland_achv.add(listprojblsBean.get(i).getCultivable_wasteland_achv()==null?BigDecimal.ZERO:listprojblsBean.get(i).getCultivable_wasteland_achv());
						degraded_land=degraded_land.add(listprojblsBean.get(i).getDegraded_land()==null?BigDecimal.ZERO:listprojblsBean.get(i).getDegraded_land());
						degraded_land_achv=degraded_land_achv.add(listprojblsBean.get(i).getDegraded_land_achv()==null?BigDecimal.ZERO:listprojblsBean.get(i).getDegraded_land_achv());
						rainfed=rainfed.add(listprojblsBean.get(i).getRainfed()==null?BigDecimal.ZERO:listprojblsBean.get(i).getRainfed());
						rainfed_achv=rainfed_achv.add(listprojblsBean.get(i).getRainfed_achv()==null?BigDecimal.ZERO:listprojblsBean.get(i).getRainfed_achv());
						others=others.add(listprojblsBean.get(i).getOthers()==null?BigDecimal.ZERO:listprojblsBean.get(i).getOthers());
						others_achv=others_achv.add(listprojblsBean.get(i).getOthers_achv()==null?BigDecimal.ZERO:listprojblsBean.get(i).getOthers_achv());
						net_sown_area=net_sown_area.add(listprojblsBean.get(i).getNet_sown_area()==null?BigDecimal.ZERO:listprojblsBean.get(i).getNet_sown_area());
						net_sown_area_ach=net_sown_area_ach.add(listprojblsBean.get(i).getNet_sown_area_achv()==null?BigDecimal.ZERO:listprojblsBean.get(i).getNet_sown_area_achv());
						gross_cropped_area=gross_cropped_area.add(listprojblsBean.get(i).getGross_cropped_area()==null?BigDecimal.ZERO:listprojblsBean.get(i).getGross_cropped_area());
						gross_cropped_area_ach=gross_cropped_area_ach.add(listprojblsBean.get(i).getGross_cropped_area_achv()==null?BigDecimal.ZERO:listprojblsBean.get(i).getGross_cropped_area_achv());
						total_income=total_income.add(listprojblsBean.get(i).getTotal_income()==null?BigDecimal.ZERO:listprojblsBean.get(i).getTotal_income().divide(BigDecimal.valueOf(100000)));
						total_income_ach=total_income_ach.add(listprojblsBean.get(i).getTotal_income_achv()==null?BigDecimal.ZERO:listprojblsBean.get(i).getTotal_income_achv().divide(BigDecimal.valueOf(100000)));
						
					}
					CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(treatable_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(cultivable_wasteland_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(degraded_land_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(rainfed_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(others), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					CommonFunctions.insertCell3(table, String.valueOf(others_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(net_sown_area_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(gross_cropped_area_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.format(Locale.US, "%1$.2f",total_income_ach), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					
					
					if(listprojblsBean.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=O4-ProjectReport.pdf");
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

	@RequestMapping(value = "/downloadExcelblsoutrpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelblsoutrpt(HttpServletRequest request, HttpServletResponse response) 
	{
		String state=request.getParameter("state");
		List<BaseLineOutcomeBean> list = new ArrayList<BaseLineOutcomeBean>();
		if(Integer.parseInt(state)>0) 
			list=reportService.getBlsOutReportStateLevelForStCode(Integer.parseInt(state));
			else 
				list=reportService.getBlsOutReportStateLevel();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O4- State wise Comparison of Distribution of Gross Cropped Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O4- State wise Comparison of Distribution of Gross Cropped Area as Per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status";
			String areaAmtValDetail = "All area in ha. And All amounts are Rs. in Lakh";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 16, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,3,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,11,12); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,13,14); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,15,16); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,5,6);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,7,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,9,10);
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
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Classification of Land(Cropped Area)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =4; i<11;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Net Sown Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead.createCell(12).setCellStyle(style);
			
			cell = rowhead.createCell(13);
			cell.setCellValue("Grossed Cropped Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead.createCell(14).setCellStyle(style);
			
			cell = rowhead.createCell(15);
			cell.setCellValue("Total Income");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead.createCell(16).setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Cultivable Wasteland");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(4).setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(6).setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Rainfed");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(8).setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(10).setCellStyle(style);
			
			for(int i =11; i<17;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}

			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<3;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(3);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(4);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(5);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(6);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(7);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(8);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(9);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(10);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(11);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(13);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(14);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(16);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			Row rowhead3 = sheet.createRow(8);
			for(int i =0; i<17;i++) {
				cell =rowhead3.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  =9;
	        double totPlotArea = 0;
	        double totCultWasteLand = 0;
	        double totCultWasteLandAchv = 0;
	        double totDegradLand = 0;
	        double totDegradLandAchv = 0;
	        double totRainfed = 0;
	        double totRainfedAchv = 0;
	        double totOthers = 0;
	        double totOthersAchv = 0;
	        double totNetSownArea = 0;
	        double totNetSownAreaAchv = 0;
	        double totGrossCrpArea = 0;
	        double totGrossCrpAreaAchv = 0;
	        double totIncome = 0;
	        double totIncomeAchv = 0;
	        
	        for(BaseLineOutcomeBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getStname());
	        	row.createCell(2).setCellValue(bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue()); 
	        	row.createCell(4).setCellValue(bean.getCultivable_wasteland_achv()==null?0.0:bean.getCultivable_wasteland_achv().doubleValue());  
	        	row.createCell(5).setCellValue(bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getDegraded_land_achv()==null?0.0:bean.getDegraded_land_achv().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getRainfed_achv()==null?0.0:bean.getRainfed_achv().doubleValue());  
	        	row.createCell(9).setCellValue(bean.getOthers()==null?0.0:bean.getOthers().doubleValue()); 
	        	row.createCell(10).setCellValue(bean.getOthers_achv()==null?0.0:bean.getOthers_achv().doubleValue());
	        	row.createCell(11).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());  
	        	row.createCell(12).setCellValue(bean.getNet_sown_area_achv()==null?0.0:bean.getNet_sown_area_achv().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue()); 
	        	row.createCell(14).setCellValue(bean.getGross_cropped_area_achv()==null?0.0:bean.getGross_cropped_area_achv().doubleValue());  
	        	row.createCell(15).setCellValue(bean.getTotal_income()==null?0.0:bean.getTotal_income().doubleValue()/100000); 
	        	row.createCell(16).setCellValue(bean.getTotal_income_achv()==null?0.0:bean.getTotal_income_achv().doubleValue()/100000); 

	        	
	        	totPlotArea = totPlotArea + (bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());
	        	totCultWasteLand = totCultWasteLand + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totCultWasteLandAchv = totCultWasteLandAchv + (bean.getCultivable_wasteland_achv()==null?0.0:bean.getCultivable_wasteland_achv().doubleValue());
	        	totDegradLand = totDegradLand + (bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue()); 
	        	totDegradLandAchv = totDegradLandAchv + (bean.getDegraded_land_achv()==null?0.0:bean.getDegraded_land_achv().doubleValue()); 
	        	totRainfed = totRainfed + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totRainfedAchv = totRainfedAchv + (bean.getRainfed_achv()==null?0.0:bean.getRainfed_achv().doubleValue());
	        	totOthers = totOthers + (bean.getOthers()==null?0.0:bean.getOthers().doubleValue());
	        	totOthersAchv = totOthersAchv + (bean.getOthers_achv()==null?0.0:bean.getOthers_achv().doubleValue());
	        	totNetSownArea = totNetSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
	        	totNetSownAreaAchv = totNetSownAreaAchv + (bean.getNet_sown_area_achv()==null?0.0:bean.getNet_sown_area_achv().doubleValue());
	        	totGrossCrpArea = totGrossCrpArea + (bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
	        	totGrossCrpAreaAchv = totGrossCrpAreaAchv + (bean.getGross_cropped_area_achv()==null?0.0:bean.getGross_cropped_area_achv().doubleValue());
	        	totIncome = totIncome + (bean.getTotal_income()==null?0.0:bean.getTotal_income().doubleValue()/100000);
	        	totIncomeAchv = totIncomeAchv + (bean.getTotal_income_achv()==null?0.0:bean.getTotal_income_achv().doubleValue()/100000);

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
	        cell.setCellValue(totCultWasteLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCultWasteLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totDegradLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totDegradLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totRainfed);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totRainfedAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOthers);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totOthersAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totNetSownArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totNetSownAreaAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totGrossCrpArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totGrossCrpAreaAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totIncome); 
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totIncomeAchv);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 16);
	        String fileName = "attachment; filename=Report O4- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/baseLineOutcomeReport";
		
	}

	@RequestMapping(value = "/downloadExcelblsoutrptdist", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelblsoutrptdist(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String stcd=request.getParameter("stcd");
		List<BaseLineOutcomeBean> list = new ArrayList<BaseLineOutcomeBean>();
		list=reportService.getBlsOutReportDistrictLevel(Integer.parseInt(stcd));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O4- Details of Comparison of Distribution of Gross Cropped Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O4- Details of Comparison of Distribution of Gross Cropped Area as Per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status for State '" + stateName+"'";
			String areaAmtValDetail = "All area in ha. And All amounts are Rs. in Lakh";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 16, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,3,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,11,12); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,13,14); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,15,16); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,5,6);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,7,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,9,10);
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
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
			cell.setCellValue("Plot Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Classification of Grossed Cropped Area");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =4; i<11;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Net Sown Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead.createCell(12).setCellStyle(style);
			
			cell = rowhead.createCell(13);
			cell.setCellValue("Grossed Cropped Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead.createCell(14).setCellStyle(style);
			
			cell = rowhead.createCell(15);
			cell.setCellValue("Total Income");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead.createCell(16).setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Cultivable Wasteland");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(4).setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(6).setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Rainfed");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(8).setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(10).setCellStyle(style);
			
			for(int i =11; i<17;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}

			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<3;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(3);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(4);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(5);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(6);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(7);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(8);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(9);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(10);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(11);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(13);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(14);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(16);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			Row rowhead3 = sheet.createRow(8);
			for(int i =0; i<17;i++) {
				cell =rowhead3.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  =9;
	        double totPlotArea = 0;
	        double totCultWasteLand = 0;
	        double totCultWasteLandAchv = 0;
	        double totDegradLand = 0;
	        double totDegradLandAchv = 0;
	        double totRainfed = 0;
	        double totRainfedAchv = 0;
	        double totOthers = 0;
	        double totOthersAchv = 0;
	        double totNetSownArea = 0;
	        double totNetSownAreaAchv = 0;
	        double totGrossCrpArea = 0;
	        double totGrossCrpAreaAchv = 0;
	        double totIncome = 0;
	        double totIncomeAchv = 0;
	        
	        for(BaseLineOutcomeBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDistname());
	        	row.createCell(2).setCellValue(bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue()); 
	        	row.createCell(4).setCellValue(bean.getCultivable_wasteland_achv()==null?0.0:bean.getCultivable_wasteland_achv().doubleValue());  
	        	row.createCell(5).setCellValue(bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getDegraded_land_achv()==null?0.0:bean.getDegraded_land_achv().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getRainfed_achv()==null?0.0:bean.getRainfed_achv().doubleValue());  
	        	row.createCell(9).setCellValue(bean.getOthers()==null?0.0:bean.getOthers().doubleValue()); 
	        	row.createCell(10).setCellValue(bean.getOthers_achv()==null?0.0:bean.getOthers_achv().doubleValue());
	        	row.createCell(11).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());  
	        	row.createCell(12).setCellValue(bean.getNet_sown_area_achv()==null?0.0:bean.getNet_sown_area_achv().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue()); 
	        	row.createCell(14).setCellValue(bean.getGross_cropped_area_achv()==null?0.0:bean.getGross_cropped_area_achv().doubleValue());  
	        	row.createCell(15).setCellValue(bean.getTotal_income()==null?0.0:bean.getTotal_income().doubleValue()/100000); 
	        	row.createCell(16).setCellValue(bean.getTotal_income_achv()==null?0.0:bean.getTotal_income_achv().doubleValue()/100000); 

	        	
	        	totPlotArea = totPlotArea + (bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());
	        	totCultWasteLand = totCultWasteLand + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totCultWasteLandAchv = totCultWasteLandAchv + (bean.getCultivable_wasteland_achv()==null?0.0:bean.getCultivable_wasteland_achv().doubleValue());
	        	totDegradLand = totDegradLand + (bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue()); 
	        	totDegradLandAchv = totDegradLandAchv + (bean.getDegraded_land_achv()==null?0.0:bean.getDegraded_land_achv().doubleValue()); 
	        	totRainfed = totRainfed + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totRainfedAchv = totRainfedAchv + (bean.getRainfed_achv()==null?0.0:bean.getRainfed_achv().doubleValue());
	        	totOthers = totOthers + (bean.getOthers()==null?0.0:bean.getOthers().doubleValue());
	        	totOthersAchv = totOthersAchv + (bean.getOthers_achv()==null?0.0:bean.getOthers_achv().doubleValue());
	        	totNetSownArea = totNetSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
	        	totNetSownAreaAchv = totNetSownAreaAchv + (bean.getNet_sown_area_achv()==null?0.0:bean.getNet_sown_area_achv().doubleValue());
	        	totGrossCrpArea = totGrossCrpArea + (bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
	        	totGrossCrpAreaAchv = totGrossCrpAreaAchv + (bean.getGross_cropped_area_achv()==null?0.0:bean.getGross_cropped_area_achv().doubleValue());
	        	totIncome = totIncome + (bean.getTotal_income()==null?0.0:bean.getTotal_income().doubleValue()/100000);
	        	totIncomeAchv = totIncomeAchv + (bean.getTotal_income_achv()==null?0.0:bean.getTotal_income_achv().doubleValue()/100000);

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
	        cell.setCellValue(totCultWasteLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCultWasteLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totDegradLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totDegradLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totRainfed);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totRainfedAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOthers);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totOthersAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totNetSownArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totNetSownAreaAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totGrossCrpArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totGrossCrpAreaAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totIncome); 
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totIncomeAchv);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 16);
	        String fileName = "attachment; filename=Report O4- District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/baseLineOutcomeDistrictReport";
		
	}
	
	@RequestMapping(value = "/downloadExcelblsoutrptproj", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelblsoutrptproj(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projId=request.getParameter("projId");
		List<BaseLineOutcomeBean> list = new ArrayList<BaseLineOutcomeBean>();
		
		list=reportService.getBlsOutReportProjectLevel(Integer.parseInt(projId));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O4- Details of Comparison of Distribution of Gross Cropped Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O4- Details of Comparison of Distribution of Gross Cropped Area as Per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status for District '" + distName+"'";
			String areaAmtValDetail = "All area in ha. And All amounts are Rs. in Lakh";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 16, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,3,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,11,12); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,13,14); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,15,16); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,3,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,5,6);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,7,8);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,9,10);
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
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
			
			for(int i =4; i<11;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Net Sown Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead.createCell(12).setCellStyle(style);
			
			cell = rowhead.createCell(13);
			cell.setCellValue("Grossed Cropped Area");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead.createCell(14).setCellStyle(style);
			
			cell = rowhead.createCell(15);
			cell.setCellValue("Total Income");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			rowhead.createCell(16).setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Cultivable Wasteland");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(4).setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Degraded Land");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(6).setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Rainfed");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(8).setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Others");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead1.createCell(10).setCellStyle(style);
			
			for(int i =11; i<17;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}

			Row rowhead2 = sheet.createRow(7);
			for(int i =0; i<3;i++) {
				rowhead2.createCell(i).setCellStyle(style);
			}
			cell = rowhead2.createCell(3);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(4);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(5);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(6);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(7);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(8);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(9);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(10);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(11);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(12);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(13);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(14);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(15);
			cell.setCellValue("As on Baseline");  
			cell.setCellStyle(style);
			
			cell = rowhead2.createCell(16);
			cell.setCellValue("As on date");
			cell.setCellStyle(style);
			
			Row rowhead3 = sheet.createRow(8);
			for(int i =0; i<17;i++) {
				cell =rowhead3.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  =9;
	        double totPlotArea = 0;
	        double totCultWasteLand = 0;
	        double totCultWasteLandAchv = 0;
	        double totDegradLand = 0;
	        double totDegradLandAchv = 0;
	        double totRainfed = 0;
	        double totRainfedAchv = 0;
	        double totOthers = 0;
	        double totOthersAchv = 0;
	        double totNetSownArea = 0;
	        double totNetSownAreaAchv = 0;
	        double totGrossCrpArea = 0;
	        double totGrossCrpAreaAchv = 0;
	        double totIncome = 0;
	        double totIncomeAchv = 0;
	        
	        for(BaseLineOutcomeBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getProjname());
	        	row.createCell(2).setCellValue(bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());  
	        	row.createCell(3).setCellValue(bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue()); 
	        	row.createCell(4).setCellValue(bean.getCultivable_wasteland_achv()==null?0.0:bean.getCultivable_wasteland_achv().doubleValue());  
	        	row.createCell(5).setCellValue(bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue()); 
	        	row.createCell(6).setCellValue(bean.getDegraded_land_achv()==null?0.0:bean.getDegraded_land_achv().doubleValue()); 
	        	row.createCell(7).setCellValue(bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue()); 
	        	row.createCell(8).setCellValue(bean.getRainfed_achv()==null?0.0:bean.getRainfed_achv().doubleValue());  
	        	row.createCell(9).setCellValue(bean.getOthers()==null?0.0:bean.getOthers().doubleValue()); 
	        	row.createCell(10).setCellValue(bean.getOthers_achv()==null?0.0:bean.getOthers_achv().doubleValue());
	        	row.createCell(11).setCellValue(bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());  
	        	row.createCell(12).setCellValue(bean.getNet_sown_area_achv()==null?0.0:bean.getNet_sown_area_achv().doubleValue());  
	        	row.createCell(13).setCellValue(bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue()); 
	        	row.createCell(14).setCellValue(bean.getGross_cropped_area_achv()==null?0.0:bean.getGross_cropped_area_achv().doubleValue());  
	        	row.createCell(15).setCellValue(bean.getTotal_income()==null?0.0:bean.getTotal_income().doubleValue()/100000); 
	        	row.createCell(16).setCellValue(bean.getTotal_income_achv()==null?0.0:bean.getTotal_income_achv().doubleValue()/100000); 

	        	
	        	totPlotArea = totPlotArea + (bean.getTreatable_area()==null?0.0:bean.getTreatable_area().doubleValue());
	        	totCultWasteLand = totCultWasteLand + (bean.getCultivable_wasteland()==null?0.0:bean.getCultivable_wasteland().doubleValue());
	        	totCultWasteLandAchv = totCultWasteLandAchv + (bean.getCultivable_wasteland_achv()==null?0.0:bean.getCultivable_wasteland_achv().doubleValue());
	        	totDegradLand = totDegradLand + (bean.getDegraded_land()==null?0.0:bean.getDegraded_land().doubleValue()); 
	        	totDegradLandAchv = totDegradLandAchv + (bean.getDegraded_land_achv()==null?0.0:bean.getDegraded_land_achv().doubleValue()); 
	        	totRainfed = totRainfed + (bean.getRainfed()==null?0.0:bean.getRainfed().doubleValue());
	        	totRainfedAchv = totRainfedAchv + (bean.getRainfed_achv()==null?0.0:bean.getRainfed_achv().doubleValue());
	        	totOthers = totOthers + (bean.getOthers()==null?0.0:bean.getOthers().doubleValue());
	        	totOthersAchv = totOthersAchv + (bean.getOthers_achv()==null?0.0:bean.getOthers_achv().doubleValue());
	        	totNetSownArea = totNetSownArea + (bean.getNet_sown_area()==null?0.0:bean.getNet_sown_area().doubleValue());
	        	totNetSownAreaAchv = totNetSownAreaAchv + (bean.getNet_sown_area_achv()==null?0.0:bean.getNet_sown_area_achv().doubleValue());
	        	totGrossCrpArea = totGrossCrpArea + (bean.getGross_cropped_area()==null?0.0:bean.getGross_cropped_area().doubleValue());
	        	totGrossCrpAreaAchv = totGrossCrpAreaAchv + (bean.getGross_cropped_area_achv()==null?0.0:bean.getGross_cropped_area_achv().doubleValue());
	        	totIncome = totIncome + (bean.getTotal_income()==null?0.0:bean.getTotal_income().doubleValue()/100000);
	        	totIncomeAchv = totIncomeAchv + (bean.getTotal_income_achv()==null?0.0:bean.getTotal_income_achv().doubleValue()/100000);

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
	        cell.setCellValue(totCultWasteLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totCultWasteLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totDegradLand);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totDegradLandAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totRainfed);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totRainfedAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totOthers);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totOthersAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totNetSownArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totNetSownAreaAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(totGrossCrpArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totGrossCrpAreaAchv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(totIncome); 
	        cell.setCellStyle(style1);
	        cell = row.createCell(16);
	        cell.setCellValue(totIncomeAchv);
	        cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 16);
	        String fileName = "attachment; filename=Report O4- Project.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/baseLineOutcomeProjectReport";
		
	}
	
	@RequestMapping(value = "/downloadExcelblsoutrptdetail", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelblsoutrptdetail(HttpServletRequest request, HttpServletResponse response) 
	{
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projName=request.getParameter("projName");
		Integer id=Integer.parseInt(request.getParameter("id"));
		
		List<NewBaseLineSurveyBean> list1= new ArrayList<NewBaseLineSurveyBean>();
		 List<NewBaseLineSurveyBean> list2= new ArrayList<NewBaseLineSurveyBean>();
		 List<NewBaseLineSurveyBean> sublist= new ArrayList<NewBaseLineSurveyBean>();
		 List<NewBaseLineSurveyBean> sublist2= new ArrayList<NewBaseLineSurveyBean>();
		 LinkedHashMap<String,List<NewBaseLineSurveyBean>> blsmap = new LinkedHashMap<String,List<NewBaseLineSurveyBean>>();
		 LinkedHashMap<String,List<NewBaseLineSurveyBean>> outmap = new LinkedHashMap<String,List<NewBaseLineSurveyBean>>();
		 LinkedHashMap<String,List<NewBaseLineSurveyBean>> map = new LinkedHashMap<String,List<NewBaseLineSurveyBean>>();
		 
		    list2 = reportService.getBlsOutReportDetailLevel(id);
		    list1 = rptService.getBlsReportDetailLevel(id);
		    
		    for (NewBaseLineSurveyBean bean : list1) 
			{
				if(!blsmap.containsKey(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name())) {
					sublist= new ArrayList<NewBaseLineSurveyBean>();
					//sublist2= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					blsmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name(), sublist);
				}else {
					sublist=blsmap.get(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name());
					//sublist= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					blsmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name(), sublist);
				}
				
			}
			
			for (NewBaseLineSurveyBean bean : list2) 
			{
				if(!outmap.containsKey(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv())) {
					sublist= new ArrayList<NewBaseLineSurveyBean>();
					//sublist2= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					outmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv(), sublist);
				}else {
					
					sublist=outmap.get(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv());
					//sublist= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					outmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv(), sublist);
				}
				
			}
			
			 for (Map.Entry<String,List<NewBaseLineSurveyBean>> entry : blsmap.entrySet()) {
					/*
					 * System.out.println("Key = " + entry.getKey() + ", Value = " +
					 * entry.getValue());
					 */
		            sublist2= entry.getValue();
		            sublist2.addAll(outmap.get(entry.getKey()));
		            map.put(entry.getKey(), sublist2);
		            
		            
		    }
		
		
		
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O4- Details of Comparison of Distribution of Gross Cropped Area");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O4- Details of Comparison of Distribution of Gross Cropped Area as Per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status for Project '" + projName+"'";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 15, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,5);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,6,15);
	        sheet.addMergedRegion(mergedRegion);
	
        
//	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
//	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("State: "+stateName+"  District: "+distName+"  Project: "+projName);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<6;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			cell = rowhead.createCell(6);
			cell.setCellValue("All area in ha. and All amounts are in Rs.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
			
			for(int i =7; i<16;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			String arr[] = {"Block","Gram Panchayat","Village","Plot/Gata No.","Plot Area","Irrigation Status","Ownership","Owner Name","Classification of Land",
					"No. of Crop","Season","Crop Type","Area (Col-A)","Crop Production per Hectare (in Quintal)(Col-B)","Avg. Income per Quintal(Col-C)","Total Income (A*B*C)"};
			
			rowhead = sheet.createRow(6);
			for (int i = 0; i < arr.length; i++) {
				cell = rowhead.createCell(i);
				cell.setCellValue(arr[i]);
				cell.setCellStyle(style);
			}
			
			Row rowhead3 = sheet.createRow(7);
			for(int i =0; i<16;i++) {
				cell =rowhead3.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int rowno  =8;
	        String plotNo = "";
	        String village = "";
	        Integer blsId = 0;
	        Double area = 0.0;
	        Double crpProd = 0.0;
	        Double avgIncm = 0.0;
	        Double totIncm = 0.0;
	        
	        Double area_achv = 0.0;
	        Double crpProd_achv = 0.0;
	        Double avgIncm_achv = 0.0;
	        Double totIncm_achv = 0.0;
	        
	        String noOfCrop = "";
	        String noOfCropAch = "";
	        Boolean total = false;
	        
	        Double grandArea = 0.0;
	        Double grandCrpProd = 0.0;
	        Double grandAvgIncm = 0.0;
	        Double grandTotIncm = 0.0;
	        
	        Double grandArea_achv = 0.0;
	        Double grandCrpProd_achv = 0.0;
	        Double grandAvgIncm_achv = 0.0;
	        Double grandTotIncm_achv = 0.0;
	        
	        Double grandPlotArea = 0.0;
	        
	        String numOfCrops = "No Crop";
	        String numOfCropsAch = "No Crop";
	        
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
			
			CellStyle style2 = workbook.createCellStyle();
			org.apache.poi.ss.usermodel.Font font2 = workbook.createFont();
			font2.setFontHeightInPoints((short) 12);
			font2.setBold(true);
			font2.setColor(IndexedColors.BLUE.getIndex());
			style2.setFont(font2);
			
			CellStyle style3 = workbook.createCellStyle();
			org.apache.poi.ss.usermodel.Font font3 = workbook.createFont();
			font3.setFontHeightInPoints((short) 12);
			font3.setBold(true);
			font3.setColor(IndexedColors.BLACK.getIndex());
			style3.setFont(font3);
	       
	        for(NewBaseLineSurveyBean bean: list1) {
	        	
	        	
				if (!bean.getPlot_no().equals(plotNo) || !bean.getVillage_name().equals(village) || !bean.getBls_out_main_id_pk().equals(blsId)) {
					plotNo = bean.getPlot_no();
					village = bean.getVillage_name();
					blsId = bean.getBls_out_main_id_pk();
					Row row = sheet.createRow(rowno);
					if (total) {
						cell = row.createCell(11);
						cell.setCellValue("Total");
						cell.setCellStyle(style2);

						cell = row.createCell(12);
						cell.setCellValue(area_achv);
						cell.setCellStyle(style2);

						cell = row.createCell(13);
						cell.setCellValue(crpProd_achv);
						cell.setCellStyle(style2);

						cell = row.createCell(14);
						cell.setCellValue(avgIncm_achv);
						cell.setCellStyle(style2);

						cell = row.createCell(15);
						cell.setCellValue(totIncm_achv);
						cell.setCellStyle(style2);
						
						grandArea_achv = grandArea_achv + area_achv;
				        grandCrpProd_achv = grandCrpProd_achv + crpProd_achv;
				        grandAvgIncm_achv = grandAvgIncm_achv + avgIncm_achv;
				        grandTotIncm_achv = grandTotIncm_achv + totIncm_achv;

						area_achv = 0.0;
						crpProd_achv = 0.0;
						avgIncm_achv = 0.0;
						totIncm_achv = 0.0;
						rowno++;
						row = sheet.createRow(rowno);
						for(int i =0; i<16;i++) {
							row.createCell(i).setCellStyle(style);
						}
						rowno++;
						row = sheet.createRow(rowno);
					}
					total = true;
					cell = row.createCell(0);
					cell.setCellValue(bean.getBlock_name());

					cell = row.createCell(1);
					cell.setCellValue(bean.getGram_panchayat_name());

					cell = row.createCell(2);
					cell.setCellValue(bean.getVillage_name());

					cell = row.createCell(3);
					cell.setCellValue(bean.getPlot_no());
					
					cell = row.createCell(4);
					cell.setCellValue(bean.getArea().doubleValue());
					
					grandPlotArea = grandPlotArea+ bean.getArea().doubleValue();
					
					rowno++;
					row = sheet.createRow(rowno);
					cell = row.createCell(8);
					cell.setCellValue("As on Baseline");
					cell.setCellStyle(style3);
					 
					rowno++;
					
					for (Map.Entry<String, List<NewBaseLineSurveyBean>> entry : map.entrySet()) {
						if (entry.getKey().equals(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString()+bean.getVillage_name())) {
							for (NewBaseLineSurveyBean ob : entry.getValue()) {
								if(ob.getClassification_land()!=null) {
									numOfCrops = ob.getClassification_land().equals("Forest Land")?"No Crop":ob.getNo_crop();
								}
								if(ob.getClassification_land_achv()!=null) {
									numOfCropsAch = ob.getClassification_land_achv().equals("Forest Land")?"No Crop":ob.getNo_crop_achv();
								}
								numOfCrops = ob.getNo_crop()==null?"No Crop":ob.getNo_crop();
								numOfCropsAch = ob.getNo_crop_achv()==null?"No Crop":ob.getNo_crop_achv();
								if (ob.getPlot_no() != null && !numOfCrops.equals(noOfCrop)) {
									noOfCropAch ="";
									row = sheet.createRow(rowno);
									cell = row.createCell(5);
									cell.setCellValue(ob.getIrrigation());

									cell = row.createCell(6);
									cell.setCellValue(ob.getOwnership());

									cell = row.createCell(7);
									cell.setCellValue(ob.getOwner_name());

									cell = row.createCell(8);
									cell.setCellValue(ob.getClassification_land());
									
									cell = row.createCell(9);
									cell.setCellValue(ob.getNo_crop());

									cell = row.createCell(10);
									cell.setCellValue(ob.getSeason());

									if (!numOfCrops.equals("No Crop")) {

										Double crparea = ob.getCrop_area()==null?0.0:ob.getCrop_area().doubleValue();
										Double crpPrd = ob.getCrop_production()==null?0.0:ob.getCrop_production().doubleValue();
										Double avgInc = ob.getAvg_income()==null?0.0:ob.getAvg_income().doubleValue();
										cell = row.createCell(11);
										cell.setCellValue(ob.getCrop_type());
										
										cell = row.createCell(12);
										cell.setCellValue(crparea);

										cell = row.createCell(13);
										cell.setCellValue(crpPrd);

										cell = row.createCell(14);
										cell.setCellValue(avgInc);

										cell = row.createCell(15);
										cell.setCellValue(crparea * crpPrd * avgInc);
										area = area + crparea;
										crpProd = crpProd + crpPrd;
										avgIncm = avgIncm + avgInc;
										totIncm = totIncm + crparea * crpPrd * avgInc;
									}
									noOfCrop = ob.getNo_crop();
									rowno++;
									
									
								}else if(ob.getPlot_no() != null && numOfCrops.equals(noOfCrop)) {
									row = sheet.createRow(rowno);
									cell = row.createCell(10);
									cell.setCellValue(ob.getSeason());

									if (!numOfCrops.equals("No Crop")) {
										
										Double crparea = ob.getCrop_area()==null?0.0:ob.getCrop_area().doubleValue();
										Double crpPrd = ob.getCrop_production()==null?0.0:ob.getCrop_production().doubleValue();
										Double avgInc = ob.getAvg_income()==null?0.0:ob.getAvg_income().doubleValue();
										cell = row.createCell(11);
										cell.setCellValue(ob.getCrop_type());
										
										cell = row.createCell(12);
										cell.setCellValue(crparea);

										cell = row.createCell(13);
										cell.setCellValue(crpPrd);

										cell = row.createCell(14);
										cell.setCellValue(avgInc);

										cell = row.createCell(15);
										cell.setCellValue(crparea * crpPrd * avgInc);
										area = area + crparea;
										crpProd = crpProd + crpPrd;
										avgIncm = avgIncm + avgInc;
										totIncm = totIncm + crparea * crpPrd * avgInc;
									}
									rowno++;
									
								}else if(!numOfCropsAch.equals(noOfCropAch)){
									noOfCrop = "";
									row = sheet.createRow(rowno);
									cell = row.createCell(11);
									cell.setCellValue("Total");
									cell.setCellStyle(style2);
									
									cell = row.createCell(12);
									cell.setCellValue(area);
									cell.setCellStyle(style2);
									
									cell = row.createCell(13);
									cell.setCellValue(crpProd);
									cell.setCellStyle(style2);
									
									cell = row.createCell(14);
									cell.setCellValue(avgIncm);
									cell.setCellStyle(style2);
									
									cell = row.createCell(15);
									cell.setCellValue(totIncm);
									cell.setCellStyle(style2);
									
									grandArea = grandArea + area;
							        grandCrpProd = grandCrpProd + crpProd;
							        grandAvgIncm = grandAvgIncm + avgIncm;
							        grandTotIncm  = grandTotIncm + totIncm;
									
									area = 0.0;
									crpProd = 0.0;
									avgIncm = 0.0;
									totIncm = 0.0;
									rowno++;
									
									row = sheet.createRow(rowno);
									cell = row.createCell(8);
									cell.setCellValue("As on Date");
									cell.setCellStyle(style3);
									rowno++;
									
									row = sheet.createRow(rowno);
									cell = row.createCell(5);
									cell.setCellValue(ob.getIrrigation_achv());

									cell = row.createCell(6);
									cell.setCellValue(ob.getOwnership_achv());

									cell = row.createCell(7);
									cell.setCellValue(ob.getOwner_name_achv());

									cell = row.createCell(8);
									cell.setCellValue(ob.getClassification_land_achv());

									cell = row.createCell(9);
									cell.setCellValue(ob.getNo_crop_achv());

									cell = row.createCell(10);
									cell.setCellValue(ob.getSeason_achv());

									if (!numOfCropsAch.equals("No Crop")) {
										Double crparea = ob.getCrop_area_achv()==null?0.0:ob.getCrop_area_achv().doubleValue();
										Double crpPrd = ob.getCrop_production_achv()==null?0.0:ob.getCrop_production_achv().doubleValue();
										Double avgInc = ob.getAvg_income_achv()==null?0.0:ob.getAvg_income_achv().doubleValue();
										cell = row.createCell(11);
										cell.setCellValue(ob.getCrop_type_achv());
										
										cell = row.createCell(12);
										cell.setCellValue(crparea);

										cell = row.createCell(13);
										cell.setCellValue(crpPrd);

										cell = row.createCell(14);
										cell.setCellValue(avgInc);

										cell = row.createCell(15);
										cell.setCellValue(crparea * crpPrd * avgInc);
										area_achv = area_achv + crparea;
										crpProd_achv = crpProd_achv + crpPrd;
										avgIncm_achv = avgIncm_achv + avgInc;
										totIncm_achv = totIncm_achv + crparea * crpPrd * avgInc;
									}
									noOfCropAch = ob.getNo_crop_achv();
									rowno++;
									
									
								}else if(numOfCropsAch.equals(noOfCropAch)) {
									row = sheet.createRow(rowno);
									cell = row.createCell(10);
									cell.setCellValue(ob.getSeason_achv());

									if (!numOfCropsAch.equals("No Crop")) {
										Double crparea = ob.getCrop_area_achv()==null?0.0:ob.getCrop_area_achv().doubleValue();
										Double crpPrd = ob.getCrop_production_achv()==null?0.0:ob.getCrop_production_achv().doubleValue();
										Double avgInc = ob.getAvg_income_achv()==null?0.0:ob.getAvg_income_achv().doubleValue();
										cell = row.createCell(11);
										cell.setCellValue(ob.getCrop_type_achv());
										
										cell = row.createCell(12);
										cell.setCellValue(crparea);

										cell = row.createCell(13);
										cell.setCellValue(crpPrd);

										cell = row.createCell(14);
										cell.setCellValue(avgInc);

										cell = row.createCell(15);
										cell.setCellValue(crparea * crpPrd * avgInc);
										area_achv = area_achv + crparea;
										crpProd_achv = crpProd_achv + crpPrd;
										avgIncm_achv = avgIncm_achv + avgInc;
										totIncm_achv = totIncm_achv + crparea * crpPrd * avgInc;
									}
									rowno++;
								}
							}
						}
					}
				}
	        }
	        
	        
	        Row row = sheet.createRow(rowno);
	        cell = row.createCell(11);
			cell.setCellValue("Total");
			cell.setCellStyle(style2);

			cell = row.createCell(12);
			cell.setCellValue(area_achv);
			cell.setCellStyle(style2);

			cell = row.createCell(13);
			cell.setCellValue(crpProd_achv);
			cell.setCellStyle(style2);

			cell = row.createCell(14);
			cell.setCellValue(avgIncm_achv);
			cell.setCellStyle(style2);

			cell = row.createCell(15);
			cell.setCellValue(totIncm_achv);
			cell.setCellStyle(style2);
			
			grandArea_achv = grandArea_achv + area_achv;
	        grandCrpProd_achv = grandCrpProd_achv + crpProd_achv;
	        grandAvgIncm_achv = grandAvgIncm_achv + avgIncm_achv;
	        grandTotIncm_achv = grandTotIncm_achv + totIncm_achv;

			area_achv = 0.0;
			crpProd_achv = 0.0;
			avgIncm_achv = 0.0;
			totIncm_achv = 0.0;
			rowno++;
			row = sheet.createRow(rowno);
			for(int i =0; i<16;i++) {
				row.createCell(i).setCellStyle(style);
			}
			rowno++;
			row = sheet.createRow(rowno);
	        row.createCell(0).setCellStyle(style1);
	        row.createCell(1).setCellStyle(style1);
	        row.createCell(2).setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue("Grand Total(Baseline)");
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(grandPlotArea);
	        cell.setCellStyle(style1);
	        for(int i = 5; i<12; i++) {
	        	row.createCell(i).setCellStyle(style1);
	        }
	        cell = row.createCell(12);
	        cell.setCellValue(grandArea);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(grandCrpProd);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(grandAvgIncm);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(grandTotIncm);
	        cell.setCellStyle(style1);
	        rowno++;
	        
	        row = sheet.createRow(rowno);
	        row.createCell(0).setCellStyle(style1);
	        row.createCell(1).setCellStyle(style1);
	        row.createCell(2).setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue("Grand Total(Outcome)");
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(grandPlotArea);
	        cell.setCellStyle(style1);
	        for(int i = 5; i<12; i++) {
	        	row.createCell(i).setCellStyle(style1);
	        }
	        cell = row.createCell(12);
	        cell.setCellValue(grandArea_achv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(grandCrpProd_achv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(grandAvgIncm_achv);
	        cell.setCellStyle(style1);
	        cell = row.createCell(15);
	        cell.setCellValue(grandTotIncm_achv);
	        cell.setCellStyle(style1);
	        
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowno+9, 15);
	        String fileName = "attachment; filename=Report O4- Project Detail.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/baselineoutcome/baseLineOutcomeDetailReport";
		
	}
	
	@RequestMapping(value = "/downloadPDFblsoutrptdetail", method = RequestMethod.POST)
	public ModelAndView downloadPDFblsoutrptdetail(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		String stateName=request.getParameter("stateName");
		String distName=request.getParameter("distName");
		String projName=request.getParameter("projName");
		Integer id=Integer.parseInt(request.getParameter("id"));
		
		List<NewBaseLineSurveyBean> list1= new ArrayList<NewBaseLineSurveyBean>();
		 List<NewBaseLineSurveyBean> list2= new ArrayList<NewBaseLineSurveyBean>();
		 List<NewBaseLineSurveyBean> sublist= new ArrayList<NewBaseLineSurveyBean>();
		 List<NewBaseLineSurveyBean> sublist2= new ArrayList<NewBaseLineSurveyBean>();
		 LinkedHashMap<String,List<NewBaseLineSurveyBean>> blsmap = new LinkedHashMap<String,List<NewBaseLineSurveyBean>>();
		 LinkedHashMap<String,List<NewBaseLineSurveyBean>> outmap = new LinkedHashMap<String,List<NewBaseLineSurveyBean>>();
		 LinkedHashMap<String,List<NewBaseLineSurveyBean>> map = new LinkedHashMap<String,List<NewBaseLineSurveyBean>>();
		 
		    list2 = reportService.getBlsOutReportDetailLevel(id);
		    list1 = rptService.getBlsReportDetailLevel(id);
		    
		    for (NewBaseLineSurveyBean bean : list1) 
			{
				if(!blsmap.containsKey(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name())) {
					sublist= new ArrayList<NewBaseLineSurveyBean>();
					//sublist2= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					blsmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name(), sublist);
				}else {
					sublist=blsmap.get(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name());
					//sublist= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					blsmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no().toString() + bean.getVillage_name(), sublist);
				}
				
			}
			
			for (NewBaseLineSurveyBean bean : list2) 
			{
				if(!outmap.containsKey(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv())) {
					sublist= new ArrayList<NewBaseLineSurveyBean>();
					//sublist2= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					outmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv(), sublist);
				}else {
					
					sublist=outmap.get(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv());
					//sublist= new ArrayList<NewBaseLineSurveyBean>();
					sublist.add(bean);
					//sublist2.add(sublist);
					outmap.put(bean.getBls_out_main_id_pk()+bean.getPlot_no_achv().toString()+bean.getVillage_name_achv(), sublist);
				}
				
			}
			
			 for (Map.Entry<String,List<NewBaseLineSurveyBean>> entry : blsmap.entrySet()) {
					/*
					 * System.out.println("Key = " + entry.getKey() + ", Value = " +
					 * entry.getValue());
					 */
		            sublist2= entry.getValue();
		            sublist2.addAll(outmap.get(entry.getKey()));
		            map.put(entry.getKey(), sublist2);
		            
		            
		    }
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("baselineOutcomeReport");
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
			
				paragraph3 = new Paragraph("Report O4- Details of Comparison of Distribution of Gross Cropped Area as Per Classification of Land, Net Sown Area, Gross Cropped Area and Total Income as On Date with Baseline Status for Project '"+ projName+"'", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
				table = new PdfPTable(16);
				table.setWidths(new int[] { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				String plotNo = "";
				String village = "";
				Integer blsId = 0;
				BigDecimal area = BigDecimal.ZERO;
				BigDecimal crpProd = BigDecimal.ZERO;
				BigDecimal avgIncm = BigDecimal.ZERO;
				BigDecimal totIncm = BigDecimal.ZERO;

				BigDecimal area_achv = BigDecimal.ZERO;
				BigDecimal crpProd_achv = BigDecimal.ZERO;
				BigDecimal avgIncm_achv = BigDecimal.ZERO;
				BigDecimal totIncm_achv = BigDecimal.ZERO;

				String noOfCrop = "";
				String noOfCropAch = "";
				Boolean total = false;

				BigDecimal grandArea = BigDecimal.ZERO;
				BigDecimal grandCrpProd = BigDecimal.ZERO;
				BigDecimal grandAvgIncm = BigDecimal.ZERO;
				BigDecimal grandTotIncm = BigDecimal.ZERO;

				BigDecimal grandArea_achv = BigDecimal.ZERO;
				BigDecimal grandCrpProd_achv = BigDecimal.ZERO;
				BigDecimal grandAvgIncm_achv = BigDecimal.ZERO;
				BigDecimal grandTotIncm_achv = BigDecimal.ZERO;

				BigDecimal grandPlotArea = BigDecimal.ZERO;

				String numOfCrops = "No Crop";
				String numOfCropsAch = "No Crop";

				CommonFunctions.insertCellHeader(table, "State: "+stateName+"  District: "+distName+"  Project: "+projName, Element.ALIGN_LEFT, 6, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "All area in ha.\r\n"
						+ "All amounts are Rs. in Lakh", Element.ALIGN_RIGHT, 10, 1, bf8Bold);
				
				String arr[] = {"Block","Gram Panchayat","Village","Plot/Gata No.","Plot Area","Irrigation Status","Ownership","Owner Name","Classification of Land",
						"No. of Crop","Season","Crop Type","Area (Col-A)","Crop Production per Hectare (in Quintal)(Col-B)","Avg. Income per Quintal(Col-C)","Total Income (A*B*C)"};
				
				for (int i = 0; i < arr.length; i++) {
					CommonFunctions.insertCellHeader(table, arr[i], Element.ALIGN_CENTER, 1, 1, bf8Bold);
				}
				for (int i = 0; i < arr.length; i++) {
					CommonFunctions.insertCellHeader(table, String.valueOf(i+1), Element.ALIGN_CENTER, 1, 1, bf8Bold);
				}
				
				
				if(list1.size()!=0)
					for(int i=0;i<list1.size();i++) 
					{
						if (!list1.get(i).getPlot_no().equals(plotNo) || !list1.get(i).getVillage_name().equals(village)||! list1.get(i).getBls_out_main_id_pk().equals(blsId)) {
							plotNo = list1.get(i).getPlot_no();
							village = list1.get(i).getVillage_name();
							blsId = list1.get(i).getBls_out_main_id_pk();
							if (total) {
								for(int a=0;a<11;a++) {
									CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
								}
								CommonFunctions.insertCell(table, "Total", Element.ALIGN_LEFT, 1, 1, bf10Bold);
								CommonFunctions.insertCell(table, area_achv.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
								CommonFunctions.insertCell(table, crpProd_achv.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
								CommonFunctions.insertCell(table, avgIncm_achv.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
								CommonFunctions.insertCell(table, totIncm_achv.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
							
								grandArea_achv = grandArea_achv.add(area_achv);
					        	grandCrpProd_achv = grandCrpProd_achv.add(crpProd_achv);
					        	grandAvgIncm_achv = grandAvgIncm_achv.add(avgIncm_achv);
					        	grandTotIncm_achv  = grandTotIncm_achv.add(totIncm_achv);
							
								area_achv = BigDecimal.ZERO;
								crpProd_achv = BigDecimal.ZERO;
								avgIncm_achv = BigDecimal.ZERO;
								totIncm_achv = BigDecimal.ZERO;
								
								CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 16, 1, bf8Bold);
							}
							total = true;
							CommonFunctions.insertCell(table, list1.get(i).getBlock_name(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table,  list1.get(i).getGram_panchayat_name(), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list1.get(i).getVillage_name(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list1.get(i).getPlot_no(), Element.ALIGN_RIGHT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list1.get(i).getArea().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
							
							grandPlotArea = grandPlotArea.add(list1.get(i).getArea());
							for(int a=6;a<17;a++) {
								CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							}
							for(int a=0;a<16;a++) {
								if(a==8)
									CommonFunctions.insertCell(table, "As on Baseline", Element.ALIGN_LEFT, 1, 1, bf10Bold);
								else
									CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							}
						
							for (Map.Entry<String, List<NewBaseLineSurveyBean>> entry : map.entrySet()) {
								if (entry.getKey().equals(list1.get(i).getBls_out_main_id_pk()+list1.get(i).getPlot_no().toString()+list1.get(i).getVillage_name())) {
									for (NewBaseLineSurveyBean ob : entry.getValue()) {
										
										if(ob.getClassification_land()!=null) {
											numOfCrops = ob.getClassification_land().equals("Forest Land")?"No Crop":ob.getNo_crop();
										}
										if(ob.getClassification_land_achv()!=null) {
											numOfCropsAch = ob.getClassification_land_achv().equals("Forest Land")?"No Crop":ob.getNo_crop_achv();
										}
										numOfCrops = ob.getNo_crop()==null?"No Crop":ob.getNo_crop();
										numOfCropsAch = ob.getNo_crop_achv()==null?"No Crop":ob.getNo_crop_achv();
										if (ob.getPlot_no() != null && !numOfCrops.equals(noOfCrop)) {
											noOfCropAch ="";
											for(int a=0;a<5;a++) {
												CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
											}
											CommonFunctions.insertCell(table, ob.getIrrigation(), Element.ALIGN_LEFT, 1, 1,	bf8);
											CommonFunctions.insertCell(table, ob.getOwnership(), Element.ALIGN_LEFT, 1, 1, bf8);
											CommonFunctions.insertCell(table, ob.getOwner_name(), Element.ALIGN_LEFT, 1, 1, bf8);
											CommonFunctions.insertCell(table, ob.getClassification_land(), Element.ALIGN_LEFT, 1, 1, bf8);
											CommonFunctions.insertCell(table, ob.getNo_crop(), Element.ALIGN_LEFT, 1, 1, bf8);
											CommonFunctions.insertCell(table, ob.getSeason(), Element.ALIGN_LEFT, 1, 1, bf8);
											if (!numOfCrops.equals("No Crop")) {
												BigDecimal crparea = ob.getCrop_area()==null?BigDecimal.ZERO:ob.getCrop_area();
												BigDecimal crpPrd = ob.getCrop_production()==null?BigDecimal.ZERO:ob.getCrop_production();
												BigDecimal avgInc = ob.getAvg_income()==null?BigDecimal.ZERO:ob.getAvg_income();
												CommonFunctions.insertCell(table, ob.getCrop_type(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crparea.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crpPrd.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, avgInc.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crparea.multiply(crpPrd).multiply(avgInc).toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												area = area.add(crparea);
												crpProd = crpProd.add(crpPrd);
												avgIncm = avgIncm.add(avgInc);
												totIncm = totIncm.add(crparea.multiply(crpPrd).multiply(avgInc));
											}else {
												for(int a=12;a<=16;a++) {
													CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
												}
											}
											noOfCrop = ob.getNo_crop();
										}
										else if(ob.getPlot_no() != null && numOfCrops.equals(noOfCrop)) {
											for(int a=0;a<10;a++) {
												CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
											}
											CommonFunctions.insertCell(table, ob.getSeason(), Element.ALIGN_LEFT, 1, 1, bf8);
											if (!numOfCrops.equals("No Crop")) {
												BigDecimal crparea = ob.getCrop_area()==null?BigDecimal.ZERO:ob.getCrop_area();
												BigDecimal crpPrd = ob.getCrop_production()==null?BigDecimal.ZERO:ob.getCrop_production();
												BigDecimal avgInc = ob.getAvg_income()==null?BigDecimal.ZERO:ob.getAvg_income();
												CommonFunctions.insertCell(table, ob.getCrop_type(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crparea.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crpPrd.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, avgInc.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crparea.multiply(crpPrd).multiply(avgInc).toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												area = area.add(crparea);
												crpProd = crpProd.add(crpPrd);
												avgIncm = avgIncm.add(avgInc);
												totIncm = totIncm.add(crparea.multiply(crpPrd).multiply(avgInc));
											}else {
												for(int a=12;a<=16;a++) {
													CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
												}
											}
										}else if(!numOfCropsAch.equals(noOfCropAch)){
											noOfCrop = "";
											for(int a=0;a<11;a++) {
												CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
											}
											CommonFunctions.insertCell(table, "Total", Element.ALIGN_LEFT, 1, 1, bf10Bold);
											CommonFunctions.insertCell(table, area.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
											CommonFunctions.insertCell(table, crpProd.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
											CommonFunctions.insertCell(table, avgIncm.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
											CommonFunctions.insertCell(table, totIncm.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
										
											grandArea = grandArea.add(area);
								        	grandCrpProd = grandCrpProd.add(crpProd);
								        	grandAvgIncm = grandAvgIncm.add(avgIncm);
								        	grandTotIncm  = grandTotIncm.add(totIncm);
										
											area = BigDecimal.ZERO;
											crpProd = BigDecimal.ZERO;
											avgIncm = BigDecimal.ZERO;
											totIncm = BigDecimal.ZERO;
										
											for(int a=0;a<16;a++) {
												if(a==8)
													CommonFunctions.insertCell(table, "As on Date", Element.ALIGN_LEFT, 1, 1, bf10Bold);
												else
													CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
											}
											for(int a=0;a<5;a++) {
												CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
											}
										
											CommonFunctions.insertCell(table, ob.getIrrigation_achv(), Element.ALIGN_LEFT, 1, 1,	bf8);
											CommonFunctions.insertCell(table, ob.getOwnership_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
											CommonFunctions.insertCell(table, ob.getOwner_name_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
											CommonFunctions.insertCell(table, ob.getClassification_land_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
											CommonFunctions.insertCell(table, ob.getNo_crop_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
											CommonFunctions.insertCell(table, ob.getSeason_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
											if (!numOfCropsAch.equals("No Crop")) {
											
												BigDecimal crparea = ob.getCrop_area_achv()==null?BigDecimal.ZERO:ob.getCrop_area_achv();
												BigDecimal crpPrd = ob.getCrop_production_achv()==null?BigDecimal.ZERO:ob.getCrop_production_achv();
												BigDecimal avgInc = ob.getAvg_income_achv()==null?BigDecimal.ZERO:ob.getAvg_income_achv();
												CommonFunctions.insertCell(table, ob.getCrop_type_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crparea.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crpPrd.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, avgInc.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crparea.multiply(crpPrd).multiply(avgInc).toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												area_achv = area_achv.add(crparea);
												crpProd_achv = crpProd_achv.add(crpPrd);
												avgIncm_achv = avgIncm_achv.add(avgInc);
												totIncm_achv = totIncm_achv.add(crparea.multiply(crpPrd).multiply(avgInc));
											}else {
												for(int a=12;a<=16;a++) {
													CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
												}
											}
											noOfCropAch = ob.getNo_crop_achv();
										}else if(numOfCropsAch.equals(noOfCropAch)) {
											for(int a=0;a<10;a++) {
												CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
											}
											CommonFunctions.insertCell(table, ob.getSeason_achv(), Element.ALIGN_LEFT, 1, 1, bf8);
											if (!numOfCropsAch.equals("No Crop")) {
												BigDecimal crparea = ob.getCrop_area_achv()==null?BigDecimal.ZERO:ob.getCrop_area_achv();
												BigDecimal crpPrd = ob.getCrop_production_achv()==null?BigDecimal.ZERO:ob.getCrop_production_achv();
												BigDecimal avgInc = ob.getAvg_income_achv()==null?BigDecimal.ZERO:ob.getAvg_income_achv();
												CommonFunctions.insertCell(table, ob.getCrop_type(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crparea.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crpPrd.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, avgInc.toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												CommonFunctions.insertCell(table, crparea.multiply(crpPrd).multiply(avgInc).toString(), Element.ALIGN_LEFT, 1, 1, bf8);
												area_achv = area_achv.add(crparea);
												crpProd_achv = crpProd_achv.add(crpPrd);
												avgIncm_achv = avgIncm_achv.add(avgInc);
												totIncm_achv = totIncm_achv.add(crparea.multiply(crpPrd).multiply(avgInc));
											}else {
												for(int a=12;a<=16;a++) {
													CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
												}
											}
										}
									}
									
								}
							}
						}
					}
				
				for(int a=0;a<11;a++) {
					CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
				}
				CommonFunctions.insertCell(table, "Total", Element.ALIGN_LEFT, 1, 1, bf10Bold);
				CommonFunctions.insertCell(table, area_achv.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
				CommonFunctions.insertCell(table, crpProd_achv.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
				CommonFunctions.insertCell(table, avgIncm_achv.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
				CommonFunctions.insertCell(table, totIncm_achv.toString(), Element.ALIGN_LEFT, 1, 1, bf10Bold);
			
				grandArea_achv = grandArea_achv.add(area_achv);
	        	grandCrpProd_achv = grandCrpProd_achv.add(crpProd_achv);
	        	grandAvgIncm_achv = grandAvgIncm_achv.add(avgIncm_achv);
	        	grandTotIncm_achv  = grandTotIncm_achv.add(totIncm_achv);
			
				area_achv = BigDecimal.ZERO;
				crpProd_achv = BigDecimal.ZERO;
				avgIncm_achv = BigDecimal.ZERO;
				totIncm_achv = BigDecimal.ZERO;
				
				CommonFunctions.insertCellHeader(table, "", Element.ALIGN_CENTER, 16, 1, bf8Bold);
				CommonFunctions.insertCell3(table, "Grand Total(Baseline)", Element.ALIGN_RIGHT, 4, 1, bf10Bold);
				CommonFunctions.insertCell3(table, grandPlotArea.toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				for(int i = 6; i<13; i++) {
					CommonFunctions.insertCell3(table, "", Element.ALIGN_CENTER, 1, 1, bf10Bold);
		        }
				CommonFunctions.insertCell3(table, String.valueOf(grandArea), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(grandCrpProd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(grandAvgIncm), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(grandTotIncm), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				CommonFunctions.insertCell3(table, "Grand Total(Outcome)", Element.ALIGN_RIGHT, 4, 1, bf10Bold);
				CommonFunctions.insertCell3(table, grandPlotArea.toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				for(int i = 6; i<13; i++) {
					CommonFunctions.insertCell3(table, "", Element.ALIGN_CENTER, 1, 1, bf10Bold);
		        }
				CommonFunctions.insertCell3(table, String.valueOf(grandArea_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(grandCrpProd_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(grandAvgIncm_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(grandTotIncm_achv), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				
					if(list1.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 16, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report-O4 Project Details.pdf");
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
	
	
