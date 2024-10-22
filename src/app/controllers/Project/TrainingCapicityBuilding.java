package app.controllers.Project;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

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

import app.PfmsTreasureBean;
import app.bean.BaseLineOutcomeBean;
import app.bean.CapicityBuildingTrainingBean;
import app.bean.Login;
import app.common.CommonFunctions;
import app.service.TrainingSubjectServices;

@Controller("TrainingCapicityBuilding")
public class TrainingCapicityBuilding {

	@Autowired(required = true)
	TrainingSubjectServices trainingSubject;

	HttpSession session;

	@RequestMapping(value="/trainingCapicityBuilding", method = RequestMethod.GET)
	public ModelAndView removeProjectActiveUser(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		ModelAndView mav = new ModelAndView();
		List<CapicityBuildingTrainingBean> list = new ArrayList<CapicityBuildingTrainingBean>();
		if(session!=null && session.getAttribute("loginid")!=null) 
		{
			try {
				mav = new ModelAndView("project/trainingCapicityBuilding");
				list=trainingSubject.getTrainingDetail12HOURS(Integer.parseInt(state));
				mav.addObject("dataList",list);
				mav.addObject("dataListSize",list.size());

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else { 
			mav = new ModelAndView("login"); 
			mav.addObject("login", new Login());
		}

		return mav; 
	}

	@RequestMapping(value="/getTrainingSubject", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getUserListUnAssigned(HttpServletRequest request)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map.putAll(trainingSubject.getTrainingSubject());
		return map;
	}

	@RequestMapping(value="/saveTrainigCapicityBuilding", method = RequestMethod.POST)
	@ResponseBody
	public String saveTrainigCapicityBuilding(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="level") String level, @RequestParam(value ="noOf") Integer noOfTConduct,
			@RequestParam(value ="person") List<Integer> noOfperson, @RequestParam(value ="sub") List<String> subject, 
			@RequestParam(value ="start") List<String> startdate, @RequestParam(value ="end") List<String> enddate, 
			@RequestParam(value ="training") List<Integer> trainday)
	{
		String res="fail";
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			//	System.out.println(level+" : "+noOfTConduct+" : "+noOfperson+" : "+trainday+" : "+subject);
			if( level!=null ) {
				res=trainingSubject.saveCapicityBuilding(level,noOfTConduct,noOfperson,subject,startdate,enddate,trainday,session);
			}
			else {
				res= "fail";
			}

		}

		return res; 
	}

	///   getCapicityBuildingReport

