package app.controllers.Project;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

import app.bean.Login;
import app.bean.ShgDetailBean;
import app.bean.pfms.PfmsStwiseExpndtreBean;
import app.common.CommonFunctions;
import app.service.DistrictMasterService;
import app.service.ProjectMasterService;
import app.service.ShgDetailService;
import app.service.StateMasterService;

@Controller("ShgDetailsController")
public class ShgDetailsController {
	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	@Autowired(required = true)
	
	ProjectMasterService projectMasterService;
	
	@Autowired
	ShgDetailService shgService;


	@RequestMapping(value = "/getShgAccountReport", method = RequestMethod.GET)
	public ModelAndView getShgAccountReport(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		 List<ShgDetailBean> list= new ArrayList<ShgDetailBean>();
		ModelAndView mav = new ModelAndView();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("reports/AddShgAccountDetails");
				 list = shgService.getShgAccount();
				mav.addObject("getList", list);
				mav.addObject("getListSize", list.size());
			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/distShgAccountReport", method = RequestMethod.GET)
	public ModelAndView distShgAccountReport(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		int stcode = Integer.parseInt(request.getParameter("stcode"));
		String stname = request.getParameter("stname");
		ModelAndView mav = new ModelAndView();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("reports/distShgAccountReport");
				 List<ShgDetailBean> list= new ArrayList<ShgDetailBean>();
				 list = shgService.distShgAccountReport(stcode);
					LinkedHashMap<Integer, String> stateList = new LinkedHashMap<>();
					stateList = stateMasterService.getAllState();
					for(Map.Entry<Integer, String> map : stateList.entrySet()) {
						if(map.getKey()==stcode) {
							stname = map.getValue();
						}
					}
				mav.addObject("AccList", list);
				mav.addObject("AccListSize", list.size());
				mav.addObject("stcode", stcode);
				mav.addObject("stname", stname);
			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/projShgAccountReport", method = RequestMethod.GET)
	public ModelAndView projShgAccountReport(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		
		ModelAndView mav = new ModelAndView();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("reports/projShgAccountReport");
				 List<ShgDetailBean> list= new ArrayList<ShgDetailBean>();
				 list = shgService.projShgAccountReport(dcode);
					
				 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(dcode).entrySet().iterator().next();
				 mav.addObject("statename",stateMasterService.getStateByDistCode(dcode).get(entry.getKey()));	
				 mav.addObject("distName",districtMasterService.getDistrictByDistCode(dcode).get(dcode));	
				mav.addObject("AcProList", list);
				mav.addObject("AcProListSize", list.size());
				mav.addObject("dcode", dcode);
			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getShgDetails", method = RequestMethod.GET)
	public ModelAndView getShgDetails(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("project/AddShgDetails");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	

	@RequestMapping(value = "/getListShgDetails", method = RequestMethod.POST)
	@ResponseBody
	public List<ShgDetailBean> getListShgDetails(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("projId") Integer projId, @ModelAttribute("grp")Integer grp){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();

		List<ShgDetailBean> list = new ArrayList<>();
		
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				list = shgService.getListShgDetails( projId, grp);
				
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/saveDraftShgDetails", method = RequestMethod.POST)
	@ResponseBody
	public String saveDraftShgDetails(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="shg_detail_id") List<String> shg_detail_id,
			@RequestParam(value="account_detail") List<String> account_detail, 
			@RequestParam(value ="ifsc_code") List<String> ifsc_code) { 
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				 res = shgService.saveListShgDetails(shg_detail_id, account_detail, ifsc_code); 
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value = "/deleteSHGDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSHGDetails(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("shg_detail_id") Integer shg_detail_id){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = "fail";
		List<ShgDetailBean> list = new ArrayList<>();
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = shgService.deleteSHGDetails(shg_detail_id);
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value = "/completeShgDetails", method = RequestMethod.POST)
	@ResponseBody
	public String completeShgDetails(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="shg_detail_id1[]") String shg_detail_id1[]){
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String res = "fail";
		try {	
			if (session != null && session.getAttribute("loginID") != null) {
				res = shgService.completeShgDetails(shg_detail_id1);
			}else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value = "/getStatusSHGDetails", method = RequestMethod.GET)
	public ModelAndView getStatusSHG(HttpServletRequest request)
	{
		session = request.getSession(true);
		
		List<ShgDetailBean> list = new ArrayList<ShgDetailBean>();
		ModelAndView mav = new ModelAndView();
		
		try {	
			if (session != null && session.getAttribute("loginID") != null) 
			{
				mav = new ModelAndView("reports/StatusSHGDetails");
				
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				
				list = shgService.getStatusSHG(stcd);
				
				mav.addObject("stcd",stcd);
				mav.addObject("statusSHGList",list);
				mav.addObject("statusSHGListSize",list.size());
				
			}
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value="/getRemainingSHGDetails", method = RequestMethod.POST)
	@ResponseBody
	public List<ShgDetailBean> getRemainingSHGDetails(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="dcode") Integer dcode, @RequestParam(value="type")String type)
	{
		session = request.getSession(true);
		List<ShgDetailBean> list = new ArrayList<ShgDetailBean>();
	    if(session!=null && session.getAttribute("loginID")!=null) {
			
	    	list = shgService.getRemainingSHG(dcode, type);
			
			
		}else {
			list=null;
			
		}
		return list;
	}
	

	@RequestMapping(value = "/downloadShgAPDF", method = RequestMethod.POST)
	public ModelAndView downloadShgAPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		
		List<ShgDetailBean> list = new ArrayList<ShgDetailBean>();
		
		list = shgService.getShgAccount();
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ShgAccountReport");
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
			
				paragraph3 = new Paragraph("ES3- State-wise Account Details", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(5);
		        table.setWidths(new int[]{2, 8, 5, 5, 5});
		        table.setWidthPercentage(70);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(2);
		        
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Project	", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of SHG Account Entered", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
		        
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				int k = 1;
				BigInteger totalproj = new BigInteger("0");
				BigInteger totshg= new BigInteger("0");
				BigInteger totshgentered= new BigInteger("0");
				
				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalproject()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_of_shg()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_of_shg_entered()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
				    	totalproj=totalproj.add(list.get(i).getTotalproject());
				    	totshg=totshg.add(list.get(i).getNo_of_shg());
				    	totshgentered=totshgentered.add(list.get(i).getNo_of_shg_entered());
						
						k++;
					}
					
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totalproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totshg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totshgentered), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 6, 1, bf8);
				
				
		document.add(table);
		table = new PdfPTable(1);
		table.setWidthPercentage(80);
		table.setSpacingBefore(15f);
		table.setSpacingAfter(0f);
		CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
		CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
		document.add(table);
		document.close();
		response.setContentType("application/pdf");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-Disposition", "attachment;filename=ES3- State.pdf");
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

	@RequestMapping(value = "/downloadDistrictShgAPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictShgAPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String stcode=request.getParameter("stcode");
		String stname=request.getParameter("stname");
		List<ShgDetailBean> list = new ArrayList<ShgDetailBean>();
		
		list = shgService.distShgAccountReport(Integer.parseInt(stcode));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ShgAccountDistReport");
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
			
				paragraph3 = new Paragraph("ES3- District-wise Account Details for State  '"+stname+"' ", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(5);
		        table.setWidths(new int[]{2, 8, 5, 5, 5});
		        table.setWidthPercentage(70);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(2);
		        
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of Project	", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of SHG Account Entered", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
		        
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				int k = 1;
				BigInteger totalproj = new BigInteger("0");
				BigInteger totshg= new BigInteger("0");
				BigInteger totshgentered= new BigInteger("0");
				
				
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalproject()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_of_shg()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_of_shg_entered()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
				    	totalproj=totalproj.add(list.get(i).getTotalproject());
				    	totshg=totshg.add(list.get(i).getNo_of_shg());
				    	totshgentered=totshgentered.add(list.get(i).getNo_of_shg_entered());
						
						k++;
					}
					
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totalproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totshg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totshgentered), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
				
				if(list.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 6, 1, bf8);
				
				
		document.add(table);
		table = new PdfPTable(1);
		table.setWidthPercentage(80);
		table.setSpacingBefore(15f);
		table.setSpacingAfter(0f);
		CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
		CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
		document.add(table);
		document.close();
		response.setContentType("application/pdf");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-Disposition", "attachment;filename=ES3- District.pdf");
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


	@RequestMapping(value = "/downloadProjectShgAPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectShgAPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
		String dcode=request.getParameter("dcode");
		List<ShgDetailBean> listt = new ArrayList<ShgDetailBean>();
		
		listt = shgService.projShgAccountReport(Integer.parseInt(dcode));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ShgAccountDistReport");
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
			
				paragraph3 = new Paragraph("ES3- Project-wise Account Details for State  '"+distName+"'  for State '"+statename+"' ", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(4);
		        table.setWidths(new int[]{2, 8, 5, 5});
		        table.setWidthPercentage(70);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
		        table.setHeaderRows(2);
		        
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Total No. of SHG Account Entered", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
		        
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);

				
				int k = 1;
				BigInteger totshg= new BigInteger("0");
				BigInteger totshgentered= new BigInteger("0");
				
				
				if(listt.size()!=0)
					for(int i=0;i<listt.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, listt.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listt.get(i).getNo_of_shg()), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, String.valueOf(listt.get(i).getNo_of_shg_entered()), Element.ALIGN_RIGHT, 1, 1, bf8);
						
						
				    	totshg=totshg.add(listt.get(i).getNo_of_shg());
				    	totshgentered=totshgentered.add(listt.get(i).getNo_of_shg_entered());
						
						k++;
					}
					
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totshg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totshgentered), Element.ALIGN_RIGHT, 1, 1, bf10Bold);			
				
				if(listt.size()==0) 
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 4, 1, bf8);
				
				
		document.add(table);
		table = new PdfPTable(1);
		table.setWidthPercentage(80);
		table.setSpacingBefore(15f);
		table.setSpacingAfter(0f);
		CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
		CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
		document.add(table);
		document.close();
		response.setContentType("application/pdf");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-Disposition", "attachment;filename=ES3- Project.pdf");
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
@RequestMapping(value = "/downloadShgAccountDetailsExcel", method = RequestMethod.POST)
@ResponseBody
public String downloadShgAccountDetailsExcel(HttpServletRequest request, HttpServletResponse response) 
{
	
	
	List<ShgDetailBean> list = new ArrayList<ShgDetailBean>();
	
	list = shgService.getShgAccount();
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("ES3- State-wise SHG Account Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "ES3- State-wise SHG Account Details";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 4, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
	sheet.addMergedRegion(mergedRegion);
	
	
	Row rowhead = sheet.createRow(5);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("State Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total No. of Project");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Total No. of SHG");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(4);
	cell.setCellValue("Total No. of SHG Account Entered");  
	cell.setCellStyle(style);
		
	Row rowhead1 = sheet.createRow(6);
	
	for(int i=0;i<5;i++)
	{
		cell =rowhead1.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno  = 7;
	double totproj=0.0;
	double totshg=0.0;
	double totshgent=0.0;
	
	
    for(ShgDetailBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getSt_name());
    	row.createCell(2).setCellValue(bean.getTotalproject().doubleValue());
    	row.createCell(3).setCellValue(bean.getNo_of_shg().doubleValue());
    	row.createCell(4).setCellValue(bean.getNo_of_shg_entered().doubleValue());
    	
    	
    	totproj = totproj + bean.getTotalproject().doubleValue();
    	totshg = totshg + bean.getNo_of_shg().doubleValue();
    	totshgent = totshgent + bean.getNo_of_shg_entered().doubleValue();

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

	Row row = sheet.createRow(list.size()+7);
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
	cell.setCellValue(totshg);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totshgent);
	cell.setCellStyle(style1);

	
    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 4);
    String fileName = "attachment; filename=ES3- State.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/AddShgAccountDetails";
}


