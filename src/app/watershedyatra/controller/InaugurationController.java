package app.watershedyatra.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.ProfileBean;
import app.bean.reports.UserFileUploadBean;
import app.service.ProfileService;
import app.service.StateMasterService;
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
			@ModelAttribute("useruploadign") InaugurationBean userfileup) throws Exception {
	
	
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

				if(result.equals("success"))
					mav.addObject("result", "Data saved Successfully");
				else
					mav.addObject("result", "Data not saved Successfully!");

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
	

}
