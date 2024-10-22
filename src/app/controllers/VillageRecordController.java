package app.controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
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
import app.bean.BaselineStateWiseAreaDetailBean;
import app.bean.VillageRecordBean;
import app.common.CommonFunctions;
import app.service.StateMasterService;
import app.service.VillageRecordService;

@Controller("VillageRecordController")
public class VillageRecordController {

@Autowired
StateMasterService stateMasterService;

@Autowired
VillageRecordService villageRecordService;
private Map<Integer, String> stateList;

@RequestMapping(value = "/vibrantvillagekdjdk", method = RequestMethod.GET)
public ModelAndView getVibrantVlgRecord(HttpServletRequest request) {
	//session = request.getSession(true);
	String state="0";
	String status="false";
	stateList=stateMasterService.getAllState();
	List<VillageRecordBean> list=new  ArrayList<VillageRecordBean>();
	ModelAndView mav = new ModelAndView("reports/VillageRecord");
	if(state!=null)
		list=villageRecordService.getVibrantVillageDetails(Integer.parseInt(state), status);
	mav.addObject("villageList",list);
	mav.addObject("villageListSize",list.size());
	mav.addObject("stateList", stateList);
	mav.addObject("stCode", state);
	mav.addObject("statuss", status);
	return mav;
}

@RequestMapping(value = "/vibrantvillagekdjdk", method = RequestMethod.POST)
public ModelAndView getVibrantVlgRecordpost(HttpServletRequest request) {
	//session = request.getSession(true);
	String state=request.getParameter("state");
	String status=request.getParameter("status");
	stateList=stateMasterService.getAllState();
	List<VillageRecordBean> list=new  ArrayList<VillageRecordBean>();
	ModelAndView mav = new ModelAndView("reports/VillageRecord");
	if(state!=null)
		list=villageRecordService.getVibrantVillageDetails(Integer.parseInt(state), status);
	mav.addObject("villageList",list);
	mav.addObject("villageListSize",list.size());
	mav.addObject("stateList", stateList);
	mav.addObject("stCode", state);
	mav.addObject("statuss", status);
	return mav;
}


@RequestMapping(value = "/downloadExcelVibrantVillage", method = RequestMethod.POST)
@ResponseBody
public String downloadExcelVibrantVillage(HttpServletRequest request, HttpServletResponse response) 
{
	String state=request.getParameter("state");
	String status=request.getParameter("status");
	String stName= request.getParameter("stName");
	
	List<VillageRecordBean> list=new  ArrayList<VillageRecordBean>();
	
	list=villageRecordService.getVibrantVillageDetails(Integer.parseInt(state), status);
		
	Workbook workbook = new XSSFWorkbook();  
	//invoking creatSheet() method and passing the name of the sheet to be created   
	Sheet sheet = workbook.createSheet("Report OT3- List of Vibrant Villages");   
	
	CellStyle style = CommonFunctions.getStyle(workbook);
    
	String rptName = "Report OT3- List of Vibrant Villages";
	String areaAmtValDetail ="";
	
	CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
	CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
	
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
	cell.setCellValue("Block Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(4);
	cell.setCellValue("Village Name");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(5);
	cell.setCellValue("LGD Code");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(6);
	cell.setCellValue("International Border");  
	cell.setCellStyle(style);
	
	cell = rowhead.createCell(7);
	cell.setCellValue("Distance from LAC");  
	cell.setCellStyle(style);
	
	Row rowhead1 = sheet.createRow(6);
	for(int i=0;i<8;i++)
	{
		cell =rowhead1.createCell(i);
		cell.setCellValue(i+1);
		cell.setCellStyle(style);
	}
	
	int sno = 1;
    int rowno  = 7;
    
    for(VillageRecordBean bean: list) {
    	Row row = sheet.createRow(rowno);
    	row.createCell(0).setCellValue(sno); 
    	row.createCell(1).setCellValue(bean.getSt_name());
    	row.createCell(2).setCellValue(bean.getDistrict());
    	row.createCell(3).setCellValue(bean.getBlock());
    	row.createCell(4).setCellValue(bean.getVillage());
    	row.createCell(5).setCellValue(bean.getLgd_code_village());
    	row.createCell(6).setCellValue(bean.getInternational_border());
    	row.createCell(7).setCellValue(bean.getDistancefromlac()==null?0.0:bean.getDistancefromlac().doubleValue());
    	
    	sno++;
    	rowno++;
    }

    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 7);
    String fileName = "attachment; filename=Report OT3.xlsx";
    
    CommonFunctions.downloadExcel(response, workbook, fileName);

    return "reports/VillageRecord";
}

@RequestMapping(value = "/downloadPDFVibrantVillage", method = RequestMethod.POST)
public ModelAndView downloadPDFVibrantVillage(HttpServletRequest request, HttpServletResponse response) 
{
	String state=request.getParameter("state");
	String status=request.getParameter("status");
	String stName= request.getParameter("stName");
	
	List<VillageRecordBean> list=new  ArrayList<VillageRecordBean>();
	
	list=villageRecordService.getVibrantVillageDetails(Integer.parseInt(state), status);
	
	try {
		
		Rectangle layout = new Rectangle(PageSize.A4.rotate());
		layout.setBackgroundColor(new BaseColor(255, 255, 255));
		Document document = new Document(layout, 25, 10, 10, 0);
		document.addTitle("VibrantVillageReport");
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
		
			paragraph3 = new Paragraph("Report OT3- List of Vibrant Villages", f3);
		
		//paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph2.setAlignment(Element.ALIGN_CENTER);
		paragraph3.setAlignment(Element.ALIGN_CENTER);
		paragraph2.setSpacingAfter(10);
		paragraph3.setSpacingAfter(10);
		CommonFunctions.addHeader(document);
//		addFooter(writer);
//		document.add(paragraph);
		document.add(paragraph2);
		document.add(paragraph3);
			table = new PdfPTable(8);
			table.setWidths(new int[] {2, 4, 4, 4, 12, 3, 4, 3});
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
						
			
			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Block Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Village Name", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "LGD Code", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "International Border", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Distance from LAC", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
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
					CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistrict().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getBlock().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getVillage().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getLgd_code_village().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getInternational_border().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistancefromlac().toString(), Element.ALIGN_CENTER, 1, 1, bf8);
					
					k=k+1;
				}
				
				if(list.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
			
			
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
	response.setHeader("Content-Disposition", "attachment;filename=Report- OT3.pdf");
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
