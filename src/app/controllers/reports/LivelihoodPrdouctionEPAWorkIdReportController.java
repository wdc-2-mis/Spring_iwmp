package app.controllers.reports;

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

import app.PfmsTreasureBean;
import app.bean.ProjectLocationBean;
import app.bean.reports.LivelihoodPrdouctionEPAWorkIdBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.common.CommonFunctions;
import app.service.DistrictMasterService;
import app.service.ProjectLocationService;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.master.LivelihoodPrdouctionEPAWorkIdService;

@Controller("LivelihoodPrdouctionEPAWorkIdReport")
public class LivelihoodPrdouctionEPAWorkIdReportController {
	
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public LivelihoodPrdouctionEPAWorkIdService ser;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	private Map<String, String> workHead;
	
	@RequestMapping(value="/livelihoodPrdouctionEPAWorkIdReport", method = RequestMethod.GET)
	public ModelAndView livelihoodPrdouctionEPAWorkIdReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		try {
			mav = new ModelAndView("reports/livelihoodPrdouctionEPAWorkIdReport");
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
			
			workHead=new LinkedHashMap<String, String>();
			workHead.put("epa", "Entry Point Activity (EPAs)");
			workHead.put("livelihood", "Livelihood Activities");
			workHead.put("production", "Production System");
			
			mav.addObject("workHeadList", workHead);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value="/livelihoodPrdouctionEPAWorkIdReport", method = RequestMethod.POST)
	public ModelAndView getLivelihoodPrdouctionEPAWorkIdReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String stname= request.getParameter("stname");
		String distname= request.getParameter("distname");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String scheme= request.getParameter("scheme");
		LinkedHashMap<Integer, String> stmap = stateMasterService.getAllState();
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(Integer.parseInt(userState));
		String projName;
		String projName1 = "";
		String projName2 = "";
		String projName3 = "";
		String block1 = "";
		String block = "";
		String gp = "";
		int i = 1;
		String data[] = null;
		ArrayList dataList = new ArrayList();
		try {
				mav = new ModelAndView("reports/livelihoodPrdouctionEPAWorkIdReport");
				List<LivelihoodPrdouctionEPAWorkIdBean> list=new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
				list=ser.getLivelihoodPrdouctionEPAWorkIdList(Integer.parseInt(userState),Integer.parseInt(district), 
						Integer.parseInt(project), scheme);
				
				for(Map.Entry<Integer,String> smap : stmap.entrySet()) {
					if(smap.getKey()==Integer.parseInt(userState))
						stname = smap.getValue();
					
				}
				
				for(Map.Entry<Integer,String> distmap : distMap.entrySet()) {
					if(distmap.getKey()==Integer.parseInt(district))
						distname = distmap.getValue();
					
				}
				for(LivelihoodPrdouctionEPAWorkIdBean bean : list) 
				{
					data = new String[8];
					projName = bean.getProjectname();
					if (projName == null) 
					{
						data[0] = "";
						data[1] = "";
						data[2] = "";
						data[3] = "";
						data[4] = "";
						data[5] = "";
						data[6] = "";
						data[7] = "";
					} 
					else {
						
						if (!projName1.equalsIgnoreCase(bean.getProjectname())) 
						{
							projName1 = bean.getProjectname();
							data[1] = bean.getProjectname();// project Name
						} 
						else {
							
							data[1] = "";// project Name
						}
						data[0] = String.valueOf(i);
						data[2] = bean.getAssetid().toString();
						data[3] = bean.getActivity();
						data[4] = bean.getVillagename();  // village
						data[5] = bean.getBlockname();
						data[6] = bean.getGrampanchayatname();
						data[7] = bean.getNearby();
						i++;
						dataList.add(data);
					}
				}
				mav.addObject("dataList", dataList);
				mav.addObject("dataListSize", dataList.size());
				
				stateList=stateMasterService.getAllState();
				mav.addObject("stateList", stateList);
				mav.addObject("state", userState);
				mav.addObject("scheme", scheme);
				mav.addObject("stmap",stmap);
				mav.addObject("distmap",distMap);
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
				mav.addObject("state", userState);
				mav.addObject("district", district);
				mav.addObject("project", project);
				mav.addObject("scheme", scheme);
				
				workHead=new LinkedHashMap<String, String>();
				workHead.put("epa", "Entry Point Activity (EPAs)");
				workHead.put("livelihood", "Livelihood Activities");
				workHead.put("production", "Production System");
				
				mav.addObject("workHeadList", workHead);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav; 
	}

