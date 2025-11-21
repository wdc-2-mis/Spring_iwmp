package app.mahotsav.controller;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.bean.Login;
import app.bean.ProfileBean;
import app.mahotsav.bean.InaugurationMahotsavBean;
import app.mahotsav.service.WatershedMahotsavInaugurationService;
import app.service.ProfileService;
import app.watershedyatra.bean.InaugurationBean;
import app.watershedyatra.service.WatershedYatraService;

@Controller("wMInaugurationController")
public class WMInaugurationController {

	HttpSession session;
	
	@Autowired
	WatershedYatraService ser;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	WatershedMahotsavInaugurationService iSer;
	
	@RequestMapping(value="/registerInauguration", method = RequestMethod.GET)
	public ModelAndView registerInauguration(HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
	//	long timeBasedId = System.currentTimeMillis();
		List<InaugurationMahotsavBean> data = new ArrayList<InaugurationMahotsavBean>();
		List<InaugurationMahotsavBean> compdata = new ArrayList<InaugurationMahotsavBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {
				mav = new ModelAndView("mahotsav/mahotsavinauguration");
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
				data=iSer.getregisterInaugurationDetails(stcd);
				mav.addObject("dataList",data);
				mav.addObject("dataListSize",data.size());
				
				compdata=iSer.getregisterInaugurationDetailsComp(stcd);
				mav.addObject("compdataList",compdata);
				mav.addObject("compdataListSize",compdata.size());
				
				mav.addObject("userType", userType);
				mav.addObject("stateName", stateName);
				mav.addObject("distList", ser.getDistrictList(stcd));
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
	
	
	@RequestMapping(value = "/saveMahotsavInaugurationDetails", method = RequestMethod.POST)
	public ModelAndView saveMahotsavInaugurationDetails(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, @ModelAttribute("useruploadign") InaugurationMahotsavBean userfileup)
			throws Exception {

		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		String result = "fail";
		List<String> imageNames = new ArrayList<>();
		List<InaugurationMahotsavBean> data = new ArrayList<InaugurationMahotsavBean>();
		List<InaugurationMahotsavBean> compdata = new ArrayList<InaugurationMahotsavBean>();
		try {
			if (session != null && session.getAttribute("loginID") != null) {

				mav = new ModelAndView("mahotsav/mahotsavinauguration");

				for(String aa:userfileup.getPhotos_bhoomipoojan_time()) {
					
					System.out.println("lat =" +aa);
				}
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
				
				data=iSer.getregisterInaugurationDetails(stcd);
				mav.addObject("dataList",data);
				mav.addObject("dataListSize",data.size());
				
				compdata=iSer.getregisterInaugurationDetailsComp(stcd);
				mav.addObject("compdataList",compdata);
				mav.addObject("compdataListSize",compdata.size());

				result = iSer.saveMahotsavInaugurationDetails(userfileup, session);

				if (result.equals("success")) {
					redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
				} 
				else if (result.equals("failexist")) {
					redirectAttributes.addFlashAttribute("result1", "Data not saved State Data already exist");
				} 
				else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved Successfully!");
				}
				return new ModelAndView("redirect:/registerInauguration");
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
	
	@RequestMapping(value="/deleteMahotsavInaugurationDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteMahotsavInaugurationDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=iSer.deleteMahotsavInaugurationDetails(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
	
	
	@RequestMapping(value="/completeMahotsavInaugurationDetails", method = RequestMethod.POST)
	@ResponseBody
	public String completeMahotsavInaugurationDetails(HttpServletRequest request, HttpServletResponse response, @RequestParam(value ="assetid") List<Integer> assetid)
	{
		ModelAndView mav = new ModelAndView();
		String res="";
		session = request.getSession(true);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			Integer sentfrom = Integer.parseInt(session.getAttribute("regId").toString());
			String userType= session.getAttribute("userType").toString();
			res=iSer.completeMahotsavInaugurationDetails(assetid, session.getAttribute("loginID").toString());
		
		 
		}else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return res; 
	}
			
}
