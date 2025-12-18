package app.mahotsav.controller;

import java.math.BigInteger;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import app.bean.InagrtnAndWtrShdDashBoardBean;
import app.bean.WatershedYatraDashboardChartBean;
import app.bean.WatrshdInagrtnPreYtraDashBean;
import app.controllers.IndexCircleDataController;
import app.mahotsav.bean.DashboardMahotsavBean;
import app.mahotsav.service.DashboardMahotsavServices;
import app.service.CommonService;
import app.service.DashBoardService;
import app.service.StateMasterService;

@Controller("mahotsavDashboardController")
public class MahotsavDashboardController {
	
	@Autowired(required = true)
	StateMasterService stateMasterService;
	private Map<Integer, String> stateList;

	@Autowired(required = true)
	DashBoardService dashBoardService;
	
	@Autowired(required = true)
	DashboardMahotsavServices ser;
	
	@Autowired(required = true)
	IndexCircleDataController indexCircleDataController;
	
	@RequestMapping(value = "/mahotsavDashBoard", method = RequestMethod.GET)
	public ModelAndView watershedDashBoard(HttpServletRequest request, HttpServletResponse response, Model model) {
		ModelAndView mav = new ModelAndView();
		String stCode = request.getParameter("stCode");

		try {
		mav = new ModelAndView("mahotsav/mahotsavDashboard");
		Map<String, List<DashboardMahotsavBean>> map = new LinkedHashMap<String, List<DashboardMahotsavBean>>();
		Map<String, List<WatrshdInagrtnPreYtraDashBean>> wtrIngMap = new LinkedHashMap<String, List<WatrshdInagrtnPreYtraDashBean>>();
		List<WatershedYatraDashboardChartBean> list = new ArrayList<>();
		List<WatershedYatraDashboardChartBean> ParticipantList = new ArrayList<>();
		List<WatershedYatraDashboardChartBean> CoveredLocations = new ArrayList<>();
        List<InagrtnAndWtrShdDashBoardBean> pList = new ArrayList<>();
        List<WatrshdInagrtnPreYtraDashBean> data = dashBoardService.getStWisePreYatraData();
        List<WatrshdInagrtnPreYtraDashBean> data1 = dashBoardService.getStWiseWatershedYatraDashboardData();
        
        stateList=stateMasterService.getAllState();
		mav.addObject("stateList", stateList);
		
		map = ser.getMahotsavInagrtnYtraAtVillData();
		wtrIngMap = dashBoardService.getWatrshdInagrtnData();
		list = dashBoardService.getWtrshdYtraChartData();
		pList = dashBoardService.getInagrtnAndWtrShdDashBoardData();
		
        Integer stCode1 = (stCode == null || stCode.isEmpty()) ? null : Integer.parseInt(stCode);
		ParticipantList = (stCode == null) 
	            ? dashBoardService.getParticipantslist(28) // Default state code
	                    : dashBoardService.getParticipantslist(stCode1);
		CoveredLocations = dashBoardService.getDateWiseCovLocations(28);
		
		Map<String, BigInteger> chartData = new LinkedHashMap<>();
		Map<String, Integer> sumTotalChartData = new LinkedHashMap<>();
		Map<String, Integer> chartLocData = new LinkedHashMap<>();
		for (WatershedYatraDashboardChartBean bean : ParticipantList) {
            chartData.put(bean.getYatradate(), bean.getTotal_participants());
            sumTotalChartData.put(bean.getYatradate(), bean.getSumtotal_participants());
        }
        for (WatershedYatraDashboardChartBean bean : CoveredLocations) {
        	chartLocData.put(bean.getYatradate(), bean.getCoveredlocations());
        }

		model.addAttribute("map",map);
		model.addAttribute("list",list);
		model.addAttribute("pList",pList);
		model.addAttribute("chartData", chartData);
		model.addAttribute("sumTotalChartData", sumTotalChartData);
		model.addAttribute("chartLocData", chartLocData);
		model.addAttribute("statelist", stateMasterService.getAllState());
        model.addAttribute("ing",map.get("ing"));
		model.addAttribute("wtr",map.get("proj"));
		model.addAttribute("bean", data);
		model.addAttribute("bean1", data1);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/watershedMahotsavDashBoard", method = RequestMethod.GET)
	public ModelAndView WatershedMahotsavDashBoard(HttpServletRequest request, HttpServletResponse response, Model model) {
		ModelAndView mav = new ModelAndView();
		String stCode = request.getParameter("stCode");

		try {
		mav = new ModelAndView("mahotsav/watershedMahotsavDashboard");
		
		Map<String, List<DashboardMahotsavBean>> map = new LinkedHashMap<String, List<DashboardMahotsavBean>>();
	//	Map<String, List<WatrshdInagrtnPreYtraDashBean>> wtrIngMap = new LinkedHashMap<String, List<WatrshdInagrtnPreYtraDashBean>>();
		List<WatershedYatraDashboardChartBean> list = new ArrayList<>();
		List<DashboardMahotsavBean> lists = new ArrayList<DashboardMahotsavBean>();
		List<DashboardMahotsavBean> listpart = new ArrayList<DashboardMahotsavBean>();
		List<DashboardMahotsavBean> statePEData = new ArrayList<DashboardMahotsavBean>();
		List<WatershedYatraDashboardChartBean> ParticipantList = new ArrayList<>();
		List<WatershedYatraDashboardChartBean> CoveredLocations = new ArrayList<>();
        List<InagrtnAndWtrShdDashBoardBean> pList = new ArrayList<>();
        List<WatrshdInagrtnPreYtraDashBean> data = dashBoardService.getStWisePreYatraData();
        List<WatrshdInagrtnPreYtraDashBean> data1 = dashBoardService.getStWiseWatershedYatraDashboardData();
        
        stateList=stateMasterService.getAllState();
		mav.addObject("stateList", stateList);
		
		map = ser.getMahotsavInagrtnYtraAtVillData();
		lists=ser.getMahotsavSocialMedia();
		listpart=ser.getParticipantsListofMahotsav();
		statePEData=ser.getStateWiseParticipants();
		//wtrIngMap = dashBoardService.getWatrshdInagrtnData();
	//	list = dashBoardService.getWtrshdYtraChartData();
	//	pList = dashBoardService.getInagrtnAndWtrShdDashBoardData();
		
        Integer stCode1 = (stCode == null || stCode.isEmpty()) ? null : Integer.parseInt(stCode);
		ParticipantList = (stCode == null) 
	            ? dashBoardService.getParticipantslist(28) // Default state code
	                    : dashBoardService.getParticipantslist(stCode1);
		CoveredLocations = dashBoardService.getDateWiseCovLocations(28);
		
		Map<String, BigInteger> chartData = new LinkedHashMap<>();
		Map<String, Integer> sumTotalChartData = new LinkedHashMap<>();
		Map<String, Integer> chartLocData = new LinkedHashMap<>();
		for (WatershedYatraDashboardChartBean bean : ParticipantList) {
            chartData.put(bean.getYatradate(), bean.getTotal_participants());
            sumTotalChartData.put(bean.getYatradate(), bean.getSumtotal_participants());
        }
        for (WatershedYatraDashboardChartBean bean : CoveredLocations) {
        	chartLocData.put(bean.getYatradate(), bean.getCoveredlocations());
        }

		model.addAttribute("map",map);
		model.addAttribute("list",list);
		model.addAttribute("lists",lists);
		model.addAttribute("pList",pList);
		model.addAttribute("chartData", chartData);
		model.addAttribute("sumTotalChartData", sumTotalChartData);
		model.addAttribute("chartLocData", chartLocData);
		model.addAttribute("statelist", stateMasterService.getAllState());
        model.addAttribute("ing",map.get("ing"));
		model.addAttribute("wtr",map.get("proj"));
		model.addAttribute("bean", data);
		model.addAttribute("bean1", data1);
		model.addAttribute("listpart", listpart);
		model.addAttribute("stateWiseData", statePEData);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

}
