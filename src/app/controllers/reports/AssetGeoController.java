package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import app.bean.reports.AssetGeoRefBean;
import app.common.CommonFunctions;
import app.service.reports.AssetGeoService;

@Controller
public class AssetGeoController {
	
	HttpSession session;
	
	@Autowired
	AssetGeoService assetGeoService;
	
	@RequestMapping(value="/getAllAssetGeoData", method = RequestMethod.GET)
	public ModelAndView getAllAssetGeoData(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String stcode= request.getParameter("stcode");
		String stname= request.getParameter("stname");
		mav = new ModelAndView("reports/assetGeoReport");
		List<AssetGeoRefBean> list = assetGeoService.getGeoRefData();
		
		mav.addObject("allStateList",list);
		mav.addObject("allStateListsize",list.size());
		mav.addObject("stname",stname);
		mav.addObject("stcode",stcode);
		
		return mav;
		
	}
	
	@RequestMapping(value="/getDistWiseAssetGeoData", method = RequestMethod.GET)
	public ModelAndView getDistWiseAssetGeoData(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		int stcode = Integer.parseInt(request.getParameter("stcode").toString());
		String stname = request.getParameter("stname").toString();
		List<AssetGeoRefBean> list = assetGeoService.getDistWiseGeoRefData(stcode);
		mav = new ModelAndView("reports/assetGeoReport");
		mav.addObject("allDistList",list);
		mav.addObject("allDistListsize",list.size());
		mav.addObject("stname",stname);
		mav.addObject("stcode",stcode);
		
		return mav;
		
	}
	
	@RequestMapping(value="/getProjWiseAssetGeoData", method = RequestMethod.GET)
	public ModelAndView getProjWiseAssetGeoData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		int dcode = Integer.parseInt(request.getParameter("dcode").toString());
		String stname = request.getParameter("stname").toString();
		String distname = request.getParameter("distname").toString();
		List<AssetGeoRefBean> list = assetGeoService.getProjWiseGeoRefData(dcode);
		mav = new ModelAndView("reports/assetGeoReport");
		mav.addObject("allProjList",list);
		mav.addObject("allProjListsize",list.size());
		mav.addObject("stname",stname);
		mav.addObject("distname",distname);
		mav.addObject("dcode",dcode);
		
