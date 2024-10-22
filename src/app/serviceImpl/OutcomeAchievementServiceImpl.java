package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.OutcomeAchievementBean;
import app.dao.OutcomeAchievementDao;
import app.service.OutcomeAchievementService;

@Service("outcomeAchievementService")
public class OutcomeAchievementServiceImpl implements OutcomeAchievementService{
	
	@Autowired
	OutcomeAchievementDao outcomeAchievementDao;

	@Override
	public String getFinancialYearForOutcomeAchievement(Integer pCode) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.getFinancialYearForOutcomeAchievement(pCode);
	}

	@Override
	public List<String> getCurrentFinYrMonthId(Integer pCode, String finYr) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.getCurrentFinYrMonthId(pCode, finYr);
	}

	@Override
	public List<OutcomeAchievementBean> getActivityWithTarget(Integer pCode, Integer finYr, Integer monthId) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.getActivityWithTarget(pCode, finYr, monthId);
	}

	@Override
	public LinkedHashMap<Integer, String> getProjectByRegIdForOutcomeAchievement(Integer regId) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.getProjectByRegIdForOutcomeAchievement(regId);
	}

	@Override
	public LinkedHashMap<Integer, String> getUserToForward(Integer regId, String userType, Character action,
			Integer achid) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.getUserToForward(regId,userType,action,achid);
	}

	@Override
	public String saveAchievementAsDraft(Integer pCode,String headCode, String activityCode, String ach, Integer finYr, Integer monthId,
			Integer sentFrom, Integer sentTo, String createdBy) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.saveAchievementAsDraft(pCode, headCode, activityCode, ach, finYr, monthId, sentFrom, sentTo, createdBy);
	}

	@Override
	public List<OutcomeAchievementBean> viewMovement(Integer regId) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.viewMovement(regId);
	}

	@Override
	public List<OutcomeAchievementBean> checkForAlreadyForwardedOutcomeAchievement(Integer achId, Integer regId) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.checkForAlreadyForwardedOutcomeAchievement(achId, regId);
	}

	@Override
	public String forwardOutcomeAchievementByWCDCSLNA(Integer sentTo, Integer sentFrom, Integer achid, Character action,
			String remarks, String userType) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.forwardOutcomeAchievementByWCDCSLNA( sentTo, sentFrom, achid, action, remarks, userType);
	}

	@Override
	public List<OutcomeAchievementBean> getOutcomeAchievementDetails(Integer achId) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.getOutcomeAchievementDetails(achId);
	}

	@Override
	public List<OutcomeAchievementBean> getOutcomeAchievementMovementDetails(Integer achId) {
		// TODO Auto-generated method stub
		return outcomeAchievementDao.getOutcomeAchievementMovementDetails(achId);
	}

}
