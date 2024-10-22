package app.projectevaluation.controller;
import java.util.ArrayList;
import java.util.HashMap;
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

import app.bean.BaselineUpdateAchievementBean;
import app.bean.Login;
import app.projectevaluation.bean.ProjectEvaluationBean;
import app.projectevaluation.model.WdcpmksyProjectProfileEvaluation;
import app.projectevaluation.service.ProjectEvaluationService;
import app.service.DistrictMasterService;
import app.service.ProjectMasterService;

@Controller("PEReportController")
public class PEReportController {

	HttpSession session;
	
	@Autowired
	DistrictMasterService districtMasterService;
	
	@Autowired(required = true)
	ProjectMasterService projectMasterService;
	
	@Autowired
	ProjectEvaluationService PEService;
	
	@RequestMapping(value="/projEvoluationRpt", method = RequestMethod.GET)
	public ModelAndView projEvoluationRpt(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mav;
	    HttpSession session = request.getSession(false); // Don't create a new session if one doesn't exist
	    
	    if (session != null && session.getAttribute("loginID") != null) {
	        Integer stateCode = (session.getAttribute("stateCode") != null) 
	                            ? Integer.parseInt(session.getAttribute("stateCode").toString()) 
	                            : null;
	        if (stateCode != null) {
	            mav = new ModelAndView("projectEvaluation/projectEvolReport");
	            mav.addObject("districtList", districtMasterService.getDistrictByStateCodeWithDcode(stateCode));
	        } else {
	            mav = new ModelAndView("errorPage"); 
	        }
	    } else {
	        mav = new ModelAndView("login");
	        mav.addObject("login", new Login());
	    }
	    
	    return mav;
	}

	
	@RequestMapping(value="/getprojectforProjEva", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer,String> getprojectforProjEva(HttpServletRequest request,@RequestParam(value ="dCode") Integer dCode)
	{
		LinkedHashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
		map = PEService.getProjByDCode(dCode);
		return map;
	}

	@RequestMapping(value="/getprojEvoRptdata", method = RequestMethod.POST)
	@ResponseBody
	public List<ProjectEvaluationBean> getprojEvoRptDetails(HttpServletRequest request, HttpServletResponse response,
			 @RequestParam(value ="pCode") Integer pCode)
	{
		ModelAndView mav = new ModelAndView();
		ProjectEvaluationBean bean = new ProjectEvaluationBean();
		List<WdcpmksyProjectProfileEvaluation>list = new ArrayList<WdcpmksyProjectProfileEvaluation>();
		List<ProjectEvaluationBean> finallist = new ArrayList<ProjectEvaluationBean>();
		 HttpSession session = request.getSession(false);
		if(session!=null && session.getAttribute("loginID")!=null) 
		{
			list = PEService.getprojectevorptdata(pCode);
			
			if ((list != null) && (list.size() > 0)) {
				for(WdcpmksyProjectProfileEvaluation data: list)
				{
					bean = new ProjectEvaluationBean();
					bean.setPro_profileid(data.getProjectProfileId());
					bean.setFin_yr(data.getIwmpMFinYear().getFinYrDesc());
					bean.setProjname(data.getIwmpMProject().getProjName());
					bean.setDistrict(data.getIwmpMProject().getIwmpDistrict().getDcode());
					bean.setDistname(data.getIwmpMProject().getIwmpDistrict().getDistName());
					bean.setMonthname(data.getIwmpMMonth().getMonthName());
					finallist.add(bean);
				}
			}
			else {
				list = null;
				
			}
		}
		else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		
	return finallist;
	}
	
}
