package app.watershedyatra.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.bean.Login;
import app.bean.ProfileBean;
import app.service.ProfileService;
import app.watershedyatra.bean.NodalOfficerBean;
import app.watershedyatra.bean.PreYatraPrepBean;
import app.watershedyatra.bean.PreYatraPreparationBean;
import app.watershedyatra.service.WatershedYatraService;

@Controller("preYatraPrepController")
public class PreYatraPrepController {

	HttpSession session;
	
	@Autowired(required = true)
	ProfileService profileService;
	
	@Autowired
	WatershedYatraService  ser;
	
	@RequestMapping(value = "/getPreYatraPrep", method = RequestMethod.GET)
	public ModelAndView getPreYatraPrep(HttpServletRequest request, HttpServletResponse response) {
	    HttpSession session = request.getSession(false); // Avoid unnecessary session creation
	    ModelAndView mav;
	    List<PreYatraPreparationBean> preyatrasavedata = new ArrayList<PreYatraPreparationBean>();
	    if (session != null && session.getAttribute("loginID") != null) {
	        mav = new ModelAndView("WatershedYatra/preYatraPrep");

	        Object regIdObj = session.getAttribute("regId");
	        Integer regId = (regIdObj != null) ? Integer.parseInt(regIdObj.toString()) : null;

	        Object stcdObj = session.getAttribute("stateCode");
	        Integer stcd = (stcdObj != null) ? Integer.parseInt(stcdObj.toString()) : null;

	        String userType = (session.getAttribute("userType") != null) ? session.getAttribute("userType").toString() : "";
          
	        List<ProfileBean> listm = profileService.getMapstate(regId, userType);

	        if (!listm.isEmpty()) {
	            ProfileBean bean = listm.get(0);
	            mav.addObject("stateName", bean.getStatename());
	            mav.addObject("distName", bean.getDistrictname());
	            mav.addObject("stCode", bean.getStatecode() != null ? bean.getStatecode() : 0);
	            mav.addObject("distCode", bean.getDistrictcode() != null ? bean.getDistrictcode() : 0);
	        }

	        mav.addObject("userType", userType);
	        mav.addObject("distList", ser.getDistrictList(stcd));
	        preyatrasavedata=ser.getpreyatrasaveRecord(stcd);
			mav.addObject("records",preyatrasavedata);
	    } else {
	        mav = new ModelAndView("login");
	        mav.addObject("login", new Login());
	    }

	    return mav;
	}

	@RequestMapping(value="/savePreYatraPreparation", method = RequestMethod.POST)
	public ModelAndView savePreYatraPreparation(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,
	        @ModelAttribute("preyatraprep") PreYatraPrepBean preYatraPrep) throws Exception {

	    HttpSession session = request.getSession(true);
	    ModelAndView mav = new ModelAndView();
	    String res = null;

	    try {
	        if (session != null && session.getAttribute("loginID") != null) {
	            Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
	            Integer stcd = Integer.parseInt(session.getAttribute("stateCode").toString());
	            String userType = session.getAttribute("userType").toString();
	            mav.setViewName("WatershedYatra/preYatraPrep");

	            List<ProfileBean> listm = profileService.getMapstate(regId, userType);
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
	            mav.addObject("distName", distName);
	            mav.addObject("stateName", stateName);
	            mav.addObject("distList", ser.getDistrictList(stcd));

	            res = ser.savePreYatraPrep(preYatraPrep, session, request);
	            if (res.equals("success")) {
	                redirectAttributes.addFlashAttribute("result", "Data saved Successfully");
	            } else {
	                redirectAttributes.addFlashAttribute("result", "Data not saved Successfully!");
	            }
	            return new ModelAndView("redirect:/getPreYatraPrep");
	        } else {
	            return new ModelAndView("redirect:/login");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return mav;
	}

	
    @RequestMapping(value="/checkGramPanchayat", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkGramPanchayat(@RequestParam("gramCode") Integer gramCode, @RequestParam("preyatraType") String preyatraType) {
        return ser.checkgrampanchayat(gramCode, preyatraType);
    }
    
    @RequestMapping(value="/checkVillage", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkVillage(@RequestParam("vCode") Integer vCode, @RequestParam("preyatraType") String preyatraType) {
        return ser.checkVillageStatus(vCode, preyatraType);
    }
}
