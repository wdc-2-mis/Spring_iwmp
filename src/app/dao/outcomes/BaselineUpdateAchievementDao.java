package app.dao.outcomes;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import app.bean.BaselineUpdateAchievementBean;
import app.model.project.WdcpmksyBaselineupdateAchievementDetail;

public interface BaselineUpdateAchievementDao {
	
	LinkedHashMap<Integer,String> getProjectByRegIdBaseline(Integer regId);
	String getFinancialYearForPlan(Integer pCode);
	List<String> getCurrentFinYrMonthId(Integer pCode,String finYr);
	List<BaselineUpdateAchievementBean> getActivityWithTarget(Integer pCode,Integer finYr,Integer monthId);
	String saveBaselineAchievementAsDraft(Integer pCode, Integer finYr, Integer monthId, Integer sentFrom, Integer sentTo, String createdBy, boolean blsconf, BigDecimal gross, BigDecimal grossachiv,
			BigDecimal total, BigDecimal totalachiv, BigDecimal cultivable, BigDecimal cultivableachiv, BigDecimal degraded, BigDecimal degradedachiv, BigDecimal rainfed, BigDecimal rainfedachiv, BigDecimal others, 
			BigDecimal othersachiv, BigDecimal protective, BigDecimal protectiveachiv, BigDecimal assured, BigDecimal assuredachiv, BigDecimal noirri, BigDecimal noirriachiv, BigDecimal single, BigDecimal singleachiv,
			BigDecimal doublec, BigDecimal doubleachiv, BigDecimal multiple, BigDecimal multipleachiv, BigDecimal nocrop, BigDecimal nocropachiv);
	List<BaselineUpdateAchievementBean> viewAchievement(Integer regId);
	List<BaselineUpdateAchievementBean> checkAlreadyForwardedBaselinAchievement(Integer achId,Integer regId);
	String forwardBaselineUpdateAchievementWCDC(Integer sentTo,Integer sentFrom,Integer achid,Character action,String remarks,String userType);
	LinkedHashMap<Integer,String> getUserToForwardkd(Integer regId,String userType,Character action,Integer achid);
	List<BaselineUpdateAchievementBean> viewMovement(Integer regId);
	List<BaselineUpdateAchievementBean> viewCompletedBaselineAchievement(Integer regId);
	List<WdcpmksyBaselineupdateAchievementDetail> getBaselineupdateAchievementDetails(Integer achievementid);
	List<BaselineUpdateAchievementBean> getBaselineupdateAchievementMovementDetails(Integer achievementid);
	
}
