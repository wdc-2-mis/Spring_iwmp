package app.watershedyatra.controller;

import java.math.BigDecimal;
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
import app.bean.pfms.AdditionalBroughtFarmerCropAreaBean;
import app.service.ProfileService;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.service.WatershedYatraPIALevelService;
import app.watershedyatra.service.WatershedYatraService;

@Controller("NodalOfficerLMSController")
public class NodalOfficerLMSController {
	
	
	HttpSession session;
	
	@Autowired
	WatershedYatraService  ser;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	WatershedYatraPIALevelService  serp;
	
	@RequestMapping(value = "/getNodalOfficerHeader", method = RequestMethod.GET)
	public ModelAndView getWatershedYatraHeader(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		List<NodalOfficerBean> draft = new ArrayList<NodalOfficerBean>();
		List<NodalOfficerBean> complete = new ArrayList<NodalOfficerBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("WatershedYatra/nodalOfficerLMS");
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
				
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				
				if(userType.equals("SL")){
				map.put("state", "State");
				map.put("district", "District");
				map.put("block", "Block/Project");
				map.put("village", "Village/Van Standing Point");
				}
				 else if(userType.equals("PI")){
					 map.put("block", "Block/Project");
					 map.put("village", "Village/Van Standing Point");
				 }
				
				mav.addObject("level",map);
				mav.addObject("blkList", serp.getBlockListpia(regId.toString()));
				
				if(userType.equals("SL")){
				draft=ser.getDraftListofNodalOfficer(stcd);
				}
				else if(userType.equals("PI")){
				draft=ser.getDraftListofPIANodalOfficer(stcd, session.getAttribute("loginID").toString());
				}
				
				mav.addObject("draftList",draft);
				mav.addObject("draftListSize",draft.size());
				mav.addObject("distCode", distCode);
				
				if(userType.equals("SL")){
				complete=ser.getCompleteListofNodalOfficer(stcd);
				}
				
				else if(userType.equals("PI")){
					complete=ser.getCompleteListofPIANodalOfficer(stcd, session.getAttribute("loginID").toString());
				}
				
				mav.addObject("completetList",complete);
				mav.addObject("completeListSize",complete.size());

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping(value="/saveNodalOfficerLMS", method = RequestMethod.POST)
	@ResponseBody
	public String saveNodalOfficerLMS(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value ="level") String level, @RequestParam(value ="district") Integer district, 
			@RequestParam(value ="block") Integer block, @RequestParam(value ="name") String name, 
			@RequestParam(value ="designation") String designation, @RequestParam(value ="mob") String mob,
			@RequestParam(value ="email") String email)
			
	{
		String res="fail";
		session = request.getSession(true);
		String userType = session.getAttribute("userType").toString();
		String state=session.getAttribute("stateCode").toString();
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer regid = Integer.parseInt(session.getAttribute("regId").toString());
			
				res=ser.saveNodalOfficerLMS(level, state, district, block, name, designation, mob, email, session);
			
		}
		
		return res; 
	}
	
	@RequestMapping(value="/completeApproveNodalOfficer", method = RequestMethod.POST)
	@ResponseBody
	public String completeApproveNodalOfficer(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.completeApproveNodalOfficer(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/deleteApproveNodalOfficer", method = RequestMethod.POST)
	@ResponseBody
	public String deleteApproveNodalOfficer(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=ser.deleteApproveNodalOfficer(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	

}
