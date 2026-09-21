package app.swachhata.controller;

import java.util.ArrayList;
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

import app.bean.Login;
import app.bean.ProfileBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
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
		List<WatershedMahotsavProjectLevelBean> list = new ArrayList<WatershedMahotsavProjectLevelBean>();
		List<WatershedMahotsavProjectLevelBean> dlist = new ArrayList<WatershedMahotsavProjectLevelBean>();
		List<WatershedMahotsavProjectLevelBean> comlist = new ArrayList<WatershedMahotsavProjectLevelBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("mahotsav/watershedSwachhataAtProject");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
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
				
			/*	list = serProj.getBlksWiseWatershedMahotsavAtProjLvl(regId.toString());
				for(WatershedMahotsavProjectLevelBean bean :list) {
					if(bean.getStatus().equals('D')) {
						dlist.add(bean);
					}else {
						comlist.add(bean);
					}
				}
				mav.addObject("dataList",dlist);
				mav.addObject("dataListSize",dlist.size());
				
				mav.addObject("compdataList",comlist);
				mav.addObject("compdataListSize",comlist.size());*/
				

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
	

}
