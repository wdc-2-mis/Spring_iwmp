package app.mahotsav.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import app.controllers.MenuController;
import app.mahotsav.bean.SocialMediaReport;
import app.mahotsav.service.WMSocialMediaAnalysisService;
import app.service.StateMasterService;

@Controller("WMSocialMediaAnalysisReport")
public class WMSocialMediaAnalysisReport {

    @Autowired(required = true)
    MenuController menuController;

    @Autowired
    StateMasterService stateMasterService;

    @Autowired
    WMSocialMediaAnalysisService wmService;

    private Map<Integer, String> stateList;
    private Map<String, String> districtList;
    private Map<Integer, String> platformList;

    @RequestMapping(value = "/getWMSocialMediaAnalysisReport", method = RequestMethod.GET)
    public ModelAndView getWMSocialMediaAnalysisReport(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("mahotsav/WMSocialMediaAnalysisReport");

        stateList = stateMasterService.getAllState();
        mav.addObject("stateList", stateList);

        districtList = new LinkedHashMap<>();
        mav.addObject("districtList", districtList);

//        Map<Integer, String> platformList = wmService.getPlatformList();
        mav.addObject("platformList", platformList);

        List<SocialMediaReport> list = new ArrayList<>();
        mav.addObject("wmList", list);
        mav.addObject("wmListSize", list.size());

        return mav;
    }


    @RequestMapping(value = "/wmSocialMediaAnalysisReport", method = RequestMethod.POST)
    public ModelAndView wmSocialMediaAnalysisReport(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("mahotsav/WMSocialMediaAnalysisReport");

        int stcd = Integer.parseInt(request.getParameter("state"));
        int dcode = Integer.parseInt(request.getParameter("district"));
        int media = Integer.parseInt(request.getParameter("platform"));

        mav.addObject("stateList", stateMasterService.getAllState());
        mav.addObject("districtList", wmService.getDistrictList(stcd));
        mav.addObject("platformList", wmService.getPlatformList());

        List<SocialMediaReport> list =
                wmService.getWMSocialMediaAnalysisReport(stcd, dcode, media);

        mav.addObject("wmList", list);
        mav.addObject("wmListSize", list.size());

        mav.addObject("state", stcd);
        mav.addObject("district", dcode);
        mav.addObject("platform", media);

        return mav;
    }

    @RequestMapping(value = "/getDistrictsByStateForWM", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getDistrictsByStateForWM(int state) {
        return wmService.getDistrictList(state);
    }

    @RequestMapping(value = "/getPlatformList", method = RequestMethod.POST)
    @ResponseBody
    public Map<Integer, String> getPlatformList() {
        return wmService.getPlatformList();
    }
}
