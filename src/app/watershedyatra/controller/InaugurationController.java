package app.watershedyatra.controller;


import java.util.ArrayList;
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
import app.service.ProfileService;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.service.InaugurationService;
import app.watershedyatra.service.WatershedYatraService;

@Controller("InaugurationController")
public class InaugurationController{

	HttpSession session;

	@Autowired
	WatershedYatraService ser;

	@Autowired(required = true)
	ProfileService profileService;

	@Autowired
	InaugurationService iSer;

	@RequestMapping(value = "/inaugurationLocation", method = RequestMethod.GET)
	public ModelAndView inaugurationLocation(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();

		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("WatershedYatra/inauguration");

				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				List<InaugurationBean> data = new ArrayList<InaugurationBean>();
				List<InaugurationBean> compdata = new ArrayList<InaugurationBean>();

				for (ProfileBean bean : listm) {
					distName = bean.getDistrictname();
					distCode = bean.getDistrictcode() == null ? 0 : bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode() == null ? 0 : bean.getStatecode();
				}

				mav.addObject("userType", userType);
				mav.addObject("stateName", stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));
				
				
				data=iSer.getInaugurationDetails(stcd);
				mav.addObject("dataList",data);
				mav.addObject("dataListSize",data.size());
				
				
				compdata=iSer.getInaugurationDetailsComp(stcd);
				mav.addObject("compdataList",compdata);
				mav.addObject("compdataListSize",compdata.size());

			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new Login());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/saveInaugurationDetails", method = RequestMethod.POST)
	public ModelAndView saveInaugurationDetails(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") InaugurationBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";

		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("WatershedYatra/inauguration");

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
				// mav.addObject("distName",distName);
				mav.addObject("stateName", stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));

				result = iSer.saveInauguration(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
				} 
				else if (result.equals("failexist")) {
					redirectAttributes.addFlashAttribute("result1", "Data not saved State Data already exist");
				} 
				else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved Successfully!");
				}
				return new ModelAndView("redirect:/inaugurationLocation");
			} else {
				return new ModelAndView("redirect:/login");

//				if(result.equals("success"))
//					mav.addObject("result", "Data saved Successfully");
//				else
//					mav.addObject("result", "Data not saved Successfully!");
//
//			} 
//			else {
//				mav = new ModelAndView("login");
//				mav.addObject("login", new Login());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/getImageByInaugurationId", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getImageByInaugurationId(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("inaugId") Integer inaugurationId){
		List<String> imgList = new ArrayList<>();
		try {
			imgList = iSer.getImagesByInaugurationId(inaugurationId);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return imgList;
	}
	
	@RequestMapping(value = "/getExistingBlockInaguraCodes", method = RequestMethod.POST)
	@ResponseBody
	public String getExistingBlockInaguraCodes( HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="villageCode") Integer villageCode ) {
	  return iSer.getExistingBlockInaguraCodes(villageCode);
	}
	
	@RequestMapping(value="/deleteInaugurationDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteInaugurationDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=iSer.deleteInaugurationDetails(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	
	@RequestMapping(value="/completeInaugurationDetails", method = RequestMethod.POST)
	@ResponseBody
	public String completeInaugurationDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=iSer.completeInaugurationDetails(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	@RequestMapping(value="/editInaugurationDetails", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView editInaugurationDetails(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") InaugurationBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";

		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("WatershedYatra/updateInauguration");

				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
				String userType = session.getAttribute("userType").toString();
				
				String inaugId= request.getParameter("inaugurationId");
				String state= request.getParameter("stName");
				String district= request.getParameter("distName");
				String block= request.getParameter("blkName");
				String date= request.getParameter("date");
				String location= request.getParameter("location");
				
				List<ProfileBean> listm = new ArrayList<ProfileBean>();
				listm = profileService.getMapstate(regId, userType);
				String distName = "";
				String stateName = "";
				int stCode = 0;
				int distCode = 0;
				List<InaugurationBean> edit = new ArrayList<InaugurationBean>();
				List<InaugurationBean> compdata = new ArrayList<InaugurationBean>();

				for (ProfileBean bean : listm) {
					distName = bean.getDistrictname();
					distCode = bean.getDistrictcode() == null ? 0 : bean.getDistrictcode();
					stateName = bean.getStatename();
					stCode = bean.getStatecode() == null ? 0 : bean.getStatecode();
				}

				mav.addObject("userType", userType);
				mav.addObject("stateName", stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));
				
				
				edit=iSer.editInaugurationDetails(Integer.parseInt(inaugId));
				mav.addObject("dataList",edit);
				mav.addObject("dataListSize",edit.size());
				
				

			}
 		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	@RequestMapping(value="/updateInaugurationDetails", method = RequestMethod.POST)
	public ModelAndView updateInaugurationDetails(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes,
			@ModelAttribute("useruploadign") InaugurationBean userfileup) throws Exception {

		session = request.getSession(true);
		
		ModelAndView mav = new ModelAndView();
		
		String res=null;
		try {
		if (session != null && session.getAttribute("loginID") != null) 
		{
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
			String userType = session.getAttribute("userType").toString();
			mav = new ModelAndView("WatershedYatra/inauguration");
			
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
			
			
			res= iSer.updateInaugurationDetails(userfileup, session);
			if (res.equals("success")) {
				redirectAttributes.addFlashAttribute("result", "Data Update Successfully.");
			} 
			else {
				redirectAttributes.addFlashAttribute("result1", "Data not Updated.");
			}
			return new ModelAndView("redirect:/inaugurationLocation");
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