	@RequestMapping(value = "/downloadExcelLivelihoodProdEPAWorkIdReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelLivelihoodProdEPAWorkIdReport(HttpServletRequest request, HttpServletResponse response) 
	{
		String userState= request.getParameter("state");
		String stname= request.getParameter("stname");
		String distname= request.getParameter("distname");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String scheme= request.getParameter("scheme");
		String projName;
		String projName1 = "";
		LinkedHashMap<Integer, String> stmap = stateMasterService.getAllState();
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(Integer.parseInt(userState));
		
		List<LivelihoodPrdouctionEPAWorkIdBean> list=new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
		
		list=ser.getLivelihoodPrdouctionEPAWorkIdList(Integer.parseInt(userState),Integer.parseInt(district), Integer.parseInt(project), scheme);
		
		for(Map.Entry<Integer,String> smap : stmap.entrySet()) {
			if(smap.getKey()==Integer.parseInt(userState))
				stname = smap.getValue();
			
		}
		for(Map.Entry<Integer,String> distmap : distMap.entrySet()) {
			if(distmap.getKey()==Integer.parseInt(district))
				distname = distmap.getValue();
			
		}
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("OC4- State and Project wise Work-Id details of Livelihood Activities / Production System Activities and Entry Point Activities(EPAs)");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "OC4- State and Project wise Work-Id details of Livelihood Activities / Production System Activities and Entry Point Activities(EPAs)";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,7); 
			sheet.addMergedRegion(mergedRegion);
			 
			if(distname==null) {
			Row rowhead4 = sheet.createRow(5); 
			
			Cell cell = rowhead4.createCell(0);
			
			cell.setCellValue("State: "+stname +" District: "+" All District "+ " Head: "+ scheme);
			cell.setCellStyle(style);
			
			}
			else {
				Row rowhead4 = sheet.createRow(5); 
				
				Cell cell = rowhead4.createCell(0);
				
				cell.setCellValue("State: "+stname +" District: "+distname+ " Head: "+ scheme);
				cell.setCellStyle(style);
			}
			Row rowhead = sheet.createRow(6); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Work-Id");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Activities");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Block Name");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Gram Panchayat");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Village Name");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Land Identification");
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			for(int i=0;i<8;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        String pName = "";
	        
	        for(LivelihoodPrdouctionEPAWorkIdBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	if(!pName.equals(bean.getProjectname())) {
	        	row.createCell(1).setCellValue(bean.getProjectname());
	        	pName = bean.getProjectname();
	        	}
	        	row.createCell(2).setCellValue(bean.getAssetid().toString());
				row.createCell(3).setCellValue(bean.getActivity());  
				row.createCell(4).setCellValue(bean.getBlockname()); 
				row.createCell(5).setCellValue(bean.getGrampanchayatname());  
				row.createCell(6).setCellValue(bean.getVillagename());
				row.createCell(7).setCellValue(bean.getNearby());
	        	
	        	sno++;
	        	rowno++;
	        }
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 7);
	        String fileName = "attachment; filename=Report OC4.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/livelihoodPrdouctionEPAWorkIdReport";
		
	}
	
	@RequestMapping(value = "/downloadlivelihoodPrdouctionEPAWorkIdReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadlivelihoodPrdouctionEPAWorkIdReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		
		String userState= request.getParameter("state");
		String stname= request.getParameter("stname");
		String distname= request.getParameter("distname");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String scheme= request.getParameter("scheme");
		String projName;
		String projName1 = "";
		List<LivelihoodPrdouctionEPAWorkIdBean> list=new ArrayList<LivelihoodPrdouctionEPAWorkIdBean>();
		LinkedHashMap<Integer, String> stmap = stateMasterService.getAllState();
		LinkedHashMap<Integer,String> distMap = districtMasterService.getDistrictByStateCode(Integer.parseInt(userState));
		list=ser.getLivelihoodPrdouctionEPAWorkIdList(Integer.parseInt(userState),Integer.parseInt(district), 
				Integer.parseInt(project), scheme);
		for(Map.Entry<Integer,String> smap : stmap.entrySet()) {
			if(smap.getKey()==Integer.parseInt(userState))
				stname = smap.getValue();
			
		}
		for(Map.Entry<Integer,String> distmap : distMap.entrySet()) {
			if(distmap.getKey()==Integer.parseInt(district))
				distname = distmap.getValue();
			
		}
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("EpaLivProdReport");
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
			
				paragraph3 = new Paragraph(" Report OC4- State and Project wise Work-Id details of Livelihood Activities / Production System Activities and Entry Point Activities(EPAs)", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			
				table = new PdfPTable(8);
				table.setWidths(new int[] { 2, 5, 3, 5, 5, 5, 5, 5 });
				table.setWidthPercentage(70);
			
				table.setWidthPercentage(100);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
				table.setHeaderRows(3);
				
				if(distname==null) {
					CommonFunctions.insertCellHeader(table, "State: "+stname +"  District: "+" All District"+" Head: "+scheme, Element.ALIGN_LEFT, 8, 1, bf8Bold);
				}
				else {
				CommonFunctions.insertCellHeader(table, "State: "+stname +"  District: "+distname+" Head: "+scheme, Element.ALIGN_LEFT, 8, 1, bf8Bold);
				}
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Work-Id ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Activities ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Block Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Gram Panchayat", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Land Identification", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						if(!list.get(i).getProjectname().equals(projName1)) {
							CommonFunctions.insertCell(table, list.get(i).getProjectname().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
							}
							else {
								CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
								}
						CommonFunctions.insertCell(table, list.get(i).getAssetid().toString(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getActivity(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getBlockname(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getGrampanchayatname(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getVillagename(), Element.ALIGN_RIGHT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getNearby(), Element.ALIGN_RIGHT, 1, 1, bf8);

						k=k+1;
						projName1 = list.get(i).getProjectname();
						
					}
					if(list.size()==0) 
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
					
						
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
		response.setHeader("Content-Disposition", "attachment;filename=OC4-Report.pdf");
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
