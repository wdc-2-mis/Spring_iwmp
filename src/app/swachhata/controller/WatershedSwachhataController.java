package app.swachhata.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.Workbook;
import app.bean.Login;
import app.bean.ProfileBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.WatershedMahotsavProjectLevelBean;
import app.service.ProfileService;
import app.service.outcome.BaseLineOutcomeService;
import app.swachhata.service.WatershedSwachhataService;

@Controller
public class WatershedSwachhataController {
	
	
	HttpSession session;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired(required = true)
	MenuController menuController;
	
	@Autowired
	CommonFunctions commonFunction;
	
	@Autowired(required = true)
	BaseLineOutcomeService baseLineOutcomeService;
	
	@Autowired
	WatershedSwachhataService serv;
	
	@RequestMapping(value = "/getWatershedSwachhataAtProj", method = RequestMethod.GET)
	public ModelAndView getWatershedSwachhataAtProj(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedSwachhataBean> list = new ArrayList<WatershedSwachhataBean>();
		List<WatershedSwachhataBean> dlist = new ArrayList<WatershedSwachhataBean>();
		List<WatershedSwachhataBean> comlist = new ArrayList<WatershedSwachhataBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("mahotsav/watershedSwachhataAtProject");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				String loginId = session.getAttribute("loginID").toString();
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				listm=profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				for(ProfileBean bean : listm) {
					distName =bean.getDistrictname();
					distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
				}
				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
				mav.addObject("projectList",baseLineOutcomeService.getProjectByRegId(regId));
				
				list = serv.getWatershedSwachhataAtProj(regId.toString());
				for(WatershedSwachhataBean bean :list) 
				{
					if(bean.getStatus().equals('D')) {
						dlist.add(bean);
					}
					else {
						comlist.add(bean);
					}
				}
				mav.addObject("dataList",dlist);
				mav.addObject("dataListSize",dlist.size());
				
				mav.addObject("compdataList",comlist);
				mav.addObject("compdataListSize",comlist.size()); 
				

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getVillageListFrmProjBlk", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getVillageListFrmProjBlk(HttpServletRequest request, 
			@RequestParam("projid") int projid, @RequestParam("bokckid") int bokckid) {
		session = request.getSession(true);
		return serv.getVillagebyProjIdBlock(projid, bokckid);
	}
	
