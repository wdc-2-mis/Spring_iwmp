package app.controllers.baseline;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import app.model.BlsOutDetailCropAchiev;
import app.model.MBlsOutcome;
import app.service.ProjectMasterService;
import app.service.VillageMasterService;
import app.service.outcome.BaseLineOutcomeService;

@Controller("BaseLineOutcomeUpdate")
public class BaseLineOutcomeUpdateController {

	HttpSession session;

	@Autowired(required = true)
	BaseLineOutcomeService baseLineOutcomeService;

	@Autowired(required = true)
	VillageMasterService villageMasterService;

	@Autowired
	ProjectMasterService projectMasterService;

	@RequestMapping(value = "/blsoutupdate", method = RequestMethod.GET)
	public ModelAndView getBaseLineSurvey(HttpServletRequest request) {
		session = request.getSession(true);
		ModelAndView mav = new ModelAndView();
		if (session != null && session.getAttribute("loginID") != null) {
			if (session.getAttribute("roleName").toString().contains("PIA")) {
				Integer regId = Integer.parseInt(session.getAttribute("regId").toString());
				mav = new ModelAndView("baseline/baselineOutcomeUpdate");
				mav.addObject("projectList", baseLineOutcomeService.getProjectforBlsUpdateByRegId(regId));

				// mav.addObject("projectList", projectMasterService.getProjectByRegId(regId));
			} else {
				return new ModelAndView("pagenotfound");
			}

		} else {
			mav = new ModelAndView("login");
			mav.addObject("login", new Login());
		}

		return mav;
	}

