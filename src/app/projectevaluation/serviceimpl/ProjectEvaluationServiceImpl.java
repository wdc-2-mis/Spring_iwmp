package app.projectevaluation.serviceimpl;

import java.math.BigDecimal;

import java.util.LinkedHashMap;
import java.util.List;


import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import app.projectevaluation.bean.ProjectEvaluationBean;
import app.projectevaluation.dao.ProjectEvaluationDAO;

import app.projectevaluation.service.ProjectEvaluationService;
import app.projectevaluation.model.WdcpmksyCroppedDetails1;
import app.projectevaluation.model.WdcpmksyCroppedDetails2;
import app.projectevaluation.model.WdcpmksyEquityAspect;
import app.projectevaluation.model.WdcpmksyProductionDetails;
import app.projectevaluation.model.WdcpmksyProjectProfileEvaluation;


@Service("projectEvaluationService")
public class ProjectEvaluationServiceImpl implements ProjectEvaluationService{

	@Autowired
	ProjectEvaluationDAO PEDAO;

	@Override
	public List<ProjectEvaluationBean> getMandayDeatails(Integer profileid) {
		return PEDAO.getMandayDeatails(profileid);
	}
	
	@Override
	public String saveMandaysDetails(Integer profile_id, BigDecimal culturable_wasteland,
			Integer whs_constructed_rejuvenated, BigDecimal soil_moisture, BigDecimal protective_irrigation,
			BigDecimal degraded_rainfed, BigDecimal farmer_income, Integer farmer_benefited, BigDecimal dug_well,
			Integer fromno, Integer mandays_generated, BigDecimal tube_well, HttpSession session, Character areatype, BigDecimal conculturable, 
			Integer conwhs, BigDecimal consoil, BigDecimal conprotective, BigDecimal condegraded_rainfed, BigDecimal confarmer_income, 
			Integer confarmer_benefited, Integer conmandays, BigDecimal condug_well, BigDecimal contube_well) {
		return PEDAO.saveMandaysDetails(profile_id, culturable_wasteland, whs_constructed_rejuvenated, soil_moisture, protective_irrigation, 
				degraded_rainfed, farmer_income, farmer_benefited, dug_well, fromno, mandays_generated, tube_well, session, areatype, 
				conculturable, conwhs, consoil, conprotective, condegraded_rainfed, confarmer_income, confarmer_benefited, conmandays, 
				condug_well, contube_well);
	}
	
	@Override
	public LinkedHashMap<Integer, List<ProjectEvaluationBean>> getprojProfileData(Integer dcode, Integer pcode) {
		return PEDAO.getprojProfileData(dcode, pcode);
	}
	
	@Override
	public String saveIndicatorEvaluationDetails(Integer profile_id,Integer fromno, Integer wcdc, Integer wc, Integer pia, String admiMechanism,
			String admiMechanismRemark, Character dprSlna, String dprSlnaRemark, Character allManpower,
			String allManpowerRemark, String wcdcRemark, String piaRemark, String wcRemark, HttpSession session) {
		return PEDAO.saveIndicatorEvaluationDetails(profile_id,fromno, wcdc, wc, pia, admiMechanism, admiMechanismRemark, dprSlna, dprSlnaRemark, allManpower, allManpowerRemark, wcdcRemark, piaRemark, wcRemark, session);
	}
	
	@Override
	public List<ProjectEvaluationBean> getFundUtilization(Integer profileid) {
		return PEDAO.getFundUtilization(profileid);
	}
		
	@Override
	public String saveFundUtilization(Integer projectProfileId, BigDecimal preCentralShare, BigDecimal midCentralShare, String rmkCentralShare, 
			BigDecimal preStateShare, BigDecimal midStateShare, String rmkStatelShare, BigDecimal preTotalFund, BigDecimal midTotalFund, String rmkTotalFund, BigDecimal preConPlannedFund, BigDecimal midConPlannedFund, String rmkConPlannedFund, 
			BigDecimal preExCon, BigDecimal midExCon, String rmkExCon, HttpSession session, Integer fromno) {
		
		return PEDAO.saveFundUtilization(projectProfileId, 
				preCentralShare, midCentralShare, rmkCentralShare, preStateShare, midStateShare, rmkStatelShare, 
				preTotalFund, midTotalFund, rmkTotalFund, preConPlannedFund, midConPlannedFund, rmkConPlannedFund, 
				preExCon, midExCon, rmkExCon, session, fromno);
	}
	
	@Override
	public List<WdcpmksyEquityAspect> getEquityAspect(Integer profileid) {
		return PEDAO.getEquityAspect(profileid);
	}
	
