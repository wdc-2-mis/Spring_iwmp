package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

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

import app.TargetAchievementQuarterBean;
import app.bean.AddOutcomeParaBean;
import app.bean.PfmsComponentBean;
import app.bean.PfmsTransactionBean;
import app.bean.WcdcPiaUserBean;
import app.bean.reports.PMKSYBean;
import app.common.CommonFunctions;
import app.service.DistrictMasterService;
import app.service.StateMasterService;
import app.service.reports.PfmscompService;
import app.service.reports.PhysicalActionAchievementService;

@Controller("ComponentOfPfmsReport")
public class ComponentOfPfmsReport {
	HttpSession session;
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@Autowired
	PfmscompService pfmscompService;
	
	@RequestMapping(value="/getPfmsComponentReport", method = RequestMethod.GET)
	public ModelAndView getPfmsComponentReport(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/ComponentPfmsReport");
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		mav.addObject("compList",pfmscompService.getAllComponent());
		return mav;
	}
	
	@RequestMapping(value="/getPfmsComponentReport", method = RequestMethod.POST)
	public ModelAndView getPfmsComponentReport1(HttpServletRequest request, HttpServletResponse response)
	{
		
		String stcode=request.getParameter("state");
		String comp[]=request.getParameterValues("component");
		String finyr=request.getParameter("fromYear");
		String stName= ""; 
		LinkedHashMap<Integer, String> stmap = stateMasterService.getAllState();
		LinkedHashMap<String, String> compnts = pfmscompService.getAllComponent();
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/ComponentPfmsReport");
		List<PfmsComponentBean> list=new  ArrayList<PfmsComponentBean>();
		
		list=pfmscompService.getPfmsComponentReport(stcode,comp,finyr);
		
		Map<String,String> cmpntMap = new LinkedHashMap<>();
		Map<String,BigDecimal> stcmpMap = new LinkedHashMap<>();
		Map<String,BigDecimal> totstmap = new LinkedHashMap<>();
		
		for(Map.Entry<Integer,String> smap : stmap.entrySet()) {
			for(int i =0; i<comp.length; i++) {
			if(!stcmpMap.containsKey(smap.getKey()+","+ comp[i]))
				stcmpMap.put(smap.getKey()+","+ comp[i], BigDecimal.ZERO);
			}
			if(smap.getKey()==Integer.parseInt(stcode))
				stName = smap.getValue();
			
		}
		
		for(Map.Entry<String,String> smap : compnts.entrySet()) {
			for(int i =0; i<comp.length; i++) {
				
				if(smap.getKey().equals(comp[i])) {
					cmpntMap.put(smap.getKey(), smap.getValue());
				}
			}
				
		}
		
		list.forEach(s->{
			if(!stcmpMap.containsKey(s.getSt_code()+","+s.getComponent_code())) {
				stcmpMap.put(s.getSt_code()+","+s.getComponent_code(), s.getExpenditure());
			}else {
				stcmpMap.put(s.getSt_code()+","+s.getComponent_code(),stcmpMap.get(s.getSt_code()+","+s.getComponent_code()).add(s.getExpenditure()));
			}
			if(!cmpntMap.containsKey(s.getComponent_code())) {
				cmpntMap.put(s.getComponent_code(),s.getComponent_name()+".");
			}
				
		});	
		
		for(Map.Entry<String, BigDecimal> map: stcmpMap.entrySet()) {
			if(totstmap.containsKey(map.getKey().substring(map.getKey().indexOf(',')))) {
				totstmap.put(map.getKey().substring(map.getKey().indexOf(',')), totstmap.get(map.getKey().substring(map.getKey().indexOf(','))).add(map.getValue()));
			}else {
				totstmap.put(map.getKey().substring(map.getKey().indexOf(',')), map.getValue());
			}
		}
	
		String cmpcd=null;
		for(int i=0;i<comp.length; i++) {
			
			if(cmpcd!=null)
			cmpcd=cmpcd+","+comp[i]; 
			else
				cmpcd=comp[i];
	

		}
		mav.addObject("dataList",list);
		mav.addObject("dataListsize",list.size());
		mav.addObject("stCode",stcode);
		mav.addObject("stName",stName);
		mav.addObject("stmap",stmap);
		mav.addObject("fromYear",finyr);
		mav.addObject("cmpntMap",cmpntMap);
		mav.addObject("stcmpMap",stcmpMap);
		mav.addObject("comp",cmpcd);
		mav.addObject("totstmap",totstmap);
		mav.addObject("cmpntMapSize",cmpntMap.size());
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		mav.addObject("compList",pfmscompService.getAllComponent());
		
		return mav;
	}
	
