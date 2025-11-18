package app.mahotsav.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.bean.Login;
import app.bean.ProfileBean;
import app.service.ProfileService;
import app.watershedyatra.bean.PreYatraPreparationBean;
import app.watershedyatra.service.WatershedYatraPIALevelService;
import app.watershedyatra.service.WatershedYatraService;

@Controller("wMPrabhatPheriController")
public class WMPrabhatPheriController {

HttpSession session;
	

@Autowired(required = true)
ProfileService profileService;

@Autowired
WatershedYatraService  ser;

@Autowired
WatershedYatraPIALevelService  serp;
	
@RequestMapping(value = "/getWMPrabhatPheri", method = RequestMethod.GET)
public ModelAndView getWMPrabhatPheri(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(false); // Avoid unnecessary session creation
    ModelAndView mav;
    List<PreYatraPreparationBean> preyatracompletedata = new ArrayList<PreYatraPreparationBean>();
    if (session != null && session.getAttribute("loginID") != null) {
        mav = new ModelAndView("mahotsav/mahotsavPrabhatPheri");
        Object regIdObj = session.getAttribute("regId");
        Integer regId = (regIdObj != null) ? Integer.parseInt(regIdObj.toString()) : null;
        List<PreYatraPreparationBean> preyatrasavedata = new ArrayList<PreYatraPreparationBean>();
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
        
        if(userType.equals("SL")){
        preyatrasavedata=ser.getpreyatrasaveRecord(stcd);
        }
        else if(userType.equals("PI")){
        preyatrasavedata=ser.getpreyatraPIAsaveRecord(stcd, session.getAttribute("loginID").toString());
        }
        
        if(userType.equals("SL")){
        preyatracompletedata=ser.getpreyatracompleteRecord(stcd);
        }
        else if(userType.equals("PI")){
        preyatracompletedata=ser.getpreyatraPIAcompleteRecord(stcd, session.getAttribute("loginID").toString());
        }
		mav.addObject("records",preyatrasavedata);
		mav.addObject("comprecords",preyatracompletedata);
		mav.addObject("blkList", serp.getBlockListpia(regId.toString()));
    
    
    
    } else {
        mav = new ModelAndView("login");
        mav.addObject("login", new Login());
    }

    return mav;
}

	
}
