package app.controllers.outcome;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import app.bean.Login;
import app.bean.NewBaseLineSurveyBean;
import app.model.BlsOutMain;
import app.model.IwmpMProject;
import app.model.MBlsOutcome;
import app.service.ProjectMasterService;
import app.service.VillageMasterService;
import app.service.outcome.BaseLineOutcomeService;

@Controller("BaseLineOutcome")
public class BaseLineOutcomeController {
	
	HttpSession session;
	
	@Autowired(required = true)
	BaseLineOutcomeService baseLineOutcomeService;
	
	@Autowired(required = true)
	VillageMasterService villageMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;

	
	@RequestMapping(value = "/blsout", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurvey(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			if(session.getAttribute("roleName").toString().contains("PIA")) {
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				mav = new ModelAndView("baselineOutcome");
				mav.addObject("projectList",baseLineOutcomeService.getProjectByRegId(regId));
			}else {
				return new ModelAndView("pagenotfound");
			}
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		return mav;
	}
	
	
	@RequestMapping(value = "/getVillageOfProject", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<Integer,String> getVillageOfProject(HttpServletRequest request, @RequestParam("projId") Integer projId) {
		HashMap<Integer,String> res=new HashMap<Integer,String>();
	try {
		res=baseLineOutcomeService.getVillageOfProject(projId);
	}catch(Exception ex) {
		res=null;
	}
	return res;
		
	}
	
	
	@RequestMapping(value = "/getOwnership", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getOwnership(HttpServletRequest request) {
		LinkedHashMap<Integer,String> res=new LinkedHashMap<Integer,String>();
	try {
		List<MBlsOutcome> list = baseLineOutcomeService.getOutComeMaster();
		for(MBlsOutcome l : list) {
			if(l.getMBlsOutType().getTypeCode().equalsIgnoreCase("own"))
			res.put(l.getMBlsOutIdPk(), l.getDescription());
		}
		
	}catch(Exception ex) {
		res=null;
		ex.printStackTrace();
	}
	return res;
		
	}
	
	@RequestMapping(value = "/getLandClassification", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getLandClassification(HttpServletRequest request) {
		LinkedHashMap<Integer,String> res=new LinkedHashMap<Integer,String>();
	try {
		List<MBlsOutcome> list = baseLineOutcomeService.getOutComeMaster();
		for(MBlsOutcome l : list) {
			if(l.getMBlsOutType().getTypeCode().equalsIgnoreCase("class_land"))
			res.put(l.getMBlsOutIdPk(), l.getDescription());
		}
		
	}catch(Exception ex) {
		res=null;
		ex.printStackTrace();
	}
	return res;
		
	}
	
	@RequestMapping(value = "/getLandSubClassification", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getLandSubClassification(HttpServletRequest request) {
		LinkedHashMap<Integer,String> res=new LinkedHashMap<Integer,String>();
	try {
		List<MBlsOutcome> list = baseLineOutcomeService.getOutComeMaster();
		for(MBlsOutcome l : list) {
			if(l.getMBlsOutType().getTypeCode().equalsIgnoreCase("sub_class_land"))
			res.put(l.getMBlsOutIdPk(), l.getDescription());
		}
		
	}catch(Exception ex) {
		res=null;
		ex.printStackTrace();
	}
	return res;
		
	}
	
	@RequestMapping(value = "/getNoofCrop", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getNoofCrop(HttpServletRequest request) {
		LinkedHashMap<Integer,String> res=new LinkedHashMap<Integer,String>();
	try {
		List<MBlsOutcome> list = baseLineOutcomeService.getOutComeMaster();
		for(MBlsOutcome l : list) {
			if(l.getMBlsOutType().getTypeCode().equalsIgnoreCase("no_crop"))
			res.put(l.getMBlsOutIdPk(), l.getDescription());
		}
		
	}catch(Exception ex) {
		res=null;
		ex.printStackTrace();
	}
	return res;
		
	}
	
	@RequestMapping(value = "/getForestlandType", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getForestlandType(HttpServletRequest request) {
		LinkedHashMap<Integer,String> res=new LinkedHashMap<Integer,String>();
	try {
		List<MBlsOutcome> list = baseLineOutcomeService.getOutComeMaster();
		for(MBlsOutcome l : list) {
			if(l.getMBlsOutType().getTypeCode().equalsIgnoreCase("for_land_type"))
			res.put(l.getMBlsOutIdPk(), l.getDescription());
		}
		
	}catch(Exception ex) {
		res=null;
		ex.printStackTrace();
	}
	return res;
		
	}
	
	@RequestMapping(value = "/getSeasonList", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getSeasonList(HttpServletRequest request) {
		LinkedHashMap<Integer,String> res=new LinkedHashMap<Integer,String>();
	try {
		List<MBlsOutcome> list = baseLineOutcomeService.getOutComeMaster();
		for(MBlsOutcome l : list) {
			if(l.getMBlsOutType().getTypeCode().equalsIgnoreCase("season"))
				res.put(l.getMBlsOutIdPk(), l.getDescription());
		}
		
	}catch(Exception ex) {
		res=null;
		ex.printStackTrace();
	}
	return res;
		
	}
	
	@RequestMapping(value = "/getCropType", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getCropType(HttpServletRequest request) {
		LinkedHashMap<Integer,String> res=new LinkedHashMap<Integer,String>();
	try {
		List<MBlsOutcome> list = baseLineOutcomeService.getOutComeMaster();
		for(MBlsOutcome l : list) {
			if(l.getMBlsOutType().getTypeCode().equalsIgnoreCase("crop_type"))
			res.put(l.getMBlsOutIdPk(), l.getDescription());
		}
		
	}catch(Exception ex) {
		res=null;
		ex.printStackTrace();
	}
	return res;
		
	}
	
	@RequestMapping(value = "/getIrrigationStatus", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getIrrigationStatus(HttpServletRequest request) {
		LinkedHashMap<Integer,String> res=new LinkedHashMap<Integer,String>();
	try {
		List<MBlsOutcome> list = baseLineOutcomeService.getOutComeMaster();
		for(MBlsOutcome l : list) {
			if(l.getMBlsOutType().getTypeCode().equalsIgnoreCase("irrig_status"))
			res.put(l.getMBlsOutIdPk(), l.getDescription());
		}
		
	}catch(Exception ex) {
		res=null;
		ex.printStackTrace();
	}
	return res;
		
	}
	
	@RequestMapping(value = "/saveAsDraftBLS", method = RequestMethod.POST)
	@ResponseBody
	public String saveAsDraft(HttpServletRequest request, @RequestParam("projId") Integer projId, @RequestParam("vcode") Integer vcode, @RequestParam("plotNo") String plotNo, 
			@RequestParam("projectArea") BigDecimal projectArea, @RequestParam("irrigationStatus") Integer irrigationStatus, @RequestParam("ownership") Integer ownership, 
			@RequestParam("ownerName") String ownerName, @RequestParam("landClassification") Integer landClassification,@RequestParam("landSubClassification") String landSubClassification, @RequestParam("noOfCrop") String noOfCrop, 
			@RequestParam("season") List<Integer> season, @RequestParam("cropType") List<Integer> cropType, @RequestParam("cropArea") List<BigDecimal> cropArea, 
			@RequestParam("cropProduction") List<BigDecimal> cropProduction, 
			@RequestParam("avgIncome") List<BigDecimal> avgIncome, @RequestParam("forestLandType") String forestLandType) {
		String res="fail";
	try {
		session = request.getSession(true);
		String userId = session.getAttribute("loginID").toString();
		//System.out.println("-------------->"+projId+" : "+vcode+" : "+plotNo+" : "+projectArea+" : "+irrigationStatus+" : "+ownership+" : "+ownerName+" : "+landClassification+" : "+landSubClassification+" : "+noOfCrop+" : "+season+" : "+
			//	cropType+" : "+cropArea+" : "+cropProduction+" : "+avgIncome+" : "+forestLandType);
		// kdy
		res=baseLineOutcomeService.saveBLSasDraft(projId,vcode,plotNo,projectArea,irrigationStatus,ownership,ownerName,landClassification,landSubClassification,noOfCrop,season,cropType,cropArea,cropProduction,avgIncome,forestLandType,userId);
		
	}catch(Exception ex) {
		res=null;
		ex.printStackTrace();
	}
	return res;
		
	}
	
	@RequestMapping(value = "/getPlotDataOfAVillage", method = RequestMethod.POST)
	@ResponseBody
	public List<NewBaseLineSurveyBean> getPlotDataOfAVillage(HttpServletRequest request, @RequestParam("plotno") String plotno,
			@RequestParam("vcode") String vcode, @RequestParam("projid") Integer projId) {
		List<NewBaseLineSurveyBean> res=new ArrayList<NewBaseLineSurveyBean>();
	try {
		
		res=baseLineOutcomeService.getPlotDataOfAVillage(Integer.parseInt(vcode),plotno,projId);
		
	}catch(Exception ex) {
		res=null;
	}
	return res;
		
	}
	
	@RequestMapping(value = "/getProjectSanctionedArea", method = RequestMethod.POST)
	@ResponseBody
	public BigDecimal getProjectSanctionedArea(HttpServletRequest request, @RequestParam("projId") Integer projId) {
		BigDecimal res= BigDecimal.ZERO;
		BigDecimal big= BigDecimal.ZERO;
		try {
			IwmpMProject mp = new IwmpMProject();
			mp=projectMasterService.getProjectByProjectId(projId);
			big=baseLineOutcomeService.gettotalarea(projId);
			res= mp.getAreaProposed().subtract(big);
		//	System.out.println(mp.getAreaProposed()+"  =kd=   "+res);
		
		}
		catch(Exception ex) {
			res=null;
		}
		return res;
		
	}
}
