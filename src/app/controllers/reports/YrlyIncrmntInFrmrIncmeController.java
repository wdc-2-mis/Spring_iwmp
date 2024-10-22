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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
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

import app.bean.reports.AssetGeoRefBean;
import app.bean.reports.IncrmntOfFrmrIncmBean;
import app.bean.reports.PMKSYBean;
import app.common.CommonFunctions;
import app.model.IwmpMFinYear;
import app.service.StateMasterService;
import app.service.reports.IncrmntOfFrmrIncmService;
import app.service.reports.TargetAchievementQuarterService;

@Controller
public class YrlyIncrmntInFrmrIncmeController {
	
	HttpSession session;
	
	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	public TargetAchievementQuarterService ser;
	
	@Autowired
	IncrmntOfFrmrIncmService frmrIncmSrvc;
	
	
	@RequestMapping(value="/getYrlyIncrmntInFrmrIncm", method = RequestMethod.GET)
	public ModelAndView targetAchievementofIndicators(HttpServletRequest request, HttpServletResponse response)
	{
		
		ModelAndView mav = new ModelAndView();
		
			mav = new ModelAndView("reports/yrlyIncrmntInFrmrIncm");
			
			Map<Integer, String> stList= new LinkedHashMap<>();
			
			List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
			Map<Integer, String> finMap = new LinkedHashMap<>();
			finList.stream().filter(s-> s.getFinYrCd()>22).forEach(s->{
				finMap.put(s.getFinYrCd(), s.getFinYrDesc());
			});
			List<IncrmntOfFrmrIncmBean> listarea = frmrIncmSrvc.getStWiseAreaPercentage();
			listarea.stream().filter(s-> s.getPrcntgarea().compareTo(new BigDecimal(100))>=0).forEach(s->{
				stList.put(s.getStcode(), s.getStname());
			});
			
			mav.addObject("stateList", stList);
			mav.addObject("finMap", finMap);
			mav.addObject("finMapsize",finMap.size());
		
		return mav; 
	}
	
	@RequestMapping(value="/getYrlyIncrmntInFrmrIncm", method = RequestMethod.POST)
	public ModelAndView getYrlyIncrmntInFrmrIncm(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String userState=request.getParameter("state");
		String year= request.getParameter("year");
		mav = new ModelAndView("reports/yrlyIncrmntInFrmrIncm");
		List<IncrmntOfFrmrIncmBean> list = frmrIncmSrvc.getStWiseFrmrIncmDetail(Integer.parseInt(userState),Integer.parseInt(year));
		List<IncrmntOfFrmrIncmBean> listarea = frmrIncmSrvc.getStWiseAreaPercentage();
		Map<String, BigDecimal> areaMap = new LinkedHashMap<>();
		Map<String, BigDecimal> frmrMap = new LinkedHashMap<>();
		Map<Integer, String> stList= new LinkedHashMap<>();
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		String yrName = "";
		for(IwmpMFinYear s :finList) {
			if (s.getFinYrCd() > 22) {
				finMap.put(s.getFinYrCd(), s.getFinYrDesc());
				if (s.getFinYrCd() == Integer.parseInt(year) && Integer.parseInt(year) != 0) {
					yrName = s.getFinYrDesc();
				}else if(Integer.parseInt(year) == 0) {
					yrName = "--All Year--";
				}
			}
		}
		listarea.stream().filter(s-> s.getPrcntgarea().compareTo(new BigDecimal(100))>=0).forEach(s->{
			stList.put(s.getStcode(), s.getStname());
			for(Map.Entry<Integer, String> map :finMap.entrySet()) {
				areaMap.put(s.getStcode()+","+map.getKey(),BigDecimal.ZERO);
				frmrMap.put(s.getStcode()+","+map.getKey(), BigDecimal.ZERO);
			}
		});
		
		List<IncrmntOfFrmrIncmBean> finalList = new ArrayList<>();
		for(IncrmntOfFrmrIncmBean bean : list) {
			for(IncrmntOfFrmrIncmBean obj : listarea) {
				if(obj.getStcode()==bean.getStcode() && obj.getPrcntgarea().compareTo(new BigDecimal(100))>=0) {
					finalList.add(bean);
					areaMap.put(bean.getStcode()+","+(bean.getFinyrcd()-1), bean.getProgcroparea());
					frmrMap.put(bean.getStcode()+","+(bean.getFinyrcd()-1), bean.getProgfrmrincome());
					
				}
			}
		}
		mav.addObject("stateList", stList);
		mav.addObject("finalList",finalList);
		mav.addObject("finalListSize",finalList.size());
		mav.addObject("frmrMap",frmrMap);
		mav.addObject("areaMap",areaMap);
		mav.addObject("finMap",finMap);
		mav.addObject("finMapsize",finMap.size());
		mav.addObject("state", userState);
		mav.addObject("year", year);
		mav.addObject("yrName",yrName);
		
		return mav;
		
	}
	