	@RequestMapping(value="/capicityBuildingReport", method = RequestMethod.GET)
	public ModelAndView capicityBuildingReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		List<CapicityBuildingTrainingBean> list = new ArrayList<CapicityBuildingTrainingBean>();
		String[] dataArrNetTotalStr = null;
		ArrayList dataListNetTotal = new ArrayList();
		Integer[] dataArrNetTotal = null;
		try {
			mav = new ModelAndView("reports/capicityBuildingReport");
			list=trainingSubject.getcapicityBuildingReport();
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

			for(CapicityBuildingTrainingBean bean : list){

				dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getSl_tcond();
				dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getSl_tperson();
				dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getDi_tcond();
				dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getDi_tperson();	
				dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getPi_tcond();
				dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getPi_tperson();
				dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getWc_tcond();
				dataArrNetTotal[7] = dataArrNetTotal[7] + bean.getWc_tperson();
				dataArrNetTotal[8] = dataArrNetTotal[8] + bean.getTotalcond();
				dataArrNetTotal[9] = dataArrNetTotal[9] + bean.getTotalperson();
			}
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotal[0].toString();
			dataArrNetTotalStr[1] = dataArrNetTotal[1].toString();
			dataArrNetTotalStr[2] = dataArrNetTotal[2].toString();
			dataArrNetTotalStr[3] = dataArrNetTotal[3].toString();
			dataArrNetTotalStr[4] = dataArrNetTotal[4].toString();
			dataArrNetTotalStr[5] = dataArrNetTotal[5].toString();
			dataArrNetTotalStr[6] = dataArrNetTotal[6].toString();
			dataArrNetTotalStr[7] = dataArrNetTotal[7].toString();
			dataArrNetTotalStr[8] = dataArrNetTotal[8].toString();
			dataArrNetTotalStr[9] = dataArrNetTotal[9].toString();

			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);  
			mav.addObject("dataList",list);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav; 
	}

	@RequestMapping(value = "/downloadCapacityBuildingReportPDF", method = RequestMethod.POST)
	public ModelAndView downloadCapacityBuildingReportPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		//WDC-PMKSY-0001113
		//		String state=request.getParameter("state");;

		List<CapicityBuildingTrainingBean> cpBean = new ArrayList<CapicityBuildingTrainingBean>();

		try {


			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("capacityBuildingReport");
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

			paragraph3 = new Paragraph("Report CB1- State wise and Year wise number of Training Conducted and Persons Trained at SLNA/WCDC/PIA/WC Level ", f3);

			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph3.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingAfter(10);
			paragraph3.setSpacingAfter(10);
			CommonFunctions.addHeader(document);
			document.add(paragraph2);
			document.add(paragraph3);
			table = new PdfPTable(13);
			table.setWidths(new int[] { 2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
			table.setWidthPercentage(100);
			table.setSpacingBefore(0f);
			table.setSpacingAfter(0f);
			table.setHeaderRows(3);

			int sl_tcond=0,sl_tperson=0,di_tcond=0,di_tperson=0,pi_tcond=0,pi_tperson=0,wc_tcond=0,wc_tperson=0,totalcond=0,totalperson=0;

			cpBean =trainingSubject.getcapicityBuildingReport();

			CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Financial Year", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "SLNA", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "WCDC", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "PIA", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "WC", Element.ALIGN_CENTER, 2, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 2, 1, bf8Bold);

			CommonFunctions.insertCellHeader(table, "Total no. of Training conducted", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total no. of Persons trained", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total no. of Training conducted", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total no. of Persons trained", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total no. of Training conducted", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total no. of Persons trained", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total no. of Training conducted", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total no. of Persons trained", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total no. of Training conducted(4+6+8+10)", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total no. of Persons trained(5+7+9+11)", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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

			int k=1;
			int totNum = 0;
			if(cpBean.size()!=0)
				for(int i=0;i<cpBean.size();i++) 
				{
					if(cpBean.get(i).getSt_code() != totNum) {
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, cpBean.get(i).getStatename().toString(), Element.ALIGN_LEFT, 1, 1, bf8);
						k=k+1;
					}
					else {
						CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, "", Element.ALIGN_LEFT, 1, 1, bf8);
					}
					CommonFunctions.insertCell(table, cpBean.get(i).getFinyear(), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(cpBean.get(i).getSl_tcond()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(cpBean.get(i).getSl_tperson()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(cpBean.get(i).getDi_tcond()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(cpBean.get(i).getDi_tperson()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(cpBean.get(i).getPi_tcond()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(cpBean.get(i).getPi_tperson()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(cpBean.get(i).getWc_tcond()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(cpBean.get(i).getWc_tperson()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(cpBean.get(i).getTotalcond()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(cpBean.get(i).getTotalperson()), Element.ALIGN_RIGHT, 1, 1, bf8);

					totNum = cpBean.get(i).getSt_code();
					
					sl_tcond=sl_tcond+cpBean.get(i).getSl_tcond();
					sl_tperson=sl_tperson+cpBean.get(i).getSl_tperson();
					di_tcond=di_tcond+cpBean.get(i).getDi_tcond();
					di_tperson=di_tperson+cpBean.get(i).getDi_tperson();
					pi_tcond=pi_tcond+cpBean.get(i).getPi_tcond();
					pi_tperson=pi_tperson+cpBean.get(i).getPi_tperson();
					wc_tcond=wc_tcond+cpBean.get(i).getWc_tcond();
					wc_tperson=wc_tperson+cpBean.get(i).getWc_tperson();
					totalcond=totalcond+cpBean.get(i).getTotalcond();
					totalperson=totalperson+cpBean.get(i).getTotalperson();
				}
			CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 3, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(sl_tcond), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(sl_tperson), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(di_tcond), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(di_tperson), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(pi_tcond), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(pi_tperson), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(wc_tcond), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(wc_tperson), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totalcond), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
			CommonFunctions.insertCell3(table, String.valueOf(totalperson), Element.ALIGN_RIGHT, 1, 1, bf10Bold);



			if(cpBean.size()==0) 
				CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 13, 1, bf8);


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
			response.setHeader("Content-Disposition", "attachment;filename=CB1-Report.pdf");
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

	@RequestMapping(value = "/downloadExcelcapicityBuildingReport", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelcapicityBuildingReport(HttpServletRequest request, HttpServletResponse response) 
	{

		List<CapicityBuildingTrainingBean> list = new ArrayList<CapicityBuildingTrainingBean>();
		
		list=trainingSubject.getcapicityBuildingReport();
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report CB1- State-wise and Year-wise number of Training Conducted and Persons Trained");   
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report CB1- State-wise and Year-wise number of Training Conducted and Persons Trained At SLNA/WCDC/PIA/WC Level";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 12, areaAmtValDetail, workbook);
			
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
	        mergedRegion = new CellRangeAddress(5,5,9,10); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,11,12); 
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,2); 
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
			cell.setCellValue("Financial Year");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("SLNA");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(4).setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("WCDC");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(6).setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("PIA");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(8).setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("WC");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(10).setCellStyle(style);
			
			cell = rowhead.createCell(11);
			cell.setCellValue("Total");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(12).setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("Total no. of Training conducted");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("Total no. of Persons trained");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(5);
			cell.setCellValue("Total no. of Training conducted");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(6);
			cell.setCellValue("Total no. of Persons trained");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(7);
			cell.setCellValue("Total no. of Training conducted");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(8);
			cell.setCellValue("Total no. of Persons trained");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(9);
			cell.setCellValue("Total no. of Training conducted");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(10);
			cell.setCellValue("Total no. of Persons trained");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(11);
			cell.setCellValue("Total no. of Training conducted (4+6+8+10)");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(12);
			cell.setCellValue("Total no. of Persons trained (5+7+9+11)");  
			cell.setCellStyle(style);
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<13;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        int totSLNATrainCond = 0;
	        int totSLNAPerTrain = 0;
	        int totWCDCTrainCond = 0;
	        int totWCDCPerTrain = 0;
	        int totPIATrainCond = 0;
	        int totPIAPerTrain = 0;
	        int totWCTrainCond = 0;
	        int totWCPerTrain = 0;
	        int totTotalTrainCond = 0;
	        int totTotalPerTrain = 0;
	        String StName = "";
	        
	        for(CapicityBuildingTrainingBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	 
	        	if(!StName.equals(bean.getStatename())) {
	        	row.createCell(0).setCellValue(sno);
	        	row.createCell(1).setCellValue(bean.getStatename());
	        	sno++;
	        	}
	        	row.createCell(2).setCellValue(bean.getFinyear());
	        	row.createCell(3).setCellValue(bean.getSl_tcond());
	        	row.createCell(4).setCellValue(bean.getSl_tperson());
	        	row.createCell(5).setCellValue(bean.getDi_tcond());
	        	row.createCell(6).setCellValue(bean.getDi_tperson());
	        	row.createCell(7).setCellValue(bean.getPi_tcond());
	        	row.createCell(8).setCellValue(bean.getPi_tperson());
	        	row.createCell(9).setCellValue(bean.getWc_tcond());
	        	row.createCell(10).setCellValue(bean.getWc_tperson());
	        	row.createCell(11).setCellValue(bean.getTotalcond());
	        	row.createCell(12).setCellValue(bean.getTotalperson());
	        	
	        	totSLNATrainCond = totSLNATrainCond + bean.getSl_tcond();
	        	totSLNAPerTrain = totSLNAPerTrain + bean.getSl_tperson();
	        	totWCDCTrainCond = totWCDCTrainCond + bean.getDi_tcond();
	        	totWCDCPerTrain = totWCDCPerTrain + bean.getDi_tperson();
	        	totPIATrainCond = totPIATrainCond + bean.getPi_tcond();
	        	totPIAPerTrain = totPIAPerTrain + bean.getPi_tperson();
	        	totWCTrainCond = totWCTrainCond + bean.getWc_tcond();
	        	totWCPerTrain = totWCPerTrain + bean.getWc_tperson();
	        	totTotalTrainCond = totTotalTrainCond + bean.getTotalcond();
	        	totTotalPerTrain = totTotalPerTrain + bean.getTotalperson();
				
	        	StName = bean.getStatename();
	        	
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
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(totSLNATrainCond);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(totSLNAPerTrain);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(totWCDCTrainCond);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(totWCDCPerTrain);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(totPIATrainCond);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(totPIAPerTrain);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(totWCTrainCond);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(totWCPerTrain);
	        cell.setCellStyle(style1);
	        cell = row.createCell(11);
	        cell.setCellValue(totTotalTrainCond);
	        cell.setCellStyle(style1);
	        cell = row.createCell(12);
	        cell.setCellValue(totTotalPerTrain);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 12);
	        String fileName = "attachment; filename=Report CB1- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/capicityBuildingReport";
		
	}

}
