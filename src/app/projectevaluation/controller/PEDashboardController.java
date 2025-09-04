package app.projectevaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import app.projectevaluation.service.ProjectEvaluationService;
import app.projectevaluation.bean.ProjectEvaluationBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller("PEDashboardController")
public class PEDashboardController {

    @Autowired
    ProjectEvaluationService PEService;

    @RequestMapping(value = "/projectEvaluationDashBoard", method = RequestMethod.GET)
    public ModelAndView projectEvaluationDashBoard(HttpServletRequest request, HttpServletResponse response, Model model) {
        ModelAndView mav = new ModelAndView("projectEvaluation/peDashBoard");

        try {
            String stcd = request.getParameter("stcd");

            List<ProjectEvaluationBean> list = PEService.getProjEvlData();
            List<ProjectEvaluationBean> statePEData = PEService.getStateProjEvlData();
            List<ProjectEvaluationBean> pieGradeData = PEService.getPieGradeData();
            List<ProjectEvaluationBean> GradeStateData = PEService.getGradeWiseStateProjEvlData("E");

            int defaultStateCode = (statePEData != null && !statePEData.isEmpty()) ? statePEData.get(0).getSt_code() : 0;
            String defaultStateName = (statePEData != null && !statePEData.isEmpty()) ? statePEData.get(0).getSt_name() : "Unknown";

            List<ProjectEvaluationBean> distPEData = PEService.getDistProjEvlData(
                (stcd != null && !stcd.isEmpty()) ? Integer.parseInt(stcd) : defaultStateCode
            );

            model.addAttribute("peData", list);
            model.addAttribute("stateWiseData", statePEData);
            model.addAttribute("distPEData", distPEData);
            model.addAttribute("initialStateName", defaultStateName);
            model.addAttribute("pieGradeData", pieGradeData);
            model.addAttribute("GradeStateData", GradeStateData);


        } catch (Exception e) {
            System.err.println("Error in projectEvaluationDashBoard: " + e.getMessage());
            e.printStackTrace();
        }

        return mav;
    }

    @RequestMapping(value = "/getDistrictData", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectEvaluationBean> getDistrictData(HttpServletRequest request) {
        List<ProjectEvaluationBean> distPEData = new ArrayList<>();
        try {
            String stcd = request.getParameter("stcd");
            if (stcd != null && !stcd.isEmpty()) {
                distPEData = PEService.getDistProjEvlData(Integer.parseInt(stcd));
            }
        } catch (Exception e) {
            System.err.println("Error fetching district data: " + e.getMessage());
            e.printStackTrace();
        }
        return distPEData;
    }
    
    @RequestMapping(value = "/getStateDataByGrade", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectEvaluationBean> getStateDataByGrade(@RequestParam("grade") String grade) {
        List<ProjectEvaluationBean> stateDataByGrade = new ArrayList<>();
        try {
            if (grade != null && !grade.isEmpty()) {
                stateDataByGrade = PEService.getGradeWiseStateProjEvlData(grade);
            }
        } catch (Exception e) {
            System.err.println("Error fetching state data by grade: " + e.getMessage());
            e.printStackTrace();
        }
        return stateDataByGrade;
    }
    
}