	@RequestMapping(value="/getDistWiseYrlyIncrmntInFrmrIncm", method = RequestMethod.GET)
	public ModelAndView getDistWiseYrlyIncrmntInFrmrIncm(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String stname = request.getParameter("stname");
		String yrName = request.getParameter("yrName");
		String userState=request.getParameter("state");
		String year= request.getParameter("year");
		mav = new ModelAndView("reports/distWiseYrlyIncrmntInFrmrIncm");
		List<IncrmntOfFrmrIncmBean> list = frmrIncmSrvc.getDistWiseFrmrIncmDetail(Integer.parseInt(userState),Integer.parseInt(year));
		Map<String, BigDecimal> areaMap = new LinkedHashMap<>();
		Map<String, BigDecimal> frmrMap = new LinkedHashMap<>();
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>22).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		
		for (IncrmntOfFrmrIncmBean bean : list) {
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				areaMap.put(bean.getDcode() + "," + map.getKey(), BigDecimal.ZERO);
				frmrMap.put(bean.getDcode() + "," + map.getKey(), BigDecimal.ZERO);
			}
		}
		
		for(IncrmntOfFrmrIncmBean bean : list) {
						areaMap.put(bean.getDcode()+","+(bean.getFinyrcd()-1), bean.getProgcroparea());
						frmrMap.put(bean.getDcode()+","+(bean.getFinyrcd()-1), bean.getProgfrmrincome());
		}
		mav.addObject("finalList",list);
		mav.addObject("finalListSize",list.size());
		mav.addObject("frmrMap",frmrMap);
		mav.addObject("areaMap",areaMap);
		mav.addObject("finMap",finMap);
		mav.addObject("finMapsize",finMap.size());
		mav.addObject("state", userState);
		mav.addObject("year", year);
		mav.addObject("stName",stname);
		mav.addObject("yrName",yrName);
		
		return mav;
		
	}
	
	@RequestMapping(value="/getProjWiseYrlyIncrmntInFrmrIncm", method = RequestMethod.GET)
	public ModelAndView getProjWiseYrlyIncrmntInFrmrIncm(HttpServletRequest request, HttpServletResponse response)
	{
		
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
		String yrName = request.getParameter("yrName");
		String dcode=request.getParameter("dcode");
		String year= request.getParameter("year");
		mav = new ModelAndView("reports/projWiseYrlyIncrmntInFrmrIncm");
		List<IncrmntOfFrmrIncmBean> list = frmrIncmSrvc.getProjWiseFrmrIncmDetail(Integer.parseInt(dcode),Integer.parseInt(year));
		Map<String, BigDecimal> areaMap = new LinkedHashMap<>();
		Map<String, BigDecimal> frmrMap = new LinkedHashMap<>();
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>22).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		
		for (IncrmntOfFrmrIncmBean bean : list) {
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				areaMap.put(bean.getProjid() + "," + map.getKey(), BigDecimal.ZERO);
				frmrMap.put(bean.getProjid() + "," + map.getKey(), BigDecimal.ZERO);
			}
		}
		
		List<IncrmntOfFrmrIncmBean> finalList = new ArrayList<>();
		for (IncrmntOfFrmrIncmBean bean : list) {
			finalList.add(bean);
			areaMap.put(bean.getProjid() + "," + (bean.getFinyrcd() - 1), bean.getProgcroparea());
			frmrMap.put(bean.getProjid() + "," + (bean.getFinyrcd() - 1), bean.getProgfrmrincome());

		}
		mav.addObject("finalList",list);
		mav.addObject("finalListSize",list.size());
		mav.addObject("frmrMap",frmrMap);
		mav.addObject("areaMap",areaMap);
		mav.addObject("finMap",finMap);
		mav.addObject("finMapsize",finMap.size());
		mav.addObject("dcode", dcode);
		mav.addObject("year", year);
		mav.addObject("stName",stName);
		mav.addObject("distName",distName);
		mav.addObject("yrName",yrName);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/getStWiseFrmrIncmExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getStWiseFrmrIncmExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		session = request.getSession(true);
		String userState=request.getParameter("state");
		String year= request.getParameter("year");
		List<IncrmntOfFrmrIncmBean> list = frmrIncmSrvc.getStWiseFrmrIncmDetail(Integer.parseInt(userState),Integer.parseInt(year));
		List<IncrmntOfFrmrIncmBean> listarea = frmrIncmSrvc.getStWiseAreaPercentage();
		Map<String, BigDecimal> areaMap = new LinkedHashMap<>();
		Map<String, BigDecimal> frmrMap = new LinkedHashMap<>();
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>22).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		listarea.stream().filter(s-> s.getPrcntgarea().compareTo(new BigDecimal(100))>=0).forEach(s->{
			for(Map.Entry<Integer, String> map :finMap.entrySet()) {
				areaMap.put(s.getStcode()+","+map.getKey(),BigDecimal.ZERO);
				frmrMap.put(s.getStcode()+","+map.getKey(), BigDecimal.ZERO);
			}
		});
		List<IncrmntOfFrmrIncmBean> finalList = new ArrayList<>();
		for(IncrmntOfFrmrIncmBean bean : list) {
			for(IncrmntOfFrmrIncmBean obj : listarea) {
				if(obj.getStcode()==bean.getStcode() && obj.getPrcntgarea().compareTo(new BigDecimal(100))>=0) {
					finalList.add(bean);
					areaMap.put(bean.getStcode()+","+(bean.getFinyrcd()-1), bean.getProgcroparea());
					frmrMap.put(bean.getStcode()+","+(bean.getFinyrcd()-1), bean.getProgfrmrincome());
					
				}
			}
		}
		
		
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report O12 - State and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).");   
		
		
		CellStyle style = CommonFunctions.getStyle(workbook);
        
		String rptName = "Report O12 - State and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).";
		String areaAmtValDetail = "All Area in Ha.";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, finMap.size()*2+1, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(5,6,0,0);
        sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,1,1);
        sheet.addMergedRegion(mergedRegion);
		if (finMap.size() > 1) {
			mergedRegion = new CellRangeAddress(5, 5, 2, finMap.size() + 1);
			sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5, 5, finMap.size() + 2, 2 * finMap.size() + 1);
			sheet.addMergedRegion(mergedRegion);
		}
        	
        
        Row rowhead = sheet.createRow(5); 
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		cell = rowhead.createCell(2);
		cell.setCellValue("Change in Crop Area Year wise");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		if (finMap.size() > 1) {
			for (int i = 3; i < finMap.size() + 2; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
		}
		cell = rowhead.createCell(finMap.size()+2);
		cell.setCellValue("Change in Farmer Income Year wise(in %)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		if (finMap.size() > 1) {
			for (int i = finMap.size()+3; i < 2*finMap.size()+2; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
		}
		
        
		Row rowhead1 = sheet.createRow(6);
		
			rowhead1.createCell(0).setCellStyle(style);
			rowhead1.createCell(1).setCellStyle(style);
		int no = 2;
		for (int i = 0; i < 2; i++) {
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				no++;
			}
		}
		
		
		Row rowhead2 = sheet.createRow(7);
		for(int i =0; i<2*finMap.size()+2;i++) {
			cell = rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		}
		
		
		int rowno = 8;
		int count  =1;
		for(IncrmntOfFrmrIncmBean bean : finalList) {
			Row row1 = sheet.createRow(rowno);
			cell = row1.createCell(0);
			cell.setCellValue(count);
			
			cell = row1.createCell(1);
			cell.setCellValue(bean.getStname());
			int colno = 2;
			for(Map.Entry<String, BigDecimal> map : areaMap.entrySet()) {
				if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(bean.getStcode().toString())) {
					cell = row1.createCell(colno);
					cell.setCellValue(map.getValue().doubleValue());
					colno ++;
				}
			}
			
			for(Map.Entry<String, BigDecimal> map : frmrMap.entrySet()) {
				if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(bean.getStcode().toString())) {
					cell = row1.createCell(colno);
					cell.setCellValue(map.getValue().doubleValue());
					colno++;
				}
			}
			
			count++;
			rowno++;
			
		}
		
        
        
        CommonFunctions.getExcelFooter(sheet, mergedRegion, finalList.size(), 2*finMap.size()+1);
        String fileName = "attachment; filename=Report O12- State.xlsx";
        
        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/yrlyIncrmntInFrmrIncm";
	}
	
	@RequestMapping(value = "/getDistWiseFrmrIncmExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getDistWiseFrmrIncmExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		session = request.getSession(true);
		String stName = request.getParameter("stName");
		String yrName = request.getParameter("yrName");
		String userState=request.getParameter("state");
		String year= request.getParameter("year");
		List<IncrmntOfFrmrIncmBean> list = frmrIncmSrvc.getDistWiseFrmrIncmDetail(Integer.parseInt(userState),Integer.parseInt(year));
		Map<String, BigDecimal> areaMap = new LinkedHashMap<>();
		Map<String, BigDecimal> frmrMap = new LinkedHashMap<>();
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>22).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		
		for (IncrmntOfFrmrIncmBean bean : list) {
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				areaMap.put(bean.getDcode() + "," + map.getKey(), BigDecimal.ZERO);
				frmrMap.put(bean.getDcode() + "," + map.getKey(), BigDecimal.ZERO);
			}
		}
		
		List<IncrmntOfFrmrIncmBean> finalList = new ArrayList<>();
		for (IncrmntOfFrmrIncmBean bean : list) {
			finalList.add(bean);
			areaMap.put(bean.getDcode() + "," + (bean.getFinyrcd() - 1), bean.getProgcroparea());
			frmrMap.put(bean.getDcode() + "," + (bean.getFinyrcd() - 1), bean.getProgfrmrincome());

		}
		
		
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report O12 - District and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).");   
		
		
		CellStyle style = CommonFunctions.getStyle(workbook);
        
		String rptName = "Report O12 - District and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).";
		String areaAmtValDetail = "";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, finMap.size()*2+1, areaAmtValDetail, workbook);
		
		
		mergedRegion = new CellRangeAddress(5,5,0,1);
        sheet.addMergedRegion(mergedRegion);
        mergedRegion = new CellRangeAddress(5,5,2, 2 * finMap.size() + 1);
        sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,0,0);
        sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,1,1);
        sheet.addMergedRegion(mergedRegion);
		if (finMap.size() > 1) {
			mergedRegion = new CellRangeAddress(6, 6, 2, finMap.size() + 1);
			sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6, 6, finMap.size() + 2, 2 * finMap.size() + 1);
			sheet.addMergedRegion(mergedRegion);
		}
        	
		Row rowhead = sheet.createRow(5); 
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("State: "+stName+" , Financial Year: "+ yrName);
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
		
		rowhead.createCell(1).setCellStyle(style);
		cell = rowhead.createCell(2);
		cell.setCellValue("All Area in Ha.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		for(int i =3; i < 2*finMap.size() + 2; i++) {
			rowhead.createCell(i).setCellStyle(style);
		}
        
        rowhead = sheet.createRow(6); 
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		cell = rowhead.createCell(2);
		cell.setCellValue("Change in Crop Area Year wise");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		if (finMap.size() > 1) {
			for (int i = 3; i < finMap.size() + 2; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
		}
		cell = rowhead.createCell(finMap.size()+2);
		cell.setCellValue("Change in Farmer Income Year wise(in %)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		if (finMap.size() > 1) {
			for (int i = finMap.size()+3; i < 2*finMap.size()+2; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
		}
		
        
		Row rowhead1 = sheet.createRow(7);
		
			rowhead1.createCell(0).setCellStyle(style);
			rowhead1.createCell(1).setCellStyle(style);
		int no = 2;
		for (int i = 0; i < 2; i++) {
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				no++;
			}
		}
		
		
		Row rowhead2 = sheet.createRow(8);
		for(int i =0; i<2*finMap.size()+2;i++) {
			cell = rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		}
		
		
		int rowno = 9;
		int count  =1;
		for(IncrmntOfFrmrIncmBean bean : finalList) {
			Row row1 = sheet.createRow(rowno);
			cell = row1.createCell(0);
			cell.setCellValue(count);
			
			cell = row1.createCell(1);
			cell.setCellValue(bean.getDistname());
			int colno = 2;
			for(Map.Entry<String, BigDecimal> map : areaMap.entrySet()) {
				if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(bean.getDcode().toString())) {
					cell = row1.createCell(colno);
					cell.setCellValue(map.getValue().doubleValue());
					colno ++;
				}
			}
			
			for(Map.Entry<String, BigDecimal> map : frmrMap.entrySet()) {
				if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(bean.getDcode().toString())) {
					cell = row1.createCell(colno);
					cell.setCellValue(map.getValue().doubleValue());
					colno++;
				}
			}
			
			count++;
			rowno++;
			
		}
		
        
        
        CommonFunctions.getExcelFooter(sheet, mergedRegion, finalList.size(), 2*finMap.size()+1);
        String fileName = "attachment; filename=Report O12- District.xlsx";
        
        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/distWiseYrlyIncrmntInFrmrIncm";
	}
	
	@RequestMapping(value = "/getProjWiseFrmrIncmExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getProjWiseFrmrIncmExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		session = request.getSession(true);
		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
		String yrName = request.getParameter("yrName");
		String dcode=request.getParameter("dcode");
		String year= request.getParameter("year");
		List<IncrmntOfFrmrIncmBean> list = frmrIncmSrvc.getProjWiseFrmrIncmDetail(Integer.parseInt(dcode),Integer.parseInt(year));
		Map<String, BigDecimal> areaMap = new LinkedHashMap<>();
		Map<String, BigDecimal> frmrMap = new LinkedHashMap<>();
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>22).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		
		for (IncrmntOfFrmrIncmBean bean : list) {
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				areaMap.put(bean.getProjid() + "," + map.getKey(), BigDecimal.ZERO);
				frmrMap.put(bean.getProjid() + "," + map.getKey(), BigDecimal.ZERO);
			}
		}
		
		List<IncrmntOfFrmrIncmBean> finalList = new ArrayList<>();
		for (IncrmntOfFrmrIncmBean bean : list) {
			finalList.add(bean);
			areaMap.put(bean.getProjid() + "," + (bean.getFinyrcd() - 1), bean.getProgcroparea());
			frmrMap.put(bean.getProjid() + "," + (bean.getFinyrcd() - 1), bean.getProgfrmrincome());

		}
		
		
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report O12 - Project and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).");   
		
		
		CellStyle style = CommonFunctions.getStyle(workbook);
        
		String rptName = "Report O12 - Project and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).";
		String areaAmtValDetail = "";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, finMap.size()*2+1, areaAmtValDetail, workbook);
		
		
		mergedRegion = new CellRangeAddress(5,5,0,1);
        sheet.addMergedRegion(mergedRegion);
        mergedRegion = new CellRangeAddress(5,5,2, 2 * finMap.size() + 1);
        sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,0,0);
        sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,1,1);
        sheet.addMergedRegion(mergedRegion);
		if (finMap.size() > 1) {
			mergedRegion = new CellRangeAddress(6, 6, 2, finMap.size() + 1);
			sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(6, 6, finMap.size() + 2, 2 * finMap.size() + 1);
			sheet.addMergedRegion(mergedRegion);
		}
        	
		Row rowhead = sheet.createRow(5); 
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("State: "+stName+", District: "+distName+", Financial Year: "+ yrName);
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
		
		rowhead.createCell(1).setCellStyle(style);
		cell = rowhead.createCell(2);
		cell.setCellValue("All Area in Ha.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		for(int i =3; i < 2*finMap.size() + 2; i++) {
			rowhead.createCell(i).setCellStyle(style);
		}
        
        rowhead = sheet.createRow(6); 
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		cell = rowhead.createCell(1);
		cell.setCellValue("Project Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		cell = rowhead.createCell(2);
		cell.setCellValue("Change in Crop Area Year wise");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		if (finMap.size() > 1) {
			for (int i = 3; i < finMap.size() + 2; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
		}
		cell = rowhead.createCell(finMap.size()+2);
		cell.setCellValue("Change in Farmer Income Year wise(in %)");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		if (finMap.size() > 1) {
			for (int i = finMap.size()+3; i < 2*finMap.size()+2; i++) {
				rowhead.createCell(i).setCellStyle(style);
			}
		}
		
        
		Row rowhead1 = sheet.createRow(7);
		
			rowhead1.createCell(0).setCellStyle(style);
			rowhead1.createCell(1).setCellStyle(style);
		int no = 2;
		for (int i = 0; i < 2; i++) {
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				cell = rowhead1.createCell(no);
				cell.setCellValue(map.getValue());
				cell.setCellStyle(style);
				CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
				no++;
			}
		}
		
		
		Row rowhead2 = sheet.createRow(8);
		for(int i =0; i<2*finMap.size()+2;i++) {
			cell = rowhead2.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		}
		
		
		int rowno = 9;
		int count  =1;
		for(IncrmntOfFrmrIncmBean bean : finalList) {
			Row row1 = sheet.createRow(rowno);
			cell = row1.createCell(0);
			cell.setCellValue(count);
			
			cell = row1.createCell(1);
			cell.setCellValue(bean.getProjname());
			int colno = 2;
			for(Map.Entry<String, BigDecimal> map : areaMap.entrySet()) {
				if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(bean.getProjid().toString())) {
					cell = row1.createCell(colno);
					cell.setCellValue(map.getValue().doubleValue());
					colno ++;
				}
			}
			
			for(Map.Entry<String, BigDecimal> map : frmrMap.entrySet()) {
				if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(bean.getProjid().toString())) {
					cell = row1.createCell(colno);
					cell.setCellValue(map.getValue().doubleValue());
					colno++;
				}
			}
			
			count++;
			rowno++;
			
		}
		
        
        
        CommonFunctions.getExcelFooter(sheet, mergedRegion, finalList.size(), 2*finMap.size()+1);
        String fileName = "attachment; filename=Report O12- Project.xlsx";
        
        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/projWiseYrlyIncrmntInFrmrIncm";
	}
	
	
	@RequestMapping(value = "/getStWiseFrmrIncmPDF", method = RequestMethod.POST)
	public ModelAndView getStWiseFrmrIncmPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		session = request.getSession(true);
		String userState=request.getParameter("state");
		String year= request.getParameter("year");
		List<IncrmntOfFrmrIncmBean> list = frmrIncmSrvc.getStWiseFrmrIncmDetail(Integer.parseInt(userState),Integer.parseInt(year));
		List<IncrmntOfFrmrIncmBean> listarea = frmrIncmSrvc.getStWiseAreaPercentage();
		Map<String, BigDecimal> areaMap = new LinkedHashMap<>();
		Map<String, BigDecimal> frmrMap = new LinkedHashMap<>();
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>22).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		
		listarea.stream().filter(s-> s.getPrcntgarea().compareTo(new BigDecimal(100))>=0).forEach(s->{
			for(Map.Entry<Integer, String> map :finMap.entrySet()) {
				areaMap.put(s.getStcode()+","+map.getKey(),BigDecimal.ZERO);
				frmrMap.put(s.getStcode()+","+map.getKey(), BigDecimal.ZERO);
			}
		});
		
		List<IncrmntOfFrmrIncmBean> finalList = new ArrayList<>();
		for(IncrmntOfFrmrIncmBean bean : list) {
			for(IncrmntOfFrmrIncmBean obj : listarea) {
				if(obj.getStcode()==bean.getStcode() && obj.getPrcntgarea().compareTo(new BigDecimal(100))>=0) {
					finalList.add(bean);
					areaMap.put(bean.getStcode()+","+(bean.getFinyrcd()-1), bean.getProgcroparea());
					frmrMap.put(bean.getStcode()+","+(bean.getFinyrcd()-1), bean.getProgfrmrincome());
					
				}
			}
		}
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("StateWiseFarmerIncomeDetail");
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
			
				paragraph3 = new Paragraph("Report O12 - State and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(2*finMap.size() + 2);
		        int arr[] = new int[2*finMap.size() + 2];
		        arr[0] = 3;
		        arr[1] = 8;
		        for(int i = 2; i<2*finMap.size() + 2;i++) {
		        	arr[i] =5;
		        }
		        table.setWidths(arr);
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
							
		        CommonFunctions.insertCellHeader(table, "All Area in Ha.", Element.ALIGN_RIGHT, 2*finMap.size() + 2, 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Change in Crop Area Year wise", Element.ALIGN_CENTER, finMap.size(), 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Change in Farmer Income Year wise(in %)", Element.ALIGN_CENTER, finMap.size(), 1, bf8Bold);
				for (int i = 0; i < 2; i++) {
					for (Map.Entry<Integer, String> map : finMap.entrySet()) {
						CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
					}
				}
				for(int i =0; i<2*finMap.size()+2;i++) {
					CommonFunctions.insertCellHeader(table, String.valueOf(i+1), Element.ALIGN_CENTER, 1, 1, bf8Bold);
				}
				
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
				
				if(finalList.size()!=0)
					for(int i=0;i<finalList.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						for(Map.Entry<String, BigDecimal> map : areaMap.entrySet()) {
							if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(finalList.get(i).getStcode().toString())) {
								CommonFunctions.insertCell(table, String.valueOf(map.getValue()), Element.ALIGN_RIGHT, 1, 1, bf8);
							}
						}
						for(Map.Entry<String, BigDecimal> map : frmrMap.entrySet()) {
							if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(finalList.get(i).getStcode().toString())) {
								CommonFunctions.insertCell(table, String.valueOf(map.getValue()), Element.ALIGN_RIGHT, 1, 1, bf8);
							}
						}
						
						
						
						
