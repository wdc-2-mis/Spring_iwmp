package app.punarutthan.controller;

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
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.bean.WMPrabhatPheriBean;
import app.punarutthan.service.WatershedPunarutthanService;
import app.service.ProfileService;
import app.watershedyatra.bean.WatershedYatraBean;

@Controller
public class WatershedPunarutthanController {

	HttpSession session;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	WatershedPunarutthanService ser;
	
	@RequestMapping(value = "/getWatershedPunarutthanPlan", method = RequestMethod.GET)
	public ModelAndView getWatershedPunarutthanPlan(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedPunarutthanBean> datlist = new ArrayList<WatershedPunarutthanBean>();
		List<WatershedPunarutthanBean> complist = new ArrayList<WatershedPunarutthanBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("punarutthan/watershedPunarutthanPlan");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				String username = session.getAttribute("username").toString();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				listm=profileService.getPunarutthanStateDist(regId);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				int stCodelgd = 0;
				int distCodelgd = 0;
				for(ProfileBean bean : listm) {
					distName =bean.getDistrictname();
					distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
					stCodelgd=bean.getState_codelgd();
					distCodelgd=bean.getDistrict_codelgd();
				}
				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
				mav.addObject("projList", ser.getProjectListMis(distCodelgd));
				mav.addObject("StructureList", ser.getStructureListMis());
				
				datlist=ser.getWatershedPunarutthanPlanDraft(session, distCode, stCode); 
				  mav.addObject("dataList1",datlist);
				  mav.addObject("dataListSize1",datlist.size());
				  
				  complist=ser.getWatershedPunarutthanPlanComplete(session, distCode, stCode);
				  mav.addObject("comdataList1",complist);
				  mav.addObject("comdataListSize1",complist.size());
				 
			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getPunarutthanVillage", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<String, Integer> getPunarutthanVillage(HttpServletRequest request, @RequestParam("pCode") String pCode) {
		
		return ser.getPunarutthanVillage(pCode);
	}
	
