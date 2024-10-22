package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

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

import app.bean.AddOutcomeParaBean;
import app.bean.RPCLivelihoodBean;
import app.bean.reports.PhysicalActionAchievementBean;
import app.common.CommonFunctions;
import app.service.FinYrServices;
import app.service.StateMasterService;
import app.service.reports.MnthWiseMndyFrmrDtlService;
import app.service.reports.PhysicalActionAchievementService;

@Controller
public class MnthWiseMndyFrmrDtlController {
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	PhysicalActionAchievementService pAAservices;
	
	@Autowired
	MnthWiseMndyFrmrDtlService mnthWiseMndyFrmrDtlService;
	
	@RequestMapping(value="/getMonthWiseStatusAdditionalParameter", method = RequestMethod.GET)
	public ModelAndView monthWiseMndyFrmrDtl(HttpServletRequest request, HttpServletResponse response)
	{
		//session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("reports/mnthWiseMndyFrmrDtls");
		mav.addObject("stateList",stateMasterService.getAllState());
		mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));
		return mav;
	}

	@RequestMapping(value = "/getMonthWiseStatusAdditionalParameter", method = RequestMethod.POST)
	public ModelAndView getMonthWiseStatusAdditionalParameter(HttpServletRequest request, HttpServletResponse response) {

		 String stcode=request.getParameter("state");
		 String dcode=request.getParameter("district");
		 String projid=request.getParameter("project");
		 String finyear=request.getParameter("finyear");
		
		ModelAndView mav = new ModelAndView();
		List<AddOutcomeParaBean> list = new ArrayList<AddOutcomeParaBean>();
		
		try {
			list = mnthWiseMndyFrmrDtlService.getMnthWiseMndyFrmrDtlList(Integer.parseInt(stcode), Integer.parseInt(dcode), Integer.parseInt(projid), Integer.parseInt(finyear));
			mav = new ModelAndView("reports/mnthWiseMndyFrmrDtls");
			mav.addObject("stateList",stateMasterService.getAllState());
			mav.addObject("yearList",pAAservices.getYearForPhysicalActionAchievementReport(0));	
			mav.addObject("netTreatledata",list);
			mav.addObject("listsize", list.size());
			mav.addObject("stcode", stcode);
			mav.addObject("dcode", dcode);
			mav.addObject("projid", projid);
			mav.addObject("fyear", finyear);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}
	
	@RequestMapping(value = "/getMonthwiseMandaysExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getMonthwiseMandaysExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		int stCode= Integer.parseInt(request.getParameter("stCode"));
		int distCode= Integer.parseInt(request.getParameter("distCode"));
		int projectId= Integer.parseInt(request.getParameter("projId"));
		int fromYear= Integer.parseInt(request.getParameter("fYear"));
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		List<AddOutcomeParaBean> beanList = new ArrayList<AddOutcomeParaBean>();
		beanList=mnthWiseMndyFrmrDtlService.getMnthWiseMndyFrmrDtlList(stCode, distCode, projectId, fromYear);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report O10- Financial Year and Month wise Mandays Generated and Farmers Benefitted Details");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report O10- Financial Year and Month wise Mandays Generated and Farmers Benefitted Details";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, beanList.size()+2, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,5,0,beanList.size()+2);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(8,8,0,beanList.size()+2);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(10,10,0,beanList.size()+2);
	        sheet.addMergedRegion(mergedRegion);
	        
	        
	        
	        
			Row rowhead0 = sheet.createRow(5);
			Cell cell = rowhead0.createCell(0);
			cell.setCellValue("State: "+stName+" District: "+ distName+" Project: "+projName+" Financial Year: "+yearName);
			cell.setCellStyle(style);
			for(int i =1;i<beanList.size()+3;i++) {
				cell = rowhead0.createCell(i);
				cell.setCellStyle(style);
			}
			
			Row rowhead = sheet.createRow(6);
			cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			cell = rowhead.createCell(1);
			cell.setCellValue("Parameter Names");
			cell.setCellStyle(style);
			int cellNo = 2;
			for(AddOutcomeParaBean bean : beanList){
				cell = rowhead.createCell(cellNo);
				cell.setCellValue(bean.getMonth_name());
				cell.setCellStyle(style);
				cellNo++;
			}
			
			cell = rowhead.createCell(cellNo);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(7);
			int colNo = 0;
			for(int i =0; i<beanList.size()+3;i++){
				cell = rowhead1.createCell(colNo);
				cell.setCellValue(colNo+1);  
				cell.setCellStyle(style);
				colNo ++;
			}
			
			Row rowhead2 = sheet.createRow(8);
			cell = rowhead2.createCell(0);
			cell.setCellValue("Total Number Of Mandays Generated");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			for(int i =1;i<beanList.size()+3;i++) {
				cell = rowhead2.createCell(i);
				cell.setCellStyle(style);
			}
			
			
			
	        int rowno  =9;
	        int count = 1;
	        double mandays = 0.0;
	        double sc = 0.0;
	        double st = 0.0;
	        double other = 0.0;
	        double total = 0.0;
	        double female = 0.0;
	        double small = 0.0;
	        double mirginal = 0.0;
	        double landlss = 0.0;
	        double bpl = 0.0;
	        
	        for(AddOutcomeParaBean bean: beanList) {
	        	mandays = mandays+ bean.getMan_day();
	        	sc = sc + bean.getSc();
	        	st = st + bean.getSt();
	        	other = other + bean.getOther();
	        	total = total + bean.getTotal();
	        	female = female + bean.getFemale();
	        	small = small + bean.getSmall();
	        	mirginal = mirginal + bean.getMirginal();
	        	landlss = landlss + bean.getLandless();
	        	bpl = bpl + bean.getBpl();
	        }
	        
	        Row row = sheet.createRow(rowno);
	        for(int i = 0;i<10;i++) {
	        	if(count ==2) {
	        		row = sheet.createRow(rowno);
	        		cell = row.createCell(0);
	        		cell.setCellValue("Total Number Of Farmer Benefitted");
	        		cell.setCellStyle(style);
	        		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
	        		rowno++;
	        	}
	        	row = sheet.createRow(rowno);
	        	cell = row.createCell(0);
	        	cell.setCellValue(count);
	        	if(count ==1) {
	        		cell = row.createCell(1);
		        	cell.setCellValue("Mandays");
	        	}
	        	if(count ==2) {
	        		cell = row.createCell(1);
		        	cell.setCellValue("SC");
	        	}
	        	if(count ==3) {
	        		cell = row.createCell(1);
		        	cell.setCellValue("ST");
	        	}
	        	if(count ==4) {
	        		cell = row.createCell(1);
		        	cell.setCellValue("Others");
	        	}
	        	if(count ==5) {
	        		cell = row.createCell(1);
		        	cell.setCellValue("Total");
	        	}
	        	if(count ==6) {
	        		cell = row.createCell(1);
		        	cell.setCellValue("Female");
	        	}
	        	if(count ==7) {
	        		cell = row.createCell(1);
		        	cell.setCellValue("Small Farmers");
	        	}
	        	if(count ==8) {
	        		cell = row.createCell(1);
		        	cell.setCellValue("Marginal Farmers");
	        	}
	        	if(count ==9) {
	        		cell = row.createCell(1);
		        	cell.setCellValue("Landless");
	        	}
	        	if(count ==10) {
	        		cell = row.createCell(1);
		        	cell.setCellValue("BPL");
	        	}
	        	int no =2 ;
	        	for(AddOutcomeParaBean bean: beanList) {
	        		if(count ==1) {
	        			cell = row.createCell(no);
			        	cell.setCellValue(bean.getMan_day());
	        		}
	        		if(count ==2) {
	        			cell = row.createCell(no);
			        	cell.setCellValue(bean.getSc());
	        		}
	        		if(count ==3) {
	        			cell = row.createCell(no);
			        	cell.setCellValue(bean.getSt());
	        		}
	        		if(count ==4) {
	        			cell = row.createCell(no);
			        	cell.setCellValue(bean.getOther());
	        		}
	        		if(count ==5) {
	        			cell = row.createCell(no);
			        	cell.setCellValue(bean.getTotal());
	        		}
	        		if(count ==6) {
	        			cell = row.createCell(no);
			        	cell.setCellValue(bean.getFemale());
	        		}
	        		if(count ==7) {
	        			cell = row.createCell(no);
			        	cell.setCellValue(bean.getSmall());
	        		}
	        		if(count ==8) {
	        			cell = row.createCell(no);
			        	cell.setCellValue(bean.getMirginal());
	        		}
	        		if(count ==9) {
	        			cell = row.createCell(no);
			        	cell.setCellValue(bean.getLandless());
	        		}
	        		if(count ==10) {
	        			cell = row.createCell(no);
			        	cell.setCellValue(bean.getBpl());
	        		}
	        	
	        	no++;
	        	
	        	}
	        	if(count ==1) {
        			cell = row.createCell(no);
		        	cell.setCellValue(mandays);
        		}
        		if(count ==2) {
        			cell = row.createCell(no);
		        	cell.setCellValue(sc);
        		}
        		if(count ==3) {
        			cell = row.createCell(no);
		        	cell.setCellValue(st);
        		}
        		if(count ==4) {
        			cell = row.createCell(no);
		        	cell.setCellValue(other);
        		}
        		if(count ==5) {
        			cell = row.createCell(no);
		        	cell.setCellValue(total);
        		}
        		if(count ==6) {
        			cell = row.createCell(no);
		        	cell.setCellValue(female);
        		}
        		if(count ==7) {
        			cell = row.createCell(no);
		        	cell.setCellValue(small);
        		}
        		if(count ==8) {
        			cell = row.createCell(no);
		        	cell.setCellValue(mirginal);
        		}
        		if(count ==9) {
        			cell = row.createCell(no);
		        	cell.setCellValue(landlss);
        		}
        		if(count ==10) {
        			cell = row.createCell(no);
		        	cell.setCellValue(bpl);
        		}
        		rowno++;
        		count++;
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
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowno, beanList.size()+2);
	        String fileName = "attachment; filename=Report O10.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/mothWiseMndyFrmrDtls";
		
	}
	
	@RequestMapping(value = "/downloadMonthwiseStatusAddPrmPDF", method = RequestMethod.POST)
	public ModelAndView downloadMonthwiseStatusAddPrmPDF(HttpServletRequest request, HttpServletResponse response) 
	{
//		//WDC-PMKSY-0001113
		int stCode= Integer.parseInt(request.getParameter("stCode"));
		int distCode= Integer.parseInt(request.getParameter("distCode"));
		int projectId= Integer.parseInt(request.getParameter("projId"));
		int fromYear= Integer.parseInt(request.getParameter("fYear"));
		String stName= request.getParameter("stName");
		String distName= request.getParameter("distName");
		String projName= request.getParameter("projName");
		String yearName= request.getParameter("yearName");
		List<AddOutcomeParaBean> beanList = new ArrayList<AddOutcomeParaBean>();
		beanList=mnthWiseMndyFrmrDtlService.getMnthWiseMndyFrmrDtlList(stCode, distCode, projectId, fromYear);
		
		try {
			
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("MonthwiseStAddPrmReport");
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
			
			paragraph3 = new Paragraph("Report O10- Financial Year and Month wise Mandays Generated and Farmers Benefitted Details ", f3);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
//			addFooter(writer);
//			document.add(paragraph);
			document.add(paragraph2);
			document.add(paragraph3);
			if(beanList.size()!=0)
				table = new PdfPTable(beanList.size()+3);
				int[] arr = new int[beanList.size()+3];
				for(int i =0; i<beanList.size()+3;i++) {
					arr[i] = 5;
				}
				table.setWidths(arr);
				table.setWidthPercentage(70);
				table.setSpacingBefore(0f);
				table.setSpacingAfter(0f);
			
				
				 int sc=0, st=0, other=0, female=0, small=0, mirginal=0, landlss=0, bpl=0, total=0, mandays=0;
				 
				 CommonFunctions.insertCellHeader(table, "State: "+stName+"  District: "+distName + "  Project: "+ projName + "  Financial Year: "+yearName, Element.ALIGN_LEFT, 15, 1, bf8Bold);					
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Parameter Names", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				for(int i=0;i<beanList.size();i++) 
					{
				CommonFunctions.insertCellHeader(table, beanList.get(i).getMonth_name(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
					}
				CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
				 
				for(int i=0;i<beanList.size()+3;i++) 
				{
					CommonFunctions.insertCellHeader(table, String.valueOf(i+1), Element.ALIGN_CENTER, 1, 1, bf8Bold);
				}
				CommonFunctions.insertCellHeader(table, "Total Number of Mandays Generated", Element.ALIGN_CENTER, beanList.size()+3, 1, bf8Bold);
        
			        for(AddOutcomeParaBean bean: beanList) {
			        	mandays = mandays+ bean.getMan_day();
			        	sc = sc + bean.getSc();
			        	st = st + bean.getSt();
			        	other = other + bean.getOther();
			        	total = total + bean.getTotal();
			        	female = female + bean.getFemale();
			        	small = small + bean.getSmall();
			        	mirginal = mirginal + bean.getMirginal();
			        	landlss = landlss + bean.getLandless();
			        	bpl = bpl + bean.getBpl();
			        }
				if(beanList.size()!=0)
					for(int i1 = 1;i1<11;i1++)
					{
						
						if(i1 ==2) {
							CommonFunctions.insertCellHeader(table, "Total Number Of Farmer Benefitted", Element.ALIGN_CENTER, beanList.size()+3, 1, bf8Bold);
				        		}
						CommonFunctions.insertCell(table, String.valueOf(i1), Element.ALIGN_LEFT, 1, 1, bf8);
				        	if(i1 ==1) {
				        		CommonFunctions.insertCell(table, "Mandays", Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
						
				        	if(i1 ==2) {
				        		CommonFunctions.insertCell(table, "SC", Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==3) {
				        		CommonFunctions.insertCell(table, "ST", Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==4) {
				        		CommonFunctions.insertCell(table, "Others", Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==5) {
				        		CommonFunctions.insertCell(table, "Total", Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==6) {
				        		CommonFunctions.insertCell(table, "Female", Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==7) {
				        		CommonFunctions.insertCell(table, "Small Farmers", Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==8) {
				        		CommonFunctions.insertCell(table, "Marginal Farmers", Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==9) {
				        		CommonFunctions.insertCell(table, "Landless", Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==10) {
				        		CommonFunctions.insertCell(table, "BPL", Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        		
						for(int i=0;i<beanList.size();i++) {
							if(i1 ==1) {
				        		CommonFunctions.insertCell(table, String.valueOf(beanList.get(i).getMan_day()), Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
							if(i1 ==2) {
				        		CommonFunctions.insertCell(table, String.valueOf(beanList.get(i).getSc()), Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==3) {
				        		CommonFunctions.insertCell(table, String.valueOf(beanList.get(i).getSt()), Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==4) {
				        		CommonFunctions.insertCell(table, String.valueOf(beanList.get(i).getOther()), Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==5) {
				        		CommonFunctions.insertCell(table, String.valueOf(beanList.get(i).getTotal()), Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	
				        	if(i1 ==6) {
				        		CommonFunctions.insertCell(table, String.valueOf(beanList.get(i).getFemale()), Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==7) {
				        		CommonFunctions.insertCell(table, String.valueOf(beanList.get(i).getSmall()), Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==8) {
				        		CommonFunctions.insertCell(table, String.valueOf(beanList.get(i).getMirginal()), Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==9) {
				        		CommonFunctions.insertCell(table, String.valueOf(beanList.get(i).getLandless()), Element.ALIGN_LEFT, 1, 1, bf8);
				        	}
				        	if(i1 ==10) {
				        		CommonFunctions.insertCell(table, String.valueOf(beanList.get(i).getBpl()), Element.ALIGN_LEFT, 1, 1, bf8);
				        	}	
						}
						
						if(i1 ==1) {
			        		CommonFunctions.insertCell(table, String.valueOf(mandays), Element.ALIGN_LEFT, 1, 1, bf8);
			        	}
						if(i1 ==2) {
			        		CommonFunctions.insertCell(table, String.valueOf(sc), Element.ALIGN_LEFT, 1, 1, bf8);
			        	}
			        	if(i1 ==3) {
			        		CommonFunctions.insertCell(table, String.valueOf(st), Element.ALIGN_LEFT, 1, 1, bf8);
			        	}
			        	if(i1 ==4) {
			        		CommonFunctions.insertCell(table, String.valueOf(other), Element.ALIGN_LEFT, 1, 1, bf8);
			        	}
			        	if(i1 ==5) {
			        		CommonFunctions.insertCell(table, String.valueOf(total), Element.ALIGN_LEFT, 1, 1, bf8);
			        	}
			        	if(i1 ==6) {
			        		CommonFunctions.insertCell(table, String.valueOf(female), Element.ALIGN_LEFT, 1, 1, bf8);
			        	}
			        	if(i1 ==7) {
			        		CommonFunctions.insertCell(table, String.valueOf(small), Element.ALIGN_LEFT, 1, 1, bf8);
			        	}
			        	if(i1 ==8) {
			        		CommonFunctions.insertCell(table, String.valueOf(mirginal), Element.ALIGN_LEFT, 1, 1, bf8);
			        	}
			        	if(i1 ==9) {
			        		CommonFunctions.insertCell(table, String.valueOf(landlss), Element.ALIGN_LEFT, 1, 1, bf8);
			        	}
			        	if(i1 ==10) {
			        		CommonFunctions.insertCell(table, String.valueOf(bpl), Element.ALIGN_LEFT, 1, 1, bf8);
			        	}
			        	
					}
					
			document.add(table);
			table = new PdfPTable(1);
			table.setWidthPercentage(70);
			table.setSpacingBefore(15f);
			table.setSpacingAfter(0f);
			CommonFunctions.insertCellPageHeader(table,"wdcpmksy 2.0 - MIS Website hosted and maintained by National Informatics Center. Data presented in this site has been updated by respective State Govt./UT Administration and DoLR "+ 
			CommonFunctions.dateToString(null, "dd/MM/yyyy hh:mm aaa"), Element.ALIGN_LEFT, 1, 4, bf8);
			document.add(table);
			CommonFunctions.addFooter(writer);
			document.close();
			response.setContentType("application/pdf");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment;filename=Report- O10.pdf");
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
