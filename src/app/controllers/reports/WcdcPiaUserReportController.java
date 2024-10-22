package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import app.bean.BaseLineOutcomeBean;
import app.bean.RPCLivelihoodBean;
import app.bean.WcdcPiaUserBean;
import app.bean.reports.PhysicalActionAchievementBean;
import app.common.CommonFunctions;
import app.common.CommonFunctions;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.reports.WcdcPiaUserService;

@Controller("WcdcPiaUserReportController")
public class WcdcPiaUserReportController {
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public WcdcPiaUserService WcdcPiaUserService;
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	
	
	@RequestMapping(value="/wcdcPiaUser", method = RequestMethod.GET)
	public ModelAndView wcdcPiaUser(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String userType= request.getParameter("userType");
		String acHoldName=request.getParameter("acHoldName");
		
			mav = new ModelAndView("reports/wcdcPiaUserReport");
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			if(userState!=null && !userState.equalsIgnoreCase(""))
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);
			mav.addObject("district", district);
				
			mav.addObject("userType", userType);
			mav.addObject("acHoldName", acHoldName);
		
		return mav; 
	}
	
	@RequestMapping(value="/wcdcPiaUserList", method = RequestMethod.POST)
	public ModelAndView wcdcPiaUserList(HttpServletRequest request, HttpServletResponse response)
	{	
		ModelAndView mav = new ModelAndView();
		String userState=request.getParameter("state");
		String district=request.getParameter("district");
		String userType=request.getParameter("userType");
		String acHoldName=request.getParameter("acHoldName");
		String data[] = null;
		String data11[] = null;
		String stateName = "";
		String stateName1 = "";
		String distName = "";
		String dataArrNetTotalStr = "";
		String dataArrNetTotalStr1 = "";
		String distName1 = "";
		String userId = "";
		int i = 1;
		int k = 0;
		int p = 1;
		
			ArrayList dataList = new ArrayList();
			mav = new ModelAndView("reports/wcdcPiaUserReport");
			List<WcdcPiaUserBean> list=new ArrayList<WcdcPiaUserBean>();
			list=WcdcPiaUserService.getWcdcPiaUserList(userState, district,  userType);
			
			if (userType != null && userType.equalsIgnoreCase("WCDC")) 
			{
				int ii = 0;
				for(WcdcPiaUserBean bean : list) 
				{
					
					if (!userId.equalsIgnoreCase(bean.getUser_id())) 
					{
						if (ii != 0)
							dataList.add(data);
						
						k++;
						dataArrNetTotalStr = String.valueOf(k);
						data = new String[3];
						if (!stateName.equalsIgnoreCase(bean.getState_name())) 
						{
							stateName = bean.getState_name();
							data[0] = String.valueOf(i);
							data[1] = bean.getState_name();
							i++;
						} 
						else {
							p++;
							data[0] = "";
						}
						data[2] = bean.getDist_name();
					}
					userId = bean.getUser_id();
					ii++;
					if (!stateName1.equalsIgnoreCase(bean.getState_name())) 
					{
						if (!stateName1.equalsIgnoreCase("")) 
						{
							data11 = new String[2];
							data11[0] = "Total";
							data11[1] = String.valueOf(p);
							dataList.add(data11);
							data11 = new String[2];
						}
						stateName1 = bean.getState_name();
						p = 1;
					}
				}
				if (ii > 1) 
				{
					dataList.add(data);
					data11 = new String[2];
					data11[0] = "Total";
					data11[1] = String.valueOf(p);
					dataList.add(data11);
				}
				mav.addObject("dataArrNetTotalStr", dataArrNetTotalStr);
				mav.addObject("dataList", dataList);
				mav.addObject("dataListSize", dataList.size());
				mav.addObject("hide", "");
			} 
			else {
				if (acHoldName == "on") 
				{
					mav.addObject("hide", "");
				} 
				else {
					mav.addObject("hide", "none");
				}
				int pp = 0;
				int ii = 0;
				int jj = 0;
				for(WcdcPiaUserBean bean : list) 
				{
					if (!userId.equalsIgnoreCase(bean.getUser_id())) 
					{
						if (ii != 0)
							dataList.add(data);
						k++;
						dataArrNetTotalStr = String.valueOf(k);
						data = new String[5];
						if (!stateName.equalsIgnoreCase(bean.getState_name())) 
						{
							stateName = bean.getState_name();
							data[0] = String.valueOf(i);
							data[1] = bean.getState_name();
							i++;
						} 
						else {
							data[1] = "";
						}
						if (!distName.equalsIgnoreCase(bean.getDist_name())) 
						{
							distName = bean.getDist_name();
							data[2] = bean.getDist_name();
						} 
						else {
							p++;
							data[2] = "";
						}
						/*
						 * if (!blockName.equalsIgnoreCase(rs.getString(3))) {
						 * 
						 * blockName = rs.getString(3); data[3] = rs.getString(3); } else {
						 * 
						 * data[3] = ""; }
						 */
						data[3] = bean.getProj_name();
						data[4] = bean.getUser_name();
						pp++;
					} 
					else {
						data[3] = data[3] +", "+ bean.getProj_name();
						pp++;
					}	
					userId = bean.getUser_id();
					jj++;
					ii++;
					dataArrNetTotalStr1 = String.valueOf(jj);
					if (!distName1.equalsIgnoreCase(bean.getDist_name())) 
					{
						if (!distName1.equalsIgnoreCase("")) 
						{
							data11 = new String[4];
							data11[0] = "Total Users";
							data11[1] = String.valueOf(p);
							data11[2] = String.valueOf(pp);
							data11[3] = "Total Projects";
							dataList.add(data11);
							data11 = new String[4];
						}
						distName1 = bean.getDist_name();
						p = 1;
						pp = 0;
					}
				}
				if (ii > 1) 
				{
					dataList.add(data);
					pp++;
					data11 = new String[4];
					data11[0] = "Total Users";
					data11[1] = String.valueOf(p);
					data11[2] = String.valueOf(pp);
					data11[3] = "Total Projects";
					dataList.add(data11);
				}
				mav.addObject("dataArrNetTotalStr", dataArrNetTotalStr);
				mav.addObject("dataArrNetTotalStr1", dataArrNetTotalStr1);
				mav.addObject("dataList", dataList);
				mav.addObject("dataListSize", dataList.size());
			}
			mav.addObject("WcdcPiaUserList", list);
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equalsIgnoreCase(""))
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);
			mav.addObject("district", district);
			mav.addObject("userType", userType);
			mav.addObject("acHoldName", acHoldName);
		
		return mav; 
	}
	@RequestMapping(value = "/downloadwcdcPiaUserListPDF", method = RequestMethod.POST)
	public ModelAndView downloadwcdcPiaUserListPDF(HttpServletRequest request, HttpServletResponse response) 	{
		//WDC-PMKSY-0001113	
		String userState=request.getParameter("state");
		String district=request.getParameter("district");
		String userType=request.getParameter("userType");
		String acHoldName=request.getParameter("acHoldName");
		 
		List<WcdcPiaUserBean> list=new ArrayList<WcdcPiaUserBean>();
		list=WcdcPiaUserService.getWcdcPiaUserList(userState, district,  userType);
		
		try {	
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("wcdcPiaUserList");	
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
			paragraph3 = new Paragraph("Report OT2- List of Account Created At District Level for  "+ userType+ " User", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);	
			document.add(paragraph3);	
			table = new PdfPTable(3);
			table.setWidths(new int[] { 2, 5, 5});
			
			table.setWidthPercentage(70);	
			table.setWidthPercentage(100);	
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(2);
			
				CommonFunctions.insertCellHeader(table, "S.No", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				 	 	 	 	 	 	 	 	 	 	
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);	
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				int k=1;
				int total=0;
				int totDist=0;
				String stateName="" ;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
					{
						if( i!=0&&!stateName.equals(list.get(i).getState_name())) {
							CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
							CommonFunctions.insertCell3(table, String.valueOf(totDist), Element.ALIGN_RIGHT, 2, 1, bf10Bold);
							totDist=0;
							
						}
						if (!stateName.equalsIgnoreCase(list.get(i).getState_name())) 
						{
								CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
								CommonFunctions.insertCell(table, list.get(i).getState_name(), Element.ALIGN_LEFT, 1, 1, bf8);
								k=k+1;
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
								CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_RIGHT, 1, 1, bf8);
								stateName=list.get(i).getState_name();
								totDist=totDist+1 ;
					    		total=total+1 ;
								
					}
				CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totDist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, " Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(total), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(list.size()==0) {				
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 3, 1, bf8);		
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
			response.setHeader("Content-Disposition", "attachment;filename=OT2-Report.pdf");		
			response.setHeader("Pragma", "public");		
			response.setContentLength(baos.size());		
			OutputStream os = response.getOutputStream();		
			baos.writeTo(os);		
			os.flush();		
			os.close();		
			} 	catch (Exception ex) 	
		{
				ex.printStackTrace();	
				
		} 		
		return null;	
				
	}	
	
	@RequestMapping(value = "/downloadPIAUserListPDF", method = RequestMethod.POST)
	public ModelAndView downloadPIAUserListPDF(HttpServletRequest request, HttpServletResponse response) 	{
		//WDC-PMKSY-0001113	
		String userState=request.getParameter("state");
		String district=request.getParameter("district");
		String userType=request.getParameter("userType");
		 
		List<WcdcPiaUserBean> list=new ArrayList<WcdcPiaUserBean>();
		list=WcdcPiaUserService.getWcdcPiaUserList(userState, district,  userType);
		
		try {	
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("PiaUserList");	
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
			paragraph3 = new Paragraph("Report OT2- List of Account Created At Project Level for  "+ userType+ " User", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);	
			document.add(paragraph3);	
			table = new PdfPTable(5);
			table.setWidths(new int[] { 2, 5, 5, 5, 5});
			
			table.setWidthPercentage(70);	
			table.setWidthPercentage(100);	
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(2);
			
				CommonFunctions.insertCellHeader(table, "S.No", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name For which permission given ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Account Holder Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);	
				CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				 
				 int k=1;
				 int totUser = 0;
			     int totProj = 0;
			     String distName = "";
			     String userName = "";
			     String projname="";
				 int totDist=0;
				int tottotal=1;
				String stateName="" ;
				if(list.size()!=0)
					for(int i=0;i<list.size();i++) 
			        
					{
						if( i!=0&&!distName.equals(list.get(i).getDist_name())) {
							CommonFunctions.insertCell3(table, "Total Projects: "+totProj, Element.ALIGN_RIGHT, 4, 1, bf10Bold);
							CommonFunctions.insertCell3(table, "Total Users: "+totUser, Element.ALIGN_RIGHT, 1, 1, bf10Bold);
		        			totProj=0;
		                	totUser=0;
							
						}
						if (!stateName.equalsIgnoreCase(list.get(i).getState_name())) 
						{
								CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
								CommonFunctions.insertCell(table, list.get(i).getState_name(), Element.ALIGN_LEFT, 1, 1, bf8);
								k=k+1;
						}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!distName.equalsIgnoreCase(list.get(i).getDist_name())) 
						{
								CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
								}
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
							
						}
						if (!projname.equalsIgnoreCase(list.get(i).getProj_name())) 
						{
								CommonFunctions.insertCell(table, list.get(i).getProj_name()+",", Element.ALIGN_LEFT, 1, 1, bf8);
							
								
								}
							
						
						if (!userName.equalsIgnoreCase(list.get(i).getUser_id())) 
						{
						CommonFunctions.insertCell(table, list.get(i).getUser_name(), Element.ALIGN_LEFT, 1, 1, bf8);
						tottotal=tottotal+1 ;
						totUser=totUser+1;
						userName=list.get(i).getUser_id();
						}
					
						else {
							CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						}
								stateName=list.get(i).getState_name();
								distName=list.get(i).getDist_name();
								userName=list.get(i).getUser_id();
								projname=list.get(i).getProj_name();
								totDist=totDist+1 ;
					    		totProj=totProj+1;
								
					}
				
				CommonFunctions.insertCell3(table, "Total Projects: "+totProj, Element.ALIGN_RIGHT, 4, 1, bf10Bold);
				CommonFunctions.insertCell3(table, "Total Users: "+totUser, Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				CommonFunctions.insertCell3(table, " Grand Total: "+totDist, Element.ALIGN_RIGHT, 4, 1, bf10Bold);
				CommonFunctions.insertCell3(table, " Grand Total: "+(tottotal-1), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				if(list.size()==0) {				
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 3, 1, bf8);		
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
			response.setHeader("Content-Disposition", "attachment;filename=OT2-Report.pdf");		
			response.setHeader("Pragma", "public");		
			response.setContentLength(baos.size());		
			OutputStream os = response.getOutputStream();		
			baos.writeTo(os);		
			os.flush();		
			os.close();		
			} 	catch (Exception ex) 	
		{
				ex.printStackTrace();	
				
		} 		
		return null;	
				
	}	
	
	@RequestMapping(value = "/downloadExcelWCDCUserList", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelWCDCUserList(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String userState=request.getParameter("state");
		String district=request.getParameter("district");
		String userType=request.getParameter("userType");
		String acHoldName=request.getParameter("acHoldName");
		
		List<WcdcPiaUserBean> list=new ArrayList<WcdcPiaUserBean>();
		
		list=WcdcPiaUserService.getWcdcPiaUserList(userState, district,  userType);
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report OT2- List of Account Created At District Level");   
		
		
		CellStyle style = CommonFunctions.getStyle(workbook);
        
		String rptName = "Report OT2- List of Account Created At District Level for "+userType+" User";
		String areaAmtValDetail = "";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 2, areaAmtValDetail, workbook);
		
        //creating the 5th row using the createRow() method  
		Row rowhead = sheet.createRow(5); 
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("District Name");  
		cell.setCellStyle(style);
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i = 0; i<3; i++) {	
		Cell cell1 = rowhead1.createCell(i);
		cell1.setCellValue(i+1);
		cell1.setCellStyle(style);
		}
		
		 int sno = 1;
		 int count = 0;
	     int rowno  = 7;
	     int totDist = 0;
	     int total = 0;
	     String stName = "";
        
        for(WcdcPiaUserBean bean: list) {
        	Row row = sheet.createRow(rowno);
        	if( !stName.equals(bean.getState_name())) {
//        		Row row = sheet.createRow(rowno);
        		if(sno!=1 ) {
        			mergedRegion = new CellRangeAddress(rowno,rowno,0,2);
        		    sheet.addMergedRegion(mergedRegion);
        			cell = row.createCell(0);
        			cell.setCellValue("Total :"+totDist);
        			cell.setCellStyle(style);
        			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
        			cell = row.createCell(1);
        			cell.setCellStyle(style);
        			cell =row.createCell(2);
        			cell.setCellStyle(style);
        			rowno++;
        			row = sheet.createRow(rowno);
        		}
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getState_name());
	        	stName = bean.getState_name();
	        	totDist = 0;
	        	sno++;
	        	
        	}
        	row.createCell(2).setCellValue(bean.getDist_name());
        	
        	totDist++ ;
    		total++ ;
    		rowno++;
    		count++;
    		if(list.size()==count) {
    			row = sheet.createRow(rowno);
    			mergedRegion = new CellRangeAddress(rowno,rowno,0,2);
    		    sheet.addMergedRegion(mergedRegion);
    			cell = row.createCell(0);
    			cell.setCellValue("Total :"+totDist);
    			cell.setCellStyle(style);
    			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
    			cell = row.createCell(1);
    			cell.setCellStyle(style);
    			cell =row.createCell(2);
    			cell.setCellStyle(style);
    			rowno++;
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
//		font1.setColor(IndexedColors.WHITE.getIndex());
		style1.setFont(font1);
        
      mergedRegion = new CellRangeAddress(rowno,rowno,0,2); 
      sheet.addMergedRegion(mergedRegion);
		
        Row row = sheet.createRow(rowno);
        cell = row.createCell(0);
        cell.setCellValue("Grand Total : "+total);
        cell.setCellStyle(style1);
        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell = row.createCell(2);
        cell.setCellStyle(style1);
     
        
        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowno, 2);
        String fileName = "attachment; filename=Report OT2.xlsx";
        
        CommonFunctions.downloadExcel(response, workbook, fileName);
		
	
	
	return "reports/wcdcPiaUserReport";
	
}
	
	@RequestMapping(value = "/downloadExcelPIAUserList", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelPIAUserList(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String userState=request.getParameter("state");
		String district=request.getParameter("district");
		String userType=request.getParameter("userType");
		String acHoldName=request.getParameter("acHoldName");
		
		List<WcdcPiaUserBean> list=new ArrayList<WcdcPiaUserBean>();
		
		list=WcdcPiaUserService.getWcdcPiaUserList(userState, district,  userType);
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report OT2- List of Account Created At District Level");   
		
		
		CellStyle style = CommonFunctions.getStyle(workbook);
        
		String rptName = "Report OT2- List of Account Created At Project Level for "+userType+" User";
		String areaAmtValDetail = "";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 4, areaAmtValDetail, workbook);
		
        //creating the 5th row using the createRow() method  
		Row rowhead = sheet.createRow(5); 
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("District Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Project Name For which permission given");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("Account Holder Name");  
		cell.setCellStyle(style);
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i = 0; i<5; i++) {	
		Cell cell1 = rowhead1.createCell(i);
		cell1.setCellValue(i+1);
		cell1.setCellStyle(style);
		}
		
		 int sno = 1;
	     int rowno  = 7;
	     int count = 0;
	     int totUser = 0;
	     int totProj = 0;
	     int gdTotalProj = 0;
	     int gdTotalUser = 0;
	     String stName = "";
	     String distName = "";
	     String userName = "";
	     String updateProjName = "";
	     String userId = "";
        
	    Row row = sheet.createRow(rowno);
        for(WcdcPiaUserBean bean: list) {
        	
        	if( !stName.equals(bean.getState_name())) {
        	
        		if(count!=0 ) {
        			row.createCell(3).setCellValue(updateProjName);
            		row.createCell(4).setCellValue(userName);
            		totUser++;
            		rowno++;
        			Row row1 = sheet.createRow(rowno);
        			mergedRegion = new CellRangeAddress(rowno,rowno,0,3);
        		    sheet.addMergedRegion(mergedRegion);
        			cell = row1.createCell(0);
        			cell.setCellValue("Total Project :"+totProj);
        			cell.setCellStyle(style);
        			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
        			cell = row1.createCell(1);
        			cell.setCellStyle(style);
        			cell =row1.createCell(2);
        			cell.setCellStyle(style);
        			cell =row1.createCell(3);
        			cell.setCellStyle(style);
        			cell =row1.createCell(4);
        			cell.setCellValue("Total User :"+totUser);
        			cell.setCellStyle(style);
        			rowno++;
        			gdTotalProj =gdTotalProj + totProj;
        			gdTotalUser = gdTotalUser + totUser;
        			totProj=0;
                	totUser=0;
        			row = sheet.createRow(rowno);
        		}
        		
        		row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getState_name());
	        	sno++;
        	}
        	
        	if( !distName.equals(bean.getDist_name())) {
        				
        		if(count!=0 && stName.equals(bean.getState_name())) {
        			row.createCell(3).setCellValue(updateProjName);
            		row.createCell(4).setCellValue(userName);
            		totUser++;
            		rowno++;
        			Row row1 = sheet.createRow(rowno);
        			mergedRegion = new CellRangeAddress(rowno,rowno,0,3);
        		    sheet.addMergedRegion(mergedRegion);
        			cell = row1.createCell(0);
        			cell.setCellValue("Total Project :"+totProj);
        			cell.setCellStyle(style);
        			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
        			cell = row1.createCell(1);
        			cell.setCellStyle(style);
        			cell =row1.createCell(2);
        			cell.setCellStyle(style);
        			cell =row1.createCell(3);
        			cell.setCellStyle(style);
        			cell =row1.createCell(4);
        			cell.setCellValue("Total User :"+totUser);
        			cell.setCellStyle(style);
        			rowno++;
        			gdTotalProj =gdTotalProj + totProj;
        			gdTotalUser = gdTotalUser + totUser;
        			totProj=0;
                	totUser=0;
        			row = sheet.createRow(rowno);
        		}
        		row.createCell(2).setCellValue(bean.getDist_name());
        		updateProjName = bean.getProj_name();
        		
        		userName = bean.getUser_name();
        		distName = bean.getDist_name();
        		stName = bean.getState_name();
        		userId = bean.getUser_id();
        		totProj++;
        		
        	}else {
        		if(userId.equals(bean.getUser_id())) {
        			updateProjName = updateProjName+","+bean.getProj_name();
        		
        			totProj++;
        		}else {
        			row.createCell(3).setCellValue(updateProjName);
            		row.createCell(4).setCellValue(userName);
            		userName = bean.getUser_name();
            		userId = bean.getUser_id();
            		updateProjName = bean.getProj_name();
            		totProj++;
            		rowno++;
            		totUser++;
            		row = sheet.createRow(rowno);
        		}
        	}
        	
        	
        	
    		count++;
    		if(list.size()==count) {
    			row.createCell(3).setCellValue(updateProjName);
        		row.createCell(4).setCellValue(bean.getUser_name());
        		totUser++;
        		rowno++;
    			row = sheet.createRow(rowno);
    			gdTotalProj =gdTotalProj + totProj;
    			gdTotalUser = gdTotalUser + totUser;
    			mergedRegion = new CellRangeAddress(rowno,rowno,0,3);
    		    sheet.addMergedRegion(mergedRegion);
    			cell = row.createCell(0);
    			cell.setCellValue("Total Project :"+totProj);
    			cell.setCellStyle(style);
    			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
    			cell = row.createCell(1);
    			cell.setCellStyle(style);
    			cell =row.createCell(2);
    			cell.setCellStyle(style);
    			cell =row.createCell(3);
    			cell.setCellStyle(style);
    			cell =row.createCell(4);
    			cell.setCellValue("Total User :"+totUser);
    			cell.setCellStyle(style);
    			rowno++;
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
//		font1.setColor(IndexedColors.WHITE.getIndex());
		style1.setFont(font1);
        
      mergedRegion = new CellRangeAddress(rowno,rowno,0,3); 
      sheet.addMergedRegion(mergedRegion);
		
        row = sheet.createRow(rowno);
        cell = row.createCell(0);
        cell.setCellValue("Grand Total : "+gdTotalProj);
        cell.setCellStyle(style1);
        CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell = row.createCell(2);
        cell.setCellStyle(style1);
        cell = row.createCell(3);
        cell.setCellStyle(style1);
        cell = row.createCell(4);
        cell.setCellValue("Grand Total : "+gdTotalUser);
        cell.setCellStyle(style1);
        
        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowno, 4);
        String fileName = "attachment; filename=Report OT2.xlsx";
        
        CommonFunctions.downloadExcel(response, workbook, fileName);
		
	
	
	return "reports/wcdcPiaUserReport";
	
}
	
}