//						totCreated_N = totCreated_N + list.get(i).getN_created();
//						totUnStarted_N = totUnStarted_N + list.get(i).getN_unstarted();
//						totOngoing_N = totOngoing_N + list.get(i).getN_ongoing();
//						totCompleted_N = totCompleted_N + list.get(i).getN_completed();
//						
//						totCreated_E = totCreated_E + list.get(i).getE_created();
//						totUnStarted_E = totUnStarted_E + list.get(i).getE_unstarted();
//						totOngoing_E = totOngoing_E + list.get(i).getE_ongoing();
//						totCompleted_E = totCompleted_E + list.get(i).getE_completed();
//						
//						totCreated_L = totCreated_L + list.get(i).getL_created();
//						totUnStarted_L = totUnStarted_L + list.get(i).getL_unstarted();
//						totOngoing_L = totOngoing_L + list.get(i).getL_ongoing();
//						totCompleted_L = totCompleted_L + list.get(i).getL_completed();
//						
//						totCreated_P = totCreated_P + list.get(i).getP_created();
//						totUnStarted_P = totUnStarted_P + list.get(i).getP_unstarted();
//						totOngoing_P = totOngoing_P + list.get(i).getP_ongoing();
//						totCompleted_P = totCompleted_P + list.get(i).getP_completed();
//						
//						totproj=totproj+list.get(i).getTotproj();
//						totworkcode=totworkcode.add(list.get(i).getTotworkcode());
//						totgeorefwrkcd = totgeorefwrkcd+list.get(i).getTotgeorefwrkcd();
//						totnongeorefwrkcd = totnongeorefwrkcd.add(list.get(i).getTotnongeorefwrkcd());
						
						k++;
					}
					
				
