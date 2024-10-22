package app.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.bean.PhysicalActBean;
import app.bean.TarAchProjOutcomeBean;
import app.service.DistrictMasterService;
import app.service.FinYrServices;
import app.service.ProjectMasterService;
import app.service.StateMasterService;
import app.service.TarAchProjOutcomeService;
import app.serviceImpl.DistrictMasterServiceImpl;

@Controller("TarAchProjOutcomeController")
public class TarAchProjOutcomeController {
	HttpSession session;

	@Autowired
	StateMasterService stateMasterService;
	
	@Autowired(required = true)
	DistrictMasterServiceImpl DistrictActService;
	
	@Autowired
	TarAchProjOutcomeService tarAchProjOutcomeService;

	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired
	FinYrServices finYrServices;
	
	@RequestMapping(value = "/tarAchProjOutcome", method = RequestMethod.GET)
	public ModelAndView tarAchProjOutcome(HttpServletRequest request, HttpServletResponse response) {
		// System.out.println("starting....");
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String listdata="";
		mav = new ModelAndView("reports/tarAchProjOutcome");
		List<TarAchProjOutcomeBean> finyear = new ArrayList<TarAchProjOutcomeBean>();
		List<TarAchProjOutcomeBean> yearlist = new ArrayList<TarAchProjOutcomeBean>();
		finyear = tarAchProjOutcomeService.getfYear();
		yearlist = tarAchProjOutcomeService.getyList();
		
		/* yearlist.forEach(s) */
		
		for(TarAchProjOutcomeBean list:yearlist)
		{
			if(listdata.equals(""))
			listdata=list.getYeardesc();
			else
			listdata+=","+list.getYeardesc(); 
			
		}
		List<String[]> dataList =getData(finyear.get(0).getfyear(),finyear.get(0).getLastyear(),0,0,0,"a","t");
		
		mav.addObject("stateList", stateMasterService.getAllState());
		mav.addObject("dataList", dataList);
		mav.addObject("yrListSize", finyear.get(0).getListsize());
		mav.addObject("yrList", listdata);
		mav.addObject("valuesa", "a");
		mav.addObject("valuest", "t");
		//mav.addObject("yrList", yr);
		return mav;
	}

	@RequestMapping(value = "/getFromYearFortarAchProjOutcome", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getFromYearFortarAchProjOutcome(HttpServletRequest request,
			@RequestParam(value = "pCode") Integer pCode) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		// map.put(0, "All");
		map.putAll(tarAchProjOutcomeService.getFromYearFortarAchProjOutcome(pCode));
		return map;
	}

	@RequestMapping(value = "/getToYearFortarAchProjOutcome", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getToYearForPhysicalActionPlanReport(HttpServletRequest request,
			@RequestParam(value = "fromYear") Integer fromYear, @RequestParam(value = "projId") String projId) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.putAll(tarAchProjOutcomeService.getToYearFortarAchProjOutcome(fromYear, projId));
		return map;
	}

