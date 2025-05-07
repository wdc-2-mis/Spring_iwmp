package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
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

import app.TargetAchievementQuarterBean;
import app.bean.AssetIdBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaBean;
import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaService;
import app.service.StateMasterService;
import app.service.UserService;

@Controller("JanbhagidariPratiyogitaReportController")
public class JanbhagidariPratiyogitaReportController {
	
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired
	JanbhagidariPratiyogitaService serk;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	
	@RequestMapping(value="/janbhagidariPratiyogitaReport", method = RequestMethod.GET)
	public ModelAndView janbhagidariPratiyogitaReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
		try {
			
			mav = new ModelAndView("reports/janbhagidariPratiyogitaReport");
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			if(userState!=null && !userState.equals("")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			if(district!=null && !district.equals("")) 
			{
				projectList=userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			mav.addObject("district", district);
			mav.addObject("project", project);
			
			if(userState==null) {
			data=serk.getListJanbhagidariPratiyogitaDetails();
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			
			int totno_gp=0;
			int totno_village=0;
			BigDecimal totproj_area = BigDecimal.valueOf(0);
			BigDecimal totproj_outlay = BigDecimal.valueOf(0);
			int totno_ngo_name=0;
			int totno_ngo_gp=0;
			int totno_ngo_vill=0;
			int totno_swck_gp=0;
			BigDecimal totfund_expenditure = BigDecimal.valueOf(0);
			
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					totno_gp=totno_gp+bean.getNo_gp();
					totno_village=totno_village+bean.getNo_village();
					totproj_area=totproj_area.add(new BigDecimal(bean.getProj_area()));
					totproj_outlay=totproj_outlay.add(new BigDecimal(bean.getProj_outlay()));
					totno_ngo_name=totno_ngo_name+bean.getNo_ngo_name();
					totno_ngo_gp=totno_ngo_gp+bean.getNo_ngo_gp();
					totno_ngo_vill=totno_ngo_vill+bean.getNo_ngo_vill();
					totno_swck_gp=totno_swck_gp+bean.getNo_swck_gp();
					totfund_expenditure=totfund_expenditure.add(new BigDecimal(bean.getFund_expenditure()));
					
				}
			}	
			
			mav.addObject("totno_gp",totno_gp);
			mav.addObject("totno_village",totno_village);
			mav.addObject("totproj_area",totproj_area);
			mav.addObject("totproj_outlay",totproj_outlay);
			mav.addObject("totno_ngo_name",totno_ngo_name);
			mav.addObject("totno_ngo_gp",totno_ngo_gp);
			mav.addObject("totno_ngo_vill",totno_ngo_vill);
			mav.addObject("totno_swck_gp",totno_swck_gp);
			mav.addObject("totfund_expenditure",totfund_expenditure);
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value="/janbhagidariPratiyogitaALLReport", method = RequestMethod.POST)
	public ModelAndView janbhagidariPratiyogitaALLReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
		try {
			
			mav = new ModelAndView("reports/janbhagidariPratiyogitaReport");
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			if(userState!=null && !userState.equals("")) 
			{
				districtList = userService.getDistrictList(Integer.parseInt(userState));
				mav.addObject("districtList", districtList);
			}
			if(district!=null && !district.equals("")) 
			{
				projectList=userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("projectList", projectList);
			}
			mav.addObject("district", district);
			mav.addObject("project", project);
			
			data=serk.janbhagidariPratiyogitaALLReport(userState, district, project);
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			
			int totno_gp=0;
			int totno_village=0;
			BigDecimal totproj_area = BigDecimal.valueOf(0);
			BigDecimal totproj_outlay = BigDecimal.valueOf(0);
			int totno_ngo_name=0;
			int totno_ngo_gp=0;
			int totno_ngo_vill=0;
			int totno_swck_gp=0;
			BigDecimal totfund_expenditure = BigDecimal.valueOf(0);
			
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					totno_gp=totno_gp+bean.getNo_gp();
					totno_village=totno_village+bean.getNo_village();
					totproj_area=totproj_area.add(new BigDecimal(bean.getProj_area()));
					totproj_outlay=totproj_outlay.add(new BigDecimal(bean.getProj_outlay()));
					totno_ngo_name=totno_ngo_name+bean.getNo_ngo_name();
					totno_ngo_gp=totno_ngo_gp+bean.getNo_ngo_gp();
					totno_ngo_vill=totno_ngo_vill+bean.getNo_ngo_vill();
					totno_swck_gp=totno_swck_gp+bean.getNo_swck_gp();
					totfund_expenditure=totfund_expenditure.add(new BigDecimal(bean.getFund_expenditure()));
					
				}
			}	
			