//				CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totworkcode), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totgeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totnongeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            
				
					if(finalList.size()==0) 
						CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, finMap.size()*2+2, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report OT12- State.pdf");
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
	
	
	
	
	@RequestMapping(value = "/getDistWiseFrmrIncmPDF", method = RequestMethod.POST)
	public ModelAndView getDistWiseFrmrIncmPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		session = request.getSession(true);
		String stName = request.getParameter("stName");
		String yrName = request.getParameter("yrName");
		String userState=request.getParameter("state");
		String year= request.getParameter("year");
		List<IncrmntOfFrmrIncmBean> list = frmrIncmSrvc.getDistWiseFrmrIncmDetail(Integer.parseInt(userState),Integer.parseInt(year));
		Map<String, BigDecimal> areaMap = new LinkedHashMap<>();
		Map<String, BigDecimal> frmrMap = new LinkedHashMap<>();
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>22).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		
		for (IncrmntOfFrmrIncmBean bean : list) {
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				areaMap.put(bean.getDcode() + "," + map.getKey(), BigDecimal.ZERO);
				frmrMap.put(bean.getDcode() + "," + map.getKey(), BigDecimal.ZERO);
			}
		}
		
		List<IncrmntOfFrmrIncmBean> finalList = new ArrayList<>();
		for (IncrmntOfFrmrIncmBean bean : list) {
			finalList.add(bean);
			areaMap.put(bean.getDcode() + "," + (bean.getFinyrcd() - 1), bean.getProgcroparea());
			frmrMap.put(bean.getDcode() + "," + (bean.getFinyrcd() - 1), bean.getProgfrmrincome());

		}
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("DistrictWiseFarmerIncomeDetail");
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
			
				paragraph3 = new Paragraph("Report O12 - District and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(2*finMap.size() + 2);
		        int arr[] = new int[2*finMap.size() + 2];
		        arr[0] = 3;
		        arr[1] = 8;
		        for(int i = 2; i<2*finMap.size() + 2;i++) {
		        	arr[i] =5;
		        }
		        table.setWidths(arr);
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
				
		        
		        CommonFunctions.insertCellHeader(table, "State: "+stName+", "+"Financial Year: "+yrName, Element.ALIGN_LEFT, 2, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "All Area in Ha.", Element.ALIGN_RIGHT, 2*finMap.size(), 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Change in Crop Area Year wise", Element.ALIGN_CENTER, finMap.size(), 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Change in Farmer Income Year wise(in %)", Element.ALIGN_CENTER, finMap.size(), 1, bf8Bold);
				for (int i = 0; i < 2; i++) {
					for (Map.Entry<Integer, String> map : finMap.entrySet()) {
						CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
					}
				}
				for(int i =0; i<2*finMap.size()+2;i++) {
					CommonFunctions.insertCellHeader(table, String.valueOf(i+1), Element.ALIGN_CENTER, 1, 1, bf8Bold);
				}
				
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
				
				if(finalList.size()!=0)
					for(int i=0;i<finalList.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						for(Map.Entry<String, BigDecimal> map : areaMap.entrySet()) {
							if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(finalList.get(i).getDcode().toString())) {
								CommonFunctions.insertCell(table, String.valueOf(map.getValue()), Element.ALIGN_RIGHT, 1, 1, bf8);
							}
						}
						for(Map.Entry<String, BigDecimal> map : frmrMap.entrySet()) {
							if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(finalList.get(i).getDcode().toString())) {
								CommonFunctions.insertCell(table, String.valueOf(map.getValue()), Element.ALIGN_RIGHT, 1, 1, bf8);
							}
						}
						
						
						
						