	@RequestMapping(value = "/getPlotofVillageOfProject", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> getPlotofVillageOfProject(HttpServletRequest request,
			@RequestParam("projId") Integer projId, @RequestParam("vCode") Integer vCode) {
		HashMap<String, String> res = new HashMap<String, String>();
		try {
			res = baseLineOutcomeService.getPlotNoByProjectVillage(projId, vCode);
		} catch (Exception ex) {
			res = null;
		}
		return res;

	}

	@RequestMapping(value = "/getVillageOfProjectBlsUpdate", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<Integer, String> getVillageOfProject(HttpServletRequest request,
			@RequestParam("projId") Integer projId) {
		HashMap<Integer, String> res = new HashMap<Integer, String>();
		try {
			res = baseLineOutcomeService.getVillageOfProjectforBlsUpdate(projId);

		} catch (Exception ex) {
			res = null;
		}
		return res;

	}

	@RequestMapping(value = "/getCropDataOfPlotProject", method = RequestMethod.POST)
	@ResponseBody
	public List<NewBaseLineSurveyBean> getCropDataOfPlotProject(HttpServletRequest request,
			@RequestParam("plotno") String plotno, @RequestParam("vcode") String vcode,
			@RequestParam("projId") Integer projId) {
		List<NewBaseLineSurveyBean> res = new ArrayList<NewBaseLineSurveyBean>();
		try {

			res = baseLineOutcomeService.getCropDataOfPlotProject(Integer.parseInt(vcode), plotno, projId);

		} catch (Exception ex) {
			res = null;
		}
		return res;

	}

	@RequestMapping(value = "/getPlotDataOfAVillagetoUpdate", method = RequestMethod.POST)
	@ResponseBody
	public List<NewBaseLineSurveyBean> getPlotDataOfAVillage(HttpServletRequest request,
			@RequestParam("plotno") String plotno, @RequestParam("vcode") String vcode) {
		List<NewBaseLineSurveyBean> res = new ArrayList<NewBaseLineSurveyBean>();
		try {

			res = baseLineOutcomeService.getPlotDataOfAVillageForUpdate(Integer.parseInt(vcode), plotno);

		} catch (Exception ex) {
			res = null;
		}
		return res;

	}

	@RequestMapping(value = "/deleteCropData", method = RequestMethod.POST)
	@ResponseBody
	public String deleteCropData(HttpServletRequest request, @RequestParam("rowId") Integer rowId) {

		String res = "fail";
		try {
			String userId = session.getAttribute("loginID").toString();

			Boolean flag = baseLineOutcomeService.deleteBaselineCropAchiev(rowId);
			if (flag != null) {
				if (flag == true)
					res = "success";
				else if (flag == false)
					res = "fail";
			} else
				res = "single";

		} catch (Exception ex) {
			res = null;
			ex.printStackTrace();
		}
		return res;

	}

	@RequestMapping(value = "/updateBLS", method = RequestMethod.POST)
	@ResponseBody
	public String updateBLS(HttpServletRequest request, @RequestParam("projId") Integer projId,
			@RequestParam("vcode") Integer vcode, @RequestParam("plotNo") String plotNo,
			@RequestParam("projectArea") BigDecimal projectArea,
			@RequestParam("irrigationStatus") Integer irrigationStatus, @RequestParam("ownership") Integer ownership,
			@RequestParam("ownerName") String ownerName, @RequestParam("landClassification") Integer landClassification,@RequestParam("landSubClassification") String landSubClassification,
			@RequestParam("noOfCrop") String noOfCrop, @RequestParam("season") List<Integer> season,
			@RequestParam("cropType") List<Integer> cropType, @RequestParam("cropArea") List<BigDecimal> cropArea,
			@RequestParam("cropProduction") List<BigDecimal> cropProduction,
			@RequestParam("avgIncome") List<BigDecimal> avgIncome,
			@RequestParam("forestLandType") String forestLandType,
			@RequestParam("blsoutDetailId") Integer blsoutDetailId, @RequestParam("microIrrigation") BigDecimal microIrrigation) 
//	(HttpServletRequest request, @RequestParam("projId") Integer projId, @RequestParam("vcode") Integer vcode,
//			@RequestParam("plotNo") String plotNo, 
//			@RequestParam("projectArea") BigDecimal projectArea, 
//			@RequestParam("irrigationStatus") Integer irrigationStatus, @RequestParam("ownership") Integer ownership, 
//			@RequestParam("ownerName") String ownerName, @RequestParam("landClassification") Integer landClassification,@RequestParam("landSubClassification") String landSubClassification, 
//			@RequestParam("noOfCrop") String noOfCrop, @RequestParam("season") List<Integer> season, 
//			@RequestParam("cropType") List<Integer> cropType, @RequestParam("cropArea") List<BigDecimal> cropArea, 
//			@RequestParam("cropProduction") List<BigDecimal> cropProduction, 
//			@RequestParam("avgIncome") List<BigDecimal> avgIncome,
//			@RequestParam("forestLandType") String forestLandType)
	
	{
		String res = "fail";
		try {
			String userId = session.getAttribute("loginID").toString();
			/*
			 * System.out.println("-------------->" + projId + " : " + vcode + " : " +
			 * plotNo + " : " + projectArea + " : " + irrigationStatus + " : " + ownership +
			 * " : " + ownerName + " : " + landClassification + " : " + noOfCrop +
			 * " :season " + season + " : " + cropType + " : " + cropArea + " : " +
			 * cropProduction + " : " + avgIncome + " : " + forestLandType);
			 */

			Integer crop=(noOfCrop==""?0:Integer.parseInt(noOfCrop));
			Integer forest=(forestLandType==""?0:Integer.parseInt(forestLandType));
			res = baseLineOutcomeService.updateBLS(projId, vcode, plotNo, projectArea, irrigationStatus, ownership,
					ownerName, landClassification,landSubClassification, crop, season, cropType, cropArea, cropProduction, avgIncome,
					forest, userId, blsoutDetailId, microIrrigation);

		} catch (Exception ex) {
			res = null;
			ex.printStackTrace();
		}
		return res;

	}

	@RequestMapping(value = "/changeownershipOrcropno", method = RequestMethod.POST)
	@ResponseBody
	public String changeownershipOrcropno(HttpServletRequest request, @RequestParam("blsOutDetailId") Integer rowId) {

		String res = "fail";
		try {
			String userId = session.getAttribute("loginID").toString();

			Boolean flag = baseLineOutcomeService.deleteAllBaselineCropAchiev(rowId);
			if (flag == true)
				res = "success";
			else
				res = "fail";

		} catch (Exception ex) {
			res = null;
			ex.printStackTrace();
		}
		return res;

	}

	@RequestMapping(value = "/updateCropData", method = RequestMethod.POST)
	@ResponseBody
	public String updateCropData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("bsl_crop_id") int id, @RequestParam("season") String season,
			@RequestParam("ctype") int ctype, @RequestParam("areahac") BigDecimal areahac,
			@RequestParam("crop_prod") BigDecimal crop_prod, @RequestParam("avg_income") BigDecimal avg_income) {

		String res = "fail";
		Integer regId = null;
		try {
			String userId = session.getAttribute("loginID").toString();

			regId = Integer.parseInt(session.getAttribute("regId").toString());
			BlsOutDetailCropAchiev bod = new BlsOutDetailCropAchiev();
			bod.setBlsOutDetailCropIdPk(id);
			MBlsOutcome obj = new MBlsOutcome();
			if (!season.isEmpty()) {
				obj.setMBlsOutIdPk(Integer.parseInt(season));
				bod.setMBlsOutcomeBySeasonId(obj);
			}
			obj = new MBlsOutcome();
			obj.setMBlsOutIdPk(ctype);
			bod.setMBlsOutcomeByCropTypeId(obj);

			bod.setCropArea(areahac);
			bod.setCropProduction(crop_prod);
			bod.setAvgIncome(avg_income);
			bod.setCreatedBy(session.getAttribute("loginID").toString());
			Boolean flag = baseLineOutcomeService.updateBaselineCropAchiev(bod);

			if (flag == true)
				res = "success";
			else
				res = "fail";

		} catch (Exception ex) {
			res = null;
			ex.printStackTrace();
		}
		return res;

	}
	
	@RequestMapping(value = "/changeCropDeleteStatus", method = RequestMethod.POST)
	@ResponseBody
	public String changeCropDeleteStatus(HttpServletRequest request, @RequestParam("blsOutDetailId") Integer rowId) {

		String res = "fail";
		try {

			res = baseLineOutcomeService.changeCropDeleteStatus(rowId);

		} catch (Exception ex) {
			res = null;
			ex.printStackTrace();
		}
		return res;

	}

}
