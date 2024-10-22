package app.controllers.baseline;

import java.math.BigDecimal;
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

import app.bean.Login;
import app.bean.NewBaseLineSurveyBean;
import app.bean.PhysicalActBean;
import app.model.BlsOutDetailCrop;
import app.model.BlsOutDetailCropAchiev;
import app.model.BlsOutMain;
import app.model.MBlsOutcome;
import app.model.master.IwmpMPhySubactivity;
import app.service.ProjectMasterService;
import app.service.outcome.BaseLineOutcomeService;

@Controller("baselineSurveyUpdateController")
public class BaselineSurveyUpdateController {

	HttpSession session = null;

	@Autowired(required = true)
	ProjectMasterService projectMasterService;

	@Autowired(required = true)
	BaseLineOutcomeService bso;

	@RequestMapping(value = "/showBaselinedata", method = RequestMethod.GET)
	public ModelAndView showBaselinedata(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if (session != null && session.getAttribute("loginID") != null) {
			Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
			mav = new ModelAndView("project/showbaselinecomplete");
			mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}
		return mav;
	}

	@RequestMapping(value = "/getBaselineSaveData", method = RequestMethod.POST)
	public ModelAndView getBaselineSaveData(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String project = request.getParameter("ddlProject");
		session = request.getSession(true);
		if (session != null && session.getAttribute("loginID") != null) {
			mav = new ModelAndView("project/showbaselinecomplete");
			mav.addObject("baselinedata", bso.getbaselinefinaldata(Integer.parseInt(project)));

		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());

		}
		return mav;
	}

	@RequestMapping(value = "/getcroptype", method = RequestMethod.POST)
	@ResponseBody
	public LinkedHashMap<Integer, String> getcroptype(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		session = request.getSession(true);
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		if (session != null && session.getAttribute("loginID") != null) {
			map = bso.getcroptypeCode();
		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());

		}
		return map;
	}
	
	@RequestMapping(value = "/getcroptypeupdate", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> getcroptypeupdate(HttpServletRequest request,
			@RequestParam("projId") Integer projId, @RequestParam("vCode") Integer vCode, @RequestParam("plotNo") String plotNo, @RequestParam("season") Integer season) {
		HashMap<String, String> res = new HashMap<String, String>();
		try {
			res = bso.getcroptypeCode(projId, vCode,plotNo,season);
		} catch (Exception ex) {
			res = null;
		}
		return res;
	}

	@RequestMapping(value = "/baselinecdatafind", method = RequestMethod.GET)
	@ResponseBody
	public List<NewBaseLineSurveyBean> baselinecdatafind(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") Integer id) {
		session = request.getSession(true);
		// List<BlsOutDetailCrop> proj = new ArrayList<BlsOutDetailCrop>();
		List<NewBaseLineSurveyBean> finalList = new ArrayList<NewBaseLineSurveyBean>();
		NewBaseLineSurveyBean bean = new NewBaseLineSurveyBean();
		if (session != null && session.getAttribute("loginID") != null) {

			BlsOutDetailCropAchiev proj = bso.findbaselineedata(id);
			if (proj != null) {
				bean = new NewBaseLineSurveyBean();
				// bean.setCropid(proj.getMBlsOutcomeByCropTypeId());
				bean.setArea(proj.getCropArea());
				// bean.setMBlsOutcomeBySeasonId(proj.getMBlsOutcomeBySeasonId());
				if(proj.getMBlsOutcomeBySeasonId()!=null)
					bean.setSeason_id(proj.getMBlsOutcomeBySeasonId().getMBlsOutIdPk());
				bean.setCrop_production(proj.getCropProduction());
				bean.setCrop_type_id(proj.getMBlsOutcomeByCropTypeId().getMBlsOutIdPk());
				bean.setAvg_income(proj.getAvgIncome());
				bean.setBls_out_detail_id_pk(proj.getBlsOutDetailAchiev().getBlsOutDetailIdPk());
				bean.setBls_out_main_id_pk(proj.getBlsOutDetailAchiev().getBlsOutMainAchiev().getBlsOutMainIdPk());
				bean.setBls_out_detail_crop_id_pk(proj.getBlsOutDetailCropIdPk());
				bean.setVcode(proj.getBlsOutDetailAchiev().getBlsOutMainAchiev().getIwmpVillage().getVcode());
				bean.setPlot_no(proj.getBlsOutDetailAchiev().getBlsOutMainAchiev().getPlotNo());
				bean.setProj_id(proj.getBlsOutDetailAchiev().getBlsOutMainAchiev().getIwmpMProject().getProjectId());
				/*
				 * bean.setArea(list.getArea());
				 * bean.setHeaddesc(list.getIwmpMPhyActivity().getIwmpMPhyHeads().getHeadDesc())
				 * ; bean.setSubactivitydesc(list.getSubActivityDesc());
				 * bean.setSeqno(list.getSeqNo());
				 * bean.setActdesc(list.getIwmpMPhyActivity().getActivityDesc());
				 * bean.setStatus(list.getStatus());
				 */

				// bean.setHeaddesc(null)
				finalList.add(bean);
			}

		} else {
			// proj=null;

		}
		return finalList;
	}

	@RequestMapping(value = "/updatebaselineDetail", method = RequestMethod.POST)
	public ModelAndView updatebaselinetypeData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("bsl_id") int id, @RequestParam("season") int season, @RequestParam("ctype") int ctype,
			@RequestParam("areahac") BigDecimal areahac, @RequestParam("crop_prod") BigDecimal crop_prod,
			@RequestParam("avg_income") BigDecimal avg_income) {
		ModelAndView mav = new ModelAndView();
		mav = new ModelAndView("project/showbaselinecomplete");
		session = request.getSession(true);
		Boolean res=false;
		Integer regId=null ;
		if (session != null && session.getAttribute("loginID") != null) {
			regId = Integer.parseInt(session.getAttribute("regId").toString());
			BlsOutDetailCropAchiev bod=new BlsOutDetailCropAchiev();
			bod.setBlsOutDetailCropIdPk(id);
			MBlsOutcome obj=new MBlsOutcome();
			obj.setMBlsOutIdPk(season);
			bod.setMBlsOutcomeBySeasonId(obj);
			
			obj=new MBlsOutcome();
			obj.setMBlsOutIdPk(ctype);
			bod.setMBlsOutcomeByCropTypeId(obj);
			
			bod.setCropArea(areahac);
			bod.setCropProduction(crop_prod);
			bod.setAvgIncome(avg_income);
			bod.setCreatedBy(session.getAttribute("loginID").toString());
			res = bso.updateBaselineCropAchiev(bod);
			if (res==true) {
				mav.addObject("message", "Record has been updated successfully");
			} else {
				mav.addObject("message", "Could not update Record");
				//mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
			}
		} else {
			mav.addObject("message", "Problem on updated data");
		}
		mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
	
		return mav;
	}
}
