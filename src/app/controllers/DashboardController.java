package app.controllers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.InagrtnAndWtrShdDashBoardBean;
import app.bean.WatershedYatraDashboardChartBean;
import app.bean.WatrshdInagrtnPreYtraDashBean;
import app.bean.reports.DolrDashboardBean;
import app.model.UserReg;
import app.model.WdcpmksyMQuadIndicators;
import app.service.CommonService;
import app.service.DashBoardService;
import app.service.StateMasterService;

@Controller("dashboardControlle")
public class DashboardController {
	@Autowired(required = true)
	StateMasterService stateMasterService;
	private Map<Integer, String> stateList;

	
	@Autowired(required = true)
	private CommonService commonService;
	
	@Autowired(required = true)
	DashBoardService dashBoardService;
	
	@Autowired(required = true)
	IndexCircleDataController indexCircleDataController;

	
	@RequestMapping(value="/dolrDashBoard", method = RequestMethod.GET)
	public ModelAndView dolrDashBoard(HttpServletRequest request, HttpServletResponse response,Model model)
	{
		ModelAndView mav = new ModelAndView();
	try {
		mav = new ModelAndView("dolrDashBoard");
		DolrDashboardBean dashboard=new DolrDashboardBean();
		model.addAttribute("statelist", stateMasterService.getAllState());
		//model.addAttribute("projectstatewise", dashBoardService.getAllProject());
		model.addAttribute("financialYear", commonService.getAllFinancialYear());
		model.addAttribute("dashboard", dashboard);
		
		List<String> list = indexCircleDataController.getData();
		
		if (list != null)
			mav.addObject("area_soilmoisture_activities_achie", list.get(0));
		else
			mav.addObject("area_soilmoisture_activities_achie", "Data Not Available");
		if (list != null)
			mav.addObject("area_afforestation_horticulture_achie", list.get(1));
		else
			mav.addObject("area_afforestation_horticulture_achie", "Data Not Available");
		if (list != null)
			mav.addObject("water_created_renovated_achie", list.get(2));
		else
			mav.addObject("water_created_renovated_achie", "Data Not Available");
		if (list != null)
			mav.addObject("protective_irrigation_achie", list.get(3));
		else
			mav.addObject("protective_irrigation_achie", "Data Not Available");
		if (list != null)
			mav.addObject("farmer_benefitted_achie", list.get(4));
		else
			mav.addObject("farmer_benefitted_achie", "Data Not Available");
		if (list != null)
			mav.addObject("man_days_gen", list.get(5));
		else
			mav.addObject("man_days_gen", "Data Not Available");
		if (list != null)
			mav.addObject("degraded_rainfed", list.get(6));
		else
			mav.addObject("degraded_rainfed", "Data Not Available");
		
	}
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return mav; 
	}
	
	
	
	
	
