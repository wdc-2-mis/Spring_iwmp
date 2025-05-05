package app.controllers.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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

import app.bean.AddOutcomeParaBean;
import app.bean.BaseLineOutcomeBean;
import app.bean.BaselineStateWiseAreaDetailBean;
import app.bean.Login;
import app.bean.NetTretatbleAreaBean;
import app.bean.ShgDetailBean;
import app.bean.reports.SelfHelpGroupReportBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.common.CommonFunctions;
import app.service.DistrictMasterService;
import app.service.ProjectMasterService;
import app.service.StateMasterService;
import app.service.reports.StateWiseCurrentStatusService;

@Controller("StateWiseCurrentStatusController")
public class StateWiseCurrentStatusController {

	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	@Autowired(required = true)
	
	ProjectMasterService projectMasterService;
	
	@Autowired(required = true)
	StateWiseCurrentStatusService curntst;  


	@RequestMapping(value = "/getStateWiseCurrentStatus", method = RequestMethod.GET)
	public ModelAndView getStateWiseCurrentStatus(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String stname= request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		//	BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		//	approveUploadofDocument
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/stateWiseCurrentStatus");

			mav.addObject("netTreatledata",curntst.getStateWiseCurrentStatus());
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0};
			for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseCurrentStatus().values())
				//System.out.println("value: " + l);

				for(StateWiseCurrentStatusBean bean : l){

					dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getTotal_dist();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getTotal_distproj();
					dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getTotal_project();
					dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getTot_proj_loc();
					dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTotal_vill_project();
					dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getTotal_wc();
					dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getTotal_villin_wc();
					dataArrNetTotal[7] = dataArrNetTotal[7] + bean.getTotal_vill_basel();

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


			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			mav.addObject("stname", stname);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/getStateWiseCurrentStatusOther", method = RequestMethod.GET)
	public ModelAndView getStateWiseCurrentStatusOther(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String stname = request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		//	BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		//	approveUploadofDocument
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/stateWiseCurrentStatusOther");

			mav.addObject("netTreatledata",curntst.getStateWiseCurrentStatusOther());
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0};
			for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseCurrentStatusOther().values())
				//System.out.println("value: " + l);

				for(StateWiseCurrentStatusBean bean : l){

					dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getTotal_dist();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getTotal_village();
					dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getTotal_project();
					dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getTot_proj_permission();
					dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTotal_project_plan();
					dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getTot_proj_basel_ground();
					dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getTotal_wcdc();
					dataArrNetTotal[7] = dataArrNetTotal[7] + bean.getTotal_pia();

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


			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			mav.addObject("stname", stname);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/getStateWiseStatusOtherActivity", method = RequestMethod.GET)
	public ModelAndView getStateWiseStatusOtherActivity(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String stname= request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		//	BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		//	approveUploadofDocument
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/stateWiseStatusOtherActivity");

			mav.addObject("netTreatledata",curntst.getStateWiseStatusOtherActivity());
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseStatusOtherActivity().values())
				//System.out.println("value: " + l);

				for(StateWiseCurrentStatusBean bean : l){

					dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getTotal_dist();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getTotal_project();
					dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getTrain_conduct();
					dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getPerson_train();
					dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTotal_group();
					dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getTotal_shg();
					dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getTotal_fpo();
					dataArrNetTotal[7] = dataArrNetTotal[7] + bean.getTotal_epa();
					dataArrNetTotal[8] = dataArrNetTotal[8] + bean.getTotal_production();
					dataArrNetTotal[9] = dataArrNetTotal[9] + bean.getTotal_livelihood();

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
			mav.addObject("stname",stname);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/getStateWiseStatusBaselNetTreatArea", method = RequestMethod.GET)
	public ModelAndView getStateWiseStatusBaselNetTreatArea(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String stname = request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		BigDecimal baseb;
		BigDecimal base0;
		BigDecimal base2;
		BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/stateWiseStatusBaselNetTreatArea");
			mav.addObject("netTreatledata",curntst.getstateWiseStatusBaselNetTreatArea());
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", ""};
			dataArrNetTotal = new Integer[] { 0, 0 };
			for (List<StateWiseCurrentStatusBean> l : curntst.getstateWiseStatusBaselNetTreatArea().values())
				//System.out.println("value: " + l);

				for(StateWiseCurrentStatusBean bean : l){

					dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getTotal_dist();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getTotal_project();

					dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(bean.getProject_area());
					dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(bean.getProj_area_coverbasel());
					dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(bean.getPlot_area_basel());
					dataArrNetTotalBD[3] = dataArrNetTotalBD[3].add(bean.getNet_treat_area());

				}
			BigDecimal base = new BigDecimal(100);
			base0 =dataArrNetTotalBD[0].setScale(2, BigDecimal.ROUND_UP);
			base2 =dataArrNetTotalBD[2].setScale(2, BigDecimal.ROUND_UP);
			baseb=base2.divide(base0, 2, RoundingMode.UP);
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotal[0].toString();
			dataArrNetTotalStr[1] = dataArrNetTotal[1].toString();
			dataArrNetTotalStr[2] = baseb.multiply(base).toString();
			//	System.out.println(dataArrNetTotalStr[2] +" kdy ");
			dataArrNetTotalStr[3] = dataArrNetTotalBD[0].toString();
			dataArrNetTotalStr[4] = dataArrNetTotalBD[1].toString();
			dataArrNetTotalStr[5] = dataArrNetTotalBD[2].toString();
			dataArrNetTotalStr[6] = dataArrNetTotalBD[3].toString();

			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			mav.addObject("stname", stname);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	//uc
	@RequestMapping(value = "/getDistWiseStatusBaselNetTreatArea", method = RequestMethod.GET)
	public ModelAndView getDistWiseStatusBaselNetTreatArea(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) {
		ModelAndView mav = new ModelAndView();
		String stname = request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list =new ArrayList<StateWiseCurrentStatusBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		//	BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		//	approveUploadofDocument
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/distWiseCurrentStatus");

			mav.addObject("netTreatledata",curntst.getdistWiseStatusBaselNetTreatArea(id));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0};
			for (List<StateWiseCurrentStatusBean> l : curntst.getdistWiseStatusBaselNetTreatArea(id).values())
				//System.out.println("value: " + l);

				for(StateWiseCurrentStatusBean bean : l){


					dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getTotal_project();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getTot_proj_loc();
					dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getTotal_vill_project();
					dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getTotal_wc();
					dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTotal_villin_wc();
					dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getTotal_vill_basel();

				}
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotal[0].toString();
			dataArrNetTotalStr[1] = dataArrNetTotal[1].toString();
			dataArrNetTotalStr[2] = dataArrNetTotal[2].toString();
			dataArrNetTotalStr[3] = dataArrNetTotal[3].toString();
			dataArrNetTotalStr[4] = dataArrNetTotal[4].toString();
			dataArrNetTotalStr[5] = dataArrNetTotal[5].toString();


			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			mav.addObject("id",id);
			mav.addObject("stname", stname);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}
	
	@RequestMapping(value = "/projWiseStatusBaselNetTreatArea", method = RequestMethod.GET)
	public ModelAndView projWiseStatusBaselNetTreatArea(HttpServletRequest request, HttpServletResponse response) {
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		
		ModelAndView mav = new ModelAndView();
		try {
				mav = new ModelAndView("reports/projWiseCurrentStatus");
				 List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
				 list = curntst.projWiseStatusBaselNetTreatArea(dcode);
					
				 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(dcode).entrySet().iterator().next();
				 mav.addObject("statename",stateMasterService.getStateByDistCode(dcode).get(entry.getKey()));	
				 mav.addObject("distName",districtMasterService.getDistrictByDistCode(dcode).get(dcode));	
				mav.addObject("bslList", list);
				mav.addObject("bslListSize", list.size());
				mav.addObject("dcode", dcode);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	//uc
	@RequestMapping(value = "/getDistrictWiseCurrentStatusOther", method = RequestMethod.GET)
	public ModelAndView getDistrictWiseCurrentStatusOther(HttpServletRequest request, HttpServletResponse response,  @RequestParam("id") int id) {
		ModelAndView mav = new ModelAndView();
		String stname= request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		//	BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		//	approveUploadofDocument
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/districtWiseCurrentStatusOther");

			mav.addObject("netTreatledata",curntst.getDistrictWiseCurrentStatusOther(id));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0};
			for (List<StateWiseCurrentStatusBean> l : curntst.getDistrictWiseCurrentStatusOther(id).values())
				//System.out.println("value: " + l);

				for(StateWiseCurrentStatusBean bean : l){

					dataArrNetTotal[ 0] = dataArrNetTotal[0] + bean.getTotal_village();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getTotal_project();
					dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getTot_proj_permission();
					dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getTotal_project_plan();
					dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTot_proj_basel_ground();
					dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getTotal_wcdc();
					dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getTotal_pia();

				}
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotal[0].toString();
			dataArrNetTotalStr[1] = dataArrNetTotal[1].toString();
			dataArrNetTotalStr[2] = dataArrNetTotal[2].toString();
			dataArrNetTotalStr[3] = dataArrNetTotal[3].toString();
			dataArrNetTotalStr[4] = dataArrNetTotal[4].toString();
			dataArrNetTotalStr[5] = dataArrNetTotal[5].toString();
			dataArrNetTotalStr[6] = dataArrNetTotal[6].toString();


			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			mav.addObject("id", id);
			mav.addObject("stname",stname);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}
	
	@RequestMapping(value = "/projWiseCurrentStatusOther", method = RequestMethod.GET)
	public ModelAndView projWiseCurrentStatusOther(HttpServletRequest request, HttpServletResponse response) {
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		
		ModelAndView mav = new ModelAndView();
		try {
				mav = new ModelAndView("reports/projectWiseCurrentStatusOther");
				 List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
				 list = curntst.projWiseCurrentStatusOther(dcode);
					
				 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(dcode).entrySet().iterator().next();
				 mav.addObject("statename",stateMasterService.getStateByDistCode(dcode).get(entry.getKey()));	
				 mav.addObject("distName",districtMasterService.getDistrictByDistCode(dcode).get(dcode));	
				mav.addObject("bslListt", list);
				mav.addObject("bslListtSize", list.size());
				mav.addObject("dcode", dcode);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	 
	
	@RequestMapping(value = "/getDistrictWiseStatusBaselNetTreatArea", method = RequestMethod.GET)
	public ModelAndView getDistrictWiseStatusBaselNetTreatArea(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) {
		ModelAndView mav = new ModelAndView();
		String stname = request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		BigDecimal baseb;
		BigDecimal base0;
		BigDecimal base2;
		BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/districtWiseStatusBaseNetTreatArea");
			mav.addObject("netTreatledata",curntst.getdistrictWiseStatusBaselNetTreatArea(id));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", ""};
			dataArrNetTotal = new Integer[] { 0, 0 };
			for (List<StateWiseCurrentStatusBean> l : curntst.getdistrictWiseStatusBaselNetTreatArea(id).values())
				//System.out.println("value: " + l);

				for(StateWiseCurrentStatusBean bean : l){

					dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getTotal_project();

					dataArrNetTotalBD[0] = dataArrNetTotalBD[0].add(bean.getProject_area()==null?BigDecimal.ZERO:bean.getProject_area());
					dataArrNetTotalBD[1] = dataArrNetTotalBD[1].add(bean.getProj_area_coverbasel());
					dataArrNetTotalBD[2] = dataArrNetTotalBD[2].add(bean.getPlot_area_basel());


				}
			BigDecimal base = new BigDecimal(100);
			base0 =dataArrNetTotalBD[0].setScale(2, BigDecimal.ROUND_UP);
			base2 =dataArrNetTotalBD[2].setScale(2, BigDecimal.ROUND_UP);
			baseb=base2.divide(base0, 2, RoundingMode.UP);
			dataListNetTotal = new ArrayList();
			dataArrNetTotalStr[0] = dataArrNetTotal[0].toString();
			dataArrNetTotalStr[1] = baseb.multiply(base).toString();
			//	System.out.println(dataArrNetTotalStr[2] +" kdy ");
			dataArrNetTotalStr[2] = dataArrNetTotalBD[0].toString();
			dataArrNetTotalStr[3] = dataArrNetTotalBD[2].toString();
			dataArrNetTotalStr[4] = dataArrNetTotalBD[1].toString();

			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			mav.addObject("id", id);
			mav.addObject("stname", stname);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/projWiseStatusBaselArea", method = RequestMethod.GET)
	public ModelAndView projWiseStatusBaselArea(HttpServletRequest request, HttpServletResponse response) {
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		
		ModelAndView mav = new ModelAndView();
		try {
				mav = new ModelAndView("reports/projWiseStatusBaselArea");
				 List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
				 list = curntst.projWiseStatusBaselArea(dcode);
					
				 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(dcode).entrySet().iterator().next();
				mav.addObject("statename",stateMasterService.getStateByDistCode(dcode).get(entry.getKey()));	
				mav.addObject("distName",districtMasterService.getDistrictByDistCode(dcode).get(dcode));	
				mav.addObject("blListt", list);
				mav.addObject("blListtSize", list.size());
				mav.addObject("dcode", dcode);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	 

	@RequestMapping(value = "/getdistrictWiseStatusOtherActivity", method = RequestMethod.GET)
	public ModelAndView getdistrictWiseStatusOtherActivity(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id)  {
		ModelAndView mav = new ModelAndView();
		String stname= request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		//	BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
		//	approveUploadofDocument
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/districtWiseStatusOtherActivity");

			mav.addObject("netTreatledata",curntst.getdistrictWiseStatusOtherActivity(id));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			for (List<StateWiseCurrentStatusBean> l : curntst.getdistrictWiseStatusOtherActivity(id).values())
				//System.out.println("value: " + l);

				for(StateWiseCurrentStatusBean bean : l){

					//	dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getTotal_dist();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getTotal_project();
					//dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getTrain_conduct();
					dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getPerson_train();
					dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTotal_group();
					dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getTotal_shg();
					dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getTotal_fpo();
					dataArrNetTotal[7] = dataArrNetTotal[7] + bean.getTotal_epa();
					dataArrNetTotal[8] = dataArrNetTotal[8] + bean.getTotal_production();
					dataArrNetTotal[9] = dataArrNetTotal[9] + bean.getTotal_livelihood();

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
			mav.addObject("id", id);
			mav.addObject("stname",stname);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}
	
	@RequestMapping(value = "/projWiseStatusofOtherActivity", method = RequestMethod.GET)
	public ModelAndView projWiseStatusofOtherActivity(HttpServletRequest request, HttpServletResponse response) {
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		
		ModelAndView mav = new ModelAndView();
		try {
				mav = new ModelAndView("reports/projWiseStatusofOtherActivity");
				 List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
				 list = curntst.projWiseStatusofOtherActivity(dcode);
					
				 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(dcode).entrySet().iterator().next();
				mav.addObject("statename",stateMasterService.getStateByDistCode(dcode).get(entry.getKey()));	
				mav.addObject("distName",districtMasterService.getDistrictByDistCode(dcode).get(dcode));	
				mav.addObject("blListt", list);
				mav.addObject("blListtSize", list.size());
				mav.addObject("dcode", dcode);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/downloadExcelStateWiseCurrentStatus", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStateWiseCurrentStatus(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseCurrentStatus().values()) {
			for(StateWiseCurrentStatusBean bean : l){
				list.add(bean);
			}
		}
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME1- State wise Current Status of Project Location Details");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME1- State wise Current Status of Project Location Details Including Total No. of Villages, Total No. of Watershed Committees Constituted, Mapping of Watershed Committees with Villages and No. of Villages Covered under Baseline Survey";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);

		mergedRegion = new CellRangeAddress(5,6,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,2,2); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,3,3); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,4,4); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,5,8); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,9,9); 
		sheet.addMergedRegion(mergedRegion);

		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
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
		cell.setCellValue("Total no. of Districts");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("No. of District Covered in Project");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("Total Projects Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("Project Location");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

		cell = rowhead.createCell(6);
		cell.setCellStyle(style);
		cell = rowhead.createCell(7);
		cell.setCellStyle(style);
		cell = rowhead.createCell(8);
		cell.setCellStyle(style);

		cell = rowhead.createCell(9);
		cell.setCellValue("Total Village covered in Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		Row rowhead1 = sheet.createRow(6);
		for(int i =0; i<5;i++) {
			rowhead1.createCell(i).setCellStyle(style);
		}

		cell = rowhead1.createCell(5);
		cell.setCellValue("No. of Project with Location Details");  
		cell.setCellStyle(style);

		cell = rowhead1.createCell(6);
		cell.setCellValue("No. of Village Covered in Project");
		cell.setCellStyle(style);

		cell = rowhead1.createCell(7);
		cell.setCellValue("Total no. of Watershed Committee");
		cell.setCellStyle(style);

		cell = rowhead1.createCell(8);
		cell.setCellValue("No. of Villages Mapped with Watershed Committee");
		cell.setCellStyle(style);
		cell = rowhead1.createCell(9);
		cell.setCellStyle(style);

		Row rowhead2 = sheet.createRow(7); 
		for(int i = 0; i<10; i++) {	
		Cell cell1 = rowhead2.createCell(i);
		cell1.setCellValue(i+1);
		cell1.setCellStyle(style);
		}

		int sno = 1;
		int rowno  =8;
		int totDist = 0;
		int totDistProj = 0;
		int totProj = 0;
		int totProjLoc = 0;
		int totVillProj = 0;
		int totWc = 0;
		int totVillWc = 0;
		int totVillBase = 0;
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getSt_name());
			row.createCell(2).setCellValue(bean.getTotal_dist());  
			row.createCell(3).setCellValue(bean.getTotal_distproj());  
			row.createCell(4).setCellValue(bean.getTotal_project()); 
			row.createCell(5).setCellValue(bean.getTot_proj_loc());  
			row.createCell(6).setCellValue(bean.getTotal_vill_project()); 
			row.createCell(7).setCellValue(bean.getTotal_wc()); 
			row.createCell(8).setCellValue(bean.getTotal_villin_wc());  
			row.createCell(9).setCellValue(bean.getTotal_vill_basel()); 

			totDist = totDist + bean.getTotal_dist();
			totDistProj = totDistProj + bean.getTotal_distproj();
			totProj = totProj + bean.getTotal_project();
			totProjLoc = totProjLoc + bean.getTot_proj_loc();
			totVillProj = totVillProj + bean.getTotal_vill_project();
			totWc = totWc + bean.getTotal_wc();
			totVillWc = totVillWc + bean.getTotal_villin_wc();
			totVillBase = totVillBase + bean.getTotal_vill_basel();
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
		cell.setCellValue(totDist);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totDistProj);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totProj);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totProjLoc);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totVillProj);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totWc);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totVillWc);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totVillBase);
		cell.setCellStyle(style1);

		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);
		String fileName = "attachment; filename=Report ME1- State.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/stateWiseCurrentStatus";

	}

	@RequestMapping(value = "/downloadExcelDistWiseCurrentStatus", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistWiseCurrentStatus(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String id=request.getParameter("id");
		String stname=request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		for (List<StateWiseCurrentStatusBean> l : curntst.getdistWiseStatusBaselNetTreatArea(Integer.parseInt(id)).values()) {
			for(StateWiseCurrentStatusBean bean : l){
				list.add(bean);
			}
		}
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME1- District wise Current Status of Project Location Details");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME1- District wise Current Status of Project Location Details Including Total No. of Villages, Total No. of Watershed Committees Constituted, Mapping of Watershed Committees with Villages and No. of Villages Covered under Baseline Survey for State "+" '"+stname+"' ";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);

		mergedRegion = new CellRangeAddress(5,6,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,2,2); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,3,6); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,7,7); 
		sheet.addMergedRegion(mergedRegion);

		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
		sheet.addMergedRegion(mergedRegion);


		Row rowhead = sheet.createRow(5); 

		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(2);
		cell.setCellValue("Total Projects Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("Project Location");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellStyle(style);
		cell = rowhead.createCell(5);
		cell.setCellStyle(style);
		cell = rowhead.createCell(6);
		cell.setCellStyle(style);

		cell = rowhead.createCell(7);
		cell.setCellValue("Total Village covered in Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		Row rowhead1 = sheet.createRow(6);
		for(int i =0; i<3;i++) {
			rowhead1.createCell(i).setCellStyle(style);
		}

		cell = rowhead1.createCell(3);
		cell.setCellValue("No. of Project with Location Details");  
		cell.setCellStyle(style);

		cell = rowhead1.createCell(4);
		cell.setCellValue("No. of Village Covered in Project");
		cell.setCellStyle(style);

		cell = rowhead1.createCell(5);
		cell.setCellValue("Total no. of Watershed Committee");
		cell.setCellStyle(style);

		cell = rowhead1.createCell(6);
		cell.setCellValue("No. of Villages Mapped with Watershed Committee");
		cell.setCellStyle(style);
		cell = rowhead1.createCell(7);
		cell.setCellStyle(style);

		Row rowhead2 = sheet.createRow(7); 
		for(int i = 0; i<8; i++) {	
		Cell cell1 = rowhead2.createCell(i);
		cell1.setCellValue(i+1);
		cell1.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  =8;
		int totProj = 0;
		int totProjLoc = 0;
		int totVillProj = 0;
		int totWc = 0;
		int totVillWc = 0;
		int totVillBase = 0;
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getDist_name());
			row.createCell(2).setCellValue(bean.getTotal_project());  
			row.createCell(3).setCellValue(bean.getTot_proj_loc());  
			row.createCell(4).setCellValue(bean.getTotal_vill_project()); 
			row.createCell(5).setCellValue(bean.getTotal_wc());  
			row.createCell(6).setCellValue(bean.getTotal_villin_wc()); 
			row.createCell(7).setCellValue(bean.getTotal_vill_basel()); 

			totProj = totProj + bean.getTotal_project();
			totProjLoc = totProjLoc + bean.getTot_proj_loc();
			totVillProj = totVillProj + bean.getTotal_vill_project();
			totWc = totWc + bean.getTotal_wc();
			totVillWc = totVillWc + bean.getTotal_villin_wc();
			totVillBase = totVillBase + bean.getTotal_vill_basel();
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
		cell.setCellValue(totProj);
		cell.setCellStyle(style1);

		cell = row.createCell(3);
		cell.setCellValue(totProjLoc);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totVillProj);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totWc);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totVillWc);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totVillBase);
		cell.setCellStyle(style1);

		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 7);
		String fileName = "attachment; filename=Report ME1- District.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/distWiseCurrentStatus";

	}
		
	@RequestMapping(value = "/downloadExcelProjWiseCurrentStatus", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjWiseCurrentStatus(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		String dcode=request.getParameter("dcode");
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
	    List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
		 list = curntst.projWiseStatusBaselNetTreatArea(Integer.parseInt(dcode));
			
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME1- Project wise Current Status of Project Location Details");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME1- Project wise Current Status of Project Location Details Including Total No. of Villages, Total No. of Watershed Committees Constituted, Mapping of Watershed Committees with Villages and No. of Villages Covered under Baseline Survey for District "+" '"+distName+"'  of State  '"+statename+"' " ;
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);

		mergedRegion = new CellRangeAddress(5,6,0,0);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,1,1);
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,2,5); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,6,6,6); 
		sheet.addMergedRegion(mergedRegion);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
		sheet.addMergedRegion(mergedRegion);


		Row rowhead = sheet.createRow(5); 

		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(1);
		cell.setCellValue("Project Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(2);
		cell.setCellValue("Project Location");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellStyle(style);
		cell = rowhead.createCell(4);
		cell.setCellStyle(style);
		cell = rowhead.createCell(5);
		cell.setCellStyle(style);

		cell = rowhead.createCell(6);
		cell.setCellValue("Total Village covered in Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		Row rowhead1 = sheet.createRow(6);
		for(int i =0; i<2;i++) {
			rowhead1.createCell(i).setCellStyle(style);
		}

		cell = rowhead1.createCell(2);
		cell.setCellValue("No. of Project with Location Details");  
		cell.setCellStyle(style);

		cell = rowhead1.createCell(3);
		cell.setCellValue("No. of Village Covered in Project");
		cell.setCellStyle(style);

		cell = rowhead1.createCell(4);
		cell.setCellValue("Total no. of Watershed Committee");
		cell.setCellStyle(style);

		cell = rowhead1.createCell(5);
		cell.setCellValue("No. of Villages Mapped with Watershed Committee");
		cell.setCellStyle(style);
		cell = rowhead1.createCell(6);
		cell.setCellStyle(style);

		Row rowhead2 = sheet.createRow(7); 
		for(int i = 0; i<7; i++) {	
		Cell cell1 = rowhead2.createCell(i);
		cell1.setCellValue(i+1);
		cell1.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  =8;
		double totProjLoc = 0.0;
		double totVillProj = 0.0;
		double totWc = 0.0;
		double totVillWc = 0.0;
		double totVillBase = 0.0;
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getProj_name());
			row.createCell(2).setCellValue(bean.getTotprojloc().doubleValue());  
			row.createCell(3).setCellValue(bean.getTotalvillproject().doubleValue()); 
			row.createCell(4).setCellValue(bean.getTotalwc().doubleValue());  
			row.createCell(5).setCellValue(bean.getTotalvillinwc().doubleValue()); 
			row.createCell(6).setCellValue(bean.getTotalvillbasel().doubleValue()); 

			totProjLoc = totProjLoc+bean.getTotprojloc().doubleValue();
			totVillProj = totVillProj + bean.getTotalvillproject().doubleValue();
			totWc = totWc + bean.getTotalwc().doubleValue();
			totVillWc = totVillWc + bean.getTotalvillinwc().doubleValue();
			totVillBase = totVillBase + bean.getTotalvillbasel().doubleValue();
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
		cell.setCellValue(totProjLoc);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totVillProj);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totWc);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totVillWc);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totVillBase);
		cell.setCellStyle(style1);

		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 6);
		String fileName = "attachment; filename=Report ME1- Project.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/projWiseCurrentStatus";

	}
		


	@RequestMapping(value = "/getStateWiseCurrentStatusPlanAchieve", method = RequestMethod.GET)
	public ModelAndView getStateWiseCurrentStatusPlanAchieve(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String stname = request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
	//	BigDecimal [] dataArrNetTotalBD = {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
	//	approveUploadofDocument
		ArrayList dataListNetTotal = new ArrayList();
		try {
		mav = new ModelAndView("reports/stateWiseCurrentStatusPlanAchieve");
		
		mav.addObject("netTreatledata",curntst.getStateWiseCurrentStatusPlanAchieve());
		dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "", "", ""};
		dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseCurrentStatusPlanAchieve().values())
            //System.out.println("value: " + l);
    
		for(StateWiseCurrentStatusBean bean : l){
			
			dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getTotal_project();
			dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getTotal_project_plan2022();
			dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getTotal_project_achievement2022();
			dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getTotal_project_plan2023();
			dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTotal_project_achievement2023();
			dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getTotal_project_plan2024();
			dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getTotal_project_achievement2024();
			dataArrNetTotal[7] = dataArrNetTotal[7] + bean.getTotal_project_plan2025();
			dataArrNetTotal[8] = dataArrNetTotal[8] + bean.getTotal_project_achievement2025();
			
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
		dataListNetTotal.add(dataArrNetTotalStr);
		mav.addObject("dataListNetTotal", dataListNetTotal);
		mav.addObject("stname", stname);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/getdistrictWiseCurrentStatusPlanAchieve", method = RequestMethod.GET)
	public ModelAndView getdistrictWiseCurrentStatusPlanAchieve(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id)  {
		
		ModelAndView mav = new ModelAndView();
		String stname= request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		String[] dataArrNetTotalStr = null;
		Integer[] dataArrNetTotal = null;
		ArrayList dataListNetTotal = new ArrayList();
		try {
			mav = new ModelAndView("reports/districtWiseCurrentStatusPlanAchieve");
			mav.addObject("netTreatledata",curntst.getdistrictWiseCurrentStatusPlanAchieve(id));
			dataArrNetTotalStr = new String[] { "", "", "", "", "", "", "", "", "", "", "", "" };
			dataArrNetTotal = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			for (List<StateWiseCurrentStatusBean> l : curntst.getdistrictWiseCurrentStatusPlanAchieve(id).values())
				for(StateWiseCurrentStatusBean bean : l){
					dataArrNetTotal[0] = dataArrNetTotal[0] + bean.getTotal_project();
					dataArrNetTotal[1] = dataArrNetTotal[1] + bean.getTotal_project_plan2022();
					dataArrNetTotal[2] = dataArrNetTotal[2] + bean.getTotal_project_achievement2022();
					dataArrNetTotal[3] = dataArrNetTotal[3] + bean.getTotal_project_plan2023();
					dataArrNetTotal[4] = dataArrNetTotal[4] + bean.getTotal_project_achievement2023();
					dataArrNetTotal[5] = dataArrNetTotal[5] + bean.getTotal_project_plan2024();
					dataArrNetTotal[6] = dataArrNetTotal[6] + bean.getTotal_project_achievement2024();
					dataArrNetTotal[7] = dataArrNetTotal[7] + bean.getTotal_project_plan2025();
					dataArrNetTotal[8] = dataArrNetTotal[8] + bean.getTotal_project_achievement2025();
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
			
			dataListNetTotal.add(dataArrNetTotalStr);
			mav.addObject("dataListNetTotal", dataListNetTotal);
			mav.addObject("id", id);
			mav.addObject("stname", stname);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return mav;

	}
	
	@RequestMapping(value = "/getProjWiseCurrentStatusPlanAchieve", method = RequestMethod.GET)
	public ModelAndView getProjWiseCurrentStatusPlanAchieve(HttpServletRequest request, HttpServletResponse response) {
		
		int dcode = Integer.parseInt(request.getParameter("dcode"));
		
		ModelAndView mav = new ModelAndView();
		try {
				mav = new ModelAndView("reports/projWiseCurrentStatusPlanAchieve");
				 List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
				 
				 list = curntst.getprojWiseCurrentStatusPlanAchieve(dcode);
					
				 Map.Entry<Integer,String> entry = stateMasterService.getStateByDistCode(dcode).entrySet().iterator().next();
				mav.addObject("statename",stateMasterService.getStateByDistCode(dcode).get(entry.getKey()));	
				mav.addObject("distName",districtMasterService.getDistrictByDistCode(dcode).get(dcode));	
				mav.addObject("listt", list);
				mav.addObject("listtSize", list.size());
				mav.addObject("dcode", dcode);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}	
	
	@RequestMapping(value = "/downloadStateWiseStatusOtherActivityPDF", method = RequestMethod.POST)
	public ModelAndView downloadStateWiseStatusOtherActivityPDF(HttpServletRequest request, HttpServletResponse response) {
		
		List<StateWiseCurrentStatusBean> lists = new ArrayList<StateWiseCurrentStatusBean>();
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME4-Report");
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

	        paragraph3 = new Paragraph("Report ME4- State wise Current Status of Capacity Building Activities, UGs, SHGs, FPOs, EPAs, Production System and Livelihood Activities", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(12);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(60);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(2);

	        int total_dist = 0;
	        int total_project = 0;
	        int train_conduct = 0;
	        int person_train = 0;
	        int total_group = 0;
	        int total_shg = 0;
	        int total_fpo = 0;
	        int total_epa = 0;
	        int total_production = 0;
	        int total_livelihood = 0;

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total no. of Districts", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Total Projects Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total no. of Training Conducted", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "Total no. of Persons Trained", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of User Groups Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of SHGs Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of FPOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of EPAs Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Production System Activities Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Livelihood Activities for the Asset-less Persons Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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
//	        for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseStatusOtherActivity().values())

//				for(StateWiseCurrentStatusBean bean : l){
	        for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseStatusOtherActivity().values())
			for(StateWiseCurrentStatusBean bean : l){
				lists.add(bean);	
			}
	        int k = 1;
	        if (lists.size() != 0) {
	            for (int i = 0; i < lists.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, lists.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(lists.get(i).getTotal_dist()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(lists.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(lists.get(i).getTrain_conduct()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(lists.get(i).getPerson_train()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(lists.get(i).getTotal_group()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(lists.get(i).getTotal_shg()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(lists.get(i).getTotal_fpo()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(lists.get(i).getTotal_epa()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(lists.get(i).getTotal_production()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(lists.get(i).getTotal_livelihood()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                
	                k = k + 1;
	                total_dist=total_dist+lists.get(i).getTotal_dist();
	                total_project = total_project+ lists.get(i).getTotal_project();
	                train_conduct = train_conduct+lists.get(i).getTrain_conduct();
	                person_train = person_train+lists.get(i).getPerson_train();
	                total_group = total_group+lists.get(i).getTotal_group();
	                total_shg=total_shg+lists.get(i).getTotal_shg();
	                total_fpo=total_fpo+lists.get(i).getTotal_fpo();
	                total_epa = total_epa+ lists.get(i).getTotal_epa();
	                total_production = total_production+lists.get(i).getTotal_production();
	                total_livelihood = total_livelihood+lists.get(i).getTotal_livelihood();
	               
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_dist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(train_conduct), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(person_train), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_group), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_shg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_fpo), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_epa), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_production), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_livelihood), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            if(lists.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 12, 1, bf8);
			
		
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
	        response.setHeader("Content-Disposition", "attachment; filename=ME4-Report.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	
	@RequestMapping(value = "/downloadDistrictWiseStatusOtherActivityPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictWiseStatusOtherActivityPDF(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String stname = request.getParameter("stname");
		
		List<StateWiseCurrentStatusBean> dlist = new ArrayList<StateWiseCurrentStatusBean>();
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME4-Report");
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

	        paragraph3 = new Paragraph("Report ME4- District wise Current Status of Capacity Building Activities, UGs, SHGs, FPOs, EPAs, Production System and Livelihood Activities for State "+" '"+stname+"' ", f3);

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
	        table.setHeaderRows(2);

	        int total_project = 0;
	        int total_group = 0;
	        int total_shg = 0;
	        int total_fpo = 0;
	        int total_epa = 0;
	        int total_production = 0;
	        int total_livelihood = 0;

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "District Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Total Projects Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of User Groups Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of SHGs Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of FPOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of EPAs Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Production System Activities Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Livelihood Activities for the Asset-less Persons Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        for (List<StateWiseCurrentStatusBean> l : curntst.getdistrictWiseStatusOtherActivity(Integer.parseInt(id)).values())
				for(StateWiseCurrentStatusBean bean : l){

				dlist.add(bean);	
			}
	        int k = 1;
	        if (dlist.size() != 0) {
	            for (int i = 0; i < dlist.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, dlist.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlist.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlist.get(i).getTotal_group()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlist.get(i).getTotal_shg()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlist.get(i).getTotal_fpo()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlist.get(i).getTotal_epa()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlist.get(i).getTotal_production()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlist.get(i).getTotal_livelihood()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                
	                k = k + 1;
	                total_project = total_project+ dlist.get(i).getTotal_project();
	                total_group = total_group+dlist.get(i).getTotal_group();
	                total_shg=total_shg+dlist.get(i).getTotal_shg();
	                total_fpo=total_fpo+dlist.get(i).getTotal_fpo();
	                total_epa = total_epa+ dlist.get(i).getTotal_epa();
	                total_production = total_production+dlist.get(i).getTotal_production();
	                total_livelihood = total_livelihood+dlist.get(i).getTotal_livelihood();
	               
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_group), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_shg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_fpo), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_epa), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_production), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_livelihood), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            if(dlist.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 9, 1, bf8);
			
		
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
	        response.setHeader("Content-Disposition", "attachment; filename=ME4-Report(District).pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/downloadProjectWiseStatusOtherActivityPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectWiseStatusOtherActivityPDF(HttpServletRequest request, HttpServletResponse response) {
		
		String dcode=request.getParameter("dcode");
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
		 List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
		 list = curntst.projWiseStatusofOtherActivity(Integer.parseInt(dcode));
			
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME4-Report");
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

	        paragraph3 = new Paragraph("Report ME4- Project wise Current Status of Capacity Building Activities, UGs, SHGs, FPOs, EPAs, Production System and Livelihood Activities for District "+" '"+ distName +"'  of State    '"+statename+"' ", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(8);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(60);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(2);

	        int total_project = 0;
	        int total_group = 0;
	        int total_shg = 0;
	        int total_fpo = 0;
	        int total_epa = 0;
	        int total_production = 0;
	        int total_livelihood = 0;

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "No. of User Groups Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of SHGs Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of FPOs", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of EPAs Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Production System Activities Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Livelihood Activities for the Asset-less Persons Reported", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        int k = 1;
	        if (list.size() != 0) {
	            for (int i = 0; i < list.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, list.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_group()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_shg()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_fpo()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_epa()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_production()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_livelihood()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                
	                k = k + 1;
	                total_project = total_project+ list.get(i).getTotal_project();
	                total_group = total_group+list.get(i).getTotal_group();
	                total_shg=total_shg+list.get(i).getTotal_shg();
	                total_fpo=total_fpo+list.get(i).getTotal_fpo();
	                total_epa = total_epa+ list.get(i).getTotal_epa();
	                total_production = total_production+list.get(i).getTotal_production();
	                total_livelihood = total_livelihood+list.get(i).getTotal_livelihood();
	               
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_group), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_shg), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_fpo), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_epa), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_production), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_livelihood), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
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
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        }

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=ME4-Report(Project).pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/getStateWiseCurrentStatusPlanAchievePDF", method = RequestMethod.POST)
	public ModelAndView getStateWiseCurrentStatusPlanAchievePDF(HttpServletRequest request, HttpServletResponse response) {
		
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME5-Report");
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

	        paragraph3 = new Paragraph("Report ME5- State and Year wise Current Status of Plan and Achievement", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(11);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);

	        int total_project = 0;
	        int total_project_plan2022 = 0;
	        int total_project_achievement2022 = 0;
	        int total_project_plan2023 = 0;
	        int total_project_achievement2023 = 0;
	        int total_project_plan2024 = 0;
	        int total_project_achievement2024 = 0;
	        int total_project_plan2025 = 0;
	        int total_project_achievement2025 = 0;

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Projects Sanctioned", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Financial Year(2022-23)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Financial Year(2023-24)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Financial Year(2024-25)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Financial Year(2025-26)", Element.ALIGN_CENTER, 2, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);


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
	        
	        for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseCurrentStatusPlanAchieve().values())
			for(StateWiseCurrentStatusBean bean : l){
				 list.add(bean);	
			}
	        int k = 1;
	        if (list.size() != 0) {
	            for (int i = 0; i < list.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, list.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2022()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2022()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2023()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2023()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2024()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2024()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2025()), Element.ALIGN_RIGHT, 1,1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2025()), Element.ALIGN_RIGHT, 1,1, bf8);
	               
	                k = k + 1;
	                
	                total_project = total_project+ list.get(i).getTotal_project();
	    	        total_project_plan2022 = total_project_plan2022+list.get(i).getTotal_project_plan2022();
	    	        total_project_achievement2022 = total_project_achievement2022+list.get(i).getTotal_project_achievement2022();
	    	        total_project_plan2023 = total_project_plan2023+list.get(i).getTotal_project_plan2023();
	    	        total_project_achievement2023=total_project_achievement2023+list.get(i).getTotal_project_achievement2023();
	    	        total_project_plan2024 = total_project_plan2024+list.get(i).getTotal_project_plan2024();
	    	        total_project_achievement2024=total_project_achievement2024+list.get(i).getTotal_project_achievement2024();
	    	        total_project_plan2025 = total_project_plan2025+list.get(i).getTotal_project_plan2025();
	    	        total_project_achievement2025=total_project_achievement2025+list.get(i).getTotal_project_achievement2025();
	               
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2022), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2022), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2023), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2023), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2024), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2024), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2025), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2025), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

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
	        response.setHeader("Content-Disposition", "attachment; filename=ME5-Report.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}


	@RequestMapping(value = "/downloadStatewiseCurrentStatusPDF", method = RequestMethod.POST)
	public ModelAndView downloadStatewiseCurrentStatusPDF(HttpServletRequest request, HttpServletResponse response) {
	    List<StateWiseCurrentStatusBean> listbean = new ArrayList<StateWiseCurrentStatusBean>();
	    for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseCurrentStatus().values()) {
	        for (StateWiseCurrentStatusBean bean : l) {
	            listbean.add(bean);
	        }
	    }

	    try {
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME1-Report");
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

	        paragraph3 = new Paragraph("Report ME1- State wise Current Status of Project Location Details Including Total No. of Villages, Total No. of Watershed Committees Constituted, Mapping of Watershed Committees with Villages and No. of Villages Covered under Baseline Survey", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(10);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);

	        int total_dist = 0;
	        int total_distproj = 0;
	        int total_project = 0;
	        int total_wc = 0;
	        int total_villin_wc = 0;
	        int total_vill_basel = 0;
	        int total_vill_project = 0;
	        int tot_proj_loc = 0;

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total no of Districts", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of District Covered in Project", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Projects Sanctioned", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Project Location", Element.ALIGN_CENTER, 4, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Village Covered in Baseline Survey", Element.ALIGN_CENTER, 1, 2, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "No. of Project with Location Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Village Covered in Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total no. of Watershed Committee", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Villages Mapped with Watershed Committee", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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

	        int k = 1;
	        if (listbean.size() != 0) {
	            for (int i = 0; i < listbean.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, listbean.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listbean.get(i).getTotal_dist()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listbean.get(i).getTotal_distproj()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listbean.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listbean.get(i).getTot_proj_loc()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listbean.get(i).getTotal_vill_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listbean.get(i).getTotal_wc()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listbean.get(i).getTotal_villin_wc()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listbean.get(i).getTotal_vill_basel()), Element.ALIGN_RIGHT, 1, 1, bf8);

	                k = k + 1;
	                total_dist = total_dist + listbean.get(i).getTotal_dist();
	                total_distproj = total_distproj + listbean.get(i).getTotal_distproj();
	                total_project = total_project + listbean.get(i).getTotal_project();
	                tot_proj_loc=tot_proj_loc+listbean.get(i).getTot_proj_loc();
	                total_vill_project = total_vill_project + listbean.get(i).getTotal_vill_project();
	                total_wc = total_wc + listbean.get(i).getTotal_wc();
	                total_villin_wc = total_villin_wc + listbean.get(i).getTotal_villin_wc();
	                total_vill_basel = total_vill_basel + listbean.get(i).getTotal_vill_basel();
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_dist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_distproj), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(tot_proj_loc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_vill_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_wc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_villin_wc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_vill_basel), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

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
	        response.setHeader("Content-Disposition", "attachment; filename=ME1-Report.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}

	@RequestMapping(value = "/downloadDistrictwiseCurrentStatusPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictwiseCurrentStatusPDF(HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		String stname=request.getParameter("stname");
	    List<StateWiseCurrentStatusBean> listdistbean = new ArrayList<StateWiseCurrentStatusBean>();
	    for (List<StateWiseCurrentStatusBean> l : curntst.getdistWiseStatusBaselNetTreatArea(Integer.parseInt(id)).values()) {
	        for (StateWiseCurrentStatusBean bean : l) {
	        	listdistbean.add(bean);
	        }
	    }

	    try {
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME1-Report");
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

	        paragraph3 = new Paragraph("Report ME1- District wise Current Status of Project Location Details Including Total No. of Villages, Total No. of Watershed Committees Constituted, Mapping of Watershed Committees with Villages and No. of Villages Covered under Baseline Survey for State "+" '"+stname+"' ", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(8);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);

	        int total_project = 0;
	        int total_wc = 0;
	        int total_villin_wc = 0;
	        int total_vill_basel = 0;
	        int total_vill_project = 0;
	        int tot_proj_loc = 0;

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "District ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Projects Sanctioned", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Project Location", Element.ALIGN_CENTER, 4, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Village Covered in Baseline Survey", Element.ALIGN_CENTER, 1, 2, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "No. of Project with Location Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Village Covered in Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total no. of Watershed Committee", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Villages Mapped with Watershed Committee", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        int k = 1;
	        if (listdistbean.size() != 0) {
	            for (int i = 0; i < listdistbean.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, listdistbean.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listdistbean.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listdistbean.get(i).getTot_proj_loc()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listdistbean.get(i).getTotal_vill_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listdistbean.get(i).getTotal_wc()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listdistbean.get(i).getTotal_villin_wc()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listdistbean.get(i).getTotal_vill_basel()), Element.ALIGN_RIGHT, 1, 1, bf8);

	                k = k + 1;
	                total_project = total_project + listdistbean.get(i).getTotal_project();
	                tot_proj_loc=tot_proj_loc+listdistbean.get(i).getTot_proj_loc();
	                total_vill_project = total_vill_project + listdistbean.get(i).getTotal_vill_project();
	                total_wc = total_wc + listdistbean.get(i).getTotal_wc();
	                total_villin_wc = total_villin_wc + listdistbean.get(i).getTotal_villin_wc();
	                total_vill_basel = total_vill_basel + listdistbean.get(i).getTotal_vill_basel();
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(tot_proj_loc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_vill_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_wc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_villin_wc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_vill_basel), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

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
	        response.setHeader("Content-Disposition", "attachment; filename=ME1-DistrictReport.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
		
	@RequestMapping(value = "/downloadProjectwiseCurrentStatusPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectwiseCurrentStatusPDF(HttpServletRequest request, HttpServletResponse response) {
		String dcode=request.getParameter("dcode");
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
		
	    List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
		 list = curntst.projWiseStatusBaselNetTreatArea(Integer.parseInt(dcode));
			

	    try {
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME1-Report");
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

	        paragraph3 = new Paragraph("Report ME1- Project wise Current Status of Project Location Details Including Total No. of Villages, Total No. of Watershed Committees Constituted, Mapping of Watershed Committees with Villages and No. of Villages Covered under Baseline Survey for District "+" '"+distName+"' of State " +" '"+statename+"' ", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(7);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(70);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);

	        BigInteger totalwc =new BigInteger("0");
	        BigInteger totalvillinwc = new BigInteger("0");
	        BigInteger totalvillbasel = new BigInteger("0");
	        BigInteger totalvillproject = new BigInteger("0");
	        BigInteger totprojloc = new BigInteger("0");

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Project Location", Element.ALIGN_CENTER, 4, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Village Covered in Baseline Survey", Element.ALIGN_CENTER, 1, 2, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "No. of Project with Location Details", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Village Covered in Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total no. of Watershed Committee", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Villages Mapped with Watershed Committee", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        int k = 1;
	        if (list.size() != 0) {
	            for (int i = 0; i < list.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, list.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotprojloc()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalvillproject()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalwc()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalvillinwc()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotalvillbasel()), Element.ALIGN_RIGHT, 1, 1, bf8);

	                k = k + 1;
	                totprojloc=totprojloc.add(list.get(i).getTotprojloc());
	                totalvillproject = totalvillproject .add(list.get(i).getTotalvillproject());
	                totalwc = totalwc.add(list.get(i).getTotalwc());
	                totalvillinwc = totalvillinwc.add(list.get(i).getTotalvillinwc());
	                totalvillbasel = totalvillbasel.add(list.get(i).getTotalvillbasel());
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totprojloc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totalvillproject), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totalwc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totalvillinwc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(totalvillbasel), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

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
	        response.setHeader("Content-Disposition", "attachment; filename=ME1-ProjectReport.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/downloadStateWiseStatusBaselNetTreatAreaPDF", method = RequestMethod.POST)
	public ModelAndView downloadStateWiseStatusBaselNetTreatAreaPDF(HttpServletRequest request, HttpServletResponse response) {
		
		List<StateWiseCurrentStatusBean> listt = new ArrayList<StateWiseCurrentStatusBean>();
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME3-Report");
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

	        paragraph3 = new Paragraph("Report ME3- State wise Current Status of Baseline Survey and Net Treatable Area", f3);

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
	        table.setHeaderRows(2);

	        int total_dist = 0;
	        int total_project = 0;
	       BigDecimal project_area= BigDecimal.valueOf(0);
	       BigDecimal proj_area_coverbasel= BigDecimal.valueOf(0);
	       BigDecimal plot_area_basel= BigDecimal.valueOf(0);
	       BigDecimal percentage_area= BigDecimal.valueOf(0);
	       BigDecimal net_treat_area= BigDecimal.valueOf(0);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total no. of Districts", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Total Projects Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Project Area Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "Total Project Area Covered for Baseline Survey", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area of Plots Covered for Baseline Survey", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "% of Total Area Covered for Plot-Wise Baseline Survey", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Net Treatable Area(in Lakhs Ha.)", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "9", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        for (List<StateWiseCurrentStatusBean> l : curntst.getstateWiseStatusBaselNetTreatArea().values())
			for(StateWiseCurrentStatusBean bean : l){
				listt.add(bean);	
			}
	        int k = 1;
	        if (listt.size() != 0) {
	            for (int i = 0; i < listt.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, listt.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listt.get(i).getTotal_dist()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listt.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listt.get(i).getProject_area()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listt.get(i).getProj_area_coverbasel()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listt.get(i).getPlot_area_basel()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listt.get(i).getPercentage_area())+"%", Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(listt.get(i).getNet_treat_area()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                
	                k = k + 1;
	                total_dist=total_dist+listt.get(i).getTotal_dist();
	                total_project = total_project+ listt.get(i).getTotal_project();
	                project_area=project_area.add(listt.get(i).getProject_area());
	                proj_area_coverbasel = proj_area_coverbasel.add(listt.get(i).getProj_area_coverbasel());
	                plot_area_basel = plot_area_basel.add(listt.get(i).getPlot_area_basel());
	                percentage_area = percentage_area.add(listt.get(i).getPercentage_area());
	                net_treat_area = net_treat_area.add(listt.get(i).getNet_treat_area());
	                
	               
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_dist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(proj_area_coverbasel), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(plot_area_basel), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(plot_area_basel.divide(project_area,2,RoundingMode.UP).multiply(new BigDecimal(100)))+"%", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(net_treat_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            if(listt.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 9, 1, bf8);
			
		
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
	        response.setHeader("Content-Disposition", "attachment; filename=ME3-Report.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	@RequestMapping(value = "/downloadDistrictWiseStatusBaselNetTreatAreaPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictWiseStatusBaselNetTreatAreaPDF(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String stname = request.getParameter("stname");
		List<StateWiseCurrentStatusBean> dlistt = new ArrayList<StateWiseCurrentStatusBean>();
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME3-Report");
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

	        paragraph3 = new Paragraph("Report ME3- District wise Current Status of Baseline Survey and Net Treatable Area for State "+" '"+stname+"' ", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(7);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(60);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(2);

	        int total_project = 0;
	       BigDecimal project_area= BigDecimal.valueOf(0);
	       BigDecimal proj_area_coverbasel= BigDecimal.valueOf(0);
	       BigDecimal plot_area_basel= BigDecimal.valueOf(0);
	       BigDecimal percentage_area= BigDecimal.valueOf(0);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "District Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Total Projects Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Project Area Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "Total Project Area Covered for Baseline Survey", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area of Plots Covered for Baseline Survey", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "% of Total Area Covered for Plot-Wise Baseline Survey", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        for (List<StateWiseCurrentStatusBean> l : curntst.getdistrictWiseStatusBaselNetTreatArea(Integer.parseInt(id)).values())
			for(StateWiseCurrentStatusBean bean : l){
				dlistt.add(bean);	
			}
	        int k = 1;
	        if (dlistt.size() != 0) {
	            for (int i = 0; i < dlistt.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, dlistt.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlistt.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlistt.get(i).getProject_area()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlistt.get(i).getProj_area_coverbasel()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlistt.get(i).getPlot_area_basel()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlistt.get(i).getPercentage_area())+"%", Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                
	                k = k + 1;
	                total_project = total_project+ dlistt.get(i).getTotal_project();
	                project_area=project_area.add(dlistt.get(i).getProject_area());
	                proj_area_coverbasel = proj_area_coverbasel.add(dlistt.get(i).getProj_area_coverbasel());
	                plot_area_basel = plot_area_basel.add(dlistt.get(i).getPlot_area_basel());
	                percentage_area = percentage_area.add(dlistt.get(i).getPercentage_area());
	                
	               
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(proj_area_coverbasel), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(plot_area_basel), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(plot_area_basel.divide(project_area,2,RoundingMode.UP).multiply(new BigDecimal(100)))+"%", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            if(dlistt.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 7, 1, bf8);
			
		
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
	        response.setHeader("Content-Disposition", "attachment; filename=ME3-Report(District).pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/downloadProjWiseStatusBaselNetTreatAreaPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjWiseStatusBaselNetTreatAreaPDF(HttpServletRequest request, HttpServletResponse response) {
		String dcode=request.getParameter("dcode");
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
		
		List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
		 list = curntst.projWiseStatusBaselArea(Integer.parseInt(dcode));
			
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME3-Report");
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

	        paragraph3 = new Paragraph("Report ME3- Project wise Current Status of Baseline Survey and Net Treatable Area for District "+" '"+distName+"' of State  '"+statename+"' ", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(6);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5});
	        table.setWidthPercentage(60);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(2);

	        int total_project = 0;
	       BigDecimal project_area= BigDecimal.valueOf(0);
	       BigDecimal proj_area_coverbasel= BigDecimal.valueOf(0);
	       BigDecimal plot_area_basel= BigDecimal.valueOf(0);
	       BigDecimal percentage_area= BigDecimal.valueOf(0);

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Total Project Area Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "Total Project Area Covered for Baseline Survey", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Area of Plots Covered for Baseline Survey", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "% of Total Area Covered for Plot-Wise Baseline Survey", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        int k = 1;
	        if (list.size() != 0) {
	            for (int i = 0; i < list.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, list.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProject_area()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getProj_area_coverbasel()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPlot_area_basel()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getPercentage_area())+"%", Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                
	                k = k + 1;
	                project_area=project_area.add(list.get(i).getProject_area());
	                proj_area_coverbasel = proj_area_coverbasel.add(list.get(i).getProj_area_coverbasel());
	                plot_area_basel = plot_area_basel.add(list.get(i).getPlot_area_basel());
	                percentage_area = percentage_area.add(list.get(i).getPercentage_area());
//	                totPerTotAreaCovPlot = (totProjPlotCovBslnSur / totProjAreaSan)*100;
	               
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(project_area), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(proj_area_coverbasel), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(plot_area_basel), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(plot_area_basel.divide(project_area,2,RoundingMode.UP).multiply(new BigDecimal(100)))+"%", Element.ALIGN_RIGHT, 1, 1, bf10Bold);
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
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        }

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=ME3-Report(Project).pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	
	@RequestMapping(value = "/downloadDistrictWiseCurrentStatusOtherPDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictWiseCurrentStatusOtherPDF(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String stname = request.getParameter("stname");
		List<StateWiseCurrentStatusBean> dlists = new ArrayList<StateWiseCurrentStatusBean>();
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME2-Report");
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

	        paragraph3 = new Paragraph("Report ME2- District wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details for State "+" '"+stname+"' ", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(8);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(60);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(2);

	        int total_project = 0;
	        int total_village = 0;
	        int tot_proj_permission = 0;
	        int total_project_plan = 0;
	        int tot_proj_basel_ground = 0;
	        int total_wcdc = 0;
	        int total_pia = 0;
	       

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "District Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Total no. of Villages", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Projects Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "No. of Project for Permission given", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project with Baseline Ground Water", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of PIA Account Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "7", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "8", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        for (List<StateWiseCurrentStatusBean> l : curntst.getDistrictWiseCurrentStatusOther(Integer.parseInt(id)).values())
			for(StateWiseCurrentStatusBean bean : l){
				dlists.add(bean);	
			}
	        int k = 1;
	        if (dlists.size() != 0) {
	            for (int i = 0; i < dlists.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, dlists.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlists.get(i).getTotal_village()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlists.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlists.get(i).getTot_proj_permission()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlists.get(i).getTotal_project_plan()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlists.get(i).getTot_proj_basel_ground()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(dlists.get(i).getTotal_pia()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                k = k + 1;
	                total_project = total_project+ dlists.get(i).getTotal_project();
	                total_village=total_village+dlists.get(i).getTotal_village();
	                tot_proj_permission=tot_proj_permission+dlists.get(i).getTot_proj_permission();
	                total_project_plan=total_project_plan+dlists.get(i).getTotal_project_plan();
	                tot_proj_basel_ground = tot_proj_basel_ground+ dlists.get(i).getTot_proj_basel_ground();
	                total_wcdc=total_wcdc+dlists.get(i).getTotal_wcdc();
	                total_pia=total_pia+dlists.get(i).getTotal_pia();
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_village), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(tot_proj_permission), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(tot_proj_basel_ground), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_pia), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            if(dlists.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 8, 1, bf8);
			
		
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
	        response.setHeader("Content-Disposition", "attachment; filename=ME2-Report(District).pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/downloadProjectWiseCurrentStatusOtherPDF", method = RequestMethod.POST)
	public ModelAndView downloadProjectWiseCurrentStatusOtherPDF(HttpServletRequest request, HttpServletResponse response) {
		
		String dcode=request.getParameter("dcode");
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
		
	    List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
		 list = curntst.projWiseCurrentStatusOther(Integer.parseInt(dcode));
			
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME2-Report");
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

	        paragraph3 = new Paragraph("Report ME2- Project wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details for District  '"+distName+" of State'"+statename+"' ", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(6);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5});
	        table.setWidthPercentage(60);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(2);
	        
	        int coveredvill = 0;
	        int tot_proj_permission = 0;
	        int total_project_plan = 0;
	        int tot_proj_basel_ground = 0;
	       

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Total no. of Village Covered in Project", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "No. of Project for Permission given", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project with Baseline Ground Water", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "1", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "2", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "3", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "4", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "5", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "6", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        int k = 1;
	        if (list.size() != 0) {
	            for (int i = 0; i < list.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, list.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getCovered_village()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTot_proj_permission()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTot_proj_basel_ground()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                k = k + 1;
	                coveredvill = coveredvill+ list.get(i).getCovered_village();
	                tot_proj_permission=tot_proj_permission+list.get(i).getTot_proj_permission();
	                total_project_plan=total_project_plan+list.get(i).getTotal_project_plan();
	                tot_proj_basel_ground = tot_proj_basel_ground+ list.get(i).getTot_proj_basel_ground();
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(coveredvill), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(tot_proj_permission), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(tot_proj_basel_ground), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
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
				response.setContentType("application/pdf");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        }

	        document.close();

	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=ME2-Report(Project).pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/downloadStateWiseCurrentStatusOtherPDF", method = RequestMethod.POST)
	public ModelAndView downloadStateWiseCurrentStatusOtherPDF(HttpServletRequest request, HttpServletResponse response) {
		
		List<StateWiseCurrentStatusBean> slist = new ArrayList<StateWiseCurrentStatusBean>();
	  
	    try {
	    	
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME2-Report");
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

	        paragraph3 = new Paragraph("Report ME2- State wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(10);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(60);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(2);

	        int total_dist = 0;
	        int total_project = 0;
	        int total_village = 0;
	        int tot_proj_permission = 0;
	        int total_project_plan = 0;
	        int tot_proj_basel_ground = 0;
	        int total_wcdc = 0;
	        int total_pia = 0;
	       

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "State Name ", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total no. of Districts", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Total no. of Villages", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Projects Sanctioned", Element.ALIGN_CENTER, 1, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "No. of Project for Permission given", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project with Baseline Ground Water", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of WCDC Account Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of PIA Account Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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

	        for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseCurrentStatusOther().values())
			for(StateWiseCurrentStatusBean bean : l){
				slist.add(bean);	
			}
	        int k = 1;
	        if (slist.size() != 0) {
	            for (int i = 0; i < slist.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, slist.get(i).getSt_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(slist.get(i).getTotal_dist()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(slist.get(i).getTotal_village()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(slist.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(slist.get(i).getTot_proj_permission()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(slist.get(i).getTotal_project_plan()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(slist.get(i).getTot_proj_basel_ground()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(slist.get(i).getTotal_wcdc()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(slist.get(i).getTotal_pia()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                
	                k = k + 1;
	                total_dist=total_dist+slist.get(i).getTotal_dist();
	                total_project = total_project+ slist.get(i).getTotal_project();
	                total_village=total_village+slist.get(i).getTotal_village();
	                tot_proj_permission=tot_proj_permission+slist.get(i).getTot_proj_permission();
	                total_project_plan=total_project_plan+slist.get(i).getTotal_project_plan();
	                tot_proj_basel_ground = tot_proj_basel_ground+ slist.get(i).getTot_proj_basel_ground();
	                total_wcdc=total_wcdc+slist.get(i).getTotal_wcdc();
	                total_pia=total_pia+slist.get(i).getTotal_pia();
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_dist), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_village), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            
	            CommonFunctions.insertCell3(table, String.valueOf(tot_proj_permission), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(tot_proj_basel_ground), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_wcdc), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_pia), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            if(slist.size()==0)
					CommonFunctions.insertCell(table, " Data not found", Element.ALIGN_CENTER, 10, 1, bf8);
			
		
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
	        response.setHeader("Content-Disposition", "attachment; filename=ME2-Report.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/downloadExcelStateWiseStatusOtherActivity", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStateWiseStatusOtherActivity(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseStatusOtherActivity().values()) {
			for(StateWiseCurrentStatusBean bean : l){
				list.add(bean);
			}
		}
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME4- State wise Current Status of Capacity Building Activities, UGs, SHGs, FPOs, EPAs, Production System and Livelihood Activities");   

	    

		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME4- State wise Current Status of Capacity Building Activities, UGs, SHGs, FPOs, EPAs, Production System and Livelihood Activities";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 11, areaAmtValDetail, workbook);

		
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
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
		cell.setCellValue("Total no. of Districts");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("Total Projects Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("Total no. of Training Conducted");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("Total no. of Persons Trained");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(6);
		cell.setCellValue("No. of User Groups Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("No. of SHGs Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("No. of FPOs");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(9);
		cell.setCellValue("No. of EPAs Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(10);
		cell.setCellValue("No. of Production System Activities Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(11);
		cell.setCellValue("No. of Livelihood Activities for the Asset-less Persons Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i=0;i<12;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		

		int sno = 1;
		int rowno  = 7;
		int totDist = 0;
		int totProjSan = 0;
		int totTrainCon = 0;
		int totPernTrain = 0;
		int totUsrGrpRpt = 0;
		int totSHGRpt = 0;
		int totFPO = 0;
		int totEPARpt = 0;
		int totProdSysActRpt = 0;
		int totLivelihoodPernRpt = 0;
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getSt_name());
			row.createCell(2).setCellValue(bean.getTotal_dist());  
			row.createCell(3).setCellValue(bean.getTotal_project());  
			row.createCell(4).setCellValue(bean.getTrain_conduct()); 
			row.createCell(5).setCellValue(bean.getPerson_train());  
			row.createCell(6).setCellValue(bean.getTotal_group()); 
			row.createCell(7).setCellValue(bean.getTotal_shg()); 
			row.createCell(8).setCellValue(bean.getTotal_fpo());  
			row.createCell(9).setCellValue(bean.getTotal_epa());
			row.createCell(10).setCellValue(bean.getTotal_production());
			row.createCell(11).setCellValue(bean.getTotal_livelihood());
			

			totDist = totDist + bean.getTotal_dist();
			totProjSan = totProjSan + bean.getTotal_project();
			totTrainCon = totTrainCon + bean.getTrain_conduct();
			totPernTrain = totPernTrain + bean.getPerson_train();
			totUsrGrpRpt = totUsrGrpRpt + bean.getTotal_group();
			totSHGRpt = totSHGRpt + bean.getTotal_shg();
			totFPO = totFPO + bean.getTotal_fpo();
			totEPARpt = totEPARpt + bean.getTotal_epa();
			totProdSysActRpt = totProdSysActRpt + bean.getTotal_production();
			totLivelihoodPernRpt = totLivelihoodPernRpt + bean.getTotal_livelihood();
			
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
		cell.setCellValue(totDist);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totProjSan);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totTrainCon);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totPernTrain);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totUsrGrpRpt);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totSHGRpt);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totFPO);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totEPARpt);
		cell.setCellStyle(style1);
		cell = row.createCell(10);
		cell.setCellValue(totProdSysActRpt);
		cell.setCellStyle(style1);
		cell = row.createCell(11);
		cell.setCellValue(totLivelihoodPernRpt);
		cell.setCellStyle(style1);

		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
		String fileName = "attachment; filename=Report ME4- State.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/stateWiseStatusOtherActivity";

	}
	
	@RequestMapping(value = "/downloadExcelDistrictWiseStatusOtherActivity", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistrictWiseStatusOtherActivity(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String id=request.getParameter("id");
		String stname=request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		for (List<StateWiseCurrentStatusBean> l : curntst.getdistrictWiseStatusOtherActivity(Integer.parseInt(id)).values()) {
			for(StateWiseCurrentStatusBean bean : l){
				list.add(bean);
			}
		}
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME4- District wise Current Status of Capacity Building Activities, UGs, SHGs, FPOs, EPAs, Production System and Livelihood Activities");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME4- District wise Current Status of Capacity Building Activities, UGs, SHGs, FPOs, EPAs, Production System and Livelihood Activities for State "+" '"+stname+"' ";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);
	
		
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
		sheet.addMergedRegion(mergedRegion);


		Row rowhead = sheet.createRow(5); 

		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(2);
		cell.setCellValue("Total Projects Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("No. of User Groups Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("No. of SHGs Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("No. of FPOs");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(6);
		cell.setCellValue("No. of EPAs Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("No. of Production System Activities Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("No. of Livelihood Activities for the Asset-less Persons Reported");  
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
		int totProjSan = 0;
		int totUsrGrpRpt = 0;
		int totSHGRpt = 0;
		int totFPO = 0;
		int totEPARpt = 0;
		int totProdSysActRpt = 0;
		int totLivelihoodPernRpt = 0;
		
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getDist_name());
			row.createCell(2).setCellValue(bean.getTotal_project());  
			row.createCell(3).setCellValue(bean.getTotal_group());  
			row.createCell(4).setCellValue(bean.getTotal_shg()); 
			row.createCell(5).setCellValue(bean.getTotal_fpo());  
			row.createCell(6).setCellValue(bean.getTotal_epa()); 
			row.createCell(7).setCellValue(bean.getTotal_production()); 
			row.createCell(8).setCellValue(bean.getTotal_livelihood());
			

			totProjSan = totProjSan + bean.getTotal_project();
			totUsrGrpRpt = totUsrGrpRpt + bean.getTotal_group();
			totSHGRpt = totSHGRpt + bean.getTotal_shg();
			totFPO = totFPO + bean.getTotal_fpo();
			totEPARpt = totEPARpt + bean.getTotal_epa();
			totProdSysActRpt = totProdSysActRpt + bean.getTotal_production();
			totLivelihoodPernRpt = totLivelihoodPernRpt + bean.getTotal_livelihood();
			
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
		cell.setCellValue(totProjSan);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totUsrGrpRpt);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totSHGRpt);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totFPO);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totEPARpt);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totProdSysActRpt);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totLivelihoodPernRpt);
		cell.setCellStyle(style1);


		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
		String fileName = "attachment; filename=Report ME4- District.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/districtWiseStatusOtherActivity";

	}
	
	
		
	@RequestMapping(value = "/downloadExcelProjectWiseStatusOtherActivity", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjectWiseStatusOtherActivity(HttpServletRequest request, HttpServletResponse response, Model model)
	{
			String dcode=request.getParameter("dcode");
			String statename=request.getParameter("statename");
			String distName=request.getParameter("distName");
			
			 List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
			 list = curntst.projWiseStatusofOtherActivity(Integer.parseInt(dcode));
				
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME4- Project wise Current Status of Capacity Building Activities, UGs, SHGs, FPOs, EPAs, Production System and Livelihood Activities");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME4- Project wise Current Status of Capacity Building Activities, UGs, SHGs, FPOs, EPAs, Production System and Livelihood Activities for District "+" '"+distName+"'  of State  '"+ statename+"' ";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);
	
		
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
		sheet.addMergedRegion(mergedRegion);


		Row rowhead = sheet.createRow(5); 

		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(1);
		cell.setCellValue("Project Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(2);
		cell.setCellValue("No. of User Groups Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("No. of SHGs Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("No. of FPOs");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("No. of EPAs Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(6);
		cell.setCellValue("No. of Production System Activities Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("No. of Livelihood Activities for the Asset-less Persons Reported");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		
		Row rowhead1 = sheet.createRow(6); 
		for(int i=0;i<8;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		

		int sno = 1;
		int rowno  = 7;
		int totUsrGrpRpt = 0;
		int totSHGRpt = 0;
		int totFPO = 0;
		int totEPARpt = 0;
		int totProdSysActRpt = 0;
		int totLivelihoodPernRpt = 0;
		
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getProj_name());
			row.createCell(2).setCellValue(bean.getTotal_group());  
			row.createCell(3).setCellValue(bean.getTotal_shg()); 
			row.createCell(4).setCellValue(bean.getTotal_fpo());  
			row.createCell(5).setCellValue(bean.getTotal_epa()); 
			row.createCell(6).setCellValue(bean.getTotal_production()); 
			row.createCell(7).setCellValue(bean.getTotal_livelihood());
			

			totUsrGrpRpt = totUsrGrpRpt + bean.getTotal_group();
			totSHGRpt = totSHGRpt + bean.getTotal_shg();
			totFPO = totFPO + bean.getTotal_fpo();
			totEPARpt = totEPARpt + bean.getTotal_epa();
			totProdSysActRpt = totProdSysActRpt + bean.getTotal_production();
			totLivelihoodPernRpt = totLivelihoodPernRpt + bean.getTotal_livelihood();
			
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
		cell.setCellValue(totUsrGrpRpt);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totSHGRpt);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totFPO);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totEPARpt);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totProdSysActRpt);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totLivelihoodPernRpt);
		cell.setCellStyle(style1);


		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 11);
		String fileName = "attachment; filename=Report ME4- Project.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/projWiseStatusOtherActivity";

	}
	
	
	@RequestMapping(value = "/downloadExcelDistrictWiseCurrentStatusPlanAchieve", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistrictWiseCurrentStatusPlanAchieve(HttpServletRequest request, HttpServletResponse response) 
	{
		String stname=request.getParameter("stname");
		String id=request.getParameter("id");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		
        for (List<StateWiseCurrentStatusBean> l : curntst.getdistrictWiseCurrentStatusPlanAchieve(Integer.parseInt(id)).values())
		for(StateWiseCurrentStatusBean bean : l){
			 list.add(bean);	
		}
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report ME5- District and Year wise Current Status of Plan and Achievement ");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report ME5- District and Year wise Current Status of Plan and Achievement for State "+" '"+stname+"' ";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 10, areaAmtValDetail, workbook);
			
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
	
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("District Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
			cell = rowhead.createCell(2);
			cell.setCellValue("Total Projects Sanctioned");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Financial Year(2022-23)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(4).setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Financial Year(2023-24)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(6).setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Financial Year(2024-25)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(8).setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Financial Year(2025-26)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(10).setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(9);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(10);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<11;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        int ProjSan = 0;
	        int ProjPlan22 = 0;
	        int ProjAch22 = 0;
	        int ProjPlan23 = 0;
	        int ProjAch23 = 0;
	        int ProjPlan24 = 0;
	        int ProjAch24 = 0;
	        int ProjPlan25 = 0;
	        int ProjAch25 = 0;
	        
	        
	        for(StateWiseCurrentStatusBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getDist_name());
	        	row.createCell(2).setCellValue(bean.getTotal_project());
				row.createCell(3).setCellValue(bean.getTotal_project_plan2022());  
				row.createCell(4).setCellValue(bean.getTotal_project_achievement2022()); 
				row.createCell(5).setCellValue(bean.getTotal_project_plan2023());  
				row.createCell(6).setCellValue(bean.getTotal_project_achievement2023());
				row.createCell(7).setCellValue(bean.getTotal_project_plan2024());  
				row.createCell(8).setCellValue(bean.getTotal_project_achievement2024());
				row.createCell(9).setCellValue(bean.getTotal_project_plan2025());  
				row.createCell(10).setCellValue(bean.getTotal_project_achievement2025());
	        	
				ProjSan = ProjSan + bean.getTotal_project();
				ProjPlan22 = ProjPlan22 + bean.getTotal_project_plan2022();
				ProjAch22 = ProjAch22 + bean.getTotal_project_achievement2022();
				ProjPlan23 = ProjPlan23 + bean.getTotal_project_plan2023();
				ProjAch23 = ProjAch23 + bean.getTotal_project_achievement2023();
				ProjPlan24 = ProjPlan24 + bean.getTotal_project_plan2024();
				ProjAch24 = ProjAch24 + bean.getTotal_project_achievement2024();
				ProjPlan25 = ProjPlan25 + bean.getTotal_project_plan2025();
				ProjAch25 = ProjAch25 + bean.getTotal_project_achievement2025();
				
				
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
	        cell.setCellValue(ProjSan);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(ProjPlan22);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(ProjAch22);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(ProjPlan23);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(ProjAch23);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(ProjPlan24);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(ProjAch24);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(ProjPlan25);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(ProjAch25);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 10);
	        String fileName = "attachment; filename=Report ME5- District.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/districtWiseCurrentStatusPlanAchieve";
		
	}
	
	@RequestMapping(value = "/downloadExcelProjWiseCurrentStatusPlanAchieve", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjWiseCurrentStatusPlanAchieve(HttpServletRequest request, HttpServletResponse response) 
	{
		String dcode=request.getParameter("dcode");
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
		
	    List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
		 list = curntst.getprojWiseCurrentStatusPlanAchieve(Integer.parseInt(dcode));
			

			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report ME5- Project and Year wise Current Status of Plan and Achievement ");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report ME5- Project and Year wise Current Status of Plan and Achievement for District  '"+distName+"'  of State  '"+statename+"' ";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);
			
			mergedRegion = new CellRangeAddress(5,6,0,0);
	        sheet.addMergedRegion(mergedRegion);
			mergedRegion = new CellRangeAddress(5,6,1,1);
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,2,3); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,4,5); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,6,7); 
	        sheet.addMergedRegion(mergedRegion);
	        mergedRegion = new CellRangeAddress(5,5,8,9); 
	        sheet.addMergedRegion(mergedRegion);
	
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
	        sheet.addMergedRegion(mergedRegion);
	        
	        
			Row rowhead = sheet.createRow(5); 
			
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("S.No.");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(1);
			cell.setCellValue("Project Name");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
				
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Financial Year(2022-23)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(3).setCellStyle(style);
			
			cell = rowhead.createCell(4);
			cell.setCellValue("Financial Year(2023-24)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(5).setCellStyle(style);
			
			cell = rowhead.createCell(6);
			cell.setCellValue("Financial Year(2024-25)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(7).setCellStyle(style);
			
			cell = rowhead.createCell(8);
			cell.setCellValue("Financial Year(2025-26)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(9).setCellStyle(style);
			
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<2;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(2);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(3);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(4);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(9);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<10;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        int ProjPlan22 = 0;
	        int ProjAch22 = 0;
	        int ProjPlan23 = 0;
	        int ProjAch23 = 0;
	        int ProjPlan24 = 0;
	        int ProjAch24 = 0;
	        int ProjPlan25 = 0;
	        int ProjAch25 = 0;
	        
	        
	        for(StateWiseCurrentStatusBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getProj_name());
				row.createCell(2).setCellValue(bean.getTotal_project_plan2022());  
				row.createCell(3).setCellValue(bean.getTotal_project_achievement2022()); 
				row.createCell(4).setCellValue(bean.getTotal_project_plan2023());  
				row.createCell(5).setCellValue(bean.getTotal_project_achievement2023());
				row.createCell(6).setCellValue(bean.getTotal_project_plan2024());  
				row.createCell(7).setCellValue(bean.getTotal_project_achievement2024());
				row.createCell(8).setCellValue(bean.getTotal_project_plan2025());  
				row.createCell(9).setCellValue(bean.getTotal_project_achievement2025());
	        	
				ProjPlan22 = ProjPlan22 + bean.getTotal_project_plan2022();
				ProjAch22 = ProjAch22 + bean.getTotal_project_achievement2022();
				ProjPlan23 = ProjPlan23 + bean.getTotal_project_plan2023();
				ProjAch23 = ProjAch23 + bean.getTotal_project_achievement2023();
				ProjPlan24 = ProjPlan24 + bean.getTotal_project_plan2024();
				ProjAch24 = ProjAch24 + bean.getTotal_project_achievement2024();
				ProjPlan25 = ProjPlan25 + bean.getTotal_project_plan2025();
				ProjAch25 = ProjAch25 + bean.getTotal_project_achievement2025();
				
				
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
	        cell.setCellValue(ProjPlan22);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(ProjAch22);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(ProjPlan23);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(ProjAch23);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(ProjPlan24);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(ProjAch24);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(ProjPlan25);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(ProjAch25);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);
	        String fileName = "attachment; filename=Report ME5- Project.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/projWiseCurrentStatusPlanAchieve";
		
	}
	
	@RequestMapping(value = "/downloadDistrictWiseCurrentStatusPlanAchievePDF", method = RequestMethod.POST)
	public ModelAndView downloadDistrictWiseCurrentStatusPlanAchievePDF(HttpServletRequest request, HttpServletResponse response) {
		
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
	  
	    try {
	    	
	    	String id = request.getParameter("id");
	    	String stname = request.getParameter("stname");
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME5-Report");
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

	        paragraph3 = new Paragraph("Report ME5- District and Year wise Current Status of Plan and Achievement for State "+" '"+stname+"' ", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(11);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);

	        int total_project = 0;
	        int total_project_plan2022 = 0;
	        int total_project_achievement2022 = 0;
	        int total_project_plan2023 = 0;
	        int total_project_achievement2023 = 0;
	        int total_project_plan2024 = 0;
	        int total_project_achievement2024 = 0;
	        int total_project_plan2025 = 0;
	        int total_project_achievement2025 = 0;

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "District Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Total Projects Sanctioned", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Financial Year(2022-23)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Financial Year(2023-24)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Financial Year(2024-25)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Financial Year(2025-26)", Element.ALIGN_CENTER, 2, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);


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
	        for (List<StateWiseCurrentStatusBean> l : curntst.getdistrictWiseCurrentStatusPlanAchieve(Integer.parseInt(id)).values())
			for(StateWiseCurrentStatusBean bean : l){
				 list.add(bean);	
			}
	        int k = 1;
	        if (list.size() != 0) {
	            for (int i = 0; i < list.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, list.get(i).getDist_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2022()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2022()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2023()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2023()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2024()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2024()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2025()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2025()), Element.ALIGN_RIGHT, 1, 1, bf8);
	               
	                k = k + 1;
	                
	                total_project = total_project+ list.get(i).getTotal_project();
	    	        total_project_plan2022 = total_project_plan2022+list.get(i).getTotal_project_plan2022();
	    	        total_project_achievement2022 = total_project_achievement2022+list.get(i).getTotal_project_achievement2022();
	    	        total_project_plan2023 = total_project_plan2023+list.get(i).getTotal_project_plan2023();
	    	        total_project_achievement2023=total_project_achievement2023+list.get(i).getTotal_project_achievement2023();
	    	        total_project_plan2024 = total_project_plan2024+list.get(i).getTotal_project_plan2024();
	    	        total_project_achievement2024=total_project_achievement2024+list.get(i).getTotal_project_achievement2024();
	    	        total_project_plan2025 = total_project_plan2025+list.get(i).getTotal_project_plan2025();
	    	        total_project_achievement2025=total_project_achievement2025+list.get(i).getTotal_project_achievement2025();
	               
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2022), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2022), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2023), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2023), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2024), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2024), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2025), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2025), Element.ALIGN_RIGHT, 1, 1, bf10Bold);


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
	        response.setHeader("Content-Disposition", "attachment; filename=ME5-DistrictReport.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	

	@RequestMapping(value = "/downloadProjWiseCurrentStatusPlanAchievePDF", method = RequestMethod.POST)
	public ModelAndView downloadProjWiseCurrentStatusPlanAchievePDF(HttpServletRequest request, HttpServletResponse response) {
		
		String dcode=request.getParameter("dcode");
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
		
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		
		 list = curntst.getprojWiseCurrentStatusPlanAchieve(Integer.parseInt(dcode));
	  
	    try {
	    	
	        Rectangle layout = new Rectangle(PageSize.A4.rotate());
	        layout.setBackgroundColor(new BaseColor(255, 255, 255));
	        Document document = new Document(layout, 25, 10, 10, 0);
	        document.addTitle("ME5-Report");
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

	        paragraph3 = new Paragraph("Report ME5- Project and Year wise Current Status of Plan and Achievement for District "+" '"+distName+"'  of State  '"+statename+"' ", f3);

	        paragraph2.setAlignment(Element.ALIGN_CENTER);
	        paragraph3.setAlignment(Element.ALIGN_CENTER);
	        paragraph2.setSpacingAfter(10);
	        paragraph3.setSpacingAfter(10);
	        CommonFunctions.addHeader(document);
	        document.add(paragraph2);
	        document.add(paragraph3);
	        table = new PdfPTable(10);
	        table.setWidths(new int[]{2, 8, 5, 5, 5, 5, 5, 5, 5, 5});
	        table.setWidthPercentage(90);
	        table.setSpacingBefore(0f);
	        table.setSpacingAfter(0f);
	        table.setHeaderRows(3);

	        int total_project_plan2022 = 0;
	        int total_project_achievement2022 = 0;
	        int total_project_plan2023 = 0;
	        int total_project_achievement2023 = 0;
	        int total_project_plan2024 = 0;
	        int total_project_achievement2024 = 0;
	        int total_project_plan2025 = 0;
	        int total_project_achievement2025 = 0;

	        CommonFunctions.insertCellHeader(table, "S.No.", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Project Name ", Element.ALIGN_CENTER, 1, 2, bf8Bold);
	       
	        CommonFunctions.insertCellHeader(table, "Financial Year(2022-23)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Financial Year(2023-24)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Financial Year(2024-25)", Element.ALIGN_CENTER, 2, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "Financial Year(2025-26)", Element.ALIGN_CENTER, 2, 1, bf8Bold);

	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Plan Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);
	        CommonFunctions.insertCellHeader(table, "No. of Project Achievement Created", Element.ALIGN_CENTER, 1, 1, bf8Bold);

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
	       
	        int k = 1;
	        if (list.size() != 0) {
	            for (int i = 0; i < list.size(); i++) {
	                CommonFunctions.insertCell(table, String.valueOf(k), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, list.get(i).getProj_name(), Element.ALIGN_LEFT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2022()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2022()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2023()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2023()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2024()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2024()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_plan2025()), Element.ALIGN_RIGHT, 1, 1, bf8);
	                CommonFunctions.insertCell(table, String.valueOf(list.get(i).getTotal_project_achievement2025()), Element.ALIGN_RIGHT, 1, 1, bf8);
	               
	                k = k + 1;
	                
	    	        total_project_plan2022 = total_project_plan2022+list.get(i).getTotal_project_plan2022();
	    	        total_project_achievement2022 = total_project_achievement2022+list.get(i).getTotal_project_achievement2022();
	    	        total_project_plan2023 = total_project_plan2023+list.get(i).getTotal_project_plan2023();
	    	        total_project_achievement2023=total_project_achievement2023+list.get(i).getTotal_project_achievement2023();
	    	        total_project_plan2024 = total_project_plan2024+list.get(i).getTotal_project_plan2024();
	    	        total_project_achievement2024=total_project_achievement2024+list.get(i).getTotal_project_achievement2024();
	    	        total_project_plan2025 = total_project_plan2025+list.get(i).getTotal_project_plan2025();
	    	        total_project_achievement2025=total_project_achievement2025+list.get(i).getTotal_project_achievement2025();
	               
	            }

	            CommonFunctions.insertCell3(table, "Grand Total", Element.ALIGN_CENTER, 2, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2022), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2022), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2023), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2023), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2024), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2024), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_plan2025), Element.ALIGN_RIGHT, 1, 1, bf10Bold);
	            CommonFunctions.insertCell3(table, String.valueOf(total_project_achievement2025), Element.ALIGN_RIGHT, 1, 1, bf10Bold);

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
	        response.setHeader("Content-Disposition", "attachment; filename=ME5-ProjectReport.pdf");
	        OutputStream outputStream = response.getOutputStream();
	        baos.writeTo(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	@RequestMapping(value = "/downloadExcelStateWiseCurrentStatusOther", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStateWiseCurrentStatusOther(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseCurrentStatusOther().values()) {
			for(StateWiseCurrentStatusBean bean : l){
				list.add(bean);
			}
		}
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME2- State wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME2- State wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 9, areaAmtValDetail, workbook);

		
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
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
		cell.setCellValue("Total no. of Districts");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("Total no. of Villages");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("Total Projects Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("No. of Project for Permission given");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(6);
		cell.setCellValue("No. of Project Plan Created");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("No. of Project with Baseline Ground Water");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("No. of WCDC Account Created");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(9);
		cell.setCellValue("No. of PIA Account Created");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i=0;i<10;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		

		int sno = 1;
		int rowno  = 7;
		int totDist = 0;
		int totVil = 0;
		int totProjSan = 0;
		int totProjPerm = 0;
		int totProjPlan = 0;
		int totProjBslnGrdWtr = 0;
		int totWCDC = 0;
		int totPIA = 0;
		
		
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getSt_name());
			row.createCell(2).setCellValue(bean.getTotal_dist());  
			row.createCell(3).setCellValue(bean.getTotal_village());  
			row.createCell(4).setCellValue(bean.getTotal_project()); 
			row.createCell(5).setCellValue(bean.getTot_proj_permission());  
			row.createCell(6).setCellValue(bean.getTotal_project_plan()); 
			row.createCell(7).setCellValue(bean.getTot_proj_basel_ground()); 
			row.createCell(8).setCellValue(bean.getTotal_wcdc());  
			row.createCell(9).setCellValue(bean.getTotal_pia());
			

			totDist = totDist + bean.getTotal_dist();
			totVil = totVil + bean.getTotal_village();
			totProjSan = totProjSan + bean.getTotal_project();
			totProjPerm = totProjPerm + bean.getTot_proj_permission();
			totProjPlan = totProjPlan + bean.getTotal_project_plan();
			totProjBslnGrdWtr = totProjBslnGrdWtr + bean.getTot_proj_basel_ground();
			totWCDC = totWCDC + bean.getTotal_wcdc();
			totPIA = totPIA + bean.getTotal_pia();
			
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
		cell.setCellValue(totDist);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totVil);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totProjSan);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totProjPerm);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totProjPlan);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totProjBslnGrdWtr);
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totWCDC);
		cell.setCellStyle(style1);
		cell = row.createCell(9);
		cell.setCellValue(totPIA);
		cell.setCellStyle(style1);

		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 9);
		String fileName = "attachment; filename=Report ME2- State.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/stateWiseCurrentStatusOther";

	}
	
	@RequestMapping(value = "/downloadExcelDistrictWiseCurrentStatusOther", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistrictWiseCurrentStatusOther(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String id=request.getParameter("id");
		String stname=request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		for (List<StateWiseCurrentStatusBean> l : curntst.getDistrictWiseCurrentStatusOther(Integer.parseInt(id)).values()) {
			for(StateWiseCurrentStatusBean bean : l){
				list.add(bean);
			}
		}
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME2- District-wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME2- District-wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details for State "+" '"+stname+"' ";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 7, areaAmtValDetail, workbook);

	
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
		sheet.addMergedRegion(mergedRegion);


		Row rowhead = sheet.createRow(5); 

		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(2);
		cell.setCellValue("Total no. of Villages");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("Total Projects Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("No. of Project for Permission given");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("No. of Project Plan Created");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(6);
		cell.setCellValue("No. of Project with Baseline Ground Water");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("No. of PIA Account Created");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i=0;i<8;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		

		int sno = 1;
		int rowno  = 7;
		int totVil = 0;
		int totProjSan = 0;
		int totProjPerm = 0;
		int totProjPlan = 0;
		int totProjBslnGrdWtr = 0;
		int totPIA = 0;
		
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getDist_name());
			row.createCell(2).setCellValue(bean.getTotal_village());  
			row.createCell(3).setCellValue(bean.getTotal_project());  
			row.createCell(4).setCellValue(bean.getTot_proj_permission()); 
			row.createCell(5).setCellValue(bean.getTotal_project_plan());  
			row.createCell(6).setCellValue(bean.getTot_proj_basel_ground()); 
			row.createCell(7).setCellValue(bean.getTotal_pia()); 
			

			totVil = totVil + bean.getTotal_village();
			totProjSan = totProjSan + bean.getTotal_project();
			totProjPerm = totProjPerm + bean.getTot_proj_permission();
			totProjPlan = totProjPlan + bean.getTotal_project_plan();
			totProjBslnGrdWtr = totProjBslnGrdWtr + bean.getTot_proj_basel_ground();
			totPIA = totPIA + bean.getTotal_pia();
			
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
		cell.setCellValue(totVil);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totProjSan);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totProjPerm);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totProjPlan);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totProjBslnGrdWtr);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(totPIA);
		cell.setCellStyle(style1);


		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 7);
		String fileName = "attachment; filename=Report ME2- District.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/districtWiseCurrentStatusOther";

	}
	
	@RequestMapping(value = "/downloadExcelStateWiseStatusBaselNetTreatArea", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStateWiseStatusBaselNetTreatArea(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		for (List<StateWiseCurrentStatusBean> l : curntst.getstateWiseStatusBaselNetTreatArea().values()) {
			for(StateWiseCurrentStatusBean bean : l){
				list.add(bean);
			}
		}
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME3- State wise Current Status of Baseline Survey and Net Treatable Area");   



		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME3- State wise Current Status of Baseline Survey and Net Treatable Area";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 8, areaAmtValDetail, workbook);

		
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
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
		cell.setCellValue("Total no. of Districts");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("Total Projects Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("Total Project Area Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("Total Project Area Covered for Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(6);
		cell.setCellValue("Total Area of Plots Covered for Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(7);
		cell.setCellValue("% of Total Area Covered for Plot-Wise Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
		
		cell = rowhead.createCell(8);
		cell.setCellValue("Net Treatable Area(in Lakhs Ha.)");  
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
		int totDist = 0;
		int totProjSan = 0;
		Double totProjAreaSan = 0.0;
		Double totProjAreaCovBslnSur = 0.0;
		Double totProjPlotCovBslnSur = 0.0;
		Double totPerTotAreaCovPlot = 0.0;
		Double totNetTreatArea = 0.0;
		
		
		
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getSt_name());
			row.createCell(2).setCellValue(bean.getTotal_dist());  
			row.createCell(3).setCellValue(bean.getTotal_project());  
			row.createCell(4).setCellValue(bean.getProject_area().doubleValue()); 
			row.createCell(5).setCellValue(bean.getProj_area_coverbasel().doubleValue());  
			row.createCell(6).setCellValue(bean.getPlot_area_basel().doubleValue()); 
			row.createCell(7).setCellValue(bean.getPercentage_area().doubleValue()); 
			row.createCell(8).setCellValue(bean.getNet_treat_area().doubleValue());  
			

			totDist = totDist + bean.getTotal_dist();
			totProjSan = totProjSan + bean.getTotal_project();
			totProjAreaSan = totProjAreaSan + bean.getProject_area().doubleValue();
			totProjAreaCovBslnSur = totProjAreaCovBslnSur + bean.getProj_area_coverbasel().doubleValue();
			totProjPlotCovBslnSur = totProjPlotCovBslnSur + bean.getPlot_area_basel().doubleValue();
			totNetTreatArea = totNetTreatArea + bean.getNet_treat_area().doubleValue();
			totPerTotAreaCovPlot = (totProjPlotCovBslnSur / totProjAreaSan)*100.0;
			
			sno++;
			rowno++;
		}
		
		String formattedValue = String.format("%.2f", totPerTotAreaCovPlot);
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
		cell.setCellValue(totDist);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totProjSan);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totProjAreaSan);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totProjAreaCovBslnSur);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(totProjPlotCovBslnSur);
		cell.setCellStyle(style1);
		cell = row.createCell(7);
		cell.setCellValue(Double.parseDouble(formattedValue));
		cell.setCellStyle(style1);
		cell = row.createCell(8);
		cell.setCellValue(totNetTreatArea);
		cell.setCellStyle(style1);


		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 8);
		String fileName = "attachment; filename=Report ME3- State.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/stateWiseStatusBaselNetTreatArea";

	}
	
	@RequestMapping(value = "/downloadExcelDistrictWiseStatusBaselNetTreatArea", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelDistrictWiseStatusBaselNetTreatArea(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String id=request.getParameter("id");
		String stname=request.getParameter("stname");
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		for (List<StateWiseCurrentStatusBean> l : curntst.getdistrictWiseStatusBaselNetTreatArea(Integer.parseInt(id)).values()) {
			for(StateWiseCurrentStatusBean bean : l){
				list.add(bean);
			}
		}
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME3- District wise Current Status of Baseline Survey and Net Treatable Area");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME3- District wise Current Status of Baseline Survey and Net Treatable Area for State "+" '"+stname+"' ";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);

	
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
		sheet.addMergedRegion(mergedRegion);


		Row rowhead = sheet.createRow(5); 

		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(1);
		cell.setCellValue("District Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(2);
		cell.setCellValue("Total Projects Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("Total Project Area Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("Total Project Area Covered for Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("Total Area of Plots Covered for Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(6);
		cell.setCellValue("% of Total Area Covered for Plot-Wise Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i=0;i<7;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		

		int sno = 1;
		int rowno  = 7;
		int totProjSan = 0;
		Double totProjAreaSan = 0.0;
		Double totProjAreaCovBslnSur = 0.0;
		Double totProjPlotCovBslnSur = 0.0;
		Double totPerTotAreaCovPlot = 0.0;
		
		
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getDist_name());
			row.createCell(2).setCellValue(bean.getTotal_project());  
			row.createCell(3).setCellValue(bean.getProject_area().doubleValue());  
			row.createCell(4).setCellValue(bean.getProj_area_coverbasel().doubleValue()); 
			row.createCell(5).setCellValue(bean.getPlot_area_basel().doubleValue());  
			row.createCell(6).setCellValue(bean.getPercentage_area().doubleValue()); 
			

			totProjSan = totProjSan + bean.getTotal_project();
			totProjAreaSan = totProjAreaSan + bean.getProject_area().doubleValue();
			totProjAreaCovBslnSur = totProjAreaCovBslnSur + bean.getProj_area_coverbasel().doubleValue();
			totProjPlotCovBslnSur = totProjPlotCovBslnSur + bean.getPlot_area_basel().doubleValue();
			totPerTotAreaCovPlot = (totProjPlotCovBslnSur / totProjAreaSan)*100.0;
			
			sno++;
			rowno++;
		}

		String formattedValue = String.format("%.2f", totPerTotAreaCovPlot);
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
		cell.setCellValue(totProjSan);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totProjAreaSan);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totProjAreaCovBslnSur);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totProjPlotCovBslnSur);
		cell.setCellStyle(style1);
		cell = row.createCell(6);
		cell.setCellValue(Double.parseDouble(formattedValue));
		cell.setCellStyle(style1);



		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 6);
		String fileName = "attachment; filename=Report ME3- District.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/districtWiseStatusBaseNetTreatArea";

	}
	@RequestMapping(value = "/downloadExcelProjWiseStatusBaselNetTreatArea", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelProjWiseStatusBaselNetTreatArea(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String dcode=request.getParameter("dcode");
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
		
		List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
		 list = curntst.projWiseStatusBaselArea(Integer.parseInt(dcode));
			
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME3- Project wise Current Status of Baseline Survey and Net Treatable Area");   


		CellStyle style = CommonFunctions.getStyle(workbook);

		String rptName = "Report ME3- Project wise Current Status of Baseline Survey and Net Treatable Area for District "+" '"+distName+"' for State  '"+statename+"'";
		String areaAmtValDetail = "";

		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 6, areaAmtValDetail, workbook);

	
		mergedRegion = new CellRangeAddress(list.size()+7,list.size()+7,0,1); 
		sheet.addMergedRegion(mergedRegion);


		Row rowhead = sheet.createRow(5); 

		Cell cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(1);
		cell.setCellValue("project Name");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);


		cell = rowhead.createCell(2);
		cell.setCellValue("Total Project Area Sanctioned");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(3);
		cell.setCellValue("Total Project Area Covered for Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(4);
		cell.setCellValue("Total Area of Plots Covered for Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		cell = rowhead.createCell(5);
		cell.setCellValue("% of Total Area Covered for Plot-Wise Baseline Survey");  
		cell.setCellStyle(style);
		CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);

		
		
		Row rowhead1 = sheet.createRow(6); 
		for(int i=0;i<6;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		

		int sno = 1;
		int rowno  = 7;
		Double totProjAreaSan = 0.0;
		Double totProjAreaCovBslnSur = 0.0;
		Double totProjPlotCovBslnSur = 0.0;
		Double totPerTotAreaCovPlot = 0.0;
		
		
		for(StateWiseCurrentStatusBean bean: list) {
			Row row = sheet.createRow(rowno);
			row.createCell(0).setCellValue(sno); 
			row.createCell(1).setCellValue(bean.getProj_name());
			row.createCell(2).setCellValue(bean.getProject_area().doubleValue());  
			row.createCell(3).setCellValue(bean.getProj_area_coverbasel().doubleValue()); 
			row.createCell(4).setCellValue(bean.getPlot_area_basel().doubleValue());  
			row.createCell(5).setCellValue(bean.getPercentage_area().doubleValue()); 
			

			totProjAreaSan = totProjAreaSan + bean.getProject_area().doubleValue();
			totProjAreaCovBslnSur = totProjAreaCovBslnSur + bean.getProj_area_coverbasel().doubleValue();
			totProjPlotCovBslnSur = totProjPlotCovBslnSur + bean.getPlot_area_basel().doubleValue();
			totPerTotAreaCovPlot = (totProjPlotCovBslnSur / totProjAreaSan)*100.0;
			
			sno++;
			rowno++;
		}
		String formattedValue = String.format("%.2f", totPerTotAreaCovPlot);
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
		cell.setCellValue(totProjAreaSan);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totProjAreaCovBslnSur);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totProjPlotCovBslnSur);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(Double.parseDouble(formattedValue));
		cell.setCellStyle(style1);



		CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 6);
		String fileName = "attachment; filename=Report ME3- Project.xlsx";

		CommonFunctions.downloadExcel(response, workbook, fileName);

		return "reports/projWiseStatusBaselArea";

	}
	
	@RequestMapping(value = "/downloadExcelStateWiseCurrentStatusPlanAchieve", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelStateWiseCurrentStatusPlanAchieve(HttpServletRequest request, HttpServletResponse response) 
	{
		String state=request.getParameter("state");
		
		List<StateWiseCurrentStatusBean> list = new ArrayList<StateWiseCurrentStatusBean>();
		
        for (List<StateWiseCurrentStatusBean> l : curntst.getStateWiseCurrentStatusPlanAchieve().values())
		for(StateWiseCurrentStatusBean bean : l){
			 list.add(bean);	
		}
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Report ME5- State and Year wise Current Status of Plan and Achievement ");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Report ME5- State and Year wise Current Status of Plan and Achievement ";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 10, areaAmtValDetail, workbook);
			
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
        
	        mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
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
			cell.setCellValue("Total Projects Sanctioned");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.VERTICAL_ALIGNMENT, VerticalAlignment.CENTER);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Financial Year(2022-23)");  
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(4).setCellStyle(style);
			
			cell = rowhead.createCell(5);
			cell.setCellValue("Financial Year(2023-24)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(6).setCellStyle(style);
			
			cell = rowhead.createCell(7);
			cell.setCellValue("Financial Year(2024-25)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(8).setCellStyle(style);
			
			cell = rowhead.createCell(9);
			cell.setCellValue("Financial Year(2025-26)");
			cell.setCellStyle(style);
			CellUtil.setCellStyleProperty(cell, CellUtil.ALIGNMENT, HorizontalAlignment.CENTER);
			rowhead.createCell(10).setCellStyle(style);
			
			Row rowhead1 = sheet.createRow(6);
			for(int i =0; i<3;i++) {
				rowhead1.createCell(i).setCellStyle(style);
			}
			
			cell = rowhead1.createCell(3);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			
			cell = rowhead1.createCell(4);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(5);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(6);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(7);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(8);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(9);
			cell.setCellValue("No. of Project Plan Created");  
			cell.setCellStyle(style);
			cell = rowhead1.createCell(10);
			cell.setCellValue("No. of Project Achievement Created");  
			cell.setCellStyle(style);
			
			
			Row rowhead2 = sheet.createRow(7);
			for(int i=0;i<11;i++)
			{
				cell =rowhead2.createCell(i);
				cell.setCellValue(i+1);
				cell.setCellStyle(style);
			}
			
	        
	        int sno = 1;
	        int rowno  = 8;
	        int ProjSan = 0;
	        int ProjPlan22 = 0;
	        int ProjAch22 = 0;
	        int ProjPlan23 = 0;
	        int ProjAch23 = 0;
	        int ProjPlan24 = 0;
	        int ProjAch24 = 0;
	        int ProjPlan25 = 0;
	        int ProjAch25 = 0;
	        
	        
	        for(StateWiseCurrentStatusBean bean: list) {
	        	Row row = sheet.createRow(rowno);
	        	row.createCell(0).setCellValue(sno); 
	        	row.createCell(1).setCellValue(bean.getSt_name());
	        	row.createCell(2).setCellValue(bean.getTotal_project());
				row.createCell(3).setCellValue(bean.getTotal_project_plan2022());  
				row.createCell(4).setCellValue(bean.getTotal_project_achievement2022()); 
				row.createCell(5).setCellValue(bean.getTotal_project_plan2023());  
				row.createCell(6).setCellValue(bean.getTotal_project_achievement2023());
				row.createCell(7).setCellValue(bean.getTotal_project_plan2024());
				row.createCell(8).setCellValue(bean.getTotal_project_achievement2024());
				row.createCell(9).setCellValue(bean.getTotal_project_plan2025());
				row.createCell(10).setCellValue(bean.getTotal_project_achievement2025());
	        	
				ProjSan = ProjSan + bean.getTotal_project();
				ProjPlan22 = ProjPlan22 + bean.getTotal_project_plan2022();
				ProjAch22 = ProjAch22 + bean.getTotal_project_achievement2022();
				ProjPlan23 = ProjPlan23 + bean.getTotal_project_plan2023();
				ProjAch23 = ProjAch23 + bean.getTotal_project_achievement2023();
				ProjPlan24 = ProjPlan24 + bean.getTotal_project_plan2024();
				ProjAch24 = ProjAch24 + bean.getTotal_project_achievement2024();
				ProjPlan25 = ProjPlan25 + bean.getTotal_project_plan2025();
				ProjAch25 = ProjAch25 + bean.getTotal_project_achievement2025();
				
				
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
	        cell.setCellValue(ProjSan);
	        cell.setCellStyle(style1);
	        cell = row.createCell(3);
	        cell.setCellValue(ProjPlan22);
	        cell.setCellStyle(style1);
	        cell = row.createCell(4);
	        cell.setCellValue(ProjAch22);
	        cell.setCellStyle(style1);
	        cell = row.createCell(5);
	        cell.setCellValue(ProjPlan23);
	        cell.setCellStyle(style1);
	        cell = row.createCell(6);
	        cell.setCellValue(ProjAch23);
	        cell.setCellStyle(style1);
	        cell = row.createCell(7);
	        cell.setCellValue(ProjPlan24);
	        cell.setCellStyle(style1);
	        cell = row.createCell(8);
	        cell.setCellValue(ProjAch24);
	        cell.setCellStyle(style1);
	        cell = row.createCell(9);
	        cell.setCellValue(ProjPlan25);
	        cell.setCellStyle(style1);
	        cell = row.createCell(10);
	        cell.setCellValue(ProjAch25);
	        cell.setCellStyle(style1);
	       
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 10);
	        String fileName = "attachment; filename=Report ME5- State.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "reports/stateWiseCurrentStatusPlanAchieve";
		
	}
	
	@RequestMapping(value = "/downloadExcelprojWiseCurrentStatusOther", method = RequestMethod.POST)
	@ResponseBody
	public String downloadExcelprojWiseCurrentStatusOther(HttpServletRequest request, HttpServletResponse response) 
	{
		String dcode=request.getParameter("dcode");
		String statename=request.getParameter("statename");
		String distName=request.getParameter("distName");
	    List<StateWiseCurrentStatusBean> list= new ArrayList<StateWiseCurrentStatusBean>();
		 list = curntst.projWiseCurrentStatusOther(Integer.parseInt(dcode));
			
		
		Workbook workbook = new XSSFWorkbook();  
		//invoking creatSheet() method and passing the name of the sheet to be created   
		Sheet sheet = workbook.createSheet("Report ME2- Project-wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details");   
		
		CellStyle style = CommonFunctions.getStyle(workbook);
	    
		String rptName = "Report ME2- Project-wise Current Status of Project Plans Created, No. of WCDC and PIA Accounts and No. of Projects with Baseline Ground Water Details";
		String areaAmtValDetail ="";
		
		CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
		CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 5, areaAmtValDetail, workbook);
		
		mergedRegion = new CellRangeAddress(list.size()+8,list.size()+8,0,1); 
		sheet.addMergedRegion(mergedRegion);
		mergedRegion = new CellRangeAddress(5,5,0,5); 
		sheet.addMergedRegion(mergedRegion);
		
		Row rowDetail = sheet.createRow(5);
		
		Cell cell = rowDetail.createCell(0);
		cell.setCellValue("State : "+ statename+"     District : "+distName);  
		cell.setCellStyle(style);
		
		Row rowhead = sheet.createRow(6);
		
		cell = rowhead.createCell(0);
		cell.setCellValue("S.No.");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(1);
		cell.setCellValue("Project Name");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(2);
		cell.setCellValue("Total no. of Village Covered in Project");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(3);
		cell.setCellValue("No. of Project for Permission given");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(4);
		cell.setCellValue("No. of Project Plan Created");  
		cell.setCellStyle(style);
		
		cell = rowhead.createCell(5);
		cell.setCellValue("No. of Project with Baseline Ground Water");  
		cell.setCellStyle(style);

			
		Row rowhead1 = sheet.createRow(7);
		
		for(int i=0;i<6;i++)
		{
			cell =rowhead1.createCell(i);
			cell.setCellValue(i+1);
			cell.setCellStyle(style);
		}
		
		int sno = 1;
		int rowno  = 8;
		int totcvil = 0;
		int totprojper = 0;
		double totprojplan=0.0;
		double totblsground=0.0;
		
		
	    for(StateWiseCurrentStatusBean bean: list) {
	    	Row row = sheet.createRow(rowno);
	    	row.createCell(0).setCellValue(sno); 
	    	row.createCell(1).setCellValue(bean.getProj_name());
	    	row.createCell(2).setCellValue(bean.getCovered_village());
	    	row.createCell(3).setCellValue(bean.getTot_proj_permission());
	    	row.createCell(4).setCellValue(bean.getTotal_project_plan());
	    	row.createCell(5).setCellValue(bean.getTot_proj_basel_ground());
	    	
	    	
	    	totcvil = totcvil + bean.getCovered_village();
	    	totprojper = totprojper + bean.getTot_proj_permission();
	    	totprojplan = totprojplan + bean.getTotal_project_plan();
	    	totblsground = totblsground + bean.getTot_proj_basel_ground();

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
		cell.setCellValue(totcvil);
		cell.setCellStyle(style1);
		cell = row.createCell(3);
		cell.setCellValue(totprojper);
		cell.setCellStyle(style1);
		cell = row.createCell(4);
		cell.setCellValue(totprojplan);
		cell.setCellStyle(style1);
		cell = row.createCell(5);
		cell.setCellValue(totblsground);
		cell.setCellStyle(style1);

		
	    CommonFunctions.getExcelFooter(sheet, mergedRegion, list.size(), 5);
	    String fileName = "attachment; filename=Report ME2- State.xlsx";
	    
	    CommonFunctions.downloadExcel(response, workbook, fileName);

	    return "reports/projectWiseCurrentStatusOther";
	}

}