//						totCreated_N = totCreated_N + list.get(i).getN_created();
//						totUnStarted_N = totUnStarted_N + list.get(i).getN_unstarted();
//						totOngoing_N = totOngoing_N + list.get(i).getN_ongoing();
//						totCompleted_N = totCompleted_N + list.get(i).getN_completed();
//						
//						totCreated_E = totCreated_E + list.get(i).getE_created();
//						totUnStarted_E = totUnStarted_E + list.get(i).getE_unstarted();
//						totOngoing_E = totOngoing_E + list.get(i).getE_ongoing();
//						totCompleted_E = totCompleted_E + list.get(i).getE_completed();
//						
//						totCreated_L = totCreated_L + list.get(i).getL_created();
//						totUnStarted_L = totUnStarted_L + list.get(i).getL_unstarted();
//						totOngoing_L = totOngoing_L + list.get(i).getL_ongoing();
//						totCompleted_L = totCompleted_L + list.get(i).getL_completed();
//						
//						totCreated_P = totCreated_P + list.get(i).getP_created();
//						totUnStarted_P = totUnStarted_P + list.get(i).getP_unstarted();
//						totOngoing_P = totOngoing_P + list.get(i).getP_ongoing();
//						totCompleted_P = totCompleted_P + list.get(i).getP_completed();
//						
//						totproj=totproj+list.get(i).getTotproj();
//						totworkcode=totworkcode.add(list.get(i).getTotworkcode());
//						totgeorefwrkcd = totgeorefwrkcd+list.get(i).getTotgeorefwrkcd();
//						totnongeorefwrkcd = totnongeorefwrkcd.add(list.get(i).getTotnongeorefwrkcd());
						
						k++;
					}
					
				