	@RequestMapping(value = "/saveWatershedPunarutthanPlan", method = RequestMethod.POST)
	public ModelAndView saveWatershedPunarutthanPlan(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") WatershedPunarutthanBean userfileup)
			throws Exception {
		//System.out.println("kdy");
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		List<String> imageNames = new ArrayList<>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("punarutthan/watershedPunarutthanPlan");
				
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm=profileService.getPunarutthanStateDist(regId);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				int stCodelgd = 0;
				int distCodelgd = 0;
				for(ProfileBean bean : listm) {
					distName =bean.getDistrictname();
					distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
					stCodelgd=bean.getState_codelgd();
					distCodelgd=bean.getDistrict_codelgd();
				}

				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
				mav.addObject("projList", ser.getProjectListMis(distCodelgd));
				mav.addObject("StructureList", ser.getStructureListMis());
				
				result = ser.saveWatershedPunarutthanPlan(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
				} 
				else if (result.equals("failexist")) {
					redirectAttributes.addFlashAttribute("result", "Data not saved, Geo-referenced with Location already exist!");
				} 
				else {
					redirectAttributes.addFlashAttribute("result", "Data not saved!");
				}
				return new ModelAndView("redirect:/getWatershedPunarutthanPlan");
			} 
			else {
				return new ModelAndView("redirect:/login");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getExistingPunarutthanPlanVillageCodes", method = RequestMethod.POST)
	@ResponseBody
	public String getExistingPunarutthanPlanVillageCodes( HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="villageCode") Integer villageCode ) {
	  return ser.getExistingPunarutthanPlanVillageCodes(villageCode);
	}
	
	@RequestMapping(value="/deletePunarutthanPlanDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deletePunarutthanPlanDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.deletePunarutthanPlanDetails(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/completePunarutthanPlanDetails", method = RequestMethod.POST)
	@ResponseBody
	public String completePunarutthanPlanDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.completePunarutthanPlanDetails(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value = "/getImageWatershedPunarutthanPlan", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageMahotsavInaugurationId(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("planid") Integer planid){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = ser.getImageWatershedPunarutthanPlan(planid);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}
	
	@RequestMapping(value = "/getWatershedPunarutthanPlanImplement", method = RequestMethod.GET)
	public ModelAndView getWatershedPunarutthanPlanImplement(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedPunarutthanBean> dataListd = new ArrayList<WatershedPunarutthanBean>();
		List<WatershedPunarutthanBean> comdataListc = new ArrayList<WatershedPunarutthanBean>();
		List<WatershedPunarutthanBean> complist = new ArrayList<WatershedPunarutthanBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("punarutthan/watershedPunarutthanPlanImplement");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				String username = session.getAttribute("username").toString();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				listm=profileService.getPunarutthanStateDist(regId);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				int stCodelgd = 0;
				int distCodelgd = 0;
				for(ProfileBean bean : listm) {
					distName =bean.getDistrictname();
					distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
					stCodelgd=bean.getState_codelgd();
					distCodelgd=bean.getDistrict_codelgd();
				}
				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
				mav.addObject("projList", ser.getProjectListMis(distCodelgd));
				mav.addObject("StructureList", ser.getStructureListMis());
				
				  complist=ser.getWatershedPunarutthanPlanCompletetoImpl(session, distCode, stCode);
				  //datlist=ser.getWatershedPunarutthanPlanDraft(session, distCode, stCode); 
				  mav.addObject("comdataList",complist);
				  mav.addObject("comdataListSize",complist.size());
				  
				  dataListd=ser.getPunarutthanDraftImplementation(session, distCode, stCode);
				  mav.addObject("dataListd",dataListd);
				  mav.addObject("dataListSized",dataListd.size());
				  
				  comdataListc=ser.getPunarutthanCompleteImplementation(session, distCode, stCode);
				  mav.addObject("comdataListc",comdataListc);
				  mav.addObject("comdataListSizec",comdataListc.size());
				 
			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getWatershedPunarutthanPlanImplement", method = RequestMethod.POST)
	public ModelAndView getWatershedPunarutthanPlanImplement1(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedPunarutthanBean> dataListd = new ArrayList<WatershedPunarutthanBean>();
		List<WatershedPunarutthanBean> comdataListc = new ArrayList<WatershedPunarutthanBean>();
		List<WatershedPunarutthanBean> complist = new ArrayList<WatershedPunarutthanBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("punarutthan/watershedPunarutthanPlanImplement");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				String username = session.getAttribute("username").toString();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				listm=profileService.getPunarutthanStateDist(regId);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				int stCodelgd = 0;
				int distCodelgd = 0;
				for(ProfileBean bean : listm) {
					distName =bean.getDistrictname();
					distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
					stCodelgd=bean.getState_codelgd();
					distCodelgd=bean.getDistrict_codelgd();
				}
				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
				mav.addObject("projList", ser.getProjectListMis(distCodelgd));
				mav.addObject("StructureList", ser.getStructureListMis());
				
				  complist=ser.getWatershedPunarutthanPlanCompletetoImpl(session, distCode, stCode);
				 // complist=ser.getWatershedPunarutthanPlanComplete(session.getAttribute("loginID").toString());
				  mav.addObject("comdataList",complist);
				  mav.addObject("comdataListSize",complist.size());
				  
				  dataListd=ser.getPunarutthanDraftImplementation(session, distCode, stCode);
				  mav.addObject("dataListd",dataListd);
				  mav.addObject("dataListSized",dataListd.size());
				  
				  comdataListc=ser.getPunarutthanCompleteImplementation(session, distCode, stCode);
				  mav.addObject("comdataListc",comdataListc);
				  mav.addObject("comdataListSizec",comdataListc.size());
				 
			} 
			else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value = "/getWatershedPunarutthanPlanImpl", method = RequestMethod.POST)
	public ModelAndView getWatershedPunarutthanPlanImpl(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<WatershedPunarutthanBean> editlist = new ArrayList<WatershedPunarutthanBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("punarutthan/watershedPunarutthanImplementation");
				String plan_id=request.getParameter("plan_id");
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm=new  ArrayList<ProfileBean>();
				listm=profileService.getPunarutthanStateDist(regId);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				int stCodelgd = 0;
				int distCodelgd = 0;
				for(ProfileBean bean : listm) {
					distName =bean.getDistrictname();
					distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
					stCodelgd=bean.getState_codelgd();
					distCodelgd=bean.getDistrict_codelgd();
				}
				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
				mav.addObject("projList", ser.getProjectListMis(distCodelgd));
				mav.addObject("StructureList", ser.getStructureListMis());
				
				editlist=ser.getWatershedPunarutthanPlanImpl(Integer.parseInt(plan_id));
				
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
	
	@RequestMapping(value = "/saveWatershedPunarutthanImplementation", method = RequestMethod.POST)
	public ModelAndView saveWatershedPunarutthanImplementation(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") WatershedPunarutthanBean userfileup)
			throws Exception {
		System.out.println("kdy");
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		List<String> imageNames = new ArrayList<>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("punarutthan/watershedPunarutthanPlanImplement");
				
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm=profileService.getPunarutthanStateDist(regId);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				int stCodelgd = 0;
				int distCodelgd = 0;
				for(ProfileBean bean : listm) {
					distName =bean.getDistrictname();
					distCode = bean.getDistrictcode()==null?0:bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode()==null?0:bean.getStatecode();
					stCodelgd=bean.getState_codelgd();
					distCodelgd=bean.getDistrict_codelgd();
				}

				mav.addObject("userType",userType);
				mav.addObject("distName",distName);
				mav.addObject("distCode",distCode);
				mav.addObject("stateName",stateName);
				mav.addObject("projList", ser.getProjectListMis(distCodelgd));
				mav.addObject("StructureList", ser.getStructureListMis());
				
				result = ser.saveWatershedPunarutthanImplementation(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
				} 
				else if (result.equals("failexist")) {
					redirectAttributes.addFlashAttribute("result", "Data not saved, Geo-referenced with Location already exist!");
				} 
				else {
					redirectAttributes.addFlashAttribute("result", "Data not saved or photo should not contain geotag!");
				}
				return new ModelAndView("redirect:/getWatershedPunarutthanPlanImplement");
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
	
	@RequestMapping(value="/completePunarutthanImplementation", method = RequestMethod.POST)
	@ResponseBody
	public String completePunarutthanImplementation(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.completePunarutthanImplementation(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/deletePunarutthanImplementation", method = RequestMethod.POST)
	@ResponseBody
	public String deletePunarutthanImplementation(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.deletePunarutthanImplementation(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
}
