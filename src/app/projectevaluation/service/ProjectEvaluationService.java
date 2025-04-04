package app.projectevaluation.service;


import java.math.BigDecimal;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import app.projectevaluation.bean.ProjectEvaluationBean;
import app.projectevaluation.model.WdcpmksyCroppedDetails1;
import app.projectevaluation.model.WdcpmksyCroppedDetails2;
import app.projectevaluation.model.WdcpmksyCroppedDetails3;
import app.projectevaluation.model.WdcpmksyEquityAspect;
import app.projectevaluation.model.WdcpmksyProductionDetails;
import app.projectevaluation.model.WdcpmksyProjectProfileEvaluation;

@Service("projectEvaluationService")

public interface ProjectEvaluationService {
	
	List<ProjectEvaluationBean> getMandayDeatails(Integer profileid);

	LinkedHashMap<Integer, List<ProjectEvaluationBean>> getprojProfileData(Integer dcode, Integer pcode);

	List<ProjectEvaluationBean> getExecutionPlanWork(Integer profile_id);
	
	String saveExecutionPlanWork(Integer profile_id, Integer created_work, String created_work_remark, Integer completed_work, String completed_work_remark, Integer ongoing_work, String ongoing_work_remark, Integer fromno, HttpSession session );
	
	List<ProjectEvaluationBean> getQualityShapeFile(Integer profile_id);
	
	String saveQualityShapeFile(Integer profile_id, BigDecimal shape_file_area, String shape_file_area_remark, BigDecimal variation_area, String variation_area_remark, Integer fromno, HttpSession session );
	
	List<ProjectEvaluationBean> getStatusGeotagWork(Integer profile_id);
	
	String saveGeoTagDetails(Integer profile_id, Integer geo_tagg_work, String geo_tagg_work_remark, Integer fromno, HttpSession session );


    List<ProjectEvaluationBean> monthYear();
	
	public String saveIndicatorEvaluationDetails(Integer profile_id, Integer fromno,Integer wcdc, Integer wc, Integer pia, String admiMechanism,
			String admiMechanismRemark, Character dprSlna, String dprSlnaRemark, Character allManpower,
			String allManpowerRemark, String wcdcRemark, String piaRemark, String wcRemark, HttpSession session);
	
	public String insertprojectProfile(Integer projid, Integer fcode, Integer mcode, Integer evaId, BigDecimal sanctionedC,
			BigDecimal cShare, BigDecimal sShare, BigDecimal sancitonedP, Integer villageC, Integer waterC,
			Integer membersWC, Integer householdsC, HttpSession session, HttpServletRequest request);
	
	String checkProjectProfileStatus(String project);
	
	LinkedHashMap<Integer, List<ProjectEvaluationBean>> fetchprojProfileData(Integer pcode);
	
	List<ProjectEvaluationBean> getFundUtilization(Integer profileid);
	
	List<ProjectEvaluationBean> getFundDetails(Integer pcode);
	
	public String saveFundUtilization(Integer projectProfileId, BigDecimal centralShare, String rmkCentralShare, BigDecimal stateShare, String rmkStatelShare, 
			BigDecimal totalFund, String rmkTotalFund, BigDecimal conPlannedFund, String rmkConPlannedFund, BigDecimal exCon, String rmkExCon, 
			HttpSession session, Integer fromno);
	
	List<WdcpmksyEquityAspect> getEquityAspect(Integer profileid);
	
	public String saveEquityAspect(Integer projectProfileId, Boolean pWatershedCom, Boolean cWatershedCom, String rmkWatershedCom, Boolean pFpoShgVli, 
			Boolean cFpoShgVli, String rmkFpoShgVli, Boolean pLivelihood, Boolean cLivelihood, String rmkLivelihood, HttpSession session, Integer fromno);


	List<WdcpmksyCroppedDetails1> getCroppedDetails(Integer projProfId);
	
	String saveOrUpdateProductionDetails(HttpServletRequest request, HttpSession session, Integer projProfId, BigDecimal preMilch, BigDecimal midMilch, 
			BigDecimal cMilch, String rmkMilch, BigDecimal preFodder, BigDecimal midFodder, BigDecimal cFodder, String rmkFodder, Integer preRuralUrban, 
			Integer midRuralUrban, Integer cRuralUrban, String rmkRuralUrban, Integer spring, Integer cSpring, String rmkSpring, Integer benefit, Integer cBenefit, 
			String rmkBenefit, Integer shg, Integer cShg, String rmkShg, Integer fpo, Integer cFpo, String rmkFpo, Integer ug, Integer cUg, String rmkUg, Integer mShg, 
			Integer cMshg, String rmkMshg, Integer mFpo, Integer cMfpo, String rmkMfpo, Integer mUg, Integer cMug, String rmkMug, BigDecimal preTrunOverFpo, 
			BigDecimal midTrunOverFpo, BigDecimal cTrunOverFpo, String rmkTrunOverFpo, BigDecimal preIncomeFpo, BigDecimal midIncomeFpo, BigDecimal cIncomeFpo, 
			String rmkIncomeFpo, BigDecimal preAnnualIncomeShg, BigDecimal midAnnualIncomeShg, BigDecimal cAnnualIncomeShg, String rmkAnnualIncomeShg);

	
	