		return mav;
	}
	
	@RequestMapping(value="/getProjDtlAssetGeoData", method = RequestMethod.GET)
	public ModelAndView getProjDtlAssetGeoData(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		int projid = Integer.parseInt(request.getParameter("projid").toString());
		String stname = request.getParameter("stname").toString();
		String distname = request.getParameter("distname").toString();
		String projname = request.getParameter("projname").toString();
		Map<String,Map<BigDecimal,String>> stgeMap = new LinkedHashMap<>();
		List<AssetGeoRefBean> list = assetGeoService.getProjWiseGeoRefDetails(projid);
		list.forEach(s->{
			Map<BigDecimal,String> map = new LinkedHashMap<>();
				stgeMap.put("Pre Implementation"+","+s.getWorkcode(), map);
				stgeMap.put("Mid Implementation"+","+s.getWorkcode(), map);
				stgeMap.put("Post Implementation"+","+s.getWorkcode(), map);
				
		});
		
		list.forEach(s->{
			Map<BigDecimal,String> map = new LinkedHashMap<>();
			if(stgeMap.containsKey(s.getStage()+","+s.getWorkcode())) {
				if(stgeMap.get(s.getStage()+","+s.getWorkcode())!=null)
					map.putAll(stgeMap.get(s.getStage()+","+s.getWorkcode()));
				
				map.put(s.getColno(), s.getStatus());
				stgeMap.put(s.getStage()+","+s.getWorkcode(), map);
			}else {
				map.put(s.getColno(), s.getStatus());
				stgeMap.put(s.getStage()+","+s.getWorkcode(), map);
			}
		});		
		
		mav = new ModelAndView("reports/assetGeoReport");
		mav.addObject("allProjDtlList",list);
		mav.addObject("allProjDtlListsize",list.size());
		mav.addObject("stgeMap",stgeMap);
		mav.addObject("stname",stname);
		mav.addObject("distname",distname);
		mav.addObject("projname",projname);
		mav.addObject("projid",projid);
		
		return mav;
	}
	

	@RequestMapping(value = "/downloadProjectwiseAllAssetGeoDataPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectwiseAllAssetGeoDataPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String dcode = request.getParameter("dcode");
		String distname = request.getParameter("distname");
		String stname = request.getParameter("stname");
		List<AssetGeoRefBean> list = assetGeoService.getProjWiseGeoRefData(Integer.parseInt(distname));
		
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("AllAssetGeoDataReport");
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
			
				paragraph3 = new Paragraph("Report GT2 - Project Wise Work Code Status with Geotagging Details for District \r\n"+" '"+dcode+"' of State '"+stname+"'", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(21);
		        table.setWidths(new int[]{3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(3);
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "NRM Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "EPA Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Livelihood Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Work Code excluding Foreclosed (NRM(4+5+6) + EPA(8+9+10) + LIV(12+13+14) + PROD(16+17+18))", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Geotag Work Code", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Non Geotag Work Code (19-20)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
		        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
		        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
		        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
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
				CommonFunctions.insertCellHeader(table, "18", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "19", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "20", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				int k = 1;
				int totCreated_N = 0;
				int totUnStarted_N = 0;
				int totOngoing_N = 0;
				int totCompleted_N = 0;
				int totCreated_E = 0;
				int totUnStarted_E = 0;
				int totOngoing_E = 0;
				int totCompleted_E = 0;
				int totCreated_L = 0;
				int totUnStarted_L = 0;
				int totOngoing_L = 0;
				int totCompleted_L = 0;
				int totCreated_P = 0;
				int totUnStarted_P = 0;
				int totOngoing_P = 0;
				int totCompleted_P = 0;
				int totgeorefwrkcd = 0;
				BigInteger totworkcode = BigInteger.valueOf(0);
				BigInteger  totnongeorefwrkcd=BigInteger.valueOf(0);
				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotworkcode()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotgeorefwrkcd()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotnongeorefwrkcd()), Element.ALIGN_RIGHT, 1, 1, bf8);
						

						totCreated_N = totCreated_N + list.get(i).getN_created();
						totUnStarted_N = totUnStarted_N + list.get(i).getN_unstarted();
						totOngoing_N = totOngoing_N + list.get(i).getN_ongoing();
						totCompleted_N = totCompleted_N + list.get(i).getN_completed();
						
						totCreated_E = totCreated_E + list.get(i).getE_created();
						totUnStarted_E = totUnStarted_E + list.get(i).getE_unstarted();
						totOngoing_E = totOngoing_E + list.get(i).getE_ongoing();
						totCompleted_E = totCompleted_E + list.get(i).getE_completed();
						
						totCreated_L = totCreated_L + list.get(i).getL_created();
						totUnStarted_L = totUnStarted_L + list.get(i).getL_unstarted();
						totOngoing_L = totOngoing_L + list.get(i).getL_ongoing();
						totCompleted_L = totCompleted_L + list.get(i).getL_completed();
						
						totCreated_P = totCreated_P + list.get(i).getP_created();
						totUnStarted_P = totUnStarted_P + list.get(i).getP_unstarted();
						totOngoing_P = totOngoing_P + list.get(i).getP_ongoing();
						totCompleted_P = totCompleted_P + list.get(i).getP_completed();
						
						totworkcode=totworkcode.add(list.get(i).getTotworkcode());
						totgeorefwrkcd = totgeorefwrkcd+list.get(i).getTotgeorefwrkcd();
						totnongeorefwrkcd = totnongeorefwrkcd.add(list.get(i).getTotnongeorefwrkcd());
						
						k++;
					}
					
				
				CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				
				CommonFunctions.insertCell3(table, String.valueOf(totCreated_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        
		        CommonFunctions.insertCell3(table, String.valueOf(totCreated_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        
		        CommonFunctions.insertCell3(table, String.valueOf(totCreated_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        
		        CommonFunctions.insertCell3(table, String.valueOf(totCreated_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totOngoing_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        CommonFunctions.insertCell3(table, String.valueOf(totCompleted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        
	            CommonFunctions.insertCell3(table, String.valueOf(totworkcode), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totgeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totnongeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            
				
					if(list.size()==0) 
						CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 21, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report GT2- Project.pdf");
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
	
	@RequestMapping(value = "/downloadDistwiseAllAssetGeoDataPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistwiseAllAssetGeoDataPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcode= request.getParameter("stcode");
		String stname= request.getParameter("stname");
		List<AssetGeoRefBean> list = assetGeoService.getDistWiseGeoRefData(Integer.parseInt(stname));
		
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("AllAssetGeoDataReport");
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
			
				paragraph3 = new Paragraph("Report GT2 - District Wise Work Code Status with Geotagging Details for State \r\n"+" '"+stcode+"' ", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(22);
		        table.setWidths(new int[]{3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(3);
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Projects", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "NRM Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "EPA Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Livelihood Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Work Code excluding Foreclosed (NRM(5+6+7) + EPA(9+10+11) + LIV(13+14+15) + PROD(17+18+19))", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Geotag Work Code", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Non Geotag Work Code (20-21)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted) ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
		        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
		        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
		        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
				
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
				CommonFunctions.insertCellHeader(table, "18", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "19", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "20", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "22", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				int k = 1;
				int totproj=0;
				int totCreated_N = 0;
				int totUnStarted_N = 0;
				int totOngoing_N = 0;
				int totCompleted_N = 0;
				int totCreated_E = 0;
				int totUnStarted_E = 0;
				int totOngoing_E = 0;
				int totCompleted_E = 0;
				int totCreated_L = 0;
				int totUnStarted_L = 0;
				int totOngoing_L = 0;
				int totCompleted_L = 0;
				int totCreated_P = 0;
				int totUnStarted_P = 0;
				int totOngoing_P = 0;
				int totCompleted_P = 0;
				int totgeorefwrkcd = 0;
				BigInteger totworkcode = BigInteger.valueOf(0);
				BigInteger  totnongeorefwrkcd=BigInteger.valueOf(0);
				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotproj()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotworkcode()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotgeorefwrkcd()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotnongeorefwrkcd()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						totCreated_N = totCreated_N + list.get(i).getN_created();
						totUnStarted_N = totUnStarted_N + list.get(i).getN_unstarted();
						totOngoing_N = totOngoing_N + list.get(i).getN_ongoing();
						totCompleted_N = totCompleted_N + list.get(i).getN_completed();
						
						totCreated_E = totCreated_E + list.get(i).getE_created();
						totUnStarted_E = totUnStarted_E + list.get(i).getE_unstarted();
						totOngoing_E = totOngoing_E + list.get(i).getE_ongoing();
						totCompleted_E = totCompleted_E + list.get(i).getE_completed();
						
						totCreated_L = totCreated_L + list.get(i).getL_created();
						totUnStarted_L = totUnStarted_L + list.get(i).getL_unstarted();
						totOngoing_L = totOngoing_L + list.get(i).getL_ongoing();
						totCompleted_L = totCompleted_L + list.get(i).getL_completed();
						
						totCreated_P = totCreated_P + list.get(i).getP_created();
						totUnStarted_P = totUnStarted_P + list.get(i).getP_unstarted();
						totOngoing_P = totOngoing_P + list.get(i).getP_ongoing();
						totCompleted_P = totCompleted_P + list.get(i).getP_completed();
						
						totproj=totproj+list.get(i).getTotproj();
						totworkcode=totworkcode.add(list.get(i).getTotworkcode());
						totgeorefwrkcd = totgeorefwrkcd+list.get(i).getTotgeorefwrkcd();
						totnongeorefwrkcd = totnongeorefwrkcd.add(list.get(i).getTotnongeorefwrkcd());
						
						k++;
					}
					
				
				CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(totworkcode), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totgeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totnongeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            
				
					if(list.size()==0) 
						CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 22, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report GT2- District.pdf");
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

	@RequestMapping(value = "/downloadAllAssetGeoDataPDF", method = RequestMethod.POST)
	public ModelAndView downloadAllAssetGeoDataPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		List<AssetGeoRefBean> list = assetGeoService.getGeoRefData();
		
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("AllAssetGeoDataReport");
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
			
				paragraph3 = new Paragraph("Report GT2 - State Wise Work Code Status with Geotagging Details", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(22);
		        table.setWidths(new int[]{3, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(3);
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Projects", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "NRM Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "EPA Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Livelihood Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Production Works", Element.ALIGN_CENTER, 4, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Work Code excluding Foreclosed (NRM(5+6+7) + EPA(9+10+11) + LIV(13+14+15) + PROD(17+18+19))", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Geotag Work Code", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total Non Geotag Work Code (20-21)", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
		        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
		        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
		        CommonFunctions.insertCellHeader(table, "Total Works", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Not Started (Status Not Submitted)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Ongoing", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "Completed", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		        
				
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
				CommonFunctions.insertCellHeader(table, "18", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "19", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "20", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "21", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "22", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				int k = 1;
				int totproj=0;
				int totCreated_N = 0;
				int totUnStarted_N = 0;
				int totOngoing_N = 0;
				int totCompleted_N = 0;
				int totCreated_E = 0;
				int totUnStarted_E = 0;
				int totOngoing_E = 0;
				int totCompleted_E = 0;
				int totCreated_L = 0;
				int totUnStarted_L = 0;
				int totOngoing_L = 0;
				int totCompleted_L = 0;
				int totCreated_P = 0;
				int totUnStarted_P = 0;
				int totOngoing_P = 0;
				int totCompleted_P = 0;
				int totgeorefwrkcd = 0;
				BigInteger totworkcode = BigInteger.valueOf(0);
				BigInteger  totnongeorefwrkcd=BigInteger.valueOf(0);
				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotproj()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getN_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getE_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getL_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_created()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_unstarted()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_ongoing()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getP_completed()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotworkcode()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotgeorefwrkcd()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotnongeorefwrkcd()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
						totCreated_N = totCreated_N + list.get(i).getN_created();
						totUnStarted_N = totUnStarted_N + list.get(i).getN_unstarted();
						totOngoing_N = totOngoing_N + list.get(i).getN_ongoing();
						totCompleted_N = totCompleted_N + list.get(i).getN_completed();
						
						totCreated_E = totCreated_E + list.get(i).getE_created();
						totUnStarted_E = totUnStarted_E + list.get(i).getE_unstarted();
						totOngoing_E = totOngoing_E + list.get(i).getE_ongoing();
						totCompleted_E = totCompleted_E + list.get(i).getE_completed();
						
						totCreated_L = totCreated_L + list.get(i).getL_created();
						totUnStarted_L = totUnStarted_L + list.get(i).getL_unstarted();
						totOngoing_L = totOngoing_L + list.get(i).getL_ongoing();
						totCompleted_L = totCompleted_L + list.get(i).getL_completed();
						
						totCreated_P = totCreated_P + list.get(i).getP_created();
						totUnStarted_P = totUnStarted_P + list.get(i).getP_unstarted();
						totOngoing_P = totOngoing_P + list.get(i).getP_ongoing();
						totCompleted_P = totCompleted_P + list.get(i).getP_completed();
						
						totproj=totproj+list.get(i).getTotproj();
						totworkcode=totworkcode.add(list.get(i).getTotworkcode());
						totgeorefwrkcd = totgeorefwrkcd+list.get(i).getTotgeorefwrkcd();
						totnongeorefwrkcd = totnongeorefwrkcd.add(list.get(i).getTotnongeorefwrkcd());
						
						k++;
					}
					
				
				CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(totworkcode), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totgeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totnongeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            
				
					if(list.size()==0) 
						CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 22, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report GT2- State.pdf");
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
	
	@RequestMapping(value = "/downloadProjectExcelAllAssetGeoData", method = RequestMethod.POST)
	@ResponseBody
	public String downloadProjectExcelAllAssetGeoData(HttpServletRequest request, HttpServletResponse response) 
	{
		String dcode = request.getParameter("dcode");
		String distname = request.getParameter("distname");
		String stname = request.getParameter("stname");
		List<AssetGeoRefBean> list = assetGeoService.getProjWiseGeoRefData(Integer.parseInt(distname));
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report GT2 - Project Wise Work Code Status with Geotagging Details");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report GT2 - Project Wise Work Code Status with Geotagging Details for District "+" '"+dcode+"' of State '"+stname+"'";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 20, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
		sheet.addMergedRegion(mergedRegion);
		
		
		mergedRegion = new CellRangeAddress(5,5,2,5);
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(5,5,6,9); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(5,5,10,13); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(5,5,14,17); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(5,6,0,0); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(5,6,1,1); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(5,6,18,18); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(5,6,19,19); 
	    sheet.addMergedRegion(mergedRegion);
	    mergedRegion = new CellRangeAddress(5,6,20,20); 
	    sheet.addMergedRegion(mergedRegion);
	    
	    
		
		Row rowhead = sheet.createRow(5);
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Project Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("NRM Works");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=3;i<6;i++)
		{
			cell = rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(6);
		cell.setCellValue("EPA Works");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=7;i<10;i++)
		{
			cell = rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Livelihood Works");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=11;i<14;i++)
		{
			cell = rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(14);
		cell.setCellValue("Production Works");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		for(int i=15;i<18;i++)
		{
			cell = rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(18);
		cell.setCellValue("Total Work Code excluding Foreclosed (NRM(4+5+6) + EPA(8+9+10) + LIV(12+13+14) + PROD(16+17+18))");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(19);
		cell.setCellValue("Total Geotag Work Code");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(20);
		cell.setCellValue("Total Non Geotag Work Code (19-20)");  
		cell.setCellStyle(style);
		
		
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<2;i++)
		{
			cell = rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(2);
		cell.setCellValue("Total Works");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("Not Started (Status Not Submitted)");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(4);
		cell.setCellValue("Ongoing");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("Completed");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Total Works");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Not Started (Status Not Submitted)");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Ongoing");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(9);
		cell.setCellValue("Completed");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(10);
		cell.setCellValue("Total Works");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(11);
		cell.setCellValue("Not Started (Status Not Submitted)");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(12);
		cell.setCellValue("Ongoing");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(13);
		cell.setCellValue("Completed");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(14);
		cell.setCellValue("Total Works");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(15);
		cell.setCellValue("Not Started (Status Not Submitted)");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(16);
		cell.setCellValue("Ongoing");  
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(17);
		cell.setCellValue("Completed");  
		cell.setCellStyle(style);
		
		for(int i=18;i<21;i++)
		{
			cell = rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		
		
		Row rowhead2 = sheet.createRow(7);
		
		for(int i=0;i<21;i++)
		{
			cell =rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		
		
		int sno = 1;
		int rowno  = 8;
		int totCreated_N = 0;
		int totUnStarted_N = 0;
		int totOngoing_N = 0;
		int totCompleted_N = 0;
		int totCreated_E = 0;
		int totUnStarted_E = 0;
		int totOngoing_E = 0;
		int totCompleted_E = 0;
		int totCreated_L = 0;
		int totUnStarted_L = 0;
		int totOngoing_L = 0;
		int totCompleted_L = 0;
		int totCreated_P = 0;
		int totUnStarted_P = 0;
		int totOngoing_P = 0;
		int totCompleted_P = 0;
		int totgeorefwrkcd = 0;
		double totworkcode = 0.0;
		double  totnongeorefwrkcd=0.0;
		
		
	    for(AssetGeoRefBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getProjname());
	    	row.createCell(2).setCellValue(bean.getN_created());
			row.createCell(3).setCellValue(bean.getN_unstarted());
			row.createCell(4).setCellValue(bean.getN_ongoing());
			row.createCell(5).setCellValue(bean.getN_completed());
			row.createCell(6).setCellValue(bean.getE_created());
			row.createCell(7).setCellValue(bean.getE_unstarted());
			row.createCell(8).setCellValue(bean.getE_ongoing());
			row.createCell(9).setCellValue(bean.getE_completed());
			row.createCell(10).setCellValue(bean.getL_created());
			row.createCell(11).setCellValue(bean.getL_unstarted());
			row.createCell(12).setCellValue(bean.getL_ongoing());
			row.createCell(13).setCellValue(bean.getL_completed());
			row.createCell(14).setCellValue(bean.getP_created());
			row.createCell(15).setCellValue(bean.getP_unstarted());
			row.createCell(16).setCellValue(bean.getP_ongoing());
			row.createCell(17).setCellValue(bean.getP_completed());
	    	row.createCell(18).setCellValue(bean.getTotworkcode().doubleValue());
	    	row.createCell(19).setCellValue(bean.getTotgeorefwrkcd());
	    	row.createCell(20).setCellValue(bean.getTotnongeorefwrkcd().doubleValue());
	    	
	    	
	    	totCreated_N = totCreated_N + bean.getN_created();
			totUnStarted_N = totUnStarted_N + bean.getN_unstarted();
			totOngoing_N = totOngoing_N + bean.getN_ongoing();
			totCompleted_N = totCompleted_N + bean.getN_completed();
			
			totCreated_E = totCreated_E + bean.getE_created();
			totUnStarted_E = totUnStarted_E + bean.getE_unstarted();
			totOngoing_E = totOngoing_E + bean.getE_ongoing();
			totCompleted_E = totCompleted_E + bean.getE_completed();
			
			totCreated_L = totCreated_L + bean.getL_created();
			totUnStarted_L = totUnStarted_L + bean.getL_unstarted();
			totOngoing_L = totOngoing_L + bean.getL_ongoing();
			totCompleted_L = totCompleted_L + bean.getL_completed();
			
			totCreated_P = totCreated_P + bean.getP_created();
			totUnStarted_P = totUnStarted_P + bean.getP_unstarted();
			totOngoing_P = totOngoing_P + bean.getP_ongoing();
			totCompleted_P = totCompleted_P + bean.getP_completed();
	    	
	    	totgeorefwrkcd = totgeorefwrkcd + bean.getTotgeorefwrkcd();
	    	totworkcode = totworkcode+bean.getTotworkcode().doubleValue();
	    	totnongeorefwrkcd = totnongeorefwrkcd + bean.getTotnongeorefwrkcd().doubleValue();
	    	
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
		cell.setCellValue("Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellValue(totCreated_N);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totUnStarted_N);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totOngoing_N);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totCompleted_N);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totCreated_E);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totUnStarted_E);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totOngoing_E);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totCompleted_E);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totCreated_L);
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totUnStarted_L);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totOngoing_L);
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(totCompleted_L);
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(totCreated_P);
		cell.setCellStyle(style1);
		cell = row.createCell(15);
		cell.setCellValue(totUnStarted_P);
		cell.setCellStyle(style1);
		cell = row.createCell(16);
		cell.setCellValue(totOngoing_P);
		cell.setCellStyle(style1);
		cell = row.createCell(17);
		cell.setCellValue(totCompleted_P);
		cell.setCellStyle(style1);
		cell = row.createCell(18);
		cell.setCellValue(totworkcode);
		cell.setCellStyle(style1);
		cell = row.createCell(19);
		cell.setCellValue(totgeorefwrkcd);
		cell.setCellStyle(style1);
		cell = row.createCell(20);
		cell.setCellValue(totnongeorefwrkcd);
		cell.setCellStyle(style1);
		
		
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 20);
	    String fileName = "attachment; filename=Report GT2- Project.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/assetGeoReport";
	}

@RequestMapping(value = "/downloadDistExcelAllAssetGeoData", method = RequestMethod.POST)
@ResponseBody
public String downloadDistExcelAllAssetGeoData(HttpServletRequest request, HttpServletResponse response) 
{
	String stcode= request.getParameter("stcode");
	String stname= request.getParameter("stname");
	List<AssetGeoRefBean> list = assetGeoService.getDistWiseGeoRefData(Integer.parseInt(stname));
	
	
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("Report GT2 - District Wise Work Code Status with Geotagging Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "Report GT2 - District Wise Work Code Status with Geotagging Details for State "+" '"+stcode+"' ";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 21, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	sheet.addMergedRegion(mergedRegion);
	
	
	mergedRegion = new CellRangeAddress(5,5,3,6);
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,5,7,10); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,5,11,14); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,5,15,18); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,0,0); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,1,1); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,2,2); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,19,19); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,20,20); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,21,21); 
    sheet.addMergedRegion(mergedRegion);
	
	Row rowhead = sheet.createRow(5);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("District Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total Projects");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("NRM Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=4;i<7;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(7);
	cell.setCellValue("EPA Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=8;i<11;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(11);
	cell.setCellValue("Livelihood Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=12;i<15;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(15);
	cell.setCellValue("Production Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=16;i<19;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(19);
	cell.setCellValue("Total Work Code excluding Foreclosed (NRM(5+6+7) + EPA(9+10+11) + LIV(13+14+15) + PROD(17+18+19))");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(20);
	cell.setCellValue("Total Geotag Work Code");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(21);
	cell.setCellValue("Total Non Geotag Work Code (20-21)");  
	cell.setCellStyle(style);
	
	
	Row rowhead1 = sheet.createRow(6);
	
	for(int i=0;i<3;i++)
	{
		cell = rowhead1.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead1.createCell(3);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(4);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(5);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(6);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(7);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(8);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(9);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(10);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(11);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(12);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(13);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(14);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(15);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(16);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(17);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(18);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	for(int i=19;i<22;i++)
	{
		cell = rowhead1.createCell(i);
		cell.setCellStyle(style);
	}
	
	
	Row rowhead2 = sheet.createRow(7);
	
	for(int i=0;i<22;i++)
	{
		cell =rowhead2.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno  = 8;
	int totproj=0;
	int totCreated_N = 0;
	int totUnStarted_N = 0;
	int totOngoing_N = 0;
	int totCompleted_N = 0;
	int totCreated_E = 0;
	int totUnStarted_E = 0;
	int totOngoing_E = 0;
	int totCompleted_E = 0;
	int totCreated_L = 0;
	int totUnStarted_L = 0;
	int totOngoing_L = 0;
	int totCompleted_L = 0;
	int totCreated_P = 0;
	int totUnStarted_P = 0;
	int totOngoing_P = 0;
	int totCompleted_P = 0;
	int totgeorefwrkcd = 0;
	double totworkcode = 0.0;
	double  totnongeorefwrkcd=0.0;
	
	
    for(AssetGeoRefBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getDistname());
    	row.createCell(2).setCellValue(bean.getTotproj());
    	row.createCell(3).setCellValue(bean.getN_created());
    	row.createCell(4).setCellValue(bean.getN_unstarted());
    	row.createCell(5).setCellValue(bean.getN_ongoing());
    	row.createCell(6).setCellValue(bean.getN_completed());
    	row.createCell(7).setCellValue(bean.getE_created());
    	row.createCell(8).setCellValue(bean.getE_unstarted());
    	row.createCell(9).setCellValue(bean.getE_ongoing());
    	row.createCell(10).setCellValue(bean.getE_completed());
    	row.createCell(11).setCellValue(bean.getL_created());
    	row.createCell(12).setCellValue(bean.getL_unstarted());
    	row.createCell(13).setCellValue(bean.getL_ongoing());
    	row.createCell(14).setCellValue(bean.getL_completed());
    	row.createCell(15).setCellValue(bean.getP_created());
    	row.createCell(16).setCellValue(bean.getP_unstarted());
    	row.createCell(17).setCellValue(bean.getP_ongoing());
    	row.createCell(18).setCellValue(bean.getP_completed());
    	row.createCell(19).setCellValue(bean.getTotworkcode().doubleValue());
    	row.createCell(20).setCellValue(bean.getTotgeorefwrkcd());
    	row.createCell(21).setCellValue(bean.getTotnongeorefwrkcd().doubleValue());
    	
    	
    	
    	totCreated_N = totCreated_N + bean.getN_created();
		totUnStarted_N = totUnStarted_N + bean.getN_unstarted();
		totOngoing_N = totOngoing_N + bean.getN_ongoing();
		totCompleted_N = totCompleted_N + bean.getN_completed();
		
		totCreated_E = totCreated_E + bean.getE_created();
		totUnStarted_E = totUnStarted_E + bean.getE_unstarted();
		totOngoing_E = totOngoing_E + bean.getE_ongoing();
		totCompleted_E = totCompleted_E + bean.getE_completed();
		
		totCreated_L = totCreated_L + bean.getL_created();
		totUnStarted_L = totUnStarted_L + bean.getL_unstarted();
		totOngoing_L = totOngoing_L + bean.getL_ongoing();
		totCompleted_L = totCompleted_L + bean.getL_completed();
		
		totCreated_P = totCreated_P + bean.getP_created();
		totUnStarted_P = totUnStarted_P + bean.getP_unstarted();
		totOngoing_P = totOngoing_P + bean.getP_ongoing();
		totCompleted_P = totCompleted_P + bean.getP_completed();
    	
    	totproj = totproj + bean.getTotproj();
    	totgeorefwrkcd = totgeorefwrkcd + bean.getTotgeorefwrkcd();
    	totworkcode = totworkcode+bean.getTotworkcode().doubleValue();
    	totnongeorefwrkcd = totnongeorefwrkcd + bean.getTotnongeorefwrkcd().doubleValue();
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
	cell.setCellValue("Total");
	cell.setCellStyle(style1);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	cell = row.createCell(1);
	cell.setCellStyle(style1);
	cell = row.createCell(2);
	cell.setCellValue(totproj);
	cell.setCellStyle(style1);
	cell = row.createCell(3);
	cell.setCellValue(totCreated_N);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totUnStarted_N);
	cell.setCellStyle(style1);
	cell = row.createCell(5);
	cell.setCellValue(totOngoing_N);
	cell.setCellStyle(style1);
	cell = row.createCell(6);
	cell.setCellValue(totCompleted_N);
	cell.setCellStyle(style1);
	cell = row.createCell(7);
	cell.setCellValue(totCreated_E);
	cell.setCellStyle(style1);
	cell = row.createCell(8);
	cell.setCellValue(totUnStarted_E);
	cell.setCellStyle(style1);
	cell = row.createCell(9);
	cell.setCellValue(totOngoing_E);
	cell.setCellStyle(style1);
	cell = row.createCell(10);
	cell.setCellValue(totCompleted_E);
	cell.setCellStyle(style1);
	cell = row.createCell(11);
	cell.setCellValue(totCreated_L);
	cell.setCellStyle(style1);
	cell = row.createCell(12);
	cell.setCellValue(totUnStarted_L);
	cell.setCellStyle(style1);
	cell = row.createCell(13);
	cell.setCellValue(totOngoing_L);
	cell.setCellStyle(style1);
	cell = row.createCell(14);
	cell.setCellValue(totCompleted_L);
	cell.setCellStyle(style1);
	cell = row.createCell(15);
	cell.setCellValue(totCreated_P);
	cell.setCellStyle(style1);
	cell = row.createCell(16);
	cell.setCellValue(totUnStarted_P);
	cell.setCellStyle(style1);
	cell = row.createCell(17);
	cell.setCellValue(totOngoing_P);
	cell.setCellStyle(style1);
	cell = row.createCell(18);
	cell.setCellValue(totCompleted_P);
	cell.setCellStyle(style1);
	cell = row.createCell(19);
	cell.setCellValue(totworkcode);
	cell.setCellStyle(style1);
	cell = row.createCell(20);
	cell.setCellValue(totgeorefwrkcd);
	cell.setCellStyle(style1);
	cell = row.createCell(21);
	cell.setCellValue(totnongeorefwrkcd);
	cell.setCellStyle(style1);
	
	

    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 21);
    String fileName = "attachment; filename=Report GT2- District.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/assetGeoReport";
}


	
@RequestMapping(value = "/downloadExcelAllAssetGeoData", method = RequestMethod.POST)
@ResponseBody
public String downloadExcelAllAssetGeoData(HttpServletRequest request, HttpServletResponse response) 
{
	List<AssetGeoRefBean> list = assetGeoService.getGeoRefData();
	
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("Report GT2 - State Wise Work Code Status with Geotagging Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "Report GT2 - State Wise Work Code Status with Geotagging Details";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 21, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	sheet.addMergedRegion(mergedRegion);
	
	
	mergedRegion = new CellRangeAddress(5,5,3,6);
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,5,7,10); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,5,11,14); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,5,15,18); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,0,0); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,1,1); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,2,2); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,19,19); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,20,20); 
    sheet.addMergedRegion(mergedRegion);
    mergedRegion = new CellRangeAddress(5,6,21,21); 
    sheet.addMergedRegion(mergedRegion);
    
	
	Row rowhead = sheet.createRow(5);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("State Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total Projects");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("NRM Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=4;i<7;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(7);
	cell.setCellValue("EPA Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=8;i<11;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(11);
	cell.setCellValue("Livelihood Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=12;i<15;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(15);
	cell.setCellValue("Production Works");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	for(int i=16;i<19;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead.createCell(19);
	cell.setCellValue("Total Work Code excluding Foreclosed (NRM(5+6+7) + EPA(9+10+11) + LIV(13+14+15) + PROD(17+18+19))");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(20);
	cell.setCellValue("Total Geotag Work Code");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(21);
	cell.setCellValue("Total Non Geotag Work Code (20-21)");  
	cell.setCellStyle(style);
	
	
	
	Row rowhead1 = sheet.createRow(6);
	
	for(int i=0;i<3;i++)
	{
		cell = rowhead1.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead1.createCell(3);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(4);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(5);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(6);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	
	cell = rowhead1.createCell(7);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(8);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(9);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(10);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	
	cell = rowhead1.createCell(11);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(12);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(13);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(14);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	
	cell = rowhead1.createCell(15);
	cell.setCellValue("Total Works");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(16);
	cell.setCellValue("Not Started (Status Not Submitted)");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(17);
	cell.setCellValue("Ongoing");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(18);
	cell.setCellValue("Completed");  
	cell.setCellStyle(style);
	
	
	for(int i=19;i<21;i++)
	{
		cell = rowhead1.createCell(i);
		cell.setCellStyle(style);
	}
	
	
	
	Row rowhead2 = sheet.createRow(7);
	
	for(int i=0;i<22;i++)
	{
		cell =rowhead2.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	
	int sno = 1;
	int rowno  = 8;
	int totproj=0;
	int totCreated_N = 0;
	int totUnStarted_N = 0;
	int totOngoing_N = 0;
	int totCompleted_N = 0;
	int totCreated_E = 0;
	int totUnStarted_E = 0;
	int totOngoing_E = 0;
	int totCompleted_E = 0;
	int totCreated_L = 0;
	int totUnStarted_L = 0;
	int totOngoing_L = 0;
	int totCompleted_L = 0;
	int totCreated_P = 0;
	int totUnStarted_P = 0;
	int totOngoing_P = 0;
	int totCompleted_P = 0;
	int totgeorefwrkcd = 0;
	double totworkcode = 0.0;
	double  totnongeorefwrkcd=0.0;
	
	
    for(AssetGeoRefBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getStname());
    	row.createCell(2).setCellValue(bean.getTotproj());
    	row.createCell(3).setCellValue(bean.getN_created());
    	row.createCell(4).setCellValue(bean.getN_unstarted());
    	row.createCell(5).setCellValue(bean.getN_ongoing());
    	row.createCell(6).setCellValue(bean.getN_completed());
    	row.createCell(7).setCellValue(bean.getE_created());
    	row.createCell(8).setCellValue(bean.getE_unstarted());
    	row.createCell(9).setCellValue(bean.getE_ongoing());
    	row.createCell(10).setCellValue(bean.getE_completed());
    	row.createCell(11).setCellValue(bean.getL_created());
    	row.createCell(12).setCellValue(bean.getL_unstarted());
    	row.createCell(13).setCellValue(bean.getL_ongoing());
    	row.createCell(14).setCellValue(bean.getL_completed());
    	row.createCell(15).setCellValue(bean.getP_created());
    	row.createCell(16).setCellValue(bean.getP_unstarted());
    	row.createCell(17).setCellValue(bean.getP_ongoing());
    	row.createCell(18).setCellValue(bean.getP_completed());
    	row.createCell(19).setCellValue(bean.getTotworkcode().doubleValue());
    	row.createCell(20).setCellValue(bean.getTotgeorefwrkcd());
    	row.createCell(21).setCellValue(bean.getTotnongeorefwrkcd().doubleValue());
    	
    	
    	totCreated_N = totCreated_N + bean.getN_created();
    	totUnStarted_N = totUnStarted_N + bean.getN_unstarted();
    	totOngoing_N = totOngoing_N + bean.getN_ongoing();
    	totCompleted_N = totCompleted_N + bean.getN_completed();
    	
    	totCreated_E = totCreated_E + bean.getE_created();
    	totUnStarted_E = totUnStarted_E + bean.getE_unstarted();
    	totOngoing_E = totOngoing_E + bean.getE_ongoing();
    	totCompleted_E = totCompleted_E + bean.getE_completed();
    	
    	totCreated_L = totCreated_L + bean.getL_created();
    	totUnStarted_L = totUnStarted_L + bean.getL_unstarted();
    	totOngoing_L = totOngoing_L + bean.getL_ongoing();
    	totCompleted_L = totCompleted_L + bean.getL_completed();
    	
    	totCreated_P = totCreated_P + bean.getP_created();
    	totUnStarted_P = totUnStarted_P + bean.getP_unstarted();
    	totOngoing_P = totOngoing_P + bean.getP_ongoing();
    	totCompleted_P = totCompleted_P + bean.getP_completed();
    	
    	totproj = totproj + bean.getTotproj();
    	totgeorefwrkcd = totgeorefwrkcd + bean.getTotgeorefwrkcd();
    	totworkcode = totworkcode+bean.getTotworkcode().doubleValue();
    	totnongeorefwrkcd = totnongeorefwrkcd + bean.getTotnongeorefwrkcd().doubleValue();
    	
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
	cell.setCellValue("Total");
	cell.setCellStyle(style1);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	cell = row.createCell(1);
	cell.setCellStyle(style1);
	cell = row.createCell(2);
	cell.setCellValue(totproj);
	cell.setCellStyle(style1);
	cell = row.createCell(3);
	cell.setCellValue(totCreated_N);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totUnStarted_N);
	cell.setCellStyle(style1);
	cell = row.createCell(5);
	cell.setCellValue(totOngoing_N);
	cell.setCellStyle(style1);
	cell = row.createCell(6);
	cell.setCellValue(totCompleted_N);
	cell.setCellStyle(style1);
	cell = row.createCell(7);
	cell.setCellValue(totCreated_E);
	cell.setCellStyle(style1);
	cell = row.createCell(8);
	cell.setCellValue(totUnStarted_E);
	cell.setCellStyle(style1);
	cell = row.createCell(9);
	cell.setCellValue(totOngoing_E);
	cell.setCellStyle(style1);
	cell = row.createCell(10);
	cell.setCellValue(totCompleted_E);
	cell.setCellStyle(style1);
	cell = row.createCell(11);
	cell.setCellValue(totCreated_L);
	cell.setCellStyle(style1);
	cell = row.createCell(12);
	cell.setCellValue(totUnStarted_L);
	cell.setCellStyle(style1);
	cell = row.createCell(13);
	cell.setCellValue(totOngoing_L);
	cell.setCellStyle(style1);
	cell = row.createCell(14);
	cell.setCellValue(totCompleted_L);
	cell.setCellStyle(style1);
	cell = row.createCell(15);
	cell.setCellValue(totCreated_P);
	cell.setCellStyle(style1);
	cell = row.createCell(16);
	cell.setCellValue(totUnStarted_P);
	cell.setCellStyle(style1);
	cell = row.createCell(17);
	cell.setCellValue(totOngoing_P);
	cell.setCellStyle(style1);
	cell = row.createCell(18);
	cell.setCellValue(totCompleted_P);
	cell.setCellStyle(style1);
	cell = row.createCell(19);
	cell.setCellValue(totworkcode);
	cell.setCellStyle(style1);
	cell = row.createCell(20);
	cell.setCellValue(totgeorefwrkcd);
	cell.setCellStyle(style1);
	cell = row.createCell(21);
	cell.setCellValue(totnongeorefwrkcd);
	cell.setCellStyle(style1);
	
	

    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 21);
    String fileName = "attachment; filename=Report GT2- State.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/assetGeoReport";
}


@RequestMapping(value = "/downloadProjDetailAllAssetGeoDataPDF", method = RequestMethod.POST)
public ModelAndView downloadProjDetailAllAssetGeoDataPDF(HttpServletRequest request, HttpServletResponse response) {
	
	String stname = request.getParameter("stname");
	String distname = request.getParameter("distname");
	String projid = request.getParameter("projid");
	String projname = request.getParameter("projname");
	Map<String, String> stgeMap = new LinkedHashMap<>();
	List<AssetGeoRefBean> listt = assetGeoService.getProjWiseGeoRefDetails(Integer.parseInt(projid));
	listt.forEach(s -> {
		stgeMap.put("Pre Implementation" + "," + s.getWorkcode(), "");
		stgeMap.put("Mid Implementation" + "," + s.getWorkcode(), "");
		stgeMap.put("Post Implementation" + "," + s.getWorkcode(), "");

	});

	listt.forEach(s -> {
		if (stgeMap.containsKey(s.getStage() + "," + s.getWorkcode())) {
			String status = "";
			if (!stgeMap.get(s.getStage() + "," + s.getWorkcode()).equals(""))
				status = stgeMap.get(s.getStage() + "," + s.getWorkcode()) + ", " + s.getStatus();
			else
				status = s.getStatus();
			stgeMap.put(s.getStage() + "," + s.getWorkcode(), status);
		} else {
			stgeMap.put(s.getStage() + "," + s.getWorkcode(), s.getStatus());
		}
	});

	try {

		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("AllAssetGeoDataReport");
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

		paragraph3 = new Paragraph("Report GT2 - Project Wise Work Code Status with Geotagging Details for Project \r\n"
				+ " '" + projname + "' " + "of District " + "'" + distname + "' " + "of State " + "'" + stname + "' ",
				f3);

		paragraph2.setAlignment(Element.ALIGN_CENTER);
		paragraph3.setAlignment(Element.ALIGN_CENTER);
		paragraph2.setSpacingAfter(10);
		paragraph3.setSpacingAfter(10);
		CommonFunctions.addHeader(document);
		document.add(paragraph2);
		document.add(paragraph3);
		table = new PdfPTable(7);
		table.setWidths(new int[] { 3, 8, 5, 5, 6, 6, 6 });
		table.setWidthPercentage(60);
		table.setSpacingBefore(0f);
		table.setSpacingAfter(0f);
		table.setHeaderRows(3);

		CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Work Code", Element.ALIGN_CENTER, 1, 2, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Head Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Activity Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Stage", Element.ALIGN_CENTER, 3, 1, bf8Bold);

		CommonFunctions.insertCellHeader(table, "Pre Implementation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Mid Implementation", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Post Implementation", Element.ALIGN_CENTER, 1, 1, bf8Bold);

		CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);

		int k = 1;
		String wrkcd = "";
		int epa = 0;
		int liv = 0;
		int prod = 0;
		int dpr = 0;
		char hd;
		if (listt.size() != 0)
			for (int i = 0; i < listt.size(); i++) {
				hd = listt.get(i).getHeadcode();
				if (hd == 'E') {
					if (epa == 0) {
						CommonFunctions.insertCellHeader(table, "EPA", Element.ALIGN_CENTER, 7, 1, bf8Bold);
					}

					if (!listt.get(i).getWorkcode().equals(wrkcd)) {
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_CENTER, 1, 1, bf8);
						CommonFunctions.insertCell(table, listt.get(i).getWorkcode(), Element.ALIGN_CENTER, 1, 1, bf8);
						CommonFunctions.insertCell(table, listt.get(i).getHeaddes(), Element.ALIGN_CENTER, 1, 1, bf8);
						CommonFunctions.insertCell(table, listt.get(i).getActdes(), Element.ALIGN_CENTER, 1, 1, bf8);
						for (Map.Entry<String, String> map : stgeMap.entrySet()) {
							if (listt.get(i).getWorkcode()
									.equals(map.getKey().substring(map.getKey().indexOf(',') + 1))) {

								CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1,
										bf8);

							}
						}
						wrkcd = listt.get(i).getWorkcode();
						k++;
					}
					epa = 1;
				}
			}

		for (int i = 0; i < listt.size(); i++) {
			hd = listt.get(i).getHeadcode();
			if (hd == 'L') {
				if (liv == 0) {
					CommonFunctions.insertCellHeader(table, "Livelihood", Element.ALIGN_CENTER, 7, 1, bf8Bold);
				}

				if (!listt.get(i).getWorkcode().equals(wrkcd)) {
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, listt.get(i).getWorkcode(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, listt.get(i).getHeaddes(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, listt.get(i).getActdes(), Element.ALIGN_CENTER, 1, 1, bf8);
					for (Map.Entry<String, String> map : stgeMap.entrySet()) {
						if (listt.get(i).getWorkcode().equals(map.getKey().substring(map.getKey().indexOf(',') + 1))) {
							CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1,
									bf8);
						}
					}
					wrkcd = listt.get(i).getWorkcode();
					k++;
				}
				liv = 1;
			}
		}
		for (int i = 0; i < listt.size(); i++) {
			hd = listt.get(i).getHeadcode();
			if (hd == 'P') {
				if (prod == 0) {
					CommonFunctions.insertCellHeader(table, "Production", Element.ALIGN_CENTER, 7, 1, bf8Bold);
				}

				if (!listt.get(i).getWorkcode().equals(wrkcd)) {
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, listt.get(i).getWorkcode(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, listt.get(i).getHeaddes(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, listt.get(i).getActdes(), Element.ALIGN_CENTER, 1, 1, bf8);
					for (Map.Entry<String, String> map : stgeMap.entrySet()) {
						if (listt.get(i).getWorkcode().equals(map.getKey().substring(map.getKey().indexOf(',') + 1))) {
							CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1,
									bf8);
						}
					}
					wrkcd = listt.get(i).getWorkcode();
					k++;
				}
				prod = 1;
			}

		}
		for (int i = 0; i < listt.size(); i++) {
			hd = listt.get(i).getHeadcode();
			if (String.valueOf(hd).matches("[0-9]+")) {
				if (dpr == 0) {
					CommonFunctions.insertCellHeader(table, "NRM", Element.ALIGN_CENTER, 7, 1, bf8Bold);
				}

				if (!listt.get(i).getWorkcode().equals(wrkcd)) {
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, listt.get(i).getWorkcode(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, listt.get(i).getHeaddes(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCell(table, listt.get(i).getActdes(), Element.ALIGN_CENTER, 1, 1, bf8);
					for (Map.Entry<String, String> map : stgeMap.entrySet()) {
						if (listt.get(i).getWorkcode().equals(map.getKey().substring(map.getKey().indexOf(',') + 1))) {
							CommonFunctions.insertCell(table, map.getValue().toString(), Element.ALIGN_CENTER, 1, 1,
									bf8);
						}
					}
					wrkcd = listt.get(i).getWorkcode();
					k++;
				}
				dpr = 1;
			}
		}

		if (listt.size() == 0)
			CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 7, 1, bf8);

		document.add(table);
		table = new PdfPTable(1);
		table.setWidthPercentage(70);
		table.setSpacingBefore(15f);
		table.setSpacingAfter(0f);
		CommonFunctions.insertCellPageHeader(table,
				"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "
						+ CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"),
				Element.ALIGN_LEFT, 1, 4, bf8);
		document.add(table);
		document.close();
		response.setContentType("application/pdf");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-Disposition", "attachment;filename=Report GT2- ProjectDetail.pdf");
		response.setHeader("Pragma", "public");
		response.setContentLength(baos.size());
		OutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();

	} catch (Exception ex) {
		ex.printStackTrace();
	}

	return null;
}

@RequestMapping(value = "/downloadProjDetailAllAssetGeoDataExcel", method = RequestMethod.POST)
@ResponseBody
public String downloadProjDetailAllAssetGeoDataExcel(HttpServletRequest request, HttpServletResponse response) 
{
	String stname = request.getParameter("stname");
	String distname = request.getParameter("distname");
	String projid = request.getParameter("projid");
	String projname = request.getParameter("projname");
	Map<String,String> stgeMap = new LinkedHashMap<>();
	List<AssetGeoRefBean> list = assetGeoService.getProjWiseGeoRefDetails(Integer.parseInt(projid));
	list.forEach(s->{
		stgeMap.put("Pre Implementation"+","+s.getWorkcode(), "");
		stgeMap.put("Mid Implementation"+","+s.getWorkcode(), "");
		stgeMap.put("Post Implementation"+","+s.getWorkcode(), "");
		
});

	list.forEach(s->{
	if(stgeMap.containsKey(s.getStage()+","+s.getWorkcode())) {
		String status = "";
		if(!stgeMap.get(s.getStage()+","+s.getWorkcode()).equals(""))
			status =stgeMap.get(s.getStage()+","+s.getWorkcode())+", "+ s.getStatus();
		else
			status = s.getStatus();
		stgeMap.put(s.getStage()+","+s.getWorkcode(), status);
	}else {
		stgeMap.put(s.getStage()+","+s.getWorkcode(), s.getStatus());
	}
});		
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("Report GT2 - Project Wise Work Code Status with Geotagging Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "Report GT2 - Project Wise Work Code Status with Geotagging Details for Project "+" '"+projname+"' "+"of District "+"'"+distname+"' "+"of State "+"'"+stname+"'";
	String areaAmtValDetail ="";
	
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
    mergedRegion = new CellRangeAddress(5,5,4,6);
    sheet.addMergedRegion(mergedRegion);
    
	
	Row rowhead = sheet.createRow(5);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("Work Code");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Head Name");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Activity Name");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
	
	cell = rowhead.createCell(4);
	cell.setCellValue("Stage");  
	cell.setCellStyle(style);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	
	
	for(int i=5;i<7;i++)
	{
		cell = rowhead.createCell(i);
		cell.setCellStyle(style);
	}
	
	Row rowhead1 = sheet.createRow(6);
	
	for(int i=0;i<4;i++)
	{
		cell = rowhead1.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead1.createCell(4);
	cell.setCellValue("Pre Implementation");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(5);
	cell.setCellValue("Mid Implementation");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(6);
	cell.setCellValue("Post Implementation");  
	cell.setCellStyle(style);
	
	
	Row rowhead2 = sheet.createRow(7);
	
	for(int i=0;i<7;i++)
	{
		cell =rowhead2.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno = 8;
	String wrkcd = "";
	int epa = 0;
	int liv = 0;
	int prod = 0;
	int dpr = 0;
	char hd;
	
	
    for(AssetGeoRefBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	hd = bean.getHeadcode();
    	if(hd == 'E') {
    		if(epa == 0) {
    			mergedRegion = new CellRangeAddress(rowno,rowno,0,6);
    			sheet.addMergedRegion(mergedRegion);
    			cell = row.createCell(0);
    			cell.setCellValue("EPA");  
    			cell.setCellStyle(style);
    			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
    			for(int i=1;i<7;i++){
    				row.createCell(i).setCellStyle(style);
    			}
    			rowno++;
    		}
    		if(!bean.getWorkcode().equals(wrkcd)) {
    			row = sheet.createRow(rowno);
    			row.createCell(0).setCellValue(sno);
    			row.createCell(1).setCellValue(bean.getWorkcode());
    			row.createCell(2).setCellValue(bean.getHeaddes());
    			row.createCell(3).setCellValue(bean.getActdes());
    			int no = 4;
    			for(Map.Entry<String, String> map:stgeMap.entrySet()) {
    				if(bean.getWorkcode().equals(map.getKey().substring(map.getKey().indexOf(',')+1))) {
    					row.createCell(no).setCellValue(map.getValue());
    					no++;
    				}
    			}
    			rowno++;
    			sno++;
    			wrkcd = bean.getWorkcode();
    		}
    		epa = 1;
    	}
    	
    }
    for(AssetGeoRefBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	hd = bean.getHeadcode();
    	if(hd == 'L') {
    		if(liv == 0) {
    			mergedRegion = new CellRangeAddress(rowno,rowno,0,6);
    			sheet.addMergedRegion(mergedRegion);
    			cell = row.createCell(0);
    			cell.setCellValue("Livelihood");  
    			cell.setCellStyle(style);
    			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
    			for(int i=1;i<7;i++){
    				row.createCell(i).setCellStyle(style);
    			}
    			rowno++;
    		}
    		if(!bean.getWorkcode().equals(wrkcd)) {
    			row = sheet.createRow(rowno);
    			row.createCell(0).setCellValue(sno);
    			row.createCell(1).setCellValue(bean.getWorkcode());
    			row.createCell(2).setCellValue(bean.getHeaddes());
    			row.createCell(3).setCellValue(bean.getActdes());
    			int no = 4;
    			for(Map.Entry<String, String> map:stgeMap.entrySet()) {
    				if(bean.getWorkcode().equals(map.getKey().substring(map.getKey().indexOf(',')+1))) {
    					row.createCell(no).setCellValue(map.getValue());
    					no++;
    				}
    			}
    			rowno++;
    			sno++;
    			wrkcd = bean.getWorkcode();
    		}
    		liv = 1;
    	}
    	
    }
    for(AssetGeoRefBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	hd = bean.getHeadcode();
    	if(hd == 'P') {
    		if(prod == 0) {
    			mergedRegion = new CellRangeAddress(rowno,rowno,0,6);
    			sheet.addMergedRegion(mergedRegion);
    			cell = row.createCell(0);
    			cell.setCellValue("Production");  
    			cell.setCellStyle(style);
    			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
    			for(int i=1;i<7;i++){
    				row.createCell(i).setCellStyle(style);
    			}
    			rowno++;
    		}
    		if(!bean.getWorkcode().equals(wrkcd)) {
    			row = sheet.createRow(rowno);
    			row.createCell(0).setCellValue(sno);
    			row.createCell(1).setCellValue(bean.getWorkcode());
    			row.createCell(2).setCellValue(bean.getHeaddes());
    			row.createCell(3).setCellValue(bean.getActdes());
    			int no = 4;
    			for(Map.Entry<String, String> map:stgeMap.entrySet()) {
    				if(bean.getWorkcode().equals(map.getKey().substring(map.getKey().indexOf(',')+1))) {
    					row.createCell(no).setCellValue(map.getValue());
    					no++;
    				}
    			}
    			rowno++;
    			sno++;
    			wrkcd = bean.getWorkcode();
    		}
    		prod = 1;
    	}
    	
    }
    for(AssetGeoRefBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	hd = bean.getHeadcode();
    	if(String.valueOf(hd).matches("[0-9]+")) {
    		if(dpr == 0) {
    			mergedRegion = new CellRangeAddress(rowno,rowno,0,6);
    			sheet.addMergedRegion(mergedRegion);
    			cell = row.createCell(0);
    			cell.setCellValue("NRM");  
    			cell.setCellStyle(style);
    			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
    			for(int i=1;i<7;i++){
    				row.createCell(i).setCellStyle(style);
    			}
    			rowno++;
    		}
    		if(!bean.getWorkcode().equals(wrkcd)) {
    			row = sheet.createRow(rowno);
    			row.createCell(0).setCellValue(sno);
    			row.createCell(1).setCellValue(bean.getWorkcode());
    			row.createCell(2).setCellValue(bean.getHeaddes());
    			row.createCell(3).setCellValue(bean.getActdes());
    			int no = 4;
    			for(Map.Entry<String, String> map:stgeMap.entrySet()) {
    				if(bean.getWorkcode().equals(map.getKey().substring(map.getKey().indexOf(',')+1))) {
    					row.createCell(no).setCellValue(map.getValue());
    					no++;
    				}
    			}
    			rowno++;
    			sno++;
    			wrkcd = bean.getWorkcode();
    		}
    		dpr = 1;
    	}
    	
    }
	

    CommonFunctions.getExcelFooter(sheet, mergedRegion, rowno-5, 6);
    String fileName = "attachment; filename=Report GT2- Project Details.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/assetGeoReport";
}

@RequestMapping(value = "/downloadImageDetailsPDF", method = RequestMethod.GET)
public ModelAndView downloadImageDetailsPDF(HttpServletRequest request, HttpServletResponse response) 
{
	
	String workid = request.getParameter("wrkid");
	String wrkid = workid.substring(workid.indexOf(',')+1);
	String stage = workid.substring(0,workid.indexOf(','));
	List<AssetGeoRefBean> listt = assetGeoService.getGeoImages(wrkid, stage);
	List<String> imgList = new ArrayList<>();
	
	try {
		
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("GeoImageDetail");
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
		
			paragraph3 = new Paragraph("Work Images Recieved from NRSC", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(2);
	        table.setWidths(new int[]{8,11});
	        table.setWidthPercentage(80);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
			
	        BigInteger geoId = BigInteger.ZERO;
	        
			for(AssetGeoRefBean bean : listt) {
				if (!bean.getAssetgeoref().equals(geoId)) {
					CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCell(table, bean.getStname(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCell(table, bean.getDistname(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCell(table, bean.getProjname(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCellHeader(table, "Work Code", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCell(table, bean.getWorkcode(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCellHeader(table, "Head Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCell(table, bean.getHeaddes(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCellHeader(table, "Activity Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCell(table, bean.getActdes(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCellHeader(table, "Obeserver Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCell(table, bean.getObservername(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCellHeader(table, "Collection Sno.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCell(table, bean.getColno().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					CommonFunctions.insertCellHeader(table, "Stage", Element.ALIGN_CENTER, 1, 1, bf8Bold);
					CommonFunctions.insertCell(table, bean.getStage(), Element.ALIGN_CENTER, 1, 1, bf8);
					geoId = bean.getAssetgeoref();
				}
				imgList.add(bean.getImages());
			}
			
				if(listt.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 1, 1, bf8);
			
			
	document.add(table);
	table = new PdfPTable(2);
	table.setWidths(new int[]{2,11});
	table.setWidthPercentage(80);
	table.setSpacingBefore(15f);
	table.setSpacingAfter(0f);
	
	int no = 1;
	for(String img : imgList) {
		PdfPCell text = new PdfPCell();
		text.addElement(new Phrase("Image"+ no, new Font(Font.FontFamily.HELVETICA, 11.0f, Font.BOLD)));
		text.setVerticalAlignment(Element.ALIGN_LEFT);
        text.setHorizontalAlignment(Element.ALIGN_LEFT);
        text.setBorder(Rectangle.BOTTOM);
        text.setBorderColor(BaseColor.WHITE);
		table.addCell(text);
		try {
			table.addCell(Image.getInstance(img));
		} catch (Exception e) {
			table.addCell("Image URL was not found on this server.");
			e.printStackTrace();
		}
		no++;
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
	response.setHeader("Content-Disposition", "inline;filename=Report GT2- ImageDetail.pdf");
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