@RequestMapping(value = "/downloadDistrictShgAExcel", method = RequestMethod.POST)
@ResponseBody
public String downloadDistrictShgAExcel(HttpServletRequest request, HttpServletResponse response) 
{
	String stcode=request.getParameter("stcode");
	String stname=request.getParameter("stname");
	List<ShgDetailBean> listt = new ArrayList<ShgDetailBean>();
	
	listt = shgService.distShgAccountReport(Integer.parseInt(stcode));
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("ES3- District-wise SHG Account Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "ES3- District-wise SHG Account Details for State '"+ stname +"' ";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 4, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(listt.size()+7,listt.size()+7,0,1); 
	sheet.addMergedRegion(mergedRegion);
	
	
	Row rowhead = sheet.createRow(5);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("District Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total No. of Project");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Total No. of SHG");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(4);
	cell.setCellValue("Total No. of SHG Account Entered");  
	cell.setCellStyle(style);
		
	Row rowhead1 = sheet.createRow(6);
	
	for(int i=0;i<5;i++)
	{
		cell =rowhead1.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno  = 7;
	double totproj=0.0;
	double totshg=0.0;
	double totshgent=0.0;
	
	
    for(ShgDetailBean bean: listt) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getDist_name());
    	row.createCell(2).setCellValue(bean.getTotalproject().doubleValue());
    	row.createCell(3).setCellValue(bean.getNo_of_shg().doubleValue());
    	row.createCell(4).setCellValue(bean.getNo_of_shg_entered().doubleValue());
    	
    	
    	totproj = totproj + bean.getTotalproject().doubleValue();
    	totshg = totshg + bean.getNo_of_shg().doubleValue();
    	totshgent = totshgent + bean.getNo_of_shg_entered().doubleValue();

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

	Row row = sheet.createRow(listt.size()+7);
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
	cell.setCellValue(totshg);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totshgent);
	cell.setCellStyle(style1);

	
    CommonFunctions.getExcelFooter(sheet, mergedRegion, listt.size(), 4);
    String fileName = "attachment; filename=ES3- District.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/distShgAccountReport";
}