	@Override
	public String saveEquityAspect(Integer projectProfileId, Boolean pWatershedCom, Boolean cWatershedCom, String rmkWatershedCom, Boolean pFpoShgVli, 
			Boolean cFpoShgVli, String rmkFpoShgVli, Boolean pLivelihood, Boolean cLivelihood, String rmkLivelihood, HttpSession session, Integer fromno) {
		
		return PEDAO.saveEquityAspect(projectProfileId, pWatershedCom, cWatershedCom, rmkWatershedCom, pFpoShgVli, cFpoShgVli, rmkFpoShgVli,
				pLivelihood, cLivelihood, rmkLivelihood, session, fromno);
	}
	
	@Override
	public String saveExecutionPlanWork(Integer profile_id, Integer created_work, String created_work_remark,
			Integer completed_work, String completed_work_remark, Integer ongoing_work, String ongoing_work_remark,
			Integer fromno, HttpSession session) {
		return PEDAO.saveExecutionPlanWork(profile_id, created_work, created_work_remark, completed_work, completed_work_remark, ongoing_work, ongoing_work_remark, fromno, session);
	}


	@Override
	public String saveQualityShapeFile(Integer profile_id, BigDecimal shape_file_area, String shape_file_area_remark,
			BigDecimal variation_area, String variation_area_remark, Integer fromno, HttpSession session) {
		return PEDAO.saveQualityShapeFile(profile_id, shape_file_area, shape_file_area_remark, variation_area, variation_area_remark, fromno, session);
	}

	@Override
	public String saveGeoTagDetails(Integer profile_id, Integer geo_tagg_work, String geo_tagg_work_remark,
			Integer fromno, HttpSession session) {
		return PEDAO.saveGeoTagDetails(profile_id, geo_tagg_work, geo_tagg_work_remark, fromno, session);
	}

	@Override
	public List<ProjectEvaluationBean> monthYear() {
		return PEDAO.monthYear();
	}

	@Override
	public String insertprojectProfile(Integer projid, Integer fcode, Integer mcode, Integer evaId,
			BigDecimal sanctionedC, BigDecimal cShare, BigDecimal sShare, BigDecimal sancitonedP, Integer villageC,
			Integer waterC, Integer membersWC, Integer householdsC, HttpSession session, HttpServletRequest request) {
		return PEDAO.insertprojectProfile(projid, fcode, mcode, evaId, sanctionedC, cShare, sShare, sancitonedP, villageC,
					waterC, membersWC, householdsC, session, request);
	}
	
	@Override
	public List<WdcpmksyCroppedDetails1> getCroppedDetails(Integer projProfId) {
		return PEDAO.getCroppedDetails(projProfId);
	}

	@Override
	public String checkProjectProfileStatus(String project) {
		return PEDAO.checkProjectProfileStatus(project);
	}

	@Override
	public LinkedHashMap<Integer, List<ProjectEvaluationBean>> fetchprojProfileData(Integer pcode) {
		return PEDAO.fetchprojProfileData(pcode);
	}

	@Override
	public String saveOrUpdateCroppedDetails(HttpServletRequest request, HttpSession session, Integer projProfId, BigDecimal kharifCrop,BigDecimal rabiCrop, BigDecimal thirdCrop, BigDecimal cereals,BigDecimal pulses, BigDecimal oilSeed, BigDecimal millets,
			BigDecimal others, BigDecimal horticulture,BigDecimal netSown,BigDecimal cropIntensity, BigDecimal diversifiedCrop, BigDecimal ckharifCrop, BigDecimal crabiCrop, BigDecimal cthirdCrop, BigDecimal ccereals, BigDecimal cpulses,
			BigDecimal coilSeed, BigDecimal cmillets, BigDecimal cothers, BigDecimal chorticulture, BigDecimal cnetSown,
			BigDecimal ccropIntensity, BigDecimal cdiversifiedCrop) {
		return PEDAO.saveOrUpdateCroppedDetails(request, session, projProfId, kharifCrop, rabiCrop, thirdCrop, cereals, pulses, oilSeed, millets, others, horticulture, netSown, cropIntensity, diversifiedCrop
				, ckharifCrop, crabiCrop, cthirdCrop, ccereals, cpulses, coilSeed, cmillets, cothers, chorticulture, cnetSown, ccropIntensity, cdiversifiedCrop);
	}

	@Override
	public Integer getProjectProfileId(Integer projId, Integer finYrId, Integer monthId) {
		return PEDAO.getProjectProfileId(projId, finYrId, monthId);
	}

	@Override
	public List<ProjectEvaluationBean> getExecutionPlanWork(Integer profile_id) {
		return PEDAO.getExecutionPlanWork(profile_id);
	}

	
	@Override
	public List<ProjectEvaluationBean> getIndicatorEvaluation(Integer profile_id) {
		return PEDAO.getIndicatorEvaluation(profile_id);
	}