	@RequestMapping(value = "/getTarAchProjOutcomeReport", method = RequestMethod.POST)
	@ResponseBody
	public List<TarAchProjOutcomeBean> getUserToForward(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "fromYear") Integer fromYear, @RequestParam(value = "toYear") Integer toYear,
			@RequestParam(value = "project") Integer project, @RequestParam(value = "state") Integer state,
			@RequestParam(value = "district") Integer district) {
		System.out.println("starting....");
		ModelAndView mav = new ModelAndView();
		List<TarAchProjOutcomeBean> res = new ArrayList<TarAchProjOutcomeBean>();
		session = request.getSession(true);
		res = tarAchProjOutcomeService.getToYearForPhysicalActionPlanReport(fromYear, toYear, project, state, district);

		return res;
	}

	private static int indexOf(Object[] strArray, Object element) {

		/*
		 * Convert array to List and then use indexOf method of List class.
		 */
		int index = Arrays.asList(strArray).indexOf(element);

		return index;

	}

	@RequestMapping(value = "/tarAchProjOutcome", method = RequestMethod.POST)
	public ModelAndView getPhysicalActionReport(HttpServletRequest request,
			@RequestParam(value = "state") Integer state, @RequestParam(value = "district") Integer district,
			@RequestParam(value = "project") Integer project, @RequestParam(value = "fromYear") Integer fromYear,
			@RequestParam(value = "toYear") Integer toYear)

	{
		String valuesa = "";
		if (request.getParameter("a") != null)
			valuesa = request.getParameter("a");
		    String value = (String)request.getParameter("distCode");
		    System.out.println("value of state:" +value);
		System.out.println("valuesa" + valuesa);
		String valuest = "";
		if (request.getParameter("t") != null)
			valuest = request.getParameter("t");

		ModelAndView mav = new ModelAndView("reports/tarAchProjOutcome");
		LinkedHashMap<Integer, List<String>> map = new LinkedHashMap<Integer, List<String>>();
		

		List<String> target = new ArrayList<String>();
		
		List<String> yr = new ArrayList<String>();
		Integer yearListSize = 1;

		String strYr[] = new String[toYear - fromYear];
		
		String yrChk = "";

		List<String[]> dataList =getData(fromYear, toYear, project, state,
				district,valuesa,valuest);
		List<TarAchProjOutcomeBean> beanList = new ArrayList<TarAchProjOutcomeBean>();
		beanList = tarAchProjOutcomeService.getToYearForPhysicalActionPlanReport(fromYear, toYear, project, state,
				district);
		for (TarAchProjOutcomeBean bean : beanList) {
		if (yr != null && !yr.contains(bean.getFinyear()))
			yr.add(bean.getFinyear());
		target.add(bean.getTarget().toString());
		}
		if (yr.size() > 0)
			yearListSize = yr.size();
		System.out.println("yr--------->" + yr.size() + " : " + yearListSize);

		strYr = yrChk.split(",");
		mav.addObject("dataList", dataList);
		mav.addObject("target", target);
		mav.addObject("valuesa", valuesa);
		mav.addObject("valuest", valuest);
		mav.addObject("yrListSize", yearListSize);
		mav.addObject("yrList", yr);
		mav.addObject("stCode", state);
		mav.addObject("distCode", district);
		mav.addObject("projectId", project);
		mav.addObject("fromYear", fromYear);
		mav.addObject("toYear", toYear);
		
		mav.addObject("stateList", stateMasterService.getAllState());
		mav.addObject("statename", stateMasterService.getStateByStateCode(state).values());
	    mav.addObject("distact",DistrictActService.getDistrictByStateCode(state));
	    if(district!=0) {
	    	mav.addObject("getdistrict", districtMasterService.getDistrictByDistCode(district).values());
	    }
	    else {
	    	mav.addObject("getdistrict", "All Districts");
	    }
     	if(project!=0) {
	    mav.addObject("projdesc", projectMasterService.getProjectByProjectId(project).getProjName());
     	}
     	else {
     		mav.addObject("projdesc", "All Projects");	
     	}
        mav.addObject("toyear",finYrServices.getfinYearByFinCode(toYear).values());
	    mav.addObject("fromyear",finYrServices.getfinYearByFinCode(fromYear).values());
		return mav;
	}
	
	
	public List<String[]> getData(Integer fromYear,Integer toYear,Integer project,Integer state,
			Integer district,String valuesa,String valuest) {
		List<String[]> dataList = new ArrayList<String[]>();
		Integer next = 1;
		String taroutcome = "";
		String str[] = null;
		List<String> yrList = new ArrayList<String>();
		
		List<TarAchProjOutcomeBean> beanList = new ArrayList<TarAchProjOutcomeBean>();
		beanList = tarAchProjOutcomeService.getToYearForPhysicalActionPlanReport(fromYear, toYear, project, state,
				district);
		for (TarAchProjOutcomeBean bean : beanList) {
			if (yrList.size() < 1)
				yrList.add(bean.getFinyear());
			else {
				int position = yrList.indexOf(bean.getFinyear());
				if (position < 0)
					yrList.add(bean.getFinyear());
			}

		}
		Collections.sort(yrList);
		int j = 0;
		int i = 0;
		Double targetTotal = 0.0;
		Double achievementTotal = 0.0;
		for (TarAchProjOutcomeBean bean : beanList) {
			if ((valuesa.isEmpty() && valuest.isEmpty()) || (valuesa.contains("a") && valuest.contains("t"))) {
				if (taroutcome.isEmpty() || !taroutcome.equals(bean.getOutcomedesc())) {
					if (!taroutcome.isEmpty()) {
						str[(yrList.size() * 2) + 2] = targetTotal.toString();
						str[(yrList.size() * 2) + 3] = achievementTotal.toString();
						dataList.add(str);
					}
					j += 1;
					targetTotal = 0.0;
					achievementTotal = 0.0;
					str = new String[(yrList.size() * 2 + 4)];
					i = 0;
					str[i] = Integer.toString(j);
					i++;
					taroutcome = bean.getOutcomedesc();
					str[i] = taroutcome;
					int position = yrList.indexOf(bean.getFinyear());
					i = position + 2;
					str[i] = bean.getTarget().toString();
					i = i + 1;
					str[i] = bean.getAchievement().toString() + ",#";
					targetTotal = targetTotal + bean.getTarget().doubleValue();
					achievementTotal = achievementTotal + bean.getAchievement().doubleValue();

				} else if (taroutcome.equals(bean.getOutcomedesc())) {
					int position = yrList.lastIndexOf(bean.getFinyear());
					i = position * 2 + 2;
					// i+=1;
					str[i] = bean.getTarget().toString();
					i = i + 1;
					str[i] = bean.getAchievement().toString() + ",#";
					targetTotal = targetTotal + bean.getTarget().doubleValue();
					achievementTotal = achievementTotal + bean.getAchievement().doubleValue();

				}
			} else 
				if (valuesa.isEmpty() && valuest.contains("t")) {
					if (taroutcome.isEmpty() || !taroutcome.equals(bean.getOutcomedesc())) {
						if (!taroutcome.isEmpty()) {
							str[yrList.size() + 2] = targetTotal.toString();
							// str[(yrList.size() * 2) + 3] = achievementTotal.toString();
							dataList.add(str);
						}
						j += 1;
						targetTotal = 0.0;
						// achievementTotal = 0.0;
						str = new String[(yrList.size() + 3)];
						i = 0;
						str[i] = Integer.toString(j);
						i++;
						taroutcome = bean.getOutcomedesc();
						str[i] = taroutcome;
						int position = yrList.indexOf(bean.getFinyear());
						i = position + 2;
						str[i] = bean.getTarget().toString();
						// i = i + 1;
						// str[i] = bean.getAchievement().toString() + ",#";
						targetTotal = targetTotal + bean.getTarget().doubleValue();
						// achievementTotal = achievementTotal + bean.getAchievement().doubleValue();

					} else if (taroutcome.equals(bean.getOutcomedesc())) {
						int position = yrList.lastIndexOf(bean.getFinyear());
						i = position + 2;
						// i+=1;
						str[i] = bean.getTarget().toString();
						// i = i + 1;
						// str[i] = bean.getAchievement().toString() + ",u";
						targetTotal = targetTotal + bean.getTarget().doubleValue();
						// achievementTotal = achievementTotal + bean.getAchievement().doubleValue();

					}
				} else if (valuesa.contains("a") && valuest.isEmpty()) {
					if (taroutcome.isEmpty() || !taroutcome.equals(bean.getOutcomedesc())) {
						if (!taroutcome.isEmpty()) {
							str[yrList.size() + 2] = achievementTotal.toString();
							// str[(yrList.size() * 2) + 3] = achievementTotal.toString();
							dataList.add(str);
						}
						j += 1;
						// targetTotal = 0.0;
						achievementTotal = 0.0;
						str = new String[(yrList.size() + 3)];
						i = 0;
						str[i] = Integer.toString(j);
						i++;
						taroutcome = bean.getOutcomedesc();
						str[i] = taroutcome;
						int position = yrList.indexOf(bean.getFinyear());
						i = position + 2;
						// str[i] = bean.getTarget().toString();
						// i = i + 1;
						str[i] = bean.getAchievement().toString() + ",#";
						// targetTotal = targetTotal + bean.getTarget().doubleValue();
						achievementTotal = achievementTotal + bean.getAchievement().doubleValue();

					} else if (taroutcome.equals(bean.getOutcomedesc())) {
						int position = yrList.lastIndexOf(bean.getFinyear());
						i = position + 2;
						// i+=1;
						// str[i] = bean.getTarget().toString();
						// i = i + 1;
						str[i] = bean.getAchievement().toString() + ",#";
						// targetTotal = targetTotal + bean.getTarget().doubleValue();
						achievementTotal = achievementTotal + bean.getAchievement().doubleValue();

					}
				}
			

			
		}

		if (str != null) {
			if ((valuesa.isEmpty() && valuest.isEmpty()) || (valuesa.contains("a") && valuest.contains("t"))) {
				str[(yrList.size() * 2) + 2] = targetTotal.toString();
				str[(yrList.size() * 2) + 3] = achievementTotal.toString();
				dataList.add(str);
			} else if (valuesa.contains("a") && valuest == null) {
				str[yrList.size() + 2] = achievementTotal.toString();
				dataList.add(str);
			} else if (valuesa == null && valuest.contains("t")) {
				str[yrList.size() + 2] = targetTotal.toString();
				dataList.add(str);
			}
		}
		return dataList;
	}

}