	@RequestMapping(value="/getdistWisePfmsComponentReport", method = RequestMethod.GET)
	public ModelAndView getdistWisePfmsComponentReport(HttpServletRequest request, HttpServletResponse response)
	{
 		String stcode=request.getParameter("stcode");
		String comp=request.getParameter("comp");
		String finyr=request.getParameter("fromYear");
		String stName= request.getParameter("stname");
		String distName= request.getParameter("distName");
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(Integer.parseInt(stcode));
		LinkedHashMap<String, String> compnts = pfmscompService.getAllComponent();
		
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/DistPfmsComponentReport");
		List<PfmsComponentBean> list=new  ArrayList<PfmsComponentBean>();
		
		String acomp[]=comp.split(",");
		
		list=pfmscompService.getdistPfmsComponentReport(stcode, comp, finyr);
		
		Map<String,String> cmpntMap = new LinkedHashMap<>();
		Map<String,BigDecimal> distcmpMap = new LinkedHashMap<>();
		Map<String,BigDecimal> totdistmap = new LinkedHashMap<>();
		
		for(Map.Entry<Integer,String> smap : distMap.entrySet()) {
			for(int i =0; i<acomp.length; i++) {
			if(!distcmpMap.containsKey(smap.getKey()+","+ acomp[i]))
				distcmpMap.put(smap.getKey()+","+ acomp[i], BigDecimal.ZERO);
			}
		}
		for (Map.Entry<String, String> stmap : compnts.entrySet()) {
		    for (int i = 0; i < acomp.length; i++) {
		        if (stmap.getKey().equals(acomp[i])) {
		            cmpntMap.put(stmap.getKey(), stmap.getValue());
		        }
		    }
		    if (stcode != null && stmap.getKey().equals(stcode)) {
		        stName = stmap.getValue();
		    }
		}
		
		list.forEach(s->{
			if(!distcmpMap.containsKey(s.getDcode()+","+s.getComponent_code())) {
				distcmpMap.put(s.getDcode()+","+s.getComponent_code(), s.getExpenditure());
			}else {
				distcmpMap.put(s.getDcode()+","+s.getComponent_code(),distcmpMap.get(s.getDcode()+","+s.getComponent_code()).add(s.getExpenditure()));
			}
			if(!cmpntMap.containsKey(s.getComponent_code())) {
				cmpntMap.put(s.getComponent_code(),s.getComponent_name());
			}
				
		});	
		for(Map.Entry<String, BigDecimal> map: distcmpMap.entrySet()) {
			if(totdistmap.containsKey(map.getKey().substring(map.getKey().indexOf(',')))) {
				totdistmap.put(map.getKey().substring(map.getKey().indexOf(',')), totdistmap.get(map.getKey().substring(map.getKey().indexOf(','))).add(map.getValue()));
			}else {
				totdistmap.put(map.getKey().substring(map.getKey().indexOf(',')), map.getValue());
			}
		}
		mav.addObject("cmpntMapSize",cmpntMap.size());
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		mav.addObject("compList",pfmscompService.getAllComponent());
		mav.addObject("dataList",list);
		mav.addObject("dataListsize",list.size());
		mav.addObject("stCode",stcode);
		mav.addObject("stname",stName);
		mav.addObject("distName",distName);
		mav.addObject("comp",comp);
		mav.addObject("distMap",distMap);
		mav.addObject("totdistmap",totdistmap);
		mav.addObject("fromYear",finyr);
		mav.addObject("cmpntMap",cmpntMap);
		mav.addObject("distcmpMap",distcmpMap);
		mav.addObject("cmpntMapSize",cmpntMap.size());
		mav.addObject("stateList",districtMasterService.getDistrictByStateCode(Integer.parseInt(stcode)));
		mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		mav.addObject("compList",pfmscompService.getAllComponent());
		
		return mav;
	}
	
