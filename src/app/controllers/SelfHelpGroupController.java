package app.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import app.bean.AssetIdBean;
import app.bean.Login;
import app.bean.SelfHelpGroupBean;
import app.bean.reports.SelfHelpGroupReportBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.janbhagidariPratiyogita.JanbhagidariPratiyogitaBean;
import app.model.outcome.ShgCoreactivityDetail;
import app.service.ProjectMasterService;
import app.service.SelfHelpGroupService;
import app.service.StateMasterService;
import app.service.UserService;
import app.service.unfreeze.UnfreezeBaselineServices;

@Controller("SelfHelpGroupController")
public class SelfHelpGroupController {
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	SelfHelpGroupService selfHelpGroupService;
	
	HttpSession session;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public UserService userService;
	
	@Autowired(required = true)
	public UnfreezeBaselineServices ser;
	
	
	private Map<Integer, String> stateList;
	private Map<String, String> districtList;
	private Map<String, String> ProjectList;
	private Map<String, String> shgType;

	@RequestMapping(value = "/newshg", method = RequestMethod.GET)
	public ModelAndView getNewSHG(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			userType.put("newSHG","Newly Created SHG");
			mav = new ModelAndView("selfHelpGroup");
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/eshg", method = RequestMethod.GET)
	public ModelAndView getExistingSHG(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			userType.put("oldSHG","Existing SHG");
			mav = new ModelAndView("selfHelpGroup");
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/ug", method = RequestMethod.GET)
	public ModelAndView getUserGroup(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> userType = new HashMap<String,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			userType.put("group","User Group");
			mav = new ModelAndView("selfHelpGroup");
			mav.addObject("projectList",projectMasterService.getProjectByRegId(regId));
			mav.addObject("groupType",userType);
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/getSHGCoreActivity", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,Integer> getSHGCoreActivity(HttpServletRequest request) {
		session = request.getSession(true);
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		LinkedHashMap<String,Integer> remap = new LinkedHashMap<>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			map = selfHelpGroupService.getSHGCoreActivity();
			for(Map.Entry<Integer, String> remp : map.entrySet()) {
				remap.put(remp.getValue(), remp.getKey());
			}
			/*
			 * map.put(1, "A"); map.put(2, "B");
			 */
		}else {
			
		}
		
		return remap;
	}
	
	
	@RequestMapping(value = "/saveDraftSHG", method = RequestMethod.POST)
	@ResponseBody
	public String saveDraftSHGData(HttpServletRequest request,@RequestParam("project") Integer projectCd,@RequestParam("group") String group,@RequestParam("no") Integer shgno,
			@RequestParam("name") List<String> name,@RequestParam("sc") List<Integer> sc,@RequestParam("st") List<Integer> st,@RequestParam("others") List<Integer> others,
			@RequestParam("women") List<Integer> women,@RequestParam("activity") List<String> activity,@RequestParam("turnover") List<BigDecimal> turnover,@RequestParam("income") List<BigDecimal> income,
			@RequestParam("pmbima") List<Integer> pmbima,@RequestParam("fedrated") List<String> fedrated,@RequestParam("department") List<Integer> department,@RequestParam("regdate") List<String> regdate,
			@RequestParam("revolve_amount") List<BigDecimal> revolve_amount,@RequestParam("threft_credit") List<Boolean> threft_credit) {
		session = request.getSession(true);
		String res="fail";
		if(session!=null && session.getAttribute("loginID")!=null) {
			String updatedby = session.getAttribute("loginID").toString();
			System.out.println(projectCd+" : "+group+" : "+shgno+" : "+name+" : "+sc+" : "+st+" : "+others+" : "+women+" : "+activity+" : "+turnover+" : "+income+" : "+pmbima+" : "+fedrated+" : "+department+" : "+regdate+" : "+revolve_amount+" : "+threft_credit);
			res= selfHelpGroupService.saveDraftSHGData(projectCd,group,shgno,name,sc,st,others,women,activity,turnover,income,pmbima,fedrated,updatedby,department,regdate,revolve_amount,threft_credit);
			
		}else {
			
		}
		
		return res;
	}
	
	@RequestMapping(value = "/getSHGDraftData", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,List<SelfHelpGroupBean>> getSHGDraftData(HttpServletRequest request,@RequestParam("project") Integer projectCd,@RequestParam("group") String group) {
		session = request.getSession(true);
		LinkedHashMap<Integer,List<SelfHelpGroupBean>> map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		
		if(session!=null && session.getAttribute("loginID")!=null) {
			System.out.println(projectCd+" : "+group);
			map= selfHelpGroupService.getSHGDraftData(projectCd,group);
			
		}else {
			map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		}
		
		return map;
	}
	
	@RequestMapping(value = "/deleteSHG", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSHG(HttpServletRequest request,@RequestParam("shgid") Integer shgid,@RequestParam("group") String group) {
		session = request.getSession(true);
		String res="fail";
		if(session!=null && session.getAttribute("loginID")!=null) {
			res= selfHelpGroupService.deleteSHG(shgid,group);
			
		}else {
			//map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		}
		
		return res;
	}
	
	@RequestMapping(value = "/completeSHG", method = RequestMethod.POST)
	@ResponseBody
	public String completeSHG(HttpServletRequest request,@RequestParam("shgid") Integer shgid) {
		session = request.getSession(true);
		String res="fail";
		if(session!=null && session.getAttribute("loginID")!=null) {
			System.out.println(shgid);
			res= selfHelpGroupService.completeSHG(shgid);
			
		}else {
			//map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		}
		
		return res;
	}
	
	@RequestMapping(value = "/getSHGDepartment", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getSHGDepartment(HttpServletRequest request) {
		session = request.getSession(true);
		LinkedHashMap<Integer,String> res=new LinkedHashMap<Integer,String>();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer stCode = Integer.parseInt(session.getAttribute("stateCode").toString());
			res= selfHelpGroupService.getSHGDepartment(stCode);
			
		}else {
			//map = new LinkedHashMap<Integer,List<SelfHelpGroupBean>>();
		}
		
		return res;
	}
	
	@RequestMapping(value="/selfHelpGroupNameAccountReport", method = RequestMethod.GET)
	public ModelAndView selfHelpGroupNameAccountReport(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		List<SelfHelpGroupReportBean> data =new ArrayList<SelfHelpGroupReportBean>();
				
		/* if(session!=null && session.getAttribute("loginID")!=null) { */
			
			mav = new ModelAndView("reports/selfHelpGroupNameAccount");
			
			shgType= new LinkedHashMap<String, String>();
			shgType.put("both", "Both SHG");
			shgType.put("newSHG", "Newly Created SHG");
			shgType.put("oldSHG", "Existing Created SHG");
			
			mav.addObject("shgType", shgType);
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);}
				mav.addObject("project", project);
				
				/*	if(userState==null) {
					data=selfHelpGroupService.getselfHelpGroupNameAccountReport();
					mav.addObject("dataList",data);
					mav.addObject("dataListSize",data.size());	
				}
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}*/
		return mav; 
	}
	
	@RequestMapping(value="/selfHelpGroupNameAccountReport", method = RequestMethod.POST)
	public ModelAndView selfHelpGroupNameAccountReport1(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
	//	String st_code=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String shgty=request.getParameter("shg");
		List<SelfHelpGroupReportBean> data =new ArrayList<SelfHelpGroupReportBean>();
				
		/* if(session!=null && session.getAttribute("loginID")!=null) { */
			
			mav = new ModelAndView("reports/selfHelpGroupNameAccount");
			
			shgType= new LinkedHashMap<String, String>();
			shgType.put("both", "Both SHG");
			shgType.put("newSHG", "Newly Created SHG");
			shgType.put("oldSHG", "Existing Created SHG");
			
			mav.addObject("shgType", shgType);
			
			stateList=stateMasterService.getAllState();
			mav.addObject("stateList", stateList);
			mav.addObject("state", userState);
			
			if(userState!=null && !userState.equals("") && !userState.equals("0")) {
			districtList = userService.getDistrictList(Integer.parseInt(userState));
			mav.addObject("districtList", districtList);}
			mav.addObject("district", district);
			
			if( district!=null && !district.equalsIgnoreCase("") && !district.equals("0")) {
				ProjectList = userService.getProjectList(Integer.parseInt(userState), Integer.parseInt(district));
				mav.addObject("ProjectList", ProjectList);}
				mav.addObject("project", project);
				
				
					data=selfHelpGroupService.getselfHelpGroupNameAccountReportshg(userState, district, project, shgty);
					mav.addObject("dataList",data);
					mav.addObject("dataListSize",data.size());	
					
					mav.addObject("shg", shgty);
		/*}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}*/
		return mav; 
	}
	
	@RequestMapping(value = "/selfHelpGroupNameAccountReportPDF", method = RequestMethod.POST)
	public ModelAndView selfHelpGroupNameAccountReportPDF(HttpServletRequest request, HttpServletResponse response) {
		
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String shgty=request.getParameter("shg");
		
		List<SelfHelpGroupReportBean> data =new ArrayList<SelfHelpGroupReportBean>();
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("State, District and Project-wise self Help Group Name with Bank Account Details");
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

	        paragraph3 = new Paragraph("State, District and Project-wise self Help Group Name with Bank Account Details", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(9);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(1);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Registration Date", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "SHG Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Account Number", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "IFSC Code", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "SHG Type", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	    
	        data=selfHelpGroupService.getselfHelpGroupNameAccountReportshg(userState, district, project, shgty);
	        String st="";
	        String dist="";
	        int k=1;
	        String proj="";
			
	        if (data.size() != 0) 
	        {
	            for (int i = 0; i < data.size(); i++) 
	            {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                if( !st.equalsIgnoreCase(data.get(i).getSt_name()) ) {
	                	 CommonFunctions.insertCell(table, data.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					}
					else {
						CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
					}
	                if(!dist.equalsIgnoreCase(data.get(i).getDist_name()) ) {
	                	 CommonFunctions.insertCell(table, data.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                	 dist=data.get(i).getDist_name();
					}
					else {
						CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					if(!proj.equalsIgnoreCase(data.get(i).getProj_name()) ) {
	                	 CommonFunctions.insertCell(table, data.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                	 proj=data.get(i).getProj_name();
					}
					else {
						CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					
					 CommonFunctions.insertCell(table, data.get(i).getReg_date(), Element.ALIGN_LEFT, 1, 1, bf8);
					 CommonFunctions.insertCell(table, data.get(i).getName(), Element.ALIGN_LEFT, 1, 1, bf8);
					 CommonFunctions.insertCell(table, data.get(i).getAccount_detail(), Element.ALIGN_LEFT, 1, 1, bf8);
					 CommonFunctions.insertCell(table, data.get(i).getIfsc_code(), Element.ALIGN_LEFT, 1, 1, bf8);
					 
					 if(data.get(i).getGroup_type().equalsIgnoreCase("newSHG"))
					 CommonFunctions.insertCell(table, "New SHG", Element.ALIGN_LEFT, 1, 1, bf8);
					 
					 if(data.get(i).getGroup_type().equalsIgnoreCase("oldSHG"))
						 CommonFunctions.insertCell(table, "Old SHG", Element.ALIGN_LEFT, 1, 1, bf8);
	               
	                
	                st=data.get(i).getSt_name();
	                k = k + 1;
	            }


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
	        response.setHeader("Content-Disposition", "attachment; filename=SHG Name with Bank Account Details.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/selfHelpGroupNameAccountReportExcel", method = RequestMethod.POST)
	@ResponseBody
	public String selfHelpGroupNameAccountReportExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String userState= request.getParameter("state");
		String district= request.getParameter("district");
		String project= request.getParameter("project");
		String shgty=request.getParameter("shg");
		
		List<SelfHelpGroupReportBean> data =new ArrayList<SelfHelpGroupReportBean>();
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("State, District and Project-wise self Help Group Name with Bank Account Details");   

	    

		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "State, District and Project-wise self Help Group Name with Bank Account Details";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);

		data=selfHelpGroupService.getselfHelpGroupNameAccountReportshg(userState, district, project, shgty);
		mergedRegion = new CellRangeAddress(data.size()+7,data.size()+7,0,1); 
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
		cell.setCellValue("District Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("Project Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("Registration Date");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("SHG Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(6);
		cell.setCellValue("Account Number");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("IFSC Code");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("SHG Type");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i=0;i<9;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		

		int sno = 1;
		int rowno  = 7;
		
		for(SelfHelpGroupReportBean bean: data) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getSt_name());
			row.createCell(2).setCellValue(bean.getDist_name());  
			row.createCell(3).setCellValue(bean.getProj_name());  
			row.createCell(4).setCellValue(bean.getReg_date()); 
			row.createCell(5).setCellValue(bean.getName());  
			row.createCell(6).setCellValue(bean.getAccount_detail()); 
			row.createCell(7).setCellValue(bean.getIfsc_code()); 
			if(bean.getGroup_type().equalsIgnoreCase("newSHG"))
				 row.createCell(8).setCellValue("New SHG");
				 
			if(bean.getGroup_type().equalsIgnoreCase("oldSHG"))
				row.createCell(8).setCellValue("Old SHG");  
			
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

		
		CommonFunctions.getExcelFooter(sheet, mergedRegion, data.size(), 8);
		String fileName = "attachment; filename=SHG Name with Bank Account Details.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return null;

	}

}
