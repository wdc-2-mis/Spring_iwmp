package app.watershedyatra.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
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
import app.watershedyatra.service.WatershedYatraService;
import app.watershedyatra.serviceImpl.InaugurationServiceImpl;

@Controller("InaugurationController")
public class InaugurationController extends HttpServlet {

	HttpSession session;

	@Autowired
	WatershedYatraService ser;

	@Autowired(required = true)
	ProfileService profileService;

	@Autowired
	InaugurationServiceImpl iSer;

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
				} else {
					redirectAttributes.addFlashAttribute("result1", "Data not saved Successfully and Upload correct file!");
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
	
	

}