	@RequestMapping(value = "/downloadDistrictCompExpndtrPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictCompExpndtrPDF(HttpServletRequest request, HttpServletResponse response) 
	{

 		String stcode=request.getParameter("stCode");
		String comp=request.getParameter("comp");
		String finyr=request.getParameter("fromYear");
		String stName= request.getParameter("stname");
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(Integer.parseInt(stcode));
		LinkedHashMap<String, String> compnts = pfmscompService.getAllComponent();
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/DistPfmsComponentReport");
		List<PfmsComponentBean> list=new  ArrayList<PfmsComponentBean>();
		
		String acomp[]=comp.split(",");
		
		list=pfmscompService.getdistPfmsComponentReport(stcode, comp, finyr);
		
		Map<String,String> cmpntMap = new LinkedHashMap<>();
		Map<String,BigDecimal> distcmpMap = new LinkedHashMap<>();
		Map<String,BigDecimal> totdistmap = new LinkedHashMap<>();
		for(Map.Entry<Integer,String> smap : distMap.entrySet()) {
			for(int i =0; i<acomp.length; i++) {
			if(!distcmpMap.containsKey(smap.getKey()+","+ acomp[i]))
				distcmpMap.put(smap.getKey()+","+ acomp[i], BigDecimal.ZERO);
			}
		}
		
		for(Map.Entry<String,String> smap : compnts.entrySet()) {
			for(int i =0; i<acomp.length; i++) {
				
				if(smap.getKey().equals(acomp[i])) {
					cmpntMap.put(smap.getKey(), smap.getValue());
				}
			}
			if(smap.getKey()==stcode)
				stName = smap.getValue();
			
		}
		
		list.forEach(s->{
			if(!distcmpMap.containsKey(s.getDcode()+","+s.getComponent_code())) {
				distcmpMap.put(s.getDcode()+","+s.getComponent_code(), s.getExpenditure());
			}else {
				distcmpMap.put(s.getDcode()+","+s.getComponent_code(),distcmpMap.get(s.getDcode()+","+s.getComponent_code()).add(s.getExpenditure()));
			}
			if(!cmpntMap.containsKey(s.getComponent_code())) {
				cmpntMap.put(s.getComponent_code(),s.getComponent_name());
			}
				
		});	
		for(Map.Entry<String, BigDecimal> map: distcmpMap.entrySet()) {
			if(totdistmap.containsKey(map.getKey().substring(map.getKey().indexOf(',')))) {
				totdistmap.put(map.getKey().substring(map.getKey().indexOf(',')), totdistmap.get(map.getKey().substring(map.getKey().indexOf(','))).add(map.getValue()));
			}else {
				totdistmap.put(map.getKey().substring(map.getKey().indexOf(',')), map.getValue());
			}
		}


		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report PF7- District Wise Component Expenditure for State "+" '"+stName+"' ", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(8);
			paragraph3.setSpacingAfter(8);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
		
			if(cmpntMap.size()!=0)
				table = new PdfPTable(cmpntMap.size()+2);
			int[]arr=new int[cmpntMap.size()+2];
			for(int i=0; i<cmpntMap.size()+2;i++) {
				arr[i]=3;
			}
				table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);


			CommonFunctions.insertCellHeader(table, "All amount in Rs.", Element.ALIGN_RIGHT, cmpntMap.size()+2, 1, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "State :  "+ stName+","+" Financial Year :  " +finyr+"-"+(Integer.parseInt(finyr)+1) , Element.ALIGN_LEFT, cmpntMap.size()+2, 1, bf8Bold);
				
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Component Name", Element.ALIGN_CENTER, cmpntMap.size(), 1, bf8Bold);
			
			for(Map.Entry<String, String> map : cmpntMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER,1, 1, bf8Bold);
			}
			
			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<cmpntMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			int k=1;
			int dcd=0;
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
			