	@RequestMapping(value="/getprojectstatewise", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,Integer> getprojectstatewise(HttpServletRequest request) throws InterruptedException
	{
		//List<UserReg> list=new  ArrayList<UserReg>();
		LinkedHashMap<String,Integer> map100 = new LinkedHashMap<String,Integer>();
		map100.putAll(dashBoardService.getAllProject(100,0));
		System.out.println("100%"+dashBoardService.getAllProject(100,0));
		return map100;
		
	}
	
	@RequestMapping(value="/getprojectstatewisecentral90", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,Integer> getprojectstatewisecentral90(HttpServletRequest request) throws InterruptedException
	{
		//List<UserReg> list=new  ArrayList<UserReg>();
		LinkedHashMap<String,Integer> map90 = new LinkedHashMap<String,Integer>();
		map90.putAll(dashBoardService.getAllProject(90,10));
		System.out.println("90%"+dashBoardService.getAllProject(90,10));
		return map90;
		
	}
	
	@RequestMapping(value="/getprojectstatewisecentral60", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String,Integer> getprojectstatewisecentral60(HttpServletRequest request) throws InterruptedException
	{
		//List<UserReg> list=new  ArrayList<UserReg>();
		LinkedHashMap<String,Integer> map60 = new LinkedHashMap<String,Integer>();
		map60.putAll(dashBoardService.getAllProject(60,40));
		System.out.println("60%"+dashBoardService.getAllProject(60,40));
		return map60;
		
	}


	@RequestMapping(value ="/getTopTenStwiseBlsCmpltd", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, BigDecimal> getTopTenStwiseBlsCmpltd(HttpServletRequest request){
		LinkedHashMap<String, BigDecimal> map = new LinkedHashMap<>();
		map.putAll(dashBoardService.getTopTenStateBlsCompleted());
		System.out.println("10%"+dashBoardService.getTopTenStateBlsCompleted());
		return map;
		
	}
//	
//	@RequestMapping(value ="/getTopTenProjLocCmpltnPrcntg", method = RequestMethod.POST)
//	@ResponseBody
//	public LinkedHashMap<String, BigDecimal> getTopTenProjLocCmpltdPrcntg(HttpServletRequest request){
//		LinkedHashMap<String, BigDecimal> map = new LinkedHashMap<>();
//		map.putAll(dashBoardService.getTopTenProjLocCmpltnPrcntg());
//		System.out.println("20%"+dashBoardService.getTopTenProjLocCmpltnPrcntg());
//		return map;
//		
//	}
	
	@RequestMapping(value = "/getTopTenStLandClassPrcntg", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getTopTenStClassLandPrcntg(HttpServletRequest request){
		List<DolrDashboardBean> topTenStLandClassprcntgList=new ArrayList<>();
		topTenStLandClassprcntgList= dashBoardService.getTopTenClassLandPrcntg();
		
		return topTenStLandClassprcntgList;
	}
	
	@RequestMapping(value = "/getCompLandClassPrcntg", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getCompLandClassPrcntg(HttpServletRequest request){
		List<DolrDashboardBean> compLandClassprcntgList=new ArrayList<>();
		compLandClassprcntgList= dashBoardService.getCompClassLandAchPrcntg();
		
		return compLandClassprcntgList;
	}
	
	@RequestMapping(value = "/getCompIrriStatusPrcntg", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getCompIrriStatusPrcntg(HttpServletRequest request){
		List<DolrDashboardBean> compIrriStatusprcntgList=new ArrayList<>();
		compIrriStatusprcntgList= dashBoardService.getCompIrriStatusPrcntg();
		
		return compIrriStatusprcntgList;
	}
	
	@RequestMapping(value = "/getCompIrriStatusAchPrcntg", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getCompIrriStatusAchPrcntg(HttpServletRequest request){
		List<DolrDashboardBean> compIrriStatusAchprcntgList=new ArrayList<>();
		compIrriStatusAchprcntgList= dashBoardService.getCompIrriStatusAchPrcntg();
		
		return compIrriStatusAchprcntgList;
	}
	
	@RequestMapping(value = "/getCompNoOfCropPrcntg", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getCompNoOfCropPrcntg(HttpServletRequest request){
		List<DolrDashboardBean> compNoOfCropprcntgList=new ArrayList<>();
		compNoOfCropprcntgList= dashBoardService.getCompNoOfCropPrcntg();
		
		return compNoOfCropprcntgList;
	}
	
	@RequestMapping(value = "/getCompNoOfCropAchPrcntg", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getCompNoOfCropAchPrcntg(HttpServletRequest request){
		List<DolrDashboardBean> compNoOfCropAchprcntgList=new ArrayList<>();
		compNoOfCropAchprcntgList= dashBoardService.getCompNoOfCropAchPrcntg();
		
		return compNoOfCropAchprcntgList;
	}
	
	@RequestMapping(value = "/getStwiseTotalExpndtrPrcntg", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getStwiseTotalExpndtrPrcntg(HttpServletRequest request){
		List<DolrDashboardBean> stwiseTotalExpndtrPrcntList=new ArrayList<>();
		stwiseTotalExpndtrPrcntList= dashBoardService.getStwiseTotalExpPrcntg();
		
		return stwiseTotalExpndtrPrcntList;
	}
	
//	@RequestMapping(value = "/getTopTenNonCompLandPrcntg", method = RequestMethod.POST)
//	@ResponseBody
//	public List<DolrDashboardBean> getTopTenNonCompLandPrcntg(HttpServletRequest request){
//		List<DolrDashboardBean> topTenStLandClassprcntgList=new ArrayList<>();
//		topTenStLandClassprcntgList= dashBoardService.getTopTenStNonCompLandPrcntg();
//		System.out.println("05%"+topTenStLandClassprcntgList);
//		
//		return topTenStLandClassprcntgList;
//	}
	
//	@RequestMapping(value = "/getTopTenNonCompLandAchPrcntg", method = RequestMethod.POST)
//	@ResponseBody
//	public List<DolrDashboardBean> getTopTenNonCompLandAchPrcntg(HttpServletRequest request){
//		List<DolrDashboardBean> compLandClassprcntgList=new ArrayList<>();
//		compLandClassprcntgList= dashBoardService.getCompClassLandAchPrcntg();
//		System.out.println("05%"+dashBoardService.getCompClassLandAchPrcntg());
//		
//		return compLandClassprcntgList;
//	}
	
//	@RequestMapping(value = "/getTopTenNonCompIrriPrcntg", method = RequestMethod.POST)
//	@ResponseBody
//	public List<DolrDashboardBean> getTopTenNonCompIrriPrcntg(HttpServletRequest request){
//		List<DolrDashboardBean> compIrriStatusprcntgList=new ArrayList<>();
//		compIrriStatusprcntgList= dashBoardService.getTopTenStNonCompIrriPrcntg();
//		System.out.println("03%"+compIrriStatusprcntgList);
//		
//		return compIrriStatusprcntgList;
//	}
	
//	@RequestMapping(value = "/getTopTenNonCompIrriAchPrcntg", method = RequestMethod.POST)
//	@ResponseBody
//	public List<DolrDashboardBean> getTopTenNonCompIrriAchPrcntg(HttpServletRequest request){
//		List<DolrDashboardBean> compIrriStatusAchprcntgList=new ArrayList<>();
//		compIrriStatusAchprcntgList= dashBoardService.getCompIrriStatusAchPrcntg();
//		System.out.println("03%"+dashBoardService.getCompIrriStatusAchPrcntg());
//		
//		return compIrriStatusAchprcntgList;
//	}
	
//	@RequestMapping(value = "/getTopTenNonCompCropPrcntg", method = RequestMethod.POST)
//	@ResponseBody
//	public List<DolrDashboardBean> getTopTenNonCompCropPrcntg(HttpServletRequest request){
//		List<DolrDashboardBean> compNoOfCropprcntgList=new ArrayList<>();
//		compNoOfCropprcntgList= dashBoardService.getTopTenStNonCompCropPrcntg();
//		System.out.println("04%"+compNoOfCropprcntgList);
//		
//		return compNoOfCropprcntgList;
//	}
	
	@RequestMapping(value = "/getStwiseSancExpndtrRcpt", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getStwiseSancExpndtrRcpt(HttpServletRequest request){
		List<DolrDashboardBean> stwiseSancExpndtrRcpt=new ArrayList<>();
		stwiseSancExpndtrRcpt= dashBoardService.getStwiseSancExpndtrRecpt();
		System.out.println("33%"+dashBoardService.getStwiseSancExpndtrRecpt());
		
		return stwiseSancExpndtrRcpt;
	}
	
	
	
	@RequestMapping(value="/getwhsdata", method = RequestMethod.POST)
	@ResponseBody
	public List<DolrDashboardBean> getwhsdata(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id)
	{
		LinkedHashMap<Integer,List<DolrDashboardBean>> map = new LinkedHashMap<Integer,List<DolrDashboardBean>>();
		List<DolrDashboardBean> proj = new ArrayList<DolrDashboardBean>();
       	proj = dashBoardService.getdashboardtarget(id);
		System.out.println("proj data:" +proj);
		return proj;
	}
	
	@RequestMapping(value = "/watershedDashBoard", method = RequestMethod.GET)
	public ModelAndView watershedDashBoard(HttpServletRequest request, HttpServletResponse response, Model model) {
		ModelAndView mav = new ModelAndView();
		String stCode = request.getParameter("stCode");

		try {
		mav = new ModelAndView("WatershedYatra/watershedDashBoard");
		Map<String, List<WatrshdInagrtnPreYtraDashBean>> map = new LinkedHashMap<String, List<WatrshdInagrtnPreYtraDashBean>>();
		Map<String, List<WatrshdInagrtnPreYtraDashBean>> wtrIngMap = new LinkedHashMap<String, List<WatrshdInagrtnPreYtraDashBean>>();
		List<WatershedYatraDashboardChartBean> list = new ArrayList<>();
		List<WatershedYatraDashboardChartBean> ParticipantList = new ArrayList<>();
		List<WatershedYatraDashboardChartBean> CoveredLocations = new ArrayList<>();
        List<InagrtnAndWtrShdDashBoardBean> pList = new ArrayList<>();
        
        stateList=stateMasterService.getAllState();
		mav.addObject("stateList", stateList);
		
		map = dashBoardService.getWatrshdInagrtnPreYtraData();
		wtrIngMap = dashBoardService.getWatrshdInagrtnData();
		list = dashBoardService.getWtrshdYtraChartData();
		pList = dashBoardService.getInagrtnAndWtrShdDashBoardData();
		
        Integer stCode1 = (stCode == null || stCode.isEmpty()) ? null : Integer.parseInt(stCode);
		ParticipantList = (stCode == null) 
	            ? dashBoardService.getParticipantslist(28) // Default state code
	                    : dashBoardService.getParticipantslist(stCode1);
		CoveredLocations = dashBoardService.getDateWiseCovLocations(28);
		
		Map<String, Integer> chartData = new LinkedHashMap<>();
		Map<String, Integer> chartLocData = new LinkedHashMap<>();
        for (WatershedYatraDashboardChartBean bean : ParticipantList) {
            chartData.put(bean.getYatradate(), bean.getTotal_participants());
        }
        for (WatershedYatraDashboardChartBean bean : CoveredLocations) {
        	chartLocData.put(bean.getYatradate(), bean.getCoveredlocations());
        }

		model.addAttribute("map",map);
		model.addAttribute("list",list);
		model.addAttribute("pList",pList);
		model.addAttribute("chartData", chartData);
		model.addAttribute("chartLocData", chartLocData);
		model.addAttribute("statelist", stateMasterService.getAllState());
        model.addAttribute("ing",wtrIngMap.get("ing"));
		model.addAttribute("wtr",wtrIngMap.get("wtr"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	@RequestMapping(value = "/getParticipantData", method = RequestMethod.GET)
	@ResponseBody // Ensures only the data (not a view) is returned
	public Map<String, Object> getParticipantData(@RequestParam("stateCode") Integer stCode) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        // Fetch participant data for the selected state
	        List<WatershedYatraDashboardChartBean> ParticipantList = dashBoardService.getParticipantslist(stCode);
	        List<WatershedYatraDashboardChartBean> CoveredLocations = dashBoardService.getDateWiseCovLocations(stCode);
	        
	        Map<String, Integer> chartData = new LinkedHashMap<>();
	        for (WatershedYatraDashboardChartBean bean : ParticipantList) {
	            chartData.put(bean.getYatradate(), bean.getTotal_participants());
	        }

	        Map<String, Integer> chartLocData = new LinkedHashMap<>();
	        for (WatershedYatraDashboardChartBean bean : CoveredLocations) {
	        	chartLocData.put(bean.getYatradate(), bean.getCoveredlocations());
	        }
	        // Populate the response with chart data
	        response.put("chartData", chartData);
	        response.put("chartLocData", chartLocData);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return response;
	}


}