	@RequestMapping(value="/checkWatershedSwachhataVillageExits", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkWatershedSwachhataVillageExits(HttpServletRequest request, HttpServletResponse response
    		,@RequestParam("village") int vcode) {
		session = request.getSession(true);
		Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
        return serv.checkWatershedSwachhataVillageExits(vcode);
    }

	
	@RequestMapping(value = "/saveWatershedSwachhataDetails", method = RequestMethod.POST)
	public ModelAndView saveWatershedSwachhataDetails(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") WatershedSwachhataBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("mahotsav/watershedSwachhataAtProject");
				int projid = Integer.parseInt(request.getParameter("project"));
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				
				for (ProfileBean bean : listm) {
					distName = bean.getDistrictname();
					distCode = bean.getDistrictcode() == null ? 0 : bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode() == null ? 0 : bean.getStatecode();
				}
				
				mav.addObject("userType", userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
			//	mav.addObject("blkList", serProj.getBlockbyProjId(projid));

				result = serv.saveWatershedSwachhataDetails(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
				} 
				else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved ");
				} 
				/*else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved!");
				}*/
				return new ModelAndView("redirect:/getWatershedSwachhataAtProj");
			} else {
				return new ModelAndView("redirect:/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/completeWatershedSwachhataDetails", method = RequestMethod.POST)
	@ResponseBody
	public String completeWatershedSwachhataDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=serv.completeWatershedSwachhataDetails(assetid, session.getAttribute("loginID").toString());
		 
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/deleteWatershedSwachhataDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteWatershedSwachhataDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=serv.deleteWatershedSwachhataDetails(assetid, session.getAttribute("loginID").toString());
		 
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value = "/getImageSwachhataProjLvlId", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageSwachhataProjLvlId(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("swachhataid") Integer swachhataid){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = serv.getImageSwachhataProjLvlId(swachhataid);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}
	
	@RequestMapping(value = "/getWatershedSwachhataidProjLvlEdit", method = RequestMethod.POST)
	public ModelAndView getWatershedSwachhataidProjLvlEdit(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedSwachhataBean> editlist = new ArrayList<WatershedSwachhataBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("mahotsav/updateWatershedSwachhataAtProject");
				String waterid=request.getParameter("waterid");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				listm=profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				for(ProfileBean bean : listm) {
					distName =bean.getDistrictname();
					distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
				}
				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("stateName",stateName);
				//mav.addObject("distList", ser.getDistrictList(stcd));
				
				editlist=serv.getWatershedSwachhataidProjLvlEdit(Integer.parseInt(waterid));
				
				mav.addObject("dataList",editlist);
				mav.addObject("dataListSize",editlist.size());

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
	
	@RequestMapping(value = "/updateWatershedSwachhataDetails", method = RequestMethod.POST)
	public ModelAndView updateWatershedSwachhataDetails(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") WatershedSwachhataBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("mahotsav/watershedSwachhataAtProject");
				int projid = Integer.parseInt(request.getParameter("project"));
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				
				for (ProfileBean bean : listm) {
					distName = bean.getDistrictname();
					distCode = bean.getDistrictcode() == null ? 0 : bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode() == null ? 0 : bean.getStatecode();
				}
				
				mav.addObject("userType", userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
			//	mav.addObject("blkList", serProj.getBlockbyProjId(projid));

				result = serv.updateWatershedSwachhataDetails(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data Updated Successfully");
				} 
				else {
					redirectAttributes.addFlashAttribute("result", "Data Updation failed!");
				} 
				/*else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved!");
				}*/
				return new ModelAndView("redirect:/getWatershedSwachhataAtProj");
			} else {
				return new ModelAndView("redirect:/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/getWatershedSwachhataReport", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("mahotsav/watershedSwachhataReport");
		
		List<WatershedSwachhataBean> list = serv.getProjectLevelSwachhataStateWise(0);
		mav.addObject("projLvlWSPrgList", list);
		mav.addObject("projLvlWSPrgListSize", list.size());
		
		return mav; 
	}
	
	@RequestMapping(value = "/getImageByStcode", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageByStcode(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("stCode") Integer stcode){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = serv.getImageByStcode(stcode);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}
	
	@RequestMapping(value = "/distWSProjLvlProgRpt", method = RequestMethod.GET)
	public ModelAndView distWSProjLvlProgRpt(HttpServletRequest request, HttpServletResponse response) {
		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<WatershedSwachhataBean> list = new ArrayList<WatershedSwachhataBean>();
		
		ModelAndView mav = new ModelAndView("mahotsav/watershedSwachhataReport");
		
		list = serv.getdistWSProjLvlProgRpt(Integer.parseInt(stcd));
		
		mav.addObject("stcd",stcd);
		mav.addObject("stName",stName);
		mav.addObject("distWSProjList",list);
		mav.addObject("distWSProjListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/getImageByDcode", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageByDcode(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("dcode") Integer dcode){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = serv.getImageByDcode(dcode);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}
	
	@RequestMapping(value = "/downloadExcelStSwachhataProgram", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStSwachhataProgram(HttpServletRequest request, HttpServletResponse response)
	{
		
		List<WatershedSwachhataBean> list = serv.getProjectLevelSwachhataStateWise(0);
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report WS1 - State Wise Project Level Watershed Swachhata hi Seva Program");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report WS1 - State Wise Project Level Watershed Swachhata hi Seva Program";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 14, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1);
		sheet.addMergedRegion(mergedRegion);
		
		mergedRegion = new CellRangeAddress(5,6,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,3,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,9,9);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,10,10);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,11,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,12,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,13,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,14,14);
		sheet.addMergedRegion(mergedRegion);
		
		Row rowhead = sheet.createRow(5);
		
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("State Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total Number of Villages Involved");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total Number of People Participated");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		for(int i=4;i<9;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Number of Photographs Uploaded");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Total Number of Saplings Planted");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("Total Number of Works for Lokarpan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(12);
		cell.setCellValue("Shramdaan Undertaken on Total Number of Locations");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(13);
		cell.setCellValue("Number of Cleaniness Drives Organised");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(14);
		cell.setCellValue("Awareness Sessions Oraganised");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		Row rowhead1 = sheet.createRow(6);
		
		for(int i=0;i<3;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("SHG");
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(4);
		cell.setCellValue("User Groups");
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("FPO Youth");
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Students");
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Others");
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Total");
		cell.setCellStyle(style);
		
		for (int i = 9; i < 15; i++) {
			cell = rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		Row rowhead2 = sheet.createRow(7);
		for (int i = 0; i < 15; i++) {
			cell = rowhead2.createCell(i);
			cell.setCellValue(i + 1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 8;
		Integer totVillage = 0;
		Integer totShg = 0;
		Integer totUg = 0;
		Integer totFpo = 0;
		Integer totYouth = 0;
		
		Integer totOther = 0;
		Integer totTotal = 0;
		Integer totTotPhotos = 0;
		Integer totSaplings = 0;
		Integer totLokarpans = 0;
		Integer totShramdaans = 0;
		Integer totCleaniness = 0;
		Integer totAwareness = 0;
		
	    for(WatershedSwachhataBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getStname());
	    	row.createCell(2).setCellValue(bean.getVillage());
	    	row.createCell(3).setCellValue(bean.getShg());
	    	row.createCell(4).setCellValue(bean.getUg());
	    	row.createCell(5).setCellValue(bean.getFpo());
	    	row.createCell(6).setCellValue(bean.getYouth());
	    	row.createCell(7).setCellValue(bean.getOther());
	    	row.createCell(8).setCellValue(bean.getTotal());
	    	row.createCell(9).setCellValue(bean.getTotal_photos());
	    	row.createCell(10).setCellValue(bean.getNo_sapling());
	    	row.createCell(11).setCellValue(bean.getNo_works_lokarpan());
	    	row.createCell(12).setCellValue(bean.getNo_location_shramdaan());
	    	row.createCell(13).setCellValue(bean.getNo_cleanliness());
	    	row.createCell(14).setCellValue(bean.getNo_awareness());
	    	
	    	totVillage = totVillage + bean.getVillage();
	    	totShg = totShg + bean.getShg();
	    	totUg = totUg + bean.getUg();
	    	totFpo = totFpo + bean.getFpo();
	    	totYouth = totYouth + bean.getYouth();
			
	    	totOther = totOther + bean.getOther();
	    	totTotal = totTotal + bean.getTotal();
	    	totTotPhotos = totTotPhotos + bean.getTotal_photos();
	    	totSaplings = totSaplings + bean.getNo_sapling();
	    	totLokarpans = totLokarpans + bean.getNo_works_lokarpan();
	    	totShramdaans = totShramdaans + bean.getNo_location_shramdaan();
	    	totCleaniness = totCleaniness + bean.getNo_cleanliness();
	    	totAwareness = totAwareness + bean.getNo_awareness();
	    	
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
		cell.setCellValue(totVillage);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totShg);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totUg);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totFpo);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totYouth);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totOther);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totTotal);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totTotPhotos);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totSaplings.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totLokarpans);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totShramdaans);
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(totCleaniness);
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(totAwareness);
		cell.setCellStyle(style1);
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 14);
	    String fileName = "attachment; filename=Report WS1- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "mahotsav/watershedSwachhataReport";
	}
	
	@RequestMapping(value = "/downloadPDFStSwachhataProgram", method = RequestMethod.POST)
	public ModelAndView downloadPDFStSwachhataProgram(HttpServletRequest request, HttpServletResponse response)
	{
		List<WatershedSwachhataBean> list = serv.getProjectLevelSwachhataStateWise(0);
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 14, 14, 0);
			document.addTitle("WS1 - WSProjectLevelReport");
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
			
			paragraph3 = new Paragraph("Report WS1 - State Wise Project Level Watershed Swachhata hi Seva Program", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(14);
		    paragraph3.setSpacingAfter(14);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(15);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(3);
		    
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "State Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Number of Villages Involved", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Number of People Participated", Element.ALIGN_CENTER, 6, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Photographs Uploaded", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Number of Saplings Planted", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Number of Works for Lokarpan", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Shramdaan Undertaken on Total Number of Locations", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Cleaniness Drives Organised", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Awareness Sessions Oraganised", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "User Groups", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "FPO Youth", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Students", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
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
			
			int k = 1;
			Integer totVillage = 0;
			Integer totShg = 0;
			Integer totUg = 0;
			Integer totFpo = 0;
			Integer totYouth = 0;
			
			Integer totOther = 0;
			Integer totTotal = 0;
			Integer totTotPhotos = 0;
			Integer totSaplings = 0;
			Integer totLokarpans = 0;
			Integer totShramdaans = 0;
			Integer totCleaniness = 0;
			Integer totAwareness = 0;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getStname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVillage()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getShg()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getUg()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getFpo()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getYouth()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOther()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_photos()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_sapling()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getLokarpan()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_location_shramdaan()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_cleanliness()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_awareness()), Element.ALIGN_RIGHT, 1, 1, bf8);

					
			        totVillage = totVillage + list.get(i).getVillage();
			        totShg = totShg + list.get(i).getShg();
			        totUg = totUg + list.get(i).getUg();
			        totFpo = totFpo + list.get(i).getFpo();
			        totYouth = totYouth + list.get(i).getYouth();
			        
			        totOther = totOther + list.get(i).getOther();
			        totTotal = totTotal + list.get(i).getTotal();
			        totTotPhotos = totTotPhotos + list.get(i).getTotal_photos();
			        totSaplings = totSaplings + list.get(i).getNo_sapling();
			        totLokarpans = totLokarpans + list.get(i).getNo_works_lokarpan();
			        totCleaniness = totCleaniness + list.get(i).getNo_cleanliness();
			        totShramdaans = totShramdaans + list.get(i).getNo_location_shramdaan();
			        totAwareness = totAwareness + list.get(i).getNo_awareness();
			        		
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totVillage), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totShg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totUg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totFpo), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totYouth), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				CommonFunctions.insertCell3(table, String.valueOf(totOther), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totTotPhotos), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totSaplings), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totLokarpans), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCleaniness), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totShramdaans), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totAwareness), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 15, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WS1- State.pdf");
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
	
	@RequestMapping(value = "/downloadDistExcelSwachhataProgram", method = RequestMethod.POST)
	@ResponseBody
	public String downloadDistExcelSwachhataProgram(HttpServletRequest request, HttpServletResponse response)
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		List<WatershedSwachhataBean> list = serv.getdistWSProjLvlProgRpt(Integer.parseInt(stcd));
			
		Workbook workbook = new XSSFWorkbook();
		//invoking creatSheet() method and passing the name of the sheet to be created
		Sheet sheet = workbook.createSheet("Report WS1 - District Wise Project Level Watershed Swachhata hi Seva Program");
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report WS1 - District Wise Project Level Watershed Swachhata hi Seva Program";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 14, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+9,list.size()+9,0,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,14);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,2,2);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,6,3,8);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,9,9);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,10,10);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,11,11);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,12,12);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,13,13);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(6,7,14,14);
		sheet.addMergedRegion(mergedRegion);
		
		Row rowhead = sheet.createRow(5);
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("State Name: "+stName);
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.LEFT);
		for(int i=1;i<15;i++){
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		rowhead = sheet.createRow(6);
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total Number of Villages Involved");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("Total Number of People Participated");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		for(int i=4;i<9;i++)
		{
			cell =rowhead.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead.createCell(9);
		cell.setCellValue("Number of Photographs Uploaded");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(10);
		cell.setCellValue("Total Number of Saplings Planted");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("Total Number of Works for Lokarpan");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(12);
		cell.setCellValue("Shramdaan Undertaken on Total Number of Locations");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(13);
		cell.setCellValue("Number of Cleaniness Drives Organised");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		cell = rowhead.createCell(14);
		cell.setCellValue("Awareness Sessions Oraganised");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
		
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<3;i++){
			cell =rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		cell = rowhead1.createCell(3);
		cell.setCellValue("SHG");
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(4);
		cell.setCellValue("User Groups");
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(5);
		cell.setCellValue("FPO Youth");
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(6);
		cell.setCellValue("Students");
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(7);
		cell.setCellValue("Others");
		cell.setCellStyle(style);
		
		cell = rowhead1.createCell(8);
		cell.setCellValue("Total");
		cell.setCellStyle(style);
		
		for (int i = 9; i < 15; i++) {
			cell = rowhead1.createCell(i);
			cell.setCellStyle(style);
		}
		
		Row rowhead2 = sheet.createRow(8);
		for (int i = 0; i < 15; i++) {
			cell = rowhead2.createCell(i);
			cell.setCellValue(i + 1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 9;
		Integer totVillage = 0;
		Integer totShg = 0;
		Integer totUg = 0;
		Integer totFpo = 0;
		Integer totYouth = 0;
		
		Integer totOther = 0;
		Integer totTotal = 0;
		Integer totTotPhotos = 0;
		Integer totSaplings = 0;
		Integer totLokarpans = 0;
		Integer totShramdaans = 0;
		Integer totCleaniness = 0;
		Integer totAwareness = 0;
		
	    for(WatershedSwachhataBean bean: list)
	    {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno);
	    	row.createCell(1).setCellValue(bean.getDistname());
	    	row.createCell(2).setCellValue(bean.getVillage());
	    	row.createCell(3).setCellValue(bean.getShg());
	    	row.createCell(4).setCellValue(bean.getUg());
	    	row.createCell(5).setCellValue(bean.getFpo());
	    	row.createCell(6).setCellValue(bean.getYouth());
	    	row.createCell(7).setCellValue(bean.getOther());
	    	row.createCell(8).setCellValue(bean.getTotal());
	    	row.createCell(9).setCellValue(bean.getTotal_photos());
	    	row.createCell(10).setCellValue(bean.getNo_sapling());
	    	row.createCell(11).setCellValue(bean.getNo_works_lokarpan());
	    	row.createCell(12).setCellValue(bean.getNo_location_shramdaan());
	    	row.createCell(13).setCellValue(bean.getNo_cleanliness());
	    	row.createCell(14).setCellValue(bean.getNo_awareness());
	    	
	    	totVillage = totVillage + bean.getVillage();
	    	totShg = totShg + bean.getShg();
	    	totUg = totUg + bean.getUg();
	    	totFpo = totFpo + bean.getFpo();
	    	totYouth = totYouth + bean.getYouth();
			
	    	totOther = totOther + bean.getOther();
	    	totTotal = totTotal + bean.getTotal();
	    	totTotPhotos = totTotPhotos + bean.getTotal_photos();
	    	totSaplings = totSaplings + bean.getNo_sapling();
	    	totLokarpans = totLokarpans + bean.getNo_works_lokarpan();
	    	totShramdaans = totShramdaans + bean.getNo_location_shramdaan();
	    	totCleaniness = totCleaniness + bean.getNo_cleanliness();
	    	totAwareness = totAwareness + bean.getNo_awareness();
	    	
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
		
		Row row = sheet.createRow(list.size()+9);
		cell = row.createCell(0);
		cell.setCellValue("Grand Total");
		cell.setCellStyle(style1);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.RIGHT);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell = row.createCell(2);
		cell.setCellValue(totVillage);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totShg);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totUg);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totFpo);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totYouth);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totOther);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totTotal);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totTotPhotos);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totSaplings.doubleValue());
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totLokarpans);
		cell.setCellStyle(style1);
		cell = row.createCell(12);
		cell.setCellValue(totShramdaans);
		cell.setCellStyle(style1);
		cell = row.createCell(13);
		cell.setCellValue(totCleaniness);
		cell.setCellStyle(style1);
		cell = row.createCell(14);
		cell.setCellValue(totAwareness);
		cell.setCellStyle(style1);
		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 14);
	    String fileName = "attachment; filename=Report WS1- District.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);
	    
	    return "mahotsav/watershedSwachhataReport";
	}
	
	@RequestMapping(value = "/downloadDistPDFSwachhataProgram", method = RequestMethod.POST)
	public ModelAndView downloadDistPDFSwachhataProgram(HttpServletRequest request, HttpServletResponse response)
	{
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		List<WatershedSwachhataBean> list = serv.getdistWSProjLvlProgRpt(Integer.parseInt(stcd));
		try {
			Rectangle layout = new Rectangle(PageSize.A4.rotate());
			layout.setBackgroundColor(new BaseColor(255, 255, 255));
			Document document = new Document(layout, 25, 14, 14, 0);
			document.addTitle("WS1 - WSDistrictProjectLevelReport");
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
			
			paragraph3 = new Paragraph("Report WS1 - District Wise Project Level Watershed Swachhata hi Seva Program", f3);
			
			paragraph2.setAlignment(Element.ALIGN_CENTER);
		    paragraph3.setAlignment(Element.ALIGN_CENTER);
		    paragraph2.setSpacingAfter(14);
		    paragraph3.setSpacingAfter(14);
		    CommonFunctions.addHeader(document);
		    document.add(paragraph2);
		    document.add(paragraph3);
		    table = new PdfPTable(15);
		    table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(0f);
		    table.setSpacingAfter(0f);
		    table.setHeaderRows(4);
		    
		    CommonFunctions.insertCellHeader(table, "State Name: "+stName, Element.ALIGN_LEFT, 15, 1, bf8Bold);
		    CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "District Name", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Number of Villages Involved", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Number of People Participated", Element.ALIGN_CENTER, 6, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Photographs Uploaded", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Number of Saplings Planted", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total Number of Works for Lokarpan", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Shramdaan Undertaken on Total Number of Locations", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Number of Cleaniness Drives Organised", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Awareness Sessions Oraganised", Element.ALIGN_CENTER, 1, 2, bf8Bold);
			
			CommonFunctions.insertCellHeader(table, "SHG", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "User Groups", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "FPO Youth", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Students", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Others", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			CommonFunctions.insertCellHeader(table, "Total", Element.ALIGN_CENTER, 1, 1, bf8Bold);
			
			
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
			
			int k = 1;
			Integer totVillage = 0;
			Integer totShg = 0;
			Integer totUg = 0;
			Integer totFpo = 0;
			Integer totYouth = 0;
			
			Integer totOther = 0;
			Integer totTotal = 0;
			Integer totTotPhotos = 0;
			Integer totSaplings = 0;
			Integer totLokarpans = 0;
			Integer totShramdaans = 0;
			Integer totCleaniness = 0;
			Integer totAwareness = 0;
			
				
			if(list.size()!=0)
				for(int i=0;i<list.size();i++)
				{
					CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, list.get(i).getDistname(), Element.ALIGN_LEFT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getVillage()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getShg()), Element.ALIGN_RIGHT, 1, 1, bf8);
					CommonFunctions.insertCell(table, String.valueOf(list.get(i).getUg()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getFpo()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getYouth()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getOther()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_photos()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_sapling()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getLokarpan()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_location_shramdaan()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_cleanliness()), Element.ALIGN_RIGHT, 1, 1, bf8);
			        CommonFunctions.insertCell(table, String.valueOf(list.get(i).getNo_awareness()), Element.ALIGN_RIGHT, 1, 1, bf8);

					
			        totVillage = totVillage + list.get(i).getVillage();
			        totShg = totShg + list.get(i).getShg();
			        totUg = totUg + list.get(i).getUg();
			        totFpo = totFpo + list.get(i).getFpo();
			        totYouth = totYouth + list.get(i).getYouth();
			        
			        totOther = totOther + list.get(i).getOther();
			        totTotal = totTotal + list.get(i).getTotal();
			        totTotPhotos = totTotPhotos + list.get(i).getTotal_photos();
			        totSaplings = totSaplings + list.get(i).getNo_sapling();
			        totLokarpans = totLokarpans + list.get(i).getNo_works_lokarpan();
			        totCleaniness = totCleaniness + list.get(i).getNo_cleanliness();
			        totShramdaans = totShramdaans + list.get(i).getNo_location_shramdaan();
			        totAwareness = totAwareness + list.get(i).getNo_awareness();
			        		
					k++;
				}
				
				CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_RIGHT, 2, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totVillage), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totShg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totUg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totFpo), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totYouth), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				CommonFunctions.insertCell3(table, String.valueOf(totOther), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totTotal), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totTotPhotos), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totSaplings), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totLokarpans), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totCleaniness), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totShramdaans), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				CommonFunctions.insertCell3(table, String.valueOf(totAwareness), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
				
				if(list.size()==0)
					CommonFunctions.insertCell(table, "Data not found", Element.ALIGN_CENTER, 15, 1, bf8);
				
				
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
		response.setHeader("Content-Disposition", "attachment;filename=Report WS1- District.pdf");
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
