package app.projectevaluation.dao;
import java.math.BigDecimal;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import app.projectevaluation.bean.ProjectEvaluationBean;
import app.projectevaluation.model.WdcpmksyCroppedDetails1;
import app.projectevaluation.model.WdcpmksyCroppedDetails2;
import app.projectevaluation.model.WdcpmksyEquityAspect;
import app.projectevaluation.model.WdcpmksyProductionDetails;
import app.projectevaluation.model.WdcpmksyProjectProfileEvaluation;




public interface ProjectEvaluationDAO {

	List<ProjectEvaluationBean> getMandayDeatails(Integer profileid);

	String saveMandaysDetails(Integer profile_id, BigDecimal culturable_wasteland, Integer whs_constructed_rejuvenated,
			BigDecimal soil_moisture, BigDecimal protective_irrigation, BigDecimal degraded_rainfed,
			BigDecimal farmer_income, Integer farmer_benefited, BigDecimal dug_well, Integer fromno,
			Integer mandays_generated, BigDecimal tube_well, HttpSession session, Character areatype, BigDecimal conculturable, Integer conwhs, 
			BigDecimal consoil, BigDecimal conprotective, BigDecimal condegraded_rainfed, BigDecimal confarmer_income, Integer confarmer_benefited, 
			Integer conmandays, BigDecimal condug_well, BigDecimal contube_well);

	LinkedHashMap<Integer, List<ProjectEvaluationBean>> getprojProfileData(Integer dcode, Integer pcode);
	List<ProjectEvaluationBean> monthYear();

	List<ProjectEvaluationBean> getExecutionPlanWork(Integer profile_id);

	String saveExecutionPlanWork(Integer profile_id, Integer created_work, String created_work_remark,
			Integer completed_work, String completed_work_remark, Integer ongoing_work, String ongoing_work_remark,
			Integer fromno, HttpSession session);
	
	List<ProjectEvaluationBean> getQualityShapeFile(Integer profile_id);

	String saveQualityShapeFile(Integer profile_id, BigDecimal shape_file_area, String shape_file_area_remark,
			BigDecimal variation_area, String variation_area_remark, Integer fromno, HttpSession session);
	
	List<ProjectEvaluationBean> getStatusGeotagWork(Integer profile_id);

	String saveGeoTagDetails(Integer profile_id, Integer geo_tagg_work, String geo_tagg_work_remark, Integer fromno,
			HttpSession session);

	public String saveIndicatorEvaluationDetails(Integer profile_id, Integer fromno, Integer wcdc, Integer wc,
			Integer pia, String admiMechanism, String admiMechanismRemark, String dprSlna, String dprSlnaRemark,
			String allManpower, String allManpowerRemark, String wcdcRemark, String piaRemark, String wcRemark,
			HttpSession session);

	List<ProjectEvaluationBean> getFundUtilization(Integer profileid);
	
	public String saveFundUtilization(Integer projectProfileId, BigDecimal preCentralShare, BigDecimal midCentralShare,
			String rmkCentralShare, BigDecimal preStateShare, BigDecimal midStateShare, String rmkStatelShare,
			BigDecimal preTotalFund, BigDecimal midTotalFund, String rmkTotalFund, BigDecimal preConPlannedFund,
			BigDecimal midConPlannedFund, String rmkConPlannedFund, BigDecimal preExCon, BigDecimal midExCon,
			String rmkExCon, HttpSession session, Integer fromno);
	
	List<WdcpmksyEquityAspect> getEquityAspect(Integer profileid);
	
	public String saveEquityAspect(Integer projectProfileId, Boolean pWatershedCom, Boolean cWatershedCom, String rmkWatershedCom, Boolean pFpoShgVli, 
			Boolean cFpoShgVli, String rmkFpoShgVli, Boolean pLivelihood, Boolean cLivelihood, String rmkLivelihood, HttpSession session, Integer fromno);

