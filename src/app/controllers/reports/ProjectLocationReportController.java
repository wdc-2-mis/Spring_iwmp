package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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

import app.bean.ProjectLocationBean;
import app.common.CommonFunctions;

import app.service.ProjectLocationService;
import app.service.StateMasterService;
import app.service.UserService;

@Controller("ProjectLocationReportController")
public class ProjectLocationReportController {
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public ProjectLocationService PLService;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> projectList;
	
	@RequestMapping(value="/projLocDtlRpt", method = RequestMethod.GET)
	public ModelAndView projLocDtlRpt(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		try {
			mav = new ModelAndView("reports/projectLocationDetails");
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
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return mav; 
	}
	
	@RequestMapping(value="/getProjLocDtlRpt", method = RequestMethod.POST)
	public ModelAndView getProjLocDtlRpt(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String projName;
		String projName1 = "";
		String projName2 = "";
		String projName3 = "";
		String projName4 = "";
		String block = "";
		String block1 = "";
		String block2 = "";
		String gp = "";
		String gp1 = "";
		String wc="";
		int i = 0, b=0, g=0, v=0, w=0;
		String data[] = null;
		ArrayList dataList = new ArrayList();
		try {
				mav = new ModelAndView("reports/projectLocationDetails");
				List<ProjectLocationBean> list=new ArrayList<ProjectLocationBean>();
				list=PLService.getProjectLocationList(Integer.parseInt(userState),Integer.parseInt(district), Integer.parseInt(project));
				
				for(ProjectLocationBean bean : list) 
				{
					data = new String[6];
					projName = bean.getProjectname();
					if (projName == null) 
					{
						data[0] = "";
						data[1] = "";
						data[2] = "";// Block
						data[3] = "";// GP
						data[4] = "";// Village
						data[5] = "";//
					} 
					else {
						
						if (!projName1.equalsIgnoreCase(bean.getProjectname())) 
						{
							projName1 = bean.getProjectname();
							i++;
							data[0] = String.valueOf(i);
							data[1] = bean.getProjectname();// project Name
						} 
						else {
							
							data[0] = "";
							data[1] = "";// project Name
						}
						if (!block.equalsIgnoreCase(bean.getBlockname()) || !projName2.equalsIgnoreCase(bean.getProjectname())) 
						{
							projName2 = bean.getProjectname();
							block = bean.getBlockname();
							data[2] = bean.getBlockname(); // block
							b++;
						} 
						else {
							
							data[2] = "";
						}
						if (!gp.equalsIgnoreCase(bean.getGrampanchayatname()) || !block1.equalsIgnoreCase(bean.getBlockname())
								|| !projName3.equalsIgnoreCase(bean.getProjectname())) 
						{
							projName3 = bean.getProjectname();
							block1 = bean.getBlockname();
							gp = bean.getGrampanchayatname();
							data[3] = bean.getGrampanchayatname(); // gp
							g++;
						} 
						else {
							
							data[3] = "";
						}
						if (!wc.equalsIgnoreCase(bean.getWcname())  ||  !gp1.equalsIgnoreCase(bean.getGrampanchayatname()) || !block2.equalsIgnoreCase(bean.getBlockname())
								|| !projName4.equalsIgnoreCase(bean.getProjectname())) 
						{
							wc=bean.getWcname();
							projName4 = bean.getProjectname();
							block2 = bean.getBlockname();
							gp1 = bean.getGrampanchayatname();
							data[5] = bean.getWcname();
							w++;
						} 
						else {
							
							data[5] = "";
						}
						
						
						data[4] = bean.getVillagename();  // village
						//data[5] = bean.getWcname();   //  wcname
						v++;
						dataList.add(data);
					}
				}
				mav.addObject("dataList", dataList);
				mav.addObject("dataListSize", dataList.size());
				mav.addObject("projSize", i);
				mav.addObject("blkSize", b);
				mav.addObject("gpSize", g);
				mav.addObject("wcSize", w);
				mav.addObject("villSize", v);
				
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav; 
	}
	@RequestMapping(value = "/downloadprojectLocationDetailsPDF", method = RequestMethod.POST)
	public ModelAndView downloadprojectLocationDetailsPDF(HttpServletRequest request, HttpServletResponse response)
	{
		//WDC-PMKSY-0001113
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String projName;
		String projName1 = "";
		String projName2 = "";
		String projName3 = "";
		String block1 = "";
		String block = "";
		String gp = "";
//		int i = 0;
		String data[] = null;
		ArrayList dataList = new ArrayList();
		
		List<ProjectLocationBean> list=new ArrayList<ProjectLocationBean>();
		list=PLService.getProjectLocationList(Integer.parseInt(userState),Integer.parseInt(district), Integer.parseInt(project));
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ProjectLocationReport");
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
			
				paragraph3 = new Paragraph("Report P2- State,District and Project-wise Location Details including Mapped Watershed Committee ", f3);
			
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
				table.setHeaderRows(2);
				
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Block Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Gram Panchayat/Village Council ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Watershed Committee", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Village Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				
				int k=1, b=0, g=0, v=0, w=0 ;
				String totNum="";
				String totNum2="";
				String totNum3="";
				String totNum4="";
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						if(!list.get(i).getProjectname().equals(totNum)) {
							CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, list.get(i).getProjectname().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
							k=k+1;
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						
						if(!list.get(i).getBlockname().equals(totNum2)) 
						{
							CommonFunctions.insertCell(table, list.get(i).getBlockname().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
							b++;
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						}
						if(!list.get(i).getGrampanchayatname().equals(totNum3)) 
						{
							CommonFunctions.insertCell(table, list.get(i).getGrampanchayatname().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
							g++;
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						}
						if(!list.get(i).getWcname().equals(totNum4) || !list.get(i).getGrampanchayatname().equals(totNum3)) 
						{
							CommonFunctions.insertCell(table, list.get(i).getWcname().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
							w++;
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						}
						
					//	CommonFunctions.insertCell(table, list.get(i).getWcname(), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getVillagename(), Element.ALIGN_LEFT, 1, 1, bf8);
						v++;
						totNum = list.get(i).getProjectname();
						totNum2 = list.get(i).getBlockname();
						totNum3 = list.get(i).getGrampanchayatname();
						totNum4 = list.get(i).getWcname();
					}
				
					CommonFunctions.insertCell3(table, "Total", Element.ALIGN_CENTER, 3, 1, bf10Bold);
//					CommonFunctions.insertCell3(table, String.valueOf(k-1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//					CommonFunctions.insertCell3(table, String.valueOf(b), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(g), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(w), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
					CommonFunctions.insertCell3(table, String.valueOf(v), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
					if(list.size()==0) 
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
		response.setHeader("Content-Disposition", "attachment;filename=P2-Report.pdf");
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
	
	@RequestMapping(value = "/downloadExcelProjLocDtlRpt", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjLocDtlRpt(HttpServletRequest request, HttpServletResponse response) 
	{
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		
		List<ProjectLocationBean> list=new ArrayList<ProjectLocationBean>();
		
		list=PLService.getProjectLocationList(Integer.parseInt(userState),Integer.parseInt(district), Integer.parseInt(project));
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report P2- State,District and Project-wise Location Details");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report P2- State,District and Project-wise Location Details including Mapped Watershed Committee";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
			
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Block Name");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Gram Panchayat/Village Council");  
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Watershed Committee");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Village Name"); 
			cell.setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i=0;i<6;i++)
			{
				cell =rowhead1.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
	        
	        int sno = 1, b=0, g=0, w=0, v=0;
	        int rowno  = 7;
	        String projName = "";
	        String blkName = "";
	        String gramPytName = "";
	        String wcName = "";

	        
	        for(ProjectLocationBean bean: list) {
	        	Row row = sheet.createRow(rowno);

	        	if(!projName.equals(bean.getProjectname())) 
	        	{
	        		row.createCell(0).setCellValue(sno);
	        		row.createCell(1).setCellValue(bean.getProjectname());
	        		sno++;
		        }
	        	if(!blkName.equals(bean.getBlockname())) 
	        	{
	        		row.createCell(2).setCellValue(bean.getBlockname());
	        		b++;
		        }
	        	if(!gramPytName.equals(bean.getGrampanchayatname())) 
	        	{
	        		row.createCell(3).setCellValue(bean.getGrampanchayatname());
	        		g++;
		        }
	        	if(!gramPytName.equals(bean.getGrampanchayatname()) || !wcName.equals(bean.getWcname())) 
	        	{
	        		row.createCell(4).setCellValue(bean.getWcname());
	        		w++;
		        }
	        	row.createCell(5).setCellValue(bean.getVillagename());
	        	v++;
	        	
	        	projName = bean.getProjectname();
	        	blkName = bean.getBlockname();
	        	gramPytName = bean.getGrampanchayatname();
	        	wcName=bean.getWcname();
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
//	    	font1.setColor(IndexedColors.WHITE.getIndex());
	    	style1.setFont(font1);
	    	
	    	Row row = sheet.createRow(list.size()+7);
	    	cell = row.createCell(0);
//	    	cell.setCellValue("Total");
	    	cell.setCellStyle(style1);
	    	CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
	    	cell = row.createCell(1);
//	    	cell.setCellValue((sno-1));
	    	cell.setCellStyle(style1);
	    	cell = row.createCell(2);
	    	cell.setCellValue("Total");
	    	cell.setCellStyle(style1);
	    	cell = row.createCell(3);
	    	cell.setCellValue(g);
	    	cell.setCellStyle(style1);
	    	cell = row.createCell(4);
	    	cell.setCellValue(w);
	    	cell.setCellStyle(style1);
	    	cell = row.createCell(5);
	    	cell.setCellValue(v);
	    	cell.setCellStyle(style1);
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 5);
	        String fileName = "attachment; filename=Report P2.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/projectLocationDetails";
		
	}
	
}
