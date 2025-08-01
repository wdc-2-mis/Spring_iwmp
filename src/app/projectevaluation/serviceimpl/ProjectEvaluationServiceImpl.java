package app.projectevaluation.serviceimpl;

import java.math.BigDecimal;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import app.projectevaluation.bean.CroppedDetailsReportBean;
import app.projectevaluation.bean.ProductionDetailsBean;
import app.projectevaluation.bean.ProjectEvaluationBean;
import app.projectevaluation.dao.ProjectEvaluationDAO;

import app.projectevaluation.service.ProjectEvaluationService;
import app.projectevaluation.model.WdcpmksyCroppedDetails1;
import app.projectevaluation.model.WdcpmksyCroppedDetails2;
import app.projectevaluation.model.WdcpmksyCroppedDetails3;
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
	public LinkedHashMap<Integer, List<ProjectEvaluationBean>> getprojProfileData(Integer dcode, Integer pcode) {
		return PEDAO.getprojProfileData(dcode, pcode);
	}
	
	@Override
	public String saveIndicatorEvaluationDetails(Integer profile_id,Integer fromno, Integer wcdc, Integer wc, Integer pia, 
			 Character dprSlna, String dprSlnaRemark, Character allManpower,
			String allManpowerRemark, String wcdcRemark, String piaRemark, String wcRemark, HttpSession session) {
		return PEDAO.saveIndicatorEvaluationDetails(profile_id,fromno, wcdc, wc, pia, dprSlna, dprSlnaRemark, allManpower, allManpowerRemark, wcdcRemark, piaRemark, wcRemark, session);
	}
	
	@Override
	public List<ProjectEvaluationBean> getFundUtilization(Integer profileid) {
		return PEDAO.getFundUtilization(profileid);
	}
	
	@Override
	public List<ProjectEvaluationBean> getFundDetails(Integer pcode) {
		return PEDAO.getFundDetails(pcode);
	}
		
	@Override
	public String saveFundUtilization(Integer projectProfileId, BigDecimal centralShare, String rmkCentralShare, BigDecimal stateShare, String rmkStatelShare, 
			BigDecimal totalFund, String rmkTotalFund, BigDecimal conPlannedFund, String rmkConPlannedFund, BigDecimal exCon, String rmkExCon,
			BigDecimal wdf, String rmkWdf, HttpSession session, Integer fromno) {
		
		return PEDAO.saveFundUtilization(projectProfileId, centralShare, rmkCentralShare, stateShare, rmkStatelShare, totalFund, rmkTotalFund, conPlannedFund, 
				rmkConPlannedFund, exCon, rmkExCon, wdf, rmkWdf, session, fromno);
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
			Integer waterC, Integer membersWC, Integer householdsC, String pagency, HttpSession session, HttpServletRequest request) {
		return PEDAO.insertprojectProfile(projid, fcode, mcode, evaId, sanctionedC, cShare, sShare, sancitonedP, villageC,
					waterC, membersWC, householdsC, pagency, session, request);
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
	public String saveOrUpdateCroppedDetails(HttpServletRequest request, HttpSession session, Integer projProfId, BigDecimal prekharifCrop,BigDecimal prerabiCrop, BigDecimal prethirdCrop, BigDecimal precereals,BigDecimal prepulses, BigDecimal preoilSeed, BigDecimal premillets,
			BigDecimal preothers, BigDecimal prehorticulture,BigDecimal prenetSown, BigDecimal precropIntensity, BigDecimal midkharifCrop, BigDecimal midrabiCrop, BigDecimal midthirdCrop, BigDecimal midcereals,BigDecimal midpulses, BigDecimal midoilSeed, BigDecimal midmillets,
			BigDecimal midothers, BigDecimal midhorticulture,BigDecimal midnetSown, BigDecimal midcropIntensity, BigDecimal ckharifCrop, BigDecimal crabiCrop, BigDecimal cthirdCrop, BigDecimal ccereals, BigDecimal cpulses, BigDecimal coilSeed, BigDecimal cmillets, BigDecimal cothers, 
			BigDecimal chorticulture, BigDecimal cnetSown, BigDecimal ccropIntensity, String kharifCropremark, String rabiCropremark, String thirdCropremark, String cerealsremark, String pulsesremark, String oilSeedremark, String milletsremark, String othersremark, String horticultureremark, 
			String netSownremark, String cropIntensityremark, String othercrop) {
		return PEDAO.saveOrUpdateCroppedDetails(request, session, projProfId, prekharifCrop, prerabiCrop, prethirdCrop, precereals, prepulses, preoilSeed, premillets, preothers, prehorticulture, prenetSown, precropIntensity,
				midkharifCrop, midrabiCrop, midthirdCrop, midcereals, midpulses, midoilSeed, midmillets, midothers, midhorticulture, midnetSown, midcropIntensity, ckharifCrop, crabiCrop, cthirdCrop, ccereals, cpulses, coilSeed, cmillets, 
				cothers, chorticulture, cnetSown, ccropIntensity, kharifCropremark, rabiCropremark, thirdCropremark, cerealsremark, pulsesremark, oilSeedremark, milletsremark, othersremark, horticultureremark, netSownremark, cropIntensityremark, othercrop);
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
	public String saveOrUpdateCroppedDetails2(HttpServletRequest request, HttpSession session, Integer profile_id, Integer projProfId,
			BigDecimal diversifiedcrops, BigDecimal niltosingle, BigDecimal sdcrop, Integer wHSConReju,
			BigDecimal soilandmoiscrops, BigDecimal degradedrainfed, BigDecimal cdiversifiedcrops,
			BigDecimal cniltosingle, BigDecimal csdcrop, Integer cWHSConReju, BigDecimal csoilandmoiscrops,
			BigDecimal cdegradedrainfed, String diversifiedcropsremark, String niltosingleremark, String sdcropremark,
			String WHSConRejuremark, String soilandmoiscropsremark, String degradedrainfedremark) {
		return PEDAO.saveOrUpdateCroppedDetails2(request, session, profile_id, projProfId, diversifiedcrops, niltosingle, sdcrop, wHSConReju, soilandmoiscrops, degradedrainfed,
				cdiversifiedcrops, cniltosingle, csdcrop, cWHSConReju, csoilandmoiscrops, cdegradedrainfed, diversifiedcropsremark, niltosingleremark,
				sdcropremark, WHSConRejuremark, soilandmoiscropsremark, degradedrainfedremark);
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
	public String saveOrUpdateProductionDetails(HttpServletRequest request, HttpSession session, Integer projProfId, BigDecimal preMilch, BigDecimal midMilch, 
			BigDecimal cMilch, String rmkMilch, BigDecimal preFodder, BigDecimal midFodder, BigDecimal cFodder, String rmkFodder, Integer preRuralUrban, 
			Integer midRuralUrban, Integer cRuralUrban, String rmkRuralUrban, Integer prespring, Integer midspring, Integer cSpring, String rmkSpring, Integer prebenefit, Integer midbenefit, Integer cBenefit, 
			String rmkBenefit, Integer preshg, Integer midshg, Integer cShg, String rmkShg, Integer prefpo, Integer midfpo, Integer cFpo, String rmkFpo, Integer preug, Integer midug, Integer cUg, String rmkUg, Integer preMShg, 
			Integer midMShg, Integer cMshg, String rmkMshg, Integer preMFpo, Integer midMFpo, Integer cMfpo, String rmkMfpo, Integer preMUg, Integer midMUg, Integer cMug, String rmkMug, BigDecimal preTrunOverFpo, 
			BigDecimal midTrunOverFpo, BigDecimal cTrunOverFpo, String rmkTrunOverFpo, BigDecimal preIncomeFpo, BigDecimal midIncomeFpo, BigDecimal cIncomeFpo, 
			String rmkIncomeFpo, BigDecimal preAnnualIncomeShg, BigDecimal midAnnualIncomeShg, BigDecimal cAnnualIncomeShg, String rmkAnnualIncomeShg) 
	{
		return PEDAO.saveOrUpdateProductionDetails(request, session, projProfId, preMilch, midMilch, cMilch, rmkMilch, preFodder, midFodder, cFodder, rmkFodder, 
				preRuralUrban, midRuralUrban, cRuralUrban, rmkRuralUrban, prespring, midspring, cSpring, rmkSpring, prebenefit, midbenefit, cBenefit, rmkBenefit, 
				preshg, midshg, cShg, rmkShg, prefpo, midfpo, cFpo, rmkFpo, preug, midug, cUg, rmkUg, preMShg, midMShg, cMshg, rmkMshg, preMFpo, midMFpo, cMfpo, 
				rmkMfpo, preMUg, midMUg, cMug, rmkMug, preTrunOverFpo, midTrunOverFpo, cTrunOverFpo, rmkTrunOverFpo, preIncomeFpo, midIncomeFpo, cIncomeFpo, rmkIncomeFpo, 
				preAnnualIncomeShg, midAnnualIncomeShg, cAnnualIncomeShg, rmkAnnualIncomeShg);
	}

	@Override
	public String completeprojEvaldata(Integer projProfId, String summary, Character grade) {
		// TODO Auto-generated method stub
		return PEDAO.completeprojEvaldata(projProfId, summary, grade);
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


	@Override
	public String updateProjProfileMonth(Integer projid, Integer monthid) {
		// TODO Auto-generated method stub
		return PEDAO.updateProjProfileMonth(projid, monthid);
	}
	@Override
	public String saveMandaysDetails(Integer profile_id, BigDecimal pre_farmer_income, BigDecimal mid_farmer_income,
			BigDecimal control_farmer_income, String remark_farmer_income, Integer farmer_benefited,
			Integer control_farmer_benefited, String remark_farmer_benefited, Integer mandays_generated,
			Integer control_mandays_generated, String remark_mandays_generated, BigDecimal pre_dug_well,
			BigDecimal mid_dug_well, BigDecimal control_dug_well, String remark_dug_well, BigDecimal pre_tube_well,
			BigDecimal mid_tube_well, BigDecimal control_tube_well, String remark_tube_well, Integer fromno,
			HttpSession session, Character area) {
		
		return PEDAO.saveMandaysDetails(profile_id, pre_farmer_income, mid_farmer_income, control_farmer_income, remark_farmer_income, 
				farmer_benefited, control_farmer_benefited, remark_farmer_benefited, mandays_generated, control_mandays_generated, 
				remark_mandays_generated, pre_dug_well, mid_dug_well, control_dug_well, remark_dug_well, pre_tube_well, mid_tube_well, 
				control_tube_well, remark_tube_well, fromno, session, area);
	}
	
	@Override
	public List<WdcpmksyCroppedDetails3> getCroppedDetails3(Integer projProfId) {
		// TODO Auto-generated method stub
		return PEDAO.getCroppedDetails3(projProfId);
	}

	@Override
	public String saveOrUpdateCroppedDetails3(HttpServletRequest request, HttpSession session, Integer projProfId,
			WdcpmksyCroppedDetails3 cropDetail3) {
		// TODO Auto-generated method stub
		return PEDAO.saveOrUpdateCroppedDetails3(request, session, projProfId, cropDetail3);
	}
	@Override
	public LinkedHashMap<Integer, List<ProjectEvaluationBean>> fetchcompleteProjProfileData(Integer pcode) {
		// TODO Auto-generated method stub
		return PEDAO.fetchcompleteProjProfileData(pcode);
	}
	
	@Override
	public LinkedHashMap<Integer, List<ProjectEvaluationBean>> getPlanWorkData(Integer pcode) {
		
		return PEDAO.getPlanWorkData(pcode);
	}

	@Override
	public LinkedHashMap<Integer, List<ProjectEvaluationBean>> getGeoTaggingWorks(Integer pcode) {
		return PEDAO.getGeoTaggingWorks(pcode);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjProfileBlock(Integer pcode) {
		return PEDAO.getProjProfileBlock(pcode);
	}
	
	@Override
	public List<ProjectEvaluationBean> getStateMidProjEvoluation() {
		return PEDAO.getStateMidProjEvoluation();
	}
	
	@Override
	public List<ProjectEvaluationBean> getDistMidProjEvoluation(Integer stcd) {
		return PEDAO.getDistMidProjEvoluation(stcd);
	}

	@Override
	public List<ProjectEvaluationBean> getStateMidProjEvlCropDetails() {
		return PEDAO.getStateMidProjEvlCropDetails();
	}
	
	@Override
	public List<ProjectEvaluationBean> getDistMidProjEvlCropDetails(Integer stcd) {
		return PEDAO.getDistMidProjEvlCropDetails(stcd);
	}
	
	@Override
	public List<ProjectEvaluationBean> getStateMidProjEvlWorkDetails() {
		return PEDAO.getStateMidProjEvlWorkDetails();
	}
	
	@Override
	public List<ProjectEvaluationBean> getDistMidProjEvlWorkDetails(Integer stcd) {
		return PEDAO.getDistMidProjEvlWorkDetails(stcd);
	}

	@Override
	public Map<String, List<CroppedDetailsReportBean>> getCroppedDetailsReportData() {
		// TODO Auto-generated method stub
		return PEDAO.getCroppedDetailsReportData();
	}

	@Override
	public List<ProductionDetailsBean> getAverageAnnualIncome() {
		// TODO Auto-generated method stub
		return PEDAO.getAverageAnnualIncome();
	}

	@Override
	public List<ProductionDetailsBean> getCommunityBasedData() {
		// TODO Auto-generated method stub
		return PEDAO.getCommunityBasedData();
	}

	@Override
	public List<ProductionDetailsBean> getDistwiseAverageAnnualIncome(Integer stcode) {
		// TODO Auto-generated method stub
		return PEDAO.getDistwiseAverageAnnualIncome(stcode);
	}

	@Override
	public List<ProductionDetailsBean> getDistwiseCommunityBasedData(Integer stcode) {
		// TODO Auto-generated method stub
		return PEDAO.getDistwiseCommunityBasedData(stcode);
	}

	@Override
	public List<CroppedDetailsReportBean> getDistwiseCropDetailsReportData(Integer stcode, String type) {
		// TODO Auto-generated method stub
		return PEDAO.getDistwiseCropDetailsReportData(stcode, type);
	}

	@Override
	public String getpAgency(String project) {
		// TODO Auto-generated method stub
		return PEDAO.getpAgency(project);
	}

	@Override
	public String updateProjAgency(Integer projid, String agencyName) {
		// TODO Auto-generated method stub
		return PEDAO.updateProjAgency(projid, agencyName);
	}
	
	
	
}