	String insertprojectProfile(Integer projid, Integer fcode, Integer mcode, Integer evaId, BigDecimal sanctionedC,
			BigDecimal cShare, BigDecimal sShare, BigDecimal sancitonedP, Integer villageC, Integer waterC,
			Integer membersWC, Integer householdsC, HttpSession session, HttpServletRequest request);

	String checkProjectProfileStatus(String project);

	LinkedHashMap<Integer, List<ProjectEvaluationBean>> fetchprojProfileData(Integer pcode);

	String saveOrUpdateCroppedDetails(HttpServletRequest request, HttpSession session, Integer projProfId,
			BigDecimal kharifCrop, BigDecimal rabiCrop, BigDecimal thirdCrop, BigDecimal cereals, BigDecimal pulses,
			BigDecimal oilSeed, BigDecimal millets, BigDecimal others, BigDecimal horticulture, BigDecimal netSown,
			BigDecimal cropIntensity, BigDecimal diversifiedCrop, BigDecimal ckharifCrop, BigDecimal crabiCrop, BigDecimal cthirdCrop, BigDecimal ccereals, BigDecimal cpulses,
			BigDecimal coilSeed, BigDecimal cmillets, BigDecimal cothers, BigDecimal chorticulture, BigDecimal cnetSown,
			BigDecimal ccropIntensity, BigDecimal cdiversifiedCrop);

	Integer getProjectProfileId(Integer projId, Integer finYrId, Integer monthId);

	List<WdcpmksyCroppedDetails1> getCroppedDetails(Integer projProfId);
	
	List<ProjectEvaluationBean> getIndicatorEvaluation(Integer profile_id);

    LinkedHashMap<Integer, String> getCurrentFinYear();

	LinkedHashMap<Integer, String> getmonthforproject(int project);

	List<ProjectEvaluationBean> getprojectstatus(int project, int month);
	
	public String saveEcoperspectiveDetails(Integer profile_id, String naturalresource, String naturalresourceRemark,
		String norm, String normRemark, String antrlasset, String antrlassetRemark, String controlntlresource,
		String controlnorm, Integer fromno, String controlantrlasset, HttpSession session);

	String saveOrUpdateCroppedDetails2(HttpServletRequest request, HttpSession session, Integer projProfId, BigDecimal niltosingle,
			BigDecimal sdcrop, BigDecimal plantation, BigDecimal rice, BigDecimal wheat, BigDecimal pulses,
			BigDecimal millets, BigDecimal oilseed, BigDecimal others, BigDecimal cniltosingle, BigDecimal csdcrop,
			BigDecimal cplantation, BigDecimal crice, BigDecimal cwheat, BigDecimal cpulses, BigDecimal cmillets,
			BigDecimal coilseed, BigDecimal cothers);
	List<ProjectEvaluationBean> getEcoPerspective(Integer profile_id);
	
	List<WdcpmksyProductionDetails> getProductionDetails(Integer projProfId);
	
	String saveOrUpdateProductionDetails(HttpServletRequest request, HttpSession session, Integer projProfId,
			BigDecimal milch, BigDecimal fodder, Integer ruralUrban, Integer spring, Integer benefit, Integer shg,
			Integer fpo, Integer ug, Integer mshg, Integer mfpo, Integer mug, BigDecimal trunoverFpo,
			BigDecimal incomeFpo, BigDecimal annualIncomeShg, BigDecimal cmilch, BigDecimal cfodder,
			Integer cruralUrban, Integer cspring, Integer cbenefit, Integer cshg, Integer cfpo, Integer cug,
			Integer cmshg, Integer cmfpo, Integer cmug, BigDecimal ctrunoverFpo, BigDecimal cincomeFpo,
			BigDecimal cannualIncomeShg);
	List<WdcpmksyCroppedDetails2> getCroppedDetails2(Integer projProfId);

	String completeprojEvaldata(Integer projProfId);

	LinkedHashMap<Integer, String> getProjByDCode(Integer dCode);

	List<WdcpmksyProjectProfileEvaluation> getprojectevorptdata(Integer pCode);



}