	@Override
	public List<ProjectEvaluationBean> getQualityShapeFile(Integer profile_id) {
		return PEDAO.getQualityShapeFile( profile_id);
	}
	@Override
	public List<ProjectEvaluationBean> getStatusGeotagWork(Integer profile_id) {
		return PEDAO.getStatusGeotagWork(profile_id);
	}
	@Override
	public LinkedHashMap<Integer, String> getCurrentFinYear() {
		return PEDAO.getCurrentFinYear();
	}
	@Override
	public LinkedHashMap<Integer, String> getmonthforproject() {
		return PEDAO.getmonthforproject();
	}
    @Override
    public List<ProjectEvaluationBean> getprojectstatus(int project, int month) {
	return PEDAO.getprojectstatus(project, month);
    }

	@Override
	public String saveEcoperspectiveDetails(Integer profile_id, String naturalresource, String naturalresourceRemark,
			String norm, String normRemark, String antrlasset, String antrlassetRemark, String controlntlresource,
			String controlnorm, Integer fromno, String controlantrlasset, HttpSession session) {
		return PEDAO.saveEcoperspectiveDetails(profile_id, naturalresource, naturalresourceRemark, norm, normRemark, antrlasset, antrlassetRemark, controlntlresource, controlnorm, fromno, controlantrlasset, session);
	}

	@Override
	public List<ProjectEvaluationBean> getEcoPerspective(Integer profile_id) {
		return PEDAO.getEcoPerspective(profile_id);
	}

	@Override
	public String saveOrUpdateCroppedDetails2(HttpServletRequest request, HttpSession session, Integer projProfId, BigDecimal niltosingle,
			BigDecimal sdcrop, BigDecimal plantation, BigDecimal rice, BigDecimal wheat, BigDecimal pulses,
			BigDecimal millets, BigDecimal oilseed, BigDecimal others, BigDecimal cniltosingle, BigDecimal csdcrop,
			BigDecimal cplantation, BigDecimal crice, BigDecimal cwheat, BigDecimal cpulses, BigDecimal cmillets,
			BigDecimal coilseed, BigDecimal cothers) {
		// TODO Auto-generated method stub
		return PEDAO.saveOrUpdateCroppedDetails2(request, session, projProfId, niltosingle, sdcrop, plantation, rice, wheat, pulses, millets, oilseed,
				others, cniltosingle, csdcrop, cplantation, crice, cwheat, cpulses, cmillets, coilseed, cothers);
	}

	@Override
	public List<WdcpmksyCroppedDetails2> getCroppedDetails2(Integer projProfId) {
		// TODO Auto-generated method stub
		return PEDAO.getCroppedDetails2(projProfId);
	}
	
	@Override
	public List<WdcpmksyProductionDetails> getProductionDetails(Integer projProfId) {
		return PEDAO.getProductionDetails(projProfId);
	}

	@Override
	public String saveOrUpdateProductionDetails(HttpServletRequest request, HttpSession session, Integer projProfId,
			BigDecimal milch, BigDecimal fodder, Integer ruralUrban, Integer spring, Integer benefit, Integer shg,
			Integer fpo, Integer ug, Integer mshg, Integer mfpo, Integer mug, BigDecimal trunoverFpo,
			BigDecimal incomeFpo, BigDecimal annualIncomeShg, BigDecimal cmilch, BigDecimal cfodder,
			Integer cruralUrban, Integer cspring, Integer cbenefit, Integer cshg, Integer cfpo, Integer cug,
			Integer cmshg, Integer cmfpo, Integer cmug, BigDecimal ctrunoverFpo, BigDecimal cincomeFpo,
			BigDecimal cannualIncomeShg) {
		return PEDAO.saveOrUpdateProductionDetails(request, session, projProfId, milch, fodder, ruralUrban, spring,
				benefit, shg, fpo, ug, mshg, mfpo, mug, trunoverFpo, incomeFpo, annualIncomeShg, cmilch, cfodder,
				cruralUrban, cspring, cbenefit, cshg, cfpo, cug, cmshg, cmfpo, cmug, ctrunoverFpo, cincomeFpo,
				cannualIncomeShg);
	}

	@Override
	public String completeprojEvaldata(Integer projProfId) {
		// TODO Auto-generated method stub
		return PEDAO.completeprojEvaldata(projProfId);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjByDCode(Integer dCode) {
		// TODO Auto-generated method stub
		return PEDAO.getProjByDCode(dCode);
	}

	@Override
	public List<WdcpmksyProjectProfileEvaluation> getprojectevorptdata(Integer pCode) {
		// TODO Auto-generated method stub
		return PEDAO.getprojectevorptdata(pCode);
	}

	
	@Override
	public ProjectEvaluationBean getProjectDetails(int projId) {
		// TODO Auto-generated method stub
		return PEDAO.getProjectDetails(projId);
	}


}