@RequestMapping(value = "/downloadProjectShgAExcel", method = RequestMethod.POST)
@ResponseBody
public String downloadProjectShgAExcel(HttpServletRequest request, HttpServletResponse response) 
{
	String statename=request.getParameter("statename");
	String distName=request.getParameter("distName");
	String dcode=request.getParameter("dcode");
	List<ShgDetailBean> listt = new ArrayList<ShgDetailBean>();
	
	listt = shgService.projShgAccountReport(Integer.parseInt(dcode));
	
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("ES3- Project-wise SHG Account Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "ES3- Project-wise SHG Account Details for District '"+ distName +"'  of State  '"+statename;
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 4, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(listt.size()+7,listt.size()+7,0,1); 
	sheet.addMergedRegion(mergedRegion);
	
	
	Row rowhead = sheet.createRow(5);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("Project Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total No. of SHG");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Total No. of SHG Account Entered");  
	cell.setCellStyle(style);
		
	Row rowhead1 = sheet.createRow(6);
	
	for(int i=0;i<4;i++)
	{
		cell =rowhead1.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno  = 7;
	double totshg=0.0;
	double totshgent=0.0;
	
	
    for(ShgDetailBean bean: listt) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getProj_name());
    	row.createCell(2).setCellValue(bean.getNo_of_shg().doubleValue());
    	row.createCell(3).setCellValue(bean.getNo_of_shg_entered().doubleValue());
    	
    	totshg = totshg + bean.getNo_of_shg().doubleValue();
    	totshgent = totshgent + bean.getNo_of_shg_entered().doubleValue();

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

	Row row = sheet.createRow(listt.size()+7);
	cell = row.createCell(0);
	cell.setCellValue("Grand Total");
	cell.setCellStyle(style1);
	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	cell = row.createCell(1);
	cell.setCellStyle(style1);
	cell = row.createCell(2);
	cell.setCellValue(totshg);
	cell.setCellStyle(style1);
	cell = row.createCell(3);
	cell.setCellValue(totshgent);
	cell.setCellStyle(style1);

	
    CommonFunctions.getExcelFooter(sheet, mergedRegion, listt.size(), 4);
    String fileName = "attachment; filename=ES3- Project.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/projShgAccountReport";
}

@RequestMapping(value = "/downloadExcelStatusSHGDetails", method = RequestMethod.POST)
@ResponseBody
public String downloadExcelStatusSHGDetails(HttpServletRequest request, HttpServletResponse response) 
{
	session = request.getSession(true);
	
	Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
	
	List<ShgDetailBean> list = new ArrayList<ShgDetailBean>();
		
	list = shgService.getStatusSHG(stcd);
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("ES4- Entry Status of SHG Account Details");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "ES4- Entry Status of SHG Account Details";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
	
	mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,6,0,0); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,6,1,1); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,6,2,2); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,3,4); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,5,6); 
	sheet.addMergedRegion(mergedRegion);
	mergedRegion = new CellRangeAddress(5,5,7,8); 
	sheet.addMergedRegion(mergedRegion);
	
	
	Row rowhead = sheet.createRow(5);
	
	Cell cell = rowhead.createCell(0);
	cell.setCellValue("S.No.");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(1);
	cell.setCellValue("District Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(2);
	cell.setCellValue("Total No. of Project");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(3);
	cell.setCellValue("Total No. of SHG");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(4);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(5);
	cell.setCellValue("Total No. of SHG Entered Account Details");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(6);
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(7);
	cell.setCellValue("Total No. of SHG Pending Account Details");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(8);
	cell.setCellStyle(style);
	
	
	Row rowhead1 = sheet.createRow(6);
	
	for(int i=0;i<3;i++)
	{
		cell =rowhead1.createCell(i);
		cell.setCellStyle(style);
	}
	
	cell = rowhead1.createCell(3);
	cell.setCellValue("Newly created");
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(4);
	cell.setCellValue("Existing created");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(5);
	cell.setCellValue("Newly created");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(6);
	cell.setCellValue("Existing created");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(7);
	cell.setCellValue("Newly created");  
	cell.setCellStyle(style);
	
	cell = rowhead1.createCell(8);
	cell.setCellValue("Existing created");  
	cell.setCellStyle(style);
	
	
	Row rowhead2 = sheet.createRow(7);
	
	for(int i=0;i<9;i++)
	{
		cell =rowhead2.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
	int rowno  = 8;
	int totproj = 0;
	int totshg_new = 0;
	int totshg_old = 0;
	int enteredshg_new = 0;
	int enteredshg_old = 0;
	int remainshg_new = 0;
	int remainshg_old = 0;
	
	
    for(ShgDetailBean bean: list) 
    {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getDist_name());
    	row.createCell(2).setCellValue(bean.getTotproj());
    	row.createCell(3).setCellValue(bean.getTotalshg_new());
    	row.createCell(4).setCellValue(bean.getTotalshg_old());
    	row.createCell(5).setCellValue(bean.getEnteredshg_new());
    	row.createCell(6).setCellValue(bean.getEnteredshg_old());
    	row.createCell(7).setCellValue(bean.getRemaining_new());
    	row.createCell(8).setCellValue(bean.getRemaining_old());
    	
    	totproj = totproj + bean.getTotproj();
    	totshg_new = totshg_new + bean.getTotalshg_new();
    	totshg_old = totshg_old + bean.getTotalshg_old();
    	enteredshg_new = enteredshg_new + bean.getEnteredshg_new();
    	enteredshg_old = enteredshg_old + bean.getEnteredshg_old();
    	remainshg_new = remainshg_new + bean.getRemaining_new();
    	remainshg_old = remainshg_old + bean.getRemaining_old();

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
	cell.setCellValue(totshg_new);
	cell.setCellStyle(style1);
	cell = row.createCell(4);
	cell.setCellValue(totshg_old);
	cell.setCellStyle(style1);
	cell = row.createCell(5);
	cell.setCellValue(enteredshg_new);
	cell.setCellStyle(style1);
	cell = row.createCell(6);
	cell.setCellValue(enteredshg_old);
	cell.setCellStyle(style1);
	cell = row.createCell(7);
	cell.setCellValue(remainshg_new);
	cell.setCellStyle(style1);
	cell = row.createCell(8);
	cell.setCellValue(remainshg_old);
	cell.setCellStyle(style1);

	
    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 8);
    String fileName = "attachment; filename=ES4- State.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/StatusSHGDetails";
}