			mav.addObject("totno_gp",totno_gp);
			mav.addObject("totno_village",totno_village);
			mav.addObject("totproj_area",totproj_area);
			mav.addObject("totproj_outlay",totproj_outlay);
			mav.addObject("totno_ngo_name",totno_ngo_name);
			mav.addObject("totno_ngo_gp",totno_ngo_gp);
			mav.addObject("totno_ngo_vill",totno_ngo_vill);
			mav.addObject("totno_swck_gp",totno_swck_gp);
			mav.addObject("totfund_expenditure",totfund_expenditure);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value = "/janbhagidariPratiyogitaPDF", method = RequestMethod.POST)
	public ModelAndView janbhagidariPratiyogitaPDF(HttpServletRequest request, HttpServletResponse response) {
		
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("State, District and Project-wise Janbhagidari Cup under WDC-PMKSY2.0 Report");
	        document.addCreationDate();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter writer = PdfWriter.getInstance(document, baos);

	        document.open();

	        Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC);
	        Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD);
	        Font bf8 = new Font(FontFamily.HELVETICA, 8);
	        Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
	        Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

	        PdfPTable table = null;
	        document.newPage();
	        Paragraph paragraph3 = null;
	        Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);

	        paragraph3 = new Paragraph("State, District and Project-wise Janbhagidari Cup under WDC-PMKSY2.0 Report", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(14);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	     //   table.setHeaderRows(3);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Gram Panchayat", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Villages", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area Allocated for Project (ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Project Outlay of WDC PMKSY (Rs. in Lakh) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of NGOs Engaged in Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Gram Panchayat to be Covered by NGO", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Villages to be Covered by NGO", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Gram Panchayat where SWCK Account is Opened", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Project Expenditure of WDC PMKSY 2.0 (In Lakh)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Percentage of Expenditure (%)", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	   /*     CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);  */
	        data=serk.janbhagidariPratiyogitaALLReport(userState, district, project);
	        String st="";
	        String dist="";
	        int k = 1;
	        int tno_gp=0;
			int tno_village=0;
			BigDecimal tproj_area = BigDecimal.valueOf(0);
			BigDecimal tproj_outlay = BigDecimal.valueOf(0);
			int tno_ngo_name=0;
			int tno_ngo_gp=0;
			int tno_ngo_vill=0;
			int tno_swck_gp=0;
			BigDecimal tfund_expenditure = BigDecimal.valueOf(0);
			
			int totno_gp=0;
			int totno_village=0;
			BigDecimal totproj_area = BigDecimal.valueOf(0);
			BigDecimal totproj_outlay = BigDecimal.valueOf(0);
			int totno_ngo_name=0;
			int totno_ngo_gp=0;
			int totno_ngo_vill=0;
			int totno_swck_gp=0;
			BigDecimal totfund_expenditure = BigDecimal.valueOf(0);
			
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					totno_gp=totno_gp+bean.getNo_gp();
					totno_village=totno_village+bean.getNo_village();
					totproj_area=totproj_area.add(new BigDecimal(bean.getProj_area()));
					totproj_outlay=totproj_outlay.add(new BigDecimal(bean.getProj_outlay()));
					totno_ngo_name=totno_ngo_name+bean.getNo_ngo_name();
					totno_ngo_gp=totno_ngo_gp+bean.getNo_ngo_gp();
					totno_ngo_vill=totno_ngo_vill+bean.getNo_ngo_vill();
					totno_swck_gp=totno_swck_gp+bean.getNo_swck_gp();
					totfund_expenditure=totfund_expenditure.add(new BigDecimal(bean.getFund_expenditure()));
					
				}
			}	
			
	        if (data.size() != 0) {
	            for (int i = 0; i < data.size(); i++) {
	            	
	            	if(!st.equalsIgnoreCase(data.get(i).getStname()) && k!=1) {
	            		
	            		CommonFunctions.insertCell3(table, "State Total", Element.ALIGN_CENTER, 4, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_village), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.format(Locale.US, "%.2f", tproj_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", tproj_outlay), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_ngo_name), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_ngo_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_ngo_vill), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_swck_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.format(Locale.US, "%.2f", tfund_expenditure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, "", Element.ALIGN_CENTER, 1, 1, bf10Bold);
	            		
	            		tno_gp=0;
	            		tno_gp=tno_gp+data.get(i).getNo_gp();
	            		tno_village=0;
	            		tno_village=tno_village+data.get(i).getNo_village();
	            		tproj_area = BigDecimal.valueOf(0);
	            		tproj_area=tproj_area.add(new BigDecimal(data.get(i).getProj_area()));
	            		tproj_outlay = BigDecimal.valueOf(0);
	            		tproj_outlay=tproj_outlay.add(new BigDecimal(data.get(i).getProj_outlay()));
	            		tno_ngo_name=0;
	            		tno_ngo_name=tno_ngo_name+data.get(i).getNo_ngo_name();
	            		tno_ngo_gp=0;
	            		tno_ngo_gp=tno_ngo_gp+data.get(i).getNo_ngo_gp();
	            		tno_ngo_vill=0;
	            		tno_ngo_vill=tno_ngo_vill+data.get(i).getNo_ngo_vill();
	            		tno_swck_gp=0;
	            		tno_swck_gp=tno_swck_gp+data.get(i).getNo_swck_gp();
	            		tfund_expenditure= BigDecimal.valueOf(0);
	            		tfund_expenditure=tfund_expenditure.add(new BigDecimal(data.get(i).getFund_expenditure()));
	            	}
	            	
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                if(data.get(i).getStname()!=st) {
	                	 CommonFunctions.insertCell(table, data.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
					}
	                if(data.get(i).getDistname()!=dist) {
	                	 CommonFunctions.insertCell(table, data.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
	                	 dist=data.get(i).getDistname();
					}
					else {
						CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
					}
	                CommonFunctions.insertCell(table, data.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(data.get(i).getNo_gp()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                CommonFunctions.insertCell(table, String.valueOf(data.get(i).getNo_village()) , Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(data.get(i).getProj_area()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(data.get(i).getProj_outlay()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(data.get(i).getNo_ngo_name()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(data.get(i).getNo_ngo_gp()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(data.get(i).getNo_ngo_vill()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(data.get(i).getNo_swck_gp()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(data.get(i).getFund_expenditure()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(data.get(i).getFund_per_exp()) +" %", Element.ALIGN_RIGHT, 1, 1, bf8);
	       
	                if(st.equalsIgnoreCase(data.get(i).getStname()) || k==1) 
	                {
	            		tno_gp=tno_gp+data.get(i).getNo_gp();
	            		tno_village=tno_village+data.get(i).getNo_village();
	            		tproj_area=tproj_area.add(new BigDecimal(data.get(i).getProj_area()));
	            		tproj_outlay=tproj_outlay.add(new BigDecimal(data.get(i).getProj_outlay()));
	            		tno_ngo_name=tno_ngo_name+data.get(i).getNo_ngo_name();
	            		tno_ngo_gp=tno_ngo_gp+data.get(i).getNo_ngo_gp();
	            		tno_ngo_vill=tno_ngo_vill+data.get(i).getNo_ngo_vill();
	            		tno_swck_gp=tno_swck_gp+data.get(i).getNo_swck_gp();
	            		tfund_expenditure=tfund_expenditure.add(new BigDecimal(data.get(i).getFund_expenditure()));
	                }
	                
	                if(k==data.size()) {
	                	
	                	CommonFunctions.insertCell3(table, "State Total", Element.ALIGN_CENTER, 4, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_village), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.format(Locale.US, "%.2f", tproj_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", tproj_outlay), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_ngo_name), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_ngo_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_ngo_vill), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.valueOf(tno_swck_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, String.format(Locale.US, "%.2f", tfund_expenditure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            		CommonFunctions.insertCell3(table, "", Element.ALIGN_CENTER, 1, 1, bf10Bold);
	                }
	                
	                st=data.get(i).getStname();
	                k = k + 1;
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 4, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_village), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.2f", totproj_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
        		CommonFunctions.insertCell3(table, String.format(Locale.US, "%.4f", totproj_outlay), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_ngo_name), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_ngo_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_ngo_vill), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_swck_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.format(Locale.US, "%.2f", totfund_expenditure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, "", Element.ALIGN_RIGHT, 1, 1, bf10Bold);

	            document.add(table);
	            table = new PdfPTable(1);
				table.setWidthPercentage(70);
				table.setSpacingBefore(15f);
				table.setSpacingAfter(0f);
				CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
				CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
				document.add(table);
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        }

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=JP1-Report.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}

	
	
	@RequestMapping(value="/getListofNGONameWithGPandVillage", method = RequestMethod.POST)
	@ResponseBody
	public List<JanbhagidariPratiyogitaBean> getListofNGONameWithGPandVillage(HttpServletRequest request, 
			@RequestParam(value ="projid") Integer projid) throws ParseException
	{
		List<JanbhagidariPratiyogitaBean> sublist = new ArrayList<JanbhagidariPratiyogitaBean>();
		
		
		sublist=serk.getListofNGONameWithGPandVillage(projid);
		
		
		return sublist;
	}
	
	@RequestMapping(value="/getListofswckGPand", method = RequestMethod.POST)
	@ResponseBody
	public List<JanbhagidariPratiyogitaBean> getListofswckGPand(HttpServletRequest request, 
			@RequestParam(value ="projid") Integer projid) throws ParseException
	{
		List<JanbhagidariPratiyogitaBean> sublist = new ArrayList<JanbhagidariPratiyogitaBean>();
		
		
		sublist=serk.getListofswckGPand(projid);
		
		
		return sublist;
	}
	
	@RequestMapping(value="/janbhagidariPratiyogitaStatusReport", method = RequestMethod.GET)
	public ModelAndView janbhagidariPratiyogitaStatusReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
		try {
			
			mav = new ModelAndView("reports/janbhagidariPratiyogitaStatus");
			
			
			data=serk.getjanbhagidariPratiyogitaStatusReport();
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			
			int total_projectt=0, proj_repotedt=0, proj_notrepotedt=0;
			
			int totno_gp=0;
			int totno_village=0;
			BigDecimal totproj_area = BigDecimal.valueOf(0);
			BigDecimal totproj_outlay = BigDecimal.valueOf(0);
			int totno_ngo_name=0;
			int totno_ngo_gp=0;
			int totno_ngo_vill=0;
			int totno_swck_gp=0;
			BigDecimal fund_outlayex = BigDecimal.valueOf(0);
			BigDecimal totfund_expenditure = BigDecimal.valueOf(0);
			
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					total_projectt=total_projectt+bean.getTotal_project();
					proj_repotedt=proj_repotedt+bean.getProj_repoted();
					proj_notrepotedt=proj_notrepotedt+bean.getProj_notrepoted();
					
					totno_gp=totno_gp+bean.getNo_gp();
					totno_village=totno_village+bean.getNo_village();
					totproj_area=totproj_area.add(new BigDecimal(bean.getProj_area()));
					totproj_outlay=totproj_outlay.add(new BigDecimal(bean.getProj_outlay()));
					totno_ngo_name=totno_ngo_name+bean.getNo_ngo_name();
					totno_ngo_gp=totno_ngo_gp+bean.getNo_ngo_gp();
					totno_ngo_vill=totno_ngo_vill+bean.getNo_ngo_vill();
					totno_swck_gp=totno_swck_gp+bean.getNo_swck_gp();
					fund_outlayex=fund_outlayex.add(new BigDecimal(bean.getFund_outlayex()));
					totfund_expenditure=totfund_expenditure.add(new BigDecimal(bean.getFund_expenditure()));
					
				}
			}	
			
			mav.addObject("total_projectt",total_projectt);
			mav.addObject("proj_repotedt",proj_repotedt);
			mav.addObject("proj_notrepotedt",proj_notrepotedt);
			
			mav.addObject("totno_gp",totno_gp);
			mav.addObject("totno_village",totno_village);
			mav.addObject("totproj_area",totproj_area);
			mav.addObject("totproj_outlay",totproj_outlay);
			mav.addObject("totno_ngo_name",totno_ngo_name);
			mav.addObject("totno_ngo_gp",totno_ngo_gp);
			mav.addObject("totno_ngo_vill",totno_ngo_vill);
			mav.addObject("totno_swck_gp",totno_swck_gp);
			mav.addObject("fund_outlayex",fund_outlayex);
			mav.addObject("totfund_expenditure",totfund_expenditure);
			
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}  
	
	@RequestMapping(value = "/JanbhagidariStatewiseCurrentStatusPDF", method = RequestMethod.POST)
	public ModelAndView JanbhagidariStatewiseCurrentStatusPDF(HttpServletRequest request, HttpServletResponse response) {
		
	  
	    try {
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("Janbhagidari State-wise Current Status");
	        document.addCreationDate();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter writer = PdfWriter.getInstance(document, baos);

	        document.open();

	        Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC);
	        Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD);
	        Font bf8 = new Font(FontFamily.HELVETICA, 8);
	        Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
	        Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

	        PdfPTable table = null;
	        document.newPage();
	        Paragraph paragraph3 = null;
	        Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);

	        paragraph3 = new Paragraph("State wise Current Status of Watersheed Janbhagidari", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(15);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	      //  table.setHeaderRows(3);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Reported Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Not Reported Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Basic Information about Project", Element.ALIGN_CENTER, 4, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Identification & Engagement of NGOs", Element.ALIGN_CENTER, 3, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Opening of SWCK Bank Account at Gram Panchayat", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Fund Utilization under WDC-PMKSY2.0", Element.ALIGN_CENTER, 2, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "Total No. of Gram Panchayat", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Villages", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area Allocated for Project (ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Project Outlay (Rs. In Lakh)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total No. of NGOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Gram Panchayat NGOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Villages NGOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Fund Outlay", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Fund Expenditure", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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
	        List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
	        
	        data=serk.getjanbhagidariPratiyogitaStatusReport();
			
			int total_projectt=0, proj_repotedt=0, proj_notrepotedt=0;
			
			int totno_gp=0;
			int totno_village=0;
			BigDecimal totproj_area = BigDecimal.valueOf(0);
			BigDecimal totproj_outlay = BigDecimal.valueOf(0);
			int totno_ngo_name=0;
			int totno_ngo_gp=0;
			int totno_ngo_vill=0;
			int totno_swck_gp=0;
			BigDecimal fund_outlayex = BigDecimal.valueOf(0);
			BigDecimal totfund_expenditure = BigDecimal.valueOf(0);
			int k = 1;
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, bean.getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProj_repoted()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProj_notrepoted()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_gp()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_village()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProj_area()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProj_outlay()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_ngo_name()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_ngo_gp()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_ngo_vill()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_swck_gp()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getFund_outlayex()), Element.ALIGN_RIGHT, 1,1, bf8); 
	                CommonFunctions.insertCell(table, String.valueOf(bean.getFund_expenditure()), Element.ALIGN_RIGHT, 1,1, bf8);
	                
	               
	              
					
					total_projectt=total_projectt+bean.getTotal_project();
					proj_repotedt=proj_repotedt+bean.getProj_repoted();
					proj_notrepotedt=proj_notrepotedt+bean.getProj_notrepoted();
					totno_gp=totno_gp+bean.getNo_gp();
					totno_village=totno_village+bean.getNo_village();
					totproj_area=totproj_area.add(new BigDecimal(bean.getProj_area()));
					totproj_outlay=totproj_outlay.add(new BigDecimal(bean.getProj_outlay()));
					totno_ngo_name=totno_ngo_name+bean.getNo_ngo_name();
					totno_ngo_gp=totno_ngo_gp+bean.getNo_ngo_gp();
					totno_ngo_vill=totno_ngo_vill+bean.getNo_ngo_vill();
					totno_swck_gp=totno_swck_gp+bean.getNo_swck_gp();
					fund_outlayex=fund_outlayex.add(new BigDecimal(bean.getFund_outlayex()));
					totfund_expenditure=totfund_expenditure.add(new BigDecimal(bean.getFund_expenditure()));
					
					  k = k + 1;
					  
				}
			}	

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_projectt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(proj_repotedt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(proj_notrepotedt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_village), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totproj_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totproj_outlay), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_ngo_name), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_ngo_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_ngo_vill), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_swck_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(fund_outlayex), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totfund_expenditure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

	            document.add(table);
	            table = new PdfPTable(1);
				table.setWidthPercentage(70);
				table.setSpacingBefore(15f);
				table.setSpacingAfter(0f);
				CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
				CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
				document.add(table);
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=State wise Current Status of Watersheed Janbhagidari.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/ExcelJanbhagidariStateWiseCurrentStatus", method = RequestMethod.POST)
	@ResponseBody
	public String ExcelJanbhagidariStateWiseCurrentStatus(HttpServletRequest request, HttpServletResponse response) 
	{
		//String state=request.getParameter("state");
		
		List<JanbhagidariPratiyogitaBean> list = new ArrayList<JanbhagidariPratiyogitaBean>();
        
		list=serk.getjanbhagidariPratiyogitaStatusReport();
		
		int total_projectt=0, proj_repotedt=0, proj_notrepotedt=0;
		
		int totno_gp=0;
		int totno_village=0;
		BigDecimal totproj_area = BigDecimal.valueOf(0);
		BigDecimal totproj_outlay = BigDecimal.valueOf(0);
		int totno_ngo_name=0;
		int totno_ngo_gp=0;
		int totno_ngo_vill=0;
		int totno_swck_gp=0;
		BigDecimal fund_outlayex = BigDecimal.valueOf(0);
		BigDecimal totfund_expenditure = BigDecimal.valueOf(0);
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("State wise Current Status of Watersheed Janbhagidari");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "State wise Current Status of Watersheed Janbhagidari";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 14, areaAmtValDetail, workbook);
			
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
	        mergedRegion = new CellRangeAddress(5,5,5,8); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,9,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,12,12); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,13,14); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
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
			cell.setCellValue("Total No. of Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total No. of Reported Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Total No. of Not Reported Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Basic Information about Project");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =6; i<9;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Identification & Engagement of NGOs");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =10; i<=11;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Opening of SWCK Bank Account at Gram Panchayat");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(13);
			cell.setCellValue("Fund Utilization under WDC-PMKSY2.0");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(14).setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<5;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Total No. of Gram Panchayat");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total No. of Villages");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellValue("Total Area Allocated for Project (ha.)");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("Total Project Outlay (Rs. In Lakh)");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Total No. of NGOs");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(10);
			cell.setCellValue("Total No. of Gram Panchayat NGO");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(11);
			cell.setCellValue("Total No. of Villages NGO");  
			cell.setCellStyle(style);
			
			rowhead1.createCell(12).setCellStyle(style);
			
			cell = rowhead1.createCell(13);
			cell.setCellValue("Total Fund Outlay");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(14);
			cell.setCellValue("Total Fund Expenditure");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<15;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        int sno = 1;
	        int rowno  = 8;
	        
	        for(JanbhagidariPratiyogitaBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getSt_name());
	        	row.createCell(2).setCellValue(bean.getTotal_project());
				row.createCell(3).setCellValue(bean.getProj_repoted());  
				row.createCell(4).setCellValue(bean.getProj_notrepoted()); 
				row.createCell(5).setCellValue(bean.getNo_gp());  
				row.createCell(6).setCellValue(bean.getNo_village());
				row.createCell(7).setCellValue(new BigDecimal(bean.getProj_area()).doubleValue());
				row.createCell(8).setCellValue(new BigDecimal(bean.getProj_outlay()).doubleValue());
				row.createCell(9).setCellValue(bean.getNo_ngo_name());
				row.createCell(10).setCellValue(bean.getNo_ngo_gp());
				row.createCell(11).setCellValue(bean.getNo_ngo_vill());
				row.createCell(12).setCellValue(bean.getNo_swck_gp());
				row.createCell(13).setCellValue(new BigDecimal(bean.getFund_outlayex()).doubleValue());
				row.createCell(14).setCellValue(new BigDecimal(bean.getFund_expenditure()).doubleValue());
	        	
				total_projectt=total_projectt+bean.getTotal_project();
				proj_repotedt=proj_repotedt+bean.getProj_repoted();
				proj_notrepotedt=proj_notrepotedt+bean.getProj_notrepoted();
				totno_gp=totno_gp+bean.getNo_gp();
				totno_village=totno_village+bean.getNo_village();
				totproj_area=totproj_area.add(new BigDecimal(bean.getProj_area()));
				totproj_outlay=totproj_outlay.add(new BigDecimal(bean.getProj_outlay()));
				totno_ngo_name=totno_ngo_name+bean.getNo_ngo_name();
				totno_ngo_gp=totno_ngo_gp+bean.getNo_ngo_gp();
				totno_ngo_vill=totno_ngo_vill+bean.getNo_ngo_vill();
				totno_swck_gp=totno_swck_gp+bean.getNo_swck_gp();
				fund_outlayex=fund_outlayex.add(new BigDecimal(bean.getFund_outlayex()));
				totfund_expenditure=totfund_expenditure.add(new BigDecimal(bean.getFund_expenditure()));
				
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
	        cell.setCellValue(total_projectt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(proj_repotedt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(proj_notrepotedt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totno_gp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totno_village);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totproj_area.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totproj_outlay.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totno_ngo_name);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totno_ngo_gp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totno_ngo_vill);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totno_swck_gp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(fund_outlayex.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totfund_expenditure.doubleValue());
	        cell.setCellStyle(style1);
	       
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 14);
	        String fileName = "attachment; filename=State wise Current Status of Watersheed Janbhagidari.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}
	
	
	@RequestMapping(value="/getdistrictWiseCurrentStatusJanbhagidari", method = RequestMethod.GET)
	public ModelAndView getdistrictWiseCurrentStatusJanbhagidari(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id)
	{
		ModelAndView mav = new ModelAndView();
		String stname= request.getParameter("stname");
		List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
		try {
			
			mav = new ModelAndView("reports/janbhagidariPratiyogitaDistrictStatus");
			
			data=serk.getjanbhagidariPratiyogitaDistStatusReport(id);
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			
			int total_projectt=0, proj_repotedt=0, proj_notrepotedt=0;
			int totno_gp=0;
			int totno_village=0;
			BigDecimal totproj_area = BigDecimal.valueOf(0);
			BigDecimal totproj_outlay = BigDecimal.valueOf(0);
			int totno_ngo_name=0;
			int totno_ngo_gp=0;
			int totno_ngo_vill=0;
			int totno_swck_gp=0;
			BigDecimal fund_outlayex = BigDecimal.valueOf(0);
			BigDecimal totfund_expenditure = BigDecimal.valueOf(0);
			
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					total_projectt=total_projectt+bean.getTotal_project();
					proj_repotedt=proj_repotedt+bean.getProj_repoted();
					proj_notrepotedt=proj_notrepotedt+bean.getProj_notrepoted();
					
					totno_gp=totno_gp+bean.getNo_gp();
					totno_village=totno_village+bean.getNo_village();
					totproj_area=totproj_area.add(new BigDecimal(bean.getProj_area()));
					totproj_outlay=totproj_outlay.add(new BigDecimal(bean.getProj_outlay()));
					totno_ngo_name=totno_ngo_name+bean.getNo_ngo_name();
					totno_ngo_gp=totno_ngo_gp+bean.getNo_ngo_gp();
					totno_ngo_vill=totno_ngo_vill+bean.getNo_ngo_vill();
					totno_swck_gp=totno_swck_gp+bean.getNo_swck_gp();
					fund_outlayex=fund_outlayex.add(new BigDecimal(bean.getFund_outlayex()));
					totfund_expenditure=totfund_expenditure.add(new BigDecimal(bean.getFund_expenditure()));
					
				}
			}	
			
			mav.addObject("total_projectt",total_projectt);
			mav.addObject("proj_repotedt",proj_repotedt);
			mav.addObject("proj_notrepotedt",proj_notrepotedt);
			
			mav.addObject("totno_gp",totno_gp);
			mav.addObject("totno_village",totno_village);
			mav.addObject("totproj_area",totproj_area);
			mav.addObject("totproj_outlay",totproj_outlay);
			mav.addObject("totno_ngo_name",totno_ngo_name);
			mav.addObject("totno_ngo_gp",totno_ngo_gp);
			mav.addObject("totno_ngo_vill",totno_ngo_vill);
			mav.addObject("totno_swck_gp",totno_swck_gp);
			mav.addObject("fund_outlayex",fund_outlayex);
			mav.addObject("totfund_expenditure",totfund_expenditure);
			mav.addObject("stname",stname);
			mav.addObject("stcode",id);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value = "/districtWiseCurrentStatusJanbhagidariPDF", method = RequestMethod.POST)
	public ModelAndView districtWiseCurrentStatusJanbhagidariPDF(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
    	String stname = request.getParameter("stname");
	  
	    try {
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("Janbhagidari District-wise Current Status");
	        document.addCreationDate();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter writer = PdfWriter.getInstance(document, baos);

	        document.open();

	        Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC);
	        Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD);
	        Font bf8 = new Font(FontFamily.HELVETICA, 8);
	        Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
	        Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

	        PdfPTable table = null;
	        document.newPage();
	        Paragraph paragraph3 = null;
	        Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);

	        paragraph3 = new Paragraph("District wise Current Status of Watersheed Janbhagidari", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(15);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(4);

	        CommonFunctions.insertCellHeader(table, "State Name "+stname, Element.ALIGN_LEFT, 15, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Reported Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Not Reported Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Basic Information about Project", Element.ALIGN_CENTER, 4, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Identification & Engagement of NGOs", Element.ALIGN_CENTER, 3, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Opening of SWCK Bank Account at Gram Panchayat", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Fund Utilization under WDC-PMKSY2.0", Element.ALIGN_CENTER, 2, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "Total No. of Gram Panchayat", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Villages", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area Allocated for Project (ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Project Outlay (Rs. In Lakh)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total No. of NGOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Gram Panchayat NGOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total No. of Villages NGOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Total Fund Outlay", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Fund Expenditure", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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
	        
	        List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
	        
	        data=serk.getjanbhagidariPratiyogitaDistStatusReport(Integer.parseInt(id));
			
			int total_projectt=0, proj_repotedt=0, proj_notrepotedt=0;
			int totno_gp=0;
			int totno_village=0;
			BigDecimal totproj_area = BigDecimal.valueOf(0);
			BigDecimal totproj_outlay = BigDecimal.valueOf(0);
			int totno_ngo_name=0;
			int totno_ngo_gp=0;
			int totno_ngo_vill=0;
			int totno_swck_gp=0;
			BigDecimal fund_outlayex = BigDecimal.valueOf(0);
			BigDecimal totfund_expenditure = BigDecimal.valueOf(0);
			int k = 1;
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, bean.getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProj_repoted()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProj_notrepoted()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_gp()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_village()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProj_area()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProj_outlay()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_ngo_name()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_ngo_gp()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_ngo_vill()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_swck_gp()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getFund_outlayex()), Element.ALIGN_RIGHT, 1,1, bf8); 
	                CommonFunctions.insertCell(table, String.valueOf(bean.getFund_expenditure()), Element.ALIGN_RIGHT, 1,1, bf8);
					
					total_projectt=total_projectt+bean.getTotal_project();
					proj_repotedt=proj_repotedt+bean.getProj_repoted();
					proj_notrepotedt=proj_notrepotedt+bean.getProj_notrepoted();
					totno_gp=totno_gp+bean.getNo_gp();
					totno_village=totno_village+bean.getNo_village();
					totproj_area=totproj_area.add(new BigDecimal(bean.getProj_area()));
					totproj_outlay=totproj_outlay.add(new BigDecimal(bean.getProj_outlay()));
					totno_ngo_name=totno_ngo_name+bean.getNo_ngo_name();
					totno_ngo_gp=totno_ngo_gp+bean.getNo_ngo_gp();
					totno_ngo_vill=totno_ngo_vill+bean.getNo_ngo_vill();
					totno_swck_gp=totno_swck_gp+bean.getNo_swck_gp();
					fund_outlayex=fund_outlayex.add(new BigDecimal(bean.getFund_outlayex()));
					totfund_expenditure=totfund_expenditure.add(new BigDecimal(bean.getFund_expenditure()));
					
					  k = k + 1;
				}
			}	

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_projectt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(proj_repotedt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(proj_notrepotedt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_village), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totproj_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totproj_outlay), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_ngo_name), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_ngo_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_ngo_vill), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totno_swck_gp), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(fund_outlayex), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totfund_expenditure), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

	            document.add(table);
	            table = new PdfPTable(1);
				table.setWidthPercentage(70);
				table.setSpacingBefore(15f);
				table.setSpacingAfter(0f);
				CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
				CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
				document.add(table);
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=District wise Current Status of Watersheed Janbhagidari.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/ExceldistrictWiseCurrentStatusJanbhagidari", method = RequestMethod.POST)
	@ResponseBody
	public String ExceldistrictWiseCurrentStatusJanbhagidari(HttpServletRequest request, HttpServletResponse response) 
	{
		String id = request.getParameter("id");
    	String stname = request.getParameter("stname");
		
		List<JanbhagidariPratiyogitaBean> list = new ArrayList<JanbhagidariPratiyogitaBean>();
        
		list=serk.getjanbhagidariPratiyogitaDistStatusReport(Integer.parseInt(id));
		
		int total_projectt=0, proj_repotedt=0, proj_notrepotedt=0;
		
		int totno_gp=0;
		int totno_village=0;
		BigDecimal totproj_area = BigDecimal.valueOf(0);
		BigDecimal totproj_outlay = BigDecimal.valueOf(0);
		int totno_ngo_name=0;
		int totno_ngo_gp=0;
		int totno_ngo_vill=0;
		int totno_swck_gp=0;
		BigDecimal fund_outlayex = BigDecimal.valueOf(0);
		BigDecimal totfund_expenditure = BigDecimal.valueOf(0);
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("District wise Current Status of Watersheed Janbhagidari");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "District wise Current Status of Watersheed Janbhagidari";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 14, areaAmtValDetail, workbook);
			
			
			mergedRegion = new CellRangeAddress(5,5,0,14);
	        sheet.addMergedRegion(mergedRegion);
			
			mergedRegion = new CellRangeAddress(6,7,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6,7,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,3,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,4,4); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,5,8); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,9,11); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,12,12); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,13,14); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row rowDetail = sheet.createRow(5);
			
			Cell cell = rowDetail.createCell(0);
			cell.setCellValue("State : "+ stname);  
			cell.setCellStyle(style);
	        
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
			cell.setCellValue("Total No. of Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Total No. of Reported Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Total No. of Not Reported Project");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Basic Information about Project");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =6; i<9;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Identification & Engagement of NGOs");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =10; i<=11;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(12);
			cell.setCellValue("Opening of SWCK Bank Account at Gram Panchayat");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(13);
			cell.setCellValue("Fund Utilization under WDC-PMKSY2.0");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(14).setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int i =0; i<5;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Total No. of Gram Panchayat");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total No. of Villages");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellValue("Total Area Allocated for Project (ha.)");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("Total Project Outlay (Rs. In Lakh)");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Total No. of NGOs");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(10);
			cell.setCellValue("Total No. of Gram Panchayat NGO");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(11);
			cell.setCellValue("Total No. of Villages NGO");  
			cell.setCellStyle(style);
			
			rowhead1.createCell(12).setCellStyle(style);
			
			cell = rowhead1.createCell(13);
			cell.setCellValue("Total Fund Outlay");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(14);
			cell.setCellValue("Total Fund Expenditure");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(8);
			for(int i=0;i<15;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        int sno = 1;
	        int rowno  = 9;
	        
	        for(JanbhagidariPratiyogitaBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDist_name());
	        	row.createCell(2).setCellValue(bean.getTotal_project());
				row.createCell(3).setCellValue(bean.getProj_repoted());  
				row.createCell(4).setCellValue(bean.getProj_notrepoted()); 
				row.createCell(5).setCellValue(bean.getNo_gp());  
				row.createCell(6).setCellValue(bean.getNo_village());
				row.createCell(7).setCellValue(new BigDecimal(bean.getProj_area()).doubleValue());
				row.createCell(8).setCellValue(new BigDecimal(bean.getProj_outlay()).doubleValue());
				row.createCell(9).setCellValue(bean.getNo_ngo_name());
				row.createCell(10).setCellValue(bean.getNo_ngo_gp());
				row.createCell(11).setCellValue(bean.getNo_ngo_vill());
				row.createCell(12).setCellValue(bean.getNo_swck_gp());
				row.createCell(13).setCellValue(new BigDecimal(bean.getFund_outlayex()).doubleValue());
				row.createCell(14).setCellValue(new BigDecimal(bean.getFund_expenditure()).doubleValue());
				
	        	
				total_projectt=total_projectt+bean.getTotal_project();
				proj_repotedt=proj_repotedt+bean.getProj_repoted();
				proj_notrepotedt=proj_notrepotedt+bean.getProj_notrepoted();
				totno_gp=totno_gp+bean.getNo_gp();
				totno_village=totno_village+bean.getNo_village();
				totproj_area=totproj_area.add(new BigDecimal(bean.getProj_area()));
				totproj_outlay=totproj_outlay.add(new BigDecimal(bean.getProj_outlay()));
				totno_ngo_name=totno_ngo_name+bean.getNo_ngo_name();
				totno_ngo_gp=totno_ngo_gp+bean.getNo_ngo_gp();
				totno_ngo_vill=totno_ngo_vill+bean.getNo_ngo_vill();
				totno_swck_gp=totno_swck_gp+bean.getNo_swck_gp();
				fund_outlayex=fund_outlayex.add(new BigDecimal(bean.getFund_outlayex()));
				totfund_expenditure=totfund_expenditure.add(new BigDecimal(bean.getFund_expenditure()));
		        
				
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
	        cell.setCellValue(total_projectt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(proj_repotedt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(proj_notrepotedt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totno_gp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totno_village);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totproj_area.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totproj_outlay.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totno_ngo_name);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totno_ngo_gp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totno_ngo_vill);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totno_swck_gp);
	        cell.setCellStyle(style1);
	        cell = row.createCell(13);
	        cell.setCellValue(fund_outlayex.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(14);
	        cell.setCellValue(totfund_expenditure.doubleValue());
	        cell.setCellStyle(style1);
	       
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 14);
	        String fileName = "attachment; filename=District wise Current Status of Watersheed Janbhagidari.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}
	
	
	@RequestMapping(value="/getProjectWiseCurrentStatusJanbhagidari", method = RequestMethod.GET)
	public ModelAndView getProjectWiseCurrentStatusJanbhagidari(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id)
	{
		ModelAndView mav = new ModelAndView();
		String stname= request.getParameter("stname");
		String distname= request.getParameter("distname");
		String stid= request.getParameter("stid");
		
		List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
		try {
			
			mav = new ModelAndView("reports/janbhagidariPratiyogitaProjStatus");
			
			data=serk.getjanbhagidariPratiyogitaDistStatusReport(id);
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			
			int total_projectt=0, proj_repotedt=0, proj_notrepotedt=0;
			int totno_gp=0;
			int totno_village=0;
			BigDecimal totproj_area = BigDecimal.valueOf(0);
			BigDecimal totproj_outlay = BigDecimal.valueOf(0);
			int totno_ngo_name=0;
			int totno_ngo_gp=0;
			int totno_ngo_vill=0;
			int totno_swck_gp=0;
			BigDecimal fund_outlayex = BigDecimal.valueOf(0);
			BigDecimal totfund_expenditure = BigDecimal.valueOf(0);
			
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					total_projectt=total_projectt+bean.getTotal_project();
					proj_repotedt=proj_repotedt+bean.getProj_repoted();
					proj_notrepotedt=proj_notrepotedt+bean.getProj_notrepoted();
					
					totno_gp=totno_gp+bean.getNo_gp();
					totno_village=totno_village+bean.getNo_village();
					totproj_area=totproj_area.add(new BigDecimal(bean.getProj_area()));
					totproj_outlay=totproj_outlay.add(new BigDecimal(bean.getProj_outlay()));
					totno_ngo_name=totno_ngo_name+bean.getNo_ngo_name();
					totno_ngo_gp=totno_ngo_gp+bean.getNo_ngo_gp();
					totno_ngo_vill=totno_ngo_vill+bean.getNo_ngo_vill();
					totno_swck_gp=totno_swck_gp+bean.getNo_swck_gp();
					fund_outlayex=fund_outlayex.add(new BigDecimal(bean.getFund_outlayex()));
					totfund_expenditure=totfund_expenditure.add(new BigDecimal(bean.getFund_expenditure()));
					
				}
			}	
			
			mav.addObject("total_projectt",total_projectt);
			mav.addObject("proj_repotedt",proj_repotedt);
			mav.addObject("proj_notrepotedt",proj_notrepotedt);
			
			mav.addObject("totno_gp",totno_gp);
			mav.addObject("totno_village",totno_village);
			mav.addObject("totproj_area",totproj_area);
			mav.addObject("totproj_outlay",totproj_outlay);
			mav.addObject("totno_ngo_name",totno_ngo_name);
			mav.addObject("totno_ngo_gp",totno_ngo_gp);
			mav.addObject("totno_ngo_vill",totno_ngo_vill);
			mav.addObject("totno_swck_gp",totno_swck_gp);
			mav.addObject("fund_outlayex",fund_outlayex);
			mav.addObject("totfund_expenditure",totfund_expenditure);
			mav.addObject("stname",stname);
			mav.addObject("dcode",id);
			mav.addObject("distname",distname);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value="/janbhagidariPratiyogitaActivitiesStatusReport", method = RequestMethod.GET)
	public ModelAndView janbhagidariPratiyogitaActivitiesStatusReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		
		List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
		try {
			
			mav = new ModelAndView("reports/janbhagidariPratiyogitaActivitiesStatus");
			
			data=serk.getjanbhagidariPratiyogitaActivitiesStatus();
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			
			int proj_repotedt=0, no_work_creationt=0, no_work_repairt=0, no_work_plantt=0, no_work_productiont=0, no_work_compt=0;
			
			BigDecimal estimate_workt = BigDecimal.valueOf(0);
			BigDecimal villaget = BigDecimal.valueOf(0);
			BigDecimal ngot=BigDecimal.valueOf(0);
			BigDecimal corporatet = BigDecimal.valueOf(0);
			
			
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					proj_repotedt=proj_repotedt+bean.getProj_repoted();
					no_work_creationt=no_work_creationt+bean.getNo_work_creation();
					no_work_repairt=no_work_repairt+bean.getNo_work_repair();
					no_work_plantt=no_work_plantt+bean.getNo_work_plant();
					no_work_productiont=no_work_productiont+bean.getNo_work_production();
					no_work_compt=no_work_compt+bean.getNo_work_comp();
					
					estimate_workt=estimate_workt.add(bean.getEstimate_work());
					villaget=villaget.add(bean.getVillage());
					ngot=ngot.add(bean.getNgo());
					corporatet=corporatet.add(bean.getCorporate());
					
				}
			}	
			
			mav.addObject("proj_repotedt",proj_repotedt);
			mav.addObject("no_work_creationt",no_work_creationt);
			mav.addObject("no_work_repairt",no_work_repairt);
			mav.addObject("no_work_plantt",no_work_plantt);
			mav.addObject("no_work_productiont",no_work_productiont);
			mav.addObject("no_work_compt",no_work_compt);
			mav.addObject("estimate_workt",estimate_workt);
			mav.addObject("villaget",villaget);
			mav.addObject("ngot",ngot);
			mav.addObject("corporatet",corporatet);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}  
	
	@RequestMapping(value = "/JanbhagidariStatewiseActivitiesStatusPDF", method = RequestMethod.POST)
	public ModelAndView JanbhagidariStatewiseActivitiesStatusPDF(HttpServletRequest request, HttpServletResponse response) {
		
	  
	    try {
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("JP-3");
	        document.addCreationDate();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter writer = PdfWriter.getInstance(document, baos);

	        document.open();

	        Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC);
	        Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD);
	        Font bf8 = new Font(FontFamily.HELVETICA, 8);
	        Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
	        Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

	        PdfPTable table = null;
	        document.newPage();
	        Paragraph paragraph3 = null;
	        Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);

	        paragraph3 = new Paragraph("JP3 - State wise Watershed Janbhagidari Activities Details", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(12);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	      //  table.setHeaderRows(3);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Projects where Watersheed Janbhagidari Organized", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "No. of Work to be done through Janbhagidari Activities", Element.ALIGN_CENTER, 4, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Tentative Community Contribution Percentage (%)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Estimated Value of Work to be done through Janbhagidari (Rs. in Lakh)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Achievements made so for (No. of Work Completed)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	       

	        CommonFunctions.insertCellHeader(table, "NRM/SWC Structure Creation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "NRM/SWC Structure Repair /Rejuvenation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Plantation Related Work", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Production System Related Work", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Villagers", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "NGOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Corporates", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
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
	       
	        List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
	        
	        data=serk.getjanbhagidariPratiyogitaActivitiesStatus();
			
			int proj_repotedt=0, no_work_creationt=0, no_work_repairt=0, no_work_plantt=0, no_work_productiont=0, no_work_compt=0;
			
			BigDecimal estimate_workt = BigDecimal.valueOf(0);
			BigDecimal villaget = BigDecimal.valueOf(0);
			BigDecimal ngot=BigDecimal.valueOf(0);
			BigDecimal corporatet = BigDecimal.valueOf(0);
			
			int k = 1;
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, bean.getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProj_repoted()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_work_creation()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_work_repair()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_work_plant()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_work_production()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                CommonFunctions.insertCell(table, String.valueOf(bean.getVillage()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNgo()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getCorporate()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getEstimate_work()), Element.ALIGN_RIGHT, 1,1, bf8);
	                
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_work_comp()), Element.ALIGN_RIGHT, 1,1, bf8);
					
					proj_repotedt=proj_repotedt+bean.getProj_repoted();
					no_work_creationt=no_work_creationt+bean.getNo_work_creation();
					no_work_repairt=no_work_repairt+bean.getNo_work_repair();
					no_work_plantt=no_work_plantt+bean.getNo_work_plant();
					no_work_productiont=no_work_productiont+bean.getNo_work_production();
					no_work_compt=no_work_compt+bean.getNo_work_comp();
					
					estimate_workt=estimate_workt.add(bean.getEstimate_work());
					villaget=villaget.add(bean.getVillage());
					ngot=ngot.add(bean.getNgo());
					corporatet=corporatet.add(bean.getCorporate());
					k = k + 1;
					
				}
			}	
			BigDecimal nostate = BigDecimal.valueOf(30);

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(proj_repotedt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(no_work_creationt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(no_work_repairt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(no_work_plantt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(no_work_productiont), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(villaget.divide(nostate, 2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(ngot.divide(nostate, 2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(corporatet.divide(nostate, 2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(estimate_workt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(no_work_compt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            

	            document.add(table);
	            table = new PdfPTable(1);
				table.setWidthPercentage(70);
				table.setSpacingBefore(15f);
				table.setSpacingAfter(0f);
				CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
				CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
				document.add(table);
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=JP-3.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/ExcelJanbhagidariStateWiseActivitiesStatus", method = RequestMethod.POST)
	@ResponseBody
	public String ExcelJanbhagidariStateWiseActivitiesStatus(HttpServletRequest request, HttpServletResponse response) 
	{

			List<JanbhagidariPratiyogitaBean> list = new ArrayList<JanbhagidariPratiyogitaBean>();
	        
			list=serk.getjanbhagidariPratiyogitaActivitiesStatus();
			
			int proj_repotedt=0, no_work_creationt=0, no_work_repairt=0, no_work_plantt=0, no_work_productiont=0, no_work_compt=0;
			
			BigDecimal estimate_workt = BigDecimal.valueOf(0);
			BigDecimal villaget = BigDecimal.valueOf(0);
			BigDecimal ngot=BigDecimal.valueOf(0);
			BigDecimal corporatet = BigDecimal.valueOf(0);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("State wise Watershed Janbhagidari Activities Details");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "State wise Watershed Janbhagidari Activities Details";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,2,2); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(5,5,3,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,7,9); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(5,6,10,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,6,11,11); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
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
			cell.setCellValue("No. of Projects where Watersheed Janbhagidari Organized");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("No. of Work to be done through Janbhagidari Activities");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =4; i<7;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Tentative Community Contribution Percentage (%)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =8; i<=9;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Total Estimated Value of Work to be done through Janbhagidari (Rs. in Lakh)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Achievements made so for (No. of Work Completed)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("NRM/SWC Structure Creation");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(4);
			cell.setCellValue("NRM/SWC Structure Repair /Rejuvenation");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("Plantation Related Work");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("Production System Related Work");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Villagers");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("NGOs");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(9);
			cell.setCellValue("Corporates");  
			cell.setCellStyle(style);
			for(int i =10; i<12;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<12;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        int sno = 1;
	        int rowno  = 8;
	        
	        for(JanbhagidariPratiyogitaBean bean: list) 
	        {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getSt_name());
	        	row.createCell(2).setCellValue(bean.getProj_repoted());
				row.createCell(3).setCellValue(bean.getNo_work_creation());  
				row.createCell(4).setCellValue(bean.getNo_work_repair()); 
				row.createCell(5).setCellValue(bean.getNo_work_plant());  
				row.createCell(6).setCellValue(bean.getNo_work_production());
				
				row.createCell(7).setCellValue(bean.getVillage().doubleValue());
				row.createCell(8).setCellValue(bean.getNgo().doubleValue());
				row.createCell(9).setCellValue(bean.getCorporate().doubleValue());
				row.createCell(10).setCellValue(bean.getEstimate_work().doubleValue());
				row.createCell(11).setCellValue(bean.getNo_work_comp());
				
				proj_repotedt=proj_repotedt+bean.getProj_repoted();
				no_work_creationt=no_work_creationt+bean.getNo_work_creation();
				no_work_repairt=no_work_repairt+bean.getNo_work_repair();
				no_work_plantt=no_work_plantt+bean.getNo_work_plant();
				no_work_productiont=no_work_productiont+bean.getNo_work_production();
				no_work_compt=no_work_compt+bean.getNo_work_comp();
				estimate_workt=estimate_workt.add(bean.getEstimate_work());
				villaget=villaget.add(bean.getVillage());
				ngot=ngot.add(bean.getNgo());
				corporatet=corporatet.add(bean.getCorporate());
				
	        	sno++;
	        	rowno++;
	        }
	        
	        BigDecimal nostate = BigDecimal.valueOf(30);
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
	        
	        Row row = sheet.createRow(list.size()+8);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(proj_repotedt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(no_work_creationt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(no_work_repairt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(no_work_plantt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(no_work_productiont);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(villaget.divide(nostate, 2, RoundingMode.HALF_UP).doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(ngot.divide(nostate, 2, RoundingMode.HALF_UP).doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(corporatet.divide(nostate, 2, RoundingMode.HALF_UP).doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(estimate_workt.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(no_work_compt);
	        cell.setCellStyle(style1);
	       
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
	        String fileName = "attachment; filename=JP-3.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}
	
	@RequestMapping(value="/getdistrictWiseJanbhagidariActivitiesReport", method = RequestMethod.GET)
	public ModelAndView getdistrictWiseJanbhagidariActivitiesReport(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id)
	{
		ModelAndView mav = new ModelAndView();
		String stname= request.getParameter("stname");
		
		List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
		try {
			
			mav = new ModelAndView("reports/janbhagidariPratiyogitaActivitiesDistrict");
			
			data=serk.getjanbhagidariPratiyogitaActivitiesDistrict(id);
			mav.addObject("dataList",data);
			mav.addObject("dataListSize",data.size());
			
			int proj_repotedt=0, no_work_creationt=0, no_work_repairt=0, no_work_plantt=0, no_work_productiont=0, no_work_compt=0;
			
			BigDecimal estimate_workt = BigDecimal.valueOf(0);
			BigDecimal villaget = BigDecimal.valueOf(0);
			BigDecimal ngot=BigDecimal.valueOf(0);
			BigDecimal corporatet = BigDecimal.valueOf(0);
			
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					proj_repotedt=proj_repotedt+bean.getProj_repoted();
					no_work_creationt=no_work_creationt+bean.getNo_work_creation();
					no_work_repairt=no_work_repairt+bean.getNo_work_repair();
					no_work_plantt=no_work_plantt+bean.getNo_work_plant();
					no_work_productiont=no_work_productiont+bean.getNo_work_production();
					no_work_compt=no_work_compt+bean.getNo_work_comp();
					
					estimate_workt=estimate_workt.add(bean.getEstimate_work());
					villaget=villaget.add(bean.getVillage());
					ngot=ngot.add(bean.getNgo());
					corporatet=corporatet.add(bean.getCorporate());
				}
			}	
			
			mav.addObject("proj_repotedt",proj_repotedt);
			mav.addObject("no_work_creationt",no_work_creationt);
			mav.addObject("no_work_repairt",no_work_repairt);
			mav.addObject("no_work_plantt",no_work_plantt);
			mav.addObject("no_work_productiont",no_work_productiont);
			mav.addObject("no_work_compt",no_work_compt);
			mav.addObject("estimate_workt",estimate_workt);
			mav.addObject("villaget",villaget);
			mav.addObject("ngot",ngot);
			mav.addObject("corporatet",corporatet);
			mav.addObject("stname",stname);
			mav.addObject("stcd",id);
			//System.out.println("data size="+data.size());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value = "/getdistrictWiseJanbhagidariActivitiesReportPDF", method = RequestMethod.POST)
	public ModelAndView getdistrictWiseJanbhagidariActivitiesReportPDF(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
    	String stname = request.getParameter("stname");
	  
	    try {
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("JP-3 District Wise");
	        document.addCreationDate();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter writer = PdfWriter.getInstance(document, baos);

	        document.open();

	        Font f1 = new Font(FontFamily.HELVETICA, 11.0f, Font.BOLDITALIC);
	        Font f3 = new Font(FontFamily.HELVETICA, 13.0f, Font.BOLD);
	        Font bf8 = new Font(FontFamily.HELVETICA, 8);
	        Font bf8Bold = new Font(FontFamily.HELVETICA, 8, Font.BOLD, new BaseColor(255, 255, 240));
	        Font bf10Bold = new Font(FontFamily.HELVETICA, 8.0f, Font.BOLD);

	        PdfPTable table = null;
	        document.newPage();
	        Paragraph paragraph3 = null;
	        Paragraph paragraph2 = new Paragraph("Department of Land Resources, Ministry of Rural Development\n", f1);

	        paragraph3 = new Paragraph("JP3 - District wise Watershed Janbhagidari Activities Details", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(12);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(4);
	        
	        CommonFunctions.insertCellHeader(table, "State Name "+stname, Element.ALIGN_LEFT, 15, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Projects where Watersheed Janbhagidari Organized", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "No. of Work to be done through Janbhagidari Activities", Element.ALIGN_CENTER, 4, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Tentative Community Contribution Percentage (%)", Element.ALIGN_CENTER, 3, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Estimated Value of Work to be done through Janbhagidari (Rs. in Lakh)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Achievements made so for (No. of Work Completed)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	       

	        CommonFunctions.insertCellHeader(table, "NRM/SWC Structure Creation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "NRM/SWC Structure Repair /Rejuvenation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Plantation Related Work", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Production System Related Work", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
	        CommonFunctions.insertCellHeader(table, "Villagers", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "NGOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Corporates", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        
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
	       
	        List<JanbhagidariPratiyogitaBean> data = new ArrayList<JanbhagidariPratiyogitaBean>();
	        
	        data=serk.getjanbhagidariPratiyogitaActivitiesDistrict(Integer.parseInt(id));
			
			int proj_repotedt=0, no_work_creationt=0, no_work_repairt=0, no_work_plantt=0, no_work_productiont=0, no_work_compt=0;
			
			BigDecimal estimate_workt = BigDecimal.valueOf(0);
			BigDecimal villaget = BigDecimal.valueOf(0);
			BigDecimal ngot=BigDecimal.valueOf(0);
			BigDecimal corporatet = BigDecimal.valueOf(0);
			
			int k = 1;
			if(data != null) 
			{
				for(JanbhagidariPratiyogitaBean bean : data) 
				{
					
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, bean.getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getProj_repoted()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_work_creation()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_work_repair()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_work_plant()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_work_production()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                CommonFunctions.insertCell(table, String.valueOf(bean.getVillage()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNgo()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getCorporate()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(bean.getEstimate_work()), Element.ALIGN_RIGHT, 1,1, bf8);
	                
	                CommonFunctions.insertCell(table, String.valueOf(bean.getNo_work_comp()), Element.ALIGN_RIGHT, 1,1, bf8);
					
					proj_repotedt=proj_repotedt+bean.getProj_repoted();
					no_work_creationt=no_work_creationt+bean.getNo_work_creation();
					no_work_repairt=no_work_repairt+bean.getNo_work_repair();
					no_work_plantt=no_work_plantt+bean.getNo_work_plant();
					no_work_productiont=no_work_productiont+bean.getNo_work_production();
					no_work_compt=no_work_compt+bean.getNo_work_comp();
					
					estimate_workt=estimate_workt.add(bean.getEstimate_work());
					villaget=villaget.add(bean.getVillage());
					ngot=ngot.add(bean.getNgo());
					corporatet=corporatet.add(bean.getCorporate());
					k = k + 1;
					
				}
			}	
			BigDecimal nostate = BigDecimal.valueOf(k);

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(proj_repotedt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(no_work_creationt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(no_work_repairt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(no_work_plantt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(no_work_productiont), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(villaget.divide(nostate, 2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(ngot.divide(nostate, 2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(corporatet.divide(nostate, 2, RoundingMode.HALF_UP)), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(estimate_workt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(no_work_compt), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            

	            document.add(table);
	            table = new PdfPTable(1);
				table.setWidthPercentage(70);
				table.setSpacingBefore(15f);
				table.setSpacingAfter(0f);
				CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
				CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
				document.add(table);
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=JP-3-District Wise.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/ExcelgetdistrictWiseJanbhagidariActivitiesReport", method = RequestMethod.POST)
	@ResponseBody
	public String ExcelgetdistrictWiseJanbhagidariActivitiesReport(HttpServletRequest request, HttpServletResponse response) 
	{
			String id = request.getParameter("id");
	    	String stname = request.getParameter("stname");
		
			List<JanbhagidariPratiyogitaBean> list = new ArrayList<JanbhagidariPratiyogitaBean>();
	        
			list=serk.getjanbhagidariPratiyogitaActivitiesDistrict(Integer.parseInt(id));
			
			int proj_repotedt=0, no_work_creationt=0, no_work_repairt=0, no_work_plantt=0, no_work_productiont=0, no_work_compt=0;
			
			BigDecimal estimate_workt = BigDecimal.valueOf(0);
			BigDecimal villaget = BigDecimal.valueOf(0);
			BigDecimal ngot=BigDecimal.valueOf(0);
			BigDecimal corporatet = BigDecimal.valueOf(0);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("JP3 - District wise Watershed Janbhagidari Activities Details");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "JP3 - District wise Watershed Janbhagidari Activities Details";
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
	        
	        mergedRegion = new CellRangeAddress(6,6,3,6); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,6,7,9); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        mergedRegion = new CellRangeAddress(6,7,10,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(6,7,11,11); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        Row rowDetail = sheet.createRow(5);
	        
	        Cell cell = rowDetail.createCell(0);
			cell.setCellValue("State : "+ stname);  
			cell.setCellStyle(style);
	        
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
			cell.setCellValue("No. of Projects where Watersheed Janbhagidari Organized");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("No. of Work to be done through Janbhagidari Activities");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =4; i<7;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Tentative Community Contribution Percentage (%)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			for(int i =8; i<=9;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead.createCell(10);
			cell.setCellValue("Total Estimated Value of Work to be done through Janbhagidari (Rs. in Lakh)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Achievements made so for (No. of Work Completed)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			
			Row rowhead1 = sheet.createRow(7);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("NRM/SWC Structure Creation");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(4);
			cell.setCellValue("NRM/SWC Structure Repair /Rejuvenation");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("Plantation Related Work");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("Production System Related Work");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Villagers");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("NGOs");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(9);
			cell.setCellValue("Corporates");  
			cell.setCellStyle(style);
			for(int i =10; i<12;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			Row rowhead2 = sheet.createRow(8);
			for(int i=0;i<12;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        int sno = 1;
	        int rowno  = 9;
	        
	        for(JanbhagidariPratiyogitaBean bean: list) 
	        {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDist_name());
	        	row.createCell(2).setCellValue(bean.getProj_repoted());
				row.createCell(3).setCellValue(bean.getNo_work_creation());  
				row.createCell(4).setCellValue(bean.getNo_work_repair()); 
				row.createCell(5).setCellValue(bean.getNo_work_plant());  
				row.createCell(6).setCellValue(bean.getNo_work_production());
				
				row.createCell(7).setCellValue(bean.getVillage().doubleValue());
				row.createCell(8).setCellValue(bean.getNgo().doubleValue());
				row.createCell(9).setCellValue(bean.getCorporate().doubleValue());
				row.createCell(10).setCellValue(bean.getEstimate_work().doubleValue());
				row.createCell(11).setCellValue(bean.getNo_work_comp());
				
				proj_repotedt=proj_repotedt+bean.getProj_repoted();
				no_work_creationt=no_work_creationt+bean.getNo_work_creation();
				no_work_repairt=no_work_repairt+bean.getNo_work_repair();
				no_work_plantt=no_work_plantt+bean.getNo_work_plant();
				no_work_productiont=no_work_productiont+bean.getNo_work_production();
				no_work_compt=no_work_compt+bean.getNo_work_comp();
				estimate_workt=estimate_workt.add(bean.getEstimate_work());
				villaget=villaget.add(bean.getVillage());
				ngot=ngot.add(bean.getNgo());
				corporatet=corporatet.add(bean.getCorporate());
				
	        	sno++;
	        	rowno++;
	        }
	        
	        BigDecimal nostate = BigDecimal.valueOf(sno);
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
	        
	        Row row = sheet.createRow(list.size()+9);
	        cell = row.createCell(0);
	        cell.setCellValue("Grand Total");
	        cell.setCellStyle(style1);
	        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	        cell = row.createCell(1);
	        cell.setCellStyle(style1);
	        cell = row.createCell(2);
	        cell.setCellValue(proj_repotedt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(no_work_creationt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(no_work_repairt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(no_work_plantt);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(no_work_productiont);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(villaget.divide(nostate, 2, RoundingMode.HALF_UP).doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(ngot.divide(nostate, 2, RoundingMode.HALF_UP).doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(corporatet.divide(nostate, 2, RoundingMode.HALF_UP).doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(estimate_workt.doubleValue());
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(no_work_compt);
	        cell.setCellStyle(style1);
	       
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
	        String fileName = "attachment; filename=JP-3-District Wise.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return null;
		
	}

}
