package app.controllers.baseline;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.AssetIdBean;
import app.bean.Login;
import app.bean.PhysicalActBean;
import app.bean.VillGramPanBean;
import app.model.BlsOutMain;
import app.model.IwmpProjectAssetStatus;
import app.model.project.IwmpProjectPhysicalAsset;
import app.service.MicroIrrigationService;
import app.service.outcome.BaseLineOutcomeService;

@Controller("microIrrigationController")
public class MicroIrrigationController {

	HttpSession session;
	
	@Autowired(required = true)
	BaseLineOutcomeService baseLineOutcomeService;
	
	@Autowired(required =true)
	MicroIrrigationService microIrrigationService;
	
	@RequestMapping(value = "/microIrrigation", method = RequestMethod.GET)
	public ModelAndView getMicroIrrigation(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if(session!=null && session.getAttribute("loginID")!=null) {
			if(session.getAttribute("roleName").toString().contains("PIA")) {
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				mav = new ModelAndView("microIrrigation");
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
		
	@RequestMapping(value = "/getVillageOfProjects", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<Integer,String> getVillageOfProject(HttpServletRequest request, @RequestParam("projId") Integer projId) {
		HashMap<Integer,String> res=new HashMap<Integer,String>();
	try {
		res=baseLineOutcomeService.getVillageOfProjectMicro(projId);
	}catch(Exception ex) {
		res=null;
	}
	return res;
		
	}	

	
	@RequestMapping(value = "/getPlotNoOfProject", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<Integer,String> getPlotNoOfProject(HttpServletRequest request, @RequestParam("villageId") Integer villageId) {
		HashMap<Integer,String> res=new HashMap<Integer,String>();
	try {
		res=microIrrigationService.getPlotnoOfProject(villageId);
	}catch(Exception ex) {
		res=null;
	}
	return res;
		
	}	

	
	@RequestMapping(value="/getPlotAreaOfProject", method = RequestMethod.POST)
	@ResponseBody
	public List<VillGramPanBean> getPlotAreaOfProject(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="plotnoId") Integer plotnoId)
	{
		//System.out.println("value of id:" +id);
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		LinkedHashMap<Integer,List<VillGramPanBean>> map = new LinkedHashMap<Integer,List<VillGramPanBean>>();
		List<VillGramPanBean> data = new ArrayList<VillGramPanBean>();
		List<VillGramPanBean> data1 = new ArrayList<VillGramPanBean>();
		if(session!=null && session.getAttribute("loginID")!=null) {
		data = microIrrigationService.getplotirriga(plotnoId);
		
		}else {
			data=null;
			
		}
		return data;  
	}	

	@RequestMapping(value="/updateMicroIrr", method = RequestMethod.POST)
	@ResponseBody
	public Boolean saveasdraft(HttpServletRequest request, HttpServletResponse response,@RequestParam(value ="plotno") Integer plotno,
			@RequestParam(value ="microI") BigDecimal microI, @RequestParam(value ="microstatus") Character microstatus) {
		ModelAndView mav = new ModelAndView();
		String res = "";
		Boolean flag=false;
		try {
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) {
			flag = microIrrigationService.saveMicroIrr(microI, plotno, microstatus);
			
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
		
		}
		catch (Exception e) {
			flag = null;
			e.printStackTrace();
		}
		
		return flag;
	}
}

