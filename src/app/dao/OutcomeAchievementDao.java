package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.OutcomeAchievementBean;

public interface OutcomeAchievementDao {
	String getFinancialYearForOutcomeAchievement(Integer pCode);
	List<String> getCurrentFinYrMonthId(Integer pCode,String finYr);
	List<OutcomeAchievementBean> getActivityWithTarget(Integer pCode,Integer finYr,Integer monthId);
	LinkedHashMap<Integer,String> getProjectByRegIdForOutcomeAchievement(Integer regId);
	LinkedHashMap<Integer,String> getUserToForward(Integer regId,String userType,Character action,Integer achid);
	String saveAchievementAsDraft(Integer pCode,String headCode,String activityCode,String ach,Integer finYr,Integer monthId,Integer sentFrom,Integer sentTo,String createdBy);
	List<OutcomeAchievementBean> viewMovement(Integer regId);
	List<OutcomeAchievementBean> checkForAlreadyForwardedOutcomeAchievement(Integer achId,Integer regId);
	String forwardOutcomeAchievementByWCDCSLNA(Integer sentTo,Integer sentFrom,Integer achid,Character action,String remarks,String userType);
	List<OutcomeAchievementBean> getOutcomeAchievementDetails(Integer achId);
	List<OutcomeAchievementBean> getOutcomeAchievementMovementDetails(Integer achId);

}