	String saveOrUpdateCroppedDetails(HttpServletRequest request, HttpSession session, Integer projProfId, BigDecimal prekharifCrop,BigDecimal prerabiCrop, BigDecimal prethirdCrop, BigDecimal precereals,BigDecimal prepulses, BigDecimal preoilSeed, BigDecimal premillets,
			BigDecimal preothers, BigDecimal prehorticulture,BigDecimal prenetSown, BigDecimal precropIntensity, BigDecimal midkharifCrop, BigDecimal midrabiCrop, BigDecimal midthirdCrop, BigDecimal midcereals,BigDecimal midpulses, BigDecimal midoilSeed, BigDecimal midmillets,
			BigDecimal midothers, BigDecimal midhorticulture,BigDecimal midnetSown, BigDecimal midcropIntensity, BigDecimal ckharifCrop, BigDecimal crabiCrop, BigDecimal cthirdCrop, BigDecimal ccereals, BigDecimal cpulses, BigDecimal coilSeed, BigDecimal cmillets, BigDecimal cothers, 
			BigDecimal chorticulture, BigDecimal cnetSown, BigDecimal ccropIntensity, String kharifCropremark, String rabiCropremark, String thirdCropremark, String cerealsremark, String pulsesremark, String oilSeedremark, String milletsremark, String othersremark, String horticultureremark, 
			String netSownremark, String cropIntensityremark);

	
	Integer getProjectProfileId(Integer projId, Integer finYrId, Integer monthId);
	List<ProjectEvaluationBean> getIndicatorEvaluation(Integer profile_id);
	LinkedHashMap<Integer, String> getCurrentFinYear();
	LinkedHashMap<Integer, String> getmonthforproject();
	List<ProjectEvaluationBean> getprojectstatus(int project, int month);
	
	public String saveEcoperspectiveDetails(Integer profile_id, String naturalresource, String naturalresourceRemark,
			String norm, String normRemark, String antrlasset, String antrlassetRemark, String controlntlresource,
			String controlnorm, Integer fromno, String controlantrlasset, HttpSession session);
	
	List<ProjectEvaluationBean> getEcoPerspective(Integer profile_id);
	
	List<WdcpmksyProductionDetails> getProductionDetails(Integer projProfId);
	
	

	String saveOrUpdateCroppedDetails2(HttpServletRequest request, HttpSession session, Integer profile_id, Integer projProfId,
			BigDecimal diversifiedcrops, BigDecimal niltosingle, BigDecimal sdcrop, Integer WHSConReju,
			BigDecimal soilandmoiscrops, BigDecimal degradedrainfed, BigDecimal cdiversifiedcrops,
			BigDecimal cniltosingle, BigDecimal csdcrop, Integer cWHSConReju, BigDecimal csoilandmoiscrops,
			BigDecimal cdegradedrainfed, String diversifiedcropsremark, String niltosingleremark, String sdcropremark, String wHSConRejuremark, String soilandmoiscropsremark, String degradedrainfedremark);


	List<WdcpmksyCroppedDetails2> getCroppedDetails2(Integer projProfId);

	String completeprojEvaldata(Integer projProfId, String summary, Character grade);

	LinkedHashMap<Integer, String> getProjByDCode(Integer dCode);

	List<WdcpmksyProjectProfileEvaluation> getprojectevorptdata(Integer pCode);

	ProjectEvaluationBean getProjectDetails(int projId);

	String updateProjProfileMonth(Integer projid, Integer monthid);
	
	String saveMandaysDetails(Integer profile_id, BigDecimal pre_farmer_income, BigDecimal mid_farmer_income,
			BigDecimal control_farmer_income, String remark_farmer_income, Integer farmer_benefited,
			Integer control_farmer_benefited, String remark_farmer_benefited, Integer mandays_generated,
			Integer control_mandays_generated, String remark_mandays_generated, BigDecimal pre_dug_well,
			BigDecimal mid_dug_well, BigDecimal control_dug_well, String remark_dug_well, BigDecimal pre_tube_well,
			BigDecimal mid_tube_well, BigDecimal control_tube_well, String remark_tube_well, Integer fromno,
			HttpSession session, Character area);
	
	List<WdcpmksyCroppedDetails3> getCroppedDetails3(Integer projProfId);
	
	String saveOrUpdateCroppedDetails3(HttpServletRequest request, HttpSession session, Integer projProfId, WdcpmksyCroppedDetails3 cropDetail3);
	LinkedHashMap<Integer, List<ProjectEvaluationBean>> fetchcompleteProjProfileData(Integer pcode);

}