			if (list.get(i).getDcode() != dcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);

				for (Map.Entry<String, BigDecimal> map : distcmpMap.entrySet()) {
					if (list.get(i).getDcode() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					}
				}

				k = k + 1;
				dcd = list.get(i).getDcode();
			}
				
				}
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			for (Map.Entry<String, BigDecimal> map : totdistmap.entrySet()) {
				
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				
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
			response.setHeader("Content-Disposition", "attachment;filename=PF7-DistReport.pdf");
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
	
	
	@RequestMapping(value = "/downloadStCompExpndtrPDF", method = RequestMethod.POST)
	public ModelAndView downloadStCompExpndtrPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcode=request.getParameter("stCode");
		String comp[]=request.getParameterValues("comp");
		String cmpStr = comp[0];
		
		String cmp[] = cmpStr.split(",");
		String finyr=request.getParameter("fromYear");
		String stName= request.getParameter("stName");
		LinkedHashMap<Integer, String> stmap = stateMasterService.getAllState();
		LinkedHashMap<String, String> compnts = pfmscompService.getAllComponent();
		
		List<PfmsComponentBean> list=new  ArrayList<PfmsComponentBean>();
		
		list=pfmscompService.getPfmsComponentReport(stcode,comp,finyr);
		
		Map<String,String> cmpntMap = new LinkedHashMap<>();
		Map<String,BigDecimal> stcmpMap = new LinkedHashMap<>();
		Map<String,BigDecimal> totstmap = new LinkedHashMap<>();
		
		for(Map.Entry<Integer,String> smap : stmap.entrySet()) {
			for(int i =0; i<cmp.length; i++) {
			if(!stcmpMap.containsKey(smap.getKey()+","+ cmp[i]))
				stcmpMap.put(smap.getKey()+","+ cmp[i], BigDecimal.ZERO);
			}
			if(smap.getKey()==Integer.parseInt(stcode))
				stName = smap.getValue();
			
		}
		
		for(Map.Entry<String,String> smap : compnts.entrySet()) {
			for(int i =0; i<comp.length; i++) {
				
				if(smap.getKey().equals(comp[i])) {
					cmpntMap.put(smap.getKey(), smap.getValue());
				}
			}
				
		}
		
		list.forEach(s->{
			if(!stcmpMap.containsKey(s.getSt_code()+","+s.getComponent_code())) {
				stcmpMap.put(s.getSt_code()+","+s.getComponent_code(), s.getExpenditure());
				
			}else {
				stcmpMap.put(s.getSt_code()+","+s.getComponent_code(),stcmpMap.get(s.getSt_code()+","+s.getComponent_code()).add(s.getExpenditure()));
			}
			if(!cmpntMap.containsKey(s.getComponent_code())) {
				cmpntMap.put(s.getComponent_code(),s.getComponent_name()+".");
			}
				
		});	
		for(Map.Entry<String, BigDecimal> map: stcmpMap.entrySet()) {
			if(totstmap.containsKey(map.getKey().substring(map.getKey().indexOf(',')))) {
				totstmap.put(map.getKey().substring(map.getKey().indexOf(',')), totstmap.get(map.getKey().substring(map.getKey().indexOf(','))).add(map.getValue()));
			}else {
				totstmap.put(map.getKey().substring(map.getKey().indexOf(',')), map.getValue());
			}
		}


		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("WDC-PMKSY");
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

			paragraph3 = new Paragraph("Report PF7- State Wise Component Expenditure", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(8);
			paragraph3.setSpacingAfter(8);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
		
			if(cmpntMap.size()!=0)
				table = new PdfPTable(cmpntMap.size()+2);
			int[]arr=new int[cmpntMap.size()+2];
			for(int i=0; i<cmpntMap.size()+2;i++) {
				arr[i]=3;
			}
				table.setWidths(arr);
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);


			CommonFunctions.insertCellHeader(table, "All amount in Rs.", Element.ALIGN_RIGHT, cmpntMap.size()+2, 1, bf8Bold);
			if(stName==null) {
				CommonFunctions.insertCellHeader(table, "State :  "+ "All State"+","+" Financial Year :  " +"20"+finyr+"-"+"20"+(Integer.parseInt(finyr)+1) , Element.ALIGN_LEFT, cmpntMap.size()+2, 1, bf8Bold);
				}else {
			CommonFunctions.insertCellHeader(table, "State :  "+ stName+","+" Financial Year :  " +"20"+finyr+"-"+"20"+(Integer.parseInt(finyr)+1) , Element.ALIGN_LEFT, cmpntMap.size()+2, 1, bf8Bold);
				}
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Component Name", Element.ALIGN_CENTER, cmpntMap.size(), 1, bf8Bold);
			
			for(Map.Entry<String, String> map : cmpntMap.entrySet()) {
				CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER,1, 1, bf8Bold);
			}
			
			CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			for(int i = 0;i<cmpntMap.size();i++) {
				CommonFunctions.insertCellHeader(table, String.valueOf(i+3), Element.ALIGN_CENTER, 1, 1, bf8Bold);
			}
			
			int k=1;
			int stcd=0;
			if(list.size()!=0)
				for(int i=0;i<list.size();i++) 
				{
			
			if (list.get(i).getSt_code() != stcd) {
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);

				
				for (Map.Entry<String, BigDecimal> map : stcmpMap.entrySet()) {
					if (list.get(i).getSt_code() == Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(',')))) {
						CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
						
					}
					
				}
				k = k + 1;
				stcd = list.get(i).getSt_code();
			}
			
				}
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			
			for (Map.Entry<String, BigDecimal> map : totstmap.entrySet()) {
				
				CommonFunctions.insertCell3(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1, bf10Bold);
				
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
			response.setHeader("Content-Disposition", "attachment;filename=PF7-Report.pdf");
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

	


	@RequestMapping(value = "/getDistrictWiseComponentExpndtrExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getDistrictWiseComponentExpndtrExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcode=request.getParameter("stCode");
		String comp=request.getParameter("comp");
		String finyr=request.getParameter("fromYear");
		String stName= request.getParameter("stname");
		String distName= request.getParameter("distName");
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(Integer.parseInt(stcode));
		LinkedHashMap<String, String> compnts = pfmscompService.getAllComponent();
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/DistPfmsComponentReport");
		List<PfmsComponentBean> list=new  ArrayList<PfmsComponentBean>();
		
		String acomp[]=comp.split(",");
		
		
		list=pfmscompService.getdistPfmsComponentReport(stcode, comp, finyr);
		
		Map<String,String> cmpntMap = new LinkedHashMap<>();
		Map<String,BigDecimal> distcmpMap = new LinkedHashMap<>();
		Map<String,BigDecimal> totdistmap = new LinkedHashMap<>();
		
		for(Map.Entry<Integer,String> smap : distMap.entrySet()) {
			for(int i =0; i<acomp.length; i++) {
			if(!distcmpMap.containsKey(smap.getKey()+","+ acomp[i]))
				distcmpMap.put(smap.getKey()+","+ acomp[i], BigDecimal.ZERO);
			}
			
			
		}
		
		for(Map.Entry<String,String> smap : compnts.entrySet()) {
			for(int i =0; i<acomp.length; i++) {
				
				if(smap.getKey().equals(acomp[i])) {
					cmpntMap.put(smap.getKey(), smap.getValue());
				}
			}
			if(smap.getKey()==stcode)
				stName = smap.getValue();
			
		}
		
		list.forEach(s->{
			if(!distcmpMap.containsKey(s.getDcode()+","+s.getComponent_code())) {
				distcmpMap.put(s.getDcode()+","+s.getComponent_code(), s.getExpenditure());
			}else {
				distcmpMap.put(s.getDcode()+","+s.getComponent_code(),distcmpMap.get(s.getDcode()+","+s.getComponent_code()).add(s.getExpenditure()));
			}
			if(!cmpntMap.containsKey(s.getComponent_code())) {
				cmpntMap.put(s.getComponent_code(),s.getComponent_name());
			}
				
		});	
	
		for(Map.Entry<String, BigDecimal> map: distcmpMap.entrySet()) {
			if(totdistmap.containsKey(map.getKey().substring(map.getKey().indexOf(',')))) {
				totdistmap.put(map.getKey().substring(map.getKey().indexOf(',')), totdistmap.get(map.getKey().substring(map.getKey().indexOf(','))).add(map.getValue()));
			}else {
				totdistmap.put(map.getKey().substring(map.getKey().indexOf(',')), map.getValue());
			}
		}

			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF7 - District Wise Component Expenditure");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF7 - District Wise Component Expenditure for State "+" '"+stName+"' ";
			String areaAmtValDetail = "All amount in Rs.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, cmpntMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,cmpntMap.size()+1);
	        sheet.addMergedRegion(mergedRegion);
			
	        
	        mergedRegion = new CellRangeAddress(6,7,0,0); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(6,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        if(cmpntMap.size()!=1) {
	        mergedRegion = new CellRangeAddress(6,6,2,cmpntMap.size()+1);
	        sheet.addMergedRegion(mergedRegion);
	        }
	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
			Row detail = sheet.createRow(5); 
			
			Cell cell = detail.createCell(0);
 			cell.setCellValue("State : "  +stName+     "  Financial Year : " +finyr+"-"+(Integer.parseInt(finyr)+1) );
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<cmpntMap.size()+1;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
			Row rowhead = sheet.createRow(6);
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Component Name");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i=3;i<cmpntMap.size()+1;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(7);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<String, String> map : cmpntMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(8);
			for(int i=0;i<=cmpntMap.size()+1;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int dcode1 = 0;
	        int rowno  = 9;
	        for(PfmsComponentBean bean: list) {
	        	
	        	if(bean.getDcode()!=dcode1) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getDistname());
	        		
	        		int i =2;
	        		for (Map.Entry<String, BigDecimal> map : distcmpMap.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getDcode() == st) {
	        				row.createCell(i).setCellValue(map.getValue().doubleValue());
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	dcode1 = bean.getDcode();
	        	
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
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigDecimal> map : totdistmap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()));
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), cmpntMap.size()+1);
	        String fileName = "attachment; filename=Report PF7- District CompExpndtr.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/DistPfmsComponentReport";
	        
	        }

	@RequestMapping(value = "/getStWiseComponentExpndtrExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getStWiseComponentExpndtrExcel(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcode=request.getParameter("stCode");
		String comp[]=request.getParameterValues("comp");
		String cmpStr = comp[0];
		
		String cmp[] = cmpStr.split(",");
		String finyr=request.getParameter("fromYear");
		String stName= request.getParameter("stName");
		LinkedHashMap<Integer, String> stmap = stateMasterService.getAllState();
		LinkedHashMap<String, String> compnts = pfmscompService.getAllComponent();
		
		List<PfmsComponentBean> list=new  ArrayList<PfmsComponentBean>();
		
		list=pfmscompService.getPfmsComponentReport(stcode,comp,finyr);
		
		Map<String,String> cmpntMap = new LinkedHashMap<>();
		Map<String,BigDecimal> stcmpMap = new LinkedHashMap<>();
		Map<String,BigDecimal> totstmap = new LinkedHashMap<>();
		
		for(Map.Entry<Integer,String> smap : stmap.entrySet()) {
			for(int i =0; i<cmp.length; i++) {
			if(!stcmpMap.containsKey(smap.getKey()+","+ cmp[i]))
				stcmpMap.put(smap.getKey()+","+ cmp[i], BigDecimal.ZERO);
			}
			if(smap.getKey()==Integer.parseInt(stcode))
				stName = smap.getValue();
			
		}
		
		for(Map.Entry<String,String> smap : compnts.entrySet()) {
			for(int i =0; i<cmp.length; i++) {
				
				if(smap.getKey().equals(cmp[i])) {
					cmpntMap.put(smap.getKey(), smap.getValue());
				}
			}
				
		}
		
		list.forEach(s->{
			if(!stcmpMap.containsKey(s.getSt_code()+","+s.getComponent_code())) {
				stcmpMap.put(s.getSt_code()+","+s.getComponent_code(), s.getExpenditure());
				
			}else {
				stcmpMap.put(s.getSt_code()+","+s.getComponent_code(),stcmpMap.get(s.getSt_code()+","+s.getComponent_code()).add(s.getExpenditure()));
			}
			if(!cmpntMap.containsKey(s.getComponent_code())) {
				cmpntMap.put(s.getComponent_code(),s.getComponent_name()+".");
			}
				
		});	
		for(Map.Entry<String, BigDecimal> map: stcmpMap.entrySet()) {
			if(totstmap.containsKey(map.getKey().substring(map.getKey().indexOf(',')))) {
				totstmap.put(map.getKey().substring(map.getKey().indexOf(',')), totstmap.get(map.getKey().substring(map.getKey().indexOf(','))).add(map.getValue()));
			}else {
				totstmap.put(map.getKey().substring(map.getKey().indexOf(',')), map.getValue());
			}
		}


			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report PF7 - State Wise Component Expenditure");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report PF7 - State Wise Component Expenditure";
			String areaAmtValDetail = "All amount in Rs.";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, cmpntMap.size()+1, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,cmpntMap.size()+1);
	        sheet.addMergedRegion(mergedRegion);
			
	        
	        mergedRegion = new CellRangeAddress(6,7,0,0); 
	        sheet.addMergedRegion(mergedRegion);
        
	        mergedRegion = new CellRangeAddress(6,7,1,1); 
	        sheet.addMergedRegion(mergedRegion);
	        if(cmpntMap.size()!=1) {
	        mergedRegion = new CellRangeAddress(6,6,2,cmpntMap.size()+1);
	        sheet.addMergedRegion(mergedRegion);
	        }

	        mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
			Row detail = sheet.createRow(5); 
			
			Cell cell = detail.createCell(0);
			cell.setCellValue("State : "  +stName+    " Financial Year :  " +  " 20"+finyr+"-"+ "20"+(Integer.parseInt(finyr)+1));
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
			for(int i =1; i<cmpntMap.size()+1;i++) {
				detail.createCell(i).setCellStyle(style);
			}
			
			Row rowhead = sheet.createRow(6);
			
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("State Name");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Component Name");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i=3;i<cmpntMap.size()+1;i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
			
			Row rowhead1 = sheet.createRow(7);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			int no  = 2;
			for(Map.Entry<String, String> map : cmpntMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				no++;
			}
			
			
			Row rowhead2 = sheet.createRow(8);
			for(int i=0;i<=cmpntMap.size()+1;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int stcode1 = 0;
	        int rowno  = 9;
	        for(PfmsComponentBean bean: list) {
	        	
	        	if(bean.getSt_code()!=stcode1) {
	        		Row row = sheet.createRow(rowno);
	        		row.createCell(0).setCellValue(sno); 
	        		row.createCell(1).setCellValue(bean.getStname());
	        		
	        		int i =2;
	        		for (Map.Entry<String, BigDecimal> map : stcmpMap.entrySet()) {
	        			int st = Integer.parseInt(map.getKey().substring(0, map.getKey().indexOf(",")));
	        			if(bean.getSt_code() == st) {
	        				row.createCell(i).setCellValue(map.getValue().doubleValue());
	        				i++;
	        			}
	        		}
	        		sno++;
		        	rowno++;
	        	}
	        	stcode1 = bean.getSt_code();
	        	
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
	        row.createCell(1).setCellStyle(style1);
	        int index = 2;
	        for(Map.Entry<String, BigDecimal> map : totstmap.entrySet()) {
	        	cell = row.createCell(index);
	        	cell.setCellValue(Math.round(map.getValue().doubleValue()));
		        cell.setCellStyle(style1);
		        index++;
	        }
	       
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), cmpntMap.size()+1);
	        String fileName = "attachment; filename=Report PF7- State CompExpndtr.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/ComponentPfmsReport";
	        
	        }
		}

