package app.serviceImpl.outcomes;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.BaselineUpdateAchievementBean;
import app.dao.outcomes.BaselineUpdateAchievementDao;
import app.model.project.WdcpmksyBaselineupdateAchievementDetail;
import app.service.outcome.BaselineUpdateAchievementService;

@Service("BaselineUpdateAchievementService")
public class BaselineUpdateAchievementServiceImpl implements BaselineUpdateAchievementService{

	@Autowired
	BaselineUpdateAchievementDao dao;
	
	
	@Override
	public LinkedHashMap<Integer, String> getProjectByRegIdBaseline(Integer regId) {
		// TODO Auto-generated method stub
		return dao.getProjectByRegIdBaseline(regId);
	}
	
	@Override
	public String getFinancialYearForPlan(Integer pCode) {
		
		return dao.getFinancialYearForPlan(pCode);
	}

	@Override
	public List<String> getCurrentFinYrMonthId(Integer pCode, String finYr) {
		
		return dao.getCurrentFinYrMonthId(pCode, finYr);
	}

	@Override
	public List<BaselineUpdateAchievementBean> getActivityWithTarget(Integer pCode, Integer finYr, Integer monthId) {
		
		return dao.getActivityWithTarget(pCode, finYr, monthId);
	}

	@Override
	public String saveBaselineAchievementAsDraft(Integer pCode, Integer finYr, Integer monthId, Integer sentFrom,
			Integer sentTo, String createdBy, boolean blsconf, BigDecimal gross, BigDecimal grossachiv,
			BigDecimal total, BigDecimal totalachiv, BigDecimal cultivable, BigDecimal cultivableachiv,
			BigDecimal degraded, BigDecimal degradedachiv, BigDecimal rainfed, BigDecimal rainfedachiv,
			BigDecimal others, BigDecimal othersachiv, BigDecimal protective, BigDecimal protectiveachiv,
			BigDecimal assured, BigDecimal assuredachiv, BigDecimal noirri, BigDecimal noirriachiv, BigDecimal single,
			BigDecimal singleachiv, BigDecimal doublec, BigDecimal doubleachiv, BigDecimal multiple,
			BigDecimal multipleachiv, BigDecimal nocrop, BigDecimal nocropachiv) {
		// TODO Auto-generated method stub
		return dao.saveBaselineAchievementAsDraft(pCode, finYr, monthId, sentFrom, sentTo, createdBy, blsconf, gross, grossachiv, total, totalachiv, cultivable, cultivableachiv, degraded, degradedachiv, 
				rainfed, rainfedachiv, others, othersachiv, protective, protectiveachiv, assured, assuredachiv, noirri, noirriachiv, single, singleachiv, doublec, doubleachiv, multiple, multipleachiv, nocrop, nocropachiv);
	}

	@Override
	public List<BaselineUpdateAchievementBean> viewAchievement(Integer regId) {
		// TODO Auto-generated method stub
		return dao.viewAchievement(regId);
	}

	@Override
	public List<BaselineUpdateAchievementBean> checkAlreadyForwardedBaselinAchievement(Integer achId, Integer regId) {
		// TODO Auto-generated method stub
		return dao.checkAlreadyForwardedBaselinAchievement(achId, regId);
	}

	@Override
	public String forwardBaselineUpdateAchievementWCDC(Integer sentTo, Integer sentFrom, Integer achid,
			Character action, String remarks, String userType) {
		// TODO Auto-generated method stub
		return dao.forwardBaselineUpdateAchievementWCDC(sentTo, sentFrom, achid, action, remarks, userType);
	}

	@Override
	public LinkedHashMap<Integer, String> getUserToForwardkd(Integer regId, String userType, Character action,
			Integer achid) {
		// TODO Auto-generated method stub
		return dao.getUserToForwardkd(regId, userType, action, achid);
	}

	@Override
	public List<BaselineUpdateAchievementBean> viewMovement(Integer regId) {
		// TODO Auto-generated method stub
		return dao.viewMovement(regId);
	}

	@Override
	public List<BaselineUpdateAchievementBean> viewCompletedBaselineAchievement(Integer regId) {
		// TODO Auto-generated method stub
		return dao.viewCompletedBaselineAchievement(regId);
	}

	@Override
	public List<WdcpmksyBaselineupdateAchievementDetail> getBaselineupdateAchievementDetails(Integer achievementid) {
		// TODO Auto-generated method stub
		return dao.getBaselineupdateAchievementDetails(achievementid);
	}

	@Override
	public List<BaselineUpdateAchievementBean> getBaselineupdateAchievementMovementDetails(Integer achievementid) {
		// TODO Auto-generated method stub
		return dao.getBaselineupdateAchievementMovementDetails(achievementid);
	}

	

}