@RequestMapping(value = "/downloadPDFStatusSHGDetails", method = RequestMethod.POST)
public ModelAndView downloadPDFStatusSHGDetails(HttpServletRequest request, HttpServletResponse response) 
{
	session = request.getSession(true);
	
	Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
	
	List<ShgDetailBean> list = new ArrayList<ShgDetailBean>();
		
	list = shgService.getStatusSHG(stcd);
	
	try {
		
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("StatusSHGDetailsReport");
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
		
		paragraph3 = new Paragraph("ES4- Entry Status of SHG Account Details", f3);
			
		paragraph2.setAlignment(Element.ALIGN_CENTER);
	    paragraph3.setAlignment(Element.ALIGN_CENTER);
	    paragraph2.setSpacingAfter(10);
	    paragraph3.setSpacingAfter(10);
	    CommonFunctions.addHeader(document);
	    document.add(paragraph2);
	    document.add(paragraph3);
	    table = new PdfPTable(9);
	    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5});
	    table.setWidthPercentage(60);
	    table.setSpacingBefore(0f);
	    table.setSpacingAfter(0f);
	    table.setHeaderRows(3);
	        
	    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
		CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Total No. of Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Total No. of SHG", Element.ALIGN_CENTER, 2, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Total No. of SHG Entered Account Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Total No. of SHG Pending Account Details", Element.ALIGN_CENTER, 2, 1, bf8Bold);
		
		CommonFunctions.insertCellHeader(table, "Newly created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Existing created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Newly created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Existing created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Newly created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "Existing created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		
		
		CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
		CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);

			
		int k = 1;
		int totproj = 0;
		int totshg_new = 0;
		int totshg_old = 0;
		int enteredshg_new = 0;
		int enteredshg_old = 0;
		int remainshg_new = 0;
		int remainshg_old = 0;

			
		if(list.size()!=0)
			for(int i=0;i<list.size();i++) 
			{
				CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotproj()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalshg_new()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalshg_old()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getEnteredshg_new()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getEnteredshg_old()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getRemaining_new()), Element.ALIGN_RIGHT, 1, 1, bf8);
				CommonFunctions.insertCell(table, String.valueOf(list.get(i).getRemaining_old()), Element.ALIGN_RIGHT, 1, 1, bf8);
					
			    	
			    totproj = totproj + list.get(i).getTotproj();
			    totshg_new = totshg_new + list.get(i).getTotalshg_new();
			    totshg_old = totshg_old + list.get(i).getTotalshg_old();
			    enteredshg_new = enteredshg_new + list.get(i).getEnteredshg_new();
			    enteredshg_old = enteredshg_old + list.get(i).getEnteredshg_old();
			    remainshg_new = remainshg_new + list.get(i).getRemaining_new();
			    remainshg_old = remainshg_old + list.get(i).getRemaining_old();
					
				k++;
			}
			
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totshg_new), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totshg_old), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(enteredshg_new), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(enteredshg_old), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(remainshg_new), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(remainshg_old), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			
			if(list.size()==0) 
				CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 9, 1, bf8);
			
			
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
	response.setHeader("Content-Disposition", "attachment;filename=ES4- State.pdf");
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