//				CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totworkcode), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totgeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totnongeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            
				
					if(finalList.size()==0) 
						CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, finMap.size()*2+2, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report OT12- District.pdf");
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
	
	
	@RequestMapping(value = "/getProjWiseFrmrIncmPDF", method = RequestMethod.POST)
	public ModelAndView getProjWiseFrmrIncmPDF(HttpServletRequest request, HttpServletResponse response) 
	{
		session = request.getSession(true);
		String stName = request.getParameter("stName");
		String distName = request.getParameter("distName");
		String yrName = request.getParameter("yrName");
		String dcode=request.getParameter("dcode");
		String year= request.getParameter("year");
		List<IncrmntOfFrmrIncmBean> list = frmrIncmSrvc.getProjWiseFrmrIncmDetail(Integer.parseInt(dcode),Integer.parseInt(year));
		Map<String, BigDecimal> areaMap = new LinkedHashMap<>();
		Map<String, BigDecimal> frmrMap = new LinkedHashMap<>();
		List<IwmpMFinYear> finList =  ser.getFinancialYearonward21();
		Map<Integer, String> finMap = new LinkedHashMap<>();
		finList.stream().filter(s-> s.getFinYrCd()>22).forEach(s->{
			finMap.put(s.getFinYrCd(), s.getFinYrDesc());
		});
		
		for (IncrmntOfFrmrIncmBean bean : list) {
			for (Map.Entry<Integer, String> map : finMap.entrySet()) {
				areaMap.put(bean.getProjid() + "," + map.getKey(), BigDecimal.ZERO);
				frmrMap.put(bean.getProjid() + "," + map.getKey(), BigDecimal.ZERO);
			}
		}
		
		List<IncrmntOfFrmrIncmBean> finalList = new ArrayList<>();
		for (IncrmntOfFrmrIncmBean bean : list) {
			finalList.add(bean);
			areaMap.put(bean.getProjid() + "," + (bean.getFinyrcd() - 1), bean.getProgcroparea());
			frmrMap.put(bean.getProjid() + "," + (bean.getFinyrcd() - 1), bean.getProgfrmrincome());

		}
		
		try {
			
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 10, 10, 0);
			document.addTitle("ProjectWiseFarmerIncomeDetail");
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
			
				paragraph3 = new Paragraph("Report O12 - Project and Financial Year wise Achievements on Farmer Income and Crop Area(OOMF-2).", f3);
				
				paragraph2.setAlignment(Element.ALIGN_CENTER);
		        paragraph3.setAlignment(Element.ALIGN_CENTER);
		        paragraph2.setSpacingAfter(10);
		        paragraph3.setSpacingAfter(10);
		        CommonFunctions.addHeader(document);
		        document.add(paragraph2);
		        document.add(paragraph3);
		        table = new PdfPTable(2*finMap.size() + 2);
		        int arr[] = new int[2*finMap.size() + 2];
		        arr[0] = 3;
		        arr[1] = 8;
		        for(int i = 2; i<2*finMap.size() + 2;i++) {
		        	arr[i] =5;
		        }
		        table.setWidths(arr);
		        table.setWidthPercentage(100);
		        table.setSpacingBefore(0f);
		        table.setSpacingAfter(0f);
				
		        
		        CommonFunctions.insertCellHeader(table, "State: "+stName+", "+"District: "+distName+", "+"Financial Year: "+yrName, Element.ALIGN_LEFT, 2, 1, bf8Bold);
		        CommonFunctions.insertCellHeader(table, "All Area in Ha.", Element.ALIGN_RIGHT, 2*finMap.size(), 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Project Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
				
				CommonFunctions.insertCellHeader(table, "Change in Crop Area Year wise", Element.ALIGN_CENTER, finMap.size(), 1, bf8Bold);
				CommonFunctions.insertCellHeader(table, "Change in Farmer Income Year wise(in %)", Element.ALIGN_CENTER, finMap.size(), 1, bf8Bold);
				for (int i = 0; i < 2; i++) {
					for (Map.Entry<Integer, String> map : finMap.entrySet()) {
						CommonFunctions.insertCellHeader(table, map.getValue(), Element.ALIGN_CENTER, 1, 1, bf8Bold);
					}
				}
				for(int i =0; i<2*finMap.size()+2;i++) {
					CommonFunctions.insertCellHeader(table, String.valueOf(i+1), Element.ALIGN_CENTER, 1, 1, bf8Bold);
				}
				
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
				
				if(finalList.size()!=0)
					for(int i=0;i<finalList.size();i++) 
					{
						CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
						CommonFunctions.insertCell(table, list.get(i).getProjname(), Element.ALIGN_LEFT, 1, 1, bf8);
						
						for(Map.Entry<String, BigDecimal> map : areaMap.entrySet()) {
							if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(finalList.get(i).getProjid().toString())) {
								CommonFunctions.insertCell(table, String.valueOf(map.getValue()), Element.ALIGN_RIGHT, 1, 1, bf8);
							}
						}
						for(Map.Entry<String, BigDecimal> map : frmrMap.entrySet()) {
							if(map.getKey().substring(0, map.getKey().indexOf(",")).equals(finalList.get(i).getProjid().toString())) {
								CommonFunctions.insertCell(table, String.valueOf(map.getValue()), Element.ALIGN_RIGHT, 1, 1, bf8);
							}
						}
						
						
						
						
//						totCreated_N = totCreated_N + list.get(i).getN_created();
//						totUnStarted_N = totUnStarted_N + list.get(i).getN_unstarted();
//						totOngoing_N = totOngoing_N + list.get(i).getN_ongoing();
//						totCompleted_N = totCompleted_N + list.get(i).getN_completed();
//						
//						totCreated_E = totCreated_E + list.get(i).getE_created();
//						totUnStarted_E = totUnStarted_E + list.get(i).getE_unstarted();
//						totOngoing_E = totOngoing_E + list.get(i).getE_ongoing();
//						totCompleted_E = totCompleted_E + list.get(i).getE_completed();
//						
//						totCreated_L = totCreated_L + list.get(i).getL_created();
//						totUnStarted_L = totUnStarted_L + list.get(i).getL_unstarted();
//						totOngoing_L = totOngoing_L + list.get(i).getL_ongoing();
//						totCompleted_L = totCompleted_L + list.get(i).getL_completed();
//						
//						totCreated_P = totCreated_P + list.get(i).getP_created();
//						totUnStarted_P = totUnStarted_P + list.get(i).getP_unstarted();
//						totOngoing_P = totOngoing_P + list.get(i).getP_ongoing();
//						totCompleted_P = totCompleted_P + list.get(i).getP_completed();
//						
//						totproj=totproj+list.get(i).getTotproj();
//						totworkcode=totworkcode.add(list.get(i).getTotworkcode());
//						totgeorefwrkcd = totgeorefwrkcd+list.get(i).getTotgeorefwrkcd();
//						totnongeorefwrkcd = totnongeorefwrkcd.add(list.get(i).getTotnongeorefwrkcd());
						
						k++;
					}
					
				
//				CommonFunctions.insertCell3(table, "Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_N), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_E), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_L), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totCreated_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totUnStarted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totOngoing_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totCompleted_P), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            
//	            CommonFunctions.insertCell3(table, String.valueOf(totworkcode), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totgeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
//	            CommonFunctions.insertCell3(table, String.valueOf(totnongeorefwrkcd), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            
				
					if(finalList.size()==0) 
						CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, finMap.size()*2+2, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report OT12- Project.pdf");
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
