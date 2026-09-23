package app.swachhata.controller;

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
import app.mahotsav.bean.InaugurationMahotsavBean;
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
		List<WatershedSwachhataBean> list = new ArrayList<WatershedSwachhataBean>();
		List<WatershedSwachhataBean> dlist = new ArrayList<WatershedSwachhataBean>();
		List<WatershedSwachhataBean> comlist = new ArrayList<WatershedSwachhataBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("mahotsav/watershedSwachhataAtProject");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				String loginId = session.getAttribute("loginID").toString();
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
				
				list = serv.getWatershedSwachhataAtProj(regId.toString());
				for(WatershedSwachhataBean bean :list) 
				{
					if(bean.getStatus().equals('D')) {
						dlist.add(bean);
					}
					else {
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
	
	@RequestMapping(value = "/getVillageListFrmProjBlk", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getVillageListFrmProjBlk(HttpServletRequest request, 
			@RequestParam("projid") int projid, @RequestParam("bokckid") int bokckid) {
		session = request.getSession(true);
		return serv.getVillagebyProjIdBlock(projid, bokckid);
	}
	
	@RequestMapping(value="/checkWatershedSwachhataVillageExits", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkWatershedSwachhataVillageExits(HttpServletRequest request, HttpServletResponse response
    		,@RequestParam("village") int vcode) {
		session = request.getSession(true);
		Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
        return serv.checkWatershedSwachhataVillageExits(vcode);
    }

	
	@RequestMapping(value = "/saveWatershedSwachhataDetails", method = RequestMethod.POST)
	public ModelAndView saveWatershedSwachhataDetails(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") WatershedSwachhataBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("mahotsav/watershedSwachhataAtProject");
				int projid = Integer.parseInt(request.getParameter("project"));
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
			//	mav.addObject("blkList", serProj.getBlockbyProjId(projid));

				result = serv.saveWatershedSwachhataDetails(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
				} 
				else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved ");
				} 
				/*else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved!");
				}*/
				return new ModelAndView("redirect:/getWatershedSwachhataAtProj");
			} else {
				return new ModelAndView("redirect:/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/completeWatershedSwachhataDetails", method = RequestMethod.POST)
	@ResponseBody
	public String completeWatershedSwachhataDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=serv.completeWatershedSwachhataDetails(assetid, session.getAttribute("loginID").toString());
		 
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/deleteWatershedSwachhataDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteWatershedSwachhataDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=serv.deleteWatershedSwachhataDetails(assetid, session.getAttribute("loginID").toString());
		 
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value = "/getImageSwachhataProjLvlId", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageSwachhataProjLvlId(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("swachhataid") Integer swachhataid){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = serv.getImageSwachhataProjLvlId(swachhataid);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}
	
	@RequestMapping(value = "/getWatershedSwachhataidProjLvlEdit", method = RequestMethod.POST)
	public ModelAndView getWatershedSwachhataidProjLvlEdit(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedSwachhataBean> editlist = new ArrayList<WatershedSwachhataBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("mahotsav/updateWatershedSwachhataAtProject");
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
				//mav.addObject("distList", ser.getDistrictList(stcd));
				
				editlist=serv.getWatershedSwachhataidProjLvlEdit(Integer.parseInt(waterid));
				
				mav.addObject("dataList",editlist);
				mav.addObject("dataListSize",editlist.size());

			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/updateWatershedSwachhataDetails", method = RequestMethod.POST)
	public ModelAndView updateWatershedSwachhataDetails(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") WatershedSwachhataBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("mahotsav/watershedSwachhataAtProject");
				int projid = Integer.parseInt(request.getParameter("project"));
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
			//	mav.addObject("blkList", serProj.getBlockbyProjId(projid));

				result = serv.updateWatershedSwachhataDetails(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data Updated Successfully");
				} 
				else {
					redirectAttributes.addFlashAttribute("result", "Data Updation failed!");
				} 
				/*else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved!");
				}*/
				return new ModelAndView("redirect:/getWatershedSwachhataAtProj");
			} else {
				return new ModelAndView("redirect:/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/getWatershedSwachhataReport", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraReport(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("mahotsav/watershedSwachhataReport");
		
		List<WatershedSwachhataBean> list = serv.getProjectLevelSwachhataStateWise(0);
		mav.addObject("projLvlWSPrgList", list);
		mav.addObject("projLvlWSPrgListSize", list.size());
		
		return mav; 
	}
	
	@RequestMapping(value = "/getImageByStcode", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageByStcode(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("stCode") Integer stcode){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = serv.getImageByStcode(stcode);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}
	
	@RequestMapping(value = "/distWSProjLvlProgRpt", method = RequestMethod.GET)
	public ModelAndView distWSProjLvlProgRpt(HttpServletRequest request, HttpServletResponse response) {
		
		String stcd = request.getParameter("stcd");
		String stName = request.getParameter("stName");
		
		List<WatershedSwachhataBean> list = new ArrayList<WatershedSwachhataBean>();
		
		ModelAndView mav = new ModelAndView("mahotsav/watershedSwachhataReport");
		
		list = serv.getdistWSProjLvlProgRpt(Integer.parseInt(stcd));
		
		mav.addObject("stcd",stcd);
		mav.addObject("stName",stName);
		mav.addObject("distWSProjList",list);
		mav.addObject("distWSProjListSize",list.size());
		
		return mav;
	}
	
	@RequestMapping(value = "/getImageByDcode", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageByDcode(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("dcode") Integer dcode){
		List<String> imgList = new ArrayList<>();
		System.out.println("check it.");
		try {
			imgList = serv.getImageByDcode(dcode);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}
	
	
	

}
