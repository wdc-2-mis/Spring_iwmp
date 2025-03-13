
package app.watershedyatra.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import app.watershedyatra.service.WatershedYatraService;


@Controller("WatershedYatraController")
public class WatershedYatraController {
	
	HttpSession session;
	
	@Autowired
	WatershedYatraService  ser;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired(required = true)
	MenuController menuController;

	
	@Autowired
	CommonFunctions commonFunction;
	
	@RequestMapping(value = "/getWatershedYatraHeader", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraHeader(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedYatraBean> dlist = new ArrayList<WatershedYatraBean>();
		List<WatershedYatraBean> comlist = new ArrayList<WatershedYatraBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("WatershedYatra/WatershedYatraVillage");
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
				dlist=ser.getWatershedYatraList(stcd);
				mav.addObject("dataList",dlist);
				mav.addObject("dataListSize",dlist.size());
				
				comlist=ser.getWatershedYatraListcomplete(stcd);
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
	

	@RequestMapping(value = "/getWatershedYatraBlock", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getWatershedYatraBlock(HttpServletRequest request, @RequestParam("stateCode") int stateCode) {
		
		return ser.getWatershedYatraBlock(stateCode);
	}

	@RequestMapping(value = "/getWatershedYatraGPs", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getWatershedYatraGPs(HttpServletRequest request, @RequestParam("blkCode") int blkCode) {
		
		return ser.getWatershedYatraGPs(blkCode);
	}
	
	@RequestMapping(value = "/getWatershedYatraVillage", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getWatershedYatraVillage(HttpServletRequest request, @RequestParam("gpscode") int gpCode) {
		
		return ser.getWatershedYatraVillage(gpCode);
	}
	
	@RequestMapping(value = "/getExistingWatershedYatraVillageCodes", method = RequestMethod.POST)
	@ResponseBody
	public String getExistingWatershedYatraVillageCodes( HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="villageCode") Integer villageCode ) {
	  return ser.getExistingWatershedYatraVillageCodes(villageCode);
	}
	
	@RequestMapping(value = "/getExistingWatershedYatraVillageLoction", method = RequestMethod.POST)
	@ResponseBody
	public String getExistingWatershedYatraVillageLoction( HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="villageCode") Integer villageCode, @RequestParam(value ="loct") String loct ) {
	  return ser.getExistingWatershedYatraVillageLoction(villageCode, loct);
	}


@RequestMapping(value = "/getImageWatershedYatraId", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageWatershedYatraId(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("watershedYatraId") Integer watershedYatraId){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = ser.getImagesWatershedYatraId(watershedYatraId);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}

@RequestMapping(value="/deleteWatershedYatraDetails", method = RequestMethod.POST)
@ResponseBody
public String deleteWatershedYatraDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
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

@RequestMapping(value="/completeWatershedYatraDetails", method = RequestMethod.POST)
@ResponseBody
public String completeWatershedYatraDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
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

@RequestMapping(value="/saveWatershedYatraVillage", method = RequestMethod.POST)
public ModelAndView saveWatershedYatraVillage(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes,
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
		mav = new ModelAndView("WatershedYatra/WatershedYatraVillage");
		
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
		return new ModelAndView("redirect:/getWatershedYatraHeader");
	} 
	else {
		return new ModelAndView("redirect:/login");

	}	
//		if(res.equals("success"))
//			mav.addObject("result","Data saved successfully");
//		else
//			mav.addObject("result","Data not saved successfully");
	
	} 
	catch (Exception e) {
		e.printStackTrace();
	}
	return mav;
}


@RequestMapping(value = "/getWatershedYatraEdit", method = RequestMethod.POST)
public ModelAndView getWatershedYatraEdit(HttpServletRequest request, HttpServletResponse response) {
	session = request.getSession(true);
	ModelAndView mav = new ModelAndView();
	List<WatershedYatraBean> editlist = new ArrayList<WatershedYatraBean>();
	try {
		if (session != null && session.getAttribute("loginID") != null) {
			mav = new ModelAndView("WatershedYatra/WatershedYatraVillageEdit");
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


@RequestMapping(value="/updateWatershedYatraAtVillage", method = RequestMethod.POST)
public ModelAndView updateWatershedYatraAtVillage(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes,
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
		mav = new ModelAndView("WatershedYatra/WatershedYatraVillage");
		
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
		return new ModelAndView("redirect:/getWatershedYatraHeader");
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