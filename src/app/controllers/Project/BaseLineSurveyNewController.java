package app.controllers.Project;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import app.bean.AddOutcomeParaBean;
import app.bean.Login;
import app.bean.NewBaseLineSurveyBean;
import app.bean.reports.UserUploadDetailsBean;
import app.common.CommonFunctions;
import app.model.BlsOutDetail;
import app.model.BlsOutDetailCrop;
import app.model.BlsOutDetailCropAchiev;
import app.model.MBlsOutcome;
import app.service.ProjectMasterService;
import app.service.outcome.BaseLineOutcomeService;

@Controller("BaseLineSurveyNewController")
public class BaseLineSurveyNewController {
	
	HttpSession session=null;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	@Autowired(required = true)
	BaseLineOutcomeService bso;
	
	@RequestMapping(value="/addBaselineNewDetComp", method = RequestMethod.GET)   //  addBaselineNewDetComp
	public ModelAndView addBaselineNewDetComp(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("project/addBaseLineSurveyNew");
		    mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/getBaselineNewDraft", method = RequestMethod.POST)  
	public ModelAndView getBaselineNewDraft(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String project= request.getParameter("ddlProject");
		Integer vill= Integer.parseInt(request.getParameter("village"));
		HashMap<Integer,String> res=new HashMap<Integer,String>();
		try {
			res=bso.getVillageOfProject(Integer.parseInt(project));
		}catch(Exception ex) {
			res=null;
		}
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		if(session!=null && session.getAttribute("loginID")!=null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("project/addBaseLineSurveyNew");
		    
		    List<NewBaseLineSurveyBean> list= new ArrayList<NewBaseLineSurveyBean>();
		    List<NewBaseLineSurveyBean> villList= new ArrayList<NewBaseLineSurveyBean>();
		    villList = bso.getBaselineNewDraft(Integer.parseInt(project));
		    if(vill!=0) {
		    	for (NewBaseLineSurveyBean chkbean : villList) {
		    		if(chkbean.getVcode().equals(vill)) {
		    			list.add(chkbean);
		    		}
		    	}
		    }else {
		    	list = villList;
		    }
		    
		    
		    List<String> invalidVill = new ArrayList<>();
		    List<String> invalidPLotArea = new ArrayList<>();
		    List<String> allPlotNo = new ArrayList<>();
		    List<String> chkCropType = new ArrayList<>();
		    List<String> dupliPlotNo = new ArrayList<>();
		    boolean validate = false;
		    String noCrop = "";
		    for (NewBaseLineSurveyBean chkbean : list) 
		    	{
		    	for(int i =0;i<allPlotNo.size();i++) {
		    		noCrop =chkbean.getNo_crop()==null?"":chkbean.getNo_crop();
		    		if(allPlotNo.get(i).equals(chkbean.getPlot_no() + " "+chkbean.getVillage_name()) && ((noCrop.equalsIgnoreCase("Single Crop") && chkCropType.get(i).equals(chkbean.getCrop_type()))||noCrop.equalsIgnoreCase("No Crop"))) {
		    			if(!invalidVill.contains(chkbean.getVillage_name()))
	    		    		invalidVill.add(chkbean.getVillage_name());
		    			if(!dupliPlotNo.contains(chkbean.getPlot_no()+" "+chkbean.getVillage_name())) {
		    				dupliPlotNo.add(chkbean.getPlot_no()+" "+chkbean.getVillage_name());
		    				dupliPlotNo.add(chkbean.getPlot_no());
		    			}
		    			validate = true;
		    		}
		    	}
		    	allPlotNo.add(chkbean.getPlot_no() + " "+chkbean.getVillage_name());
		    	chkCropType.add(chkbean.getCrop_type()==null?"check":chkbean.getCrop_type());
		    	if(!chkbean.getPlot_no().matches("^[a-zA-Z0-9-/*]*$") || chkbean.getArea()==null || chkbean.getArea().doubleValue()==0 || chkbean.getArea().toString().equals("") ) {
    		    	if(!invalidVill.contains(chkbean.getVillage_name()))
    		    		invalidVill.add(chkbean.getVillage_name());
    		    	if(!chkbean.getPlot_no().matches("^[a-zA-Z0-9-/*]*$") && (chkbean.getArea().doubleValue()==0|| chkbean.getArea()==null|| chkbean.getArea().toString().equals(""))) {
    		    		if(!dupliPlotNo.contains(chkbean.getPlot_no()+" "+chkbean.getVillage_name())) {
    		    			dupliPlotNo.add(chkbean.getPlot_no()+" "+chkbean.getVillage_name());
    		    			dupliPlotNo.add(chkbean.getPlot_no());
    		    		}
    		    		if(chkbean.getArea()==null) {}
    		    		else
    		    			invalidPLotArea.add(chkbean.getArea().toString());
    		    	}else if(!chkbean.getPlot_no().matches("^[a-zA-Z0-9-/*]*$")) {
    		    		if(!dupliPlotNo.contains(chkbean.getPlot_no()+" "+chkbean.getVillage_name())){
    		    			dupliPlotNo.add(chkbean.getPlot_no()+" "+chkbean.getVillage_name());
    		    			dupliPlotNo.add(chkbean.getPlot_no());
    		    		}
    		    	}else if(chkbean.getArea()==null || chkbean.getArea().doubleValue()==0 || chkbean.getArea().toString().equals("")) {
    		    		if(chkbean.getArea()==null) {}
    		    		else
    		    			invalidPLotArea.add(chkbean.getArea().toString());    		    		
    		    	}
    		    	validate = true;
    		    }
    }if(validate) {
    	mav.addObject("msg", "The highlighted data is not correct, please check that plot no or plot area is not in the correct format or that the plot no is not a duplicate. Please delete and insert a new valid data for the highlighted plot no and plot area.");
		mav.addObject("plotValidation",invalidVill);
		mav.addObject("plotAreaValidation",invalidPLotArea);
		mav.addObject("duplicatePlotNo",dupliPlotNo);
    }
		    
			int i = 0;
			for (NewBaseLineSurveyBean bean : list) 
			{
				data = new String[28];
				i++;
				data[0] = String.valueOf(i);// serial no
				data[1] = bean.getProj_id().toString();
				data[2] = bean.getVcode().toString();
				data[3] = bean.getVillage_name();
				data[4] = bean.getPlot_no();
				data[5] = bean.getArea()==null?"":bean.getArea().toString();
				if(bean.getOwner_name()!=null) {
					data[6] = bean.getOwner_name();
				}
				else {
					data[6] = "";
				}
				if(bean.getIrrigation_status_id()!=null) {
					data[8] = bean.getIrrigation_status_id().toString();
					data[9] = bean.getIrrigation();
				}
				else {
					data[8] = "";
					data[9] = "";
				}
				data[10] = String.valueOf(bean.getStatus());
				if(bean.getClassification_land_id()!=null) {
					data[11] = bean.getClassification_land_id().toString();
					String subClassLand= (bean.getClassification_land().equals("Others"))?bean.getClassification_land()+" ("+bean.getLand_sub_classification()+")":bean.getClassification_land();
					data[12] = subClassLand;
				}
				else {
					data[11] ="";
					data[12] =""; 		
				}
				if(bean.getNo_of_crop_id()!=null) {
					data[13] = bean.getNo_of_crop_id().toString();
					data[14] = bean.getNo_crop();
				}
				else {
					data[13] = "";
					data[14] = "";
				}
				if(bean.getForest_land_type_id()!= null) {
					data[15] = bean.getForest_land_type_id().toString();
					data[16] = bean.getForest_land();
				}else { 
					data[15] = "";
					data[16] = "";
				}
				if(bean.getCrop_type_id()!= null) {
					data[17] = bean.getCrop_type_id().toString();
					data[18] = bean.getCrop_type();
				}
				else {
					data[17] ="";
					data[18] ="";
				}
				if(bean.getCrop_production()!=null)
					data[19] = bean.getCrop_production().toString();
				else
					data[19] ="";
				
				if(bean.getAvg_income()!=null)
					data[20] = bean.getAvg_income().toString();
				else
					data[20] ="";
				
				if(bean.getCrop_area()!=null)
					data[21] = bean.getCrop_area().toString();
				else
					data[21] ="";
				
				if(bean.getSeason_id()!=null) {
					data[22] = bean.getSeason_id().toString();
					data[23] = bean.getSeason();
				}
				else {
					data[22] ="";
					data[23] ="";
				}
				data[24] = bean.getBls_out_main_id_pk().toString();
				data[7] = bean.getBls_out_detail_id_pk().toString();
				if(bean.getBls_out_detail_tranx_id_pk()!=null)
					data[25] = bean.getBls_out_detail_tranx_id_pk().toString();	
				else
					data[25] ="";
				
				if(bean.getOwnership_id()!= null) {
					data[26] = bean.getOwnership_id().toString();
					data[27] = bean.getOwnership();
				}
				else {
					data[26] ="";
					data[27] ="";
				}
				dataList.add(data);
			}
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());
		    mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
		    mav.addObject("projectcd", project);
		    mav.addObject("villListSize",villList.size());
		    mav.addObject("villList",res);
		    mav.addObject("vcode",vill);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/deleteBaselineNewDraft", method = RequestMethod.POST)   
	public ModelAndView deleteBaselineNewDraft(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String project= request.getParameter("ddlProject");
		Integer vill= Integer.parseInt(request.getParameter("village"));
		String main= request.getParameter("main");
		String detail= request.getParameter("detail");
		String detailtrans= request.getParameter("detailtrans");
		
		HashMap<Integer,String> res=new HashMap<Integer,String>();
		try {
			res=bso.getVillageOfProject(Integer.parseInt(project));
		}catch(Exception ex) {
			res=null;
		}
		boolean delete=false;
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("project/addBaseLineSurveyNew");
		    
		    delete=bso.deleteBaselineNewDraft(Integer.parseInt(main), Integer.parseInt(detail), detailtrans);
		    if(delete) 
				mav.addObject("message", "Deleted Successfully");
		    else
				mav.addObject("message", "Not delete technical issue?");
		    
		    List<NewBaseLineSurveyBean> list= new ArrayList<NewBaseLineSurveyBean>();
		    List<NewBaseLineSurveyBean> villList= new ArrayList<NewBaseLineSurveyBean>();
		    villList = bso.getBaselineNewDraft(Integer.parseInt(project));
		    if(vill!=0) {
		    	for (NewBaseLineSurveyBean chkbean : villList) {
		    		if(chkbean.getVcode().equals(vill)) {
		    			list.add(chkbean);
		    		}
		    	}
		    }else {
		    	list = villList;
		    }
		    
		    List<String> invalidVill = new ArrayList<>();
		    List<String> invalidPLotArea = new ArrayList<>();
		    List<String> allPlotNo = new ArrayList<>();
		    List<String> chkCropType = new ArrayList<>();
		    List<String> dupliPlotNo = new ArrayList<>();
		    boolean validate = false;
		    String noCrop = "";
		    for (NewBaseLineSurveyBean chkbean : list) 
		    	{
		    	for(int i =0;i<allPlotNo.size();i++) {
		    		noCrop =chkbean.getNo_crop()==null?"":chkbean.getNo_crop();
		    		if(allPlotNo.get(i).equals(chkbean.getPlot_no() + " "+chkbean.getVillage_name()) && ((noCrop.equalsIgnoreCase("Single Crop") && chkCropType.get(i).equals(chkbean.getCrop_type()))||noCrop.equalsIgnoreCase("No Crop"))) {
		    			if(!invalidVill.contains(chkbean.getVillage_name()))
	    		    		invalidVill.add(chkbean.getVillage_name());
		    			if(!dupliPlotNo.contains(chkbean.getPlot_no()+" "+chkbean.getVillage_name())) {
		    				dupliPlotNo.add(chkbean.getPlot_no()+" "+chkbean.getVillage_name());
		    				dupliPlotNo.add(chkbean.getPlot_no());
		    			}
		    			validate = true;
		    		}
		    	}
		    	allPlotNo.add(chkbean.getPlot_no() + " "+chkbean.getVillage_name());
		    	chkCropType.add(chkbean.getCrop_type()==null?"check":chkbean.getCrop_type());
		    	if(!chkbean.getPlot_no().matches("^[a-zA-Z0-9-/*]*$") || chkbean.getArea()==null || chkbean.getArea().doubleValue()==0 || chkbean.getArea().toString().equals("") ) {
    		    	if(!invalidVill.contains(chkbean.getVillage_name()))
    		    		invalidVill.add(chkbean.getVillage_name());
    		    	if(!chkbean.getPlot_no().matches("^[a-zA-Z0-9-/*]*$") && (chkbean.getArea().doubleValue()==0|| chkbean.getArea()==null|| chkbean.getArea().toString().equals(""))) {
    		    		if(!dupliPlotNo.contains(chkbean.getPlot_no()+" "+chkbean.getVillage_name())) {
    		    			dupliPlotNo.add(chkbean.getPlot_no()+" "+chkbean.getVillage_name());
    		    			dupliPlotNo.add(chkbean.getPlot_no());
    		    		}
    		    		if(chkbean.getArea()==null) {}
    		    		else
    		    			invalidPLotArea.add(chkbean.getArea().toString());
    		    	}else if(!chkbean.getPlot_no().matches("^[a-zA-Z0-9-/*]*$")) {
    		    		if(!dupliPlotNo.contains(chkbean.getPlot_no()+" "+chkbean.getVillage_name())){
    		    			dupliPlotNo.add(chkbean.getPlot_no()+" "+chkbean.getVillage_name());
    		    			dupliPlotNo.add(chkbean.getPlot_no());
    		    		}
    		    	}else if(chkbean.getArea()==null || chkbean.getArea().doubleValue()==0 || chkbean.getArea().toString().equals("")) {
    		    		if(chkbean.getArea()==null) {}
    		    		else
    		    			invalidPLotArea.add(chkbean.getArea().toString());    		    		
    		    	}
    		    	validate = true;
    		    }
    }if(validate) {
    	mav.addObject("msg", "The highlighted data is not correct, please check that plot no or plot area is not in the correct format or that the plot no is not a duplicate. Please delete and insert a new valid data for the highlighted plot no and plot area.");
		mav.addObject("plotValidation",invalidVill);
		mav.addObject("plotAreaValidation",invalidPLotArea);
		mav.addObject("duplicatePlotNo",dupliPlotNo);
    }
		    
			int i = 0;
			for (NewBaseLineSurveyBean bean : list) 
			{
				data = new String[28];
				i++;
				data[0] = String.valueOf(i);// serial no
				data[1] = bean.getProj_id().toString();
				data[2] = bean.getVcode().toString();
				data[3] = bean.getVillage_name();
				data[4] = bean.getPlot_no();
				data[5] = bean.getArea()==null?"":bean.getArea().toString();
				if(bean.getOwner_name()!=null) {
					data[6] = bean.getOwner_name();
				}
				else {
					data[6] = "";
				}
				if(bean.getIrrigation_status_id()!=null) {
					data[8] = bean.getIrrigation_status_id().toString();
					data[9] = bean.getIrrigation();
				}
				else {
					data[8] = "";
					data[9] = "";
				}
				data[10] = String.valueOf(bean.getStatus());
				if(bean.getClassification_land_id()!=null) {
					data[11] = bean.getClassification_land_id().toString();
					String subClassLand= (bean.getClassification_land().equals("Others"))?bean.getClassification_land()+" ("+bean.getLand_sub_classification()+")":bean.getClassification_land();
					data[12] = subClassLand;
				}
				else {
					data[11] ="";
					data[12] =""; 		
				}
				if(bean.getNo_of_crop_id()!=null) {
					data[13] = bean.getNo_of_crop_id().toString();
					data[14] = bean.getNo_crop();
				}
				else {
					data[13] = "";
					data[14] = "";
				}
				if(bean.getForest_land_type_id()!= null) {
					data[15] = bean.getForest_land_type_id().toString();
					data[16] = bean.getForest_land();
				}else { 
					data[15] = "";
					data[16] = "";
				}
				if(bean.getCrop_type_id()!= null) {
					data[17] = bean.getCrop_type_id().toString();
					data[18] = bean.getCrop_type();
				}
				else {
					data[17] ="";
					data[18] ="";
				}
				if(bean.getCrop_production()!=null)
					data[19] = bean.getCrop_production().toString();
				else
					data[19] ="";
				
				if(bean.getAvg_income()!=null)
					data[20] = bean.getAvg_income().toString();
				else
					data[20] ="";
				
				if(bean.getCrop_area()!=null)
					data[21] = bean.getCrop_area().toString();
				else
					data[21] ="";
				
				if(bean.getSeason_id()!=null) {
					data[22] = bean.getSeason_id().toString();
					data[23] = bean.getSeason();
				}
				else {
					data[22] ="";
					data[23] ="";
				}
				data[24] = bean.getBls_out_main_id_pk().toString();
				data[7] = bean.getBls_out_detail_id_pk().toString();
				if(bean.getBls_out_detail_tranx_id_pk()!=null)
					data[25] = bean.getBls_out_detail_tranx_id_pk().toString();	
				else
					data[25] ="";
				
				if(bean.getOwnership_id()!= null) {
					data[26] = bean.getOwnership_id().toString();
					data[27] = bean.getOwnership();
				}
				else {
					data[26] ="";
					data[27] ="";
				}
				
				dataList.add(data);
			}
			mav.addObject("dataList", dataList);
			mav.addObject("dataListsize", dataList.size());
		    mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
		    mav.addObject("projectcd", project);
		    mav.addObject("villListSize",villList.size());
		    mav.addObject("villList",res);
		    mav.addObject("vcode",vill);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value="/completeBaselineNewDraft", method = RequestMethod.POST)   
	public ModelAndView completeBaselineNewDraft(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		String project= request.getParameter("ddlProject");
		String villcode[]= request.getParameterValues("villcode");
		
		HashMap<Integer,String> res=new HashMap<Integer,String>();
		try {
			res=bso.getVillageOfProject(Integer.parseInt(project));
		}catch(Exception ex) {
			res=null;
		}
		boolean delete=false;
		ModelAndView mav = new ModelAndView();
		String data[] = null;
		ArrayList dataList = new ArrayList();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
		    mav = new ModelAndView("project/addBaseLineSurveyNew");
		    
		    List<NewBaseLineSurveyBean> chklist= new ArrayList<NewBaseLineSurveyBean>();
		    chklist = bso.getBaselineNewDraft(Integer.parseInt(project));
		    List<String> village = new ArrayList<>();
		    List<String> invalidVill = new ArrayList<>();
		    List<String> invalidPLotArea = new ArrayList<>();
		    List<String> allPlotNo = new ArrayList<>();
		    List<String> chkCropType = new ArrayList<>();
		    List<String> dupliPlotNo = new ArrayList<>();
		    boolean validatePlot = false;
		    boolean validate = false;
		    if(villcode!=null) {
		    for (NewBaseLineSurveyBean bean : chklist) 
			{
		    	if(Arrays.asList(villcode).contains(bean.getVcode().toString())) {
		    		if(bean.getPlot_no().matches("^[a-zA-Z0-9-/*]*$") && (bean.getArea()!=null || bean.getArea().doubleValue()!=0 || !bean.getArea().toString().equals(""))) {
		    			if(!village.contains(bean.getVcode().toString()) && !invalidVill.contains(bean.getVillage_name())) {
		    				village.add(bean.getVcode().toString()) ;
		    			}
		    			validatePlot = true;
		    		}else if(village.contains(bean.getVcode().toString())){
		    			village.remove(village.size()-1);
		    			validatePlot = false;
		    			invalidVill.add(bean.getVillage_name());
		    		}else {
		    			invalidVill.add(bean.getVillage_name());
		    		}
		    	}
			}if(validatePlot) {
		    	delete=bso.completeBaselineNewDraft(Integer.parseInt(project), village);
		    	if(delete) 
		    		mav.addObject("message", "Completed Successfully");
		    	else
		    		mav.addObject("message", "Not complete technical issue?");
		    }
			invalidVill = new ArrayList<>();
			String noCrop = "";
			for (NewBaseLineSurveyBean chkbean : chklist) 
	    	{
		    	for(int i =0;i<allPlotNo.size();i++) {
		    		noCrop =chkbean.getNo_crop()==null?"":chkbean.getNo_crop();
		    		if(allPlotNo.get(i).equals(chkbean.getPlot_no() + " "+chkbean.getVillage_name()) && ((noCrop.equalsIgnoreCase("Single Crop") && chkCropType.get(i).equals(chkbean.getCrop_type()))||noCrop.equalsIgnoreCase("No Crop"))) {
		    			if(!invalidVill.contains(chkbean.getVillage_name()))
	    		    		invalidVill.add(chkbean.getVillage_name());
		    			if(!dupliPlotNo.contains(chkbean.getPlot_no()+" "+chkbean.getVillage_name())) {
		    				dupliPlotNo.add(chkbean.getPlot_no()+" "+chkbean.getVillage_name());
		    				dupliPlotNo.add(chkbean.getPlot_no());
		    			}
		    			validate = true;
		    		}
		    	}
		    	allPlotNo.add(chkbean.getPlot_no() + " "+chkbean.getVillage_name());
		    	chkCropType.add(chkbean.getCrop_type()==null?"check":chkbean.getCrop_type());
		    	if(!chkbean.getPlot_no().matches("^[a-zA-Z0-9-/*]*$") || chkbean.getArea()==null || chkbean.getArea().doubleValue()==0 || chkbean.getArea().toString().equals("") ) {
    		    	if(!invalidVill.contains(chkbean.getVillage_name()))
    		    		invalidVill.add(chkbean.getVillage_name());
    		    	if(!chkbean.getPlot_no().matches("^[a-zA-Z0-9-/*]*$") && (chkbean.getArea().doubleValue()==0|| chkbean.getArea()==null|| chkbean.getArea().toString().equals(""))) {
    		    		if(!dupliPlotNo.contains(chkbean.getPlot_no()+" "+chkbean.getVillage_name())) {
    		    			dupliPlotNo.add(chkbean.getPlot_no()+" "+chkbean.getVillage_name());
    		    			dupliPlotNo.add(chkbean.getPlot_no());
    		    		}
    		    		if(chkbean.getArea()==null) {}
    		    		else
    		    			invalidPLotArea.add(chkbean.getArea().toString());
    		    	}else if(!chkbean.getPlot_no().matches("^[a-zA-Z0-9-/*]*$")) {
    		    		if(!dupliPlotNo.contains(chkbean.getPlot_no()+" "+chkbean.getVillage_name())){
    		    			dupliPlotNo.add(chkbean.getPlot_no()+" "+chkbean.getVillage_name());
    		    			dupliPlotNo.add(chkbean.getPlot_no());
    		    		}
    		    	}else if(chkbean.getArea()==null || chkbean.getArea().doubleValue()==0 || chkbean.getArea().toString().equals("")) {
    		    		if(chkbean.getArea()==null) {}
    		    		else
    		    			invalidPLotArea.add(chkbean.getArea().toString());    		    		
    		    	}
    		    	validate = true;
    		    }
	    	}if(validate) {
	        	mav.addObject("msg", "The highlighted data is not correct, please check that plot no or plot area is not in the correct format or that the plot no is not a duplicate. Please delete and insert a new valid data for the highlighted plot no and plot area.");
	    		mav.addObject("plotValidation",invalidVill);
	    		mav.addObject("plotAreaValidation",invalidPLotArea);
	    		mav.addObject("duplicatePlotNo",dupliPlotNo);
	        }
		    }
		    else {
	    		mav.addObject("msg", "Please Select the Villages to Complete/Locked the data !");
	    	}
	    	
		   
		    
		    List<NewBaseLineSurveyBean> list= new ArrayList<NewBaseLineSurveyBean>();
		    list = bso.getBaselineNewDraft(Integer.parseInt(project));
		    
		    
			int i = 0;
			for (NewBaseLineSurveyBean bean : list) 
			{
				data = new String[28];
				i++;
				data[0] = String.valueOf(i);// serial no
				data[1] = bean.getProj_id().toString();
				data[2] = bean.getVcode().toString();
				data[3] = bean.getVillage_name();
				data[4] = bean.getPlot_no();
				data[5] = bean.getArea()==null?"":bean.getArea().toString();
				if(bean.getOwner_name()!=null) {
					data[6] = bean.getOwner_name();
				}
				else {
					data[6] = "";
				}
				if(bean.getIrrigation_status_id()!=null) {
					data[8] = bean.getIrrigation_status_id().toString();
					data[9] = bean.getIrrigation();
				}
				else {
					data[8] = "";
					data[9] = "";
				}
				data[10] = String.valueOf(bean.getStatus());
				if(bean.getClassification_land_id()!=null) {
					data[11] = bean.getClassification_land_id().toString();
					String subClassLand= (bean.getClassification_land().equals("Others"))?bean.getClassification_land()+" ("+bean.getLand_sub_classification()+")":bean.getClassification_land();
					data[12] = subClassLand;
				}
				else {
					data[11] ="";
					data[12] =""; 		
				}
				if(bean.getNo_of_crop_id()!=null) {
					data[13] = bean.getNo_of_crop_id().toString();
					data[14] = bean.getNo_crop();
				}
				else {
					data[13] = "";
					data[14] = "";
				}
				if(bean.getForest_land_type_id()!= null) {
					data[15] = bean.getForest_land_type_id().toString();
					data[16] = bean.getForest_land();
				}else { 
					data[15] = "";
					data[16] = "";
				}
				if(bean.getCrop_type_id()!= null) {
					data[17] = bean.getCrop_type_id().toString();
					data[18] = bean.getCrop_type();
				}
				else {
					data[17] ="";
					data[18] ="";
				}
				if(bean.getCrop_production()!=null)
					data[19] = bean.getCrop_production().toString();
				else
					data[19] ="";
				
				if(bean.getAvg_income()!=null)
					data[20] = bean.getAvg_income().toString();
				else
					data[20] ="";
				
				if(bean.getCrop_area()!=null)
					data[21] = bean.getCrop_area().toString();
				else
					data[21] ="";
				
				if(bean.getSeason_id()!=null) {
					data[22] = bean.getSeason_id().toString();
					data[23] = bean.getSeason();
				}
				else {
					data[22] ="";
					data[23] ="";
				}
				data[24] = bean.getBls_out_main_id_pk().toString();
				data[7] = bean.getBls_out_detail_id_pk().toString();
				if(bean.getBls_out_detail_tranx_id_pk()!=null)
					data[25] = bean.getBls_out_detail_tranx_id_pk().toString();	
				else
					data[25] ="";
				
				if(bean.getOwnership_id()!= null) {
					data[26] = bean.getOwnership_id().toString();
					data[27] = bean.getOwnership();
				}
				else {
					data[26] ="";
					data[27] ="";
				}
				
				dataList.add(data);
			}
//			mav.addObject("dataList", dataList);
//			mav.addObject("dataListsize", dataList.size());
		    mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
//		    mav.addObject("projectcd", project);
//		    mav.addObject("villListSize",list.size());
//		    mav.addObject("villList",res);
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav; 
	}
	
	@RequestMapping(value = "/baselineDraftdatafind", method = RequestMethod.GET)
	@ResponseBody
	public List<NewBaseLineSurveyBean> baselineDraftdatafind(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") Integer id) {
		session = request.getSession(true);
		List<NewBaseLineSurveyBean> finalList = new ArrayList<NewBaseLineSurveyBean>();
		NewBaseLineSurveyBean bean = new NewBaseLineSurveyBean();
		if (session != null && session.getAttribute("loginID") != null) {

			BlsOutDetailCrop proj = bso.findbaselineDraftdata(id);
			if (proj != null) {
				bean = new NewBaseLineSurveyBean();
				bean.setArea(proj.getCropArea());
				if(proj.getMBlsOutcomeBySeasonId()!=null)
					bean.setSeason_id(proj.getMBlsOutcomeBySeasonId().getMBlsOutIdPk());
				bean.setCrop_production(proj.getCropProduction());
				bean.setCrop_type_id(proj.getMBlsOutcomeByCropTypeId().getMBlsOutIdPk());
				bean.setAvg_income(proj.getAvgIncome());
				bean.setBls_out_detail_id_pk(proj.getBlsOutDetail().getBlsOutDetailIdPk());
				bean.setBls_out_main_id_pk(proj.getBlsOutDetail().getBlsOutMain().getBlsOutMainIdPk());
				bean.setBls_out_detail_tranx_id_pk(proj.getBlsOutDetailTranxIdPk());
				bean.setVcode(proj.getBlsOutDetail().getBlsOutMain().getIwmpVillage().getVcode());
				bean.setPlot_no(proj.getBlsOutDetail().getBlsOutMain().getPlotNo());
				bean.setProj_id(proj.getBlsOutDetail().getBlsOutMain().getIwmpMProject().getProjectId());
				finalList.add(bean);
			}

		} else {

		}
		return finalList;
	}
	
	@RequestMapping(value = "/updateCropDraftData", method = RequestMethod.POST)
	@ResponseBody
	public String updateCropDraftData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("bsl_crop_id") int id, @RequestParam("bsl_detail_id") int bsl_detail_id, @RequestParam("season") String season,
			@RequestParam("ctype") int ctype, @RequestParam("areahac") BigDecimal areahac,
			@RequestParam("crop_prod") BigDecimal crop_prod, @RequestParam("avg_income") BigDecimal avg_income) {

		String res = "fail";
		Integer regId = null;
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("project/addBaseLineSurveyNew");
		try {
			String userId = session.getAttribute("loginID").toString();

			regId = Integer.parseInt(session.getAttribute("regId").toString());
			BlsOutDetailCrop bod = new BlsOutDetailCrop();
			BlsOutDetail boDet = new BlsOutDetail();
			boDet.setBlsOutDetailIdPk(bsl_detail_id);
			bod.setBlsOutDetailTranxIdPk(id);
			bod.setBlsOutDetail(boDet);
			MBlsOutcome obj = new MBlsOutcome();
			if (!season.isEmpty()) {
				obj.setMBlsOutIdPk(Integer.parseInt(season));
				bod.setMBlsOutcomeBySeasonId(obj);
			}
			obj = new MBlsOutcome();
			obj.setMBlsOutIdPk(ctype);
			bod.setMBlsOutcomeByCropTypeId(obj);

			bod.setCropArea(areahac);
			bod.setCropProduction(crop_prod);
			bod.setAvgIncome(avg_income);
			bod.setCreatedBy(session.getAttribute("loginID").toString());
			res = bso.updateDraftWiseCropData(bod);


		} catch (Exception ex) {
			res = null;
			ex.printStackTrace();
		}
		return res;

	}
	
	@RequestMapping(value = "/getDraftCropDataOfPlotProject", method = RequestMethod.POST)
	@ResponseBody
	public List<NewBaseLineSurveyBean> getCropDataOfPlotProject(HttpServletRequest request,
			@RequestParam("plotno") String plotno, @RequestParam("vcode") String vcode,
			@RequestParam("projId") Integer projId) {
		List<NewBaseLineSurveyBean> res = new ArrayList<NewBaseLineSurveyBean>();
		try {

			res = bso.getDraftCropDataOfPlotProject(Integer.parseInt(vcode), plotno, projId);

		} catch (Exception ex) {
			res = null;
		}
		return res;

	}
	
	
	@RequestMapping(value = "/getDraftwiseCheckCropArea", method = RequestMethod.POST)
	@ResponseBody
	public String getDraftwiseCheckCropArea(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("bsl_crop_id") int id, @RequestParam("bsl_detail_id") int bsl_detail_id, @RequestParam("season") String season,
			@RequestParam("areahac") BigDecimal areahac, @RequestParam("ctype") int ctype, @RequestParam("plotno") String plotno, @RequestParam("vcode") String vcode,
			@RequestParam("projId") int projId) {
		String res = "fail";
		List<NewBaseLineSurveyBean> list= new ArrayList<NewBaseLineSurveyBean>();
		try {
	    list = bso.getBaselineNewDraft(projId);
	    
	    BigDecimal cropArea =BigDecimal.ZERO;
	    BigDecimal area = BigDecimal.ZERO;
	    for(NewBaseLineSurveyBean s : list) {
	    	if(season.equals("")) {
	    		if(s.getBls_out_detail_id_pk()==bsl_detail_id && s.getVcode()==Integer.parseInt(vcode) && s.getPlot_no().equals(plotno)) {
	    	    		area = s.getArea();
	    	    		if(s.getBls_out_detail_tranx_id_pk()==id) {
	    	    			cropArea = cropArea.add(areahac);
	    	    		}
	    	    		else {
	    	    			cropArea = cropArea.add(s.getCrop_area());
	    	    		}
	    	    	}
	    	}
	    	else if(s.getBls_out_detail_id_pk()==bsl_detail_id && s.getSeason_id()==Integer.parseInt(season)
	    		&& s.getVcode()==Integer.parseInt(vcode) && s.getPlot_no().equals(plotno)) {
	    		area = s.getArea();
	    		if(s.getBls_out_detail_tranx_id_pk()==id) {
	    			cropArea = cropArea.add(areahac);
	    		}
	    		else {
	    			cropArea = cropArea.add(s.getCrop_area());
	    		}
	    	}
	    }
	    if(cropArea.compareTo(area)==1) {
	    	res = "fail";
	    }else {
	    	res = "success";
	    }
	    	
		} catch (Exception ex) {
			res = null;
		}
		
		
	    return res;
		
	}
	
	@RequestMapping(value = "/getBaselineNewDraftExcel", method = RequestMethod.POST)
	@ResponseBody
	public String getBaselineNewDraftExcel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		int projectId= Integer.parseInt(request.getParameter("projId"));
		 List<NewBaseLineSurveyBean> beanList= new ArrayList<NewBaseLineSurveyBean>();
		beanList= bso.getBaselineNewDraft(projectId);
		
			Workbook workbook = new XSSFWorkbook();  
			//invoking creatSheet() method and passing the name of the sheet to be created   
			Sheet sheet = workbook.createSheet("Finalization of Baseline Survey village wise");   
			
			
			CellStyle style = CommonFunctions.getStyle(workbook);
	        
			String rptName = "Finalization of Baseline Survey village wise";
			String areaAmtValDetail = "";
			
			CellRangeAddress mergedRegion = new CellRangeAddress(0,0,0,0);
			CommonFunctions.getExcelHeader(sheet, mergedRegion, rptName, 14, areaAmtValDetail, workbook);
			
	        
			
			Row rowhead = sheet.createRow(5);
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("Village");
			cell.setCellStyle(style);
			cell = rowhead.createCell(1);
			cell.setCellValue("Plot/Gata No.");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(2);
			cell.setCellValue("Plot Area (in ha.)");
			cell.setCellStyle(style);
			
			cell = rowhead.createCell(3);
			cell.setCellValue("Irrigation Status");
			cell.setCellStyle(style);
			cell = rowhead.createCell(4);
			cell.setCellValue("Ownership");
			cell.setCellStyle(style);
			cell = rowhead.createCell(5);
			cell.setCellValue("Owner Name");
			cell.setCellStyle(style);
			cell = rowhead.createCell(6);
			cell.setCellValue("Classification of Land");
			cell.setCellStyle(style);
			cell = rowhead.createCell(7);
			cell.setCellValue("No. of Crop");
			cell.setCellStyle(style);
			cell = rowhead.createCell(8);
			cell.setCellValue("Forest Land type");
			cell.setCellStyle(style);
			cell = rowhead.createCell(9);
			cell.setCellValue("Season");
			cell.setCellStyle(style);
			cell = rowhead.createCell(10);
			cell.setCellValue("Crop Type");
			cell.setCellStyle(style);
			cell = rowhead.createCell(11);
			cell.setCellValue("Area (in ha) (Col-A)");
			cell.setCellStyle(style);
			cell = rowhead.createCell(12);
			cell.setCellValue("Crop Production per Hectare (in Quintal) (Col-B)");
			cell.setCellStyle(style);
			cell = rowhead.createCell(13);
			cell.setCellValue("Avg. Income per Quintal (in Rs.) (Col-C)");
			cell.setCellStyle(style);
			cell = rowhead.createCell(14);
			cell.setCellValue("Total Income (in Rs.) (A*B*C)");
			cell.setCellStyle(style);
			
			
	        int rowno  =6;
	        int count = 1;
	        String vill = "";
	        String plotno = "";
	        int clsfctnid = 0;
	        
	        double pltArea = 0.0;
	        double crpArea  = 0.0;
	        double crpPrdtn = 0.0;
	        double avgIncm = 0.0;
	        double totIncm = 0.0;
	        
	        BigDecimal totalIncome = BigDecimal.ZERO;
	        for(NewBaseLineSurveyBean bean : beanList) {
	        	if(count != 1 && clsfctnid != bean.getClassification_land_id() && !plotno.equals(bean.getPlot_no())) {
	        		Row row = sheet.createRow(rowno);
	        		cell = row.createCell(2);
	        		cell.setCellValue(pltArea);
	        		cell = row.createCell(11);
	        		cell.setCellValue(crpArea);
	        		cell = row.createCell(12);
	        		cell.setCellValue(crpPrdtn);
	        		cell = row.createCell(13);
	        		cell.setCellValue(avgIncm);
	        		cell = row.createCell(14);
	        		cell.setCellValue(totIncm);
	        		
	        		pltArea = 0.0;
	    	        crpArea  = 0.0;
	    	        crpPrdtn = 0.0;
	    	        avgIncm = 0.0;
	    	        totIncm = 0.0;
	    	        
	    	        rowno++;
	    	        count ++;
	        		
	        	}
	        	Row row = sheet.createRow(rowno);
	        	if(!bean.getVillage_name().equals(vill) || !plotno.equals(bean.getPlot_no())) {
	        		cell = row.createCell(0);
	        		cell.setCellValue(bean.getVillage_name());
	        		cell = row.createCell(1);
		        	cell.setCellValue(bean.getPlot_no());
		        	cell = row.createCell(2);
		        	cell.setCellValue(bean.getArea().doubleValue());
		        	cell = row.createCell(3);
		        	cell.setCellValue(bean.getIrrigation());
		        	cell = row.createCell(4);
		        	cell.setCellValue(bean.getOwnership());
		        	cell = row.createCell(5);
		        	cell.setCellValue(bean.getOwner_name()==null?"":bean.getOwner_name());
		        	cell = row.createCell(6);
		        	cell.setCellValue(bean.getClassification_land());
		        	cell = row.createCell(7);
		        	cell.setCellValue(bean.getNo_crop());
	        	}else {
	        		for(int i = 0; i<8; i++) {
	        			cell = row.createCell(i);
	        		}
	        	}
	        	
	        	
	        	cell = row.createCell(8);
	        	cell.setCellValue(bean.getForest_land()==null?"":bean.getForest_land());
	        	cell = row.createCell(9);
	        	cell.setCellValue(bean.getSeason());
	        	cell = row.createCell(10);
	        	cell.setCellValue(bean.getCrop_type());
	        	cell = row.createCell(11);
	        	cell.setCellValue(bean.getCrop_area()==null?0.0:bean.getCrop_area().doubleValue());
	        	cell = row.createCell(12);
	        	cell.setCellValue(bean.getCrop_production()==null?0.0:bean.getCrop_production().doubleValue());
	        	cell = row.createCell(13);
	        	cell.setCellValue(bean.getAvg_income()==null?0.0:bean.getAvg_income().doubleValue());
	        	if(bean.getCrop_area()!=null)
	        		totalIncome = bean.getCrop_area().multiply(bean.getCrop_production()).multiply(bean.getAvg_income());
	        	cell = row.createCell(14);
	        	cell.setCellValue(totalIncome.doubleValue());
	        	
	        	pltArea = pltArea + bean.getArea().doubleValue();
	        	crpArea = crpArea + bean.getCrop_area().doubleValue();
	        	crpPrdtn = crpPrdtn + bean.getCrop_production().doubleValue();
	        	avgIncm = avgIncm + bean.getAvg_income().doubleValue();
	        	totIncm =  totIncm + totalIncome.doubleValue();
	        	
	        	vill = bean.getVillage_name();
	        	plotno = bean.getPlot_no();
	        	clsfctnid= bean.getClassification_land_id();
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
	        
	        
	        CommonFunctions.getExcelFooter(sheet, mergedRegion, rowno, beanList.size());
	        String fileName = "attachment; filename=Finalization of Baseline Survey village wise.xlsx";
	        
	        CommonFunctions.downloadExcel(response, workbook, fileName);
		
		return "project/addBaseLineSurveyNew";
		
	}

}
