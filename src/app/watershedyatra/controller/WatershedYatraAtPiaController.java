package app.watershedyatra.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.bean.Login;
import app.bean.ProfileBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.service.ProfileService;
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.service.WatershedYatraPIALevelService;
import app.watershedyatra.service.WatershedYatraService;

@Controller("WatershedYatraAtPiaController")
public class WatershedYatraAtPiaController {
	
	
HttpSession session;
	
	@Autowired
	WatershedYatraPIALevelService  serp;
	
	@Autowired
	WatershedYatraService  ser;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired(required = true)
	MenuController menuController;

	
	@Autowired
	CommonFunctions commonFunction;
	
	@RequestMapping(value = "/getWatershedYatraAtPiaHeader", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraHeader(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedYatraBean> dlist = new ArrayList<WatershedYatraBean>();
		List<WatershedYatraBean> comlist = new ArrayList<WatershedYatraBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("WatershedYatra/WatershedYatraVillageAtPia");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				String username = session.getAttribute("username").toString();
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
				mav.addObject("blkList", serp.getBlockListpia(session.getAttribute("regId").toString()));
				
				LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
				map=ser.getCultActivity();
				mav.addObject("cultMap", map);
				dlist=ser.getWatershedYatraPIAList(stcd, session.getAttribute("loginID").toString());
				mav.addObject("dataList",dlist);
				mav.addObject("dataListSize",dlist.size());
				
				comlist=ser.getWatershedYatraPIAListcomplete(stcd, session.getAttribute("loginID").toString());
				mav.addObject("comdataList",comlist);
				mav.addObject("comdataListSize",comlist.size());
				

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getWatershedYatraAtPiaGPs", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getWatershedYatraAtPiaGPs(HttpServletRequest request, @RequestParam("blkCode") int blkCode) {
		
		session = request.getSession(true);
		
		return serp.getWatershedYatraAtPiaGPs(blkCode, session.getAttribute("regId").toString());
	}	
	
	@RequestMapping(value = "/getWatershedYatraAtPiaVillage", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getWatershedYatraAtPiaVillage(HttpServletRequest request, @RequestParam("gpscode") int gpCode) {
		
		session = request.getSession(true);
		
		return serp.getWatershedYatraAtPiaVillage(gpCode, session.getAttribute("regId").toString());
	}

	
	@RequestMapping(value="/saveWatershedYatraPIAVillage", method = RequestMethod.POST)
	public ModelAndView saveWatershedYatraPIAVillage(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes,
			@ModelAttribute("useruploadsl") WatershedYatraBean userfileup) throws Exception {


		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		
		String res=null;
		try {
		if (session != null && session.getAttribute("loginID") != null) 
		{
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
			String userType = session.getAttribute("userType").toString();
			mav = new ModelAndView("WatershedYatra/WatershedYatraVillageAtPia");
			
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
			mav.addObject("distList", ser.getDistrictList(stcd));
			
			
			res= ser.saveWatershedYatraVillageupload(userfileup, session);
			if (res.equals("success")) {
				redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
			} 
			else {
				redirectAttributes.addFlashAttribute("result", "Data not saved Successfully and Upload correct file!");
			}
			return new ModelAndView("redirect:/getWatershedYatraAtPiaHeader");
		} 
		else {
			return new ModelAndView("redirect:/login");

		}	
//			if(res.equals("success"))
//				mav.addObject("result","Data saved successfully");
//			else
//				mav.addObject("result","Data not saved successfully");
		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/deleteWatershedYatraPIADetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteWatershedYatraPIADetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.deleteWatershedYatraDetails(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}

	@RequestMapping(value="/completeWatershedYatraPIADetails", method = RequestMethod.POST)
	@ResponseBody
	public String completeWatershedYatraPIADetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.completeWatershedYatraDetails(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value = "/getWatershedYatraPIAEdit", method = RequestMethod.POST)
	public ModelAndView getWatershedYatraPIAEdit(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedYatraBean> editlist = new ArrayList<WatershedYatraBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("WatershedYatra/WatershedYatraPIAVillageEdit");
				String waterId=request.getParameter("waterid");
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
				mav.addObject("distList", ser.getDistrictList(stcd));
				LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
				map=ser.getCultActivity();
				mav.addObject("cultMap", map);
				editlist=ser.getWatershedYatraEditList(Integer.parseInt(waterId));
				mav.addObject("dataList",editlist);
				mav.addObject("dataListSize",editlist.size());
				
				
				

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/updateWatershedYatraAtPIAVillage", method = RequestMethod.POST)
	public ModelAndView updateWatershedYatraAtPIAVillage(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes,
			@ModelAttribute("useruploadsl") WatershedYatraBean userfileup) throws Exception {


		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		
		String res=null;
		try {
		if (session != null && session.getAttribute("loginID") != null) 
		{
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
			String userType = session.getAttribute("userType").toString();
			mav = new ModelAndView("WatershedYatra/WatershedYatraVillageAtPia");
			
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
			mav.addObject("distList", ser.getDistrictList(stcd));
			
			
			res= ser.updateWatershedYatraAtVillage(userfileup, session);
			if (res.equals("success")) {
				redirectAttributes.addFlashAttribute("result", "Data Update Successfully.");
			} 
			else {
				redirectAttributes.addFlashAttribute("result", "Data not Updated. ");
			}
			return new ModelAndView("redirect:/getWatershedYatraAtPiaHeader");
		} 
		else {
			return new ModelAndView("redirect:/login");

		}	
		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}
