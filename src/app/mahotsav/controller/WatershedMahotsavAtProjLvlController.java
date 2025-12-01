package app.mahotsav.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.itextpdf.text.pdf.events.IndexEvents.Entry;

import app.bean.Login;
import app.bean.ProfileBean;
import app.common.CommonFunctions;
import app.controllers.MenuController;
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.WatershedMahotsavProjectLevelBean;
import app.mahotsav.model.WatershedMahotsavProjectLevel;
import app.mahotsav.service.WatershedMahotsavProjLvlService;
import app.service.ProfileService;
import app.watershedyatra.bean.WatershedYatraBean;
import app.watershedyatra.service.WatershedYatraPIALevelService;
import app.watershedyatra.service.WatershedYatraService;

@Controller
public class WatershedMahotsavAtProjLvlController {
	
	
HttpSession session;
	
	@Autowired
	WatershedYatraPIALevelService  serp;
	
	@Autowired
	WatershedMahotsavProjLvlService  serProj;
	
	@Autowired
	WatershedYatraService  ser;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired(required = true)
	MenuController menuController;

	
	@Autowired
	CommonFunctions commonFunction;
	
	
	@RequestMapping(value = "/getWatershedMahotsavAtProjLvl", method = RequestMethod.GET)
	public ModelAndView getWatershedMahotsavAtProjLvl(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedMahotsavProjectLevelBean> list = new ArrayList<WatershedMahotsavProjectLevelBean>();
		List<WatershedMahotsavProjectLevelBean> dlist = new ArrayList<WatershedMahotsavProjectLevelBean>();
		List<WatershedMahotsavProjectLevelBean> comlist = new ArrayList<WatershedMahotsavProjectLevelBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("mahotsav/watershedMahotsavProjectLvl");
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
				mav.addObject("blkList", serp.getBlockListpia(session.getAttribute("regId").toString()));
				List<Integer> blkcds = new ArrayList<>();
				for(Map.Entry<Integer, String> map : serp.getBlockListpia(session.getAttribute("regId").toString()).entrySet()) {
					blkcds.add(map.getKey());
				}
				
//				LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
//				map=ser.getCultActivity();
//				mav.addObject("cultMap", map);
				list = serProj.getBlksWiseWatershedMahotsavAtProjLvl(blkcds, session.getAttribute("loginID").toString());
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
				mav.addObject("compdataListSize",comlist.size());
				

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/getWatershedMahotsavAtProjLvl", method = RequestMethod.POST)
	public ModelAndView getBlkWatershedMahotsavAtProjLvl(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		int bcode = Integer.parseInt(request.getParameter("block"));
		String dateTime = request.getParameter("datetime");
		List<WatershedMahotsavProjectLevelBean> dlist = new ArrayList<WatershedMahotsavProjectLevelBean>();
		List<WatershedMahotsavProjectLevelBean> comlist = new ArrayList<WatershedMahotsavProjectLevelBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("mahotsav/watershedMahotsavProjectLvl");
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
				mav.addObject("datetimeValue", dateTime);
				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
				mav.addObject("blkcode",bcode);
				mav.addObject("blkList", serp.getBlockListpia(session.getAttribute("regId").toString()));
				
//				LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
//				map=ser.getCultActivity();
//				mav.addObject("cultMap", map);
				dlist=serProj.getWatershedMahotsavAtProjLvl(bcode, session.getAttribute("loginID").toString());
				Boolean check = false;
				if(dlist.size()>0) {
					check = true;
					mav.addObject("result","Data already saved for this Block. Please select different Block Name.");
				}
				mav.addObject("dataList",dlist);
				mav.addObject("dataListSize",dlist.size());
				
				comlist=serProj.getComWatershedMahotsavAtProjLvl(bcode, session.getAttribute("loginID").toString());
				if(comlist.size()>0) {
					check = true;
					mav.addObject("result","Data already Completed for this Block. Please select different Block Name.");
				}
				mav.addObject("compdataList",comlist);
				mav.addObject("compdataListSize",comlist.size());
				mav.addObject("check",check);
				

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getWatershedMahotsavAtBlockLvl", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getWatershedMahotsavAtBlockLvl(HttpServletRequest request, @RequestParam("blkCode") int blkCode) {
		
		session = request.getSession(true);
		
		return serp.getWatershedYatraAtPiaGPs(blkCode, session.getAttribute("regId").toString());
	}
	
	@RequestMapping(value = "/saveWatershedMahotsavProjLvlDetails", method = RequestMethod.POST)
	public ModelAndView saveWatershedMahotsavProjLvlDetails(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") WatershedMahotsavProjectLevelBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("mahotsav/watershedMahotsavProjectLvl");
				
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				
				for (ProfileBean bean : listm) {
					distName = bean.getDistrictname();
					distCode = bean.getDistrictcode() == null ? 0 : bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode() == null ? 0 : bean.getStatecode();
				}
				
				mav.addObject("userType", userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
				mav.addObject("blkList", serp.getBlockListpia(session.getAttribute("regId").toString()));

				result = serProj.saveMahotsavProjLvlDetails(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
				} 
				else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved State Data already exist");
				} 
				/*else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved!");
				}*/
				return new ModelAndView("redirect:/getWatershedMahotsavAtProjLvl");
			} else {
				return new ModelAndView("redirect:/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/completeMahotsavProjLvlDetails", method = RequestMethod.POST)
	@ResponseBody
	public String completeMahotsavProjLvlDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=serProj.completeMahotsavProjLvlDetails(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/deleteMahotsavProjLvlDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteMahotsavProjLvlDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=serProj.deleteMahotsavProjLvlDetails(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	
	@RequestMapping(value = "/getWatershedMahotsavProjLvlEdit", method = RequestMethod.POST)
	public ModelAndView getWatershedMahotsavProjLvlEdit(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedMahotsavProjectLevelBean> editlist = new ArrayList<WatershedMahotsavProjectLevelBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("mahotsav/updateWatershedMahotsavProjLvl");
				String waterid=request.getParameter("waterid");
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
				
				editlist=serProj.getWatershedMahotsavProjLvlDtlForEdit(Integer.parseInt(waterid));
				
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
	
	@RequestMapping(value = "/updateWatershedMahotsavProjLvlDetails", method = RequestMethod.POST)
	public ModelAndView updateWatershedMahotsavProjLvlDetails(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") WatershedMahotsavProjectLevelBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("mahotsav/watershedMahotsavProjectLvl");
				
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				
				for (ProfileBean bean : listm) {
					distName = bean.getDistrictname();
					distCode = bean.getDistrictcode() == null ? 0 : bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode() == null ? 0 : bean.getStatecode();
				}
				
				mav.addObject("userType", userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
				mav.addObject("blkList", serp.getBlockListpia(session.getAttribute("regId").toString()));

				result = serProj.updateMahotsavProjLvlDetails(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data Updated Successfully");
				} 
				else {
					redirectAttributes.addFlashAttribute("result", "Data Updation failed!");
				} 
				/*else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved!");
				}*/
				return new ModelAndView("redirect:/getWatershedMahotsavAtProjLvl");
			} else {
				return new ModelAndView("redirect:/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getImageMahotsavProjLvlId", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageMahotsavProjLvlId(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("waterId") Integer waterId){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = serProj.getImageMahotsavProjLvl(waterId);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}

}
